package afkt.project.feature.ui_effect.shop_cart_anim

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.features.ui_effect.recycler_view.adapter_concat.CommodityBean
import afkt.project.features.ui_effect.recycler_view.adapter_concat.createCommodity
import android.view.ViewGroup
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.widget.decoration.linear.FirstLinearColorItemDecoration

/**
 * detail: 购物车加入动画
 * @author Ttt
 */
class ShopCartAddAnimActivity :
    BaseProjectActivity<BaseViewRecyclerviewBinding, AppViewModel>(
        R.layout.base_view_recyclerview, simple_Agile = {
            if (it is ShopCartAddAnimActivity) {
                it.apply {
                    val parent = binding.vidRv.parent as? ViewGroup
                    // 根布局处理
                    QuickHelper.get(parent).setPadding(0)
                        .setBackgroundColor(ResourceUtils.getColor(R.color.color_33))

                    // 创建购物车悬浮
                    val shopCartFloating = ShopCartFloating(this)

                    val lists = mutableListOf<CommodityBean>()
                    for (i in 0..14) lists.add(createCommodity())

                    // 初始化布局管理器、适配器
                    ShopCartAnimAdapter(lists).setClickListener {
                        shopCartFloating.executeAnim(it)
                    }.bindAdapter(binding.vidRv)

                    QuickHelper.get(binding.vidRv)
                        .removeAllItemDecoration()
                        .addItemDecoration(
                            FirstLinearColorItemDecoration(
                                true, ResourceUtils.getDimension(R.dimen.dp_10)
                            )
                        )
                }
            }
        }
    )