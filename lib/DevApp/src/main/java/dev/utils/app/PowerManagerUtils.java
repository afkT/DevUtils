package dev.utils.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.PowerManager;
import android.view.Window;

import dev.utils.LogPrintUtils;

/**
 * detail: 电源管理工具类
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.WAKE_LOCK"/>
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

    // 电源管理锁
    private PowerManager.WakeLock mWakeLock;

    /**
     * 构造函数
     */
    private PowerManagerUtils() {
        try {
            PowerManager powerManager = AppUtils.getPowerManager();
            // 电源管理锁
            mWakeLock = powerManager.newWakeLock(
                    PowerManager.ACQUIRE_CAUSES_WAKEUP
                            | PowerManager.FULL_WAKE_LOCK, TAG
            );
        } catch (Exception ignored) {
        }
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
     * 唤醒屏幕锁
     * @param wakeLock 设备唤醒对象
     * @return {@code true} success, {@code false} fail
     */
    @SuppressLint("WakelockTimeout")
    public boolean acquire(final PowerManager.WakeLock wakeLock) {
        if (wakeLock != null && !wakeLock.isHeld()) {
            try {
                wakeLock.acquire();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "acquire");
            }
        }
        return false;
    }

    /**
     * 释放屏幕锁 ( 允许休眠时间自动黑屏 )
     * @param wakeLock 设备唤醒对象
     * @return {@code true} success, {@code false} fail
     */
    @SuppressLint("WakelockTimeout")
    public boolean release(final PowerManager.WakeLock wakeLock) {
        if (wakeLock != null && wakeLock.isHeld()) {
            try {
                wakeLock.release();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "release");
            }
        }
        return false;
    }

    // =

    /**
     * 屏幕是否打开 ( 亮屏 )
     * @return {@code true} yes, {@code false} no
     */
    public boolean isScreenOn() {
        PowerManager powerManager = AppUtils.getPowerManager();
        if (powerManager == null) return false;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                return powerManager.isInteractive();
            }
            return powerManager.isScreenOn();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isScreenOn");
        }
        return false;
    }

    /**
     * 唤醒 / 点亮 屏幕
     * @return {@code true} success, {@code false} fail
     */
    @SuppressLint("WakelockTimeout")
    public boolean turnScreenOn() {
        return acquire(mWakeLock);
    }

    /**
     * 释放屏幕锁 ( 允许休眠时间自动黑屏 )
     * @return {@code true} success, {@code false} fail
     */
    public boolean turnScreenOff() {
        return release(mWakeLock);
    }

    // =

    /**
     * 设置屏幕常亮
     * @param activity {@link Activity}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setBright(final Activity activity) {
        return setBright(activity != null ? activity.getWindow() : null);
    }

    /**
     * 设置屏幕常亮
     * @param window {@link Window}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setBright(final Window window) {
        return WindowUtils.get().setFlagKeepScreenOn(window);
    }

    /**
     * 创建 WakeLock 常亮配置
     * <pre>
     *     提前创建 WakeLock {@link #createWakeLockToBright()}
     *     在 Activity#onResume() 中调用
     *     // 常亮, 持有不黑屏
     *     {@link PowerManagerUtils#getInstance()#acquire(PowerManager.WakeLock)}
     *     在 Activity#onPause() 中调用
     *     // 释放资源, 到休眠时间自动黑屏
     *     {@link PowerManagerUtils#getInstance()#release(PowerManager.WakeLock)}
     * </pre>
     * @return {@link PowerManager.WakeLock}
     */
    @SuppressLint("WakelockTimeout")
    public static PowerManager.WakeLock createWakeLockToBright() {
        try {
            return AppUtils.getPowerManager().newWakeLock(
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                            | PowerManager.ON_AFTER_RELEASE, TAG
            );
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createWakeLockToBright");
        }
        return null;
    }
}