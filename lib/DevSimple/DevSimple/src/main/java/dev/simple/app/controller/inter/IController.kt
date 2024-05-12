package dev.simple.app.controller.inter

import dev.simple.app.controller.ui.IUIController

/**
 * detail: Base 汇总控制器接口
 * @author Ttt
 */
interface IController : IUIController {

    // =======================
    // = ControllerViewModel =
    // =======================

    /**
     * 是否初始化 ControllerViewModel 相关参数
     * @return `true` yes, `false` no
     */
    fun isControllerViewModelInit(): Boolean = true
}