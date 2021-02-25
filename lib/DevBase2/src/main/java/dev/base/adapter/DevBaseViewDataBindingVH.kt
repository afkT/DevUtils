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
class DevBaseViewDataBindingVH<VDB : ViewDataBinding> : RecyclerView.ViewHolder {

    @JvmField
    val binding: VDB

    constructor(itemView: View) : super(itemView) {
        binding = DataBindingUtil.bind<VDB>(itemView)!!;
    }

    constructor(vdb: VDB) : super(vdb.root) {
        binding = vdb
    }

    constructor(
        parent: ViewGroup,
        @LayoutRes resource: Int
    ) : this(LayoutInflater.from(parent.context).inflate(resource, null))

    constructor(
        context: Context,
        @LayoutRes resource: Int
    ) : this(LayoutInflater.from(context).inflate(resource, null))
}