package dev.mvvm.binding.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.mvvm.base.attribute.Margins
import dev.mvvm.base.attribute.Paddings
import dev.utils.app.ViewUtils

// =======================
// = View BindingAdapter =
// =======================

// ===========
// = visible =
// ===========

@BindingAdapter("binding_visibleOrGone")
fun View.bindingVisibleOrGone(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("binding_visibleOrInVisible")
fun View.bindingVisibleOrInVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

// ==========
// = margin =
// ==========

@BindingAdapter("binding_layout_margin")
fun View.bindingLayoutMargin(margins: Margins?) {
    margins?.let {
        ViewUtils.setMargin(this, it.left, it.top, it.right, it.bottom)
    }
}

// ==========
// = padding =
// ==========

@BindingAdapter("binding_layout_padding")
fun View.bindingLayoutPadding(paddings: Paddings?) {
    paddings?.let {
        ViewUtils.setPadding(this, it.left, it.top, it.right, it.bottom)
    }
}
