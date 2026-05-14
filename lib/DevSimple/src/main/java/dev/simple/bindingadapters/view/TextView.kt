package dev.simple.bindingadapters.view

import android.graphics.Paint
import android.os.Build
import android.widget.TextView
import androidx.databinding.BindingAdapter
import dev.utils.LogPrintUtils
import dev.utils.app.TextViewUtils

// ===========================
// = TextView BindingAdapter =
// ===========================

private const val TAG = "Dev_TextView_BindingAdapter"

// =============
// = textStyle =
// =============

/**
 * 通过数据绑定设置 TextView 粗体样式。
 * <pre>
 *     布局属性 binding_tv_text_bold；内部委托 TextViewUtils.setBold。
 * </pre>
 *
 * @param bold 是否粗体
 */
@BindingAdapter("binding_tv_text_bold")
fun TextView.bindingTVTextBold(bold: Boolean) {
    TextViewUtils.setBold(this, bold)
}

// =============
// = paintFlags =
// =============

/**
 * 通过数据绑定控制下划线样式。
 * <pre>
 *     布局属性 binding_tv_underline、binding_tv_underline_anti_alias（后者可选；未指定时抗锯齿按 true）。
 *     true 时委托 TextViewUtils.setUnderlineText；false 时清除下划线标志；主属性为 null 时不修改状态。
 * </pre>
 *
 * @param underline 是否显示下划线；未绑定主属性时为 null
 * @param antiAlias 是否叠加抗锯齿
 */
@BindingAdapter(
    value = ["binding_tv_underline", "binding_tv_underline_anti_alias"],
    requireAll = false
)
fun TextView.bindingTVUnderline(
    underline: Boolean?,
    antiAlias: Boolean?
) {
    when (underline) {
        true -> TextViewUtils.setUnderlineText(this, antiAlias != false)
        false -> paintFlags = paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
        null -> Unit
    }
}

/**
 * 通过数据绑定控制删除线样式。
 * <pre>
 *     布局属性 binding_tv_strike_thru、binding_tv_strike_thru_anti_alias（后者可选；未指定时抗锯齿按 true）。
 *     true 时委托 TextViewUtils.setStrikeThruText；false 时清除删除线标志；主属性为 null 时不修改状态。
 * </pre>
 *
 * @param strikeThru 是否显示删除线；未绑定主属性时为 null
 * @param antiAlias 是否叠加抗锯齿
 */
@BindingAdapter(
    value = ["binding_tv_strike_thru", "binding_tv_strike_thru_anti_alias"],
    requireAll = false
)
fun TextView.bindingTVStrikeThru(
    strikeThru: Boolean?,
    antiAlias: Boolean?
) {
    when (strikeThru) {
        true -> TextViewUtils.setStrikeThruText(this, antiAlias != false)
        false -> paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        null -> Unit
    }
}

/**
 * 通过数据绑定直接设置 Paint 标志位。
 * <pre>
 *     布局属性 binding_tv_paint_flags；会覆盖下划线、删除线等相关位，不宜与上述装饰属性混用。
 *     委托 TextViewUtils.setPaintFlags。
 * </pre>
 *
 * @param flags 传给 TextView.setPaintFlags 的完整标志
 */
@BindingAdapter("binding_tv_paint_flags")
fun TextView.bindingTVPaintFlags(flags: Int) {
    TextViewUtils.setPaintFlags(this, flags)
}

// =============
// = 排版与字体 =
// =============

/**
 * 通过数据绑定设置是否保留字体上下留白。
 * <pre>
 *     布局属性 binding_tv_include_font_padding；委托 TextViewUtils.setIncludeFontPadding。
 * </pre>
 *
 * @param includePadding 是否保留 includeFontPadding
 */
@BindingAdapter("binding_tv_include_font_padding")
fun TextView.bindingTVIncludeFontPadding(includePadding: Boolean) {
    TextViewUtils.setIncludeFontPadding(this, includePadding)
}

/**
 * 通过数据绑定设置字间距。
 * <pre>
 *     布局属性 binding_tv_letter_spacing；API 21 以下忽略；委托 TextViewUtils.setLetterSpacing。
 * </pre>
 *
 * @param letterSpacing 字间距
 */
@BindingAdapter("binding_tv_letter_spacing")
fun TextView.bindingTVLetterSpacing(letterSpacing: Float) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        TextViewUtils.setLetterSpacing(this, letterSpacing)
    }
}

/**
 * 通过数据绑定设置行距与行距倍数。
 * <pre>
 *     布局属性 binding_tv_line_spacing_extra、binding_tv_line_spacing_multiplier，可只设其一；均未绑定时直接返回。
 *     未指定的 extra 按 0、multiplier 按 1；委托 TextViewUtils.setLineSpacingAndMultiplier。
 * </pre>
 *
 * @param lineSpacingExtra 行间距附加值
 * @param lineSpacingMultiplier 行间距倍数
 */
@BindingAdapter(
    value = ["binding_tv_line_spacing_extra", "binding_tv_line_spacing_multiplier"],
    requireAll = false
)
fun TextView.bindingTVLineSpacing(
    lineSpacingExtra: Float?,
    lineSpacingMultiplier: Float?
) {
    if (lineSpacingExtra == null && lineSpacingMultiplier == null) return
    TextViewUtils.setLineSpacingAndMultiplier(
        this,
        lineSpacingExtra ?: 0f,
        lineSpacingMultiplier ?: 1f
    )
}

/**
 * 通过数据绑定设置文字水平缩放比例。
 * <pre>
 *     布局属性 binding_tv_text_scale_x；委托 TextViewUtils.setTextScaleX。
 * </pre>
 *
 * @param scaleX 缩放比例
 */
@BindingAdapter("binding_tv_text_scale_x")
fun TextView.bindingTVTextScaleX(scaleX: Float) {
    TextViewUtils.setTextScaleX(this, scaleX)
}

/**
 * 通过数据绑定按 sp 设置字号。
 * <pre>
 *     布局属性 binding_tv_text_size_sp；委托 TextViewUtils.setTextSizeBySp。
 * </pre>
 *
 * @param sizeSp 字号（sp）
 */
@BindingAdapter("binding_tv_text_size_sp")
fun TextView.bindingTVTextSizeSp(sizeSp: Float) {
    TextViewUtils.setTextSizeBySp(this, sizeSp)
}

// =============
// = 行与宽度 =
// =============

/**
 * 通过数据绑定设置最大行数。
 * <pre>
 *     布局属性 binding_tv_max_lines；委托 TextViewUtils.setMaxLines。
 * </pre>
 *
 * @param maxLines 最大行数
 */
@BindingAdapter("binding_tv_max_lines")
fun TextView.bindingTVMaxLines(maxLines: Int) {
    TextViewUtils.setMaxLines(this, maxLines)
}

/**
 * 通过数据绑定设置最小行数。
 * <pre>
 *     布局属性 binding_tv_min_lines；委托 TextViewUtils.setMinLines。
 * </pre>
 *
 * @param minLines 最小行数
 */
@BindingAdapter("binding_tv_min_lines")
fun TextView.bindingTVMinLines(minLines: Int) {
    TextViewUtils.setMinLines(this, minLines)
}

/**
 * 通过数据绑定固定行数。
 * <pre>
 *     布局属性 binding_tv_lines；委托 TextViewUtils.setLines。
 * </pre>
 *
 * @param lines 行数
 */
@BindingAdapter("binding_tv_lines")
fun TextView.bindingTVLines(lines: Int) {
    TextViewUtils.setLines(this, lines)
}

/**
 * 通过数据绑定设置最大字符宽度（ems）。
 * <pre>
 *     布局属性 binding_tv_max_ems；委托 TextViewUtils.setMaxEms。
 * </pre>
 *
 * @param maxEms 最大 ems
 */
@BindingAdapter("binding_tv_max_ems")
fun TextView.bindingTVMaxEms(maxEms: Int) {
    TextViewUtils.setMaxEms(this, maxEms)
}

/**
 * 通过数据绑定设置最小字符宽度（ems）。
 * <pre>
 *     布局属性 binding_tv_min_ems；委托 TextViewUtils.setMinEms。
 * </pre>
 *
 * @param minEms 最小 ems
 */
@BindingAdapter("binding_tv_min_ems")
fun TextView.bindingTVMinEms(minEms: Int) {
    TextViewUtils.setMinEms(this, minEms)
}

/**
 * 通过数据绑定设置字符宽度（ems）。
 * <pre>
 *     布局属性 binding_tv_ems；委托 TextViewUtils.setEms。
 * </pre>
 *
 * @param ems 字符宽度
 */
@BindingAdapter("binding_tv_ems")
fun TextView.bindingTVEms(ems: Int) {
    TextViewUtils.setEms(this, ems)
}

// =============
// = 内容与行为 =
// =============

/**
 * 通过数据绑定按工具类规则设置 Html 文本。
 * <pre>
 *     布局属性 binding_tv_html_text；委托 TextViewUtils.setHtmlText（与系统 N 以上 FROM_HTML_MODE_LEGACY 行为一致）。
 *     content 为 null 时清空文本。
 * </pre>
 *
 * @param content Html 字符串
 */
@BindingAdapter("binding_tv_html_text")
fun TextView.bindingTVHtmlText(content: String?) {
    if (content == null) {
        text = ""
        return
    }
    try {
        TextViewUtils.setHtmlText(this, content)
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "bindingTVHtmlText")
        text = ""
    }
}

/**
 * 通过数据绑定设置文字对齐方式。
 * <pre>
 *     布局属性 binding_tv_gravity；委托 TextViewUtils.setGravity。
 * </pre>
 *
 * @param gravity Gravity 标志组合
 */
@BindingAdapter("binding_tv_gravity")
fun TextView.bindingTVGravity(gravity: Int) {
    TextViewUtils.setGravity(this, gravity)
}

/**
 * 通过数据绑定设置是否全大写展示。
 * <pre>
 *     布局属性 binding_tv_all_caps；委托 TextViewUtils.setAllCaps。
 * </pre>
 *
 * @param allCaps 是否全大写
 */
@BindingAdapter("binding_tv_all_caps")
fun TextView.bindingTVAllCaps(allCaps: Boolean) {
    TextViewUtils.setAllCaps(this, allCaps)
}

/**
 * 通过数据绑定设置最大输入长度。
 * <pre>
 *     布局属性 binding_tv_max_length；委托 TextViewUtils.setMaxLength。
 * </pre>
 *
 * @param maxLength 最大长度
 */
@BindingAdapter("binding_tv_max_length")
fun TextView.bindingTVMaxLength(maxLength: Int) {
    TextViewUtils.setMaxLength(this, maxLength)
}

/**
 * 通过数据绑定设置自动链接掩码。
 * <pre>
 *     布局属性 binding_tv_auto_link_mask；委托 TextViewUtils.setAutoLinkMask。
 * </pre>
 *
 * @param mask 自动链接 mask
 */
@BindingAdapter("binding_tv_auto_link_mask")
fun TextView.bindingTVAutoLinkMask(mask: Int) {
    TextViewUtils.setAutoLinkMask(this, mask)
}

/**
 * 通过数据绑定设置输入类型。
 * <pre>
 *     布局属性 binding_tv_input_type；委托 TextViewUtils.setInputType。
 * </pre>
 *
 * @param type InputType 常量组合
 */
@BindingAdapter("binding_tv_input_type")
fun TextView.bindingTVInputType(type: Int) {
    TextViewUtils.setInputType(this, type)
}

/**
 * 通过数据绑定设置输入法动作选项。
 * <pre>
 *     布局属性 binding_tv_ime_options；委托 TextViewUtils.setImeOptions。
 * </pre>
 *
 * @param imeOptions ImeOptions 标志组合
 */
@BindingAdapter("binding_tv_ime_options")
fun TextView.bindingTVImeOptions(imeOptions: Int) {
    TextViewUtils.setImeOptions(this, imeOptions)
}

/**
 * 通过数据绑定切换密码明文与密文显示。
 * <pre>
 *     布局属性 binding_tv_display_password；true 为明文，false 为密文；委托 TextViewUtils.setTransformationMethod。
 * </pre>
 *
 * @param displayPassword 是否显示密码明文
 */
@BindingAdapter("binding_tv_display_password")
fun TextView.bindingTVDisplayPassword(displayPassword: Boolean) {
    TextViewUtils.setTransformationMethod(this, displayPassword)
}

/**
 * 通过数据绑定设置 compound drawable 与文字间距。
 * <pre>
 *     布局属性 binding_tv_compound_drawable_padding；委托 TextViewUtils.setCompoundDrawablePadding。
 * </pre>
 *
 * @param padding 间距（px）
 */
@BindingAdapter("binding_tv_compound_drawable_padding")
fun TextView.bindingTVCompoundDrawablePadding(padding: Int) {
    TextViewUtils.setCompoundDrawablePadding(this, padding)
}
