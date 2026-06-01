package dev.engine.extensions.web

import android.content.Context
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.view.KeyEvent
import android.view.View
import dev.engine.DevEngine
import dev.engine.web.IWebEngine
import java.util.concurrent.Executor

// ====================================
// = IWebEngine<EngineConfig, EngineItem> =
// ====================================

/**
 * 通过 Key 获取 WebView Engine
 * @param engine String?
 * @return IWebEngine<EngineConfig, EngineItem>
 * 内部做了处理如果匹配不到则返回默认 WebView Engine
 */
fun String?.getWebEngine(): IWebEngine<in IWebEngine.EngineConfig, in IWebEngine.EngineItem>? {
    DevEngine.getWeb(this)?.let { value ->
        return value
    }
    return DevEngine.getWeb()
}

// =============
// = 对外公开方法 =
// =============

fun web_getConfig(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.config
}

fun <Item : IWebEngine.EngineItem> Item?.web_initialize(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.initialize(this) ?: false
}

fun <Config : IWebEngine.EngineConfig, Item : IWebEngine.EngineItem> Item?.web_applyConfig(
    engine: String? = null,
    config: Config?
): Boolean {
    return engine.getWebEngine()?.applyConfig(this, config) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_getWebView(
    engine: String? = null
): View? {
    return engine.getWebEngine()?.getWebView(this)
}

fun <Item : IWebEngine.EngineItem> Item?.web_isWebViewNotEmpty(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.isWebViewNotEmpty(this) ?: false
}

// ==========
// = 加载方法 =
// ==========

fun <Item : IWebEngine.EngineItem> Item?.web_loadUrl(
    engine: String? = null,
    url: String?
): Boolean {
    return engine.getWebEngine()?.loadUrl(this, url) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_loadUrl(
    engine: String? = null,
    url: String?,
    additionalHttpHeaders: MutableMap<String, String>?
): Boolean {
    return engine.getWebEngine()?.loadUrl(this, url, additionalHttpHeaders) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_loadData(
    engine: String? = null,
    data: String?,
    mimeType: String?,
    encoding: String?
): Boolean {
    return engine.getWebEngine()?.loadData(this, data, mimeType, encoding) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_loadDataWithBaseURL(
    engine: String? = null,
    baseUrl: String?,
    data: String?,
    mimeType: String?,
    encoding: String?,
    historyUrl: String?
): Boolean {
    return engine.getWebEngine()?.loadDataWithBaseURL(
        this, baseUrl, data, mimeType, encoding, historyUrl
    ) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_postUrl(
    engine: String? = null,
    url: String?,
    postData: ByteArray?
): Boolean {
    return engine.getWebEngine()?.postUrl(this, url, postData) ?: false
}

// ==========
// = 配置相关 =
// ==========

fun <Item : IWebEngine.EngineItem> Item?.web_getSettings(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getSettings(this)
}

fun <Item : IWebEngine.EngineItem> Item?.web_getUserAgentString(
    engine: String? = null
): String? {
    return engine.getWebEngine()?.getUserAgentString(this)
}

fun <Item : IWebEngine.EngineItem> Item?.web_setUserAgentString(
    engine: String? = null,
    ua: String?
): Boolean {
    return engine.getWebEngine()?.setUserAgentString(this, ua) ?: false
}

// ==========
// = JS 交互 =
// ==========

fun <Item : IWebEngine.EngineItem> Item?.web_addJavascriptInterface(
    engine: String? = null,
    obj: Any?,
    interfaceName: String?
): Boolean {
    return engine.getWebEngine()?.addJavascriptInterface(this, obj, interfaceName) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_removeJavascriptInterface(
    engine: String? = null,
    interfaceName: String?
): Boolean {
    return engine.getWebEngine()?.removeJavascriptInterface(this, interfaceName) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_evaluateJavascript(
    engine: String? = null,
    script: String?,
    callback: Any?
): Boolean {
    return engine.getWebEngine()?.evaluateJavascript(this, script, callback) ?: false
}

// ==========
// = Client =
// ==========

fun <Item : IWebEngine.EngineItem> Item?.web_setWebViewClient(
    engine: String? = null,
    client: Any?
): Boolean {
    return engine.getWebEngine()?.setWebViewClient(this, client) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_getWebViewClient(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getWebViewClient(this)
}

fun <Item : IWebEngine.EngineItem> Item?.web_setWebChromeClient(
    engine: String? = null,
    client: Any?
): Boolean {
    return engine.getWebEngine()?.setWebChromeClient(this, client) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_getWebChromeClient(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getWebChromeClient(this)
}

fun <Item : IWebEngine.EngineItem> Item?.web_setDownloadListener(
    engine: String? = null,
    listener: Any?
): Boolean {
    return engine.getWebEngine()?.setDownloadListener(this, listener) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_setFindListener(
    engine: String? = null,
    listener: Any?
): Boolean {
    return engine.getWebEngine()?.setFindListener(this, listener) ?: false
}

// ==========
// = 导航历史 =
// ==========

fun <Item : IWebEngine.EngineItem> Item?.web_canGoBack(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.canGoBack(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_goBack(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.goBack(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_canGoForward(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.canGoForward(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_goForward(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.goForward(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_canGoBackOrForward(
    engine: String? = null,
    steps: Int
): Boolean {
    return engine.getWebEngine()?.canGoBackOrForward(this, steps) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_goBackOrForward(
    engine: String? = null,
    steps: Int
): Boolean {
    return engine.getWebEngine()?.goBackOrForward(this, steps) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_copyBackForwardList(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.copyBackForwardList(this)
}

fun <Item : IWebEngine.EngineItem> Item?.web_reload(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.reload(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_stopLoading(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.stopLoading(this) ?: false
}

// ==========
// = 清理操作 =
// ==========

fun <Item : IWebEngine.EngineItem> Item?.web_clearCache(
    engine: String? = null,
    includeDiskFiles: Boolean
): Boolean {
    return engine.getWebEngine()?.clearCache(this, includeDiskFiles) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_clearHistory(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.clearHistory(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_clearFormData(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.clearFormData(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_clearMatches(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.clearMatches(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_clearSslPreferences(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.clearSslPreferences(this) ?: false
}

// ==========
// = 状态查询 =
// ==========

fun <Item : IWebEngine.EngineItem> Item?.web_getScale(
    engine: String? = null
): Float {
    return engine.getWebEngine()?.getScale(this) ?: 1.0F
}

fun <Item : IWebEngine.EngineItem> Item?.web_getScrollX(
    engine: String? = null
): Int {
    return engine.getWebEngine()?.getScrollX(this) ?: 0
}

fun <Item : IWebEngine.EngineItem> Item?.web_getScrollY(
    engine: String? = null
): Int {
    return engine.getWebEngine()?.getScrollY(this) ?: 0
}

fun <Item : IWebEngine.EngineItem> Item?.web_getContentHeight(
    engine: String? = null
): Int {
    return engine.getWebEngine()?.getContentHeight(this) ?: 0
}

fun <Item : IWebEngine.EngineItem> Item?.web_getScaleHeight(
    engine: String? = null
): Int {
    return engine.getWebEngine()?.getScaleHeight(this) ?: 0
}

fun <Item : IWebEngine.EngineItem> Item?.web_getScaleHeight(
    engine: String? = null,
    scale: Float
): Int {
    return engine.getWebEngine()?.getScaleHeight(this, scale) ?: 0
}

fun <Item : IWebEngine.EngineItem> Item?.web_getHeight(
    engine: String? = null
): Int {
    return engine.getWebEngine()?.getHeight(this) ?: 0
}

fun <Item : IWebEngine.EngineItem> Item?.web_getUrl(
    engine: String? = null
): String? {
    return engine.getWebEngine()?.getUrl(this)
}

fun <Item : IWebEngine.EngineItem> Item?.web_getOriginalUrl(
    engine: String? = null
): String? {
    return engine.getWebEngine()?.getOriginalUrl(this)
}

fun <Item : IWebEngine.EngineItem> Item?.web_getTitle(
    engine: String? = null
): String? {
    return engine.getWebEngine()?.getTitle(this)
}

fun <Item : IWebEngine.EngineItem> Item?.web_getProgress(
    engine: String? = null
): Int {
    return engine.getWebEngine()?.getProgress(this) ?: 0
}

fun <Item : IWebEngine.EngineItem> Item?.web_getFavicon(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getFavicon(this)
}

fun <Item : IWebEngine.EngineItem> Item?.web_getHitTestResult(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getHitTestResult(this)
}

fun <Item : IWebEngine.EngineItem> Item?.web_getCertificate(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getCertificate(this)
}

// ==========
// = 滚动缩放 =
// ==========

fun <Item : IWebEngine.EngineItem> Item?.web_pageDown(
    engine: String? = null,
    bottom: Boolean
): Boolean {
    return engine.getWebEngine()?.pageDown(this, bottom) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_pageUp(
    engine: String? = null,
    top: Boolean
): Boolean {
    return engine.getWebEngine()?.pageUp(this, top) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_scrollTo(
    engine: String? = null,
    x: Int,
    y: Int
): Boolean {
    return engine.getWebEngine()?.scrollTo(this, x, y) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_scrollBy(
    engine: String? = null,
    x: Int,
    y: Int
): Boolean {
    return engine.getWebEngine()?.scrollBy(this, x, y) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_flingScroll(
    engine: String? = null,
    vx: Int,
    vy: Int
): Boolean {
    return engine.getWebEngine()?.flingScroll(this, vx, vy) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_zoomBy(
    engine: String? = null,
    zoomFactor: Float
): Boolean {
    return engine.getWebEngine()?.zoomBy(this, zoomFactor) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_zoomIn(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.zoomIn(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_zoomOut(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.zoomOut(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_setInitialScale(
    engine: String? = null,
    scaleInPercent: Int
): Boolean {
    return engine.getWebEngine()?.setInitialScale(this, scaleInPercent) ?: false
}

// ==========
// = 查找操作 =
// ==========

fun <Item : IWebEngine.EngineItem> Item?.web_findAllAsync(
    engine: String? = null,
    find: String?
): Boolean {
    return engine.getWebEngine()?.findAllAsync(this, find) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_findNext(
    engine: String? = null,
    forward: Boolean
): Boolean {
    return engine.getWebEngine()?.findNext(this, forward) ?: false
}

// ==========
// = 生命周期 =
// ==========

fun <Item : IWebEngine.EngineItem> Item?.web_onPause(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.onPause(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_onResume(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.onResume(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_pauseTimers(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.pauseTimers(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_resumeTimers(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.resumeTimers(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_freeMemory(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.freeMemory(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_destroy(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.destroy(this) ?: false
}

// ==========
// = 其他操作 =
// ==========

fun <Item : IWebEngine.EngineItem> Item?.web_handlerKeyDown(
    engine: String? = null,
    keyCode: Int,
    event: KeyEvent?
): Boolean {
    return engine.getWebEngine()?.handlerKeyDown(this, keyCode, event) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_setLayerTypeSoftware(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.setLayerTypeSoftware(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_setLayerType(
    engine: String? = null,
    layerType: Int,
    paint: Paint?
): Boolean {
    return engine.getWebEngine()?.setLayerType(this, layerType, paint) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_setNetworkAvailable(
    engine: String? = null,
    networkUp: Boolean
): Boolean {
    return engine.getWebEngine()?.setNetworkAvailable(this, networkUp) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_saveWebArchive(
    engine: String? = null,
    filename: String?
): Boolean {
    return engine.getWebEngine()?.saveWebArchive(this, filename) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_requestFocusNodeHref(
    engine: String? = null,
    hrefMsg: Message?
): Boolean {
    return engine.getWebEngine()?.requestFocusNodeHref(this, hrefMsg) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_requestImageRef(
    engine: String? = null,
    msg: Message?
): Boolean {
    return engine.getWebEngine()?.requestImageRef(this, msg) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_documentHasImages(
    engine: String? = null,
    response: Message?
): Boolean {
    return engine.getWebEngine()?.documentHasImages(this, response) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_createPrintDocumentAdapter(
    engine: String? = null,
    documentName: String?
): Any? {
    return engine.getWebEngine()?.createPrintDocumentAdapter(this, documentName)
}

fun <Item : IWebEngine.EngineItem> Item?.web_setRendererPriorityPolicy(
    engine: String? = null,
    rendererRequestedPriority: Int,
    waivedWhenNotVisible: Boolean
): Boolean {
    return engine.getWebEngine()?.setRendererPriorityPolicy(
        this, rendererRequestedPriority, waivedWhenNotVisible
    ) ?: false
}

// ==============
// = Web Message =
// ==============

fun <Item : IWebEngine.EngineItem> Item?.web_postWebMessage(
    engine: String? = null,
    message: Any?,
    targetOrigin: Uri?
): Boolean {
    return engine.getWebEngine()?.postWebMessage(this, message, targetOrigin) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_createWebMessageChannel(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.createWebMessageChannel(this)
}

// ==========
// = 全局静态 =
// ==========

fun web_setWebContentsDebuggingEnabled(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getWebEngine()?.setWebContentsDebuggingEnabled(enabled) ?: false
}

fun web_getCurrentWebViewPackage(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getCurrentWebViewPackage()
}

fun web_clearClientCertPreferences(
    engine: String? = null,
    onCleared: Runnable?
): Boolean {
    return engine.getWebEngine()?.clearClientCertPreferences(onCleared) ?: false
}

// =================
// = AndroidX WebKit =
// =================

fun web_isWebViewFeatureSupported(
    engine: String? = null,
    feature: String?
): Boolean {
    return engine.getWebEngine()?.isWebViewFeatureSupported(feature) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_addDocumentStartJavaScript(
    engine: String? = null,
    script: String?,
    allowedOriginRules: MutableSet<String>?
): Any? {
    return engine.getWebEngine()?.addDocumentStartJavaScript(this, script, allowedOriginRules)
}

fun <Item : IWebEngine.EngineItem> Item?.web_addWebMessageListener(
    engine: String? = null,
    jsObjectName: String?,
    allowedOriginRules: MutableSet<String>?,
    listener: Any?
): Boolean {
    return engine.getWebEngine()?.addWebMessageListener(
        this, jsObjectName, allowedOriginRules, listener
    ) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_removeWebMessageListener(
    engine: String? = null,
    jsObjectName: String?
): Boolean {
    return engine.getWebEngine()?.removeWebMessageListener(this, jsObjectName) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_getWebViewClientCompat(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getWebViewClientCompat(this)
}

fun <Item : IWebEngine.EngineItem> Item?.web_getWebChromeClientCompat(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getWebChromeClientCompat(this)
}

fun web_getCurrentWebViewPackageCompat(
    engine: String? = null,
    context: Context?
): Any? {
    return engine.getWebEngine()?.getCurrentWebViewPackageCompat(context)
}

fun <Item : IWebEngine.EngineItem> Item?.web_getWebViewRenderProcess(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getWebViewRenderProcess(this)
}

fun <Item : IWebEngine.EngineItem> Item?.web_setWebViewRenderProcessClient(
    engine: String? = null,
    client: Any?
): Boolean {
    return engine.getWebEngine()?.setWebViewRenderProcessClient(this, client) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_getWebViewRenderProcessClient(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getWebViewRenderProcessClient(this)
}

fun web_isMultiProcessEnabled(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.isMultiProcessEnabled() ?: false
}

fun web_getVariationsHeader(
    engine: String? = null
): String? {
    return engine.getWebEngine()?.getVariationsHeader()
}

fun web_startSafeBrowsing(
    engine: String? = null,
    context: Context?,
    callback: Any?
): Boolean {
    return engine.getWebEngine()?.startSafeBrowsing(context, callback) ?: false
}

fun web_setSafeBrowsingAllowlist(
    engine: String? = null,
    hosts: MutableSet<String>?,
    callback: Any?
): Boolean {
    return engine.getWebEngine()?.setSafeBrowsingAllowlist(hosts, callback) ?: false
}

fun web_getSafeBrowsingPrivacyPolicyUrl(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getSafeBrowsingPrivacyPolicyUrl()
}

fun web_setProxyOverride(
    engine: String? = null,
    proxyConfig: Any?,
    executor: Executor?,
    listener: Runnable?
): Boolean {
    return engine.getWebEngine()?.setProxyOverride(proxyConfig, executor, listener) ?: false
}

fun web_clearProxyOverride(
    engine: String? = null,
    executor: Executor?,
    listener: Runnable?
): Boolean {
    return engine.getWebEngine()?.clearProxyOverride(executor, listener) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_setAudioMuted(
    engine: String? = null,
    mute: Boolean
): Boolean {
    return engine.getWebEngine()?.setAudioMuted(this, mute) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_isAudioMuted(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.isAudioMuted(this) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_postVisualStateCallback(
    engine: String? = null,
    requestId: Long,
    callback: Any?
): Boolean {
    return engine.getWebEngine()?.postVisualStateCallback(this, requestId, callback) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_setWebViewProfile(
    engine: String? = null,
    profileName: String?
): Boolean {
    return engine.getWebEngine()?.setWebViewProfile(this, profileName) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_getWebViewProfile(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getWebViewProfile(this)
}

fun <Item : IWebEngine.EngineItem> Item?.web_setUserAgentMetadata(
    engine: String? = null,
    metadata: Any?
): Boolean {
    return engine.getWebEngine()?.setUserAgentMetadata(this, metadata) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_setWebViewMediaIntegrityApiStatus(
    engine: String? = null,
    permissionConfig: Any?
): Boolean {
    return engine.getWebEngine()?.setWebViewMediaIntegrityApiStatus(this, permissionConfig) ?: false
}

fun web_getOrCreateWebProfile(
    engine: String? = null,
    name: String?
): Any? {
    return engine.getWebEngine()?.getOrCreateWebProfile(name)
}

fun web_getWebProfile(
    engine: String? = null,
    name: String?
): Any? {
    return engine.getWebEngine()?.getWebProfile(name)
}

fun web_deleteWebProfile(
    engine: String? = null,
    name: String?
): Boolean {
    return engine.getWebEngine()?.deleteWebProfile(name) ?: false
}

fun web_getAllWebProfileNames(
    engine: String? = null
): MutableList<String>? {
    return engine.getWebEngine()?.getAllWebProfileNames()
}

fun web_setServiceWorkerClient(
    engine: String? = null,
    client: Any?
): Boolean {
    return engine.getWebEngine()?.setServiceWorkerClient(client) ?: false
}

fun web_setServiceWorkerAllowContentAccess(
    engine: String? = null,
    allow: Boolean
): Boolean {
    return engine.getWebEngine()?.setServiceWorkerAllowContentAccess(allow) ?: false
}

fun web_setServiceWorkerAllowFileAccess(
    engine: String? = null,
    allow: Boolean
): Boolean {
    return engine.getWebEngine()?.setServiceWorkerAllowFileAccess(allow) ?: false
}

fun web_setServiceWorkerBlockNetworkLoads(
    engine: String? = null,
    block: Boolean
): Boolean {
    return engine.getWebEngine()?.setServiceWorkerBlockNetworkLoads(block) ?: false
}

fun web_setServiceWorkerCacheMode(
    engine: String? = null,
    mode: Int
): Boolean {
    return engine.getWebEngine()?.setServiceWorkerCacheMode(mode) ?: false
}

fun web_isWebViewTracing(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.isWebViewTracing() ?: false
}

fun web_startWebViewTracing(
    engine: String? = null,
    config: Any?
): Boolean {
    return engine.getWebEngine()?.startWebViewTracing(config) ?: false
}

fun web_stopWebViewTracing(
    engine: String? = null,
    outputStream: Any?,
    executor: Executor?
): Boolean {
    return engine.getWebEngine()?.stopWebViewTracing(outputStream, executor) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_getBackForwardCacheSettings(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getBackForwardCacheSettings(this)
}

fun web_setDefaultTrafficStatsTag(
    engine: String? = null,
    tag: Int
): Boolean {
    return engine.getWebEngine()?.setDefaultTrafficStatsTag(tag) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_saveState(
    engine: String? = null,
    outState: Bundle?,
    maxSizeBytes: Int,
    includeForwardState: Boolean
): Boolean {
    return engine.getWebEngine()?.saveState(this, outState, maxSizeBytes, includeForwardState)
        ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_addNavigationListener(
    engine: String? = null,
    listener: Any?
): Boolean {
    return engine.getWebEngine()?.addNavigationListener(this, listener) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_addNavigationListener(
    engine: String? = null,
    executor: Executor?,
    listener: Any?
): Boolean {
    return engine.getWebEngine()?.addNavigationListener(this, executor, listener) ?: false
}

fun <Item : IWebEngine.EngineItem> Item?.web_removeNavigationListener(
    engine: String? = null,
    listener: Any?
): Boolean {
    return engine.getWebEngine()?.removeNavigationListener(this, listener) ?: false
}

// ==========
// = Cookie =
// ==========

fun web_setCookie(
    engine: String? = null,
    url: String?,
    cookie: String?
): Boolean {
    return engine.getWebEngine()?.setCookie(url, cookie) ?: false
}

fun web_getCookie(
    engine: String? = null,
    url: String?
): String? {
    return engine.getWebEngine()?.getCookie(url)
}

fun web_removeCookie(
    engine: String? = null,
    callback: Any?
) {
    engine.getWebEngine()?.removeCookie(callback)
}

fun web_removeSessionCookie(
    engine: String? = null,
    callback: Any?
): Boolean {
    return engine.getWebEngine()?.removeSessionCookie(callback) ?: false
}

fun web_removeAllCookie(
    engine: String? = null,
    callback: Any?
): Boolean {
    return engine.getWebEngine()?.removeAllCookie(callback) ?: false
}

fun web_getCookieInfo(
    engine: String? = null,
    url: String?
): MutableList<String>? {
    return engine.getWebEngine()?.getCookieInfo(url)
}

fun web_flushCookie(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.flushCookie() ?: false
}
