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

// =========================================================================
// = 在 request_coroutines.kt 基础上减少 start、success、error、finish 方法传参 =
// =========================================================================

// ==================
// = CoroutineScope =
// ==================

/**
 * 执行请求
 * 无任何封装, 支持自定义解析、处理等代码
 */
inline fun <T, P> CoroutineScope.simpleScopeExecuteRequest(
    // 请求方法体
    crossinline block: suspend () -> T?,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<T, P>,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
) {
    launch {
        finalExecute(
            block, start = {}, success = {},
            error = {}, finish = {},
            callback, globalCallback
        )
    }
}

/**
 * 执行请求
 * 封装为 Base.Response、Base.Result 进行响应
 */
inline fun <T, R : Base.Response<T>, P> CoroutineScope.simpleScopeExecuteResponseRequest(
    // 请求方法体
    crossinline block: suspend () -> R?,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<Base.Result<T, R>, P>,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
) {
    launch {
        finalExecuteResponse(
            block, start = {}, success = {},
            error = {}, finish = {},
            callback, globalCallback
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
inline fun <T, P> ViewModel.simpleLaunchExecuteRequest(
    // 请求方法体
    crossinline block: suspend () -> T?,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<T, P>,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
) {
    viewModelScope.simpleScopeExecuteRequest(
        block, callback, globalCallback
    )
}

/**
 * 执行请求
 * 封装为 Base.Response、Base.Result 进行响应
 */
inline fun <T, R : Base.Response<T>, P> ViewModel.simpleLaunchExecuteResponseRequest(
    // 请求方法体
    crossinline block: suspend () -> R?,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<Base.Result<T, R>, P>,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
) {
    viewModelScope.simpleScopeExecuteResponseRequest(
        block, callback, globalCallback
    )
}

// =============
// = Lifecycle =
// =============

/**
 * 执行请求
 * 无任何封装, 支持自定义解析、处理等代码
 */
inline fun <T, P> Lifecycle.simpleLaunchExecuteRequest(
    // 请求方法体
    crossinline block: suspend () -> T?,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<T, P>,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
) {
    coroutineScope.simpleScopeExecuteRequest(
        block, callback, globalCallback
    )
}

/**
 * 执行请求
 * 封装为 Base.Response、Base.Result 进行响应
 */
inline fun <T, R : Base.Response<T>, P> Lifecycle.simpleLaunchExecuteResponseRequest(
    // 请求方法体
    crossinline block: suspend () -> R?,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<Base.Result<T, R>, P>,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
) {
    coroutineScope.simpleScopeExecuteResponseRequest(
        block, callback, globalCallback
    )
}

// ==================
// = LifecycleOwner =
// ==================

/**
 * 执行请求
 * 无任何封装, 支持自定义解析、处理等代码
 */
inline fun <T, P> LifecycleOwner.simpleLaunchExecuteRequest(
    // 请求方法体
    crossinline block: suspend () -> T?,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<T, P>,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
) {
    lifecycleScope.simpleScopeExecuteRequest(
        block, callback, globalCallback
    )
}

/**
 * 执行请求
 * 封装为 Base.Response、Base.Result 进行响应
 */
inline fun <T, R : Base.Response<T>, P> LifecycleOwner.simpleLaunchExecuteResponseRequest(
    // 请求方法体
    crossinline block: suspend () -> R?,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<Base.Result<T, R>, P>,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
) {
    lifecycleScope.simpleScopeExecuteResponseRequest(
        block, callback, globalCallback
    )
}