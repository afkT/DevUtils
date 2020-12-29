package dev.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;

/**
 * detail: 屏幕监听广播 ( 锁屏 / 解锁 / 亮屏 )
 * @author Ttt
 */
public final class ScreenReceiver
        extends BroadcastReceiver {

    private ScreenReceiver() {
        super();
    }

    // 日志 TAG
    private static final String TAG = ScreenReceiver.class.getSimpleName();

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
                case Intent.ACTION_SCREEN_ON: // 开屏
                    if (sListener != null) {
                        sListener.screenOn();
                    }
                    break;
                case Intent.ACTION_SCREEN_OFF: // 锁屏
                    if (sListener != null) {
                        sListener.screenOff();
                    }
                    break;
                case Intent.ACTION_USER_PRESENT: // 解锁
                    if (sListener != null) {
                        sListener.userPresent();
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

    // 屏幕广播监听
    private static final ScreenReceiver sReceiver = new ScreenReceiver();

    /**
     * 注册屏幕监听广播
     */
    public static void registerReceiver() {
        try {
            IntentFilter filter = new IntentFilter();
            // 屏幕状态改变通知
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_USER_PRESENT);
            filter.setPriority(Integer.MAX_VALUE);
            // 注册广播
            AppUtils.registerReceiver(sReceiver, filter);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerReceiver");
        }
    }

    /**
     * 取消注册屏幕监听广播
     */
    public static void unregisterReceiver() {
        try {
            AppUtils.unregisterReceiver(sReceiver);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "unregisterReceiver");
        }
    }

    // =

    // 屏幕监听事件
    private static ScreenListener sListener;

    /**
     * 设置屏幕监听事件
     * @param listener {@link ScreenListener}
     * @return {@link ScreenReceiver}
     */
    public static ScreenReceiver setScreenListener(final ScreenListener listener) {
        ScreenReceiver.sListener = listener;
        return sReceiver;
    }

    /**
     * detail: 屏幕监听事件
     * @author Ttt
     */
    public interface ScreenListener {

        /**
         * 用户打开屏幕 ( 屏幕变亮 )
         */
        void screenOn();

        /**
         * 锁屏触发
         */
        void screenOff();

        /**
         * 用户解锁触发
         */
        void userPresent();
    }
}