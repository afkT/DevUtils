package ktx.dev.other.retrofit_coroutines

import android.text.TextUtils
import dev.utils.app.toast.ToastUtils
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * detail: 请求响应统一解析类
 * @author Ttt
 * 主要用于统一解析后台返回 data、message、code 等
 */
class BaseResponse<T> {

    // 返回数据
    var data: T? = null

    // 返回消息
    var errorMsg: String? = null // message

    // 返回结果状态 ( 内部定义 )
    var errorCode: String? = null // code
}

/**
 * detail: 请求结果包装类
 * @author Ttt
 */
class Result<T>(
    // 原始响应数据
    val response: BaseResponse<T>,
    // 请求结果
    val success: Boolean = false,
    // 返回数据
    val data: T? = null,
    // 返回结果状态 ( 内部定义 )
    val errorCode: String? = null,
    // 异常情况
    val error: Exception
)

/**
 * 请求结果扩展函数
 * @receiver BaseResponse<T>
 * @return Result<T>
 */
fun <T> BaseResponse<T>.result(): Result<T> {
    return Result(
        this, isSuccess(errorCode),
        data, errorCode, Exception(errorMsg)
    )
}

/**
 * 执行请求统一处理方法
 */
suspend fun executeRequest(
    // 请求方法体
    block: suspend () -> Unit,
    // 开始请求
    start: suspend () -> Unit,
    // 请求完成
    complete: suspend () -> Unit,
    // 请求失败
    error: suspend (String?, Throwable) -> Unit,
) {
    start()
    try {
        block()
    } catch (e: Exception) {
        error(Int.MAX_VALUE.toString(), e)
    } finally {
        complete()
    }
}

// ==========
// = 快捷判断 =
// ==========

/**
 * 获取异常信息
 * @param throwable [Throwable]
 * @return error message
 */
fun getErrorMessage(throwable: Throwable?): String {
    return if (throwable is SocketTimeoutException) {
        "网络连接超时, 请检查您的网络状态, 稍后重试"
    } else if (throwable is ConnectException) {
        "网络连接异常, 请检查您的网络状态"
    } else if (throwable is UnknownHostException) {
        "网络异常, 请检查您的网络状态"
    } else {
        throwable?.message ?: "网络异常, 请检查您的网络状态"
    }
}

/**
 * 通过 code 判断请求是否正确
 * @param code Code
 * @return `true` yes, `false` no
 */
fun isSuccess(code: String?): Boolean {
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