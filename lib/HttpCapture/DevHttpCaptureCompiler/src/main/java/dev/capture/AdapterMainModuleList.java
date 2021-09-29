package dev.capture;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

import dev.adapter.DevDataAdapterExt;
import dev.capture.compiler.databinding.DevHttpCaptureMainModuleListAdapterBinding;
import dev.utils.app.helper.quick.QuickHelper;

/**
 * detail: DevHttpCapture 模块列表适配器
 * @author Ttt
 */
public class AdapterMainModuleList
        extends DevDataAdapterExt<CaptureItem, BaseDevHttpViewHolder<DevHttpCaptureMainModuleListAdapterBinding>> {

    public AdapterMainModuleList(List<CaptureItem> lists) {
        setDataList(lists, false);
    }

    @NonNull
    @Override
    public BaseDevHttpViewHolder<DevHttpCaptureMainModuleListAdapterBinding> onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        parentContext(parent);
        return new BaseDevHttpViewHolder<>(
                DevHttpCaptureMainModuleListAdapterBinding.inflate(
                        LayoutInflater.from(mContext), parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(
            @NonNull BaseDevHttpViewHolder<DevHttpCaptureMainModuleListAdapterBinding> holder,
            int position
    ) {
        CaptureItem item = getDataItem(position);
        QuickHelper.get(holder.binding.vidTitleTv)
                .setText(item.getYyyyMMdd())
                .setOnClick(view -> {
                });
    }
}