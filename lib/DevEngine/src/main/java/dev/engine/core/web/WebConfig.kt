package dev.engine.core.web

import dev.engine.core.web.WebConfig.Companion.UNSET_INT
import dev.engine.web.IWebEngine

/**
 * detail: WebView Config
 * @author Ttt
 * <p></p>
 * 对应 System WebView WebSettings 常用安全、缓存、缩放、UA 配置
 * 未设置项 ( null / [UNSET_INT] ) 在 applyConfig 时跳过, 保持 WebView 默认值
 */
open class WebConfig private constructor(
    config: WebConfig?
) : IWebEngine.EngineConfig {

    // 是否支持 JavaScript
    private var mJavaScriptEnabled: Boolean? = null

    // 渲染优先级 ( 内核相关对象 )
    private var mRenderPriority: Any? = null

    // 是否使用宽视图
    private var mUseWideViewPort: Boolean? = null

    // 是否按宽度缩小内容以适合屏幕
    private var mLoadWithOverviewMode: Boolean? = null

    // 基础布局算法 ( 内核相关对象 )
    private var mLayoutAlgorithm: Any? = null

    // 是否支持缩放
    private var mSupportZoom: Boolean? = null

    // 是否显示内置缩放工具
    private var mBuiltInZoomControls: Boolean? = null

    // 是否显示缩放工具
    private var mDisplayZoomControls: Boolean? = null

    // 文本缩放倍数
    private var mTextZoom = UNSET_INT

    // WebView 字体
    private var mStandardFontFamily: String? = null

    // WebView 字体大小
    private var mDefaultFontSize = UNSET_INT

    // WebView 支持最小字体大小
    private var mMinimumFontSize = UNSET_INT

    // 等宽字体大小
    private var mDefaultFixedFontSize = UNSET_INT

    // 最小逻辑字体大小
    private var mMinimumLogicalFontSize = UNSET_INT

    // 等宽字体
    private var mFixedFontFamily: String? = null

    // 无衬线字体
    private var mSansSerifFontFamily: String? = null

    // 衬线字体
    private var mSerifFontFamily: String? = null

    // 手写字体
    private var mCursiveFontFamily: String? = null

    // 奇幻字体
    private var mFantasyFontFamily: String? = null

    // 混合内容模式
    private var mMixedContentMode = UNSET_INT

    // 是否支持自动加载图片
    private var mLoadsImagesAutomatically: Boolean? = null

    // 是否支持通过 JS 打开新窗口
    private var mJavaScriptCanOpenWindowsAutomatically: Boolean? = null

    // 编码格式
    private var mDefaultTextEncodingName: String? = null

    // 是否允许网页执行定位操作
    private var mGeolocationEnabled: Boolean? = null

    // 浏览器标识 UA
    private var mUserAgentString: String? = null

    // 是否可以访问文件
    private var mAllowFileAccess: Boolean? = null

    // 是否允许通过 file url 加载的 JS 代码读取其他的本地文件
    private var mAllowFileAccessFromFileURLs: Boolean? = null

    // 是否允许通过 file url 加载的 JS 可以访问其他的源
    private var mAllowUniversalAccessFromFileURLs: Boolean? = null

    // 是否不从网络加载资源
    private var mBlockNetworkLoads: Boolean? = null

    // 是否不从网络加载图像资源
    private var mBlockNetworkImage: Boolean? = null

    // 是否需要用户手势来播放媒体
    private var mMediaPlaybackRequiresUserGesture: Boolean? = null

    // WebView 缓存模式
    private var mCacheMode = UNSET_INT

    // 是否支持 DOM Storage
    private var mDomStorageEnabled: Boolean? = null

    // 是否开启 Application Caches 功能 ( Android 13 已移除对应 WebSettings API )
    private var mAppCacheEnabled: Boolean? = null

    // Application Caches 地址 ( Android 13 已移除对应 WebSettings API )
    private var mAppCachePath: String? = null

    // Application Caches 大小 ( Android 13 已移除对应 WebSettings API )
    private var mAppCacheMaxSize = UNSET_LONG

    // 是否支持数据库缓存
    private var mDatabaseEnabled: Boolean? = null

    // 数据库缓存路径
    private var mDatabasePath: String? = null

    // 是否允许访问 content:// 资源
    private var mAllowContentAccess: Boolean? = null

    // 是否支持多窗口
    private var mSupportMultipleWindows: Boolean? = null

    // 是否在加载时获取初始焦点
    private var mNeedInitialFocus: Boolean? = null

    // 是否开启安全浏览 ( Android 8.0 起支持 )
    private var mSafeBrowsingEnabled: Boolean? = null

    // 是否开启离屏预渲染
    private var mOffscreenPreRaster: Boolean? = null

    // 禁用的 ActionMode 菜单项 ( Android 7.0 起支持 )
    private var mDisabledActionModeMenuItems = UNSET_INT

    // 是否允许算法暗色模式 ( AndroidX WebKit, 需 ALGORITHMIC_DARKENING 特性支持 )
    private var mAlgorithmicDarkeningAllowed: Boolean? = null

    // 是否启用 PaymentRequest 支付 API ( AndroidX WebKit, 需 PAYMENT_REQUEST 特性支持 )
    private var mPaymentRequestEnabled: Boolean? = null

    // 是否启用企业认证 AppLink 策略 ( AndroidX WebKit, 需 ENTERPRISE_AUTHENTICATION_APP_LINK_POLICY 特性支持 )
    private var mEnterpriseAuthenticationAppLinkPolicyEnabled: Boolean? = null

    // 归因注册行为 ( AndroidX WebKit, 需 ATTRIBUTION_REGISTRATION_BEHAVIOR 特性支持 )
    private var mAttributionRegistrationBehavior = UNSET_INT

    // 是否启用 BackForwardCache 前进后退缓存 ( AndroidX WebKit, 需 BACK_FORWARD_CACHE 特性支持 )
    private var mBackForwardCacheEnabled: Boolean? = null

    // 预测式加载状态 ( AndroidX WebKit, 实验性, 需 SPECULATIVE_LOADING 特性支持 )
    private var mSpeculativeLoadingStatus = UNSET_INT

    // 长按超链接上下文菜单项 ( 位标志组合, AndroidX WebKit, 需 HYPERLINK_CONTEXT_MENU_ITEMS 特性支持 )
    private var mHyperlinkContextMenuItems = UNSET_INT

    // 是否启用 PaymentRequest hasEnrolledInstrument ( AndroidX WebKit, 需 PAYMENT_REQUEST 特性支持 )
    private var mHasEnrolledInstrumentEnabled: Boolean? = null

    // 是否在 shouldInterceptRequest 中携带 Cookie ( AndroidX WebKit, 需 COOKIE_INTERCEPT 特性支持 )
    private var mCookiesIncludedInShouldInterceptRequest: Boolean? = null

    // Web Authentication ( WebAuthn ) 支持级别 ( AndroidX WebKit, 需 WEB_AUTHENTICATION 特性支持 )
    private var mWebAuthenticationSupport = UNSET_INT

    // 是否强制网页可缩放 ( GeckoView forceUserScalable / X5 强制手势缩放; System WebView 无对应实现 )
    private var mForceUserScalable: Boolean? = null

    companion object {

        const val UNSET_INT = -1
        const val UNSET_LONG = -1L

        fun create(): WebConfig {
            return WebConfig(null)
        }

        fun create(config: WebConfig?): WebConfig {
            return WebConfig(config)
        }

        fun default(): WebConfig {
            return defaultWebConfig()
        }
    }

    init {
        config?.let {
            this.mJavaScriptEnabled = it.mJavaScriptEnabled
            this.mRenderPriority = it.mRenderPriority
            this.mUseWideViewPort = it.mUseWideViewPort
            this.mLoadWithOverviewMode = it.mLoadWithOverviewMode
            this.mLayoutAlgorithm = it.mLayoutAlgorithm
            this.mSupportZoom = it.mSupportZoom
            this.mBuiltInZoomControls = it.mBuiltInZoomControls
            this.mDisplayZoomControls = it.mDisplayZoomControls
            this.mTextZoom = it.mTextZoom
            this.mStandardFontFamily = it.mStandardFontFamily
            this.mDefaultFontSize = it.mDefaultFontSize
            this.mMinimumFontSize = it.mMinimumFontSize
            this.mDefaultFixedFontSize = it.mDefaultFixedFontSize
            this.mMinimumLogicalFontSize = it.mMinimumLogicalFontSize
            this.mFixedFontFamily = it.mFixedFontFamily
            this.mSansSerifFontFamily = it.mSansSerifFontFamily
            this.mSerifFontFamily = it.mSerifFontFamily
            this.mCursiveFontFamily = it.mCursiveFontFamily
            this.mFantasyFontFamily = it.mFantasyFontFamily
            this.mMixedContentMode = it.mMixedContentMode
            this.mLoadsImagesAutomatically = it.mLoadsImagesAutomatically
            this.mJavaScriptCanOpenWindowsAutomatically = it.mJavaScriptCanOpenWindowsAutomatically
            this.mDefaultTextEncodingName = it.mDefaultTextEncodingName
            this.mGeolocationEnabled = it.mGeolocationEnabled
            this.mUserAgentString = it.mUserAgentString
            this.mAllowFileAccess = it.mAllowFileAccess
            this.mAllowFileAccessFromFileURLs = it.mAllowFileAccessFromFileURLs
            this.mAllowUniversalAccessFromFileURLs = it.mAllowUniversalAccessFromFileURLs
            this.mBlockNetworkLoads = it.mBlockNetworkLoads
            this.mBlockNetworkImage = it.mBlockNetworkImage
            this.mMediaPlaybackRequiresUserGesture = it.mMediaPlaybackRequiresUserGesture
            this.mCacheMode = it.mCacheMode
            this.mDomStorageEnabled = it.mDomStorageEnabled
            this.mAppCacheEnabled = it.mAppCacheEnabled
            this.mAppCachePath = it.mAppCachePath
            this.mAppCacheMaxSize = it.mAppCacheMaxSize
            this.mDatabaseEnabled = it.mDatabaseEnabled
            this.mDatabasePath = it.mDatabasePath
            this.mAllowContentAccess = it.mAllowContentAccess
            this.mSupportMultipleWindows = it.mSupportMultipleWindows
            this.mNeedInitialFocus = it.mNeedInitialFocus
            this.mSafeBrowsingEnabled = it.mSafeBrowsingEnabled
            this.mOffscreenPreRaster = it.mOffscreenPreRaster
            this.mDisabledActionModeMenuItems = it.mDisabledActionModeMenuItems
            this.mAlgorithmicDarkeningAllowed = it.mAlgorithmicDarkeningAllowed
            this.mPaymentRequestEnabled = it.mPaymentRequestEnabled
            this.mEnterpriseAuthenticationAppLinkPolicyEnabled =
                it.mEnterpriseAuthenticationAppLinkPolicyEnabled
            this.mAttributionRegistrationBehavior = it.mAttributionRegistrationBehavior
            this.mBackForwardCacheEnabled = it.mBackForwardCacheEnabled
            this.mSpeculativeLoadingStatus = it.mSpeculativeLoadingStatus
            this.mHyperlinkContextMenuItems = it.mHyperlinkContextMenuItems
            this.mHasEnrolledInstrumentEnabled = it.mHasEnrolledInstrumentEnabled
            this.mCookiesIncludedInShouldInterceptRequest =
                it.mCookiesIncludedInShouldInterceptRequest
            this.mWebAuthenticationSupport = it.mWebAuthenticationSupport
            this.mForceUserScalable = it.mForceUserScalable
        }
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 克隆配置信息
     * @return [WebConfig]
     */
    open fun clone(): WebConfig {
        return WebConfig(this)
    }

    // ===========
    // = get/set =
    // ===========

    override fun javaScriptEnabled(): Boolean? {
        return mJavaScriptEnabled
    }

    open fun setJavaScriptEnabled(javaScriptEnabled: Boolean?): WebConfig {
        mJavaScriptEnabled = javaScriptEnabled
        return this
    }

    override fun renderPriority(): Any? {
        return mRenderPriority
    }

    open fun setRenderPriority(renderPriority: Any?): WebConfig {
        mRenderPriority = renderPriority
        return this
    }

    override fun useWideViewPort(): Boolean? {
        return mUseWideViewPort
    }

    open fun setUseWideViewPort(useWideViewPort: Boolean?): WebConfig {
        mUseWideViewPort = useWideViewPort
        return this
    }

    override fun loadWithOverviewMode(): Boolean? {
        return mLoadWithOverviewMode
    }

    open fun setLoadWithOverviewMode(loadWithOverviewMode: Boolean?): WebConfig {
        mLoadWithOverviewMode = loadWithOverviewMode
        return this
    }

    override fun layoutAlgorithm(): Any? {
        return mLayoutAlgorithm
    }

    open fun setLayoutAlgorithm(layoutAlgorithm: Any?): WebConfig {
        mLayoutAlgorithm = layoutAlgorithm
        return this
    }

    override fun supportZoom(): Boolean? {
        return mSupportZoom
    }

    open fun setSupportZoom(supportZoom: Boolean?): WebConfig {
        mSupportZoom = supportZoom
        return this
    }

    override fun builtInZoomControls(): Boolean? {
        return mBuiltInZoomControls
    }

    open fun setBuiltInZoomControls(builtInZoomControls: Boolean?): WebConfig {
        mBuiltInZoomControls = builtInZoomControls
        return this
    }

    override fun displayZoomControls(): Boolean? {
        return mDisplayZoomControls
    }

    open fun setDisplayZoomControls(displayZoomControls: Boolean?): WebConfig {
        mDisplayZoomControls = displayZoomControls
        return this
    }

    override fun textZoom(): Int {
        return mTextZoom
    }

    open fun setTextZoom(textZoom: Int): WebConfig {
        mTextZoom = textZoom
        return this
    }

    override fun standardFontFamily(): String? {
        return mStandardFontFamily
    }

    open fun setStandardFontFamily(standardFontFamily: String?): WebConfig {
        mStandardFontFamily = standardFontFamily
        return this
    }

    override fun defaultFontSize(): Int {
        return mDefaultFontSize
    }

    open fun setDefaultFontSize(defaultFontSize: Int): WebConfig {
        mDefaultFontSize = defaultFontSize
        return this
    }

    override fun minimumFontSize(): Int {
        return mMinimumFontSize
    }

    open fun setMinimumFontSize(minimumFontSize: Int): WebConfig {
        mMinimumFontSize = minimumFontSize
        return this
    }

    override fun defaultFixedFontSize(): Int {
        return mDefaultFixedFontSize
    }

    open fun setDefaultFixedFontSize(defaultFixedFontSize: Int): WebConfig {
        mDefaultFixedFontSize = defaultFixedFontSize
        return this
    }

    override fun minimumLogicalFontSize(): Int {
        return mMinimumLogicalFontSize
    }

    open fun setMinimumLogicalFontSize(minimumLogicalFontSize: Int): WebConfig {
        mMinimumLogicalFontSize = minimumLogicalFontSize
        return this
    }

    override fun fixedFontFamily(): String? {
        return mFixedFontFamily
    }

    open fun setFixedFontFamily(fixedFontFamily: String?): WebConfig {
        mFixedFontFamily = fixedFontFamily
        return this
    }

    override fun sansSerifFontFamily(): String? {
        return mSansSerifFontFamily
    }

    open fun setSansSerifFontFamily(sansSerifFontFamily: String?): WebConfig {
        mSansSerifFontFamily = sansSerifFontFamily
        return this
    }

    override fun serifFontFamily(): String? {
        return mSerifFontFamily
    }

    open fun setSerifFontFamily(serifFontFamily: String?): WebConfig {
        mSerifFontFamily = serifFontFamily
        return this
    }

    override fun cursiveFontFamily(): String? {
        return mCursiveFontFamily
    }

    open fun setCursiveFontFamily(cursiveFontFamily: String?): WebConfig {
        mCursiveFontFamily = cursiveFontFamily
        return this
    }

    override fun fantasyFontFamily(): String? {
        return mFantasyFontFamily
    }

    open fun setFantasyFontFamily(fantasyFontFamily: String?): WebConfig {
        mFantasyFontFamily = fantasyFontFamily
        return this
    }

    override fun mixedContentMode(): Int {
        return mMixedContentMode
    }

    open fun setMixedContentMode(mixedContentMode: Int): WebConfig {
        mMixedContentMode = mixedContentMode
        return this
    }

    override fun loadsImagesAutomatically(): Boolean? {
        return mLoadsImagesAutomatically
    }

    open fun setLoadsImagesAutomatically(loadsImagesAutomatically: Boolean?): WebConfig {
        mLoadsImagesAutomatically = loadsImagesAutomatically
        return this
    }

    override fun javaScriptCanOpenWindowsAutomatically(): Boolean? {
        return mJavaScriptCanOpenWindowsAutomatically
    }

    open fun setJavaScriptCanOpenWindowsAutomatically(
        javaScriptCanOpenWindowsAutomatically: Boolean?
    ): WebConfig {
        mJavaScriptCanOpenWindowsAutomatically = javaScriptCanOpenWindowsAutomatically
        return this
    }

    override fun defaultTextEncodingName(): String? {
        return mDefaultTextEncodingName
    }

    open fun setDefaultTextEncodingName(defaultTextEncodingName: String?): WebConfig {
        mDefaultTextEncodingName = defaultTextEncodingName
        return this
    }

    override fun geolocationEnabled(): Boolean? {
        return mGeolocationEnabled
    }

    open fun setGeolocationEnabled(geolocationEnabled: Boolean?): WebConfig {
        mGeolocationEnabled = geolocationEnabled
        return this
    }

    override fun userAgentString(): String? {
        return mUserAgentString
    }

    open fun setUserAgentString(userAgentString: String?): WebConfig {
        mUserAgentString = userAgentString
        return this
    }

    override fun allowFileAccess(): Boolean? {
        return mAllowFileAccess
    }

    open fun setAllowFileAccess(allowFileAccess: Boolean?): WebConfig {
        mAllowFileAccess = allowFileAccess
        return this
    }

    override fun allowFileAccessFromFileURLs(): Boolean? {
        return mAllowFileAccessFromFileURLs
    }

    open fun setAllowFileAccessFromFileURLs(allowFileAccessFromFileURLs: Boolean?): WebConfig {
        mAllowFileAccessFromFileURLs = allowFileAccessFromFileURLs
        return this
    }

    override fun allowUniversalAccessFromFileURLs(): Boolean? {
        return mAllowUniversalAccessFromFileURLs
    }

    open fun setAllowUniversalAccessFromFileURLs(
        allowUniversalAccessFromFileURLs: Boolean?
    ): WebConfig {
        mAllowUniversalAccessFromFileURLs = allowUniversalAccessFromFileURLs
        return this
    }

    override fun blockNetworkLoads(): Boolean? {
        return mBlockNetworkLoads
    }

    open fun setBlockNetworkLoads(blockNetworkLoads: Boolean?): WebConfig {
        mBlockNetworkLoads = blockNetworkLoads
        return this
    }

    override fun blockNetworkImage(): Boolean? {
        return mBlockNetworkImage
    }

    open fun setBlockNetworkImage(blockNetworkImage: Boolean?): WebConfig {
        mBlockNetworkImage = blockNetworkImage
        return this
    }

    override fun mediaPlaybackRequiresUserGesture(): Boolean? {
        return mMediaPlaybackRequiresUserGesture
    }

    open fun setMediaPlaybackRequiresUserGesture(
        mediaPlaybackRequiresUserGesture: Boolean?
    ): WebConfig {
        mMediaPlaybackRequiresUserGesture = mediaPlaybackRequiresUserGesture
        return this
    }

    override fun cacheMode(): Int {
        return mCacheMode
    }

    open fun setCacheMode(cacheMode: Int): WebConfig {
        mCacheMode = cacheMode
        return this
    }

    override fun domStorageEnabled(): Boolean? {
        return mDomStorageEnabled
    }

    open fun setDomStorageEnabled(domStorageEnabled: Boolean?): WebConfig {
        mDomStorageEnabled = domStorageEnabled
        return this
    }

    override fun appCacheEnabled(): Boolean? {
        return mAppCacheEnabled
    }

    open fun setAppCacheEnabled(appCacheEnabled: Boolean?): WebConfig {
        mAppCacheEnabled = appCacheEnabled
        return this
    }

    override fun appCachePath(): String? {
        return mAppCachePath
    }

    open fun setAppCachePath(appCachePath: String?): WebConfig {
        mAppCachePath = appCachePath
        return this
    }

    override fun appCacheMaxSize(): Long {
        return mAppCacheMaxSize
    }

    open fun setAppCacheMaxSize(appCacheMaxSize: Long): WebConfig {
        mAppCacheMaxSize = appCacheMaxSize
        return this
    }

    override fun databaseEnabled(): Boolean? {
        return mDatabaseEnabled
    }

    open fun setDatabaseEnabled(databaseEnabled: Boolean?): WebConfig {
        mDatabaseEnabled = databaseEnabled
        return this
    }

    override fun databasePath(): String? {
        return mDatabasePath
    }

    open fun setDatabasePath(databasePath: String?): WebConfig {
        mDatabasePath = databasePath
        return this
    }

    override fun allowContentAccess(): Boolean? {
        return mAllowContentAccess
    }

    open fun setAllowContentAccess(allowContentAccess: Boolean?): WebConfig {
        mAllowContentAccess = allowContentAccess
        return this
    }

    override fun supportMultipleWindows(): Boolean? {
        return mSupportMultipleWindows
    }

    open fun setSupportMultipleWindows(supportMultipleWindows: Boolean?): WebConfig {
        mSupportMultipleWindows = supportMultipleWindows
        return this
    }

    override fun needInitialFocus(): Boolean? {
        return mNeedInitialFocus
    }

    open fun setNeedInitialFocus(needInitialFocus: Boolean?): WebConfig {
        mNeedInitialFocus = needInitialFocus
        return this
    }

    override fun safeBrowsingEnabled(): Boolean? {
        return mSafeBrowsingEnabled
    }

    open fun setSafeBrowsingEnabled(safeBrowsingEnabled: Boolean?): WebConfig {
        mSafeBrowsingEnabled = safeBrowsingEnabled
        return this
    }

    override fun offscreenPreRaster(): Boolean? {
        return mOffscreenPreRaster
    }

    open fun setOffscreenPreRaster(offscreenPreRaster: Boolean?): WebConfig {
        mOffscreenPreRaster = offscreenPreRaster
        return this
    }

    override fun disabledActionModeMenuItems(): Int {
        return mDisabledActionModeMenuItems
    }

    open fun setDisabledActionModeMenuItems(disabledActionModeMenuItems: Int): WebConfig {
        mDisabledActionModeMenuItems = disabledActionModeMenuItems
        return this
    }

    override fun algorithmicDarkeningAllowed(): Boolean? {
        return mAlgorithmicDarkeningAllowed
    }

    open fun setAlgorithmicDarkeningAllowed(algorithmicDarkeningAllowed: Boolean?): WebConfig {
        mAlgorithmicDarkeningAllowed = algorithmicDarkeningAllowed
        return this
    }

    override fun paymentRequestEnabled(): Boolean? {
        return mPaymentRequestEnabled
    }

    open fun setPaymentRequestEnabled(paymentRequestEnabled: Boolean?): WebConfig {
        mPaymentRequestEnabled = paymentRequestEnabled
        return this
    }

    override fun enterpriseAuthenticationAppLinkPolicyEnabled(): Boolean? {
        return mEnterpriseAuthenticationAppLinkPolicyEnabled
    }

    open fun setEnterpriseAuthenticationAppLinkPolicyEnabled(
        enterpriseAuthenticationAppLinkPolicyEnabled: Boolean?
    ): WebConfig {
        mEnterpriseAuthenticationAppLinkPolicyEnabled = enterpriseAuthenticationAppLinkPolicyEnabled
        return this
    }

    override fun attributionRegistrationBehavior(): Int {
        return mAttributionRegistrationBehavior
    }

    open fun setAttributionRegistrationBehavior(attributionRegistrationBehavior: Int): WebConfig {
        mAttributionRegistrationBehavior = attributionRegistrationBehavior
        return this
    }

    override fun backForwardCacheEnabled(): Boolean? {
        return mBackForwardCacheEnabled
    }

    open fun setBackForwardCacheEnabled(backForwardCacheEnabled: Boolean?): WebConfig {
        mBackForwardCacheEnabled = backForwardCacheEnabled
        return this
    }

    override fun speculativeLoadingStatus(): Int {
        return mSpeculativeLoadingStatus
    }

    open fun setSpeculativeLoadingStatus(speculativeLoadingStatus: Int): WebConfig {
        mSpeculativeLoadingStatus = speculativeLoadingStatus
        return this
    }

    override fun hyperlinkContextMenuItems(): Int {
        return mHyperlinkContextMenuItems
    }

    open fun setHyperlinkContextMenuItems(hyperlinkContextMenuItems: Int): WebConfig {
        mHyperlinkContextMenuItems = hyperlinkContextMenuItems
        return this
    }

    override fun hasEnrolledInstrumentEnabled(): Boolean? {
        return mHasEnrolledInstrumentEnabled
    }

    open fun setHasEnrolledInstrumentEnabled(hasEnrolledInstrumentEnabled: Boolean?): WebConfig {
        mHasEnrolledInstrumentEnabled = hasEnrolledInstrumentEnabled
        return this
    }

    override fun cookiesIncludedInShouldInterceptRequest(): Boolean? {
        return mCookiesIncludedInShouldInterceptRequest
    }

    open fun setCookiesIncludedInShouldInterceptRequest(
        cookiesIncludedInShouldInterceptRequest: Boolean?
    ): WebConfig {
        mCookiesIncludedInShouldInterceptRequest = cookiesIncludedInShouldInterceptRequest
        return this
    }

    override fun webAuthenticationSupport(): Int {
        return mWebAuthenticationSupport
    }

    open fun setWebAuthenticationSupport(webAuthenticationSupport: Int): WebConfig {
        mWebAuthenticationSupport = webAuthenticationSupport
        return this
    }

    override fun forceUserScalable(): Boolean? {
        return mForceUserScalable
    }

    open fun setForceUserScalable(forceUserScalable: Boolean?): WebConfig {
        mForceUserScalable = forceUserScalable
        return this
    }
}