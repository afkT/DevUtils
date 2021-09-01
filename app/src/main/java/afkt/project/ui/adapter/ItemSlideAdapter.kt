package afkt.project.ui.adapter

import afkt.project.R
import afkt.project.databinding.AdapterMultiSelectBinding
import afkt.project.model.bean.CommodityEvaluateBean
import afkt.project.util.ProjectUtils
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.engine.image.DevImageEngine
import dev.utils.app.helper.ViewHelper
import dev.utils.common.BigDecimalUtils
import java.math.BigDecimal

/**
 * detail: Item Slide Adapter
 * @author Ttt
 */
class ItemSlideAdapter(data: List<CommodityEvaluateBean>) : DevDataAdapter<CommodityEvaluateBean, DevBaseViewBindingVH<AdapterMultiSelectBinding>>() {

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
            .setVisibility(position == 0, holder.binding.vidAmsLine)
            .setText(holder.binding.vidAmsNameTv, item.commodityName)
            .setText(
                holder.binding.vidAmsPriceTv,
                "￥${BigDecimalUtils.round(item.commodityPrice, 2, BigDecimal.ROUND_HALF_UP)}"
            )
        // 商品图片
        DevImageEngine.getEngine()?.display(
            holder.binding.vidAmsIgview,
            item.commodityPicture,
            ProjectUtils.roundConfig3
        )
    }
}