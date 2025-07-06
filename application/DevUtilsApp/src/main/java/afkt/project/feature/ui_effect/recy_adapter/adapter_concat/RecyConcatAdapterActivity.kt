package afkt.project.feature.ui_effect.recy_adapter.adapter_concat

import afkt.project.R
import afkt.project.app.AppViewModel
import afkt.project.app.project.BaseProjectActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.feature.ui_effect.recy_adapter.HeaderFooterItem
import afkt.project.feature.ui_effect.recy_adapter.adapter_concat.adapter.*
import afkt.project.feature.ui_effect.recy_adapter.createMainData
import afkt.project.model.data.button.RouterPath
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import com.therouter.router.Route
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: RecyclerView - ConcatAdapter
 * @author Ttt
 * @see https://mp.weixin.qq.com/s/QTaz45aLucX9mivVMbCZPQ
 * @see https://zhuanlan.zhihu.com/p/275635988
 */
@Route(path = RouterPath.UI_EFFECT.RecyConcatAdapterActivity_PATH)
class RecyConcatAdapterActivity :
    BaseProjectActivity<BaseViewRecyclerviewBinding, AppViewModel>(
        R.layout.base_view_recyclerview, simple_Agile = {
            if (it is RecyConcatAdapterActivity) {
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