package afkt.project.ui.activity

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.base.config.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.bean.CommodityEvaluateBean
import afkt.project.model.bean.CommodityEvaluateBean.Companion.newCommodityEvaluateBean
import afkt.project.ui.adapter.MultiSelectAdapter
import afkt.project.ui.adapter.MultiSelectAdapter.OnSelectListener
import android.os.Bundle
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import dev.engine.DevEngine
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.quick.QuickHelper
import java.util.*

/**
 * detail: 购物车加入动画
 * @author Ttt
 */
@Route(path = RouterPath.ShopCartAddAnimActivity_PATH)
class ShopCartAddAnimActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    // 适配器
    lateinit var adapter: MultiSelectAdapter

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val parent = binding.vidBvrRecy.parent as? ViewGroup
        // 根布局处理
        QuickHelper.get(parent).setPadding(0)
            .setBackgroundColor(ResourceUtils.getColor(R.color.color_33))
    }

    override fun initValue() {
        super.initValue()

        val lists: MutableList<CommodityEvaluateBean> = ArrayList()
        for (i in 0..14) lists.add(newCommodityEvaluateBean())

        // 初始化布局管理器、适配器
        adapter = MultiSelectAdapter(lists)
            .setSelectListener(object : OnSelectListener {
                override fun onClickSelect(
                    position: Int,
                    now: Boolean
                ) {
                    val item = adapter.getDataItem(position)
                    DevEngine.getLog()
                        .eTag(TAG, "新状态: %s, 商品名: %s", now, item?.commodityName)
                }
            })
        binding.vidBvrRecy.adapter = adapter
    }
}