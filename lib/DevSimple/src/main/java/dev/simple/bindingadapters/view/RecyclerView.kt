package dev.simple.bindingadapters.view

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

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