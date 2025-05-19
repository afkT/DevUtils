package dev.capture.interceptor

import dev.capture.CaptureInfo
import dev.capture.CaptureItem
import dev.capture.CaptureRedact
import dev.capture.Utils
import dev.capture.interfaces.*
import dev.utils.common.cipher.Encrypt

/**
 * detail: Http 抓包拦截器 ( 存在存储抓包数据逻辑 )
 * @author Ttt
 * 写入本地文件 ( 未开启线程，如果有性能要求可使用 [CallbackInterceptor]、[SimpleInterceptor] )
 * 可以通过 [IHttpCaptureEnd] 回调信息 [CaptureInfo] 自行写入本地文件、数据库
 * ============
 * 该抓包拦截器适用于敏捷开发，搭配 DevHttpCaptureCompiler 可视化功能库
 * 快捷展示存储的抓包数据，减少开发成本
 * 如果抓包数据量过大且长期维护，建议使用 [CallbackInterceptor] 并自行实现存储逻辑、UI 可视化功能
 * ============
 * 该拦截器与 [CallbackInterceptor] 最大的区别在于 [BaseInterceptor] 中调用
 * storageEngine.captureStorage() 进行存储操作
 */
open class StorageInterceptor(
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
