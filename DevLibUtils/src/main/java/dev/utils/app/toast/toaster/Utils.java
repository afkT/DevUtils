package dev.utils.app.toast.toaster;

import android.app.Activity;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Toast 内部工具类
 * @author Ttt
 */
final class Utils {

    private Utils() {
    }

    // 日志 TAG
    private static final String TAG = Utils.class.getSimpleName();

    // =

    /**
     * 获取格式化字符串
     * @param format
     * @param args
     * @return
     */
    public static String getFormatString(final String format, final Object... args) {
        try {
            if (args != null && args.length != 0) {
                return String.format(format, args);
            } else {
                return format;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getFormatString");
        }
        return null;
    }

    /**
     * 获取 R.string 资源的格式化字符串
     * @param resId
     * @param objs
     */
    public static String getFormatRes(final int resId, final Object... objs) {
        try {
            // 获取字符串并且进行格式化
            if (objs != null && objs.length != 0) {
                return DevUtils.getContext().getString(resId, objs);
            } else {
                return DevUtils.getContext().getString(resId);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getFormatRes");
        }
        return null;
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     * @param str 待校验字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSpace(final String str) {
        if (str == null) return true;
        for (int i = 0, len = str.length(); i < len; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查通知栏权限有没有开启
     * 参考SupportCompat包中的: NotificationManagerCompat.from(context).areNotificationsEnabled();
     * @param context
     * @return
     */
    public static boolean isNotificationEnabled(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).areNotificationsEnabled();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            ApplicationInfo appInfo = context.getApplicationInfo();
            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;

            try {
                Class<?> appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
                int value = (Integer) opPostNotificationValue.get(Integer.class);
                return (Integer) checkOpNoThrowMethod.invoke(appOps, value, uid, pkg) == 0;
            } catch (NoSuchMethodException | NoSuchFieldException | InvocationTargetException | IllegalAccessException | RuntimeException | ClassNotFoundException ignored) {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * 获取一个对象的独一无二的标记
     * @param object
     * @return
     */
    public static String getObjectTag(final Object object) {
        if (object == null) return null;
        // 对象所在的包名 + 对象的内存地址
        return object.getClass().getName() + Integer.toHexString(object.hashCode());
    }

    /**
     * 获取一个WindowManager对象
     * @param activity
     * @return
     */
    public static WindowManager getWindowManager(final Activity activity) {
        // 如果使用的 WindowManager 对象不是当前 Activity 创建的，则会抛出异常
        // android.view.WindowManager$BadTokenException: Unable to add window -- token null is not for an application
        if (activity != null) {
            try {
                return ((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE));
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getWindowManager");
            }
        }
        return null;
    }

    // =

    /**
     * 设置背景
     * @param view
     * @param drawable
     */
    public static void setBackground(final @NonNull View view, final Drawable drawable) {
        if (view != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                view.setBackground(drawable);
            else
                view.setBackgroundDrawable(drawable);
        }
    }

    /**
     * 获取 Drawable
     * @param context
     * @param id
     * @return
     */
    public static Drawable getDrawable(final Context context, final @DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return context.getDrawable(id);
        else
            return context.getResources().getDrawable(id);
    }

    /**
     * 图片着色
     * @param drawable
     * @param tintColor
     * @return
     */
    public static Drawable tintIcon(final @NonNull Drawable drawable, final @ColorInt int tintColor) {
        if (drawable != null) {
            try {
                drawable.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "tintIcon");
            }
        }
        return drawable;
    }

    /**
     * .9 图片着色
     * @param context
     * @param tintColor
     * @return
     */
    public static Drawable tint9PatchDrawableFrame(final Context context, final @ColorInt int tintColor) {
        try {
            final NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(context, dev.utils.R.drawable.dev_toast_frame);
            return tintIcon(toastDrawable, tintColor);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "tint9PatchDrawableFrame");
        }
        return null;
    }
}
