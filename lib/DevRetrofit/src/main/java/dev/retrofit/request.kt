package dev.retrofit

import java.util.*

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
suspend inline fun <T> finalExecute(
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
    callback: Notify.Callback<T>? = null,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
) {
    val uuid: UUID = UUID.randomUUID()
    runCatching {
        // 开始请求
        innerOriginalStartCallback(
            uuid, callback, globalCallback
        )

        start.invoke()
        // 请求方法体
        block()
    }.onSuccess { itData ->
        // 请求成功、请求结束
        innerOriginalSuccessCallback(
            uuid, callback, globalCallback, itData
        )

        success.invoke(itData)
        finish.invoke()
    }.onFailure { itError ->
        // 请求异常、请求结束
        innerOriginalErrorCallback(
            uuid, callback, globalCallback, itError
        )

        error.invoke(itError)
        finish.invoke()
    }
}

// ==============
// = Base - 封装 =
// ==============

/**
 * 最终执行方法
 * 封装为 Base.Response、Base.Result 进行响应
 */
suspend inline fun <T, R : Base.Response<T>> finalExecuteResponse(
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
    callback: Notify.ResultCallback<T, R>? = null,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
) {
    val uuid: UUID = UUID.randomUUID()
    runCatching {
        // 开始请求
        innerBaseStartCallback(
            uuid, callback, globalCallback
        )

        start.invoke()
        // 请求方法体
        block()
    }.onSuccess { itData ->
        val result = itData.result().build()
        // 设置额外携带参数 ( 扩展使用 )
        result.setParams(callback?.getParams())
        // 请求成功、请求结束
        innerBaseSuccessCallback(
            uuid, callback, globalCallback, result
        )

        success.invoke(result)
        finish.invoke()
    }.onFailure { itError ->
        val result = resultCreate<T, R>(itError).build()
        // 设置额外携带参数 ( 扩展使用 )
        result.setParams(callback?.getParams())
        // 请求异常、请求结束
        innerBaseErrorCallback(
            uuid, callback, globalCallback, result, itError
        )

        error.invoke(result)
        finish.invoke()
    }
}

// =================
// = 内部封装美化代码 =
// =================

// =======
// = 原始 =
// =======

/**
 * 开始请求
 */
fun <T> innerOriginalStartCallback(
    // 每次请求唯一 id
    uuid: UUID,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<T>? = null,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
) {
    val params = callback?.getParams()
    // 开始请求
    globalCallback?.onStart(
        uuid, params
    )
    callback?.onStart(uuid)
}

/**
 * 请求成功、请求结束
 */
fun <T> innerOriginalSuccessCallback(
    // 每次请求唯一 id
    uuid: UUID,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<T>? = null,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null,
    // 请求响应数据
    itData: T?
) {
    val params = callback?.getParams()
    // 请求成功、请求结束
    globalCallback?.apply {
        onSuccess(uuid, params, itData)
        onFinish(uuid, params)
    }
    callback?.apply {
        onSuccess(uuid, itData)
        onFinish(uuid)
    }
}

/**
 * 请求异常、请求结束
 */
fun <T> innerOriginalErrorCallback(
    // 每次请求唯一 id
    uuid: UUID,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<T>? = null,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null,
    // 请求异常
    itError: Throwable
) {
    val params = callback?.getParams()
    // 请求异常、请求结束
    globalCallback?.apply {
        onError(uuid, params, itError)
        onFinish(uuid, params)
    }
    callback?.apply {
        onError(uuid, itError)
        onFinish(uuid)
    }
}

// ==============
// = Base - 封装 =
// ==============

/**
 * 开始请求
 */
fun <T, R : Base.Response<T>> innerBaseStartCallback(
    // 每次请求唯一 id
    uuid: UUID,
    // 当前请求每个阶段进行通知
    callback: Notify.ResultCallback<T, R>? = null,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
) {
    val params = callback?.getParams()
    // 开始请求
    globalCallback?.onStart(
        uuid, params
    )
    callback?.onStart(uuid)
}

/**
 * 请求成功、请求结束
 */
fun <T, R : Base.Response<T>> innerBaseSuccessCallback(
    // 每次请求唯一 id
    uuid: UUID,
    // 当前请求每个阶段进行通知
    callback: Notify.ResultCallback<T, R>? = null,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null,
    // 请求响应数据
    result: Base.Result<T, R>
) {
    val params = callback?.getParams()
    // 请求成功、请求结束
    globalCallback?.apply {
        onSuccess(uuid, params, result)
        onFinish(uuid, params)
    }
    callback?.apply {
        onSuccess(uuid, result)
        onFinish(uuid)
    }
}

/**
 * 请求异常、请求结束
 */
fun <T, R : Base.Response<T>> innerBaseErrorCallback(
    // 每次请求唯一 id
    uuid: UUID,
    // 当前请求每个阶段进行通知
    callback: Notify.ResultCallback<T, R>? = null,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null,
    // 请求响应数据
    result: Base.Result<T, R>,
    // 请求异常
    itError: Throwable
) {
    val params = callback?.getParams()
    // 请求异常、请求结束
    globalCallback?.apply {
        onError(uuid, params, itError)
        onFinish(uuid, params)
    }
    callback?.apply {
        onSuccess(uuid, result)
        onFinish(uuid)
    }
}