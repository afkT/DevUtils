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

/**
 * 通过数据绑定设置空点击监听以消费点击事件
 * <pre>
 *     对应布局属性 binding_click_empty；布尔参数为占位，用于在布局中触发绑定。
 * </pre>
 *
 * @param unused 占位参数，可为空
 */
@BindingAdapter("binding_click_empty")
fun View.bindingViewClickEmpty(
    unused: Boolean?
) {
    this.setOnClickListener(ClickUtils.EMPTY_CLICK)
}

/**
 * 通过数据绑定设置带防抖的点击回调
 * <pre>
 *     对应布局属性 binding_click、binding_click_interval，requireAll 为 false。
 *     intervalTime 大于 0 时通过 ClickUtils.isFastDoubleClick 防抖；listener 为空时不设置监听。
 * </pre>
 *
 * @param listener 点击时消费的回调，可为空
 * @param intervalTime 防抖间隔毫秒，小于等于 0 时不防抖
 */
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