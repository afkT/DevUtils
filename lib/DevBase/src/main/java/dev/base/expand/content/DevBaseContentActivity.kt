package dev.base.expand.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.base.R
import dev.base.activity.DevBaseActivity
import dev.base.utils.assist.DevBaseContentAssist

/**
 * detail: Content Activity 基类
 * @author Ttt
 * 内置 R.layout.base_activity 作为 contentView 并对所需 View 进行 Add
 */
abstract class DevBaseContentActivity : DevBaseActivity() {

    @JvmField // Layout View
    protected var mLayoutView: View? = null

    @JvmField // DevBase ContentView 填充辅助类
    protected var mContentAssist = DevBaseContentAssist()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 绑定 ContentView 填充辅助类
        mContentAssist.bind(this)
        // Layout View 初始化处理
        layoutInit(layoutInflater, null)
        // 添加到 contentLinear
        mContentAssist.addContentView(mLayoutView)
    }

    // ================
    // = Content View =
    // ================

    final override fun contentId(): Int {
        return R.layout.base_activity
    }

    final override fun contentView(): View? {
        return null
    }

    // ============
    // = 二次封装 =
    // ============

    /**
     * 获取 Layout id ( 用于 contentLinear addView )
     * @return Layout Id
     */
    abstract fun layoutId(): Int

    /**
     * 获取 Layout View ( 用于 contentLinear addView )
     * @return Layout View
     */
    abstract fun layoutView(): View?

    /**
     * Layout View 初始化处理
     * @param inflater  [LayoutInflater]
     * @param container [ViewGroup]
     */
    private fun layoutInit(inflater: LayoutInflater, container: ViewGroup?) {
        if (mLayoutView != null) return
        // 使用 layoutId()
        if (layoutId() != 0) {
            try {
                mLayoutView = inflater.inflate(layoutId(), container, false)
            } catch (e: Exception) {
                mAssist.printLog(e, "layoutInit - layoutId")
            }
        }
        // 如果 View 等于 null, 则使用 layoutView()
        if (mLayoutView == null) mLayoutView = layoutView()
    }
}