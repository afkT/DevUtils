package dev.engine.core.web

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

    override fun getConfig(): WebConfig {
        return mConfig
    }

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

    override fun applyConfig(
        item: WebItem?,
        config: WebConfig?
    ): Boolean {
        val webSettings = getSettings(item) ?: return false
        config?.let { configIt ->
            configIt.javaScriptEnabled()?.let {
                webSettings.javaScriptEnabled = it
            }
            configIt.renderPriority()?.let {
                @Suppress("DEPRECATION")
                webSettings.setRenderPriority(it)
            }
            configIt.useWideViewPort()?.let {
                webSettings.useWideViewPort = it
            }
            configIt.loadWithOverviewMode()?.let {
                webSettings.loadWithOverviewMode = it
            }
            configIt.layoutAlgorithm()?.let {
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
            val cacheMode = configIt.cacheMode()
            if (cacheMode >= 0) {
                webSettings.cacheMode = cacheMode
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
    @OptIn(WebSettingsCompat.ExperimentalSpeculativeLoading::class)
    protected open fun applySpeculativeLoadingStatus(
        webSettings: WebSettings,
        status: Int
    ) {
        if (status >= 0 && WebViewFeature.isFeatureSupported(WebViewFeature.SPECULATIVE_LOADING)) {
            WebSettingsCompat.setSpeculativeLoadingStatus(webSettings, status)
        }
    }

    override fun getWebView(item: WebItem?): View? {
        return getWebViewImpl(item)
    }

    override fun isWebViewNotEmpty(item: WebItem?): Boolean {
        return getWebViewImpl(item) != null
    }

    // ==========
    // = 加载方法 =
    // ==========

    override fun loadUrl(
        item: WebItem?,
        url: String?
    ): Boolean {
        url ?: return false
        return getWebViewImpl(item)?.run {
            loadUrl(url); true
        } ?: false
    }

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

    override fun getSettings(item: WebItem?): WebSettings? {
        return getWebViewImpl(item)?.settings
    }

    override fun getUserAgentString(item: WebItem?): String? {
        return getSettings(item)?.userAgentString
    }

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

    override fun removeJavascriptInterface(
        item: WebItem?,
        interfaceName: String?
    ): Boolean {
        interfaceName ?: return false
        return getWebViewImpl(item)?.run {
            removeJavascriptInterface(interfaceName); true
        } ?: false
    }

    override fun evaluateJavascript(
        item: WebItem?,
        script: String?,
        callback: Any?
    ): Boolean {
        script ?: return false
        val webView = getWebViewImpl(item) ?: return false
        return try {
            @Suppress("UNCHECKED_CAST")
            webView.evaluateJavascript(script, callback as? ValueCallback<String>)
            true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "evaluateJavascript")
            false
        }
    }

    // ==========
    // = Client =
    // ==========

    override fun setWebViewClient(
        item: WebItem?,
        client: Any?
    ): Boolean {
        val webViewClient = client as? WebViewClient ?: return false
        return getWebViewImpl(item)?.run {
            this.webViewClient = webViewClient; true
        } ?: false
    }

    override fun getWebViewClient(item: WebItem?): WebViewClient? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return getWebViewImpl(item)?.webViewClient
        }
        return null
    }

    override fun setWebChromeClient(
        item: WebItem?,
        client: Any?
    ): Boolean {
        val webChromeClient = client as? WebChromeClient ?: return false
        return getWebViewImpl(item)?.run {
            this.webChromeClient = webChromeClient; true
        } ?: false
    }

    override fun getWebChromeClient(item: WebItem?): WebChromeClient? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return getWebViewImpl(item)?.webChromeClient
        }
        return null
    }

    override fun setDownloadListener(
        item: WebItem?,
        listener: Any?
    ): Boolean {
        val downloadListener = listener as? DownloadListener ?: return false
        return getWebViewImpl(item)?.run {
            setDownloadListener(downloadListener); true
        } ?: false
    }

    override fun setFindListener(
        item: WebItem?,
        listener: Any?
    ): Boolean {
        val findListener = listener as? WebView.FindListener ?: return false
        return getWebViewImpl(item)?.run {
            setFindListener(findListener); true
        } ?: false
    }

    // ==========
    // = 导航历史 =
    // ==========

    override fun canGoBack(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.canGoBack() ?: false
    }

    override fun goBack(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            goBack(); true
        } ?: false
    }

    override fun canGoForward(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.canGoForward() ?: false
    }

    override fun goForward(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            goForward(); true
        } ?: false
    }

    override fun canGoBackOrForward(
        item: WebItem?,
        steps: Int
    ): Boolean {
        return getWebViewImpl(item)?.canGoBackOrForward(steps) ?: false
    }

    override fun goBackOrForward(
        item: WebItem?,
        steps: Int
    ): Boolean {
        return getWebViewImpl(item)?.run {
            goBackOrForward(steps); true
        } ?: false
    }

    override fun copyBackForwardList(item: WebItem?): Any? {
        return getWebViewImpl(item)?.copyBackForwardList()
    }

    override fun reload(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            reload(); true
        } ?: false
    }

    override fun stopLoading(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            stopLoading(); true
        } ?: false
    }

    // ==========
    // = 清理操作 =
    // ==========

    override fun clearCache(
        item: WebItem?,
        includeDiskFiles: Boolean
    ): Boolean {
        return getWebViewImpl(item)?.run {
            clearCache(includeDiskFiles); true
        } ?: false
    }

    override fun clearHistory(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            clearHistory(); true
        } ?: false
    }

    override fun clearFormData(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            clearFormData(); true
        } ?: false
    }

    override fun clearMatches(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            clearMatches(); true
        } ?: false
    }

    override fun clearSslPreferences(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            clearSslPreferences(); true
        } ?: false
    }

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

    @Suppress("DEPRECATION")
    override fun getScale(item: WebItem?): Float {
        return getWebViewImpl(item)?.scale ?: 1.0F
    }

    override fun getScrollX(item: WebItem?): Int {
        return getWebViewImpl(item)?.scrollX ?: 0
    }

    override fun getScrollY(item: WebItem?): Int {
        return getWebViewImpl(item)?.scrollY ?: 0
    }

    override fun getContentHeight(item: WebItem?): Int {
        return getWebViewImpl(item)?.contentHeight ?: 0
    }

    override fun getScaleHeight(item: WebItem?): Int {
        return getScaleHeight(item, getScale(item))
    }

    override fun getScaleHeight(
        item: WebItem?,
        scale: Float
    ): Int {
        return (getContentHeight(item) * scale).toInt()
    }

    override fun getHeight(item: WebItem?): Int {
        return getWebViewImpl(item)?.height ?: 0
    }

    override fun getUrl(item: WebItem?): String? {
        return getWebViewImpl(item)?.url
    }

    override fun getOriginalUrl(item: WebItem?): String? {
        return getWebViewImpl(item)?.originalUrl
    }

    override fun getTitle(item: WebItem?): String? {
        return getWebViewImpl(item)?.title
    }

    override fun getProgress(item: WebItem?): Int {
        return getWebViewImpl(item)?.progress ?: 0
    }

    override fun getFavicon(item: WebItem?): Any? {
        return getWebViewImpl(item)?.favicon
    }

    override fun getHitTestResult(item: WebItem?): Any? {
        return getWebViewImpl(item)?.hitTestResult
    }

    override fun getCertificate(item: WebItem?): Any? {
        return getWebViewImpl(item)?.certificate
    }

    // ==========
    // = 滚动缩放 =
    // ==========

    override fun pageDown(
        item: WebItem?,
        bottom: Boolean
    ): Boolean {
        return getWebViewImpl(item)?.pageDown(bottom) ?: false
    }

    override fun pageUp(
        item: WebItem?,
        top: Boolean
    ): Boolean {
        return getWebViewImpl(item)?.pageUp(top) ?: false
    }

    override fun scrollTo(
        item: WebItem?,
        x: Int,
        y: Int
    ): Boolean {
        return getWebViewImpl(item)?.run {
            scrollTo(x, y); true
        } ?: false
    }

    override fun scrollBy(
        item: WebItem?,
        x: Int,
        y: Int
    ): Boolean {
        return getWebViewImpl(item)?.run {
            scrollBy(x, y); true
        } ?: false
    }

    override fun flingScroll(
        item: WebItem?,
        vx: Int,
        vy: Int
    ): Boolean {
        return getWebViewImpl(item)?.run {
            flingScroll(vx, vy); true
        } ?: false
    }

    @Suppress("DEPRECATION")
    override fun zoomBy(
        item: WebItem?,
        zoomFactor: Float
    ): Boolean {
        return getWebViewImpl(item)?.run {
            zoomBy(zoomFactor); true
        } ?: false
    }

    @Suppress("DEPRECATION")
    override fun zoomIn(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.zoomIn() ?: false
    }

    @Suppress("DEPRECATION")
    override fun zoomOut(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.zoomOut() ?: false
    }

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

    override fun findAllAsync(
        item: WebItem?,
        find: String?
    ): Boolean {
        find ?: return false
        return getWebViewImpl(item)?.run {
            findAllAsync(find); true
        } ?: false
    }

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

    override fun onPause(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            onPause(); true
        } ?: false
    }

    override fun onResume(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            onResume(); true
        } ?: false
    }

    override fun pauseTimers(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            pauseTimers(); true
        } ?: false
    }

    override fun resumeTimers(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            resumeTimers(); true
        } ?: false
    }

    @Suppress("DEPRECATION")
    override fun freeMemory(item: WebItem?): Boolean {
        return getWebViewImpl(item)?.run {
            freeMemory(); true
        } ?: false
    }

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
                goBack(item)
                return true
            }
        }
        return false
    }

    override fun setLayerTypeSoftware(item: WebItem?): Boolean {
        return setLayerType(item, View.LAYER_TYPE_SOFTWARE, null)
    }

    override fun setLayerType(
        item: WebItem?,
        layerType: Int,
        paint: Paint?
    ): Boolean {
        return getWebViewImpl(item)?.run {
            setLayerType(layerType, paint); true
        } ?: false
    }

    override fun setNetworkAvailable(
        item: WebItem?,
        networkUp: Boolean
    ): Boolean {
        return getWebViewImpl(item)?.run {
            setNetworkAvailable(networkUp); true
        } ?: false
    }

    override fun saveWebArchive(
        item: WebItem?,
        filename: String?
    ): Boolean {
        filename ?: return false
        return getWebViewImpl(item)?.run {
            saveWebArchive(filename); true
        } ?: false
    }

    override fun requestFocusNodeHref(
        item: WebItem?,
        hrefMsg: Message?
    ): Boolean {
        return getWebViewImpl(item)?.run {
            requestFocusNodeHref(hrefMsg); true
        } ?: false
    }

    override fun requestImageRef(
        item: WebItem?,
        msg: Message?
    ): Boolean {
        msg ?: return false
        return getWebViewImpl(item)?.run {
            requestImageRef(msg); true
        } ?: false
    }

    override fun documentHasImages(
        item: WebItem?,
        response: Message?
    ): Boolean {
        response ?: return false
        return getWebViewImpl(item)?.run {
            documentHasImages(response); true
        } ?: false
    }

    override fun createPrintDocumentAdapter(
        item: WebItem?,
        documentName: String?
    ): Any? {
        documentName ?: return null
        return getWebViewImpl(item)?.createPrintDocumentAdapter(documentName)
    }

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

    override fun postWebMessage(
        item: WebItem?,
        message: Any?,
        targetOrigin: Uri?
    ): Boolean {
        val webMessage = message as? WebMessage ?: return false
        targetOrigin ?: return false
        return getWebViewImpl(item)?.run {
            postWebMessage(webMessage, targetOrigin); true
        } ?: false
    }

    override fun createWebMessageChannel(item: WebItem?): Any? {
        return getWebViewImpl(item)?.createWebMessageChannel()
    }

    // ==========
    // = 全局静态 =
    // ==========

    override fun setWebContentsDebuggingEnabled(enabled: Boolean): Boolean {
        return try {
            WebView.setWebContentsDebuggingEnabled(enabled)
            true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "setWebContentsDebuggingEnabled")
            false
        }
    }

    override fun getCurrentWebViewPackage(): Any? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return WebView.getCurrentWebViewPackage()
        }
        return null
    }

    override fun clearClientCertPreferences(onCleared: Runnable?): Boolean {
        return try {
            WebView.clearClientCertPreferences(onCleared)
            true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "clearClientCertPreferences")
            false
        }
    }

    // =================
    // = AndroidX WebKit =
    // =================

    override fun isWebViewFeatureSupported(feature: String?): Boolean {
        feature ?: return false
        return try {
            WebViewFeature.isFeatureSupported(feature)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "isWebViewFeatureSupported")
            false
        }
    }

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

    override fun addWebMessageListener(
        item: WebItem?,
        jsObjectName: String?,
        allowedOriginRules: MutableSet<String>?,
        listener: Any?
    ): Boolean {
        if (jsObjectName == null || allowedOriginRules == null) return false
        val messageListener = listener as? WebViewCompat.WebMessageListener ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.WEB_MESSAGE_LISTENER)) return false
        return getWebViewImpl(item)?.run {
            WebViewCompat.addWebMessageListener(
                this, jsObjectName, allowedOriginRules, messageListener
            )
            true
        } ?: false
    }

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

    override fun getWebViewClientCompat(item: WebItem?): Any? {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.GET_WEB_VIEW_CLIENT)) return null
        return getWebViewImpl(item)?.let { WebViewCompat.getWebViewClient(it) }
    }

    override fun getWebChromeClientCompat(item: WebItem?): Any? {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.GET_WEB_CHROME_CLIENT)) return null
        return getWebViewImpl(item)?.let { WebViewCompat.getWebChromeClient(it) }
    }

    override fun getCurrentWebViewPackageCompat(context: Context?): Any? {
        context ?: return null
        return WebViewCompat.getCurrentWebViewPackage(context)
    }

    override fun getWebViewRenderProcess(item: WebItem?): Any? {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.GET_WEB_VIEW_RENDERER)) return null
        return getWebViewImpl(item)?.let { WebViewCompat.getWebViewRenderProcess(it) }
    }

    override fun setWebViewRenderProcessClient(
        item: WebItem?,
        client: Any?
    ): Boolean {
        val renderClient = client as? WebViewRenderProcessClient ?: return false
        if (!WebViewFeature.isFeatureSupported(
                WebViewFeature.WEB_VIEW_RENDERER_CLIENT_BASIC_USAGE
            )
        ) return false
        return getWebViewImpl(item)?.run {
            WebViewCompat.setWebViewRenderProcessClient(this, renderClient)
            true
        } ?: false
    }

    override fun getWebViewRenderProcessClient(item: WebItem?): Any? {
        if (!WebViewFeature.isFeatureSupported(
                WebViewFeature.WEB_VIEW_RENDERER_CLIENT_BASIC_USAGE
            )
        ) return null
        return getWebViewImpl(item)?.let { WebViewCompat.getWebViewRenderProcessClient(it) }
    }

    override fun isMultiProcessEnabled(): Boolean {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROCESS)) return false
        return WebViewCompat.isMultiProcessEnabled()
    }

    override fun getVariationsHeader(): String? {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.GET_VARIATIONS_HEADER)) return null
        return WebViewCompat.getVariationsHeader()
    }

    override fun startSafeBrowsing(
        context: Context?,
        callback: Any?
    ): Boolean {
        context ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.START_SAFE_BROWSING)) return false
        @Suppress("UNCHECKED_CAST")
        WebViewCompat.startSafeBrowsing(context, callback as? ValueCallback<Boolean>)
        return true
    }

    override fun setSafeBrowsingAllowlist(
        hosts: MutableSet<String>?,
        callback: Any?
    ): Boolean {
        hosts ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.SAFE_BROWSING_ALLOWLIST)) return false
        @Suppress("UNCHECKED_CAST")
        WebViewCompat.setSafeBrowsingAllowlist(hosts, callback as? ValueCallback<Boolean>)
        return true
    }

    override fun getSafeBrowsingPrivacyPolicyUrl(): Any? {
        if (!WebViewFeature.isFeatureSupported(
                WebViewFeature.SAFE_BROWSING_PRIVACY_POLICY_URL
            )
        ) return null
        return WebViewCompat.getSafeBrowsingPrivacyPolicyUrl()
    }

    override fun setProxyOverride(
        proxyConfig: Any?,
        executor: Executor?,
        listener: Runnable?
    ): Boolean {
        val config = proxyConfig as? ProxyConfig ?: return false
        if (executor == null || listener == null) return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.PROXY_OVERRIDE)) return false
        return try {
            ProxyController.getInstance().setProxyOverride(config, executor, listener)
            true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "setProxyOverride")
            false
        }
    }

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

    override fun isAudioMuted(item: WebItem?): Boolean {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.MUTE_AUDIO)) return false
        return getWebViewImpl(item)?.let { WebViewCompat.isAudioMuted(it) } ?: false
    }

    override fun postVisualStateCallback(
        item: WebItem?,
        requestId: Long,
        callback: Any?
    ): Boolean {
        val visualCallback = callback as? WebViewCompat.VisualStateCallback ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.VISUAL_STATE_CALLBACK)) return false
        return getWebViewImpl(item)?.run {
            WebViewCompat.postVisualStateCallback(this, requestId, visualCallback)
            true
        } ?: false
    }

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

    override fun getWebViewProfile(item: WebItem?): Any? {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROFILE)) return null
        return getWebViewImpl(item)?.let { WebViewCompat.getProfile(it) }
    }

    override fun setUserAgentMetadata(
        item: WebItem?,
        metadata: Any?
    ): Boolean {
        val userAgentMetadata = metadata as? UserAgentMetadata ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.USER_AGENT_METADATA)) return false
        val webSettings = getSettings(item) ?: return false
        WebSettingsCompat.setUserAgentMetadata(webSettings, userAgentMetadata)
        return true
    }

    override fun setWebViewMediaIntegrityApiStatus(
        item: WebItem?,
        permissionConfig: Any?
    ): Boolean {
        val config = permissionConfig as? WebViewMediaIntegrityApiStatusConfig ?: return false
        if (!WebViewFeature.isFeatureSupported(
                WebViewFeature.WEBVIEW_MEDIA_INTEGRITY_API_STATUS
            )
        ) return false
        val webSettings = getSettings(item) ?: return false
        WebSettingsCompat.setWebViewMediaIntegrityApiStatus(webSettings, config)
        return true
    }

    override fun getOrCreateWebProfile(name: String?): Any? {
        name ?: return null
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROFILE)) return null
        return ProfileStore.getInstance().getOrCreateProfile(name)
    }

    override fun getWebProfile(name: String?): Any? {
        name ?: return null
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROFILE)) return null
        return ProfileStore.getInstance().getProfile(name)
    }

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

    override fun getAllWebProfileNames(): MutableList<String> {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.MULTI_PROFILE)) return ArrayList()
        return ProfileStore.getInstance().allProfileNames
    }

    override fun setServiceWorkerClient(client: Any?): Boolean {
        val serviceWorkerClient = client as? ServiceWorkerClientCompat ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.SERVICE_WORKER_BASIC_USAGE)) {
            return false
        }
        ServiceWorkerControllerCompat.getInstance().setServiceWorkerClient(serviceWorkerClient)
        return true
    }

    override fun setServiceWorkerAllowContentAccess(allow: Boolean): Boolean {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.SERVICE_WORKER_CONTENT_ACCESS)) {
            return false
        }
        ServiceWorkerControllerCompat.getInstance()
            .serviceWorkerWebSettings.allowContentAccess = allow
        return true
    }

    override fun setServiceWorkerAllowFileAccess(allow: Boolean): Boolean {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.SERVICE_WORKER_FILE_ACCESS)) {
            return false
        }
        ServiceWorkerControllerCompat.getInstance()
            .serviceWorkerWebSettings.allowFileAccess = allow
        return true
    }

    override fun setServiceWorkerBlockNetworkLoads(block: Boolean): Boolean {
        if (!WebViewFeature.isFeatureSupported(
                WebViewFeature.SERVICE_WORKER_BLOCK_NETWORK_LOADS
            )
        ) return false
        ServiceWorkerControllerCompat.getInstance()
            .serviceWorkerWebSettings.blockNetworkLoads = block
        return true
    }

    override fun setServiceWorkerCacheMode(mode: Int): Boolean {
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.SERVICE_WORKER_CACHE_MODE)) {
            return false
        }
        ServiceWorkerControllerCompat.getInstance()
            .serviceWorkerWebSettings.cacheMode = mode
        return true
    }

    override fun isWebViewTracing(): Boolean {
        if (!WebViewFeature.isFeatureSupported(
                WebViewFeature.TRACING_CONTROLLER_BASIC_USAGE
            )
        ) return false
        return TracingController.getInstance().isTracing
    }

    override fun startWebViewTracing(config: Any?): Boolean {
        val tracingConfig = config as? TracingConfig ?: return false
        if (!WebViewFeature.isFeatureSupported(
                WebViewFeature.TRACING_CONTROLLER_BASIC_USAGE
            )
        ) return false
        TracingController.getInstance().start(tracingConfig)
        return true
    }

    override fun stopWebViewTracing(
        outputStream: Any?,
        executor: Executor?
    ): Boolean {
        executor ?: return false
        if (!WebViewFeature.isFeatureSupported(
                WebViewFeature.TRACING_CONTROLLER_BASIC_USAGE
            )
        ) return false
        return TracingController.getInstance().stop(outputStream as? OutputStream, executor)
    }

    @OptIn(WebSettingsCompat.ExperimentalBackForwardCacheSettings::class)
    override fun getBackForwardCacheSettings(item: WebItem?): Any? {
        if (!WebViewFeature.isFeatureSupported(
                WebViewFeature.BACK_FORWARD_CACHE_SETTINGS_EXPERIMENTAL_V3
            )
        ) return null
        val webSettings = getSettings(item) ?: return null
        return WebSettingsCompat.getBackForwardCacheSettings(webSettings)
    }

    override fun setDefaultTrafficStatsTag(tag: Int): Boolean {
        if (!WebViewFeature.isFeatureSupported(
                WebViewFeature.DEFAULT_TRAFFICSTATS_TAGGING
            )
        ) return false
        WebViewCompat.setDefaultTrafficStatsTag(tag)
        return true
    }

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

    override fun saveState(
        item: WebItem?,
        outState: Bundle?
    ): Boolean {
        outState ?: return false
        return getWebViewImpl(item)?.let { it.saveState(outState) != null } ?: false
    }

    override fun addNavigationListener(
        item: WebItem?,
        listener: Any?
    ): Boolean {
        val navigationListener = listener as? NavigationListener ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.NAVIGATION_LISTENER)) return false
        return getWebViewImpl(item)?.run {
            WebViewCompat.addNavigationListener(this, navigationListener)
            true
        } ?: false
    }

    override fun addNavigationListener(
        item: WebItem?,
        executor: Executor?,
        listener: Any?
    ): Boolean {
        executor ?: return false
        val navigationListener = listener as? NavigationListener ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.NAVIGATION_LISTENER)) return false
        return getWebViewImpl(item)?.run {
            WebViewCompat.addNavigationListener(this, executor, navigationListener)
            true
        } ?: false
    }

    override fun removeNavigationListener(
        item: WebItem?,
        listener: Any?
    ): Boolean {
        val navigationListener = listener as? NavigationListener ?: return false
        if (!WebViewFeature.isFeatureSupported(WebViewFeature.NAVIGATION_LISTENER)) return false
        return getWebViewImpl(item)?.run {
            WebViewCompat.removeNavigationListener(this, navigationListener)
            true
        } ?: false
    }

    // ============
    // = 跨内核扩展 =
    // ============

    override fun restoreState(
        item: WebItem?,
        inState: Bundle?
    ): Boolean {
        inState ?: return false
        return getWebViewImpl(item)?.run {
            restoreState(inState); true
        } ?: false
    }

    override fun setActive(
        item: WebItem?,
        active: Boolean
    ): Boolean {
        return getWebViewImpl(item)?.run {
            if (active) onResume() else onPause()
            true
        } ?: false
    }

    override fun getCoreType(): String {
        return "SystemWebView"
    }

    override fun getCoreVersion(context: Context?): String? {
        context ?: return null
        return try {
            WebViewCompat.getCurrentWebViewPackage(context)?.versionName
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "getCoreVersion")
            null
        }
    }

    override fun isCoreReady(): Boolean {
        return true
    }

    // ==========
    // = Cookie =
    // ==========

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

    override fun setCookie(
        url: String?,
        cookie: String?,
        callback: Any?
    ): Boolean {
        try {
            @Suppress("UNCHECKED_CAST")
            CookieManager.getInstance().setCookie(url, cookie, callback as? ValueCallback<Boolean>)
            return true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "setCookie - callback")
        }
        return false
    }

    override fun getCookie(url: String?): String? {
        try {
            return CookieManager.getInstance().getCookie(url)
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "getCookie")
        }
        return null
    }

    override fun setAcceptCookie(accept: Boolean): Boolean {
        try {
            CookieManager.getInstance().setAcceptCookie(accept)
            return true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "setAcceptCookie")
        }
        return false
    }

    override fun acceptCookie(): Boolean {
        return try {
            CookieManager.getInstance().acceptCookie()
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "acceptCookie")
            false
        }
    }

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

    override fun hasCookies(): Boolean {
        return try {
            CookieManager.getInstance().hasCookies()
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "hasCookies")
            false
        }
    }

    override fun removeCookie(callback: Any?) {
        @Suppress("UNCHECKED_CAST")
        val valueCallback = callback as? ValueCallback<Boolean>
        removeSessionCookie(valueCallback)
        removeAllCookie(valueCallback)
    }

    override fun removeSessionCookie(callback: Any?): Boolean {
        try {
            @Suppress("UNCHECKED_CAST")
            val valueCallback = callback as? ValueCallback<Boolean>
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

    override fun removeAllCookie(callback: Any?): Boolean {
        try {
            @Suppress("UNCHECKED_CAST")
            val valueCallback = callback as? ValueCallback<Boolean>
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

    override fun flushCookie(): Boolean {
        try {
            CookieManager.getInstance().flush()
            return true
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "flushCookie")
        }
        return false
    }

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
}