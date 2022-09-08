package dev.capture;

import androidx.annotation.NonNull;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dev.utils.DevFinal;
import dev.utils.common.ThrowableUtils;
import dev.utils.common.cipher.Encrypt;
import okhttp3.Connection;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * detail: Http 抓包拦截器 ( 存在存储抓包数据逻辑 )
 * @author Ttt
 */
public final class HttpCaptureInterceptor
        implements Interceptor,
        IHttpCapture {

    // 模块名 ( 要求唯一性 )
    private final String      mModuleName;
    // 抓包数据加密中间层
    private final Encrypt     mEncrypt;
    // Http 拦截过滤器
    private final IHttpFilter mHttpFilter;
    // 是否进行 Http 抓包拦截
    private       boolean     mCapture;

    // ================
    // = IHttpCapture =
    // ================

    @Override
    public String getModuleName() {
        return mModuleName;
    }

    @Override
    public Encrypt getEncrypt() {
        return mEncrypt;
    }

    @Override
    public IHttpFilter getHttpFilter() {
        return mHttpFilter;
    }

    @Override
    public boolean isCapture() {
        return mCapture;
    }

    @Override
    public void setCapture(boolean capture) {
        this.mCapture = capture;
    }

    @Override
    public String getModulePath() {
        return Utils.getModulePath(mModuleName);
    }

    @Override
    public List<CaptureItem> getModuleHttpCaptures() {
        return Utils.getModuleHttpCaptures(
                mModuleName, mEncrypt != null
        );
    }

    @NonNull
    @Override
    public CaptureRedact captureRedact() {
        return null;
    }

    // ==========
    // = 构造函数 =
    // ==========

    /**
     * 构造函数
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param encrypt    抓包数据加密中间层
     * @param httpFilter Http 拦截过滤器
     * @param capture    是否进行 Http 抓包拦截
     */
    public HttpCaptureInterceptor(
            final String moduleName,
            final Encrypt encrypt,
            final IHttpFilter httpFilter,
            final boolean capture
    ) {
        this.mModuleName = moduleName;
        this.mEncrypt    = encrypt;
        this.mHttpFilter = httpFilter;
        this.mCapture    = capture;
    }

    // ===============
    // = Interceptor =
    // ===============

    @Override
    public Response intercept(Chain chain)
            throws IOException {
        // 如果不需要抓包则直接返回
        if (!mCapture) {
            return chain.proceed(chain.request());
        }
        // 进行抓包处理
        return innerResponse(chain);
    }

    // ==========
    // = 内部方法 =
    // ==========

    private final Charset UTF_8 = Charset.forName(DevFinal.ENCODE.UTF_8);

    /**
     * 内部抓包方法
     * <pre>
     *     减少 {@link #intercept} 方法逻辑, 不同情况调用不同方法一目了然
     * </pre>
     */
    public Response innerResponse(Chain chain)
            throws IOException {
        CaptureInfo captureInfo = new CaptureInfo();

        Request     request        = chain.request();
        RequestBody requestBody    = request.body();
        boolean     hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol   protocol   = connection != null ? connection.protocol() : Protocol.HTTP_1_1;

        StringBuilder requestStartMessage = new StringBuilder();
        requestStartMessage.append(request.method()).append(" ").append(protocol);
        if (hasRequestBody) {
            requestStartMessage.append(" (")
                    .append(requestBody.contentLength())
                    .append(" byte body)");
        }

        if (mHttpFilter != null) {
            boolean isFilter = false;
            try {
                isFilter = mHttpFilter.filter(
                        request, request.url(), request.method(),
                        protocol, request.headers()
                );
            } catch (Exception ignored) {
            }
            if (isFilter) {
                // 不需要抓包直接返回
                return chain.proceed(chain.request());
            }
        }

        // ==========
        // = 请求信息 =
        // ==========

        captureInfo.requestMethod = requestStartMessage.toString();
        captureInfo.requestUrl    = request.url().toString();

        if (hasRequestBody) {
            if (requestBody.contentType() != null) {
                captureInfo.requestHeader.put(
                        "Content-Type", String.valueOf(requestBody.contentType())
                );
            }
            if (requestBody.contentLength() != -1) {
                captureInfo.requestHeader.put(
                        "Content-Length", String.valueOf(requestBody.contentLength())
                );
            }
        }

        Headers headers = request.headers();
        for (int i = 0, len = headers.size(); i < len; i++) {
            String name = headers.name(i);
            captureInfo.requestHeader.put(name, headers.value(i));
        }

        if (!bodyEncoded(request.headers())) {
            Buffer buffer = new Buffer();
            if (requestBody != null) {
                if (requestBody.isOneShot()) {
                    captureInfo.requestBody.put("end", "one-shot body omitted");
                } else {
                    requestBody.writeTo(buffer);

                    if (isPlaintext(buffer)) {
                        if (requestBody instanceof FormBody) {
                            FormBody formBody = (FormBody) requestBody;
                            for (int i = 0, len = formBody.size(); i < len; i++) {
                                captureInfo.requestBody.put(formBody.name(i), formBody.value(i));
                            }
                        }
                        String bodyBuilder = request.method() +
                                " (" + requestBody.contentLength() + "- byte body)";
                        captureInfo.requestBody.put("body length", bodyBuilder);
                    } else {
                        String bodyBuilder = request.method() +
                                " (binary " + requestBody.contentLength() + "- byte body omitted)";
                        captureInfo.requestBody.put("body length", bodyBuilder);
                    }
                }
            }
        }

        // 设置抓包文件信息
        CaptureFile captureFile = new CaptureFile()
                .setUrl(captureInfo.requestUrl)
                .setMethod(captureInfo.requestMethod)
                .setEncrypt(mEncrypt != null)
                .setModuleName(mModuleName)
                .setTime(System.currentTimeMillis());

        // ==========
        // = 响应信息 =
        // ==========

        long     startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            captureInfo.responseBody = "HTTP FAILED:" + e;
            finalHttpCaptureSave(captureFile, captureInfo);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();

        captureInfo.responseStatus.put("status", response.code() + " " + response.message());
        captureInfo.responseStatus.put("time", tookMs + "ms");

        Headers respHeaders = response.headers();
        for (int i = 0, len = respHeaders.size(); i < len; i++) {
            captureInfo.responseHeader.put(respHeaders.name(i), respHeaders.value(i));
        }

        if (!bodyEncoded(response.headers())) {
            long           contentLength = responseBody.contentLength();
            BufferedSource source        = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset   charset     = UTF_8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF_8);
            }

            if (!isPlaintext(buffer)) {
                captureInfo.responseBody = "非文本信息";
                finalHttpCaptureSave(captureFile, captureInfo);
                return response;
            }

            if (contentLength != 0L) {
                try {
                    captureInfo.responseBody = buffer.clone().readString(charset);
                } catch (Exception e) {
                    captureInfo.responseBody = "buffer readString error";
                }
            }
            captureInfo.responseStatus.put("body length", buffer.size() + " byte body");
        }
        finalHttpCaptureSave(captureFile, captureInfo);
        return response;
    }

    // =

    /**
     * 抓包数据最终保存方法
     * @param captureFile 抓包存储文件
     * @param captureInfo 抓包数据
     */
    private void finalHttpCaptureSave(
            final CaptureFile captureFile,
            final CaptureInfo captureInfo
    ) {
        String httpCaptureData = captureInfo.toJson();
        // 如果存在加密则进行处理
        if (mEncrypt != null) {
            try {
                byte[] bytes = mEncrypt.encrypt(httpCaptureData.getBytes());
                httpCaptureData = new String(bytes);
            } catch (Exception e) {
                httpCaptureData = ThrowableUtils.getThrowable(e);
            }
        }
        // 保存抓包数据
        captureFile.setHttpCaptureData(httpCaptureData);
        // 存储文件
        Utils.saveHttpCaptureFile(captureFile);
    }

    // =

    private boolean isPlaintext(final Buffer buffer) {
        try {
            Buffer prefix    = new Buffer();
            long   byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(final Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}