package dev.base.simple

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import dev.base.core.interfaces.IDevBase
import dev.base.simple.contracts.binding.BindingActivityView
import dev.base.simple.contracts.factory.SimpleActivityIMPL
import dev.base.simple.mvvm.DevSimpleMVVMActivity

/**
 * detail: Simple MVVM Activity
 * @author Ttt
 * 如有额外参数等可统一在此基类增加, 避免污染底层基类过于复杂、混乱
 * 使用方法【统一继承该类即可】
 */
abstract class DevSimpleActivity<VDB : ViewDataBinding, VM : ViewModel> :
    DevSimpleMVVMActivity<VDB, VM> {

    // ==========
    // = 构造函数 =
    // ==========

//    constructor(
//        bindLayoutId: Int,
//        bindViewModelId: Int,
//        vmType: ActivityVMType = ActivityVMType.ACTIVITY
//    ) : super(bindLayoutId, null, bindViewModelId, vmType)
//
//    constructor(
//        bindLayoutView: BindingActivityView?,
//        bindViewModelId: Int,
//        vmType: ActivityVMType = ActivityVMType.ACTIVITY
//    ) : super(IDevBase.NONE, bindLayoutView, bindViewModelId, vmType)

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
    ) : super(bindLayoutId, null, bindViewModelId, vmType) {
        simpleFactory = SimpleActivityIMPL.of(
            simple_Init, simple_Start, simple_PreLoad, simple_Agile
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
    ) : super(IDevBase.NONE, bindLayoutView, bindViewModelId, vmType) {
        simpleFactory = SimpleActivityIMPL.of(
            simple_Init, simple_Start, simple_PreLoad, simple_Agile
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
}