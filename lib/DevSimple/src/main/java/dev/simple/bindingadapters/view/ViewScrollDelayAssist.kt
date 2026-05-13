package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.simple.bindingadapters.view.attribute.XYI
import dev.utils.app.assist.DelayAssist

// ============================================
// = View Scroll DelayAssist BindingAdapter =
// ============================================

/**
 * 与 `ViewScroll.kt` 配套：先执行即时绑定逻辑；若首次成功且传入非 null 的 [DelayAssist]，
 * 则通过 [DelayAssist.post] 在配置的间隔后再次执行一次（与 `ViewScrollDelayed` 的 `postDelayed` 实现方式不同）。
 * `assist` 为 null 时仅执行一次。
 */
private inline fun View.runScrollWithDelayAssist(
    assist: DelayAssist?,
    crossinline action: () -> Boolean
) {
    val result = action()
    if (!result) return
    val a = assist ?: return
    a.setCallback { action() }
    a.post()
}

// =====================
// = RecyclerViewUtils =
// =====================

/**
 * 数据绑定触发 RecyclerView 平滑滚动并使目标项与列表顶部对齐。
 * @param position 目标 adapter 索引
 * @param assist 非 null 时在 [DelayAssist] 间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_rv_snap_start_index",
        "binding_scroll_delay_assist_rv_snap_start_index_assist"
    ],
    requireAll = false
)
fun RecyclerView.bindingScrollDelayAssistRvSnapStartIndex(
    position: Int?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollRvSnapStartIndex(position) }
}

/**
 * 数据绑定触发 RecyclerView 平滑滚向顶部吸附区域。
 * @param timestamp 大于 0 时执行一次
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_rv_snap_start_auto",
        "binding_scroll_delay_assist_rv_snap_start_auto_assist"
    ],
    requireAll = false
)
fun RecyclerView.bindingScrollDelayAssistRvSnapStartAuto(
    timestamp: Long?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollRvSnapStartAuto(timestamp) }
}

/**
 * 数据绑定触发 RecyclerView 平滑滚动并使目标项与列表底部对齐。
 * @param position 目标 adapter 索引
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_rv_snap_end_index",
        "binding_scroll_delay_assist_rv_snap_end_index_assist"
    ],
    requireAll = false
)
fun RecyclerView.bindingScrollDelayAssistRvSnapEndIndex(
    position: Int?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollRvSnapEndIndex(position) }
}

/**
 * 数据绑定触发 RecyclerView 平滑滚向底部吸附区域。
 * @param timestamp 大于 0 时执行一次
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_rv_snap_end_auto",
        "binding_scroll_delay_assist_rv_snap_end_auto_assist"
    ],
    requireAll = false
)
fun RecyclerView.bindingScrollDelayAssistRvSnapEndAuto(
    timestamp: Long?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollRvSnapEndAuto(timestamp) }
}

/**
 * 数据绑定将指定项滚入可视区并附加像素偏移。
 * @param position 目标 adapter 索引
 * @param offsetPx 偏移像素
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_rv_linear_index",
        "binding_scroll_delay_assist_rv_linear_offset",
        "binding_scroll_delay_assist_rv_linear_assist"
    ],
    requireAll = false
)
fun RecyclerView.bindingScrollDelayAssistRvLinearIndexOffset(
    position: Int?,
    offsetPx: Int?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollRvLinearIndexOffset(position, offsetPx) }
}

/**
 * 数据绑定停止 RecyclerView 当前平滑滚动与拖动及惯性滚动。
 * @param timestamp 大于 0 时执行一次
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_rv_stop",
        "binding_scroll_delay_assist_rv_stop_assist"
    ],
    requireAll = false
)
fun RecyclerView.bindingScrollDelayAssistRvStop(
    timestamp: Long?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollRvStop(timestamp) }
}

// =================
// = ListViewUtils =
// =================

/**
 * 数据绑定平滑滑动到指定 adapter 或列表项索引。
 * @param index 目标索引
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_smooth_adapter_index",
        "binding_scroll_delay_assist_smooth_adapter_index_assist"
    ],
    requireAll = false
)
fun View.bindingScrollDelayAssistSmoothAdapterIndex(
    index: Int?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollSmoothAdapterIndex(index) }
}

/**
 * 数据绑定无动画滚动到指定 adapter 或列表项索引。
 * @param index 目标索引
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_instant_adapter_index",
        "binding_scroll_delay_assist_instant_adapter_index_assist"
    ],
    requireAll = false
)
fun View.bindingScrollDelayAssistInstantAdapterIndex(
    index: Int?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollInstantAdapterIndex(index) }
}

/**
 * 数据绑定触发平滑滚动到内容或列表顶部。
 * @param timestamp 大于 0 时执行一次
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_event_smooth_top",
        "binding_scroll_delay_assist_event_smooth_top_assist"
    ],
    requireAll = false
)
fun View.bindingScrollDelayAssistEventSmoothTop(
    timestamp: Long?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollEventSmoothTop(timestamp) }
}

/**
 * 数据绑定触发瞬时滚动到内容或列表顶部。
 * @param timestamp 大于 0 时执行一次
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_event_instant_top",
        "binding_scroll_delay_assist_event_instant_top_assist"
    ],
    requireAll = false
)
fun View.bindingScrollDelayAssistEventInstantTop(
    timestamp: Long?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollEventInstantTop(timestamp) }
}

/**
 * 数据绑定触发平滑滚动到内容或列表底部。
 * @param timestamp 大于 0 时执行一次
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_event_smooth_bottom",
        "binding_scroll_delay_assist_event_smooth_bottom_assist"
    ],
    requireAll = false
)
fun View.bindingScrollDelayAssistEventSmoothBottom(
    timestamp: Long?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollEventSmoothBottom(timestamp) }
}

/**
 * 数据绑定触发瞬时滚动到内容或列表底部。
 * @param timestamp 大于 0 时执行一次
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_event_instant_bottom",
        "binding_scroll_delay_assist_event_instant_bottom_assist"
    ],
    requireAll = false
)
fun View.bindingScrollDelayAssistEventInstantBottom(
    timestamp: Long?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollEventInstantBottom(timestamp) }
}

/**
 * 数据绑定执行平滑绝对滚动。
 * @param xy 目标坐标，语义同 [bindingScrollSmoothAbsXY]
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_smooth_abs_xy",
        "binding_scroll_delay_assist_smooth_abs_assist"
    ],
    requireAll = false
)
fun View.bindingScrollDelayAssistSmoothAbsXY(
    xy: XYI?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollSmoothAbsXY(xy) }
}

/**
 * 数据绑定执行平滑相对滚动。
 * @param xy 相对位移，语义同 [bindingScrollSmoothRelDxDy]
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_smooth_rel_xy",
        "binding_scroll_delay_assist_smooth_rel_assist"
    ],
    requireAll = false
)
fun View.bindingScrollDelayAssistSmoothRelDxDy(
    xy: XYI?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollSmoothRelDxDy(xy) }
}

/**
 * 数据绑定按焦点方向整段滚动。
 * @param direction 滚动方向常量
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_full_direction",
        "binding_scroll_delay_assist_full_direction_assist"
    ],
    requireAll = false
)
fun View.bindingScrollDelayAssistFullDirection(
    direction: Int?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollFullDirection(direction) }
}

/**
 * 数据绑定执行无动画绝对滚动。
 * @param xy 目标坐标，语义同 [bindingScrollInstantAbsXY]
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_instant_abs_xy",
        "binding_scroll_delay_assist_instant_abs_assist"
    ],
    requireAll = false
)
fun View.bindingScrollDelayAssistInstantAbsXY(
    xy: XYI?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollInstantAbsXY(xy) }
}

/**
 * 数据绑定执行无动画相对滚动。
 * @param xy 相对位移，语义同 [bindingScrollInstantRelDxDy]
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_instant_rel_xy",
        "binding_scroll_delay_assist_instant_rel_assist"
    ],
    requireAll = false
)
fun View.bindingScrollDelayAssistInstantRelDxDy(
    xy: XYI?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollInstantRelDxDy(xy) }
}

/**
 * 数据绑定设置内容水平滚动位置。
 * @param value 目标 scrollX
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_set_scroll_x",
        "binding_scroll_delay_assist_set_scroll_x_assist"
    ],
    requireAll = false
)
fun View.bindingScrollDelayAssistSetScrollX(
    value: Int?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollSetScrollX(value) }
}

/**
 * 数据绑定设置内容垂直滚动位置。
 * @param value 目标 scrollY
 * @param assist 非 null 时在间隔后再次执行
 */
@BindingAdapter(
    value = [
        "binding_scroll_delay_assist_set_scroll_y",
        "binding_scroll_delay_assist_set_scroll_y_assist"
    ],
    requireAll = false
)
fun View.bindingScrollDelayAssistSetScrollY(
    value: Int?,
    assist: DelayAssist?
) {
    runScrollWithDelayAssist(assist) { bindingScrollSetScrollY(value) }
}