package dev.environment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * detail: DevEnvironment Activity
 * @author Ttt
 */
public final class DevEnvironmentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
}