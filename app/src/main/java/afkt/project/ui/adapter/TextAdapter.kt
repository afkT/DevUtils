package afkt.project.ui.adapter

import afkt.project.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * detail: Text Adapter
 * @author Ttt
 */
class TextAdapter(list: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(
    R.layout.adapter_text,
    list
) {
    override fun convert(
        helper: BaseViewHolder,
        item: String
    ) {
        helper.setText(R.id.vid_at_title_tv, item)
    }
}