package dev.simple.app.controller.ui.ext

import dev.simple.app.controller.BaseKeyEventController
import dev.utils.app.ClickUtils
import dev.utils.common.able.Interceptable

// 双击返回键退出 APP 间隔时间
const val BACK_EXIT_INTERVAL_TIME = 1500L

// =================================
// = BaseKeyEventController 扩展函数 =
// =================================

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