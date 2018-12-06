package dev.utils.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * detail: AlarmManager (全局定时器/闹钟)指定时长或以周期形式执行某项操作
 * Created by MaTianyu
 * Update to Ttt
 * https://www.cnblogs.com/zyw-205520/p/4040923.html
 */
public final class AlarmUtils {

    private AlarmUtils(){
    }

    /**
     * 开启定时器
     * @param context
     * @param triggerAtMillis 执行时间
     * @param pendingIntent 响应动作
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static void startAlarmIntent(Context context, long triggerAtMillis, PendingIntent pendingIntent) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        }
    }

    /**
     * 关闭定时器
     * @param context
     * @param pendingIntent
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static void stopAlarmIntent(Context context, PendingIntent pendingIntent) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

    /**
     * 开启轮询服务
     * @param context
     * @param triggerAtMillis
     * @param cls
     * @param action
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static void startAlarmService(Context context, long triggerAtMillis, Class<?> cls, String action) {
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        startAlarmIntent(context, triggerAtMillis, pendingIntent);
    }

    /**
     * 停止轮询服务
     * @param context
     * @param cls
     * @param action
     */
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    public static void stopAlarmService(Context context, Class<?> cls, String action) {
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        stopAlarmIntent(context, pendingIntent);
    }

}
