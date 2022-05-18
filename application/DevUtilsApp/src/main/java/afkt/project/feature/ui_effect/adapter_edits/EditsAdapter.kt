package afkt.project.feature.ui_effect.adapter_edits

import afkt.project.R
import afkt.project.databinding.AdapterItemEditsBinding
import afkt.project.model.bean.EvaluateItem
import afkt_replace.core.lib.utils.engine.image.IMAGE_ROUND_3
import afkt_replace.core.lib.utils.engine.image.display
import afkt_replace.core.lib.utils.engine.image.toImageConfig
import afkt_replace.core.lib.utils.price.toPriceString
import afkt_replace.core.lib.utils.price.toRMBSubZeroAndDot
import afkt_replace.core.lib.utils.toSource
import android.view.ViewGroup
import dev.adapter.DevDataAdapterExt
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.utils.common.StringUtils

/**
 * detail: Item EditText 输入监听 Adapter
 * @author Ttt
 */
class EditsAdapter(data: List<EvaluateItem>) : DevDataAdapterExt<EvaluateItem, DevBaseViewBindingVH<AdapterItemEditsBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterItemEditsBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_item_edits)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterItemEditsBinding>,
        position: Int
    ) {
        val item = getDataItem(position)

        // ==========
        // = 商品信息 =
        // ==========

        val commodity = item.commodityItem

        // 商品名
        holder.binding.vidNameTv.text = commodity.commodityName

        // 商品价格
        holder.binding.vidPriceTv.text =
            commodity.commodityPrice.toPriceString()?.toRMBSubZeroAndDot()

        // 商品图片
        holder.binding.vidPicIv.display(
            source = commodity.commodityPicture?.toSource(),
            config = IMAGE_ROUND_3.toImageConfig()
        )
        // 评星等级
        val ratingBar = holder.binding.vidRatingbar
        ratingBar.setOnRatingChangeListener { ratingCount ->
            item.evaluateLevel = ratingCount
        }
        // 设置评星等级
        ratingBar.setStar(item.evaluateLevel)

        // ==========
        // = 输入监听 =
        // ==========

        // 评价内容字数
        val numberTv = holder.binding.vidNumberTv
        // 计算已经输入的内容长度
        numberTv.text = "${120 - StringUtils.length(item.evaluateContent)}"
        // 绑定监听事件
        mTextWatcherAssist.bindListener(
            item.evaluateContent,
            position,
            holder.binding.vidContentEt
        ) { charSequence, _, pos, _ ->
            try {
                // 保存评价内容
                getDataItem(pos).evaluateContent = charSequence.toString()
            } catch (e: Exception) {
            }
            try {
                // 计算已经输入的内容长度
                numberTv.text = "${120 - StringUtils.length(item.evaluateContent)}"
            } catch (e: Exception) {
            }
        }
    }
}