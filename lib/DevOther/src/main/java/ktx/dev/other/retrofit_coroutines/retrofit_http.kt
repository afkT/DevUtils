package ktx.dev.other.retrofit_coroutines

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
    var errorCode: Int = -1 // code
}

/**
 * 请求结果
 */
class Result<T>(
    // 原始响应数据
    var response: BaseResponse<T>,
    // 请求结果
    var success: Boolean = false,
    // 返回数据
    var data: T? = null,
) {

    // 异常情况
    var error: Exception? = null
}

/**
 * 请求结果扩展函数
 * @receiver BaseResponse<T>
 * @return Result<T>
 */
fun <T> BaseResponse<T>.result(): Result<T> {
    return Result(this, true, data).apply {
        if (!success) { // 请求失败
            error = Exception(errorMsg)
        }
    }
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
    error: suspend (Throwable) -> Unit,
) {
    start()
    try {
        block()
    } catch (e: Exception) {
        error(e)
    } finally {
        complete()
    }
}

interface Callback<T> {

    fun onStart()

    fun onSuccess(
        data: T?,
        result: Result<T>
    )

    fun onComplete()

    fun onError(throwable: Throwable?)
}