package afkt.project.ui.adapter.multitype

import afkt.project.R
import afkt.project.databinding.AdapterItemEditsBinding
import afkt.project.model.bean.CommodityEvaluateBeanItem
import afkt.project.utils.ProjectUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import com.drakeet.multitype.ItemViewBinder
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.engine.DevEngine
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.view.ViewHelper
import dev.utils.common.BigDecimalUtils
import java.math.BigDecimal

/**
 * detail: Commodity Evaluate Adapter
 * @author Ttt
 */
class CommodityEvaluateItemViewBinder : ItemViewBinder<CommodityEvaluateBeanItem, DevBaseViewBindingVH<AdapterItemEditsBinding>>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): DevBaseViewBindingVH<AdapterItemEditsBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_item_edits)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterItemEditsBinding>,
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
            .setText(itemObj.commodityName, holder.binding.vidNameTv)
            // 商品价格
            .setText(
                "￥" + BigDecimalUtils.round(
                    itemObj.commodityPrice, 2, BigDecimal.ROUND_HALF_UP
                ), holder.binding.vidPriceTv
            )
            // 评价内容
            .setText(itemObj.evaluateContent, holder.binding.vidContentEt)
            // 禁止点击评价输入框
            .setEnabled(false, holder.binding.vidContentEt)

        // 商品图片
        DevEngine.getImage()?.display(
            holder.binding.vidPicIv,
            itemObj.commodityPicture,
            ProjectUtils.roundConfig3
        )
        // 评星等级
        val ratingBar = holder.binding.vidRatingbar
        ratingBar.setOnRatingChangeListener { ratingCount ->
            itemObj.evaluateLevel = ratingCount
        }
        // 设置评星等级
        ratingBar.setStar(itemObj.evaluateLevel)
    }
}