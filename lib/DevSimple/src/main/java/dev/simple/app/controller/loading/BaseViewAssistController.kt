package dev.simple.app.controller.loading

import dev.base.utils.assist.DevBaseContentAssist
import dev.simple.app.BaseAppViewModel
import dev.utils.DevFinal
import dev.widget.assist.ViewAssist

/**
 * detail: 基础 ViewAssist 控制封装
 * @author Ttt
 */
abstract class BaseViewAssistController<VM : BaseAppViewModel>(
    val contentAssist: DevBaseContentAssist,
    val FORCED_SHOW: Boolean = true
) {
    // Loading 填充辅助类
    abstract fun viewAssist(): ViewAssist

    // ==============
    // = 内部封装逻辑 =
    // ==============

    fun isVisibility(): Boolean {
        return viewAssist().isVisibleWrapper
    }

    fun isTypeNone(): Boolean {
        return viewAssist().isTypeView(DevFinal.DEFAULT.ERROR_INT)
    }

    fun isTypeIng(): Boolean {
        return viewAssist().isTypeIng
    }

    fun isTypeFailed(): Boolean {
        return viewAssist().isTypeFailed
    }

    fun isTypeSuccess(): Boolean {
        return viewAssist().isTypeSuccess
    }

    fun isTypeEmptyData(): Boolean {
        return viewAssist().isTypeEmptyData
    }

    // ==========
    // = 操作方法 =
    // ==========

    fun showIng(
        notFoundOP: Boolean = true,
        forcedShow: Boolean = FORCED_SHOW
    ) {
        if (forcedShow) {
            viewAssist().showIng(notFoundOP)
        } else if (isVisibility()) {
            viewAssist().showIng(notFoundOP)
        }
    }

    fun showFailed(
        notFoundOP: Boolean = true,
        forcedShow: Boolean = FORCED_SHOW
    ) {
        if (forcedShow) {
            viewAssist().showFailed(notFoundOP)
        } else if (isVisibility()) {
            viewAssist().showFailed(notFoundOP)
        }
    }

    fun showSuccess(
        notFoundOP: Boolean = true,
        forcedShow: Boolean = FORCED_SHOW
    ) {
        if (forcedShow) {
            viewAssist().showSuccess(notFoundOP)
        } else if (isVisibility()) {
            viewAssist().showSuccess(notFoundOP)
        }
    }

    fun showEmptyData(
        notFoundOP: Boolean = true,
        forcedShow: Boolean = FORCED_SHOW
    ) {
        if (forcedShow) {
            viewAssist().showEmptyData(notFoundOP)
        } else if (isVisibility()) {
            viewAssist().showEmptyData(notFoundOP)
        }
    }

    fun showType(
        type: Int,
        notFoundOP: Boolean = true,
        forcedShow: Boolean = FORCED_SHOW
    ) {
        if (forcedShow) {
            viewAssist().showType(type, notFoundOP)
        } else if (isVisibility()) {
            viewAssist().showType(type, notFoundOP)
        }
    }
}