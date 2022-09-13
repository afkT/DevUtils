package dev.capture.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.adapter.DevDataAdapterExt2
import dev.capture.base.BaseDevHttpViewHolder
import dev.capture.compiler.R
import dev.capture.compiler.databinding.DevHttpCaptureMainModuleAdapterBinding
import dev.capture.model.Items.MainItem
import dev.utils.app.ListViewUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.ViewUtils
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: DevHttpCapture 模块适配器
 * @author Ttt
 */
class AdapterMainModule :
    DevDataAdapterExt2<MainItem, BaseDevHttpViewHolder<DevHttpCaptureMainModuleAdapterBinding>>() {

    // 延迟滑动时间
    private val mDelay = ResourceUtils.getInteger(
        R.integer.dev_http_capture_query_item_scroll_delay
    ).toLong().coerceAtLeast(30L)

    override fun getMultiSelectKey(
        item: MainItem,
        position: Int
    ): String {
        return item.moduleName
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseDevHttpViewHolder<DevHttpCaptureMainModuleAdapterBinding> {
        return BaseDevHttpViewHolder(
            DevHttpCaptureMainModuleAdapterBinding.inflate(
                LayoutInflater.from(mContext), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: BaseDevHttpViewHolder<DevHttpCaptureMainModuleAdapterBinding>,
        position: Int
    ) {
        val item = getDataItem(position)
        // 判断对应模块是否展开
        val unfold = mMultiSelectMap.isSelectKey(item.moduleName)
        if (ViewUtils.setVisibility(unfold, holder.binding.vidRv)) {
            AdapterMainModuleList(item, holder.binding.vidRv)
        }
        QuickHelper.get(holder.binding.vidTitleTv)
            .setText(item.moduleName)
            .setOnClick { view: View ->
                // 反选展开状态
                mMultiSelectMap.toggle(item.moduleName, item)
                // 刷新适配器
                notifyDataChanged()
                // 延时滑动到点击的索引
                view.postDelayed({
                    ListViewUtils.scrollToPosition(
                        mRecyclerView, position
                    )
                }, mDelay)
            }
    }
}