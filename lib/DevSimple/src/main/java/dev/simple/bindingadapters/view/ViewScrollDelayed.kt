package dev.simple.bindingadapters.view

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.utils.app.ListViewUtils
import dev.utils.app.RecyclerViewUtils

// ======================================
// = View Scroll Delayed BindingAdapter =
// ======================================

// 与 ViewScroll.kt 配套：在即时滚动后再 postDelayed 执行一次，用于布局/数据晚到时的二次对齐。

// ==========
// = 具体实现 =
// ==========

// =====================
// = RecyclerViewUtils =
// =====================

/**
 * 数据绑定 RecyclerView 平滑滚向底部吸附，并在 [delayMs] 毫秒后再执行一次（默认 2000；≤0 则仅一次）。
 * <pre>
 *     布局属性：binding_scroll_rv_snap_end_auto、binding_scroll_rv_snap_end_auto_delay_ms，requireAll 为 false
 *     在 ViewScroll.kt 单参「snap 滚底」基础上增加延时二次触发；需 LinearLayoutManager。
 * </pre>
 *
 * @param timestamp 大于 0 时执行
 * @param delayMs 二次滚动间隔毫秒，缺省 2000
 */
@BindingAdapter(
    value = ["binding_scroll_rv_snap_end_auto", "binding_scroll_rv_snap_end_auto_delay_ms"],
    requireAll = false
)
fun RecyclerView.bindingScrollRvSnapEndAuto(
    timestamp: Long?,
    delayMs: Long?
) {
    if (!timestamp.shouldTriggerScroll()) return
    RecyclerViewUtils.startSmoothScrollSnapEnd(this)
    val delay = delayMs ?: 2000
    if (delay > 0L) {
        postDelayed({
            RecyclerViewUtils.startSmoothScrollSnapEnd(this)
        }, delay)
    }
}

// =================
// = ListViewUtils =
// =================

// ========================================
// = 入参为 View，适用于 RV、NSV、ListView 等 =
// ========================================

/**
 * 数据绑定瞬时滚底，并在 [delayMs] 毫秒后再滚一次（默认 2000；≤0 则仅滚一次）。
 * <pre>
 *     布局属性：binding_scroll_event_instant_bottom、binding_scroll_event_instant_bottom_delay_ms，requireAll 为 false
 *     在 ViewScroll.kt 单参「瞬时滚底」基础上增加延时二次触发。
 * </pre>
 *
 * @param timestamp 大于 0 时执行
 * @param delayMs 二次滚底间隔毫秒，缺省 2000
 */
@BindingAdapter(
    value = ["binding_scroll_event_instant_bottom", "binding_scroll_event_instant_bottom_delay_ms"],
    requireAll = false
)
fun View.bindingScrollEventInstantBottom(
    timestamp: Long?,
    delayMs: Long?
) {
    if (!timestamp.shouldTriggerScroll()) return
    ListViewUtils.scrollToBottom(this)
    val delay = delayMs ?: 2000
    if (delay > 0L) {
        postDelayed({
            ListViewUtils.scrollToBottom(this)
        }, delay)
    }
}