package dev.simple.core.binding.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.simple.core.base.attribute.Paddings
import dev.utils.app.ViewUtils

// ==============================
// = View Padding BindingAdapter =
// ==============================

@BindingAdapter("binding_paddingAttr")
fun View.bindingPaddingAttr(paddings: Paddings?) {
    paddings?.let {
        ViewUtils.setPadding(this, it.left, it.top, it.right, it.bottom)
    }
}

// =

@BindingAdapter("binding_padding")
fun View.bindingPadding(padding: Int) {
    ViewUtils.setPadding(this, padding)
}

@BindingAdapter(
    value = ["binding_paddingLeft", "binding_paddingReset"],
    requireAll = false
)
fun View.bindingPaddingLeft(
    padding: Int,
    reset: Boolean
) {
    ViewUtils.setPaddingLeft(this, padding, reset)
}

@BindingAdapter(
    value = ["binding_paddingTop", "binding_paddingReset"],
    requireAll = false
)
fun View.bindingPaddingTop(
    padding: Int,
    reset: Boolean
) {
    ViewUtils.setPaddingTop(this, padding, reset)
}

@BindingAdapter(
    value = ["binding_paddingRight", "binding_paddingReset"],
    requireAll = false
)
fun View.bindingPaddingRight(
    padding: Int,
    reset: Boolean
) {
    ViewUtils.setPaddingRight(this, padding, reset)
}

@BindingAdapter(
    value = ["binding_paddingBottom", "binding_paddingReset"],
    requireAll = false
)
fun View.bindingPaddingBottom(
    padding: Int,
    reset: Boolean
) {
    ViewUtils.setPaddingBottom(this, padding, reset)
}