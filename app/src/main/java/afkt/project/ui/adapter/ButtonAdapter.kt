package afkt.project.ui.adapter

import afkt.project.R
import afkt.project.databinding.BaseViewButtonBinding
import afkt.project.model.item.ButtonValue
import android.view.ViewGroup
import dev.adapter.DevDataAdapterExt
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder

/**
 * detail: Button 适配器
 * @author Ttt
 */
class ButtonAdapter(data: List<ButtonValue>) : DevDataAdapterExt<ButtonValue, DevBaseViewBindingVH<BaseViewButtonBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<BaseViewButtonBinding> {
        return newBindingViewHolder(parent, R.layout.base_view_button)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<BaseViewButtonBinding>,
        position: Int
    ) {
        val item = getDataItem(position)
        holder.binding.vidBvbBtn.apply {
            text = item.text
            setOnClickListener {
                mItemCallback?.onItemClick(item, position)
            }
            setOnLongClickListener {
                mItemCallback?.onItemLongClick(item, position)
                true
            }
        }
    }
}