package afkt.project.feature.ui_effect.recy_adapter.item_slide

import afkt.project.R
import afkt.project.databinding.AdapterMultiSelectBinding
import afkt.project.model.bean.CommodityItem
import afkt_replace.core.lib.utils.image.IMAGE_ROUND_3
import afkt_replace.core.lib.utils.image.toImageConfig
import afkt_replace.core.lib.utils.price.toPriceString
import afkt_replace.core.lib.utils.price.toRMBSubZeroAndDot
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.engine.DevEngine
import dev.utils.app.helper.view.ViewHelper

/**
 * detail: Item Slide Adapter
 * @author Ttt
 */
class ItemSlideAdapter(data: List<CommodityItem>) : DevDataAdapter<CommodityItem, DevBaseViewBindingVH<AdapterMultiSelectBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterMultiSelectBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_multi_select)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterMultiSelectBinding>,
        position: Int
    ) {
        val item = getDataItem(position)
        ViewHelper.get()
            .setText(item.commodityName, holder.binding.vidNameTv)
            .setText(
                item.commodityPrice.toPriceString()?.toRMBSubZeroAndDot(),
                holder.binding.vidPriceTv
            )
        // 商品图片
        DevEngine.getImage()?.display(
            holder.binding.vidIv,
            item.commodityPicture,
            IMAGE_ROUND_3.toImageConfig()
        )
    }
}