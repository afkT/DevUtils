package afkt.project.ui.adapter

import afkt.project.R
import afkt.project.databinding.AdapterLinearSnapBinding
import afkt.project.model.bean.ItemBean
import afkt.project.util.ProjectUtils
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.engine.DevEngine
import dev.utils.app.helper.view.ViewHelper

/**
 * detail: RecyclerView Gallery 效果 Adapter
 * @author Ttt
 */
class LinearSnapAdapter(data: List<ItemBean>) : DevDataAdapter<ItemBean, DevBaseViewBindingVH<AdapterLinearSnapBinding>>() {

    init {
        setDataList(data, false)
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
        DevEngine.getImage()?.display(
            holder.binding.vidAlsIgview,
            item.imageUrl,
            ProjectUtils.roundConfig10
        )
    }
}