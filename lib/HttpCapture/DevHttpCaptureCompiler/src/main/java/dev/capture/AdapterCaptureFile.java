package dev.capture;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import dev.adapter.DevDataAdapterExt;
import dev.capture.compiler.R;
import dev.capture.compiler.databinding.DevHttpCaptureCaptureFileAdapterBinding;
import dev.utils.app.ClipboardUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.helper.view.ViewHelper;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: DevHttpCapture 抓包数据详情适配器
 * @author Ttt
 */
public class AdapterCaptureFile
        extends DevDataAdapterExt<Items.FileItem, BaseDevHttpViewHolder<DevHttpCaptureCaptureFileAdapterBinding>> {

    @NonNull
    @Override
    public BaseDevHttpViewHolder<DevHttpCaptureCaptureFileAdapterBinding> onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        return new BaseDevHttpViewHolder<>(
                DevHttpCaptureCaptureFileAdapterBinding.inflate(
                        LayoutInflater.from(mContext), parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(
            @NonNull BaseDevHttpViewHolder<DevHttpCaptureCaptureFileAdapterBinding> holder,
            int position
    ) {
        Items.FileItem item = getDataItem(position);
        ViewHelper.get()
                .setText(item.title, holder.binding.vidTitleTv)
                .setText(item.value, holder.binding.vidValueTv)
                .setOnClick(view -> {
                    ClipboardUtils.copyText(item.value);
                    ToastTintUtils.success(
                            ResourceUtils.getString(R.string.dev_http_capture_copy_success)
                    );
                }, holder.binding.vidCopyTv);
    }
}