package dev.simple.bindingadapters.attribute

import android.graphics.Typeface
import android.graphics.drawable.Drawable

/**
 * 行间距额外值与倍数，用于单次绑定调用 [dev.utils.app.TextViewUtils.setLineSpacingAndMultiplier]。
 * <pre>
 *     与布局属性 `binding_tv_line_spacing_extra_multiplier` 搭配；`multiplier` 默认 1。
 * </pre>
 *
 * @property lineSpacingExtra 行间距额外值，对应 `android:lineSpacingExtra` 语义
 * @property lineSpacingMultiplier 行间距倍数，对应 `android:lineSpacingMultiplier` 语义
 */
data class TvLineSpacingExtraMultiplier(
    val lineSpacingExtra: Float,
    val lineSpacingMultiplier: Float = 1f,
)

/**
 * 最大长度与待设正文，用于单次绑定调用 [dev.utils.app.TextViewUtils.setMaxLengthAndText]。
 * <pre>
 *     与布局属性 `binding_tv_max_length_and_text` 搭配。
 * </pre>
 *
 * @property content 文本，可为空
 * @property maxLength 最大长度，须为正数方与工具类行为一致
 */
data class TvMaxLengthAndText(
    val content: CharSequence?,
    val maxLength: Int,
)

/**
 * [android.util.TypedValue] 单位与字号，用于单次绑定调用 [dev.utils.app.TextViewUtils.setTextSize]。
 * <pre>
 *     与布局属性 `binding_tv_text_size_unit_size` 搭配；`unit` 取 `TypedValue.COMPLEX_UNIT_*` 等常量。
 * </pre>
 *
 * @property unit 字号单位
 * @property size 字号数值
 */
data class TvTextSizeUnit(
    val unit: Int,
    val size: Float,
)

/**
 * 四周 [Drawable] 复合图标，用于 [dev.utils.app.TextViewUtils.setCompoundDrawables] 或
 * [dev.utils.app.TextViewUtils.setCompoundDrawablesWithIntrinsicBounds]。
 * <pre>
 *     与 `binding_tv_compound_drawables`、`binding_tv_compound_drawables_intrinsic` 搭配；
 *     各边可为空；若需 intrinsic 语义须使用后者属性。
 * </pre>
 */
data class TvCompoundDrawablesFour(
    val left: Drawable? = null,
    val top: Drawable? = null,
    val right: Drawable? = null,
    val bottom: Drawable? = null,
)

/**
 * 均匀自动调整字号配置，用于 [dev.utils.app.TextViewUtils.setAutoSizeTextTypeUniformWithConfiguration]。
 * <pre>
 *     与布局属性 `binding_tv_auto_size_uniform_config` 搭配。
 * </pre>
 */
data class TvAutoSizeUniformConfiguration(
    val minTextSize: Int,
    val maxTextSize: Int,
    val stepGranularity: Int,
    val unit: Int,
)

/**
 * 预设字号阶梯与单位，用于 [dev.utils.app.TextViewUtils.setAutoSizeTextTypeUniformWithPresetSizes]。
 * <pre>
 *     与布局属性 `binding_tv_auto_size_preset_sizes` 搭配。
 * </pre>
 *
 * @property presetSizes 预设像素尺寸数组
 * @property unit [android.util.TypedValue] 单位常量
 */
data class TvAutoSizePresetSizes(
    val presetSizes: IntArray,
    val unit: Int,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TvAutoSizePresetSizes
        if (unit != other.unit) return false
        if (!presetSizes.contentEquals(other.presetSizes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = unit
        result = 31 * result + presetSizes.contentHashCode()
        return result
    }
}

/**
 * 在指定 [Typeface] 上切换粗体样式，用于 [dev.utils.app.TextViewUtils.setBold] 三参重载。
 * <pre>
 *     与布局属性 `binding_tv_typeface_bold` 搭配。
 * </pre>
 *
 * @property typeface 字体实例
 * @property bold 是否粗体
 */
data class TvTypefaceBold(
    val typeface: Typeface,
    val bold: Boolean,
)