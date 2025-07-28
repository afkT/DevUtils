package afkt.project.features.ui_effect.shop_cart

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectShopCartBinding
import afkt.project.features.ui_effect.recycler_view.adapter_concat.CommodityBean
import afkt.project.features.ui_effect.recycler_view.adapter_concat.createCommodity
import afkt.project.model.basic.AdapterModel
import android.view.View
import dev.simple.app.base.asFragment
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.widget.decoration.linear.FirstLinearColorItemDecoration
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * detail: 购物车加入动画
 * @author Ttt
 */
class ShopCartFragment : AppFragment<FragmentUiEffectShopCartBinding, ShopCartViewModel>(
    R.layout.fragment_ui_effect_shop_cart, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<ShopCartFragment> {
            QuickHelper.get(binding.vidRv).removeAllItemDecoration().addItemDecoration(
                FirstLinearColorItemDecoration(
                    true, ResourceUtils.getDimension(R.dimen.dp_10)
                )
            )
            val animation = ShopCartAnimation()
            viewModel.adapter.viewClick = { view ->
                animation.executeAnim(view, binding.vidRv)
            }
        }
    }
)

class ShopCartViewModel : AppViewModel() {

    val adapter = ShopCartAdapter().apply {
        val lists = mutableListOf<CommodityBean>()
        for (i in 0 until 15) lists.add(createCommodity())
        addAllAndClear(lists)
    }
}

class ShopCartAdapter() : AdapterModel<CommodityBean>() {

    private val itemClick = View.OnClickListener { view ->
        viewClick.invoke(view)
    }

    // Item Binding
    val itemBinding = ItemBinding.of<CommodityBean>(
        BR.itemValue, R.layout.adapter_item_shop_cart_anim
    ).bindExtra(BR.itemClick, itemClick)

    var viewClick: (View) -> Unit = {}
}