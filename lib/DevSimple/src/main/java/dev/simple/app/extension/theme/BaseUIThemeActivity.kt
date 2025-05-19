package dev.simple.app.extension.theme

import android.view.View
import androidx.databinding.ViewDataBinding
import dev.simple.app.BaseAppViewModel
import dev.simple.app.base.ActivityVMType
import dev.simple.app.base.BaseActivity
import dev.simple.app.base.interfaces.BindingActivityView
import dev.simple.app.controller.ui.theme.ActivityUITheme

/**
 * detail: Base Theme Activity
 * @author Ttt
 * 在 [BaseActivity] 基础上封装 UITheme 样式 Intent 传参控制处理
 */
abstract class BaseUIThemeActivity<VDB : ViewDataBinding, VM : BaseAppViewModel>(
    private val bindLayoutId: Int = 0,
    private val bindLayoutView: BindingActivityView? = null,
    vmType: ActivityVMType = ActivityVMType.ACTIVITY
) : BaseActivity<VDB, VM>(vmType) {

    // ==================
    // = IDevBaseLayout =
    // ==================

    /**
     * 获取 Layout Id ( 用于 contentLinear addView )
     * @return Layout Id
     */
    override fun baseLayoutId(): Int {
        return bindLayoutId
    }

    /**
     * 获取 Layout View ( 用于 contentLinear addView )
     * @return Layout View
     */
    override fun baseLayoutView(): View? {
        return bindLayoutView?.bind(this, layoutInflater)
    }

    // ==============
    // = 对外公开方法 =
    // ==============

    private val activityUITheme: ActivityUITheme by lazy {
        createActivityUITheme(ActivityUITheme.with(intent))
    }

    /**
     * 初始化创建 ActivityUITheme
     * @return [ActivityUITheme]
     */
    open fun createActivityUITheme(theme: ActivityUITheme): ActivityUITheme {
        return theme
    }

    // =================
    // = IUIController =
    // =================

    override fun isContentAssistSafe(): Boolean {
        return activityUITheme.isContentAssistSafe()
    }

    // ===========
    // = Base UI =
    // ===========

    override fun isStatusBarFrame(): Boolean {
        return activityUITheme.isStatusBarFrame()
    }

    override fun isLightMode(): Boolean {
        return activityUITheme.isLightMode()
    }

    override fun isAddStatusBar(): Boolean {
        return activityUITheme.isAddStatusBar()
    }

    override fun isAddTitleBar(): Boolean {
        return activityUITheme.isAddTitleBar()
    }

    // ===============
    // = Window Flag =
    // ===============

    override fun isFlagFullScreen(): Boolean {
        return activityUITheme.isFlagFullScreen()
    }

    override fun isFlagKeepScreen(): Boolean {
        return activityUITheme.isFlagKeepScreen()
    }

    override fun isFlagSecure(): Boolean {
        return activityUITheme.isFlagSecure()
    }

    // ==================
    // = Window Feature =
    // ==================

    override fun isFeatureNoTitle(): Boolean {
        return activityUITheme.isFeatureNoTitle()
    }
}