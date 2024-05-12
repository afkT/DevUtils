package dev.simple.app.controller.ui.theme

// ======================
// = BaseUITheme 扩展函数 =
// ======================

/**
 * 统一填充样式方便控制
 */
private fun <T : BaseUITheme<*>> T.unifiedUITheme(): T {
    // DevBaseContentAssist 是否安全处理
    setContentAssistSafe(true)
    // StatusBar 是否占位显示 ( StatusBar 底部透明 )
    setStatusBarFrame(true)
    // StatusBar 是否高亮模式
    setLightMode(true)
    // 是否添加 StatusBar
    setAddStatusBar(false)
    // 是否添加 TitleBar
    setAddTitleBar(false)
    // Activity 是否全屏
    setFlagFullScreen(false)
    // 是否屏幕常亮
    setFlagKeepScreen(false)
    // 是否允许截屏
    setFlagSecure(true)
    // 是否屏幕页面为无标题
    setFeatureNoTitle(true)
    return this
}

// ==========
// = 通用扩展 =
// ==========

/**
 * 填充默认 Activity 样式
 */
fun <T : BaseUITheme<*>> T.defaultActivityUITheme(): T {
    return defaultCompleteUITheme()
}

/**
 * 填充默认 Fragment 样式
 */
fun <T : BaseUITheme<*>> T.defaultFragmentUITheme(): T {
    return defaultBlankUITheme()
}

/**
 * 填充默认 App 启动页样式
 */
fun <T : BaseUITheme<*>> T.defaultAppLauncherUITheme(): T {
    // 统一填充样式方便控制
    unifiedUITheme()
    return this
}

/**
 * 填充模块化首页入口样式
 */
fun <T : BaseUITheme<*>> T.defaultMainContainerUITheme(): T {
    defaultCompleteUITheme()
    return this
}

/**
 * 设置完整样式 ( 存在标题、自定义状态栏且颜色自定义 )
 */
fun <T : BaseUITheme<*>> T.defaultCompleteUITheme(): T {
    // 统一填充样式方便控制
    unifiedUITheme()
    // 是否添加 StatusBar
    setAddStatusBar(true)
    // 是否添加 TitleBar
    setAddTitleBar(true)
    return this
}

/**
 * 设置空白样式 ( 只存在默认系统状态栏 )
 */
fun <T : BaseUITheme<*>> T.defaultBlankUITheme(): T {
    // 统一填充样式方便控制
    unifiedUITheme()
    return this
}

/**
 * 设置 Activity 空白样式 ( 整个页面空白 )
 */
fun <T : BaseUITheme<*>> T.defaultActivityBlankUITheme(): T {
    // 统一填充样式方便控制
    unifiedUITheme()
    return this
}