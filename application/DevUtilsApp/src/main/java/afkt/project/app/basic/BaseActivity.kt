package afkt.project.app.basic

import afkt.project.app.autoResources
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.databinding.ViewDataBinding
import dev.simple.app.BaseAppActivity
import dev.simple.app.base.ActivityVMType
import dev.simple.app.base.interfaces.BindingActivityView
import dev.simple.app.controller.ui.theme.ActivityUITheme

/**
 * detail: Activity MVVM 基类
 * @author Ttt
 */
open class BaseActivity<VDB : ViewDataBinding, VM : BaseViewModel> :
    BaseAppActivity<VDB, VM> {

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

    // ============
    // = override =
    // ============

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }

    // ============
    // = AutoSize =
    // ============

    override fun getResources(): Resources? {
        return autoResources(super.getResources())
    }
}