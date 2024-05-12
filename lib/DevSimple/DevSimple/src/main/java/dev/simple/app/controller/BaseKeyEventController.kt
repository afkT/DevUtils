package dev.simple.app.controller

import androidx.activity.OnBackPressedDispatcher
import dev.utils.common.able.Interceptable

/**
 * detail: 基础 KeyEvent 物理按键 控制封装
 * @author Ttt
 */
class BaseKeyEventController {

    // =========
    // = 返回键 =
    // =========

    /**
     * 返回键可用 [OnBackPressedDispatcher] 实现监听拦截
     */

    // 返回键退出 App 拦截监听
    var exitBackIntercept: Interceptable.Intercept<Boolean>? = null

    // 返回键拦截监听
    var backIntercept: Interceptable.Intercept<Boolean>? = null
}