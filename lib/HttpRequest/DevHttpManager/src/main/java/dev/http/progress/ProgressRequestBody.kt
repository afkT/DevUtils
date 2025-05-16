package dev.http.progress

import android.os.Handler
import dev.DevUtils
import dev.utils.LogPrintUtils
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*
import java.io.IOException

/**
 * detail: 上行进度监听请求体
 * @author Ttt
 * 通过此类获取 OkHttp 请求体数据处理进度, 可以处理任何的 RequestBody
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
    // 额外携带信息 ( 可通过 Request.toExtras() 创建 )
    protected val extras: Progress.Extras? = null
) : RequestBody() {

    // 日志 TAG
    protected val TAG = ProgressRequestBody::class.java.simpleName

    // ===============
    // = RequestBody =
    // ===============

    override fun isOneShot(): Boolean {
        return delegate.isOneShot()
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
//        val countingSink = CountingSink(sink)
//        val bufferedSink = countingSink.buffer()
//        delegate.writeTo(bufferedSink)
//        bufferedSink.flush()
//        /**
//         * 在这里调用 finish 是防止后台异常无限制上传
//         * 而不是通过 [Progress.isSizeSame]
//         * 判断数据总长度与当前已上传、下载总长度是否一样大小
//         */
//        countingSink.finishCallback()

        if (innerBufferedSink == null) {
            innerBufferedSink = InnerBufferedSink(sink)
        }
        innerBufferedSink?.writeTo(delegate)
    }

    // ============
    // = 内部包装类 =
    // ============

    private var innerBufferedSink: InnerBufferedSink? = null

    /**
     * detail: writeTo 注释代码二次封装
     * @author Ttt
     */
    private inner class InnerBufferedSink(sink: BufferedSink) {

        val countingSink = CountingSink(sink)

        val bufferedSink: BufferedSink by lazy {
            countingSink.buffer()
        }

        fun writeTo(body: RequestBody) {
            body.writeTo(bufferedSink)
            bufferedSink.flush()
            /**
             * 在这里调用 finish 是防止后台异常无限制上传
             * 而不是通过 [Progress.isSizeSame]
             * 判断数据总长度与当前已上传、下载总长度是否一样大小
             */
            countingSink.finishCallback()
        }
    }

    /**
     * detail: 内部进度监听包装类
     * @author Ttt
     */
    private inner class CountingSink(sink: Sink) : ForwardingSink(sink) {

        // 进度信息存储类
        private val progress = Progress(true)

        init {
            progress.setExtras(extras)
                .setTotalSize(contentLength())
        }

        // ==================
        // = ForwardingSink =
        // ==================

        @Throws(IOException::class)
        override fun write(
            source: Buffer,
            byteCount: Long
        ) {
            if (progress.getTotalSize() <= 0) {
                progress.setTotalSize(contentLength())
            }
            if (progress.getTotalSize() > 0) {
                progress.toStartAndCallback(callback, handler)
            }

            try {
                super.write(source, byteCount)
            } catch (e: Exception) {
                progress.flowIng().toErrorAndCallback(e, callback, handler)
                throw e
            }
            if (progress.getTotalSize() > 0) {
                progress.flowIng()
                // 更新进度信息并返回是否允许通知
                val allowCallback = changeProgress(
                    progress, refreshTime, byteCount.coerceAtLeast(0)
                )
                if (allowCallback) {
                    progress.toIngAndCallback(callback, handler)
                }
            }
        }

        // =============
        // = 对外公开方法 =
        // =============

        /**
         * 流程完成回调
         */
        fun finishCallback() {
            if (progress.getTotalSize() > 0) {
                progress.toFinishAndCallback(callback, handler)
            }
        }
    }

//    /**
//     * detail: 内部进度监听包装类
//     * @author Ttt
//     * 历史实现代码, 待优化回调顺序
//     */
//    private inner class CountingSink(sink: Sink) : ForwardingSink(sink) {
//
//        // 进度信息存储类
//        private val progress = Progress(true)
//
//        init {
//            progress.setExtras(extras)
//                .setTotalSize(contentLength())
//                .toStartAndCallback(callback, handler)
//        }
//
//        // ==================
//        // = ForwardingSink =
//        // ==================
//
//        @Throws(IOException::class)
//        override fun write(
//            source: Buffer,
//            byteCount: Long
//        ) {
//            progress.toIng()
//            try {
//                super.write(source, byteCount)
//            } catch (e: Exception) {
//                progress.toErrorAndCallback(e, callback, handler)
//                throw e
//            }
//            if (progress.getTotalSize() <= 0) {
//                progress.setTotalSize(contentLength())
//            }
//            val allowCallback = changeProgress(
//                progress, refreshTime, byteCount.coerceAtLeast(0)
//            )
//            if (allowCallback) {
//                progress.toIngAndCallback(callback, handler)
//            }
//        }
//
//        // =============
//        // = 对外公开方法 =
//        // =============
//
//        /**
//         * 流程完成回调
//         */
//        fun finishCallback() {
//            progress.toFinishAndCallback(callback, handler)
//        }
//    }
}