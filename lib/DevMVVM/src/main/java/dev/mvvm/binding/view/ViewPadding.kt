package dev.mvvm.binding.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.mvvm.base.attribute.Paddings
import dev.utils.app.ViewUtils

// ==============================
// = View Padding BindingAdapter =
// ==============================

@BindingAdapter("binding_layout_padding_attr")
fun View.bindingLayoutPaddingAttr(paddings: Paddings?) {
    paddings?.let {
        ViewUtils.setPadding(this, it.left, it.top, it.right, it.bottom)
    }
}

// =

@BindingAdapter("binding_layout_padding")
fun View.bindingLayoutPadding(padding: Int) {
    ViewUtils.setPadding(this, padding)
}

@BindingAdapter(
    value = ["binding_layout_padding_left", "binding_layout_reset"],
    requireAll = false
)
fun View.bindingLayoutPaddingLeft(
    padding: Int,
    reset: Boolean
) {
    ViewUtils.setPaddingLeft(this, padding, reset)
}

@BindingAdapter(
    value = ["binding_layout_padding_top", "binding_layout_reset"],
    requireAll = false
)
fun View.bindingLayoutPaddingTop(
    padding: Int,
    reset: Boolean
) {
    ViewUtils.setPaddingTop(this, padding, reset)
}

@BindingAdapter(
    value = ["binding_layout_padding_right", "binding_layout_reset"],
    requireAll = false
)
fun View.bindingLayoutPaddingRight(
    padding: Int,
    reset: Boolean
) {
    ViewUtils.setPaddingRight(this, padding, reset)
}

@BindingAdapter(
    value = ["binding_layout_padding_bottom", "binding_layout_reset"],
    requireAll = false
)
fun View.bindingLayoutPaddingBottom(
    padding: Int,
    reset: Boolean
) {
    ViewUtils.setPaddingBottom(this, padding, reset)
}