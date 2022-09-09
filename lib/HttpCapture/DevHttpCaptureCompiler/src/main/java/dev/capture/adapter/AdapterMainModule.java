package dev.capture.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import dev.adapter.DevDataAdapterExt2;
import dev.capture.base.BaseDevHttpViewHolder;
import dev.capture.compiler.R;
import dev.capture.compiler.databinding.DevHttpCaptureMainModuleAdapterBinding;
import dev.capture.model.Items;
import dev.utils.app.ListViewUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.quick.QuickHelper;

/**
 * detail: DevHttpCapture 模块适配器
 * @author Ttt
 */
public class AdapterMainModule
        extends DevDataAdapterExt2<Items.MainItem, BaseDevHttpViewHolder<DevHttpCaptureMainModuleAdapterBinding>> {

    // 延迟滑动时间
    private final long mDelay;

    public AdapterMainModule() {
        // 初始化延迟滑动时间
        mDelay = Math.max(ResourceUtils.getInteger(
                R.integer.dev_http_capture_query_item_scroll_delay
        ), 30L);
    }

    @Override
    public String getMultiSelectKey(
            Items.MainItem item,
            int position
    ) {
        return item.moduleName;
    }

    @NonNull
    @Override
    public BaseDevHttpViewHolder<DevHttpCaptureMainModuleAdapterBinding> onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        return new BaseDevHttpViewHolder<>(
                DevHttpCaptureMainModuleAdapterBinding.inflate(
                        LayoutInflater.from(mContext), parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(
            @NonNull BaseDevHttpViewHolder<DevHttpCaptureMainModuleAdapterBinding> holder,
            int position
    ) {
        Items.MainItem item = getDataItem(position);
        // 判断对应模块是否展开
        boolean unfold = mMultiSelectMap.isSelectKey(item.moduleName);
        if (ViewUtils.setVisibility(unfold, holder.binding.vidRv)) {
            new AdapterMainModuleList(item, holder.binding.vidRv);
        }
        QuickHelper.get(holder.binding.vidTitleTv)
                .setText(item.moduleName)
                .setOnClick(view -> {
                    // 反选展开状态
                    mMultiSelectMap.toggle(item.moduleName, item);
                    // 刷新适配器
                    notifyDataChanged();
                    // 延时滑动到点击的索引
                    view.postDelayed(() -> ListViewUtils.scrollToPosition(
                            mRecyclerView, position
                    ), mDelay);
                });
    }
}