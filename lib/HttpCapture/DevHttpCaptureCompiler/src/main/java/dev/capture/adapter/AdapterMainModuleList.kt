package dev.capture.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.adapter.DevDataAdapterExt
import dev.capture.CaptureItem
import dev.capture.activity.DevHttpCaptureListActivity
import dev.capture.base.BaseDevHttpViewHolder
import dev.capture.compiler.databinding.DevHttpCaptureMainModuleListAdapterBinding
import dev.capture.model.Items.MainItem
import dev.utils.DevFinal
import dev.utils.app.helper.quick.QuickHelper

/**
 * detail: DevHttpCapture 模块列表适配器
 * @author Ttt
 */
internal class AdapterMainModuleList(
    private val mainItem: MainItem,
    recyclerView: RecyclerView
) : DevDataAdapterExt<CaptureItem, BaseDevHttpViewHolder<DevHttpCaptureMainModuleListAdapterBinding>>() {

    init {
        setDataList(mainItem.lists, false)
        bindAdapter(recyclerView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseDevHttpViewHolder<DevHttpCaptureMainModuleListAdapterBinding> {
        return BaseDevHttpViewHolder(
            DevHttpCaptureMainModuleListAdapterBinding.inflate(
                LayoutInflater.from(mContext), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: BaseDevHttpViewHolder<DevHttpCaptureMainModuleListAdapterBinding>,
        position: Int
    ) {
        val item = getDataItem(position)
        QuickHelper.get(holder.binding.vidTitleTv)
            .setText(item.yyyyMMdd)
            .setOnClick {
                // 跳转 抓包数据列表 Activity
                start(mContext, mainItem.moduleName, item.yyyyMMdd)
            }
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 跳转 抓包数据列表 Activity
     * @param context    [Context]
     * @param moduleName 模块名 ( 要求唯一性 )
     * @param date       yyyyMMdd
     */
    private fun start(
        context: Context,
        moduleName: String,
        date: String
    ) {
        try {
            val intent = Intent(context, DevHttpCaptureListActivity::class.java)
            intent.putExtra(DevFinal.STR.MODULE, moduleName)
            intent.putExtra(DevFinal.STR.DATE, date)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } catch (_: Exception) {
        }
    }
}