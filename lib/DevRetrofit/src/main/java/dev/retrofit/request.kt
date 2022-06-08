package dev.retrofit

// =============
// = 封装请求方法 =
// =============

// =======
// = 原始 =
// =======

/**
 * 最终执行方法
 * 无任何封装, 支持自定义解析、处理等代码
 */
suspend inline fun <T, P> finalExecute(
    // 请求方法体
    crossinline block: suspend () -> T?,
    // 开始请求
    crossinline start: suspend () -> Unit,
    // 请求成功
    crossinline success: suspend (T?) -> Unit,
    // 请求异常
    crossinline error: suspend (Throwable) -> Unit,
    // 请求结束
    crossinline finish: suspend () -> Unit,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<T, P>? = null
) {
    runCatching {
        // 开始请求
        callback?.onStart()

        start.invoke()
        // 请求方法体
        block()
    }.onSuccess { itData ->
        // 请求成功、请求结束
        callback?.apply {
            onSuccess(itData)
            onFinish()
        }
        success.invoke(itData)
        finish.invoke()
    }.onFailure { itError ->
        // 请求异常、请求结束
        callback?.apply {
            onError(itError)
            onFinish()
        }
        error.invoke(itError)
        finish.invoke()
    }
}

// =======
// = 封装 =
// =======

/**
 * 最终执行方法
 * 封装为 Base.Response、Base.Result 进行响应
 */
suspend inline fun <T, R : Base.Response<T>, P> finalExecuteResponse(
    // 请求方法体
    crossinline block: suspend () -> R?,
    // 开始请求
    crossinline start: suspend () -> Unit,
    // 请求成功
    crossinline success: suspend (Base.Result<T, R>) -> Unit,
    // 请求异常
    crossinline error: suspend (Base.Result<T, R>) -> Unit,
    // 请求结束
    crossinline finish: suspend () -> Unit,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<Base.Result<T, R>, P>? = null
) {
    runCatching {
        // 开始请求
        callback?.onStart()

        start.invoke()
        // 请求方法体
        block()
    }.onSuccess { itData ->
        val result = itData.result().build()
        // 请求成功、请求结束
        callback?.apply {
            // 设置额外携带参数 ( 扩展使用 )
            result.setParams(this.getParams())

            onSuccess(result)
            onFinish()
        }
        success.invoke(result)
        finish.invoke()
    }.onFailure { itError ->
        val result = resultCreate<T, R>(itError).build()
        // 请求异常、请求结束
        callback?.apply {
            // 设置额外携带参数 ( 扩展使用 )
            result.setParams(this.getParams())

            onError(itError)
            onFinish()
        }
        error.invoke(result)
        finish.invoke()
    }
}