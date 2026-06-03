package dev.engine.core.web

import android.annotation.SuppressLint
import android.webkit.WebSettings
import dev.utils.DevFinal
import dev.utils.app.VersionUtils

/**
 * 创建 WebView Web Config 默认配置值
 * @return [WebConfig]
 */
@SuppressLint("SetJavaScriptEnabled")
internal fun defaultWebConfig(): WebConfig {
    return WebConfig.create()
        // WebView 缓存模式
        .setCacheMode(WebSettings.LOAD_DEFAULT)
        // 混合内容模式
        .setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW)
        // 渲染优先级高
        .setRenderPriority(WebSettings.RenderPriority.HIGH)
        // 基础布局算法
        .setLayoutAlgorithm(
            if (VersionUtils.isLollipop()) {
                WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
            } else {
                WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            }
        )
        // 是否使用宽视图
        .setUseWideViewPort(true)
        // 是否按宽度缩小内容以适合屏幕
        .setLoadWithOverviewMode(true)
        // 是否支持缩放
        .setSupportZoom(false)
        // 是否显示内置缩放工具
        .setBuiltInZoomControls(false)
        // 是否显示缩放工具
        .setDisplayZoomControls(false)
        // 文本缩放倍数
        .setTextZoom(0)
        // WebView 字体大小
        .setDefaultFontSize(0)
        // WebView 支持最小字体大小
        .setMinimumFontSize(0)
        // 编码格式
        .setDefaultTextEncodingName(DevFinal.ENCODE.UTF_8)
        // 是否允许网页执行定位操作
        .setGeolocationEnabled(true)
        // 是否支持 JavaScript
        .setJavaScriptEnabled(true)
        // 是否支持通过 JS 打开新窗口
        .setJavaScriptCanOpenWindowsAutomatically(true)
        // 是否可以访问文件
        .setAllowFileAccess(true)
        // 是否允许通过 file url 加载的 JS 代码读取其他的本地文件
        .setAllowFileAccessFromFileURLs(false)
        // 是否允许通过 file url 加载的 JS 可以访问其他的源
        .setAllowUniversalAccessFromFileURLs(false)
        // 是否支持自动加载图片
        .setLoadsImagesAutomatically(true)
        // 是否不从网络加载资源
        .setBlockNetworkLoads(false)
        // 是否不从网络加载图像资源
        .setBlockNetworkImage(false)
        // 是否需要用户手势来播放媒体
        .setMediaPlaybackRequiresUserGesture(true)
        // 是否支持 DOM Storage
        .setDomStorageEnabled(true)
        // 是否支持数据库缓存
        .setDatabaseEnabled(true)
        // 是否开启 Application Caches 功能
        .setAppCacheEnabled(true)
        // Application Caches 大小
        .setAppCacheMaxSize((50 * 1024 * 1024).toLong())
}