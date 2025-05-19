package dev.capture

import dev.utils.common.cipher.Encrypt

/**
 * detail: Http 抓包拦截器 ( 存在存储抓包数据逻辑 )
 * @author Ttt
 */
class HttpCaptureInterceptor(
    // 模块名 ( 要求唯一性 )
    private val moduleName: String,
    // 抓包数据加密中间层
    private val encrypt: Encrypt? = null,
    // Http 拦截过滤器
    private val httpFilter: IHttpFilter? = null,
    // 是否进行 Http 抓包拦截
    private var capture: Boolean = true,
    // Http 抓包事件回调
    eventIMPL: IHttpCaptureEvent = object : HttpCaptureEventIMPL() {
        override fun callEnd(info: CaptureInfo) {
        }
    },
    // Http 抓包事件处理拦截
    eventFilter: IHttpCaptureEventFilter = object : IHttpCaptureEventFilter {}
) : BaseInterceptor(true, eventIMPL, eventFilter) {

    // 抓包信息隐藏字段
    private val captureRedact = CaptureRedact()

    // ================
    // = IHttpCapture =
    // ================

    override fun getModuleName(): String {
        return moduleName
    }

    override fun getEncrypt(): Encrypt? {
        return encrypt
    }

    override fun getHttpFilter(): IHttpFilter? {
        return httpFilter
    }

    override fun isCapture(): Boolean {
        return capture
    }

    override fun setCapture(capture: Boolean) {
        this.capture = capture
    }

    override fun captureRedact(): CaptureRedact {
        return captureRedact
    }

    override fun getModulePath(): String {
        return Utils.getModulePath(moduleName)
    }

    override fun getModuleHttpCaptures(): MutableList<CaptureItem> {
        return Utils.getModuleHttpCaptures(
            moduleName, encrypt != null
        )
    }
}