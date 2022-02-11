package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.bean.CommodityEvaluateBean
import afkt.project.model.bean.CommodityEvaluateBean.Companion.newCommodityEvaluateBean
import afkt.project.ui.adapter.ShopCartAnimAdapter
import afkt.project.util.ShopCartFloating
import android.os.Bundle
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.widget.decoration.linear.FirstLineItemDecoration

/**
 * detail: 购物车加入动画
 * @author Ttt
 */
@Route(path = RouterPath.ShopCartAddAnimActivity_PATH)
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

        val lists: MutableList<CommodityEvaluateBean> = ArrayList()
        for (i in 0..14) lists.add(newCommodityEvaluateBean())

        // 初始化布局管理器、适配器
        ShopCartAnimAdapter(lists).setClickListener {
            shopCartFloating.executeAnim(it)
        }.bindAdapter(binding.vidRv)

        QuickHelper.get(binding.vidRv)
            .removeAllItemDecoration()
            .addItemDecoration(
                FirstLineItemDecoration(
                    ResourceUtils.getDimension(R.dimen.dp_10)
                )
            )
    }
}