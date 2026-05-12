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

/**
 * 在方法边界内执行滚动相关逻辑，避免未捕获异常导致进程崩溃。
 * <pre>
 *     使用 try-catch 包裹 block，异常时通过 {@code LogPrintUtils.eTag} 记录，不向调用方抛出。
 * </pre>
 *
 * @param block 实际滚动调用
 */
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
 * 数据绑定触发 RecyclerView 平滑滚动并使目标项与列表顶部对齐。
 * <pre>
 *     布局属性：binding_scroll_rv_snap_start_index
 *     对应 {@link RecyclerViewUtils#startSmoothScrollSnapStart(RecyclerView, int)}；position 为 null 或小于 0 时跳过；需 {@link androidx.recyclerview.widget.LinearLayoutManager}。
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
 * 数据绑定触发 RecyclerView 平滑滚动并使目标项与列表底部对齐。
 * <pre>
 *     布局属性：binding_scroll_rv_snap_end_index
 *     对应 {@link RecyclerViewUtils#startSmoothScrollSnapEnd(RecyclerView, int)}；position 为 null 或小于 0 时跳过；需 LinearLayoutManager。
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
 * 数据绑定触发 RecyclerView 平滑滚向底部吸附区域。
 * <pre>
 *     布局属性：binding_scroll_rv_snap_end_auto
 *     对应无 position 重载的 {@link RecyclerViewUtils#startSmoothScrollSnapEnd(RecyclerView)}；仅在为 true 时执行；需 LinearLayoutManager。
 * </pre>
 *
 * @param toBottomAuto 为 true 时执行
 */
@BindingAdapter("binding_scroll_rv_snap_end_auto")
fun RecyclerView.bindingScrollRvSnapEndAuto(toBottomAuto: Boolean?) {
    if (toBottomAuto != true) return
    runScroll { RecyclerViewUtils.startSmoothScrollSnapEnd(this) }
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
 * 数据绑定停止 RecyclerView 当前平滑滚动与拖动及惯性滚动。
 * <pre>
 *     布局属性：binding_scroll_rv_stop
 *     对应 {@link RecyclerViewUtils#stopSmoothScroller(RecyclerView)}；仅在为 true 时执行。
 * </pre>
 *
 * @param stop 为 true 时执行
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
 * 数据绑定平滑滑动 RecyclerView 到指定 adapter 索引。
 * <pre>
 *     布局属性：binding_scroll_rv_smooth_index
 *     对应 {@link ListViewUtils#smoothScrollToPosition}；index 为 null 或小于 0 时跳过。
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
 * 数据绑定无动画滚动 RecyclerView 到指定 adapter 索引。
 * <pre>
 *     布局属性：binding_scroll_rv_instant_index
 *     对应 {@link ListViewUtils#scrollToPosition}；index 为 null 或小于 0 时跳过。
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
 * 数据绑定触发 RecyclerView 平滑滚动到列表顶部。
 * <pre>
 *     布局属性：binding_scroll_rv_event_smooth_top
 *     对应 {@link ListViewUtils#smoothScrollToTop}；event 为 null 时跳过；建议在 ViewModel 中递增计数以支持重复触发。
 * </pre>
 *
 * @param event 非 null 时执行一次
 */
@BindingAdapter("binding_scroll_rv_event_smooth_top")
fun RecyclerView.bindingScrollRvEventSmoothTop(event: Int?) {
    if (event == null) return
    runScroll { ListViewUtils.smoothScrollToTop(this) }
}

/**
 * 数据绑定触发 RecyclerView 瞬时滚动到列表顶部。
 * <pre>
 *     布局属性：binding_scroll_rv_event_instant_top
 *     对应 {@link ListViewUtils#scrollToTop}；event 为 null 时跳过。
 * </pre>
 *
 * @param event 非 null 时执行一次
 */
@BindingAdapter("binding_scroll_rv_event_instant_top")
fun RecyclerView.bindingScrollRvEventInstantTop(event: Int?) {
    if (event == null) return
    runScroll { ListViewUtils.scrollToTop(this) }
}

/**
 * 数据绑定触发 RecyclerView 平滑滚动到列表底部。
 * <pre>
 *     布局属性：binding_scroll_rv_event_smooth_bottom
 *     对应 {@link ListViewUtils#smoothScrollToBottom}；event 为 null 时跳过。
 * </pre>
 *
 * @param event 非 null 时执行一次
 */
@BindingAdapter("binding_scroll_rv_event_smooth_bottom")
fun RecyclerView.bindingScrollRvEventSmoothBottom(event: Int?) {
    if (event == null) return
    runScroll { ListViewUtils.smoothScrollToBottom(this) }
}

/**
 * 数据绑定触发 RecyclerView 瞬时滚动到列表底部。
 * <pre>
 *     布局属性：binding_scroll_rv_event_instant_bottom
 *     对应 {@link ListViewUtils#scrollToBottom}；event 为 null 时跳过。
 * </pre>
 *
 * @param event 非 null 时执行一次
 */
@BindingAdapter("binding_scroll_rv_event_instant_bottom")
fun RecyclerView.bindingScrollRvEventInstantBottom(event: Int?) {
    if (event == null) return
    runScroll { ListViewUtils.scrollToBottom(this) }
}

/**
 * 数据绑定对 RecyclerView 执行平滑相对滚动。
 * <pre>
 *     布局属性：binding_scroll_rv_smooth_dx、binding_scroll_rv_smooth_dy，requireAll 为 false
 *     对应 {@link ListViewUtils#smoothScrollBy}；dx、dy 均为 null 时跳过；缺省为 0。
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
 * 数据绑定对 RecyclerView 执行无动画绝对滚动。
 * <pre>
 *     布局属性：binding_scroll_rv_instant_abs_x、binding_scroll_rv_instant_abs_y，requireAll 为 false
 *     对应 {@link ListViewUtils#scrollTo}；x、y 均为 null 时跳过；缺省保留当前 scrollX、scrollY。
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
 * 数据绑定对 RecyclerView 执行无动画相对滚动。
 * <pre>
 *     布局属性：binding_scroll_rv_instant_rel_dx、binding_scroll_rv_instant_rel_dy，requireAll 为 false
 *     对应 {@link ListViewUtils#scrollBy}；dx、dy 均为 null 时跳过；缺省为 0。
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
 * 数据绑定设置 RecyclerView 内容水平滚动位置。
 * <pre>
 *     布局属性：binding_scroll_rv_set_scroll_x
 *     对应 {@link ListViewUtils#setScrollX}；value 为 null 时跳过。
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
 * 数据绑定设置 RecyclerView 内容垂直滚动位置。
 * <pre>
 *     布局属性：binding_scroll_rv_set_scroll_y
 *     对应 {@link ListViewUtils#setScrollY}；value 为 null 时跳过。
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
 * 数据绑定触发 NestedScrollView 平滑滚动到内容顶部。
 * <pre>
 *     布局属性：binding_scroll_nsv_event_smooth_top
 *     对应 {@link ListViewUtils#smoothScrollToTop} 在非列表 View 上的分支；event 为 null 时跳过。
 * </pre>
 *
 * @param event 非 null 时执行一次
 */
@BindingAdapter("binding_scroll_nsv_event_smooth_top")
fun NestedScrollView.bindingScrollNsvEventSmoothTop(event: Int?) {
    if (event == null) return
    runScroll { ListViewUtils.smoothScrollToTop(this) }
}

/**
 * 数据绑定触发 NestedScrollView 瞬时滚动到内容顶部。
 * <pre>
 *     布局属性：binding_scroll_nsv_event_instant_top
 *     对应 {@link ListViewUtils#scrollToTop}；event 为 null 时跳过。
 * </pre>
 *
 * @param event 非 null 时执行一次
 */
@BindingAdapter("binding_scroll_nsv_event_instant_top")
fun NestedScrollView.bindingScrollNsvEventInstantTop(event: Int?) {
    if (event == null) return
    runScroll { ListViewUtils.scrollToTop(this) }
}

/**
 * 数据绑定触发 NestedScrollView 平滑滚动到内容底部。
 * <pre>
 *     布局属性：binding_scroll_nsv_event_smooth_bottom
 *     对应 {@link ListViewUtils#smoothScrollToBottom} 在非列表 View 上的分支；event 为 null 时跳过。
 * </pre>
 *
 * @param event 非 null 时执行一次
 */
@BindingAdapter("binding_scroll_nsv_event_smooth_bottom")
fun NestedScrollView.bindingScrollNsvEventSmoothBottom(event: Int?) {
    if (event == null) return
    runScroll { ListViewUtils.smoothScrollToBottom(this) }
}

/**
 * 数据绑定触发 NestedScrollView 瞬时滚动到内容底部。
 * <pre>
 *     布局属性：binding_scroll_nsv_event_instant_bottom
 *     对应 {@link ListViewUtils#scrollToBottom}；event 为 null 时跳过。
 * </pre>
 *
 * @param event 非 null 时执行一次
 */
@BindingAdapter("binding_scroll_nsv_event_instant_bottom")
fun NestedScrollView.bindingScrollNsvEventInstantBottom(event: Int?) {
    if (event == null) return
    runScroll { ListViewUtils.scrollToBottom(this) }
}

/**
 * 数据绑定对 NestedScrollView 执行平滑绝对滚动。
 * <pre>
 *     布局属性：binding_scroll_nsv_smooth_abs_x、binding_scroll_nsv_smooth_abs_y，requireAll 为 false
 *     对应 {@link ListViewUtils#smoothScrollTo}；x、y 均为 null 时跳过；缺省保留当前 scrollX、scrollY。
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
 * 数据绑定对 NestedScrollView 执行平滑相对滚动。
 * <pre>
 *     布局属性：binding_scroll_nsv_smooth_rel_dx、binding_scroll_nsv_smooth_rel_dy，requireAll 为 false
 *     对应 {@link ListViewUtils#smoothScrollBy}；dx、dy 均为 null 时跳过；缺省为 0。
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
 * 数据绑定对 NestedScrollView 按焦点方向整段滚动。
 * <pre>
 *     布局属性：binding_scroll_nsv_full_direction
 *     对应 {@link ListViewUtils#fullScroll}；direction 为 null 时跳过；取值与 {@link android.view.View#FOCUS_UP}、{@link android.view.View#FOCUS_DOWN} 等一致。
 * </pre>
 *
 * @param direction 滚动方向常量
 */
@BindingAdapter("binding_scroll_nsv_full_direction")
fun NestedScrollView.bindingScrollNsvFullDirection(direction: Int?) {
    val d = direction ?: return
    runScroll { ListViewUtils.fullScroll(this, d) }
}

/**
 * 数据绑定对 NestedScrollView 执行无动画绝对滚动。
 * <pre>
 *     布局属性：binding_scroll_nsv_instant_abs_x、binding_scroll_nsv_instant_abs_y，requireAll 为 false
 *     对应 {@link ListViewUtils#scrollTo}；x、y 均为 null 时跳过；缺省保留当前 scrollX、scrollY。
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
 * 数据绑定对 NestedScrollView 执行无动画相对滚动。
 * <pre>
 *     布局属性：binding_scroll_nsv_instant_rel_dx、binding_scroll_nsv_instant_rel_dy，requireAll 为 false
 *     对应 {@link ListViewUtils#scrollBy}；dx、dy 均为 null 时跳过；缺省为 0。
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
 * 数据绑定设置 NestedScrollView 内容水平滚动位置。
 * <pre>
 *     布局属性：binding_scroll_nsv_set_scroll_x
 *     对应 {@link ListViewUtils#setScrollX}；value 为 null 时跳过。
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
 * 数据绑定设置 NestedScrollView 内容垂直滚动位置。
 * <pre>
 *     布局属性：binding_scroll_nsv_set_scroll_y
 *     对应 {@link ListViewUtils#setScrollY}；value 为 null 时跳过。
 * </pre>
 *
 * @param value 目标 scrollY
 */
@BindingAdapter("binding_scroll_nsv_set_scroll_y")
fun NestedScrollView.bindingScrollNsvSetScrollY(value: Int?) {
    val v = value ?: return
    runScroll { ListViewUtils.setScrollY(this, v) }
}