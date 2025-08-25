package afkt.environment.use.base

import androidx.databinding.ViewDataBinding
import dev.base.simple.DevSimpleFragment
import dev.base.simple.FragmentVMType
import dev.base.simple.contracts.binding.BindingFragmentView

/**
 * detail: Fragment MVVM 基类
 * @author Ttt
 */
open class BaseFragment<VDB : ViewDataBinding, VM : BaseViewModel> :
    DevSimpleFragment<VDB, VM> {

    // ==========
    // = 构造函数 =
    // ==========

    constructor(
        bindLayoutId: Int,
        bindViewModelId: Int = -1,
        vmType: FragmentVMType = FragmentVMType.FRAGMENT,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null
    ) : super(
        bindLayoutId, bindViewModelId, vmType,
        simple_Init, simple_Start, simple_PreLoad, simple_Agile
    )

    constructor(
        bindLayoutView: BindingFragmentView,
        bindViewModelId: Int = -1,
        vmType: FragmentVMType = FragmentVMType.FRAGMENT,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null
    ) : super(
        bindLayoutView, bindViewModelId, vmType,
        simple_Init, simple_Start, simple_PreLoad, simple_Agile
    )
}