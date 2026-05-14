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
import dev.simple.bindingadapters.BindingTrigger.shouldTriggerBindingAction
import dev.simple.bindingadapters.view.attribute.BarProgressState
import dev.simple.bindingadapters.view.attribute.WidthHeightDims
import dev.utils.app.ViewUtils

// ===============================
// = ViewProperty BindingAdapter =
// ===============================

/**
 * View 属性相关的 Data Binding 适配集合（源文件命名 ViewProperty）。
 *
 * 布局自定义属性统一为 `binding_view_*`（View Property），Kotlin 扩展统一为 `bindingView*`；
 * 实现上主要对应 `dev.utils.app.ViewUtils` 的 `set*` 与若干 `clear*` / `remove*` / `request*` 等
 * 适合在单 View 上通过绑定触发的 API（见各方法 KDoc）。
 * <pre>
 *     未封装 `inflate`、`getActivity`、`getChildAt`、`convertViewGroup` 等在 XML 中无意义或无法表达的接口。
 *     需对同一命令多次触发时，可使用 `binding_view_*_ts` 与 [shouldTriggerBindingAction] 同判定的正时间戳绑定。
 *     强关联多参可合并单属性：`binding_view_width_height_dims`、[dev.simple.bindingadapters.view.attribute.WidthHeightDims]、
 *     `binding_view_bar_progress_state`、[dev.simple.bindingadapters.view.attribute.BarProgressState]。
 * </pre>
 */

// =============
// = ViewUtils =
// =============

/**
 * 通过数据绑定设置 View id。
 * <pre>
 *     布局属性 binding_view_id；对应 ViewUtils.setId。
 * </pre>
 *
 * @param id `R.id.*` 或 `@android:id/...`
 */
@BindingAdapter("binding_view_id")
fun View.bindingViewId(@IdRes id: Int) {
    ViewUtils.setId(this, id)
}

/**
 * 通过数据绑定设置 ViewGroup 是否裁剪子 View 绘制区域。
 * <pre>
 *     布局属性 binding_view_clip_children；仅用于 ViewGroup；对应 ViewUtils.setClipChildren。
 * </pre>
 *
 * @param clipChildren 是否裁剪子 View
 */
@BindingAdapter("binding_view_clip_children")
fun ViewGroup.bindingViewClipChildren(clipChildren: Boolean) {
    ViewUtils.setClipChildren(this, clipChildren)
}

// ======================================
// = ViewUtils：适合 XML 的非 set/get API =
// ======================================
// 不含 inflate、getActivity、getChildAt、convertViewGroup 等不适合绑定到布局单节点的用法。

/**
 * 通过数据绑定移除 ViewGroup 的全部子 View。
 * <pre>
 *     布局属性 binding_view_remove_all_views；为 true 时调用 ViewUtils.removeAllViews；false 或 null 时不调用。
 *     若需同值多次触发，改用 binding_view_remove_all_views_ts 并绑定正时间戳（与 [shouldTriggerBindingAction] 判定一致）。
 * </pre>
 */
@BindingAdapter("binding_view_remove_all_views")
fun ViewGroup.bindingViewRemoveAllViews(removeAll: Boolean?) {
    if (removeAll != true) return
    ViewUtils.removeAllViews(this)
}

/**
 * 通过数据绑定将自身从父容器中移除。
 * <pre>
 *     布局属性 binding_view_remove_self_from_parent；为 true 时调用 ViewUtils.removeSelfFromParent。
 *     若需同值多次触发，改用 binding_view_remove_self_from_parent_ts 并绑定正时间戳。
 * </pre>
 */
@BindingAdapter("binding_view_remove_self_from_parent")
fun View.bindingViewRemoveSelfFromParent(remove: Boolean?) {
    if (remove != true) return
    ViewUtils.removeSelfFromParent(this)
}

/**
 * 通过数据绑定请求对该 View 重新布局。
 * <pre>
 *     布局属性 binding_view_request_layout；为 true 时调用 ViewUtils.requestLayout。
 *     若需同值多次触发，改用 binding_view_request_layout_ts 并绑定正时间戳。
 * </pre>
 */
@BindingAdapter("binding_view_request_layout")
fun View.bindingViewRequestLayout(request: Boolean?) {
    if (request != true) return
    ViewUtils.requestLayout(this)
}

/**
 * 通过数据绑定请求该 View 获取焦点。
 * <pre>
 *     布局属性 binding_view_request_focus；为 true 时调用 ViewUtils.requestFocus。
 *     若需同值多次触发，改用 binding_view_request_focus_ts 并绑定正时间戳。
 * </pre>
 */
@BindingAdapter("binding_view_request_focus")
fun View.bindingViewRequestFocus(request: Boolean?) {
    if (request != true) return
    ViewUtils.requestFocus(this)
}

/**
 * 通过数据绑定清除该 View 的焦点。
 * <pre>
 *     布局属性 binding_view_clear_focus；为 true 时调用 ViewUtils.clearFocus。
 *     若需同值多次触发，改用 binding_view_clear_focus_ts 并绑定正时间戳。
 * </pre>
 */
@BindingAdapter("binding_view_clear_focus")
fun View.bindingViewClearFocus(clear: Boolean?) {
    if (clear != true) return
    ViewUtils.clearFocus(this)
}

/**
 * 通过数据绑定沿父链请求 layout。
 * <pre>
 *     布局属性 binding_view_request_layout_parent、binding_view_request_layout_parent_all（可选，默认 false）；
 *     对应 ViewUtils.requestLayoutParent。
 *     若需同值多次触发，改用 binding_view_request_layout_parent_ts 与可选 binding_view_request_layout_parent_ts_all（勿与布尔版混用同一 optional 名）。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_view_request_layout_parent", "binding_view_request_layout_parent_all"],
    requireAll = false
)
fun View.bindingViewRequestLayoutParent(
    request: Boolean?,
    allParent: Boolean?
) {
    if (request != true) return
    ViewUtils.requestLayoutParent(this, allParent == true)
}

/**
 * 通过数据绑定对已设置的 Animation 调用 start（不替换 animation 对象）。
 * <pre>
 *     布局属性 binding_view_start_attached_animation；为 true 时调用 ViewUtils.startAnimation(view)。
 *     若需同值多次触发，改用 binding_view_start_attached_animation_ts 并绑定正时间戳。
 * </pre>
 */
@BindingAdapter("binding_view_start_attached_animation")
fun View.bindingViewStartAttachedAnimation(start: Boolean?) {
    if (start != true) return
    ViewUtils.startAnimation(this)
}

/**
 * 通过数据绑定启动指定 Animation（内部会 startAnimation）。
 * <pre>
 *     布局属性 binding_view_start_animation；非 null 时调用 ViewUtils.startAnimation(view, animation)。
 * </pre>
 */
@BindingAdapter("binding_view_start_animation")
fun View.bindingViewStartAnimation(animation: Animation?) {
    if (animation == null) return
    ViewUtils.startAnimation(this, animation)
}

/**
 * 通过数据绑定取消 View 上正在播放的动画。
 * <pre>
 *     布局属性 binding_view_cancel_animation；为 true 时调用 ViewUtils.cancelAnimation(view)。
 *     若需同值多次触发，改用 binding_view_cancel_animation_ts 并绑定正时间戳。
 * </pre>
 */
@BindingAdapter("binding_view_cancel_animation")
fun View.bindingViewCancelAnimation(cancel: Boolean?) {
    if (cancel != true) return
    ViewUtils.cancelAnimation(this)
}

/**
 * 通过数据绑定移除背景并清除 ImageView 的 src（与仅 removeBackground 区分）。
 * <pre>
 *     布局属性 binding_view_remove_all_background；为 true 时调用 ViewUtils.removeAllBackground。
 *     若需同值多次触发，改用 binding_view_remove_all_background_ts 并绑定正时间戳。
 * </pre>
 */
@BindingAdapter("binding_view_remove_all_background")
fun View.bindingViewRemoveAllBackground(remove: Boolean?) {
    if (remove != true) return
    ViewUtils.removeAllBackground(this)
}

/**
 * 通过数据绑定为 RelativeLayout 子 View 添加布局规则。
 * <pre>
 *     布局属性 binding_view_relative_add_rule_verb、binding_view_relative_add_rule_subject（可选，缺省等价 subject = -1）；
 *     对应 ViewUtils.addRule。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_view_relative_add_rule_verb", "binding_view_relative_add_rule_subject"],
    requireAll = false
)
fun View.bindingViewRelativeAddRule(
    verb: Int?,
    subject: Int?
) {
    if (verb == null) return
    if (subject != null) {
        ViewUtils.addRule(this, verb, subject)
    } else {
        ViewUtils.addRule(this, verb)
    }
}

/**
 * 通过数据绑定为 RelativeLayout 子 View 移除布局规则。
 * <pre>
 *     布局属性 binding_view_relative_remove_rule_verb；对应 ViewUtils.removeRule。
 * </pre>
 */
@BindingAdapter("binding_view_relative_remove_rule_verb")
fun View.bindingViewRelativeRemoveRule(verb: Int?) {
    if (verb == null) return
    ViewUtils.removeRule(this, verb)
}

// ======================================
// = 时间戳触发变体（命令式多次执行） =
// ======================================

/**
 * 通过数据绑定以时间戳触发移除 ViewGroup 的全部子 View。
 * <pre>
 *     布局属性 binding_view_remove_all_views_ts；与 [shouldTriggerBindingAction] 判定一致时调用 ViewUtils.removeAllViews。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_remove_all_views_ts")
fun ViewGroup.bindingViewRemoveAllViewsTs(timestamp: Long?) {
    if (!timestamp.shouldTriggerBindingAction()) return
    ViewUtils.removeAllViews(this)
}

/**
 * 通过数据绑定以时间戳触发将自身从父容器中移除。
 * <pre>
 *     布局属性 binding_view_remove_self_from_parent_ts；判定同 [shouldTriggerBindingAction]。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_remove_self_from_parent_ts")
fun View.bindingViewRemoveSelfFromParentTs(timestamp: Long?) {
    if (!timestamp.shouldTriggerBindingAction()) return
    ViewUtils.removeSelfFromParent(this)
}

/**
 * 通过数据绑定以时间戳触发 requestLayout。
 * <pre>
 *     布局属性 binding_view_request_layout_ts；判定同 [shouldTriggerBindingAction]。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_request_layout_ts")
fun View.bindingViewRequestLayoutTs(timestamp: Long?) {
    if (!timestamp.shouldTriggerBindingAction()) return
    ViewUtils.requestLayout(this)
}

/**
 * 通过数据绑定以时间戳触发 requestFocus。
 * <pre>
 *     布局属性 binding_view_request_focus_ts；判定同 [shouldTriggerBindingAction]。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_request_focus_ts")
fun View.bindingViewRequestFocusTs(timestamp: Long?) {
    if (!timestamp.shouldTriggerBindingAction()) return
    ViewUtils.requestFocus(this)
}

/**
 * 通过数据绑定以时间戳触发 clearFocus。
 * <pre>
 *     布局属性 binding_view_clear_focus_ts；判定同 [shouldTriggerBindingAction]。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_clear_focus_ts")
fun View.bindingViewClearFocusTs(timestamp: Long?) {
    if (!timestamp.shouldTriggerBindingAction()) return
    ViewUtils.clearFocus(this)
}

/**
 * 通过数据绑定以时间戳触发沿父链 requestLayout。
 * <pre>
 *     布局属性 binding_view_request_layout_parent_ts、binding_view_request_layout_parent_ts_all（可选，默认 false）；
 *     判定同 [shouldTriggerBindingAction]；与布尔版分用属性名，避免与 binding_view_request_layout_parent_all 混绑冲突。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 * @param allParent 是否沿父链全部请求
 */
@BindingAdapter(
    value = ["binding_view_request_layout_parent_ts", "binding_view_request_layout_parent_ts_all"],
    requireAll = false
)
fun View.bindingViewRequestLayoutParentTs(
    timestamp: Long?,
    allParent: Boolean?
) {
    if (!timestamp.shouldTriggerBindingAction()) return
    ViewUtils.requestLayoutParent(this, allParent == true)
}

/**
 * 通过数据绑定以时间戳触发已附着 Animation 的 start。
 * <pre>
 *     布局属性 binding_view_start_attached_animation_ts；判定同 [shouldTriggerBindingAction]。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_start_attached_animation_ts")
fun View.bindingViewStartAttachedAnimationTs(timestamp: Long?) {
    if (!timestamp.shouldTriggerBindingAction()) return
    ViewUtils.startAnimation(this)
}

/**
 * 通过数据绑定以时间戳触发 cancelAnimation。
 * <pre>
 *     布局属性 binding_view_cancel_animation_ts；判定同 [shouldTriggerBindingAction]。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_cancel_animation_ts")
fun View.bindingViewCancelAnimationTs(timestamp: Long?) {
    if (!timestamp.shouldTriggerBindingAction()) return
    ViewUtils.cancelAnimation(this)
}

/**
 * 通过数据绑定以时间戳触发 removeAllBackground。
 * <pre>
 *     布局属性 binding_view_remove_all_background_ts；判定同 [shouldTriggerBindingAction]。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_remove_all_background_ts")
fun View.bindingViewRemoveAllBackgroundTs(timestamp: Long?) {
    if (!timestamp.shouldTriggerBindingAction()) return
    ViewUtils.removeAllBackground(this)
}

/**
 * 通过数据绑定以时间戳触发 clearAnimation。
 * <pre>
 *     布局属性 binding_view_clear_animation_ts；判定同 [shouldTriggerBindingAction]。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_clear_animation_ts")
fun View.bindingViewClearAnimationTs(timestamp: Long?) {
    if (!timestamp.shouldTriggerBindingAction()) return
    ViewUtils.clearAnimation(this)
}

/**
 * 通过数据绑定以时间戳触发 removeBackground。
 * <pre>
 *     布局属性 binding_view_remove_background_ts；判定同 [shouldTriggerBindingAction]。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_remove_background_ts")
fun View.bindingViewRemoveBackgroundTs(timestamp: Long?) {
    if (!timestamp.shouldTriggerBindingAction()) return
    ViewUtils.removeBackground(this)
}

/**
 * 通过数据绑定以时间戳触发 removeForeground（API 23 以下忽略）。
 * <pre>
 *     布局属性 binding_view_remove_foreground_ts；判定同 [shouldTriggerBindingAction]。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_view_remove_foreground_ts")
fun View.bindingViewRemoveForegroundTs(timestamp: Long?) {
    if (!timestamp.shouldTriggerBindingAction()) return
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
    ViewUtils.removeForeground(this)
}

// ================
// = LayoutParams =
// ================

/**
 * 通过数据绑定替换 LayoutParams。
 * <pre>
 *     布局属性 binding_view_layout_params；为 null 时不调用；对应 ViewUtils.setLayoutParams。
 * </pre>
 *
 * @param params 新的 LayoutParams
 */
@BindingAdapter("binding_view_layout_params")
fun View.bindingViewLayoutParams(params: ViewGroup.LayoutParams?) {
    if (params == null) return
    ViewUtils.setLayoutParams(this, params)
}

/**
 * 通过数据绑定设置宽高（像素），可选是否在 LayoutParams 为 null 时创建新实例。
 * <pre>
 *     布局属性 binding_view_width_height_w、binding_view_width_height_h、binding_view_width_height_null_new_lp（后者可选，默认 true）。
 *     宽高均为 null 时不修改；仅一侧有时不调用；对应 ViewUtils.setWidthHeight。
 *     亦可改用单属性 binding_view_width_height_dims 与 [WidthHeightDims]。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_view_width_height_w", "binding_view_width_height_h", "binding_view_width_height_null_new_lp"],
    requireAll = false
)
fun View.bindingViewWidthHeight(
    width: Int?,
    height: Int?,
    nullNewLp: Boolean?
) {
    if (width == null || height == null) return
    ViewUtils.setWidthHeight(this, width, height, nullNewLp != false)
}

/**
 * 通过数据绑定以单属性设置宽高（像素）及 LayoutParams 空时是否新建。
 * <pre>
 *     布局属性 binding_view_width_height_dims；为 null 时不调用；与三属性宽高绑定等价；对应 ViewUtils.setWidthHeight。
 * </pre>
 *
 * @param dims 宽高及 nullNewLp 合并参数
 */
@BindingAdapter("binding_view_width_height_dims")
fun View.bindingViewWidthHeightDims(dims: WidthHeightDims?) {
    if (dims == null) return
    ViewUtils.setWidthHeight(this, dims.widthPx, dims.heightPx, dims.nullNewLp)
}

/**
 * 通过数据绑定设置 LinearLayout 子项 weight。
 * <pre>
 *     布局属性 binding_view_weight；对应 ViewUtils.setWeight。
 * </pre>
 */
@BindingAdapter("binding_view_weight")
fun View.bindingViewWeight(weight: Float) {
    ViewUtils.setWeight(this, weight)
}

/**
 * 通过数据绑定设置宽度（像素），可选 LayoutParams 为 null 时是否新建。
 * <pre>
 *     布局属性 binding_view_width_px、binding_view_width_null_new_lp（可选，默认 true）；对应 ViewUtils.setWidth。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_view_width_px", "binding_view_width_null_new_lp"],
    requireAll = false
)
fun View.bindingViewWidth(
    width: Int?,
    nullNewLp: Boolean?
) {
    if (width == null) return
    ViewUtils.setWidth(this, width, nullNewLp != false)
}

/**
 * 通过数据绑定设置高度（像素），可选 LayoutParams 为 null 时是否新建。
 * <pre>
 *     布局属性 binding_view_height_px、binding_view_height_null_new_lp（可选，默认 true）；对应 ViewUtils.setHeight。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_view_height_px", "binding_view_height_null_new_lp"],
    requireAll = false
)
fun View.bindingViewHeight(
    height: Int?,
    nullNewLp: Boolean?
) {
    if (height == null) return
    ViewUtils.setHeight(this, height, nullNewLp != false)
}

/**
 * 通过数据绑定设置最小高度。
 * <pre>
 *     布局属性 binding_view_minimum_height；对应 ViewUtils.setMinimumHeight。
 * </pre>
 */
@BindingAdapter("binding_view_minimum_height")
fun View.bindingViewMinimumHeight(minHeight: Int) {
    ViewUtils.setMinimumHeight(this, minHeight)
}

/**
 * 通过数据绑定设置最小宽度。
 * <pre>
 *     布局属性 binding_view_minimum_width；对应 ViewUtils.setMinimumWidth。
 * </pre>
 */
@BindingAdapter("binding_view_minimum_width")
fun View.bindingViewMinimumWidth(minWidth: Int) {
    ViewUtils.setMinimumWidth(this, minWidth)
}

// ===========
// = 外观变换 =
// ===========

/**
 * 通过数据绑定设置透明度。
 * <pre>
 *     布局属性 binding_view_alpha；对应 ViewUtils.setAlpha。
 * </pre>
 */
@BindingAdapter("binding_view_alpha")
fun View.bindingViewAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float) {
    ViewUtils.setAlpha(this, alpha)
}

/**
 * 通过数据绑定设置 tag。
 * <pre>
 *     布局属性 binding_view_tag；对应 ViewUtils.setTag。
 * </pre>
 */
@BindingAdapter("binding_view_tag")
fun View.bindingViewTag(tag: Any?) {
    ViewUtils.setTag(this, tag)
}

/**
 * 通过数据绑定设置子 View 焦点传递策略。
 * <pre>
 *     布局属性 binding_view_descendant_focusability；仅 ViewGroup；对应 ViewUtils.setDescendantFocusability。
 * </pre>
 */
@BindingAdapter("binding_view_descendant_focusability")
fun ViewGroup.bindingViewDescendantFocusability(focusability: Int) {
    ViewUtils.setDescendantFocusability(this, focusability)
}

/**
 * 通过数据绑定设置边缘过度滚动模式。
 * <pre>
 *     布局属性 binding_view_over_scroll_mode；对应 ViewUtils.setOverScrollMode。
 * </pre>
 */
@BindingAdapter("binding_view_over_scroll_mode")
fun View.bindingViewOverScrollMode(mode: Int) {
    ViewUtils.setOverScrollMode(this, mode)
}

/**
 * 通过数据绑定设置是否绘制横向滚动条。
 * <pre>
 *     布局属性 binding_view_horizontal_scroll_bar_enabled；对应 ViewUtils.setHorizontalScrollBarEnabled。
 * </pre>
 */
@BindingAdapter("binding_view_horizontal_scroll_bar_enabled")
fun View.bindingViewHorizontalScrollBarEnabled(enabled: Boolean) {
    ViewUtils.setHorizontalScrollBarEnabled(this, enabled)
}

/**
 * 通过数据绑定设置是否绘制纵向滚动条。
 * <pre>
 *     布局属性 binding_view_vertical_scroll_bar_enabled；对应 ViewUtils.setVerticalScrollBarEnabled。
 * </pre>
 */
@BindingAdapter("binding_view_vertical_scroll_bar_enabled")
fun View.bindingViewVerticalScrollBarEnabled(enabled: Boolean) {
    ViewUtils.setVerticalScrollBarEnabled(this, enabled)
}

/**
 * 通过数据绑定设置是否为滚动容器。
 * <pre>
 *     布局属性 binding_view_scroll_container；对应 ViewUtils.setScrollContainer。
 * </pre>
 */
@BindingAdapter("binding_view_scroll_container")
fun View.bindingViewScrollContainer(isScrollContainer: Boolean) {
    ViewUtils.setScrollContainer(this, isScrollContainer)
}

/**
 * 通过数据绑定设置是否按 Outline 裁剪。
 * <pre>
 *     布局属性 binding_view_clip_to_outline；对应 ViewUtils.setClipToOutline。
 * </pre>
 */
@BindingAdapter("binding_view_clip_to_outline")
fun View.bindingViewClipToOutline(clipToOutline: Boolean) {
    ViewUtils.setClipToOutline(this, clipToOutline)
}

/**
 * 通过数据绑定设置 OutlineProvider。
 * <pre>
 *     布局属性 binding_view_outline_provider；null 时亦调用 ViewUtils.setOutlineProvider；行为由系统与工具类处理。
 * </pre>
 */
@BindingAdapter("binding_view_outline_provider")
fun View.bindingViewOutlineProvider(provider: ViewOutlineProvider?) {
    ViewUtils.setOutlineProvider(this, provider)
}

/**
 * 通过数据绑定设置 OutlineProvider 并开启裁剪。
 * <pre>
 *     布局属性 binding_view_outline_provider_clip；对应 ViewUtils.setOutlineProviderClip。
 * </pre>
 */
@BindingAdapter("binding_view_outline_provider_clip")
fun View.bindingViewOutlineProviderClip(provider: ViewOutlineProvider?) {
    ViewUtils.setOutlineProviderClip(this, provider)
}

// ===========
// = 焦点导航 =
// ===========

/**
 * 通过数据绑定设置下一个前向焦点 id。
 * <pre>
 *     布局属性 binding_view_next_focus_forward_id；对应 ViewUtils.setNextFocusForwardId。
 * </pre>
 */
@BindingAdapter("binding_view_next_focus_forward_id")
fun View.bindingViewNextFocusForwardId(@IdRes nextFocusForwardId: Int) {
    ViewUtils.setNextFocusForwardId(this, nextFocusForwardId)
}

/**
 * 通过数据绑定设置向下焦点导航目标 id。
 * <pre>
 *     布局属性 binding_view_next_focus_down_id；对应 ViewUtils.setNextFocusDownId。
 * </pre>
 */
@BindingAdapter("binding_view_next_focus_down_id")
fun View.bindingViewNextFocusDownId(@IdRes nextFocusDownId: Int) {
    ViewUtils.setNextFocusDownId(this, nextFocusDownId)
}

/**
 * 通过数据绑定设置向左焦点导航目标 id。
 * <pre>
 *     布局属性 binding_view_next_focus_left_id；对应 ViewUtils.setNextFocusLeftId。
 * </pre>
 */
@BindingAdapter("binding_view_next_focus_left_id")
fun View.bindingViewNextFocusLeftId(@IdRes nextFocusLeftId: Int) {
    ViewUtils.setNextFocusLeftId(this, nextFocusLeftId)
}

/**
 * 通过数据绑定设置向右焦点导航目标 id。
 * <pre>
 *     布局属性 binding_view_next_focus_right_id；对应 ViewUtils.setNextFocusRightId。
 * </pre>
 */
@BindingAdapter("binding_view_next_focus_right_id")
fun View.bindingViewNextFocusRightId(@IdRes nextFocusRightId: Int) {
    ViewUtils.setNextFocusRightId(this, nextFocusRightId)
}

/**
 * 通过数据绑定设置向上焦点导航目标 id。
 * <pre>
 *     布局属性 binding_view_next_focus_up_id；对应 ViewUtils.setNextFocusUpId。
 * </pre>
 */
@BindingAdapter("binding_view_next_focus_up_id")
fun View.bindingViewNextFocusUpId(@IdRes nextFocusUpId: Int) {
    ViewUtils.setNextFocusUpId(this, nextFocusUpId)
}

// ===========
// = 变换矩阵 =
// ===========

/**
 * 通过数据绑定设置 Z 轴旋转角。
 * <pre>
 *     布局属性 binding_view_rotation；对应 ViewUtils.setRotation。
 * </pre>
 */
@BindingAdapter("binding_view_rotation")
fun View.bindingViewRotation(rotation: Float) {
    ViewUtils.setRotation(this, rotation)
}

/**
 * 通过数据绑定设置绕 X 轴旋转角。
 * <pre>
 *     布局属性 binding_view_rotation_x；对应 ViewUtils.setRotationX。
 * </pre>
 */
@BindingAdapter("binding_view_rotation_x")
fun View.bindingViewRotationX(rotationX: Float) {
    ViewUtils.setRotationX(this, rotationX)
}

/**
 * 通过数据绑定设置绕 Y 轴旋转角。
 * <pre>
 *     布局属性 binding_view_rotation_y；对应 ViewUtils.setRotationY。
 * </pre>
 */
@BindingAdapter("binding_view_rotation_y")
fun View.bindingViewRotationY(rotationY: Float) {
    ViewUtils.setRotationY(this, rotationY)
}

/**
 * 通过数据绑定设置水平缩放。
 * <pre>
 *     布局属性 binding_view_scale_x；对应 ViewUtils.setScaleX。
 * </pre>
 */
@BindingAdapter("binding_view_scale_x")
fun View.bindingViewScaleX(scaleX: Float) {
    ViewUtils.setScaleX(this, scaleX)
}

/**
 * 通过数据绑定设置垂直缩放。
 * <pre>
 *     布局属性 binding_view_scale_y；对应 ViewUtils.setScaleY。
 * </pre>
 */
@BindingAdapter("binding_view_scale_y")
fun View.bindingViewScaleY(scaleY: Float) {
    ViewUtils.setScaleY(this, scaleY)
}

/**
 * 通过数据绑定设置文本对齐（View 层语义）。
 * <pre>
 *     布局属性 binding_view_text_alignment；API 17 以下忽略；对应 ViewUtils.setTextAlignment。
 * </pre>
 */
@BindingAdapter("binding_view_text_alignment")
fun View.bindingViewTextAlignment(textAlignment: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        ViewUtils.setTextAlignment(this, textAlignment)
    }
}

/**
 * 通过数据绑定设置文本方向。
 * <pre>
 *     布局属性 binding_view_text_direction；API 17 以下忽略；对应 ViewUtils.setTextDirection。
 * </pre>
 */
@BindingAdapter("binding_view_text_direction")
fun View.bindingViewTextDirection(textDirection: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        ViewUtils.setTextDirection(this, textDirection)
    }
}

/**
 * 通过数据绑定设置变换锚点 X。
 * <pre>
 *     布局属性 binding_view_pivot_x；对应 ViewUtils.setPivotX。
 * </pre>
 */
@BindingAdapter("binding_view_pivot_x")
fun View.bindingViewPivotX(pivotX: Float) {
    ViewUtils.setPivotX(this, pivotX)
}

/**
 * 通过数据绑定设置变换锚点 Y。
 * <pre>
 *     布局属性 binding_view_pivot_y；对应 ViewUtils.setPivotY。
 * </pre>
 */
@BindingAdapter("binding_view_pivot_y")
fun View.bindingViewPivotY(pivotY: Float) {
    ViewUtils.setPivotY(this, pivotY)
}

/**
 * 通过数据绑定设置 translationX。
 * <pre>
 *     布局属性 binding_view_translation_x；对应 ViewUtils.setTranslationX。
 * </pre>
 */
@BindingAdapter("binding_view_translation_x")
fun View.bindingViewTranslationX(translationX: Float) {
    ViewUtils.setTranslationX(this, translationX)
}

/**
 * 通过数据绑定设置 translationY。
 * <pre>
 *     布局属性 binding_view_translation_y；对应 ViewUtils.setTranslationY。
 * </pre>
 */
@BindingAdapter("binding_view_translation_y")
fun View.bindingViewTranslationY(translationY: Float) {
    ViewUtils.setTranslationY(this, translationY)
}

/**
 * 通过数据绑定设置左侧绘制坐标 X。
 * <pre>
 *     布局属性 binding_view_x；对应 ViewUtils.setX。
 * </pre>
 */
@BindingAdapter("binding_view_x")
fun View.bindingViewX(x: Float) {
    ViewUtils.setX(this, x)
}

/**
 * 通过数据绑定设置顶部绘制坐标 Y。
 * <pre>
 *     布局属性 binding_view_y；对应 ViewUtils.setY。
 * </pre>
 */
@BindingAdapter("binding_view_y")
fun View.bindingViewY(y: Float) {
    ViewUtils.setY(this, y)
}

/**
 * 通过数据绑定设置硬件分层类型与可选 Paint。
 * <pre>
 *     布局属性 binding_view_layer_type、binding_view_layer_paint（可选）；layerType 为 null 时不调用；对应 ViewUtils.setLayerType。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_view_layer_type", "binding_view_layer_paint"],
    requireAll = false
)
fun View.bindingViewLayerType(
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
 *     布局属性 binding_view_focusable_in_touch_mode；对应 ViewUtils.setFocusableInTouchMode。
 * </pre>
 */
@BindingAdapter("binding_view_focusable_in_touch_mode")
fun View.bindingViewFocusableInTouchMode(focusableInTouchMode: Boolean) {
    ViewUtils.setFocusableInTouchMode(focusableInTouchMode, this)
}

/**
 * 通过数据绑定设置是否可聚焦。
 * <pre>
 *     布局属性 binding_view_focusable；对应 ViewUtils.setFocusable。
 * </pre>
 */
@BindingAdapter("binding_view_focusable")
fun View.bindingViewFocusable(focusable: Boolean) {
    ViewUtils.setFocusable(focusable, this)
}

/**
 * 通过数据绑定设置选中状态。
 * <pre>
 *     布局属性 binding_view_selected；对应 ViewUtils.setSelected。
 * </pre>
 */
@BindingAdapter("binding_view_selected")
fun View.bindingViewSelected(selected: Boolean) {
    ViewUtils.setSelected(selected, this)
}

/**
 * 通过数据绑定设置是否启用。
 * <pre>
 *     布局属性 binding_view_enabled；对应 ViewUtils.setEnabled。
 * </pre>
 */
@BindingAdapter("binding_view_enabled")
fun View.bindingViewEnabled(enabled: Boolean) {
    ViewUtils.setEnabled(enabled, this)
}

/**
 * 通过数据绑定设置是否可点击。
 * <pre>
 *     布局属性 binding_view_clickable；对应 ViewUtils.setClickable。
 * </pre>
 */
@BindingAdapter("binding_view_clickable")
fun View.bindingViewClickable(clickable: Boolean) {
    ViewUtils.setClickable(clickable, this)
}

/**
 * 通过数据绑定设置是否可长按。
 * <pre>
 *     布局属性 binding_view_long_clickable；对应 ViewUtils.setLongClickable。
 * </pre>
 */
@BindingAdapter("binding_view_long_clickable")
fun View.bindingViewLongClickable(longClickable: Boolean) {
    ViewUtils.setLongClickable(longClickable, this)
}

/**
 * 通过数据绑定切换 VISIBLE / GONE（与 ViewUtils.setVisibility(boolean, View) 一致）。
 * <pre>
 *     布局属性 binding_view_visibility_bool；true 为 VISIBLE，false 为 GONE；对应 ViewUtils.setVisibility(boolean, View)。
 * </pre>
 */
@BindingAdapter("binding_view_visibility_bool")
fun View.bindingViewVisibilityBool(isVisibility: Boolean) {
    ViewUtils.setVisibility(isVisibility, this)
}

/**
 * 通过数据绑定设置可见性整型常量。
 * <pre>
 *     布局属性 binding_view_visibility_int；对应 ViewUtils.setVisibility(int, View)。
 * </pre>
 */
@BindingAdapter("binding_view_visibility_int")
fun View.bindingViewVisibilityInt(visibility: Int) {
    ViewUtils.setVisibility(visibility, this)
}

/**
 * 通过数据绑定切换 VISIBLE / INVISIBLE（与 ViewUtils.setVisibilityIN 一致）。
 * <pre>
 *     布局属性 binding_view_visibility_in_bool；对应 ViewUtils.setVisibilityIN。
 * </pre>
 */
@BindingAdapter("binding_view_visibility_in_bool")
fun View.bindingViewVisibilityInBool(isVisibility: Boolean) {
    ViewUtils.setVisibilityIN(isVisibility, this)
}

// ===========
// = Gravity =
// ===========

/**
 * 通过数据绑定设置子项在父容器中的 layout gravity。
 * <pre>
 *     布局属性 binding_view_layout_gravity、binding_view_layout_gravity_reflection（可选，默认 true）；对应 ViewUtils.setLayoutGravity。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_view_layout_gravity", "binding_view_layout_gravity_reflection"],
    requireAll = false
)
fun View.bindingViewLayoutGravity(
    gravity: Int?,
    isReflection: Boolean?
) {
    if (gravity == null) return
    ViewUtils.setLayoutGravity(this, gravity, isReflection != false)
}

// =============
// = 动画与背景 =
// =============

/**
 * 通过数据绑定设置或清除 View 动画。
 * <pre>
 *     布局属性 binding_view_animation；null 时对应 ViewUtils.clearAnimation；非 null 时对应 ViewUtils.setAnimation。
 * </pre>
 */
@BindingAdapter("binding_view_animation")
fun View.bindingViewAnimation(animation: Animation?) {
    if (animation == null) {
        ViewUtils.clearAnimation(this)
    } else {
        ViewUtils.setAnimation(this, animation)
    }
}

/**
 * 通过数据绑定显式清空 View 动画。
 * <pre>
 *     布局属性 binding_view_clear_animation；为 true 时调用 ViewUtils.clearAnimation；false 或 null 时不调用。
 *     若需同值多次触发，改用 binding_view_clear_animation_ts 并绑定正时间戳。
 * </pre>
 *
 * @param clear 是否执行清空（便于与 `binding_view_animation` 分路控制）
 */
@BindingAdapter("binding_view_clear_animation")
fun View.bindingViewClearAnimation(clear: Boolean?) {
    if (clear != true) return
    ViewUtils.clearAnimation(this)
}

/**
 * 通过数据绑定设置背景 Drawable。
 * <pre>
 *     布局属性 binding_view_background；null 时对应 ViewUtils.removeBackground；非 null 时对应 ViewUtils.setBackground。
 * </pre>
 */
@BindingAdapter("binding_view_background")
fun View.bindingViewBackground(background: Drawable?) {
    if (background == null) {
        ViewUtils.removeBackground(this)
    } else {
        ViewUtils.setBackground(this, background)
    }
}

/**
 * 通过数据绑定显式移除背景 Drawable。
 * <pre>
 *     布局属性 binding_view_remove_background；为 true 时调用 ViewUtils.removeBackground；false 或 null 时不调用。
 *     若需同值多次触发，改用 binding_view_remove_background_ts 并绑定正时间戳。
 * </pre>
 *
 * @param remove 是否移除背景
 */
@BindingAdapter("binding_view_remove_background")
fun View.bindingViewRemoveBackground(remove: Boolean?) {
    if (remove != true) return
    ViewUtils.removeBackground(this)
}

/**
 * 通过数据绑定设置背景色。
 * <pre>
 *     布局属性 binding_view_background_color；对应 ViewUtils.setBackgroundColor。
 * </pre>
 */
@BindingAdapter("binding_view_background_color")
fun View.bindingViewBackgroundColor(@ColorInt color: Int) {
    ViewUtils.setBackgroundColor(this, color)
}

/**
 * 通过数据绑定设置背景资源 id。
 * <pre>
 *     布局属性 binding_view_background_resource；0 或无效时可传 0 仍走 ViewUtils；对应 ViewUtils.setBackgroundResource。
 * </pre>
 */
@BindingAdapter("binding_view_background_resource")
fun View.bindingViewBackgroundResource(@DrawableRes resId: Int) {
    ViewUtils.setBackgroundResource(this, resId)
}

/**
 * 通过数据绑定设置背景着色。
 * <pre>
 *     布局属性 binding_view_background_tint_list；API 21 以下忽略；对应 ViewUtils.setBackgroundTintList。
 * </pre>
 */
@BindingAdapter("binding_view_background_tint_list")
fun View.bindingViewBackgroundTintList(tint: ColorStateList?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        ViewUtils.setBackgroundTintList(this, tint)
    }
}

/**
 * 通过数据绑定设置背景着色模式。
 * <pre>
 *     布局属性 binding_view_background_tint_mode；API 21 以下忽略；null 时不调用；对应 ViewUtils.setBackgroundTintMode。
 * </pre>
 */
@BindingAdapter("binding_view_background_tint_mode")
fun View.bindingViewBackgroundTintMode(tintMode: PorterDuff.Mode?) {
    if (tintMode == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return
    ViewUtils.setBackgroundTintMode(this, tintMode)
}

// ===========
// = 前景 =
// ===========

/**
 * 通过数据绑定设置前景 Drawable（API 23+）。
 * <pre>
 *     布局属性 binding_view_foreground；null 时对应 ViewUtils.removeForeground；非 null 时对应 ViewUtils.setForeground。
 * </pre>
 */
@BindingAdapter("binding_view_foreground")
fun View.bindingViewForeground(foreground: Drawable?) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
    if (foreground == null) {
        ViewUtils.removeForeground(this)
    } else {
        ViewUtils.setForeground(this, foreground)
    }
}

/**
 * 通过数据绑定显式移除前景 Drawable（API 23+）。
 * <pre>
 *     布局属性 binding_view_remove_foreground；为 true 时调用 ViewUtils.removeForeground；false 或 null 时不调用。
 *     API 23 以下忽略。
 *     若需同值多次触发，改用 binding_view_remove_foreground_ts 并绑定正时间戳。
 * </pre>
 *
 * @param remove 是否移除前景
 */
@BindingAdapter("binding_view_remove_foreground")
fun View.bindingViewRemoveForeground(remove: Boolean?) {
    if (remove != true) return
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
    ViewUtils.removeForeground(this)
}

/**
 * 通过数据绑定设置前景重心（API 23+）。
 * <pre>
 *     布局属性 binding_view_foreground_gravity；对应 ViewUtils.setForegroundGravity。
 * </pre>
 */
@BindingAdapter("binding_view_foreground_gravity")
fun View.bindingViewForegroundGravity(gravity: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        ViewUtils.setForegroundGravity(this, gravity)
    }
}

/**
 * 通过数据绑定设置前景着色（API 23+）。
 * <pre>
 *     布局属性 binding_view_foreground_tint_list；对应 ViewUtils.setForegroundTintList。
 * </pre>
 */
@BindingAdapter("binding_view_foreground_tint_list")
fun View.bindingViewForegroundTintList(tint: ColorStateList?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        ViewUtils.setForegroundTintList(this, tint)
    }
}

/**
 * 通过数据绑定设置前景着色模式（API 23+）。
 * <pre>
 *     布局属性 binding_view_foreground_tint_mode；null 时不调用；对应 ViewUtils.setForegroundTintMode。
 * </pre>
 */
@BindingAdapter("binding_view_foreground_tint_mode")
fun View.bindingViewForegroundTintMode(tintMode: PorterDuff.Mode?) {
    if (tintMode == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
    ViewUtils.setForegroundTintMode(this, tintMode)
}

// ==============
// = 着色与进度条 =
// ==============

/**
 * 通过数据绑定按颜色过滤背景 Drawable 并设回背景。
 * <pre>
 *     布局属性 binding_view_color_filter；对应 ViewUtils.setColorFilter(view, color)。
 * </pre>
 */
@BindingAdapter("binding_view_color_filter")
fun View.bindingViewColorFilter(@ColorInt color: Int) {
    ViewUtils.setColorFilter(this, color)
}

/**
 * 通过数据绑定对指定 Drawable 着色并设为背景。
 * <pre>
 *     布局属性 binding_view_color_filter_drawable、binding_view_color_filter_drawable_color；对应 ViewUtils.setColorFilter(view, drawable, color)。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_view_color_filter_drawable", "binding_view_color_filter_drawable_color"],
    requireAll = true
)
fun View.bindingViewColorFilterDrawable(
    drawable: Drawable,
    @ColorInt color: Int
) {
    ViewUtils.setColorFilter(this, drawable, color)
}

/**
 * 通过数据绑定使用 ColorFilter 处理当前背景。
 * <pre>
 *     布局属性 binding_view_color_filter_object；对应 ViewUtils.setColorFilter(view, colorFilter)。
 * </pre>
 */
@BindingAdapter("binding_view_color_filter_object")
fun View.bindingViewColorFilterObject(colorFilter: ColorFilter?) {
    if (colorFilter == null) return
    ViewUtils.setColorFilter(this, colorFilter)
}

/**
 * 通过数据绑定对 Drawable 应用 ColorFilter 并设为背景。
 * <pre>
 *     布局属性 binding_view_color_filter_drawable_object、binding_view_color_filter_drawable_filter；对应 ViewUtils.setColorFilter(view, drawable, filter)。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_view_color_filter_drawable_object", "binding_view_color_filter_drawable_filter"],
    requireAll = true
)
fun View.bindingViewColorFilterDrawableObject(
    drawable: Drawable,
    colorFilter: ColorFilter
) {
    ViewUtils.setColorFilter(this, drawable, colorFilter)
}

/**
 * 通过数据绑定设置 ProgressBar 的进度 Drawable（若不是 ProgressBar 则工具类内部忽略）。
 * <pre>
 *     布局属性 binding_view_progress_drawable；对应 ViewUtils.setProgressDrawable。
 * </pre>
 */
@BindingAdapter("binding_view_progress_drawable")
fun View.bindingViewProgressDrawable(drawable: Drawable?) {
    if (drawable == null) return
    ViewUtils.setProgressDrawable(this, drawable)
}

/**
 * 通过数据绑定设置 ProgressBar 当前进度。
 * <pre>
 *     布局属性 binding_view_bar_progress；对应 ViewUtils.setBarProgress。
 * </pre>
 */
@BindingAdapter("binding_view_bar_progress")
fun View.bindingViewBarProgress(progress: Int) {
    ViewUtils.setBarProgress(this, progress)
}

/**
 * 通过数据绑定设置 ProgressBar 最大值。
 * <pre>
 *     布局属性 binding_view_bar_max；对应 ViewUtils.setBarMax。
 * </pre>
 */
@BindingAdapter("binding_view_bar_max")
fun View.bindingViewBarMax(max: Int) {
    ViewUtils.setBarMax(this, max)
}

/**
 * 通过数据绑定同时设置 ProgressBar 的最大值与当前进度。
 * <pre>
 *     布局属性 binding_view_bar_progress_value、binding_view_bar_max_value；对应 ViewUtils.setBarValue。
 *     亦可改用单属性 binding_view_bar_progress_state 与 [BarProgressState]。
 * </pre>
 */
@BindingAdapter(
    value = ["binding_view_bar_progress_value", "binding_view_bar_max_value"],
    requireAll = true
)
fun View.bindingViewBarValue(
    progress: Int,
    max: Int
) {
    ViewUtils.setBarValue(this, progress, max)
}

/**
 * 通过数据绑定以单属性同时设置 ProgressBar 的最大值与当前进度。
 * <pre>
 *     布局属性 binding_view_bar_progress_state；为 null 时不调用；与双属性绑定等价；对应 ViewUtils.setBarValue。
 * </pre>
 *
 * @param state 当前进度与最大值
 */
@BindingAdapter("binding_view_bar_progress_state")
fun View.bindingViewBarProgressState(state: BarProgressState?) {
    if (state == null) return
    ViewUtils.setBarValue(this, state.progress, state.max)
}