package dev.simple.app.controller.loading

import android.widget.FrameLayout
import dev.base.utils.assist.DevBaseContentAssist
import dev.simple.app.BaseViewModel
import dev.utils.DevFinal
import dev.widget.assist.ViewAssist

/**
 * detail: 基础 Loading Skeleton 控制封装
 * @author Ttt
 * 首次进入使用 Activity、Fragment Loading 使用
 */
class BaseLoadingSkeletonController<VM : BaseViewModel>(
    val contentAssist: DevBaseContentAssist,
    val FORCED_SHOW: Boolean = false
) {
    // Loading Skeleton 填充辅助类
    val viewAssist: ViewAssist

    init {
//        // 如果 stateLinear 已经有其他用途则可以在 bodyFrame 后续添加一个新的布局
//        viewAssist = ViewAssist.wrap(contentAssist.stateLinear)
//        contentAssist.addStateView()
//        contentAssist.addBodyView()

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

    // ==============
    // = 内部封装逻辑 =
    // ==============

    fun isVisibility(): Boolean {
        return viewAssist.isVisibleWrapper
    }

    fun isTypeNone(): Boolean {
        return viewAssist.isTypeView(DevFinal.DEFAULT.ERROR_INT)
    }

    fun isTypeIng(): Boolean {
        return viewAssist.isTypeIng
    }

    fun isTypeFailed(): Boolean {
        return viewAssist.isTypeFailed
    }

    fun isTypeSuccess(): Boolean {
        return viewAssist.isTypeSuccess
    }

    fun isTypeEmptyData(): Boolean {
        return viewAssist.isTypeEmptyData
    }

    // =

    fun showIng(
        notFoundOP: Boolean = true,
        forcedShow: Boolean = FORCED_SHOW
    ) {
        if (forcedShow) {
            viewAssist.showIng(notFoundOP)
        } else if (isVisibility()) {
            viewAssist.showIng(notFoundOP)
        }
    }

    fun showFailed(
        notFoundOP: Boolean = true,
        forcedShow: Boolean = FORCED_SHOW
    ) {
        if (forcedShow) {
            viewAssist.showFailed(notFoundOP)
        } else if (isVisibility()) {
            viewAssist.showFailed(notFoundOP)
        }
    }

    fun showSuccess(
        notFoundOP: Boolean = true,
        forcedShow: Boolean = FORCED_SHOW
    ) {
        if (forcedShow) {
            viewAssist.showSuccess(notFoundOP)
        } else if (isVisibility()) {
            viewAssist.showSuccess(notFoundOP)
        }
    }

    fun showEmptyData(
        notFoundOP: Boolean = true,
        forcedShow: Boolean = FORCED_SHOW
    ) {
        if (forcedShow) {
            viewAssist.showEmptyData(notFoundOP)
        } else if (isVisibility()) {
            viewAssist.showEmptyData(notFoundOP)
        }
    }
}