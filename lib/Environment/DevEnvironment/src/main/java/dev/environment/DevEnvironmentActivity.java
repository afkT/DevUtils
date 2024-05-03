package dev.environment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * detail: DevEnvironment Activity
 * @author Ttt
 */
public final class DevEnvironmentActivity
        extends Activity {

    // ============
    // = Activity =
    // ============

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                View decorView = getWindow().getDecorView();
                // 设置全屏和状态栏透明
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                // 设置状态栏为主题色
                getWindow().setStatusBarColor(this.getResources().getColor(R.color.dev_environment_title_bg_color));
            } catch (Exception ignored) {
            }
        }
        if (DevEnvironmentUtils.isRelease()) {
            finish();
            return;
        }
        setContentView(R.layout.dev_environment_activity);
        // back
        findViewById(R.id.vid_back_iv).setOnClickListener(v -> finish());
        // restart
        TextView vid_restart_tv = findViewById(R.id.vid_restart_tv);
        if (DevEnvironmentUtils.sCallback != null) {
            vid_restart_tv.setVisibility(View.VISIBLE);
            vid_restart_tv.setOnClickListener(v -> {
                finish();
                if (DevEnvironmentUtils.sCallback != null) {
                    DevEnvironmentUtils.sCallback.onRestart();
                }
            });
        }
        // 初始化适配器并绑定
        ListView vid_lv = findViewById(R.id.vid_lv);
        vid_lv.setAdapter(new Adapter(DevEnvironmentUtils.getAdapterItems(this)));
    }

    // =========
    // = 适配器 =
    // =========

    /**
     * detail: Item Adapter
     * @author Ttt
     */
    class Adapter
            extends BaseAdapter {

        List<AdapterItem> lists;

        public Adapter(List<AdapterItem> lists) {
            this.lists = lists;
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public AdapterItem getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(
                int position,
                View convertView,
                ViewGroup parent
        ) {
            TextView  vid_name_tv;
            TextView  vid_value_tv;
            ImageView vid_mark_iv;

            final AdapterItem item = getItem(position);
            if (item.isModule()) { // Module Type
                convertView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.dev_environment_item_module, parent, false
                );
                vid_name_tv = convertView.findViewById(R.id.vid_name_tv);

                vid_name_tv.setText(item.getDisplayName());
            } else if (item.isEnvironment()) { // Environment Type
                convertView  = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.dev_environment_item_environment, parent, false
                );
                vid_name_tv  = convertView.findViewById(R.id.vid_name_tv);
                vid_value_tv = convertView.findViewById(R.id.vid_value_tv);
                vid_mark_iv  = convertView.findViewById(R.id.vid_mark_iv);

                vid_name_tv.setText(item.getDisplayName());
                vid_value_tv.setText(item.getValue());
                vid_mark_iv.setVisibility(item.isSelect() ? View.VISIBLE : View.INVISIBLE);

                convertView.setOnClickListener(v -> {
                    // 设置选中的环境
                    if (item.setModuleEnvironment(DevEnvironmentActivity.this)) {
                        notifyDataSetChanged();
                    }
                });
            }
            return convertView;
        }
    }
}