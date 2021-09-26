package dev.capture;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import dev.DevHttpCapture;
import dev.DevHttpCaptureCompiler;
import dev.capture.compiler.databinding.DevHttpcaptureMainActivityBinding;
import dev.utils.DevFinal;
import dev.utils.common.StringUtils;

/**
 * detail: DevHttpCapture 入口
 * @author Ttt
 */
public class DevHttpCaptureMainActivity
        extends BaseDevHttpActivity {

    private DevHttpcaptureMainActivityBinding mBinding;
    // 当前选中的 Module
    private String                            mModule;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DevHttpcaptureMainActivityBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        // 获取模块名
        mModule = getIntent().getStringExtra(DevFinal.MODULE);

        // ===========
        // = Toolbar =
        // ===========

        setSupportActionBar(mBinding.vidToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 给左上角图标的左边加上一个返回的图标
            actionBar.setDisplayHomeAsUpEnabled(true);
            // 对应 ActionBar.DISPLAY_SHOW_TITLE
            actionBar.setDisplayShowTitleEnabled(false);
        }
        // 设置点击事件
        mBinding.vidToolbar.setNavigationOnClickListener(view -> DevHttpCaptureCompiler.finishAllActivity());
        // 设置标题
        if (StringUtils.isEmpty(mModule)) {
            mBinding.vidToolbar.setTitle(DevHttpCapture.TAG);
        } else {
            mBinding.vidToolbar.setTitle(mModule);
        }
    }
}