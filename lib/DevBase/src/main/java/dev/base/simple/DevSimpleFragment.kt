package dev.base.simple

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import dev.base.simple.contracts.binding.BindingFragmentView
import dev.base.simple.contracts.factory.SimpleFragmentIMPL
import dev.base.simple.mvvm.DevSimpleMVVMFragment

/**
 * detail: Simple MVVM Fragment
 * @author Ttt
 */
abstract class DevSimpleFragment<VDB : ViewDataBinding, VM : ViewModel> :
    DevSimpleMVVMFragment<VDB, VM> {

    // ==========
    // = 构造函数 =
    // ==========

//    constructor(
//        bindLayoutId: Int,
//        bindViewModelId: Int,
//        vmType: FragmentVMType = FragmentVMType.FRAGMENT
//    ) : super(bindLayoutId, null, bindViewModelId, vmType)
//
//    constructor(
//        bindLayoutView: BindingFragmentView,
//        bindViewModelId: Int,
//        vmType: FragmentVMType = FragmentVMType.FRAGMENT
//    ) : super(0, bindLayoutView, bindViewModelId, vmType)

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
    ) : super(bindLayoutId, null, bindViewModelId, vmType) {
        simpleFactory = SimpleFragmentIMPL.of(
            simple_Init, simple_Start, simple_PreLoad, simple_Agile
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
    ) : super(0, bindLayoutView, bindViewModelId, vmType) {
        simpleFactory = SimpleFragmentIMPL.of(
            simple_Init, simple_Start, simple_PreLoad, simple_Agile
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
}