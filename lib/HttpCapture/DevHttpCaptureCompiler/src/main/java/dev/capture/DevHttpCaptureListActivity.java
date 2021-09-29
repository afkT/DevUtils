package dev.capture;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import dev.callback.DevCallback;
import dev.capture.compiler.R;
import dev.capture.compiler.databinding.DevHttpCaptureMainActivityBinding;
import dev.utils.DevFinal;
import dev.utils.app.BarUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: DevHttpCapture 抓包数据列表
 * @author Ttt
 */
public class DevHttpCaptureListActivity
        extends BaseDevHttpActivity {

    private       DevHttpCaptureMainActivityBinding mBinding;
    // 当前选中的 Module
    private       String                            mModule;
    // 当前选中的日期
    private       String                            mDate;
    // 首页适配器
    private final AdapterMainModule                 mAdapter  = new AdapterMainModule();
    // 查询回调
    private final DevCallback<Boolean>              mCallback = new DevCallback<Boolean>() {
        @Override
        public void callback(Boolean isQuerying) {
            if (!isFinishing()) {
                if (isQuerying) {
                    ToastTintUtils.normal(
                            ResourceUtils.getString(R.string.dev_http_capture_querying)
                    );
                    return;
                }
                ToastTintUtils.success(
                        ResourceUtils.getString(R.string.dev_http_capture_query_complete)
                );
                // 设置数据源
                mAdapter.setDataList(UtilsCompiler.getInstance().getMainData(mModule));
                // 判断是否存在数据
                ViewUtils.reverseVisibilitys(
                        mAdapter.isDataNotEmpty(),
                        mBinding.vidRecycler,
                        mBinding.tipsView.vidTipsFrame
                );
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DevHttpCaptureMainActivityBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        // 设置状态栏颜色
        BarUtils.setStatusBarColor(this, ResourceUtils.getColor(R.color.dev_http_capture_title_bg));
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
    private void initValue(Intent intent) {
        // 获取模块名
        mModule = intent.getStringExtra(DevFinal.MODULE);
        // 获取时间
        mDate = intent.getStringExtra(DevFinal.DATE);

        // 设置点击事件
        mBinding.title.vidBackIgview.setOnClickListener(view -> finishOperate());
        // 设置标题
        mBinding.title.vidTitleTv.setText(mDate + " - " + mModule);
        // 设置提示文案
        mBinding.tipsView.vidTipsTv.setText(R.string.dev_http_capture_query_no_data);
    }
}