package dev.utils.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import dev.utils.LogPrintUtils;

/**
 * detail: AlarmManager (全局定时器/闹钟)指定时长或以周期形式执行某项操作
 * @author Ttt
 * <pre>
 *      @see <a href="https://www.cnblogs.com/zyw-205520/p/4040923.html"/>
 * </pre>
 */
public final class AlarmUtils {

    private AlarmUtils() {
    }

    // 日志 TAG
    private static final String TAG = AlarmUtils.class.getSimpleName();

    /**
     * 开启定时器
     * @param context
     * @param triggerAtMillis 执行时间
     * @param pendingIntent   响应动作
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static void startAlarmIntent(final Context context, final long triggerAtMillis, final PendingIntent pendingIntent) {
        try {
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                manager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
            } else {
                manager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmIntent");
        }
    }

    /**
     * 关闭定时器
     * @param context
     * @param pendingIntent
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static void stopAlarmIntent(final Context context, final PendingIntent pendingIntent) {
        try {
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            manager.cancel(pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopAlarmIntent");
        }
    }

    /**
     * 开启轮询服务
     * @param context
     * @param triggerAtMillis
     * @param clazz
     * @param action
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static void startAlarmService(final Context context, final long triggerAtMillis, final Class<?> clazz, final String action) {
        try {
            Intent intent = new Intent(context, clazz);
            intent.setAction(action);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            startAlarmIntent(context, triggerAtMillis, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmService");
        }
    }

    /**
     * 停止轮询服务
     * @param context
     * @param clazz
     * @param action
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static void stopAlarmService(final Context context, final Class<?> clazz, final String action) {
        try {
            Intent intent = new Intent(context, clazz);
            intent.setAction(action);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            stopAlarmIntent(context, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopAlarmService");
        }
    }

    /**
     * 开启轮询广播
     * @param context
     * @param triggerAtMillis
     * @param intent
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static void startAlarmBroadcast(final Context context, final long triggerAtMillis, final Intent intent) {
        try {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            startAlarmIntent(context, triggerAtMillis, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmBroadcast");
        }
    }

    /**
     * 停止轮询广播
     * @param context
     * @param intent
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static void stopAlarmBroadcast(final Context context, final Intent intent) {
        try {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            stopAlarmIntent(context, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopAlarmBroadcast");
        }
    }

    /**
     * 开启轮询 Activity
     * @param context
     * @param triggerAtMillis
     * @param intent
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static void startAlarmActivity(final Context context, final long triggerAtMillis, final Intent intent) {
        try {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            startAlarmIntent(context, triggerAtMillis, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmActivity");
        }
    }

    /**
     * 停止轮询 Activity
     * @param context
     * @param intent
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static void stopAlarmActivity(final Context context, final Intent intent) {
        try {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            stopAlarmIntent(context, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopAlarmActivity");
        }
    }
}
