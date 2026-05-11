package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.simple.bindingadapters.view.attribute.Margins
import dev.utils.app.ViewUtils

// ==============================
// = View Margin BindingAdapter =
// ==============================

/**
 * 通过数据绑定按四边距对象设置外边距
 * <pre>
 *     对应布局属性 binding_marginAttr；margins 为空时不修改。
 * </pre>
 *
 * @param margins 左、上、右、下边距封装，可为空
 */
@BindingAdapter("binding_marginAttr")
fun View.bindingMarginAttr(margins: Margins?) {
    margins?.let {
        ViewUtils.setMargin(this, it.left, it.top, it.right, it.bottom)
    }
}

// =

/**
 * 通过数据绑定为四边设置相同外边距
 * <pre>
 *     对应布局属性 binding_margin。
 * </pre>
 *
 * @param margin 统一边距像素值
 */
@BindingAdapter("binding_margin")
fun View.bindingMargin(margin: Int) {
    ViewUtils.setMargin(this, margin)
}

/**
 * 通过数据绑定设置左侧外边距
 * <pre>
 *     对应布局属性 binding_marginLeft、binding_marginReset，requireAll 为 false。
 * </pre>
 *
 * @param margin 左边距像素值
 * @param reset 为 true 时将其余三边 margin 置 0；为 false 时保留其余三边当前值（与 ViewUtils.setMarginLeft 一致）
 */
@BindingAdapter(
    value = ["binding_marginLeft", "binding_marginReset"],
    requireAll = false
)
fun View.bindingMarginLeft(
    margin: Int,
    reset: Boolean?
) {
    ViewUtils.setMarginLeft(this, margin, reset == true)
}

/**
 * 通过数据绑定设置顶部外边距
 * <pre>
 *     对应布局属性 binding_marginTop、binding_marginReset，requireAll 为 false。
 * </pre>
 *
 * @param margin 上边距像素值
 * @param reset 为 true 时将其余三边 margin 置 0；为 false 时保留其余三边当前值
 */
@BindingAdapter(
    value = ["binding_marginTop", "binding_marginReset"],
    requireAll = false
)
fun View.bindingMarginTop(
    margin: Int,
    reset: Boolean?
) {
    ViewUtils.setMarginTop(this, margin, reset == true)
}

/**
 * 通过数据绑定设置右侧外边距
 * <pre>
 *     对应布局属性 binding_marginRight、binding_marginReset，requireAll 为 false。
 * </pre>
 *
 * @param margin 右边距像素值
 * @param reset 为 true 时将其余三边 margin 置 0；为 false 时保留其余三边当前值
 */
@BindingAdapter(
    value = ["binding_marginRight", "binding_marginReset"],
    requireAll = false
)
fun View.bindingMarginRight(
    margin: Int,
    reset: Boolean?
) {
    ViewUtils.setMarginRight(this, margin, reset == true)
}

/**
 * 通过数据绑定设置底部外边距
 * <pre>
 *     对应布局属性 binding_marginBottom、binding_marginReset，requireAll 为 false。
 * </pre>
 *
 * @param margin 下边距像素值
 * @param reset 为 true 时将其余三边 margin 置 0；为 false 时保留其余三边当前值
 */
@BindingAdapter(
    value = ["binding_marginBottom", "binding_marginReset"],
    requireAll = false
)
fun View.bindingMarginBottom(
    margin: Int,
    reset: Boolean?
) {
    ViewUtils.setMarginBottom(this, margin, reset == true)
}