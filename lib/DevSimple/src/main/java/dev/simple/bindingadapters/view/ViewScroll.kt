package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.simple.bindingadapters.BindingTrigger.shouldTriggerScroll
import dev.simple.bindingadapters.view.attribute.XYI
import dev.utils.app.ListViewUtils
import dev.utils.app.RecyclerViewUtils

// ==============================
// = View Scroll BindingAdapter =
// ==============================

// =====================
// = RecyclerViewUtils =
// =====================

/**
 * 数据绑定触发 RecyclerView 平滑滚动并使目标项与列表顶部对齐。
 * <pre>
 *     布局属性：binding_scroll_rv_snap_start_index
 *     对应 [RecyclerViewUtils.startSmoothScrollSnapStart]；position 为 null 或小于 0 时跳过；需 [androidx.recyclerview.widget.LinearLayoutManager]。
 * </pre>
 *
 * @param position 目标 adapter 索引，可为 null
 * @return `true` 滚动已发起或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_rv_snap_start_index")
fun RecyclerView.bindingScrollRvSnapStartIndex(position: Int?): Boolean {
    val p = position ?: return false
    if (p < 0) return false
    return RecyclerViewUtils.startSmoothScrollSnapStart(this, p)
}

/**
 * 数据绑定触发 RecyclerView 平滑滚向顶部吸附区域。
 * <pre>
 *     布局属性：binding_scroll_rv_snap_start_auto
 *     对应无 position 重载的 [RecyclerViewUtils.startSmoothScrollSnapStart]；timestamp 为 null 或不大于 0 时跳过；需 [androidx.recyclerview.widget.LinearLayoutManager]。
 *     建议绑定 `System.currentTimeMillis()` 或递增时间戳，便于同操作多次触发（优于 LiveData Boolean 同 true 不刷新）。
 * </pre>
 *
 * @param timestamp 触发用时间戳，可为 null，大于 0 时执行一次
 * @return `true` 滚动已发起或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_rv_snap_start_auto")
fun RecyclerView.bindingScrollRvSnapStartAuto(timestamp: Long?): Boolean {
    if (!timestamp.shouldTriggerScroll()) return false
    return RecyclerViewUtils.startSmoothScrollSnapStart(this)
}

/**
 * 数据绑定触发 RecyclerView 平滑滚动并使目标项与列表底部对齐。
 * <pre>
 *     布局属性：binding_scroll_rv_snap_end_index
 *     对应 [RecyclerViewUtils.startSmoothScrollSnapEnd]；position 为 null 或小于 0 时跳过；需 [androidx.recyclerview.widget.LinearLayoutManager]。
 * </pre>
 *
 * @param position 目标 adapter 索引，可为 null
 * @return `true` 滚动已发起或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_rv_snap_end_index")
fun RecyclerView.bindingScrollRvSnapEndIndex(position: Int?): Boolean {
    val p = position ?: return false
    if (p < 0) return false
    return RecyclerViewUtils.startSmoothScrollSnapEnd(this, p)
}

/**
 * 数据绑定触发 RecyclerView 平滑滚向底部吸附区域。
 * <pre>
 *     布局属性：binding_scroll_rv_snap_end_auto
 *     对应无 position 重载的 [RecyclerViewUtils.startSmoothScrollSnapEnd]；timestamp 为 null 或不大于 0 时跳过；需 [androidx.recyclerview.widget.LinearLayoutManager]。
 *     建议绑定时间戳以便重复触发到底部。
 * </pre>
 *
 * @param timestamp 触发用时间戳，可为 null，大于 0 时执行一次
 * @return `true` 滚动已发起或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_rv_snap_end_auto")
fun RecyclerView.bindingScrollRvSnapEndAuto(timestamp: Long?): Boolean {
    if (!timestamp.shouldTriggerScroll()) return false
    return RecyclerViewUtils.startSmoothScrollSnapEnd(this)
}

/**
 * 数据绑定将指定项滚入可视区并附加像素偏移。
 * <pre>
 *     布局属性：binding_scroll_rv_linear_index、binding_scroll_rv_linear_offset，requireAll 为 false
 *     对应 [RecyclerViewUtils.scrollToPositionWithOffset]；position 无效则跳过；offset 缺省为 0；需 [androidx.recyclerview.widget.LinearLayoutManager]。
 * </pre>
 *
 * @param position 目标 adapter 索引，可为 null
 * @param offsetPx 偏移像素，可为 null，缺省为 0
 * @return `true` 滚动已发起或视为成功，`false` 跳过或失败
 */
@BindingAdapter(
    value = ["binding_scroll_rv_linear_index", "binding_scroll_rv_linear_offset"],
    requireAll = false
)
fun RecyclerView.bindingScrollRvLinearIndexOffset(
    position: Int?,
    offsetPx: Int?
): Boolean {
    val p = position ?: return false
    if (p < 0) return false
    val off = offsetPx ?: 0
    return RecyclerViewUtils.scrollToPositionWithOffset(this, p, off)
}

/**
 * 数据绑定停止 RecyclerView 当前平滑滚动与拖动及惯性滚动。
 * <pre>
 *     布局属性：binding_scroll_rv_stop
 *     对应 [RecyclerViewUtils.stopSmoothScroller]；timestamp 为 null 或不大于 0 时跳过。
 *     建议绑定时间戳以便重复触发停止（含打断平滑动画）。
 * </pre>
 *
 * @param timestamp 触发用时间戳，可为 null，大于 0 时执行一次
 * @return `true` 已尝试停止或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_rv_stop")
fun RecyclerView.bindingScrollRvStop(timestamp: Long?): Boolean {
    if (!timestamp.shouldTriggerScroll()) return false
    return RecyclerViewUtils.stopSmoothScroller(this)
}

// =================
// = ListViewUtils =
// =================

// ========================================
// = 入参为 View，适用于 RV、NSV、ListView 等 =
// ========================================

/**
 * 数据绑定平滑滑动到指定 adapter 或列表项索引。
 * <pre>
 *     布局属性：binding_scroll_smooth_adapter_index
 *     接收者为任意 [View]，内部由 [ListViewUtils.smoothScrollToPosition] 按类型分支处理；index 为 null 或小于 0 时跳过。
 * </pre>
 *
 * @param index 目标索引，可为 null
 * @return `true` 滚动已发起或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_smooth_adapter_index")
fun View.bindingScrollSmoothAdapterIndex(index: Int?): Boolean {
    val i = index ?: return false
    if (i < 0) return false
    return ListViewUtils.smoothScrollToPosition(this, i)
}

/**
 * 数据绑定无动画滚动到指定 adapter 或列表项索引。
 * <pre>
 *     布局属性：binding_scroll_instant_adapter_index
 *     对应 [ListViewUtils.scrollToPosition]；index 为 null 或小于 0 时跳过。
 * </pre>
 *
 * @param index 目标索引，可为 null
 * @return `true` 滚动已发起或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_instant_adapter_index")
fun View.bindingScrollInstantAdapterIndex(index: Int?): Boolean {
    val i = index ?: return false
    if (i < 0) return false
    return ListViewUtils.scrollToPosition(this, i)
}

/**
 * 数据绑定触发平滑滚动到内容或列表顶部。
 * <pre>
 *     布局属性：binding_scroll_event_smooth_top
 *     对应 [ListViewUtils.smoothScrollToTop]；timestamp 为 null 或不大于 0 时跳过。
 *     建议绑定时间戳，便于每次新值都触发（优于 LiveData Boolean 同 true 不刷新）。
 * </pre>
 *
 * @param timestamp 触发用时间戳，可为 null，大于 0 时执行一次
 * @return `true` 滚动已发起或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_event_smooth_top")
fun View.bindingScrollEventSmoothTop(timestamp: Long?): Boolean {
    if (!timestamp.shouldTriggerScroll()) return false
    return ListViewUtils.smoothScrollToTop(this)
}

/**
 * 数据绑定触发瞬时滚动到内容或列表顶部。
 * <pre>
 *     布局属性：binding_scroll_event_instant_top
 *     对应 [ListViewUtils.scrollToTop]；timestamp 为 null 或不大于 0 时跳过。
 *     建议绑定时间戳以便重复触发。
 * </pre>
 *
 * @param timestamp 触发用时间戳，可为 null，大于 0 时执行一次
 * @return `true` 滚动已发起或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_event_instant_top")
fun View.bindingScrollEventInstantTop(timestamp: Long?): Boolean {
    if (!timestamp.shouldTriggerScroll()) return false
    return ListViewUtils.scrollToTop(this)
}

/**
 * 数据绑定触发平滑滚动到内容或列表底部。
 * <pre>
 *     布局属性：binding_scroll_event_smooth_bottom
 *     对应 [ListViewUtils.smoothScrollToBottom]；timestamp 为 null 或不大于 0 时跳过。
 *     建议绑定时间戳以便重复滑到底部。
 * </pre>
 *
 * @param timestamp 触发用时间戳，可为 null，大于 0 时执行一次
 * @return `true` 滚动已发起或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_event_smooth_bottom")
fun View.bindingScrollEventSmoothBottom(timestamp: Long?): Boolean {
    if (!timestamp.shouldTriggerScroll()) return false
    return ListViewUtils.smoothScrollToBottom(this)
}

/**
 * 数据绑定触发瞬时滚动到内容或列表底部。
 * <pre>
 *     布局属性：binding_scroll_event_instant_bottom
 *     对应 [ListViewUtils.scrollToBottom]；timestamp 为 null 或不大于 0 时跳过。
 *     建议绑定时间戳以便重复触发。
 * </pre>
 *
 * @param timestamp 触发用时间戳，可为 null，大于 0 时执行一次
 * @return `true` 滚动已发起或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_event_instant_bottom")
fun View.bindingScrollEventInstantBottom(timestamp: Long?): Boolean {
    if (!timestamp.shouldTriggerScroll()) return false
    return ListViewUtils.scrollToBottom(this)
}

/**
 * 数据绑定执行平滑绝对滚动。
 * <pre>
 *     布局属性：binding_scroll_smooth_abs_xy
 *     对应 [ListViewUtils.smoothScrollTo]；xy 为 null 时跳过；某轴为 [XYI.KEEP_SCROLL] 时保留当前
 *     scrollX / scrollY；两轴均为 KEEP_SCROLL 时跳过。
 * </pre>
 *
 * @param xy 目标坐标，x、y 为像素，可为 null
 * @return `true` 滚动已发起或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_smooth_abs_xy")
fun View.bindingScrollSmoothAbsXY(xy: XYI?): Boolean {
    if (xy == null) return false
    if (xy.x == XYI.KEEP_SCROLL && xy.y == XYI.KEEP_SCROLL) return false
    val tx = if (xy.x == XYI.KEEP_SCROLL) scrollX else xy.x
    val ty = if (xy.y == XYI.KEEP_SCROLL) scrollY else xy.y
    return ListViewUtils.smoothScrollTo(this, tx, ty)
}

/**
 * 数据绑定执行平滑相对滚动。
 * <pre>
 *     布局属性：binding_scroll_smooth_rel_xy
 *     对应 [ListViewUtils.smoothScrollBy]；xy 为 null 时跳过；x、y 分别为 dx、dy。
 * </pre>
 *
 * @param xy 相对位移，可为 null
 * @return `true` 滚动已发起或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_smooth_rel_xy")
fun View.bindingScrollSmoothRelDxDy(xy: XYI?): Boolean {
    if (xy == null) return false
    return ListViewUtils.smoothScrollBy(this, xy.x, xy.y)
}

/**
 * 数据绑定按焦点方向整段滚动。
 * <pre>
 *     布局属性：binding_scroll_full_direction
 *     对应 [ListViewUtils.fullScroll]；direction 为 null 时跳过；取值与 [View.FOCUS_UP]、[View.FOCUS_DOWN] 等一致。
 * </pre>
 *
 * @param direction 滚动方向常量，可为 null，详见备注
 * @return `true` 滚动已发起或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_full_direction")
fun View.bindingScrollFullDirection(direction: Int?): Boolean {
    val d = direction ?: return false
    return ListViewUtils.fullScroll(this, d)
}

/**
 * 数据绑定执行无动画绝对滚动。
 * <pre>
 *     布局属性：binding_scroll_instant_abs_xy
 *     对应 [ListViewUtils.scrollTo]；语义同平滑绝对滚动的 bindingScrollSmoothAbsXY。
 * </pre>
 *
 * @param xy 目标坐标，可为 null
 * @return `true` 滚动已发起或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_instant_abs_xy")
fun View.bindingScrollInstantAbsXY(xy: XYI?): Boolean {
    if (xy == null) return false
    if (xy.x == XYI.KEEP_SCROLL && xy.y == XYI.KEEP_SCROLL) return false
    val tx = if (xy.x == XYI.KEEP_SCROLL) scrollX else xy.x
    val ty = if (xy.y == XYI.KEEP_SCROLL) scrollY else xy.y
    return ListViewUtils.scrollTo(this, tx, ty)
}

/**
 * 数据绑定执行无动画相对滚动。
 * <pre>
 *     布局属性：binding_scroll_instant_rel_xy
 *     对应 [ListViewUtils.scrollBy]；语义同平滑相对滚动的 bindingScrollSmoothRelDxDy。
 * </pre>
 *
 * @param xy 相对位移，可为 null
 * @return `true` 滚动已发起或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_instant_rel_xy")
fun View.bindingScrollInstantRelDxDy(xy: XYI?): Boolean {
    if (xy == null) return false
    return ListViewUtils.scrollBy(this, xy.x, xy.y)
}

/**
 * 数据绑定设置内容水平滚动位置。
 * <pre>
 *     布局属性：binding_scroll_set_scroll_x
 *     对应 [ListViewUtils.setScrollX]；value 为 null 时跳过。
 * </pre>
 *
 * @param value 目标 scrollX，可为 null
 * @return `true` 已设置或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_set_scroll_x")
fun View.bindingScrollSetScrollX(value: Int?): Boolean {
    val v = value ?: return false
    return ListViewUtils.setScrollX(this, v)
}

/**
 * 数据绑定设置内容垂直滚动位置。
 * <pre>
 *     布局属性：binding_scroll_set_scroll_y
 *     对应 [ListViewUtils.setScrollY]；value 为 null 时跳过。
 * </pre>
 *
 * @param value 目标 scrollY，可为 null
 * @return `true` 已设置或视为成功，`false` 跳过或失败
 */
@BindingAdapter("binding_scroll_set_scroll_y")
fun View.bindingScrollSetScrollY(value: Int?): Boolean {
    val v = value ?: return false
    return ListViewUtils.setScrollY(this, v)
}