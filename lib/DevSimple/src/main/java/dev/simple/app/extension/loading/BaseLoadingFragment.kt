package dev.simple.app.extension.loading

import androidx.databinding.ViewDataBinding
import dev.simple.app.BaseAppViewModel
import dev.simple.app.base.FragmentVMType
import dev.simple.app.base.inter.BindingFragmentView
import dev.simple.app.controller.loading.BaseLoadingController
import dev.simple.app.controller.ui.theme.FragmentUITheme

/**
 * detail: Base Loading Fragment
 * @author Ttt
 * 区别于 Loading Skeleton 只用在首次进入管理控制
 * 该 Loading 是重复性使用在 Content Layout 上层显示
 */
abstract class BaseLoadingFragment<VDB : ViewDataBinding, VM : BaseAppViewModel> :
    BaseLoadingSkeletonFragment<VDB, VM> {

    // ==========
    // = 构造函数 =
    // ==========

    constructor(
        bindLayoutId: Int,
        bindViewModelId: Int,
        vmType: FragmentVMType = FragmentVMType.FRAGMENT,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null,
        simple_UITheme: ((FragmentUITheme) -> FragmentUITheme)? = null
    ) : super(
        bindLayoutId, bindViewModelId, vmType,
        simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
    )

    constructor(
        bindLayoutView: BindingFragmentView,
        bindViewModelId: Int,
        vmType: FragmentVMType = FragmentVMType.FRAGMENT,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null,
        simple_UITheme: ((FragmentUITheme) -> FragmentUITheme)? = null
    ) : super(
        bindLayoutView, bindViewModelId, vmType,
        simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
    )

    // ===========
    // = Loading =
    // ===========

    // 基础 Loading 控制封装
    open val loadingController: BaseLoadingController<VM> by lazy {
        BaseLoadingController(contentAssist)
    }

    // ============
    // = override =
    // ============

    override fun initViewModel() {
        super.initViewModel()

        // 初始化 ViewModel loadingController
        loadingController.initialize(viewModel)
    }
}