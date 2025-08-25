package afkt.project.features.other_function.floating

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppActivity
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentOtherFunctionFloating2Binding
import android.app.Activity
import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dev.engine.extensions.toast.toast_showShort
import dev.utils.app.ActivityUtils
import dev.utils.app.ViewUtils
import dev.utils.app.assist.floating.*

/**
 * detail: 悬浮窗管理辅助类 ( 无需权限，依赖 Activity )
 * @author Ttt
 * 推荐使用
 * 悬浮窗框架 https://github.com/getActivity/EasyWindow
 * 悬浮窗解决方案 https://github.com/Petterpx/FloatingX
 */
class FloatingWindowManager2Fragment : AppFragment<FragmentOtherFunctionFloating2Binding, FloatingWindowManager2ViewModel>(
    R.layout.fragment_other_function_floating2, BR.viewModel
)

class FloatingWindowManager2ViewModel : AppViewModel() {

    // 打开悬浮窗
    val clickOpen = View.OnClickListener { view ->
        Utils2.instance.apply {
            isNeedsAdd = true

            /**
             * 因为这里是使用单 Activity 多 Fragment 方式，非多 Activity 方式
             * 所以需要获取 BaseActivity 的 floatingLifecycle 进行添加悬浮窗 View
             * 如果当前是 BaseActivity 并且实现了 [IFloatingActivity]
             * 则可直接调用 addFloatingView(IFloatingActivity@this)
             * 或者类似 activity.floatingLifecycle.addFloatingView()
             */
            val activity = ActivityUtils.getActivity(view)
            if (activity is AppActivity<*, *>) {
                // 添加悬浮窗 View
                activity.floatingLifecycle.addFloatingView()
            }
        }
    }

    // 关闭悬浮窗
    val clickClose = View.OnClickListener { view ->
        Utils2.instance.apply {
            isNeedsAdd = false
            // 移除所有悬浮窗 View
            removeAllFloatingView()
        }
    }
}

// ========================
// = 悬浮窗实现方式 ( 两种 ) =
// ========================

// ==============
// = 实现方式【一】=
// ==============

/**
 * detail: 实现方式【一】
 * @author Ttt
 * 该实现方式无需 BaseActivity 实现 [IFloatingActivity]
 * 而是通过 BaseActivity 监听生命周期在 [onResume]、[onDestroy] 中添加和移除悬浮 View
 * 该方式最简单，只需要创建该类即可，并且不影响 Base 代码【自动添加到 R.id.content 中】
 */
class FloatingLifecycle(
    private val activity: AppCompatActivity
) : DefaultLifecycleObserver,
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
        addFloatingView()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        // 移除悬浮窗 View
        removeFloatingView()
    }

    // =

    fun addFloatingView() {
        // 添加悬浮窗 View
        Utils2.instance.addFloatingView(this)
    }

    fun removeFloatingView() {
        // 移除悬浮窗 View
        Utils2.instance.removeFloatingView(this)
    }
}

// ==============
// = 实现方式【二】=
// ==============

/**
 * detail: 实现方式【二】
 * @author Ttt
 * 该实现方式需要 BaseActivity 实现 [IFloatingActivity]
 */
private class BaseActivity : AppCompatActivity(),
    IFloatingActivity {

    // =====================
    // = IFloatingActivity =
    // =====================

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

    // ==========
    // = 生命周期 =
    // ==========

    override fun onResume() {
        super.onResume()
        // 添加悬浮窗 View
        addFloatingView()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 移除悬浮窗 View
        removeFloatingView()
    }

    // =

    fun addFloatingView() {
        // 添加悬浮窗 View
        Utils2.instance.addFloatingView(this)
    }

    fun removeFloatingView() {
        // 移除悬浮窗 View
        Utils2.instance.removeFloatingView(this)
    }
}

// ==========
// = 实现代码 =
// ==========

/**
 * detail: 悬浮窗工具类
 * @author Ttt
 */
internal class Utils2 private constructor() : IFloatingOperate {

    companion object {

        val instance: Utils2 by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Utils2()
        }
    }

    // 悬浮窗管理辅助类 ( 无需权限依赖 Activity )
    private val mAssist = object : FloatingWindowManagerAssist2() {
        override fun updateViewLayout(
            floatingActivity: IFloatingActivity,
            view: View
        ) {
            instance.updateViewLayout(floatingActivity, view)
        }
    }.apply {
        // 默认不添加悬浮处理
        isNeedsAdd = false
    }

    // 悬浮窗触摸辅助类实现
    private val mTouchAssist = DevFloatingTouchImpl2().apply {
        (floatingEdge as? DevFloatingEdgeImpl)?.let { edge ->
            edge.setStatusBarHeightMargin()
            edge.setNavigationBarHeightMargin()
        }
        // 悬浮窗触摸事件接口 ( 如果不需要触发点击、长按则可不设置 )
        floatingListener = object : DevFloatingListener() {
            override fun onClick(
                view: View?,
                event: MotionEvent,
                firstPoint: PointF
            ): Boolean {
                if (DevFloatingCommon.isValidEvent(event, firstPoint)) {
                    toast_showShort(text = "触发点击")
                }
                return true
            }

            override fun onLongClick(
                view: View?,
                event: MotionEvent,
                firstPoint: PointF
            ): Boolean {
                if (DevFloatingCommon.isValidEvent(event, firstPoint)) {
                    toast_showShort(text = "触发长按")
                }
                return true
            }
        }
    }

    /**
     * 创建悬浮 View
     * @param floatingActivity 悬浮窗辅助类接口
     * @return FloatingView
     */
    fun createFloatingView(floatingActivity: IFloatingActivity): FloatingView {
        return FloatingView(floatingActivity.attachActivity, mTouchAssist)
    }

    /**
     * 创建悬浮 View LayoutParams
     * @param floatingActivity 悬浮窗辅助类接口
     * @return ViewGroup.LayoutParams
     */
    fun createLayoutParams(floatingActivity: IFloatingActivity): ViewGroup.LayoutParams {
        return FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(mTouchAssist.x, mTouchAssist.y, 0, 0)
        }
    }

    // ====================
    // = IFloatingOperate =
    // ====================

    override fun removeFloatingView(floatingActivity: IFloatingActivity): Boolean {
        return mAssist.removeFloatingView(floatingActivity)
    }

    override fun addFloatingView(floatingActivity: IFloatingActivity): Boolean {
        return mAssist.addFloatingView(floatingActivity)
    }

    override fun removeAllFloatingView() {
        mAssist.removeAllFloatingView()
    }

    override fun updateViewLayout(
        floatingActivity: IFloatingActivity,
        view: View
    ) {
        ViewUtils.setMargin(
            view, mTouchAssist.x, mTouchAssist.y, 0, 0
        )
    }

    override fun isNeedsAdd(): Boolean {
        return mAssist.isNeedsAdd
    }

    override fun setNeedsAdd(needsAdd: Boolean) {
        mAssist.isNeedsAdd = needsAdd
    }
}