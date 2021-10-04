package dev.capture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import dev.callback.DevCallback;
import dev.capture.compiler.R;
import dev.capture.compiler.databinding.DevHttpCaptureListActivityBinding;
import dev.utils.DevFinal;
import dev.utils.app.BarUtils;
import dev.utils.app.ClickUtils;
import dev.utils.app.DialogUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.view.ViewHelper;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: DevHttpCapture 抓包数据列表
 * @author Ttt
 */
public class DevHttpCaptureListActivity
        extends BaseDevHttpActivity {

    private       DevHttpCaptureListActivityBinding mBinding;
    // 当前选中的 Module
    private       String                            mModule;
    // 当前选中的日期
    private       String                            mDate;
    // 拼接筛选选项
    private       String                            mOptions   = null;
    // 数据来源类型
    private       Items.DataType                    mDataType  = Items.DataType.T_ALL;
    // 分组类型
    private       Items.GroupType                   mGroupType = Items.GroupType.T_TIME;
    // 首页适配器
    private final AdapterDateModuleList             mAdapter   = new AdapterDateModuleList();
    // 查询回调
    private final DevCallback<Boolean>              mCallback  = new DevCallback<Boolean>() {
        @Override
        public void callback(
                Boolean isQuerying,
                int size
        ) {
            if (!isFinishing()) {
                if (isQuerying) {
                    if (size == 0) {
                        ToastTintUtils.normal(
                                ResourceUtils.getString(R.string.dev_http_capture_querying)
                        );
                    }
                    return;
                }
                // 如果和之前的选项不一样, 则清空历史多选数据
                if (!getNewOptions().equals(mOptions)) {
                    mOptions = getNewOptions();
                    mAdapter.setNotifyAdapter(false)
                            .clearSelectAll();
                }
                // 设置数据源
                mAdapter.setDataList(
                        UtilsCompiler.getInstance().getDateData(
                                mModule, mDate, mDataType, mGroupType
                        )
                );
                // 判断是否存在数据
                ViewUtils.reverseVisibilitys(
                        mAdapter.isDataNotEmpty(),
                        mBinding.vidRecycler,
                        mBinding.vidTips.vidTipsFrame
                );
                ToastTintUtils.success(
                        ResourceUtils.getString(R.string.dev_http_capture_query_complete)
                );
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DevHttpCaptureListActivityBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        // 设置状态栏颜色
        BarUtils.setStatusBarColor(this, ResourceUtils.getColor(R.color.dev_http_capture_title_bg_color));
        // 初始化数据
        initValue(getIntent());
    }

    @Override
    public void onBackPressed() {
        finishOperate();
    }

    /**
     * 销毁操作方法
     */
    private void finishOperate() {
        // 移除回调
        UtilsCompiler.getInstance().removeCallback(mCallback);
        // 关闭当前页面
        finish();
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 初始化数据
     * @param intent Intent
     */
    private void initValue(final Intent intent) {
        // 获取模块名
        mModule = intent.getStringExtra(DevFinal.MODULE);
        // 获取时间
        mDate = intent.getStringExtra(DevFinal.DATE);

        // 设置点击事件
        mBinding.vidTitle.vidBackIgview.setOnClickListener(view -> finishOperate());
        // 设置标题
        mBinding.vidTitle.vidTitleTv.setText(mDate + " - " + mModule);
        // 设置提示文案
        mBinding.vidTips.vidTipsTv.setText(R.string.dev_http_capture_query_no_data);
        // 绑定适配器
        mAdapter.bindAdapter(mBinding.vidRecycler);

        // 刷新选项 View 文本
        refreshOptionsText();
        // 设置新的拼接筛选选项
        mOptions = getNewOptions();

        // 初始化事件
        initListener();

        // ==========
        // = 数据获取 =
        // ==========

        queryData();
    }

    /**
     * 查询数据
     */
    private void queryData() {
        UtilsCompiler.getInstance().queryData(
                mCallback, false
        );
    }

    /**
     * 获取新的拼接筛选选项
     * @return 拼接筛选选项
     */
    private String getNewOptions() {
        return mDataType.type + "-" + mGroupType.type;
    }

    /**
     * 刷新选项 View 文本
     */
    private void refreshOptionsText() {
        ViewHelper.get()
                .setText(mDataType.getTitle(), mBinding.vidTab.vidDataType)
                .setText(mGroupType.getTitle(), mBinding.vidTab.vidGroupType);
    }

    // ==========
    // = 事件处理 =
    // ==========

    // 点击 ( 双击 ) 辅助类
    private final ClickUtils.ClickAssist mClickAssist = new ClickUtils.ClickAssist(300L);

    /**
     * 初始化事件
     */
    private void initListener() {
        initDialogs();

        ViewHelper.get()
                .setOnClick(new ClickUtils.OnDebouncingClickListener(mClickAssist) {
                    @Override
                    public void doClick(View view) {
                        DialogUtils.closeDialog(mDataTypeDialog);
                        DialogUtils.showDialog(mDataTypeDialog);
                    }
                }, mBinding.vidTab.vidDataTab)
                .setOnClick(new ClickUtils.OnDebouncingClickListener(mClickAssist) {
                    @Override
                    public void doClick(View view) {
                        DialogUtils.closeDialog(mGroupTypeDialog);
                        DialogUtils.showDialog(mGroupTypeDialog);
                    }
                }, mBinding.vidTab.vidGroupTab);
    }

    // ==========
    // = 弹窗处理 =
    // ==========

    // 数据来源选项 Dialog
    private Dialogs.DataTypeDialog  mDataTypeDialog;
    // 分组选项 Dialog
    private Dialogs.GroupTypeDialog mGroupTypeDialog;

    /**
     * 初始化 Dialog
     */
    private void initDialogs() {
        mDataTypeDialog  = new Dialogs.DataTypeDialog(
                this, new DevCallback<Items.DataType>() {
            @Override
            public void callback(Items.DataType value) {
                if (value != mDataType) {
                    mDataType = value;
                    // 刷新选项 View 文本
                    refreshOptionsText();
                    // 查询数据
                    queryData();
                }
            }
        });
        mGroupTypeDialog = new Dialogs.GroupTypeDialog(
                this, new DevCallback<Items.GroupType>() {
            @Override
            public void callback(Items.GroupType value) {
                if (value != mGroupType) {
                    mGroupType = value;
                    // 刷新选项 View 文本
                    refreshOptionsText();
                    // 查询数据
                    queryData();
                }
            }
        });
    }
}