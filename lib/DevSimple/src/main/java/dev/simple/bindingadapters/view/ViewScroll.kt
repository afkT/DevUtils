package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.utils.app.ListViewUtils
import dev.utils.app.RecyclerViewUtils

// ==============================
// = View Scroll BindingAdapter =
// ==============================

/**
 * 供需重复触发的绑定使用：传入时间戳等 Long，仅在大于 0 时执行，避免 LiveData 同值不刷新。
 */
fun Long?.shouldTriggerScroll(): Boolean = this != null && this > 0L

// =====================
// = RecyclerViewUtils =
// =====================

/**
 * 数据绑定触发 RecyclerView 平滑滚动并使目标项与列表顶部对齐。
 * <pre>
 *     布局属性：binding_scroll_rv_snap_start_index
 *     对应 {@link RecyclerViewUtils#startSmoothScrollSnapStart(RecyclerView, int)}；position 为 null 或小于 0 时跳过；需 {@link androidx.recyclerview.widget.LinearLayoutManager}。
 * </pre>
 *
 * @param position 目标 adapter 索引
 * @return `true` success, `false` fail
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
 *     对应无 position 重载的 {@link RecyclerViewUtils#startSmoothScrollSnapStart(RecyclerView)}；timestamp 为 null 或不大于 0 时跳过；需 LinearLayoutManager。
 *     建议绑定 {@code System.currentTimeMillis()} 或递增时间戳，便于同操作多次触发（优于 LiveData Boolean 同 true 不刷新）。
 * </pre>
 *
 * @param timestamp 大于 0 时执行一次
 * @return `true` success, `false` fail
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
 *     对应 {@link RecyclerViewUtils#startSmoothScrollSnapEnd(RecyclerView, int)}；position 为 null 或小于 0 时跳过；需 LinearLayoutManager。
 * </pre>
 *
 * @param position 目标 adapter 索引
 * @return `true` success, `false` fail
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
 *     对应无 position 重载的 {@link RecyclerViewUtils#startSmoothScrollSnapEnd(RecyclerView)}；timestamp 为 null 或不大于 0 时跳过；需 LinearLayoutManager。
 *     建议绑定时间戳以便重复触发到底部。
 * </pre>
 *
 * @param timestamp 大于 0 时执行一次
 * @return `true` success, `false` fail
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
 *     对应 {@link RecyclerViewUtils#scrollToPositionWithOffset(RecyclerView, int, int)}；position 无效则跳过；offset 缺省为 0；需 LinearLayoutManager。
 * </pre>
 *
 * @param position 目标 adapter 索引
 * @param offsetPx 偏移像素
 * @return `true` success, `false` fail
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
 *     对应 {@link RecyclerViewUtils#stopSmoothScroller(RecyclerView)}；timestamp 为 null 或不大于 0 时跳过。
 *     建议绑定时间戳以便重复触发停止（含打断平滑动画）。
 * </pre>
 *
 * @param timestamp 大于 0 时执行一次
 * @return `true` success, `false` fail
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
 *     接收者为任意 {@link View}，内部由 {@link ListViewUtils#smoothScrollToPosition} 按类型分支处理；index 为 null 或小于 0 时跳过。
 * </pre>
 *
 * @param index 目标索引
 * @return `true` success, `false` fail
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
 *     对应 {@link ListViewUtils#scrollToPosition}；index 为 null 或小于 0 时跳过。
 * </pre>
 *
 * @param index 目标索引
 * @return `true` success, `false` fail
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
 *     对应 {@link ListViewUtils#smoothScrollToTop}；timestamp 为 null 或不大于 0 时跳过。
 *     建议绑定时间戳，便于每次新值都触发（优于 LiveData Boolean 同 true 不刷新）。
 * </pre>
 *
 * @param timestamp 大于 0 时执行一次
 * @return `true` success, `false` fail
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
 *     对应 {@link ListViewUtils#scrollToTop}；timestamp 为 null 或不大于 0 时跳过。
 *     建议绑定时间戳以便重复触发。
 * </pre>
 *
 * @param timestamp 大于 0 时执行一次
 * @return `true` success, `false` fail
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
 *     对应 {@link ListViewUtils#smoothScrollToBottom}；timestamp 为 null 或不大于 0 时跳过。
 *     建议绑定时间戳以便重复滑到底部。
 * </pre>
 *
 * @param timestamp 大于 0 时执行一次
 * @return `true` success, `false` fail
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
 *     对应 {@link ListViewUtils#scrollToBottom}；timestamp 为 null 或不大于 0 时跳过。
 *     建议绑定时间戳以便重复触发。
 * </pre>
 *
 * @param timestamp 大于 0 时执行一次
 * @return `true` success, `false` fail
 */
@BindingAdapter("binding_scroll_event_instant_bottom")
fun View.bindingScrollEventInstantBottom(timestamp: Long?): Boolean {
    if (!timestamp.shouldTriggerScroll()) return false
    return ListViewUtils.scrollToBottom(this)
}

/**
 * 数据绑定执行平滑绝对滚动。
 * <pre>
 *     布局属性：binding_scroll_smooth_abs_x、binding_scroll_smooth_abs_y，requireAll 为 false
 *     对应 {@link ListViewUtils#smoothScrollTo}；x、y 均为 null 时跳过；缺省保留当前 scrollX、scrollY。
 * </pre>
 *
 * @param x 目标 X
 * @param y 目标 Y
 * @return `true` success, `false` fail
 */
@BindingAdapter(
    value = ["binding_scroll_smooth_abs_x", "binding_scroll_smooth_abs_y"],
    requireAll = false
)
fun View.bindingScrollSmoothAbsXY(
    x: Int?,
    y: Int?
): Boolean {
    if (x == null && y == null) return false
    val tx = x ?: scrollX
    val ty = y ?: scrollY
    return ListViewUtils.smoothScrollTo(this, tx, ty)
}

/**
 * 数据绑定执行平滑相对滚动。
 * <pre>
 *     布局属性：binding_scroll_smooth_rel_dx、binding_scroll_smooth_rel_dy，requireAll 为 false
 *     对应 {@link ListViewUtils#smoothScrollBy}；dx、dy 均为 null 时跳过；缺省为 0。
 * </pre>
 *
 * @param dx X 增量
 * @param dy Y 增量
 * @return `true` success, `false` fail
 */
@BindingAdapter(
    value = ["binding_scroll_smooth_rel_dx", "binding_scroll_smooth_rel_dy"],
    requireAll = false
)
fun View.bindingScrollSmoothRelDxDy(
    dx: Int?,
    dy: Int?
): Boolean {
    if (dx == null && dy == null) return false
    val rdx = dx ?: 0
    val rdy = dy ?: 0
    return ListViewUtils.smoothScrollBy(this, rdx, rdy)
}

/**
 * 数据绑定按焦点方向整段滚动。
 * <pre>
 *     布局属性：binding_scroll_full_direction
 *     对应 {@link ListViewUtils#fullScroll}；direction 为 null 时跳过；取值与 {@link View#FOCUS_UP}、{@link View#FOCUS_DOWN} 等一致。
 * </pre>
 *
 * @param direction 滚动方向常量
 * @return `true` success, `false` fail
 */
@BindingAdapter("binding_scroll_full_direction")
fun View.bindingScrollFullDirection(direction: Int?): Boolean {
    val d = direction ?: return false
    return ListViewUtils.fullScroll(this, d)
}

/**
 * 数据绑定执行无动画绝对滚动。
 * <pre>
 *     布局属性：binding_scroll_instant_abs_x、binding_scroll_instant_abs_y，requireAll 为 false
 *     对应 {@link ListViewUtils#scrollTo}；x、y 均为 null 时跳过；缺省保留当前 scrollX、scrollY。
 * </pre>
 *
 * @param x 目标 X
 * @param y 目标 Y
 * @return `true` success, `false` fail
 */
@BindingAdapter(
    value = ["binding_scroll_instant_abs_x", "binding_scroll_instant_abs_y"],
    requireAll = false
)
fun View.bindingScrollInstantAbsXY(
    x: Int?,
    y: Int?
): Boolean {
    if (x == null && y == null) return false
    val tx = x ?: scrollX
    val ty = y ?: scrollY
    return ListViewUtils.scrollTo(this, tx, ty)
}

/**
 * 数据绑定执行无动画相对滚动。
 * <pre>
 *     布局属性：binding_scroll_instant_rel_dx、binding_scroll_instant_rel_dy，requireAll 为 false
 *     对应 {@link ListViewUtils#scrollBy}；dx、dy 均为 null 时跳过；缺省为 0。
 * </pre>
 *
 * @param dx X 增量
 * @param dy Y 增量
 * @return `true` success, `false` fail
 */
@BindingAdapter(
    value = ["binding_scroll_instant_rel_dx", "binding_scroll_instant_rel_dy"],
    requireAll = false
)
fun View.bindingScrollInstantRelDxDy(
    dx: Int?,
    dy: Int?
): Boolean {
    if (dx == null && dy == null) return false
    val rdx = dx ?: 0
    val rdy = dy ?: 0
    return ListViewUtils.scrollBy(this, rdx, rdy)
}

/**
 * 数据绑定设置内容水平滚动位置。
 * <pre>
 *     布局属性：binding_scroll_set_scroll_x
 *     对应 {@link ListViewUtils#setScrollX}；value 为 null 时跳过。
 * </pre>
 *
 * @param value 目标 scrollX
 * @return `true` success, `false` fail
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
 *     对应 {@link ListViewUtils#setScrollY}；value 为 null 时跳过。
 * </pre>
 *
 * @param value 目标 scrollY
 * @return `true` success, `false` fail
 */
@BindingAdapter("binding_scroll_set_scroll_y")
fun View.bindingScrollSetScrollY(value: Int?): Boolean {
    val v = value ?: return false
    return ListViewUtils.setScrollY(this, v)
}