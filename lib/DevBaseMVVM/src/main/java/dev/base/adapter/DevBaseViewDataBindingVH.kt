package dev.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * detail: RecyclerView ViewDataBinding ViewHolder
 * @author Ttt
 */
class DevBaseViewDataBindingVH<VDB : ViewDataBinding>(@JvmField val binding: VDB) : RecyclerView.ViewHolder(
    binding.root
) {
    companion object {
        @JvmStatic
        fun <VDB : ViewDataBinding> create(
            parent: ViewGroup,
            @LayoutRes resource: Int
        ) =
            DevBaseViewDataBindingVH(
                DataBindingUtil.bind<VDB>(
                    LayoutInflater.from(parent.context).inflate(resource, null)
                )!!
            )

        @JvmStatic
        fun <VDB : ViewDataBinding> create(
            context: Context,
            @LayoutRes resource: Int
        ) =
            DevBaseViewDataBindingVH(
                DataBindingUtil.bind<VDB>(
                    LayoutInflater.from(context).inflate(resource, null)
                )!!
            )

        @JvmStatic
        fun <VDB : ViewDataBinding> create(
            view: View
        ) =
            DevBaseViewDataBindingVH(
                DataBindingUtil.bind<VDB>(view)!!
            )
    }
}

fun <VDB : ViewDataBinding> newDataBindingViewHolder(
    parent: ViewGroup,
    @LayoutRes resource: Int
): DevBaseViewDataBindingVH<VDB> =
    DevBaseViewDataBindingVH.create(parent, resource)

fun <VDB : ViewDataBinding> newDataBindingViewHolder(
    context: Context,
    @LayoutRes resource: Int
): DevBaseViewDataBindingVH<VDB> =
    DevBaseViewDataBindingVH.create(context, resource)

fun <VDB : ViewDataBinding> newDataBindingViewHolder(
    view: View,
): DevBaseViewDataBindingVH<VDB> =
    DevBaseViewDataBindingVH.create(view)

fun <VDB : ViewDataBinding> newDataBindingViewHolder(
    binding: VDB
): DevBaseViewDataBindingVH<VDB> = DevBaseViewDataBindingVH(binding)