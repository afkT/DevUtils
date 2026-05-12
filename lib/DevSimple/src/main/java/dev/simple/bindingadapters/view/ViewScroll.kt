package dev.simple.bindingadapters.view

import androidx.core.widget.NestedScrollView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.utils.LogPrintUtils
import dev.utils.app.ListViewUtils
import dev.utils.app.RecyclerViewUtils

// ==============================
// = View Scroll BindingAdapter =
// ==============================

private const val TAG = "Dev_Scroll_BindingAdapter"

// ==========
// = 内部工具 =
// ==========

private inline fun runScroll(block: () -> Unit) {
    try {
        block()
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "runScroll")
    }
}

// ====================
// = RecyclerView 专用 =
// ====================

// =====================
// = RecyclerViewUtils =
// =====================

/**
 * 通过数据绑定触发 RecyclerView 平滑滚动，目标项与列表顶部对齐（LinearSmoothScroller SNAP_TO_START）。
 * <pre>
 *     布局属性：binding_scroll_rv_snap_start_index（Int?）。
 *     position 为 null 或小于 0 时不执行；需 LinearLayoutManager。
 * </pre>
 *
 * @param position 目标 adapter 索引
 */
@BindingAdapter("binding_scroll_rv_snap_start_index")
fun RecyclerView.bindingScrollRvSnapStartIndex(position: Int?) {
    val p = position ?: return
    if (p < 0) return
    runScroll { RecyclerViewUtils.startSmoothScrollSnapStart(this, p) }
}

/**
 * 通过数据绑定触发 RecyclerView 平滑滚动，目标项与列表底部对齐（LinearSmoothScroller SNAP_TO_END）。
 * <pre>
 *     布局属性：binding_scroll_rv_snap_end_index。
 *     position 为 null 或小于 0 时不执行；需 LinearLayoutManager。
 * </pre>
 *
 * @param position 目标 adapter 索引
 */
@BindingAdapter("binding_scroll_rv_snap_end_index")
fun RecyclerView.bindingScrollRvSnapEndIndex(position: Int?) {
    val p = position ?: return
    if (p < 0) return
    runScroll { RecyclerViewUtils.startSmoothScrollSnapEnd(this, p) }
}

/**
 * 通过数据绑定触发 RecyclerView 平滑滚至底部附近（内部目标 position 为 itemCount + 30）。
 * <pre>
 *     布局属性：binding_scroll_rv_snap_end_auto（Boolean?）。
 *     仅在值为 true 时执行；需 LinearLayoutManager。
 * </pre>
 *
 * @param toBottomAuto 为 true 时调用无参 startSmoothScrollSnapEnd
 */
@BindingAdapter("binding_scroll_rv_snap_end_auto")
fun RecyclerView.bindingScrollRvSnapEndAuto(toBottomAuto: Boolean?) {
    if (toBottomAuto != true) return
    runScroll { RecyclerViewUtils.startSmoothScrollSnapEnd(this) }
}

/**
 * 通过数据绑定将指定 item 滚动到可见区域并附加像素偏移（LinearLayoutManager.scrollToPositionWithOffset）。
 * <pre>
 *     布局属性：binding_scroll_rv_linear_index、binding_scroll_rv_linear_offset，requireAll 为 false。
 *     position 为 null 或小于 0 时不执行；offset 缺省为 0。
 * </pre>
 *
 * @param position 目标 adapter 索引
 * @param offsetPx 偏移像素
 */
@BindingAdapter(
    value = ["binding_scroll_rv_linear_index", "binding_scroll_rv_linear_offset"],
    requireAll = false
)
fun RecyclerView.bindingScrollRvLinearIndexOffset(
    position: Int?,
    offsetPx: Int?
) {
    val p = position ?: return
    if (p < 0) return
    val off = offsetPx ?: 0
    runScroll { RecyclerViewUtils.scrollToPositionWithOffset(this, p, off) }
}

/**
 * 通过数据绑定停止 RecyclerView 当前平滑滚动与拖动/惯性滚动。
 * <pre>
 *     布局属性：binding_scroll_rv_stop（Boolean?）。
 *     仅在值为 true 时执行。
 * </pre>
 *
 * @param stop 为 true 时调用 stopSmoothScroller
 */
@BindingAdapter("binding_scroll_rv_stop")
fun RecyclerView.bindingScrollRvStop(stop: Boolean?) {
    if (stop != true) return
    runScroll { RecyclerViewUtils.stopSmoothScroller(this) }
}

// =======================
// = RecyclerView 列表滑动 =
// =======================

// ================================
// = ListViewUtils 中适配 RV 的分支 =
// ================================

/**
 * 通过数据绑定平滑滑动 RecyclerView 到指定 adapter 索引。
 * <pre>
 *     布局属性：binding_scroll_rv_smooth_index。
 *     index 为 null 或小于 0 时不执行。
 * </pre>
 *
 * @param index 目标索引
 */
@BindingAdapter("binding_scroll_rv_smooth_index")
fun RecyclerView.bindingScrollRvSmoothIndex(index: Int?) {
    val i = index ?: return
    if (i < 0) return
    runScroll { ListViewUtils.smoothScrollToPosition(this, i) }
}

/**
 * 通过数据绑定无动画滚动 RecyclerView 到指定 adapter 索引。
 * <pre>
 *     布局属性：binding_scroll_rv_instant_index。
 *     index 为 null 或小于 0 时不执行。
 * </pre>
 *
 * @param index 目标索引
 */
@BindingAdapter("binding_scroll_rv_instant_index")
fun RecyclerView.bindingScrollRvInstantIndex(index: Int?) {
    val i = index ?: return
    if (i < 0) return
    runScroll { ListViewUtils.scrollToPosition(this, i) }
}

/**
 * 通过数据绑定触发 RecyclerView 平滑滚动到顶部（首项）。
 * <pre>
 *     布局属性：binding_scroll_rv_event_smooth_top（Int? 事件值）。
 *     在布局中绑定会递增的计数/代数，以便重复触发；为 null 时不执行。
 * </pre>
 *
 * @param event 非 null 时执行 smoothScrollToTop
 */
@BindingAdapter("binding_scroll_rv_event_smooth_top")
fun RecyclerView.bindingScrollRvEventSmoothTop(event: Int?) {
    if (event == null) return
    runScroll { ListViewUtils.smoothScrollToTop(this) }
}

/**
 * 通过数据绑定触发 RecyclerView 瞬时滚动到顶部（首项）。
 * <pre>
 *     布局属性：binding_scroll_rv_event_instant_top（Int?）。
 * </pre>
 *
 * @param event 非 null 时执行 scrollToTop
 */
@BindingAdapter("binding_scroll_rv_event_instant_top")
fun RecyclerView.bindingScrollRvEventInstantTop(event: Int?) {
    if (event == null) return
    runScroll { ListViewUtils.scrollToTop(this) }
}

/**
 * 通过数据绑定触发 RecyclerView 平滑滚动到底部（末项）。
 * <pre>
 *     布局属性：binding_scroll_rv_event_smooth_bottom（Int?）。
 * </pre>
 *
 * @param event 非 null 时执行 smoothScrollToBottom
 */
@BindingAdapter("binding_scroll_rv_event_smooth_bottom")
fun RecyclerView.bindingScrollRvEventSmoothBottom(event: Int?) {
    if (event == null) return
    runScroll { ListViewUtils.smoothScrollToBottom(this) }
}

/**
 * 通过数据绑定触发 RecyclerView 瞬时滚动到底部（末项）。
 * <pre>
 *     布局属性：binding_scroll_rv_event_instant_bottom（Int?）。
 * </pre>
 *
 * @param event 非 null 时执行 scrollToBottom
 */
@BindingAdapter("binding_scroll_rv_event_instant_bottom")
fun RecyclerView.bindingScrollRvEventInstantBottom(event: Int?) {
    if (event == null) return
    runScroll { ListViewUtils.scrollToBottom(this) }
}

/**
 * 通过数据绑定对 RecyclerView 执行平滑相对滚动（像素）。
 * <pre>
 *     布局属性：binding_scroll_rv_smooth_dx、binding_scroll_rv_smooth_dy，requireAll 为 false。
 *     dx、dy 均为 null 时不执行；缺省为 0。
 * </pre>
 *
 * @param dx X 方向增量
 * @param dy Y 方向增量
 */
@BindingAdapter(
    value = ["binding_scroll_rv_smooth_dx", "binding_scroll_rv_smooth_dy"],
    requireAll = false
)
fun RecyclerView.bindingScrollRvSmoothDxDy(
    dx: Int?,
    dy: Int?
) {
    if (dx == null && dy == null) return
    val rdx = dx ?: 0
    val rdy = dy ?: 0
    runScroll { ListViewUtils.smoothScrollBy(this, rdx, rdy) }
}

/**
 * 通过数据绑定对 RecyclerView 执行无动画绝对滚动（ListViewUtils.scrollTo）。
 * <pre>
 *     布局属性：binding_scroll_rv_instant_abs_x、binding_scroll_rv_instant_abs_y，requireAll 为 false。
 *     均为 null 时不执行；缺省保留当前 scrollX/scrollY。
 * </pre>
 *
 * @param x 目标 X
 * @param y 目标 Y
 */
@BindingAdapter(
    value = ["binding_scroll_rv_instant_abs_x", "binding_scroll_rv_instant_abs_y"],
    requireAll = false
)
fun RecyclerView.bindingScrollRvInstantAbsXY(
    x: Int?,
    y: Int?
) {
    if (x == null && y == null) return
    val tx = x ?: scrollX
    val ty = y ?: scrollY
    runScroll { ListViewUtils.scrollTo(this, tx, ty) }
}

/**
 * 通过数据绑定对 RecyclerView 执行无动画相对滚动（ListViewUtils.scrollBy）。
 * <pre>
 *     布局属性：binding_scroll_rv_instant_rel_dx、binding_scroll_rv_instant_rel_dy，requireAll 为 false。
 *     均为 null 时不执行；缺省为 0。
 * </pre>
 *
 * @param dx X 增量
 * @param dy Y 增量
 */
@BindingAdapter(
    value = ["binding_scroll_rv_instant_rel_dx", "binding_scroll_rv_instant_rel_dy"],
    requireAll = false
)
fun RecyclerView.bindingScrollRvInstantRelDxDy(
    dx: Int?,
    dy: Int?
) {
    if (dx == null && dy == null) return
    val rdx = dx ?: 0
    val rdy = dy ?: 0
    runScroll { ListViewUtils.scrollBy(this, rdx, rdy) }
}

/**
 * 通过数据绑定设置 RecyclerView 内容滚动 X（ListViewUtils.setScrollX）。
 * <pre>
 *     布局属性：binding_scroll_rv_set_scroll_x（Int?）。
 * </pre>
 *
 * @param value 目标 scrollX
 */
@BindingAdapter("binding_scroll_rv_set_scroll_x")
fun RecyclerView.bindingScrollRvSetScrollX(value: Int?) {
    val v = value ?: return
    runScroll { ListViewUtils.setScrollX(this, v) }
}

/**
 * 通过数据绑定设置 RecyclerView 内容滚动 Y（ListViewUtils.setScrollY）。
 * <pre>
 *     布局属性：binding_scroll_rv_set_scroll_y（Int?）。
 * </pre>
 *
 * @param value 目标 scrollY
 */
@BindingAdapter("binding_scroll_rv_set_scroll_y")
fun RecyclerView.bindingScrollRvSetScrollY(value: Int?) {
    val v = value ?: return
    runScroll { ListViewUtils.setScrollY(this, v) }
}

// ===============================
// = NestedScrollView 与可滚动内容 =
// ===============================

// ================================
// = ListViewUtils 中 Scroll 类分支 =
// ================================

/**
 * 通过数据绑定触发 NestedScrollView 平滑滚动到内容顶部（smoothScrollTo(0,0) 分支）。
 * <pre>
 *     布局属性：binding_scroll_nsv_event_smooth_top（Int?）。
 * </pre>
 *
 * @param event 非 null 时执行 smoothScrollToTop
 */
@BindingAdapter("binding_scroll_nsv_event_smooth_top")
fun NestedScrollView.bindingScrollNsvEventSmoothTop(event: Int?) {
    if (event == null) return
    runScroll { ListViewUtils.smoothScrollToTop(this) }
}

/**
 * 通过数据绑定触发 NestedScrollView 瞬时滚动到内容顶部。
 * <pre>
 *     布局属性：binding_scroll_nsv_event_instant_top（Int?）。
 * </pre>
 *
 * @param event 非 null 时执行 scrollToTop
 */
@BindingAdapter("binding_scroll_nsv_event_instant_top")
fun NestedScrollView.bindingScrollNsvEventInstantTop(event: Int?) {
    if (event == null) return
    runScroll { ListViewUtils.scrollToTop(this) }
}

/**
 * 通过数据绑定触发 NestedScrollView 平滑滚动到内容底部（fullScroll FOCUS_DOWN 分支）。
 * <pre>
 *     布局属性：binding_scroll_nsv_event_smooth_bottom（Int?）。
 * </pre>
 *
 * @param event 非 null 时执行 smoothScrollToBottom
 */
@BindingAdapter("binding_scroll_nsv_event_smooth_bottom")
fun NestedScrollView.bindingScrollNsvEventSmoothBottom(event: Int?) {
    if (event == null) return
    runScroll { ListViewUtils.smoothScrollToBottom(this) }
}

/**
 * 通过数据绑定触发 NestedScrollView 瞬时滚动到内容底部。
 * <pre>
 *     布局属性：binding_scroll_nsv_event_instant_bottom（Int?）。
 * </pre>
 *
 * @param event 非 null 时执行 scrollToBottom
 */
@BindingAdapter("binding_scroll_nsv_event_instant_bottom")
fun NestedScrollView.bindingScrollNsvEventInstantBottom(event: Int?) {
    if (event == null) return
    runScroll { ListViewUtils.scrollToBottom(this) }
}

/**
 * 通过数据绑定对 NestedScrollView 执行平滑绝对滚动（ListViewUtils.smoothScrollTo）。
 * <pre>
 *     布局属性：binding_scroll_nsv_smooth_abs_x、binding_scroll_nsv_smooth_abs_y，requireAll 为 false。
 *     均为 null 时不执行；缺省保留当前 scrollX/scrollY。
 * </pre>
 *
 * @param x 目标 X
 * @param y 目标 Y
 */
@BindingAdapter(
    value = ["binding_scroll_nsv_smooth_abs_x", "binding_scroll_nsv_smooth_abs_y"],
    requireAll = false
)
fun NestedScrollView.bindingScrollNsvSmoothAbsXY(
    x: Int?,
    y: Int?
) {
    if (x == null && y == null) return
    val tx = x ?: scrollX
    val ty = y ?: scrollY
    runScroll { ListViewUtils.smoothScrollTo(this, tx, ty) }
}

/**
 * 通过数据绑定对 NestedScrollView 执行平滑相对滚动（ListViewUtils.smoothScrollBy）。
 * <pre>
 *     布局属性：binding_scroll_nsv_smooth_rel_dx、binding_scroll_nsv_smooth_rel_dy，requireAll 为 false。
 *     均为 null 时不执行；缺省为 0。
 * </pre>
 *
 * @param dx X 增量
 * @param dy Y 增量
 */
@BindingAdapter(
    value = ["binding_scroll_nsv_smooth_rel_dx", "binding_scroll_nsv_smooth_rel_dy"],
    requireAll = false
)
fun NestedScrollView.bindingScrollNsvSmoothRelDxDy(
    dx: Int?,
    dy: Int?
) {
    if (dx == null && dy == null) return
    val rdx = dx ?: 0
    val rdy = dy ?: 0
    runScroll { ListViewUtils.smoothScrollBy(this, rdx, rdy) }
}

/**
 * 通过数据绑定对 NestedScrollView 执行 fullScroll（如 View.FOCUS_UP / View.FOCUS_DOWN）。
 * <pre>
 *     布局属性：binding_scroll_nsv_full_direction（Int?）。
 *     direction 为 null 时不执行。
 * </pre>
 *
 * @param direction 与 View.FOCUS_UP、View.FOCUS_DOWN 等一致的滚动方向
 */
@BindingAdapter("binding_scroll_nsv_full_direction")
fun NestedScrollView.bindingScrollNsvFullDirection(direction: Int?) {
    val d = direction ?: return
    runScroll { ListViewUtils.fullScroll(this, d) }
}

/**
 * 通过数据绑定对 NestedScrollView 执行无动画绝对滚动（ListViewUtils.scrollTo）。
 * <pre>
 *     布局属性：binding_scroll_nsv_instant_abs_x、binding_scroll_nsv_instant_abs_y，requireAll 为 false。
 *     均为 null 时不执行；缺省保留当前 scrollX/scrollY。
 * </pre>
 *
 * @param x 目标 X
 * @param y 目标 Y
 */
@BindingAdapter(
    value = ["binding_scroll_nsv_instant_abs_x", "binding_scroll_nsv_instant_abs_y"],
    requireAll = false
)
fun NestedScrollView.bindingScrollNsvInstantAbsXY(
    x: Int?,
    y: Int?
) {
    if (x == null && y == null) return
    val tx = x ?: scrollX
    val ty = y ?: scrollY
    runScroll { ListViewUtils.scrollTo(this, tx, ty) }
}

/**
 * 通过数据绑定对 NestedScrollView 执行无动画相对滚动（ListViewUtils.scrollBy）。
 * <pre>
 *     布局属性：binding_scroll_nsv_instant_rel_dx、binding_scroll_nsv_instant_rel_dy，requireAll 为 false。
 *     均为 null 时不执行；缺省为 0。
 * </pre>
 *
 * @param dx X 增量
 * @param dy Y 增量
 */
@BindingAdapter(
    value = ["binding_scroll_nsv_instant_rel_dx", "binding_scroll_nsv_instant_rel_dy"],
    requireAll = false
)
fun NestedScrollView.bindingScrollNsvInstantRelDxDy(
    dx: Int?,
    dy: Int?
) {
    if (dx == null && dy == null) return
    val rdx = dx ?: 0
    val rdy = dy ?: 0
    runScroll { ListViewUtils.scrollBy(this, rdx, rdy) }
}

/**
 * 通过数据绑定设置 NestedScrollView 内容滚动 X 坐标（ListViewUtils.setScrollX）。
 * <pre>
 *     布局属性：binding_scroll_nsv_set_scroll_x（Int?）。
 *     为 null 时不执行。
 * </pre>
 *
 * @param value 目标 scrollX
 */
@BindingAdapter("binding_scroll_nsv_set_scroll_x")
fun NestedScrollView.bindingScrollNsvSetScrollX(value: Int?) {
    val v = value ?: return
    runScroll { ListViewUtils.setScrollX(this, v) }
}

/**
 * 通过数据绑定设置 NestedScrollView 内容滚动 Y 坐标（ListViewUtils.setScrollY）。
 * <pre>
 *     布局属性：binding_scroll_nsv_set_scroll_y（Int?）。
 *     为 null 时不执行。
 * </pre>
 *
 * @param value 目标 scrollY
 */
@BindingAdapter("binding_scroll_nsv_set_scroll_y")
fun NestedScrollView.bindingScrollNsvSetScrollY(value: Int?) {
    val v = value ?: return
    runScroll { ListViewUtils.setScrollY(this, v) }
}