package dev.capture;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import dev.adapter.DevDataAdapterExt2;
import dev.base.multiselect.DevMultiSelectMap;
import dev.capture.compiler.databinding.DevHttpCaptureMainModuleAdapterBinding;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.quick.QuickHelper;

/**
 * detail: DevHttpCapture 模块适配器
 * @author Ttt
 */
public class AdapterMainModule
        extends DevDataAdapterExt2<Items.MainItem, BaseDevHttpViewHolder<DevHttpCaptureMainModuleAdapterBinding>> {

    public AdapterMainModule() {
        // 初始化多选实现方案
        setMultiSelectMap(new DevMultiSelectMap<>());
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
        parentContext(parent);
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
        if (ViewUtils.setVisibility(unfold, holder.binding.vidRecycler)) {
            holder.binding.vidRecycler.setAdapter(new AdapterMainModuleList(item));
        }
        QuickHelper.get(holder.binding.vidTitleTv)
                .setText(item.moduleName)
                .setOnClick(view -> {
                    // 反选展开状态
                    mMultiSelectMap.toggle(item.moduleName, item);
                    // 刷新适配器
                    notifyDataChanged();
                });
    }
}