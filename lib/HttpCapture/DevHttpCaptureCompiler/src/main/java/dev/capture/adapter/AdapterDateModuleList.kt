package dev.capture.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.adapter.DevDataAdapterExt2
import dev.capture.base.BaseDevHttpViewHolder
import dev.capture.compiler.R
import dev.capture.compiler.databinding.DevHttpCaptureDateModuleListAdapterBinding
import dev.capture.model.Items.GroupItem
import dev.utils.app.ListViewUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.ViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.StringUtils

/**
 * detail: DevHttpCapture 对应模块具体日期抓包列表适配器
 * @author Ttt
 */
class AdapterDateModuleList :
    DevDataAdapterExt2<GroupItem, BaseDevHttpViewHolder<DevHttpCaptureDateModuleListAdapterBinding>>() {

    // 延迟滑动时间
    private val mDelay = ResourceUtils.getInteger(
        R.integer.dev_http_capture_query_item_scroll_delay
    ).toLong().coerceAtLeast(30L)

    override fun getMultiSelectKey(
        item: GroupItem,
        position: Int
    ): String {
        return item.title
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseDevHttpViewHolder<DevHttpCaptureDateModuleListAdapterBinding> {
        return BaseDevHttpViewHolder(
            DevHttpCaptureDateModuleListAdapterBinding.inflate(
                LayoutInflater.from(mContext), parent, false
            )
        )
    }

    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(
        holder: BaseDevHttpViewHolder<DevHttpCaptureDateModuleListAdapterBinding>,
        position: Int
    ) {
        val item = getDataItem(position)
        // 判断对应模块是否展开
        val unfold = mMultiSelectMap.isSelectKey(item.title)
        if (ViewUtils.setVisibility(unfold, holder.binding.vidRv)) {
            AdapterDateModuleListItem(item, holder.binding.vidRv)
        }
        QuickHelper.get(holder.binding.vidTitleTv)
            .setText(item.title)
            .setOnClick { view: View ->
                // 反选展开状态
                mMultiSelectMap.toggle(item.title, item)
                // 刷新适配器
                notifyDataChanged()
                // 延时滑动到点击的索引
                view.postDelayed({
                    ListViewUtils.scrollToPosition(
                        mRecyclerView, position
                    )
                }, mDelay)
            }
        // 接口所属功能
        QuickHelper.get(holder.binding.vidFunctionTv)
            .setText(item.function)
            .setVisibilitys(StringUtils.isNotEmpty(item.function))
    }
}