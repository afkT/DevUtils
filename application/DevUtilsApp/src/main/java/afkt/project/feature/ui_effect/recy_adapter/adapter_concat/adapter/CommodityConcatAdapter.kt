package afkt.project.feature.ui_effect.recy_adapter.adapter_concat.adapter

import afkt.project.R
import afkt.project.base.helper.IMAGE_ROUND_3
import afkt.project.databinding.AdapterItemEditsBinding
import afkt.project.databinding.AdapterMultiSelectBinding
import afkt.project.feature.ui_effect.recy_adapter.CommodityBean
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.adapter.DevDataAdapter
import dev.expand.engine.image.display
import dev.mvvm.utils.image.toImageConfig
import dev.mvvm.utils.toPriceString
import dev.mvvm.utils.toRMBSubZeroAndDot
import dev.mvvm.utils.toSource
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.view.ViewHelper

/**
 * detail: Commodity、Evaluate Adapter
 * @author Ttt
 */
class CommodityConcatAdapter(data: List<CommodityBean>) :
    DevDataAdapter<CommodityBean, RecyclerView.ViewHolder>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        parentContext(parent)
        if (viewType == R.layout.adapter_item_edits) {
            // 商品评价类型
            return CommodityEvaluateHolder(
                AdapterItemEditsBinding.inflate(
                    LayoutInflater.from(context),
                    parent, false
                )
            )
        }
        // 商品类型
        return CommodityHolder(
            AdapterMultiSelectBinding.inflate(
                LayoutInflater.from(context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        // 统一设置背景
        ViewHelper.get().setBackgroundColor(
            ResourceUtils.getColor(R.color.color_33),
            holder.itemView
        )

        val item = getDataItem(position)
        if (holder is CommodityHolder) {
            ViewHelper.get()
                // 是否显示编辑按钮
                .setVisibilitys(false, holder.binding.vidIv)
                // 判断是否显示边距
                .setVisibilitys(position == 0, holder.binding.vidLineView)
                // 商品名
                .setText(item.commodityName, holder.binding.vidNameTv)
                // 商品价格
                .setText(
                    item.commodityPrice.toPriceString()?.toRMBSubZeroAndDot(),
                    holder.binding.vidPriceTv
                )
            // 商品图片
            holder.binding.vidPicIv.display(
                source = item.commodityPicture.toSource(),
                config = IMAGE_ROUND_3.toImageConfig()
            )

        } else if (holder is CommodityEvaluateHolder) {
            ViewHelper.get()
                // 判断是否显示边距
                .setVisibilitys(position == 0, holder.binding.vidLineView)
                // 商品名
                .setText(item.commodityName, holder.binding.vidNameTv)
                // 商品价格
                .setText(
                    item.commodityPrice.toPriceString()?.toRMBSubZeroAndDot(),
                    holder.binding.vidPriceTv
                )
                // 评价内容
                .setText(item.evaluateContent, holder.binding.vidContentEt)
                // 禁止点击评价输入框
                .setEnabled(false, holder.binding.vidContentEt)

            // 商品图片
            holder.binding.vidPicIv.display(
                source = item.commodityPicture.toSource(),
                config = IMAGE_ROUND_3.toImageConfig()
            )
            // 评星等级
            val ratingBar = holder.binding.vidRatingbar
            ratingBar.setOnRatingChangeListener { _, rating, _ ->
                item.evaluateLevel = rating
            }
            // 设置评星等级
            ratingBar.rating = item.evaluateLevel
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (getDataItem(position).isEvaluateCommodity) {
            // 商品评价类型
            return R.layout.adapter_item_edits
        }
        // 商品类型
        return R.layout.adapter_multi_select
    }

    class CommodityHolder(val binding: AdapterMultiSelectBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    class CommodityEvaluateHolder(val binding: AdapterItemEditsBinding) : RecyclerView.ViewHolder(
        binding.root
    )
}