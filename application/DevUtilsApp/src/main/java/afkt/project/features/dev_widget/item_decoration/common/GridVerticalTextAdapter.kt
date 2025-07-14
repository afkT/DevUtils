package afkt.project.features.dev_widget.item_decoration.common

import afkt.project.R
import afkt.project.databinding.AdapterGridVerticalTextBinding
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder

/**
 * detail: Grid Text Adapter
 * @author Ttt
 */
class GridVerticalTextAdapter(data: MutableList<String>) :
    DevDataAdapter<String, DevBaseViewBindingVH<AdapterGridVerticalTextBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterGridVerticalTextBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_grid_vertical_text)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterGridVerticalTextBinding>,
        position: Int
    ) {
        holder.binding.vidTv.text = getDataItem(position)
    }
}