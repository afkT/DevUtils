package dev.retrofit

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

// ====================
// = 请求方法协程扩展函数 =
// ====================

// ====================================================
// = 在 request_coroutines_simple.kt 基础上使用 LiveData =
// ====================================================

// ==================
// = CoroutineScope =
// ==================

/**
 * 执行请求
 * 无任何额外逻辑封装, 支持自定义解析、处理等代码
 */
inline fun <T> CoroutineScope.liveDataScopeExecuteRequest(
    // 请求方法体
    crossinline block: suspend () -> T?,
    // LiveData
    liveData: MutableLiveData<T>,
    // 是否使用 postValue
    usePostValue: Boolean = true,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<T>? = null,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
): Job {
    return launch {
        finalExecute(
            block, start = {}, success = {
                it?.let { data ->
                    if (usePostValue) {
                        liveData.postValue(data)
                    } else {
                        liveData.value = data
                    }
                }
            },
            error = {}, finish = {},
            callback, globalCallback
        )
    }
}

/**
 * 执行请求
 * 封装为 Base.Response、Base.Result 进行响应
 */
inline fun <T, R : Base.Response<T>> CoroutineScope.liveDataScopeExecuteResponseRequest(
    // 请求方法体
    crossinline block: suspend () -> R?,
    // LiveData
    liveData: MutableLiveData<Base.Result<T, R>>,
    // 是否使用 postValue
    usePostValue: Boolean = true,
    // 当前请求每个阶段进行通知
    callback: Notify.ResultCallback<T, R>? = null,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
): Job {
    return launch {
        finalExecuteResponse(
            block, start = {}, success = {},
            error = {}, finish = {},
            object : Notify.ResultCallback<T, R>() {
                override fun onStart(uuid: UUID) {
                    callback?.onStart(uuid)
                }

                override fun onSuccess(
                    uuid: UUID,
                    data: Base.Result<T, R>
                ) {
                    callback?.onSuccess(uuid, data)

                    if (usePostValue) {
                        liveData.postValue(data)
                    } else {
                        liveData.value = data
                    }
                }

                override fun onFinish(uuid: UUID) {
                    callback?.onFinish(uuid)
                }
            }, globalCallback
        )
    }
}

// =============
// = ViewModel =
// =============

inline fun <T> ViewModel.liveDataLaunchExecuteRequest(
    // 请求方法体
    crossinline block: suspend () -> T?,
    // LiveData
    liveData: MutableLiveData<T>,
    // 是否使用 postValue
    usePostValue: Boolean = true,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<T>? = null,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
): Job {
    return viewModelScope.liveDataScopeExecuteRequest(
        block, liveData, usePostValue, callback, globalCallback
    )
}

inline fun <T, R : Base.Response<T>> ViewModel.liveDataLaunchExecuteResponseRequest(
    // 请求方法体
    crossinline block: suspend () -> R?,
    // LiveData
    liveData: MutableLiveData<Base.Result<T, R>>,
    // 是否使用 postValue
    usePostValue: Boolean = true,
    // 当前请求每个阶段进行通知
    callback: Notify.ResultCallback<T, R>? = null,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
): Job {
    return viewModelScope.liveDataScopeExecuteResponseRequest(
        block, liveData, usePostValue, callback, globalCallback
    )
}

// =============
// = Lifecycle =
// =============

inline fun <T> Lifecycle.liveDataLaunchExecuteRequest(
    // 请求方法体
    crossinline block: suspend () -> T?,
    // LiveData
    liveData: MutableLiveData<T>,
    // 是否使用 postValue
    usePostValue: Boolean = true,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<T>? = null,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
): Job {
    return coroutineScope.liveDataScopeExecuteRequest(
        block, liveData, usePostValue, callback, globalCallback
    )
}

inline fun <T, R : Base.Response<T>> Lifecycle.liveDataLaunchExecuteResponseRequest(
    // 请求方法体
    crossinline block: suspend () -> R?,
    // LiveData
    liveData: MutableLiveData<Base.Result<T, R>>,
    // 是否使用 postValue
    usePostValue: Boolean = true,
    // 当前请求每个阶段进行通知
    callback: Notify.ResultCallback<T, R>? = null,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
): Job {
    return coroutineScope.liveDataScopeExecuteResponseRequest(
        block, liveData, usePostValue, callback, globalCallback
    )
}

// ==================
// = LifecycleOwner =
// ==================

inline fun <T> LifecycleOwner.liveDataLaunchExecuteRequest(
    // 请求方法体
    crossinline block: suspend () -> T?,
    // LiveData
    liveData: MutableLiveData<T>,
    // 是否使用 postValue
    usePostValue: Boolean = true,
    // 当前请求每个阶段进行通知
    callback: Notify.Callback<T>? = null,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
): Job {
    return lifecycleScope.liveDataScopeExecuteRequest(
        block, liveData, usePostValue, callback, globalCallback
    )
}

inline fun <T, R : Base.Response<T>> LifecycleOwner.liveDataLaunchExecuteResponseRequest(
    // 请求方法体
    crossinline block: suspend () -> R?,
    // LiveData
    liveData: MutableLiveData<Base.Result<T, R>>,
    // 是否使用 postValue
    usePostValue: Boolean = true,
    // 当前请求每个阶段进行通知
    callback: Notify.ResultCallback<T, R>? = null,
    // 全局通知回调方法 ( 创建一个全局通用传入 )
    globalCallback: Notify.GlobalCallback? = null
): Job {
    return lifecycleScope.liveDataScopeExecuteResponseRequest(
        block, liveData, usePostValue, callback, globalCallback
    )
}