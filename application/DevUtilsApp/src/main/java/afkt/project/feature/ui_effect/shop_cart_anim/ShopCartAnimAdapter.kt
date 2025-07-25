package afkt.project.feature.ui_effect.shop_cart_anim

import afkt.project.R
import afkt.project.app.helper.IMAGE_ROUND_3
import afkt.project.databinding.AdapterItemShopCartAnimBinding
import afkt.project.model.data.bean.CommodityItem
import android.view.View
import android.view.ViewGroup
import dev.adapter.DevDataAdapterExt
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.expand.engine.image.display
import dev.mvvm.utils.image.toImageConfig
import dev.mvvm.utils.toPriceString
import dev.mvvm.utils.toRMBSubZeroAndDot
import dev.mvvm.utils.toSource
import dev.utils.app.helper.view.ViewHelper

/**
 * detail: 购物车动画 Adapter
 * @author Ttt
 */
class ShopCartAnimAdapter(data: List<CommodityItem?>) :
    DevDataAdapterExt<CommodityItem?, DevBaseViewBindingVH<AdapterItemShopCartAnimBinding>>() {

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
        val item = getDataItem(position)

        // 商品信息
        ViewHelper.get()
            .setText(item?.name, holder.binding.vidNameTv)
            .setText(
                item?.price?.toPriceString()?.toRMBSubZeroAndDot(),
                holder.binding.vidPriceTv
            )
        // 商品图片
        holder.binding.vidPicIv.display(
            source = item?.picture?.toSource(),
            config = IMAGE_ROUND_3.toImageConfig()
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