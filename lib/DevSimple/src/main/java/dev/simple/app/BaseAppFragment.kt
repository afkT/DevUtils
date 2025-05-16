package dev.simple.app

import androidx.databinding.ViewDataBinding
import dev.simple.app.base.FragmentVMType
import dev.simple.app.base.inter.BindingFragmentView
import dev.simple.app.base.simple.factory.SimpleFragmentIMPL
import dev.simple.app.controller.ui.theme.FragmentUITheme
import dev.simple.app.extension.mvvm.BaseMVVMFragment

/**
 * detail: Base App MVVM Fragment
 * @author Ttt
 * 如有额外参数等可统一在此基类增加, 避免污染底层基类过于复杂、混乱
 * 使用方法【统一继承该类即可】
 */
abstract class BaseAppFragment<VDB : ViewDataBinding, VM : BaseAppViewModel> :
    BaseMVVMFragment<VDB, VM> {

    // ==========
    // = 构造函数 =
    // ==========

//    constructor(
//        bindLayoutId: Int,
//        bindViewModelId: Int,
//        vmType: FragmentVMType = FragmentVMType.FRAGMENT
//    ) : super(bindLayoutId, bindViewModelId, vmType)
//
//    constructor(
//        bindLayoutView: BindingFragmentView,
//        bindViewModelId: Int,
//        vmType: FragmentVMType = FragmentVMType.FRAGMENT
//    ) : super(bindLayoutView, bindViewModelId, vmType)

    // ====================
    // = 敏捷简化开发扩展接口 =
    // ====================

    /**
     * 避免污染原始构造函数以及后续删减、扩展统一复制一份构造函数新增函数参数
     * 【后续基类不再复制该注释】如不考虑特殊化
     * 为了代码简洁可把下方构造方法覆盖上方代码
     */

    constructor(
        bindLayoutId: Int,
        bindViewModelId: Int,
        vmType: FragmentVMType = FragmentVMType.FRAGMENT,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null,
        simple_UITheme: ((FragmentUITheme) -> FragmentUITheme)? = null
    ) : super(bindLayoutId, bindViewModelId, vmType) {
        simpleFactory = SimpleFragmentIMPL.of(
            simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
        )
    }

    constructor(
        bindLayoutView: BindingFragmentView,
        bindViewModelId: Int,
        vmType: FragmentVMType = FragmentVMType.FRAGMENT,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null,
        simple_UITheme: ((FragmentUITheme) -> FragmentUITheme)? = null
    ) : super(bindLayoutView, bindViewModelId, vmType) {
        simpleFactory = SimpleFragmentIMPL.of(
            simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
        )
    }

    // ====================
    // = 敏捷简化开发扩展接口 =
    // ====================

    private val simpleFactory: SimpleFragmentIMPL

    override fun simpleInit() {
        simpleFactory.simpleInit(this)
    }

    override fun simpleStart() {
        simpleFactory.simpleStart(this)
    }

    override fun simpleAgile() {
        simpleFactory.simpleAgile(this)
    }

    override fun simplePreLoad() {
        simpleFactory.simplePreLoad(this)
    }

    // ===========================
    // = 敏捷简化开发扩展 - UITheme =
    // ===========================

    override fun createFragmentUITheme(theme: FragmentUITheme): FragmentUITheme {
        return super.createFragmentUITheme(
            simpleFactory.simpleUITheme(theme)
        )
    }
}