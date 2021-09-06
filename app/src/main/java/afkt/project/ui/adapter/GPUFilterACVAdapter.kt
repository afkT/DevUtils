package afkt.project.ui.adapter

import afkt.project.R
import afkt.project.model.bean.ACVFileBean
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Gallery
import dev.base.widget.BaseTextView
import dev.utils.app.ResourceUtils
import dev.utils.app.SizeUtils
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: GPU ACV 文件滤镜效果适配器
 * @author Ttt
 */
class GPUFilterACVAdapter(
    // Context
    private val context: Context,
    // ACV 文件集合
    private val listACVFiles: List<ACVFileBean>
) : BaseAdapter() {

    // 当前选中索引
    private var selectPosition = -1

    override fun getCount(): Int {
        return listACVFiles.size
    }

    override fun getItem(position: Int): ACVFileBean {
        return listACVFiles[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        return createTextView(position)
    }

    /**
     * 设置选中索引
     * @param selectPosition 选中索引
     */
    fun setSelectPosition(selectPosition: Int) {
        this.selectPosition = selectPosition
        notifyDataSetChanged()
    }

    /**
     * 创建 TextView
     * @param position 对应索引
     * @return [BaseTextView]
     */
    private fun createTextView(position: Int): BaseTextView {
        val acvFileBean = getItem(position)
        val isSelect = (selectPosition == position)
        val width = SizeUtils.dipConvertPx(100f)
        val layoutParams = Gallery.LayoutParams(
            width, Gallery.LayoutParams.MATCH_PARENT
        )
        // 初始化 View
        return QuickHelper.get(BaseTextView(context))
            .setText(acvFileBean.acvName)
            .setBold(isSelect)
            .setTextGravity(Gravity.CENTER)
            .setTextColor(ResourceUtils.getColor(if (isSelect) R.color.red else R.color.black))
            .setTextSizeBySp(if (isSelect) 18.0f else 13.0f)
            .setLayoutParams(layoutParams)
            .getView()
    }
}