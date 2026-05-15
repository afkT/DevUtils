package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.utils.app.ViewUtils

// =================================
// = Focusable State BindingAdapter =
// =================================

/**
 * 通过数据绑定设置触摸模式下是否可聚焦。
 * <pre>
 *     布局属性 binding_focusable_in_touch_mode；对应 ViewUtils.setFocusableInTouchMode。
 * </pre>
 */
@BindingAdapter("binding_focusable_in_touch_mode")
fun View.bindingFocusableInTouchMode(focusableInTouchMode: Boolean) {
    ViewUtils.setFocusableInTouchMode(focusableInTouchMode, this)
}

/**
 * 通过数据绑定设置是否可聚焦。
 * <pre>
 *     布局属性 binding_focusable；对应 ViewUtils.setFocusable。
 * </pre>
 */
@BindingAdapter("binding_focusable")
fun View.bindingFocusable(focusable: Boolean) {
    ViewUtils.setFocusable(focusable, this)
}