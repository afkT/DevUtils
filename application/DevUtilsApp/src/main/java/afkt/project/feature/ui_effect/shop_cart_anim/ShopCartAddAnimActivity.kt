package afkt.project.feature.ui_effect.shop_cart_anim

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.bean.CommodityItem
import afkt.project.data_model.bean.CommodityItem.Companion.newCommodityItem
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import android.view.ViewGroup
import com.therouter.router.Route
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.widget.decoration.linear.FirstLinearColorItemDecoration

/**
 * detail: 购物车加入动画
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.ShopCartAddAnimActivity_PATH)
class ShopCartAddAnimActivity :
    BaseProjectActivity<BaseViewRecyclerviewBinding, BaseProjectViewModel>(
        R.layout.base_view_recyclerview, simple_Agile = {
            if (it is ShopCartAddAnimActivity) {
                it.apply {
                    val parent = binding.vidRv.parent as? ViewGroup
                    // 根布局处理
                    QuickHelper.get(parent).setPadding(0)
                        .setBackgroundColor(ResourceUtils.getColor(R.color.color_33))

                    // 创建购物车悬浮
                    val shopCartFloating = ShopCartFloating(this)

                    val lists = mutableListOf<CommodityItem>()
                    for (i in 0..14) lists.add(newCommodityItem())

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