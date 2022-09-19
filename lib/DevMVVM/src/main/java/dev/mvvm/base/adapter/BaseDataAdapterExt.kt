package dev.mvvm.base.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import dev.adapter.DevDataAdapterExt
import dev.base.adapter.DevBaseViewDataBindingVH
import dev.base.adapter.newDataBindingViewHolder
import dev.mvvm.base.adapter.item.ItemBinding

/**
 * detail: 通用 DataBinding Data AdapterExt
 * @author Ttt
 */
internal abstract class BaseDataAdapterExt<T, VDB : ViewDataBinding>(
    val itemBinding: ItemBinding<T>
) : DevDataAdapterExt<T, DevBaseViewDataBindingVH<VDB>>() {

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
        onItemBinding(holder.binding, position, getDataItem(position))
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