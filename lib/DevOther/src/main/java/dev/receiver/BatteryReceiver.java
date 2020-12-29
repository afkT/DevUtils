package dev.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;

/**
 * detail: 电量监听广播
 * @author Ttt
 */
public final class BatteryReceiver
        extends BroadcastReceiver {

    private BatteryReceiver() {
        super();
    }

    // 日志 TAG
    private static final String TAG = BatteryReceiver.class.getSimpleName();

    @Override
    public void onReceive(
            Context context,
            Intent intent
    ) {
        try {
            String action = intent.getAction();
            // 打印当前触发的广播
            LogPrintUtils.dTag(TAG, "onReceive Action: %s", action);
            // 获取当前电量, 范围是 0-100
            int level = intent.getIntExtra("level", 0);
            // 判断类型
            switch (action) {
                case Intent.ACTION_BATTERY_CHANGED: // 电量状态发送改变
                    if (sListener != null) {
                        sListener.onBatteryChanged(level);
                    }
                    break;
                case Intent.ACTION_BATTERY_LOW: // 电量低
                    if (sListener != null) {
                        sListener.onBatteryLow(level);
                    }
                    break;
                case Intent.ACTION_BATTERY_OKAY: // 电量从低变回高通知
                    if (sListener != null) {
                        sListener.onBatteryOkay(level);
                    }
                    break;
                case Intent.ACTION_POWER_CONNECTED: // 连接充电器
                    if (sListener != null) {
                        sListener.onPowerConnected(level, true);
                    }
                    break;
                case Intent.ACTION_POWER_DISCONNECTED: // 断开充电器
                    if (sListener != null) {
                        sListener.onPowerConnected(level, false);
                    }
                    break;
                case Intent.ACTION_POWER_USAGE_SUMMARY: // 电力使用情况总结
                    if (sListener != null) {
                        sListener.onPowerUsageSummary(level);
                    }
                    break;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "onReceive");
        }
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    // 电量监听广播
    private static final BatteryReceiver sReceiver = new BatteryReceiver();

    /**
     * 注册电量监听广播
     */
    public static void registerReceiver() {
        try {
            IntentFilter filter = new IntentFilter();
            // 电量状态发送改变
            filter.addAction(Intent.ACTION_BATTERY_CHANGED);
            // 电量低
            filter.addAction(Intent.ACTION_BATTERY_LOW);
            // 电量从低变回高
            filter.addAction(Intent.ACTION_BATTERY_OKAY);
            // 连接充电器
            filter.addAction(Intent.ACTION_POWER_CONNECTED);
            // 断开充电器
            filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
            // 电力使用情况总结
            filter.addAction(Intent.ACTION_POWER_USAGE_SUMMARY);
            // 注册广播
            AppUtils.registerReceiver(sReceiver, filter);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerReceiver");
        }
    }

    /**
     * 取消注册电量监听广播
     */
    public static void unregisterReceiver() {
        try {
            AppUtils.unregisterReceiver(sReceiver);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "unregisterReceiver");
        }
    }

    // =

    // 电量监听事件
    private static BatteryListener sListener;

    /**
     * 设置电量监听事件
     * @param listener {@link BatteryListener}
     * @return {@link BatteryReceiver}
     */
    public static BatteryReceiver setBatteryListener(final BatteryListener listener) {
        BatteryReceiver.sListener = listener;
        return sReceiver;
    }

    /**
     * detail: 电量监听事件
     * @author Ttt
     */
    public interface BatteryListener {

        /**
         * 电量改变通知
         * @param level 电量百分比
         */
        void onBatteryChanged(int level);

        /**
         * 电量低通知
         * @param level 电量百分比
         */
        void onBatteryLow(int level);

        /**
         * 电量从低变回高通知
         * @param level 电量百分比
         */
        void onBatteryOkay(int level);

        /**
         * 充电状态改变通知
         * @param level       电量百分比
         * @param isConnected 是否充电连接中
         */
        void onPowerConnected(
                int level,
                boolean isConnected
        );

        /**
         * 电力使用情况总结
         * @param level 电量百分比
         */
        void onPowerUsageSummary(int level);
    }
}