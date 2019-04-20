package dev.utils.app;

import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.RequiresPermission;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 震动相关工具类
 * @author Ttt
 */
public final class VibrationUtils {

    private VibrationUtils() {
    }

    // 日志 TAG
    private static final String TAG = VibrationUtils.class.getSimpleName();

    /**
     * 震动
     * <uses-permission android:name="android.permission.VIBRATE" />
     * @param milliseconds 震动时长
     */
    @RequiresPermission(android.Manifest.permission.VIBRATE)
    public static void vibrate(final long milliseconds) {
        try {
            Vibrator vibrator = (Vibrator) DevUtils.getContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(milliseconds);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "vibrate");
        }
    }

    /**
     * 指定手机以pattern模式震动
     * <uses-permission android:name="android.permission.VIBRATE" />
     * @param pattern new long[]{400,800,1200,1600}，就是指定在 400ms、800ms、1200ms、1600ms 这些时间点交替启动、关闭手机震动器
     * @param repeat  指定pattern数组的索引，指定pattern数组中从repeat索引开始的震动进行循环。-1表示只震动一次，非-1表示从 pattern的指定下标开始重复震动。
     */
    @RequiresPermission(android.Manifest.permission.VIBRATE)
    public static void vibrate(final long[] pattern, final int repeat) {
        try {
            Vibrator vibrator = (Vibrator) DevUtils.getContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(pattern, repeat);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "vibrate");
        }
    }

    /**
     * 取消震动
     * <uses-permission android:name="android.permission.VIBRATE" />
     */
    @RequiresPermission(android.Manifest.permission.VIBRATE)
    public static void cancel() {
        try {
            ((Vibrator) DevUtils.getContext().getSystemService(Context.VIBRATOR_SERVICE)).cancel();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cancel");
        }
    }
}
