package dev.http.progress

import android.os.Handler
import dev.DevUtils
import dev.utils.LogPrintUtils
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import java.io.IOException

/**
 * detail: 下行 ( 下载、响应 ) 进度监听响应体 RequestBody
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
    protected val refreshTime: Long = Progress.REFRESH_TIME
) : ResponseBody() {

    // 日志 TAG
    protected val TAG = ProgressResponseBody::class.java.simpleName

    // ===============
    // = ResponseBody =
    // ===============

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

    override fun source(): BufferedSource {
        TODO("Not yet implemented")
    }
}