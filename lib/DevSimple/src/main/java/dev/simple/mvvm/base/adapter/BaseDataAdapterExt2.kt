package dev.simple.mvvm.base.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import dev.adapter.DevDataAdapterExt2
import dev.base.utils.adapter.DevBaseViewDataBindingVH
import dev.base.utils.adapter.newDataBindingViewHolder
import dev.simple.mvvm.base.adapter.item.ItemBinding

/**
 * detail: 通用 DataBinding Data AdapterExt2
 * @author Ttt
 */
abstract class BaseDataAdapterExt2<T, VDB : ViewDataBinding>(
    val itemBinding: ItemBinding<T>
) : DevDataAdapterExt2<T, DevBaseViewDataBindingVH<VDB>>() {

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