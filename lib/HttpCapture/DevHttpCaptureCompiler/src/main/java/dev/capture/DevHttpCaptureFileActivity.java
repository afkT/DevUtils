package dev.capture;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import dev.capture.compiler.R;
import dev.capture.compiler.databinding.DevHttpCaptureFileActivityBinding;
import dev.utils.DevFinal;
import dev.utils.app.BarUtils;
import dev.utils.app.ResourceUtils;

/**
 * detail: DevHttpCapture 抓包数据详情页
 * @author Ttt
 */
public class DevHttpCaptureFileActivity
        extends BaseDevHttpActivity {

    private       DevHttpCaptureFileActivityBinding mBinding;
    // 首页适配器
    private final AdapterCaptureFile                mAdapter = new AdapterCaptureFile();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DevHttpCaptureFileActivityBinding.inflate(getLayoutInflater());
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
        // 获取抓包文件
        String json = intent.getStringExtra(DevFinal.JSON);

        // 设置点击事件
        mBinding.vidTitle.vidBackIgview.setOnClickListener(view -> finishOperate());
        // 设置标题
        mBinding.vidTitle.vidTitleTv.setText("Http Capture Details");
        // 绑定适配器
        mAdapter.bindAdapter(mBinding.vidRecycler);
        // 设置数据源
        mBinding.vidRecycler.post(() -> {
            if (!isFinishing()) {
                mAdapter.setDataList(UtilsCompiler.getInstance().getFileData(json));
            }
        });
    }
}