package afkt.project.ui.adapter.concat

import afkt.project.R
import afkt.project.databinding.AdapterConcatArticleBinding
import afkt.project.model.bean.ArticleBean1
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.utils.app.helper.view.ViewHelper

/**
 * detail: Article Adapter
 * @author Ttt
 */
class ArticleConcatAdapter(data: List<ArticleBean1>) : DevDataAdapter<ArticleBean1, DevBaseViewBindingVH<AdapterConcatArticleBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterConcatArticleBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_concat_article)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterConcatArticleBinding>,
        position: Int
    ) {
        val item = getDataItem(position)
        ViewHelper.get()
            .setText(item.title, holder.binding.vidTitleTv)
            .setText(item.content, holder.binding.vidContentTv)
            .setImageBitmap(item.pictures, holder.binding.vidIv)
            .setBackgroundColor(item.background, holder.itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.adapter_concat_article
    }
}