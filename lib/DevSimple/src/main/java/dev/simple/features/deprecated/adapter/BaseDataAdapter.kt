package dev.simple.features.deprecated.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import dev.adapter.DevDataAdapter
import dev.base.utils.adapter.DevBaseViewDataBindingVH
import dev.base.utils.adapter.newDataBindingViewHolder
import dev.simple.features.deprecated.adapter.item.ItemBinding
import dev.simple.features.deprecated.adapter.item.ItemLifecycle

/**
 * detail: 通用 DataBinding Data Adapter
 * @author Ttt
 */
abstract class BaseDataAdapter<T, VDB : ViewDataBinding>(
    val itemBinding: ItemBinding<T>,
    var itemLifecycle: ItemLifecycle = ItemLifecycle.of()
) : DevDataAdapter<T, DevBaseViewDataBindingVH<VDB>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewDataBindingVH<VDB> {
        return newDataBindingViewHolder(parent, itemBinding.layoutRes)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewDataBindingVH<VDB>,
        position: Int
    ) {
        itemLifecycle.tryGetLifecycleOwner(holder.itemView)
        onItemBinding(holder.binding, position, getDataItem(position))
        itemLifecycle.getLifecycleOwner()?.let {
            holder.binding.lifecycleOwner = it
        }
    }

    /**
     * Item DataBinding
     * @param binding VDB
     * @param position Int
     * @param item 对应索引实体类
     */
    abstract fun onItemBinding(
        binding: VDB,
        position: Int,
        item: T
    )
}