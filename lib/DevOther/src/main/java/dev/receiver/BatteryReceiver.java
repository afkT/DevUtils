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
public final class BatteryReceiver extends BroadcastReceiver {

    private BatteryReceiver() {
        super();
    }

    // 日志 TAG
    private static final String TAG = BatteryReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String action = intent.getAction();
            // 打印当前触发的广播
            LogPrintUtils.dTag(TAG, "onReceive Action: " + action);
            // 判断类型
            switch (action) {
                case Intent.ACTION_TIMEZONE_CHANGED: // 时区改变
                    if (batteryListener != null) {
                        batteryListener.onTimeZoneChanged();
                    }
                    break;
                case Intent.ACTION_TIME_CHANGED: // 设置时间
                    if (batteryListener != null) {
                        batteryListener.onTimeChanged();
                    }
                    break;
                case Intent.ACTION_TIME_TICK: // 每分钟调用
                    if (batteryListener != null) {
                        batteryListener.onTimeTick();
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

    // 电量监听广播
    private static final BatteryReceiver batteryReceiver = new BatteryReceiver();

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
            AppUtils.registerReceiver(batteryReceiver, filter);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerReceiver");
        }
    }

    /**
     * 取消注册电量监听广播
     */
    public static void unregisterReceiver() {
        try {
            AppUtils.unregisterReceiver(batteryReceiver);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "unregisterReceiver");
        }
    }

    // =

    // 电量监听事件
    private static BatteryListener batteryListener;

    /**
     * 设置电量监听事件
     * @param listener {@link BatteryListener}
     * @return {@link BatteryReceiver}
     */
    public static BatteryReceiver setBatteryListener(final BatteryListener listener) {
        BatteryReceiver.batteryListener = listener;
        return batteryReceiver;
    }

    /**
     * detail: 电量监听事件
     * @author Ttt
     */
    public interface BatteryListener {

        /**
         * 时区改变
         */
        void onTimeZoneChanged();

        /**
         * 设置电量
         */
        void onTimeChanged();

        /**
         * 每分钟调用
         */
        void onTimeTick();
    }
}
