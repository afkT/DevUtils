package dev.simple.app.extension.theme

import android.view.View
import androidx.databinding.ViewDataBinding
import dev.simple.app.BaseAppViewModel
import dev.simple.app.base.BaseFragment
import dev.simple.app.base.FragmentVMType
import dev.simple.app.base.inter.BindingFragmentView
import dev.simple.app.controller.ui.theme.FragmentUITheme

/**
 * detail: Base Theme Fragment
 * @author Ttt
 * 在 [BaseFragment] 基础上封装 UITheme 样式 Intent 传参控制处理
 */
abstract class BaseUIThemeFragment<VDB : ViewDataBinding, VM : BaseAppViewModel>(
    private val bindLayoutId: Int = 0,
    private val bindLayoutView: BindingFragmentView? = null,
    vmType: FragmentVMType = FragmentVMType.FRAGMENT
) : BaseFragment<VDB, VM>(vmType) {

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

    private val fragmentUITheme: FragmentUITheme by lazy {
        createFragmentUITheme(FragmentUITheme.with(arguments))
    }

    /**
     * 初始化创建 FragmentUITheme
     * @return [FragmentUITheme]
     */
    open fun createFragmentUITheme(theme: FragmentUITheme): FragmentUITheme {
        return theme
    }

    // =================
    // = IUIController =
    // =================

    override fun isContentAssistSafe(): Boolean {
        return fragmentUITheme.isContentAssistSafe()
    }

    // ===========
    // = Base UI =
    // ===========

    override fun isStatusBarFrame(): Boolean {
        return fragmentUITheme.isStatusBarFrame()
    }

    override fun isLightMode(): Boolean {
        return fragmentUITheme.isLightMode()
    }

    override fun isAddStatusBar(): Boolean {
        return fragmentUITheme.isAddStatusBar()
    }

    override fun isAddTitleBar(): Boolean {
        return fragmentUITheme.isAddTitleBar()
    }

    // ===============
    // = Window Flag =
    // ===============

    override fun isFlagFullScreen(): Boolean {
        return fragmentUITheme.isFlagFullScreen()
    }

    override fun isFlagKeepScreen(): Boolean {
        return fragmentUITheme.isFlagKeepScreen()
    }

    override fun isFlagSecure(): Boolean {
        return fragmentUITheme.isFlagSecure()
    }

    // ==================
    // = Window Feature =
    // ==================

    override fun isFeatureNoTitle(): Boolean {
        return fragmentUITheme.isFeatureNoTitle()
    }
}