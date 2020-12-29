package dev.other.okgo;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import dev.other.GsonUtils;
import dev.utils.LogPrintUtils;
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
 * detail: OkHttp 打印日志拦截器
 * @author DingDeGao
 * <pre>
 *     基于 OKHttp 的实用抓包小工具
 *     @see <a href="https://github.com/DingProg/NetworkCaptureSelf"/>
 * </pre>
 */
public class HttpLoggingInterceptor
        implements Interceptor {

    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain)
            throws IOException {
        CaptureEntity captureEntity = new CaptureEntity();

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

        // ===========
        // = 请求信息 =
        // ===========

        captureEntity.requestMethod = requestStartMessage.toString();
        captureEntity.requestUrl = request.url().toString();

        if (hasRequestBody) {
            if (requestBody.contentType() != null) {
                captureEntity.requestHeader.put("Content-Type", String.valueOf(requestBody.contentType()));
            }
            if (requestBody.contentLength() != -1) {
                captureEntity.requestHeader.put("Content-Length", String.valueOf(requestBody.contentLength()));
            }
        }

        Headers headers = request.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            String name = headers.name(i);
            // Skip headers from the request body as they are explicitly logged above.
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                captureEntity.requestHeader.put(name, headers.value(i));
            }
        }

        if ((!bodyEncoded(request.headers()))) {
            Buffer buffer = new Buffer();
            if (requestBody != null) {
                requestBody.writeTo(buffer);

                if (isPlaintext(buffer)) {
                    if (requestBody instanceof FormBody) {
                        FormBody formBody = (FormBody) requestBody;
                        for (int i = 0; i < formBody.size(); i++) {
                            captureEntity.requestBody.put(formBody.name(i), formBody.value(i));
                        }
                    }
                    StringBuilder bodyBuilder = new StringBuilder();
                    bodyBuilder.append(request.method())
                            .append(" (").append(requestBody.contentLength())
                            .append("- byte body)");
                    captureEntity.requestBody.put("body length", bodyBuilder.toString());
                } else {
                    StringBuilder bodyBuilder = new StringBuilder();
                    bodyBuilder.append(request.method())
                            .append(" (binary ").append(requestBody.contentLength())
                            .append("- byte body omitted)");
                    captureEntity.requestBody.put("body length", bodyBuilder.toString());
                }
            }
        }

        // ===========
        // = 响应信息 =
        // ===========

        long     startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            captureEntity.responseBody = "HTTP FAILED:" + e;
            _finalPrintLog(captureEntity);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody  = response.body();
        long         contentLength = responseBody.contentLength();

        captureEntity.responseStatus.put("status", response.code() + " " + response.message());
        captureEntity.responseStatus.put("time", tookMs + "ms");

        Headers respHeaders = response.headers();
        for (int i = 0, count = respHeaders.size(); i < count; i++) {
            captureEntity.responseHeader.put(respHeaders.name(i), respHeaders.value(i));
        }

        if (!bodyEncoded(response.headers())) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset   charset     = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            if (!isPlaintext(buffer)) {
                captureEntity.responseBody = "非文本信息";
                _finalPrintLog(captureEntity);
                return response;
            }

            if (contentLength != 0) {
                captureEntity.responseBody = buffer.clone().readString(charset);
            }
            captureEntity.responseStatus.put("body length", buffer.size() + " byte body");
        }

        _finalPrintLog(captureEntity);
        return response;
    }

    // ===============
    // = 日志打印方法 =
    // ===============

    /**
     * 最终输出日志方法
     * @param captureEntity 捕获信息实体类
     */
    private void _finalPrintLog(CaptureEntity captureEntity) {
        if (captureEntity != null) {
            LogPrintUtils.json(GsonUtils.toJson(captureEntity));
        }
    }

    // ===========
    // = 内部方法 =
    // ===========

    private class CaptureEntity {
        // 请求方法
        public String              requestMethod  = "";
        // 请求链接
        public String              requestUrl     = "";
        // 请求头信息
        public Map<String, String> requestHeader  = new HashMap<>();
        // 请求体
        public Map<String, String> requestBody    = new HashMap<>();
        // 响应状态
        public Map<String, String> responseStatus = new HashMap<>();
        // 响应头信息
        public Map<String, String> responseHeader = new HashMap<>();
        // 响应体
        public String              responseBody   = "";
    }

    // =

    private boolean isPlaintext(Buffer buffer) {
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

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}