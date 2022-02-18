package afkt.project.feature.ui_effect.recy_adapter_multitype.adapter

import afkt.project.R
import afkt.project.databinding.AdapterConcatArticleBinding
import afkt.project.model.bean.ArticleBean1Item
import android.view.LayoutInflater
import android.view.ViewGroup
import com.drakeet.multitype.ItemViewBinder
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.utils.app.helper.view.ViewHelper

/**
 * detail: Article Adapter
 * @author Ttt
 */
class ArticleItemViewBinder : ItemViewBinder<ArticleBean1Item, DevBaseViewBindingVH<AdapterConcatArticleBinding>>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): DevBaseViewBindingVH<AdapterConcatArticleBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_concat_article)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterConcatArticleBinding>,
        item: ArticleBean1Item
    ) {
        val itemObj = item.obj

        ViewHelper.get()
            .setText(itemObj.title, holder.binding.vidTitleTv)
            .setText(itemObj.content, holder.binding.vidContentTv)
            .setImageBitmap(itemObj.pictures, holder.binding.vidIv)
            .setBackgroundColor(itemObj.background, holder.itemView)
    }
}