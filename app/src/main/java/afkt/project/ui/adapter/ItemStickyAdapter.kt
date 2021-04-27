package afkt.project.ui.adapter

import afkt.project.R
import afkt.project.databinding.AdapterItemStickyBinding
import afkt.project.model.ItemStickyBean
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder

/**
 * detail: 吸附 Item 预览 View Adapter
 * @author Ttt
 */
class ItemStickyAdapter(data: List<ItemStickyBean>) : DevDataAdapter<ItemStickyBean, DevBaseViewBindingVH<AdapterItemStickyBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterItemStickyBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_item_sticky)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterItemStickyBinding>,
        position: Int
    ) {
        val item = getDataItem(position)
        holder.binding.vidAisTitleTv.text = item.title
        holder.binding.vidAisTimeTv.text = item.timeFormat
    }
}