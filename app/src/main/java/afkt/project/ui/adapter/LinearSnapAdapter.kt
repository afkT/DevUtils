package afkt.project.ui.adapter

import afkt.project.R
import afkt.project.databinding.AdapterLinearSnapBinding
import afkt.project.model.bean.ItemBean
import afkt.project.util.ProjectUtils
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.engine.image.DevImageEngine
import dev.utils.app.helper.ViewHelper

/**
 * detail: RecyclerView Gallery 效果 Adapter
 * @author Ttt
 */
class LinearSnapAdapter(data: List<ItemBean>) : DevDataAdapter<ItemBean, DevBaseViewBindingVH<AdapterLinearSnapBinding>>() {

    init {
        dataList = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterLinearSnapBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_linear_snap)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterLinearSnapBinding>,
        position: Int
    ) {
        val item = getDataItem(position)
        ViewHelper.get()
            .setText(holder.binding.vidAlsTitleTv, item.title)
            .setText(holder.binding.vidAlsSubtitleTv, item.subtitle)
            .setText(holder.binding.vidAlsTimeTv, item.timeFormat)
        DevImageEngine.getEngine().display(
            holder.binding.vidAlsIgview,
            item.imageUrl,
            ProjectUtils.getRoundConfig10()
        )
    }
}

/**
 * detail: RecyclerView Gallery 效果 Adapter
 * @author Ttt
 */
class LinearSnapAdapter1(data: List<ItemBean>) : DevDataAdapter<ItemBean, RecyclerView.ViewHolder>() {

    init {
        dataList = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
//        var holder: DevBaseViewBindingVH<AdapterLinearSnapBinding> =
//            newBindingViewHolder(parent, R.layout.adapter_linear_snap)
//        return holder
        return DevBaseViewBindingVH.create(
            AdapterLinearSnapBinding::class.java,
            parent, R.layout.adapter_linear_snap
        )
    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        position: Int
    ) {
        var holder = viewHolder as DevBaseViewBindingVH<AdapterLinearSnapBinding>
        val item = getDataItem(position)
        ViewHelper.get()
            .setText(holder.binding.vidAlsTitleTv, item.title)
            .setText(holder.binding.vidAlsSubtitleTv, item.subtitle)
            .setText(holder.binding.vidAlsTimeTv, item.timeFormat)
        DevImageEngine.getEngine().display(
            holder.binding.vidAlsIgview,
            item.imageUrl,
            ProjectUtils.getRoundConfig10()
        )
    }
}