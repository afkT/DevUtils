package dev.simple.app.extension.loading

import androidx.databinding.ViewDataBinding
import dev.simple.app.BaseAppViewModel
import dev.simple.app.base.FragmentVMType
import dev.simple.app.base.inter.BindingFragmentView
import dev.simple.app.base.simple.factory.SimpleFragmentIMPL
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
        simple_Init: ((BaseLoadingFragment<VDB, VM>) -> Unit)? = null,
        simple_Start: ((BaseLoadingFragment<VDB, VM>) -> Unit)? = null,
        simple_PreLoad: ((BaseLoadingFragment<VDB, VM>) -> Unit)? = null,
        simple_Agile: ((BaseLoadingFragment<VDB, VM>) -> Unit)? = null,
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
        simple_Init: ((BaseLoadingFragment<VDB, VM>) -> Unit)? = null,
        simple_Start: ((BaseLoadingFragment<VDB, VM>) -> Unit)? = null,
        simple_PreLoad: ((BaseLoadingFragment<VDB, VM>) -> Unit)? = null,
        simple_Agile: ((BaseLoadingFragment<VDB, VM>) -> Unit)? = null,
        simple_UITheme: ((FragmentUITheme) -> FragmentUITheme)? = null
    ) : super(bindLayoutView, bindViewModelId, vmType) {
        simpleFactory = SimpleFragmentIMPL.of(
            simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
        )
    }

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

    // ====================
    // = 敏捷简化开发扩展接口 =
    // ====================

    private val simpleFactory: SimpleFragmentIMPL<BaseLoadingFragment<VDB, VM>>

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