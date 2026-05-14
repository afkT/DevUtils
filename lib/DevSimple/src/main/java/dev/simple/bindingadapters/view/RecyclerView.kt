package dev.simple.bindingadapters.view

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.simple.bindingadapters.attribute.RvAdapterNotifyItemAt
import dev.simple.bindingadapters.attribute.RvAdapterNotifyItemMovedAt
import dev.simple.bindingadapters.attribute.RvItemDecorationAddOp
import dev.simple.bindingadapters.shouldTriggerBindingAction
import dev.utils.app.RecyclerViewUtils

// ==============================
// = RecyclerView BindingAdapter =
// ==============================

/**
 * 通过数据绑定设置 RecyclerView 的固定尺寸标记。
 * <pre>
 *     布局属性 binding_rv_has_fixed_size；直接调用 [androidx.recyclerview.widget.RecyclerView.setHasFixedSize]。
 * </pre>
 *
 * @param hasFixedSize 是否认为子项尺寸固定
 */
@BindingAdapter("binding_rv_has_fixed_size")
fun RecyclerView.bindingRVHasFixedSize(hasFixedSize: Boolean) {
    setHasFixedSize(hasFixedSize)
}

/**
 * 通过数据绑定设置 RecyclerView 的 item 视图缓存数量。
 * <pre>
 *     布局属性 binding_rv_item_view_cache_size；直接调用 [androidx.recyclerview.widget.RecyclerView.setItemViewCacheSize]。
 * </pre>
 *
 * @param size 缓存大小
 */
@BindingAdapter("binding_rv_item_view_cache_size")
fun RecyclerView.bindingRVItemViewCacheSize(size: Int) {
    setItemViewCacheSize(size)
}

/**
 * 通过数据绑定移除 RecyclerView 的 item 动画器。
 * <pre>
 *     布局属性 binding_rv_item_animator_remove；布尔参数仅为满足 Data Binding 占位，取值不参与逻辑。
 *     若需同值多次触发，改用 binding_rv_item_animator_remove_ts 并绑定正时间戳。
 * </pre>
 *
 * @param unused 占位参数，可为空
 */
@BindingAdapter("binding_rv_item_animator_remove")
fun RecyclerView.bindingRVItemAnimatorRemove(
    unused: Boolean?,
) {
    itemAnimator = null
}

/**
 * 通过数据绑定以时间戳移除 RecyclerView 的 item 动画器。
 * <pre>
 *     布局属性 binding_rv_item_animator_remove_ts；判定同 [shouldTriggerBindingAction]。
 *     与 binding_rv_item_animator_remove 相比，便于在 ViewModel 中多次触发同一移除命令。
 * </pre>
 *
 * @param timestamp 建议绑定 `System.currentTimeMillis` 或 ViewModel 内递增值
 */
@BindingAdapter("binding_rv_item_animator_remove_ts")
fun RecyclerView.bindingRVItemAnimatorRemoveTs(timestamp: Long?) {
    if (!timestamp.shouldTriggerBindingAction()) return
    itemAnimator = null
}

// ====================
// = RecyclerViewUtils =
// ====================

/**
 * 通过数据绑定设置 LayoutManager。
 * <pre>
 *     布局属性 binding_rv_layout_manager；为 null 时跳过；委托 [RecyclerViewUtils.setLayoutManager]。
 * </pre>
 *
 * @param layoutManager 布局管理器实例，可为 null
 */
@BindingAdapter("binding_rv_layout_manager")
fun RecyclerView.bindingRVLayoutManager(layoutManager: RecyclerView.LayoutManager?) {
    if (layoutManager == null) return
    RecyclerViewUtils.setLayoutManager(this, layoutManager)
}

/**
 * 通过数据绑定设置 Adapter。
 * <pre>
 *     布局属性 binding_rv_adapter；为 null 时跳过（与工具类一致，不执行清空）；委托 [RecyclerViewUtils.setAdapter]。
 * </pre>
 *
 * @param adapter 列表适配器，可为 null
 */
@BindingAdapter("binding_rv_adapter")
fun RecyclerView.bindingRVAdapter(adapter: RecyclerView.Adapter<*>?) {
    if (adapter == null) return
    RecyclerViewUtils.setAdapter(this, adapter)
}

/**
 * 通过数据绑定设置网格或交错网格的 Span 列数。
 * <pre>
 *     布局属性 binding_rv_span_count；为 null 时跳过；span 小于 1 时由工具类返回失败；委托 [RecyclerViewUtils.setSpanCount]。
 * </pre>
 *
 * @param spanCount 列数或跨度，可为 null
 * @return `true` 已应用到 Grid 或 Staggered 管理器，`false` 跳过或类型不匹配
 */
@BindingAdapter("binding_rv_span_count")
fun RecyclerView.bindingRVSpanCount(spanCount: Int?): Boolean {
    val c = spanCount ?: return false
    return RecyclerViewUtils.setSpanCount(this, c)
}

/**
 * 通过数据绑定设置列表方向。
 * <pre>
 *     布局属性 binding_rv_orientation；为 null 时跳过；取值与 [androidx.recyclerview.widget.RecyclerView.VERTICAL]、[androidx.recyclerview.widget.RecyclerView.HORIZONTAL] 一致；委托 [RecyclerViewUtils.setOrientation]。
 * </pre>
 *
 * @param orientation 方向常量，可为 null
 */
@BindingAdapter("binding_rv_orientation")
fun RecyclerView.bindingRVOrientation(orientation: Int?) {
    val o = orientation ?: return
    RecyclerViewUtils.setOrientation(this, o)
}

/**
 * 通过数据绑定设置嵌套滚动开关。
 * <pre>
 *     布局属性 binding_rv_nested_scrolling_enabled；为 null 时跳过；委托 [RecyclerViewUtils.setNestedScrollingEnabled]。
 * </pre>
 *
 * @param enabled 是否允许嵌套滚动，可为 null
 * @return `true` 已设置，`false` 跳过
 */
@BindingAdapter("binding_rv_nested_scrolling_enabled")
fun RecyclerView.bindingRVNestedScrollingEnabled(enabled: Boolean?): Boolean {
    val e = enabled ?: return false
    return RecyclerViewUtils.setNestedScrollingEnabled(this, e)
}

/**
 * 通过数据绑定触发 notifyItemRemoved。
 * <pre>
 *     布局属性 binding_rv_notify_item_removed；payload 为 null 或时间戳无效时跳过；委托 [RecyclerViewUtils.notifyItemRemoved]。
 * </pre>
 *
 * @param payload 时间戳与索引封装，可为 null
 * @return `true` 通知已发出，`false` 跳过或失败
 */
@BindingAdapter("binding_rv_notify_item_removed")
fun RecyclerView.bindingRVNotifyItemRemoved(payload: RvAdapterNotifyItemAt?): Boolean {
    val p = payload ?: return false
    if (!p.timestamp.shouldTriggerBindingAction()) return false
    return RecyclerViewUtils.notifyItemRemoved(this, p.position)
}

/**
 * 通过数据绑定触发 notifyItemInserted。
 * <pre>
 *     布局属性 binding_rv_notify_item_inserted；语义同 binding_rv_notify_item_removed；委托 [RecyclerViewUtils.notifyItemInserted]。
 * </pre>
 *
 * @param payload 时间戳与索引封装，可为 null
 * @return `true` 通知已发出，`false` 跳过或失败
 */
@BindingAdapter("binding_rv_notify_item_inserted")
fun RecyclerView.bindingRVNotifyItemInserted(payload: RvAdapterNotifyItemAt?): Boolean {
    val p = payload ?: return false
    if (!p.timestamp.shouldTriggerBindingAction()) return false
    return RecyclerViewUtils.notifyItemInserted(this, p.position)
}

/**
 * 通过数据绑定触发 notifyItemMoved。
 * <pre>
 *     布局属性 binding_rv_notify_item_moved；委托 [RecyclerViewUtils.notifyItemMoved]。
 * </pre>
 *
 * @param payload 时间戳与起止索引封装，可为 null
 * @return `true` 通知已发出，`false` 跳过或失败
 */
@BindingAdapter("binding_rv_notify_item_moved")
fun RecyclerView.bindingRVNotifyItemMoved(payload: RvAdapterNotifyItemMovedAt?): Boolean {
    val p = payload ?: return false
    if (!p.timestamp.shouldTriggerBindingAction()) return false
    return RecyclerViewUtils.notifyItemMoved(this, p.fromPosition, p.toPosition)
}

/**
 * 通过数据绑定触发 notifyDataSetChanged。
 * <pre>
 *     布局属性 binding_rv_notify_data_set_changed_ts；判定同 [shouldTriggerBindingAction]；委托 [RecyclerViewUtils.notifyDataSetChanged]。
 *     建议用细粒度 notify 替代；此处与工具类行为对齐。
 * </pre>
 *
 * @param timestamp 触发用时间戳，可为 null
 * @return `true` 已通知，`false` 跳过或失败
 */
@BindingAdapter("binding_rv_notify_data_set_changed_ts")
fun RecyclerView.bindingRVNotifyDataSetChangedTs(timestamp: Long?): Boolean {
    if (!timestamp.shouldTriggerBindingAction()) return false
    return RecyclerViewUtils.notifyDataSetChanged(this)
}

/**
 * 通过数据绑定附加 LinearSnapHelper。
 * <pre>
 *     布局属性 binding_rv_attach_linear_snap_helper_ts；判定同 [shouldTriggerBindingAction]；委托 [RecyclerViewUtils.attachLinearSnapHelper]。
 * </pre>
 *
 * @param timestamp 触发用时间戳，可为 null
 * @return `true` 已附加或 helper 非空，`false` 跳过或失败
 */
@BindingAdapter("binding_rv_attach_linear_snap_helper_ts")
fun RecyclerView.bindingRVAttachLinearSnapHelperTs(timestamp: Long?): Boolean {
    if (!timestamp.shouldTriggerBindingAction()) return false
    return RecyclerViewUtils.attachLinearSnapHelper(this) != null
}

/**
 * 通过数据绑定附加 PagerSnapHelper。
 * <pre>
 *     布局属性 binding_rv_attach_pager_snap_helper_ts；判定同 [shouldTriggerBindingAction]；委托 [RecyclerViewUtils.attachPagerSnapHelper]。
 * </pre>
 *
 * @param timestamp 触发用时间戳，可为 null
 * @return `true` 已附加或 helper 非空，`false` 跳过或失败
 */
@BindingAdapter("binding_rv_attach_pager_snap_helper_ts")
fun RecyclerView.bindingRVAttachPagerSnapHelperTs(timestamp: Long?): Boolean {
    if (!timestamp.shouldTriggerBindingAction()) return false
    return RecyclerViewUtils.attachPagerSnapHelper(this) != null
}

/**
 * 通过数据绑定添加 ItemDecoration。
 * <pre>
 *     布局属性 binding_rv_item_decoration_add；为 null 时跳过；委托 [RecyclerViewUtils.addItemDecoration] 对应重载。
 * </pre>
 *
 * @param op 装饰与可选下标封装，可为 null
 * @return `true` 添加成功，`false` 跳过或失败
 */
@BindingAdapter("binding_rv_item_decoration_add")
fun RecyclerView.bindingRVItemDecorationAdd(op: RvItemDecorationAddOp?): Boolean {
    val o = op ?: return false
    return if (o.index == null) {
        RecyclerViewUtils.addItemDecoration(this, o.decor)
    } else {
        RecyclerViewUtils.addItemDecoration(this, o.decor, o.index)
    }
}

/**
 * 通过数据绑定移除指定 ItemDecoration。
 * <pre>
 *     布局属性 binding_rv_item_decoration_remove；为 null 时跳过；委托 [RecyclerViewUtils.removeItemDecoration]。
 * </pre>
 *
 * @param decor 与添加时同一实例，可为 null
 * @return `true` 移除成功，`false` 跳过或失败
 */
@BindingAdapter("binding_rv_item_decoration_remove")
fun RecyclerView.bindingRVItemDecorationRemove(decor: RecyclerView.ItemDecoration?): Boolean {
    val d = decor ?: return false
    return RecyclerViewUtils.removeItemDecoration(this, d)
}

/**
 * 通过数据绑定按下标移除 ItemDecoration。
 * <pre>
 *     布局属性 binding_rv_item_decoration_remove_at；为 null 时跳过；委托 [RecyclerViewUtils.removeItemDecorationAt]。
 * </pre>
 *
 * @param index 装饰下标，可为 null
 * @return `true` 移除成功，`false` 跳过或失败
 */
@BindingAdapter("binding_rv_item_decoration_remove_at")
fun RecyclerView.bindingRVItemDecorationRemoveAt(index: Int?): Boolean {
    val i = index ?: return false
    return RecyclerViewUtils.removeItemDecorationAt(this, i)
}

/**
 * 通过数据绑定移除全部 ItemDecoration。
 * <pre>
 *     布局属性 binding_rv_remove_all_item_decoration_ts；判定同 [shouldTriggerBindingAction]；委托 [RecyclerViewUtils.removeAllItemDecoration]。
 * </pre>
 *
 * @param timestamp 触发用时间戳，可为 null
 * @return `true` 已执行清理流程，`false` 跳过或失败
 */
@BindingAdapter("binding_rv_remove_all_item_decoration_ts")
fun RecyclerView.bindingRVRemoveAllItemDecorationTs(timestamp: Long?): Boolean {
    if (!timestamp.shouldTriggerBindingAction()) return false
    return RecyclerViewUtils.removeAllItemDecoration(this)
}

/**
 * 通过数据绑定添加滚动监听。
 * <pre>
 *     布局属性 binding_rv_add_on_scroll_listener；为 null 时跳过；委托 [RecyclerViewUtils.addOnScrollListener]。
 * </pre>
 *
 * @param listener 监听实例，可为 null
 * @return `true` 添加成功，`false` 跳过或失败
 */
@BindingAdapter("binding_rv_add_on_scroll_listener")
fun RecyclerView.bindingRVAddOnScrollListener(listener: RecyclerView.OnScrollListener?): Boolean {
    val l = listener ?: return false
    return RecyclerViewUtils.addOnScrollListener(this, l)
}

/**
 * 通过数据绑定移除滚动监听。
 * <pre>
 *     布局属性 binding_rv_remove_on_scroll_listener；为 null 时跳过；委托 [RecyclerViewUtils.removeOnScrollListener]。
 * </pre>
 *
 * @param listener 与添加时同一实例，可为 null
 * @return `true` 移除成功，`false` 跳过或失败
 */
@BindingAdapter("binding_rv_remove_on_scroll_listener")
fun RecyclerView.bindingRVRemoveOnScrollListener(listener: RecyclerView.OnScrollListener?): Boolean {
    val l = listener ?: return false
    return RecyclerViewUtils.removeOnScrollListener(this, l)
}

/**
 * 通过数据绑定清空全部滚动监听。
 * <pre>
 *     布局属性 binding_rv_clear_on_scroll_listeners_ts；判定同 [shouldTriggerBindingAction]；委托 [RecyclerViewUtils.clearOnScrollListeners]。
 * </pre>
 *
 * @param timestamp 触发用时间戳，可为 null
 * @return `true` 已清空，`false` 跳过或失败
 */
@BindingAdapter("binding_rv_clear_on_scroll_listeners_ts")
fun RecyclerView.bindingRVClearOnScrollListenersTs(timestamp: Long?): Boolean {
    if (!timestamp.shouldTriggerBindingAction()) return false
    return RecyclerViewUtils.clearOnScrollListeners(this)
}