package dev.utils.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import dev.utils.LogPrintUtils;

/**
 * detail: AlarmManager ( 全局定时器、闹钟 ) 工具类
 * @author Ttt
 * <pre>
 *     指定时长或以周期形式执行某项操作
 *     @see <a href="https://www.cnblogs.com/zyw-205520/p/4040923.html"/>
 *     关于使用 AlarmManager 的注意事项
 *     @see <a href="https://www.jianshu.com/p/d69a90bc44c0"/>
 * </pre>
 */
public final class AlarmUtils {

    private AlarmUtils() {
    }

    // 日志 TAG
    private static final String TAG = AlarmUtils.class.getSimpleName();

    // ===========
    // = 开启闹钟 =
    // ===========

    /**
     * 开启一次性闹钟
     * @param triggerAtMillis 执行时间
     * @param pendingIntent   {@link PendingIntent} 响应动作
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static boolean startAlarmIntent(
            final long triggerAtMillis,
            final PendingIntent pendingIntent
    ) {
        return startAlarmIntent(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
    }

    /**
     * 开启一次性闹钟
     * @param type            闹钟类型, 常用的有 5 个值:
     *                        AlarmManager.ELAPSED_REALTIME、
     *                        AlarmManager.ELAPSED_REALTIME_WAKEUP、
     *                        AlarmManager.RTC、
     *                        AlarmManager.RTC_WAKEUP、
     *                        AlarmManager.POWER_OFF_WAKEUP
     * @param triggerAtMillis 执行时间
     * @param pendingIntent   {@link PendingIntent} 响应动作
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static boolean startAlarmIntent(
            final int type,
            final long triggerAtMillis,
            final PendingIntent pendingIntent
    ) {
        try {
            AlarmManager manager = AppUtils.getAlarmManager();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                manager.setExactAndAllowWhileIdle(type, triggerAtMillis, pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                manager.setExact(type, triggerAtMillis, pendingIntent);
            } else {
                manager.set(type, triggerAtMillis, pendingIntent);
            }
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmIntent");
        }
        return false;
    }

    // ===========
    // = 关闭闹钟 =
    // ===========

    /**
     * 关闭闹钟
     * @param pendingIntent {@link PendingIntent} 响应动作
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static boolean stopAlarmIntent(final PendingIntent pendingIntent) {
        try {
            AppUtils.getAlarmManager().cancel(pendingIntent);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopAlarmIntent");
        }
        return false;
    }

    // ===============
    // = Service 闹钟 =
    // ===============

    /**
     * 开启 Service 闹钟
     * @param context         {@link Context}
     * @param triggerAtMillis 执行时间
     * @param clazz           Class
     * @param action          Intent Action
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static boolean startAlarmService(
            final Context context,
            final long triggerAtMillis,
            final Class<?> clazz,
            final String action
    ) {
        try {
            Intent intent = new Intent(context, clazz);
            intent.setAction(action);
            return startAlarmService(context, triggerAtMillis, intent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmService");
        }
        return false;
    }

    /**
     * 开启 Service 闹钟
     * @param context         {@link Context}
     * @param triggerAtMillis 执行时间
     * @param intent          {@link Intent
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static boolean startAlarmService(
            final Context context,
            final long triggerAtMillis,
            final Intent intent
    ) {
        try {
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            return startAlarmIntent(triggerAtMillis, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmService");
        }
        return false;
    }

    // =

    /**
     * 关闭 Service 闹钟
     * @param context {@link Context}
     * @param clazz   Class
     * @param action  Intent Action
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static boolean stopAlarmService(
            final Context context,
            final Class<?> clazz,
            final String action
    ) {
        try {
            Intent intent = new Intent(context, clazz);
            intent.setAction(action);
            return stopAlarmService(context, intent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopAlarmService");
        }
        return false;
    }

    /**
     * 关闭 Service 闹钟
     * @param context {@link Context}
     * @param intent  {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static boolean stopAlarmService(
            final Context context,
            final Intent intent
    ) {
        try {
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            return stopAlarmIntent(pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopAlarmService");
        }
        return false;
    }

    // =====================
    // = ForegroundService =
    // =====================

    /**
     * 开启 ForegroundService 闹钟
     * @param context         {@link Context}
     * @param triggerAtMillis 执行时间
     * @param clazz           Class
     * @param action          Intent Action
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean startAlarmForegroundService(
            final Context context,
            final long triggerAtMillis,
            final Class<?> clazz,
            final String action
    ) {
        try {
            Intent intent = new Intent(context, clazz);
            intent.setAction(action);
            return startAlarmForegroundService(context, triggerAtMillis, intent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmForegroundService");
        }
        return false;
    }

    /**
     * 开启 ForegroundService 闹钟
     * @param context         {@link Context}
     * @param triggerAtMillis 执行时间
     * @param intent          {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean startAlarmForegroundService(
            final Context context,
            final long triggerAtMillis,
            final Intent intent
    ) {
        try {
            PendingIntent pendingIntent = PendingIntent.getForegroundService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            return startAlarmIntent(triggerAtMillis, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmForegroundService");
        }
        return false;
    }

    // =

    /**
     * 关闭 ForegroundService 闹钟
     * @param context {@link Context}
     * @param clazz   Class
     * @param action  Intent Action
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean stopAlarmForegroundService(
            final Context context,
            final Class<?> clazz,
            final String action
    ) {
        try {
            Intent intent = new Intent(context, clazz);
            intent.setAction(action);
            return stopAlarmForegroundService(context, intent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopAlarmForegroundService");
        }
        return false;
    }

    /**
     * 关闭 ForegroundService 闹钟
     * @param context {@link Context}
     * @param intent  {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean stopAlarmForegroundService(
            final Context context,
            final Intent intent
    ) {
        try {
            PendingIntent pendingIntent = PendingIntent.getForegroundService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            return stopAlarmIntent(pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopAlarmForegroundService");
        }
        return false;
    }

    // ===========================
    // = Broadcast Receiver 闹钟 =
    // ===========================

    /**
     * 开启 Receiver 闹钟
     * @param context         {@link Context}
     * @param triggerAtMillis 执行时间
     * @param intent          {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static boolean startAlarmBroadcast(
            final Context context,
            final long triggerAtMillis,
            final Intent intent
    ) {
        try {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            return startAlarmIntent(triggerAtMillis, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmBroadcast");
        }
        return false;
    }

    // =

    /**
     * 关闭 Receiver 闹钟
     * @param context {@link Context}
     * @param intent  {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static boolean stopAlarmBroadcast(
            final Context context,
            final Intent intent
    ) {
        try {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            return stopAlarmIntent(pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopAlarmBroadcast");
        }
        return false;
    }

    // ================
    // = Activity 闹钟 =
    // ================

    /**
     * 开启 Activity 闹钟
     * @param context         {@link Context}
     * @param triggerAtMillis 执行时间
     * @param intent          {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static boolean startAlarmActivity(
            final Context context,
            final long triggerAtMillis,
            final Intent intent
    ) {
        try {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            return startAlarmIntent(triggerAtMillis, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmActivity");
        }
        return false;
    }

    /**
     * 关闭 Activity 闹钟
     * @param context {@link Context}
     * @param intent  {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static boolean stopAlarmActivity(
            final Context context,
            final Intent intent
    ) {
        try {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            return stopAlarmIntent(pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopAlarmActivity");
        }
        return false;
    }
}