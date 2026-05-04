package dev.simple.bindingadapters.view

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

// ==============================
// = RecyclerView BindingAdapter =
// ==============================

@BindingAdapter("binding_rv_has_fixed_size")
fun RecyclerView.bindingRVHasFixedSize(hasFixedSize: Boolean) {
    setHasFixedSize(hasFixedSize)
}

@BindingAdapter("binding_rv_item_view_cache_size")
fun RecyclerView.bindingRVItemViewCacheSize(size: Int) {
    setItemViewCacheSize(size)
}

@BindingAdapter("binding_rv_item_animator_remove")
fun RecyclerView.bindingRVItemAnimatorRemove(
    unused: Boolean?
) {
    itemAnimator = null
}