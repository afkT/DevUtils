package dev.retrofit

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// ====================
// = 请求方法协程扩展函数 =
// ====================

// ==============
// = request.kt =
// ==============

// ==================
// = CoroutineScope =
// ==================

/**
 * 执行请求
 * 无任何封装, 支持自定义解析、处理等代码
 * <p></p>
 * 防止使用、阅读混淆
 * 通用 CoroutineScope 执行方法使用 scope 为方法名前缀
 */
inline fun <T> CoroutineScope.scopeExecuteRequest(
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
    launch {
        finalExecute(
            block, start, success, error, finish, callback, globalCallback
        )
    }
}

/**
 * 执行请求
 * 封装为 Base.Response、Base.Result 进行响应
 * <p></p>
 * 防止使用、阅读混淆
 * 通用 CoroutineScope 执行方法使用 scope 为方法名前缀
 */
inline fun <T, R : Base.Response<T>> CoroutineScope.scopeExecuteResponseRequest(
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
    launch {
        finalExecuteResponse(
            block, start, success, error, finish, callback, globalCallback
        )
    }
}

// =============
// = ViewModel =
// =============

/**
 * 执行请求
 * 无任何封装, 支持自定义解析、处理等代码
 */
inline fun <T> ViewModel.launchExecuteRequest(
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
    viewModelScope.scopeExecuteRequest(
        block, start, success, error, finish, callback, globalCallback
    )
}

/**
 * 执行请求
 * 封装为 Base.Response、Base.Result 进行响应
 */
inline fun <T, R : Base.Response<T>> ViewModel.launchExecuteResponseRequest(
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
    viewModelScope.scopeExecuteResponseRequest(
        block, start, success, error, finish, callback, globalCallback
    )
}

// =============
// = Lifecycle =
// =============

/**
 * 执行请求
 * 无任何封装, 支持自定义解析、处理等代码
 */
inline fun <T> Lifecycle.launchExecuteRequest(
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
    coroutineScope.scopeExecuteRequest(
        block, start, success, error, finish, callback, globalCallback
    )
}

/**
 * 执行请求
 * 封装为 Base.Response、Base.Result 进行响应
 */
inline fun <T, R : Base.Response<T>> Lifecycle.launchExecuteResponseRequest(
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
    coroutineScope.scopeExecuteResponseRequest(
        block, start, success, error, finish, callback, globalCallback
    )
}

// ==================
// = LifecycleOwner =
// ==================

/**
 * 执行请求
 * 无任何封装, 支持自定义解析、处理等代码
 */
inline fun <T> LifecycleOwner.launchExecuteRequest(
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
    lifecycleScope.scopeExecuteRequest(
        block, start, success, error, finish, callback, globalCallback
    )
}

/**
 * 执行请求
 * 封装为 Base.Response、Base.Result 进行响应
 */
inline fun <T, R : Base.Response<T>> LifecycleOwner.launchExecuteResponseRequest(
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
    lifecycleScope.scopeExecuteResponseRequest(
        block, start, success, error, finish, callback, globalCallback
    )
}