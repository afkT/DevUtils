package afkt.project.ui.adapter

import afkt.project.R
import afkt.project.databinding.AdapterRecyclerLoadingBinding
import afkt.project.utils.ViewAssistUtils
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import dev.adapter.DevDataAdapter
import dev.base.DevSource
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.base.widget.BaseImageView
import dev.engine.DevEngine
import dev.engine.image.listener.DrawableListener
import dev.widget.assist.ViewAssist

/**
 * detail: ViewAssist RecyclerView 适配器
 * @author Ttt
 */
class RecyclerLoadingAdapter(data: List<String>) : DevDataAdapter<String, DevBaseViewBindingVH<AdapterRecyclerLoadingBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterRecyclerLoadingBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_recycler_loading)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterRecyclerLoadingBinding>,
        position: Int
    ) {
        val url = getDataItem(position)
        val viewAssist = ViewAssist.wrap(holder.binding.vidFl)
        ViewAssistUtils.registerRecyclerLoading(viewAssist) {
            loadImage(holder.binding.vidIv, viewAssist, url)
        }
        loadImage(holder.binding.vidIv, viewAssist, url)
    }

    private fun loadImage(
        imageView: BaseImageView,
        viewAssist: ViewAssist,
        url: String
    ) {
        viewAssist.showIng()
        // 加载图片
        DevEngine.getImage()?.display<Drawable>(
            imageView, url,
            object : DrawableListener() {
                override fun onStart(source: DevSource) {}
                override fun onResponse(
                    source: DevSource,
                    value: Drawable
                ) {
                    viewAssist.showSuccess()
                }

                override fun onFailure(
                    source: DevSource,
                    throwable: Throwable
                ) {
                    viewAssist.showFailed()
                }
            }
        )
    }
}