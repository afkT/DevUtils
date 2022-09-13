package dev.capture.model;

import android.app.Dialog;
import android.content.Context;

import dev.callback.DevCallback;
import dev.capture.compiler.databinding.DevHttpCaptureDataTypeDialogBinding;
import dev.capture.compiler.databinding.DevHttpCaptureGroupTypeDialogBinding;

public class Dialogs {

    /**
     * 数据来源选项 Dialog
     */
    public static class DataTypeDialog
            extends Dialog {

        private final DevHttpCaptureDataTypeDialogBinding mBinding;
        // 回调事件
        private final DevCallback<Items.DataType>         mCallback;

        public DataTypeDialog(
                Context context,
                DevCallback<Items.DataType> callback
        ) {
            super(context, dev.capture.R.style.DevDialogFullScreenTheme);

            this.mCallback = callback;
            this.mBinding  = DevHttpCaptureDataTypeDialogBinding.inflate(
                    getLayoutInflater()
            );
            setContentView(mBinding.getRoot());

            mBinding.vidAllTv.setOnClickListener(view -> callback(Items.DataType.T_ALL));
            mBinding.vid09Tv.setOnClickListener(view -> callback(Items.DataType.T_0_9));
            mBinding.vid1019Tv.setOnClickListener(view -> callback(Items.DataType.T_10_19));
            mBinding.vid2029Tv.setOnClickListener(view -> callback(Items.DataType.T_20_29));
            mBinding.vid3039Tv.setOnClickListener(view -> callback(Items.DataType.T_30_39));
            mBinding.vid4049Tv.setOnClickListener(view -> callback(Items.DataType.T_40_49));
            mBinding.vid5059Tv.setOnClickListener(view -> callback(Items.DataType.T_50_59));
            mBinding.vidCancelTv.setOnClickListener(view -> dismiss());
        }

        private void callback(final Items.DataType dataType) {
            dismiss();
            mCallback.callback(dataType);
        }
    }

    /**
     * 分组选项 Dialog
     */
    public static class GroupTypeDialog
            extends Dialog {

        private final DevHttpCaptureGroupTypeDialogBinding mBinding;
        // 回调事件
        private final DevCallback<Items.GroupType>         mCallback;

        public GroupTypeDialog(
                Context context,
                DevCallback<Items.GroupType> callback
        ) {
            super(context, dev.capture.R.style.DevDialogFullScreenTheme);

            this.mCallback = callback;
            this.mBinding  = DevHttpCaptureGroupTypeDialogBinding.inflate(
                    getLayoutInflater()
            );
            setContentView(mBinding.getRoot());

            mBinding.vidTimeTv.setOnClickListener(view -> callback(Items.GroupType.T_TIME));
            mBinding.vidUrlTv.setOnClickListener(view -> callback(Items.GroupType.T_URL));
            mBinding.vidCancelTv.setOnClickListener(view -> dismiss());
        }

        private void callback(final Items.GroupType dataType) {
            dismiss();
            mCallback.callback(dataType);
        }
    }
}