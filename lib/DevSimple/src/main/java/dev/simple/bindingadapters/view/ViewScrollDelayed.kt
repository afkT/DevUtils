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
 * 布局属性：binding_scroll_delayed_rv_snap_start_index、binding_scroll_delayed_rv_snap_start_index_delay_ms，requireAll 为 false。
 * 委托 [RecyclerView.bindingScrollRvSnapStartIndex]。
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
 * 布局属性：binding_scroll_delayed_rv_snap_start_auto、binding_scroll_delayed_rv_snap_start_auto_delay_ms，requireAll 为 false。
 * 委托 [RecyclerView.bindingScrollRvSnapStartAuto]。
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
 * 布局属性：binding_scroll_delayed_rv_snap_end_index、binding_scroll_delayed_rv_snap_end_index_delay_ms，requireAll 为 false。
 * 委托 [RecyclerView.bindingScrollRvSnapEndIndex]。
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
 * 布局属性：binding_scroll_delayed_rv_snap_end_auto、binding_scroll_delayed_rv_snap_end_auto_delay_ms，requireAll 为 false。
 * 委托 [RecyclerView.bindingScrollRvSnapEndAuto]。
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
 * 布局属性：binding_scroll_delayed_rv_linear_index、binding_scroll_delayed_rv_linear_offset、binding_scroll_delayed_rv_linear_delay_ms，requireAll 为 false。
 * 委托 [RecyclerView.bindingScrollRvLinearIndexOffset]。
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
 * 布局属性：binding_scroll_delayed_rv_stop、binding_scroll_delayed_rv_stop_delay_ms，requireAll 为 false。
 * 委托 [RecyclerView.bindingScrollRvStop]。
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
 * 布局属性：binding_scroll_delayed_smooth_adapter_index、binding_scroll_delayed_smooth_adapter_index_delay_ms，requireAll 为 false。
 * 委托 [View.bindingScrollSmoothAdapterIndex]。
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
 * 布局属性：binding_scroll_delayed_instant_adapter_index、binding_scroll_delayed_instant_adapter_index_delay_ms，requireAll 为 false。
 * 委托 [View.bindingScrollInstantAdapterIndex]。
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
 * 布局属性：binding_scroll_delayed_event_smooth_top、binding_scroll_delayed_event_smooth_top_delay_ms，requireAll 为 false。
 * 委托 [View.bindingScrollEventSmoothTop]。
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
 * 布局属性：binding_scroll_delayed_event_instant_top、binding_scroll_delayed_event_instant_top_delay_ms，requireAll 为 false。
 * 委托 [View.bindingScrollEventInstantTop]。
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
 * 布局属性：binding_scroll_delayed_event_smooth_bottom、binding_scroll_delayed_event_smooth_bottom_delay_ms，requireAll 为 false。
 * 委托 [View.bindingScrollEventSmoothBottom]。
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
 * 布局属性：binding_scroll_delayed_event_instant_bottom、binding_scroll_delayed_event_instant_bottom_delay_ms，requireAll 为 false。
 * 委托 [View.bindingScrollEventInstantBottom]。
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
 * 布局属性：binding_scroll_delayed_smooth_abs_x、binding_scroll_delayed_smooth_abs_y、binding_scroll_delayed_smooth_abs_delay_ms，requireAll 为 false。
 * 委托 [View.bindingScrollSmoothAbsXY]。
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
 * 布局属性：binding_scroll_delayed_smooth_rel_dx、binding_scroll_delayed_smooth_rel_dy、binding_scroll_delayed_smooth_rel_delay_ms，requireAll 为 false。
 * 委托 [View.bindingScrollSmoothRelDxDy]。
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
 * 布局属性：binding_scroll_delayed_full_direction、binding_scroll_delayed_full_direction_delay_ms，requireAll 为 false。
 * 委托 [View.bindingScrollFullDirection]。
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
 * 布局属性：binding_scroll_delayed_instant_abs_x、binding_scroll_delayed_instant_abs_y、binding_scroll_delayed_instant_abs_delay_ms，requireAll 为 false。
 * 委托 [View.bindingScrollInstantAbsXY]。
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
 * 布局属性：binding_scroll_delayed_instant_rel_dx、binding_scroll_delayed_instant_rel_dy、binding_scroll_delayed_instant_rel_delay_ms，requireAll 为 false。
 * 委托 [View.bindingScrollInstantRelDxDy]。
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
 * 布局属性：binding_scroll_delayed_set_scroll_x、binding_scroll_delayed_set_scroll_x_delay_ms，requireAll 为 false。
 * 委托 [View.bindingScrollSetScrollX]。
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
 * 布局属性：binding_scroll_delayed_set_scroll_y、binding_scroll_delayed_set_scroll_y_delay_ms，requireAll 为 false。
 * 委托 [View.bindingScrollSetScrollY]。
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