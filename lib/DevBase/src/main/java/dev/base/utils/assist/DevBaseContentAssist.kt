package dev.base.utils.assist

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import dev.base.R
import dev.utils.app.ViewUtils

/**
 * detail: DevBase ContentView 填充辅助类
 * @author Ttt
 * 主要用于整个应用使用相同结构 Layout 作为布局
 * 并对各个 Activity Layout 动态添加到 contentLinear
 * 其他 Linear、Frame 作用相同, 也可以根据不同页面进行特殊添加
 */
class DevBaseContentAssist {

    // 日志 TAG
    private val TAG: String = DevBaseContentAssist::class.java.simpleName

    // 是否安全处理 ( 建议跟随 BuildConfig.DEBUG 取反处理, 开发阶段抛出异常 )
    private var isSafe: Boolean = false

    // 最外层 Layout
    @JvmField
    open var rootLinear: LinearLayout? = null

    // Title Layout
    @JvmField
    open var titleLinear: LinearLayout? = null

    // Body Layout
    @JvmField
    open var bodyFrame: FrameLayout? = null

    // 填充容器
    @JvmField
    open var contentLinear: LinearLayout? = null

    // 状态布局容器
    @JvmField
    open var stateLinear: LinearLayout? = null

    constructor(activity: Activity) {
        // R.layout.base_activity
        this.rootLinear = activity.findViewById(R.id.vid_ba_root_linear)
        this.titleLinear = activity.findViewById(R.id.vid_ba_title_linear)
        this.bodyFrame = activity.findViewById(R.id.vid_ba_body_frame)
        this.contentLinear = activity.findViewById(R.id.vid_ba_content_linear)
        this.stateLinear = activity.findViewById(R.id.vid_ba_state_linear)
    }

    constructor(
        rootLinear: LinearLayout?,
        titleLinear: LinearLayout?,
        bodyFrame: FrameLayout?,
        contentLinear: LinearLayout?,
        stateLinear: LinearLayout?
    ) {
        this.rootLinear = rootLinear
        this.titleLinear = titleLinear
        this.bodyFrame = bodyFrame
        this.contentLinear = contentLinear
        this.stateLinear = stateLinear
    }

    // ================
    // = 对外公开方法 =
    // ================

    /**
     * 是否安全处理
     * @return [DevBaseContentAssist]
     */
    fun isSafe(): Boolean {
        return isSafe
    }

    /**
     * 设置是否安全处理
     * @return [DevBaseContentAssist]
     */
    fun setSafe(safe: Boolean): DevBaseContentAssist {
        isSafe = safe
        return this
    }

    /**
     * 显示 contentLinear
     * @return [DevBaseContentAssist]
     */
    fun visibleContentLinear(): DevBaseContentAssist {
        ViewUtils.setVisibility(true, contentLinear)
        return this
    }

    /**
     * 隐藏 contentLinear
     * @return [DevBaseContentAssist]
     */
    fun goneContentLinear(): DevBaseContentAssist {
        ViewUtils.setVisibility(false, contentLinear)
        return this
    }

    /**
     * 显示 stateLinear
     * @return [DevBaseContentAssist]
     */
    fun visibleStateLinear(): DevBaseContentAssist {
        ViewUtils.setVisibility(true, stateLinear)
        return this
    }

    /**
     * 隐藏 stateLinear
     * @return [DevBaseContentAssist]
     */
    fun goneStateLinear(): DevBaseContentAssist {
        ViewUtils.setVisibility(false, stateLinear)
        return this
    }

    // =

    /**
     * 最外层 Layout 添加 View - rootLinear
     * @return [DevBaseContentAssist]
     */
    fun addRootView(view: View?): DevBaseContentAssist {
        return addView(rootLinear, view, -1)
    }

    /**
     * 最外层 Layout 添加 View - rootLinear
     * @return [DevBaseContentAssist]
     */
    fun addRootView(view: View?, index: Int): DevBaseContentAssist {
        return addView(rootLinear, view, index)
    }

    // ================
    // = 内部处理方法 =
    // ================

    /**
     * 添加 View
     * @param viewGroup 容器 Layout
     * @param view      待添加 View
     * @param index     添加索引
     * @return [DevBaseContentAssist]
     */
    private fun addView(viewGroup: ViewGroup?, view: View?, index: Int): DevBaseContentAssist {
        if (isSafe) {
            view?.let { viewGroup?.addView(it, index) }
        } else {
            viewGroup!!.addView(view, index)
        }
        return this
    }
}