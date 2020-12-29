package dev.other.retrofit.subscriber;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import dev.utils.LogPrintUtils;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;

/**
 * detail: 服务器请求响应处理, 映射各种 JSON 实体类
 * @author Ttt
 * <pre>
 *     等同于 Callback 类, 因为设置 Retrofit Converter Factory ( GsonConverterFactory )
 *     Retrofit 会自动映射泛型实体类
 * </pre>
 */
public abstract class BaseBeanSubscriber<T>
        extends DisposableSubscriber<T> {

    // 日志 TAG
    protected final String TAG = BaseBeanSubscriber.class.getSimpleName();

    @Override
    public void onNext(T response) {
        LogPrintUtils.dTag(TAG, "请求成功");

        if (response != null) {
            onSuccessResponse(response);
        } else {
            onErrorResponse(new NullPointerException("response is null"), "解析失败, 请稍后重试");
        }
    }

    @Override
    public void onError(Throwable throwable) {
        LogPrintUtils.eTag(TAG, "请求异常", throwable);

        onErrorResponse(throwable, getErrorMessage(throwable));
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogPrintUtils.dTag(TAG, "请求开始");
    }

    @Override
    public void onComplete() {
        LogPrintUtils.dTag(TAG, "请求完成");
    }

    /**
     * 请求响应并处理数据无误
     * @param data 响应数据
     */
    abstract public void onSuccessResponse(T data);

    /**
     * 请求失败、响应错误、数据解析错误等, 都会回调该方法 ( UI 线程 )
     * @param throwable {@link Throwable}
     * @param message   error message
     */
    abstract public void onErrorResponse(
            Throwable throwable,
            String message
    );

    // ===============
    // = 内部判断方法 =
    // ===============

    /**
     * 获取异常信息
     * @param throwable {@link Throwable}
     * @return error message
     */
    protected String getErrorMessage(Throwable throwable) {
        String errorMessage;
        if (throwable instanceof SocketTimeoutException) {
            errorMessage = "网络连接超时, 请检查您的网络状态, 稍后重试";
        } else if (throwable instanceof ConnectException) {
            errorMessage = "网络连接异常, 请检查您的网络状态";
        } else if (throwable instanceof UnknownHostException) {
            errorMessage = "网络异常, 请检查您的网络状态";
        } else {
            errorMessage = "网络异常, 请检查您的网络状态";
        }
        return errorMessage;
    }
}