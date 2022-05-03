package dev.http.progress

import dev.utils.LogPrintUtils
import okhttp3.Request

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

    // =============
    // = 对外公开方法 =
    // =============


    // ==========
    // = 内部方法 =
    // ==========

    /**
     * detail: 全局进度回调
     * @author Ttt
     * 只要是 DevHttpManager 库内部创建的 [ProgressRequestBody]、[ProgressResponseBody]
     * 都会统一使用该回调接口实现
     */
    private val sGlobalCallback = object : Progress.Callback {
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