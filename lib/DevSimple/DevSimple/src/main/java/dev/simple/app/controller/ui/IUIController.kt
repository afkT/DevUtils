package dev.simple.app.controller.ui

import android.app.Activity
import dev.base.utils.assist.DevBaseContentAssist
import dev.utils.app.assist.WindowAssist
import dev.utils.common.able.Operatorable

/**
 * detail: UI 基类控制器接口
 * @author Ttt
 */
interface IUIController {

    /**
     * Activity onCreate 创建之前触发
     * @return Operatorable.OperatorByParam3<Void, Activity, WindowAssist, IUIController>
     */
    fun onCreateBefore(): Operatorable.OperatorByParam3<Void, Activity,
            WindowAssist, IUIController>? = null

    /**
     * [DevBaseContentAssist] 是否安全处理
     * @return `true` yes, `false` no
     */
    fun isContentAssistSafe(): Boolean = true

    // ===========
    // = Base UI =
    // ===========

    /**
     * StatusBar 是否占位显示 ( StatusBar 底部透明 )
     * @return `true` yes, `false` no
     */
    fun isStatusBarFrame(): Boolean = true

    /**
     * StatusBar 是否高亮模式
     * @return `true` yes, `false` no
     */
    fun isLightMode(): Boolean = true

    /**
     * 是否添加 StatusBar
     * @return `true` yes, `false` no
     */
    fun isAddStatusBar(): Boolean = true

    /**
     * 是否添加 TitleBar
     * @return `true` yes, `false` no
     */
    fun isAddTitleBar(): Boolean = true

    // ===============
    // = Window Flag =
    // ===============

    /**
     * Activity 是否全屏
     * @return `true` yes, `false` no
     */
    fun isFlagFullScreen(): Boolean = false

    /**
     * 是否屏幕常亮
     * @return `true` yes, `false` no
     */
    fun isFlagKeepScreen(): Boolean = false

    /**
     * 是否允许截屏
     * @return `true` yes, `false` no
     */
    fun isFlagSecure(): Boolean = true

    // ==================
    // = Window Feature =
    // ==================

    /**
     * 是否屏幕页面为无标题
     * @return `true` yes, `false` no
     */
    fun isFeatureNoTitle(): Boolean = true
}