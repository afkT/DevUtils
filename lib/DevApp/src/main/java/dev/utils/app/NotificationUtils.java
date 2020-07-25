package dev.utils.app;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.DrawableRes;
import androidx.core.app.NotificationManagerCompat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 通知栏管理工具类
 * @author Ttt
 * <pre>
 *     @see <a href="https://www.jianshu.com/p/cf5f6c30019d"/>
 * </pre>
 */
public final class NotificationUtils {

    private NotificationUtils() {
    }

    // 日志 TAG
    private static final String TAG = NotificationUtils.class.getSimpleName();

    // 通知栏管理类
    private static NotificationManager sNotificationManager = null;

    /**
     * 获取通知栏管理对象
     * @return {@link NotificationManager}
     */
    public static NotificationManager getNotificationManager() {
        if (sNotificationManager == null) {
            try {
                sNotificationManager = AppUtils.getNotificationManager();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getNotificationManager");
            }
        }
        return sNotificationManager;
    }

    /**
     * 检查通知栏权限是否开启
     * 参考 SupportCompat 包中的: NotificationManagerCompat.from(context).areNotificationsEnabled();
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotificationEnabled() {
        Context context = DevUtils.getContext();
        if (context != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return AppUtils.getNotificationManager().areNotificationsEnabled();
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    AppOpsManager appOps = AppUtils.getAppOpsManager();
                    ApplicationInfo appInfo = context.getApplicationInfo();
                    String pkg = context.getApplicationContext().getPackageName();
                    int uid = appInfo.uid;

                    Class<?> appOpsClass = Class.forName(AppOpsManager.class.getName());
                    Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
                    Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
                    int value = (Integer) opPostNotificationValue.get(Integer.class);
                    return (Integer) checkOpNoThrowMethod.invoke(appOps, value, uid, pkg) == 0;
                } catch (Throwable ignore) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否有获取通知栏信息权限并跳转设置页面
     * @return {@code true} yes, {@code false} no
     */
    public static boolean checkAndIntentSetting() {
        if (!isNotificationListenerEnabled()) {
            startNotificationListenSettings();
            return false;
        }
        return true;
    }

    /**
     * 判断是否有获取通知栏信息权限
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotificationListenerEnabled() {
        return isNotificationListenerEnabled(AppUtils.getPackageName());
    }

    /**
     * 判断是否有获取通知栏信息权限
     * @param packageName 应用包名
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotificationListenerEnabled(final String packageName) {
        Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages(DevUtils.getContext());
        return packageNames.contains(packageName);
    }

    /**
     * 跳转到设置页面, 开启获取通知栏信息权限
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startNotificationListenSettings() {
        return AppUtils.startActivity(IntentUtils.getLaunchAppNotificationListenSettingsIntent());
    }

    // =

    /**
     * 移除通知 - 移除所有通知
     * <pre>
     *      只是针对当前 Context 下的所有 Notification
     * </pre>
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cancelAll() {
        if (getNotificationManager() != null) {
            try {
                sNotificationManager.cancelAll();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "cancelAll");
            }
        }
        return false;
    }

    /**
     * 移除通知 - 移除标记为 id 的通知
     * <pre>
     *      只是针对当前 Context 下的所有 Notification
     * </pre>
     * @param args 消息 id 集合
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cancel(final int... args) {
        if (getNotificationManager() != null && args != null) {
            for (int id : args) {
                try {
                    sNotificationManager.cancel(id);
                    return true;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "cancel - id: " + id);
                }
            }
        }
        return false;
    }

    /**
     * 移除通知 - 移除标记为 id 的通知
     * <pre>
     *      只是针对当前 Context 下的所有 Notification
     * </pre>
     * @param tag 标记 TAG
     * @param id  消息 id
     * @return {@code true} success, {@code false} fail
     */
    public static boolean cancel(final String tag, final int id) {
        if (getNotificationManager() != null && tag != null) {
            try {
                sNotificationManager.cancel(tag, id);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "cancel - id: " + id + ", tag: " + tag);
            }
        }
        return false;
    }

    /**
     * 进行通知
     * @param id           消息 id
     * @param notification {@link Notification}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean notify(final int id, final Notification notification) {
        if (getNotificationManager() != null && notification != null) {
            try {
                sNotificationManager.notify(id, notification);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "notify - id: " + id);
            }
        }
        return false;
    }

    /**
     * 进行通知
     * @param tag          标记 TAG
     * @param id           消息 id
     * @param notification {@link Notification}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean notify(final String tag, final int id, final Notification notification) {
        if (getNotificationManager() != null && tag != null && notification != null) {
            try {
                sNotificationManager.notify(tag, id, notification);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "notify - id: " + id + ", tag: " + tag);
            }
        }
        return false;
    }

    // ================
    // = 封装外部调用 =
    // ================

//    // 使用自定义 View
//    RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.xxx);
//    // 设置 View
//    Notification.builder.setContent(remoteViews);

    /**
     * 获取 PendingIntent
     * @param intent      {@link Intent}
     * @param requestCode 请求 code
     * @return {@link PendingIntent}
     */
    public static PendingIntent createPendingIntent(final Intent intent, final int requestCode) {
        try {
            return PendingIntent.getActivity(DevUtils.getContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createPendingIntent");
        }
        return null;
    }

    /**
     * 创建通知栏对象
     * @param icon  图标
     * @param title 通知栏标题
     * @param msg   通知栏内容
     * @return {@link Notification}
     */
    public static Notification createNotification(@DrawableRes final int icon, final String title, final String msg) {
        return createNotification(null, icon, title, title, msg, true, VibratePattern.obtain(0, 100, 300),
                LightPattern.obtain(Color.WHITE, 1000, 1000));
    }

    /**
     * 创建通知栏对象
     * @param icon           图标
     * @param title          通知栏标题
     * @param msg            通知栏内容
     * @param vibratePattern 震动频率
     * @param lightPattern   Led 闪灯参数
     * @return {@link Notification}
     */
    public static Notification createNotification(@DrawableRes final int icon, final String title, final String msg,
                                                  final VibratePattern vibratePattern, final LightPattern lightPattern) {
        return createNotification(null, icon, title, title, msg, true, vibratePattern, lightPattern);
    }

    /**
     * 创建通知栏对象
     * @param pendingIntent  {@link PendingIntent}
     * @param icon           图标
     * @param ticker         状态栏通知文案
     * @param title          通知栏标题
     * @param msg            通知栏内容
     * @param isAutoCancel   点击是否自动关闭
     * @param vibratePattern 震动频率
     * @param lightPattern   Led 闪灯参数
     * @return {@link Notification}
     */
    public static Notification createNotification(final PendingIntent pendingIntent, @DrawableRes final int icon, final String ticker,
                                                  final String title, final String msg, final boolean isAutoCancel,
                                                  final VibratePattern vibratePattern, final LightPattern lightPattern) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Notification.Builder builder = new Notification.Builder(DevUtils.getContext());
            // 点击通知执行 intent
            builder.setContentIntent(pendingIntent);
            // 设置图标
            builder.setSmallIcon(icon);
            // 设置图标
            builder.setLargeIcon(BitmapFactory.decodeResource(ResourceUtils.getResources(), icon));
            // 指定通知的 ticker 内容, 通知被创建的时候, 在状态栏一闪而过, 属于瞬时提示信息
            builder.setTicker(ticker);
            // 设置标题
            builder.setContentTitle(title);
            // 设置内容
            builder.setContentText(msg);
            // 设置消息提醒, 震动 | 声音
            builder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);
            // 将 AutoCancel 设为 true 后, 当你点击通知栏的 notification 后, 它会自动被取消消失
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
//            notification.largeIcon = BitmapFactory.decodeResource(ResourceUtils.getResources(), icon);
//            // 指定通知的 ticker 内容, 通知被创建的时候, 在状态栏一闪而过, 属于瞬时提示信息
//            notification.tickerText = title;
//            // 设置时间
//            notification.when = System.currentTimeMillis();
//            // 设置消息提醒, 震动 | 声音
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
//                    notification.ledARGB = lightPattern.argb; // 控制 LED 灯的颜色, 一般有红绿蓝三种颜色可选
//                    notification.ledOffMS = lightPattern.startOffMS; // 指定 LED 灯暗去的时长, 也是以毫秒为单位
//                    notification.ledOnMS = lightPattern.durationMS; // 指定 LED 灯亮起的时长, 以毫秒为单位
//                    notification.flags = Notification.FLAG_SHOW_LIGHTS;
//                } catch (Exception e) {
//                }
//            }
//            // 设置标题内容等 - 已经移除, 现在都是支持 4.0 以上, 不需要兼容处理
//            notification.setLatestEventInfo(DevUtils.getContext(), title, msg, pendingIntent);
//            return notification;
        }
        return null;
    }

    /**
     * detail: 设置通知栏 Led 灯参数实体类
     * @author Ttt
     * <pre>
     *     手机处于锁屏状态时, LED 灯就会不停地闪烁, 提醒用户去查看手机, 下面是绿色的灯光一闪一闪的效果
     * </pre>
     */
    public static class LightPattern {

        private int argb       = 0; // 控制 LED 灯的颜色, 一般有红绿蓝三种颜色可选
        private int startOffMS = 0; // 指定 LED 灯暗去的时长, 以毫秒为单位
        private int durationMS = 0; // 指定 LED 灯亮起的时长, 以毫秒为单位

        /**
         * 构造函数
         * @param argb       颜色值
         * @param startOffMS 开始时间
         * @param durationMS 持续时间
         */
        private LightPattern(int argb, int startOffMS, int durationMS) {
            this.argb = argb;
            this.startOffMS = startOffMS;
            this.durationMS = durationMS;
        }

        /**
         * 获取 Led 配置参数
         * @param argb       颜色值
         * @param startOffMS 开始时间
         * @param durationMS 持续时间
         * @return {@link LightPattern}
         */
        public static LightPattern obtain(final int argb, final int startOffMS, final int durationMS) {
            return new LightPattern(argb, startOffMS, durationMS);
        }
    }

    /**
     * detail: 设置通知栏震动参数实体类
     * @author Ttt
     * <pre>
     *     vibrate 属性是一个长整型的数组, 用于设置手机静止和震动的时长, 以毫秒为单位
     *     参数中下标为 0 的值表示手机静止的时长, 下标为 1 的值表示手机震动的时长, 下标为 2 的值又表示手机静止的时长, 以此类推
     * </pre>
     */
    public static class VibratePattern {

        // long[] vibrates = { 0, 1000, 1000, 1000 };
        private long[] vibrates = null;

        /**
         * 构造函数
         * @param vibrates 震动时间数组
         */
        private VibratePattern(long[] vibrates) {
            this.vibrates = vibrates;
        }

        /**
         * 判断是否为 null
         * @return {@code true} yes, {@code false} no
         */
        public boolean isEmpty() {
            return (vibrates == null || vibrates.length == 0);
        }

        /**
         * 获取 震动时间 配置参数
         * @param times 震动时间数组
         * @return {@link VibratePattern}
         */
        public static VibratePattern obtain(final long... times) {
            return new VibratePattern(times);
        }
    }
}