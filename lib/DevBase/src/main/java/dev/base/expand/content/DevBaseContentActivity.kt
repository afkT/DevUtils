package dev.base.expand.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import dev.base.R
import dev.base.able.IDevBaseLayout
import dev.base.activity.DevBaseActivity
import dev.base.utils.assist.DevBaseContentAssist

/**
 * detail: Content Activity 基类
 * @author Ttt
 * 内置 R.layout.base_activity 作为 contentView 并对所需 View 进行 Add
 * 设计思路:
 * 全局统一使用 R.layout.base_activity 作为根布局进行显示
 * 并且进行动态添加 title、body 等布局 View
 * 能够对全局进行增删 View 控制处理, 以及后期全局需求配置
 */
abstract class DevBaseContentActivity : DevBaseActivity(), IDevBaseLayout {

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
//        mContentAssist.addContentView(mLayoutView)
        mContentAssist.addContentView(
            mLayoutView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )
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

    // ==================
    // = IDevBaseLayout =
    // ==================

    /**
     * Layout View 初始化处理
     * @param inflater  [LayoutInflater]
     * @param container [ViewGroup]
     */
    private fun layoutInit(inflater: LayoutInflater, container: ViewGroup?) {
        if (mLayoutView != null) return
        // 使用 baseLayoutId()
        if (baseLayoutId() != 0) {
            try {
                mLayoutView = inflater.inflate(baseLayoutId(), container, false)
            } catch (e: Exception) {
                mAssist.printLog(e, "layoutInit - baseLayoutId")
            }
        }
        // 如果 View 等于 null, 则使用 baseLayoutView()
        if (mLayoutView == null) mLayoutView = baseLayoutView()
    }
}