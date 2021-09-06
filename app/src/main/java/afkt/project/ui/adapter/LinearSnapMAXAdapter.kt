package afkt.project.ui.adapter

import afkt.project.R
import afkt.project.databinding.AdapterLinearSnapBinding
import afkt.project.model.bean.ItemBean
import afkt.project.util.ProjectUtils
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.engine.DevEngine
import dev.utils.app.helper.view.ViewHelper

/**
 * detail: RecyclerView Gallery 效果 Adapter
 * @author Ttt
 */
class LinearSnapMAXAdapter(data: List<ItemBean>) : DevDataAdapter<ItemBean, RecyclerView.ViewHolder>() {

    init {
        setDataList(data, false)
    }

    /**
     * 获取真实索引
     * @param position 当前索引
     * @return Data 真实索引
     */
    fun getRealIndex(position: Int): Int {
        val size = dataSize
        return if (size != 0) position % size else 0
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
//        val holder: DevBaseViewBindingVH<AdapterLinearSnapBinding> =
//            newBindingViewHolder(parent, R.layout.adapter_linear_snap)
//        return holder
        return DevBaseViewBindingVH.create(
            AdapterLinearSnapBinding::class.java,
            parent, R.layout.adapter_linear_snap
        )
    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val size = dataSize
        if (size != 0) {
            val holder = viewHolder as DevBaseViewBindingVH<AdapterLinearSnapBinding>
            val index = position % size
            val itemBean = getDataItem(index)
            ViewHelper.get()
                .setText(holder.binding.vidAlsTitleTv, itemBean.title)
                .setText(holder.binding.vidAlsSubtitleTv, itemBean.subtitle)
                .setText(holder.binding.vidAlsTimeTv, itemBean.timeFormat)
                .setText(holder.binding.vidAlsIndexTv, "$position - $index")
            DevEngine.getImage()?.display(
                holder.binding.vidAlsIgview,
                itemBean.imageUrl,
                ProjectUtils.roundConfig10
            )
        }
    }
}