package dev.simple.bindingadapters.attribute

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

/**
 * 引用线样式，用于 [dev.utils.app.SpanUtils.setQuoteColor] 三参重载。
 * <pre>
 *     与布局属性 `binding_tv_span_segments` 内 [TvSpanSegment.quote] 搭配。
 * </pre>
 */
data class TvSpanQuote(
    @ColorInt val color: Int,
    val stripeWidth: Int = 2,
    val gapWidth: Int = 2,
)

/**
 * 段落缩进，用于 [dev.utils.app.SpanUtils.setLeadingMargin]。
 * <pre>
 *     与 `binding_tv_span_segments` 内 [TvSpanSegment.leadingMargin] 搭配。
 * </pre>
 */
data class TvSpanLeadingMargin(
    val first: Int,
    val rest: Int,
)

/**
 * 列表圆点样式，用于 [dev.utils.app.SpanUtils.setBullet] 三参重载。
 * <pre>
 *     与 `binding_tv_span_segments` 内 [TvSpanSegment.bullet] 搭配；`color` 为 0 时与工具类默认一致。
 * </pre>
 */
data class TvSpanBullet(
    @ColorInt val color: Int = 0,
    val radius: Int = 3,
    val gapWidth: Int,
)

/**
 * 绝对字号，用于 [dev.utils.app.SpanUtils.setFontSize]。
 */
data class TvSpanFontSize(
    val size: Int,
    val isDp: Boolean = false,
)

/**
 * 行高与行内对齐，用于 [dev.utils.app.SpanUtils.setLineHeight] 双参重载。
 * <pre>
 *     `align` 取 [dev.utils.app.SpanUtils.ALIGN_TOP]、[ALIGN_CENTER]、[ALIGN_BOTTOM] 等常量。
 * </pre>
 */
data class TvSpanLineHeight(
    val lineHeight: Int,
    val align: Int,
)

/**
 * 文字阴影，用于 [dev.utils.app.SpanUtils.setShadow]。
 */
data class TvSpanShadow(
    val radius: Float,
    val dx: Float = 0f,
    val dy: Float = 0f,
    @ColorInt val color: Int,
)

/**
 * 占位空格，用于 [dev.utils.app.SpanUtils.appendSpace]。
 */
data class TvSpanSpace(
    val size: Int,
    @ColorInt val color: Int = Color.TRANSPARENT,
)

/**
 * 行内图片，用于 [dev.utils.app.SpanUtils.appendImage]。
 * <pre>
 *     `resourceId` 与 `drawable` 二选一；`align` 默认 [dev.utils.app.SpanUtils.ALIGN_BOTTOM]。
 * </pre>
 */
data class TvSpanImage(
    @DrawableRes val resourceId: Int? = null,
    val drawable: Drawable? = null,
    val align: Int = dev.utils.app.SpanUtils.ALIGN_BOTTOM,
)

/**
 * 单段富文本描述：先应用段内样式字段，再追加文本、换行、空格或图片。
 * <pre>
 *     与布局属性 `binding_tv_span_segments` 搭配；由适配器按序委托 [dev.utils.app.SpanUtils] 链式 API。
 *     未封装 [dev.utils.app.SpanUtils.setClickSpan]、[setShader]、[setBlur]、[setTypeface]、
 *     [setSpans]、[appendImage] 的 Bitmap/Uri 等无法在 Binding 中稳定传递的类型。
 * </pre>
 */
data class TvSpanSegment(
    val text: CharSequence? = null,
    val appendLine: Boolean = false,
    val flag: Int? = null,
    @ColorInt val foregroundColor: Int? = null,
    @ColorInt val backgroundColor: Int? = null,
    val lineHeight: TvSpanLineHeight? = null,
    val quote: TvSpanQuote? = null,
    val leadingMargin: TvSpanLeadingMargin? = null,
    val bullet: TvSpanBullet? = null,
    val fontSize: TvSpanFontSize? = null,
    val proportion: Float? = null,
    val xProportion: Float? = null,
    val strikethrough: Boolean = false,
    val underline: Boolean = false,
    val superscript: Boolean = false,
    val subscript: Boolean = false,
    val bold: Boolean = false,
    val italic: Boolean = false,
    val boldItalic: Boolean = false,
    val fontFamily: String? = null,
    val url: String? = null,
    val verticalAlign: Int? = null,
    val shadow: TvSpanShadow? = null,
    val space: TvSpanSpace? = null,
    val image: TvSpanImage? = null,
)