package dev.assist;

import android.webkit.WebView;

import java.util.Map;

/**
 * detail: WebView 辅助类
 * @author Ttt
 * <pre>
 *     Android WebView 常见问题及解决方案汇总
 *     @see <a href="https://www.cnblogs.com/Free-Thinker/p/6179016.html"/>
 *     WebView 全面解析
 *     @see <a href="https://www.jianshu.com/p/3e0136c9e748"/>
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
     */
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
    public WebViewAssist loadData(final String data) {
        return loadData(data, "text/html", "utf-8");
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
}
