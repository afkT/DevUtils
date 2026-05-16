package dev.utils.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.concurrent.Executor;

import dev.utils.LogPrintUtils;

/**
 * detail: AlarmManager ( 全局定时器、闹钟 ) 工具类
 * @author Ttt
 * <pre>
 *     API 31+ 精确闹钟受 {@link android.app.AlarmManager#canScheduleExactAlarms()} 约束，
 *     请使用 {@link #canScheduleExactAlarms()}、{@link #startScheduleExactAlarmSettings()} 引导用户授权；
 *     {@link PendingIntent} 请统一经 {@link PendingIntentUtils} 创建。
 *     Android 17+ 周期性精确回调可使用 {@link #startExactAlarmListener(int, long, String, Executor, AlarmManager.OnAlarmListener)}，
 *     相对 PendingIntent 方案可减少 partial wakelock。
 *     @see <a href="https://developer.android.com/about/versions/17/features">Android 17 Features</a>
 * </pre>
 */
public final class AlarmUtils {

    private AlarmUtils() {
    }

    // 日志 TAG
    private static final String TAG = AlarmUtils.class.getSimpleName();

    // ==========
    // = 开启闹钟 =
    // ==========

    /**
     * 开启一次性闹钟
     * @param triggerAtMillis 执行时间
     * @param pendingIntent   {@link PendingIntent} 响应动作
     * @return {@code true} success, {@code false} fail
     */
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
    public static boolean startAlarmIntent(
            final int type,
            final long triggerAtMillis,
            final PendingIntent pendingIntent
    ) {
        if (pendingIntent == null) {
            return false;
        }
        try {
            AlarmManager alarmManager = AppUtils.getAlarmManager();
            if (alarmManager == null) return false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                    && !alarmManager.canScheduleExactAlarms()) {
                LogPrintUtils.wTag(
                        TAG, "canScheduleExactAlarms() == false; exact alarm may throw SecurityException, use startScheduleExactAlarmSettings()"
                );
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(type, triggerAtMillis, pendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(type, triggerAtMillis, pendingIntent);
            } else {
                alarmManager.set(type, triggerAtMillis, pendingIntent);
            }
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmIntent");
        }
        return false;
    }

    // ====================================
    // = 精确闹钟 OnAlarmListener (API 37+) =
    // ====================================

    /**
     * 安排一次性精确闹钟（Doze / 低电下仍尽量触发），回调 OnAlarmListener
     * <pre>
     *     需 API 37+；回调类型为 {@link AlarmManager.OnAlarmListener}。
     *     {@code executor} 为 null 时使用主线程 {@link HandlerUtils#getMainHandler()}。
     *     {@code tag} 用于系统区分闹钟，取消时需使用同一 {@link AlarmManager.OnAlarmListener} 实例。
     * </pre>
     * @param type            {@link AlarmManager#RTC_WAKEUP} 等
     * @param triggerAtMillis 触发时间 ( 毫秒 )
     * @param tag             闹钟标识，可为 null（系统可能替换为默认）
     * @param executor        回调线程；null 表示主线程
     * @param listener        {@link AlarmManager.OnAlarmListener}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CINNAMON_BUN)
    public static boolean startExactAlarmListener(
            final int type,
            final long triggerAtMillis,
            @Nullable final String tag,
            @Nullable final Executor executor,
            @NonNull final AlarmManager.OnAlarmListener listener
    ) {
        if (listener == null) {
            return false;
        }
        try {
            AlarmManager alarmManager = AppUtils.getAlarmManager();
            if (alarmManager == null) {
                return false;
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.CINNAMON_BUN) {
                LogPrintUtils.wTag(TAG, "startExactAlarmListener requires API 37+");
                return false;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                    && !alarmManager.canScheduleExactAlarms()) {
                LogPrintUtils.wTag(
                        TAG, "canScheduleExactAlarms() == false; use startScheduleExactAlarmSettings()"
                );
            }
            Executor callbackExecutor = executor != null
                    ? executor
                    : HandlerUtils.getMainHandler()::post;
            alarmManager.setExactAndAllowWhileIdle(
                    type, triggerAtMillis, tag, callbackExecutor, listener
            );
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startExactAlarmListener");
        }
        return false;
    }

    /**
     * 安排一次性精确闹钟（主线程回调）
     * <pre>
     *     闹钟类型为 {@link AlarmManager#RTC_WAKEUP}。
     * </pre>
     * @param triggerAtMillis 触发时间
     * @param tag             闹钟标识
     * @param listener        {@link AlarmManager.OnAlarmListener}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CINNAMON_BUN)
    public static boolean startExactAlarmListener(
            final long triggerAtMillis,
            @Nullable final String tag,
            @NonNull final AlarmManager.OnAlarmListener listener
    ) {
        return startExactAlarmListener(
                AlarmManager.RTC_WAKEUP, triggerAtMillis, tag, null, listener
        );
    }

    /**
     * 取消 OnAlarmListener 精确闹钟
     * <pre>
     *     需 API 37+；{@link AlarmManager.OnAlarmListener} 须与
     *     {@link #startExactAlarmListener(int, long, String, Executor, AlarmManager.OnAlarmListener)}
     *     传入的同一实例。
     * </pre>
     * @param listener 与注册时传入的同一 {@link AlarmManager.OnAlarmListener} 实例
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.CINNAMON_BUN)
    public static boolean cancelExactAlarmListener(
            @NonNull final AlarmManager.OnAlarmListener listener
    ) {
        if (listener == null) {
            return false;
        }
        try {
            AlarmManager alarmManager = AppUtils.getAlarmManager();
            if (alarmManager == null) {
                return false;
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.CINNAMON_BUN) {
                return false;
            }
            alarmManager.cancel(listener);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cancelExactAlarmListener");
        }
        return false;
    }

    // =========================
    // = 精确闹钟 (API 31+) 适配 =
    // =========================

    /**
     * 当前应用是否允许使用精确闹钟（API 31 以下恒为 true）
     * @return {@code true} 可安排精确闹钟；{@code false} 可能被系统拒绝或需用户授权
     */
    public static boolean canScheduleExactAlarms() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            return true;
        }
        try {
            AlarmManager alarmManager = AppUtils.getAlarmManager();
            return alarmManager != null && alarmManager.canScheduleExactAlarms();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "canScheduleExactAlarms");
            return false;
        }
    }

    /**
     * 跳转系统「闹钟和提醒」精确闹钟授权页（API 31+）
     * <pre>
     *     低版本无需授权，直接返回 {@code true}
     * </pre>
     * @return {@code true} 已发起跳转或无需跳转, {@code false} 跳转 Intent 不可用或异常
     */
    public static boolean startScheduleExactAlarmSettings() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            return true;
        }
        Intent intent = IntentUtils.getRequestScheduleExactAlarmSettingsIntent(true);
        if (intent == null) return false;
        return AppUtils.startActivity(intent);
    }

    // ==========
    // = 关闭闹钟 =
    // ==========

    /**
     * 关闭闹钟
     * @param pendingIntent {@link PendingIntent} 响应动作
     * @return {@code true} success, {@code false} fail
     */
    public static boolean stopAlarmIntent(final PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            return false;
        }
        try {
            final AlarmManager alarmManager = AppUtils.getAlarmManager();
            if (alarmManager == null) {
                return false;
            }
            alarmManager.cancel(pendingIntent);
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
     * @param requestCode     请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startAlarmService(
            final Context context,
            final long triggerAtMillis,
            final Class<?> clazz,
            final String action,
            final int requestCode
    ) {
        try {
            Intent intent = new Intent(context, clazz);
            intent.setAction(action);
            return startAlarmService(context, triggerAtMillis, intent, requestCode);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmService");
        }
        return false;
    }

    /**
     * 开启 Service 闹钟
     * @param context         {@link Context}
     * @param triggerAtMillis 执行时间
     * @param intent          {@link Intent}
     * @param requestCode     请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startAlarmService(
            final Context context,
            final long triggerAtMillis,
            final Intent intent,
            final int requestCode
    ) {
        try {
            PendingIntent pendingIntent = PendingIntentUtils.getService(
                    context, requestCode, intent
            );
            return startAlarmIntent(triggerAtMillis, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmService");
        }
        return false;
    }

    // =

    /**
     * 关闭 Service 闹钟
     * @param context     {@link Context}
     * @param clazz       Class
     * @param action      Intent Action
     * @param requestCode 请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean stopAlarmService(
            final Context context,
            final Class<?> clazz,
            final String action,
            final int requestCode
    ) {
        try {
            Intent intent = new Intent(context, clazz);
            intent.setAction(action);
            return stopAlarmService(context, intent, requestCode);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopAlarmService");
        }
        return false;
    }

    /**
     * 关闭 Service 闹钟
     * @param context     {@link Context}
     * @param intent      {@link Intent}
     * @param requestCode 请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean stopAlarmService(
            final Context context,
            final Intent intent,
            final int requestCode
    ) {
        try {
            PendingIntent pendingIntent = PendingIntentUtils.getService(
                    context, requestCode, intent
            );
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
     * @param requestCode     请求 code
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean startAlarmForegroundService(
            final Context context,
            final long triggerAtMillis,
            final Class<?> clazz,
            final String action,
            final int requestCode
    ) {
        try {
            Intent intent = new Intent(context, clazz);
            intent.setAction(action);
            return startAlarmForegroundService(
                    context, triggerAtMillis, intent, requestCode
            );
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
     * @param requestCode     请求 code
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean startAlarmForegroundService(
            final Context context,
            final long triggerAtMillis,
            final Intent intent,
            final int requestCode
    ) {
        try {
            PendingIntent pendingIntent = PendingIntentUtils.getForegroundService(
                    context, requestCode, intent
            );
            return startAlarmIntent(triggerAtMillis, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmForegroundService");
        }
        return false;
    }

    // =

    /**
     * 关闭 ForegroundService 闹钟
     * @param context     {@link Context}
     * @param clazz       Class
     * @param action      Intent Action
     * @param requestCode 请求 code
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean stopAlarmForegroundService(
            final Context context,
            final Class<?> clazz,
            final String action,
            final int requestCode
    ) {
        try {
            Intent intent = new Intent(context, clazz);
            intent.setAction(action);
            return stopAlarmForegroundService(context, intent, requestCode);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopAlarmForegroundService");
        }
        return false;
    }

    /**
     * 关闭 ForegroundService 闹钟
     * @param context     {@link Context}
     * @param intent      {@link Intent}
     * @param requestCode 请求 code
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean stopAlarmForegroundService(
            final Context context,
            final Intent intent,
            final int requestCode
    ) {
        try {
            PendingIntent pendingIntent = PendingIntentUtils.getForegroundService(
                    context, requestCode, intent
            );
            return stopAlarmIntent(pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopAlarmForegroundService");
        }
        return false;
    }

    // ==========================
    // = Broadcast Receiver 闹钟 =
    // ==========================

    /**
     * 开启 Receiver 闹钟
     * @param context         {@link Context}
     * @param triggerAtMillis 执行时间
     * @param intent          {@link Intent}
     * @param requestCode     请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startAlarmBroadcast(
            final Context context,
            final long triggerAtMillis,
            final Intent intent,
            final int requestCode
    ) {
        try {
            PendingIntent pendingIntent = PendingIntentUtils.getBroadcast(
                    context, requestCode, intent
            );
            return startAlarmIntent(triggerAtMillis, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmBroadcast");
        }
        return false;
    }

    // =

    /**
     * 关闭 Receiver 闹钟
     * @param context     {@link Context}
     * @param intent      {@link Intent}
     * @param requestCode 请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean stopAlarmBroadcast(
            final Context context,
            final Intent intent,
            final int requestCode
    ) {
        try {
            PendingIntent pendingIntent = PendingIntentUtils.getBroadcast(
                    context, requestCode, intent
            );
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
     * @param requestCode     请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startAlarmActivity(
            final Context context,
            final long triggerAtMillis,
            final Intent intent,
            final int requestCode
    ) {
        try {
            PendingIntent pendingIntent = PendingIntentUtils.getActivity(
                    context, requestCode, intent
            );
            return startAlarmIntent(triggerAtMillis, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startAlarmActivity");
        }
        return false;
    }

    /**
     * 关闭 Activity 闹钟
     * @param context     {@link Context}
     * @param intent      {@link Intent}
     * @param requestCode 请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean stopAlarmActivity(
            final Context context,
            final Intent intent,
            final int requestCode
    ) {
        try {
            PendingIntent pendingIntent = PendingIntentUtils.getActivity(
                    context, requestCode, intent
            );
            return stopAlarmIntent(pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopAlarmActivity");
        }
        return false;
    }
}