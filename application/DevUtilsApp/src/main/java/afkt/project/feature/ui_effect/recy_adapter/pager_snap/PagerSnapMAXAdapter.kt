package afkt.project.feature.ui_effect.recy_adapter.pager_snap

import afkt.project.R
import afkt.project.databinding.AdapterPagerSnapBinding
import afkt.project.model.bean.ItemBean
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.adapter.DevDataAdapter
import dev.base.adapter.DevBaseViewDataBindingVH
import dev.expand.engine.image.display
import dev.kotlin.utils.image.IMAGE_ROUND_10
import dev.kotlin.utils.image.toImageConfig
import dev.kotlin.utils.toSource

/**
 * detail: RecyclerView Gallery 效果 Adapter
 * @author Ttt
 */
class PagerSnapMAXAdapter(data: List<ItemBean>) : DevDataAdapter<ItemBean, RecyclerView.ViewHolder>() {

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
//        val holder: DevBaseViewDataBindingVH<AdapterPagerSnapBinding> =
//            newDataBindingViewHolder(parent, R.layout.adapter_pager_snap)
//        return holder
        val holder: DevBaseViewDataBindingVH<AdapterPagerSnapBinding> =
            DevBaseViewDataBindingVH.create(
                parent, R.layout.adapter_pager_snap
            )
        return holder
    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val size = dataSize
        if (size != 0) {
            val holder = viewHolder as DevBaseViewDataBindingVH<AdapterPagerSnapBinding>
            val index = position % size

            holder.binding.page = "$position - $index"
            holder.binding.item = getDataItem(index)
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun bindImageUrl(
            view: ImageView?,
            imageUrl: String?
        ) {
            view?.display(
                source = imageUrl?.toSource(),
                config = IMAGE_ROUND_10.toImageConfig()
            )
        }
    }
}