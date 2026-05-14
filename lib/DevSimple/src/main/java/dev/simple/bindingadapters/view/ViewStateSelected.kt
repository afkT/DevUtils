package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.utils.app.ViewUtils

// =================================
// = Selected State BindingAdapter =
// =================================

/**
 * 通过数据绑定设置选中状态。
 * <pre>
 *     布局属性 binding_view_selected；对应 ViewUtils.setSelected。
 * </pre>
 */
@BindingAdapter("binding_view_selected")
fun View.bindingViewSelected(selected: Boolean) {
    ViewUtils.setSelected(selected, this)
}
