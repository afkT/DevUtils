package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.utils.app.ViewUtils

// ======================================
// = LongClickable State BindingAdapter =
// ======================================

/**
 * 通过数据绑定设置是否可长按。
 * <pre>
 *     布局属性 binding_view_long_clickable；对应 ViewUtils.setLongClickable。
 * </pre>
 */
@BindingAdapter("binding_view_long_clickable")
fun View.bindingViewLongClickable(longClickable: Boolean) {
    ViewUtils.setLongClickable(longClickable, this)
}
