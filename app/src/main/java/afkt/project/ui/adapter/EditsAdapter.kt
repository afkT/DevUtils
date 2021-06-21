package afkt.project.ui.adapter

import afkt.project.R
import afkt.project.databinding.AdapterItemEditsBinding
import afkt.project.model.item.EvaluateItem
import afkt.project.util.ProjectUtils
import android.view.ViewGroup
import dev.adapter.DevDataAdapterExt
import dev.assist.EditTextWatcherAssist
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.engine.image.DevImageEngine
import dev.utils.app.ViewUtils
import dev.utils.common.BigDecimalUtils
import dev.utils.common.StringUtils
import java.math.BigDecimal

/**
 * detail: Item EditText 输入监听 Adapter
 * @author Ttt
 */
class EditsAdapter(data: List<EvaluateItem>) : DevDataAdapterExt<EvaluateItem, DevBaseViewBindingVH<AdapterItemEditsBinding>>() {

    init {
        setTextWatcherAssist(EditTextWatcherAssist())
            .setDataList(data, false)
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
        // 判断是否显示边距
        ViewUtils.setVisibility(position == 0, holder.binding.vidAieLine)

        // ==========
        // = 商品信息 =
        // ==========

        val commodity = item.commodityEvaluateBean

        // 商品名
        holder.binding.vidAieNameTv.text = commodity.commodityName
        // 商品价格
        holder.binding.vidAiePriceTv.text =
            "￥${BigDecimalUtils.round(commodity.commodityPrice, 2, BigDecimal.ROUND_HALF_UP)}"
        // 商品图片
        DevImageEngine.getEngine().display(
            holder.binding.vidAiePicIgview,
            commodity.commodityPicture,
            ProjectUtils.roundConfig3
        )
        // 评星等级
        val ratingBar = holder.binding.vidAieRatingbar
        ratingBar.setOnRatingChangeListener { ratingCount ->
            item.evaluateLevel = ratingCount
        }
        // 设置评星等级
        ratingBar.setStar(item.evaluateLevel)

        // ==========
        // = 输入监听 =
        // ==========

        // 评价内容字数
        val vid_aie_number_tv = holder.binding.vidAieNumberTv
        // 计算已经输入的内容长度
        vid_aie_number_tv.text = "${120 - StringUtils.length(item.evaluateContent)}"
        // 绑定监听事件
        mTextWatcherAssist.bindListener(
            item.evaluateContent,
            position,
            holder.binding.vidAieContentEdit
        ) { charSequence, _, pos, _ ->
            try {
                // 保存评价内容
                getDataItem(pos).evaluateContent = charSequence.toString()
            } catch (e: Exception) {
            }
            try {
                // 计算已经输入的内容长度
                vid_aie_number_tv.text = "${120 - StringUtils.length(item.evaluateContent)}"
            } catch (e: Exception) {
            }
        }
    }
}