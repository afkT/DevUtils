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
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

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

    // =====================
    // = Activity 判断处理 =
    // =====================

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
        return false;
    }

    /**
     * 判断 Activity 是否关闭
     * @param context {@link Context}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFinishing(final Context context) {
        if (context != null) {
            try {
                return ((Activity) context).isFinishing();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isFinishing");
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
        return isActivityExists(DevUtils.getContext(), DevUtils.getContext().getPackageName(), className);
    }

    /**
     * 判断是否存在指定的 Activity
     * @param context   {@link Context}
     * @param className Activity.class.getCanonicalName()
     * @return {@code true} 存在, {@code false} 不存在
     */
    public static boolean isActivityExists(final Context context, final String className) {
        return isActivityExists(context, DevUtils.getContext().getPackageName(), className);
    }

    /**
     * 判断是否存在指定的 Activity
     * @param packageName 应用包名
     * @param className   Activity.class.getCanonicalName()
     * @return {@code true} 存在, {@code false} 不存在
     */
    public static boolean isActivityExists(final String packageName, final String className) {
        return isActivityExists(DevUtils.getContext(), packageName, className);
    }

    /**
     * 判断是否存在指定的 Activity
     * @param context     {@link Context}
     * @param packageName 应用包名
     * @param className   Activity.class.getCanonicalName()
     * @return {@code true} 存在, {@code false} 不存在
     */
    public static boolean isActivityExists(final Context context, final String packageName, final String className) {
        if (context == null || packageName == null || className == null) return false;
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

    // =====================
    // = Activity 获取操作 =
    // =====================

    /**
     * 回到桌面 ( 同点击 Home 键效果 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startHomeActivity() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            DevUtils.getContext().startActivity(intent);
            return true;
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
            return getLauncherActivity(DevUtils.getContext().getPackageName());
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
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PackageManager packageManager = DevUtils.getContext().getPackageManager();
            List<ResolveInfo> lists = packageManager.queryIntentActivities(intent, 0);
            for (ResolveInfo resolveinfo : lists) {
                if (resolveinfo != null && resolveinfo.activityInfo != null) {
                    if (resolveinfo.activityInfo.packageName.equals(packageName)) {
                        return resolveinfo.activityInfo.name;
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
        try {
            return DevUtils.getContext().getPackageManager().getActivityIcon(componentName);
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
        try {
            return DevUtils.getContext().getPackageManager().getActivityLogo(componentName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getActivityLogo");
        }
        return null;
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
        try {
            PackageManager packageManager = DevUtils.getContext().getPackageManager();
            // 获取对应的 PackageInfo
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);

            if (packageInfo == null) return null;

            // 创建一个类别为 CATEGORY_LAUNCHER 的该包名的 Intent
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageInfo.packageName);

            // 通过 getPackageManager() 的 queryIntentActivities 方法遍历
            List<ResolveInfo> lists = packageManager.queryIntentActivities(resolveIntent, 0);
            // 循环返回
            for (ResolveInfo resolveinfo : lists) {
                if (resolveinfo != null && resolveinfo.activityInfo != null) {
                    // resolveinfo.activityInfo.packageName; // packageName
                    // 这个就是该 APP 的 LAUNCHER 的 Activity [ 组织形式: packageName.mainActivityname ]
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
     * @return {@link ResolveInfo}
     */
    public static ResolveInfo getLauncherCategoryHomeToResolveInfo() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return DevUtils.getContext().getPackageManager().resolveActivity(intent, 0);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLauncherCategoryHomeToResolveInfo");
        }
        return null;
    }

    /**
     * 获取系统桌面信息 - packageName
     * <pre>
     *     注: 存在多个桌面时且未指定默认桌面时, 该方法返回 Null, 使用时需处理这个情况
     * </pre>
     * @return packageName
     */
    public static String getLauncherCategoryHomeToPackageName() {
        ResolveInfo resolveinfo = getLauncherCategoryHomeToResolveInfo();
        if (resolveinfo != null && resolveinfo.activityInfo != null) {
            // 有多个桌面程序存在, 且未指定默认项时
            if (resolveinfo.activityInfo.packageName.equals("android")) {
                return null;
            } else {
                return resolveinfo.activityInfo.packageName;
            }
        }
        return null;
    }

    /**
     * 获取系统桌面信息 - activityName
     * @return activityName
     */
    public static String getLauncherCategoryHomeToActivityName() {
        ResolveInfo resolveinfo = getLauncherCategoryHomeToResolveInfo();
        if (resolveinfo != null && resolveinfo.activityInfo != null) {
            // 有多个桌面程序存在, 且未指定默认项时
            if (resolveinfo.activityInfo.packageName.equals("android")) {
                return null;
            } else {
                return resolveinfo.activityInfo.name;
            }
        }
        return null;
    }

    /**
     * 获取系统桌面信息 - package/activityName
     * @return package/activityName
     */
    public static String getLauncherCategoryHomeToPackageAndName() {
        ResolveInfo resolveinfo = getLauncherCategoryHomeToResolveInfo();
        if (resolveinfo != null && resolveinfo.activityInfo != null) {
            // 有多个桌面程序存在, 且未指定默认项时
            if (resolveinfo.activityInfo.packageName.equals("android")) {
                return null;
            } else {
                // 判断是否. 开头
                String name = resolveinfo.activityInfo.name;
                if (name != null) {
                    // 判断是否 . 开头
                    if (name.startsWith(".")) {
                        name = resolveinfo.activityInfo.packageName + name;
                    }
                    return resolveinfo.activityInfo.packageName + "/" + name;
                }
            }
        }
        return null;
    }

    // ============
    // = 转场动画 =
    // ============

    /**
     * 设置跳转动画
     * @param context   {@link Context}
     * @param enterAnim 进入动画
     * @param exitAnim  退出动画
     * @return {@link Bundle}
     */
    public static Bundle getOptionsBundle(final Context context, final int enterAnim, final int exitAnim) {
        try {
            return ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exitAnim).toBundle();
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
    public static Bundle getOptionsBundle(final Activity activity, final View[] sharedElements) {
        if (activity == null) return null;
        try {
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
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getOptionsBundle");
        }
        return null;
    }

    // =====================
    // = Activity 管理控制 =
    // =====================

    // ActivityUtils 实例
    private static final ActivityUtils INSTANCE = new ActivityUtils();

    /**
     * 获取 ActivityUtils 管理实例
     * @return {@link ActivityUtils}
     */
    public static ActivityUtils getManager() {
        return INSTANCE;
    }

    // ===================
    // = Activity 栈处理 =
    // ===================

    // Activity 栈 ( 后进先出 )
    private final Stack<Activity> mActivityStacks = new Stack<>();

    /**
     * 获取 Activity 栈
     * @return {@link Stack<Activity>}
     */
    public Stack<Activity> getActivityStacks() {
        return mActivityStacks;
    }

    /**
     * 添加 Activity
     * @param activity {@link Activity}
     * @return {@link ActivityUtils}
     */
    public ActivityUtils addActivity(final Activity activity) {
        if (activity != null) {
            synchronized (mActivityStacks) {
                if (mActivityStacks.contains(activity)) {
                    return this;
                }
                mActivityStacks.add(activity);
            }
        }
        return this;
    }

    /**
     * 移除 Activity
     * @param activity {@link Activity}
     * @return {@link ActivityUtils}
     */
    public ActivityUtils removeActivity(final Activity activity) {
        if (activity != null) {
            synchronized (mActivityStacks) {
                int index = mActivityStacks.indexOf(activity);
                if (index == -1) {
                    return this;
                }
                try {
                    mActivityStacks.remove(index);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "removeActivity");
                }
            }
        }
        return this;
    }

    /**
     * 移除多个 Activity
     * @param activitys Activity[]
     * @return {@link ActivityUtils}
     */
    public ActivityUtils removeActivity(final Activity... activitys) {
        if (activitys != null && activitys.length != 0) {
            for (int i = 0, len = activitys.length; i < len; i++) {
                removeActivity(activitys[i]);
            }
        }
        return this;
    }

    /**
     * 获取最后一个 ( 当前 ) Activity
     * @return 最后一个 ( 当前 ) {@link Activity}
     */
    public Activity currentActivity() {
        return mActivityStacks.lastElement();
    }

    /**
     * 关闭最后一个 ( 当前 ) Activity
     * @return {@link ActivityUtils}
     */
    public ActivityUtils finishActivity() {
        return finishActivity(mActivityStacks.lastElement());
    }

    /**
     * 检测是否包含指定的 Activity
     * @param clazzs Class(Activity)[]
     * @return {@code true} yes, {@code false} no
     */
    public boolean existActivitys(final Class<?>... clazzs) {
        if (clazzs != null && clazzs.length != 0) {
            synchronized (mActivityStacks) {
                // 保存新的堆栈, 防止出现同步问题
                Stack<Activity> stack = new Stack<>();
                stack.addAll(mActivityStacks);
                try {
                    // 进行遍历判断
                    Iterator<Activity> iterator = stack.iterator();
                    while (iterator.hasNext()) {
                        Activity activity = iterator.next();
                        if (activity != null && !activity.isFinishing()) {
                            for (int i = 0, len = clazzs.length; i < len; i++) {
                                if (clazzs[i] != null && activity.getClass().getName().equals(clazzs[i].getName())) {
                                    return true;
                                }
                            }
                        }
                    }
                } finally {
                    // 移除数据, 并且清空内存
                    stack.clear();
                    stack = null;
                }
            }
        }
        return false;
    }

    /**
     * 关闭指定 Activity
     * @param activity {@link Activity}
     * @return {@link ActivityUtils}
     */
    public ActivityUtils finishActivity(final Activity activity) {
        // 先移除 Activity
        removeActivity(activity);
        // Activity 不为 null, 并且属于未销毁状态
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
        return this;
    }

    /**
     * 关闭多个 Activity
     * @param activitys Activity[]
     * @return {@link ActivityUtils}
     */
    public ActivityUtils finishActivity(final Activity... activitys) {
        if (activitys != null && activitys.length != 0) {
            for (int i = 0, len = activitys.length; i < len; i++) {
                finishActivity(activitys[i]);
            }
        }
        return this;
    }

    /**
     * 关闭指定类名 Activity
     * @param clazz Activity.class
     * @return {@link ActivityUtils}
     */
    public ActivityUtils finishActivity(final Class<?> clazz) {
        if (clazz != null) {
            synchronized (mActivityStacks) {
                // 保存新的堆栈, 防止出现同步问题
                Stack<Activity> stack = new Stack<>();
                stack.addAll(mActivityStacks);
                // 清空全部, 便于后续操作处理
                mActivityStacks.clear();
                // 进行遍历移除
                Iterator<Activity> iterator = stack.iterator();
                while (iterator.hasNext()) {
                    Activity activity = iterator.next();
                    // 判断是否想要关闭的 Activity
                    if (activity != null) {
                        if (activity.getClass() == clazz) {
                            // 如果 Activity 没有 finish 则进行 finish
                            if (!activity.isFinishing()) {
                                activity.finish();
                            }
                            // 删除对应的 Item
                            iterator.remove();
                        }
                    } else {
                        // 删除对应的 Item
                        iterator.remove();
                    }
                }
                // 把不符合条件的保存回去
                mActivityStacks.addAll(stack);
                // 移除数据, 并且清空内存
                stack.clear();
                stack = null;
            }
        }
        return this;
    }

    /**
     * 结束多个类名 Activity
     * @param clazzs Class(Activity)[]
     * @return {@link ActivityUtils}
     */
    public ActivityUtils finishActivity(final Class<?>... clazzs) {
        if (clazzs != null && clazzs.length != 0) {
            synchronized (mActivityStacks) {
                // 保存新的堆栈, 防止出现同步问题
                Stack<Activity> stack = new Stack<>();
                stack.addAll(mActivityStacks);
                // 清空全部, 便于后续操作处理
                mActivityStacks.clear();
                // 判断是否销毁
                boolean isRemove;
                // 进行遍历移除
                Iterator<Activity> iterator = stack.iterator();
                while (iterator.hasNext()) {
                    Activity activity = iterator.next();
                    // 判断是否想要关闭的 Activity
                    if (activity != null) {
                        // 默认不需要销毁
                        isRemove = false;
                        // 循环判断
                        for (int i = 0, len = clazzs.length; i < len; i++) {
                            // 判断是否相同
                            if (activity.getClass() == clazzs[i]) {
                                isRemove = true;
                                break;
                            }
                        }
                        // 判断是否销毁
                        if (isRemove) {
                            // 如果 Activity 没有 finish 则进行 finish
                            if (!activity.isFinishing()) {
                                activity.finish();
                            }
                            // 删除对应的 Item
                            iterator.remove();
                        }
                    } else {
                        // 删除对应的 Item
                        iterator.remove();
                    }
                }
                // 把不符合条件的保存回去
                mActivityStacks.addAll(stack);
                // 移除数据, 并且清空内存
                stack.clear();
                stack = null;
            }
        }
        return this;
    }

    /**
     * 结束全部 Activity 除忽略的 Activity 外
     * @param clazz Activity.class
     * @return {@link ActivityUtils}
     */
    public ActivityUtils finishAllActivityToIgnore(final Class<?> clazz) {
        if (clazz != null) {
            synchronized (mActivityStacks) {
                // 保存新的堆栈, 防止出现同步问题
                Stack<Activity> stack = new Stack<>();
                stack.addAll(mActivityStacks);
                // 清空全部, 便于后续操作处理
                mActivityStacks.clear();
                // 进行遍历移除
                Iterator<Activity> iterator = stack.iterator();
                while (iterator.hasNext()) {
                    Activity activity = iterator.next();
                    // 判断是否想要关闭的 Activity
                    if (activity != null) {
                        if (!(activity.getClass() == clazz)) {
                            // 如果 Activity 没有 finish 则进行 finish
                            if (!activity.isFinishing()) {
                                activity.finish();
                            }
                            // 删除对应的 Item
                            iterator.remove();
                        }
                    } else {
                        // 删除对应的 Item
                        iterator.remove();
                    }
                }
                // 把不符合条件的保存回去
                mActivityStacks.addAll(stack);
                // 移除数据, 并且清空内存
                stack.clear();
                stack = null;
            }
        }
        return this;
    }

    /**
     * 结束全部 Activity 除忽略的 Activity 外
     * @param clazzs Class(Activity)[]
     * @return {@link ActivityUtils}
     */
    public ActivityUtils finishAllActivityToIgnore(final Class<?>... clazzs) {
        if (clazzs != null && clazzs.length != 0) {
            synchronized (mActivityStacks) {
                // 保存新的堆栈, 防止出现同步问题
                Stack<Activity> stack = new Stack<>();
                stack.addAll(mActivityStacks);
                // 清空全部, 便于后续操作处理
                mActivityStacks.clear();
                // 判断是否销毁
                boolean isRemove;
                // 进行遍历移除
                Iterator<Activity> iterator = stack.iterator();
                while (iterator.hasNext()) {
                    Activity activity = iterator.next();
                    // 判断是否想要关闭的 Activity
                    if (activity != null) {
                        // 默认需要销毁
                        isRemove = true;
                        // 循环判断
                        for (int i = 0, len = clazzs.length; i < len; i++) {
                            // 判断是否相同
                            if (activity.getClass() == clazzs[i]) {
                                isRemove = false;
                                break;
                            }
                        }
                        // 判断是否销毁
                        if (isRemove) {
                            // 如果 Activity 没有 finish 则进行 finish
                            if (!activity.isFinishing()) {
                                activity.finish();
                            }
                            // 删除对应的 Item
                            iterator.remove();
                        }
                    } else {
                        // 删除对应的 Item
                        iterator.remove();
                    }
                }
                // 把不符合条件的保存回去
                mActivityStacks.addAll(stack);
                // 移除数据, 并且清空内存
                stack.clear();
                stack = null;
            }
        }
        return this;
    }

    /**
     * 结束所有 Activity
     * @return {@link ActivityUtils}
     */
    public ActivityUtils finishAllActivity() {
        synchronized (mActivityStacks) {
            // 保存新的堆栈, 防止出现同步问题
            Stack<Activity> stack = new Stack<>();
            stack.addAll(mActivityStacks);
            // 清空全部, 便于后续操作处理
            mActivityStacks.clear();
            // 进行遍历移除
            Iterator<Activity> iterator = stack.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                    // 删除对应的 Item
                    iterator.remove();
                }
            }
            // 移除数据, 并且清空内存
            stack.clear();
            stack = null;
        }
        return this;
    }

    // =

    /**
     * 退出应用程序
     * @return {@link ActivityUtils}
     */
    public ActivityUtils appExit() {
        try {
            finishAllActivity();
            // 退出 JVM (Java 虚拟机 ) 释放所占内存资源, 0 表示正常退出、非 0 的都为异常退出
            System.exit(0);
            // 从操作系统中结束掉当前程序的进程
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "appExit");
            // =
            System.exit(-1);
        }
        return this;
    }

    /**
     * 重启 APP
     * @return {@link ActivityUtils}
     */
    public ActivityUtils restartApplication() {
        try {
            Intent intent = DevUtils.getContext().getPackageManager().getLaunchIntentForPackage(DevUtils.getContext().getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            DevUtils.getContext().startActivity(intent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "restartApplication");
        }
        return this;
    }
}