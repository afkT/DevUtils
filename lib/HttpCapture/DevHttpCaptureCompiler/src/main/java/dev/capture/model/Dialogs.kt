package dev.capture.model

import android.app.Dialog
import android.content.Context
import dev.callback.DevCallback
import dev.capture.compiler.R
import dev.capture.compiler.databinding.DevHttpCaptureDataTypeDialogBinding
import dev.capture.compiler.databinding.DevHttpCaptureGroupTypeDialogBinding
import dev.capture.model.Items.GroupType

internal class Dialogs {

    /**
     * 数据来源选项 Dialog
     */
    class DataTypeDialog(
        context: Context,
        // 回调事件
        private val mCallback: DevCallback<Items.DataType>
    ) : Dialog(context, R.style.DevHttpCaptureCompilerDialogFullScreenTheme) {

        private val binding = DevHttpCaptureDataTypeDialogBinding.inflate(
            layoutInflater
        )

        private fun dispatchDataTypeChoice(dataType: Items.DataType) {
            dismiss()
            mCallback.callback(dataType)
        }

        init {
            setContentView(binding.root)

            binding.apply {
                vidAllTv.setOnClickListener { dispatchDataTypeChoice(Items.DataType.T_ALL) }
                vid09Tv.setOnClickListener { dispatchDataTypeChoice(Items.DataType.T_0_9) }
                vid1019Tv.setOnClickListener { dispatchDataTypeChoice(Items.DataType.T_10_19) }
                vid2029Tv.setOnClickListener { dispatchDataTypeChoice(Items.DataType.T_20_29) }
                vid3039Tv.setOnClickListener { dispatchDataTypeChoice(Items.DataType.T_30_39) }
                vid4049Tv.setOnClickListener { dispatchDataTypeChoice(Items.DataType.T_40_49) }
                vid5059Tv.setOnClickListener { dispatchDataTypeChoice(Items.DataType.T_50_59) }
                vidCancelTv.setOnClickListener { dismiss() }
            }
        }
    }

    /**
     * 分组选项 Dialog
     */
    class GroupTypeDialog(
        context: Context,
        // 回调事件
        private val mCallback: DevCallback<GroupType>
    ) : Dialog(context, R.style.DevHttpCaptureCompilerDialogFullScreenTheme) {

        private val binding = DevHttpCaptureGroupTypeDialogBinding.inflate(
            layoutInflater
        )

        private fun dispatchGroupTypeChoice(groupType: GroupType) {
            dismiss()
            mCallback.callback(groupType)
        }

        init {
            setContentView(binding.root)

            binding.apply {
                vidTimeTv.setOnClickListener { dispatchGroupTypeChoice(GroupType.T_TIME) }
                vidUrlTv.setOnClickListener { dispatchGroupTypeChoice(GroupType.T_URL) }
                vidCancelTv.setOnClickListener { dismiss() }
            }
        }
    }
}