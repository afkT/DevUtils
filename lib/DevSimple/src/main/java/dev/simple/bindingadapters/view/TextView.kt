package dev.simple.bindingadapters.view

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.text.method.TransformationMethod
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import dev.simple.bindingadapters.attribute.*
import dev.simple.bindingadapters.qualifiesBindingAction
import dev.utils.LogPrintUtils
import dev.utils.app.TextViewUtils

// ===========================
// = TextView BindingAdapter =
// ===========================

/**
 * TextView 与通用文本控件的 Data Binding 适配集合。
 *
 * 布局属性统一为 `binding_tv_*`；实现主要对应 `dev.utils.app.TextViewUtils` 中适合在单节点上表达的 `set*` 等 API。
 * <pre>
 *     未封装 `get*` 系列、多 `View…` 变参批量操作、`Paint`/`Rect` 纯测量工具等不适合布局绑定的接口。
 *     与 [EditText] 专项属性（`binding_et_*`）并存时，按控件语义择一使用，避免同一语义重复绑定冲突。
 *     需对仅接收者型副作用多次触发时，使用带 `_ts` 后缀属性并绑定正时间戳（判定同 [qualifiesBindingAction]）。
 *     强关联多参使用 `attribute` 包下 [TvLineSpacingExtraMultiplier]、[TvMaxLengthAndText] 等实体单属性绑定。
 * </pre>
 */

private const val TAG = "Dev_TextView_BindingAdapter"

// =============
// = Hint 与正文 =
// =============

/**
 * 通过数据绑定设置提示文案。
 * <pre>
 *     布局属性 `binding_tv_hint`；委托 [TextViewUtils.setHint]。
 * </pre>
 *
 * @param text 提示内容，`null` 时不修改
 */
@BindingAdapter("binding_tv_hint")
fun TextView.bindingTVHint(text: CharSequence?) {
    if (text == null) return
    TextViewUtils.setHint(this, text)
}

/**
 * 通过数据绑定按整型色值设置提示文字颜色。
 * <pre>
 *     布局属性 `binding_tv_hint_color`；委托 [TextViewUtils.setHintTextColor]；`null` 时不修改。
 * </pre>
 *
 * @param color `@ColorInt` 色值
 */
@BindingAdapter("binding_tv_hint_color")
fun TextView.bindingTVHintColor(@ColorInt color: Int?) {
    if (color == null) return
    TextViewUtils.setHintTextColor(this, color)
}

/**
 * 通过数据绑定按状态列表设置提示文字颜色。
 * <pre>
 *     布局属性 `binding_tv_hint_color_state`；委托 [TextViewUtils.setHintTextColor]；`null` 时不修改。
 * </pre>
 *
 * @param colors 颜色状态列表
 */
@BindingAdapter("binding_tv_hint_color_state")
fun TextView.bindingTVHintColorState(colors: ColorStateList?) {
    if (colors == null) return
    TextViewUtils.setHintTextColor(this, colors)
}

/**
 * 通过数据绑定设置显示文本。
 * <pre>
 *     布局属性 `binding_tv_text`；委托 [TextViewUtils.setText]。
 * </pre>
 *
 * @param text 正文，`null` 时仍委托工具类写入（与工具类行为一致）
 */
@BindingAdapter("binding_tv_text")
fun TextView.bindingTVText(text: CharSequence?) {
    TextViewUtils.setText(this, text)
}

/**
 * 通过数据绑定按整型色值设置正文颜色。
 * <pre>
 *     布局属性 `binding_tv_text_color`；`null` 时不修改；委托 [TextViewUtils.setTextColor]。
 * </pre>
 *
 * @param color `@ColorInt` 色值
 */
@BindingAdapter("binding_tv_text_color")
fun TextView.bindingTVTextColor(@ColorInt color: Int?) {
    if (color == null) return
    TextViewUtils.setTextColor(this, color)
}

/**
 * 通过数据绑定按状态列表设置正文颜色。
 * <pre>
 *     布局属性 `binding_tv_text_color_state`；`null` 时不修改；委托 [TextViewUtils.setTextColor]。
 * </pre>
 *
 * @param colors 颜色状态列表
 */
@BindingAdapter("binding_tv_text_color_state")
fun TextView.bindingTVTextColorState(colors: ColorStateList?) {
    if (colors == null) return
    TextViewUtils.setTextColor(this, colors)
}

/**
 * 通过数据绑定按 Html 解析结果设置正文。
 * <pre>
 *     布局属性 `binding_tv_html`；委托 [TextViewUtils.setHtmlText]；解析异常在适配器内捕获并记日志。
 * </pre>
 *
 * @param html Html 字符串，`null` 时不修改
 */
@BindingAdapter("binding_tv_html")
fun TextView.bindingTVHtml(html: String?) {
    if (html == null) {
        text = ""
        return
    }
    try {
        TextViewUtils.setHtmlText(this, html)
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "bindingTVHtml")
        text = ""
    }
}

// ============
// = 字体与字号 =
// ============

/**
 * 通过数据绑定设置 [Typeface]。
 * <pre>
 *     布局属性 `binding_tv_typeface`、`binding_tv_typeface_style`（可选，未指定则仅设字体不分样式）；
 *     委托 [TextViewUtils.setTypeface]；`typeface` 为 `null` 时不修改。
 * </pre>
 *
 * @param typeface 字体实例
 * @param style [Typeface] 样式常量，可选
 */
@BindingAdapter(
    value = ["binding_tv_typeface", "binding_tv_typeface_style"],
    requireAll = false,
)
fun TextView.bindingTVTypeface(
    typeface: Typeface?,
    style: Int?,
) {
    if (typeface == null) return
    if (style != null) {
        TextViewUtils.setTypeface(this, typeface, style)
    } else {
        TextViewUtils.setTypeface(this, typeface)
    }
}

/**
 * 通过数据绑定在指定字体上切换粗体。
 * <pre>
 *     布局属性 `binding_tv_typeface_bold`；`null` 跳过；委托 [TextViewUtils.setBold]。
 * </pre>
 *
 * @param value 字体与是否粗体
 */
@BindingAdapter("binding_tv_typeface_bold")
fun TextView.bindingTVTypefaceBold(value: TvTypefaceBold?) {
    if (value == null) return
    TextViewUtils.setBold(this, value.typeface, value.bold)
}

/**
 * 通过数据绑定设置是否粗体（默认字体样式）。
 * <pre>
 *     布局属性 `binding_tv_bold`；`null` 时不修改；
 *     `true` / `false` 委托 [TextViewUtils.setBold] 开启或关闭粗体。
 * </pre>
 *
 * @param bold 是否粗体
 */
@BindingAdapter("binding_tv_bold")
fun TextView.bindingTVBold(bold: Boolean?) {
    if (bold == null) return
    TextViewUtils.setBold(this, bold)
}

/**
 * 通过数据绑定以 sp 为单位设置字号。
 * <pre>
 *     布局属性 `binding_tv_text_size_sp`；`null` 不修改；委托 [TextViewUtils.setTextSizeBySp]。
 * </pre>
 *
 * @param size 字号
 */
@BindingAdapter("binding_tv_text_size_sp")
fun TextView.bindingTVTextSizeSp(size: Float?) {
    if (size == null) return
    TextViewUtils.setTextSizeBySp(this, size)
}

/**
 * 通过数据绑定以 dp 为单位设置字号。
 * <pre>
 *     布局属性 `binding_tv_text_size_dp`；`null` 不修改；委托 [TextViewUtils.setTextSizeByDp]。
 * </pre>
 *
 * @param size 字号
 */
@BindingAdapter("binding_tv_text_size_dp")
fun TextView.bindingTVTextSizeDp(size: Float?) {
    if (size == null) return
    TextViewUtils.setTextSizeByDp(this, size)
}

/**
 * 通过数据绑定以 px 为单位设置字号。
 * <pre>
 *     布局属性 `binding_tv_text_size_px`；`null` 不修改；委托 [TextViewUtils.setTextSizeByPx]。
 * </pre>
 *
 * @param size 字号
 */
@BindingAdapter("binding_tv_text_size_px")
fun TextView.bindingTVTextSizePx(size: Float?) {
    if (size == null) return
    TextViewUtils.setTextSizeByPx(this, size)
}

/**
 * 通过数据绑定以英寸为单位设置字号。
 * <pre>
 *     布局属性 `binding_tv_text_size_in`；`null` 不修改；委托 [TextViewUtils.setTextSizeByIn]。
 * </pre>
 *
 * @param size 字号
 */
@BindingAdapter("binding_tv_text_size_in")
fun TextView.bindingTVTextSizeIn(size: Float?) {
    if (size == null) return
    TextViewUtils.setTextSizeByIn(this, size)
}

/**
 * 通过数据绑定按单位与数值设置字号。
 * <pre>
 *     布局属性 `binding_tv_text_size_unit_size`；`null` 跳过；委托 [TextViewUtils.setTextSize]。
 * </pre>
 *
 * @param value 单位与字号
 */
@BindingAdapter("binding_tv_text_size_unit_size")
fun TextView.bindingTVTextSizeUnitSize(value: TvTextSizeUnit?) {
    if (value == null) return
    TextViewUtils.setTextSize(this, value.unit, value.size)
}

// ================
// = Paint 与装饰线 =
// ================

/**
 * 通过数据绑定在时间戳有效时清空画笔标志。
 * <pre>
 *     布局属性 `binding_tv_clear_paint_flags_ts`；判定同 [qualifiesBindingAction]；委托 [TextViewUtils.clearFlags]。
 * </pre>
 *
 * @param timestamp 建议绑定递增时间戳或 [System.currentTimeMillis]
 */
@BindingAdapter("binding_tv_clear_paint_flags_ts")
fun TextView.bindingTVClearPaintFlagsTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    TextViewUtils.clearFlags(this)
}

/**
 * 通过数据绑定在时间戳有效时将画笔标志设为抗锯齿。
 * <pre>
 *     布局属性 `binding_tv_anti_alias_paint_flag_ts`；判定同 [qualifiesBindingAction]；委托 [TextViewUtils.setAntiAliasFlag]。
 * </pre>
 *
 * @param timestamp 建议绑定递增时间戳或 [System.currentTimeMillis]
 */
@BindingAdapter("binding_tv_anti_alias_paint_flag_ts")
fun TextView.bindingTVAntiAliasPaintFlagTs(timestamp: Long?) {
    if (!timestamp.qualifiesBindingAction()) return
    TextViewUtils.setAntiAliasFlag(this)
}

/**
 * 通过数据绑定设置画笔标志位。
 * <pre>
 *     布局属性 `binding_tv_paint_flags`；`null` 不修改；委托 [TextViewUtils.setPaintFlags]。
 * </pre>
 *
 * @param flags [android.graphics.Paint] 标志组合
 */
@BindingAdapter("binding_tv_paint_flags")
fun TextView.bindingTVPaintFlags(flags: Int?) {
    if (flags == null) return
    TextViewUtils.setPaintFlags(this, flags)
}

/**
 * 通过数据绑定设置或移除下划线相关标志。
 * <pre>
 *     布局属性 `binding_tv_underline`、`binding_tv_underline_anti_alias`（可选，默认 `true`）；
 *     `underline` 为 `true` 时委托 [TextViewUtils.setUnderlineText]，
 *     为 `false` 时委托 [TextViewUtils.removeUnderlineText]，`null` 时不修改。
 * </pre>
 *
 * @param underline 是否显示下划线效果
 * @param antiAlias 设置下划线时是否同时合并抗锯齿标志
 */
@BindingAdapter(
    value = ["binding_tv_underline", "binding_tv_underline_anti_alias"],
    requireAll = false,
)
fun TextView.bindingTVUnderline(
    underline: Boolean?,
    antiAlias: Boolean?,
) {
    if (underline == null) return
    if (underline) {
        TextViewUtils.setUnderlineText(this, antiAlias ?: true)
    } else {
        TextViewUtils.removeUnderlineText(this)
    }
}

/**
 * 通过数据绑定设置或移除删除线相关标志。
 * <pre>
 *     布局属性 `binding_tv_strike_thru`、`binding_tv_strike_thru_anti_alias`（可选，默认 `true`）；
 *     `strikeThru` 为 `true` 时委托 [TextViewUtils.setStrikeThruText]，
 *     为 `false` 时委托 [TextViewUtils.removeStrikeThruText]，`null` 时不修改。
 * </pre>
 *
 * @param strikeThru 是否显示中划线效果
 * @param antiAlias 设置中划线时是否同时合并抗锯齿标志
 */
@BindingAdapter(
    value = ["binding_tv_strike_thru", "binding_tv_strike_thru_anti_alias"],
    requireAll = false,
)
fun TextView.bindingTVStrikeThru(
    strikeThru: Boolean?,
    antiAlias: Boolean?,
) {
    if (strikeThru == null) return
    if (strikeThru) {
        TextViewUtils.setStrikeThruText(this, antiAlias ?: true)
    } else {
        TextViewUtils.removeStrikeThruText(this)
    }
}

// ============
// = 间距与缩放 =
// ============

/**
 * 通过数据绑定设置字间距。
 * <pre>
 *     布局属性 `binding_tv_letter_spacing`；API 21 以下忽略；委托 [TextViewUtils.setLetterSpacing]。
 * </pre>
 *
 * @param spacing 字间距，`null` 不修改
 */
@BindingAdapter("binding_tv_letter_spacing")
fun TextView.bindingTVLetterSpacing(spacing: Float?) {
    if (spacing == null) return
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
    TextViewUtils.setLetterSpacing(this, spacing)
}

/**
 * 通过数据绑定设置行间距与倍数。
 * <pre>
 *     布局属性 `binding_tv_line_spacing_extra_multiplier`；`null` 跳过；委托 [TextViewUtils.setLineSpacingAndMultiplier]。
 * </pre>
 *
 * @param value 行间距与倍数
 */
@BindingAdapter("binding_tv_line_spacing_extra_multiplier")
fun TextView.bindingTVLineSpacingExtraMultiplier(value: TvLineSpacingExtraMultiplier?) {
    if (value == null) return
    TextViewUtils.setLineSpacingAndMultiplier(
        this,
        value.lineSpacingExtra,
        value.lineSpacingMultiplier
    )
}

/**
 * 通过数据绑定仅设置行间距额外值（倍数固定为 1）。
 * <pre>
 *     布局属性 `binding_tv_line_spacing_extra`；`null` 不修改；委托 [TextViewUtils.setLineSpacing]。
 *     需同时指定倍数时请用 `binding_tv_line_spacing_extra_multiplier` 与 [TvLineSpacingExtraMultiplier]。
 * </pre>
 *
 * @param lineSpacingExtra 行间距额外值，对应 `android:lineSpacingExtra` 语义
 */
@BindingAdapter("binding_tv_line_spacing_extra")
fun TextView.bindingTVLineSpacingExtra(lineSpacingExtra: Float?) {
    if (lineSpacingExtra == null) return
    TextViewUtils.setLineSpacing(this, lineSpacingExtra)
}

/**
 * 通过数据绑定设置横向字宽缩放。
 * <pre>
 *     布局属性 `binding_tv_text_scale_x`；`null` 不修改；委托 [TextViewUtils.setTextScaleX]。
 * </pre>
 *
 * @param scaleX 缩放比例
 */
@BindingAdapter("binding_tv_text_scale_x")
fun TextView.bindingTVTextScaleX(scaleX: Float?) {
    if (scaleX == null) return
    TextViewUtils.setTextScaleX(this, scaleX)
}

/**
 * 通过数据绑定设置是否包含字体额外 padding。
 * <pre>
 *     布局属性 `binding_tv_include_font_padding`；`null` 不修改；委托 [TextViewUtils.setIncludeFontPadding]。
 * </pre>
 *
 * @param include 是否保留字体 padding
 */
@BindingAdapter("binding_tv_include_font_padding")
fun TextView.bindingTVIncludeFontPadding(include: Boolean?) {
    if (include == null) return
    TextViewUtils.setIncludeFontPadding(this, include)
}

// ============
// = 输入与显示 =
// ============

/**
 * 通过数据绑定设置 [android.text.InputType]。
 * <pre>
 *     布局属性 `binding_tv_input_type`；`null` 不修改；委托 [TextViewUtils.setInputType]。
 * </pre>
 *
 * @param type 输入类型位组合
 */
@BindingAdapter("binding_tv_input_type")
fun TextView.bindingTVInputType(type: Int?) {
    if (type == null) return
    TextViewUtils.setInputType(this, type)
}

/**
 * 通过数据绑定设置输入法选项。
 * <pre>
 *     布局属性 `binding_tv_ime_options`；`null` 不修改；委托 [TextViewUtils.setImeOptions]。
 * </pre>
 *
 * @param imeOptions IME 选项标志
 */
@BindingAdapter("binding_tv_ime_options")
fun TextView.bindingTVImeOptions(imeOptions: Int?) {
    if (imeOptions == null) return
    TextViewUtils.setImeOptions(this, imeOptions)
}

/**
 * 通过数据绑定设置固定行数。
 * <pre>
 *     布局属性 `binding_tv_lines`；`null` 不修改；委托 [TextViewUtils.setLines]。
 * </pre>
 *
 * @param lines 行数
 */
@BindingAdapter("binding_tv_lines")
fun TextView.bindingTVLines(lines: Int?) {
    if (lines == null) return
    TextViewUtils.setLines(this, lines)
}

/**
 * 通过数据绑定设置最大行数。
 * <pre>
 *     布局属性 `binding_tv_max_lines`；`null` 不修改；委托 [TextViewUtils.setMaxLines]。
 * </pre>
 *
 * @param maxLines 最大行数
 */
@BindingAdapter("binding_tv_max_lines")
fun TextView.bindingTVMaxLines(maxLines: Int?) {
    if (maxLines == null) return
    TextViewUtils.setMaxLines(this, maxLines)
}

/**
 * 通过数据绑定设置最小行数。
 * <pre>
 *     布局属性 `binding_tv_min_lines`；`null` 不修改；委托 [TextViewUtils.setMinLines]。
 * </pre>
 *
 * @param minLines 最小行数
 */
@BindingAdapter("binding_tv_min_lines")
fun TextView.bindingTVMinLines(minLines: Int?) {
    if (minLines == null) return
    TextViewUtils.setMinLines(this, minLines)
}

/**
 * 通过数据绑定设置最大 ems 宽度。
 * <pre>
 *     布局属性 `binding_tv_max_ems`；`null` 不修改；委托 [TextViewUtils.setMaxEms]。
 * </pre>
 *
 * @param maxEms 最大字符宽度
 */
@BindingAdapter("binding_tv_max_ems")
fun TextView.bindingTVMaxEms(maxEms: Int?) {
    if (maxEms == null) return
    TextViewUtils.setMaxEms(this, maxEms)
}

/**
 * 通过数据绑定设置最小 ems 宽度。
 * <pre>
 *     布局属性 `binding_tv_min_ems`；`null` 不修改；委托 [TextViewUtils.setMinEms]。
 * </pre>
 *
 * @param minEms 最小字符宽度
 */
@BindingAdapter("binding_tv_min_ems")
fun TextView.bindingTVMinEms(minEms: Int?) {
    if (minEms == null) return
    TextViewUtils.setMinEms(this, minEms)
}

/**
 * 通过数据绑定设置固定 ems 宽度。
 * <pre>
 *     布局属性 `binding_tv_ems`；`null` 不修改；委托 [TextViewUtils.setEms]。
 * </pre>
 *
 * @param ems 字符宽度
 */
@BindingAdapter("binding_tv_ems")
fun TextView.bindingTVEms(ems: Int?) {
    if (ems == null) return
    TextViewUtils.setEms(this, ems)
}

/**
 * 通过数据绑定设置最大长度过滤。
 * <pre>
 *     布局属性 `binding_tv_max_length`；`null` 不修改；委托 [TextViewUtils.setMaxLength]。
 * </pre>
 *
 * @param maxLength 最大长度
 */
@BindingAdapter("binding_tv_max_length")
fun TextView.bindingTVMaxLength(maxLength: Int?) {
    if (maxLength == null) return
    TextViewUtils.setMaxLength(this, maxLength)
}

/**
 * 通过数据绑定同时设置最大长度与正文。
 * <pre>
 *     布局属性 `binding_tv_max_length_and_text`；`null` 跳过；委托 [TextViewUtils.setMaxLengthAndText]。
 * </pre>
 *
 * @param value 长度与正文
 */
@BindingAdapter("binding_tv_max_length_and_text")
fun TextView.bindingTVMaxLengthAndText(value: TvMaxLengthAndText?) {
    if (value == null) return
    TextViewUtils.setMaxLengthAndText(this, value.content, value.maxLength)
}

/**
 * 通过数据绑定设置省略号位置。
 * <pre>
 *     布局属性 `binding_tv_ellipsize`；`null` 不修改；委托 [TextViewUtils.setEllipsize]。
 * </pre>
 *
 * @param where 截断模式
 */
@BindingAdapter("binding_tv_ellipsize")
fun TextView.bindingTVEllipsize(where: TextUtils.TruncateAt?) {
    if (where == null) return
    TextViewUtils.setEllipsize(this, where)
}

/**
 * 通过数据绑定设置自动链接掩码。
 * <pre>
 *     布局属性 `binding_tv_auto_link_mask`；`null` 不修改；委托 [TextViewUtils.setAutoLinkMask]。
 * </pre>
 *
 * @param mask 链接识别掩码
 */
@BindingAdapter("binding_tv_auto_link_mask")
fun TextView.bindingTVAutoLinkMask(mask: Int?) {
    if (mask == null) return
    TextViewUtils.setAutoLinkMask(this, mask)
}

/**
 * 通过数据绑定设置是否全大写展示。
 * <pre>
 *     布局属性 `binding_tv_all_caps`；`null` 不修改；委托 [TextViewUtils.setAllCaps]。
 * </pre>
 *
 * @param allCaps 是否全大写
 */
@BindingAdapter("binding_tv_all_caps")
fun TextView.bindingTVAllCaps(allCaps: Boolean?) {
    if (allCaps == null) return
    TextViewUtils.setAllCaps(this, allCaps)
}

/**
 * 通过数据绑定设置文本对齐与重力。
 * <pre>
 *     布局属性 `binding_tv_gravity`；`null` 不修改；委托 [TextViewUtils.setGravity]。
 * </pre>
 *
 * @param gravity [android.view.Gravity] 标志组合
 */
@BindingAdapter("binding_tv_gravity")
fun TextView.bindingTVGravity(gravity: Int?) {
    if (gravity == null) return
    TextViewUtils.setGravity(this, gravity)
}

/**
 * 通过数据绑定设置转换方法实例。
 * <pre>
 *     布局属性 `binding_tv_transformation_method`；`null` 不修改；委托 [TextViewUtils.setTransformationMethod]。
 *     与 `binding_tv_password_visible` 二选一即可，避免同轮绑定互相覆盖。
 * </pre>
 *
 * @param method 转换方法
 */
@BindingAdapter("binding_tv_transformation_method")
fun TextView.bindingTVTransformationMethod(method: TransformationMethod?) {
    if (method == null) return
    TextViewUtils.setTransformationMethod(this, method)
}

/**
 * 通过数据绑定切换密码明文与密文显示（内置两种 [TransformationMethod]）。
 * <pre>
 *     布局属性 `binding_tv_password_visible`；`null` 不修改；委托 [TextViewUtils.setTransformationMethod]。
 * </pre>
 *
 * @param visible 是否显示明文
 */
@BindingAdapter("binding_tv_password_visible")
fun TextView.bindingTVPasswordVisible(visible: Boolean?) {
    if (visible == null) return
    TextViewUtils.setTransformationMethod(this, visible)
}

// ====================
// = CompoundDrawable =
// ====================

/**
 * 通过数据绑定设置四周 drawable 与文字间距。
 * <pre>
 *     布局属性 `binding_tv_compound_drawable_padding`；`null` 不修改；委托 [TextViewUtils.setCompoundDrawablePadding]。
 * </pre>
 *
 * @param padding 像素间距
 */
@BindingAdapter("binding_tv_compound_drawable_padding")
fun TextView.bindingTVCompoundDrawablePadding(padding: Int?) {
    if (padding == null) return
    TextViewUtils.setCompoundDrawablePadding(this, padding)
}

/**
 * 通过数据绑定设置四周 compound drawable（须已 bounds 或接受系统行为）。
 * <pre>
 *     布局属性 `binding_tv_compound_drawables`；`null` 跳过；委托 [TextViewUtils.setCompoundDrawables]。
 * </pre>
 *
 * @param four 左上下右 drawable
 */
@BindingAdapter("binding_tv_compound_drawables")
fun TextView.bindingTVCompoundDrawables(four: TvCompoundDrawablesFour?) {
    if (four == null) return
    TextViewUtils.setCompoundDrawables(this, four.left, four.top, four.right, four.bottom)
}

/**
 * 通过数据绑定按固有尺寸设置四周 compound drawable。
 * <pre>
 *     布局属性 `binding_tv_compound_drawables_intrinsic`；`null` 跳过；委托 [TextViewUtils.setCompoundDrawablesWithIntrinsicBounds]。
 * </pre>
 *
 * @param four 左上下右 drawable
 */
@BindingAdapter("binding_tv_compound_drawables_intrinsic")
fun TextView.bindingTVCompoundDrawablesIntrinsic(four: TvCompoundDrawablesFour?) {
    if (four == null) return
    TextViewUtils.setCompoundDrawablesWithIntrinsicBounds(
        this,
        four.left,
        four.top,
        four.right,
        four.bottom
    )
}

/**
 * 通过数据绑定仅设置左侧 compound drawable。
 * <pre>
 *     布局属性 `binding_tv_compound_left`；`null` 不修改；委托 [TextViewUtils.setCompoundDrawablesByLeft]。
 *     其余三边置空；与 `binding_tv_compound_drawables` 四边同绑二选一，避免同轮覆盖。
 * </pre>
 *
 * @param left 左侧 drawable
 */
@BindingAdapter("binding_tv_compound_left")
fun TextView.bindingTVCompoundLeft(left: Drawable?) {
    if (left == null) return
    TextViewUtils.setCompoundDrawablesByLeft(this, left)
}

/**
 * 通过数据绑定仅设置顶部 compound drawable。
 * <pre>
 *     布局属性 `binding_tv_compound_top`；`null` 不修改；委托 [TextViewUtils.setCompoundDrawablesByTop]。
 * </pre>
 *
 * @param top 顶部 drawable
 */
@BindingAdapter("binding_tv_compound_top")
fun TextView.bindingTVCompoundTop(top: Drawable?) {
    if (top == null) return
    TextViewUtils.setCompoundDrawablesByTop(this, top)
}

/**
 * 通过数据绑定仅设置右侧 compound drawable。
 * <pre>
 *     布局属性 `binding_tv_compound_right`；`null` 不修改；委托 [TextViewUtils.setCompoundDrawablesByRight]。
 * </pre>
 *
 * @param right 右侧 drawable
 */
@BindingAdapter("binding_tv_compound_right")
fun TextView.bindingTVCompoundRight(right: Drawable?) {
    if (right == null) return
    TextViewUtils.setCompoundDrawablesByRight(this, right)
}

/**
 * 通过数据绑定仅设置底部 compound drawable。
 * <pre>
 *     布局属性 `binding_tv_compound_bottom`；`null` 不修改；委托 [TextViewUtils.setCompoundDrawablesByBottom]。
 * </pre>
 *
 * @param bottom 底部 drawable
 */
@BindingAdapter("binding_tv_compound_bottom")
fun TextView.bindingTVCompoundBottom(bottom: Drawable?) {
    if (bottom == null) return
    TextViewUtils.setCompoundDrawablesByBottom(this, bottom)
}

/**
 * 通过数据绑定按资源 ID 仅设置左侧 compound drawable（固有尺寸）。
 * <pre>
 *     布局属性 `binding_tv_compound_left_res`；`null` 不修改；
 *     语义对齐 [TextViewUtils.setCompoundDrawablesWithIntrinsicBoundsByLeft]。
 *     与 `binding_tv_compound_left`（[Drawable]）二选一，避免同轮覆盖。
 * </pre>
 *
 * @param resId 左侧 drawable 资源 ID
 */
@BindingAdapter("binding_tv_compound_left_res")
fun TextView.bindingTVCompoundLeftRes(@DrawableRes resId: Int?) {
    if (resId == null) return
    setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0)
}

/**
 * 通过数据绑定按资源 ID 仅设置顶部 compound drawable（固有尺寸）。
 * <pre>
 *     布局属性 `binding_tv_compound_top_res`；`null` 不修改；
 *     语义对齐 [TextViewUtils.setCompoundDrawablesWithIntrinsicBoundsByTop]。
 * </pre>
 *
 * @param resId 顶部 drawable 资源 ID
 */
@BindingAdapter("binding_tv_compound_top_res")
fun TextView.bindingTVCompoundTopRes(@DrawableRes resId: Int?) {
    if (resId == null) return
    setCompoundDrawablesWithIntrinsicBounds(0, resId, 0, 0)
}

/**
 * 通过数据绑定按资源 ID 仅设置右侧 compound drawable（固有尺寸）。
 * <pre>
 *     布局属性 `binding_tv_compound_right_res`；`null` 不修改；
 *     语义对齐 [TextViewUtils.setCompoundDrawablesWithIntrinsicBoundsByRight]。
 * </pre>
 *
 * @param resId 右侧 drawable 资源 ID
 */
@BindingAdapter("binding_tv_compound_right_res")
fun TextView.bindingTVCompoundRightRes(@DrawableRes resId: Int?) {
    if (resId == null) return
    setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0)
}

/**
 * 通过数据绑定按资源 ID 仅设置底部 compound drawable（固有尺寸）。
 * <pre>
 *     布局属性 `binding_tv_compound_bottom_res`；`null` 不修改；
 *     语义对齐 [TextViewUtils.setCompoundDrawablesWithIntrinsicBoundsByBottom]。
 * </pre>
 *
 * @param resId 底部 drawable 资源 ID
 */
@BindingAdapter("binding_tv_compound_bottom_res")
fun TextView.bindingTVCompoundBottomRes(@DrawableRes resId: Int?) {
    if (resId == null) return
    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, resId)
}

/**
 * 通过数据绑定按资源 ID 设置四周 compound drawable（固有尺寸）。
 * <pre>
 *     布局属性 `binding_tv_compound_drawables_intrinsic_res`；`null` 跳过；
 *     语义对齐 [TextViewUtils.setCompoundDrawablesWithIntrinsicBounds]。
 * </pre>
 *
 * @param four 左上下右资源 ID，未指定边为 0
 */
@BindingAdapter("binding_tv_compound_drawables_intrinsic_res")
fun TextView.bindingTVCompoundDrawablesIntrinsicRes(four: TvCompoundDrawablesFourRes?) {
    if (four == null) return
    setCompoundDrawablesWithIntrinsicBounds(
        four.left,
        four.top,
        four.right,
        four.bottom,
    )
}

// =============
// = 自动调整字号 =
// =============

/**
 * 通过数据绑定设置自动调整字号类型（默认配置）。
 * <pre>
 *     布局属性 `binding_tv_auto_size_text_type_with_defaults`；`null` 不修改；委托 [TextViewUtils.setAutoSizeTextTypeWithDefaults]。
 * </pre>
 *
 * @param autoSizeTextType [TextViewCompat.AutoSizeTextType] 取值
 */
@BindingAdapter("binding_tv_auto_size_text_type_with_defaults")
fun TextView.bindingTVAutoSizeTextTypeWithDefaults(autoSizeTextType: Int?) {
    if (autoSizeTextType == null) return
    TextViewUtils.setAutoSizeTextTypeWithDefaults(this, autoSizeTextType)
}

/**
 * 通过数据绑定设置均匀自动调整字号参数。
 * <pre>
 *     布局属性 `binding_tv_auto_size_uniform_config`；`null` 跳过；委托 [TextViewUtils.setAutoSizeTextTypeUniformWithConfiguration]。
 * </pre>
 *
 * @param config 最小、最大、步进与单位
 */
@BindingAdapter("binding_tv_auto_size_uniform_config")
fun TextView.bindingTVAutoSizeUniformConfig(config: TvAutoSizeUniformConfiguration?) {
    if (config == null) return
    TextViewUtils.setAutoSizeTextTypeUniformWithConfiguration(
        this,
        config.minTextSize,
        config.maxTextSize,
        config.stepGranularity,
        config.unit,
    )
}

/**
 * 通过数据绑定按预设字号表启用均匀自动调整。
 * <pre>
 *     布局属性 `binding_tv_auto_size_preset_sizes`；`null` 跳过；委托 [TextViewUtils.setAutoSizeTextTypeUniformWithPresetSizes]。
 * </pre>
 *
 * @param value 预设尺寸数组与单位
 */
@BindingAdapter("binding_tv_auto_size_preset_sizes")
fun TextView.bindingTVAutoSizePresetSizes(value: TvAutoSizePresetSizes?) {
    if (value == null) return
    TextViewUtils.setAutoSizeTextTypeUniformWithPresetSizes(this, value.presetSizes, value.unit)
}