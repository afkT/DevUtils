package dev.http.progress

import android.os.Handler
import dev.DevUtils
import dev.utils.LogPrintUtils
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*
import java.io.IOException

/**
 * detail: 上行 ( 上传、请求 ) 进度监听请求体
 * @author Ttt
 * 通过此类获取 OkHttp 请求体数据处理进度, 可以处理任何的 RequestBody
 * <p></p>
 * shouldOneShot:
 * 避免拦截器调用 writeTo 导致多次触发
 * @see https://square.github.io/okhttp/4.x/okhttp/okhttp3/-request-body/is-one-shot
 * 自行传参或重写 [isOneShot] 方法决定是否允许多次触发
 * <p></p>
 * 注意事项:
 * 并非实现该方法就能够避免多次触发, 也需要拦截器内部调用 [isOneShot] 判断
 * 可参考 DevHttpCapture 库的
 * CallbackInterceptor、HttpCaptureInterceptor
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/CallbackInterceptor.java
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/HttpCaptureInterceptor.java
 * <p></p>
 * 或 OkHttp logging-interceptor 库的 HttpLoggingInterceptor
 * @see https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor
 */
open class ProgressRequestBody(
    // 原数据请求体
    protected val delegate: RequestBody,
    // 上传、下载回调接口
    protected val callback: Progress.Callback?,
    // 回调 UI 线程通知 ( 如果为 null 则会非 UI 线程通知 )
    protected val handler: Handler? = DevUtils.getHandler(),
    // 回调刷新时间 ( 毫秒 ) - 小于等于 0 则每次进度变更都进行通知
    protected val refreshTime: Long = Progress.REFRESH_TIME,
    // 是否推荐请求一次 ( isOneShot() return 使用 ) 避免拦截器调用 writeTo 导致多次触发
    protected val shouldOneShot: Boolean = true
) : RequestBody() {

    // 日志 TAG
    protected val TAG = ProgressRequestBody::class.java.simpleName

    // ===============
    // = RequestBody =
    // ===============

    override fun isOneShot(): Boolean {
        return shouldOneShot
    }

    override fun contentType(): MediaType? {
        return delegate.contentType()
    }

    override fun contentLength(): Long {
        return try {
            delegate.contentLength()
        } catch (e: IOException) {
            LogPrintUtils.eTag(TAG, e, "contentLength")
            -1L
        }
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val countingSink = CountingSink(sink)
        val bufferedSink = countingSink.buffer()
        delegate.writeTo(bufferedSink)
        bufferedSink.flush()
        /**
         * 在这里调用 finish 是防止后台异常无限制上传
         * 而不是通过 [Progress.isSizeSame]
         * 判断数据总长度与当前已上传、下载总长度是否一样大小
         */
        countingSink.finishCallback()
    }

    // ============
    // = 内部包装类 =
    // ============

    /**
     * detail: 内部进度监听包装类
     * @author Ttt
     */
    private inner class CountingSink(sink: Sink) : ForwardingSink(sink) {

        // 进度信息存储类
        private val progress = Progress(true)

        init {
            progress.setTotalSize(contentLength())
                .toStartAndCallback(callback, handler)
        }

        // ==================
        // = ForwardingSink =
        // ==================

        @Throws(IOException::class)
        override fun write(
            source: Buffer,
            byteCount: Long
        ) {
            progress.toIng()
            try {
                super.write(source, byteCount)
            } catch (e: Exception) {
                progress.toErrorAndCallback(e, callback, handler)
                throw e
            }
            if (progress.getTotalSize() <= 0) {
                progress.setTotalSize(contentLength())
            }
            val allowCallback = changeProgress(
                progress, refreshTime, byteCount.coerceAtLeast(0)
            )
            if (allowCallback) {
                progress.toIngAndCallback(callback, handler)
            }
        }

        // =============
        // = 对外公开方法 =
        // =============

        /**
         * 流程完成回调
         */
        fun finishCallback() {
            progress.toFinishAndCallback(callback, handler)
        }
    }
}