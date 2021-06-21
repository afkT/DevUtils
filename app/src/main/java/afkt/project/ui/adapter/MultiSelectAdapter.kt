package afkt.project.ui.adapter

import afkt.project.R
import afkt.project.databinding.AdapterMultiSelectBinding
import afkt.project.model.bean.CommodityEvaluateBean
import afkt.project.util.ProjectUtils
import android.view.View
import android.view.ViewGroup
import dev.adapter.DevDataAdapterExt2
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.base.multiselect.DevMultiSelectMap
import dev.engine.image.DevImageEngine
import dev.utils.app.ViewUtils
import dev.utils.app.helper.ViewHelper
import dev.utils.common.BigDecimalUtils
import java.math.BigDecimal

/**
 * detail: 多选 Adapter
 * @author Ttt
 */
class MultiSelectAdapter(data: List<CommodityEvaluateBean?>) :
    DevDataAdapterExt2<CommodityEvaluateBean?, DevBaseViewBindingVH<AdapterMultiSelectBinding>>() {

    init {
        setMultiSelectMap(DevMultiSelectMap()).setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterMultiSelectBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_multi_select)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterMultiSelectBinding>,
        position: Int
    ) {
        val item: CommodityEvaluateBean? = getDataItem(position)
        // 判断是否显示边距
        ViewUtils.setVisibility(position == 0, holder.binding.vidAmsLine)

        // 商品信息
        ViewHelper.get()
            .setText(holder.binding.vidAmsNameTv, item?.commodityName)
            .setText(
                holder.binding.vidAmsPriceTv, "￥" + BigDecimalUtils.round(
                    item?.commodityPrice, 2, BigDecimal.ROUND_HALF_UP
                )
            )
        // 商品图片
        DevImageEngine.getEngine().display(
            holder.binding.vidAmsPicIgview,
            item?.commodityPicture,
            ProjectUtils.roundConfig3
        )

        // ==========
        // = 多选处理 =
        // ==========

        val key = getMultiSelectKey(item, position)
        val selectIGView = holder.binding.vidAmsIgview
        // 是否显示编辑按钮、以及是否选中
        ViewHelper.get().setVisibility(isEditState, selectIGView)
            .setSelected(mMultiSelectMap.isSelectKey(key), selectIGView)
            .setOnClicks(View.OnClickListener {
                if (!isEditState) return@OnClickListener
                // 反选处理
                mMultiSelectMap.toggle(key, item)
                // 设置是否选中
                ViewUtils.setSelected(mMultiSelectMap.isSelectKey(key), selectIGView)
                // 触发回调
                selectListener?.onClickSelect(position, mMultiSelectMap.isSelectKey(key))
            }, holder.itemView.findViewById(R.id.vid_ams_linear))
    }

    // =======
    // = 多选 =
    // =======

    override fun getMultiSelectKey(
        item: CommodityEvaluateBean?,
        position: Int
    ): String {
        return position.toString()
    }

    // =============
    // = 操作监听事件 =
    // =============

    // 选择事件通知事件
    private var selectListener: OnSelectListener? = null

    /**
     * 设置选择事件通知事件
     * @param selectListener [OnSelectListener]
     * @return [MultiSelectAdapter]
     */
    fun setSelectListener(selectListener: OnSelectListener?): MultiSelectAdapter {
        this.selectListener = selectListener
        return this
    }

    /**
     * detail: 选择事件通知事件
     * @author Ttt
     */
    interface OnSelectListener {

        /**
         * 点击选中切换
         * @param position 对应的索引
         * @param now      新的状态
         */
        fun onClickSelect(
            position: Int,
            now: Boolean
        )
    }
}