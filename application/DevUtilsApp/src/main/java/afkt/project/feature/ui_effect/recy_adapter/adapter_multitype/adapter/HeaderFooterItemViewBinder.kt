package afkt.project.feature.ui_effect.recy_adapter.adapter_multitype.adapter

import afkt.project.R
import afkt.project.databinding.AdapterConcatHeaderFooterBinding
import afkt.project.feature.ui_effect.recy_adapter.HeaderFooterItem
import android.view.LayoutInflater
import android.view.ViewGroup
import com.drakeet.multitype.ItemViewBinder
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder

/**
 * detail: Header、Footer Adapter
 * @author Ttt
 */
class HeaderFooterItemViewBinder : ItemViewBinder<HeaderFooterItem, DevBaseViewBindingVH<AdapterConcatHeaderFooterBinding>>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): DevBaseViewBindingVH<AdapterConcatHeaderFooterBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_concat_header_footer)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterConcatHeaderFooterBinding>,
        item: HeaderFooterItem
    ) {
        holder.binding.vidTitleTv.text = item.title
    }
}