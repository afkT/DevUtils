package dev.utils.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;

import dev.utils.LogPrintUtils;

/**
 * detail: 轮询工具类
 * @author Ttt
 */
public final class PollingUtils {

    private PollingUtils() {
    }

    // 日志 TAG
    private static final String TAG = PollingUtils.class.getSimpleName();

    /**
     * 开启轮询
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static void startPolling(final Context context, final int mills, final PendingIntent pendingIntent) {
        try {
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), mills, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startPolling");
        }
    }

    /**
     * 停止轮询
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static void stopPolling(final Context context, final PendingIntent pendingIntent) {
        try {
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            manager.cancel(pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopPolling");
        }
    }

    /**
     * 开启轮询服务
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static void startPollingService(final Context context, final int mills, final Class<?> clazz, final String action) {
        try {
            Intent intent = new Intent(context, clazz);
            intent.setAction(action);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            startPolling(context, mills, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startPollingService");
        }
    }

    /**
     * 停止轮询服务
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static void stopPollingService(final Context context, final Class<?> clazz, final String action) {
        try {
            Intent intent = new Intent(context, clazz);
            intent.setAction(action);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            stopPolling(context, pendingIntent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopPollingService");
        }
    }
}
