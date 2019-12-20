package dev.assist;

import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
 * </pre>
 */
public class WebViewAssist {

    // 日志 TAG
    private static final String TAG = WebViewAssist.class.getSimpleName();
    // WebView
    private WebView mWebView;

    public WebViewAssist() {
    }

    /**
     * 设置 WebView
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

    // ============
    // = 加载方法 =
    // ============

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
    public WebViewAssist loadUrl(final String url, final Map<String, String> additionalHttpHeaders) {
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
    public WebViewAssist loadData(final String data, final String mimeType, final String encoding) {
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
    public WebViewAssist loadDataWithBaseURL(final String baseUrl, final String data, final String mimeType, final String encoding, final String historyUrl) {
        if (isWebViewNotEmpty()) {
            mWebView.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
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
    public WebViewAssist loadDataWithBaseURL(final String baseUrl, final String data, final String historyUrl) {
        return loadDataWithBaseURL(baseUrl, data, "text/html", "utf-8", historyUrl);
    }

    // ================
    // = 快速操作方法 =
    // ================

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
    public WebViewAssist addJavascriptInterface(final Object obj, final String interfaceName) {
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
     * @param script         JS 内容
     * @param resultCallback 执行回调结果 ( 返回值 )
     * @return {@link WebViewAssist}
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public WebViewAssist evaluateJavascript(final String script, final ValueCallback<String> resultCallback) {
        if (isWebViewNotEmpty()) {
            try {
                mWebView.evaluateJavascript(script, resultCallback);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "evaluateJavascript");
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public WebViewClient getWebViewClient() {
        return isWebViewNotEmpty() ? mWebView.getWebViewClient() : null;
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public WebChromeClient getWebChromeClient() {
        return isWebViewNotEmpty() ? mWebView.getWebChromeClient() : null;
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
        }
        return this;
    }

    /**
     * WebView 是否可以后退
     * @return {@code true} yes, {@code false} no
     */
    public boolean canGoBack() {
        return isWebViewNotEmpty() ? mWebView.canGoBack() : false;
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
        return isWebViewNotEmpty() ? mWebView.canGoForward() : false;
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
        return isWebViewNotEmpty() ? mWebView.canGoBackOrForward(steps) : false;
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
    public boolean handlerKeyDown(final int keyCode, final KeyEvent event) {
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
        return setLayerType(View.LAYER_TYPE_SOFTWARE,null);
    }

    /**
     * 设置 WebView 硬件加速类型
     * @param layerType 硬件加速类型
     * @param paint     {@link Paint}
     * @return {@link WebViewAssist}
     */
    public WebViewAssist setLayerType(final int layerType, final Paint paint) {
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
    public static boolean setCookie(final String url, final String cookie) {
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
}