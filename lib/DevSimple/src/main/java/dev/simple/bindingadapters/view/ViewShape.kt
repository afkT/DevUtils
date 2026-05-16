package dev.simple.bindingadapters.view

import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.View
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import dev.simple.bindingadapters.attribute.*
import dev.simple.bindingadapters.qualifiesBindingAction
import dev.utils.LogPrintUtils
import dev.utils.app.ShapeUtils
import dev.utils.app.ViewUtils

// ============================
// = View Shape BindingAdapter =
// ============================

/**
 * [View] 背景 Shape（[dev.utils.app.ShapeUtils]）的 Data Binding 适配集合。
 *
 * 布局属性前缀为 `binding_view_shape_*`；实现委托 [ShapeUtils.setDrawable] 写入背景。
 * <pre>
 *     推荐在 ViewModel 中链式配置 [ShapeUtils] 后绑定 `binding_view_shape_utils`；
 *     简单圆角纯色可用 `binding_view_shape_radius_color` 与 [ViewShapeRadiusColor]。
 *     未封装 [android.content.res.ColorStateList] 填充/描边、环形 innerRadius/thickness、
 *     仅 [GradientDrawable] 入参的 `newShape(GradientDrawable)` 等；复杂场景在 VM 构建实例后绑 utils。
 *     同一 [ShapeUtils] 引用需再次刷 UI 时使用 `binding_view_shape_utils_ts` 与正时间戳。
 *     VM 持有 [ShapeUtils] 产物时可用 `binding_view_shape`：非 null 应用、null 移除背景（[ViewUtils.removeBackground]）。
 *     与 `binding_view_background` 可并存但同轮后者可能覆盖；同节点 shape 相关属性宜二选一。
 * </pre>
 */

private const val TAG = "Dev_ViewShape_BindingAdapter"

// =================
// = 通用 ShapeUtils =
// =================

/**
 * 通过数据绑定应用或移除 [ShapeUtils] 背景。
 * <pre>
 *     布局属性 `binding_view_shape`；非 null 时 [ShapeUtils.setDrawable]；null 时 [ViewUtils.removeBackground]。
 *     与 `binding_view_shape_utils`（null 跳过）并存；需显式清除背景时绑本属性。
 * </pre>
 *
 * @param utils 已在 ViewModel 中配置完成的构建器，null 表示移除背景
 */
@BindingAdapter("binding_view_shape")
fun View.bindingViewShape(utils: ShapeUtils?) {
    if (utils == null) {
        ViewUtils.removeBackground(this)
    } else {
        applyShapeUtils(utils)
    }
}

// =================
// = ShapeUtils 应用 =
// =================

/**
 * 通过数据绑定将已配置的 [ShapeUtils] 设为背景。
 * <pre>
 *     布局属性 `binding_view_shape_utils`；`null` 时跳过；委托 [ShapeUtils.setDrawable]。
 * </pre>
 *
 * @param utils 已在 ViewModel 中配置完成的构建器
 */
@BindingAdapter("binding_view_shape_utils")
fun View.bindingViewShapeUtils(utils: ShapeUtils?) {
    if (utils == null) return
    applyShapeUtils(utils)
}

/**
 * 通过数据绑定在时间戳有效时再次执行 [ShapeUtils.setDrawable]。
 * <pre>
 *     布局属性 `binding_view_shape_utils`、`binding_view_shape_utils_ts`（requireAll 为 false）；
 *     仅绑 utils 时与 [bindingViewShapeUtils] 相同；同时绑递增时间戳可在引用不变时再次刷 UI。
 *     时间戳判定同 [qualifiesBindingAction]。
 * </pre>
 *
 * @param utils 已配置完成的构建器
 * @param timestamp 可选触发时间戳
 */
@BindingAdapter(
    value = [
        "binding_view_shape_utils",
        "binding_view_shape_utils_ts",
    ],
    requireAll = false,
)
fun View.bindingViewShapeUtilsTs(
    utils: ShapeUtils?,
    timestamp: Long?,
) {
    if (utils == null) return
    if (!timestamp.qualifiesBindingAction()) return
    applyShapeUtils(utils)
}

// ==========
// = 常用工厂 =
// ==========

/**
 * 通过数据绑定创建圆角纯色 Shape 并设为背景。
 * <pre>
 *     布局属性 `binding_view_shape_radius_color`；`null` 时跳过；
 *     委托 [ShapeUtils.newShape] 双参工厂与 [ShapeUtils.setDrawable]。
 * </pre>
 *
 * @param spec 圆角与背景色
 */
@BindingAdapter("binding_view_shape_radius_color")
fun View.bindingViewShapeRadiusColor(spec: ViewShapeRadiusColor?) {
    if (spec == null) return
    applyShapeUtils(ShapeUtils.newShape(spec.radius, spec.color))
}

/**
 * 通过数据绑定创建统一圆角 Shape 并设为背景。
 * <pre>
 *     布局属性 `binding_view_shape_corner_radius`；`null` 时跳过；
 *     委托 [ShapeUtils.newShape] 单参工厂。
 * </pre>
 *
 * @param radius 圆角像素值
 */
@BindingAdapter("binding_view_shape_corner_radius")
fun View.bindingViewShapeCornerRadius(radius: Float?) {
    if (radius == null) return
    applyShapeUtils(ShapeUtils.newShape(radius))
}

/**
 * 通过数据绑定按角度渐变创建 Shape 并设为背景。
 * <pre>
 *     布局属性 `binding_view_shape_gradient`；`null` 时跳过；
 *     委托 [ShapeUtils.newShape] 角度与颜色数组工厂。
 * </pre>
 *
 * @param gradient 渐变角度与颜色
 */
@BindingAdapter("binding_view_shape_gradient")
fun View.bindingViewShapeGradient(gradient: ViewShapeGradient?) {
    if (gradient == null) return
    applyShapeUtils(ShapeUtils.newShape(gradient.angle, gradient.colors))
}

/**
 * 通过数据绑定按 [GradientDrawable.Orientation] 渐变创建 Shape 并设为背景。
 * <pre>
 *     布局属性 `binding_view_shape_gradient_orient`；`null` 时跳过。
 * </pre>
 *
 * @param gradient 渐变方向与颜色
 */
@BindingAdapter("binding_view_shape_gradient_orient")
fun View.bindingViewShapeGradientOrient(gradient: ViewShapeGradientOrient?) {
    if (gradient == null) return
    applyShapeUtils(ShapeUtils.newShape(gradient.orientation, gradient.colors))
}

// ==========
// = 链式片段 =
// ==========

/**
 * 通过数据绑定设置四角圆角并应用为新背景 Shape。
 * <pre>
 *     布局属性 `binding_view_shape_corners`；`null` 时跳过；
 *     自 [ShapeUtils.newShape] 起仅应用圆角与 [setDrawable]。
 * </pre>
 *
 * @param corners 四角半径
 */
@BindingAdapter("binding_view_shape_corners")
fun View.bindingViewShapeCorners(corners: ViewShapeCornerRadii?) {
    if (corners == null) return
    applyShapeUtils(
        ShapeUtils.newShape().setCornerRadius(
            corners.leftTop,
            corners.rightTop,
            corners.rightBottom,
            corners.leftBottom,
        ),
    )
}

/**
 * 通过数据绑定设置左侧两角圆角。
 * <pre>
 *     布局属性 `binding_view_shape_corner_radius_left`；`null` 时跳过。
 * </pre>
 *
 * @param left 左侧圆角像素值
 */
@BindingAdapter("binding_view_shape_corner_radius_left")
fun View.bindingViewShapeCornerRadiusLeft(left: Float?) {
    if (left == null) return
    applyShapeUtils(ShapeUtils.newShape().setCornerRadiusLeft(left))
}

/**
 * 通过数据绑定设置右侧两角圆角。
 * <pre>
 *     布局属性 `binding_view_shape_corner_radius_right`；`null` 时跳过。
 * </pre>
 *
 * @param right 右侧圆角像素值
 */
@BindingAdapter("binding_view_shape_corner_radius_right")
fun View.bindingViewShapeCornerRadiusRight(right: Float?) {
    if (right == null) return
    applyShapeUtils(ShapeUtils.newShape().setCornerRadiusRight(right))
}

/**
 * 通过数据绑定设置顶部两角圆角。
 * <pre>
 *     布局属性 `binding_view_shape_corner_radius_top`；`null` 时跳过。
 * </pre>
 *
 * @param top 顶部圆角像素值
 */
@BindingAdapter("binding_view_shape_corner_radius_top")
fun View.bindingViewShapeCornerRadiusTop(top: Float?) {
    if (top == null) return
    applyShapeUtils(ShapeUtils.newShape().setCornerRadiusTop(top))
}

/**
 * 通过数据绑定设置底部两角圆角。
 * <pre>
 *     布局属性 `binding_view_shape_corner_radius_bottom`；`null` 时跳过。
 * </pre>
 *
 * @param bottom 底部圆角像素值
 */
@BindingAdapter("binding_view_shape_corner_radius_bottom")
fun View.bindingViewShapeCornerRadiusBottom(bottom: Float?) {
    if (bottom == null) return
    applyShapeUtils(ShapeUtils.newShape().setCornerRadiusBottom(bottom))
}

/**
 * 通过数据绑定设置描边并应用为新背景 Shape。
 * <pre>
 *     布局属性 `binding_view_shape_stroke`；`null` 时跳过。
 * </pre>
 *
 * @param stroke 描边宽度、颜色与虚线参数
 */
@BindingAdapter("binding_view_shape_stroke")
fun View.bindingViewShapeStroke(stroke: ViewShapeStroke?) {
    if (stroke == null) return
    val utils = ShapeUtils.newShape().setStroke(
        stroke.width,
        stroke.color,
        stroke.dashWidth,
        stroke.dashGap,
    )
    applyShapeUtils(utils)
}

/**
 * 通过数据绑定设置纯色填充并应用为新背景 Shape。
 * <pre>
 *     布局属性 `binding_view_shape_color`；`null` 时跳过；委托 [ShapeUtils.setColor]。
 * </pre>
 *
 * @param color 背景 ARGB
 */
@BindingAdapter("binding_view_shape_color")
fun View.bindingViewShapeColor(@ColorInt color: Int?) {
    if (color == null) return
    applyShapeUtils(ShapeUtils.newShape().setColor(color))
}

/**
 * 通过数据绑定设置 Shape 透明度并应用为背景。
 * <pre>
 *     布局属性 `binding_view_shape_alpha`；`null` 时跳过；委托 [ShapeUtils.setAlpha]。
 * </pre>
 *
 * @param alpha 透明度 0–255
 */
@BindingAdapter("binding_view_shape_alpha")
fun View.bindingViewShapeAlpha(alpha: Int?) {
    if (alpha == null) return
    applyShapeUtils(ShapeUtils.newShape().setAlpha(alpha))
}

/**
 * 通过数据绑定设置 Shape drawable 固有尺寸。
 * <pre>
 *     布局属性 `binding_view_shape_size`；`null` 时跳过；委托 [ShapeUtils.setSize]。
 * </pre>
 *
 * @param size 宽与高（像素）
 */
@BindingAdapter("binding_view_shape_size")
fun View.bindingViewShapeSize(size: ViewShapeSize?) {
    if (size == null) return
    applyShapeUtils(ShapeUtils.newShape().setSize(size.width, size.height))
}

/**
 * 通过数据绑定设置 Shape drawable 内边距（API 29+）。
 * <pre>
 *     布局属性 `binding_view_shape_padding`；`null` 时跳过；低版本忽略。
 *     复用 [Paddings] 四边语义，委托 [ShapeUtils.setPadding]。
 * </pre>
 *
 * @param paddings 左、上、右、下内边距
 */
@BindingAdapter("binding_view_shape_padding")
fun View.bindingViewShapePadding(paddings: Paddings?) {
    if (paddings == null) return
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return
    applyShapeUtils(
        ShapeUtils.newShape().setPadding(
            paddings.left,
            paddings.top,
            paddings.right,
            paddings.bottom,
        ),
    )
}

// ============
// = 声明式合并 =
// ============

/**
 * 通过数据绑定按 [ViewShapeSpec] 声明式构建 Shape 并设为背景。
 * <pre>
 *     布局属性 `binding_view_shape_spec`；`null` 时跳过；
 *     内部 [buildShapeFromSpec] 后 [applyShapeUtils]。
 * </pre>
 *
 * @param spec 可选 shape 配置字段集合
 */
@BindingAdapter("binding_view_shape_spec")
fun View.bindingViewShapeSpec(spec: ViewShapeSpec?) {
    if (spec == null) return
    try {
        applyShapeUtils(buildShapeFromSpec(spec))
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "bindingViewShapeSpec")
    }
}

// ==========
// = 内部实现 =
// ==========

private fun View.applyShapeUtils(utils: ShapeUtils) {
    try {
        utils.setDrawable(this)
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "applyShapeUtils")
    }
}

private fun buildShapeFromSpec(spec: ViewShapeSpec): ShapeUtils {
    var utils = when {
        spec.gradient != null -> {
            val g = spec.gradient!!
            ShapeUtils.newShape(g.angle, g.colors)
        }

        spec.gradientOrient != null -> {
            val g = spec.gradientOrient!!
            ShapeUtils.newShape(g.orientation, g.colors)
        }

        else -> ShapeUtils.newShape()
    }

    spec.shape?.let { utils = utils.setShape(it) }
    spec.alpha?.let { utils = utils.setAlpha(it) }
    spec.color?.let { utils = utils.setColor(it) }

    when {
        spec.corners != null -> {
            val c = spec.corners!!
            utils = utils.setCornerRadius(c.leftTop, c.rightTop, c.rightBottom, c.leftBottom)
        }

        spec.cornerRadiusLeft != null -> utils = utils.setCornerRadiusLeft(spec.cornerRadiusLeft!!)
        spec.cornerRadiusRight != null -> utils =
            utils.setCornerRadiusRight(spec.cornerRadiusRight!!)

        spec.cornerRadiusTop != null -> utils = utils.setCornerRadiusTop(spec.cornerRadiusTop!!)
        spec.cornerRadiusBottom != null -> utils =
            utils.setCornerRadiusBottom(spec.cornerRadiusBottom!!)

        spec.cornerRadius != null -> utils = utils.setCornerRadius(spec.cornerRadius!!)
    }

    spec.stroke?.let { s ->
        utils = utils.setStroke(s.width, s.color, s.dashWidth, s.dashGap)
    }

    spec.gradientExtra?.let { extra ->
        utils = applyGradientExtra(utils, extra)
    }

    spec.size?.let { s ->
        utils = utils.setSize(s.width, s.height)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        spec.padding?.let { p ->
            utils = utils.setPadding(p.left, p.top, p.right, p.bottom)
        }
    }

    return utils
}

private fun applyGradientExtra(
    utils: ShapeUtils,
    extra: ViewShapeGradientExtra,
): ShapeUtils {
    var result = utils
    extra.colors?.let { colors ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            result = result.setColors(colors)
        }
    }
    extra.gradientType?.let { result = result.setGradientType(it) }
    extra.orientation?.let { orient ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            result = result.setOrientation(orient)
        }
    }
    extra.angle?.let { angle ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            result = result.setOrientation(angle)
        }
    }
    if (extra.centerX != null && extra.centerY != null) {
        result = result.setGradientCenter(extra.centerX!!, extra.centerY!!)
    }
    extra.gradientRadius?.let { result = result.setGradientRadius(it) }
    extra.useLevel?.let { result = result.setUseLevel(it) }
    return result
}