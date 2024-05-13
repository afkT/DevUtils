package dev.simple.app.extension.loading

import androidx.databinding.ViewDataBinding
import dev.simple.app.BaseAppViewModel
import dev.simple.app.base.ActivityVMType
import dev.simple.app.base.inter.BindingActivityView
import dev.simple.app.base.simple.factory.SimpleActivityIMPL
import dev.simple.app.controller.loading.BaseLoadingController
import dev.simple.app.controller.ui.theme.ActivityUITheme

/**
 * detail: Base Loading Activity
 * @author Ttt
 * 区别于 Loading Skeleton 只用在首次进入管理控制
 * 该 Loading 是重复性使用在 Content Layout 上层显示
 */
abstract class BaseLoadingActivity<VDB : ViewDataBinding, VM : BaseAppViewModel> :
    BaseLoadingSkeletonActivity<VDB, VM> {

    // ==========
    // = 构造函数 =
    // ==========

    constructor(
        bindLayoutId: Int,
        bindViewModelId: Int,
        vmType: ActivityVMType = ActivityVMType.ACTIVITY,
        simple_Init: ((BaseLoadingActivity<VDB, VM>) -> Unit)? = null,
        simple_Start: ((BaseLoadingActivity<VDB, VM>) -> Unit)? = null,
        simple_PreLoad: ((BaseLoadingActivity<VDB, VM>) -> Unit)? = null,
        simple_Agile: ((BaseLoadingActivity<VDB, VM>) -> Unit)? = null,
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
        simple_Init: ((BaseLoadingActivity<VDB, VM>) -> Unit)? = null,
        simple_Start: ((BaseLoadingActivity<VDB, VM>) -> Unit)? = null,
        simple_PreLoad: ((BaseLoadingActivity<VDB, VM>) -> Unit)? = null,
        simple_Agile: ((BaseLoadingActivity<VDB, VM>) -> Unit)? = null,
        simple_UITheme: ((ActivityUITheme) -> ActivityUITheme)? = null
    ) : super(bindLayoutView, bindViewModelId, vmType) {
        simpleFactory = SimpleActivityIMPL.of(
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

    private val simpleFactory: SimpleActivityIMPL<BaseLoadingActivity<VDB, VM>>

    override fun simpleInit() {
        super.simpleInit()
        simpleFactory.simpleInit(this)
    }

    override fun simpleStart() {
        super.simpleStart()
        simpleFactory.simpleStart(this)
    }

    override fun simpleAgile() {
        super.simpleAgile()
        simpleFactory.simpleAgile(this)
    }

    override fun simplePreLoad() {
        super.simplePreLoad()
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