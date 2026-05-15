package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import dev.utils.app.ViewUtils

// ================================
// = Enabled State BindingAdapter =
// ================================

/**
 * 通过数据绑定设置是否启用。
 * <pre>
 *     布局属性 binding_enabled；对应 ViewUtils.setEnabled。
 * </pre>
 */
@BindingAdapter("binding_enabled")
fun View.bindingEnabled(enabled: Boolean) {
    ViewUtils.setEnabled(enabled, this)
}
