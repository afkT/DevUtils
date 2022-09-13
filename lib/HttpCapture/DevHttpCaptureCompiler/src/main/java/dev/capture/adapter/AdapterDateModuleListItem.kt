package dev.capture.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.adapter.DevDataAdapterExt
import dev.capture.CaptureFile
import dev.capture.UtilsCompiler
import dev.capture.activity.DevHttpCaptureFileActivity
import dev.capture.base.BaseDevHttpViewHolder
import dev.capture.compiler.databinding.DevHttpCaptureDateModuleListItemAdapterBinding
import dev.capture.model.Items.GroupItem
import dev.utils.DevFinal
import dev.utils.app.ViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.app.helper.view.ViewHelper
import dev.utils.common.DateUtils
import dev.utils.common.StringUtils

/**
 * detail: DevHttpCapture 模块列表适配器
 * @author Ttt
 */
class AdapterDateModuleListItem(
    private val groupItem: GroupItem,
    recyclerView: RecyclerView
) : DevDataAdapterExt<CaptureFile, BaseDevHttpViewHolder<DevHttpCaptureDateModuleListItemAdapterBinding>>() {

    init {
        setDataList(groupItem.lists, false)
        bindAdapter(recyclerView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseDevHttpViewHolder<DevHttpCaptureDateModuleListItemAdapterBinding> {
        return BaseDevHttpViewHolder(
            DevHttpCaptureDateModuleListItemAdapterBinding.inflate(
                LayoutInflater.from(mContext), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: BaseDevHttpViewHolder<DevHttpCaptureDateModuleListItemAdapterBinding>,
        position: Int
    ) {
        val item = getDataItem(position)
        ViewHelper.get()
            .setVisibilitys(isFirstPosition(position), holder.binding.vidLineView)
            .setText(item.getMethod(), holder.binding.vidMethodTv)
            .setText(item.getUrl(), holder.binding.vidUrlTv)
            .setText(DateUtils.formatTime(item.getTime()), holder.binding.vidTimeTv)
            .setOnClick({
                // 跳转 抓包数据详情 Activity
                start(mContext, item)
            }, holder.binding.root)

        // 判断分组筛选类型是否为请求链接类型
        if (ViewUtils.setVisibility(
                StringUtils.isEmpty(groupItem.function),
                holder.binding.vidFunctionTv
            )
        ) {
            val function = UtilsCompiler.getUrlFunctionByFile(item)
            QuickHelper.get(holder.binding.vidFunctionTv)
                .setText(function)
                .setVisibilitys(StringUtils.isNotEmpty(function))
        }
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 跳转 抓包数据详情 Activity
     * @param context     [Context]
     * @param captureFile 抓包存储文件
     */
    private fun start(
        context: Context,
        captureFile: CaptureFile
    ) {
        try {
            val intent = Intent(context, DevHttpCaptureFileActivity::class.java)
            intent.putExtra(DevFinal.STR.JSON, captureFile.toJson())
            context.startActivity(intent)
        } catch (ignored: Exception) {
        }
    }
}