package dev.utils.app;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;

/**
 * detail: 轮询工具类
 * @author MaTianyu
 */
public final class PollingUtils {

    private PollingUtils(){
    }

    /**
     * 开启轮询
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static void startPolling(Context context, int mills, PendingIntent pendingIntent) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), mills, pendingIntent);
    }

    /**
     * 停止轮询
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static void stopPolling(Context context, PendingIntent pendingIntent) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

    /**
     * 开启轮询服务
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static void startPollingService(Context context, int mills, Class<?> cls, String action) {
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        startPolling(context, mills, pendingIntent);
    }

    /**
     * 停止启轮询服务
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static void stopPollingService(Context context, Class<?> cls, String action) {
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        stopPolling(context, pendingIntent);
    }

}
