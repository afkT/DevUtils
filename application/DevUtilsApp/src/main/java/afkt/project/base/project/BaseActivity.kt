package afkt.project.base.project

import afkt.project.base.app.AppViewModel
import afkt.project.feature.other_function.floating.Utils2
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dev.simple.app.BaseAppActivity
import dev.simple.app.base.ActivityVMType
import dev.simple.app.base.interfaces.BindingActivityView
import dev.simple.app.controller.ui.theme.ActivityUITheme
import dev.utils.app.assist.floating.IFloatingActivity

/**
 * detail: BaseActivity
 * @author Ttt
 */
open class BaseActivity<VDB : ViewDataBinding, VM : AppViewModel> :
    BaseAppActivity<VDB, VM>,
    IFloatingActivity {

    // ==========
    // = 构造函数 =
    // ==========

    constructor(
        bindLayoutId: Int,
        bindViewModelId: Int,
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
        bindViewModelId: Int,
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

    // ========================
    // = 悬浮窗实现方式 ( 两种 ) =
    // ========================

    // =====================
    // = IFloatingActivity =
    // =====================

    // ========
    // = 方式一 =
    // ========

    val floatingLifecycle: FloatingLifecycle by lazy {
        FloatingLifecycle(this)
    }

    // ========
    // = 方式二 =
    // ========

    override fun getAttachActivity(): Activity {
        return this
    }

    override fun getMapFloatingKey(): String {
        return this.toString()
    }

    override fun getMapFloatingView(): View {
        return Utils2.instance.createFloatingView(this)
    }

    override fun getMapFloatingViewLayoutParams(): ViewGroup.LayoutParams {
        return Utils2.instance.createLayoutParams(this)
    }

    // =

    override fun onResume() {
        super.onResume()
        // 添加悬浮窗 View
        Utils2.instance.addFloatingView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 移除悬浮窗 View
        Utils2.instance.removeFloatingView(this)
    }
}

// ========
// = 方式一 =
// ========

class FloatingLifecycle(val activity: AppCompatActivity) : DefaultLifecycleObserver,
    IFloatingActivity {

    init {
        activity.lifecycle.addObserver(this)
    }

    // =====================
    // = IFloatingActivity =
    // =====================

    override fun getAttachActivity(): Activity {
        return activity
    }

    override fun getMapFloatingKey(): String {
        return this.toString()
    }

    override fun getMapFloatingView(): View {
        return Utils2.instance.createFloatingView(this)
    }

    override fun getMapFloatingViewLayoutParams(): ViewGroup.LayoutParams {
        return Utils2.instance.createLayoutParams(this)
    }

    // ============================
    // = DefaultLifecycleObserver =
    // ============================

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        // 添加悬浮窗 View
        Utils2.instance.addFloatingView(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        // 移除悬浮窗 View
        Utils2.instance.removeFloatingView(this)
    }
}