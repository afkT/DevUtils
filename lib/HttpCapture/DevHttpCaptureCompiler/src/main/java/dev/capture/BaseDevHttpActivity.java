package dev.capture;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * detail: DevHttpCapture Base Activity
 * @author Ttt
 */
class BaseDevHttpActivity
        extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 记录 Activity
        UtilsCompiler.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 移除 Activity
        UtilsCompiler.getInstance().removeActivity(this);
    }
}