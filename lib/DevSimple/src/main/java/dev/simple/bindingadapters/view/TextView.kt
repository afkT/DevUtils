package dev.simple.bindingadapters.view

import android.text.Html
import android.widget.TextView
import androidx.databinding.BindingAdapter
import dev.utils.LogPrintUtils
import dev.utils.app.TextViewUtils
import dev.utils.common.StringUtils

// ===========================
// = TextView BindingAdapter =
// ===========================

private const val TAG = "TextView_BindingAdapter"

// =============
// = textStyle =
// =============

/**
 * 通过数据绑定将 HTML 字符串解析为图文并设置到 TextView
 * <pre>
 *     对应布局属性 binding_html_text；解析失败时回退为空串并打错误日志，避免绑定阶段崩溃。
 * </pre>
 *
 * @param text HTML 片段，空或空串时显示空串
 */
@BindingAdapter("binding_html_text")
fun TextView.bindingHtmlText(text: String?) {
    if (StringUtils.isNotEmpty(text)) {
        try {
            setText(Html.fromHtml(text))
        } catch (e: Throwable) {
            LogPrintUtils.eTag(TAG, e, "bindingHtmlText")
            setText("")
        }
    } else {
        setText("")
    }
}

/**
 * 通过数据绑定设置 TextView 粗体样式
 * <pre>
 *     对应布局属性 binding_textBold；内部委托 TextViewUtils.setBold。
 * </pre>
 *
 * @param bold 是否粗体
 */
@BindingAdapter("binding_textBold")
fun TextView.bindingTextBold(bold: Boolean) {
    TextViewUtils.setBold(this, bold)
}