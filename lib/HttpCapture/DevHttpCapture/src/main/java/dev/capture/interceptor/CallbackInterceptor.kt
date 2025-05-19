package dev.capture.interceptor

import dev.capture.CaptureInfo
import dev.capture.CaptureItem
import dev.capture.CaptureRedact
import dev.capture.interfaces.*
import dev.utils.common.cipher.Encrypt

/**
 * detail: Http 抓包拦截器 ( 无存储逻辑, 通过回调通知 )
 * @author Ttt
 * 如果对 [StorageInterceptor] 存储性能以及逻辑实现代码，觉得太过复杂不够简洁优美
 * 可以通过 [IHttpCaptureEnd] 回调信息 [CaptureInfo] 自行写入本地文件、数据库
 */
open class CallbackInterceptor(
    // Http 抓包结束回调
    endCall: IHttpCaptureEnd? = null,
    // Http 抓包事件回调
    eventIMPL: IHttpCaptureEvent = object : HttpCaptureEventIMPL() {
        override fun callEnd(info: CaptureInfo) {
            endCall?.callEnd(info)
        }
    },
    // Http 抓包事件处理拦截
    eventFilter: IHttpCaptureEventFilter = object : IHttpCaptureEventFilter {}
) : BaseInterceptor(false, eventIMPL, eventFilter) {

    // 抓包信息隐藏字段
    private val captureRedact = CaptureRedact()

    // ================
    // = IHttpCapture =
    // ================

    final override fun getModuleName(): String {
        return ""
    }

    final override fun getEncrypt(): Encrypt? {
        return null
    }

    final override fun isCapture(): Boolean {
        return true
    }

    final override fun setCapture(capture: Boolean) {
    }

    final override fun captureRedact(): CaptureRedact {
        return captureRedact
    }

    final override fun getModulePath(): String {
        return ""
    }

    final override fun getModuleHttpCaptures(): MutableList<CaptureItem> {
        return mutableListOf()
    }

    // ============
    // = override =
    // ============

    override fun getHttpFilter(): IHttpFilter? {
        return null
    }
}