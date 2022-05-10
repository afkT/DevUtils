package dev.http.progress

import android.os.Handler
import dev.DevUtils
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

/**
 * detail: 下行进度监听响应体
 * @author Ttt
 */
open class ProgressResponseBody(
    // 原数据响应体
    protected val delegate: ResponseBody,
    // 上传、下载回调接口
    protected val callback: Progress.Callback?,
    // 回调 UI 线程通知 ( 如果为 null 则会非 UI 线程通知 )
    protected val handler: Handler? = DevUtils.getHandler(),
    // 回调刷新时间 ( 毫秒 ) - 小于等于 0 则每次进度变更都进行通知
    protected val refreshTime: Long = Progress.REFRESH_TIME,
    // 额外携带信息 ( 可通过 Request.toExtras() 创建 )
    protected val extras: Progress.Extras? = null
) : ResponseBody() {

    // ===============
    // = ResponseBody =
    // ===============

    override fun contentType(): MediaType? {
        return delegate.contentType()
    }

    override fun contentLength(): Long {
        return delegate.contentLength()
    }

    override fun source(): BufferedSource {
//        return CountingSource(delegate.source()).buffer()
        return bufferedSource
    }

    // ============
    // = 内部包装类 =
    // ============

    private val bufferedSource: BufferedSource by lazy {
        CountingSource(delegate.source()).buffer()
    }

    /**
     * detail: 内部进度监听包装类
     * @author Ttt
     */
    private inner class CountingSource(source: Source) : ForwardingSource(source) {

        // 进度信息存储类
        private val progress = Progress(false)

        init {
            progress.setExtras(extras)
                .setTotalSize(contentLength())
        }

        // ====================
        // = ForwardingSource =
        // ====================

        override fun read(
            sink: Buffer,
            byteCount: Long
        ): Long {
            if (progress.getTotalSize() <= 0) {
                progress.setTotalSize(contentLength())
            }
            if (progress.getTotalSize() > 0) {
                progress.toStartAndCallback(callback, handler)
            }

            val byteRead: Long
            try {
                byteRead = super.read(sink, byteCount)
            } catch (e: Exception) {
                progress.flowIng().toErrorAndCallback(e, callback, handler)
                throw e
            }
            if (progress.getTotalSize() > 0) {
                progress.flowIng()
                // 更新进度信息并返回是否允许通知
                val allowCallback = changeProgress(
                    progress, refreshTime, byteRead.coerceAtLeast(0)
                )
                if (allowCallback) {
                    progress.toIngAndCallback(callback, handler)
                }
                if (progress.isSizeSame()) {
                    progress.toFinishAndCallback(callback, handler)
                }
            }
            return byteRead
        }
    }

//    /**
//     * detail: 内部进度监听包装类
//     * @author Ttt
//     * 历史实现代码, 当 contentLength() 为 -1 时
//     * 只会触发一次 startCallback 且不会触发其他回调
//     */
//    private inner class CountingSource(source: Source) : ForwardingSource(source) {
//
//        // 进度信息存储类
//        private val progress = Progress(false)
//
//        init {
//            progress.setExtras(extras)
//                .setTotalSize(contentLength())
//                .toStartAndCallback(callback, handler)
//        }
//
//        // ====================
//        // = ForwardingSource =
//        // ====================
//
//        override fun read(
//            sink: Buffer,
//            byteCount: Long
//        ): Long {
//            progress.toIng()
//
//            val byteRead: Long
//            try {
//                byteRead = super.read(sink, byteCount)
//            } catch (e: Exception) {
//                progress.toErrorAndCallback(e, callback, handler)
//                throw e
//            }
//            if (progress.getTotalSize() <= 0) {
//                progress.setTotalSize(contentLength())
//            }
//            val allowCallback = changeProgress(
//                progress, refreshTime, byteRead.coerceAtLeast(0)
//            )
//            if (allowCallback) {
//                progress.toIngAndCallback(callback, handler)
//            }
//            if (progress.isSizeSame()) {
//                progress.toFinishAndCallback(callback, handler)
//            }
//            return byteRead
//        }
//    }
}