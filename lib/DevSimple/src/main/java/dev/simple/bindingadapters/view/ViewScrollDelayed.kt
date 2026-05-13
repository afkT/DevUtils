package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

// ======================================
// = View Scroll Delayed BindingAdapter =
// ======================================

/**
 * 与 `ViewScroll.kt` 配套：先执行即时绑定逻辑，若 `delayMs` 大于 0 则在间隔后再次执行一次（用于布局/数据晚到时的二次对齐）。
 * `delayMs` 为 null 或不大于 0 时仅执行一次。
 */
private inline fun View.runScrollWithOptionalDelay(
    delayMs: Long?,
    crossinline action: () -> Unit
) {
    action()
    val d = delayMs ?: return
    if (d <= 0L) return
    postDelayed({ action() }, d)
}

// =====================
// = RecyclerViewUtils =
// =====================

/**
 * 数据绑定触发 RecyclerView 平滑滚动并使目标项与列表顶部对齐。
 * @param position 目标 adapter 索引
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_rv_snap_start_index",
        "binding_scroll_delayed_rv_snap_start_index_delay_ms"
    ],
    requireAll = false
)
fun RecyclerView.bindingScrollDelayedRvSnapStartIndex(
    position: Int?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollRvSnapStartIndex(position) }
}

/**
 * 数据绑定触发 RecyclerView 平滑滚向顶部吸附区域。
 * @param timestamp 大于 0 时执行一次
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_rv_snap_start_auto",
        "binding_scroll_delayed_rv_snap_start_auto_delay_ms"
    ],
    requireAll = false
)
fun RecyclerView.bindingScrollDelayedRvSnapStartAuto(
    timestamp: Long?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollRvSnapStartAuto(timestamp) }
}

/**
 * 数据绑定触发 RecyclerView 平滑滚动并使目标项与列表底部对齐。
 * @param position 目标 adapter 索引
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_rv_snap_end_index",
        "binding_scroll_delayed_rv_snap_end_index_delay_ms"
    ],
    requireAll = false
)
fun RecyclerView.bindingScrollDelayedRvSnapEndIndex(
    position: Int?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollRvSnapEndIndex(position) }
}

/**
 * 数据绑定触发 RecyclerView 平滑滚向底部吸附区域。
 * @param timestamp 大于 0 时执行一次
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_rv_snap_end_auto",
        "binding_scroll_delayed_rv_snap_end_auto_delay_ms"
    ],
    requireAll = false
)
fun RecyclerView.bindingScrollDelayedRvSnapEndAuto(
    timestamp: Long?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollRvSnapEndAuto(timestamp) }
}

/**
 * 数据绑定将指定项滚入可视区并附加像素偏移。
 * @param position 目标 adapter 索引
 * @param offsetPx 偏移像素
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_rv_linear_index",
        "binding_scroll_delayed_rv_linear_offset",
        "binding_scroll_delayed_rv_linear_delay_ms"
    ],
    requireAll = false
)
fun RecyclerView.bindingScrollDelayedRvLinearIndexOffset(
    position: Int?,
    offsetPx: Int?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollRvLinearIndexOffset(position, offsetPx) }
}

/**
 * 数据绑定停止 RecyclerView 当前平滑滚动与拖动及惯性滚动。
 * @param timestamp 大于 0 时执行一次
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = ["binding_scroll_delayed_rv_stop", "binding_scroll_delayed_rv_stop_delay_ms"],
    requireAll = false
)
fun RecyclerView.bindingScrollDelayedRvStop(
    timestamp: Long?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollRvStop(timestamp) }
}

// =================
// = ListViewUtils =
// =================

/**
 * 数据绑定平滑滑动到指定 adapter 或列表项索引。
 * @param index 目标索引
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_smooth_adapter_index",
        "binding_scroll_delayed_smooth_adapter_index_delay_ms"
    ],
    requireAll = false
)
fun View.bindingScrollDelayedSmoothAdapterIndex(
    index: Int?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollSmoothAdapterIndex(index) }
}

/**
 * 数据绑定无动画滚动到指定 adapter 或列表项索引。
 * @param index 目标索引
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_instant_adapter_index",
        "binding_scroll_delayed_instant_adapter_index_delay_ms"
    ],
    requireAll = false
)
fun View.bindingScrollDelayedInstantAdapterIndex(
    index: Int?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollInstantAdapterIndex(index) }
}

/**
 * 数据绑定触发平滑滚动到内容或列表顶部。
 * @param timestamp 大于 0 时执行一次
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_event_smooth_top",
        "binding_scroll_delayed_event_smooth_top_delay_ms"
    ],
    requireAll = false
)
fun View.bindingScrollDelayedEventSmoothTop(
    timestamp: Long?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollEventSmoothTop(timestamp) }
}

/**
 * 数据绑定触发瞬时滚动到内容或列表顶部。
 * @param timestamp 大于 0 时执行一次
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_event_instant_top",
        "binding_scroll_delayed_event_instant_top_delay_ms"
    ],
    requireAll = false
)
fun View.bindingScrollDelayedEventInstantTop(
    timestamp: Long?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollEventInstantTop(timestamp) }
}

/**
 * 数据绑定触发平滑滚动到内容或列表底部。
 * @param timestamp 大于 0 时执行一次
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_event_smooth_bottom",
        "binding_scroll_delayed_event_smooth_bottom_delay_ms"
    ],
    requireAll = false
)
fun View.bindingScrollDelayedEventSmoothBottom(
    timestamp: Long?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollEventSmoothBottom(timestamp) }
}

/**
 * 数据绑定触发瞬时滚动到内容或列表底部。
 * @param timestamp 大于 0 时执行一次
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_event_instant_bottom",
        "binding_scroll_delayed_event_instant_bottom_delay_ms"
    ],
    requireAll = false
)
fun View.bindingScrollDelayedEventInstantBottom(
    timestamp: Long?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollEventInstantBottom(timestamp) }
}

/**
 * 数据绑定执行平滑绝对滚动。
 * @param x 目标 X
 * @param y 目标 Y
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_smooth_abs_x",
        "binding_scroll_delayed_smooth_abs_y",
        "binding_scroll_delayed_smooth_abs_delay_ms"
    ],
    requireAll = false
)
fun View.bindingScrollDelayedSmoothAbsXY(
    x: Int?,
    y: Int?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollSmoothAbsXY(x, y) }
}

/**
 * 数据绑定执行平滑相对滚动。
 * @param dx X 增量
 * @param dy Y 增量
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_smooth_rel_dx",
        "binding_scroll_delayed_smooth_rel_dy",
        "binding_scroll_delayed_smooth_rel_delay_ms"
    ],
    requireAll = false
)
fun View.bindingScrollDelayedSmoothRelDxDy(
    dx: Int?,
    dy: Int?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollSmoothRelDxDy(dx, dy) }
}

/**
 * 数据绑定按焦点方向整段滚动。
 * @param direction 滚动方向常量
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_full_direction",
        "binding_scroll_delayed_full_direction_delay_ms"
    ],
    requireAll = false
)
fun View.bindingScrollDelayedFullDirection(
    direction: Int?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollFullDirection(direction) }
}

/**
 * 数据绑定执行无动画绝对滚动。
 * @param x 目标 X
 * @param y 目标 Y
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_instant_abs_x",
        "binding_scroll_delayed_instant_abs_y",
        "binding_scroll_delayed_instant_abs_delay_ms"
    ],
    requireAll = false
)
fun View.bindingScrollDelayedInstantAbsXY(
    x: Int?,
    y: Int?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollInstantAbsXY(x, y) }
}

/**
 * 数据绑定执行无动画相对滚动。
 * @param dx X 增量
 * @param dy Y 增量
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_instant_rel_dx",
        "binding_scroll_delayed_instant_rel_dy",
        "binding_scroll_delayed_instant_rel_delay_ms"
    ],
    requireAll = false
)
fun View.bindingScrollDelayedInstantRelDxDy(
    dx: Int?,
    dy: Int?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollInstantRelDxDy(dx, dy) }
}

/**
 * 数据绑定设置内容水平滚动位置。
 * @param value 目标 scrollX
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_set_scroll_x",
        "binding_scroll_delayed_set_scroll_x_delay_ms"
    ],
    requireAll = false
)
fun View.bindingScrollDelayedSetScrollX(
    value: Int?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollSetScrollX(value) }
}

/**
 * 数据绑定设置内容垂直滚动位置。
 * @param value 目标 scrollY
 * @param delayMs 延迟多少毫秒后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delayed_set_scroll_y",
        "binding_scroll_delayed_set_scroll_y_delay_ms"
    ],
    requireAll = false
)
fun View.bindingScrollDelayedSetScrollY(
    value: Int?,
    delayMs: Long?
) {
    runScrollWithOptionalDelay(delayMs) { bindingScrollSetScrollY(value) }
}