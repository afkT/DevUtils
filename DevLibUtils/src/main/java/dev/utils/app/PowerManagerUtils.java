package dev.utils.app;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 电源管理工具类
 * @author Ttt
 * <pre>
 *      需要的权限:
 *      <uses-permission android:name="android.permission.WAKE_LOCK"/>
 * </pre>
 */
public final class PowerManagerUtils {

    // 日志 TAG
    private static final String TAG = PowerManagerUtils.class.getSimpleName();
    // PowerManagerUtils 实例
    private static PowerManagerUtils INSTANCE;

    /**
     * 获取 PowerManagerUtils 实例 ,单例模式
     */
    public static PowerManagerUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PowerManagerUtils();
        }
        return INSTANCE;
    }

    /**
     * 电源管理类
     */
    PowerManager powerManager;
    // 电源管理锁
    PowerManager.WakeLock wakeLock;

    /**
     * 构造函数
     */
    private PowerManagerUtils() {
        try {
            // 获取系统服务
            powerManager = (PowerManager) DevUtils.getContext().getSystemService(Context.POWER_SERVICE);
            // 电源管理锁
            wakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, "PowerManagerUtils");
        } catch (Exception e) {
        }
    }

    /**
     * 屏幕是否打开(亮屏)
     * @return
     */
    public boolean isScreenOn() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ECLAIR_MR1) {
            return false;
        } else {
            if (powerManager == null) {
                return false;
            }
            return powerManager.isScreenOn();
        }
    }

    /**
     * 唤醒屏幕/点亮亮屏
     */
    public void turnScreenOn() {
        if (wakeLock != null && !wakeLock.isHeld()) {
            wakeLock.acquire();
        }
    }

    /**
     * 释放屏幕锁, 允许休眠时间自动黑屏
     */
    public void turnScreenOff() {
        if (wakeLock != null && wakeLock.isHeld()) {
            try {
                wakeLock.release();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "turnScreenOff");
            }
        }
    }

    /**
     * 获取 PowerManager.WakeLock
     * @return
     */
    public PowerManager.WakeLock getWakeLock() {
        return wakeLock;
    }

    /**
     * 设置 PowerManager.WakeLock
     * @param wakeLock
     */
    public void setWakeLock(final PowerManager.WakeLock wakeLock) {
        this.wakeLock = wakeLock;
    }

    /**
     * 获取 PowerManager
     * @return
     */
    public PowerManager getPowerManager() {
        return powerManager;
    }

    /**
     * 设置 PowerManager
     * @param powerManager
     */
    public void setPowerManager(final PowerManager powerManager) {
        this.powerManager = powerManager;
    }

    /**
     * 设置屏幕常亮
     * @param activity
     */
    public static void setBright(final Activity activity) {
        if (activity != null) {
            setBright(activity.getWindow());
        }
    }

    /**
     * 设置屏幕常亮
     * @param window {@link Activity#getWindow()}
     */
    public static void setBright(final Window window) {
        if (window != null) {
            window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    /**
     * 设置WakeLock 常亮
     * run: {@link Activity#onResume()}
     * @return
     */
    public static PowerManager.WakeLock setWakeLockToBright() {
        try {
            // onResume()
            PowerManager.WakeLock mWakeLock = PowerManagerUtils.getInstance().getPowerManager().newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "setWakeLockToBright");
            mWakeLock.acquire(); // 常量, 持有不黑屏

//        // onPause()
//        if (mWakeLock != null) {
//            mWakeLock.release(); // 释放资源, 到休眠时间自动黑屏
//        }
            return mWakeLock;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setWakeLockToBright");
        }
        return null;
    }
}
