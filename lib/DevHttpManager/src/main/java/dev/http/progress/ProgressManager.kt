package dev.http.progress

import android.os.Handler
import dev.DevUtils
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
    callback: Progress.Callback? = ProgressManager.sGlobalCallback,
    // 回调 UI 线程通知 ( 如果为 null 则会非 UI 线程通知 )
    handler: Handler? = DevUtils.getHandler(),
    // 回调刷新时间 ( 毫秒 ) - 小于等于 0 则每次进度变更都进行通知
    refreshTime: Long = Progress.REFRESH_TIME,
    // 是否推荐请求一次 ( isOneShot() return 使用 ) 避免拦截器调用 writeTo 导致多次触发
    shouldOneShot: Boolean = true,
    // 额外携带信息
    extras: Progress.Extras? = this.toExtras()
): Request {
    return body?.let { requestBody ->
        this.newBuilder()
            .method(
                method, ProgressRequestBody(
                    requestBody, callback, handler,
                    refreshTime, shouldOneShot, extras
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
    callback: Progress.Callback? = ProgressManager.sGlobalCallback,
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

    // 回调刷新时间 ( 毫秒 ) - 小于等于 0 则每次进度变更都进行通知
    private var mRefreshTime: Long = Progress.REFRESH_TIME

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取回调刷新时间 ( 毫秒 )
     * @return 回调刷新时间 ( 毫秒 )
     */
    fun getRefreshTime(): Long {
        return mRefreshTime
    }

    /**
     * 设置回调刷新时间 ( 毫秒 )
     * @param refreshTime 回调刷新时间 ( 毫秒 )
     */
    fun setRefreshTime(refreshTime: Long) {
        mRefreshTime = refreshTime.coerceAtLeast(0)
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * detail: 全局进度回调
     * @author Ttt
     * 只要是 DevHttpManager 库内部创建的 [ProgressRequestBody]、[ProgressResponseBody]
     * 都会统一使用该回调接口实现
     */
    val sGlobalCallback = object : Progress.Callback {
        override fun onStart(progress: Progress) {
        }

        override fun onProgress(progress: Progress) {
        }

        override fun onError(progress: Progress) {
        }

        override fun onFinish(progress: Progress) {
        }

        override fun onEnd(progress: Progress) {
        }
    }
}