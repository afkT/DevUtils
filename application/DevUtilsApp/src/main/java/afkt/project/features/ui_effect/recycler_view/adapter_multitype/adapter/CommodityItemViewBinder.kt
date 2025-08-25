package afkt.project.features.ui_effect.recycler_view.adapter_multitype.adapter

import afkt.project.R
import afkt.project.databinding.AdapterConcatCommodityMultiSelectBinding
import afkt.project.features.ui_effect.recycler_view.adapter_concat.CommodityBeanItem
import afkt.project.model.engine.IMAGE_ROUND_3
import android.view.LayoutInflater
import android.view.ViewGroup
import com.drakeet.multitype.ItemViewBinder
import dev.base.utils.adapter.DevBaseViewBindingVH
import dev.base.utils.adapter.newBindingViewHolder
import dev.engine.extensions.image.display
import dev.simple.mvvm.utils.image.toImageConfig
import dev.simple.mvvm.utils.toSource
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.view.ViewHelper

/**
 * detail: Commodity Adapter
 * @author Ttt
 */
class CommodityItemViewBinder :
    ItemViewBinder<CommodityBeanItem, DevBaseViewBindingVH<AdapterConcatCommodityMultiSelectBinding>>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): DevBaseViewBindingVH<AdapterConcatCommodityMultiSelectBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_concat_commodity_multi_select)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterConcatCommodityMultiSelectBinding>,
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
            .setText(itemObj.name, holder.binding.vidNameTv)
            // 商品价格
            .setText(itemObj.priceText, holder.binding.vidPriceTv)
        // 商品图片
        holder.binding.vidPicIv.display(
            source = itemObj.picture.toSource(),
            config = IMAGE_ROUND_3.toImageConfig()
        )
    }
}