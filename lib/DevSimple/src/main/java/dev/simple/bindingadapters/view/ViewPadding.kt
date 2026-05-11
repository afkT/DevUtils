package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.simple.bindingadapters.view.attribute.Paddings
import dev.utils.app.ViewUtils

// ==============================
// = View Padding BindingAdapter =
// ==============================

/**
 * 通过数据绑定按四边距对象设置内边距
 * <pre>
 *     对应布局属性 binding_paddingAttr；paddings 为空时不修改。
 * </pre>
 *
 * @param paddings 左、上、右、下内边距封装，可为空
 */
@BindingAdapter("binding_paddingAttr")
fun View.bindingPaddingAttr(paddings: Paddings?) {
    paddings?.let {
        ViewUtils.setPadding(this, it.left, it.top, it.right, it.bottom)
    }
}

// =

/**
 * 通过数据绑定为四边设置相同内边距
 * <pre>
 *     对应布局属性 binding_padding。
 * </pre>
 *
 * @param padding 统一内边距像素值
 */
@BindingAdapter("binding_padding")
fun View.bindingPadding(padding: Int) {
    ViewUtils.setPadding(this, padding)
}

/**
 * 通过数据绑定设置左侧内边距
 * <pre>
 *     对应布局属性 binding_paddingLeft、binding_paddingReset，requireAll 为 false。
 * </pre>
 *
 * @param padding 左内边距像素值
 * @param reset 为 true 时将其余三边 padding 置 0；为 false 时保留其余三边当前值
 */
@BindingAdapter(
    value = ["binding_paddingLeft", "binding_paddingReset"],
    requireAll = false
)
fun View.bindingPaddingLeft(
    padding: Int,
    reset: Boolean?
) {
    ViewUtils.setPaddingLeft(this, padding, reset == true)
}

/**
 * 通过数据绑定设置顶部内边距
 * <pre>
 *     对应布局属性 binding_paddingTop、binding_paddingReset，requireAll 为 false。
 * </pre>
 *
 * @param padding 上内边距像素值
 * @param reset 为 true 时将其余三边 padding 置 0；为 false 时保留其余三边当前值
 */
@BindingAdapter(
    value = ["binding_paddingTop", "binding_paddingReset"],
    requireAll = false
)
fun View.bindingPaddingTop(
    padding: Int,
    reset: Boolean?
) {
    ViewUtils.setPaddingTop(this, padding, reset == true)
}

/**
 * 通过数据绑定设置右侧内边距
 * <pre>
 *     对应布局属性 binding_paddingRight、binding_paddingReset，requireAll 为 false。
 * </pre>
 *
 * @param padding 右内边距像素值
 * @param reset 为 true 时将其余三边 padding 置 0；为 false 时保留其余三边当前值
 */
@BindingAdapter(
    value = ["binding_paddingRight", "binding_paddingReset"],
    requireAll = false
)
fun View.bindingPaddingRight(
    padding: Int,
    reset: Boolean?
) {
    ViewUtils.setPaddingRight(this, padding, reset == true)
}

/**
 * 通过数据绑定设置底部内边距
 * <pre>
 *     对应布局属性 binding_paddingBottom、binding_paddingReset，requireAll 为 false。
 * </pre>
 *
 * @param padding 下内边距像素值
 * @param reset 为 true 时将其余三边 padding 置 0；为 false 时保留其余三边当前值
 */
@BindingAdapter(
    value = ["binding_paddingBottom", "binding_paddingReset"],
    requireAll = false
)
fun View.bindingPaddingBottom(
    padding: Int,
    reset: Boolean?
) {
    ViewUtils.setPaddingBottom(this, padding, reset == true)
}