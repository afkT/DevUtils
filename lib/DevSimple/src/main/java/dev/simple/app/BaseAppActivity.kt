package dev.simple.app

import androidx.databinding.ViewDataBinding
import dev.simple.app.base.ActivityVMType
import dev.simple.app.base.inter.BindingActivityView
import dev.simple.app.base.simple.factory.SimpleActivityIMPL
import dev.simple.app.controller.ui.theme.ActivityUITheme
import dev.simple.app.extension.mvvm.BaseMVVMActivity

/**
 * detail: Base App MVVM Activity
 * @author Ttt
 * 如有额外参数等可统一在此基类增加, 避免污染底层基类过于复杂、混乱
 * 使用方法【统一继承该类即可】
 */
abstract class BaseAppActivity<VDB : ViewDataBinding, VM : BaseAppViewModel> :
    BaseMVVMActivity<VDB, VM> {

    // ==========
    // = 构造函数 =
    // ==========

//    constructor(
//        bindLayoutId: Int,
//        bindViewModelId: Int,
//        vmType: ActivityVMType = ActivityVMType.ACTIVITY
//    ) : super(bindLayoutId, bindViewModelId, vmType)
//
//    constructor(
//        bindLayoutView: BindingActivityView?,
//        bindViewModelId: Int,
//        vmType: ActivityVMType = ActivityVMType.ACTIVITY
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
        vmType: ActivityVMType = ActivityVMType.ACTIVITY,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null,
        simple_UITheme: ((ActivityUITheme) -> ActivityUITheme)? = null
    ) : super(bindLayoutId, bindViewModelId, vmType) {
        simpleFactory = SimpleActivityIMPL.of(
            simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
        )
    }

    constructor(
        bindLayoutView: BindingActivityView?,
        bindViewModelId: Int,
        vmType: ActivityVMType = ActivityVMType.ACTIVITY,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null,
        simple_UITheme: ((ActivityUITheme) -> ActivityUITheme)? = null
    ) : super(bindLayoutView, bindViewModelId, vmType) {
        simpleFactory = SimpleActivityIMPL.of(
            simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
        )
    }

    // ====================
    // = 敏捷简化开发扩展接口 =
    // ====================

    private val simpleFactory: SimpleActivityIMPL

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

    override fun createActivityUITheme(theme: ActivityUITheme): ActivityUITheme {
        return super.createActivityUITheme(
            simpleFactory.simpleUITheme(theme)
        )
    }
}