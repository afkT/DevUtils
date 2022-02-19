package afkt.project.feature.dev_widget.recy_item_decoration.linear

import afkt.project.R
import afkt.project.databinding.AdapterLinearVerticalTextBinding
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder

/**
 * detail: Linear Text Adapter
 * @author Ttt
 */
class LinearVerticalTextAdapter(data: MutableList<String>) :
    DevDataAdapter<String, DevBaseViewBindingVH<AdapterLinearVerticalTextBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterLinearVerticalTextBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_linear_vertical_text)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterLinearVerticalTextBinding>,
        position: Int
    ) {
        holder.binding.vidTv.text = getDataItem(position)
    }
}