package afkt.project.ui.adapter

import afkt.project.R
import afkt.project.databinding.AdapterItemShopCartAnimBinding
import afkt.project.model.bean.CommodityEvaluateBean
import afkt.project.utils.ProjectUtils
import android.view.View
import android.view.ViewGroup
import dev.adapter.DevDataAdapterExt
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.engine.DevEngine
import dev.utils.app.helper.view.ViewHelper
import dev.utils.common.BigDecimalUtils
import java.math.BigDecimal

/**
 * detail: 购物车动画 Adapter
 * @author Ttt
 */
class ShopCartAnimAdapter(data: List<CommodityEvaluateBean?>) :
    DevDataAdapterExt<CommodityEvaluateBean?, DevBaseViewBindingVH<AdapterItemShopCartAnimBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterItemShopCartAnimBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_item_shop_cart_anim)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterItemShopCartAnimBinding>,
        position: Int
    ) {
        val item: CommodityEvaluateBean? = getDataItem(position)

        // 商品信息
        ViewHelper.get()
            .setText(item?.commodityName, holder.binding.vidNameTv)
            .setText(
                "￥" + BigDecimalUtils.round(
                    item?.commodityPrice, 2, BigDecimal.ROUND_HALF_UP
                ), holder.binding.vidPriceTv
            )
        // 商品图片
        DevEngine.getImage()?.display(
            holder.binding.vidPicIv,
            item?.commodityPicture,
            ProjectUtils.roundConfig3
        )
        // 点击加入购物车
        ViewHelper.get()
            .setOnClick({
                mClick?.onClick(it)
            }, holder.binding.vidAddIv)
    }

    // =

    private var mClick: View.OnClickListener? = null

    fun setClickListener(click: View.OnClickListener): ShopCartAnimAdapter {
        mClick = click
        return this
    }
}