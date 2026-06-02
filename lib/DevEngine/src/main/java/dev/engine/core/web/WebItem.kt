package dev.engine.core.web

import android.view.View
import dev.engine.web.IWebEngine
import java.lang.ref.WeakReference

/**
 * detail: WebView Item Params
 * @author Ttt
 */
open class WebItem private constructor(
    view: View?
) : IWebEngine.EngineItem {

    // WebView
    private var mView: WeakReference<View>? = view?.let { WeakReference(it) }

    // WebView Config
    private var mConfig: IWebEngine.EngineConfig? = null

    // 处理各种通知和请求事件对象 ( WebViewClient )
    private var mWebViewClient: Any? = null

    // 辅助处理 Javascript 对话框、标题等对象 ( WebChromeClient )
    private var mWebChromeClient: Any? = null

    // 下载监听 ( DownloadListener )
    private var mDownloadListener: Any? = null

    // 查找结果监听 ( FindListener )
    private var mFindListener: Any? = null

    // JS 交互注入对象集合 ( interfaceName-obj )
    private var mJavascriptInterfaces: MutableMap<String, Any> = LinkedHashMap()

    // 页面加载监听
    private var mOnWebListener: IWebEngine.OnWebListener? = null

    companion object {

        /**
         * 创建 WebView Item
         * @param view WebView
         * @return [WebItem]
         */
        fun create(view: View?): WebItem {
            return WebItem(view)
        }
    }

    // ===========
    // = get/set =
    // ===========

    override fun view(): WeakReference<View>? {
        return mView
    }

    open fun setView(view: View?): WebItem {
        mView = view?.let { WeakReference(it) }
        return this
    }

    open fun release(): WebItem {
        mView?.clear()
        mView = null
        mConfig = null
        mWebViewClient = null
        mWebChromeClient = null
        mDownloadListener = null
        mFindListener = null
        mJavascriptInterfaces.clear()
        mOnWebListener = null
        return this
    }

    override fun config(): IWebEngine.EngineConfig? {
        return mConfig
    }

    open fun setConfig(config: IWebEngine.EngineConfig?): WebItem {
        mConfig = config
        return this
    }

    override fun webViewClient(): Any? {
        return mWebViewClient
    }

    open fun setWebViewClient(client: Any?): WebItem {
        mWebViewClient = client
        return this
    }

    override fun webChromeClient(): Any? {
        return mWebChromeClient
    }

    open fun setWebChromeClient(client: Any?): WebItem {
        mWebChromeClient = client
        return this
    }

    override fun downloadListener(): Any? {
        return mDownloadListener
    }

    open fun setDownloadListener(listener: Any?): WebItem {
        mDownloadListener = listener
        return this
    }

    override fun findListener(): Any? {
        return mFindListener
    }

    open fun setFindListener(listener: Any?): WebItem {
        mFindListener = listener
        return this
    }

    override fun javascriptInterfaces(): MutableMap<String, Any> {
        return mJavascriptInterfaces
    }

    /**
     * 添加 JS 交互注入对象
     * @param interfaceName 在 JavaScript 中公开对象的名称
     * @param obj           JavaScript 交互方法注入对象
     * @return [WebItem]
     */
    open fun putJavascriptInterface(
        interfaceName: String,
        obj: Any
    ): WebItem {
        mJavascriptInterfaces[interfaceName] = obj
        return this
    }

    override fun onWebListener(): IWebEngine.OnWebListener? {
        return mOnWebListener
    }

    open fun setOnWebListener(listener: IWebEngine.OnWebListener?): WebItem {
        mOnWebListener = listener
        return this
    }
}