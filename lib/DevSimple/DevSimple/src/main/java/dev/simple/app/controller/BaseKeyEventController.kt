package dev.simple.app.controller

import androidx.activity.OnBackPressedDispatcher
import dev.utils.app.ClickUtils
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

// =================================
// = BaseKeyEventController 扩展函数 =
// =================================

// 双击返回键退出 APP 间隔时间
const val BACK_EXIT_INTERVAL_TIME = 1500L

/**
 * 设置返回键退出 App 拦截监听
 * @param tag 双击校验 Key
 * @param intervalTime 双击时间间隔
 * @param callToast 返回键 Toast 提示
 * @return [Interceptable.Intercept]
 */
fun BaseKeyEventController.setExitBackIntercept(
    tag: String,
    intervalTime: Long = BACK_EXIT_INTERVAL_TIME,
    callToast: (() -> Unit)? = null
): BaseKeyEventController {
    exitBackIntercept = createExitBackIntercept(tag, intervalTime, callToast)
    return this
}

/**
 * 创建返回键退出 App 拦截监听
 * @param tag 双击校验 Key
 * @param intervalTime 双击时间间隔
 * @param callToast 返回键 Toast 提示
 * @return [Interceptable.Intercept]
 */
fun createExitBackIntercept(
    tag: String,
    intervalTime: Long = BACK_EXIT_INTERVAL_TIME,
    callToast: (() -> Unit)? = null
): Interceptable.Intercept<Boolean> {
    return object : Interceptable.Intercept<Boolean> {
        override fun intercept(): Boolean {
            if (!ClickUtils.isFastDoubleClick(tag, intervalTime)) {
                callToast?.invoke()
                return true
            }
            return false
        }
    }
}