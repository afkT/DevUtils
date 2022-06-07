package ktx.dev.other.retrofit_rxjava.subscriber

import android.text.TextUtils
import dev.utils.LogPrintUtils
import dev.utils.app.toast.ToastUtils
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import ktx.dev.other.retrofit_rxjava.response.BaseResponse
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * detail: 服务器请求响应处理, 映射统一标准 JSON 格式实体类
 * @author Ttt
 * 等同于 Callback 类, 因为设置 Retrofit Converter Factory ( GsonConverterFactory )
 * Retrofit 会自动映射泛型实体类
 */
abstract class BaseResponseSubscriber<T> : DisposableSubscriber<BaseResponse<T>>() {

    // 日志 TAG
    private val TAG = BaseResponseSubscriber::class.java.simpleName

    // 响应结果
    protected var builder: BaseResponse<T>? = null

    override fun onNext(response: BaseResponse<T>?) {
        LogPrintUtils.dTag(TAG, "请求成功")
        builder = response
        builder?.let {
            it.result = isSuccess(it.errorCode)
            if (it.result) {
                onSuccessResponse(it)
            } else {
                onErrorResponse(it)
            }
        }
    }

    override fun onError(throwable: Throwable?) {
        LogPrintUtils.eTag(TAG, "请求异常", throwable)
        if (builder == null) builder = BaseResponse()

        builder?.let {
            if (TextUtils.isEmpty(it.errorMsg)) {
                val errorMessage = getErrorMessage(throwable)
                it.errorMsg = errorMessage
                ToastUtils.showShort(errorMessage)
            }
            it.exception = throwable
            onErrorResponse(it)
        }
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
     * @param response [BaseResponse]
     */
    abstract fun onSuccessResponse(response: BaseResponse<T>)

    /**
     * 请求失败、响应错误、数据解析错误等, 都会回调该方法 ( UI 线程 )
     * @param response [BaseResponse]
     */
    abstract fun onErrorResponse(response: BaseResponse<T>)

    // =============
    // = 内部判断方法 =
    // =============

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

    /**
     * 通过 code 判断请求是否正确
     * @param code Code
     * @return `true` yes, `false` no
     */
    private fun isSuccess(code: String?): Boolean {
        if (!TextUtils.isEmpty(code)) {
            if ("0000" == code) { // 自行判断
                return true
            } else if ("0" == code) {
                return true
            } else if ("xxx" == code) {
                ToastUtils.showShort("xxx")
            }
        }
        return false
    }
}