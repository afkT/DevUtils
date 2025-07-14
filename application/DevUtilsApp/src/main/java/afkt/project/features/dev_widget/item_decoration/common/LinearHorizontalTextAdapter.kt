package afkt.project.features.dev_widget.item_decoration.common

import afkt.project.R
import afkt.project.databinding.AdapterLinearHorizontalTextBinding
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder

/**
 * detail: Linear Text Adapter
 * @author Ttt
 */
class LinearHorizontalTextAdapter(data: MutableList<String>) :
    DevDataAdapter<String, DevBaseViewBindingVH<AdapterLinearHorizontalTextBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterLinearHorizontalTextBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_linear_horizontal_text)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterLinearHorizontalTextBinding>,
        position: Int
    ) {
        holder.binding.vidTv.text = getDataItem(position)
    }
}