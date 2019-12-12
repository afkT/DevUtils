package dev.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;

/**
 * detail: 应用状态监听广播 ( 安装、更新、卸载 )
 * @author Ttt
 */
public final class AppStatusReceiver extends BroadcastReceiver {

    private AppStatusReceiver() {
        super();
    }

    // 日志 TAG
    private static final String TAG = AppStatusReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String action = intent.getAction();
            // 打印当前触发的广播
            LogPrintUtils.dTag(TAG, "onReceive Action: " + action);
            // 被操作应用包名
            String packageName = null;
            Uri uri = intent.getData();
            if (uri != null) {
//                packageName = uri.toString();
                packageName = uri.getEncodedSchemeSpecificPart();
            }
            // 判断类型
            switch (action) {
                case Intent.ACTION_PACKAGE_ADDED: // 应用安装
                    if (appStatusListener != null) {
                        appStatusListener.onAdded(packageName);
                    }
                    break;
                case Intent.ACTION_PACKAGE_REPLACED: // 应用更新
                    if (appStatusListener != null) {
                        appStatusListener.onReplaced(packageName);
                    }
                    break;
                case Intent.ACTION_PACKAGE_REMOVED: // 应用卸载
                    if (appStatusListener != null) {
                        appStatusListener.onRemoved(packageName);
                    }
                    break;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "onReceive");
        }
    }

    // ================
    // = 对外公开方法 =
    // ================

    // 应用状态监听广播
    private static final AppStatusReceiver appStatusReceiver = new AppStatusReceiver();

    /**
     * 注册应用状态监听广播
     */
    public static void registerReceiver() {
        try {
            IntentFilter filter = new IntentFilter();
            // 安装
            filter.addAction(Intent.ACTION_PACKAGE_ADDED);
            // 更新
            filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
            // 卸载
            filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
            filter.addDataScheme("package");
            // 注册广播
            AppUtils.registerReceiver(appStatusReceiver, filter);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerReceiver");
        }
    }

    /**
     * 取消注册应用状态监听广播
     */
    public static void unregisterReceiver() {
        try {
            AppUtils.unregisterReceiver(appStatusReceiver);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "unregisterReceiver");
        }
    }

    // =

    // 应用状态监听事件
    private static AppStatusListener appStatusListener;

    /**
     * 设置应用状态监听事件
     * @param listener {@link AppStatusListener}
     * @return {@link AppStatusReceiver}
     */
    public static AppStatusReceiver setAppStatusListener(final AppStatusListener listener) {
        AppStatusReceiver.appStatusListener = listener;
        return appStatusReceiver;
    }

    /**
     * detail: 应用状态监听事件
     * @author Ttt
     */
    public interface AppStatusListener {

        /**
         * 应用安装
         * @param packageName 应用包名
         */
        void onAdded(String packageName);

        /**
         * 应用更新
         * @param packageName 应用包名
         */
        void onReplaced(String packageName);

        /**
         * 应用卸载
         * @param packageName 应用包名
         */
        void onRemoved(String packageName);
    }
}
