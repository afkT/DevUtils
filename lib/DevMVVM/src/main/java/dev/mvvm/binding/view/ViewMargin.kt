package dev.mvvm.binding.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.mvvm.base.attribute.Margins
import dev.utils.app.ViewUtils

// ==============================
// = View Margin BindingAdapter =
// ==============================

@BindingAdapter("binding_marginAttr")
fun View.bindingMarginAttr(margins: Margins?) {
    margins?.let {
        ViewUtils.setMargin(this, it.left, it.top, it.right, it.bottom)
    }
}

// =

@BindingAdapter("binding_margin")
fun View.bindingMargin(margin: Int) {
    ViewUtils.setMargin(this, margin)
}

@BindingAdapter(
    value = ["binding_marginLeft", "binding_marginReset"],
    requireAll = false
)
fun View.bindingMarginLeft(
    margin: Int,
    reset: Boolean
) {
    ViewUtils.setMarginLeft(this, margin, reset)
}

@BindingAdapter(
    value = ["binding_marginTop", "binding_marginReset"],
    requireAll = false
)
fun View.bindingMarginTop(
    margin: Int,
    reset: Boolean
) {
    ViewUtils.setMarginTop(this, margin, reset)
}

@BindingAdapter(
    value = ["binding_marginRight", "binding_marginReset"],
    requireAll = false
)
fun View.bindingMarginRight(
    margin: Int,
    reset: Boolean
) {
    ViewUtils.setMarginRight(this, margin, reset)
}

@BindingAdapter(
    value = ["binding_marginBottom", "binding_marginReset"],
    requireAll = false
)
fun View.bindingMarginBottom(
    margin: Int,
    reset: Boolean
) {
    ViewUtils.setMarginBottom(this, margin, reset)
}