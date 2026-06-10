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

// ========================================
// = IWebEngine<EngineConfig, EngineItem> =
// ========================================

/**
 * 通过 Key 获取 WebView Engine
 * @receiver String?
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

/**
 * 获取 WebView Engine Config
 * @param engine String?
 * @return WebView Config
 */
fun web_getConfig(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.config
}

/**
 * 初始化 WebView ( 应用配置、设置 Client、监听、JS 交互对象 )
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_initialize(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.initialize(this) ?: false
}

/**
 * 应用 WebView Config
 * @receiver WebView Item
 * @param engine String?
 * @param config WebView Config
 * @return `true` success, `false` fail
 */
fun <Config : IWebEngine.EngineConfig, Item : IWebEngine.EngineItem> Item?.web_applyConfig(
    engine: String? = null,
    config: Config?
): Boolean {
    return engine.getWebEngine()?.applyConfig(this, config) ?: false
}

/**
 * 获取 WebView
 * @receiver WebView Item
 * @param engine String?
 * @return WebView
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getWebView(
    engine: String? = null
): View? {
    return engine.getWebEngine()?.getWebView(this)
}

/**
 * WebView 是否不为 null
 * @receiver WebView Item
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun <Item : IWebEngine.EngineItem> Item?.web_isWebViewNotEmpty(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.isWebViewNotEmpty(this) ?: false
}

// ==========
// = 加载方法 =
// ==========

/**
 * 加载网页
 * @receiver WebView Item
 * @param engine String?
 * @param url 资源地址
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_loadUrl(
    engine: String? = null,
    url: String?
): Boolean {
    return engine.getWebEngine()?.loadUrl(this, url) ?: false
}

/**
 * 加载网页
 * @receiver WebView Item
 * @param engine String?
 * @param url 资源地址
 * @param additionalHttpHeaders Http 请求头信息
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_loadUrl(
    engine: String? = null,
    url: String?,
    additionalHttpHeaders: MutableMap<String, String>?
): Boolean {
    return engine.getWebEngine()?.loadUrl(this, url, additionalHttpHeaders) ?: false
}

/**
 * 加载 Html 代码
 * @receiver WebView Item
 * @param engine String?
 * @param data Html 数据
 * @param mimeType 加载网页的类型
 * @param encoding 编码格式
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_loadData(
    engine: String? = null,
    data: String?,
    mimeType: String?,
    encoding: String?
): Boolean {
    return engine.getWebEngine()?.loadData(this, data, mimeType, encoding) ?: false
}

/**
 * 加载 Html 代码
 * @receiver WebView Item
 * @param engine String?
 * @param baseUrl 基础链接
 * @param data Html 数据
 * @param mimeType 加载网页的类型
 * @param encoding 编码格式
 * @param historyUrl 可用历史记录
 * @return `true` success, `false` fail
 */
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

/**
 * 使用 POST 方法将带有 postData 的 url 加载到 WebView 中
 * @receiver WebView Item
 * @param engine String?
 * @param url 资源地址
 * @param postData post 数据 ( 注意 UrlEncode )
 * @return `true` success, `false` fail
 */
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

/**
 * 获取 WebView 配置 ( WebSettings )
 * @receiver WebView Item
 * @param engine String?
 * @return WebView 配置对象
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getSettings(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getSettings(this)
}

/**
 * 获取浏览器标识 UA
 * @receiver WebView Item
 * @param engine String?
 * @return 浏览器标识 UA
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getUserAgentString(
    engine: String? = null
): String? {
    return engine.getWebEngine()?.getUserAgentString(this)
}

/**
 * 设置浏览器标识 UA
 * @receiver WebView Item
 * @param engine String?
 * @param ua 浏览器标识 UA
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_setUserAgentString(
    engine: String? = null,
    ua: String?
): Boolean {
    return engine.getWebEngine()?.setUserAgentString(this, ua) ?: false
}

// ==========
// = JS 交互 =
// ==========

/**
 * 添加 JS 交互注入对象
 * @receiver WebView Item
 * @param engine String?
 * @param obj JavaScript 交互方法注入对象
 * @param interfaceName 在 JavaScript 中公开对象的名称
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_addJavascriptInterface(
    engine: String? = null,
    obj: Any?,
    interfaceName: String?
): Boolean {
    return engine.getWebEngine()?.addJavascriptInterface(this, obj, interfaceName) ?: false
}

/**
 * 移除 JS 交互注入对象
 * @receiver WebView Item
 * @param engine String?
 * @param interfaceName 在 JavaScript 中公开对象的名称
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_removeJavascriptInterface(
    engine: String? = null,
    interfaceName: String?
): Boolean {
    return engine.getWebEngine()?.removeJavascriptInterface(this, interfaceName) ?: false
}

/**
 * 执行 JS 方法
 * @receiver WebView Item
 * @param engine String?
 * @param script JS 内容
 * @param callback 执行回调结果 ( ValueCallback )
 * @return `true` success, `false` fail
 */
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

/**
 * 设置处理各种通知和请求事件对象
 * @receiver WebView Item
 * @param engine String?
 * @param client WebViewClient
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_setWebViewClient(
    engine: String? = null,
    client: Any?
): Boolean {
    return engine.getWebEngine()?.setWebViewClient(this, client) ?: false
}

/**
 * 获取处理各种通知和请求事件对象
 * @receiver WebView Item
 * @param engine String?
 * @return WebViewClient
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getWebViewClient(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getWebViewClient(this)
}

/**
 * 设置辅助 WebView 处理 Javascript 对话框、标题等对象
 * @receiver WebView Item
 * @param engine String?
 * @param client WebChromeClient
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_setWebChromeClient(
    engine: String? = null,
    client: Any?
): Boolean {
    return engine.getWebEngine()?.setWebChromeClient(this, client) ?: false
}

/**
 * 获取辅助 WebView 处理 Javascript 对话框、标题等对象
 * @receiver WebView Item
 * @param engine String?
 * @return WebChromeClient
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getWebChromeClient(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getWebChromeClient(this)
}

/**
 * 设置下载监听
 * @receiver WebView Item
 * @param engine String?
 * @param listener DownloadListener
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_setDownloadListener(
    engine: String? = null,
    listener: Any?
): Boolean {
    return engine.getWebEngine()?.setDownloadListener(this, listener) ?: false
}

/**
 * 设置查找结果监听
 * @receiver WebView Item
 * @param engine String?
 * @param listener FindListener
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_setFindListener(
    engine: String? = null,
    listener: Any?
): Boolean {
    return engine.getWebEngine()?.setFindListener(this, listener) ?: false
}

// ==========
// = 导航历史 =
// ==========

/**
 * WebView 是否可以后退
 * @receiver WebView Item
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun <Item : IWebEngine.EngineItem> Item?.web_canGoBack(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.canGoBack(this) ?: false
}

/**
 * WebView 后退
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_goBack(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.goBack(this) ?: false
}

/**
 * WebView 是否可以前进
 * @receiver WebView Item
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun <Item : IWebEngine.EngineItem> Item?.web_canGoForward(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.canGoForward(this) ?: false
}

/**
 * WebView 前进
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_goForward(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.goForward(this) ?: false
}

/**
 * WebView 是否可以跳转到当前起始点相距的历史记录
 * @receiver WebView Item
 * @param engine String?
 * @param steps 相距索引
 * @return `true` yes, `false` no
 */
fun <Item : IWebEngine.EngineItem> Item?.web_canGoBackOrForward(
    engine: String? = null,
    steps: Int
): Boolean {
    return engine.getWebEngine()?.canGoBackOrForward(this, steps) ?: false
}

/**
 * WebView 跳转到当前起始点相距的历史记录
 * @receiver WebView Item
 * @param engine String?
 * @param steps 相距索引 ( 负数后退、正数前进 )
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_goBackOrForward(
    engine: String? = null,
    steps: Int
): Boolean {
    return engine.getWebEngine()?.goBackOrForward(this, steps) ?: false
}

/**
 * 获取 WebView 历史记录列表 ( WebBackForwardList )
 * @receiver WebView Item
 * @param engine String?
 * @return WebView 历史记录列表
 */
fun <Item : IWebEngine.EngineItem> Item?.web_copyBackForwardList(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.copyBackForwardList(this)
}

/**
 * 刷新页面 ( 当前页面的所有资源都会重新加载 )
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_reload(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.reload(this) ?: false
}

/**
 * 停止加载
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_stopLoading(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.stopLoading(this) ?: false
}

// ==========
// = 清理操作 =
// ==========

/**
 * 清除资源缓存
 * @receiver WebView Item
 * @param engine String?
 * @param includeDiskFiles 是否清空本地缓存 ( false 则只会清空内存缓存, true 全部清空 )
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_clearCache(
    engine: String? = null,
    includeDiskFiles: Boolean
): Boolean {
    return engine.getWebEngine()?.clearCache(this, includeDiskFiles) ?: false
}

/**
 * 清除当前 WebView 访问的历史记录
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_clearHistory(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.clearHistory(this) ?: false
}

/**
 * 清除自动完成填充的表单数据
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_clearFormData(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.clearFormData(this) ?: false
}

/**
 * 清除高亮显示的查找结果
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_clearMatches(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.clearMatches(this) ?: false
}

/**
 * 清除 SSL 偏好设置
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_clearSslPreferences(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.clearSslPreferences(this) ?: false
}

/**
 * 清除全部 Web 存储数据 ( localStorage、IndexedDB、WebSQL 等, 全局生效 )
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun web_deleteAllWebStorage(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.deleteAllWebStorage() ?: false
}

// ==========
// = 状态查询 =
// ==========

/**
 * 获取缩放比例
 * @receiver WebView Item
 * @param engine String?
 * @return 缩放比例
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getScale(
    engine: String? = null
): Float {
    return engine.getWebEngine()?.getScale(this) ?: 1.0F
}

/**
 * 获取当前内容横向滚动距离
 * @receiver WebView Item
 * @param engine String?
 * @return 当前内容横向滚动距离
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getScrollX(
    engine: String? = null
): Int {
    return engine.getWebEngine()?.getScrollX(this) ?: 0
}

/**
 * 获取当前可见区域的顶端距整个页面顶端的距离 ( 当前内容滚动的距离 )
 * @receiver WebView Item
 * @param engine String?
 * @return 当前内容滚动的距离
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getScrollY(
    engine: String? = null
): Int {
    return engine.getWebEngine()?.getScrollY(this) ?: 0
}

/**
 * 获取 HTML 的高度 ( 原始高度, 不包括缩放后的高度 )
 * @receiver WebView Item
 * @param engine String?
 * @return HTML 的高度
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getContentHeight(
    engine: String? = null
): Int {
    return engine.getWebEngine()?.getContentHeight(this) ?: 0
}

/**
 * 获取缩放高度
 * @receiver WebView Item
 * @param engine String?
 * @return 缩放高度
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getScaleHeight(
    engine: String? = null
): Int {
    return engine.getWebEngine()?.getScaleHeight(this) ?: 0
}

/**
 * 获取缩放高度
 * @receiver WebView Item
 * @param engine String?
 * @param scale 缩放比例
 * @return 缩放高度
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getScaleHeight(
    engine: String? = null,
    scale: Float
): Int {
    return engine.getWebEngine()?.getScaleHeight(this, scale) ?: 0
}

/**
 * 获取 WebView 控件高度
 * @receiver WebView Item
 * @param engine String?
 * @return WebView 控件高度
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getHeight(
    engine: String? = null
): Int {
    return engine.getWebEngine()?.getHeight(this) ?: 0
}

/**
 * 获取当前 Url
 * @receiver WebView Item
 * @param engine String?
 * @return 当前 Url
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getUrl(
    engine: String? = null
): String? {
    return engine.getWebEngine()?.getUrl(this)
}

/**
 * 获取最初请求 Url
 * @receiver WebView Item
 * @param engine String?
 * @return 最初请求 Url
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getOriginalUrl(
    engine: String? = null
): String? {
    return engine.getWebEngine()?.getOriginalUrl(this)
}

/**
 * 获取当前页面标题
 * @receiver WebView Item
 * @param engine String?
 * @return 当前页面标题
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getTitle(
    engine: String? = null
): String? {
    return engine.getWebEngine()?.getTitle(this)
}

/**
 * 获取当前页面加载进度
 * @receiver WebView Item
 * @param engine String?
 * @return 当前页面加载进度
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getProgress(
    engine: String? = null
): Int {
    return engine.getWebEngine()?.getProgress(this) ?: 0
}

/**
 * 获取当前页面图标 ( Bitmap )
 * @receiver WebView Item
 * @param engine String?
 * @return 当前页面图标
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getFavicon(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getFavicon(this)
}

/**
 * 获取长按事件类型 ( HitTestResult )
 * @receiver WebView Item
 * @param engine String?
 * @return 长按事件类型
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getHitTestResult(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getHitTestResult(this)
}

/**
 * 获取当前页面 SSL 证书 ( SslCertificate )
 * @receiver WebView Item
 * @param engine String?
 * @return 当前页面 SSL 证书
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getCertificate(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getCertificate(this)
}

// ==========
// = 滚动缩放 =
// ==========

/**
 * 将视图内容向下滚动一半页面大小
 * @receiver WebView Item
 * @param engine String?
 * @param bottom 是否滑动到底部
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_pageDown(
    engine: String? = null,
    bottom: Boolean
): Boolean {
    return engine.getWebEngine()?.pageDown(this, bottom) ?: false
}

/**
 * 将视图内容向上滚动一半页面大小
 * @receiver WebView Item
 * @param engine String?
 * @param top 是否滑动到顶部
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_pageUp(
    engine: String? = null,
    top: Boolean
): Boolean {
    return engine.getWebEngine()?.pageUp(this, top) ?: false
}

/**
 * 滚动到指定坐标
 * @receiver WebView Item
 * @param engine String?
 * @param x 横向坐标
 * @param y 纵向坐标
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_scrollTo(
    engine: String? = null,
    x: Int,
    y: Int
): Boolean {
    return engine.getWebEngine()?.scrollTo(this, x, y) ?: false
}

/**
 * 滚动指定偏移量
 * @receiver WebView Item
 * @param engine String?
 * @param x 横向偏移量
 * @param y 纵向偏移量
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_scrollBy(
    engine: String? = null,
    x: Int,
    y: Int
): Boolean {
    return engine.getWebEngine()?.scrollBy(this, x, y) ?: false
}

/**
 * 以给定的速度开始滑动
 * @receiver WebView Item
 * @param engine String?
 * @param vx 横向速度
 * @param vy 纵向速度
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_flingScroll(
    engine: String? = null,
    vx: Int,
    vy: Int
): Boolean {
    return engine.getWebEngine()?.flingScroll(this, vx, vy) ?: false
}

/**
 * 按指定比例缩放
 * @receiver WebView Item
 * @param engine String?
 * @param zoomFactor 缩放比例
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_zoomBy(
    engine: String? = null,
    zoomFactor: Float
): Boolean {
    return engine.getWebEngine()?.zoomBy(this, zoomFactor) ?: false
}

/**
 * 放大
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_zoomIn(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.zoomIn(this) ?: false
}

/**
 * 缩小
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_zoomOut(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.zoomOut(this) ?: false
}

/**
 * 设置初始缩放比例
 * @receiver WebView Item
 * @param engine String?
 * @param scaleInPercent 缩放百分比
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_setInitialScale(
    engine: String? = null,
    scaleInPercent: Int
): Boolean {
    return engine.getWebEngine()?.setInitialScale(this, scaleInPercent) ?: false
}

// ==========
// = 查找操作 =
// ==========

/**
 * 异步查找页面内所有匹配项
 * @receiver WebView Item
 * @param engine String?
 * @param find 查找内容
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_findAllAsync(
    engine: String? = null,
    find: String?
): Boolean {
    return engine.getWebEngine()?.findAllAsync(this, find) ?: false
}

/**
 * 跳转到下一个 ( 上一个 ) 查找匹配项
 * @receiver WebView Item
 * @param engine String?
 * @param forward 是否向前查找
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_findNext(
    engine: String? = null,
    forward: Boolean
): Boolean {
    return engine.getWebEngine()?.findNext(this, forward) ?: false
}

// ==========
// = 生命周期 =
// ==========

/**
 * 暂停 WebView ( Activity onPause )
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_onPause(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.onPause(this) ?: false
}

/**
 * 恢复 WebView ( Activity onResume )
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_onResume(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.onResume(this) ?: false
}

/**
 * 暂停所有 WebView 的布局、解析、JavaScript 定时器
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_pauseTimers(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.pauseTimers(this) ?: false
}

/**
 * 恢复所有 WebView 的布局、解析、JavaScript 定时器
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_resumeTimers(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.resumeTimers(this) ?: false
}

/**
 * 释放内存
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_freeMemory(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.freeMemory(this) ?: false
}

/**
 * 销毁处理 ( 避免 WebView 引起的内存泄漏 )
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_destroy(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.destroy(this) ?: false
}

// ==========
// = 其他操作 =
// ==========

/**
 * 处理按键 ( 是否回退 )
 * @receiver WebView Item
 * @param engine String?
 * @param keyCode 按键类型
 * @param event 按键事件
 * @return `true` 拦截事件, `false` 不拦截接着处理
 */
fun <Item : IWebEngine.EngineItem> Item?.web_handlerKeyDown(
    engine: String? = null,
    keyCode: Int,
    event: KeyEvent?
): Boolean {
    return engine.getWebEngine()?.handlerKeyDown(this, keyCode, event) ?: false
}

/**
 * 关闭 WebView 硬件加速功能 ( 解决 WebView 闪烁问题 )
 * @receiver WebView Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_setLayerTypeSoftware(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.setLayerTypeSoftware(this) ?: false
}

/**
 * 设置 WebView 硬件加速类型
 * @receiver WebView Item
 * @param engine String?
 * @param layerType 硬件加速类型
 * @param paint Paint
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_setLayerType(
    engine: String? = null,
    layerType: Int,
    paint: Paint?
): Boolean {
    return engine.getWebEngine()?.setLayerType(this, layerType, paint) ?: false
}

/**
 * 设置 WebView 是否处于可联网状态
 * @receiver WebView Item
 * @param engine String?
 * @param networkUp 是否可联网
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_setNetworkAvailable(
    engine: String? = null,
    networkUp: Boolean
): Boolean {
    return engine.getWebEngine()?.setNetworkAvailable(this, networkUp) ?: false
}

/**
 * 保存当前页面为 Web 归档文件
 * @receiver WebView Item
 * @param engine String?
 * @param filename 保存文件路径
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_saveWebArchive(
    engine: String? = null,
    filename: String?
): Boolean {
    return engine.getWebEngine()?.saveWebArchive(this, filename) ?: false
}

/**
 * 请求获取长按聚焦节点的超链接信息
 * @receiver WebView Item
 * @param engine String?
 * @param hrefMsg 承载结果的消息 ( url 写入 data Bundle )
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_requestFocusNodeHref(
    engine: String? = null,
    hrefMsg: Message?
): Boolean {
    return engine.getWebEngine()?.requestFocusNodeHref(this, hrefMsg) ?: false
}

/**
 * 请求获取长按聚焦图片的地址信息
 * @receiver WebView Item
 * @param engine String?
 * @param msg 承载结果的消息 ( url 写入 data Bundle )
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_requestImageRef(
    engine: String? = null,
    msg: Message?
): Boolean {
    return engine.getWebEngine()?.requestImageRef(this, msg) ?: false
}

/**
 * 查询当前页面是否包含图片资源
 * @receiver WebView Item
 * @param engine String?
 * @param response 承载结果的消息 ( 是否包含图片写入 arg1 )
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_documentHasImages(
    engine: String? = null,
    response: Message?
): Boolean {
    return engine.getWebEngine()?.documentHasImages(this, response) ?: false
}

/**
 * 创建打印文档适配器 ( PrintDocumentAdapter )
 * @receiver WebView Item
 * @param engine String?
 * @param documentName 文档名称
 * @return 打印文档适配器
 */
fun <Item : IWebEngine.EngineItem> Item?.web_createPrintDocumentAdapter(
    engine: String? = null,
    documentName: String?
): Any? {
    return engine.getWebEngine()?.createPrintDocumentAdapter(this, documentName)
}

/**
 * 设置渲染进程优先级策略 ( Android 8.0 起支持 )
 * @receiver WebView Item
 * @param engine String?
 * @param rendererRequestedPriority 渲染进程请求优先级
 * @param waivedWhenNotVisible 不可见时是否放弃优先级
 * @return `true` success, `false` fail
 */
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

/**
 * 向网页投递一条 Web 消息 ( HTML5 postMessage )
 * @receiver WebView Item
 * @param engine String?
 * @param message Web 消息 ( WebMessage, 可携带 WebMessagePort )
 * @param targetOrigin 接收方源 ( 限定可接收消息的页面 origin )
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_postWebMessage(
    engine: String? = null,
    message: Any?,
    targetOrigin: Uri?
): Boolean {
    return engine.getWebEngine()?.postWebMessage(this, message, targetOrigin) ?: false
}

/**
 * 创建一对 Web 消息端口 ( HTML5 MessageChannel )
 * @receiver WebView Item
 * @param engine String?
 * @return 消息端口数组 ( WebMessagePort[] )
 */
fun <Item : IWebEngine.EngineItem> Item?.web_createWebMessageChannel(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.createWebMessageChannel(this)
}

// ==========
// = 全局静态 =
// ==========

/**
 * 设置是否开启 WebView 内容调试 ( Chrome inspect )
 * @param engine String?
 * @param enabled 是否开启
 * @return `true` success, `false` fail
 */
fun web_setWebContentsDebuggingEnabled(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getWebEngine()?.setWebContentsDebuggingEnabled(enabled) ?: false
}

/**
 * 获取当前 WebView 内核包信息 ( PackageInfo, Android 8.0 起支持 )
 * @param engine String?
 * @return 当前 WebView 内核包信息
 */
fun web_getCurrentWebViewPackage(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getCurrentWebViewPackage()
}

/**
 * 清除客户端证书选择偏好
 * @param engine String?
 * @param onCleared 清除完成回调
 * @return `true` success, `false` fail
 */
fun web_clearClientCertPreferences(
    engine: String? = null,
    onCleared: Runnable?
): Boolean {
    return engine.getWebEngine()?.clearClientCertPreferences(onCleared) ?: false
}

// ==================
// = AndroidX WebKit =
// ==================

/**
 * 判断 AndroidX WebKit 特性是否支持
 * @param engine String?
 * @param feature 特性常量 ( WebViewFeature 中定义 )
 * @return `true` yes, `false` no
 */
fun web_isWebViewFeatureSupported(
    engine: String? = null,
    feature: String?
): Boolean {
    return engine.getWebEngine()?.isWebViewFeatureSupported(feature) ?: false
}

/**
 * 在文档开始加载时注入 JS ( 需 DOCUMENT_START_SCRIPT 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @param script 注入脚本
 * @param allowedOriginRules 允许的来源规则
 * @return 脚本句柄 ( ScriptHandler )
 */
fun <Item : IWebEngine.EngineItem> Item?.web_addDocumentStartJavaScript(
    engine: String? = null,
    script: String?,
    allowedOriginRules: MutableSet<String>?
): Any? {
    return engine.getWebEngine()?.addDocumentStartJavaScript(this, script, allowedOriginRules)
}

/**
 * 添加 Web 消息监听 ( 安全 JSBridge, 需 WEB_MESSAGE_LISTENER 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @param jsObjectName 注入到页面的对象名
 * @param allowedOriginRules 允许的来源规则
 * @param listener 消息监听 ( WebViewCompat.WebMessageListener )
 * @return `true` success, `false` fail
 */
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

/**
 * 移除 Web 消息监听
 * @receiver WebView Item
 * @param engine String?
 * @param jsObjectName 注入到页面的对象名
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_removeWebMessageListener(
    engine: String? = null,
    jsObjectName: String?
): Boolean {
    return engine.getWebEngine()?.removeWebMessageListener(this, jsObjectName) ?: false
}

/**
 * 获取处理各种通知和请求事件对象 ( 兼容版, 需 GET_WEB_VIEW_CLIENT 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @return WebViewClient
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getWebViewClientCompat(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getWebViewClientCompat(this)
}

/**
 * 获取辅助处理 Javascript 对话框、标题等对象 ( 兼容版, 需 GET_WEB_CHROME_CLIENT 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @return WebChromeClient
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getWebChromeClientCompat(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getWebChromeClientCompat(this)
}

/**
 * 获取当前 WebView 内核包信息 ( 兼容版 )
 * @param engine String?
 * @param context Context
 * @return 当前 WebView 内核包信息 ( PackageInfo )
 */
fun web_getCurrentWebViewPackageCompat(
    engine: String? = null,
    context: Context?
): Any? {
    return engine.getWebEngine()?.getCurrentWebViewPackageCompat(context)
}

/**
 * 获取 WebView 渲染进程 ( 需 GET_WEB_VIEW_RENDERER 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @return 渲染进程 ( WebViewRenderProcess )
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getWebViewRenderProcess(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getWebViewRenderProcess(this)
}

/**
 * 设置 WebView 渲染进程客户端 ( 需 WEB_VIEW_RENDERER_CLIENT_BASIC_USAGE 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @param client 渲染进程客户端 ( WebViewRenderProcessClient )
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_setWebViewRenderProcessClient(
    engine: String? = null,
    client: Any?
): Boolean {
    return engine.getWebEngine()?.setWebViewRenderProcessClient(this, client) ?: false
}

/**
 * 获取 WebView 渲染进程客户端
 * @receiver WebView Item
 * @param engine String?
 * @return 渲染进程客户端 ( WebViewRenderProcessClient )
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getWebViewRenderProcessClient(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getWebViewRenderProcessClient(this)
}

/**
 * 是否启用多进程 ( 需 MULTI_PROCESS 特性 )
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun web_isMultiProcessEnabled(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.isMultiProcessEnabled() ?: false
}

/**
 * 获取 Variations Header ( 需 GET_VARIATIONS_HEADER 特性 )
 * @param engine String?
 * @return Variations Header
 */
fun web_getVariationsHeader(
    engine: String? = null
): String? {
    return engine.getWebEngine()?.getVariationsHeader()
}

/**
 * 启动安全浏览 ( 需 START_SAFE_BROWSING 特性 )
 * @param engine String?
 * @param context Context
 * @param callback 启动结果回调 ( ValueCallback )
 * @return `true` success, `false` fail
 */
fun web_startSafeBrowsing(
    engine: String? = null,
    context: Context?,
    callback: Any?
): Boolean {
    return engine.getWebEngine()?.startSafeBrowsing(context, callback) ?: false
}

/**
 * 设置安全浏览白名单 ( 需 SAFE_BROWSING_ALLOWLIST 特性 )
 * @param engine String?
 * @param hosts 白名单 Host
 * @param callback 设置结果回调 ( ValueCallback )
 * @return `true` success, `false` fail
 */
fun web_setSafeBrowsingAllowlist(
    engine: String? = null,
    hosts: MutableSet<String>?,
    callback: Any?
): Boolean {
    return engine.getWebEngine()?.setSafeBrowsingAllowlist(hosts, callback) ?: false
}

/**
 * 获取安全浏览隐私政策地址 ( 需 SAFE_BROWSING_PRIVACY_POLICY_URL 特性 )
 * @param engine String?
 * @return 隐私政策地址 ( Uri )
 */
fun web_getSafeBrowsingPrivacyPolicyUrl(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getSafeBrowsingPrivacyPolicyUrl()
}

/**
 * 设置 WebView 代理 ( 需 PROXY_OVERRIDE 特性 )
 * @param engine String?
 * @param proxyConfig 代理配置 ( ProxyConfig )
 * @param executor 回调执行器
 * @param listener 生效回调
 * @return `true` success, `false` fail
 */
fun web_setProxyOverride(
    engine: String? = null,
    proxyConfig: Any?,
    executor: Executor?,
    listener: Runnable?
): Boolean {
    return engine.getWebEngine()?.setProxyOverride(proxyConfig, executor, listener) ?: false
}

/**
 * 清除 WebView 代理 ( 需 PROXY_OVERRIDE 特性 )
 * @param engine String?
 * @param executor 回调执行器
 * @param listener 生效回调
 * @return `true` success, `false` fail
 */
fun web_clearProxyOverride(
    engine: String? = null,
    executor: Executor?,
    listener: Runnable?
): Boolean {
    return engine.getWebEngine()?.clearProxyOverride(executor, listener) ?: false
}

/**
 * 静音或取消静音 WebView ( 需 MUTE_AUDIO 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @param mute 是否静音
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_setAudioMuted(
    engine: String? = null,
    mute: Boolean
): Boolean {
    return engine.getWebEngine()?.setAudioMuted(this, mute) ?: false
}

/**
 * WebView 是否静音 ( 需 MUTE_AUDIO 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun <Item : IWebEngine.EngineItem> Item?.web_isAudioMuted(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.isAudioMuted(this) ?: false
}

/**
 * 投递可视状态回调 ( 内容可绘制时触发, 用于规避白屏, 需 VISUAL_STATE_CALLBACK 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @param requestId 请求 id ( 回调原样返回 )
 * @param callback 可视状态回调 ( WebViewCompat.VisualStateCallback )
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_postVisualStateCallback(
    engine: String? = null,
    requestId: Long,
    callback: Any?
): Boolean {
    return engine.getWebEngine()?.postVisualStateCallback(this, requestId, callback) ?: false
}

/**
 * 设置 WebView 使用的 Profile ( 多 Profile 数据隔离, 需 MULTI_PROFILE 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @param profileName Profile 名称
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_setWebViewProfile(
    engine: String? = null,
    profileName: String?
): Boolean {
    return engine.getWebEngine()?.setWebViewProfile(this, profileName) ?: false
}

/**
 * 获取 WebView 使用的 Profile ( 需 MULTI_PROFILE 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @return Profile
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getWebViewProfile(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getWebViewProfile(this)
}

/**
 * 设置 WebView 用户代理元数据 ( 生成 UA Client Hints, 需 USER_AGENT_METADATA 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @param metadata 用户代理元数据 ( UserAgentMetadata )
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_setUserAgentMetadata(
    engine: String? = null,
    metadata: Any?
): Boolean {
    return engine.getWebEngine()?.setUserAgentMetadata(this, metadata) ?: false
}

/**
 * 设置 WebView Media Integrity API 权限 ( 需 WEBVIEW_MEDIA_INTEGRITY_API_STATUS 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @param permissionConfig 权限配置 ( WebViewMediaIntegrityApiStatusConfig )
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_setWebViewMediaIntegrityApiStatus(
    engine: String? = null,
    permissionConfig: Any?
): Boolean {
    return engine.getWebEngine()?.setWebViewMediaIntegrityApiStatus(this, permissionConfig) ?: false
}

/**
 * 根据名称获取或创建 Profile ( 需 MULTI_PROFILE 特性 )
 * @param engine String?
 * @param name Profile 名称
 * @return Profile
 */
fun web_getOrCreateWebProfile(
    engine: String? = null,
    name: String?
): Any? {
    return engine.getWebEngine()?.getOrCreateWebProfile(name)
}

/**
 * 根据名称获取 Profile ( 需 MULTI_PROFILE 特性 )
 * @param engine String?
 * @param name Profile 名称
 * @return Profile ( 不存在返回 null )
 */
fun web_getWebProfile(
    engine: String? = null,
    name: String?
): Any? {
    return engine.getWebEngine()?.getWebProfile(name)
}

/**
 * 根据名称删除 Profile ( 需 MULTI_PROFILE 特性 )
 * @param engine String?
 * @param name Profile 名称
 * @return `true` success, `false` fail
 */
fun web_deleteWebProfile(
    engine: String? = null,
    name: String?
): Boolean {
    return engine.getWebEngine()?.deleteWebProfile(name) ?: false
}

/**
 * 获取全部 Profile 名称 ( 需 MULTI_PROFILE 特性 )
 * @param engine String?
 * @return Profile 名称列表
 */
fun web_getAllWebProfileNames(
    engine: String? = null
): MutableList<String>? {
    return engine.getWebEngine()?.getAllWebProfileNames()
}

/**
 * 设置 ServiceWorker 客户端 ( 需 SERVICE_WORKER_BASIC_USAGE 特性 )
 * @param engine String?
 * @param client ServiceWorker 客户端 ( ServiceWorkerClientCompat )
 * @return `true` success, `false` fail
 */
fun web_setServiceWorkerClient(
    engine: String? = null,
    client: Any?
): Boolean {
    return engine.getWebEngine()?.setServiceWorkerClient(client) ?: false
}

/**
 * 设置 ServiceWorker 是否允许访问 content:// 资源 ( 需 SERVICE_WORKER_CONTENT_ACCESS 特性 )
 * @param engine String?
 * @param allow 是否允许
 * @return `true` success, `false` fail
 */
fun web_setServiceWorkerAllowContentAccess(
    engine: String? = null,
    allow: Boolean
): Boolean {
    return engine.getWebEngine()?.setServiceWorkerAllowContentAccess(allow) ?: false
}

/**
 * 设置 ServiceWorker 是否允许访问文件 ( 需 SERVICE_WORKER_FILE_ACCESS 特性 )
 * @param engine String?
 * @param allow 是否允许
 * @return `true` success, `false` fail
 */
fun web_setServiceWorkerAllowFileAccess(
    engine: String? = null,
    allow: Boolean
): Boolean {
    return engine.getWebEngine()?.setServiceWorkerAllowFileAccess(allow) ?: false
}

/**
 * 设置 ServiceWorker 是否不从网络加载资源 ( 需 SERVICE_WORKER_BLOCK_NETWORK_LOADS 特性 )
 * @param engine String?
 * @param block 是否阻止
 * @return `true` success, `false` fail
 */
fun web_setServiceWorkerBlockNetworkLoads(
    engine: String? = null,
    block: Boolean
): Boolean {
    return engine.getWebEngine()?.setServiceWorkerBlockNetworkLoads(block) ?: false
}

/**
 * 设置 ServiceWorker 缓存模式 ( 需 SERVICE_WORKER_CACHE_MODE 特性 )
 * @param engine String?
 * @param mode 缓存模式
 * @return `true` success, `false` fail
 */
fun web_setServiceWorkerCacheMode(
    engine: String? = null,
    mode: Int
): Boolean {
    return engine.getWebEngine()?.setServiceWorkerCacheMode(mode) ?: false
}

/**
 * 是否正在进行性能跟踪 ( 需 TRACING_CONTROLLER_BASIC_USAGE 特性 )
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun web_isWebViewTracing(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.isWebViewTracing() ?: false
}

/**
 * 开始性能跟踪 ( 需 TRACING_CONTROLLER_BASIC_USAGE 特性 )
 * @param engine String?
 * @param config 跟踪配置 ( TracingConfig )
 * @return `true` success, `false` fail
 */
fun web_startWebViewTracing(
    engine: String? = null,
    config: Any?
): Boolean {
    return engine.getWebEngine()?.startWebViewTracing(config) ?: false
}

/**
 * 停止性能跟踪并输出结果 ( 需 TRACING_CONTROLLER_BASIC_USAGE 特性 )
 * @param engine String?
 * @param outputStream 结果输出流 ( OutputStream, 可为 null )
 * @param executor 回调执行器
 * @return `true` success, `false` fail
 */
fun web_stopWebViewTracing(
    engine: String? = null,
    outputStream: Any?,
    executor: Executor?
): Boolean {
    return engine.getWebEngine()?.stopWebViewTracing(outputStream, executor) ?: false
}

/**
 * 获取 BackForwardCache 实时配置对象 ( 实验性, 需 BACK_FORWARD_CACHE_SETTINGS_EXPERIMENTAL_V3 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @return BackForwardCacheSettings ( 实时对象, 修改即时生效 )
 */
fun <Item : IWebEngine.EngineItem> Item?.web_getBackForwardCacheSettings(
    engine: String? = null
): Any? {
    return engine.getWebEngine()?.getBackForwardCacheSettings(this)
}

/**
 * 设置 WebView 默认流量统计 TAG ( 需 DEFAULT_TRAFFICSTATS_TAGGING 特性 )
 * @param engine String?
 * @param tag 流量统计 TAG
 * @return `true` success, `false` fail
 */
fun web_setDefaultTrafficStatsTag(
    engine: String? = null,
    tag: Int
): Boolean {
    return engine.getWebEngine()?.setDefaultTrafficStatsTag(tag) ?: false
}

/**
 * 保存 WebView 状态 ( 支持限制大小、是否含前进历史, 需 SAVE_STATE 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @param outState 状态保存 Bundle
 * @param maxSizeBytes 状态最大字节数 ( 超出则丢弃更早历史 )
 * @param includeForwardState 是否包含前进历史
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_saveState(
    engine: String? = null,
    outState: Bundle?,
    maxSizeBytes: Int,
    includeForwardState: Boolean
): Boolean {
    return engine.getWebEngine()?.saveState(this, outState, maxSizeBytes, includeForwardState)
        ?: false
}

/**
 * 保存 WebView 状态 ( 基础版, 框架原生能力, 无特性门槛 )
 * @receiver WebView Item
 * @param engine String?
 * @param outState 状态保存 Bundle
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_saveState(
    engine: String? = null,
    outState: Bundle?
): Boolean {
    return engine.getWebEngine()?.saveState(this, outState) ?: false
}

/**
 * 添加导航监听 ( 回调在主线程, 需 NAVIGATION_LISTENER 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @param listener 导航监听 ( NavigationListener )
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_addNavigationListener(
    engine: String? = null,
    listener: Any?
): Boolean {
    return engine.getWebEngine()?.addNavigationListener(this, listener) ?: false
}

/**
 * 添加导航监听 ( 指定回调执行器, 需 NAVIGATION_LISTENER 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @param executor 回调执行器
 * @param listener 导航监听 ( NavigationListener )
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_addNavigationListener(
    engine: String? = null,
    executor: Executor?,
    listener: Any?
): Boolean {
    return engine.getWebEngine()?.addNavigationListener(this, executor, listener) ?: false
}

/**
 * 移除导航监听 ( 需 NAVIGATION_LISTENER 特性 )
 * @receiver WebView Item
 * @param engine String?
 * @param listener 导航监听 ( NavigationListener )
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_removeNavigationListener(
    engine: String? = null,
    listener: Any?
): Boolean {
    return engine.getWebEngine()?.removeNavigationListener(this, listener) ?: false
}

// ============
// = 跨内核扩展 =
// ============

/**
 * 恢复 WebView 历史与状态 ( 与 saveState 配对 )
 * <pre>
 *     System / X5 接收 Bundle 状态, GeckoView 实现层可改用其 SessionState 进行恢复
 * </pre>
 * @receiver WebView Item
 * @param engine String?
 * @param inState 状态 Bundle
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_restoreState(
    engine: String? = null,
    inState: Bundle?
): Boolean {
    return engine.getWebEngine()?.restoreState(this, inState) ?: false
}

/**
 * 设置 WebView 激活 ( 可见 ) 状态
 * <pre>
 *     GeckoView 对应 GeckoSession.setActive ( 非激活时显著降低内存 )
 *     System / X5 对应 onResume / onPause
 * </pre>
 * @receiver WebView Item
 * @param engine String?
 * @param active 是否激活
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_setActive(
    engine: String? = null,
    active: Boolean
): Boolean {
    return engine.getWebEngine()?.setActive(this, active) ?: false
}

/**
 * 获取内核类型标识
 * @param engine String?
 * @return 内核类型 ( 如 SystemWebView、GeckoView、X5 )
 */
fun web_getCoreType(
    engine: String? = null
): String? {
    return engine.getWebEngine()?.getCoreType()
}

/**
 * 获取内核版本
 * @param engine String?
 * @param context Context
 * @return 内核版本 ( 如 System WebView 包版本、X5 TbsVersion、GeckoView buildId )
 */
fun web_getCoreVersion(
    engine: String? = null,
    context: Context?
): String? {
    return engine.getWebEngine()?.getCoreVersion(context)
}

/**
 * 内核是否就绪可用
 * <pre>
 *     System WebView 通常恒为 true, X5 / GeckoView 需内核下载初始化完成
 * </pre>
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun web_isCoreReady(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.isCoreReady() ?: false
}

// ==========
// = Cookie =
// ==========

/**
 * 将 Cookie 设置到 WebView
 * @param engine String?
 * @param url 加载的 Url
 * @param cookie 同步的 cookie
 * @return `true` success, `false` fail
 */
fun web_setCookie(
    engine: String? = null,
    url: String?,
    cookie: String?
): Boolean {
    return engine.getWebEngine()?.setCookie(url, cookie) ?: false
}

/**
 * 将 Cookie 设置到 WebView ( 带设置结果回调 )
 * @param engine String?
 * @param url 加载的 Url
 * @param cookie 同步的 cookie
 * @param callback 设置结果回调 ( ValueCallback )
 * @return `true` success, `false` fail
 */
fun web_setCookie(
    engine: String? = null,
    url: String?,
    cookie: String?,
    callback: Any?
): Boolean {
    return engine.getWebEngine()?.setCookie(url, cookie, callback) ?: false
}

/**
 * 获取指定 Url 的 Cookie
 * @param engine String?
 * @param url Url
 * @return Cookie
 */
fun web_getCookie(
    engine: String? = null,
    url: String?
): String? {
    return engine.getWebEngine()?.getCookie(url)
}

/**
 * 设置是否接受 Cookie
 * @param engine String?
 * @param accept 是否接受
 * @return `true` success, `false` fail
 */
fun web_setAcceptCookie(
    engine: String? = null,
    accept: Boolean
): Boolean {
    return engine.getWebEngine()?.setAcceptCookie(accept) ?: false
}

/**
 * 是否接受 Cookie
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun web_acceptCookie(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.acceptCookie() ?: false
}

/**
 * 设置指定 WebView 是否接受第三方 Cookie
 * @receiver WebView Item
 * @param engine String?
 * @param accept 是否接受
 * @return `true` success, `false` fail
 */
fun <Item : IWebEngine.EngineItem> Item?.web_setAcceptThirdPartyCookies(
    engine: String? = null,
    accept: Boolean
): Boolean {
    return engine.getWebEngine()?.setAcceptThirdPartyCookies(this, accept) ?: false
}

/**
 * 指定 WebView 是否接受第三方 Cookie
 * @receiver WebView Item
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun <Item : IWebEngine.EngineItem> Item?.web_acceptThirdPartyCookies(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.acceptThirdPartyCookies(this) ?: false
}

/**
 * 是否存在 Cookie
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun web_hasCookies(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.hasCookies() ?: false
}

/**
 * 移除 Cookie ( Session、All )
 * @param engine String?
 * @param callback 移除回调 ( ValueCallback )
 */
fun web_removeCookie(
    engine: String? = null,
    callback: Any?
) {
    engine.getWebEngine()?.removeCookie(callback)
}

/**
 * 移除 Session Cookie
 * @param engine String?
 * @param callback 移除回调 ( ValueCallback )
 * @return `true` success, `false` fail
 */
fun web_removeSessionCookie(
    engine: String? = null,
    callback: Any?
): Boolean {
    return engine.getWebEngine()?.removeSessionCookie(callback) ?: false
}

/**
 * 移除所有的 Cookie
 * @param engine String?
 * @param callback 移除回调 ( ValueCallback )
 * @return `true` success, `false` fail
 */
fun web_removeAllCookie(
    engine: String? = null,
    callback: Any?
): Boolean {
    return engine.getWebEngine()?.removeAllCookie(callback) ?: false
}

/**
 * 同步 ( 刷新 ) Cookie 到本地存储
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun web_flushCookie(
    engine: String? = null
): Boolean {
    return engine.getWebEngine()?.flushCookie() ?: false
}

/**
 * 获取指定 Url 全部 Cookie 的完整属性 ( 含 Domain、Path、Expires、Secure 等, 需 GET_COOKIE_INFO 特性 )
 * @param engine String?
 * @param url Url
 * @return 各条 Cookie 的完整属性字符串列表
 */
fun web_getCookieInfo(
    engine: String? = null,
    url: String?
): MutableList<String>? {
    return engine.getWebEngine()?.getCookieInfo(url)
}