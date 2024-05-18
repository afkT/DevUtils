package afkt.project.feature.ui_effect.recy_adapter.item_slide

import afkt.project.R
import afkt.project.base.IMAGE_ROUND_3
import afkt.project.data_model.bean.CommodityItem
import afkt.project.databinding.AdapterMultiSelectBinding
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.expand.engine.image.display
import dev.mvvm.utils.image.toImageConfig
import dev.mvvm.utils.toPriceString
import dev.mvvm.utils.toRMBSubZeroAndDot
import dev.mvvm.utils.toSource
import dev.utils.app.helper.view.ViewHelper

/**
 * detail: Item Slide Adapter
 * @author Ttt
 */
class ItemSlideAdapter(data: List<CommodityItem>) :
    DevDataAdapter<CommodityItem, DevBaseViewBindingVH<AdapterMultiSelectBinding>>() {

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
        holder.binding.vidIv.display(
            source = item.commodityPicture?.toSource(),
            config = IMAGE_ROUND_3.toImageConfig()
        )
    }
}