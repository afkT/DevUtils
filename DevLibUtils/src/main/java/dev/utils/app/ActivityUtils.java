package dev.utils.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import java.util.List;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Acitivty 工具类
 * Created by Ttt
 */
public final class ActivityUtils {

    private ActivityUtils() {
    }

    // 日志TAG
    private static final String TAG = ActivityUtils.class.getSimpleName();

    /**
     * 返回 View context 所属的 Activity
     * @param view
     * @return
     */
    public static Activity getActivityByView(@NonNull final View view) {
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
                LogPrintUtils.e(TAG, e, "getActivityByView");
            }
        }
        return null;
    }

    /**
     * 判断是否存在指定的Activity
     * @param context
     * @param packageName 包名
     * @param className activity全路径类名
     * @return
     */
    public static boolean isActivityExists(Context context, String packageName, String className) {
        boolean result = true;
        try {
            Intent intent = new Intent();
            intent.setClassName(packageName, className);
            if (context.getPackageManager().resolveActivity(intent, 0) == null) {
                result = false;
            } else if (intent.resolveActivity(context.getPackageManager()) == null) {
                result = false;
            } else {
                List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, 0);
                if (list.size() == 0) {
                    result = false;
                }
            }
        } catch (Exception e) {
            result = false;
            LogPrintUtils.eTag(TAG, e, "isActivityExists");
        }
        return result;
    }

    /**
     * 回到桌面 -> 同点击Home键效果
     */
    public static void startHomeActivity() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        DevUtils.getContext().startActivity(homeIntent);
    }

    /**
     * 获取 Launcher activity
     * @return
     */
    public static String getLauncherActivity() {
        return getLauncherActivity(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 launcher activity
     * @param packageName
     * @return
     */
    public static String getLauncherActivity(@NonNull final String packageName) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager pm = DevUtils.getContext().getPackageManager();
        List<ResolveInfo> lists = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveinfo : lists) {
            if (resolveinfo != null && resolveinfo.activityInfo != null) {
                if (resolveinfo.activityInfo.packageName.equals(packageName)) {
                    return resolveinfo.activityInfo.name;
                }
            }
        }
        return null;
    }

    /**
     * 返回 Activity 对应的图标
     * @param clz
     * @return
     */
    public static Drawable getActivityIcon(final Class<?> clz) {
        return getActivityIcon(new ComponentName(DevUtils.getContext(), clz));
    }

    /**
     * 返回 Activity 对应的图标
     * @param activityName
     * @return
     */
    public static Drawable getActivityIcon(final ComponentName activityName) {
        try {
            return DevUtils.getContext().getPackageManager().getActivityIcon(activityName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getActivityIcon");
            return null;
        }
    }

    /**
     * 返回 Activity 对应的Logo
     * @param clz
     * @return
     */
    public static Drawable getActivityLogo(final Class<?> clz) {
        return getActivityLogo(new ComponentName(DevUtils.getContext(), clz));
    }

    /**
     * 返回 Activity 对应的Logo
     * @param activityName
     * @return
     */
    public static Drawable getActivityLogo(final ComponentName activityName) {
        try {
            return DevUtils.getContext().getPackageManager().getActivityLogo(activityName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getActivityLogo");
            return null;
        }
    }

    /**
     * 获取对应包名应用启动 Activity
     * @param packageName
     * @return
     */
    public static String getActivityToLauncher(String packageName) {
        try {
            PackageManager pManager = DevUtils.getContext().getPackageManager();
            // 获取对应的PackageInfo
            PackageInfo pInfo = pManager.getPackageInfo(packageName, 0);

            if (pInfo == null) {
                return null;
            }

            // 创建一个类别为 CATEGORY_LAUNCHER 的该包名的 Intent
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(pInfo.packageName);

            // 通过 getPackageManager() 的 queryIntentActivities 方法遍历
            List<ResolveInfo> lists = pManager.queryIntentActivities(resolveIntent, 0);
            // 循环返回
            for (ResolveInfo resolveinfo : lists) {
                if (resolveinfo != null && resolveinfo.activityInfo != null) {
                    // resolveinfo.activityInfo.packageName; => packageName
                    // 这个就是我们要找的该 App 的 LAUNCHER 的 Activity [ 组织形式：packageName.mainActivityname ]
                    return resolveinfo.activityInfo.name;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getActivityToLauncher");
        }
        return null;
    }

    /**
     * 获取系统桌面信息
     * @return
     */
    public static ResolveInfo getLauncherCategoryHomeToResolveInfo() {
        try {
            final Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            return DevUtils.getContext().getPackageManager().resolveActivity(intent, 0);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLauncherCategoryHomeToResolveInfo");
        }
        return null;
    }

    /**
     * 获取系统桌面信息 -> packageName
     * （注：存在多个桌面时且未指定默认桌面时，该方法返回Null,使用时需处理这个情况）
     * @return
     */
    public static String getLauncherCategoryHomeToPackageName() {
        final ResolveInfo res = getLauncherCategoryHomeToResolveInfo();
        if (res != null && res.activityInfo != null) {
            // 有多个桌面程序存在，且未指定默认项时
            if (res.activityInfo.packageName.equals("android")) {
                return null;
            } else {
                return res.activityInfo.packageName;
            }
        }
        return null;
    }

    /**
     * 获取系统桌面信息 -> activityName
     * @return
     */
    public static String getLauncherCategoryHomeToActivityName() {
        final ResolveInfo res = getLauncherCategoryHomeToResolveInfo();
        if (res != null && res.activityInfo != null) {
            // 有多个桌面程序存在，且未指定默认项时
            if (res.activityInfo.packageName.equals("android")) {
                return null;
            } else {
                return res.activityInfo.name;
            }
        }
        return null;
    }

    /**
     * 获取系统桌面信息 -> package/activityName
     * @return
     */
    public static String getLauncherCategoryHomeToPackageAndName() {
        final ResolveInfo res = getLauncherCategoryHomeToResolveInfo();
        if (res != null && res.activityInfo != null) {
            // 有多个桌面程序存在，且未指定默认项时
            if (res.activityInfo.packageName.equals("android")) {
                return null;
            } else {
                // 判断是否.开头
                String name = res.activityInfo.name;
                if (name != null) {
                    // 判断是否 . 开头
                    if (name.startsWith(".")) {
                        name = res.activityInfo.packageName + name;
                    }
                    return res.activityInfo.packageName + "/" + name;
                }
            }
        }
        return null;
    }

    // == 以下方法使用介绍 ==
    // https://www.cnblogs.com/tianzhijiexian/p/4087917.html
    // ActivityOptionsCompat.makeScaleUpAnimation(source, startX, startY, startWidth, startHeight)

    /**
     * 设置跳转动画
     * @param context
     * @param enterAnim
     * @param exitAnim
     * @return
     */
    private static Bundle getOptionsBundle(final Context context, final int enterAnim, final int exitAnim) {
        return ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exitAnim).toBundle();
    }

    /**
     * 设置跳转动画
     * @param activity
     * @param sharedElements
     * @return
     */
    private static Bundle getOptionsBundle(final Activity activity, final View[] sharedElements) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int len = sharedElements.length;
            @SuppressWarnings("unchecked")
            Pair<View, String>[] pairs = new Pair[len];
            for (int i = 0; i < len; i++) {
                pairs[i] = Pair.create(sharedElements[i], sharedElements[i].getTransitionName());
            }
            return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs).toBundle();
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, null, null).toBundle();
    }
}
