package dev.http.progress

import android.os.Handler
import dev.DevUtils
import dev.utils.DevFinal
import dev.utils.LogPrintUtils
import okhttp3.Request
import okhttp3.Response

// =============
// = 对外公开方法 =
// =============

/**
 * Request 转 Progress.Extras
 * @receiver Request
 * @return Progress.Extras?
 */
fun Request.toExtras(): Progress.Extras? {
    try {
        // 请求链接
        val rUrl = url.toUrl().toString()
        // 请求头信息
        val rHeaders: MutableMap<String, String> = mutableMapOf()

        for (i in 0 until headers.size) {
            rHeaders[headers.name(i)] = headers.value(i)
        }
        return Progress.Extras(rUrl, method, rHeaders)
    } catch (e: Exception) {
        LogPrintUtils.eTag(ProgressManager.TAG, e, "toExtras")
    }
    return null
}

/**
 * Request 使用 ProgressRequestBody 包装构建
 * @receiver Request
 * @return Request
 */
fun Request.wrapRequestBody(
    // 上传、下载回调接口
    callback: Progress.Callback?,
    // 回调 UI 线程通知 ( 如果为 null 则会非 UI 线程通知 )
    handler: Handler? = DevUtils.getHandler(),
    // 回调刷新时间 ( 毫秒 ) - 小于等于 0 则每次进度变更都进行通知
    refreshTime: Long = Progress.REFRESH_TIME,
    // 额外携带信息
    extras: Progress.Extras? = this.toExtras()
): Request {
    return body?.let { requestBody ->
        this.newBuilder()
            .method(
                method, ProgressRequestBody(
                    requestBody, callback, handler,
                    refreshTime, extras
                )
            )
            .build()
    } ?: this
}

/**
 * Response 使用 ProgressResponseBody 包装构建
 * @receiver Response
 * @return Response
 */
fun Response.wrapResponseBody(
    // 上传、下载回调接口
    callback: Progress.Callback?,
    // 回调 UI 线程通知 ( 如果为 null 则会非 UI 线程通知 )
    handler: Handler? = DevUtils.getHandler(),
    // 回调刷新时间 ( 毫秒 ) - 小于等于 0 则每次进度变更都进行通知
    refreshTime: Long = Progress.REFRESH_TIME,
    // 额外携带信息
    extras: Progress.Extras? = null
): Response {
    return body?.let { responseBody ->
        this.newBuilder()
            .body(
                ProgressResponseBody(
                    responseBody, callback, handler,
                    refreshTime, extras
                )
            )
            .build()
    } ?: this
}

// ============
// = 内部封装类 =
// ============

/**
 * detail: Progress Manager
 * @author Ttt
 * OkHttp API:
 * @see https://square.github.io/okhttp/recipes
 */
internal object ProgressManager {

    // 日志 TAG
    val TAG = ProgressManager::class.java.simpleName

    // 存储 Progress Operation 操作对象
    private val sOperationMaps: MutableMap<String, ProgressOperation> = LinkedHashMap()

    // 默认监听上下行操作对象
    private val mDefault: ProgressOperation by lazy {
        ProgressOperation.get(
            DevFinal.STR.DEFAULT, true,
            ProgressOperation.TYPE_ALL
        )
    }

    // =====================
    // = ProgressOperation =
    // =====================

    /**
     * 获取全局默认 Progress Operation 操作对象
     * @return ProgressOperation
     */
    fun getDefault(): ProgressOperation {
        return mDefault
    }

    /**
     * 获取 Progress Operation 操作对象
     * @param key Key
     * @return Progress Operation
     */
    fun getOperation(key: String): ProgressOperation? {
        return sOperationMaps[key]
    }

    /**
     * 通过 Key 判断是否存在 Progress Operation 操作对象
     * @param key Key
     * @return `true` yes, `false` no
     */
    fun containsOperation(key: String): Boolean {
        return sOperationMaps.containsKey(key)
    }

    /**
     * 通过 Key 解绑并返回 Operation 操作对象
     * @param key Key
     * @return Progress Operation
     */
    fun removeOperation(key: String): ProgressOperation? {
        return sOperationMaps.remove(key)?.recycle()
    }

    /**
     * 清空所有 Progress Operation 操作对象
     */
    fun clearOperation() {
        val map = sOperationMaps.toMutableMap()
        sOperationMaps.clear()
        map.values.forEach {
            it.recycle()
        }
        map.clear()
    }

    // =

    /**
     * 通过 Key 绑定并返回 Operation 操作对象 ( 监听上下行 )
     * @param key Key
     * @return Progress Operation
     */
    fun putOperationTypeAll(key: String): ProgressOperation {
        return putOperation(key, ProgressOperation.TYPE_ALL)
    }

    /**
     * 通过 Key 绑定并返回 Operation 操作对象 ( 监听上行 )
     * @param key Key
     * @return Progress Operation
     */
    fun putOperationTypeRequest(key: String): ProgressOperation {
        return putOperation(key, ProgressOperation.TYPE_REQUEST)
    }

    /**
     * 通过 Key 绑定并返回 Operation 操作对象 ( 监听下行 )
     * @param key Key
     * @return Progress Operation
     */
    fun putOperationTypeResponse(key: String): ProgressOperation {
        return putOperation(key, ProgressOperation.TYPE_RESPONSE)
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 通过 Key 绑定并返回 Operation 操作对象
     * @param key Key
     * @param type 内部拦截器监听类型
     * @return Progress Operation
     */
    private fun putOperation(
        key: String,
        type: Int
    ): ProgressOperation {
        // 如果存在那么先废弃历史对象
        getOperation(key)?.recycle()
        val operation = ProgressOperation.get(key, false, type)
        sOperationMaps[key] = operation
        return operation
    }
}