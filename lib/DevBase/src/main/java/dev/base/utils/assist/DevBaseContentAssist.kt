package dev.base.utils.assist

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import dev.base.databinding.BaseContentViewBinding
import dev.utils.app.ViewUtils

/**
 * detail: DevBase ContentView 填充辅助类
 * @author Ttt
 * 主要用于整个应用使用相同结构 Layout 作为布局
 * 并对各个 Activity Layout 动态添加到 contentLinear
 * 其他 Linear、Frame 作用相同, 也可以根据不同页面进行特殊添加
 */
class DevBaseContentAssist {

    // Content View Binding
    private lateinit var binding: BaseContentViewBinding

    // 是否安全处理 ( 建议跟随 BuildConfig.DEBUG 取反处理, 开发阶段抛出异常 )
    private var mSafe = false

    fun bind(newBinding: BaseContentViewBinding) {
        try {
            // 如果已经初始化了则先移除
            if (::binding.isInitialized) {
                ViewUtils.removeSelfFromParent(binding.root)
            }
        } catch (_: Exception) {
        }
        this.binding = newBinding
    }

    fun bindingRoot(): View {
        return binding.root
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 是否安全处理
     */
    fun isSafe(): Boolean {
        return mSafe
    }

    /**
     * 设置是否安全处理
     * @return [DevBaseContentAssist]
     */
    fun setSafe(safe: Boolean): DevBaseContentAssist {
        mSafe = safe
        return this
    }

    // ==========
    // = 获取操作 =
    // ==========

    /**
     * 最外层 Layout
     * @return [LinearLayout]
     */
    fun rootLinear(): LinearLayout {
        return binding.rootLinear
    }

    /**
     * StatusBar Layout
     * @return [LinearLayout]
     */
    fun statusBarLinear(): LinearLayout {
        return binding.statusBarLinear
    }

    /**
     * Title Layout
     * @return [LinearLayout]
     */
    fun titleLinear(): LinearLayout {
        return binding.titleLinear
    }

    /**
     * Body Layout
     * @return [FrameLayout]
     */
    fun bodyFrame(): FrameLayout {
        return binding.bodyFrame
    }

    /**
     * 填充容器
     * @return [LinearLayout]
     */
    fun contentLinear(): LinearLayout {
        return binding.contentLinear
    }

    /**
     * 状态布局容器
     * @return [LinearLayout]
     */
    fun stateLinear(): LinearLayout {
        return binding.stateLinear
    }

    /**
     * 悬浮容器
     * @return [FrameLayout]
     */
    fun floatFrame(): FrameLayout {
        return binding.floatFrame
    }

    // ==========
    // = 显示操作 =
    // ==========

    /**
     * 显示 statusBarLinear
     * @return [DevBaseContentAssist]
     */
    fun visibleStatusBarLinear(): DevBaseContentAssist {
        return setVisibility(true, statusBarLinear())
    }

    /**
     * 显示 titleLinear
     * @return [DevBaseContentAssist]
     */
    fun visibleTitleLinear(): DevBaseContentAssist {
        return setVisibility(true, titleLinear())
    }

    /**
     * 显示 bodyFrame
     * @return [DevBaseContentAssist]
     */
    fun visibleBodyFrame(): DevBaseContentAssist {
        return setVisibility(true, bodyFrame())
    }

    /**
     * 显示 contentLinear
     * @return [DevBaseContentAssist]
     */
    fun visibleContentLinear(): DevBaseContentAssist {
        return setVisibility(true, contentLinear())
    }

    /**
     * 显示 stateLinear
     * @return [DevBaseContentAssist]
     */
    fun visibleStateLinear(): DevBaseContentAssist {
        return setVisibility(true, stateLinear())
    }

    /**
     * 显示 floatFrame
     * @return [DevBaseContentAssist]
     */
    fun visibleFloatFrame(): DevBaseContentAssist {
        return setVisibility(true, floatFrame())
    }

    // ==========
    // = 隐藏操作 =
    // ==========

    /**
     * 隐藏 statusBarLinear
     * @return [DevBaseContentAssist]
     */
    fun goneStatusBarLinear(): DevBaseContentAssist {
        return setVisibility(false, statusBarLinear())
    }

    /**
     * 隐藏 titleLinear
     * @return [DevBaseContentAssist]
     */
    fun goneTitleLinear(): DevBaseContentAssist {
        return setVisibility(false, titleLinear())
    }

    /**
     * 隐藏 bodyFrame
     * @return [DevBaseContentAssist]
     */
    fun goneBodyFrame(): DevBaseContentAssist {
        return setVisibility(false, bodyFrame())
    }

    /**
     * 隐藏 contentLinear
     * @return [DevBaseContentAssist]
     */
    fun goneContentLinear(): DevBaseContentAssist {
        return setVisibility(false, contentLinear())
    }

    /**
     * 隐藏 stateLinear
     * @return [DevBaseContentAssist]
     */
    fun goneStateLinear(): DevBaseContentAssist {
        return setVisibility(false, stateLinear())
    }

    /**
     * 隐藏 floatFrame
     * @return [DevBaseContentAssist]
     */
    fun goneFloatFrame(): DevBaseContentAssist {
        return setVisibility(false, floatFrame())
    }

    // ============
    // = 添加 View =
    // ============

    /**
     * rootLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addRootView(view: View?): DevBaseContentAssist {
        return addView(rootLinear(), view, -1)
    }

    /**
     * rootLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addRootView(
        view: View?,
        index: Int
    ): DevBaseContentAssist {
        return addView(rootLinear(), view, index)
    }

    /**
     * rootLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addRootView(
        view: View?,
        params: ViewGroup.LayoutParams?
    ): DevBaseContentAssist {
        return addView(rootLinear(), view, -1, params)
    }

    /**
     * rootLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addRootView(
        view: View?,
        index: Int,
        params: ViewGroup.LayoutParams?
    ): DevBaseContentAssist {
        return addView(rootLinear(), view, index, params)
    }

    /**
     * statusBarLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addStatusBarView(view: View?): DevBaseContentAssist {
        return addView(statusBarLinear(), view, -1)
    }

    /**
     * statusBarLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addStatusBarView(
        view: View?,
        index: Int
    ): DevBaseContentAssist {
        return addView(statusBarLinear(), view, index)
    }

    /**
     * statusBarLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addStatusBarView(
        view: View?,
        params: ViewGroup.LayoutParams?
    ): DevBaseContentAssist {
        return addView(statusBarLinear(), view, -1, params)
    }

    /**
     * statusBarLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addStatusBarView(
        view: View?,
        index: Int,
        params: ViewGroup.LayoutParams?
    ): DevBaseContentAssist {
        return addView(statusBarLinear(), view, index, params)
    }

    /**
     * titleLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addTitleView(view: View?): DevBaseContentAssist {
        return addView(titleLinear(), view, -1)
    }

    /**
     * titleLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addTitleView(
        view: View?,
        index: Int
    ): DevBaseContentAssist {
        return addView(titleLinear(), view, index)
    }

    /**
     * titleLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addTitleView(
        view: View?,
        params: ViewGroup.LayoutParams?
    ): DevBaseContentAssist {
        return addView(titleLinear(), view, -1, params)
    }

    /**
     * titleLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addTitleView(
        view: View?,
        index: Int,
        params: ViewGroup.LayoutParams?
    ): DevBaseContentAssist {
        return addView(titleLinear(), view, index, params)
    }

    /**
     * bodyFrame 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addBodyView(view: View?): DevBaseContentAssist {
        return addView(bodyFrame(), view, -1)
    }

    /**
     * bodyFrame 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addBodyView(
        view: View?,
        index: Int
    ): DevBaseContentAssist {
        return addView(bodyFrame(), view, index)
    }

    /**
     * bodyFrame 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addBodyView(
        view: View?,
        params: ViewGroup.LayoutParams?
    ): DevBaseContentAssist {
        return addView(bodyFrame(), view, -1, params)
    }

    /**
     * bodyFrame 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addBodyView(
        view: View?,
        index: Int,
        params: ViewGroup.LayoutParams?
    ): DevBaseContentAssist {
        return addView(bodyFrame(), view, index, params)
    }

    /**
     * contentLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addContentView(view: View?): DevBaseContentAssist {
        return addView(contentLinear(), view, -1)
    }

    /**
     * contentLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addContentView(
        view: View?,
        index: Int
    ): DevBaseContentAssist {
        return addView(contentLinear(), view, index)
    }

    /**
     * contentLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addContentView(
        view: View?,
        params: ViewGroup.LayoutParams?
    ): DevBaseContentAssist {
        return addView(contentLinear(), view, -1, params)
    }

    /**
     * contentLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addContentView(
        view: View?,
        index: Int,
        params: ViewGroup.LayoutParams?
    ): DevBaseContentAssist {
        return addView(contentLinear(), view, index, params)
    }

    /**
     * stateLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addStateView(view: View?): DevBaseContentAssist {
        return addView(stateLinear(), view, -1)
    }

    /**
     * stateLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addStateView(
        view: View?,
        index: Int
    ): DevBaseContentAssist {
        return addView(stateLinear(), view, index)
    }

    /**
     * stateLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addStateView(
        view: View?,
        params: ViewGroup.LayoutParams?
    ): DevBaseContentAssist {
        return addView(stateLinear(), view, -1, params)
    }

    /**
     * stateLinear 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addStateView(
        view: View?,
        index: Int,
        params: ViewGroup.LayoutParams?
    ): DevBaseContentAssist {
        return addView(stateLinear(), view, index, params)
    }

    /**
     * floatFrame 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addFloatView(view: View?): DevBaseContentAssist {
        return addView(floatFrame(), view, -1)
    }

    /**
     * floatFrame 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addFloatView(
        view: View?,
        index: Int
    ): DevBaseContentAssist {
        return addView(floatFrame(), view, index)
    }

    /**
     * floatFrame 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addFloatView(
        view: View?,
        params: ViewGroup.LayoutParams?
    ): DevBaseContentAssist {
        return addView(floatFrame(), view, -1, params)
    }

    /**
     * floatFrame 添加 View
     * @return [DevBaseContentAssist]
     */
    fun addFloatView(
        view: View?,
        index: Int,
        params: ViewGroup.LayoutParams?
    ): DevBaseContentAssist {
        return addView(floatFrame(), view, index, params)
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 添加 View
     * @param viewGroup 容器 Layout
     * @param view      待添加 View
     * @param index     添加索引
     * @return [DevBaseContentAssist]
     */
    private fun addView(
        viewGroup: ViewGroup?,
        view: View?,
        index: Int
    ): DevBaseContentAssist {
        // ViewUtils.removeSelfFromParent(view)
        val parent = view?.parent as? ViewGroup
        // 防止重复添加, 当 parent 不为 null 则表示已添加, 则进行移除
        parent?.removeView(view)

        if (mSafe) {
            view?.let { viewGroup?.addView(it, index) }
        } else {
            viewGroup!!.addView(view, index)
        }
        return this
    }

    /**
     * 添加 View
     * @param viewGroup 容器 Layout
     * @param view      待添加 View
     * @param index     添加索引
     * @param params    LayoutParams
     * @return [DevBaseContentAssist]
     */
    private fun addView(
        viewGroup: ViewGroup?,
        view: View?,
        index: Int,
        params: ViewGroup.LayoutParams?
    ): DevBaseContentAssist {
        // ViewUtils.removeSelfFromParent(view)
        val parent = view?.parent as? ViewGroup
        // 防止重复添加, 当 parent 不为 null 则表示已添加, 则进行移除
        parent?.removeView(view)

        if (mSafe) {
            view?.let { viewGroup?.addView(it, index, params) }
        } else {
            viewGroup!!.addView(view, index, params)
        }
        return this
    }

    /**
     * 设置 View 显示的状态
     * @param isVisibility `true` View.VISIBLE, `false` View.GONE
     * @param view         [View]
     * @return [DevBaseContentAssist]
     */
    private fun setVisibility(
        isVisibility: Boolean,
        view: View?
    ): DevBaseContentAssist {
        view?.visibility = if (isVisibility) View.VISIBLE else View.GONE
        return this
    }
}