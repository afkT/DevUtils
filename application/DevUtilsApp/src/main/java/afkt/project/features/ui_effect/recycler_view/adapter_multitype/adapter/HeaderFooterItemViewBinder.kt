package afkt.project.features.ui_effect.recycler_view.adapter_multitype.adapter

import afkt.project.R
import afkt.project.databinding.AdapterConcatHeaderFooterBinding
import afkt.project.features.ui_effect.recycler_view.adapter_concat.HeaderFooterItem
import android.view.LayoutInflater
import android.view.ViewGroup
import com.drakeet.multitype.ItemViewBinder
import dev.base.utils.adapter.DevBaseViewBindingVH
import dev.base.utils.adapter.newBindingViewHolder

/**
 * detail: Header、Footer Adapter
 * @author Ttt
 */
class HeaderFooterItemViewBinder :
    ItemViewBinder<HeaderFooterItem, DevBaseViewBindingVH<AdapterConcatHeaderFooterBinding>>() {

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