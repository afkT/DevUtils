package afkt.httpcapture.use.base

import afkt.httpcapture.use.app.autoResources
import afkt.httpcapture.use.app.commonEnableEdgeToEdge
import android.content.res.Resources
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import dev.base.simple.ActivityVMType
import dev.base.simple.DevSimpleActivity
import dev.base.simple.contracts.binding.BindingActivityView

/**
 * detail: Activity MVVM 基类
 * @author Ttt
 */
open class BaseActivity<VDB : ViewDataBinding, VM : BaseViewModel> :
    DevSimpleActivity<VDB, VM> {

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
        simple_Agile: ((Any) -> Unit)? = null
    ) : super(
        bindLayoutId, bindViewModelId, vmType,
        simple_Init, simple_Start, simple_PreLoad, simple_Agile
    )

    constructor(
        bindLayoutView: BindingActivityView?,
        bindViewModelId: Int = -1,
        vmType: ActivityVMType = ActivityVMType.ACTIVITY,
        simple_Init: ((Any) -> Unit)? = null,
        simple_Start: ((Any) -> Unit)? = null,
        simple_PreLoad: ((Any) -> Unit)? = null,
        simple_Agile: ((Any) -> Unit)? = null
    ) : super(
        bindLayoutView, bindViewModelId, vmType,
        simple_Init, simple_Start, simple_PreLoad, simple_Agile
    )

    // ============
    // = override =
    // ============

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 通用 Enable edge to edge【适配 API 35+】
        commonEnableEdgeToEdge()
    }

    // ============
    // = AutoSize =
    // ============

    override fun getResources(): Resources? {
        return autoResources(super.getResources())
    }
}