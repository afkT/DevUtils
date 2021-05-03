package afkt.project.ui.adapter

import afkt.project.R
import afkt.project.databinding.AdapterArticleBinding
import afkt.project.model.bean.ArticleBean.DataBean.ListBean
import afkt.project.util.ProjectUtils
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.engine.image.DevImageEngine
import dev.utils.app.AppUtils
import dev.utils.app.ListenerUtils
import dev.utils.app.TextViewUtils
import dev.utils.common.NumberUtils
import dev.utils.common.StringUtils

/**
 * detail: 文章 Adapter
 * @author Ttt
 */
class ArticleAdapter : DevDataAdapter<ListBean, DevBaseViewBindingVH<AdapterArticleBinding>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterArticleBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_article)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterArticleBinding>,
        position: Int
    ) {
        val item = getDataItem(position)
        // 标题
        TextViewUtils.setHtmlText(holder.binding.vidAaTitleTv, item.title)
        // 时间
        holder.binding.vidAaTimeTv.text = StringUtils.checkValue(item.niceShareDate, item.niceDate)
        // 随机图片
        DevImageEngine.getEngine().display(
            holder.binding.vidAaPicIgview,
            "https://picsum.photos/2${NumberUtils.addZero(position)}",
            ProjectUtils.roundConfig3
        )
        // 绑定点击事件
        ListenerUtils.setOnClicks({
            val link = item.link
            if (!TextUtils.isEmpty(link)) {
                val uri = Uri.parse(link)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                AppUtils.startActivity(intent)
            }
        }, holder.binding.vidAaCardview)
    }
}