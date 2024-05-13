package dev.simple.app.controller.loading

import android.widget.FrameLayout
import dev.base.utils.assist.DevBaseContentAssist
import dev.simple.app.BaseViewModel
import dev.widget.assist.ViewAssist

/**
 * detail: 基础 Loading Skeleton 控制封装
 * @author Ttt
 * 首次进入使用 Activity、Fragment Loading 使用
 */
class BaseLoadingSkeletonController<VM : BaseViewModel>(
    contentAssist: DevBaseContentAssist,
    FORCED_SHOW: Boolean = false
) : BaseViewAssistController<VM>(contentAssist, FORCED_SHOW) {

    // Loading Skeleton 填充辅助类
    private val viewAssist: ViewAssist

    init {
        // 自行新增一个 FrameLayout 用于 Loading Skeleton 注入
        val skeleton = FrameLayout(contentAssist.rootLinear!!.context)
        contentAssist.addBodyView(
            skeleton, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )
        viewAssist = ViewAssist.wrap(skeleton)
    }

    /**
     * 初始化 ViewModel 属性值
     * @param viewModel VM
     */
    internal fun initialize(viewModel: VM) {
        viewModel.loadingSkeletonController.value = this
    }

    // ============
    // = abstract =
    // ============

    override fun viewAssist(): ViewAssist {
        return viewAssist
    }
}