package dev.utils.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import java.util.List;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.assist.ActivityManagerAssist;

/**
 * detail: Activity 工具类 ( 包含 Activity 控制管理 )
 * @author Ttt
 * <pre>
 *     转场动画
 *     @see <a href="https://www.cnblogs.com/tianzhijiexian/p/4087917.html"/>
 *     @see <a href="https://mp.weixin.qq.com/s/1Bp7ApxstPaRF9BY7AEe6g"/>
 *     ActivityOptionsCompat.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight)
 *     @see <a href="https://www.jianshu.com/p/fa1c8deeaa57"/>
 * </pre>
 */
public final class ActivityUtils {

    private ActivityUtils() {
    }

    // 日志 TAG
    private static final String TAG = ActivityUtils.class.getSimpleName();

    // ===================
    // = Activity 判断处理 =
    // ===================

    /**
     * 通过 Context 获取 Activity
     * @param context {@link Context}
     * @return {@link Activity}
     */
    public static Activity getActivity(final Context context) {
        if (context != null) {
            try {
                return (Activity) context;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getActivity");
            }
        }
        return null;
    }

    /**
     * 获取 View context 所属的 Activity
     * @param view {@link View}
     * @return {@link Activity}
     */
    public static Activity getActivity(final View view) {
        if (view != null) {
            try {
                Context context = view.getContext();
                while (context instanceof ContextWrapper) {
                    if (context instanceof Activity) {
                        return (Activity) context;
                    }
                    context = ((ContextWrapper) context).getBaseContext();
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getActivity");
            }
        }
        return null;
    }

    // =

    /**
     * 判断 Activity 是否关闭
     * @param activity {@link Activity}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFinishing(final Activity activity) {
        if (activity != null) {
            return activity.isFinishing();
        }
        return true;
    }

    /**
     * 判断 Activity 是否关闭
     * @param context {@link Context}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFinishing(final Context context) {
        if (context != null) {
            try {
                return isFinishing((Activity) context);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isFinishing");
            }
        }
        return true;
    }

    /**
     * 判断 Activity 是否未关闭
     * @param activity {@link Activity}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotFinishing(final Activity activity) {
        if (activity != null) {
            return !activity.isFinishing();
        }
        return false;
    }

    /**
     * 判断 Activity 是否未关闭
     * @param context {@link Context}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotFinishing(final Context context) {
        if (context != null) {
            try {
                return isNotFinishing((Activity) context);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isNotFinishing");
            }
        }
        return false;
    }

    /**
     * 判断 Activity 是否销毁
     * @param activity {@link Activity}
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isDestroyed(final Activity activity) {
        if (activity != null) {
            return activity.isDestroyed();
        }
        return true;
    }

    /**
     * 判断 Activity 是否销毁
     * @param context {@link Context}
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isDestroyed(final Context context) {
        if (context != null) {
            try {
                return isDestroyed((Activity) context);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isDestroyed");
            }
        }
        return true;
    }

    /**
     * 判断 Activity 是否未销毁
     * @param activity {@link Activity}
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isNotDestroyed(final Activity activity) {
        if (activity != null) {
            return !activity.isDestroyed();
        }
        return false;
    }

    /**
     * 判断 Activity 是否未销毁
     * @param context {@link Context}
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isNotDestroyed(final Context context) {
        if (context != null) {
            try {
                return isNotDestroyed((Activity) context);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isNotDestroyed");
            }
        }
        return false;
    }

    // =

    /**
     * 判断 Activity 是否有效
     * @param activity {@link Activity}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean assertValidActivity(final Activity activity) {
        if (activity != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return !activity.isFinishing() && !activity.isDestroyed();
            } else {
                return !activity.isFinishing();
            }
        }
        return false;
    }

    /**
     * 判断 Activity 是否有效
     * @param context {@link Context}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean assertValidActivity(final Context context) {
        if (context != null) {
            try {
                return assertValidActivity((Activity) context);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "assertValidActivity");
            }
        }
        return false;
    }

    // =

    /**
     * 判断是否存在指定的 Activity
     * @param className Activity.class.getCanonicalName()
     * @return {@code true} 存在, {@code false} 不存在
     */
    public static boolean isActivityExists(final String className) {
        return isActivityExists(AppUtils.getPackageName(), className);
    }

    /**
     * 判断是否存在指定的 Activity
     * @param packageName 应用包名
     * @param className   Activity.class.getCanonicalName()
     * @return {@code true} 存在, {@code false} 不存在
     */
    public static boolean isActivityExists(
            final String packageName,
            final String className
    ) {
        if (packageName == null || className == null) return false;
        PackageManager packageManager = AppUtils.getPackageManager();
        if (packageManager == null) return false;
        boolean result = true;
        try {
            Intent intent = new Intent();
            intent.setClassName(packageName, className);
            if (packageManager.resolveActivity(intent, 0) == null) {
                result = false;
            } else if (intent.resolveActivity(packageManager) == null) {
                result = false;
            } else {
                List<ResolveInfo> lists = packageManager.queryIntentActivities(
                        intent, 0
                );
                if (lists.size() == 0) {
                    result = false;
                }
            }
        } catch (Exception e) {
            result = false;
            LogPrintUtils.eTag(TAG, e, "isActivityExists");
        }
        return result;
    }

    // ===================
    // = Activity 获取操作 =
    // ===================

    /**
     * 回到桌面 ( 同点击 Home 键效果 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startHomeActivity() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            return AppUtils.startActivity(intent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startHomeActivity");
        }
        return false;
    }

    /**
     * 获取 Launcher activity
     * @return package.xx.Activity.className
     */
    public static String getLauncherActivity() {
        try {
            return getLauncherActivity(AppUtils.getPackageName());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLauncherActivity");
        }
        return null;
    }

    /**
     * 获取 Launcher activity
     * @param packageName 应用包名
     * @return package.xx.Activity.className
     */
    public static String getLauncherActivity(final String packageName) {
        if (packageName == null) return null;
        PackageManager packageManager = AppUtils.getPackageManager();
        if (packageManager == null) return null;
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            List<ResolveInfo> lists = packageManager.queryIntentActivities(intent, 0);
            for (ResolveInfo resolveInfo : lists) {
                if (resolveInfo != null && resolveInfo.activityInfo != null) {
                    if (packageName.equals(resolveInfo.activityInfo.packageName)) {
                        return resolveInfo.activityInfo.name;
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLauncherActivity");
        }
        return null;
    }

    /**
     * 获取 Activity 对应的 icon
     * @param clazz Activity.class
     * @return {@link Drawable} Activity 对应的 icon
     */
    public static Drawable getActivityIcon(final Class<?> clazz) {
        if (clazz == null) return null;
        try {
            return getActivityIcon(new ComponentName(DevUtils.getContext(), clazz));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getActivityIcon");
        }
        return null;
    }

    /**
     * 获取 Activity 对应的 icon
     * @param componentName {@link ComponentName}
     * @return {@link Drawable} Activity 对应的 icon
     */
    public static Drawable getActivityIcon(final ComponentName componentName) {
        if (componentName == null) return null;
        PackageManager packageManager = AppUtils.getPackageManager();
        if (packageManager == null) return null;
        try {
            return packageManager.getActivityIcon(componentName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getActivityIcon");
        }
        return null;
    }

    /**
     * 获取 Activity 对应的 logo
     * @param clazz Activity.class
     * @return {@link Drawable} Activity 对应的 logo
     */
    public static Drawable getActivityLogo(final Class<?> clazz) {
        if (clazz == null) return null;
        try {
            return getActivityLogo(new ComponentName(DevUtils.getContext(), clazz));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getActivityLogo");
        }
        return null;
    }

    /**
     * 获取 Activity 对应的 logo
     * @param componentName {@link ComponentName}
     * @return {@link Drawable} Activity 对应的 logo
     */
    public static Drawable getActivityLogo(final ComponentName componentName) {
        if (componentName == null) return null;
        PackageManager packageManager = AppUtils.getPackageManager();
        if (packageManager == null) return null;
        try {
            return packageManager.getActivityLogo(componentName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getActivityLogo");
        }
        return null;
    }

    /**
     * 获取对应包名应用启动的 Activity
     * @return package.xx.Activity.className
     */
    public static String getActivityToLauncher() {
        return getActivityToLauncher(AppUtils.getPackageName());
    }

    /**
     * 获取对应包名应用启动的 Activity
     * <pre>
     *     android.intent.category.LAUNCHER (android.intent.action.MAIN)
     * </pre>
     * @param packageName 应用包名
     * @return package.xx.Activity.className
     */
    public static String getActivityToLauncher(final String packageName) {
        if (packageName == null) return null;
        PackageManager packageManager = AppUtils.getPackageManager();
        if (packageManager == null) return null;
        try {
            // 创建一个类别为 CATEGORY_LAUNCHER 的该包名的 Intent
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageName);
            // 通过 AppUtils.getPackageManager() 的 queryIntentActivities 方法遍历
            List<ResolveInfo> lists = packageManager.queryIntentActivities(
                    resolveIntent, 0
            );
            for (ResolveInfo resolveInfo : lists) {
                if (resolveInfo != null && resolveInfo.activityInfo != null) {
                    // resolveInfo.activityInfo.packageName; // packageName
                    // 这个就是该 APP 的 LAUNCHER 的 Activity [ 组织形式: packageName.mainActivityName ]
                    return resolveInfo.activityInfo.name;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getActivityToLauncher");
        }
        return null;
    }

    /**
     * 获取系统桌面信息
     * @return {@link ResolveInfo}
     */
    public static ResolveInfo getLauncherCategoryHomeToResolveInfo() {
        PackageManager packageManager = AppUtils.getPackageManager();
        if (packageManager == null) return null;
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return packageManager.resolveActivity(intent, 0);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLauncherCategoryHomeToResolveInfo");
        }
        return null;
    }

    /**
     * 获取系统桌面信息 ( packageName )
     * <pre>
     *     注: 存在多个桌面时且未指定默认桌面时, 该方法返回 Null, 使用时需处理这个情况
     * </pre>
     * @return packageName
     */
    public static String getLauncherCategoryHomeToPackageName() {
        ResolveInfo resolveInfo = getLauncherCategoryHomeToResolveInfo();
        if (resolveInfo != null && resolveInfo.activityInfo != null) {
            // 有多个桌面程序存在, 且未指定默认项时
            if ("android".equals(resolveInfo.activityInfo.packageName)) {
                return null;
            } else {
                return resolveInfo.activityInfo.packageName;
            }
        }
        return null;
    }

    /**
     * 获取系统桌面信息 ( activityName )
     * @return activityName
     */
    public static String getLauncherCategoryHomeToActivityName() {
        ResolveInfo resolveInfo = getLauncherCategoryHomeToResolveInfo();
        if (resolveInfo != null && resolveInfo.activityInfo != null) {
            // 有多个桌面程序存在, 且未指定默认项时
            if ("android".equals(resolveInfo.activityInfo.packageName)) {
                return null;
            } else {
                return resolveInfo.activityInfo.name;
            }
        }
        return null;
    }

    /**
     * 获取系统桌面信息 ( package/activityName )
     * @return package/activityName
     */
    public static String getLauncherCategoryHomeToPackageAndName() {
        ResolveInfo resolveInfo = getLauncherCategoryHomeToResolveInfo();
        if (resolveInfo != null && resolveInfo.activityInfo != null) {
            // 有多个桌面程序存在, 且未指定默认项时
            if ("android".equals(resolveInfo.activityInfo.packageName)) {
                return null;
            } else {
                // 判断是否. 开头
                String name = resolveInfo.activityInfo.name;
                if (name != null) {
                    // 判断是否 . 开头
                    if (name.startsWith(".")) {
                        name = resolveInfo.activityInfo.packageName + name;
                    }
                    return resolveInfo.activityInfo.packageName + "/" + name;
                }
            }
        }
        return null;
    }

    // ==========
    // = 转场动画 =
    // ==========

    /**
     * 设置跳转动画
     * @param context   {@link Context}
     * @param enterAnim 进入动画
     * @param exitAnim  退出动画
     * @return {@link Bundle}
     */
    public static Bundle getOptionsBundle(
            final Context context,
            final int enterAnim,
            final int exitAnim
    ) {
        try {
            return ActivityOptionsCompat.makeCustomAnimation(
                    context, enterAnim, exitAnim
            ).toBundle();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getOptionsBundle");
        }
        return null;
    }

    /**
     * 设置跳转动画
     * @param activity       {@link Activity}
     * @param sharedElements 转场动画 View
     * @return {@link Bundle}
     */
    public static Bundle getOptionsBundle(
            final Activity activity,
            final View[] sharedElements
    ) {
        if (activity == null) return null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int len = sharedElements.length;
                @SuppressWarnings("unchecked")
                Pair<View, String>[] pairs = new Pair[len];
                for (int i = 0; i < len; i++) {
                    pairs[i] = Pair.create(
                            sharedElements[i],
                            sharedElements[i].getTransitionName()
                    );
                }
                return ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, pairs
                ).toBundle();
            }
            return ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, null, null
            ).toBundle();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getOptionsBundle");
        }
        return null;
    }

    // ===================
    // = Activity 管理控制 =
    // ===================

    // ActivityManagerAssist 实例
    private static volatile ActivityManagerAssist sInstance;

    /**
     * 获取 ActivityManagerAssist 管理实例
     * @return {@link ActivityManagerAssist}
     */
    public static ActivityManagerAssist getManager() {
        if (sInstance == null) {
            synchronized (ActivityManagerAssist.class) {
                if (sInstance == null) {
                    sInstance = new ActivityManagerAssist();
                }
            }
        }
        return sInstance;
    }
}