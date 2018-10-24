package dev.utils.app;

import android.content.Context;
import android.os.Vibrator;

import dev.utils.LogPrintUtils;

/**
 * detail: 震动相关工具类
 * Created by Ttt
 */
public final class VibrationUtils {

    private VibrationUtils() {
    }

    // 日志TAG
    private static final String TAG = VibrationUtils.class.getSimpleName();

    /**
     * 震动
     * <uses-permission android:name="android.permission.VIBRATE" />
     * @param context
     * @param milliseconds 震动时长
     */
    public static void vibrate(final Context context, final long milliseconds) {
        try {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(milliseconds);
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "vibrate");
        }
    }

    /**
     * 指定手机以pattern模式震动
     * @param context
     * @param pattern new long[]{400,800,1200,1600}，就是指定在 400ms、800ms、1200ms、1600ms 这些时间点交替启动、关闭手机震动器
     * @param repeat  指定pattern数组的索引，指定pattern数组中从repeat索引开始的震动进行循环。-1表示只震动一次，非-1表示从 pattern的指定下标开始重复震动。
     */
    public static void vibrate(final Context context, final long[] pattern, final int repeat) {
        try {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(pattern, repeat);
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "vibrate");
        }
    }

    /**
     * 取消震动
     * @param context
     */
    public static void cancel(final Context context) {
        try {
            ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).cancel();
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "cancel");
        }
    }
}
