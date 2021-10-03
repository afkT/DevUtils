package dev.capture;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import dev.DevHttpCapture;
import dev.callback.DevCallback;
import dev.capture.compiler.R;
import dev.capture.compiler.databinding.DevHttpCaptureMainActivityBinding;
import dev.utils.DevFinal;
import dev.utils.app.BarUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ViewUtils;
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
                        mBinding.vidTips.vidTipsFrame
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
        mModule = intent.getStringExtra(DevFinal.MODULE);

        // 设置点击事件
        mBinding.vidTitle.vidBackIgview.setOnClickListener(view -> finishOperate());
        // 设置标题
        mBinding.vidTitle.vidTitleTv.setText(DevHttpCapture.TAG);
        // 设置提示文案
        mBinding.vidTips.vidTipsTv.setText(R.string.dev_http_capture_query_no_data);
        // 绑定适配器
        mAdapter.bindAdapter(mBinding.vidRecycler);
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
    }
}