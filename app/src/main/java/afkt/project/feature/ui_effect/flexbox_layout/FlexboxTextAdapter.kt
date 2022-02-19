package afkt.project.feature.ui_effect.flexbox_layout

import afkt.project.R
import afkt.project.databinding.AdapterFlexboxTextBinding
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder

/**
 * detail: Text Adapter
 * @author Ttt
 */
class FlexboxTextAdapter(data: MutableList<String>) : DevDataAdapter<String, DevBaseViewBindingVH<AdapterFlexboxTextBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterFlexboxTextBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_flexbox_text)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterFlexboxTextBinding>,
        position: Int
    ) {
        holder.binding.vidTitleTv.text = getDataItem(position)
    }
}