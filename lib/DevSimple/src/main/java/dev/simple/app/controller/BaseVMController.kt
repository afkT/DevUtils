package dev.simple.app.controller

import androidx.databinding.ViewDataBinding
import dev.simple.app.BaseAppViewModel
import dev.simple.app.controller.inter.IController

/**
 * detail: 基础 ViewModel 控制封装
 * @author Ttt
 */
class BaseVMController<VDB : ViewDataBinding, VM : BaseAppViewModel>(
    // Base 汇总控制器接口
    private val controller: IController
) {

    /**
     * 初始化 ViewModel 属性值
     * @param binding VDB
     * @param viewModel VM
     * @param uiController 基础 UI 控制封装
     */
    internal fun initialize(
        binding: VDB,
        viewModel: VM,
        uiController: BaseUIController,
        keyEventController: BaseKeyEventController?
    ) {

        // ===============
        // = IController =
        // ===============

        // 是否初始化 ControllerViewModel 相关参数
        if (controller.isControllerViewModelInit()) {
            // 基础 UI 控制封装
            viewModel.uiController.value = uiController
            // 基础 KeyEvent 物理按键 控制封装
            viewModel.keyEventController.value = keyEventController
        }
    }
}