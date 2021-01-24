package dev.assist;

import android.graphics.Paint;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Map;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.ViewUtils;

/**
 * detail: WebView 辅助类
 * @author Ttt
 * <pre>
 *     WebView 截图使用 {@link dev.utils.app.CapturePictureUtils#snapshotByWebView(WebView)}
 *     <p></p>
 *     WebView 全面解析
 *     @see <a href="https://www.jianshu.com/p/3e0136c9e748"/>
 *     Android WebView 常见问题及解决方案汇总
 *     @see <a href="https://www.cnblogs.com/Free-Thinker/p/6179016.html"/>
 *     WebView 与 JavaScript 的交互总结
 *     @see <a href="https://www.jianshu.com/p/5cc2eae14e07"/>
 *     WebView 使用漏洞
 *     @see <a href="https://blog.csdn.net/carson_ho/article/details/64904635"/>
 *     Android Webview H5 交互之 LocalStorage
 *     @see <a href="https://www.jianshu.com/p/379a0681ce25"/>
 * </pre>
 */
public class WebViewAssist {

    // 日志 TAG
    private static final String  TAG = WebViewAssist.class.getSimpleName();
    // WebView
    private              WebView mWebView;
    // WebView 常用配置构建类
    private              Builder mBuilder;
    // 全局配置
    private static       Builder sGlobalBuilder;

    public WebViewAssist() {
        this(true);
    }

    /**
     * 构造函数
     * <pre>
     *     使用全局配置, 需要手动调用 {@link #apply} 方法
     * </pre>
     * @param listener 是否复用监听事件
     */
    public WebViewAssist(final boolean listener) {
        if (sGlobalBuilder != null) {
            mBuilder = sGlobalBuilder.clone(listener);
        }
    }

    /**
     * 设置 WebView
     * <pre>
     *     如果在 {@link #setWebView} 前调用了 {@link #setBuilder} 则需要手动调用 {@link #apply}
     * </pre>
     * @param webView {@link WebView}
     * @return {@link WebViewAssist}
     */
    public WebViewAssist setWebView(final WebView webView) {
        this.mWebView = webView;
        return this;
    }

    /**
     * 获取 WebView
     * @return {@link WebView}
     */
    public WebView getWebView() {
        return mWebView;
    }

    /**
     * WebView 是否不为 null
     * @return {@code true} yes, {@code false} no
     */
    public boolean isWebViewNotEmpty() {
        return mWebView != null;
    }

    /**
     * 设置 WebView 常用配置构建类
     * @param builder {@link Builder}
     * @return {@link WebViewAssist}
     */
    public WebViewAssist setBuilder(final Builder builder) {
        return setBuilder(builder, true);
    }

    /**
     * 设置 WebView 常用配置构建类
     * @param builder {@link Builder}
     * @param apply   是否应用配置
     * @return {@link WebViewAssist}
     */
    public WebViewAssist setBuilder(
            final Builder builder,
            final boolean apply
    ) {
        this.mBuilder = builder;
        if (this.mBuilder != null) {
            this.mBuilder.setWebViewAssist(this);
            if (apply) {
                this.mBuilder.apply();
            }
        }
        return this;
    }

    /**
     * 获取 WebView 常用配置构建类
     * @return {@link Builder}
     */
    public Builder getBuilder() {
        return mBuilder;
    }

    /**
     * 应用 ( 设置 ) 配置
     * @return {@link Builder}
     */
    public WebViewAssist apply() {
        return setBuilder(mBuilder);
    }

    // =

    /**
     * 设置全局 WebView 常用配置构建类
     * @param builder {@link Builder}
     */
    public static void setGlobalBuilder(final Builder builder) {
        WebViewAssist.sGlobalBuilder = builder;
    }

    /**
     * 获取全局 WebView 常用配置构建类
     * @return {@link Builder}
     */
    public static Builder getGlobalBuilder() {
        return WebViewAssist.sGlobalBuilder;
    }

    // ===========
    // = 加载方法 =
    // ===========

    /**
     * 加载网页
     * <pre>
     *     加载一个网页
     *     loadUrl("http://www.baidu.com")
     *     加载应用资源文件内的网页
     *     loadUrl("file:///android_asset/test.html")
     * </pre>
     * @param url 资源地址
     * @return {@link WebViewAssist}
     */
    public WebViewAssist loadUrl(final String url) {
        if (isWebViewNotEmpty()) {
            mWebView.loadUrl(url);
        }
        return this;
    }

    /**
     * 加载网页
     * @param url                   资源地址
     * @param additionalHttpHeaders Http 请求头信息
     * @return {@link WebViewAssist}
     */
    public WebViewAssist loadUrl(
            final String url,
            final Map<String, String> additionalHttpHeaders
    ) {
        if (isWebViewNotEmpty()) {
            mWebView.loadUrl(url, additionalHttpHeaders);
        }
        return this;
    }

    /**
     * 加载 Html 代码
     * @param data     Html 数据
     * @param mimeType 加载网页的类型
     * @param encoding 编码格式
     * @return {@link WebViewAssist}
     * @deprecated 推荐使用 loadDataWithBaseURL
     */
    @Deprecated
    public WebViewAssist loadData(
            final String data,
            final String mimeType,
            final String encoding
    ) {
        if (isWebViewNotEmpty()) {
            mWebView.loadData(data, mimeType, encoding);
        }
        return this;
    }

    /**
     * 加载 Html 代码
     * @param baseUrl    基础链接
     * @param data       Html 数据
     * @param mimeType   加载网页的类型
     * @param encoding   编码格式
     * @param historyUrl 可用历史记录
     * @return {@link WebViewAssist}
     */
    public WebViewAssist loadDataWithBaseURL(
            final String baseUrl,
            final String data,
            final String mimeType,
            final String encoding,
            final String historyUrl
    ) {
        if (isWebViewNotEmpty()) {
            mWebView.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
        }
        return this;
    }

    /**
     * 使用 POST 方法将带有 postData 的 url 加载到 WebView 中
     * <pre>
     *     如果 url 不是网络 url {@link #loadUrl(String)} 加载
     * </pre>
     * @param url      资源地址
     * @param postData post 数据 ( 注意 UrlEncode )
     * @return {@link WebViewAssist}
     */
    public WebViewAssist postUrl(
            final String url,
            final byte[] postData
    ) {
        if (isWebViewNotEmpty()) {
            mWebView.postUrl(url, postData);
        }
        return this;
    }

    // =

    /**
     * 加载 Html 代码
     * @param data Html 数据
     * @return {@link WebViewAssist}
     */
    public WebViewAssist loadDataWithBaseURL(final String data) {
        return loadDataWithBaseURL(null, data, null);
    }

    /**
     * 加载 Html 代码
     * @param baseUrl    基础链接
     * @param data       Html 数据
     * @param historyUrl 可用历史记录
     * @return {@link WebViewAssist}
     */
    public WebViewAssist loadDataWithBaseURL(
            final String baseUrl,
            final String data,
            final String historyUrl
    ) {
        return loadDataWithBaseURL(baseUrl, data, "text/html", "utf-8", historyUrl);
    }

    // ===============
    // = 快速操作方法 =
    // ===============

    /**
     * 获取 WebView 配置
     * @return {@link WebSettings}
     */
    public WebSettings getSettings() {
        return isWebViewNotEmpty() ? mWebView.getSettings() : null;
    }

    /**
     * 获取浏览器标识 UA
     * @return 浏览器标识 UA
     */
    public String getUserAgentString() {
        WebSettings webSettings = getSettings();
        return (webSettings != null) ? webSettings.getUserAgentString() : null;
    }

    /**
     * 设置浏览器标识
     * @param ua 浏览器标识
     * @return {@link WebViewAssist}
     */
    public WebViewAssist setUserAgentString(final String ua) {
        WebSettings webSettings = getSettings();
        if (webSettings != null) {
            webSettings.setUserAgentString(ua);
        }
        return this;
    }

    // =

    /**
     * 添加 JS 交互注入对象
     * @param obj           JavaScript 交互方法注入对象
     * @param interfaceName 在 JavaScript 中公开对象的名称
     * @return {@link WebViewAssist}
     */
    public WebViewAssist addJavascriptInterface(
            final Object obj,
            final String interfaceName
    ) {
        if (isWebViewNotEmpty()) {
            mWebView.addJavascriptInterface(obj, interfaceName);
        }
        return this;
    }

    /**
     * 移除 JS 交互注入对象
     * @param interfaceName 在 JavaScript 中公开对象的名称
     * @return {@link WebViewAssist}
     */
    public WebViewAssist removeJavascriptInterface(final String interfaceName) {
        if (isWebViewNotEmpty()) {
            mWebView.removeJavascriptInterface(interfaceName);
        }
        return this;
    }

    /**
     * 执行 JS 方法
     * @param script   JS 内容
     * @param callback 执行回调结果 ( 返回值 )
     * @return {@link WebViewAssist}
     */
    public WebViewAssist evaluateJavascript(
            final String script,
            final ValueCallback<String> callback
    ) {
        if (isWebViewNotEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    mWebView.evaluateJavascript(script, callback);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "evaluateJavascript");
                }
            }
        }
        return this;
    }

    // =

    /**
     * 设置处理各种通知和请求事件对象
     * <pre>
     *     常用方法
     *     onPageStarted()onPageFinished() 页面加载时和页面加载完毕时调用
     *     shouldOverrideKeyEvent() 重写此方法才能处理浏览器中的按键事件
     *     shouldInterceptRequest() 页面每一次请求资源之前都会调用这个方法 ( 非 UI 线程调用 )
     *     onLoadResource() 页面加载资源时调用, 每加载一个资源 ( 比如图片 ) 就调用一次
     *     onReceivedError() 加载页面的服务器出现错误 ( 比如 404 ) 时回调
     *     onReceivedSslError() 重写此方法可以让 WebView 处理 https 请求
     *     doUpdateVisitedHistory() 更新历史记录
     *     onFormResubmission() 应用程序重新请求网页数据
     *     onReceivedHttpAuthRequest() 获取返回信息授权请求
     *     onScaleChanged() WebView 发生缩放改变时调用
     *     onUnhandledKeyEvent() Key 事件未被加载时调用
     * </pre>
     * @param client {@link WebViewClient}
     * @return {@link WebViewAssist}
     */
    public WebViewAssist setWebViewClient(final WebViewClient client) {
        if (isWebViewNotEmpty()) {
            mWebView.setWebViewClient(client);
        }
        return this;
    }

    /**
     * 获取处理各种通知和请求事件对象
     * @return {@link WebViewClient}
     */
    public WebViewClient getWebViewClient() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return isWebViewNotEmpty() ? mWebView.getWebViewClient() : null;
        }
        return null;
    }

    // =

    /**
     * 设置辅助 WebView 处理 Javascript 对话框、标题等对象
     * <pre>
     *     常用方法
     *     onProgressChanged() 获得网页的加载进度并显示
     *     onReceivedTitle() 获得网页的标题时回调
     *     onReceivedIcon() 获得网页的图标时回调
     *     onCreateWindow() 打开新窗口时回调
     *     onCloseWindow() 关闭窗口时回调
     *     onJsAlert() 网页弹出提示框时触发此方法
     *     onJsConfirm() 网页弹出确认框时触发此方法
     *     onJsPrompt() 网页弹出输入框时触发此方法
     * </pre>
     * @param client {@link WebChromeClient}
     * @return {@link WebViewAssist}
     */
    public WebViewAssist setWebChromeClient(final WebChromeClient client) {
        if (isWebViewNotEmpty()) {
            mWebView.setWebChromeClient(client);
        }
        return this;
    }

    /**
     * 获取辅助 WebView 处理 Javascript 对话框、标题等对象
     * @return {@link WebChromeClient}
     */
    public WebChromeClient getWebChromeClient() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return isWebViewNotEmpty() ? mWebView.getWebChromeClient() : null;
        }
        return null;
    }

    // =

    /**
     * 销毁处理
     * <pre>
     *     避免 WebView 引起的内存泄漏
     *     可通过 WebView 所在的 Activity 新启一个进程结束时 System.exit(0) 退出当前进程
     *     Activity onDestroy use
     * </pre>
     * @return {@link WebViewAssist}
     */
    public WebViewAssist destroy() {
        if (isWebViewNotEmpty()) {
            mWebView.clearHistory();
            ViewUtils.removeSelfFromParent(mWebView);
            mWebView.loadUrl("about:blank");
            mWebView.stopLoading();
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.destroy();
            mWebView = null;
            if (mBuilder != null) {
                mBuilder.setWebViewAssist(null);
            }
        }
        return this;
    }

    /**
     * WebView 是否可以后退
     * @return {@code true} yes, {@code false} no
     */
    public boolean canGoBack() {
        return isWebViewNotEmpty() && mWebView.canGoBack();
    }

    /**
     * WebView 后退
     * @return {@link WebViewAssist}
     */
    public WebViewAssist goBack() {
        if (isWebViewNotEmpty()) {
            mWebView.goBack();
        }
        return this;
    }

    // =

    /**
     * WebView 是否可以前进
     * @return {@code true} yes, {@code false} no
     */
    public boolean canGoForward() {
        return isWebViewNotEmpty() && mWebView.canGoForward();
    }

    /**
     * WebView 前进
     * @return {@link WebViewAssist}
     */
    public WebViewAssist goForward() {
        if (isWebViewNotEmpty()) {
            mWebView.goForward();
        }
        return this;
    }

    // =

    /**
     * WebView 是否可以跳转到当前起始点相距的历史记录
     * @param steps 相距索引
     * @return {@code true} yes, {@code false} no
     */
    public boolean canGoBackOrForward(final int steps) {
        return isWebViewNotEmpty() && mWebView.canGoBackOrForward(steps);
    }

    /**
     * WebView 跳转到当前起始点相距的历史记录
     * <pre>
     *     以当前的 index 为起始点前进或者后退到历史记录中指定的 steps
     *     如果 steps 为负数则为后退, 正数则为前进
     * </pre>
     * @param steps 相距索引
     * @return {@link WebViewAssist}
     */
    public WebViewAssist goBackOrForward(final int steps) {
        if (isWebViewNotEmpty()) {
            mWebView.goBackOrForward(steps);
        }
        return this;
    }

    // =

    /**
     * 刷新页面 ( 当前页面的所有资源都会重新加载 )
     * @return {@link WebViewAssist}
     */
    public WebViewAssist reload() {
        if (isWebViewNotEmpty()) {
            mWebView.reload();
        }
        return this;
    }

    /**
     * 停止加载
     * @return {@link WebViewAssist}
     */
    public WebViewAssist stopLoading() {
        if (isWebViewNotEmpty()) {
            mWebView.stopLoading();
        }
        return this;
    }

    /**
     * 清除资源缓存
     * <pre>
     *     缓存是针对每个应用程序的, 因此这将清除所有使用的 WebView 的缓存
     * </pre>
     * @param includeDiskFiles 是否清空本地缓存 ( false 则只会清空内存缓存, true 全部清空 )
     * @return {@link WebViewAssist}
     */
    public WebViewAssist clearCache(final boolean includeDiskFiles) {
        if (isWebViewNotEmpty()) {
            mWebView.clearCache(includeDiskFiles);
        }
        return this;
    }

    /**
     * 清除当前 WebView 访问的历史记录
     * @return {@link WebViewAssist}
     */
    public WebViewAssist clearHistory() {
        if (isWebViewNotEmpty()) {
            mWebView.clearHistory();
        }
        return this;
    }

    /**
     * 清除自动完成填充的表单数据
     * <pre>
     *     并不会清除 WebView 存储到本地的数据
     * </pre>
     * @return {@link WebViewAssist}
     */
    public WebViewAssist clearFormData() {
        if (isWebViewNotEmpty()) {
            mWebView.clearFormData();
        }
        return this;
    }

    /**
     * 获取缩放比例
     * @return 缩放比例
     */
    public float getScale() {
        if (isWebViewNotEmpty()) {
            return mWebView.getScale();
        }
        return 1.0f;
    }

    /**
     * 获取当前可见区域的顶端距整个页面顶端的距离 ( 当前内容滚动的距离 )
     * @return 当前内容滚动的距离
     */
    public int getScrollY() {
        return isWebViewNotEmpty() ? mWebView.getScrollY() : 0;
    }

    /**
     * 获取当前内容横向滚动距离
     * @return 当前内容横向滚动距离
     */
    public int getScrollX() {
        return isWebViewNotEmpty() ? mWebView.getScrollX() : 0;
    }

    /**
     * 获取 HTML 的高度 ( 原始高度, 不包括缩放后的高度 )
     * <pre>
     *     可通过 setWebViewClient onScaleChanged(WebView view, float oldScale, float newScale) 获取缩放比例
     *     或者通过 webView.getScale(); 获取 ( 该方法已抛弃 )
     * </pre>
     * @return HTML 的高度 ( 原始高度, 不包括缩放后的高度 )
     */
    public int getContentHeight() {
        return isWebViewNotEmpty() ? mWebView.getContentHeight() : 0;
    }

    /**
     * 获取缩放高度
     * @return 缩放高度
     */
    public int getScaleHeight() {
        return getScaleHeight(getScale());
    }

    /**
     * 获取缩放高度
     * @param scale 缩放比例
     * @return 缩放高度
     */
    public int getScaleHeight(final float scale) {
        return (int) (getContentHeight() * scale);
    }

    /**
     * 获取 WebView 控件高度
     * @return WebView 控件高度
     */
    public int getHeight() {
        return isWebViewNotEmpty() ? mWebView.getHeight() : 0;
    }

    /**
     * 将视图内容向下滚动一半页面大小
     * @param bottom 是否滑动到底部
     * @return {@link WebViewAssist}
     */
    public WebViewAssist pageDown(final boolean bottom) {
        if (isWebViewNotEmpty()) {
            mWebView.pageDown(bottom);
        }
        return this;
    }

    /**
     * 将视图内容向上滚动一半页面大小
     * @param top 是否滑动到顶部
     * @return {@link WebViewAssist}
     */
    public WebViewAssist pageUp(final boolean top) {
        if (isWebViewNotEmpty()) {
            mWebView.pageUp(top);
        }
        return this;
    }

    /**
     * 处理按键 ( 是否回退 )
     * <pre>
     *     Activity use
     *     @Override
     *     public boolean onKeyDown(int keyCode, KeyEvent event) {
     *          if (webViewAssist.handlerKeyDown(keyCode, event)) return true;
     *          return super.onKeyDown(keyCode, event);
     *     }
     * </pre>
     * @param keyCode 按键类型
     * @param event   按键事件
     * @return {@code true} 拦截事件, {@code false} 不拦截接着处理
     */
    public boolean handlerKeyDown(
            final int keyCode,
            final KeyEvent event
    ) {
        if (keyCode == KeyEvent.KEYCODE_BACK && canGoBack()) {
            goBack();
            return true;
        }
        return false;
    }

    /**
     * 关闭 WebView 硬件加速功能
     * <pre>
     *     解决 WebView 闪烁问题
     * </pre>
     * @return {@link WebViewAssist}
     */
    public WebViewAssist setLayerTypeSoftware() {
        return setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    /**
     * 设置 WebView 硬件加速类型
     * @param layerType 硬件加速类型
     * @param paint     {@link Paint}
     * @return {@link WebViewAssist}
     */
    public WebViewAssist setLayerType(
            final int layerType,
            final Paint paint
    ) {
        if (isWebViewNotEmpty()) {
            mWebView.setLayerType(layerType, paint);
        }
        return this;
    }

    /**
     * 获取当前 Url
     * @return 当前 Url
     */
    public String getUrl() {
        return isWebViewNotEmpty() ? mWebView.getUrl() : null;
    }

    /**
     * 获取最初请求 Url
     * @return 最初请求 Url
     */
    public String getOriginalUrl() {
        return isWebViewNotEmpty() ? mWebView.getOriginalUrl() : null;
    }

    /**
     * 获取长按事件类型
     * <pre>
     *     WebView.HitTestResult.UNKNOWN_TYPE // 未知类型
     *     WebView.HitTestResult.PHONE_TYPE // 电话类型
     *     WebView.HitTestResult.EMAIL_TYPE // 电子邮件类型
     *     WebView.HitTestResult.GEO_TYPE // 地图类型
     *     WebView.HitTestResult.SRC_ANCHOR_TYPE // 超链接类型
     *     WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE // 带有链接的图片类型
     *     WebView.HitTestResult.IMAGE_TYPE // 单纯的图片类型
     *     WebView.HitTestResult.EDIT_TEXT_TYPE // 选中的文字类型
     *     <p></p>
     *     mWebView.setOnLongClickListener(new View.OnLongClickListener() {
     *          @Override
     *          public boolean onLongClick(View view) {
     *              WebView.HitTestResult result = webViewAssist.getHitTestResult();
     *              if(result != null) {
     *                  switch (result.getType()) {
     *                      case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
     *                          String imgUrl = result.getExtra();
     *                          return true;
     *                  }
     *              }
     *              return false;
     *          }
     *      });
     *      <p></p>
     *      HitTestResult.getType() 获取所选中目标的类型, 可以是图片、超链接、邮件、电话等等
     *      HitTestResult.getExtra() 获取额外的信息
     * </pre>
     * @return 长按事件类型
     */
    public WebView.HitTestResult getHitTestResult() {
        return isWebViewNotEmpty() ? mWebView.getHitTestResult() : null;
    }

    // ==========
    // = Cookie =
    // ==========

    /**
     * 将 Cookie 设置到 WebView
     * <pre>
     *     若两次设置相同, 则覆盖上一次的
     *     同步 Cookie 要在 WebView 加载 url 之前, 否则 WebView 无法获得相应的 Cookie, 也就无法通过验证
     *     Cookie 应该被及时更新, 否则很可能导致 WebView 拿的是旧的 session id 和服务器进行通信
     * </pre>
     * @param url    加载的 Url
     * @param cookie 同步的 cookie
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setCookie(
            final String url,
            final String cookie
    ) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                CookieSyncManager.createInstance(DevUtils.getContext());
            }
            CookieManager cookieManager = CookieManager.getInstance();
            // 如果没有特殊需求, 这里只需要将 session id 以 "key=value" 形式作为 Cookie 即可
            cookieManager.setCookie(url, cookie);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setCookie");
        }
        return false;
    }

    /**
     * 获取指定 Url 的 Cookie
     * @param url Url
     * @return Cookie
     */
    public static String getCookie(final String url) {
        try {
            CookieManager cookieManager = CookieManager.getInstance();
            return cookieManager.getCookie(url);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCookie");
        }
        return null;
    }

    /**
     * 移除 Cookie
     * @param callback 移除回调
     */
    public static void removeCookie(final ValueCallback<Boolean> callback) {
        removeSessionCookie(callback);
        removeAllCookie(callback);
    }

    /**
     * 移除 Session Cookie
     * @param callback 移除回调
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeSessionCookie(final ValueCallback<Boolean> callback) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager.getInstance().removeSessionCookies(callback);
            } else {
                CookieManager.getInstance().removeSessionCookie();
            }
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "removeSessionCookie");
        }
        return false;
    }

    /**
     * 移除所有的 Cookie
     * @param callback 移除回调
     * @return {@code true} success, {@code false} fail
     */
    public static boolean removeAllCookie(final ValueCallback<Boolean> callback) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager.getInstance().removeAllCookies(callback);
            } else {
                CookieManager.getInstance().removeAllCookie();
            }
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "removeAllCookie");
        }
        return false;
    }

    // ===========
    // = 配置相关 =
    // ===========

    /**
     * detail: WebView 常用配置构建类
     * @author Ttt
     * <pre>
     *     有特殊配置可在 {@link OnApplyListener#onApply(WebViewAssist, Builder)} 回调中进行增加配置设置
     * </pre>
     */
    public static class Builder {

        // WebView 辅助类
        private WebViewAssist   mWebViewAssist;
        // 应用配置监听事件
        private OnApplyListener mApplyListener;

        public Builder() {
        }

        /**
         * 构造函数
         * @param applyListener 应用配置监听事件
         */
        public Builder(final OnApplyListener applyListener) {
            setOnApplyListener(applyListener);
        }

        // =

        /**
         * 设置 WebView 辅助类
         * @param webViewAssist WebView 辅助类
         * @return {@link Builder}
         */
        private Builder setWebViewAssist(final WebViewAssist webViewAssist) {
            this.mWebViewAssist = webViewAssist;
            return this;
        }

        /**
         * 应用 ( 设置 ) 配置
         * @return {@link Builder}
         */
        public Builder apply() {
            return applyPri();
        }

        // =======
        // = 事件 =
        // =======

        /**
         * 设置应用配置监听事件
         * @param applyListener {@link OnApplyListener}
         * @return {@link Builder}
         */
        public Builder setOnApplyListener(final OnApplyListener applyListener) {
            this.mApplyListener = applyListener;
            return this;
        }

        /**
         * 获取应用配置监听事件
         * @return {@link OnApplyListener}
         */
        public OnApplyListener getApplyListener() {
            return mApplyListener;
        }

        /**
         * detail: 应用配置监听事件
         * @author Ttt
         */
        public interface OnApplyListener {

            /**
             * 应用配置通知方法
             * @param webViewAssist WebView 辅助类
             * @param builder       WebView 常用配置构建类
             */
            void onApply(
                    WebViewAssist webViewAssist,
                    Builder builder
            );
        }

        // ===========
        // = 克隆方法 =
        // ===========

        /**
         * 克隆方法 ( 用于全局配置克隆操作 )
         * @param listener 是否复用监听事件
         * @return {@link Builder}
         */
        public Builder clone(final boolean listener) {
            Builder builder = new Builder();
            if (listener) { // 复用监听事件
                builder.setOnApplyListener(mApplyListener);
            }
            builder.mJavaScriptEnabled = mJavaScriptEnabled;
            builder.mRenderPriority = mRenderPriority;
            builder.mUseWideViewPort = mUseWideViewPort;
            builder.mLoadWithOverviewMode = mLoadWithOverviewMode;
            builder.mLayoutAlgorithm = mLayoutAlgorithm;
            builder.mSupportZoom = mSupportZoom;
            builder.mBuiltInZoomControls = mBuiltInZoomControls;
            builder.mDisplayZoomControls = mDisplayZoomControls;
            builder.mTextZoom = mTextZoom;
            builder.mStandardFontFamily = mStandardFontFamily;
            builder.mDefaultFontSize = mDefaultFontSize;
            builder.mMinimumFontSize = mMinimumFontSize;
            builder.mMixedContentMode = mMixedContentMode;
            builder.mLoadsImagesAutomatically = mLoadsImagesAutomatically;
            builder.mJavaScriptCanOpenWindowsAutomatically = mJavaScriptCanOpenWindowsAutomatically;
            builder.mDefaultTextEncodingName = mDefaultTextEncodingName;
            builder.mGeolocationEnabled = mGeolocationEnabled;
            builder.mUserAgentString = mUserAgentString;
            builder.mAllowFileAccess = mAllowFileAccess;
            builder.mAllowFileAccessFromFileURLs = mAllowFileAccessFromFileURLs;
            builder.mAllowUniversalAccessFromFileURLs = mAllowUniversalAccessFromFileURLs;
            builder.mCacheMode = mCacheMode;
            builder.mDomStorageEnabled = mDomStorageEnabled;
            builder.mAppCacheEnabled = mAppCacheEnabled;
            builder.mAppCachePath = mAppCachePath;
            builder.mAppCacheMaxSize = mAppCacheMaxSize;
            builder.mDatabaseEnabled = mDatabaseEnabled;
            builder.mDatabasePath = mDatabasePath;
            return builder;
        }

        /**
         * 重置方法
         * @return {@link Builder}
         */
        public Builder reset() {
            this.mJavaScriptEnabled = true;
            this.mRenderPriority = null;
            this.mUseWideViewPort = false;
            this.mLoadWithOverviewMode = true;
            this.mLayoutAlgorithm = null;
            this.mSupportZoom = true;
            this.mBuiltInZoomControls = false;
            this.mDisplayZoomControls = false;
            this.mTextZoom = 0;
            this.mStandardFontFamily = null;
            this.mDefaultFontSize = 0;
            this.mMinimumFontSize = 0;
            this.mMixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
            this.mLoadsImagesAutomatically = true;
            this.mJavaScriptCanOpenWindowsAutomatically = true;
            this.mDefaultTextEncodingName = "utf-8";
            this.mGeolocationEnabled = true;
            this.mUserAgentString = null;
            this.mAllowFileAccess = true;
            this.mAllowFileAccessFromFileURLs = false;
            this.mAllowUniversalAccessFromFileURLs = false;
            this.mCacheMode = WebSettings.LOAD_NO_CACHE;
            this.mDomStorageEnabled = true;
            this.mAppCacheEnabled = true;
            this.mAppCachePath = null;
            this.mAppCacheMaxSize = 5 * 1024 * 1024;
            this.mDatabaseEnabled = true;
            this.mDatabasePath = null;
            return this;
        }

        // ===========
        // = 配置方法 =
        // ===========

        // 是否支持 JavaScript
        private boolean                     mJavaScriptEnabled                     = true;
        // 渲染优先级
        private WebSettings.RenderPriority  mRenderPriority                        = null;
        // 是否使用宽视图 ( 是否支持 html viewport 设置了会导致字体变小 )
        private boolean                     mUseWideViewPort                       = false;
        // 是否按宽度缩小内容以适合屏幕
        private boolean                     mLoadWithOverviewMode                  = true;
        // 基础布局算法, 大于 4.4 使用 TEXT_AUTOSIZING 解决前端 REM 适配方案有误差情况
        private WebSettings.LayoutAlgorithm mLayoutAlgorithm                       = null;
        // 是否支持缩放
        private boolean                     mSupportZoom                           = true;
        // 是否显示内置缩放工具
        private boolean                     mBuiltInZoomControls                   = false;
        // 是否显示缩放工具
        private boolean                     mDisplayZoomControls                   = false;
        // 文本缩放倍数
        private int                         mTextZoom                              = 0;
        // WebView 字体, 默认字体 "sans-serif"
        private String                      mStandardFontFamily                    = null;
        // WebView 字体大小
        private int                         mDefaultFontSize                       = 0;
        // WebView 支持最小字体大小
        private int                         mMinimumFontSize                       = 0;
        // 混合内容模式
        private int                         mMixedContentMode                      = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
        // 是否支持自动加载图片
        private boolean                     mLoadsImagesAutomatically              = true;
        // 是否支持通过 JS 打开新窗口
        private boolean                     mJavaScriptCanOpenWindowsAutomatically = true;
        // 编码格式
        private String                      mDefaultTextEncodingName               = "utf-8";
        // 是否允许网页执行定位操作
        private boolean                     mGeolocationEnabled                    = true;
        // 浏览器标识 UA
        private String                      mUserAgentString                       = null;
        // 是否可以访问文件 ( false 不影响 assets 和 resources 资源的加载 )
        private boolean                     mAllowFileAccess                       = true;
        // 是否允许通过 file url 加载的 JS 代码读取其他的本地文件
        private boolean                     mAllowFileAccessFromFileURLs           = false;
        // 是否允许通过 file url 加载的 JS 可以访问其他的源 ( 包括 http、https 等源 )
        private boolean                     mAllowUniversalAccessFromFileURLs      = false;
        // WebView 缓存模式
        private int                         mCacheMode                             = WebSettings.LOAD_NO_CACHE;
        // 是否支持 DOM Storage
        private boolean                     mDomStorageEnabled                     = true;
        // 是否开启 Application Caches 功能
        private boolean                     mAppCacheEnabled                       = true;
        // Application Caches 地址
        private String                      mAppCachePath                          = null;
        // Application Caches 大小
        private long                        mAppCacheMaxSize                       = 5 * 1024 * 1024;
        // 是否支持数据库缓存
        private boolean                     mDatabaseEnabled                       = true;
        // 数据库缓存路径
        private String                      mDatabasePath                          = null;

        /**
         * 应用 ( 设置 ) 配置
         * @return {@link Builder}
         */
        private Builder applyPri() {
            if (mWebViewAssist != null) {
                WebSettings webSettings = mWebViewAssist.getSettings();
                if (webSettings != null) {
                    // 如果访问的页面中要与 JavaScript 交互, 则 WebView 必须设置支持 JavaScript
                    webSettings.setJavaScriptEnabled(mJavaScriptEnabled);

                    if (mRenderPriority != null) {
                        webSettings.setRenderPriority(mRenderPriority);  // 设置渲染优先级
                    }

                    // 设置自适应屏幕两者合用
                    webSettings.setUseWideViewPort(mUseWideViewPort); // 是否使用宽视图
                    webSettings.setLoadWithOverviewMode(mLoadWithOverviewMode); // 是否按宽度缩小内容以适合屏幕
                    if (mLayoutAlgorithm != null) { // WebSettings.LayoutAlgorithm.SINGLE_COLUMN 4.4 抛弃了
                        // 大于 4.4 使用 TEXT_AUTOSIZING 解决前端 REM 适配方案有误差情况
                        webSettings.setLayoutAlgorithm(mLayoutAlgorithm);
                    }

                    // 缩放操作
                    webSettings.setSupportZoom(mSupportZoom); // 是否支持缩放
                    webSettings.setBuiltInZoomControls(mBuiltInZoomControls); // 是否显示内置缩放工具, 若为 false 则该 WebView 不可缩放
                    webSettings.setDisplayZoomControls(mDisplayZoomControls); // 是否显示缩放工具

                    if (mTextZoom > 0) {
                        webSettings.setTextZoom(mTextZoom); // 文本缩放倍数
                    }
                    if (mStandardFontFamily != null) {
                        webSettings.setStandardFontFamily(mStandardFontFamily); // 设置 WebView 字体
                    }
                    if (mDefaultFontSize > 0) {
                        webSettings.setDefaultFontSize(mDefaultFontSize); // 设置 WebView 字体大小
                    }
                    if (mMinimumFontSize > 0) {
                        webSettings.setMinimumFontSize(mMinimumFontSize); // 设置 WebView 支持最小字体大小
                    }

                    // 5.0 以上默认禁止了 https 和 http 混用以下方式是开启
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        webSettings.setMixedContentMode(mMixedContentMode);
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        webSettings.setLoadsImagesAutomatically(mLoadsImagesAutomatically); // 是否支持自动加载图片
                    }

                    // 是否支持通过 JS 打开新窗口
                    webSettings.setJavaScriptCanOpenWindowsAutomatically(mJavaScriptCanOpenWindowsAutomatically);
                    // 设置编码格式
                    if (mDefaultTextEncodingName != null) {
                        webSettings.setDefaultTextEncodingName(mDefaultTextEncodingName);
                    }
                    // 是否允许网页执行定位操作
                    webSettings.setGeolocationEnabled(mGeolocationEnabled);
                    // 设置浏览器标识 UA
                    if (mUserAgentString != null) {
                        webSettings.setUserAgentString(mUserAgentString);
                    }

                    // 是否可以访问文件
                    webSettings.setAllowFileAccess(mAllowFileAccess);
                    // 是否允许通过 file url 加载的 JS 代码读取其他的本地文件
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        webSettings.setAllowFileAccessFromFileURLs(mAllowFileAccessFromFileURLs);
                    }
                    // 是否允许通过 file url 加载的 JS 可以访问其他的源 ( 包括 http、https 等源 )
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        webSettings.setAllowUniversalAccessFromFileURLs(mAllowUniversalAccessFromFileURLs);
                    }
                    // 设置 WebView 缓存模式
                    if (mCacheMode > 0) {
                        // LOAD_CACHE_ONLY 不使用网络, 只读取本地缓存数据
                        // LOAD_DEFAULT ( 默认 ) 根据 cache-control 决定是否从网络上取数据
                        // LOAD_NO_CACHE 不使用缓存, 只从网络获取数据.
                        // LOAD_CACHE_ELSE_NETWORK 只要本地有, 无论是否过期或者 no-cache 都使用缓存中的数据
                        webSettings.setCacheMode(mCacheMode);
                    }

                    // 是否支持 DOM Storage
                    webSettings.setDomStorageEnabled(mDomStorageEnabled);
                    // 是否开启 Application Caches 功能
                    webSettings.setAppCacheEnabled(mAppCacheEnabled);
                    if (mAppCacheEnabled) {
                        // Application Caches 地址
                        if (mAppCachePath != null) {
                            webSettings.setAppCachePath(mAppCachePath);
                        }
                        // Application Caches 大小
                        webSettings.setAppCacheMaxSize(mAppCacheMaxSize);
                    }

                    // 是否支持数据库缓存
                    webSettings.setDatabaseEnabled(mDatabaseEnabled);
                    if (mDatabaseEnabled) {
                        // 数据库缓存路径
                        if (mDatabasePath != null) {
                            webSettings.setDatabasePath(mDatabasePath);
                        }
                    }
                }
            }

            if (mApplyListener != null) {
                mApplyListener.onApply(mWebViewAssist, this);
            }
            return this;
        }

        // ===========
        // = get/set =
        // ===========

        /**
         * 是否支持 JavaScript
         * @return {@code true} yes, {@code false} no
         */
        public boolean isJavaScriptEnabled() {
            return mJavaScriptEnabled;
        }

        /**
         * 设置是否支持 JavaScript
         * @param javaScriptEnabled {@code true} yes, {@code false} no
         * @return {@link Builder}
         */
        public Builder setJavaScriptEnabled(final boolean javaScriptEnabled) {
            this.mJavaScriptEnabled = javaScriptEnabled;
            return this;
        }

        /**
         * 获取渲染优先级
         * @return 渲染优先级
         */
        public WebSettings.RenderPriority getRenderPriority() {
            return mRenderPriority;
        }

        /**
         * 设置渲染优先级
         * @param renderPriority 渲染优先级
         * @return {@link Builder}
         */
        public Builder setRenderPriority(final WebSettings.RenderPriority renderPriority) {
            this.mRenderPriority = renderPriority;
            return this;
        }

        /**
         * 是否使用宽视图
         * @return {@code true} yes, {@code false} no
         */
        public boolean isUseWideViewPort() {
            return mUseWideViewPort;
        }

        /**
         * 设置是否使用宽视图
         * <pre>
         *     是否支持 html viewport 设置了会导致字体变小
         * </pre>
         * @param useWideViewPort {@code true} yes, {@code false} no
         * @return {@link Builder}
         */
        public Builder setUseWideViewPort(final boolean useWideViewPort) {
            this.mUseWideViewPort = useWideViewPort;
            return this;
        }

        /**
         * 是否按宽度缩小内容以适合屏幕
         * @return {@code true} yes, {@code false} no
         */
        public boolean isLoadWithOverviewMode() {
            return mLoadWithOverviewMode;
        }

        /**
         * 设置是否按宽度缩小内容以适合屏幕
         * @param loadWithOverviewMode {@code true} yes, {@code false} no
         * @return {@link Builder}
         */
        public Builder setLoadWithOverviewMode(final boolean loadWithOverviewMode) {
            this.mLoadWithOverviewMode = loadWithOverviewMode;
            return this;
        }

        /**
         * 获取基础布局算法
         * @return 基础布局算法
         */
        public WebSettings.LayoutAlgorithm getLayoutAlgorithm() {
            return mLayoutAlgorithm;
        }

        /**
         * 设置基础布局算法
         * @param layoutAlgorithm 基础布局算法
         * @return {@link Builder}
         */
        public Builder setLayoutAlgorithm(final WebSettings.LayoutAlgorithm layoutAlgorithm) {
            this.mLayoutAlgorithm = layoutAlgorithm;
            return this;
        }

        /**
         * 是否支持缩放
         * @return {@code true} yes, {@code false} no
         */
        public boolean isSupportZoom() {
            return mSupportZoom;
        }

        /**
         * 设置是否支持缩放
         * @param supportZoom {@code true} yes, {@code false} no
         * @return {@link Builder}
         */
        public Builder setSupportZoom(final boolean supportZoom) {
            this.mSupportZoom = supportZoom;
            return this;
        }

        /**
         * 是否显示内置缩放工具
         * @return {@code true} yes, {@code false} no
         */
        public boolean isBuiltInZoomControls() {
            return mBuiltInZoomControls;
        }

        /**
         * 设置是否显示内置缩放工具
         * @param builtInZoomControls {@code true} yes, {@code false} no
         * @return {@link Builder}
         */
        public Builder setBuiltInZoomControls(final boolean builtInZoomControls) {
            this.mBuiltInZoomControls = builtInZoomControls;
            return this;
        }

        /**
         * 是否显示缩放工具
         * @return {@code true} yes, {@code false} no
         */
        public boolean isDisplayZoomControls() {
            return mDisplayZoomControls;
        }

        /**
         * 设置是否显示缩放工具
         * @param displayZoomControls {@code true} yes, {@code false} no
         * @return {@link Builder}
         */
        public Builder setDisplayZoomControls(final boolean displayZoomControls) {
            this.mDisplayZoomControls = displayZoomControls;
            return this;
        }

        /**
         * 获取文本缩放倍数
         * @return 文本缩放倍数
         */
        public int getTextZoom() {
            return mTextZoom;
        }

        /**
         * 设置文本缩放倍数
         * @param textZoom 文本缩放倍数
         * @return {@link Builder}
         */
        public Builder setTextZoom(final int textZoom) {
            this.mTextZoom = textZoom;
            return this;
        }

        /**
         * 获取 WebView 字体
         * @return WebView 字体
         */
        public String getStandardFontFamily() {
            return mStandardFontFamily;
        }

        /**
         * 设置 WebView 字体
         * @param standardFontFamily WebView 字体
         * @return {@link Builder}
         */
        public Builder setStandardFontFamily(final String standardFontFamily) {
            this.mStandardFontFamily = standardFontFamily;
            return this;
        }

        /**
         * 获取 WebView 字体大小
         * @return WebView 字体大小
         */
        public int getDefaultFontSize() {
            return mDefaultFontSize;
        }

        /**
         * 设置 WebView 字体大小
         * @param defaultFontSize WebView 字体大小
         * @return {@link Builder}
         */
        public Builder setDefaultFontSize(final int defaultFontSize) {
            this.mDefaultFontSize = defaultFontSize;
            return this;
        }

        /**
         * 获取 WebView 支持最小字体大小
         * @return WebView 支持最小字体大小
         */
        public int getMinimumFontSize() {
            return mMinimumFontSize;
        }

        /**
         * 设置 WebView 支持最小字体大小
         * @param minimumFontSize WebView 支持最小字体大小
         * @return {@link Builder}
         */
        public Builder setMinimumFontSize(final int minimumFontSize) {
            this.mMinimumFontSize = minimumFontSize;
            return this;
        }

        /**
         * 获取混合内容模式
         * @return 混合内容模式
         */
        public int getMixedContentMode() {
            return mMixedContentMode;
        }

        /**
         * 设置混合内容模式
         * @param mixedContentMode 混合内容模式
         * @return {@link Builder}
         */
        public Builder setMixedContentMode(final int mixedContentMode) {
            this.mMixedContentMode = mixedContentMode;
            return this;
        }

        /**
         * 是否支持自动加载图片
         * @return {@code true} yes, {@code false} no
         */
        public boolean isLoadsImagesAutomatically() {
            return mLoadsImagesAutomatically;
        }

        /**
         * 设置是否支持自动加载图片
         * @param loadsImagesAutomatically {@code true} yes, {@code false} no
         * @return {@link Builder}
         */
        public Builder setLoadsImagesAutomatically(final boolean loadsImagesAutomatically) {
            this.mLoadsImagesAutomatically = loadsImagesAutomatically;
            return this;
        }

        /**
         * 是否支持通过 JS 打开新窗口
         * @return {@code true} yes, {@code false} no
         */
        public boolean isJavaScriptCanOpenWindowsAutomatically() {
            return mJavaScriptCanOpenWindowsAutomatically;
        }

        /**
         * 设置是否支持通过 JS 打开新窗口
         * @param javaScriptCanOpenWindowsAutomatically {@code true} yes, {@code false} no
         * @return {@link Builder}
         */
        public Builder setJavaScriptCanOpenWindowsAutomatically(final boolean javaScriptCanOpenWindowsAutomatically) {
            this.mJavaScriptCanOpenWindowsAutomatically = javaScriptCanOpenWindowsAutomatically;
            return this;
        }

        /**
         * 获取编码格式
         * @return 编码格式
         */
        public String getDefaultTextEncodingName() {
            return mDefaultTextEncodingName;
        }

        /**
         * 设置编码格式
         * @param defaultTextEncodingName 编码格式
         * @return {@link Builder}
         */
        public Builder setDefaultTextEncodingName(final String defaultTextEncodingName) {
            this.mDefaultTextEncodingName = defaultTextEncodingName;
            return this;
        }

        /**
         * 是否允许网页执行定位操作
         * @return {@code true} yes, {@code false} no
         */
        public boolean isGeolocationEnabled() {
            return mGeolocationEnabled;
        }

        /**
         * 设置是否允许网页执行定位操作
         * @param geolocationEnabled {@code true} yes, {@code false} no
         * @return {@link Builder}
         */
        public Builder setGeolocationEnabled(final boolean geolocationEnabled) {
            this.mGeolocationEnabled = geolocationEnabled;
            return this;
        }

        /**
         * 获取浏览器标识 UA
         * @return 浏览器标识 UA
         */
        public String getUserAgentString() {
            return mUserAgentString;
        }

        /**
         * 设置浏览器标识 UA
         * @param userAgentString 浏览器标识 UA
         * @return {@link Builder}
         */
        public Builder setUserAgentString(final String userAgentString) {
            this.mUserAgentString = userAgentString;
            return this;
        }

        /**
         * 是否可以访问文件 ( false 不影响 assets 和 resources 资源的加载 )
         * @return {@code true} yes, {@code false} no
         */
        public boolean isAllowFileAccess() {
            return mAllowFileAccess;
        }

        /**
         * 设置是否可以访问文件 ( false 不影响 assets 和 resources 资源的加载 )
         * @param allowFileAccess {@code true} yes, {@code false} no
         * @return {@link Builder}
         */
        public Builder setAllowFileAccess(final boolean allowFileAccess) {
            this.mAllowFileAccess = allowFileAccess;
            return this;
        }

        /**
         * 是否允许通过 file url 加载的 JS 代码读取其他的本地文件
         * @return {@code true} yes, {@code false} no
         */
        public boolean isAllowFileAccessFromFileURLs() {
            return mAllowFileAccessFromFileURLs;
        }

        /**
         * 设置是否允许通过 file url 加载的 JS 代码读取其他的本地文件
         * @param allowFileAccessFromFileURLs {@code true} yes, {@code false} no
         * @return {@link Builder}
         */
        public Builder setAllowFileAccessFromFileURLs(final boolean allowFileAccessFromFileURLs) {
            this.mAllowFileAccessFromFileURLs = allowFileAccessFromFileURLs;
            return this;
        }

        /**
         * 是否允许通过 file url 加载的 JS 可以访问其他的源 ( 包括 http、https 等源 )
         * @return {@code true} yes, {@code false} no
         */
        public boolean isAllowUniversalAccessFromFileURLs() {
            return mAllowUniversalAccessFromFileURLs;
        }

        /**
         * 设置是否允许通过 file url 加载的 JS 可以访问其他的源 ( 包括 http、https 等源 )
         * @param allowUniversalAccessFromFileURLs {@code true} yes, {@code false} no
         * @return {@link Builder}
         */
        public Builder setAllowUniversalAccessFromFileURLs(final boolean allowUniversalAccessFromFileURLs) {
            this.mAllowUniversalAccessFromFileURLs = allowUniversalAccessFromFileURLs;
            return this;
        }

        /**
         * 获取 WebView 缓存模式
         * @return WebView 缓存模式
         */
        public int getCacheMode() {
            return mCacheMode;
        }

        /**
         * 设置 WebView 缓存模式
         * @param cacheMode WebView 缓存模式
         * @return {@link Builder}
         */
        public Builder setCacheMode(final int cacheMode) {
            this.mCacheMode = cacheMode;
            return this;
        }

        /**
         * 是否支持 DOM Storage
         * @return {@code true} yes, {@code false} no
         */
        public boolean isDomStorageEnabled() {
            return mDomStorageEnabled;
        }

        /**
         * 设置是否支持 DOM Storage
         * @param domStorageEnabled {@code true} yes, {@code false} no
         * @return {@link Builder}
         */
        public Builder setDomStorageEnabled(final boolean domStorageEnabled) {
            this.mDomStorageEnabled = domStorageEnabled;
            return this;
        }

        /**
         * 是否开启 Application Caches 功能
         * @return {@code true} yes, {@code false} no
         */
        public boolean isAppCacheEnabled() {
            return mAppCacheEnabled;
        }

        /**
         * 设置是否开启 Application Caches 功能
         * @param appCacheEnabled {@code true} yes, {@code false} no
         * @return {@link Builder}
         */
        public Builder setAppCacheEnabled(final boolean appCacheEnabled) {
            this.mAppCacheEnabled = appCacheEnabled;
            return this;
        }

        /**
         * 获取 Application Caches 地址
         * @return Application Caches 地址
         */
        public String getAppCachePath() {
            return mAppCachePath;
        }

        /**
         * 设置 Application Caches 地址
         * @param appCachePath Application Caches 地址
         * @return {@link Builder}
         */
        public Builder setAppCachePath(final String appCachePath) {
            this.mAppCachePath = appCachePath;
            return this;
        }

        /**
         * 获取 Application Caches 大小
         * @return Application Caches 大小
         */
        public long getAppCacheMaxSize() {
            return mAppCacheMaxSize;
        }

        /**
         * 设置 Application Caches 大小
         * @param appCacheMaxSize Application Caches 大小
         * @return {@link Builder}
         */
        public Builder setAppCacheMaxSize(final long appCacheMaxSize) {
            this.mAppCacheMaxSize = appCacheMaxSize;
            return this;
        }

        /**
         * 是否支持数据库缓存
         * @return {@code true} yes, {@code false} no
         */
        public boolean isDatabaseEnabled() {
            return mDatabaseEnabled;
        }

        /**
         * 设置是否支持数据库缓存
         * @param databaseEnabled {@code true} yes, {@code false} no
         * @return {@link Builder}
         */
        public Builder setDatabaseEnabled(final boolean databaseEnabled) {
            this.mDatabaseEnabled = databaseEnabled;
            return this;
        }

        /**
         * 获取数据库缓存路径
         * @return 数据库缓存路径
         */
        public String getDatabasePath() {
            return mDatabasePath;
        }

        /**
         * 设置数据库缓存路径
         * @param databasePath 数据库缓存路径
         * @return {@link Builder}
         */
        public Builder setDatabasePath(final String databasePath) {
            this.mDatabasePath = databasePath;
            return this;
        }
    }
}