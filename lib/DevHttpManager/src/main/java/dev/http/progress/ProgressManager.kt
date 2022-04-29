package dev.http.progress

/**
 * detail: Progress Manager
 * @author Ttt
 * OkHttp API:
 * @see https://square.github.io/okhttp/recipes
 */
internal object ProgressManager {

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