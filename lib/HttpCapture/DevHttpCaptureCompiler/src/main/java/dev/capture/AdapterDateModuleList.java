package dev.capture;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import dev.adapter.DevDataAdapterExt2;
import dev.base.multiselect.DevMultiSelectMap;
import dev.capture.compiler.R;
import dev.capture.compiler.databinding.DevHttpCaptureDateModuleListAdapterBinding;
import dev.utils.app.ListViewUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.quick.QuickHelper;
import dev.utils.common.StringUtils;

/**
 * detail: DevHttpCapture 对应模块具体日期抓包列表适配器
 * @author Ttt
 */
class AdapterDateModuleList
        extends DevDataAdapterExt2<Items.GroupItem, BaseDevHttpViewHolder<DevHttpCaptureDateModuleListAdapterBinding>> {

    // 延迟滑动时间
    private final long mDelay;

    public AdapterDateModuleList() {
        // 初始化多选实现方案
        setMultiSelectMap(new DevMultiSelectMap<>());
        // 初始化延迟滑动时间
        mDelay = Math.max(ResourceUtils.getInteger(
                R.integer.dev_http_capture_query_item_scroll_delay
        ), 30L);
    }

    @Override
    public String getMultiSelectKey(
            Items.GroupItem item,
            int position
    ) {
        return item.title;
    }

    @NonNull
    @Override
    public BaseDevHttpViewHolder<DevHttpCaptureDateModuleListAdapterBinding> onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        return new BaseDevHttpViewHolder<>(
                DevHttpCaptureDateModuleListAdapterBinding.inflate(
                        LayoutInflater.from(mContext), parent, false
                )
        );
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(
            @NonNull BaseDevHttpViewHolder<DevHttpCaptureDateModuleListAdapterBinding> holder,
            int position
    ) {
        Items.GroupItem item = getDataItem(position);
        // 判断对应模块是否展开
        boolean unfold = mMultiSelectMap.isSelectKey(item.title);
        if (ViewUtils.setVisibility(unfold, holder.binding.vidRecycler)) {
            new AdapterDateModuleListItem(item, holder.binding.vidRecycler);
        }
        QuickHelper.get(holder.binding.vidTitleTv)
                .setText(item.title)
                .setOnClick(view -> {
                    // 反选展开状态
                    mMultiSelectMap.toggle(item.title, item);
                    // 刷新适配器
                    notifyDataChanged();
                    // 延时滑动到点击的索引
                    view.postDelayed(() -> ListViewUtils.scrollToPosition(
                            mRecyclerView, position
                    ), mDelay);
                });
        // 接口所属功能
        QuickHelper.get(holder.binding.vidFunctionTv)
                .setText(item.function)
                .setVisibilitys(StringUtils.isNotEmpty(item.function));
    }
}