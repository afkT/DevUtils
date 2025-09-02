package dev.simple.bindingadapters.view

import android.widget.TextView
import androidx.databinding.BindingAdapter
import dev.utils.app.TextViewUtils

// ===========================
// = TextView BindingAdapter =
// ===========================

// =============
// = textStyle =
// =============

@BindingAdapter("binding_textBold")
fun TextView.bindingTextBold(bold: Boolean) {
    TextViewUtils.setBold(this, bold)
}