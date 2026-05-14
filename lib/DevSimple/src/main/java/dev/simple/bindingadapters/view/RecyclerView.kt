package dev.simple.bindingadapters.view

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.simple.bindingadapters.BindingTrigger.shouldTriggerBindingAction

// ==============================
// = RecyclerView BindingAdapter =
// ==============================

/**
 * 通过数据绑定设置 RecyclerView 的固定尺寸标记
 * <pre>
 *     对应布局属性 binding_rv_has_fixed_size。
 * </pre>
 *
 * @param hasFixedSize 是否认为子项尺寸固定，传给 setHasFixedSize
 */
@BindingAdapter("binding_rv_has_fixed_size")
fun RecyclerView.bindingRVHasFixedSize(hasFixedSize: Boolean) {
    setHasFixedSize(hasFixedSize)
}

/**
 * 通过数据绑定设置 RecyclerView 的 item 视图缓存数量
 * <pre>
 *     对应布局属性 binding_rv_item_view_cache_size。
 * </pre>
 *
 * @param size 缓存大小，传给 setItemViewCacheSize
 */
@BindingAdapter("binding_rv_item_view_cache_size")
fun RecyclerView.bindingRVItemViewCacheSize(size: Int) {
    setItemViewCacheSize(size)
}

/**
 * 通过数据绑定移除 RecyclerView 的 item 动画器
 * <pre>
 *     对应布局属性 binding_rv_item_animator_remove。
 *     布尔参数仅为满足 Data Binding 占位，取值不参与逻辑，用于在布局中触发绑定。
 *     若需同值多次触发，改用 binding_rv_item_animator_remove_ts 并绑定正时间戳。
 * </pre>
 *
 * @param unused 占位参数，可为空
 */
@BindingAdapter("binding_rv_item_animator_remove")
fun RecyclerView.bindingRVItemAnimatorRemove(
    unused: Boolean?
) {
    itemAnimator = null
}

/**
 * 通过数据绑定以时间戳移除 RecyclerView 的 item 动画器
 * <pre>
 *     布局属性 binding_rv_item_animator_remove_ts；判定同 [shouldTriggerBindingAction]。
 *     与 binding_rv_item_animator_remove 相比，便于在 ViewModel 中多次触发同一移除命令（布尔同值无法二次刷新）。
 * </pre>
 *
 * @param timestamp 建议绑定 [System.currentTimeMillis] 或 ViewModel 内递增值
 */
@BindingAdapter("binding_rv_item_animator_remove_ts")
fun RecyclerView.bindingRVItemAnimatorRemoveTs(timestamp: Long?) {
    if (!timestamp.shouldTriggerBindingAction()) return
    itemAnimator = null
}