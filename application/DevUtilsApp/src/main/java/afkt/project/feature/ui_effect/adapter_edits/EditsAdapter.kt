package afkt.project.feature.ui_effect.adapter_edits

import afkt.project.R
import afkt.project.databinding.AdapterConcatCommodityEvaluateBinding
import afkt.project.features.ui_effect.recycler_view.adapter_concat.CommodityBean
import afkt.project.model.engine.IMAGE_ROUND_3
import android.view.ViewGroup
import dev.adapter.DevDataAdapterExt
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.expand.engine.image.display
import dev.mvvm.utils.image.toImageConfig
import dev.mvvm.utils.toSource
import dev.utils.common.StringUtils

/**
 * detail: Item EditText 输入监听 Adapter
 * @author Ttt
 */
class EditsAdapter(data: List<CommodityBean>) :
    DevDataAdapterExt<CommodityBean, DevBaseViewBindingVH<AdapterConcatCommodityEvaluateBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterConcatCommodityEvaluateBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_concat_commodity_evaluate)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterConcatCommodityEvaluateBinding>,
        position: Int
    ) {
        val item = getDataItem(position)

        // ==========
        // = 商品信息 =
        // ==========

        // 商品名
        holder.binding.vidNameTv.text = item.name

        // 商品价格
        holder.binding.vidPriceTv.text = item.priceText

        // 商品图片
        holder.binding.vidPicIv.display(
            source = item.picture.toSource(),
            config = IMAGE_ROUND_3.toImageConfig()
        )
        // 评星等级
        val ratingBar = holder.binding.vidRatingbar
        ratingBar.setOnRatingChangeListener { _, rating, _ ->
            item.evaluateLevel = rating
        }
        // 设置评星等级
        ratingBar.rating = item.evaluateLevel

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
            } catch (_: Exception) {
            }
            try {
                // 计算已经输入的内容长度
                numberTv.text = "${120 - StringUtils.length(item.evaluateContent)}"
            } catch (_: Exception) {
            }
        }
    }
}