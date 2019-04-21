package dev.utils.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 通知栏管理类
 * @author Ttt
 * <pre>
 *      @see <a href="https://blog.csdn.net/hss01248/article/details/55096553"/>
 *      @see <a href="https://www.jianshu.com/p/cf5f6c30019d"/>
 * </pre>
 */
public final class NotificationUtils {

    private NotificationUtils() {
    }

    // 日志 TAG
    private static final String TAG = NotificationUtils.class.getSimpleName();
    // 通知栏管理类
    private static NotificationManager mNotificationManager = null;

    /**
     * 获取通知栏管理类
     * @return
     */
    public static NotificationManager getNotificationManager() {
        if (mNotificationManager == null) {
            try {
                mNotificationManager = (NotificationManager) DevUtils.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            } catch (Exception e) {
            }
        }
        return mNotificationManager;
    }

    /**
     * 移除通知 - 移除所有通知(只是针对当前Context下的Notification)
     */
    public static void cancelAll() {
        if (getNotificationManager() != null) {
            mNotificationManager.cancelAll();
        }
    }

    /**
     * 移除通知 - 移除标记为id的通知 (只是针对当前Context下的所有Notification)
     * @param args
     */
    public static void cancel(final int... args) {
        if (getNotificationManager() != null && args != null) {
            for (int id : args) {
                mNotificationManager.cancel(id);
            }
        }
    }

    /**
     * 移除通知 - 移除标记为id的通知 (只是针对当前Context下的所有Notification)
     * @param tag
     * @param id
     */
    public static void cancel(final String tag, final int id) {
        if (getNotificationManager() != null && tag != null) {
            mNotificationManager.cancel(tag, id);
        }
    }

    /**
     * 进行通知
     * @param id
     * @param notification
     * @return
     */
    public static boolean notify(final int id, final Notification notification) {
        if (getNotificationManager() != null && notification != null) {
            mNotificationManager.notify(id, notification);
            return true;
        }
        return false;
    }

    /**
     * 进行通知
     * @param tag
     * @param id
     * @param notification
     * @return
     */
    public static boolean notify(final String tag, final int id, final Notification notification) {
        if (getNotificationManager() != null && tag != null && notification != null) {
            mNotificationManager.notify(tag, id, notification);
            return true;
        }
        return false;
    }

    // ================
    // = 封装外部调用 =
    // ================

//    // 使用自定义View
//    RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.xxx);
//    // 设置View
//    Notification.builder.setContent(remoteViews);

    /**
     * 获取跳转id
     * @param intent
     * @param id
     * @return
     */
    public static PendingIntent createPendingIntent(final Intent intent, final int id) {
        try {
            // 跳转Intent
            PendingIntent pIntent = PendingIntent.getActivity(DevUtils.getContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            return pIntent;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createPendingIntent");
        }
        return null;
    }

    /**
     * 创建通知栏对象
     * @param icon
     * @param title
     * @param msg
     * @return
     */
    public static Notification createNotification(final int icon, final String title, final String msg) {
        return createNotification(null, icon, title, title, msg, true, VibratePattern.obtain(0, 100, 300), LightPattern.obtain(Color.WHITE, 1000, 1000));
    }

    /**
     * 创建通知栏对象
     * @param icon
     * @param title
     * @param msg
     * @param vibratePattern
     * @param lightPattern
     * @return
     */
    public static Notification createNotification(final int icon, final String title, final String msg, final VibratePattern vibratePattern, final LightPattern lightPattern) {
        return createNotification(null, icon, title, title, msg, true, vibratePattern, lightPattern);
    }

    /**
     * 创建通知栏对象
     * @param pendingIntent
     * @param icon
     * @param ticker
     * @param title
     * @param msg
     * @param isAutoCancel
     * @param vibratePattern
     * @param lightPattern
     * @return
     */
    public static Notification createNotification(final PendingIntent pendingIntent, final int icon, final String ticker, final String title, final String msg, final boolean isAutoCancel,
                                                  final VibratePattern vibratePattern, final LightPattern lightPattern) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Notification.Builder builder = new Notification.Builder(DevUtils.getContext());
            // 点击通知执行intent
            builder.setContentIntent(pendingIntent);
            // 设置图标
            builder.setSmallIcon(icon);
            // 设置图标
            builder.setLargeIcon(BitmapFactory.decodeResource(DevUtils.getContext().getResources(), icon));
            // 指定通知的ticker内容，通知被创建的时候，在状态栏一闪而过，属于瞬时提示信息。
            builder.setTicker(ticker);
            // 设置标题
            builder.setContentTitle(title);
            // 设置内容
            builder.setContentText(msg);
            // 设置消息提醒，震动 | 声音
            builder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);
            // 将AutoCancel设为true后，当你点击通知栏的notification后，它会自动被取消消失
            builder.setAutoCancel(isAutoCancel);
            // 设置时间
            builder.setWhen(System.currentTimeMillis());
            // 设置震动参数
            if (vibratePattern != null && !vibratePattern.isEmpty()) {
                builder.setVibrate(vibratePattern.vibrates);
            }
            // 设置 led 灯参数
            if (lightPattern != null) {
                builder.setLights(lightPattern.argb, lightPattern.durationMS, lightPattern.startOffMS);
            }
            // = 初始化 Notification 对象 =
            Notification baseNF;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                baseNF = builder.getNotification();
            } else {
                baseNF = builder.build();
            }
            return baseNF;
        } else {
//            // = 初始化通知信息实体类 =
//            Notification notification = new Notification();
//            // 设置图标
//            notification.icon = icon;
//            // 设置图标
//            notification.largeIcon = BitmapFactory.decodeResource(DevUtils.getContext().getResources(), icon);
//            // 指定通知的ticker内容，通知被创建的时候，在状态栏一闪而过，属于瞬时提示信息。
//            notification.tickerText = title;
//            // 设置时间
//            notification.when = System.currentTimeMillis();
//            // 设置消息提醒，震动 | 声音
//            notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;
//            // 点击了此通知则取消该通知栏
//            if (isAutoCancel) {
//                notification.flags |= Notification.FLAG_AUTO_CANCEL;
//            }
//            // 设置震动参数
//            if (vibratePattern != null && !vibratePattern.isEmpty()) {
//                notification.vibrate = vibratePattern.vibrates;
//            }
//            // 设置 led 灯参数
//            if (lightPattern != null) {
//                try {
//                    notification.ledARGB = lightPattern.argb; // 控制 LED 灯的颜色，一般有红绿蓝三种颜色可选
//                    notification.ledOffMS = lightPattern.startOffMS; // 指定 LED 灯暗去的时长，也是以毫秒为单位
//                    notification.ledOnMS = lightPattern.durationMS; // 指定 LED 灯亮起的时长，以毫秒为单位
//                    notification.flags = Notification.FLAG_SHOW_LIGHTS;
//                } catch (Exception e) {
//                }
//            }
//            // 设置标题内容等 - 已经移除, 现在都是支持 4.0以上, 不需要兼容处理
//            notification.setLatestEventInfo(DevUtils.getContext(), title, msg, pendingIntent);
//            return notification;
        }
        return null;
    }

    /**
     * detail: 设置通知栏 Led 灯参数实体类
     * @author Ttt
     * <pre>
     *      手机处于锁屏状态时， LED灯就会不停地闪烁， 提醒用户去查看手机,下面是绿色的灯光一 闪一闪的效果
     * </pre>
     */
    public static class LightPattern {

        private int argb = 0; // 控制 LED 灯的颜色，一般有红绿蓝三种颜色可选
        private int startOffMS = 0; // 指定 LED 灯暗去的时长，也是以毫秒为单位
        private int durationMS = 0; // 指定 LED 灯亮起的时长，以毫秒为单位

        private LightPattern(int argb, int startOffMS, int durationMS) {
            this.argb = argb;
            this.startOffMS = startOffMS;
            this.durationMS = durationMS;
        }

        /**
         * 获取 Led 配置参数
         * @param argb       颜色
         * @param startOffMS 开始时间
         * @param durationMS 持续时间
         * @return
         */
        public static LightPattern obtain(final int argb, final int startOffMS, final int durationMS) {
            return new LightPattern(argb, startOffMS, durationMS);
        }
    }

    /**
     * detail: 设置通知栏 震动参数实体类
     * @author Ttt
     * <pre>
     *      vibrate 属性是一个长整型的数组，用于设置手机静止和震动的时长，以毫秒为单位。
     *      参数中下标为0的值表示手机静止的时长，下标为1的值表示手机震动的时长， 下标为2的值又表示手机静止的时长，以此类推。
     * </pre>
     */
    public static class VibratePattern {

        // long[] vibrates = { 0, 1000, 1000, 1000 };
        private long[] vibrates = null;

        private VibratePattern(long[] vibrates) {
            this.vibrates = vibrates;
        }

        /**
         * 判断是否为 null
         * @return {@code true} is null, {@code false} not null
         */
        public boolean isEmpty() {
            if (vibrates != null) {
                if (vibrates.length != 0) {
                    return false;
                }
            }
            return true;
        }

        /**
         * 获取 震动时间 配置参数
         * @param args
         * @return
         */
        public static VibratePattern obtain(final long... args) {
            return new VibratePattern(args);
        }
    }

}
