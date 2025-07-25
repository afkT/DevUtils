package afkt.project.features.ui_effect.recycler_view.adapter_concat

import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectConcatAdapterBinding
import afkt.project.features.ui_effect.recycler_view.adapter_concat.adapter.*
import androidx.recyclerview.widget.ConcatAdapter

/**
 * detail: RecyclerView - ConcatAdapter
 * @author Ttt
 */
class ConcatAdapterFragment : AppFragment<FragmentUiEffectConcatAdapterBinding, AppViewModel>(
    R.layout.fragment_ui_effect_concat_adapter
) {
    override fun initView() {
        super.initView()
        convertAdapter()
    }

    private fun convertAdapter() {
        // 头部适配器
        val headerAdapter = HeaderFooterConcatAdapter(
            arrayListOf(
                HeaderFooterItem("Header"),
                HeaderFooterItem("Header2")
            )
        )

        // 底部适配器
        val footerAdapter = HeaderFooterConcatAdapter(
            arrayListOf(HeaderFooterItem("Footer"))
        )

        val mainData = createMainData()

        // Banner 广告图适配器
        val bannerAdapter = BannerConcatAdapter(this, mainData.bannerLists)

        // 商品、商品评价适配器
        val commodityAdapter = CommodityConcatAdapter(mainData.commodityLists)

        // ShapeableImageView 效果适配器
        val shapeableAdapter = ShapeableImageConcatAdapter(mainData.shapeableImageLists)

        // 文章适配器
        val articleAdapter = ArticleConcatAdapter(mainData.articleLists)

        // 拼接适配器并设置
        val concatAdapter = ConcatAdapter(
            headerAdapter,
            bannerAdapter,
            commodityAdapter,
            shapeableAdapter,
            articleAdapter,
            footerAdapter
        )
        binding.vidRv.adapter = concatAdapter
    }
}