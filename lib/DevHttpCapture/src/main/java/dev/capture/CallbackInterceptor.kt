package dev.capture

import dev.utils.common.cipher.Encrypt

/**
 * detail: Http 抓包拦截器 ( 无存储逻辑, 进行回调通知 )
 * @author Ttt
 * 支持两种初始化方式
 * // 只有结束回调
 * CallbackInterceptor(endCall = xxx)
 * // 可自定义解析逻辑
 * CallbackInterceptor(eventIMPL = xxx)
 */
open class CallbackInterceptor(
    // Http 抓包结束回调
    endCall: IHttpCaptureEnd? = null,
    // Http 抓包事件回调
    eventIMPL: IHttpCaptureEvent = object : HttpCaptureEventIMPL() {
        override fun callEnd(info: CaptureInfo) {
            endCall?.callEnd(info)
        }
    }
) : BaseInterceptor(false, eventIMPL) {

    private val tag = CallbackInterceptor::class.java.simpleName

    // 抓包信息隐藏字段
    private val captureRedact = CaptureRedact()

    // ================
    // = IHttpCapture =
    // ================

    final override fun getModuleName(): String {
        return tag
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

    final override fun getModuleHttpCaptures(): List<CaptureItem> {
        return mutableListOf()
    }

    // ============
    // = override =
    // ============

    override fun getHttpFilter(): IHttpFilter? {
        return null
    }
}