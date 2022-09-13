package afkt.project.feature.ui_effect.shop_cart_anim

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.bean.CommodityItem
import afkt.project.model.bean.CommodityItem.Companion.newCommodityItem
import afkt.project.model.item.RouterPath
import android.os.Bundle
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.widget.decoration.linear.FirstLinearColorItemDecoration

/**
 * detail: 购物车加入动画
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.ShopCartAddAnimActivity_PATH)
class ShopCartAddAnimActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    // 购物车悬浮对外公开类
    lateinit var shopCartFloating: ShopCartFloating

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val parent = binding.vidRv.parent as? ViewGroup
        // 根布局处理
        QuickHelper.get(parent).setPadding(0)
            .setBackgroundColor(ResourceUtils.getColor(R.color.color_33))

        // 创建购物车悬浮
        shopCartFloating = ShopCartFloating(this)
    }

    override fun initValue() {
        super.initValue()

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