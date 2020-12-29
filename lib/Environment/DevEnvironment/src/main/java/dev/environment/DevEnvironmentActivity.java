package dev.environment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import dev.environment.bean.EnvironmentBean;
import dev.environment.bean.ModuleBean;

/**
 * detail: DevEnvironment Activity
 * @author Ttt
 */
public final class DevEnvironmentActivity
        extends Activity {

    // ===========
    // = 跳转方法 =
    // ===========

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
     * @param context  {@link Context}
     * @param callback {@link RestartCallback}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean start(
            final Context context,
            final RestartCallback callback
    ) {
        return Utils.start(context, callback);
    }

    // =

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
                getWindow().setStatusBarColor(this.getResources().getColor(R.color.dev_environment_color));
            } catch (Exception e) {
            }
        }
        if (Utils.isRelease()) {
            finish();
            return;
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
        if (Utils.sCallback != null) {
            vid_dea_restart_tv.setVisibility(View.VISIBLE);
            vid_dea_restart_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    if (Utils.sCallback != null) {
                        Utils.sCallback.onRestart();
                    }
                }
            });
        }
        AdapterItem.refreshHashCode(this);
        // 初始化适配器并绑定
        ListView vid_dea_listview = findViewById(R.id.vid_dea_listview);
        vid_dea_listview.setAdapter(new Adapter(AdapterItem.getAdapterItems(this)));
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
            final AdapterItem item = getItem(position);
            switch (item.itemType) {
                case AdapterItem.MODULE_TYPE: // Module Type
                    convertView = LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.dev_environment_item_module, parent, false);
                    TextView vid_deim_name_tv = convertView.findViewById(R.id.vid_deim_name_tv);

                    ModuleBean moduleBean = item.moduleBean;
                    String moduleName = moduleBean.getName();
                    String moduleAlias = moduleBean.getAlias();
                    vid_deim_name_tv.setText(TextUtils.isEmpty(moduleAlias) ? moduleName : moduleAlias);
                    break;
                case AdapterItem.ENVIRONMENT_TYPE: // Environment Type
                    convertView = LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.dev_environment_item_environment, parent, false);
                    TextView vid_deie_name_tv = convertView.findViewById(R.id.vid_deie_name_tv);
                    TextView vid_deie_value_tv = convertView.findViewById(R.id.vid_deie_value_tv);
                    ImageView vid_deie_mark_igview = convertView.findViewById(R.id.vid_deie_mark_igview);

                    final EnvironmentBean environmentBean = item.environmentBean;
                    String environmentName = environmentBean.getName();
                    String environmentAlias = environmentBean.getAlias();
                    vid_deie_name_tv.setText(TextUtils.isEmpty(environmentAlias) ? environmentName : environmentAlias);
                    vid_deie_value_tv.setText(environmentBean.getValue());
                    vid_deie_mark_igview.setVisibility(item.isSelect() ? View.VISIBLE : View.INVISIBLE);

                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 设置选中的环境
                            if (Utils.setModuleEnvironment(DevEnvironmentActivity.this, environmentBean)) {
//                                AdapterItem.refreshHashCode(DevEnvironmentActivity.this);
                                AdapterItem.changeHashCode(environmentBean);
                                notifyDataSetChanged();
                            }
                        }
                    });
                    break;
            }
            return convertView;
        }
    }
}