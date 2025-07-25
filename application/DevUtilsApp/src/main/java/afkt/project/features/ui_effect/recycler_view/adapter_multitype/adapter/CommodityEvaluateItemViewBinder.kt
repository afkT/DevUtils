package afkt.project.features.ui_effect.recycler_view.adapter_multitype.adapter

import afkt.project.R
import afkt.project.databinding.AdapterConcatCommodityEvaluateBinding
import afkt.project.features.ui_effect.recycler_view.adapter_concat.CommodityEvaluateBeanItem
import afkt.project.model.engine.IMAGE_ROUND_3
import android.view.LayoutInflater
import android.view.ViewGroup
import com.drakeet.multitype.ItemViewBinder
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.expand.engine.image.display
import dev.mvvm.utils.image.toImageConfig
import dev.mvvm.utils.toSource
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.view.ViewHelper

/**
 * detail: Commodity Evaluate Adapter
 * @author Ttt
 */
class CommodityEvaluateItemViewBinder :
    ItemViewBinder<CommodityEvaluateBeanItem, DevBaseViewBindingVH<AdapterConcatCommodityEvaluateBinding>>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): DevBaseViewBindingVH<AdapterConcatCommodityEvaluateBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_concat_commodity_evaluate)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterConcatCommodityEvaluateBinding>,
        item: CommodityEvaluateBeanItem
    ) {
        // 统一设置背景
        ViewHelper.get().setBackgroundColor(
            ResourceUtils.getColor(R.color.color_33),
            holder.itemView
        )

        val itemObj = item.obj

        ViewHelper.get()
            // 判断是否显示边距
            .setVisibilitys(itemObj.isFirst, holder.binding.vidLineView)
            // 商品名
            .setText(itemObj.name, holder.binding.vidNameTv)
            // 商品价格
            .setText(itemObj.priceText, holder.binding.vidPriceTv)
            // 评价内容
            .setText(itemObj.evaluateContent, holder.binding.vidContentEt)
            // 禁止点击评价输入框
            .setEnabled(false, holder.binding.vidContentEt)

        // 商品图片
        holder.binding.vidPicIv.display(
            source = itemObj.picture.toSource(),
            config = IMAGE_ROUND_3.toImageConfig()
        )
        // 评星等级
        val ratingBar = holder.binding.vidRatingbar
        ratingBar.setOnRatingChangeListener { _, rating, _ ->
            itemObj.evaluateLevel = rating
        }
        // 设置评星等级
        ratingBar.rating = itemObj.evaluateLevel
    }
}