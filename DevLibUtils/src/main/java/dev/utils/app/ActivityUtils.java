package dev.utils.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
        } catch (Exception e){
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
     * 跳转到桌面
     * @return
     */
    public static String getLauncherActivity() {
        return getLauncherActivity(DevUtils.getContext().getPackageName());
    }

    /**
     * 跳转到桌面
     * @param pkg
     * @return
     */
    public static String getLauncherActivity(@NonNull final String pkg) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager pm = DevUtils.getContext().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo aInfo : info) {
            if (aInfo.activityInfo.packageName.equals(pkg)) {
                return aInfo.activityInfo.name;
            }
        }
        return null;
    }

    /**
     * 返回Activity 对应的图标
     * @param clz
     * @return
     */
    public static Drawable getActivityIcon(final Class<?> clz) {
        return getActivityIcon(new ComponentName(DevUtils.getContext(), clz));
    }

    /**
     * 返回Activity 对应的图标
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
     * 返回Activity 对应的Logo
     * @param clz
     * @return
     */
    public static Drawable getActivityLogo(final Class<?> clz) {
        return getActivityLogo(new ComponentName(DevUtils.getContext(), clz));
    }

    /**
     * 返回Activity 对应的Logo
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
