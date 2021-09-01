package afkt.project.ui.adapter.concat

import afkt.project.R
import afkt.project.databinding.AdapterItemEditsBinding
import afkt.project.databinding.AdapterMultiSelectBinding
import afkt.project.model.bean.CommodityBean
import afkt.project.util.ProjectUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.adapter.DevDataAdapter
import dev.engine.DevEngine
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.ViewHelper
import dev.utils.common.BigDecimalUtils
import java.math.BigDecimal

/**
 * detail: Commodity、Evaluate Adapter
 * @author Ttt
 */
class CommodityConcatAdapter(data: List<CommodityBean>) : DevDataAdapter<CommodityBean, RecyclerView.ViewHolder>() {

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
            holder.itemView,
            ResourceUtils.getColor(R.color.color_33)
        )

        val item = getDataItem(position)
        if (holder is CommodityHolder) {
            ViewHelper.get()
                // 是否显示编辑按钮
                .setVisibility(false, holder.binding.vidAmsIgview)
                // 判断是否显示边距
                .setVisibility(position == 0, holder.binding.vidAmsLine)
                // 商品名
                .setText(holder.binding.vidAmsNameTv, item.commodityName)
                // 商品价格
                .setText(
                    holder.binding.vidAmsPriceTv, "￥" + BigDecimalUtils.round(
                        item.commodityPrice, 2, BigDecimal.ROUND_HALF_UP
                    )
                )
            // 商品图片
            DevEngine.getImage()?.display(
                holder.binding.vidAmsPicIgview,
                item.commodityPicture,
                ProjectUtils.roundConfig3
            )

        } else if (holder is CommodityEvaluateHolder) {
            ViewHelper.get()
                // 判断是否显示边距
                .setVisibility(position == 0, holder.binding.vidAieLine)
                // 商品名
                .setText(holder.binding.vidAieNameTv, item.commodityName)
                // 商品价格
                .setText(
                    holder.binding.vidAiePriceTv, "￥" + BigDecimalUtils.round(
                        item.commodityPrice, 2, BigDecimal.ROUND_HALF_UP
                    )
                )
                // 评价内容
                .setText(holder.binding.vidAieContentEdit, item.evaluateContent)
                // 禁止点击评价输入框
                .setEnabled(false, holder.binding.vidAieContentEdit)

            // 商品图片
            DevEngine.getImage()?.display(
                holder.binding.vidAiePicIgview,
                item.commodityPicture,
                ProjectUtils.roundConfig3
            )
            // 评星等级
            val ratingBar = holder.binding.vidAieRatingbar
            ratingBar.setOnRatingChangeListener { ratingCount ->
                item.evaluateLevel = ratingCount
            }
            // 设置评星等级
            ratingBar.setStar(item.evaluateLevel)
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