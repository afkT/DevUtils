package afkt.project.feature.ui_effect.multi_select

import afkt.project.R
import afkt.project.app.helper.IMAGE_ROUND_3
import afkt.project.databinding.AdapterConcatCommodityMultiSelectBinding
import afkt.project.model.data.bean.CommodityItem
import android.view.View
import android.view.ViewGroup
import dev.adapter.DevDataAdapterExt2
import dev.base.adapter.DevBaseViewBindingVH
import dev.base.adapter.newBindingViewHolder
import dev.expand.engine.image.display
import dev.mvvm.utils.image.toImageConfig
import dev.mvvm.utils.toPriceString
import dev.mvvm.utils.toRMBSubZeroAndDot
import dev.mvvm.utils.toSource
import dev.utils.app.ViewUtils
import dev.utils.app.helper.view.ViewHelper

/**
 * detail: 多选 Adapter
 * @author Ttt
 */
class MultiSelectAdapter(data: List<CommodityItem?>) :
    DevDataAdapterExt2<CommodityItem?, DevBaseViewBindingVH<AdapterConcatCommodityMultiSelectBinding>>() {

    init {
        setDataList(data, false)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevBaseViewBindingVH<AdapterConcatCommodityMultiSelectBinding> {
        return newBindingViewHolder(parent, R.layout.adapter_concat_commodity_multi_select)
    }

    override fun onBindViewHolder(
        holder: DevBaseViewBindingVH<AdapterConcatCommodityMultiSelectBinding>,
        position: Int
    ) {
        val item = getDataItem(position)

        // 商品信息
        ViewHelper.get()
            .setText(item?.name, holder.binding.vidNameTv)
            .setText(
                item?.price?.toPriceString()?.toRMBSubZeroAndDot(),
                holder.binding.vidPriceTv
            )
        // 商品图片
        holder.binding.vidPicIv.display(
            source = item?.picture?.toSource(),
            config = IMAGE_ROUND_3.toImageConfig()
        )

        // ==========
        // = 多选处理 =
        // ==========

        val key = getMultiSelectKey(item, position)
        val selectIGView = holder.binding.vidIv
        // 是否显示编辑按钮、以及是否选中
        ViewHelper.get().setVisibilitys(isEditState, selectIGView)
            .setSelected(mMultiSelectMap.isSelectKey(key), selectIGView)
            .setOnClick(View.OnClickListener {
                if (!isEditState) return@OnClickListener
                // 反选处理
                mMultiSelectMap.toggle(key, item)
                // 设置是否选中
                ViewUtils.setSelected(mMultiSelectMap.isSelectKey(key), selectIGView)
                // 触发回调
                selectListener?.onClickSelect(position, mMultiSelectMap.isSelectKey(key))
            }, holder.itemView.findViewById(R.id.vid_ll))
    }

    // =======
    // = 多选 =
    // =======

    override fun getMultiSelectKey(
        item: CommodityItem?,
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