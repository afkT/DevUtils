package dev.capture.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.adapter.DevDataAdapterExt;
import dev.capture.CaptureFile;
import dev.capture.UtilsCompiler;
import dev.capture.activity.DevHttpCaptureFileActivity;
import dev.capture.base.BaseDevHttpViewHolder;
import dev.capture.compiler.databinding.DevHttpCaptureDateModuleListItemAdapterBinding;
import dev.capture.model.Items;
import dev.utils.DevFinal;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.quick.QuickHelper;
import dev.utils.app.helper.view.ViewHelper;
import dev.utils.common.DateUtils;
import dev.utils.common.StringUtils;

/**
 * detail: DevHttpCapture 模块列表适配器
 * @author Ttt
 */
public class AdapterDateModuleListItem
        extends DevDataAdapterExt<CaptureFile, BaseDevHttpViewHolder<DevHttpCaptureDateModuleListItemAdapterBinding>> {

    Items.GroupItem groupItem;

    public AdapterDateModuleListItem(
            Items.GroupItem groupItem,
            RecyclerView recyclerView
    ) {
        this.groupItem = groupItem;
        setDataList(groupItem.lists, false);
        bindAdapter(recyclerView);
    }

    @NonNull
    @Override
    public BaseDevHttpViewHolder<DevHttpCaptureDateModuleListItemAdapterBinding> onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        return new BaseDevHttpViewHolder<>(
                DevHttpCaptureDateModuleListItemAdapterBinding.inflate(
                        LayoutInflater.from(mContext), parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(
            @NonNull BaseDevHttpViewHolder<DevHttpCaptureDateModuleListItemAdapterBinding> holder,
            int position
    ) {
        CaptureFile item = getDataItem(position);
        ViewHelper.get()
                .setVisibilitys(isFirstPosition(position), holder.binding.vidLineView)
                .setText(item.getMethod(), holder.binding.vidMethodTv)
                .setText(item.getUrl(), holder.binding.vidUrlTv)
                .setText(DateUtils.formatTime(item.getTime()), holder.binding.vidTimeTv)
                .setOnClick(view -> {
                    // 跳转 抓包数据详情 Activity
                    start(mContext, item);
                }, holder.binding.getRoot());

        // 判断分组筛选类型是否为请求链接类型
        if (
                ViewUtils.setVisibility(
                        TextUtils.isEmpty(groupItem.function),
                        holder.binding.vidFunctionTv
                )
        ) {
            String function = UtilsCompiler.getInstance().getUrlFunctionByFile(item);
            QuickHelper.get(holder.binding.vidFunctionTv)
                    .setText(function)
                    .setVisibilitys(StringUtils.isNotEmpty(function));
        }
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 跳转 抓包数据详情 Activity
     * @param context     {@link Context}
     * @param captureFile 抓包存储文件
     */
    private void start(
            final Context context,
            final CaptureFile captureFile
    ) {
        try {
            Intent intent = new Intent(context, DevHttpCaptureFileActivity.class);
            intent.putExtra(DevFinal.STR.JSON, captureFile.toJson());
            context.startActivity(intent);
        } catch (Exception ignored) {
        }
    }
}