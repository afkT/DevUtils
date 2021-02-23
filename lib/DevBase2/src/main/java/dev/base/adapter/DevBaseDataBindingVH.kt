package dev.base.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * detail: RecyclerView ViewDataBinding ViewHolder
 * @author Ttt
 */
abstract class DevBaseDataBindingVH<VDB : ViewDataBinding> : RecyclerView.ViewHolder {

    @JvmField
    val binding: VDB

    constructor(itemView: View) : super(itemView) {
        binding = DataBindingUtil.bind<VDB>(itemView)!!;
    }

    constructor(vdb: VDB) : super(vdb.root) {
        binding = vdb
    }
}