package dev.capture;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * detail: DevHttpCapture Base Activity
 * @author Ttt
 */
class BaseDevHttpActivity
        extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 记录 Activity
        Utils.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 移除 Activity
        Utils.getInstance().removeActivity(this);
    }
}