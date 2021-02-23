package dev.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import dev.base.utils.ViewBindingUtils

/**
 * detail: RecyclerView ViewBinding ViewHolder
 * @author Ttt
 */
abstract class DevBaseViewBindingVH<VB : ViewBinding> : RecyclerView.ViewHolder {

    @JvmField
    val binding: VB

    constructor(itemView: View) : super(itemView) {
        binding = ViewBindingUtils.viewBindingJavaClass(null, null, itemView, javaClass)
    }

    constructor(vb: VB) : super(vb.root) {
        binding = vb
    }
}