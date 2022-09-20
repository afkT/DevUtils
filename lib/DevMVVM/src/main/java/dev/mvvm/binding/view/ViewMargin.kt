package dev.mvvm.binding.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.mvvm.base.attribute.Margins
import dev.utils.app.ViewUtils

// ==============================
// = View Margin BindingAdapter =
// ==============================

@BindingAdapter("binding_layout_margin_attr")
fun View.bindingLayoutMarginAttr(margins: Margins?) {
    margins?.let {
        ViewUtils.setMargin(this, it.left, it.top, it.right, it.bottom)
    }
}

// =

@BindingAdapter("binding_layout_margin")
fun View.bindingLayoutMargin(margin: Int) {
    ViewUtils.setMargin(this, margin)
}

@BindingAdapter(
    value = ["binding_layout_margin_left", "binding_layout_reset"],
    requireAll = false
)
fun View.bindingLayoutMarginLeft(
    margin: Int,
    reset: Boolean
) {
    ViewUtils.setMarginLeft(this, margin, reset)
}

@BindingAdapter(
    value = ["binding_layout_margin_top", "binding_layout_reset"],
    requireAll = false
)
fun View.bindingLayoutMarginTop(
    margin: Int,
    reset: Boolean
) {
    ViewUtils.setMarginTop(this, margin, reset)
}

@BindingAdapter(
    value = ["binding_layout_margin_right", "binding_layout_reset"],
    requireAll = false
)
fun View.bindingLayoutMarginRight(
    margin: Int,
    reset: Boolean
) {
    ViewUtils.setMarginRight(this, margin, reset)
}

@BindingAdapter(
    value = ["binding_layout_margin_bottom", "binding_layout_reset"],
    requireAll = false
)
fun View.bindingLayoutMarginBottom(
    margin: Int,
    reset: Boolean
) {
    ViewUtils.setMarginBottom(this, margin, reset)
}