package dev.simple.bindingadapters.attribute

/**
 * 合并宽高与 LayoutParams 为 null 时是否新建，用于 DataBinding 单属性绑定。
 * <pre>
 *     与 `binding_view_width_height_w` / `binding_view_width_height_h` / `binding_view_width_height_null_new_lp`
 *     三属性等价，可减少绑定次数；对应 [dev.utils.app.ViewUtils.setWidthHeight]。
 * </pre>
 */
data class WidthHeightDims(
    val widthPx: Int,
    val heightPx: Int,
    val nullNewLp: Boolean = true
)