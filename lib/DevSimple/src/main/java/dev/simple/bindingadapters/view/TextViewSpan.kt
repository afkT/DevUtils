package dev.simple.bindingadapters.view

import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.databinding.BindingAdapter
import dev.simple.bindingadapters.attribute.TvSpanImage
import dev.simple.bindingadapters.attribute.TvSpanSegment
import dev.simple.bindingadapters.qualifiesBindingAction
import dev.utils.LogPrintUtils
import dev.utils.app.SpanUtils
import dev.utils.app.TextViewUtils

// ================================
// = TextView Span BindingAdapter =
// ================================

/**
 * [TextView] 富文本（[dev.utils.app.SpanUtils]）的 Data Binding 适配集合。
 *
 * 布局属性前缀为 `binding_tv_span_*`；实现委托 [SpanUtils] 及 [TextViewUtils.setText]。
 * <pre>
 *     推荐在 ViewModel 中用无参 [SpanUtils] 链式构建后，绑定 [CharSequence]（`binding_tv_span`）
 *     或 [SpannableStringBuilder]（`binding_tv_span_builder`）。
 *     未封装 [SpanUtils.with] 持有其它 [TextView] 的实例、ClickableSpan/Shader/Blur/Typeface、
 *     Bitmap/Uri 图片源、[setSpans] 自定义 span 数组等不适合布局单节点表达的 API。
 *     多段样式列表可用 `binding_tv_span_segments` 与 [TvSpanSegment]；
 *     含链接片段时配合 `binding_tv_span_link_movement`。
 *     同一 [SpanUtils] 引用需再次刷 UI 时使用 `binding_tv_span_utils_ts` 与正时间戳。
 *     VM 产物：`binding_tv_span_char`（[CharSequence] 写入/清空）、`binding_tv_span_utils_run`（[SpanUtils] 应用/清空正文）。
 * </pre>
 */

private const val TAG = "Dev_TextViewSpan_BindingAdapter"

// ========================
// = 通用 VM 产物（应用/结束）=
// ========================

/**
 * 通过数据绑定设置或清空富文本 [CharSequence]。
 * <pre>
 *     布局属性 `binding_tv_span_char`；非 null 时 [TextViewUtils.setText]；null 时写入空串清空。
 *     与 `binding_tv_span`（null 跳过）并存。
 * </pre>
 *
 * @param span 富文本内容，null 表示清空
 */
@BindingAdapter("binding_tv_span_char")
fun TextView.bindingTVSpanChar(span: CharSequence?) {
    TextViewUtils.setText(this, span ?: "")
}

/**
 * 通过数据绑定应用 [SpanUtils] 或清空正文。
 * <pre>
 *     布局属性 `binding_tv_span_utils_run`；非 null 时 [SpanUtils.create] 写入；null 时清空正文。
 *     与 `binding_tv_span_utils`（null 跳过）并存。
 * </pre>
 *
 * @param utils 已配置完成的构建器，null 表示清空
 */
@BindingAdapter("binding_tv_span_utils_run")
fun TextView.bindingTVSpanUtilsRun(utils: SpanUtils?) {
    if (utils == null) {
        TextViewUtils.setText(this, "")
    } else {
        applySpanUtils(utils)
    }
}

// =============
// = 结果直接绑定 =
// =============

/**
 * 通过数据绑定设置 [SpanUtils] 构建得到的富文本。
 * <pre>
 *     布局属性 `binding_tv_span`；`null` 时不修改；委托 [TextViewUtils.setText]。
 *     ViewModel 示例：`SpanUtils().append("a").setBold().create()` 的返回值。
 * </pre>
 *
 * @param span 富文本内容
 */
@BindingAdapter("binding_tv_span")
fun TextView.bindingTVSpan(span: CharSequence?) {
    if (span == null) return
    TextViewUtils.setText(this, span)
}

/**
 * 通过数据绑定设置 [SpannableStringBuilder]。
 * <pre>
 *     布局属性 `binding_tv_span_builder`；`null` 时不修改；
 *     对应 [SpanUtils.get] 或 [SpanUtils.create] 返回的 builder，直接赋给 [TextView.setText]。
 * </pre>
 *
 * @param builder 可编辑 span 构建器
 */
@BindingAdapter("binding_tv_span_builder")
fun TextView.bindingTVSpanBuilder(builder: SpannableStringBuilder?) {
    if (builder == null) return
    text = builder
}

// =================
// = SpanUtils 应用 =
// =================

/**
 * 通过数据绑定执行 [SpanUtils.create] 并写入当前 [TextView]。
 * <pre>
 *     布局属性 `binding_tv_span_utils`；`null` 时跳过；
 *     请绑定无参 [SpanUtils()] 在 ViewModel 中配置完成的实例，勿复用已 [SpanUtils.with] 其它控件的实例。
 * </pre>
 *
 * @param utils 已配置完成的构建器
 */
@BindingAdapter("binding_tv_span_utils")
fun TextView.bindingTVSpanUtils(utils: SpanUtils?) {
    if (utils == null) return
    applySpanUtils(utils)
}

/**
 * 通过数据绑定在时间戳有效时再次执行 [SpanUtils.create]。
 * <pre>
 *     布局属性 `binding_tv_span_utils`、`binding_tv_span_utils_ts`（requireAll 为 false）；
 *     仅绑 utils 时与 [bindingTVSpanUtils] 相同；同时绑递增时间戳可在引用不变时再次刷 UI。
 *     时间戳判定同 [qualifiesBindingAction]。
 * </pre>
 *
 * @param utils 已配置完成的构建器
 * @param timestamp 可选触发时间戳
 */
@BindingAdapter(
    value = [
        "binding_tv_span_utils",
        "binding_tv_span_utils_ts",
    ],
    requireAll = false,
)
fun TextView.bindingTVSpanUtilsTs(
    utils: SpanUtils?,
    timestamp: Long?,
) {
    if (utils == null) return
    if (!timestamp.qualifiesBindingAction()) return
    applySpanUtils(utils)
}

/**
 * 通过数据绑定为链接类 span 启用或清除 [LinkMovementMethod]。
 * <pre>
 *     布局属性 `binding_tv_span_link_movement`；`null` 不修改；
 *     `true` 时在 [movementMethod] 为空时设为 [LinkMovementMethod.getInstance]（与 [SpanUtils.setUrl] 行为一致），
 *     `false` 时清除 [movementMethod]。
 * </pre>
 *
 * @param enable 是否允许链接点击
 */
@BindingAdapter("binding_tv_span_link_movement")
fun TextView.bindingTVSpanLinkMovement(enable: Boolean?) {
    if (enable == null) return
    if (enable) {
        if (movementMethod == null) {
            movementMethod = LinkMovementMethod.getInstance()
        }
    } else {
        movementMethod = null
    }
}

// =============
// = 分段列表构建 =
// =============

/**
 * 通过数据绑定按 [TvSpanSegment] 列表构建富文本。
 * <pre>
 *     布局属性 `binding_tv_span_segments`；`null` 或空列表时跳过；
 *     内部使用 [SpanUtils.with] 当前 [TextView] 依次 [applySpanSegment] 后 [SpanUtils.create]。
 * </pre>
 *
 * @param segments 分段样式与内容
 */
@BindingAdapter("binding_tv_span_segments")
fun TextView.bindingTVSpanSegments(segments: List<TvSpanSegment>?) {
    if (segments.isNullOrEmpty()) return
    try {
        val utils = SpanUtils.with(this)
        for (segment in segments) {
            applySpanSegment(utils, segment)
        }
        utils.create()
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "bindingTVSpanSegments")
    }
}

// ==========
// = 内部实现 =
// ==========

private fun TextView.applySpanUtils(utils: SpanUtils) {
    try {
        text = utils.create()
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "applySpanUtils")
    }
}

private fun applySpanSegment(
    utils: SpanUtils,
    segment: TvSpanSegment,
) {
    segment.flag?.let { utils.setFlag(it) }
    segment.foregroundColor?.let { utils.setForegroundColor(it) }
    segment.backgroundColor?.let { utils.setBackgroundColor(it) }
    segment.lineHeight?.let { lh ->
        utils.setLineHeight(lh.lineHeight, lh.align)
    }
    segment.quote?.let { q ->
        utils.setQuoteColor(q.color, q.stripeWidth, q.gapWidth)
    }
    segment.leadingMargin?.let { m ->
        utils.setLeadingMargin(m.first, m.rest)
    }
    segment.bullet?.let { b ->
        utils.setBullet(b.color, b.radius, b.gapWidth)
    }
    segment.fontSize?.let { fs ->
        utils.setFontSize(fs.size, fs.isDp)
    }
    segment.proportion?.let { utils.setFontProportion(it) }
    segment.xProportion?.let { utils.setFontXProportion(it) }
    if (segment.strikethrough) utils.setStrikethrough()
    if (segment.underline) utils.setUnderline()
    if (segment.superscript) utils.setSuperscript()
    if (segment.subscript) utils.setSubscript()
    if (segment.bold) utils.setBold()
    if (segment.italic) utils.setItalic()
    if (segment.boldItalic) utils.setBoldItalic()
    segment.fontFamily?.let { utils.setFontFamily(it) }
    segment.url?.let { utils.setUrl(it) }
    segment.verticalAlign?.let { utils.setVerticalAlign(it) }
    segment.shadow?.let { s ->
        utils.setShadow(s.radius, s.dx, s.dy, s.color)
    }

    segment.space?.let { space ->
        utils.appendSpace(space.size, space.color)
        return
    }
    segment.image?.let { image ->
        appendSpanImage(utils, image)
        return
    }

    when {
        segment.appendLine && segment.text != null -> utils.appendLine(segment.text)
        segment.appendLine -> utils.appendLine()
        segment.text != null -> utils.append(segment.text)
    }
}

private fun appendSpanImage(
    utils: SpanUtils,
    image: TvSpanImage,
) {
    when {
        image.drawable != null -> utils.appendImage(image.drawable, image.align)
        image.resourceId != null -> utils.appendImage(image.resourceId!!, image.align)
    }
}
