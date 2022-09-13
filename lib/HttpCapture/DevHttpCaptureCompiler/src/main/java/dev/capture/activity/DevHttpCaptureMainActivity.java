package dev.capture.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import dev.DevHttpCapture;
import dev.callback.DevCallback;
import dev.capture.UtilsCompiler;
import dev.capture.adapter.AdapterMainModule;
import dev.capture.base.BaseDevHttpActivity;
import dev.capture.compiler.R;
import dev.capture.compiler.databinding.DevHttpCaptureMainActivityBinding;
import dev.capture.model.Items;
import dev.utils.DevFinal;
import dev.utils.app.BarUtils;
import dev.utils.app.ClickUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.view.ViewHelper;
import dev.utils.app.toast.ToastTintUtils;
import dev.utils.common.StringUtils;

/**
 * detail: DevHttpCapture 入口
 * @author Ttt
 */
public class DevHttpCaptureMainActivity
        extends BaseDevHttpActivity {

    private       DevHttpCaptureMainActivityBinding mBinding;
    // 当前选中的 Module
    private       String                            mModule;
    // 首页适配器
    private final AdapterMainModule                 mAdapter  = new AdapterMainModule();
    // 查询回调
    private final DevCallback<Boolean>              mCallback = new DevCallback<Boolean>() {
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
                // 设置数据源
                mAdapter.setDataList(UtilsCompiler.getInstance().getMainData(mModule));
                // 判断是否存在数据
                ViewUtils.reverseVisibilitys(
                        mAdapter.isDataNotEmpty(),
                        mBinding.vidRv,
                        mBinding.vidTipsInclude.vidTipsFl
                );
                ToastTintUtils.success(
                        ResourceUtils.getString(R.string.dev_http_capture_query_complete)
                );
                // 重置刷新点击处理
                UtilsCompiler.getInstance().resetRefreshClick();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DevHttpCaptureMainActivityBinding.inflate(getLayoutInflater());
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
        UtilsCompiler.getInstance().clearCallback();
        // 清空数据
        UtilsCompiler.getInstance().clearData();
        // 关闭所有页面
        UtilsCompiler.getInstance().finishAllActivity();
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 初始化数据
     * @param intent Intent
     */
    private void initValue(final Intent intent) {
        // 移除回调
        UtilsCompiler.getInstance().clearCallback();
        // 清空数据
        UtilsCompiler.getInstance().clearData();

        // 获取模块名
        mModule = intent.getStringExtra(DevFinal.STR.MODULE);

        // 设置点击事件
        mBinding.vidTitleInclude.vidBackIv.setOnClickListener(view -> finishOperate());
        // 设置标题
        mBinding.vidTitleInclude.vidTitleTv.setText(DevHttpCapture.TAG);
        // 设置提示文案
        mBinding.vidTipsInclude.vidTipsTv.setText(R.string.dev_http_capture_query_no_data);
        // 绑定适配器
        mAdapter.bindAdapter(mBinding.vidRv);
        // 判断是否选择指定模块
        if (StringUtils.isNotEmpty(mModule)) {
            // 默认展开该模块
            mAdapter.getMultiSelectMap().select(
                    mModule, new Items.MainItem(mModule, new ArrayList<>())
            );
        }

        // ==========
        // = 数据获取 =
        // ==========

        UtilsCompiler.getInstance().queryData(
                mCallback, false
        );

        // ==========
        // = 事件处理 =
        // ==========

        ViewHelper.get()
                .setOnClick(new ClickUtils.OnDebouncingClickListener(UtilsCompiler.REFRESH_CLICK) {
                    @Override
                    public void doClick(View view) {
                        ToastTintUtils.normal(
                                ResourceUtils.getString(R.string.dev_http_capture_querying)
                        );
                        if (!UtilsCompiler.getInstance().isQuerying()) {
                            UtilsCompiler.getInstance().queryData(
                                    mCallback, true
                            );
                        }
                    }

                    @Override
                    public void doInvalidClick(View view) {
                        ToastTintUtils.normal(
                                ResourceUtils.getString(R.string.dev_http_capture_querying)
                        );
                    }
                }, mBinding.vidRefreshFl);
    }
}