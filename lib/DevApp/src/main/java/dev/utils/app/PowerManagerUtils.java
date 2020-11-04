package dev.utils.app;

import android.app.Activity;
import android.os.Build;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

import dev.utils.LogPrintUtils;

/**
 * detail: 电源管理工具类
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.WAKE_LOCK" />
 * </pre>
 */
public final class PowerManagerUtils {

    // 日志 TAG
    private static final String TAG = PowerManagerUtils.class.getSimpleName();

    // PowerManagerUtils 实例
    private static volatile PowerManagerUtils sInstance;

    /**
     * 获取 PowerManagerUtils 实例
     * @return {@link PowerManagerUtils}
     */
    public static PowerManagerUtils getInstance() {
        if (sInstance == null) {
            synchronized (PowerManagerUtils.class) {
                if (sInstance == null) {
                    sInstance = new PowerManagerUtils();
                }
            }
        }
        return sInstance;
    }

    // 电源管理类
    private PowerManager          mPowerManager;
    // 电源管理锁
    private PowerManager.WakeLock mWakeLock;

    /**
     * 构造函数
     */
    private PowerManagerUtils() {
        try {
            // 获取系统服务
            mPowerManager = AppUtils.getPowerManager();
            // 电源管理锁
            mWakeLock = mPowerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.FULL_WAKE_LOCK, TAG);
        } catch (Exception e) {
        }
    }

    /**
     * 屏幕是否打开 ( 亮屏 )
     * @return {@code true} yes, {@code false} no
     */
    public boolean isScreenOn() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ECLAIR_MR1) {
            return false;
        } else {
            if (mPowerManager == null) {
                return false;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                return mPowerManager.isInteractive();
            }
            return mPowerManager.isScreenOn();
        }
    }

    /**
     * 唤醒 / 点亮 屏幕
     * @return {@code true} success, {@code false} fail
     */
    public boolean turnScreenOn() {
        if (mWakeLock != null && !mWakeLock.isHeld()) {
            try {
                mWakeLock.acquire();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "turnScreenOn");
            }
        }
        return false;
    }

    /**
     * 释放屏幕锁 ( 允许休眠时间自动黑屏 )
     * @return {@code true} success, {@code false} fail
     */
    public boolean turnScreenOff() {
        if (mWakeLock != null && mWakeLock.isHeld()) {
            try {
                mWakeLock.release();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "turnScreenOff");
            }
        }
        return false;
    }

    /**
     * 获取 PowerManager.WakeLock
     * @return {@link PowerManager.WakeLock}
     */
    public PowerManager.WakeLock getWakeLock() {
        return mWakeLock;
    }

    /**
     * 设置 PowerManager.WakeLock
     * @param wakeLock {@link PowerManager.WakeLock}
     * @return {@link PowerManagerUtils}
     */
    public PowerManagerUtils setWakeLock(final PowerManager.WakeLock wakeLock) {
        this.mWakeLock = wakeLock;
        return this;
    }

    /**
     * 获取 PowerManager
     * @return {@link PowerManager}
     */
    public PowerManager getPowerManager() {
        return mPowerManager;
    }

    /**
     * 设置 PowerManager
     * @param powerManager {@link PowerManager}
     * @return {@link PowerManagerUtils}
     */
    public PowerManagerUtils setPowerManager(final PowerManager powerManager) {
        this.mPowerManager = powerManager;
        return this;
    }

    /**
     * 设置屏幕常亮
     * @param activity {@link Activity}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setBright(final Activity activity) {
        if (activity != null) {
            return setBright(activity.getWindow());
        }
        return false;
    }

    /**
     * 设置屏幕常亮
     * @param window {@link Activity#getWindow()}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setBright(final Window window) {
        if (window != null) {
            try {
                window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBright");
            }
        }
        return false;
    }

    /**
     * 设置 WakeLock 常亮
     * <pre>
     *     {@link Activity#onResume()} 调用 setWakeLockToBright()
     *     {@link Activity#onPause()} 调用 mWakeLock.release()
     * </pre>
     * @return {@link PowerManager.WakeLock}
     */
    public static PowerManager.WakeLock setWakeLockToBright() {
        try {
            // onResume()
            PowerManager.WakeLock mWakeLock = PowerManagerUtils.getInstance().getPowerManager()
                    .newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, TAG);
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