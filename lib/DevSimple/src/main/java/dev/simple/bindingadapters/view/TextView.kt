package dev.simple.bindingadapters.view

import android.text.Html
import android.widget.TextView
import androidx.databinding.BindingAdapter
import dev.utils.app.TextViewUtils
import dev.utils.common.StringUtils

// ===========================
// = TextView BindingAdapter =
// ===========================

// =============
// = textStyle =
// =============

@BindingAdapter("binding_html_text")
fun TextView.bindingHtmlText(text: String?) {
    if (StringUtils.isNotEmpty(text)) {
        setText(Html.fromHtml(text))
    } else {
        setText("")
    }
}

@BindingAdapter("binding_textBold")
fun TextView.bindingTextBold(bold: Boolean) {
    TextViewUtils.setBold(this, bold)
}