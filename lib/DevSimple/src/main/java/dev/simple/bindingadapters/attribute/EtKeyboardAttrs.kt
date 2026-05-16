package dev.simple.bindingadapters.attribute

/**
 * 延时打开软键盘命令：时间戳 + 延迟毫秒。
 * <pre>
 *     与布局属性 `binding_et_open_keyboard_delay` 搭配；
 *     时间戳判定与 `qualifiesBindingAction` 一致；委托 [dev.utils.app.KeyBoardUtils.openKeyboardDelay]。
 * </pre>
 *
 * @property timestamp 触发用时间戳，须大于 0 才会执行
 * @property delayMillis 延迟时间（毫秒），默认 300，与 [dev.utils.app.KeyBoardUtils] 内部默认一致
 */
data class EtKeyboardOpenDelayAt(
    val timestamp: Long,
    val delayMillis: Long = 300L,
)