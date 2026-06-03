package dev.engine.web;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * detail: WebView Engine 接口
 * @author Ttt
 * <pre>
 *     对外只暴露内核无关的通用能力, 第三方内核 ( System WebView、GeckoView、X5 ) 通过各自实现解耦
 *     内核特有对象 ( WebSettings、WebViewClient、WebChromeClient、HitTestResult 等 ) 统一以 {@link Object} 传递
 * </pre>
 */
public interface IWebEngine<Config extends IWebEngine.EngineConfig,
        Item extends IWebEngine.EngineItem> {

    /**
     * detail: WebView Config
     * @author Ttt
     */
    interface EngineConfig {

        // 是否支持 JavaScript
        Boolean javaScriptEnabled();

        // 渲染优先级 ( 内核相关对象 )
        Object renderPriority();

        // 是否使用宽视图
        Boolean useWideViewPort();

        // 是否按宽度缩小内容以适合屏幕
        Boolean loadWithOverviewMode();

        // 基础布局算法 ( 内核相关对象 )
        Object layoutAlgorithm();

        // 是否支持缩放
        Boolean supportZoom();

        // 是否显示内置缩放工具
        Boolean builtInZoomControls();

        // 是否显示缩放工具
        Boolean displayZoomControls();

        // 文本缩放倍数
        int textZoom();

        // WebView 字体
        String standardFontFamily();

        // WebView 字体大小
        int defaultFontSize();

        // WebView 支持最小字体大小
        int minimumFontSize();

        // 等宽字体大小
        int defaultFixedFontSize();

        // 最小逻辑字体大小
        int minimumLogicalFontSize();

        // 等宽字体
        String fixedFontFamily();

        // 无衬线字体
        String sansSerifFontFamily();

        // 衬线字体
        String serifFontFamily();

        // 手写字体
        String cursiveFontFamily();

        // 奇幻字体
        String fantasyFontFamily();

        // 混合内容模式
        int mixedContentMode();

        // 是否支持自动加载图片
        Boolean loadsImagesAutomatically();

        // 是否支持通过 JS 打开新窗口
        Boolean javaScriptCanOpenWindowsAutomatically();

        // 编码格式
        String defaultTextEncodingName();

        // 是否允许网页执行定位操作
        Boolean geolocationEnabled();

        // 浏览器标识 UA
        String userAgentString();

        // 是否可以访问文件
        Boolean allowFileAccess();

        // 是否允许通过 file url 加载的 JS 代码读取其他的本地文件
        Boolean allowFileAccessFromFileURLs();

        // 是否允许通过 file url 加载的 JS 可以访问其他的源
        Boolean allowUniversalAccessFromFileURLs();

        // 是否不从网络加载资源
        Boolean blockNetworkLoads();

        // 是否不从网络加载图像资源
        Boolean blockNetworkImage();

        // 是否需要用户手势来播放媒体
        Boolean mediaPlaybackRequiresUserGesture();

        // WebView 缓存模式 ( null 表示未设置, 兼容 WebSettings.LOAD_DEFAULT = -1 取值 )
        Integer cacheMode();

        // 是否支持 DOM Storage
        Boolean domStorageEnabled();

        // 是否开启 Application Caches 功能 ( Android 13 已移除对应 WebSettings API )
        Boolean appCacheEnabled();

        // Application Caches 地址 ( Android 13 已移除对应 WebSettings API )
        String appCachePath();

        // Application Caches 大小 ( Android 13 已移除对应 WebSettings API )
        long appCacheMaxSize();

        // 是否支持数据库缓存
        Boolean databaseEnabled();

        // 数据库缓存路径
        String databasePath();

        // 是否允许访问 content:// 资源
        Boolean allowContentAccess();

        // 是否支持多窗口
        Boolean supportMultipleWindows();

        // 是否在加载时获取初始焦点
        Boolean needInitialFocus();

        // 是否开启安全浏览 ( Android 8.0 起支持 )
        Boolean safeBrowsingEnabled();

        // 是否开启离屏预渲染
        Boolean offscreenPreRaster();

        // 禁用的 ActionMode 菜单项 ( Android 7.0 起支持 )
        int disabledActionModeMenuItems();

        // 是否允许算法暗色模式 ( AndroidX WebKit, 需 ALGORITHMIC_DARKENING 特性支持 )
        Boolean algorithmicDarkeningAllowed();

        // 是否启用 PaymentRequest 支付 API ( AndroidX WebKit, 需 PAYMENT_REQUEST 特性支持 )
        Boolean paymentRequestEnabled();

        // 是否启用企业认证 AppLink 策略 ( AndroidX WebKit, 需 ENTERPRISE_AUTHENTICATION_APP_LINK_POLICY 特性支持 )
        Boolean enterpriseAuthenticationAppLinkPolicyEnabled();

        // 归因注册行为 ( AndroidX WebKit, 需 ATTRIBUTION_REGISTRATION_BEHAVIOR 特性支持 )
        int attributionRegistrationBehavior();

        // 是否启用 BackForwardCache 前进后退缓存 ( AndroidX WebKit, 需 BACK_FORWARD_CACHE 特性支持 )
        Boolean backForwardCacheEnabled();

        // 预测式加载状态 ( AndroidX WebKit, 实验性, 需 SPECULATIVE_LOADING 特性支持 )
        int speculativeLoadingStatus();

        // 长按超链接上下文菜单项 ( 位标志组合, AndroidX WebKit, 需 HYPERLINK_CONTEXT_MENU_ITEMS 特性支持 )
        int hyperlinkContextMenuItems();

        // 是否启用 PaymentRequest hasEnrolledInstrument ( AndroidX WebKit, 需 PAYMENT_REQUEST 特性支持 )
        Boolean hasEnrolledInstrumentEnabled();

        // 是否在 shouldInterceptRequest 中携带 Cookie ( AndroidX WebKit, 需 COOKIE_INTERCEPT 特性支持 )
        Boolean cookiesIncludedInShouldInterceptRequest();

        // Web Authentication ( WebAuthn ) 支持级别 ( AndroidX WebKit, 需 WEB_AUTHENTICATION 特性支持 )
        int webAuthenticationSupport();

        // 是否强制网页可缩放 ( GeckoView forceUserScalable / X5 强制手势缩放; System WebView 无对应实现 )
        Boolean forceUserScalable();
    }

    /**
     * detail: WebView ( View、Client、Listener、Params ) Item
     * @author Ttt
     */
    interface EngineItem {

        // WebView
        WeakReference<View> view();

        // WebView Config
        EngineConfig config();

        // 处理各种通知和请求事件对象 ( WebViewClient )
        Object webViewClient();

        // 辅助处理 Javascript 对话框、标题等对象 ( WebChromeClient )
        Object webChromeClient();

        // 下载监听 ( DownloadListener )
        Object downloadListener();

        // 查找结果监听 ( FindListener )
        Object findListener();

        // JS 交互注入对象集合 ( interfaceName-obj )
        Map<String, Object> javascriptInterfaces();

        // 页面加载监听 ( 内核无关, 实现层翻译为各内核回调 )
        OnWebListener onWebListener();
    }

    /**
     * detail: 页面加载监听
     * @author Ttt
     */
    interface OnWebListener {

        /**
         * 页面开始加载
         * @param view WebView
         * @param url  加载地址
         */
        void onPageStarted(
                View view,
                String url
        );

        /**
         * 页面加载完成
         * @param view WebView
         * @param url  加载地址
         */
        void onPageFinished(
                View view,
                String url
        );

        /**
         * 加载进度变化
         * @param view     WebView
         * @param progress 当前进度
         */
        void onProgressChanged(
                View view,
                int progress
        );

        /**
         * 接收网页标题
         * @param view  WebView
         * @param title 网页标题
         */
        void onReceivedTitle(
                View view,
                String title
        );

        /**
         * 接收加载错误
         * @param view        WebView
         * @param errorCode   错误码
         * @param description 错误描述
         * @param failingUrl  加载失败地址
         */
        void onReceivedError(
                View view,
                int errorCode,
                String description,
                String failingUrl
        );
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 WebView Engine Config
     * @return WebView Config
     */
    Config getConfig();

    /**
     * 初始化 WebView ( 应用配置、设置 Client、监听、JS 交互对象 )
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean initialize(Item item);

    /**
     * 应用 WebView Config
     * @param item   WebView Item
     * @param config WebView Config
     * @return {@code true} success, {@code false} fail
     */
    boolean applyConfig(
            Item item,
            Config config
    );

    /**
     * 获取 WebView
     * @param item WebView Item
     * @return WebView
     */
    View getWebView(Item item);

    /**
     * WebView 是否不为 null
     * @param item WebView Item
     * @return {@code true} yes, {@code false} no
     */
    boolean isWebViewNotEmpty(Item item);

    // ==========
    // = 加载方法 =
    // ==========

    /**
     * 加载网页
     * @param item WebView Item
     * @param url  资源地址
     * @return {@code true} success, {@code false} fail
     */
    boolean loadUrl(
            Item item,
            String url
    );

    /**
     * 加载网页
     * @param item                  WebView Item
     * @param url                   资源地址
     * @param additionalHttpHeaders Http 请求头信息
     * @return {@code true} success, {@code false} fail
     */
    boolean loadUrl(
            Item item,
            String url,
            Map<String, String> additionalHttpHeaders
    );

    /**
     * 加载 Html 代码
     * @param item     WebView Item
     * @param data     Html 数据
     * @param mimeType 加载网页的类型
     * @param encoding 编码格式
     * @return {@code true} success, {@code false} fail
     */
    boolean loadData(
            Item item,
            String data,
            String mimeType,
            String encoding
    );

    /**
     * 加载 Html 代码
     * @param item       WebView Item
     * @param baseUrl    基础链接
     * @param data       Html 数据
     * @param mimeType   加载网页的类型
     * @param encoding   编码格式
     * @param historyUrl 可用历史记录
     * @return {@code true} success, {@code false} fail
     */
    boolean loadDataWithBaseURL(
            Item item,
            String baseUrl,
            String data,
            String mimeType,
            String encoding,
            String historyUrl
    );

    /**
     * 使用 POST 方法将带有 postData 的 url 加载到 WebView 中
     * @param item     WebView Item
     * @param url      资源地址
     * @param postData post 数据 ( 注意 UrlEncode )
     * @return {@code true} success, {@code false} fail
     */
    boolean postUrl(
            Item item,
            String url,
            byte[] postData
    );

    // ==========
    // = 配置相关 =
    // ==========

    /**
     * 获取 WebView 配置 ( WebSettings )
     * @param item WebView Item
     * @return WebView 配置对象
     */
    Object getSettings(Item item);

    /**
     * 获取浏览器标识 UA
     * @param item WebView Item
     * @return 浏览器标识 UA
     */
    String getUserAgentString(Item item);

    /**
     * 设置浏览器标识 UA
     * @param item WebView Item
     * @param ua   浏览器标识 UA
     * @return {@code true} success, {@code false} fail
     */
    boolean setUserAgentString(
            Item item,
            String ua
    );

    // ==========
    // = JS 交互 =
    // ==========

    /**
     * 添加 JS 交互注入对象
     * @param item          WebView Item
     * @param obj           JavaScript 交互方法注入对象
     * @param interfaceName 在 JavaScript 中公开对象的名称
     * @return {@code true} success, {@code false} fail
     */
    boolean addJavascriptInterface(
            Item item,
            Object obj,
            String interfaceName
    );

    /**
     * 移除 JS 交互注入对象
     * @param item          WebView Item
     * @param interfaceName 在 JavaScript 中公开对象的名称
     * @return {@code true} success, {@code false} fail
     */
    boolean removeJavascriptInterface(
            Item item,
            String interfaceName
    );

    /**
     * 执行 JS 方法
     * @param item     WebView Item
     * @param script   JS 内容
     * @param callback 执行回调结果 ( ValueCallback )
     * @return {@code true} success, {@code false} fail
     */
    boolean evaluateJavascript(
            Item item,
            String script,
            Object callback
    );

    // ==========
    // = Client =
    // ==========

    /**
     * 设置处理各种通知和请求事件对象
     * @param item   WebView Item
     * @param client WebViewClient
     * @return {@code true} success, {@code false} fail
     */
    boolean setWebViewClient(
            Item item,
            Object client
    );

    /**
     * 获取处理各种通知和请求事件对象
     * @param item WebView Item
     * @return WebViewClient
     */
    Object getWebViewClient(Item item);

    /**
     * 设置辅助 WebView 处理 Javascript 对话框、标题等对象
     * @param item   WebView Item
     * @param client WebChromeClient
     * @return {@code true} success, {@code false} fail
     */
    boolean setWebChromeClient(
            Item item,
            Object client
    );

    /**
     * 获取辅助 WebView 处理 Javascript 对话框、标题等对象
     * @param item WebView Item
     * @return WebChromeClient
     */
    Object getWebChromeClient(Item item);

    /**
     * 设置下载监听
     * @param item     WebView Item
     * @param listener DownloadListener
     * @return {@code true} success, {@code false} fail
     */
    boolean setDownloadListener(
            Item item,
            Object listener
    );

    /**
     * 设置查找结果监听
     * @param item     WebView Item
     * @param listener FindListener
     * @return {@code true} success, {@code false} fail
     */
    boolean setFindListener(
            Item item,
            Object listener
    );

    // ==========
    // = 导航历史 =
    // ==========

    /**
     * WebView 是否可以后退
     * @param item WebView Item
     * @return {@code true} yes, {@code false} no
     */
    boolean canGoBack(Item item);

    /**
     * WebView 后退
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean goBack(Item item);

    /**
     * WebView 是否可以前进
     * @param item WebView Item
     * @return {@code true} yes, {@code false} no
     */
    boolean canGoForward(Item item);

    /**
     * WebView 前进
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean goForward(Item item);

    /**
     * WebView 是否可以跳转到当前起始点相距的历史记录
     * @param item  WebView Item
     * @param steps 相距索引
     * @return {@code true} yes, {@code false} no
     */
    boolean canGoBackOrForward(
            Item item,
            int steps
    );

    /**
     * WebView 跳转到当前起始点相距的历史记录
     * @param item  WebView Item
     * @param steps 相距索引 ( 负数后退、正数前进 )
     * @return {@code true} success, {@code false} fail
     */
    boolean goBackOrForward(
            Item item,
            int steps
    );

    /**
     * 获取 WebView 历史记录列表 ( WebBackForwardList )
     * @param item WebView Item
     * @return WebView 历史记录列表
     */
    Object copyBackForwardList(Item item);

    /**
     * 刷新页面 ( 当前页面的所有资源都会重新加载 )
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean reload(Item item);

    /**
     * 停止加载
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean stopLoading(Item item);

    // ==========
    // = 清理操作 =
    // ==========

    /**
     * 清除资源缓存
     * @param item             WebView Item
     * @param includeDiskFiles 是否清空本地缓存 ( false 则只会清空内存缓存, true 全部清空 )
     * @return {@code true} success, {@code false} fail
     */
    boolean clearCache(
            Item item,
            boolean includeDiskFiles
    );

    /**
     * 清除当前 WebView 访问的历史记录
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean clearHistory(Item item);

    /**
     * 清除自动完成填充的表单数据
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean clearFormData(Item item);

    /**
     * 清除高亮显示的查找结果
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean clearMatches(Item item);

    /**
     * 清除 SSL 偏好设置
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean clearSslPreferences(Item item);

    /**
     * 清除全部 Web 存储数据 ( localStorage、IndexedDB、WebSQL 等, 全局生效 )
     * @return {@code true} success, {@code false} fail
     */
    boolean deleteAllWebStorage();

    // ==========
    // = 状态查询 =
    // ==========

    /**
     * 获取缩放比例
     * @param item WebView Item
     * @return 缩放比例
     */
    float getScale(Item item);

    /**
     * 获取当前内容横向滚动距离
     * @param item WebView Item
     * @return 当前内容横向滚动距离
     */
    int getScrollX(Item item);

    /**
     * 获取当前可见区域的顶端距整个页面顶端的距离 ( 当前内容滚动的距离 )
     * @param item WebView Item
     * @return 当前内容滚动的距离
     */
    int getScrollY(Item item);

    /**
     * 获取 HTML 的高度 ( 原始高度, 不包括缩放后的高度 )
     * @param item WebView Item
     * @return HTML 的高度
     */
    int getContentHeight(Item item);

    /**
     * 获取缩放高度
     * @param item WebView Item
     * @return 缩放高度
     */
    int getScaleHeight(Item item);

    /**
     * 获取缩放高度
     * @param item  WebView Item
     * @param scale 缩放比例
     * @return 缩放高度
     */
    int getScaleHeight(
            Item item,
            float scale
    );

    /**
     * 获取 WebView 控件高度
     * @param item WebView Item
     * @return WebView 控件高度
     */
    int getHeight(Item item);

    /**
     * 获取当前 Url
     * @param item WebView Item
     * @return 当前 Url
     */
    String getUrl(Item item);

    /**
     * 获取最初请求 Url
     * @param item WebView Item
     * @return 最初请求 Url
     */
    String getOriginalUrl(Item item);

    /**
     * 获取当前页面标题
     * @param item WebView Item
     * @return 当前页面标题
     */
    String getTitle(Item item);

    /**
     * 获取当前页面加载进度
     * @param item WebView Item
     * @return 当前页面加载进度
     */
    int getProgress(Item item);

    /**
     * 获取当前页面图标 ( Bitmap )
     * @param item WebView Item
     * @return 当前页面图标
     */
    Object getFavicon(Item item);

    /**
     * 获取长按事件类型 ( HitTestResult )
     * @param item WebView Item
     * @return 长按事件类型
     */
    Object getHitTestResult(Item item);

    /**
     * 获取当前页面 SSL 证书 ( SslCertificate )
     * @param item WebView Item
     * @return 当前页面 SSL 证书
     */
    Object getCertificate(Item item);

    // ==========
    // = 滚动缩放 =
    // ==========

    /**
     * 将视图内容向下滚动一半页面大小
     * @param item   WebView Item
     * @param bottom 是否滑动到底部
     * @return {@code true} success, {@code false} fail
     */
    boolean pageDown(
            Item item,
            boolean bottom
    );

    /**
     * 将视图内容向上滚动一半页面大小
     * @param item WebView Item
     * @param top  是否滑动到顶部
     * @return {@code true} success, {@code false} fail
     */
    boolean pageUp(
            Item item,
            boolean top
    );

    /**
     * 滚动到指定坐标
     * @param item WebView Item
     * @param x    横向坐标
     * @param y    纵向坐标
     * @return {@code true} success, {@code false} fail
     */
    boolean scrollTo(
            Item item,
            int x,
            int y
    );

    /**
     * 滚动指定偏移量
     * @param item WebView Item
     * @param x    横向偏移量
     * @param y    纵向偏移量
     * @return {@code true} success, {@code false} fail
     */
    boolean scrollBy(
            Item item,
            int x,
            int y
    );

    /**
     * 以给定的速度开始滑动
     * @param item WebView Item
     * @param vx   横向速度
     * @param vy   纵向速度
     * @return {@code true} success, {@code false} fail
     */
    boolean flingScroll(
            Item item,
            int vx,
            int vy
    );

    /**
     * 按指定比例缩放
     * @param item       WebView Item
     * @param zoomFactor 缩放比例
     * @return {@code true} success, {@code false} fail
     */
    boolean zoomBy(
            Item item,
            float zoomFactor
    );

    /**
     * 放大
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean zoomIn(Item item);

    /**
     * 缩小
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean zoomOut(Item item);

    /**
     * 设置初始缩放比例
     * @param item           WebView Item
     * @param scaleInPercent 缩放百分比
     * @return {@code true} success, {@code false} fail
     */
    boolean setInitialScale(
            Item item,
            int scaleInPercent
    );

    // ==========
    // = 查找操作 =
    // ==========

    /**
     * 异步查找页面内所有匹配项
     * @param item WebView Item
     * @param find 查找内容
     * @return {@code true} success, {@code false} fail
     */
    boolean findAllAsync(
            Item item,
            String find
    );

    /**
     * 跳转到下一个 ( 上一个 ) 查找匹配项
     * @param item    WebView Item
     * @param forward 是否向前查找
     * @return {@code true} success, {@code false} fail
     */
    boolean findNext(
            Item item,
            boolean forward
    );

    // ==========
    // = 生命周期 =
    // ==========

    /**
     * 暂停 WebView ( Activity onPause )
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean onPause(Item item);

    /**
     * 恢复 WebView ( Activity onResume )
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean onResume(Item item);

    /**
     * 暂停所有 WebView 的布局、解析、JavaScript 定时器
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean pauseTimers(Item item);

    /**
     * 恢复所有 WebView 的布局、解析、JavaScript 定时器
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean resumeTimers(Item item);

    /**
     * 释放内存
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean freeMemory(Item item);

    /**
     * 销毁处理 ( 避免 WebView 引起的内存泄漏 )
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean destroy(Item item);

    // ==========
    // = 其他操作 =
    // ==========

    /**
     * 处理按键 ( 是否回退 )
     * @param item    WebView Item
     * @param keyCode 按键类型
     * @param event   按键事件
     * @return {@code true} 拦截事件, {@code false} 不拦截接着处理
     */
    boolean handlerKeyDown(
            Item item,
            int keyCode,
            KeyEvent event
    );

    /**
     * 关闭 WebView 硬件加速功能 ( 解决 WebView 闪烁问题 )
     * @param item WebView Item
     * @return {@code true} success, {@code false} fail
     */
    boolean setLayerTypeSoftware(Item item);

    /**
     * 设置 WebView 硬件加速类型
     * @param item      WebView Item
     * @param layerType 硬件加速类型
     * @param paint     {@link Paint}
     * @return {@code true} success, {@code false} fail
     */
    boolean setLayerType(
            Item item,
            int layerType,
            Paint paint
    );

    /**
     * 设置 WebView 是否处于可联网状态
     * @param item      WebView Item
     * @param networkUp 是否可联网
     * @return {@code true} success, {@code false} fail
     */
    boolean setNetworkAvailable(
            Item item,
            boolean networkUp
    );

    /**
     * 保存当前页面为 Web 归档文件
     * @param item     WebView Item
     * @param filename 保存文件路径
     * @return {@code true} success, {@code false} fail
     */
    boolean saveWebArchive(
            Item item,
            String filename
    );

    /**
     * 请求获取长按聚焦节点的超链接信息
     * @param item    WebView Item
     * @param hrefMsg 承载结果的消息 ( url 写入 data Bundle )
     * @return {@code true} success, {@code false} fail
     */
    boolean requestFocusNodeHref(
            Item item,
            Message hrefMsg
    );

    /**
     * 请求获取长按聚焦图片的地址信息
     * @param item WebView Item
     * @param msg  承载结果的消息 ( url 写入 data Bundle )
     * @return {@code true} success, {@code false} fail
     */
    boolean requestImageRef(
            Item item,
            Message msg
    );

    /**
     * 查询当前页面是否包含图片资源
     * @param item     WebView Item
     * @param response 承载结果的消息 ( 是否包含图片写入 arg1 )
     * @return {@code true} success, {@code false} fail
     */
    boolean documentHasImages(
            Item item,
            Message response
    );

    /**
     * 创建打印文档适配器 ( PrintDocumentAdapter )
     * @param item         WebView Item
     * @param documentName 文档名称
     * @return 打印文档适配器
     */
    Object createPrintDocumentAdapter(
            Item item,
            String documentName
    );

    /**
     * 设置渲染进程优先级策略 ( Android 8.0 起支持 )
     * @param item                      WebView Item
     * @param rendererRequestedPriority 渲染进程请求优先级
     * @param waivedWhenNotVisible      不可见时是否放弃优先级
     * @return {@code true} success, {@code false} fail
     */
    boolean setRendererPriorityPolicy(
            Item item,
            int rendererRequestedPriority,
            boolean waivedWhenNotVisible
    );

    // ==============
    // = Web Message =
    // ==============

    /**
     * 向网页投递一条 Web 消息 ( HTML5 postMessage )
     * @param item         WebView Item
     * @param message      Web 消息 ( WebMessage, 可携带 WebMessagePort )
     * @param targetOrigin 接收方源 ( 限定可接收消息的页面 origin )
     * @return {@code true} success, {@code false} fail
     */
    boolean postWebMessage(
            Item item,
            Object message,
            Uri targetOrigin
    );

    /**
     * 创建一对 Web 消息端口 ( HTML5 MessageChannel )
     * @param item WebView Item
     * @return 消息端口数组 ( WebMessagePort[] )
     */
    Object createWebMessageChannel(Item item);

    // ==========
    // = 全局静态 =
    // ==========

    /**
     * 设置是否开启 WebView 内容调试 ( Chrome inspect )
     * @param enabled 是否开启
     * @return {@code true} success, {@code false} fail
     */
    boolean setWebContentsDebuggingEnabled(boolean enabled);

    /**
     * 获取当前 WebView 内核包信息 ( PackageInfo, Android 8.0 起支持 )
     * @return 当前 WebView 内核包信息
     */
    Object getCurrentWebViewPackage();

    /**
     * 清除客户端证书选择偏好
     * @param onCleared 清除完成回调
     * @return {@code true} success, {@code false} fail
     */
    boolean clearClientCertPreferences(Runnable onCleared);

    // ===================
    // = AndroidX WebKit =
    // ===================

    /**
     * 判断 AndroidX WebKit 特性是否支持
     * @param feature 特性常量 ( WebViewFeature 中定义 )
     * @return {@code true} yes, {@code false} no
     */
    boolean isWebViewFeatureSupported(String feature);

    /**
     * 在文档开始加载时注入 JS ( 需 DOCUMENT_START_SCRIPT 特性 )
     * @param item               WebView Item
     * @param script             注入脚本
     * @param allowedOriginRules 允许的来源规则
     * @return 脚本句柄 ( ScriptHandler )
     */
    Object addDocumentStartJavaScript(
            Item item,
            String script,
            Set<String> allowedOriginRules
    );

    /**
     * 添加 Web 消息监听 ( 安全 JSBridge, 需 WEB_MESSAGE_LISTENER 特性 )
     * @param item               WebView Item
     * @param jsObjectName       注入到页面的对象名
     * @param allowedOriginRules 允许的来源规则
     * @param listener           消息监听 ( WebViewCompat.WebMessageListener )
     * @return {@code true} success, {@code false} fail
     */
    boolean addWebMessageListener(
            Item item,
            String jsObjectName,
            Set<String> allowedOriginRules,
            Object listener
    );

    /**
     * 移除 Web 消息监听
     * @param item         WebView Item
     * @param jsObjectName 注入到页面的对象名
     * @return {@code true} success, {@code false} fail
     */
    boolean removeWebMessageListener(
            Item item,
            String jsObjectName
    );

    /**
     * 获取处理各种通知和请求事件对象 ( 兼容版, 需 GET_WEB_VIEW_CLIENT 特性 )
     * @param item WebView Item
     * @return WebViewClient
     */
    Object getWebViewClientCompat(Item item);

    /**
     * 获取辅助处理 Javascript 对话框、标题等对象 ( 兼容版, 需 GET_WEB_CHROME_CLIENT 特性 )
     * @param item WebView Item
     * @return WebChromeClient
     */
    Object getWebChromeClientCompat(Item item);

    /**
     * 获取当前 WebView 内核包信息 ( 兼容版 )
     * @param context Context
     * @return 当前 WebView 内核包信息 ( PackageInfo )
     */
    Object getCurrentWebViewPackageCompat(Context context);

    /**
     * 获取 WebView 渲染进程 ( 需 GET_WEB_VIEW_RENDERER 特性 )
     * @param item WebView Item
     * @return 渲染进程 ( WebViewRenderProcess )
     */
    Object getWebViewRenderProcess(Item item);

    /**
     * 设置 WebView 渲染进程客户端 ( 需 WEB_VIEW_RENDERER_CLIENT_BASIC_USAGE 特性 )
     * @param item   WebView Item
     * @param client 渲染进程客户端 ( WebViewRenderProcessClient )
     * @return {@code true} success, {@code false} fail
     */
    boolean setWebViewRenderProcessClient(
            Item item,
            Object client
    );

    /**
     * 获取 WebView 渲染进程客户端
     * @param item WebView Item
     * @return 渲染进程客户端 ( WebViewRenderProcessClient )
     */
    Object getWebViewRenderProcessClient(Item item);

    /**
     * 是否启用多进程 ( 需 MULTI_PROCESS 特性 )
     * @return {@code true} yes, {@code false} no
     */
    boolean isMultiProcessEnabled();

    /**
     * 获取 Variations Header ( 需 GET_VARIATIONS_HEADER 特性 )
     * @return Variations Header
     */
    String getVariationsHeader();

    /**
     * 启动安全浏览 ( 需 START_SAFE_BROWSING 特性 )
     * @param context  Context
     * @param callback 启动结果回调 ( ValueCallback )
     * @return {@code true} success, {@code false} fail
     */
    boolean startSafeBrowsing(
            Context context,
            Object callback
    );

    /**
     * 设置安全浏览白名单 ( 需 SAFE_BROWSING_ALLOWLIST 特性 )
     * @param hosts    白名单 Host
     * @param callback 设置结果回调 ( ValueCallback )
     * @return {@code true} success, {@code false} fail
     */
    boolean setSafeBrowsingAllowlist(
            Set<String> hosts,
            Object callback
    );

    /**
     * 获取安全浏览隐私政策地址 ( 需 SAFE_BROWSING_PRIVACY_POLICY_URL 特性 )
     * @return 隐私政策地址 ( Uri )
     */
    Object getSafeBrowsingPrivacyPolicyUrl();

    /**
     * 设置 WebView 代理 ( 需 PROXY_OVERRIDE 特性 )
     * @param proxyConfig 代理配置 ( ProxyConfig )
     * @param executor    回调执行器
     * @param listener    生效回调
     * @return {@code true} success, {@code false} fail
     */
    boolean setProxyOverride(
            Object proxyConfig,
            Executor executor,
            Runnable listener
    );

    /**
     * 清除 WebView 代理 ( 需 PROXY_OVERRIDE 特性 )
     * @param executor 回调执行器
     * @param listener 生效回调
     * @return {@code true} success, {@code false} fail
     */
    boolean clearProxyOverride(
            Executor executor,
            Runnable listener
    );

    /**
     * 静音或取消静音 WebView ( 需 MUTE_AUDIO 特性 )
     * @param item WebView Item
     * @param mute 是否静音
     * @return {@code true} success, {@code false} fail
     */
    boolean setAudioMuted(
            Item item,
            boolean mute
    );

    /**
     * WebView 是否静音 ( 需 MUTE_AUDIO 特性 )
     * @param item WebView Item
     * @return {@code true} yes, {@code false} no
     */
    boolean isAudioMuted(Item item);

    /**
     * 投递可视状态回调 ( 内容可绘制时触发, 用于规避白屏, 需 VISUAL_STATE_CALLBACK 特性 )
     * @param item      WebView Item
     * @param requestId 请求 id ( 回调原样返回 )
     * @param callback  可视状态回调 ( WebViewCompat.VisualStateCallback )
     * @return {@code true} success, {@code false} fail
     */
    boolean postVisualStateCallback(
            Item item,
            long requestId,
            Object callback
    );

    /**
     * 设置 WebView 使用的 Profile ( 多 Profile 数据隔离, 需 MULTI_PROFILE 特性 )
     * @param item        WebView Item
     * @param profileName Profile 名称
     * @return {@code true} success, {@code false} fail
     */
    boolean setWebViewProfile(
            Item item,
            String profileName
    );

    /**
     * 获取 WebView 使用的 Profile ( 需 MULTI_PROFILE 特性 )
     * @param item WebView Item
     * @return Profile
     */
    Object getWebViewProfile(Item item);

    /**
     * 设置 WebView 用户代理元数据 ( 生成 UA Client Hints, 需 USER_AGENT_METADATA 特性 )
     * @param item     WebView Item
     * @param metadata 用户代理元数据 ( UserAgentMetadata )
     * @return {@code true} success, {@code false} fail
     */
    boolean setUserAgentMetadata(
            Item item,
            Object metadata
    );

    /**
     * 设置 WebView Media Integrity API 权限 ( 需 WEBVIEW_MEDIA_INTEGRITY_API_STATUS 特性 )
     * @param item             WebView Item
     * @param permissionConfig 权限配置 ( WebViewMediaIntegrityApiStatusConfig )
     * @return {@code true} success, {@code false} fail
     */
    boolean setWebViewMediaIntegrityApiStatus(
            Item item,
            Object permissionConfig
    );

    /**
     * 根据名称获取或创建 Profile ( 需 MULTI_PROFILE 特性 )
     * @param name Profile 名称
     * @return Profile
     */
    Object getOrCreateWebProfile(String name);

    /**
     * 根据名称获取 Profile ( 需 MULTI_PROFILE 特性 )
     * @param name Profile 名称
     * @return Profile ( 不存在返回 null )
     */
    Object getWebProfile(String name);

    /**
     * 根据名称删除 Profile ( 需 MULTI_PROFILE 特性 )
     * @param name Profile 名称
     * @return {@code true} success, {@code false} fail
     */
    boolean deleteWebProfile(String name);

    /**
     * 获取全部 Profile 名称 ( 需 MULTI_PROFILE 特性 )
     * @return Profile 名称列表
     */
    List<String> getAllWebProfileNames();

    /**
     * 设置 ServiceWorker 客户端 ( 需 SERVICE_WORKER_BASIC_USAGE 特性 )
     * @param client ServiceWorker 客户端 ( ServiceWorkerClientCompat )
     * @return {@code true} success, {@code false} fail
     */
    boolean setServiceWorkerClient(Object client);

    /**
     * 设置 ServiceWorker 是否允许访问 content:// 资源 ( 需 SERVICE_WORKER_CONTENT_ACCESS 特性 )
     * @param allow 是否允许
     * @return {@code true} success, {@code false} fail
     */
    boolean setServiceWorkerAllowContentAccess(boolean allow);

    /**
     * 设置 ServiceWorker 是否允许访问文件 ( 需 SERVICE_WORKER_FILE_ACCESS 特性 )
     * @param allow 是否允许
     * @return {@code true} success, {@code false} fail
     */
    boolean setServiceWorkerAllowFileAccess(boolean allow);

    /**
     * 设置 ServiceWorker 是否不从网络加载资源 ( 需 SERVICE_WORKER_BLOCK_NETWORK_LOADS 特性 )
     * @param block 是否阻止
     * @return {@code true} success, {@code false} fail
     */
    boolean setServiceWorkerBlockNetworkLoads(boolean block);

    /**
     * 设置 ServiceWorker 缓存模式 ( 需 SERVICE_WORKER_CACHE_MODE 特性 )
     * @param mode 缓存模式
     * @return {@code true} success, {@code false} fail
     */
    boolean setServiceWorkerCacheMode(int mode);

    /**
     * 是否正在进行性能跟踪 ( 需 TRACING_CONTROLLER_BASIC_USAGE 特性 )
     * @return {@code true} yes, {@code false} no
     */
    boolean isWebViewTracing();

    /**
     * 开始性能跟踪 ( 需 TRACING_CONTROLLER_BASIC_USAGE 特性 )
     * @param config 跟踪配置 ( TracingConfig )
     * @return {@code true} success, {@code false} fail
     */
    boolean startWebViewTracing(Object config);

    /**
     * 停止性能跟踪并输出结果 ( 需 TRACING_CONTROLLER_BASIC_USAGE 特性 )
     * @param outputStream 结果输出流 ( OutputStream, 可为 null )
     * @param executor     回调执行器
     * @return {@code true} success, {@code false} fail
     */
    boolean stopWebViewTracing(
            Object outputStream,
            Executor executor
    );

    /**
     * 获取 BackForwardCache 实时配置对象 ( 实验性, 需 BACK_FORWARD_CACHE_SETTINGS_EXPERIMENTAL_V3 特性 )
     * @param item WebView Item
     * @return BackForwardCacheSettings ( 实时对象, 修改即时生效 )
     */
    Object getBackForwardCacheSettings(Item item);

    /**
     * 设置 WebView 默认流量统计 TAG ( 需 DEFAULT_TRAFFICSTATS_TAGGING 特性 )
     * @param tag 流量统计 TAG
     * @return {@code true} success, {@code false} fail
     */
    boolean setDefaultTrafficStatsTag(int tag);

    /**
     * 保存 WebView 状态 ( 支持限制大小、是否含前进历史, 需 SAVE_STATE 特性 )
     * @param item                WebView Item
     * @param outState            状态保存 Bundle
     * @param maxSizeBytes        状态最大字节数 ( 超出则丢弃更早历史 )
     * @param includeForwardState 是否包含前进历史
     * @return {@code true} success, {@code false} fail
     */
    boolean saveState(
            Item item,
            Bundle outState,
            int maxSizeBytes,
            boolean includeForwardState
    );

    /**
     * 保存 WebView 状态 ( 基础版, 框架原生能力, 无特性门槛 )
     * @param item     WebView Item
     * @param outState 状态保存 Bundle
     * @return {@code true} success, {@code false} fail
     */
    boolean saveState(
            Item item,
            Bundle outState
    );

    /**
     * 添加导航监听 ( 回调在主线程, 需 NAVIGATION_LISTENER 特性 )
     * @param item     WebView Item
     * @param listener 导航监听 ( NavigationListener )
     * @return {@code true} success, {@code false} fail
     */
    boolean addNavigationListener(
            Item item,
            Object listener
    );

    /**
     * 添加导航监听 ( 指定回调执行器, 需 NAVIGATION_LISTENER 特性 )
     * @param item     WebView Item
     * @param executor 回调执行器
     * @param listener 导航监听 ( NavigationListener )
     * @return {@code true} success, {@code false} fail
     */
    boolean addNavigationListener(
            Item item,
            Executor executor,
            Object listener
    );

    /**
     * 移除导航监听 ( 需 NAVIGATION_LISTENER 特性 )
     * @param item     WebView Item
     * @param listener 导航监听 ( NavigationListener )
     * @return {@code true} success, {@code false} fail
     */
    boolean removeNavigationListener(
            Item item,
            Object listener
    );

    // ============
    // = 跨内核扩展 =
    // ============

    // =================================================================================
    // = 下列能力为 System WebView、GeckoView、Tencent X5 等多内核通用抽象, 便于实现层扩展替换 =
    // =================================================================================

    /**
     * 恢复 WebView 历史与状态 ( 与 saveState 配对 )
     * @param item    WebView Item
     * @param inState 状态 Bundle
     * @return {@code true} success, {@code false} fail
     * <pre>
     *     System / X5 接收 Bundle 状态, GeckoView 实现层可改用其 SessionState 进行恢复
     * </pre>
     */
    boolean restoreState(
            Item item,
            Bundle inState
    );

    /**
     * 设置 WebView 激活 ( 可见 ) 状态
     * @param item   WebView Item
     * @param active 是否激活
     * @return {@code true} success, {@code false} fail
     * <pre>
     *     GeckoView 对应 GeckoSession.setActive ( 非激活时显著降低内存 )
     *     System / X5 对应 onResume / onPause
     * </pre>
     */
    boolean setActive(
            Item item,
            boolean active
    );

    /**
     * 获取内核类型标识
     * @return 内核类型 ( 如 SystemWebView、GeckoView、X5 )
     */
    String getCoreType();

    /**
     * 获取内核版本
     * @param context Context
     * @return 内核版本 ( 如 System WebView 包版本、X5 TbsVersion、GeckoView buildId )
     */
    String getCoreVersion(Context context);

    /**
     * 内核是否就绪可用
     * @return {@code true} yes, {@code false} no
     * <pre>
     *     System WebView 通常恒为 true, X5 / GeckoView 需内核下载初始化完成
     * </pre>
     */
    boolean isCoreReady();

    // ==========
    // = Cookie =
    // ==========

    /**
     * 将 Cookie 设置到 WebView
     * @param url    加载的 Url
     * @param cookie 同步的 cookie
     * @return {@code true} success, {@code false} fail
     */
    boolean setCookie(
            String url,
            String cookie
    );

    /**
     * 将 Cookie 设置到 WebView ( 带设置结果回调 )
     * @param url      加载的 Url
     * @param cookie   同步的 cookie
     * @param callback 设置结果回调 ( ValueCallback )
     * @return {@code true} success, {@code false} fail
     */
    boolean setCookie(
            String url,
            String cookie,
            Object callback
    );

    /**
     * 获取指定 Url 的 Cookie
     * @param url Url
     * @return Cookie
     */
    String getCookie(String url);

    /**
     * 设置是否接受 Cookie
     * @param accept 是否接受
     * @return {@code true} success, {@code false} fail
     */
    boolean setAcceptCookie(boolean accept);

    /**
     * 是否接受 Cookie
     * @return {@code true} yes, {@code false} no
     */
    boolean acceptCookie();

    /**
     * 设置指定 WebView 是否接受第三方 Cookie
     * @param item   WebView Item
     * @param accept 是否接受
     * @return {@code true} success, {@code false} fail
     */
    boolean setAcceptThirdPartyCookies(
            Item item,
            boolean accept
    );

    /**
     * 指定 WebView 是否接受第三方 Cookie
     * @param item WebView Item
     * @return {@code true} yes, {@code false} no
     */
    boolean acceptThirdPartyCookies(Item item);

    /**
     * 是否存在 Cookie
     * @return {@code true} yes, {@code false} no
     */
    boolean hasCookies();

    /**
     * 移除 Cookie ( Session、All )
     * @param callback 移除回调 ( ValueCallback )
     */
    void removeCookie(Object callback);

    /**
     * 移除 Session Cookie
     * @param callback 移除回调 ( ValueCallback )
     * @return {@code true} success, {@code false} fail
     */
    boolean removeSessionCookie(Object callback);

    /**
     * 移除所有的 Cookie
     * @param callback 移除回调 ( ValueCallback )
     * @return {@code true} success, {@code false} fail
     */
    boolean removeAllCookie(Object callback);

    /**
     * 同步 ( 刷新 ) Cookie 到本地存储
     * @return {@code true} success, {@code false} fail
     */
    boolean flushCookie();

    /**
     * 获取指定 Url 全部 Cookie 的完整属性 ( 含 Domain、Path、Expires、Secure 等, 需 GET_COOKIE_INFO 特性 )
     * @param url Url
     * @return 各条 Cookie 的完整属性字符串列表
     */
    List<String> getCookieInfo(String url);
}