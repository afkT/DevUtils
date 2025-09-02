package dev.simple.bindingadapters.listener

import android.view.View
import androidx.databinding.BindingAdapter
import dev.simple.interfaces.BindingConsumer
import dev.utils.app.ClickUtils

// =======================
// = View BindingAdapter =
// =======================

// =========
// = Click =
// =========

@BindingAdapter(
    value = ["binding_click", "binding_click_interval"],
    requireAll = false
)
fun View.bindingViewClick(
    listener: BindingConsumer<View>?,
    intervalTime: Long
) {
    if (listener == null) return
    this.setOnClickListener {
        if (intervalTime > 0L && ClickUtils.isFastDoubleClick(this, intervalTime)) {
            return@setOnClickListener
        }
        listener.accept(it)
    }
}