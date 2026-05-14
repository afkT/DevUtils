package dev.simple.bindingadapters.view

import android.content.res.ColorStateList
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.animation.Animation
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.annotation.IdRes
import androidx.databinding.BindingAdapter
import dev.utils.app.ViewUtils

// =================================
// = ViewProperties BindingAdapter =
// =================================

// =============
// = id 与裁剪 =
// =============

/**
 * 通过数据绑定设置 View id。
 * <pre>
 *     布局属性 binding_vu_id；委托 ViewUtils.setId。
 * </pre>
 *
 * @param id `R.id.*` 或 `@android:id/...`
 */
@BindingAdapter("binding_vu_id")
fun View.bindingVuId(@IdRes id: Int) {
    ViewUtils.setId(this, id)
}

/**
 * 通过数据绑定设置 ViewGroup 是否裁剪子 View 绘制区域。
 * <pre>
 *     布局属性 binding_vu_clip_children；仅用于 ViewGroup；委托 ViewUtils.setClipChildren。
 * </pre>
 *
 * @param clipChildren 是否裁剪子 View
 */
@BindingAdapter("binding_vu_clip_children")
fun ViewGroup.bindingVuClipChildren(clipChildren: Boolean) {
    ViewUtils.setClipChildren(this, clipChildren)
}

// ==================
// = LayoutParams =
// ==================

/**
 * 通过数据绑定替换 LayoutParams。
 * <pre>
 *     布局属性 binding_vu_layout_params；为 null 时不调用；委托 ViewUtils.setLayoutParams。
 * </pre>
 *
 * @param params 新的 LayoutParams
 */
@BindingAdapter("binding_vu_layout_params")
fun View.bindingVuLayoutParams(params: ViewGroup.LayoutParams?) {
    if (params == null) return
    ViewUtils.setLayoutParams(this, params)
}

/**
 * 通过数据绑定设置宽高（像素），可选是否在 LayoutParams 为 null 时创建新实例。
 * <pre>
 *     布局属性 binding_vu_width_height_w、binding_vu_width_height_h、binding_vu_width_height_null_new_lp（后者可选，默认 true）。
 *     宽高均为 null 时不修改；仅一侧有时不调用；委托 ViewUtils.setWidthHeight。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_width_height_w", "binding_vu_width_height_h", "binding_vu_width_height_null_new_lp"],
    requireAll = false
)
fun View.bindingVuWidthHeight(
    width: Int?,
    height: Int?,
    nullNewLp: Boolean?
) {
    if (width == null || height == null) return
    ViewUtils.setWidthHeight(this, width, height, nullNewLp != false)
}

/**
 * 通过数据绑定设置 LinearLayout 子项 weight。
 * <pre>
 *     布局属性 binding_vu_weight；委托 ViewUtils.setWeight。
 * </pre>
 */
@BindingAdapter("binding_vu_weight")
fun View.bindingVuWeight(weight: Float) {
    ViewUtils.setWeight(this, weight)
}

/**
 * 通过数据绑定设置宽度（像素），可选 LayoutParams 为 null 时是否新建。
 * <pre>
 *     布局属性 binding_vu_width_px、binding_vu_width_null_new_lp（可选，默认 true）；委托 ViewUtils.setWidth。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_width_px", "binding_vu_width_null_new_lp"],
    requireAll = false
)
fun View.bindingVuWidth(
    width: Int?,
    nullNewLp: Boolean?
) {
    if (width == null) return
    ViewUtils.setWidth(this, width, nullNewLp != false)
}

/**
 * 通过数据绑定设置高度（像素），可选 LayoutParams 为 null 时是否新建。
 * <pre>
 *     布局属性 binding_vu_height_px、binding_vu_height_null_new_lp（可选，默认 true）；委托 ViewUtils.setHeight。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_height_px", "binding_vu_height_null_new_lp"],
    requireAll = false
)
fun View.bindingVuHeight(
    height: Int?,
    nullNewLp: Boolean?
) {
    if (height == null) return
    ViewUtils.setHeight(this, height, nullNewLp != false)
}

/**
 * 通过数据绑定设置最小高度。
 * <pre>
 *     布局属性 binding_vu_minimum_height；委托 ViewUtils.setMinimumHeight。
 * </pre>
 */
@BindingAdapter("binding_vu_minimum_height")
fun View.bindingVuMinimumHeight(minHeight: Int) {
    ViewUtils.setMinimumHeight(this, minHeight)
}

/**
 * 通过数据绑定设置最小宽度。
 * <pre>
 *     布局属性 binding_vu_minimum_width；委托 ViewUtils.setMinimumWidth。
 * </pre>
 */
@BindingAdapter("binding_vu_minimum_width")
fun View.bindingVuMinimumWidth(minWidth: Int) {
    ViewUtils.setMinimumWidth(this, minWidth)
}

// ===========
// = 外观变换 =
// ===========

/**
 * 通过数据绑定设置透明度。
 * <pre>
 *     布局属性 binding_vu_alpha；委托 ViewUtils.setAlpha。
 * </pre>
 */
@BindingAdapter("binding_vu_alpha")
fun View.bindingVuAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float) {
    ViewUtils.setAlpha(this, alpha)
}

/**
 * 通过数据绑定设置 tag。
 * <pre>
 *     布局属性 binding_vu_tag；委托 ViewUtils.setTag。
 * </pre>
 */
@BindingAdapter("binding_vu_tag")
fun View.bindingVuTag(tag: Any?) {
    ViewUtils.setTag(this, tag)
}

/**
 * 通过数据绑定设置内容滚动 X。
 * <pre>
 *     布局属性 binding_vu_scroll_x；委托 ViewUtils.setScrollX。
 * </pre>
 */
@BindingAdapter("binding_vu_scroll_x")
fun View.bindingVuScrollX(value: Int) {
    ViewUtils.setScrollX(this, value)
}

/**
 * 通过数据绑定设置内容滚动 Y。
 * <pre>
 *     布局属性 binding_vu_scroll_y；委托 ViewUtils.setScrollY。
 * </pre>
 */
@BindingAdapter("binding_vu_scroll_y")
fun View.bindingVuScrollY(value: Int) {
    ViewUtils.setScrollY(this, value)
}

/**
 * 通过数据绑定设置子 View 焦点传递策略。
 * <pre>
 *     布局属性 binding_vu_descendant_focusability；仅 ViewGroup；委托 ViewUtils.setDescendantFocusability。
 * </pre>
 */
@BindingAdapter("binding_vu_descendant_focusability")
fun ViewGroup.bindingVuDescendantFocusability(focusability: Int) {
    ViewUtils.setDescendantFocusability(this, focusability)
}

/**
 * 通过数据绑定设置边缘过度滚动模式。
 * <pre>
 *     布局属性 binding_vu_over_scroll_mode；委托 ViewUtils.setOverScrollMode。
 * </pre>
 */
@BindingAdapter("binding_vu_over_scroll_mode")
fun View.bindingVuOverScrollMode(mode: Int) {
    ViewUtils.setOverScrollMode(this, mode)
}

/**
 * 通过数据绑定设置是否绘制横向滚动条。
 * <pre>
 *     布局属性 binding_vu_horizontal_scroll_bar_enabled；委托 ViewUtils.setHorizontalScrollBarEnabled。
 * </pre>
 */
@BindingAdapter("binding_vu_horizontal_scroll_bar_enabled")
fun View.bindingVuHorizontalScrollBarEnabled(enabled: Boolean) {
    ViewUtils.setHorizontalScrollBarEnabled(this, enabled)
}

/**
 * 通过数据绑定设置是否绘制纵向滚动条。
 * <pre>
 *     布局属性 binding_vu_vertical_scroll_bar_enabled；委托 ViewUtils.setVerticalScrollBarEnabled。
 * </pre>
 */
@BindingAdapter("binding_vu_vertical_scroll_bar_enabled")
fun View.bindingVuVerticalScrollBarEnabled(enabled: Boolean) {
    ViewUtils.setVerticalScrollBarEnabled(this, enabled)
}

/**
 * 通过数据绑定设置是否为滚动容器。
 * <pre>
 *     布局属性 binding_vu_scroll_container；委托 ViewUtils.setScrollContainer。
 * </pre>
 */
@BindingAdapter("binding_vu_scroll_container")
fun View.bindingVuScrollContainer(isScrollContainer: Boolean) {
    ViewUtils.setScrollContainer(this, isScrollContainer)
}

/**
 * 通过数据绑定设置是否按 Outline 裁剪。
 * <pre>
 *     布局属性 binding_vu_clip_to_outline；委托 ViewUtils.setClipToOutline。
 * </pre>
 */
@BindingAdapter("binding_vu_clip_to_outline")
fun View.bindingVuClipToOutline(clipToOutline: Boolean) {
    ViewUtils.setClipToOutline(this, clipToOutline)
}

/**
 * 通过数据绑定设置 OutlineProvider。
 * <pre>
 *     布局属性 binding_vu_outline_provider；null 时仍委托 ViewUtils.setOutlineProvider（由系统处理）；委托 ViewUtils.setOutlineProvider。
 * </pre>
 */
@BindingAdapter("binding_vu_outline_provider")
fun View.bindingVuOutlineProvider(provider: ViewOutlineProvider?) {
    ViewUtils.setOutlineProvider(this, provider)
}

/**
 * 通过数据绑定设置 OutlineProvider 并开启裁剪。
 * <pre>
 *     布局属性 binding_vu_outline_provider_clip；委托 ViewUtils.setOutlineProviderClip。
 * </pre>
 */
@BindingAdapter("binding_vu_outline_provider_clip")
fun View.bindingVuOutlineProviderClip(provider: ViewOutlineProvider?) {
    ViewUtils.setOutlineProviderClip(this, provider)
}

// ===========
// = 焦点导航 =
// ===========

/**
 * 通过数据绑定设置下一个前向焦点 id。
 * <pre>
 *     布局属性 binding_vu_next_focus_forward_id；委托 ViewUtils.setNextFocusForwardId。
 * </pre>
 */
@BindingAdapter("binding_vu_next_focus_forward_id")
fun View.bindingVuNextFocusForwardId(@IdRes nextFocusForwardId: Int) {
    ViewUtils.setNextFocusForwardId(this, nextFocusForwardId)
}

/**
 * 通过数据绑定设置向下焦点导航目标 id。
 * <pre>
 *     布局属性 binding_vu_next_focus_down_id；委托 ViewUtils.setNextFocusDownId。
 * </pre>
 */
@BindingAdapter("binding_vu_next_focus_down_id")
fun View.bindingVuNextFocusDownId(@IdRes nextFocusDownId: Int) {
    ViewUtils.setNextFocusDownId(this, nextFocusDownId)
}

/**
 * 通过数据绑定设置向左焦点导航目标 id。
 * <pre>
 *     布局属性 binding_vu_next_focus_left_id；委托 ViewUtils.setNextFocusLeftId。
 * </pre>
 */
@BindingAdapter("binding_vu_next_focus_left_id")
fun View.bindingVuNextFocusLeftId(@IdRes nextFocusLeftId: Int) {
    ViewUtils.setNextFocusLeftId(this, nextFocusLeftId)
}

/**
 * 通过数据绑定设置向右焦点导航目标 id。
 * <pre>
 *     布局属性 binding_vu_next_focus_right_id；委托 ViewUtils.setNextFocusRightId。
 * </pre>
 */
@BindingAdapter("binding_vu_next_focus_right_id")
fun View.bindingVuNextFocusRightId(@IdRes nextFocusRightId: Int) {
    ViewUtils.setNextFocusRightId(this, nextFocusRightId)
}

/**
 * 通过数据绑定设置向上焦点导航目标 id。
 * <pre>
 *     布局属性 binding_vu_next_focus_up_id；委托 ViewUtils.setNextFocusUpId。
 * </pre>
 */
@BindingAdapter("binding_vu_next_focus_up_id")
fun View.bindingVuNextFocusUpId(@IdRes nextFocusUpId: Int) {
    ViewUtils.setNextFocusUpId(this, nextFocusUpId)
}

// ===========
// = 变换矩阵 =
// ===========

/**
 * 通过数据绑定设置 Z 轴旋转角。
 * <pre>
 *     布局属性 binding_vu_rotation；委托 ViewUtils.setRotation。
 * </pre>
 */
@BindingAdapter("binding_vu_rotation")
fun View.bindingVuRotation(rotation: Float) {
    ViewUtils.setRotation(this, rotation)
}

/**
 * 通过数据绑定设置绕 X 轴旋转角。
 * <pre>
 *     布局属性 binding_vu_rotation_x；委托 ViewUtils.setRotationX。
 * </pre>
 */
@BindingAdapter("binding_vu_rotation_x")
fun View.bindingVuRotationX(rotationX: Float) {
    ViewUtils.setRotationX(this, rotationX)
}

/**
 * 通过数据绑定设置绕 Y 轴旋转角。
 * <pre>
 *     布局属性 binding_vu_rotation_y；委托 ViewUtils.setRotationY。
 * </pre>
 */
@BindingAdapter("binding_vu_rotation_y")
fun View.bindingVuRotationY(rotationY: Float) {
    ViewUtils.setRotationY(this, rotationY)
}

/**
 * 通过数据绑定设置水平缩放。
 * <pre>
 *     布局属性 binding_vu_scale_x；委托 ViewUtils.setScaleX。
 * </pre>
 */
@BindingAdapter("binding_vu_scale_x")
fun View.bindingVuScaleX(scaleX: Float) {
    ViewUtils.setScaleX(this, scaleX)
}

/**
 * 通过数据绑定设置垂直缩放。
 * <pre>
 *     布局属性 binding_vu_scale_y；委托 ViewUtils.setScaleY。
 * </pre>
 */
@BindingAdapter("binding_vu_scale_y")
fun View.bindingVuScaleY(scaleY: Float) {
    ViewUtils.setScaleY(this, scaleY)
}

/**
 * 通过数据绑定设置文本对齐（View 层语义）。
 * <pre>
 *     布局属性 binding_vu_text_alignment；API 17 以下忽略；委托 ViewUtils.setTextAlignment。
 * </pre>
 */
@BindingAdapter("binding_vu_text_alignment")
fun View.bindingVuTextAlignment(textAlignment: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        ViewUtils.setTextAlignment(this, textAlignment)
    }
}

/**
 * 通过数据绑定设置文本方向。
 * <pre>
 *     布局属性 binding_vu_text_direction；API 17 以下忽略；委托 ViewUtils.setTextDirection。
 * </pre>
 */
@BindingAdapter("binding_vu_text_direction")
fun View.bindingVuTextDirection(textDirection: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        ViewUtils.setTextDirection(this, textDirection)
    }
}

/**
 * 通过数据绑定设置变换锚点 X。
 * <pre>
 *     布局属性 binding_vu_pivot_x；委托 ViewUtils.setPivotX。
 * </pre>
 */
@BindingAdapter("binding_vu_pivot_x")
fun View.bindingVuPivotX(pivotX: Float) {
    ViewUtils.setPivotX(this, pivotX)
}

/**
 * 通过数据绑定设置变换锚点 Y。
 * <pre>
 *     布局属性 binding_vu_pivot_y；委托 ViewUtils.setPivotY。
 * </pre>
 */
@BindingAdapter("binding_vu_pivot_y")
fun View.bindingVuPivotY(pivotY: Float) {
    ViewUtils.setPivotY(this, pivotY)
}

/**
 * 通过数据绑定设置 translationX。
 * <pre>
 *     布局属性 binding_vu_translation_x；委托 ViewUtils.setTranslationX。
 * </pre>
 */
@BindingAdapter("binding_vu_translation_x")
fun View.bindingVuTranslationX(translationX: Float) {
    ViewUtils.setTranslationX(this, translationX)
}

/**
 * 通过数据绑定设置 translationY。
 * <pre>
 *     布局属性 binding_vu_translation_y；委托 ViewUtils.setTranslationY。
 * </pre>
 */
@BindingAdapter("binding_vu_translation_y")
fun View.bindingVuTranslationY(translationY: Float) {
    ViewUtils.setTranslationY(this, translationY)
}

/**
 * 通过数据绑定设置左侧绘制坐标 X。
 * <pre>
 *     布局属性 binding_vu_x；委托 ViewUtils.setX。
 * </pre>
 */
@BindingAdapter("binding_vu_x")
fun View.bindingVuX(x: Float) {
    ViewUtils.setX(this, x)
}

/**
 * 通过数据绑定设置顶部绘制坐标 Y。
 * <pre>
 *     布局属性 binding_vu_y；委托 ViewUtils.setY。
 * </pre>
 */
@BindingAdapter("binding_vu_y")
fun View.bindingVuY(y: Float) {
    ViewUtils.setY(this, y)
}

/**
 * 通过数据绑定设置硬件分层类型与可选 Paint。
 * <pre>
 *     布局属性 binding_vu_layer_type、binding_vu_layer_paint（可选）；layerType 为 null 时不调用；委托 ViewUtils.setLayerType。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_layer_type", "binding_vu_layer_paint"],
    requireAll = false
)
fun View.bindingVuLayerType(
    layerType: Int?,
    paint: Paint?
) {
    if (layerType == null) return
    ViewUtils.setLayerType(this, layerType, paint)
}

// ==================
// = 交互与可见性 =
// ==================

/**
 * 通过数据绑定设置触摸模式下是否可聚焦。
 * <pre>
 *     布局属性 binding_vu_focusable_in_touch_mode；委托 ViewUtils.setFocusableInTouchMode。
 * </pre>
 */
@BindingAdapter("binding_vu_focusable_in_touch_mode")
fun View.bindingVuFocusableInTouchMode(focusableInTouchMode: Boolean) {
    ViewUtils.setFocusableInTouchMode(focusableInTouchMode, this)
}

/**
 * 通过数据绑定设置是否可聚焦。
 * <pre>
 *     布局属性 binding_vu_focusable；委托 ViewUtils.setFocusable。
 * </pre>
 */
@BindingAdapter("binding_vu_focusable")
fun View.bindingVuFocusable(focusable: Boolean) {
    ViewUtils.setFocusable(focusable, this)
}

/**
 * 通过数据绑定设置选中状态。
 * <pre>
 *     布局属性 binding_vu_selected；委托 ViewUtils.setSelected。
 * </pre>
 */
@BindingAdapter("binding_vu_selected")
fun View.bindingVuSelected(selected: Boolean) {
    ViewUtils.setSelected(selected, this)
}

/**
 * 通过数据绑定设置是否启用。
 * <pre>
 *     布局属性 binding_vu_enabled；委托 ViewUtils.setEnabled。
 * </pre>
 */
@BindingAdapter("binding_vu_enabled")
fun View.bindingVuEnabled(enabled: Boolean) {
    ViewUtils.setEnabled(enabled, this)
}

/**
 * 通过数据绑定设置是否可点击。
 * <pre>
 *     布局属性 binding_vu_clickable；委托 ViewUtils.setClickable。
 * </pre>
 */
@BindingAdapter("binding_vu_clickable")
fun View.bindingVuClickable(clickable: Boolean) {
    ViewUtils.setClickable(clickable, this)
}

/**
 * 通过数据绑定设置是否可长按。
 * <pre>
 *     布局属性 binding_vu_long_clickable；委托 ViewUtils.setLongClickable。
 * </pre>
 */
@BindingAdapter("binding_vu_long_clickable")
fun View.bindingVuLongClickable(longClickable: Boolean) {
    ViewUtils.setLongClickable(longClickable, this)
}

/**
 * 通过数据绑定按 ViewUtils 规则切换 VISIBLE / GONE。
 * <pre>
 *     布局属性 binding_vu_visibility_bool；true 为 VISIBLE，false 为 GONE；委托 ViewUtils.setVisibility(boolean, View)。
 * </pre>
 */
@BindingAdapter("binding_vu_visibility_bool")
fun View.bindingVuVisibilityBool(isVisibility: Boolean) {
    ViewUtils.setVisibility(isVisibility, this)
}

/**
 * 通过数据绑定设置可见性整型常量。
 * <pre>
 *     布局属性 binding_vu_visibility_int；委托 ViewUtils.setVisibility(int, View)。
 * </pre>
 */
@BindingAdapter("binding_vu_visibility_int")
fun View.bindingVuVisibilityInt(visibility: Int) {
    ViewUtils.setVisibility(visibility, this)
}

/**
 * 通过数据绑定按 ViewUtils 规则切换 VISIBLE / INVISIBLE。
 * <pre>
 *     布局属性 binding_vu_visibility_in_bool；委托 ViewUtils.setVisibilityIN。
 * </pre>
 */
@BindingAdapter("binding_vu_visibility_in_bool")
fun View.bindingVuVisibilityInBool(isVisibility: Boolean) {
    ViewUtils.setVisibilityIN(isVisibility, this)
}

// ===========
// = Gravity =
// ===========

/**
 * 通过数据绑定设置子项在父容器中的 layout gravity。
 * <pre>
 *     布局属性 binding_vu_layout_gravity、binding_vu_layout_gravity_reflection（可选，默认 true）；委托 ViewUtils.setLayoutGravity。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_layout_gravity", "binding_vu_layout_gravity_reflection"],
    requireAll = false
)
fun View.bindingVuLayoutGravity(
    gravity: Int?,
    isReflection: Boolean?
) {
    if (gravity == null) return
    ViewUtils.setLayoutGravity(this, gravity, isReflection != false)
}

// =========
// = Margin =
// =========

/**
 * 通过数据绑定设置四边相同 margin。
 * <pre>
 *     布局属性 binding_vu_margin_all；委托 ViewUtils.setMargin(view, margin)。
 * </pre>
 */
@BindingAdapter("binding_vu_margin_all")
fun View.bindingVuMarginAll(margin: Int) {
    ViewUtils.setMargin(this, margin)
}

/**
 * 通过数据绑定设置左右与上下 margin。
 * <pre>
 *     布局属性 binding_vu_margin_left_right、binding_vu_margin_top_bottom；委托 ViewUtils.setMargin(view, lr, tb)。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_margin_left_right", "binding_vu_margin_top_bottom"],
    requireAll = true
)
fun View.bindingVuMarginLrTb(
    leftRight: Int,
    topBottom: Int
) {
    ViewUtils.setMargin(this, leftRight, topBottom)
}

/**
 * 通过数据绑定分别设置左、上、右、下 margin。
 * <pre>
 *     布局属性 binding_vu_margin_l、binding_vu_margin_t、binding_vu_margin_r、binding_vu_margin_b；委托 ViewUtils.setMargin。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_margin_l", "binding_vu_margin_t", "binding_vu_margin_r", "binding_vu_margin_b"],
    requireAll = true
)
fun View.bindingVuMarginLtrb(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
) {
    ViewUtils.setMargin(this, left, top, right, bottom)
}

/**
 * 通过数据绑定设置左边 margin，可选是否重置其余边为 0。
 * <pre>
 *     布局属性 binding_vu_margin_left、binding_vu_margin_left_reset（可选，默认 true）；委托 ViewUtils.setMarginLeft。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_margin_left", "binding_vu_margin_left_reset"],
    requireAll = false
)
fun View.bindingVuMarginLeft(
    leftMargin: Int?,
    reset: Boolean?
) {
    if (leftMargin == null) return
    ViewUtils.setMarginLeft(this, leftMargin, reset != false)
}

/**
 * 通过数据绑定设置上边 margin，可选是否重置其余边。
 * <pre>
 *     布局属性 binding_vu_margin_top、binding_vu_margin_top_reset（可选，默认 true）；委托 ViewUtils.setMarginTop。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_margin_top", "binding_vu_margin_top_reset"],
    requireAll = false
)
fun View.bindingVuMarginTop(
    topMargin: Int?,
    reset: Boolean?
) {
    if (topMargin == null) return
    ViewUtils.setMarginTop(this, topMargin, reset != false)
}

/**
 * 通过数据绑定设置右边 margin，可选是否重置其余边。
 * <pre>
 *     布局属性 binding_vu_margin_right、binding_vu_margin_right_reset（可选，默认 true）；委托 ViewUtils.setMarginRight。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_margin_right", "binding_vu_margin_right_reset"],
    requireAll = false
)
fun View.bindingVuMarginRight(
    rightMargin: Int?,
    reset: Boolean?
) {
    if (rightMargin == null) return
    ViewUtils.setMarginRight(this, rightMargin, reset != false)
}

/**
 * 通过数据绑定设置下边 margin，可选是否重置其余边。
 * <pre>
 *     布局属性 binding_vu_margin_bottom、binding_vu_margin_bottom_reset（可选，默认 true）；委托 ViewUtils.setMarginBottom。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_margin_bottom", "binding_vu_margin_bottom_reset"],
    requireAll = false
)
fun View.bindingVuMarginBottom(
    bottomMargin: Int?,
    reset: Boolean?
) {
    if (bottomMargin == null) return
    ViewUtils.setMarginBottom(this, bottomMargin, reset != false)
}

// ===========
// = Padding =
// ===========

/**
 * 通过数据绑定设置四边相同 padding。
 * <pre>
 *     布局属性 binding_vu_padding_all；委托 ViewUtils.setPadding(view, padding)。
 * </pre>
 */
@BindingAdapter("binding_vu_padding_all")
fun View.bindingVuPaddingAll(padding: Int) {
    ViewUtils.setPadding(this, padding)
}

/**
 * 通过数据绑定设置左右与上下 padding。
 * <pre>
 *     布局属性 binding_vu_padding_left_right、binding_vu_padding_top_bottom；委托 ViewUtils.setPadding(view, lr, tb)。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_padding_left_right", "binding_vu_padding_top_bottom"],
    requireAll = true
)
fun View.bindingVuPaddingLrTb(
    leftRight: Int,
    topBottom: Int
) {
    ViewUtils.setPadding(this, leftRight, topBottom)
}

/**
 * 通过数据绑定分别设置左、上、右、下 padding。
 * <pre>
 *     布局属性 binding_vu_padding_l、binding_vu_padding_t、binding_vu_padding_r、binding_vu_padding_b；委托 ViewUtils.setPadding。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_padding_l", "binding_vu_padding_t", "binding_vu_padding_r", "binding_vu_padding_b"],
    requireAll = true
)
fun View.bindingVuPaddingLtrb(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
) {
    ViewUtils.setPadding(this, left, top, right, bottom)
}

/**
 * 通过数据绑定设置左侧 padding，可选是否重置其余边为 0。
 * <pre>
 *     布局属性 binding_vu_padding_left、binding_vu_padding_left_reset（可选，默认 true）；委托 ViewUtils.setPaddingLeft。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_padding_left", "binding_vu_padding_left_reset"],
    requireAll = false
)
fun View.bindingVuPaddingLeft(
    leftPadding: Int?,
    reset: Boolean?
) {
    if (leftPadding == null) return
    ViewUtils.setPaddingLeft(this, leftPadding, reset != false)
}

/**
 * 通过数据绑定设置顶部 padding，可选是否重置其余边。
 * <pre>
 *     布局属性 binding_vu_padding_top、binding_vu_padding_top_reset（可选，默认 true）；委托 ViewUtils.setPaddingTop。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_padding_top", "binding_vu_padding_top_reset"],
    requireAll = false
)
fun View.bindingVuPaddingTop(
    topPadding: Int?,
    reset: Boolean?
) {
    if (topPadding == null) return
    ViewUtils.setPaddingTop(this, topPadding, reset != false)
}

/**
 * 通过数据绑定设置右侧 padding，可选是否重置其余边。
 * <pre>
 *     布局属性 binding_vu_padding_right、binding_vu_padding_right_reset（可选，默认 true）；委托 ViewUtils.setPaddingRight。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_padding_right", "binding_vu_padding_right_reset"],
    requireAll = false
)
fun View.bindingVuPaddingRight(
    rightPadding: Int?,
    reset: Boolean?
) {
    if (rightPadding == null) return
    ViewUtils.setPaddingRight(this, rightPadding, reset != false)
}

/**
 * 通过数据绑定设置底部 padding，可选是否重置其余边。
 * <pre>
 *     布局属性 binding_vu_padding_bottom、binding_vu_padding_bottom_reset（可选，默认 true）；委托 ViewUtils.setPaddingBottom。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_padding_bottom", "binding_vu_padding_bottom_reset"],
    requireAll = false
)
fun View.bindingVuPaddingBottom(
    bottomPadding: Int?,
    reset: Boolean?
) {
    if (bottomPadding == null) return
    ViewUtils.setPaddingBottom(this, bottomPadding, reset != false)
}

// =============
// = 动画与背景 =
// =============

/**
 * 通过数据绑定设置或清除 View 动画。
 * <pre>
 *     布局属性 binding_vu_animation；null 时委托 ViewUtils.clearAnimation；非 null 时委托 ViewUtils.setAnimation。
 * </pre>
 */
@BindingAdapter("binding_vu_animation")
fun View.bindingVuAnimation(animation: Animation?) {
    if (animation == null) {
        ViewUtils.clearAnimation(this)
    } else {
        ViewUtils.setAnimation(this, animation)
    }
}

/**
 * 通过数据绑定设置背景 Drawable。
 * <pre>
 *     布局属性 binding_vu_background；null 时委托 ViewUtils.removeBackground；非 null 时委托 ViewUtils.setBackground。
 * </pre>
 */
@BindingAdapter("binding_vu_background")
fun View.bindingVuBackground(background: Drawable?) {
    if (background == null) {
        ViewUtils.removeBackground(this)
    } else {
        ViewUtils.setBackground(this, background)
    }
}

/**
 * 通过数据绑定设置背景色。
 * <pre>
 *     布局属性 binding_vu_background_color；委托 ViewUtils.setBackgroundColor。
 * </pre>
 */
@BindingAdapter("binding_vu_background_color")
fun View.bindingVuBackgroundColor(@ColorInt color: Int) {
    ViewUtils.setBackgroundColor(this, color)
}

/**
 * 通过数据绑定设置背景资源 id。
 * <pre>
 *     布局属性 binding_vu_background_resource；0 或无效时可传 0 仍委托工具类；委托 ViewUtils.setBackgroundResource。
 * </pre>
 */
@BindingAdapter("binding_vu_background_resource")
fun View.bindingVuBackgroundResource(@DrawableRes resId: Int) {
    ViewUtils.setBackgroundResource(this, resId)
}

/**
 * 通过数据绑定设置背景着色。
 * <pre>
 *     布局属性 binding_vu_background_tint_list；API 21 以下忽略；委托 ViewUtils.setBackgroundTintList。
 * </pre>
 */
@BindingAdapter("binding_vu_background_tint_list")
fun View.bindingVuBackgroundTintList(tint: ColorStateList?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        ViewUtils.setBackgroundTintList(this, tint)
    }
}

/**
 * 通过数据绑定设置背景着色模式。
 * <pre>
 *     布局属性 binding_vu_background_tint_mode；API 21 以下忽略；null 时不调用；委托 ViewUtils.setBackgroundTintMode。
 * </pre>
 */
@BindingAdapter("binding_vu_background_tint_mode")
fun View.bindingVuBackgroundTintMode(tintMode: PorterDuff.Mode?) {
    if (tintMode == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
    ViewUtils.setBackgroundTintMode(this, tintMode)
}

// ===========
// = 前景 =
// ===========

/**
 * 通过数据绑定设置前景 Drawable（API 23+）。
 * <pre>
 *     布局属性 binding_vu_foreground；null 时委托 ViewUtils.removeForeground；委托 ViewUtils.setForeground。
 * </pre>
 */
@BindingAdapter("binding_vu_foreground")
fun View.bindingVuForeground(foreground: Drawable?) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
    if (foreground == null) {
        ViewUtils.removeForeground(this)
    } else {
        ViewUtils.setForeground(this, foreground)
    }
}

/**
 * 通过数据绑定设置前景重心（API 23+）。
 * <pre>
 *     布局属性 binding_vu_foreground_gravity；委托 ViewUtils.setForegroundGravity。
 * </pre>
 */
@BindingAdapter("binding_vu_foreground_gravity")
fun View.bindingVuForegroundGravity(gravity: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        ViewUtils.setForegroundGravity(this, gravity)
    }
}

/**
 * 通过数据绑定设置前景着色（API 23+）。
 * <pre>
 *     布局属性 binding_vu_foreground_tint_list；委托 ViewUtils.setForegroundTintList。
 * </pre>
 */
@BindingAdapter("binding_vu_foreground_tint_list")
fun View.bindingVuForegroundTintList(tint: ColorStateList?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        ViewUtils.setForegroundTintList(this, tint)
    }
}

/**
 * 通过数据绑定设置前景着色模式（API 23+）。
 * <pre>
 *     布局属性 binding_vu_foreground_tint_mode；null 时不调用；委托 ViewUtils.setForegroundTintMode。
 * </pre>
 */
@BindingAdapter("binding_vu_foreground_tint_mode")
fun View.bindingVuForegroundTintMode(tintMode: PorterDuff.Mode?) {
    if (tintMode == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
    ViewUtils.setForegroundTintMode(this, tintMode)
}

// ===========
// = 着色与进度条 =
// ===========

/**
 * 通过数据绑定按颜色过滤背景 Drawable 并设回背景。
 * <pre>
 *     布局属性 binding_vu_color_filter；委托 ViewUtils.setColorFilter(view, color)。
 * </pre>
 */
@BindingAdapter("binding_vu_color_filter")
fun View.bindingVuColorFilter(@ColorInt color: Int) {
    ViewUtils.setColorFilter(this, color)
}

/**
 * 通过数据绑定对指定 Drawable 着色并设为背景。
 * <pre>
 *     布局属性 binding_vu_color_filter_drawable、binding_vu_color_filter_drawable_color；委托 ViewUtils.setColorFilter(view, drawable, color)。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_color_filter_drawable", "binding_vu_color_filter_drawable_color"],
    requireAll = true
)
fun View.bindingVuColorFilterDrawable(
    drawable: Drawable,
    @ColorInt color: Int
) {
    ViewUtils.setColorFilter(this, drawable, color)
}

/**
 * 通过数据绑定使用 ColorFilter 处理当前背景。
 * <pre>
 *     布局属性 binding_vu_color_filter_object；委托 ViewUtils.setColorFilter(view, colorFilter)。
 * </pre>
 */
@BindingAdapter("binding_vu_color_filter_object")
fun View.bindingVuColorFilterObject(colorFilter: ColorFilter?) {
    if (colorFilter == null) return
    ViewUtils.setColorFilter(this, colorFilter)
}

/**
 * 通过数据绑定对 Drawable 应用 ColorFilter 并设为背景。
 * <pre>
 *     布局属性 binding_vu_color_filter_drawable_object、binding_vu_color_filter_drawable_filter；委托 ViewUtils.setColorFilter(view, drawable, filter)。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_color_filter_drawable_object", "binding_vu_color_filter_drawable_filter"],
    requireAll = true
)
fun View.bindingVuColorFilterDrawableObject(
    drawable: Drawable,
    colorFilter: ColorFilter
) {
    ViewUtils.setColorFilter(this, drawable, colorFilter)
}

/**
 * 通过数据绑定设置 ProgressBar 的进度 Drawable（若不是 ProgressBar 则工具类内部忽略）。
 * <pre>
 *     布局属性 binding_vu_progress_drawable；委托 ViewUtils.setProgressDrawable。
 * </pre>
 */
@BindingAdapter("binding_vu_progress_drawable")
fun View.bindingVuProgressDrawable(drawable: Drawable?) {
    if (drawable == null) return
    ViewUtils.setProgressDrawable(this, drawable)
}

/**
 * 通过数据绑定设置 ProgressBar 当前进度。
 * <pre>
 *     布局属性 binding_vu_bar_progress；委托 ViewUtils.setBarProgress。
 * </pre>
 */
@BindingAdapter("binding_vu_bar_progress")
fun View.bindingVuBarProgress(progress: Int) {
    ViewUtils.setBarProgress(this, progress)
}

/**
 * 通过数据绑定设置 ProgressBar 最大值。
 * <pre>
 *     布局属性 binding_vu_bar_max；委托 ViewUtils.setBarMax。
 * </pre>
 */
@BindingAdapter("binding_vu_bar_max")
fun View.bindingVuBarMax(max: Int) {
    ViewUtils.setBarMax(this, max)
}

/**
 * 通过数据绑定同时设置 ProgressBar 的最大值与当前进度。
 * <pre>
 *     布局属性 binding_vu_bar_progress_value、binding_vu_bar_max_value；委托 ViewUtils.setBarValue。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_vu_bar_progress_value", "binding_vu_bar_max_value"],
    requireAll = true
)
fun View.bindingVuBarValue(
    progress: Int,
    max: Int
) {
    ViewUtils.setBarValue(this, progress, max)
}
