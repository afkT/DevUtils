package dev.capture.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import dev.capture.UtilsCompiler;

/**
 * detail: DevHttpCapture Base Activity
 * @author Ttt
 */
public class BaseDevHttpActivity
        extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加 Activity
        UtilsCompiler.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 移除 Activity
        UtilsCompiler.getInstance().removeActivity(this);
    }
}