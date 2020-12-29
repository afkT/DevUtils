package dev.other.retrofit.subscriber;

import android.text.TextUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import dev.other.retrofit.response.BaseResponse;
import dev.utils.LogPrintUtils;
import dev.utils.app.toast.ToastUtils;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;

/**
 * detail: 服务器请求响应处理, 映射统一标准 JSON 格式实体类
 * @author Ttt
 * <pre>
 *     等同于 Callback 类, 因为设置 Retrofit Converter Factory ( GsonConverterFactory )
 *     Retrofit 会自动映射泛型实体类
 * </pre>
 */
public abstract class BaseResponseSubscriber<T>
        extends DisposableSubscriber<BaseResponse<T>> {

    // 日志 TAG
    protected final String TAG = BaseResponseSubscriber.class.getSimpleName();

    // 响应结果
    protected BaseResponse<T> builder;

    @Override
    public void onNext(BaseResponse<T> response) {
        LogPrintUtils.dTag(TAG, "请求成功");

        builder = response;
        builder.result = isSuccess(response.code);

        if (builder.result) {
            onSuccessResponse(builder);
        } else {
            onErrorResponse(builder);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        LogPrintUtils.eTag(TAG, "请求异常", throwable);

        if (builder == null) builder = new BaseResponse<>();

        if (TextUtils.isEmpty(builder.message)) {
            String errorMessage = getErrorMessage(throwable);
            builder.message = errorMessage;
            ToastUtils.showShort(errorMessage);
        }
        builder.exception = throwable;

        onErrorResponse(builder);
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
     * @param response {@link BaseResponse}
     */
    abstract public void onSuccessResponse(BaseResponse<T> response);

    /**
     * 请求失败、响应错误、数据解析错误等, 都会回调该方法 ( UI 线程 )
     * @param response {@link BaseResponse}
     */
    abstract public void onErrorResponse(BaseResponse<T> response);

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

    /**
     * 通过 code 判断请求是否正确
     * @param code Code
     * @return {@code true} yes, {@code false} no
     */
    protected boolean isSuccess(String code) {
        if (!TextUtils.isEmpty(code)) {
            if (code.equals("0000")) { // 自行判断
                return true;
            } else if (code.equals("xxx")) {
                ToastUtils.showShort("xxx");
            }
        }
        return false;
    }
}