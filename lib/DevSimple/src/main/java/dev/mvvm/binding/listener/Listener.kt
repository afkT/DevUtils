package dev.mvvm.binding.listener

import android.view.View
import androidx.databinding.BindingAdapter
import dev.expand.engine.log.log_dTag
import dev.mvvm.base.Config
import dev.mvvm.command.BindingConsumer
import dev.utils.app.ClickUtils

// =======================
// = View BindingAdapter =
// =======================

private const val TAG = "View_BindingAdapter"

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
    val interval = Config.intervalTime(intervalTime)

    if (Config.printLog()) {
        TAG.log_dTag(
            message = "bindingViewClick\nintervalTime: %s",
            args = arrayOf(interval)
        )
    }
    this.setOnClickListener {
        if (interval > 0L && ClickUtils.isFastDoubleClick(this, interval)) {
            if (Config.printLog()) {
                TAG.log_dTag(
                    message = "bindingViewClick\nintervalTime: %s\nisFastDoubleClick: true",
                    args = arrayOf(interval)
                )
            }
            return@setOnClickListener
        }
        listener.accept(it)
    }
}