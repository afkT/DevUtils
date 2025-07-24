package afkt.project.features.ui_effect.recycler_view.adapter_multitype

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.features.ui_effect.recycler_view.adapter_concat.ArticleBean1Item
import afkt.project.features.ui_effect.recycler_view.adapter_concat.BannerBeanItem
import afkt.project.features.ui_effect.recycler_view.adapter_concat.ClassifyBeanItem1
import afkt.project.features.ui_effect.recycler_view.adapter_concat.ClassifyBeanItem2
import afkt.project.features.ui_effect.recycler_view.adapter_concat.ClassifyBeanItem3
import afkt.project.features.ui_effect.recycler_view.adapter_concat.CommodityBeanItem
import afkt.project.features.ui_effect.recycler_view.adapter_concat.CommodityEvaluateBeanItem
import afkt.project.features.ui_effect.recycler_view.adapter_concat.HeaderFooterItem
import afkt.project.features.ui_effect.recycler_view.adapter_concat.ShapeableImageBeanItem
import afkt.project.features.ui_effect.recycler_view.adapter_concat.convertMainDataItem
import afkt.project.features.ui_effect.recycler_view.adapter_concat.createMainData
import afkt.project.features.ui_effect.recycler_view.adapter_multitype.adapter.*
import android.view.ViewGroup
import com.drakeet.multitype.MultiTypeAdapter
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: RecyclerView MultiType Adapter
 * @author Ttt
 */
class RecyMultiTypeAdapterActivity :
    BaseProjectActivity<BaseViewRecyclerviewBinding, AppViewModel>(
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