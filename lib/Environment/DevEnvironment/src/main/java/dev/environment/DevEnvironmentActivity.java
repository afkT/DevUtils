package dev.environment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * detail: DevEnvironment Activity
 * @author Ttt
 */
public final class DevEnvironmentActivity extends Activity {

    /**
     * 跳转 DevEnvironment Activity
     * @param context {@link Context}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean start(final Context context) {
        return start(context, null);
    }

    /**
     * 跳转 DevEnvironment Activity
     * @param context         {@link Context}
     * @param restartCallBack {@link RestartCallBack}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean start(final Context context, final RestartCallBack restartCallBack) {
        return Utils.start(context, restartCallBack);
    }

    // =

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                View decorView = getWindow().getDecorView();
                // 设置全屏和状态栏透明
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                // 设置状态栏为主题色
                getWindow().setStatusBarColor(this.getResources().getColor(R.color.dev_environment_color));
            }
        } catch (Exception e) {
        }
        setContentView(R.layout.dev_environment_activity);
        // back
        findViewById(R.id.vid_dea_back_igview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // restart
        TextView vid_dea_restart_tv = findViewById(R.id.vid_dea_restart_tv);
        if (Utils.sRestartCallBack != null) {
            vid_dea_restart_tv.setVisibility(View.VISIBLE);
            vid_dea_restart_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Utils.sRestartCallBack.onRestart();
                }
            });
        }
        AdapterItem.refreshHashCode(this);
    }
}