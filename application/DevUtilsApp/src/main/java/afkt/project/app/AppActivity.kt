package afkt.project.app

import afkt.project.app.basic.BaseActivity
import afkt.project.features.other_function.floating.FloatingLifecycle
import androidx.databinding.ViewDataBinding
import dev.simple.app.base.ActivityVMType
import dev.simple.app.base.interfaces.BindingActivityView
import dev.simple.app.controller.ui.theme.ActivityUITheme

/**
 * detail: Base App Activity
 * @author Ttt
 */
open class AppActivity<VDB : ViewDataBinding, VM : AppViewModel> :
    BaseActivity<VDB, VM> {

    // ==========
    // = 构造函数 =
    // ==========

    constructor(
        bindLayoutId: Int,
        bindViewModelId: Int = -1,
        vmType: ActivityVMType = ActivityVMType.ACTIVITY,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null,
        simple_UITheme: ((ActivityUITheme) -> ActivityUITheme)? = null
    ) : super(
        bindLayoutId, bindViewModelId, vmType,
        simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
    )

    constructor(
        bindLayoutView: BindingActivityView?,
        bindViewModelId: Int = -1,
        vmType: ActivityVMType = ActivityVMType.ACTIVITY,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null,
        simple_UITheme: ((ActivityUITheme) -> ActivityUITheme)? = null
    ) : super(
        bindLayoutView, bindViewModelId, vmType,
        simple_Init, simple_Start, simple_PreLoad, simple_Agile, simple_UITheme
    )

    // ===============
    // = 悬浮窗实现方式 =
    // ===============

    val floatingLifecycle: FloatingLifecycle by lazy {
        FloatingLifecycle(this)
    }
}