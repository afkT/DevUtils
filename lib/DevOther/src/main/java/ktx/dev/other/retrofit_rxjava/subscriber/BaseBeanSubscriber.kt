package ktx.dev.other.retrofit_rxjava.subscriber

import dev.utils.LogPrintUtils
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * detail: 服务器请求响应处理, 映射各种 JSON 实体类
 * @author Ttt
 * 等同于 Callback 类, 因为设置 Retrofit Converter Factory ( GsonConverterFactory )
 * Retrofit 会自动映射泛型实体类
 */
abstract class BaseBeanSubscriber<T> : DisposableSubscriber<T>() {

    // 日志 TAG
    private val TAG = BaseBeanSubscriber::class.java.simpleName

    override fun onNext(response: T?) {
        LogPrintUtils.dTag(TAG, "请求成功")
        if (response != null) {
            onSuccessResponse(response)
        } else {
            onErrorResponse(NullPointerException("response is null"), "解析失败, 请稍后重试")
        }
    }

    override fun onError(throwable: Throwable?) {
        LogPrintUtils.eTag(TAG, "请求异常", throwable)
        onErrorResponse(throwable, getErrorMessage(throwable))
    }

    override fun onStart() {
        super.onStart()
        LogPrintUtils.dTag(TAG, "请求开始")
    }

    override fun onComplete() {
        LogPrintUtils.dTag(TAG, "请求完成")
    }

    /**
     * 请求响应并处理数据无误
     * @param data 响应数据
     */
    abstract fun onSuccessResponse(data: T)

    /**
     * 请求失败、响应错误、数据解析错误等, 都会回调该方法 ( UI 线程 )
     * @param throwable [Throwable]
     * @param message   error message
     */
    abstract fun onErrorResponse(
        throwable: Throwable?,
        message: String?
    )

    // ===============
    // = 内部判断方法 =
    // ===============

    /**
     * 获取异常信息
     * @param throwable [Throwable]
     * @return error message
     */
    private fun getErrorMessage(throwable: Throwable?): String {
        return if (throwable is SocketTimeoutException) {
            "网络连接超时, 请检查您的网络状态, 稍后重试"
        } else if (throwable is ConnectException) {
            "网络连接异常, 请检查您的网络状态"
        } else if (throwable is UnknownHostException) {
            "网络异常, 请检查您的网络状态"
        } else {
            "网络异常, 请检查您的网络状态"
        }
    }
}