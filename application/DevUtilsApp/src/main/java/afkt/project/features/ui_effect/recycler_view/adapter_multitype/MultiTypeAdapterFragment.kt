package afkt.project.features.ui_effect.recycler_view.adapter_multitype

import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectConcatAdapterBinding
import afkt.project.features.ui_effect.recycler_view.adapter_concat.*
import afkt.project.features.ui_effect.recycler_view.adapter_multitype.adapter.*
import com.drakeet.multitype.MultiTypeAdapter

/**
 * detail: RecyclerView MultiType Adapter
 * @author Ttt
 */
class MultiTypeAdapterFragment : AppFragment<FragmentUiEffectConcatAdapterBinding, AppViewModel>(
    R.layout.fragment_ui_effect_concat_adapter
) {
    override fun initView() {
        super.initView()
        convertAdapter()
    }

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