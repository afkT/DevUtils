package afkt.project.util

import afkt.project.R
import afkt.project.databinding.IncludeBottomShopCartFloatingBinding
import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dev.assist.NumberControlAssist
import dev.utils.app.ResourceUtils
import dev.utils.app.assist.floating.FloatingWindowManagerAssist2
import dev.utils.app.assist.floating.IFloatingActivity
import dev.utils.app.assist.floating.IFloatingOperate

/**
 * detail: 购物车悬浮对外公开类
 * @author Ttt
 * 搭配 [IFloatingActivity]、[FloatingWindowManagerAssist2] 使用, 简化代码以及使用流程
 * 而不是在每个布局内最外层加 FrameLayout 使用 include 插入布局
 */
class ShopCartFloating(private val activity: AppCompatActivity) {

    // 数量控制辅助类
    private val numberControl = NumberControlAssist(0, Int.MAX_VALUE)

    // 购物车悬浮 View
    private val binding: IncludeBottomShopCartFloatingBinding by lazy {
        IncludeBottomShopCartFloatingBinding.inflate(
            activity.layoutInflater,
            null, false
        ).apply {
            numberControl.currentNumber = 0
            // 设置购买数量
            vidCartNumberTv.text = numberControl.currentNumber.toString()
        }
    }

    // 悬浮窗生命周期处理类
    private val floatingLifecycle: FloatingLifecycle = FloatingLifecycle(activity, binding)

    /**
     * 执行添加购物车动画
     * @param view View
     */
    fun executeAnim(view: View) {
        // 设置购买数量
        numberControl.addNumber()
        binding.vidCartNumberTv.text = numberControl.currentNumber.toString()
    }
}

// ==================================================
// = IFloatingActivity、FloatingWindowManagerAssist2 =
// ==================================================

/**
 * detail: 悬浮窗生命周期处理类
 * @author Ttt
 */
class FloatingLifecycle(
    private val activity: AppCompatActivity,
    private val binding: IncludeBottomShopCartFloatingBinding
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
        return InnerUtils.instance.createFloatingView(binding.root)
    }

    override fun getMapFloatingViewLayoutParams(): ViewGroup.LayoutParams {
        return InnerUtils.instance.createLayoutParams(this)
    }

    // ============================
    // = DefaultLifecycleObserver =
    // ============================

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        // 添加悬浮窗 View
        InnerUtils.instance.addFloatingView(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        // 移除悬浮窗 View
        InnerUtils.instance.removeFloatingView(this)
    }
}

/**
 * detail: 悬浮窗工具类
 * @author Ttt
 */
internal class InnerUtils private constructor() : IFloatingOperate {

    companion object {

        val instance: InnerUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            InnerUtils()
        }
    }

    // 悬浮窗管理辅助类
    private val mAssist = object : FloatingWindowManagerAssist2() {
        override fun updateViewLayout(
            floatingActivity: IFloatingActivity,
            view: View
        ) {
            instance.updateViewLayout(floatingActivity, view)
        }
    }

    /**
     * 创建悬浮 View
     * @param view 悬浮窗 View
     * @return FloatingView
     */
    fun createFloatingView(view: View): View {
        return view
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
            // 靠左下
            gravity = Gravity.START or Gravity.BOTTOM
            // 设置边距
            setMargins(
                0, 0, 0,
                ResourceUtils.getDimensionInt(R.dimen.un_dp_70)
            )
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
    }

    override fun isNeedsAdd(): Boolean {
        return mAssist.isNeedsAdd
    }

    override fun setNeedsAdd(needsAdd: Boolean) {
        mAssist.isNeedsAdd = needsAdd
    }
}