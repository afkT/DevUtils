package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.utils.app.ViewUtils

// ==================================
// = Clickable State BindingAdapter =
// ==================================

/**
 * 通过数据绑定设置是否可点击。
 * <pre>
 *     布局属性 binding_clickable；对应 ViewUtils.setClickable。
 * </pre>
 */
@BindingAdapter("binding_clickable")
fun View.bindingClickable(clickable: Boolean) {
    ViewUtils.setClickable(clickable, this)
}