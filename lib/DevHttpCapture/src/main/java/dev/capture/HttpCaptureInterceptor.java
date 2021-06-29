package dev.capture;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import dev.utils.common.cipher.Encrypt;
import okhttp3.Interceptor;
import okhttp3.Response;

public final class HttpCaptureInterceptor
        implements Interceptor,
        IHttpCapture {

    // 模块名 ( 要求唯一性 )
    private final String  mModuleName;
    // 抓包数据加密中间层
    private final Encrypt mEncrypt;
    // 是否进行 Http 抓包拦截
    private       boolean mCapture;

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
    public List<CaptureFile> getModuleHttpCaptures() {
        return Utils.getModuleHttpCaptures(
                mModuleName, mEncrypt != null
        );
    }

    // ==========
    // = 构造函数 =
    // ==========

    /**
     * 构造函数
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param encrypt    抓包数据加密中间层
     * @param capture    是否进行 Http 抓包拦截
     */
    public HttpCaptureInterceptor(
            final String moduleName,
            final Encrypt encrypt,
            final boolean capture
    ) {
        this.mModuleName = moduleName;
        this.mEncrypt    = encrypt;
        this.mCapture    = capture;
    }

    // ===============
    // = Interceptor =
    // ===============

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain)
            throws IOException {
        // 如果不需要抓包则直接返回
        if (!mCapture) {
            return chain.proceed(chain.request());
        }
        // 进行抓包处理
        return innerResponse(chain);
    }

    // =============
    // = 内部处理方法 =
    // =============

    private final Charset UTF_8 = Charset.forName("UTF-8");

    /**
     * 内部抓包方法
     * <pre>
     *     减少 {@link #intercept} 方法逻辑, 不同情况调用不同方法一目了然
     * </pre>
     */
    public Response innerResponse(@NotNull Chain chain)
            throws IOException {
        return chain.proceed(chain.request());
    }
}