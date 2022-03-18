package afkt.project.feature.ui_effect.recy_adapter.adapter_multitype.adapter

import afkt.project.R
import afkt.project.databinding.AdapterMultiSelectBinding
import afkt.project.feature.ui_effect.recy_adapter.CommodityBeanItem
import afkt_replace.core.lib.utils.image.IMAGE_ROUND_3
import afkt_replace.core.lib.utils.image.toImageConfig
import afkt_replace.core.lib.utils.price.toPriceString
import afkt_replace.core.lib.utils.price.toRMBSubZeroAndDot
import android.view.LayoutInflater
import android.view.ViewGroup
import com.drakeet.multitype.ItemViewBinder
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.engine.DevEngine
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.view.ViewHelper

/**
 * detail: Commodity Adapter
 * @author Ttt
 */
class CommodityItemViewBinder : ItemViewBinder<CommodityBeanItem, DevBaseViewBindingVH<AdapterMultiSelectBinding>>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): DevBaseViewBindingVH<AdapterMultiSelectBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_multi_select)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterMultiSelectBinding>,
        item: CommodityBeanItem
    ) {
        // 统一设置背景
        ViewHelper.get().setBackgroundColor(
            ResourceUtils.getColor(R.color.color_33),
            holder.itemView
        )

        val itemObj = item.obj

        ViewHelper.get()
            // 是否显示编辑按钮
            .setVisibilitys(false, holder.binding.vidIv)
            // 判断是否显示边距
            .setVisibilitys(itemObj.isFirst, holder.binding.vidLineView)
            // 商品名
            .setText(itemObj.commodityName, holder.binding.vidNameTv)
            // 商品价格
            .setText(
                itemObj.commodityPrice.toPriceString()?.toRMBSubZeroAndDot(),
                holder.binding.vidPriceTv
            )
        // 商品图片
        DevEngine.getImage()?.display(
            holder.binding.vidPicIv,
            itemObj.commodityPicture,
            IMAGE_ROUND_3.toImageConfig()
        )
    }
}