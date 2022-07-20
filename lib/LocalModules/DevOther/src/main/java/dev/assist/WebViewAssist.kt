package dev.assist

import android.graphics.Paint
import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.webkit.WebSettings.LayoutAlgorithm
import android.webkit.WebSettings.RenderPriority
import android.webkit.WebView.HitTestResult
import dev.DevUtils
import dev.assist.WebViewAssist.Builder.OnApplyListener
import dev.utils.DevFinal
import dev.utils.LogPrintUtils
import dev.utils.app.ViewUtils

/**
 * detail: WebView 辅助类
 * @author Ttt
 * WebView 截图使用 [dev.utils.app.CapturePictureUtils.snapshotByWebView]
 * WebView 全面解析
 * @see https://www.jianshu.com/p/3e0136c9e748
 * Android WebView 常见问题及解决方案汇总
 * @see https://www.cnblogs.com/Free-Thinker/p/6179016.html
 * WebView 与 JavaScript 的交互总结
 * @see https://www.jianshu.com/p/5cc2eae14e07
 * WebView 使用漏洞
 * @see https://blog.csdn.net/carson_ho/article/details/64904635
 * Android Webview H5 交互之 LocalStorage
 * @see https://www.jianshu.com/p/379a0681ce25
 */
class WebViewAssist @JvmOverloads constructor(listener: Boolean = true) {

    // WebView
    private var mWebView: WebView? = null

    // WebView 常用配置构建类
    private var mBuilder: Builder? = null

    init {
        /**
         * listener 是否复用监听事件
         * 使用全局配置, 需要手动调用 [.apply] 方法
         */
        mBuilder = sGlobalBuilder?.clone(listener)
    }

    // ==========
    // = 静态方法 =
    // ==========

    companion object {

        // 日志 TAG
        private val TAG = WebViewAssist::class.java.simpleName

        // 全局配置
        private var sGlobalBuilder: Builder? = null

        /**
         * 设置全局 WebView 常用配置构建类
         * @param builder [Builder]
         */
        fun setGlobalBuilder(builder: Builder) {
            sGlobalBuilder = builder
        }

        /**
         * 获取全局 WebView 常用配置构建类
         * @return [Builder]
         */
        fun getGlobalBuilder(): Builder? {
            return sGlobalBuilder
        }

        // ==========
        // = Cookie =
        // ==========

        /**
         * 将 Cookie 设置到 WebView
         * @param url    加载的 Url
         * @param cookie 同步的 cookie
         * @return `true` success, `false` fail
         * 若两次设置相同, 则覆盖上一次的
         * 同步 Cookie 要在 WebView 加载 url 之前, 否则 WebView 无法获得相应的 Cookie, 也就无法通过验证
         * Cookie 应该被及时更新, 否则很可能导致 WebView 拿的是旧的 session id 和服务器进行通信
         */
        fun setCookie(
            url: String?,
            cookie: String?
        ): Boolean {
            try {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    CookieSyncManager.createInstance(DevUtils.getContext())
                }
                val cookieManager = CookieManager.getInstance()
                // 如果没有特殊需求, 这里只需要将 session id 以 "key=value" 形式作为 Cookie 即可
                cookieManager.setCookie(url, cookie)
                return true
            } catch (e: Exception) {
                LogPrintUtils.eTag(TAG, e, "setCookie")
            }
            return false
        }

        /**
         * 获取指定 Url 的 Cookie
         * @param url Url
         * @return Cookie
         */
        fun getCookie(url: String?): String? {
            try {
                val cookieManager = CookieManager.getInstance()
                return cookieManager.getCookie(url)
            } catch (e: Exception) {
                LogPrintUtils.eTag(TAG, e, "getCookie")
            }
            return null
        }

        /**
         * 移除 Cookie
         * @param callback 移除回调
         */
        fun removeCookie(callback: ValueCallback<Boolean>?) {
            removeSessionCookie(callback)
            removeAllCookie(callback)
        }

        /**
         * 移除 Session Cookie
         * @param callback 移除回调
         * @return `true` success, `false` fail
         */
        fun removeSessionCookie(callback: ValueCallback<Boolean>?): Boolean {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    CookieManager.getInstance().removeSessionCookies(callback)
                } else {
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
         * @param callback 移除回调
         * @return `true` success, `false` fail
         */
        fun removeAllCookie(callback: ValueCallback<Boolean>?): Boolean {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    CookieManager.getInstance().removeAllCookies(callback)
                } else {
                    CookieManager.getInstance().removeAllCookie()
                }
                return true
            } catch (e: Exception) {
                LogPrintUtils.eTag(TAG, e, "removeAllCookie")
            }
            return false
        }
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 设置 WebView
     * @param webView [WebView]
     * @return [WebViewAssist]
     * 如果在 [.setWebView] 前调用了 [.setBuilder] 则需要手动调用 [.apply]
     */
    fun setWebView(webView: WebView?): WebViewAssist {
        mWebView = webView
        return this
    }

    /**
     * 获取 WebView
     * @return [WebView]
     */
    fun getWebView(): WebView? {
        return mWebView
    }

    /**
     * WebView 是否不为 null
     * @return `true` yes, `false` no
     */
    fun isWebViewNotEmpty(): Boolean {
        return mWebView != null
    }

    /**
     * 设置 WebView 常用配置构建类
     * @param builder [Builder]
     * @return [WebViewAssist]
     */
    fun setBuilder(builder: Builder?): WebViewAssist {
        return setBuilder(builder, true)
    }

    /**
     * 设置 WebView 常用配置构建类
     * @param builder [Builder]
     * @param apply   是否应用配置
     * @return [WebViewAssist]
     */
    fun setBuilder(
        builder: Builder?,
        apply: Boolean
    ): WebViewAssist {
        mBuilder = builder
        mBuilder?.let {
            it.setWebViewAssist(this)
            if (apply) {
                it.apply()
            }
        }
        return this
    }

    /**
     * 获取 WebView 常用配置构建类
     * @return [Builder]
     */
    fun getBuilder(): Builder? {
        return mBuilder
    }

    /**
     * 应用 ( 设置 ) 配置
     * @return [Builder]
     */
    fun apply(): WebViewAssist {
        return setBuilder(mBuilder)
    }

    // ==========
    // = 加载方法 =
    // ==========

    /**
     * 加载网页
     * @param url 资源地址
     * @return [WebViewAssist]
     * 加载一个网页
     * loadUrl("http://www.baidu.com")
     * 加载应用资源文件内的网页
     * loadUrl("file:///android_asset/test.html")
     */
    fun loadUrl(url: String): WebViewAssist {
        mWebView?.loadUrl(url)
        return this
    }

    /**
     * 加载网页
     * @param url                   资源地址
     * @param additionalHttpHeaders Http 请求头信息
     * @return [WebViewAssist]
     */
    fun loadUrl(
        url: String,
        additionalHttpHeaders: Map<String?, String?>
    ): WebViewAssist {
        mWebView?.loadUrl(url, additionalHttpHeaders)
        return this
    }

    /**
     * 加载 Html 代码
     * @param data     Html 数据
     * @param mimeType 加载网页的类型
     * @param encoding 编码格式
     * @return [WebViewAssist]
     */
    @Deprecated("推荐使用 loadDataWithBaseURL")
    fun loadData(
        data: String,
        mimeType: String?,
        encoding: String?
    ): WebViewAssist {
        mWebView?.loadData(data, mimeType, encoding)
        return this
    }

    /**
     * 加载 Html 代码
     * @param baseUrl    基础链接
     * @param data       Html 数据
     * @param mimeType   加载网页的类型
     * @param encoding   编码格式
     * @param historyUrl 可用历史记录
     * @return [WebViewAssist]
     */
    fun loadDataWithBaseURL(
        baseUrl: String?,
        data: String,
        mimeType: String?,
        encoding: String?,
        historyUrl: String?
    ): WebViewAssist {
        mWebView?.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl)
        return this
    }

    /**
     * 使用 POST 方法将带有 postData 的 url 加载到 WebView 中
     * @param url      资源地址
     * @param postData post 数据 ( 注意 UrlEncode )
     * @return [WebViewAssist]
     * 如果 url 不是网络 url [.loadUrl] 加载
     */
    fun postUrl(
        url: String,
        postData: ByteArray
    ): WebViewAssist {
        mWebView?.postUrl(url, postData)
        return this
    }

    // =

    /**
     * 加载 Html 代码
     * @param data Html 数据
     * @return [WebViewAssist]
     */
    fun loadDataWithBaseURL(data: String): WebViewAssist {
        return loadDataWithBaseURL(null, data, null)
    }

    /**
     * 加载 Html 代码
     * @param baseUrl    基础链接
     * @param data       Html 数据
     * @param historyUrl 可用历史记录
     * @return [WebViewAssist]
     */
    fun loadDataWithBaseURL(
        baseUrl: String?,
        data: String,
        historyUrl: String?
    ): WebViewAssist {
        return loadDataWithBaseURL(baseUrl, data, "text/html", DevFinal.ENCODE.UTF_8, historyUrl)
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 获取 WebView 配置
     * @return [WebSettings]
     */
    fun getSettings(): WebSettings? {
        return mWebView?.settings
    }

    /**
     * 获取浏览器标识 UA
     * @return 浏览器标识 UA
     */
    fun getUserAgentString(): String? {
        val webSettings = getSettings()
        return webSettings?.userAgentString
    }

    /**
     * 设置浏览器标识
     * @param ua 浏览器标识
     * @return [WebViewAssist]
     */
    fun setUserAgentString(ua: String?): WebViewAssist {
        val webSettings = getSettings()
        webSettings?.userAgentString = ua
        return this
    }

    // =

    /**
     * 添加 JS 交互注入对象
     * @param obj           JavaScript 交互方法注入对象
     * @param interfaceName 在 JavaScript 中公开对象的名称
     * @return [WebViewAssist]
     */
    fun addJavascriptInterface(
        obj: Any,
        interfaceName: String
    ): WebViewAssist {
        mWebView?.addJavascriptInterface(obj, interfaceName)
        return this
    }

    /**
     * 移除 JS 交互注入对象
     * @param interfaceName 在 JavaScript 中公开对象的名称
     * @return [WebViewAssist]
     */
    fun removeJavascriptInterface(interfaceName: String): WebViewAssist {
        mWebView?.removeJavascriptInterface(interfaceName)
        return this
    }

    /**
     * 执行 JS 方法
     * @param script   JS 内容
     * @param callback 执行回调结果 ( 返回值 )
     * @return [WebViewAssist]
     */
    fun evaluateJavascript(
        script: String,
        callback: ValueCallback<String>?
    ): WebViewAssist {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView?.let {
                try {
                    it.evaluateJavascript(script, callback)
                } catch (e: java.lang.Exception) {
                    LogPrintUtils.eTag(TAG, e, "evaluateJavascript")
                }
            }
        }
        return this
    }

    // =

    /**
     * 设置处理各种通知和请求事件对象
     * @param client [WebViewClient]
     * @return [WebViewAssist]
     * 常用方法
     * onPageStarted()onPageFinished() 页面加载时和页面加载完毕时调用
     * shouldOverrideKeyEvent() 重写此方法才能处理浏览器中的按键事件
     * shouldInterceptRequest() 页面每一次请求资源之前都会调用这个方法 ( 非 UI 线程调用 )
     * onLoadResource() 页面加载资源时调用, 每加载一个资源 ( 比如图片 ) 就调用一次
     * onReceivedError() 加载页面的服务器出现错误 ( 比如 404 ) 时回调
     * onReceivedSslError() 重写此方法可以让 WebView 处理 https 请求
     * doUpdateVisitedHistory() 更新历史记录
     * onFormResubmission() 应用程序重新请求网页数据
     * onReceivedHttpAuthRequest() 获取返回信息授权请求
     * onScaleChanged() WebView 发生缩放改变时调用
     * onUnhandledKeyEvent() Key 事件未被加载时调用
     */
    fun setWebViewClient(client: WebViewClient): WebViewAssist {
        mWebView?.webViewClient = client
        return this
    }

    /**
     * 获取处理各种通知和请求事件对象
     * @return [WebViewClient]
     */
    fun getWebViewClient(): WebViewClient? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return mWebView?.webViewClient
        }
        return null
    }

    // =

    /**
     * 设置辅助 WebView 处理 Javascript 对话框、标题等对象
     * @param client [WebChromeClient]
     * @return [WebViewAssist]
     * 常用方法
     * onProgressChanged() 获得网页的加载进度并显示
     * onReceivedTitle() 获得网页的标题时回调
     * onReceivedIcon() 获得网页的图标时回调
     * onCreateWindow() 打开新窗口时回调
     * onCloseWindow() 关闭窗口时回调
     * onJsAlert() 网页弹出提示框时触发此方法
     * onJsConfirm() 网页弹出确认框时触发此方法
     * onJsPrompt() 网页弹出输入框时触发此方法
     */
    fun setWebChromeClient(client: WebChromeClient?): WebViewAssist {
        mWebView?.webChromeClient = client
        return this
    }

    /**
     * 获取辅助 WebView 处理 Javascript 对话框、标题等对象
     * @return [WebChromeClient]
     */
    fun getWebChromeClient(): WebChromeClient? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return mWebView?.webChromeClient
        }
        return null
    }

    // =

    /**
     * 销毁处理
     * @return [WebViewAssist]
     * 避免 WebView 引起的内存泄漏
     * 可通过 WebView 所在的 Activity 新启一个进程结束时 System.exit(0) 退出当前进程
     * Activity onDestroy use
     */
    fun destroy(): WebViewAssist {
        mWebView?.let {
            it.clearHistory()
            ViewUtils.removeSelfFromParent(mWebView)
            it.loadUrl("about:blank")
            it.stopLoading()
            it.webChromeClient = null
            it.destroy()
            mWebView = null
            mBuilder?.setWebViewAssist(null)
        }
        return this
    }

    /**
     * WebView 是否可以后退
     * @return `true` yes, `false` no
     */
    fun canGoBack(): Boolean {
        return mWebView?.canGoBack() ?: false
    }

    /**
     * WebView 后退
     * @return [WebViewAssist]
     */
    fun goBack(): WebViewAssist {
        mWebView?.goBack()
        return this
    }

    // =

    /**
     * WebView 是否可以前进
     * @return `true` yes, `false` no
     */
    fun canGoForward(): Boolean {
        return mWebView?.canGoForward() ?: false
    }

    /**
     * WebView 前进
     * @return [WebViewAssist]
     */
    fun goForward(): WebViewAssist {
        mWebView?.goForward()
        return this
    }

    // =

    /**
     * WebView 是否可以跳转到当前起始点相距的历史记录
     * @param steps 相距索引
     * @return `true` yes, `false` no
     */
    fun canGoBackOrForward(steps: Int): Boolean {
        return mWebView?.canGoBackOrForward(steps) ?: false
    }

    /**
     * WebView 跳转到当前起始点相距的历史记录
     * @param steps 相距索引
     * @return [WebViewAssist]
     * 以当前的 index 为起始点前进或者后退到历史记录中指定的 steps
     * 如果 steps 为负数则为后退, 正数则为前进
     */
    fun goBackOrForward(steps: Int): WebViewAssist {
        mWebView?.goBackOrForward(steps)
        return this
    }

    // =

    /**
     * 刷新页面 ( 当前页面的所有资源都会重新加载 )
     * @return [WebViewAssist]
     */
    fun reload(): WebViewAssist {
        mWebView?.reload()
        return this
    }

    /**
     * 停止加载
     * @return [WebViewAssist]
     */
    fun stopLoading(): WebViewAssist {
        mWebView?.stopLoading()
        return this
    }

    /**
     * 清除资源缓存
     * @param includeDiskFiles 是否清空本地缓存 ( false 则只会清空内存缓存, true 全部清空 )
     * @return [WebViewAssist]
     * 缓存是针对每个应用程序的, 因此这将清除所有使用的 WebView 的缓存
     */
    fun clearCache(includeDiskFiles: Boolean): WebViewAssist {
        mWebView?.clearCache(includeDiskFiles)
        return this
    }

    /**
     * 清除当前 WebView 访问的历史记录
     * @return [WebViewAssist]
     */
    fun clearHistory(): WebViewAssist {
        mWebView?.clearHistory()
        return this
    }

    /**
     * 清除自动完成填充的表单数据
     * @return [WebViewAssist]
     * 并不会清除 WebView 存储到本地的数据
     */
    fun clearFormData(): WebViewAssist {
        mWebView?.clearFormData()
        return this
    }

    /**
     * 获取缩放比例
     * @return 缩放比例
     */
    fun getScale(): Float {
        return mWebView?.scale ?: 1.0F
    }

    /**
     * 获取当前可见区域的顶端距整个页面顶端的距离 ( 当前内容滚动的距离 )
     * @return 当前内容滚动的距离
     */
    fun getScrollY(): Int {
        return mWebView?.scrollY ?: 0
    }

    /**
     * 获取当前内容横向滚动距离
     * @return 当前内容横向滚动距离
     */
    fun getScrollX(): Int {
        return mWebView?.scrollX ?: 0
    }

    /**
     * 获取 HTML 的高度 ( 原始高度, 不包括缩放后的高度 )
     * @return HTML 的高度 ( 原始高度, 不包括缩放后的高度 )
     * 可通过 setWebViewClient onScaleChanged(WebView view, float oldScale, float newScale) 获取缩放比例
     * 或者通过 webView.getScale() 获取 ( 该方法已抛弃 )
     */
    fun getContentHeight(): Int {
        return mWebView?.contentHeight ?: 0
    }

    /**
     * 获取缩放高度
     * @return 缩放高度
     */
    fun getScaleHeight(): Int {
        return getScaleHeight(getScale())
    }

    /**
     * 获取缩放高度
     * @param scale 缩放比例
     * @return 缩放高度
     */
    fun getScaleHeight(scale: Float): Int {
        return (getContentHeight() * scale).toInt()
    }

    /**
     * 获取 WebView 控件高度
     * @return WebView 控件高度
     */
    fun getHeight(): Int {
        return mWebView?.height ?: 0
    }

    /**
     * 将视图内容向下滚动一半页面大小
     * @param bottom 是否滑动到底部
     * @return [WebViewAssist]
     */
    fun pageDown(bottom: Boolean): WebViewAssist {
        mWebView?.pageDown(bottom)
        return this
    }

    /**
     * 将视图内容向上滚动一半页面大小
     * @param top 是否滑动到顶部
     * @return [WebViewAssist]
     */
    fun pageUp(top: Boolean): WebViewAssist {
        mWebView?.pageUp(top)
        return this
    }

    /**
     * 处理按键 ( 是否回退 )
     * @param keyCode 按键类型
     * @param event   按键事件
     * @return `true` 拦截事件, `false` 不拦截接着处理
     * Activity use
     * @Override
     * public boolean onKeyDown(int keyCode, KeyEvent event) {
     *      if (webViewAssist.handlerKeyDown(keyCode, event)) return true
     *      return super.onKeyDown(keyCode, event)
     * }
     */
    fun handlerKeyDown(
        keyCode: Int,
        event: KeyEvent?
    ): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && canGoBack()) {
            goBack()
            return true
        }
        return false
    }

    /**
     * 关闭 WebView 硬件加速功能
     * @return [WebViewAssist]
     * 解决 WebView 闪烁问题
     */
    fun setLayerTypeSoftware(): WebViewAssist {
        return setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    /**
     * 设置 WebView 硬件加速类型
     * @param layerType 硬件加速类型
     * @param paint     [Paint]
     * @return [WebViewAssist]
     */
    fun setLayerType(
        layerType: Int,
        paint: Paint?
    ): WebViewAssist {
        mWebView?.setLayerType(layerType, paint)
        return this
    }

    /**
     * 获取当前 Url
     * @return 当前 Url
     */
    fun getUrl(): String? {
        return mWebView?.url
    }

    /**
     * 获取最初请求 Url
     * @return 最初请求 Url
     */
    fun getOriginalUrl(): String? {
        return mWebView?.originalUrl
    }

    /**
     * 获取长按事件类型
     * @return 长按事件类型
     * WebView.HitTestResult.UNKNOWN_TYPE // 未知类型
     * WebView.HitTestResult.PHONE_TYPE // 电话类型
     * WebView.HitTestResult.EMAIL_TYPE // 电子邮件类型
     * WebView.HitTestResult.GEO_TYPE // 地图类型
     * WebView.HitTestResult.SRC_ANCHOR_TYPE // 超链接类型
     * WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE // 带有链接的图片类型
     * WebView.HitTestResult.IMAGE_TYPE // 单纯的图片类型
     * WebView.HitTestResult.EDIT_TEXT_TYPE // 选中的文字类型
     * <p></p>
     * mWebView.setOnLongClickListener(new View.OnLongClickListener() {
     *     @Override
     *     public boolean onLongClick(View view) {
     *         WebView.HitTestResult result = webViewAssist.getHitTestResult()
     *         if(result != null) {
     *             switch (result.getType()) {
     *                 case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
     *                     String imgUrl = result.getExtra()
     *                     return true
     *             }
     *         }
     *         return false
     *     }
     * })
     * <p></p>
     * HitTestResult.getType() 获取所选中目标的类型, 可以是图片、超链接、邮件、电话等等
     * HitTestResult.getExtra() 获取额外的信息
     */
    fun getHitTestResult(): HitTestResult? {
        return mWebView?.hitTestResult
    }

    // ==========
    // = 配置相关 =
    // ==========

    /**
     * detail: WebView 常用配置构建类
     * @author Ttt
     * 有特殊配置可在 [OnApplyListener.onApply] 回调中进行增加配置设置
     */
    open class Builder @JvmOverloads constructor(listener: OnApplyListener? = null) {

        // WebView 辅助类
        private var mWebViewAssist: WebViewAssist? = null

        // 应用配置监听事件
        private var mApplyListener: OnApplyListener? = null

        init {
            /**
             * listener 应用配置监听事件
             */
            setOnApplyListener(listener)
        }

        // =

        /**
         * 设置 WebView 辅助类
         * @param webViewAssist WebView 辅助类
         * @return [Builder]
         */
        internal fun setWebViewAssist(webViewAssist: WebViewAssist?): Builder {
            mWebViewAssist = webViewAssist
            return this
        }

        /**
         * 应用 ( 设置 ) 配置
         * @return [Builder]
         */
        fun apply(): Builder {
            return applyPri()
        }

        // =======
        // = 事件 =
        // =======

        /**
         * 设置应用配置监听事件
         * @param listener [OnApplyListener]
         * @return [Builder]
         */
        fun setOnApplyListener(listener: OnApplyListener?): Builder {
            mApplyListener = listener
            return this
        }

        /**
         * 获取应用配置监听事件
         * @return [OnApplyListener]
         */
        fun getApplyListener(): OnApplyListener? {
            return mApplyListener
        }

        /**
         * detail: 应用配置监听事件
         * @author Ttt
         */
        interface OnApplyListener {

            /**
             * 应用配置通知方法
             * @param webViewAssist WebView 辅助类
             * @param builder       WebView 常用配置构建类
             */
            fun onApply(
                webViewAssist: WebViewAssist?,
                builder: Builder
            )
        }

        // ==========
        // = 克隆方法 =
        // ==========

        /**
         * 克隆方法 ( 用于全局配置克隆操作 )
         * @param listener 是否复用监听事件
         * @return [Builder]
         */
        fun clone(listener: Boolean): Builder {
            val builder = Builder()
            if (listener) { // 复用监听事件
                builder.setOnApplyListener(mApplyListener)
            }
            builder.mJavaScriptEnabled = mJavaScriptEnabled
            builder.mRenderPriority = mRenderPriority
            builder.mUseWideViewPort = mUseWideViewPort
            builder.mLoadWithOverviewMode = mLoadWithOverviewMode
            builder.mLayoutAlgorithm = mLayoutAlgorithm
            builder.mSupportZoom = mSupportZoom
            builder.mBuiltInZoomControls = mBuiltInZoomControls
            builder.mDisplayZoomControls = mDisplayZoomControls
            builder.mTextZoom = mTextZoom
            builder.mStandardFontFamily = mStandardFontFamily
            builder.mDefaultFontSize = mDefaultFontSize
            builder.mMinimumFontSize = mMinimumFontSize
            builder.mMixedContentMode = mMixedContentMode
            builder.mLoadsImagesAutomatically = mLoadsImagesAutomatically
            builder.mJavaScriptCanOpenWindowsAutomatically = mJavaScriptCanOpenWindowsAutomatically
            builder.mDefaultTextEncodingName = mDefaultTextEncodingName
            builder.mGeolocationEnabled = mGeolocationEnabled
            builder.mUserAgentString = mUserAgentString
            builder.mAllowFileAccess = mAllowFileAccess
            builder.mAllowFileAccessFromFileURLs = mAllowFileAccessFromFileURLs
            builder.mAllowUniversalAccessFromFileURLs = mAllowUniversalAccessFromFileURLs
            builder.mCacheMode = mCacheMode
            builder.mDomStorageEnabled = mDomStorageEnabled
            builder.mAppCacheEnabled = mAppCacheEnabled
            builder.mAppCachePath = mAppCachePath
            builder.mAppCacheMaxSize = mAppCacheMaxSize
            builder.mDatabaseEnabled = mDatabaseEnabled
            builder.mDatabasePath = mDatabasePath
            return builder
        }

        /**
         * 重置方法
         * @return [Builder]
         */
        fun reset(): Builder {
            this.mJavaScriptEnabled = true
            this.mRenderPriority = null
            this.mUseWideViewPort = false
            this.mLoadWithOverviewMode = true
            this.mLayoutAlgorithm = null
            this.mSupportZoom = true
            this.mBuiltInZoomControls = false
            this.mDisplayZoomControls = false
            this.mTextZoom = 0
            this.mStandardFontFamily = null
            this.mDefaultFontSize = 0
            this.mMinimumFontSize = 0
            this.mMixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            this.mLoadsImagesAutomatically = true
            this.mJavaScriptCanOpenWindowsAutomatically = true
            this.mDefaultTextEncodingName = DevFinal.ENCODE.UTF_8
            this.mGeolocationEnabled = true
            this.mUserAgentString = null
            this.mAllowFileAccess = true
            this.mAllowFileAccessFromFileURLs = false
            this.mAllowUniversalAccessFromFileURLs = false
            this.mCacheMode = WebSettings.LOAD_NO_CACHE
            this.mDomStorageEnabled = true
            this.mAppCacheEnabled = true
            this.mAppCachePath = null
            this.mAppCacheMaxSize = 5 * 1024 * 1024
            this.mDatabaseEnabled = true
            this.mDatabasePath = null
            return this
        }

        // ==========
        // = 配置方法 =
        // ==========

        // 是否支持 JavaScript
        private var mJavaScriptEnabled = true

        // 渲染优先级
        private var mRenderPriority: RenderPriority? = null

        // 是否使用宽视图 ( 是否支持 html viewport 设置了会导致字体变小 )
        private var mUseWideViewPort = false

        // 是否按宽度缩小内容以适合屏幕
        private var mLoadWithOverviewMode = true

        // 基础布局算法, 大于 4.4 使用 TEXT_AUTOSIZING 解决前端 REM 适配方案有误差情况
        private var mLayoutAlgorithm: LayoutAlgorithm? = null

        // 是否支持缩放
        private var mSupportZoom = true

        // 是否显示内置缩放工具
        private var mBuiltInZoomControls = false

        // 是否显示缩放工具
        private var mDisplayZoomControls = false

        // 文本缩放倍数
        private var mTextZoom = 0

        // WebView 字体, 默认字体 "sans-serif"
        private var mStandardFontFamily: String? = null

        // WebView 字体大小
        private var mDefaultFontSize = 0

        // WebView 支持最小字体大小
        private var mMinimumFontSize = 0

        // 混合内容模式
        private var mMixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        // 是否支持自动加载图片
        private var mLoadsImagesAutomatically = true

        // 是否支持通过 JS 打开新窗口
        private var mJavaScriptCanOpenWindowsAutomatically = true

        // 编码格式
        private var mDefaultTextEncodingName: String? = DevFinal.ENCODE.UTF_8

        // 是否允许网页执行定位操作
        private var mGeolocationEnabled = true

        // 浏览器标识 UA
        private var mUserAgentString: String? = null

        // 是否可以访问文件 ( false 不影响 assets 和 resources 资源的加载 )
        private var mAllowFileAccess = true

        // 是否允许通过 file url 加载的 JS 代码读取其他的本地文件
        private var mAllowFileAccessFromFileURLs = false

        // 是否允许通过 file url 加载的 JS 可以访问其他的源 ( 包括 http、https 等源 )
        private var mAllowUniversalAccessFromFileURLs = false

        // WebView 缓存模式
        private var mCacheMode = WebSettings.LOAD_NO_CACHE

        // 是否支持 DOM Storage
        private var mDomStorageEnabled = true

        // 是否开启 Application Caches 功能
        private var mAppCacheEnabled = true

        // Application Caches 地址
        private var mAppCachePath: String? = null

        // Application Caches 大小
        private var mAppCacheMaxSize = (5 * 1024 * 1024).toLong()

        // 是否支持数据库缓存
        private var mDatabaseEnabled = true

        // 数据库缓存路径
        private var mDatabasePath: String? = null

        /**
         * 应用 ( 设置 ) 配置
         * @return [Builder]
         */
        private fun applyPri(): Builder {
            mWebViewAssist?.let { assist ->
                assist.getSettings()?.let { webSettings ->
                    // 如果访问的页面中要与 JavaScript 交互, 则 WebView 必须设置支持 JavaScript
                    webSettings.javaScriptEnabled = mJavaScriptEnabled
                    if (mRenderPriority != null) {
                        webSettings.setRenderPriority(mRenderPriority) // 设置渲染优先级
                    }

                    // 设置自适应屏幕两者合用
                    webSettings.useWideViewPort = mUseWideViewPort // 是否使用宽视图
                    webSettings.loadWithOverviewMode = mLoadWithOverviewMode // 是否按宽度缩小内容以适合屏幕
                    if (mLayoutAlgorithm != null) { // WebSettings.LayoutAlgorithm.SINGLE_COLUMN 4.4 抛弃了
                        // 大于 4.4 使用 TEXT_AUTOSIZING 解决前端 REM 适配方案有误差情况
                        webSettings.layoutAlgorithm = mLayoutAlgorithm
                    }

                    // 缩放操作
                    webSettings.setSupportZoom(mSupportZoom) // 是否支持缩放
                    webSettings.builtInZoomControls =
                        mBuiltInZoomControls // 是否显示内置缩放工具, 若为 false 则该 WebView 不可缩放
                    webSettings.displayZoomControls = mDisplayZoomControls // 是否显示缩放工具
                    if (mTextZoom > 0) {
                        webSettings.textZoom = mTextZoom // 文本缩放倍数
                    }
                    if (mStandardFontFamily != null) {
                        webSettings.standardFontFamily = mStandardFontFamily // 设置 WebView 字体
                    }
                    if (mDefaultFontSize > 0) {
                        webSettings.defaultFontSize = mDefaultFontSize // 设置 WebView 字体大小
                    }
                    if (mMinimumFontSize > 0) {
                        webSettings.minimumFontSize = mMinimumFontSize // 设置 WebView 支持最小字体大小
                    }

                    // 5.0 以上默认禁止了 https 和 http 混用以下方式是开启
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        webSettings.mixedContentMode = mMixedContentMode
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        webSettings.loadsImagesAutomatically =
                            mLoadsImagesAutomatically // 是否支持自动加载图片
                    }

                    // 是否支持通过 JS 打开新窗口
                    webSettings.javaScriptCanOpenWindowsAutomatically =
                        mJavaScriptCanOpenWindowsAutomatically
                    // 设置编码格式
                    if (mDefaultTextEncodingName != null) {
                        webSettings.defaultTextEncodingName = mDefaultTextEncodingName
                    }
                    // 是否允许网页执行定位操作
                    webSettings.setGeolocationEnabled(mGeolocationEnabled)
                    // 设置浏览器标识 UA
                    if (mUserAgentString != null) {
                        webSettings.userAgentString = mUserAgentString
                    }

                    // 是否可以访问文件
                    webSettings.allowFileAccess = mAllowFileAccess
                    // 是否允许通过 file url 加载的 JS 代码读取其他的本地文件
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        webSettings.allowFileAccessFromFileURLs = mAllowFileAccessFromFileURLs
                    }
                    // 是否允许通过 file url 加载的 JS 可以访问其他的源 ( 包括 http、https 等源 )
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        webSettings.allowUniversalAccessFromFileURLs =
                            mAllowUniversalAccessFromFileURLs
                    }
                    // 设置 WebView 缓存模式
                    if (mCacheMode > 0) {
                        // LOAD_CACHE_ONLY 不使用网络, 只读取本地缓存数据
                        // LOAD_DEFAULT ( 默认 ) 根据 cache-control 决定是否从网络上取数据
                        // LOAD_NO_CACHE 不使用缓存, 只从网络获取数据.
                        // LOAD_CACHE_ELSE_NETWORK 只要本地有, 无论是否过期或者 no-cache 都使用缓存中的数据
                        webSettings.cacheMode = mCacheMode
                    }

                    // 是否支持 DOM Storage
                    webSettings.domStorageEnabled = mDomStorageEnabled
                    // 是否开启 Application Caches 功能
                    webSettings.setAppCacheEnabled(mAppCacheEnabled)
                    if (mAppCacheEnabled) {
                        // Application Caches 地址
                        if (mAppCachePath != null) {
                            webSettings.setAppCachePath(mAppCachePath)
                        }
                        // Application Caches 大小
                        webSettings.setAppCacheMaxSize(mAppCacheMaxSize)
                    }

                    // 是否支持数据库缓存
                    webSettings.databaseEnabled = mDatabaseEnabled
                    if (mDatabaseEnabled) {
                        // 数据库缓存路径
                        if (mDatabasePath != null) {
                            webSettings.databasePath = mDatabasePath
                        }
                    }
                }
            }
            mApplyListener?.onApply(mWebViewAssist, this)
            return this
        }

        // ===========
        // = get/set =
        // ===========

        /**
         * 是否支持 JavaScript
         * @return `true` yes, `false` no
         */
        fun isJavaScriptEnabled(): Boolean {
            return mJavaScriptEnabled
        }

        /**
         * 设置是否支持 JavaScript
         * @param javaScriptEnabled `true` yes, `false` no
         * @return [Builder]
         */
        fun setJavaScriptEnabled(javaScriptEnabled: Boolean): Builder {
            mJavaScriptEnabled = javaScriptEnabled
            return this
        }

        /**
         * 获取渲染优先级
         * @return 渲染优先级
         */
        fun getRenderPriority(): RenderPriority? {
            return mRenderPriority
        }

        /**
         * 设置渲染优先级
         * @param renderPriority 渲染优先级
         * @return [Builder]
         */
        fun setRenderPriority(renderPriority: RenderPriority?): Builder {
            mRenderPriority = renderPriority
            return this
        }

        /**
         * 是否使用宽视图
         * @return `true` yes, `false` no
         */
        fun isUseWideViewPort(): Boolean {
            return mUseWideViewPort
        }

        /**
         * 设置是否使用宽视图
         * @param useWideViewPort `true` yes, `false` no
         * @return [Builder]
         * 是否支持 html viewport 设置了会导致字体变小
         */
        fun setUseWideViewPort(useWideViewPort: Boolean): Builder {
            mUseWideViewPort = useWideViewPort
            return this
        }

        /**
         * 是否按宽度缩小内容以适合屏幕
         * @return `true` yes, `false` no
         */
        fun isLoadWithOverviewMode(): Boolean {
            return mLoadWithOverviewMode
        }

        /**
         * 设置是否按宽度缩小内容以适合屏幕
         * @param loadWithOverviewMode `true` yes, `false` no
         * @return [Builder]
         */
        fun setLoadWithOverviewMode(loadWithOverviewMode: Boolean): Builder {
            mLoadWithOverviewMode = loadWithOverviewMode
            return this
        }

        /**
         * 获取基础布局算法
         * @return 基础布局算法
         */
        fun getLayoutAlgorithm(): LayoutAlgorithm? {
            return mLayoutAlgorithm
        }

        /**
         * 设置基础布局算法
         * @param layoutAlgorithm 基础布局算法
         * @return [Builder]
         */
        fun setLayoutAlgorithm(layoutAlgorithm: LayoutAlgorithm?): Builder {
            mLayoutAlgorithm = layoutAlgorithm
            return this
        }

        /**
         * 是否支持缩放
         * @return `true` yes, `false` no
         */
        fun isSupportZoom(): Boolean {
            return mSupportZoom
        }

        /**
         * 设置是否支持缩放
         * @param supportZoom `true` yes, `false` no
         * @return [Builder]
         */
        fun setSupportZoom(supportZoom: Boolean): Builder {
            mSupportZoom = supportZoom
            return this
        }

        /**
         * 是否显示内置缩放工具
         * @return `true` yes, `false` no
         */
        fun isBuiltInZoomControls(): Boolean {
            return mBuiltInZoomControls
        }

        /**
         * 设置是否显示内置缩放工具
         * @param builtInZoomControls `true` yes, `false` no
         * @return [Builder]
         */
        fun setBuiltInZoomControls(builtInZoomControls: Boolean): Builder {
            mBuiltInZoomControls = builtInZoomControls
            return this
        }

        /**
         * 是否显示缩放工具
         * @return `true` yes, `false` no
         */
        fun isDisplayZoomControls(): Boolean {
            return mDisplayZoomControls
        }

        /**
         * 设置是否显示缩放工具
         * @param displayZoomControls `true` yes, `false` no
         * @return [Builder]
         */
        fun setDisplayZoomControls(displayZoomControls: Boolean): Builder {
            mDisplayZoomControls = displayZoomControls
            return this
        }

        /**
         * 获取文本缩放倍数
         * @return 文本缩放倍数
         */
        fun getTextZoom(): Int {
            return mTextZoom
        }

        /**
         * 设置文本缩放倍数
         * @param textZoom 文本缩放倍数
         * @return [Builder]
         */
        fun setTextZoom(textZoom: Int): Builder {
            mTextZoom = textZoom
            return this
        }

        /**
         * 获取 WebView 字体
         * @return WebView 字体
         */
        fun getStandardFontFamily(): String? {
            return mStandardFontFamily
        }

        /**
         * 设置 WebView 字体
         * @param standardFontFamily WebView 字体
         * @return [Builder]
         */
        fun setStandardFontFamily(standardFontFamily: String?): Builder {
            mStandardFontFamily = standardFontFamily
            return this
        }

        /**
         * 获取 WebView 字体大小
         * @return WebView 字体大小
         */
        fun getDefaultFontSize(): Int {
            return mDefaultFontSize
        }

        /**
         * 设置 WebView 字体大小
         * @param defaultFontSize WebView 字体大小
         * @return [Builder]
         */
        fun setDefaultFontSize(defaultFontSize: Int): Builder {
            mDefaultFontSize = defaultFontSize
            return this
        }

        /**
         * 获取 WebView 支持最小字体大小
         * @return WebView 支持最小字体大小
         */
        fun getMinimumFontSize(): Int {
            return mMinimumFontSize
        }

        /**
         * 设置 WebView 支持最小字体大小
         * @param minimumFontSize WebView 支持最小字体大小
         * @return [Builder]
         */
        fun setMinimumFontSize(minimumFontSize: Int): Builder {
            mMinimumFontSize = minimumFontSize
            return this
        }

        /**
         * 获取混合内容模式
         * @return 混合内容模式
         */
        fun getMixedContentMode(): Int {
            return mMixedContentMode
        }

        /**
         * 设置混合内容模式
         * @param mixedContentMode 混合内容模式
         * @return [Builder]
         */
        fun setMixedContentMode(mixedContentMode: Int): Builder {
            mMixedContentMode = mixedContentMode
            return this
        }

        /**
         * 是否支持自动加载图片
         * @return `true` yes, `false` no
         */
        fun isLoadsImagesAutomatically(): Boolean {
            return mLoadsImagesAutomatically
        }

        /**
         * 设置是否支持自动加载图片
         * @param loadsImagesAutomatically `true` yes, `false` no
         * @return [Builder]
         */
        fun setLoadsImagesAutomatically(loadsImagesAutomatically: Boolean): Builder {
            mLoadsImagesAutomatically = loadsImagesAutomatically
            return this
        }

        /**
         * 是否支持通过 JS 打开新窗口
         * @return `true` yes, `false` no
         */
        fun isJavaScriptCanOpenWindowsAutomatically(): Boolean {
            return mJavaScriptCanOpenWindowsAutomatically
        }

        /**
         * 设置是否支持通过 JS 打开新窗口
         * @param javaScriptCanOpenWindowsAutomatically `true` yes, `false` no
         * @return [Builder]
         */
        fun setJavaScriptCanOpenWindowsAutomatically(javaScriptCanOpenWindowsAutomatically: Boolean): Builder {
            mJavaScriptCanOpenWindowsAutomatically = javaScriptCanOpenWindowsAutomatically
            return this
        }

        /**
         * 获取编码格式
         * @return 编码格式
         */
        fun getDefaultTextEncodingName(): String? {
            return mDefaultTextEncodingName
        }

        /**
         * 设置编码格式
         * @param defaultTextEncodingName 编码格式
         * @return [Builder]
         */
        fun setDefaultTextEncodingName(defaultTextEncodingName: String?): Builder {
            mDefaultTextEncodingName = defaultTextEncodingName
            return this
        }

        /**
         * 是否允许网页执行定位操作
         * @return `true` yes, `false` no
         */
        fun isGeolocationEnabled(): Boolean {
            return mGeolocationEnabled
        }

        /**
         * 设置是否允许网页执行定位操作
         * @param geolocationEnabled `true` yes, `false` no
         * @return [Builder]
         */
        fun setGeolocationEnabled(geolocationEnabled: Boolean): Builder {
            mGeolocationEnabled = geolocationEnabled
            return this
        }

        /**
         * 获取浏览器标识 UA
         * @return 浏览器标识 UA
         */
        fun getUserAgentString(): String? {
            return mUserAgentString
        }

        /**
         * 设置浏览器标识 UA
         * @param userAgentString 浏览器标识 UA
         * @return [Builder]
         */
        fun setUserAgentString(userAgentString: String?): Builder {
            mUserAgentString = userAgentString
            return this
        }

        /**
         * 是否可以访问文件 ( false 不影响 assets 和 resources 资源的加载 )
         * @return `true` yes, `false` no
         */
        fun isAllowFileAccess(): Boolean {
            return mAllowFileAccess
        }

        /**
         * 设置是否可以访问文件 ( false 不影响 assets 和 resources 资源的加载 )
         * @param allowFileAccess `true` yes, `false` no
         * @return [Builder]
         */
        fun setAllowFileAccess(allowFileAccess: Boolean): Builder {
            mAllowFileAccess = allowFileAccess
            return this
        }

        /**
         * 是否允许通过 file url 加载的 JS 代码读取其他的本地文件
         * @return `true` yes, `false` no
         */
        fun isAllowFileAccessFromFileURLs(): Boolean {
            return mAllowFileAccessFromFileURLs
        }

        /**
         * 设置是否允许通过 file url 加载的 JS 代码读取其他的本地文件
         * @param allowFileAccessFromFileURLs `true` yes, `false` no
         * @return [Builder]
         */
        fun setAllowFileAccessFromFileURLs(allowFileAccessFromFileURLs: Boolean): Builder {
            mAllowFileAccessFromFileURLs = allowFileAccessFromFileURLs
            return this
        }

        /**
         * 是否允许通过 file url 加载的 JS 可以访问其他的源 ( 包括 http、https 等源 )
         * @return `true` yes, `false` no
         */
        fun isAllowUniversalAccessFromFileURLs(): Boolean {
            return mAllowUniversalAccessFromFileURLs
        }

        /**
         * 设置是否允许通过 file url 加载的 JS 可以访问其他的源 ( 包括 http、https 等源 )
         * @param allowUniversalAccessFromFileURLs `true` yes, `false` no
         * @return [Builder]
         */
        fun setAllowUniversalAccessFromFileURLs(allowUniversalAccessFromFileURLs: Boolean): Builder {
            mAllowUniversalAccessFromFileURLs = allowUniversalAccessFromFileURLs
            return this
        }

        /**
         * 获取 WebView 缓存模式
         * @return WebView 缓存模式
         */
        fun getCacheMode(): Int {
            return mCacheMode
        }

        /**
         * 设置 WebView 缓存模式
         * @param cacheMode WebView 缓存模式
         * @return [Builder]
         */
        fun setCacheMode(cacheMode: Int): Builder {
            mCacheMode = cacheMode
            return this
        }

        /**
         * 是否支持 DOM Storage
         * @return `true` yes, `false` no
         */
        fun isDomStorageEnabled(): Boolean {
            return mDomStorageEnabled
        }

        /**
         * 设置是否支持 DOM Storage
         * @param domStorageEnabled `true` yes, `false` no
         * @return [Builder]
         */
        fun setDomStorageEnabled(domStorageEnabled: Boolean): Builder {
            mDomStorageEnabled = domStorageEnabled
            return this
        }

        /**
         * 是否开启 Application Caches 功能
         * @return `true` yes, `false` no
         */
        fun isAppCacheEnabled(): Boolean {
            return mAppCacheEnabled
        }

        /**
         * 设置是否开启 Application Caches 功能
         * @param appCacheEnabled `true` yes, `false` no
         * @return [Builder]
         */
        fun setAppCacheEnabled(appCacheEnabled: Boolean): Builder {
            mAppCacheEnabled = appCacheEnabled
            return this
        }

        /**
         * 获取 Application Caches 地址
         * @return Application Caches 地址
         */
        fun getAppCachePath(): String? {
            return mAppCachePath
        }

        /**
         * 设置 Application Caches 地址
         * @param appCachePath Application Caches 地址
         * @return [Builder]
         */
        fun setAppCachePath(appCachePath: String?): Builder {
            mAppCachePath = appCachePath
            return this
        }

        /**
         * 获取 Application Caches 大小
         * @return Application Caches 大小
         */
        fun getAppCacheMaxSize(): Long {
            return mAppCacheMaxSize
        }

        /**
         * 设置 Application Caches 大小
         * @param appCacheMaxSize Application Caches 大小
         * @return [Builder]
         */
        fun setAppCacheMaxSize(appCacheMaxSize: Long): Builder {
            mAppCacheMaxSize = appCacheMaxSize
            return this
        }

        /**
         * 是否支持数据库缓存
         * @return `true` yes, `false` no
         */
        fun isDatabaseEnabled(): Boolean {
            return mDatabaseEnabled
        }

        /**
         * 设置是否支持数据库缓存
         * @param databaseEnabled `true` yes, `false` no
         * @return [Builder]
         */
        fun setDatabaseEnabled(databaseEnabled: Boolean): Builder {
            mDatabaseEnabled = databaseEnabled
            return this
        }

        /**
         * 获取数据库缓存路径
         * @return 数据库缓存路径
         */
        fun getDatabasePath(): String? {
            return mDatabasePath
        }

        /**
         * 设置数据库缓存路径
         * @param databasePath 数据库缓存路径
         * @return [Builder]
         */
        fun setDatabasePath(databasePath: String?): Builder {
            mDatabasePath = databasePath
            return this
        }
    }
}