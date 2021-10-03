package dev.capture;

import android.app.Dialog;
import android.content.Context;

import dev.callback.DevCallback;
import dev.capture.compiler.databinding.DevHttpCaptureDataTypeDialogBinding;
import dev.capture.compiler.databinding.DevHttpCaptureGroupTypeDialogBinding;

class Dialogs {

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
            super(context, R.style.DevDialogFullScreenTheme);

            this.mCallback = callback;
            this.mBinding  = DevHttpCaptureDataTypeDialogBinding.inflate(
                    getLayoutInflater()
            );
            setContentView(mBinding.getRoot());

            mBinding.vidAll.setOnClickListener(view -> callback(Items.DataType.T_ALL));
            mBinding.vid014.setOnClickListener(view -> callback(Items.DataType.T_0_14));
            mBinding.vid1529.setOnClickListener(view -> callback(Items.DataType.T_15_29));
            mBinding.vid3044.setOnClickListener(view -> callback(Items.DataType.T_30_44));
            mBinding.vid4559.setOnClickListener(view -> callback(Items.DataType.T_45_59));
            mBinding.vidCancel.setOnClickListener(view -> dismiss());
        }

        private void callback(Items.DataType dataType) {
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
            super(context, R.style.DevDialogFullScreenTheme);

            this.mCallback = callback;
            this.mBinding  = DevHttpCaptureGroupTypeDialogBinding.inflate(
                    getLayoutInflater()
            );
            setContentView(mBinding.getRoot());

            mBinding.vidTime.setOnClickListener(view -> callback(Items.GroupType.T_TIME));
            mBinding.vidUrl.setOnClickListener(view -> callback(Items.GroupType.T_URL));
            mBinding.vidCancel.setOnClickListener(view -> dismiss());
        }

        private void callback(Items.GroupType dataType) {
            dismiss();
            mCallback.callback(dataType);
        }
    }
}