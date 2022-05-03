package dev.http.progress

import android.os.Handler
import dev.DevUtils
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*

/**
 * detail: 下行 ( 下载、响应 ) 进度监听响应体
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
    // 额外携带信息
    protected val extras: Progress.Extras?
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
        return CountingSource(delegate.source()).buffer()
    }

    // ============
    // = 内部包装类 =
    // ============

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
                .toStartAndCallback(callback, handler)
        }

        // ====================
        // = ForwardingSource =
        // ====================

        override fun read(
            sink: Buffer,
            byteCount: Long
        ): Long {
            progress.toIng()

            val byteRead: Long
            try {
                byteRead = super.read(sink, byteCount)
            } catch (e: Exception) {
                progress.toErrorAndCallback(e, callback, handler)
                throw e
            }
            if (progress.getTotalSize() <= 0) {
                progress.setTotalSize(contentLength())
            }
            val allowCallback = changeProgress(
                progress, refreshTime, byteRead.coerceAtLeast(0)
            )
            if (allowCallback) {
                progress.toIngAndCallback(callback, handler)
            }
            if (progress.isSizeSame()) {
                progress.toFinishAndCallback(callback, handler)
            }
            return byteRead
        }
    }
}