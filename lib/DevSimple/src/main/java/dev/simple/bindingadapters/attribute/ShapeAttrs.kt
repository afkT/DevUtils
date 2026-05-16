package dev.simple.bindingadapters.attribute

import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt

/**
 * 圆角与纯色背景，对应 [dev.utils.app.ShapeUtils.newShape] 双参工厂。
 * <pre>
 *     与布局属性 `binding_view_shape_radius_color` 搭配。
 * </pre>
 */
data class ViewShapeRadiusColor(
    val radius: Float,
    @ColorInt val color: Int,
)

/**
 * 四角圆角，对应 [dev.utils.app.ShapeUtils.setCornerRadius] 四参重载。
 * <pre>
 *     与布局属性 `binding_view_shape_corners` 搭配；顺序为左上、右上、右下、左下。
 * </pre>
 */
data class ViewShapeCornerRadii(
    val leftTop: Float,
    val rightTop: Float,
    val rightBottom: Float,
    val leftBottom: Float,
)

/**
 * 描边，对应 [dev.utils.app.ShapeUtils.setStroke] 四参重载。
 * <pre>
 *     与布局属性 `binding_view_shape_stroke` 搭配；`dashWidth` 为 0 表示实线。
 * </pre>
 */
data class ViewShapeStroke(
    val width: Int,
    @ColorInt val color: Int,
    val dashWidth: Float = 0f,
    val dashGap: Float = 0f,
)

/**
 * 按角度与颜色数组创建渐变 Shape，对应 [dev.utils.app.ShapeUtils.newShape] 角度工厂。
 * <pre>
 *     与布局属性 `binding_view_shape_gradient` 搭配；`angle` 须为 45 的整数倍。
 * </pre>
 */
data class ViewShapeGradient(
    val angle: Int,
    @ColorInt val colors: IntArray,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ViewShapeGradient
        if (angle != other.angle) return false
        if (!colors.contentEquals(other.colors)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = angle
        result = 31 * result + colors.contentHashCode()
        return result
    }
}

/**
 * 按 [GradientDrawable.Orientation] 与颜色数组创建渐变 Shape。
 * <pre>
 *     与布局属性 `binding_view_shape_gradient_orient` 搭配。
 * </pre>
 */
data class ViewShapeGradientOrient(
    val orientation: GradientDrawable.Orientation,
    @ColorInt val colors: IntArray,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ViewShapeGradientOrient
        if (orientation != other.orientation) return false
        if (!colors.contentEquals(other.colors)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = orientation.hashCode()
        result = 31 * result + colors.contentHashCode()
        return result
    }
}

/**
 * Shape drawable 固有尺寸，对应 [dev.utils.app.ShapeUtils.setSize]。
 */
data class ViewShapeSize(
    val width: Int,
    val height: Int,
)

/**
 * 渐变扩展参数，与已有 [ViewShapeGradient] 或 [ViewShapeGradientOrient] 构建结果叠加使用。
 * <pre>
 *     供 [ViewShapeSpec] 内可选字段；单独绑定时请优先使用 `binding_view_shape_gradient` 等专用属性。
 * </pre>
 */
data class ViewShapeGradientExtra(
    val gradientType: Int? = null,
    val angle: Int? = null,
    val orientation: GradientDrawable.Orientation? = null,
    @ColorInt val colors: IntArray? = null,
    val centerX: Float? = null,
    val centerY: Float? = null,
    val gradientRadius: Float? = null,
    val useLevel: Boolean? = null,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ViewShapeGradientExtra
        if (gradientType != other.gradientType) return false
        if (angle != other.angle) return false
        if (orientation != other.orientation) return false
        if (colors != null) {
            if (other.colors == null) return false
            if (!colors.contentEquals(other.colors)) return false
        } else if (other.colors != null) {
            return false
        }
        if (centerX != other.centerX) return false
        if (centerY != other.centerY) return false
        if (gradientRadius != other.gradientRadius) return false
        if (useLevel != other.useLevel) return false
        return true
    }

    override fun hashCode(): Int {
        var result = gradientType ?: 0
        result = 31 * result + (angle ?: 0)
        result = 31 * result + (orientation?.hashCode() ?: 0)
        result = 31 * result + (colors?.contentHashCode() ?: 0)
        result = 31 * result + (centerX?.hashCode() ?: 0)
        result = 31 * result + (centerY?.hashCode() ?: 0)
        result = 31 * result + (gradientRadius?.hashCode() ?: 0)
        result = 31 * result + (useLevel?.hashCode() ?: 0)
        return result
    }
}

/**
 * 声明式 Shape 描述：在适配器内链式配置后 [dev.utils.app.ShapeUtils.setDrawable]。
 * <pre>
 *     与布局属性 `binding_view_shape_spec` 搭配；字段均为可选，按非 null 依次应用。
 *     未封装 [android.content.res.ColorStateList] 描边/填充、环形 innerRadius/thickness（API 29+）等须在 ViewModel 中用 [dev.utils.app.ShapeUtils] 构建后绑 `binding_view_shape_utils`。
 *     与 `binding_view_shape_utils`、`binding_view_shape_radius_color` 等同节点其它 shape 属性二选一，避免同轮覆盖。
 * </pre>
 */
data class ViewShapeSpec(
    val shape: Int? = null,
    val alpha: Int? = null,
    @ColorInt val color: Int? = null,
    val cornerRadius: Float? = null,
    val corners: ViewShapeCornerRadii? = null,
    val cornerRadiusLeft: Float? = null,
    val cornerRadiusRight: Float? = null,
    val cornerRadiusTop: Float? = null,
    val cornerRadiusBottom: Float? = null,
    val stroke: ViewShapeStroke? = null,
    val gradient: ViewShapeGradient? = null,
    val gradientOrient: ViewShapeGradientOrient? = null,
    val gradientExtra: ViewShapeGradientExtra? = null,
    val size: ViewShapeSize? = null,
    val padding: Paddings? = null,
)