package dev.simple.bindingadapters.attribute

/**
 * 合并 ProgressBar 当前进度与最大值，用于 DataBinding 单属性绑定。
 * <pre>
 *     与 `binding_view_bar_progress_value` 与 `binding_view_bar_max_value` 双属性等价；非 ProgressBar 时由
 *     [dev.utils.app.ViewUtils.setBarValue] 内部忽略。
 * </pre>
 */
data class BarProgressState(
    val progress: Int,
    val max: Int
)