package afkt.project.feature.ui_effect.recy_adapter_concat.adapter

import afkt.project.R
import afkt.project.databinding.AdapterConcatHeaderFooterBinding
import afkt.project.model.bean.HeaderFooterItem
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder

/**
 * detail: Header、Footer Adapter
 * @author Ttt
 */
class HeaderFooterConcatAdapter(data: List<HeaderFooterItem>) : DevDataAdapter<HeaderFooterItem, DevBaseViewBindingVH<AdapterConcatHeaderFooterBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterConcatHeaderFooterBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_concat_header_footer)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterConcatHeaderFooterBinding>,
        position: Int
    ) {
        holder.binding.vidTitleTv.text = getDataItem(position).title
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.adapter_concat_header_footer
    }
}