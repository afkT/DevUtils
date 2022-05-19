package afkt.project.feature.ui_effect.recy_adapter.linear_snap

import afkt.project.R
import afkt.project.databinding.AdapterLinearSnapBinding
import afkt.project.model.bean.ItemBean
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewBindingVH
import dev.kotlin.engine.image.display
import dev.kotlin.utils.image.IMAGE_ROUND_10
import dev.kotlin.utils.image.toImageConfig
import dev.kotlin.utils.toSource
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
                .setText(itemBean.title, holder.binding.vidTitleTv)
                .setText(itemBean.subtitle, holder.binding.vidSubtitleTv)
                .setText(itemBean.timeFormat, holder.binding.vidTimeTv)
                .setText("$position - $index", holder.binding.vidIndexTv)

            holder.binding.vidIv.display(
                source = itemBean.imageUrl?.toSource(),
                config = IMAGE_ROUND_10.toImageConfig()
            )
        }
    }
}