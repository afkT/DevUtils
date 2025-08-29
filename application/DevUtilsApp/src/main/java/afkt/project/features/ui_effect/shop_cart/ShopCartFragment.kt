package afkt.project.features.ui_effect.shop_cart

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectShopCartBinding
import afkt.project.features.ui_effect.recycler_view.adapter_concat.CommodityBean
import afkt.project.features.ui_effect.recycler_view.adapter_concat.createCommodity
import android.view.View
import androidx.databinding.ObservableField
import dev.assist.NumberControlAssist
import dev.base.number.INumberListener
import dev.base.simple.extensions.asFragment
import dev.simple.core.app.adapter.AdapterModel
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
            viewModel.adapterModel.viewClick = { view ->
                viewModel.executeAnim(view, binding.vidNumberTv)
            }
        }
    }
)

class ShopCartViewModel : AppViewModel() {

    val adapterModel = ShopCartAdapter().apply {
        val lists = mutableListOf<CommodityBean>()
        for (i in 0 until 15) lists.add(createCommodity())
        addAllAndClear(lists)
    }

    // =

    // 购物车数量
    val numberText = ObservableField<String>("0")

    // 购物车动画
    private val animation = ShopCartAnimation()

    // 数量控制辅助类
    private val numberControl = NumberControlAssist(
        0, Int.MAX_VALUE
    ).setNumberListener(object : INumberListener {
        override fun onPrepareChanged(
            isAdd: Boolean,
            curNumber: Int,
            afterNumber: Int
        ): Boolean {
            return true
        }

        override fun onNumberChanged(
            isAdd: Boolean,
            curNumber: Int
        ) {
        }
    }).setCurrentNumber(0)

    /**
     * 执行添加购物车动画
     * @param view View
     */
    fun executeAnim(
        view: View,
        endView: View
    ) {
        numberControl.addNumber().apply {
            val number = if (currentNumber > 99) "99+" else currentNumber.toString()
            // 设置购买数量
            numberText.set(number)
        }
        try {
            // 开始动画
            animation.startAnim(view, endView)
        } catch (_: Exception) {
            // 防止 IncludeShopCartRedDotViewBinding.inflate() 添加到 parent 获取失败
            // 最好直接传入最外层 FrameLayout View，然后传入 inflate 中参数 parent
        }
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