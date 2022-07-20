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
 * <pre>
 *     GitHub
 *     @see <a href="https://github.com/afkT/DevUtils"/>
 *     DevApp Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md"/>
 *     DevAssist Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md"/>
 *     DevBase README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md"/>
 *     DevBaseMVVM README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/README.md"/>
 *     DevEngine README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md"/>
 *     DevHttpCapture Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md"/>
 *     DevHttpManager Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md"/>
 *     DevRetrofit Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/README.md"/>
 *     DevJava Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md"/>
 *     DevWidget Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md"/>
 *     DevEnvironment Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/Environment"/>
 * </pre>
 */
public final class DevEnvironmentActivity
        extends Activity {

    // ============
    // = 工具类版本 =
    // ============

    /**
     * 获取 DevEnvironment 版本号
     * @return DevEnvironment versionCode
     */
    public static int getDevEnvironmentVersionCode() {
        return BuildConfig.DevEnvironment_VersionCode;
    }

    /**
     * 获取 DevEnvironment 版本
     * @return DevEnvironment versionName
     */
    public static String getDevEnvironmentVersion() {
        return BuildConfig.DevEnvironment_Version;
    }

    // ==========
    // = 跳转方法 =
    // ==========

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
     * @param callback 重启按钮点击回调
     * @return {@code true} success, {@code false} fail
     */
    public static boolean start(
            final Context context,
            final RestartCallback callback
    ) {
        return Utils.start(context, callback);
    }

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
                getWindow().setStatusBarColor(this.getResources().getColor(R.color.dev_environment_color));
            } catch (Exception ignored) {
            }
        }
        if (Utils.isRelease()) {
            finish();
            return;
        }
        setContentView(R.layout.dev_environment_activity);
        // back
        findViewById(R.id.vid_back_iv).setOnClickListener(v -> finish());
        // restart
        TextView vid_restart_tv = findViewById(R.id.vid_restart_tv);
        if (Utils.sCallback != null) {
            vid_restart_tv.setVisibility(View.VISIBLE);
            vid_restart_tv.setOnClickListener(v -> {
                finish();
                if (Utils.sCallback != null) {
                    Utils.sCallback.onRestart();
                }
            });
        }
        AdapterItem.refreshHashCode(this);
        // 初始化适配器并绑定
        ListView vid_lv = findViewById(R.id.vid_lv);
        vid_lv.setAdapter(new Adapter(AdapterItem.getAdapterItems(this)));
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
            switch (item.itemType) {
                case AdapterItem.MODULE_TYPE: // Module Type
                    convertView = LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.dev_environment_item_module, parent, false);
                    vid_name_tv = convertView.findViewById(R.id.vid_name_tv);

                    ModuleBean moduleBean = item.moduleBean;
                    String moduleName = moduleBean.getName();
                    String moduleAlias = moduleBean.getAlias();
                    vid_name_tv.setText(TextUtils.isEmpty(moduleAlias) ? moduleName : moduleAlias);
                    break;
                case AdapterItem.ENVIRONMENT_TYPE: // Environment Type
                    convertView = LayoutInflater.from(parent.getContext()).inflate(
                            R.layout.dev_environment_item_environment, parent, false);
                    vid_name_tv = convertView.findViewById(R.id.vid_name_tv);
                    vid_value_tv = convertView.findViewById(R.id.vid_value_tv);
                    vid_mark_iv = convertView.findViewById(R.id.vid_mark_iv);

                    final EnvironmentBean environmentBean = item.environmentBean;
                    String environmentName = environmentBean.getName();
                    String environmentAlias = environmentBean.getAlias();
                    vid_name_tv.setText(TextUtils.isEmpty(environmentAlias) ? environmentName : environmentAlias);
                    vid_value_tv.setText(environmentBean.getValue());
                    vid_mark_iv.setVisibility(item.isSelect() ? View.VISIBLE : View.INVISIBLE);

                    convertView.setOnClickListener(v -> {
                        // 设置选中的环境
                        if (Utils.setModuleEnvironment(DevEnvironmentActivity.this, environmentBean)) {
//                            AdapterItem.refreshHashCode(DevEnvironmentActivity.this);
                            AdapterItem.changeHashCode(environmentBean);
                            notifyDataSetChanged();
                        }
                    });
                    break;
            }
            return convertView;
        }
    }
}