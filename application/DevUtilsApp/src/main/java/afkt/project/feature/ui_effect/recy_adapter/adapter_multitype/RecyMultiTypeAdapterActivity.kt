package afkt.project.feature.ui_effect.recy_adapter.adapter_multitype

import afkt.project.R
import afkt.project.base.project.BaseProjectActivity
import afkt.project.base.project.BaseProjectViewModel
import afkt.project.data_model.button.RouterPath
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.feature.ui_effect.recy_adapter.ArticleBean1Item
import afkt.project.feature.ui_effect.recy_adapter.BannerBeanItem
import afkt.project.feature.ui_effect.recy_adapter.ClassifyBeanItem1
import afkt.project.feature.ui_effect.recy_adapter.ClassifyBeanItem2
import afkt.project.feature.ui_effect.recy_adapter.ClassifyBeanItem3
import afkt.project.feature.ui_effect.recy_adapter.CommodityBeanItem
import afkt.project.feature.ui_effect.recy_adapter.CommodityEvaluateBeanItem
import afkt.project.feature.ui_effect.recy_adapter.HeaderFooterItem
import afkt.project.feature.ui_effect.recy_adapter.ShapeableImageBeanItem
import afkt.project.feature.ui_effect.recy_adapter.adapter_multitype.adapter.ArticleItemViewBinder
import afkt.project.feature.ui_effect.recy_adapter.adapter_multitype.adapter.BannerItemViewBinder
import afkt.project.feature.ui_effect.recy_adapter.adapter_multitype.adapter.Classify1ItemViewBinder
import afkt.project.feature.ui_effect.recy_adapter.adapter_multitype.adapter.Classify2ItemViewBinder
import afkt.project.feature.ui_effect.recy_adapter.adapter_multitype.adapter.Classify3ItemViewBinder
import afkt.project.feature.ui_effect.recy_adapter.adapter_multitype.adapter.CommodityEvaluateItemViewBinder
import afkt.project.feature.ui_effect.recy_adapter.adapter_multitype.adapter.CommodityItemViewBinder
import afkt.project.feature.ui_effect.recy_adapter.adapter_multitype.adapter.HeaderFooterItemViewBinder
import afkt.project.feature.ui_effect.recy_adapter.adapter_multitype.adapter.ShapeableImageItemViewBinder
import afkt.project.feature.ui_effect.recy_adapter.convertMainDataItem
import afkt.project.feature.ui_effect.recy_adapter.createMainData
import android.view.ViewGroup
import com.drakeet.multitype.MultiTypeAdapter
import com.therouter.router.Route
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: RecyclerView MultiType Adapter
 * @author Ttt
 */
@Route(path = RouterPath.UI_EFFECT.RecyMultiTypeAdapterActivity_PATH)
class RecyMultiTypeAdapterActivity : BaseProjectActivity<BaseViewRecyclerviewBinding, BaseProjectViewModel>(
    R.layout.base_view_recyclerview, simple_Agile = {
        if (it is RecyMultiTypeAdapterActivity) {
            it.apply {
                val parent = binding.vidRv.parent as? ViewGroup
                // 根布局处理
                QuickHelper.get(parent).setPadding(0)

                convertAdapter()
            }
        }
    }
) {

    private fun convertAdapter() {
        val mainData = createMainData()
        // 转换 Item 模型
        val lists = convertMainDataItem(mainData)
        // 添加头部
        lists.add(0, HeaderFooterItem("Header"))
        lists.add(1, HeaderFooterItem("Header2"))
        // 添加底部
        lists.add(HeaderFooterItem("Footer"))

        val adapter = MultiTypeAdapter(items = lists)
        // 头部、底部适配器
        adapter.register(HeaderFooterItem::class.java, HeaderFooterItemViewBinder())
        // Banner 广告图适配器
        adapter.register(BannerBeanItem::class.java, BannerItemViewBinder(this))
        // 商品适配器
        adapter.register(CommodityBeanItem::class.java, CommodityItemViewBinder())
        // 商品评价适配器
        adapter.register(CommodityEvaluateBeanItem::class.java, CommodityEvaluateItemViewBinder())
        // ShapeableImageView 效果适配器
        adapter.register(ShapeableImageBeanItem::class.java, ShapeableImageItemViewBinder())
        // 文章适配器
        adapter.register(ArticleBean1Item::class.java, ArticleItemViewBinder())
        // 一级分类适配器
        adapter.register(ClassifyBeanItem1::class.java, Classify1ItemViewBinder())
        // 二级分类适配器
        adapter.register(ClassifyBeanItem2::class.java, Classify2ItemViewBinder())
        // 三级分类适配器
        adapter.register(ClassifyBeanItem3::class.java, Classify3ItemViewBinder())

        // 绑定适配器
        binding.vidRv.adapter = adapter
    }
}