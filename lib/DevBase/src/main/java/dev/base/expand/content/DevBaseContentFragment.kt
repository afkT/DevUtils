package dev.base.expand.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import dev.base.R
import dev.base.able.IDevBaseLayout
import dev.base.fragment.DevBaseFragment
import dev.base.utils.assist.DevBaseContentAssist

/**
 * detail: Content Fragment 基类
 * @author Ttt
 */
abstract class DevBaseContentFragment : DevBaseFragment(),
    IDevBaseLayout {

    @JvmField // Layout View
    protected var layoutView: View? = null

    @JvmField // DevBase ContentView 填充辅助类
    protected var contentAssist = DevBaseContentAssist()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // 绑定 ContentView 填充辅助类
        contentAssist.bind(mContentView)
        // Layout View 初始化处理
        layoutInit(layoutInflater, null)
        // 添加到 contentLinear
        if (isLayoutMatchParent()) {
            contentAssist.addContentView(
                layoutView,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            )
        } else {
            contentAssist.addContentView(layoutView)
        }
        return mContentView
    }

    // ===================
    // = IDevBaseContent =
    // ===================

    final override fun baseContentId(): Int {
        return R.layout.base_content_view
    }

    final override fun baseContentView(): View? {
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
    private fun layoutInit(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        if (layoutView != null) return
        // 使用 baseLayoutId()
        if (baseLayoutId() != 0) {
            try {
                layoutView = inflater.inflate(baseLayoutId(), container, false)
            } catch (e: Exception) {
                assist.printLog(e, "layoutInit - baseLayoutId")
            }
        }
        // 如果 View 等于 null, 则使用 baseLayoutView()
        if (layoutView == null) layoutView = baseLayoutView()
    }
}