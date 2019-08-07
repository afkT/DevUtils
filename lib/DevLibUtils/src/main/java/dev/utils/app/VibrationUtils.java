package dev.utils.app;

import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.RequiresPermission;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 震动相关工具类
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.VIBRATE" />
 * </pre>
 */
public final class VibrationUtils {

    private VibrationUtils() {
    }

    // 日志 TAG
    private static final String TAG = VibrationUtils.class.getSimpleName();

    /**
     * 震动
     * @param milliseconds 震动时长 ( 毫秒 )
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
     * pattern 模式震动
     * @param pattern new long[]{400, 800, 1200, 1600}, 就是指定在 400ms、800ms、1200ms、1600ms 这些时间点交替启动、关闭手机震动器
     * @param repeat  指定 pattern 数组的索引, 指定 pattern 数组中从 repeat 索引开始的震动进行循环,
     *                -1 表示只震动一次, 非 -1 表示从 pattern 数组指定下标开始重复震动
     */
    @RequiresPermission(android.Manifest.permission.VIBRATE)
    public static void vibrate(final long[] pattern, final int repeat) {
        if (pattern == null) return;
        try {
            Vibrator vibrator = (Vibrator) DevUtils.getContext().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(pattern, repeat);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "vibrate");
        }
    }

    /**
     * 取消震动
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