package dev.simple.app.controller.ui.theme

import dev.base.utils.assist.DevBaseContentAssist

/**
 * detail: 基础样式 Key
 * @author Ttt
 */
object UIThemeKey {

    /**
     * [DevBaseContentAssist] 是否安全处理
     */
    const val isContentAssistSafe = "isContentAssistSafe"

    // ===========
    // = Base UI =
    // ===========

    /**
     * StatusBar 是否占位显示 ( StatusBar 底部透明 )
     */
    const val isStatusBarFrame = "isStatusBarFrame"

    /**
     * StatusBar 是否高亮模式
     */
    const val isLightMode = "isLightMode"

    /**
     * 是否添加 StatusBar
     */
    const val isAddStatusBar = "isAddStatusBar"

    /**
     * 是否添加 TitleBar
     */
    const val isAddTitleBar = "isAddTitleBar"

    // ===============
    // = Window Flag =
    // ===============

    /**
     * Activity 是否全屏
     */
    const val isFlagFullScreen = "isFlagFullScreen"

    /**
     * 是否屏幕常亮
     */
    const val isFlagKeepScreen = "isFlagKeepScreen"

    /**
     * 是否允许截屏
     */
    const val isFlagSecure = "isFlagSecure"

    // ==================
    // = Window Feature =
    // ==================

    /**
     * 是否屏幕页面为无标题
     */
    const val isFeatureNoTitle = "isFeatureNoTitle"
}