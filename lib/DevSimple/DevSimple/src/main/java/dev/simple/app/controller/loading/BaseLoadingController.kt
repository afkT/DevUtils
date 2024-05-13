package dev.simple.app.controller.loading

import dev.base.utils.assist.DevBaseContentAssist
import dev.simple.app.BaseViewModel
import dev.widget.assist.ViewAssist

/**
 * detail: 基础 Loading 控制封装
 * @author Ttt
 * 区别于 Loading Skeleton 只用在首次进入管理控制
 * 该 Loading 是重复性使用在 Content Layout 上层显示
 */
class BaseLoadingController<VM : BaseViewModel>(
    contentAssist: DevBaseContentAssist,
    FORCED_SHOW: Boolean = true
) : BaseViewAssistController<VM>(contentAssist, FORCED_SHOW) {

    // Loading 填充辅助类
    private val viewAssist: ViewAssist

    init {
        // 如果 stateLinear 已经有其他用途则可以在 bodyFrame 后续添加一个新的布局
        viewAssist = ViewAssist.wrap(contentAssist.stateLinear)
    }

    /**
     * 初始化 ViewModel 属性值
     * @param viewModel VM
     */
    internal fun initialize(viewModel: VM) {
        viewModel.loadingController.value = this
        // 默认隐藏 Loading View
        viewAssist.goneWrapper()
    }

    // ============
    // = abstract =
    // ============

    override fun viewAssist(): ViewAssist {
        return viewAssist
    }
}