package dev.engine.core.web

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import androidx.webkit.*
import androidx.webkit.TracingConfig
import androidx.webkit.TracingController
import androidx.webkit.WebViewRenderProcessClient
import dev.DevUtils
import dev.engine.web.IWebEngine
import dev.utils.LogPrintUtils
import dev.utils.app.ViewUtils
import java.io.OutputStream
import java.util.concurrent.Executor

/**
 * detail: System WebView Engine 实现
 * @author Ttt
 * @see https://developer.android.com/reference/android/webkit/WebView
 */
open class WebViewEngineImpl(
    @JvmField protected val mConfig: WebConfig
) : IWebEngine<WebConfig?, WebItem?> {

    // 日志 TAG
    private val TAG = WebViewEngineImpl::class.java.simpleName

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 WebView Engine Config
     * @return WebView Config
     */
    override fun getConfig(): WebConfig {
        return mConfig
    }

    /**
     * 初始化 WebView ( 应用配置、设置 Client、监听、JS 交互对象 )
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    override fun initialize(item: WebItem?): Boolean {
        getWebView(item) ?: return false
        item?.let { itemIt ->
            applyConfig(itemIt, getWebConfig(itemIt))
            itemIt.webViewClient()?.let { setWebViewClient(itemIt, it) }
            itemIt.webChromeClient()?.let { setWebChromeClient(itemIt, it) }
            itemIt.downloadListener()?.let { setDownloadListener(itemIt, it) }
            itemIt.findListener()?.let { setFindListener(itemIt, it) }
            itemIt.javascriptInterfaces().forEach { (interfaceName, obj) ->
                addJavascriptInterface(itemIt, obj, interfaceName)
            }
            // 页面加载监听: 未显式设置 Client 时, 将内核无关的 OnWebListener 转译为 WebView 回调
            itemIt.onWebListener()?.let { listener ->
                bindOnWebListener(itemIt, listener)
            }
        } ?: return false
        return true
    }

    /**
     * 绑定页面加载监听 ( 将 OnWebListener 转译为 WebViewClient / WebChromeClient 回调 )
     * @param item     WebView Item
     * @param listener 页面加载监听
     */
    protected open fun bindOnWebListener(
        item: WebItem?,
        listener: IWebEngine.OnWebListener
    ) {
        val webView = getWebViewImpl(item) ?: return
        if (item?.webViewClient() == null) {
            webView.webViewClient = object : WebViewClient() {
                override fun onPageStarted(
                    view: WebView?,
                    url: String?,
                    favicon: Bitmap?
                ) {
                    listener.onPageStarted(view, url)
                }

                override fun onPageFinished(
                    view: WebView?,
                    url: String?
                ) {
                    listener.onPageFinished(view, url)
                }

                @Suppress("DEPRECATION")
                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    listener.onReceivedError(view, errorCode, description, failingUrl)
                }
            }
        }
        if (item?.webChromeClient() == null) {
            webView.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(
                    view: WebView?,
                    newProgress: Int
                ) {
                    listener.onProgressChanged(view, newProgress)
                }

                override fun onReceivedTitle(
                    view: WebView?,
                    title: String?
                ) {
                    listener.onReceivedTitle(view, title)
                }
            }
        }
    }

    /**
     * 应用 WebView Config
     * @param item WebView Item
     * @param config WebView Config
     * @return `true` success, `false` fail
     */
    @SuppressLint("WrongConstant")
    override fun applyConfig(
        item: WebItem?,
        config: WebConfig?
    ): Boolean {
        val webSettings = getSettings(item) ?: return false
        config?.let { configIt ->
            configIt.javaScriptEnabled()?.let {
                webSettings.javaScriptEnabled = it
            }
            getRenderPriority(configIt.renderPriority())?.let {
                @Suppress("DEPRECATION")
                webSettings.setRenderPriority(it)
            }
            configIt.useWideViewPort()?.let {
                webSettings.useWideViewPort = it
            }
            configIt.loadWithOverviewMode()?.let {
                webSettings.loadWithOverviewMode = it
            }
            getLayoutAlgorithm(configIt.layoutAlgorithm())?.let {
                webSettings.layoutAlgorithm = it
            }
            configIt.supportZoom()?.let {
                webSettings.setSupportZoom(it)
            }
            configIt.builtInZoomControls()?.let {
                webSettings.builtInZoomControls = it
            }
            configIt.displayZoomControls()?.let {
                webSettings.displayZoomControls = it
            }
            val textZoom = configIt.textZoom()
            if (textZoom > 0) {
                webSettings.textZoom = textZoom
            }
            configIt.standardFontFamily()?.let {
                webSettings.standardFontFamily = it
            }
            val defaultFontSize = configIt.defaultFontSize()
            if (defaultFontSize > 0) {
                webSettings.defaultFontSize = defaultFontSize
            }
            val minimumFontSize = configIt.minimumFontSize()
            if (minimumFontSize > 0) {
                webSettings.minimumFontSize = minimumFontSize
            }
            val defaultFixedFontSize = configIt.defaultFixedFontSize()
            if (defaultFixedFontSize > 0) {
                webSettings.defaultFixedFontSize = defaultFixedFontSize
            }
            val minimumLogicalFontSize = configIt.minimumLogicalFontSize()
            if (minimumLogicalFontSize > 0) {
                webSettings.minimumLogicalFontSize = minimumLogicalFontSize
            }
            configIt.fixedFontFamily()?.let {
                webSettings.fixedFontFamily = it
            }
            configIt.sansSerifFontFamily()?.let {
                webSettings.sansSerifFontFamily = it
            }
            configIt.serifFontFamily()?.let {
                webSettings.serifFontFamily = it
            }
            configIt.cursiveFontFamily()?.let {
                webSettings.cursiveFontFamily = it
            }
            configIt.fantasyFontFamily()?.let {
                webSettings.fantasyFontFamily = it
            }
            val mixedContentMode = configIt.mixedContentMode()
            if (mixedContentMode >= 0
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            ) {
                webSettings.mixedContentMode = mixedContentMode
            }
            configIt.loadsImagesAutomatically()?.let {
                webSettings.loadsImagesAutomatically = it
            }
            configIt.javaScriptCanOpenWindowsAutomatically()?.let {
                webSettings.javaScriptCanOpenWindowsAutomatically = it
            }
            configIt.defaultTextEncodingName()?.let {
                webSettings.defaultTextEncodingName = it
            }
            configIt.geolocationEnabled()?.let {
                webSettings.setGeolocationEnabled(it)
            }
            configIt.userAgentString()?.let {
                webSettings.userAgentString = it
            }
            configIt.allowFileAccess()?.let {
                webSettings.allowFileAccess = it
            }
            configIt.allowFileAccessFromFileURLs()?.let {
                webSettings.allowFileAccessFromFileURLs = it
            }
            configIt.allowUniversalAccessFromFileURLs()?.let {
                webSettings.allowUniversalAccessFromFileURLs = it
            }
            configIt.blockNetworkLoads()?.let {
                webSettings.blockNetworkLoads = it
            }
            configIt.blockNetworkImage()?.let {
                webSettings.blockNetworkImage = it
            }
            configIt.mediaPlaybackRequiresUserGesture()?.let {
                webSettings.mediaPlaybackRequiresUserGesture = it
            }
            configIt.cacheMode()?.let {
                webSettings.cacheMode = it
            }
            configIt.domStorageEnabled()?.let {
                webSettings.domStorageEnabled = it
            }
            // Application Caches ( appCacheEnabled、appCachePath、appCacheMaxSize ) 配置仅作存储保留
            // 对应 WebSettings.setAppCacheEnabled / setAppCachePath / setAppCacheMaxSize 已在 Android 13 移除, 故不再下发
            configIt.databaseEnabled()?.let {
                webSettings.databaseEnabled = it
            }
            configIt.databasePath()?.let {
                @Suppress("DEPRECATION")
                webSettings.databasePath = it
            }
            configIt.allowContentAccess()?.let {
                webSettings.allowContentAccess = it
            }
            configIt.supportMultipleWindows()?.let {
                webSettings.setSupportMultipleWindows(it)
            }
            configIt.needInitialFocus()?.let {
                webSettings.setNeedInitialFocus(it)
            }
            configIt.safeBrowsingEnabled()?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    webSettings.safeBrowsingEnabled = it
                }
            }
            configIt.offscreenPreRaster()?.let {
                webSettings.offscreenPreRaster = it
            }
            val disabledActionModeMenuItems = configIt.disabledActionModeMenuItems()
            if (disabledActionModeMenuItems >= 0
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
            ) {
                webSettings.disabledActionModeMenuItems = disabledActionModeMenuItems
            }
            configIt.algorithmicDarkeningAllowed()?.let {
                if (WebViewFeature.isFeatureSupported(WebViewFeature.ALGORITHMIC_DARKENING)) {
                    WebSettingsCompat.setAlgorithmicDarkeningAllowed(webSettings, it)
                }
            }
            configIt.paymentRequestEnabled()?.let {
                if (WebViewFeature.isFeatureSupported(WebViewFeature.PAYMENT_REQUEST)) {
                    WebSettingsCompat.setPaymentRequestEnabled(webSettings, it)
                }
            }
            configIt.enterpriseAuthenticationAppLinkPolicyEnabled()?.let {
                if (WebViewFeature.isFeatureSupported(
                        WebViewFeature.ENTERPRISE_AUTHENTICATION_APP_LINK_POLICY
                    )
                ) {
                    WebSettingsCompat.setEnterpriseAuthenticationAppLinkPolicyEnabled(
                        webSettings, it
                    )
                }
            }
            val attributionRegistrationBehavior = configIt.attributionRegistrationBehavior()
            if (attributionRegistrationBehavior >= 0
                && WebViewFeature.isFeatureSupported(
                    WebViewFeature.ATTRIBUTION_REGISTRATION_BEHAVIOR
                )
            ) {
                WebSettingsCompat.setAttributionRegistrationBehavior(
                    webSettings, attributionRegistrationBehavior
                )
            }
            configIt.backForwardCacheEnabled()?.let {
                if (WebViewFeature.isFeatureSupported(WebViewFeature.BACK_FORWARD_CACHE)) {
                    WebSettingsCompat.setBackForwardCacheEnabled(webSettings, it)
                }
            }
            applySpeculativeLoadingStatus(webSettings, configIt.speculativeLoadingStatus())
            val hyperlinkContextMenuItems = configIt.hyperlinkContextMenuItems()
            if (hyperlinkContextMenuItems >= 0
                && WebViewFeature.isFeatureSupported(WebViewFeature.HYPERLINK_CONTEXT_MENU_ITEMS)
            ) {
                WebSettingsCompat.setHyperlinkContextMenuItems(
                    webSettings,
                    hyperlinkContextMenuItems
                )
            }
            configIt.hasEnrolledInstrumentEnabled()?.let {
                if (WebViewFeature.isFeatureSupported(WebViewFeature.PAYMENT_REQUEST)) {
                    WebSettingsCompat.setHasEnrolledInstrumentEnabled(webSettings, it)
                }
            }
            configIt.cookiesIncludedInShouldInterceptRequest()?.let {
                if (WebViewFeature.isFeatureSupported(WebViewFeature.COOKIE_INTERCEPT)) {
                    WebSettingsCompat.setCookiesIncludedInShouldInterceptRequest(webSettings, it)
                }
            }
            val webAuthenticationSupport = configIt.webAuthenticationSupport()
            if (webAuthenticationSupport >= 0
                && WebViewFeature.isFeatureSupported(WebViewFeature.WEB_AUTHENTICATION)
            ) {
                WebSettingsCompat.setWebAuthenticationSupport(webSettings, webAuthenticationSupport)
            }
            // forceUserScalable 仅作存储, System WebView 无对应 API; GeckoView / X5 实现层按各自内核应用
        } ?: return false
        return true
    }

    /**
     * 应用预测式加载状态 ( 实验性 API, 单独封装以收敛 OptIn 注解 )
     * @param webSettings [WebSettings]
     * @param status 预测式加载状态 ( 小于 0 表示未设置 )
     */
    @androidx.annotation.OptIn(
        markerClass = [WebSettingsCompat.ExperimentalSpeculativeLoading::class]
    )
    protected open fun applySpeculativeLoadingStatus(
        webSettings: WebSettings,
        status: Int
    ) {
        if (status >= 0 && WebViewFeature.isFeatureSupported(WebViewFeature.SPECULATIVE_LOADING)) {
            WebSettingsCompat.setSpeculativeLoadingStatus(webSettings, status)
        }
    }

    /**
     * 获取 WebView
     * @param item WebView Item
     * @return WebView
     */
    override fun getWebView(item: WebItem?): View? {
        return getWebViewImpl(item)
    }

    /**
     * WebView 是否不为 null
     * @param item WebView Item
     * @return `true` yes, `false` no
     */
    override fun isWebViewNotEmpty(item: WebItem?): Boolean {
        return getWebViewImpl(item) != null
    }

    // ==========
    // = 加载方法 =
    // ==========

    /**
     * 加载网页
     * @param item WebView Item
     * @param url 资源地址
     * @return `true` success, `false` fail
     */
    override fun loadUrl(
        item: WebItem?,
        url: String?
    ): Boolean {
        url ?: return false
        return getWebViewImpl(item)?.run {
            loadUrl(url); true
        } ?: false
    }

    /**
     * 加载网页
     * @param item WebView Item
     * @param url 资源地址
     * @param additionalHttpHeaders Http 请求头信息
     * @return `true` success, `false` fail
     */
    override fun loadUrl(
        item: WebItem?,
        url: String?,
        additionalHttpHeaders: MutableMap<String, String>?
    ): Boolean {
        if (url == null || additionalHttpHeaders == null) return false
        return getWebViewImpl(item)?.run {
            loadUrl(url, additionalHttpHeaders); true
        } ?: false
    }

    /**
     * 加载 Html 代码
     * @param item WebView Item
     * @param data Html 数据
     * @param mimeType 加载网页的类型
     * @param encoding 编码格式
     * @return `true` success, `false` fail
     */
    override fun loadData(
        item: WebItem?,
        data: String?,
        mimeType: String?,
        encoding: String?
    ): Boolean {
        data ?: return false
        return getWebViewImpl(item)?.run {
            loadData(data, mimeType, encoding); true
        } ?: false
    }

    /**
     * 加载 Html 代码
     * @param item WebView Item
     * @param baseUrl 基础链接
     * @param data Html 数据
     * @param mimeType 加载网页的类型
     * @param encoding 编码格式
     * @param historyUrl 可用历史记录
     * @return `true` success, `false` fail
     */
    override fun loadDataWithBaseURL(
        item: WebItem?,
        baseUrl: String?,
        data: String?,
        mimeType: String?,
        encoding: String?,
        historyUrl: String?
    ): Boolean {
        data ?: return false
        return getWebViewImpl(item)?.run {
            loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl); true
        } ?: false
    }

    /**
     * 使用 POST 方法将带有 postData 的 url 加载到 WebView 中
     * @param item WebView Item
     * @param url 资源地址
     * @param postData post 数据 ( 注意 UrlEncode )
     * @return `true` success, `false` fail
     */
    override fun postUrl(
        item: WebItem?,
        url: String?,
        postData: ByteArray?
    ): Boolean {
        if (url == null || postData == null) return false
        return getWebViewImpl(item)?.run {
            postUrl(url, postData); true
        } ?: false
    }

    // ==========
    // = 配置相关 =
    // ==========

    /**
     * 获取 WebView 配置 ( WebSettings )
     * @param item WebView Item
     * @return WebView 配置对象
     */
    override fun getSettings(item: WebItem?): WebSettings? {
        return getWebViewImpl(item)?.settings
    }

    /**
     * 获取浏览器标识 UA
     * @param item WebView Item
     * @return 浏览器标识 UA
     */
    override fun getUserAgentString(item: WebItem?): String? {
        return getSettings(item)?.userAgentString
    }

    /**
     * 设置浏览器标识 UA
     * @param item WebView Item
     * @param ua 浏览器标识 UA
     * @return `true` success, `false` fail
     */
    override fun setUserAgentString(
        item: WebItem?,
        ua: String?
    ): Boolean {
        return getSettings(item)?.run {
            userAgentString = ua; true
        } ?: false
    }

    // ==========
    // = JS 交互 =
    // ==========

    /**
     * 添加 JS 交互注入对象
     * @param item WebView Item
     * @param obj JavaScript 交互方法注入对象
     * @param interfaceName 在 JavaScript 中公开对象的名称
     * @return `true` success, `false` fail
     */
    @SuppressLint("JavascriptInterface")
    override fun addJavascriptInterface(
        item: WebItem?,
        obj: Any?,
        interfaceName: String?
    ): Boolean {
        if (obj == null || interfaceName == null) return false
        return getWebViewImpl(item)?.run {
            addJavascriptInterface(obj, interfaceName); true
        } ?: false
    }

    /**
     * 移除 JS 交互注入对象
     * @param item WebView Item
     * @param interfaceName 在 JavaScript 中公开对象的名称
     * @return `true` success, `false` fail
     */
    override fun removeJavascriptInterface(
        item: WebItem?,
        interfaceName: String?
    ): Boolean {
        interfaceName ?: return false
        return getWebViewImpl(item)?.run {
            removeJavascriptInterface(interfaceName); true
        } ?: false
    }

    /**
     * 执行 JS 方法
     * @param item WebView Item
     * @param script JS 内容
     * @param callback 执行回调结果 ( ValueCallback )
     * @return `true` success, `false` fail
     */
    override fun evaluateJavascript(
        item: WebItem?,
        script: String?,
        callback: Any?
    ): Boolean {
        script ?: return false
        val webView = getWebViewImpl(item) ?: return false
        return try {
            webView.evaluateJavascript(script, getValueCallbackString(callback))
            true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "evaluateJavascript")
            false
        }
    }

    // ==========
    // = Client =
    // ==========

    /**
     * 设置处理各种通知和请求事件对象
     * @param item WebView Item
     * @param client WebViewClient
     * @return `true` success, `false` fail
     */
    override fun setWebViewClient(
        item: WebItem?,
        client: Any?
    ): Boolean {
        val webViewClient = getWebViewClient(client) ?: return false
        return getWebViewImpl(item)?.run {
            this.webViewClient = webViewClient; true
        } ?: false
    }

    /**
     * 获取处理各种通知和请求事件对象
     * @param item WebView Item
     * @return WebViewClient
     */
    override fun getWebViewClient(item: WebItem?): WebViewClient? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return getWebViewImpl(item)?.webViewClient
        }
        return null
    }

    /**
     * 设置辅助 WebView 处理 Javascript 对话框、标题等对象
     * @param item WebView Item
     * @param client WebChromeClient
     * @return `true` success, `false` fail
     */
    override fun setWebChromeClient(
        item: WebItem?,
        client: Any?
    ): Boolean {
        val webChromeClient = getWebChromeClient(client) ?: return false
        return getWebViewImpl(item)?.run {
            this.webChromeClient = webChromeClient; true
        } ?: false
    }

    /**
     * 获取辅助 WebView 处理 Javascript 对话框、标题等对象
     * @param item WebView Item
     * @return WebChromeClient
     */
    override fun getWebChromeClient(item: WebItem?): WebChromeClient? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return getWebViewImpl(item)?.webChromeClient
        }
        return null
    }

    /**
     * 设置下载监听
     * @param item WebView Item
     * @param listener DownloadListener
     * @return `true` success, `false` fail
     */
    override fun setDownloadListener(
        item: WebItem?,
        listener: Any?
    ): Boolean {
        val downloadListener = getDownloadListener(listener) ?: return false
        return getWebViewImpl(item)?.run {
            setDownloadListener(downloadListener); true
        } ?: false
    }

    /**
     * 设置查找结果监听
     * @param item WebView Item
     * @param listener FindListener
     * @return `true` success, `false` fail
     */
    override fun setFindListener(
        item: WebItem?,
        listener: Any?
    ): Boolean {
        val findListener = getFindListener(listener) ?: return false
        return getWebViewImpl(item)?.run {
            setFindListener(findListener); true
        } ?: false
    }

    // ==========
    // = 导航历史 =
    // ==========

    /**
     * WebView 是否可以后退
     * @param item WebView Item
     * @return `true` yes, `false` no
     */
    override fun canGoBack(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.canGoBack() ?: false
    }

    /**
     * WebView 后退
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    override fun goBack(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            goBack(); true
        } ?: false
    }

    /**
     * WebView 是否可以前进
     * @param item WebView Item
     * @return `true` yes, `false` no
     */
    override fun canGoForward(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.canGoForward() ?: false
    }

    /**
     * WebView 前进
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    override fun goForward(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            goForward(); true
        } ?: false
    }

    /**
     * WebView 是否可以跳转到当前起始点相距的历史记录
     * @param item WebView Item
     * @param steps 相距索引
     * @return `true` yes, `false` no
     */
    override fun canGoBackOrForward(
        item: WebItem?,
        steps: Int
    ): Boolean {
        return getWebViewImpl(item)?.canGoBackOrForward(steps) ?: false
    }

    /**
     * WebView 跳转到当前起始点相距的历史记录
     * @param item WebView Item
     * @param steps 相距索引 ( 负数后退、正数前进 )
     * @return `true` success, `false` fail
     */
    override fun goBackOrForward(
        item: WebItem?,
        steps: Int
    ): Boolean {
        return getWebViewImpl(item)?.run {
            goBackOrForward(steps); true
        } ?: false
    }

    /**
     * 获取 WebView 历史记录列表 ( WebBackForwardList )
     * @param item WebView Item
     * @return WebView 历史记录列表
     */
    override fun copyBackForwardList(item: WebItem?): Any? {
        return getWebViewImpl(item)?.copyBackForwardList()
    }

    /**
     * 刷新页面 ( 当前页面的所有资源都会重新加载 )
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    override fun reload(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            reload(); true
        } ?: false
    }

    /**
     * 停止加载
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    override fun stopLoading(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            stopLoading(); true
        } ?: false
    }

    // ==========
    // = 清理操作 =
    // ==========

    /**
     * 清除资源缓存
     * @param item WebView Item
     * @param includeDiskFiles 是否清空本地缓存 ( false 则只会清空内存缓存, true 全部清空 )
     * @return `true` success, `false` fail
     */
    override fun clearCache(
        item: WebItem?,
        includeDiskFiles: Boolean
    ): Boolean {
        return getWebViewImpl(item)?.run {
            clearCache(includeDiskFiles); true
        } ?: false
    }

    /**
     * 清除当前 WebView 访问的历史记录
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    override fun clearHistory(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            clearHistory(); true
        } ?: false
    }

    /**
     * 清除自动完成填充的表单数据
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    override fun clearFormData(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            clearFormData(); true
        } ?: false
    }

    /**
     * 清除高亮显示的查找结果
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    override fun clearMatches(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            clearMatches(); true
        } ?: false
    }

    /**
     * 清除 SSL 偏好设置
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    override fun clearSslPreferences(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            clearSslPreferences(); true
        } ?: false
    }

    /**
     * 清除全部 Web 存储数据 ( localStorage、IndexedDB、WebSQL 等, 全局生效 )
     * @return `true` success, `false` fail
     */
    override fun deleteAllWebStorage(): Boolean {
        return try {
            WebStorage.getInstance().deleteAllData()
            true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "deleteAllWebStorage")
            false
        }
    }

    // ==========
    // = 状态查询 =
    // ==========

    /**
     * 获取缩放比例
     * @param item WebView Item
     * @return 缩放比例
     */
    @Suppress("DEPRECATION")
    override fun getScale(item: WebItem?): Float {
        return getWebViewImpl(item)?.scale ?: 1.0F
    }

    /**
     * 获取当前内容横向滚动距离
     * @param item WebView Item
     * @return 当前内容横向滚动距离
     */
    override fun getScrollX(item: WebItem?): Int {
        return getWebViewImpl(item)?.scrollX ?: 0
    }

    /**
     * 获取当前可见区域的顶端距整个页面顶端的距离 ( 当前内容滚动的距离 )
     * @param item WebView Item
     * @return 当前内容滚动的距离
     */
    override fun getScrollY(item: WebItem?): Int {
        return getWebViewImpl(item)?.scrollY ?: 0
    }

    /**
     * 获取 HTML 的高度 ( 原始高度, 不包括缩放后的高度 )
     * @param item WebView Item
     * @return HTML 的高度
     */
    override fun getContentHeight(item: WebItem?): Int {
        return getWebViewImpl(item)?.contentHeight ?: 0
    }

    /**
     * 获取缩放高度
     * @param item WebView Item
     * @return 缩放高度
     */
    override fun getScaleHeight(item: WebItem?): Int {
        return getScaleHeight(item, getScale(item))
    }

    /**
     * 获取缩放高度
     * @param item WebView Item
     * @param scale 缩放比例
     * @return 缩放高度
     */
    override fun getScaleHeight(
        item: WebItem?,
        scale: Float
    ): Int {
        return (getContentHeight(item) * scale).toInt()
    }

    /**
     * 获取 WebView 控件高度
     * @param item WebView Item
     * @return WebView 控件高度
     */
    override fun getHeight(item: WebItem?): Int {
        return getWebViewImpl(item)?.height ?: 0
    }

    /**
     * 获取当前 Url
     * @param item WebView Item
     * @return 当前 Url
     */
    override fun getUrl(item: WebItem?): String? {
        return getWebViewImpl(item)?.url
    }

    /**
     * 获取最初请求 Url
     * @param item WebView Item
     * @return 最初请求 Url
     */
    override fun getOriginalUrl(item: WebItem?): String? {
        return getWebViewImpl(item)?.originalUrl
    }

    /**
     * 获取当前页面标题
     * @param item WebView Item
     * @return 当前页面标题
     */
    override fun getTitle(item: WebItem?): String? {
        return getWebViewImpl(item)?.title
    }

    /**
     * 获取当前页面加载进度
     * @param item WebView Item
     * @return 当前页面加载进度
     */
    override fun getProgress(item: WebItem?): Int {
        return getWebViewImpl(item)?.progress ?: 0
    }

    /**
     * 获取当前页面图标 ( Bitmap )
     * @param item WebView Item
     * @return 当前页面图标
     */
    override fun getFavicon(item: WebItem?): Any? {
        return getWebViewImpl(item)?.favicon
    }

    /**
     * 获取长按事件类型 ( HitTestResult )
     * @param item WebView Item
     * @return 长按事件类型
     */
    override fun getHitTestResult(item: WebItem?): Any? {
        return getWebViewImpl(item)?.hitTestResult
    }

    /**
     * 获取当前页面 SSL 证书 ( SslCertificate )
     * @param item WebView Item
     * @return 当前页面 SSL 证书
     */
    override fun getCertificate(item: WebItem?): Any? {
        return getWebViewImpl(item)?.certificate
    }

    // ==========
    // = 滚动缩放 =
    // ==========

    /**
     * 将视图内容向下滚动一半页面大小
     * @param item WebView Item
     * @param bottom 是否滑动到底部
     * @return `true` success, `false` fail
     */
    override fun pageDown(
        item: WebItem?,
        bottom: Boolean
    ): Boolean {
        return getWebViewImpl(item)?.pageDown(bottom) ?: false
    }

    /**
     * 将视图内容向上滚动一半页面大小
     * @param item WebView Item
     * @param top 是否滑动到顶部
     * @return `true` success, `false` fail
     */
    override fun pageUp(
        item: WebItem?,
        top: Boolean
    ): Boolean {
        return getWebViewImpl(item)?.pageUp(top) ?: false
    }

    /**
     * 滚动到指定坐标
     * @param item WebView Item
     * @param x 横向坐标
     * @param y 纵向坐标
     * @return `true` success, `false` fail
     */
    override fun scrollTo(
        item: WebItem?,
        x: Int,
        y: Int
    ): Boolean {
        return getWebViewImpl(item)?.run {
            scrollTo(x, y); true
        } ?: false
    }

    /**
     * 滚动指定偏移量
     * @param item WebView Item
     * @param x 横向偏移量
     * @param y 纵向偏移量
     * @return `true` success, `false` fail
     */
    override fun scrollBy(
        item: WebItem?,
        x: Int,
        y: Int
    ): Boolean {
        return getWebViewImpl(item)?.run {
            scrollBy(x, y); true
        } ?: false
    }

    /**
     * 以给定的速度开始滑动
     * @param item WebView Item
     * @param vx 横向速度
     * @param vy 纵向速度
     * @return `true` success, `false` fail
     */
    override fun flingScroll(
        item: WebItem?,
        vx: Int,
        vy: Int
    ): Boolean {
        return getWebViewImpl(item)?.run {
            flingScroll(vx, vy); true
        } ?: false
    }

    /**
     * 按指定比例缩放
     * @param item WebView Item
     * @param zoomFactor 缩放比例
     * @return `true` success, `false` fail
     */
    @Suppress("DEPRECATION")
    override fun zoomBy(
        item: WebItem?,
        zoomFactor: Float
    ): Boolean {
        return getWebViewImpl(item)?.run {
            zoomBy(zoomFactor); true
        } ?: false
    }

    /**
     * 放大
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    @Suppress("DEPRECATION")
    override fun zoomIn(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.zoomIn() ?: false
    }

    /**
     * 缩小
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    @Suppress("DEPRECATION")
    override fun zoomOut(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.zoomOut() ?: false
    }

    /**
     * 设置初始缩放比例
     * @param item WebView Item
     * @param scaleInPercent 缩放百分比
     * @return `true` success, `false` fail
     */
    override fun setInitialScale(
        item: WebItem?,
        scaleInPercent: Int
    ): Boolean {
        return getWebViewImpl(item)?.run {
            setInitialScale(scaleInPercent); true
        } ?: false
    }

    // ==========
    // = 查找操作 =
    // ==========

    /**
     * 异步查找页面内所有匹配项
     * @param item WebView Item
     * @param find 查找内容
     * @return `true` success, `false` fail
     */
    override fun findAllAsync(
        item: WebItem?,
        find: String?
    ): Boolean {
        find ?: return false
        return getWebViewImpl(item)?.run {
            findAllAsync(find); true
        } ?: false
    }

    /**
     * 跳转到下一个 ( 上一个 ) 查找匹配项
     * @param item WebView Item
     * @param forward 是否向前查找
     * @return `true` success, `false` fail
     */
    override fun findNext(
        item: WebItem?,
        forward: Boolean
    ): Boolean {
        return getWebViewImpl(item)?.run {
            findNext(forward); true
        } ?: false
    }

    // ==========
    // = 生命周期 =
    // ==========

    /**
     * 暂停 WebView ( Activity onPause )
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    override fun onPause(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            onPause(); true
        } ?: false
    }

    /**
     * 恢复 WebView ( Activity onResume )
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    override fun onResume(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            onResume(); true
        } ?: false
    }

    /**
     * 暂停所有 WebView 的布局、解析、JavaScript 定时器
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    override fun pauseTimers(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            pauseTimers(); true
        } ?: false
    }

    /**
     * 恢复所有 WebView 的布局、解析、JavaScript 定时器
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    override fun resumeTimers(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            resumeTimers(); true
        } ?: false
    }

    /**
     * 释放内存
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    @Suppress("DEPRECATION")
    override fun freeMemory(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            freeMemory(); true
        } ?: false
    }

    /**
     * 销毁处理 ( 避免 WebView 引起的内存泄漏 )
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    override fun destroy(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.let {
            it.clearHistory()
            ViewUtils.removeSelfFromParent(it)
            it.loadUrl("about:blank")
            it.stopLoading()
            it.webChromeClient = null
            it.destroy()
            item?.release()
            true
        } ?: false
    }

    // ==========
    // = 其他操作 =
    // ==========

    /**
     * 处理按键 ( 是否回退 )
     * @param item WebView Item
     * @param keyCode 按键类型
     * @param event 按键事件
     * @return `true` 拦截事件, `false` 不拦截接着处理
     */
    override fun handlerKeyDown(
        item: WebItem?,
        keyCode: Int,
        event: KeyEvent?
    ): Boolean {
        event ?: return false
        // 只处理按下事件, 避免重复触发
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            // 检查 WebView 是否可以后退
            if (canGoBack(item)) {
                return goBack(item)
            }
        }
        return false
    }

    /**
     * 关闭 WebView 硬件加速功能 ( 解决 WebView 闪烁问题 )
     * @param item WebView Item
     * @return `true` success, `false` fail
     */
    override fun setLayerTypeSoftware(item: WebItem?): Boolean {
        return setLayerType(item, View.LAYER_TYPE_SOFTWARE, null)
    }

    /**
     * 设置 WebView 硬件加速类型
     * @param item WebView Item
     * @param layerType 硬件加速类型
     * @param paint Paint
     * @return `true` success, `false` fail
     */
    override fun setLayerType(
        item: WebItem?,
        layerType: Int,
        paint: Paint?
    ): Boolean {
        return getWebViewImpl(item)?.run {
            setLayerType(layerType, paint); true
        } ?: false
    }

    /**
     * 设置 WebView 是否处于可联网状态
     * @param item WebView Item
     * @param networkUp 是否可联网
     * @return `true` success, `false` fail
     */
    override fun setNetworkAvailable(
        item: WebItem?,
        networkUp: Boolean
    ): Boolean {
        return getWebViewImpl(item)?.run {
            setNetworkAvailable(networkUp); true
        } ?: false
    }

    /**
     * 保存当前页面为 Web 归档文件
     * @param item WebView Item
     * @param filename 保存文件路径
     * @return `true` success, `false` fail
     */
    override fun saveWebArchive(
        item: WebItem?,
        filename: String?
    ): Boolean {
        filename ?: return false
        return getWebViewImpl(item)?.run {
            saveWebArchive(filename); true
        } ?: false
    }

    /**
     * 请求获取长按聚焦节点的超链接信息
     * @param item WebView Item
     * @param hrefMsg 承载结果的消息 ( url 写入 data Bundle )
     * @return `true` success, `false` fail
     */
    override fun requestFocusNodeHref(
        item: WebItem?,
        hrefMsg: Message?
    ): Boolean {
        return getWebViewImpl(item)?.run {
            requestFocusNodeHref(hrefMsg); true
        } ?: false
    }

    /**
     * 请求获取长按聚焦图片的地址信息
     * @param item WebView Item
     * @param msg 承载结果的消息 ( url 写入 data Bundle )
     * @return `true` success, `false` fail
     */
    override fun requestImageRef(
        item: WebItem?,
        msg: Message?
    ): Boolean {
        msg ?: return false
        return getWebViewImpl(item)?.run {
            requestImageRef(msg); true
        } ?: false
    }

    /**
     * 查询当前页面是否包含图片资源
     * @param item WebView Item
     * @param response 承载结果的消息 ( 是否包含图片写入 arg1 )
     * @return `true` success, `false` fail
     */
    override fun documentHasImages(
        item: WebItem?,
        response: Message?
    ): Boolean {
        response ?: return false
        return getWebViewImpl(item)?.run {
            documentHasImages(response); true
        } ?: false
    }

    /**
     * 创建打印文档适配器 ( PrintDocumentAdapter )
     * @param item WebView Item
     * @param documentName 文档名称
     * @return 打印文档适配器
     */
    override fun createPrintDocumentAdapter(
        item: WebItem?,
        documentName: String?
    ): Any? {
        documentName ?: return null
        return getWebViewImpl(item)?.createPrintDocumentAdapter(documentName)
    }

    /**
     * 设置渲染进程优先级策略 ( Android 8.0 起支持 )
     * @param item WebView Item
     * @param rendererRequestedPriority 渲染进程请求优先级
     * @param waivedWhenNotVisible 不可见时是否放弃优先级
     * @return `true` success, `false` fail
     */
    override fun setRendererPriorityPolicy(
        item: WebItem?,
        rendererRequestedPriority: Int,
        waivedWhenNotVisible: Boolean
    ): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return false
        return getWebViewImpl(item)?.run {
            setRendererPriorityPolicy(rendererRequestedPriority, waivedWhenNotVisible); true
        } ?: false
    }

    // ==============
    // = Web Message =
    // ==============

    /**
     * 向网页投递一条 Web 消息 ( HTML5 postMessage )
     * @param item WebView Item
     * @param message Web 消息 ( WebMessage, 可携带 WebMessagePort )
     * @param targetOrigin 接收方源 ( 限定可接收消息的页面 origin )
     * @return `true` success, `false` fail
     */
    override fun postWebMessage(
        item: WebItem?,
        message: Any?,
        targetOrigin: Uri?
    ): Boolean {
        val webMessage = getWebMessage(message) ?: return false
        targetOrigin ?: return false
        return getWebViewImpl(item)?.run {
            postWebMessage(webMessage, targetOrigin); true
        } ?: false
    }

    /**
     * 创建一对 Web 消息端口 ( HTML5 MessageChannel )
     * @param item WebView Item
     * @return 消息端口数组 ( WebMessagePort[] )
     */
    override fun createWebMessageChannel(item: WebItem?): Any? {
        return getWebViewImpl(item)?.createWebMessageChannel()
    }

    // ==========
    // = 全局静态 =
    // ==========

    /**
     * 设置是否开启 WebView 内容调试 ( Chrome inspect )
     * @param enabled 是否开启
     * @return `true` success, `false` fail
     */
    override fun setWebContentsDebuggingEnabled(enabled: Boolean): Boolean {
        return try {
            WebView.setWebContentsDebuggingEnabled(enabled)
            true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "setWebContentsDebuggingEnabled")
            false
        }
    }

    /**
     * 获取当前 WebView 内核包信息 ( PackageInfo, Android 8.0 起支持 )
     * @return 当前 WebView 内核包信息
     */
    override fun getCurrentWebViewPackage(): Any? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return WebView.getCurrentWebViewPackage()
        }
        return null
    }

    /**
     * 清除客户端证书选择偏好
     * @param onCleared 清除完成回调
     * @return `true` success, `false` fail
     */
    override fun clearClientCertPreferences(onCleared: Runnable?): Boolean {
        return try {
            WebView.clearClientCertPreferences(onCleared)
            true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "clearClientCertPreferences")
            false
        }
    }

    // ===================
    // = AndroidX WebKit =
    // ===================

    /**
     * 判断 AndroidX WebKit 特性是否支持
     * @param feature 特性常量 ( WebViewFeature 中定义 )
     * @return `true` yes, `false` no
     */
    @SuppressLint("WrongConstant")
    override fun isWebViewFeatureSupported(feature: String?): Boolean {
        feature ?: return false
        return try {
            WebViewFeature.isFeatureSupported(feature)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "isWebViewFeatureSupported")
            false
        }
    }

    /**
     * 在文档开始加载时注入 JS ( 需 DOCUMENT_START_SCRIPT 特性 )
     * @param item WebView Item
     * @param script 注入脚本
     * @param allowedOriginRules 允许的来源规则
     * @return 脚本句柄 ( ScriptHandler )
     */
    override fun addDocumentStartJavaScript(
        item: WebItem?,
        script: String?,
        allowedOriginRules: MutableSet<String>?
    ): Any? {
        if (script == null || allowedOriginRules == null) return null
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.DOCUMENT_START_SCRIPT)) return null
        return getWebViewImpl(item)?.let {
            WebViewCompat.addDocumentStartJavaScript(it, script, allowedOriginRules)
        }
    }

    /**
     * 添加 Web 消息监听 ( 安全 JSBridge, 需 WEB_MESSAGE_LISTENER 特性 )
     * @param item WebView Item
     * @param jsObjectName 注入到页面的对象名
     * @param allowedOriginRules 允许的来源规则
     * @param listener 消息监听 ( WebViewCompat.WebMessageListener )
     * @return `true` success, `false` fail
     */
    override fun addWebMessageListener(
        item: WebItem?,
        jsObjectName: String?,
        allowedOriginRules: MutableSet<String>?,
        listener: Any?
    ): Boolean {
        if (jsObjectName == null || allowedOriginRules == null) return false
        val messageListener = getWebMessageListener(listener) ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.WEB_MESSAGE_LISTENER)) return false
        return getWebViewImpl(item)?.run {
            WebViewCompat.addWebMessageListener(
                this, jsObjectName, allowedOriginRules, messageListener
            )
            true
        } ?: false
    }

    /**
     * 移除 Web 消息监听
     * @param item WebView Item
     * @param jsObjectName 注入到页面的对象名
     * @return `true` success, `false` fail
     */
    override fun removeWebMessageListener(
        item: WebItem?,
        jsObjectName: String?
    ): Boolean {
        jsObjectName ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.WEB_MESSAGE_LISTENER)) return false
        return getWebViewImpl(item)?.run {
            WebViewCompat.removeWebMessageListener(this, jsObjectName)
            true
        } ?: false
    }

    /**
     * 获取处理各种通知和请求事件对象 ( 兼容版, 需 GET_WEB_VIEW_CLIENT 特性 )
     * @param item WebView Item
     * @return WebViewClient
     */
    override fun getWebViewClientCompat(item: WebItem?): Any? {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.GET_WEB_VIEW_CLIENT)) return null
        return getWebViewImpl(item)?.let { WebViewCompat.getWebViewClient(it) }
    }

    /**
     * 获取辅助处理 Javascript 对话框、标题等对象 ( 兼容版, 需 GET_WEB_CHROME_CLIENT 特性 )
     * @param item WebView Item
     * @return WebChromeClient
     */
    override fun getWebChromeClientCompat(item: WebItem?): Any? {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.GET_WEB_CHROME_CLIENT)) return null
        return getWebViewImpl(item)?.let { WebViewCompat.getWebChromeClient(it) }
    }

    /**
     * 获取当前 WebView 内核包信息 ( 兼容版 )
     * @param context Context
     * @return 当前 WebView 内核包信息 ( PackageInfo )
     */
    override fun getCurrentWebViewPackageCompat(context: Context?): Any? {
        context ?: return null
        return WebViewCompat.getCurrentWebViewPackage(context)
    }

    /**
     * 获取 WebView 渲染进程 ( 需 GET_WEB_VIEW_RENDERER 特性 )
     * @param item WebView Item
     * @return 渲染进程 ( WebViewRenderProcess )
     */
    override fun getWebViewRenderProcess(item: WebItem?): Any? {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.GET_WEB_VIEW_RENDERER)) return null
        return getWebViewImpl(item)?.let { WebViewCompat.getWebViewRenderProcess(it) }
    }

    /**
     * 设置 WebView 渲染进程客户端 ( 需 WEB_VIEW_RENDERER_CLIENT_BASIC_USAGE 特性 )
     * @param item WebView Item
     * @param client 渲染进程客户端 ( WebViewRenderProcessClient )
     * @return `true` success, `false` fail
     */
    override fun setWebViewRenderProcessClient(
        item: WebItem?,
        client: Any?
    ): Boolean {
        val renderClient = getWebViewRenderProcessClient(client) ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.WEB_VIEW_RENDERER_CLIENT_BASIC_USAGE)) {
            return false
        }
        return getWebViewImpl(item)?.run {
            WebViewCompat.setWebViewRenderProcessClient(this, renderClient)
            true
        } ?: false
    }

    /**
     * 获取 WebView 渲染进程客户端
     * @param item WebView Item
     * @return 渲染进程客户端 ( WebViewRenderProcessClient )
     */
    override fun getWebViewRenderProcessClient(item: WebItem?): Any? {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.WEB_VIEW_RENDERER_CLIENT_BASIC_USAGE)) {
            return null
        }
        return getWebViewImpl(item)?.let { WebViewCompat.getWebViewRenderProcessClient(it) }
    }

    /**
     * 是否启用多进程 ( 需 MULTI_PROCESS 特性 )
     * @return `true` yes, `false` no
     */
    override fun isMultiProcessEnabled(): Boolean {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROCESS)) return false
        return WebViewCompat.isMultiProcessEnabled()
    }

    /**
     * 获取 Variations Header ( 需 GET_VARIATIONS_HEADER 特性 )
     * @return Variations Header
     */
    override fun getVariationsHeader(): String? {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.GET_VARIATIONS_HEADER)) return null
        return WebViewCompat.getVariationsHeader()
    }

    /**
     * 启动安全浏览 ( 需 START_SAFE_BROWSING 特性 )
     * @param context Context
     * @param callback 启动结果回调 ( ValueCallback )
     * @return `true` success, `false` fail
     */
    override fun startSafeBrowsing(
        context: Context?,
        callback: Any?
    ): Boolean {
        context ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.START_SAFE_BROWSING)) return false
        WebViewCompat.startSafeBrowsing(context, getValueCallbackBoolean(callback))
        return true
    }

    /**
     * 设置安全浏览白名单 ( 需 SAFE_BROWSING_ALLOWLIST 特性 )
     * @param hosts 白名单 Host
     * @param callback 设置结果回调 ( ValueCallback )
     * @return `true` success, `false` fail
     */
    override fun setSafeBrowsingAllowlist(
        hosts: MutableSet<String>?,
        callback: Any?
    ): Boolean {
        hosts ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.SAFE_BROWSING_ALLOWLIST)) return false
        WebViewCompat.setSafeBrowsingAllowlist(hosts, getValueCallbackBoolean(callback))
        return true
    }

    /**
     * 获取安全浏览隐私政策地址 ( 需 SAFE_BROWSING_PRIVACY_POLICY_URL 特性 )
     * @return 隐私政策地址 ( Uri )
     */
    override fun getSafeBrowsingPrivacyPolicyUrl(): Any? {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.SAFE_BROWSING_PRIVACY_POLICY_URL)) {
            return null
        }
        return WebViewCompat.getSafeBrowsingPrivacyPolicyUrl()
    }

    /**
     * 设置 WebView 代理 ( 需 PROXY_OVERRIDE 特性 )
     * @param proxyConfig 代理配置 ( ProxyConfig )
     * @param executor 回调执行器
     * @param listener 生效回调
     * @return `true` success, `false` fail
     */
    override fun setProxyOverride(
        proxyConfig: Any?,
        executor: Executor?,
        listener: Runnable?
    ): Boolean {
        val config = getProxyConfig(proxyConfig) ?: return false
        if (executor == null || listener == null) return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.PROXY_OVERRIDE)) return false
        return try {
            ProxyController.getInstance().setProxyOverride(
                config, executor, listener
            )
            true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "setProxyOverride")
            false
        }
    }

    /**
     * 清除 WebView 代理 ( 需 PROXY_OVERRIDE 特性 )
     * @param executor 回调执行器
     * @param listener 生效回调
     * @return `true` success, `false` fail
     */
    override fun clearProxyOverride(
        executor: Executor?,
        listener: Runnable?
    ): Boolean {
        if (executor == null || listener == null) return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.PROXY_OVERRIDE)) return false
        return try {
            ProxyController.getInstance().clearProxyOverride(executor, listener)
            true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "clearProxyOverride")
            false
        }
    }

    /**
     * 静音或取消静音 WebView ( 需 MUTE_AUDIO 特性 )
     * @param item WebView Item
     * @param mute 是否静音
     * @return `true` success, `false` fail
     */
    override fun setAudioMuted(
        item: WebItem?,
        mute: Boolean
    ): Boolean {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.MUTE_AUDIO)) return false
        return getWebViewImpl(item)?.run {
            WebViewCompat.setAudioMuted(this, mute)
            true
        } ?: false
    }

    /**
     * WebView 是否静音 ( 需 MUTE_AUDIO 特性 )
     * @param item WebView Item
     * @return `true` yes, `false` no
     */
    override fun isAudioMuted(item: WebItem?): Boolean {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.MUTE_AUDIO)) return false
        return getWebViewImpl(item)?.let {
            WebViewCompat.isAudioMuted(it)
        } ?: false
    }

    /**
     * 投递可视状态回调 ( 内容可绘制时触发, 用于规避白屏, 需 VISUAL_STATE_CALLBACK 特性 )
     * @param item WebView Item
     * @param requestId 请求 id ( 回调原样返回 )
     * @param callback 可视状态回调 ( WebViewCompat.VisualStateCallback )
     * @return `true` success, `false` fail
     */
    override fun postVisualStateCallback(
        item: WebItem?,
        requestId: Long,
        callback: Any?
    ): Boolean {
        val visualCallback = getVisualStateCallback(callback) ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.VISUAL_STATE_CALLBACK)) return false
        return getWebViewImpl(item)?.run {
            WebViewCompat.postVisualStateCallback(this, requestId, visualCallback)
            true
        } ?: false
    }

    /**
     * 设置 WebView 使用的 Profile ( 多 Profile 数据隔离, 需 MULTI_PROFILE 特性 )
     * @param item WebView Item
     * @param profileName Profile 名称
     * @return `true` success, `false` fail
     */
    override fun setWebViewProfile(
        item: WebItem?,
        profileName: String?
    ): Boolean {
        profileName ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROFILE)) return false
        return getWebViewImpl(item)?.run {
            WebViewCompat.setProfile(this, profileName)
            true
        } ?: false
    }

    /**
     * 获取 WebView 使用的 Profile ( 需 MULTI_PROFILE 特性 )
     * @param item WebView Item
     * @return Profile
     */
    override fun getWebViewProfile(item: WebItem?): Any? {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROFILE)) return null
        return getWebViewImpl(item)?.let { WebViewCompat.getProfile(it) }
    }

    /**
     * 设置 WebView 用户代理元数据 ( 生成 UA Client Hints, 需 USER_AGENT_METADATA 特性 )
     * @param item WebView Item
     * @param metadata 用户代理元数据 ( UserAgentMetadata )
     * @return `true` success, `false` fail
     */
    override fun setUserAgentMetadata(
        item: WebItem?,
        metadata: Any?
    ): Boolean {
        val userAgentMetadata = getUserAgentMetadata(metadata) ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.USER_AGENT_METADATA)) return false
        val webSettings = getSettings(item) ?: return false
        WebSettingsCompat.setUserAgentMetadata(webSettings, userAgentMetadata)
        return true
    }

    /**
     * 设置 WebView Media Integrity API 权限 ( 需 WEBVIEW_MEDIA_INTEGRITY_API_STATUS 特性 )
     * @param item WebView Item
     * @param permissionConfig 权限配置 ( WebViewMediaIntegrityApiStatusConfig )
     * @return `true` success, `false` fail
     */
    override fun setWebViewMediaIntegrityApiStatus(
        item: WebItem?,
        permissionConfig: Any?
    ): Boolean {
        val config = getWebViewMediaIntegrityApiStatusConfig(permissionConfig) ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.WEBVIEW_MEDIA_INTEGRITY_API_STATUS)) {
            return false
        }
        val webSettings = getSettings(item) ?: return false
        WebSettingsCompat.setWebViewMediaIntegrityApiStatus(webSettings, config)
        return true
    }

    /**
     * 根据名称获取或创建 Profile ( 需 MULTI_PROFILE 特性 )
     * @param name Profile 名称
     * @return Profile
     */
    override fun getOrCreateWebProfile(name: String?): Any? {
        name ?: return null
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROFILE)) return null
        return ProfileStore.getInstance().getOrCreateProfile(name)
    }

    /**
     * 根据名称获取 Profile ( 需 MULTI_PROFILE 特性 )
     * @param name Profile 名称
     * @return Profile ( 不存在返回 null )
     */
    override fun getWebProfile(name: String?): Any? {
        name ?: return null
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROFILE)) return null
        return ProfileStore.getInstance().getProfile(name)
    }

    /**
     * 根据名称删除 Profile ( 需 MULTI_PROFILE 特性 )
     * @param name Profile 名称
     * @return `true` success, `false` fail
     */
    override fun deleteWebProfile(name: String?): Boolean {
        name ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROFILE)) return false
        return try {
            ProfileStore.getInstance().deleteProfile(name)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "deleteWebProfile")
            false
        }
    }

    /**
     * 获取全部 Profile 名称 ( 需 MULTI_PROFILE 特性 )
     * @return Profile 名称列表
     */
    override fun getAllWebProfileNames(): MutableList<String> {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROFILE)) return ArrayList()
        return ProfileStore.getInstance().allProfileNames
    }

    /**
     * 设置 ServiceWorker 客户端 ( 需 SERVICE_WORKER_BASIC_USAGE 特性 )
     * @param client ServiceWorker 客户端 ( ServiceWorkerClientCompat )
     * @return `true` success, `false` fail
     */
    override fun setServiceWorkerClient(client: Any?): Boolean {
        val serviceWorkerClient = getServiceWorkerClientCompat(client) ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.SERVICE_WORKER_BASIC_USAGE)) {
            return false
        }
        ServiceWorkerControllerCompat.getInstance().setServiceWorkerClient(serviceWorkerClient)
        return true
    }

    /**
     * 设置 ServiceWorker 是否允许访问 content:// 资源 ( 需 SERVICE_WORKER_CONTENT_ACCESS 特性 )
     * @param allow 是否允许
     * @return `true` success, `false` fail
     */
    override fun setServiceWorkerAllowContentAccess(allow: Boolean): Boolean {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.SERVICE_WORKER_CONTENT_ACCESS)) {
            return false
        }
        ServiceWorkerControllerCompat.getInstance()
            .serviceWorkerWebSettings.allowContentAccess = allow
        return true
    }

    /**
     * 设置 ServiceWorker 是否允许访问文件 ( 需 SERVICE_WORKER_FILE_ACCESS 特性 )
     * @param allow 是否允许
     * @return `true` success, `false` fail
     */
    override fun setServiceWorkerAllowFileAccess(allow: Boolean): Boolean {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.SERVICE_WORKER_FILE_ACCESS)) {
            return false
        }
        ServiceWorkerControllerCompat.getInstance()
            .serviceWorkerWebSettings.allowFileAccess = allow
        return true
    }

    /**
     * 设置 ServiceWorker 是否不从网络加载资源 ( 需 SERVICE_WORKER_BLOCK_NETWORK_LOADS 特性 )
     * @param block 是否阻止
     * @return `true` success, `false` fail
     */
    override fun setServiceWorkerBlockNetworkLoads(block: Boolean): Boolean {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.SERVICE_WORKER_BLOCK_NETWORK_LOADS)) {
            return false
        }
        ServiceWorkerControllerCompat.getInstance()
            .serviceWorkerWebSettings.blockNetworkLoads = block
        return true
    }

    /**
     * 设置 ServiceWorker 缓存模式 ( 需 SERVICE_WORKER_CACHE_MODE 特性 )
     * @param mode 缓存模式
     * @return `true` success, `false` fail
     */
    override fun setServiceWorkerCacheMode(mode: Int): Boolean {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.SERVICE_WORKER_CACHE_MODE)) {
            return false
        }
        ServiceWorkerControllerCompat.getInstance().serviceWorkerWebSettings.cacheMode = mode
        return true
    }

    /**
     * 是否正在进行性能跟踪 ( 需 TRACING_CONTROLLER_BASIC_USAGE 特性 )
     * @return `true` yes, `false` no
     */
    override fun isWebViewTracing(): Boolean {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.TRACING_CONTROLLER_BASIC_USAGE)) {
            return false
        }
        return TracingController.getInstance().isTracing
    }

    /**
     * 开始性能跟踪 ( 需 TRACING_CONTROLLER_BASIC_USAGE 特性 )
     * @param config 跟踪配置 ( TracingConfig )
     * @return `true` success, `false` fail
     */
    override fun startWebViewTracing(config: Any?): Boolean {
        val tracingConfig = getTracingConfig(config) ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.TRACING_CONTROLLER_BASIC_USAGE)) {
            return false
        }
        TracingController.getInstance().start(tracingConfig)
        return true
    }

    /**
     * 停止性能跟踪并输出结果 ( 需 TRACING_CONTROLLER_BASIC_USAGE 特性 )
     * @param outputStream 结果输出流 ( OutputStream, 可为 null )
     * @param executor 回调执行器
     * @return `true` success, `false` fail
     */
    override fun stopWebViewTracing(
        outputStream: Any?,
        executor: Executor?
    ): Boolean {
        executor ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.TRACING_CONTROLLER_BASIC_USAGE)) {
            return false
        }
        return TracingController.getInstance().stop(
            getOutputStream(outputStream), executor
        )
    }

    /**
     * 获取 BackForwardCache 实时配置对象 ( 实验性, 需 BACK_FORWARD_CACHE_SETTINGS_EXPERIMENTAL_V3 特性 )
     * @param item WebView Item
     * @return BackForwardCacheSettings ( 实时对象, 修改即时生效 )
     */
    @SuppressLint("WrongConstant")
    @androidx.annotation.OptIn(
        markerClass = [WebSettingsCompat.ExperimentalBackForwardCacheSettings::class]
    )
    override fun getBackForwardCacheSettings(item: WebItem?): Any? {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.BACK_FORWARD_CACHE_SETTINGS_EXPERIMENTAL_V3)) {
            return null
        }
        val webSettings = getSettings(item) ?: return null
        return WebSettingsCompat.getBackForwardCacheSettings(webSettings)
    }

    /**
     * 设置 WebView 默认流量统计 TAG ( 需 DEFAULT_TRAFFICSTATS_TAGGING 特性 )
     * @param tag 流量统计 TAG
     * @return `true` success, `false` fail
     */
    override fun setDefaultTrafficStatsTag(tag: Int): Boolean {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.DEFAULT_TRAFFICSTATS_TAGGING)) {
            return false
        }
        WebViewCompat.setDefaultTrafficStatsTag(tag)
        return true
    }

    /**
     * 保存 WebView 状态 ( 支持限制大小、是否含前进历史, 需 SAVE_STATE 特性 )
     * @param item WebView Item
     * @param outState 状态保存 Bundle
     * @param maxSizeBytes 状态最大字节数 ( 超出则丢弃更早历史 )
     * @param includeForwardState 是否包含前进历史
     * @return `true` success, `false` fail
     */
    override fun saveState(
        item: WebItem?,
        outState: Bundle?,
        maxSizeBytes: Int,
        includeForwardState: Boolean
    ): Boolean {
        outState ?: return false
        if (maxSizeBytes < 1) return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.SAVE_STATE)) return false
        return getWebViewImpl(item)?.run {
            WebViewCompat.saveState(this, outState, maxSizeBytes, includeForwardState)
            true
        } ?: false
    }

    /**
     * 保存 WebView 状态 ( 基础版, 框架原生能力, 无特性门槛 )
     * @param item WebView Item
     * @param outState 状态保存 Bundle
     * @return `true` success, `false` fail
     */
    override fun saveState(
        item: WebItem?,
        outState: Bundle?
    ): Boolean {
        outState ?: return false
        return getWebViewImpl(item)?.let {
            it.saveState(outState) != null
        } ?: false
    }

    /**
     * 添加导航监听 ( 回调在主线程, 需 NAVIGATION_LISTENER 特性 )
     * @param item WebView Item
     * @param listener 导航监听 ( NavigationListener )
     * @return `true` success, `false` fail
     */
    override fun addNavigationListener(
        item: WebItem?,
        listener: Any?
    ): Boolean {
        val navigationListener = getNavigationListener(listener) ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.NAVIGATION_LISTENER)) return false
        return getWebViewImpl(item)?.run {
            WebViewCompat.addNavigationListener(this, navigationListener)
            true
        } ?: false
    }

    /**
     * 添加导航监听 ( 指定回调执行器, 需 NAVIGATION_LISTENER 特性 )
     * @param item WebView Item
     * @param executor 回调执行器
     * @param listener 导航监听 ( NavigationListener )
     * @return `true` success, `false` fail
     */
    override fun addNavigationListener(
        item: WebItem?,
        executor: Executor?,
        listener: Any?
    ): Boolean {
        executor ?: return false
        val navigationListener = getNavigationListener(listener) ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.NAVIGATION_LISTENER)) return false
        return getWebViewImpl(item)?.run {
            WebViewCompat.addNavigationListener(this, executor, navigationListener)
            true
        } ?: false
    }

    /**
     * 移除导航监听 ( 需 NAVIGATION_LISTENER 特性 )
     * @param item WebView Item
     * @param listener 导航监听 ( NavigationListener )
     * @return `true` success, `false` fail
     */
    override fun removeNavigationListener(
        item: WebItem?,
        listener: Any?
    ): Boolean {
        val navigationListener = getNavigationListener(listener) ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.NAVIGATION_LISTENER)) return false
        return getWebViewImpl(item)?.run {
            WebViewCompat.removeNavigationListener(this, navigationListener)
            true
        } ?: false
    }

    // ============
    // = 跨内核扩展 =
    // ============

    /**
     * 恢复 WebView 历史与状态 ( 与 saveState 配对 )
     * <pre>
     *     System / X5 接收 Bundle 状态, GeckoView 实现层可改用其 SessionState 进行恢复
     * </pre>
     * @param item WebView Item
     * @param inState 状态 Bundle
     * @return `true` success, `false` fail
     */
    override fun restoreState(
        item: WebItem?,
        inState: Bundle?
    ): Boolean {
        inState ?: return false
        return getWebViewImpl(item)?.run {
            restoreState(inState); true
        } ?: false
    }

    /**
     * 设置 WebView 激活 ( 可见 ) 状态
     * <pre>
     *     GeckoView 对应 GeckoSession.setActive ( 非激活时显著降低内存 )
     *     System / X5 对应 onResume / onPause
     * </pre>
     * @param item WebView Item
     * @param active 是否激活
     * @return `true` success, `false` fail
     */
    override fun setActive(
        item: WebItem?,
        active: Boolean
    ): Boolean {
        return getWebViewImpl(item)?.run {
            if (active) onResume() else onPause()
            true
        } ?: false
    }

    /**
     * 获取内核类型标识
     * @return 内核类型 ( 如 SystemWebView、GeckoView、X5 )
     */
    override fun getCoreType(): String {
        return "SystemWebView"
    }

    /**
     * 获取内核版本
     * @param context Context
     * @return 内核版本 ( 如 System WebView 包版本、X5 TbsVersion、GeckoView buildId )
     */
    override fun getCoreVersion(context: Context?): String? {
        context ?: return null
        return try {
            WebViewCompat.getCurrentWebViewPackage(context)?.versionName
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "getCoreVersion")
            null
        }
    }

    /**
     * 内核是否就绪可用
     * <pre>
     *     System WebView 通常恒为 true, X5 / GeckoView 需内核下载初始化完成
     * </pre>
     * @return `true` yes, `false` no
     */
    override fun isCoreReady(): Boolean {
        return true
    }

    // ==========
    // = Cookie =
    // ==========

    /**
     * 将 Cookie 设置到 WebView
     * @param url 加载的 Url
     * @param cookie 同步的 cookie
     * @return `true` success, `false` fail
     */
    override fun setCookie(
        url: String?,
        cookie: String?
    ): Boolean {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                @Suppress("DEPRECATION")
                CookieSyncManager.createInstance(DevUtils.getContext())
            }
            CookieManager.getInstance().setCookie(url, cookie)
            return true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "setCookie")
        }
        return false
    }

    /**
     * 将 Cookie 设置到 WebView ( 带设置结果回调 )
     * @param url 加载的 Url
     * @param cookie 同步的 cookie
     * @param callback 设置结果回调 ( ValueCallback )
     * @return `true` success, `false` fail
     */
    override fun setCookie(
        url: String?,
        cookie: String?,
        callback: Any?
    ): Boolean {
        try {
            CookieManager.getInstance().setCookie(
                url, cookie, getValueCallbackBoolean(callback)
            )
            return true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "setCookie - callback")
        }
        return false
    }

    /**
     * 获取指定 Url 的 Cookie
     * @param url Url
     * @return Cookie
     */
    override fun getCookie(url: String?): String? {
        try {
            return CookieManager.getInstance().getCookie(url)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "getCookie")
        }
        return null
    }

    /**
     * 设置是否接受 Cookie
     * @param accept 是否接受
     * @return `true` success, `false` fail
     */
    override fun setAcceptCookie(accept: Boolean): Boolean {
        try {
            CookieManager.getInstance().setAcceptCookie(accept)
            return true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "setAcceptCookie")
        }
        return false
    }

    /**
     * 是否接受 Cookie
     * @return `true` yes, `false` no
     */
    override fun acceptCookie(): Boolean {
        return try {
            CookieManager.getInstance().acceptCookie()
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "acceptCookie")
            false
        }
    }

    /**
     * 设置指定 WebView 是否接受第三方 Cookie
     * @param item WebView Item
     * @param accept 是否接受
     * @return `true` success, `false` fail
     */
    override fun setAcceptThirdPartyCookies(
        item: WebItem?,
        accept: Boolean
    ): Boolean {
        return getWebViewImpl(item)?.let {
            try {
                CookieManager.getInstance().setAcceptThirdPartyCookies(it, accept)
                true
            } catch (e: Exception) {
                LogPrintUtils.eTag(TAG, e, "setAcceptThirdPartyCookies")
                false
            }
        } ?: false
    }

    /**
     * 指定 WebView 是否接受第三方 Cookie
     * @param item WebView Item
     * @return `true` yes, `false` no
     */
    override fun acceptThirdPartyCookies(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.let {
            try {
                CookieManager.getInstance().acceptThirdPartyCookies(it)
            } catch (e: Exception) {
                LogPrintUtils.eTag(TAG, e, "acceptThirdPartyCookies")
                false
            }
        } ?: false
    }

    /**
     * 是否存在 Cookie
     * @return `true` yes, `false` no
     */
    override fun hasCookies(): Boolean {
        return try {
            CookieManager.getInstance().hasCookies()
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "hasCookies")
            false
        }
    }

    /**
     * 移除 Cookie ( Session、All )
     * @param callback 移除回调 ( ValueCallback )
     */
    override fun removeCookie(callback: Any?) {
        val valueCallback = getValueCallbackBoolean(callback)
        removeSessionCookie(valueCallback)
        removeAllCookie(valueCallback)
    }

    /**
     * 移除 Session Cookie
     * @param callback 移除回调 ( ValueCallback )
     * @return `true` success, `false` fail
     */
    override fun removeSessionCookie(callback: Any?): Boolean {
        try {
            val valueCallback = getValueCallbackBoolean(callback)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager.getInstance().removeSessionCookies(valueCallback)
            } else {
                @Suppress("DEPRECATION")
                CookieManager.getInstance().removeSessionCookie()
            }
            return true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "removeSessionCookie")
        }
        return false
    }

    /**
     * 移除所有的 Cookie
     * @param callback 移除回调 ( ValueCallback )
     * @return `true` success, `false` fail
     */
    override fun removeAllCookie(callback: Any?): Boolean {
        try {
            val valueCallback = getValueCallbackBoolean(callback)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager.getInstance().removeAllCookies(valueCallback)
            } else {
                @Suppress("DEPRECATION")
                CookieManager.getInstance().removeAllCookie()
            }
            return true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "removeAllCookie")
        }
        return false
    }

    /**
     * 同步 ( 刷新 ) Cookie 到本地存储
     * @return `true` success, `false` fail
     */
    override fun flushCookie(): Boolean {
        try {
            CookieManager.getInstance().flush()
            return true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "flushCookie")
        }
        return false
    }

    /**
     * 获取指定 Url 全部 Cookie 的完整属性 ( 含 Domain、Path、Expires、Secure 等, 需 GET_COOKIE_INFO 特性 )
     * @param url Url
     * @return 各条 Cookie 的完整属性字符串列表
     */
    override fun getCookieInfo(url: String?): MutableList<String> {
        url ?: return ArrayList()
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.GET_COOKIE_INFO)) return ArrayList()
        return try {
            CookieManagerCompat.getCookieInfo(CookieManager.getInstance(), url)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "getCookieInfo")
            ArrayList()
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 获取 WebView Config
     * @param item WebView Item
     * @return [WebConfig]
     */
    protected open fun getWebConfig(item: WebItem?): WebConfig {
        return item?.config() as? WebConfig ?: mConfig
    }

    /**
     * 获取 WebView
     * @param item WebView Item
     * @return [WebView]
     */
    protected open fun getWebViewImpl(item: WebItem?): WebView? {
        return item?.view()?.get() as? WebView
    }

    /**
     * 获取渲染优先级
     * @param renderPriority Render Priority Item
     * @return [WebSettings.RenderPriority]
     */
    protected open fun getRenderPriority(renderPriority: Any?): WebSettings.RenderPriority? {
        return renderPriority as? WebSettings.RenderPriority
    }

    /**
     * 获取基础布局算法
     * @param layoutAlgorithm Layout Algorithm Item
     * @return [WebSettings.LayoutAlgorithm]
     */
    protected open fun getLayoutAlgorithm(layoutAlgorithm: Any?): WebSettings.LayoutAlgorithm? {
        return layoutAlgorithm as? WebSettings.LayoutAlgorithm
    }

    /**
     * 获取 String 类型回调
     * @param callback Callback Item
     * @return [ValueCallback]
     */
    @Suppress("UNCHECKED_CAST")
    protected open fun getValueCallbackString(callback: Any?): ValueCallback<String>? {
        return callback as? ValueCallback<String>
    }

    /**
     * 获取 Boolean 类型回调
     * @param callback Callback Item
     * @return [ValueCallback]
     */
    @Suppress("UNCHECKED_CAST")
    protected open fun getValueCallbackBoolean(callback: Any?): ValueCallback<Boolean>? {
        return callback as? ValueCallback<Boolean>
    }

    /**
     * 获取处理各种通知和请求事件对象
     * @param client Client Item
     * @return [WebViewClient]
     */
    protected open fun getWebViewClient(client: Any?): WebViewClient? {
        return client as? WebViewClient
    }

    /**
     * 获取辅助处理 Javascript 对话框、标题等对象
     * @param client Client Item
     * @return [WebChromeClient]
     */
    protected open fun getWebChromeClient(client: Any?): WebChromeClient? {
        return client as? WebChromeClient
    }

    /**
     * 获取下载监听
     * @param listener Listener Item
     * @return [DownloadListener]
     */
    protected open fun getDownloadListener(listener: Any?): DownloadListener? {
        return listener as? DownloadListener
    }

    /**
     * 获取查找结果监听
     * @param listener Listener Item
     * @return [WebView.FindListener]
     */
    protected open fun getFindListener(listener: Any?): WebView.FindListener? {
        return listener as? WebView.FindListener
    }

    /**
     * 获取 Web 消息
     * @param message Message Item
     * @return [WebMessage]
     */
    protected open fun getWebMessage(message: Any?): WebMessage? {
        return message as? WebMessage
    }

    /**
     * 获取 Web 消息监听
     * @param listener Listener Item
     * @return [WebViewCompat.WebMessageListener]
     */
    protected open fun getWebMessageListener(listener: Any?): WebViewCompat.WebMessageListener? {
        return listener as? WebViewCompat.WebMessageListener
    }

    /**
     * 获取 WebView 渲染进程客户端
     * @param client Client Item
     * @return [WebViewRenderProcessClient]
     */
    protected open fun getWebViewRenderProcessClient(client: Any?): WebViewRenderProcessClient? {
        return client as? WebViewRenderProcessClient
    }

    /**
     * 获取代理配置
     * @param proxyConfig Proxy Config Item
     * @return [ProxyConfig]
     */
    protected open fun getProxyConfig(proxyConfig: Any?): ProxyConfig? {
        return proxyConfig as? ProxyConfig
    }

    /**
     * 获取可视状态回调
     * @param callback Callback Item
     * @return [WebViewCompat.VisualStateCallback]
     */
    protected open fun getVisualStateCallback(callback: Any?): WebViewCompat.VisualStateCallback? {
        return callback as? WebViewCompat.VisualStateCallback
    }

    /**
     * 获取用户代理元数据
     * @param metadata Metadata Item
     * @return [UserAgentMetadata]
     */
    protected open fun getUserAgentMetadata(metadata: Any?): UserAgentMetadata? {
        return metadata as? UserAgentMetadata
    }

    /**
     * 获取 Media Integrity API 权限配置
     * @param permissionConfig Permission Config Item
     * @return [WebViewMediaIntegrityApiStatusConfig]
     */
    protected open fun getWebViewMediaIntegrityApiStatusConfig(
        permissionConfig: Any?
    ): WebViewMediaIntegrityApiStatusConfig? {
        return permissionConfig as? WebViewMediaIntegrityApiStatusConfig
    }

    /**
     * 获取 ServiceWorker 客户端
     * @param client Client Item
     * @return [ServiceWorkerClientCompat]
     */
    protected open fun getServiceWorkerClientCompat(client: Any?): ServiceWorkerClientCompat? {
        return client as? ServiceWorkerClientCompat
    }

    /**
     * 获取性能跟踪配置
     * @param config Config Item
     * @return [TracingConfig]
     */
    protected open fun getTracingConfig(config: Any?): TracingConfig? {
        return config as? TracingConfig
    }

    /**
     * 获取输出流
     * @param outputStream Output Stream Item
     * @return [OutputStream]
     */
    protected open fun getOutputStream(outputStream: Any?): OutputStream? {
        return outputStream as? OutputStream
    }

    /**
     * 获取导航监听
     * @param listener Listener Item
     * @return [NavigationListener]
     */
    protected open fun getNavigationListener(listener: Any?): NavigationListener? {
        return listener as? NavigationListener
    }
}