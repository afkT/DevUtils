package dev.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import dev.base.utils.ViewBindingUtils

/**
 * detail: RecyclerView ViewBinding ViewHolder
 * @author Ttt
 */
class DevBaseViewBindingVH<VB : ViewBinding>(@JvmField val binding: VB) : RecyclerView.ViewHolder(
    binding.root
) {
    companion object {
        @JvmStatic
        fun <VB : ViewBinding> create(
            clazz: Class<VB>,
            parent: ViewGroup,
            @LayoutRes resource: Int
        ) =
            DevBaseViewBindingVH(
                ViewBindingUtils.viewBinding(
                    view = LayoutInflater.from(parent.context).inflate(resource, null),
                    clazz = clazz
                )
            )

        @JvmStatic
        fun <VB : ViewBinding> create(
            clazz: Class<VB>,
            context: Context,
            @LayoutRes resource: Int
        ) =
            DevBaseViewBindingVH(
                ViewBindingUtils.viewBinding(
                    view = LayoutInflater.from(context).inflate(resource, null),
                    clazz = clazz
                )
            )

        @JvmStatic
        fun <VB : ViewBinding> create(
            clazz: Class<VB>,
            view: View
        ) =
            DevBaseViewBindingVH(
                ViewBindingUtils.viewBinding(
                    view = view,
                    clazz = clazz
                )
            )
    }
}

inline fun <reified VB : ViewBinding> newBindingViewHolder(
    parent: ViewGroup,
    @LayoutRes resource: Int
): DevBaseViewBindingVH<VB> =
    DevBaseViewBindingVH.create(VB::class.java, parent, resource)

inline fun <reified VB : ViewBinding> newBindingViewHolder(
    context: Context,
    @LayoutRes resource: Int
): DevBaseViewBindingVH<VB> =
    DevBaseViewBindingVH.create(VB::class.java, context, resource)

inline fun <reified VB : ViewBinding> newBindingViewHolder(
    view: View,
): DevBaseViewBindingVH<VB> =
    DevBaseViewBindingVH.create(VB::class.java, view)

inline fun <reified VB : ViewBinding> newBindingViewHolder(
    binding: VB
): DevBaseViewBindingVH<VB> = DevBaseViewBindingVH(binding)