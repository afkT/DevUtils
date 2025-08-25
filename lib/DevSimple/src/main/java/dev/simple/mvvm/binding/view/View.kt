package dev.simple.mvvm.binding.view

import android.view.View
import androidx.databinding.BindingAdapter

// =======================
// = View BindingAdapter =
// =======================

// ===========
// = visible =
// ===========

@BindingAdapter("binding_visibility")
fun View.bindingVisibility(visibility: Int) {
    this.visibility = visibility
}

@BindingAdapter("binding_visibleOrGone")
fun View.bindingVisibleOrGone(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("binding_visibleOrInVisible")
fun View.bindingVisibleOrInVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.INVISIBLE
}