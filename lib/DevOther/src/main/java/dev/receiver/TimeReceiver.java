package dev.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;

/**
 * detail: 时间监听广播
 * @author Ttt
 */
public final class TimeReceiver
        extends BroadcastReceiver {

    private TimeReceiver() {
        super();
    }

    // 日志 TAG
    private static final String TAG = TimeReceiver.class.getSimpleName();

    @Override
    public void onReceive(
            Context context,
            Intent intent
    ) {
        try {
            String action = intent.getAction();
            // 打印当前触发的广播
            LogPrintUtils.dTag(TAG, "onReceive Action: %s", action);
            // 判断类型
            switch (action) {
                case Intent.ACTION_TIMEZONE_CHANGED: // 时区改变
                    if (sListener != null) {
                        sListener.onTimeZoneChanged();
                    }
                    break;
                case Intent.ACTION_TIME_CHANGED: // 设置时间
                    if (sListener != null) {
                        sListener.onTimeChanged();
                    }
                    break;
                case Intent.ACTION_TIME_TICK: // 每分钟调用
                    if (sListener != null) {
                        sListener.onTimeTick();
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

    // 时间监听广播
    private static final TimeReceiver sReceiver = new TimeReceiver();

    /**
     * 注册时间监听广播
     */
    public static void registerReceiver() {
        try {
            IntentFilter filter = new IntentFilter();
            // 监听时间、时区改变通知
            filter.addAction(Intent.ACTION_TIME_TICK);
            filter.addAction(Intent.ACTION_TIME_CHANGED);
            filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
            filter.setPriority(Integer.MAX_VALUE);
            // 注册广播
            AppUtils.registerReceiver(sReceiver, filter);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerReceiver");
        }
    }

    /**
     * 取消注册时间监听广播
     */
    public static void unregisterReceiver() {
        try {
            AppUtils.unregisterReceiver(sReceiver);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "unregisterReceiver");
        }
    }

    // =

    // 时间监听事件
    private static TimeListener sListener;

    /**
     * 设置时间监听事件
     * @param listener {@link TimeListener}
     * @return {@link TimeReceiver}
     */
    public static TimeReceiver setTimeListener(final TimeListener listener) {
        TimeReceiver.sListener = listener;
        return sReceiver;
    }

    /**
     * detail: 时间监听事件
     * @author Ttt
     */
    public interface TimeListener {

        /**
         * 时区改变
         */
        void onTimeZoneChanged();

        /**
         * 设置时间
         */
        void onTimeChanged();

        /**
         * 每分钟调用
         */
        void onTimeTick();
    }
}