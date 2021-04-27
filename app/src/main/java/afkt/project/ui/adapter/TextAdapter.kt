package afkt.project.ui.adapter

import afkt.project.R
import afkt.project.databinding.AdapterTextBinding
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder

/**
 * detail: Text Adapter
 * @author Ttt
 */
class TextAdapter(data: MutableList<String>) : DevDataAdapter<String, DevBaseViewBindingVH<AdapterTextBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterTextBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_text)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterTextBinding>,
        position: Int
    ) {
        holder.binding.vidAtTitleTv.text = getDataItem(position)
    }
}