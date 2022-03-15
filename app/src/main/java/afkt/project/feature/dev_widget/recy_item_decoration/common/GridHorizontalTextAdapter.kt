package afkt.project.feature.dev_widget.recy_item_decoration.common

import afkt.project.R
import afkt.project.databinding.AdapterGridHorizontalTextBinding
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder

/**
 * detail: Grid Text Adapter
 * @author Ttt
 */
class GridHorizontalTextAdapter(data: MutableList<String>) :
    DevDataAdapter<String, DevBaseViewBindingVH<AdapterGridHorizontalTextBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterGridHorizontalTextBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_grid_horizontal_text)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterGridHorizontalTextBinding>,
        position: Int
    ) {
        holder.binding.vidTv.text = getDataItem(position)
    }
}