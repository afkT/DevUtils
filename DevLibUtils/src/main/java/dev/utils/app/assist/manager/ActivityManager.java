package dev.utils.app.assist.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.Iterator;
import java.util.Stack;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Activity 管理类, 用于管理应用程序 Activity
 * @author Ttt
 */
public final class ActivityManager {

    private ActivityManager() {
    }

    // 日志 TAG
    private static final String TAG = ActivityManager.class.getSimpleName();
    // ActivityManager 实例
    private static final ActivityManager INSTANCE = new ActivityManager();

    /**
     * 获取 ActivityManager 实例
     * @return {@link ActivityManager}
     */
    public static ActivityManager getInstance() {
        return INSTANCE;
    }

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

    // ===================
    // = Activity 栈处理 =
    // ===================

    // Activity 栈(后进先出)
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
     */
    public void addActivity(final Activity activity) {
        if (activity != null) {
            synchronized (mActivityStacks) {
                if (mActivityStacks.contains(activity)) {
                    return;
                }
                mActivityStacks.add(activity);
            }
        }
    }

    /**
     * 移除 Activity
     * @param activity {@link Activity}
     */
    public void removeActivity(final Activity activity) {
        if (activity != null) {
            synchronized (mActivityStacks) {
                int index = mActivityStacks.indexOf(activity);
                if (index == -1) {
                    return;
                }
                try {
                    mActivityStacks.remove(index);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "removeActivity");
                }
            }
        }
    }

    /**
     * 移除多个 Activity
     * @param activitys {@link Activity} 数组
     */
    public void removeActivity(final Activity... activitys) {
        if (activitys != null && activitys.length != 0) {
            for (int i = 0, len = activitys.length; i < len; i++) {
                removeActivity(activitys[i]);
            }
        }
    }

    /**
     * 获取最后一个(当前) Activity
     * @return 最后一个(当前) {@link Activity}
     */
    public Activity currentActivity() {
        return mActivityStacks.lastElement();
    }

    /**
     * 关闭最后一个(当前) Activity
     */
    public void finishActivity() {
        finishActivity(mActivityStacks.lastElement());
    }

    /**
     * 检测是否包含指定的 Activity
     * @param clazzs Class(Activity)[]
     * @return {@code true} yes, {@code false} no
     */
    public boolean existActivitys(final Class<?>... clazzs) {
        if (clazzs != null && clazzs.length != 0) {
            synchronized (mActivityStacks) {
                // 保存新的任务, 防止出现同步问题
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
                    // 移除, 并且清空内存
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
     */
    public void finishActivity(final Activity activity) {
        // 先移除 Activity
        removeActivity(activity);
        // Activity 不为 null, 并且属于未销毁状态
        if (activity != null && !activity.isFinishing()) {
            // Activity finish
            activity.finish();
        }
    }

    /**
     * 关闭多个 Activity
     * @param activitys Activity[]
     */
    public void finishActivity(final Activity... activitys) {
        if (activitys != null && activitys.length != 0) {
            for (int i = 0, len = activitys.length; i < len; i++) {
                finishActivity(activitys[i]);
            }
        }
    }

    /**
     * 关闭指定类名 Activity
     * @param clazz Activity.class
     */
    public void finishActivity(final Class<?> clazz) {
        if (clazz != null) {
            synchronized (mActivityStacks) {
                // 保存新的任务, 防止出现同步问题
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
                // 移除, 并且清空内存
                stack.clear();
                stack = null;
            }
        }
    }

    /**
     * 结束多个类名 Activity
     * @param clazzs Class(Activity)[]
     */
    public void finishActivity(final Class<?>... clazzs) {
        if (clazzs != null && clazzs.length != 0) {
            synchronized (mActivityStacks) {
                // 保存新的任务, 防止出现同步问题
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
                // 移除, 并且清空内存
                stack.clear();
                stack = null;
            }
        }
    }

    /**
     * 结束全部 Activity 除忽略的 Activity 外
     * @param clazz Activity.class
     */
    public void finishAllActivityToIgnore(final Class<?> clazz) {
        if (clazz != null) {
            synchronized (mActivityStacks) {
                // 保存新的任务, 防止出现同步问题
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
                // 移除, 并且清空内存
                stack.clear();
                stack = null;
            }
        }
    }

    /**
     * 结束全部 Activity 除忽略的 Activity 外
     * @param clazzs Class(Activity)[]
     */
    public void finishAllActivityToIgnore(final Class<?>... clazzs) {
        if (clazzs != null && clazzs.length != 0) {
            synchronized (mActivityStacks) {
                // 保存新的任务, 防止出现同步问题
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
                // 移除, 并且清空内存
                stack.clear();
                stack = null;
            }
        }
    }

    /**
     * 结束所有 Activity
     */
    public void finishAllActivity() {
        synchronized (mActivityStacks) {
            // 保存新的任务, 防止出现同步问题
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
            // 移除, 并且清空内存
            stack.clear();
            stack = null;
        }
    }

    // =

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAllActivity();
            // 退出 JVM (Java 虚拟机) 释放所占内存资源, 0 表示正常退出、非 0 的都为异常退出
            System.exit(0);
            // 从操作系统中结束掉当前程序的进程
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "appExit");
            // =
            System.exit(-1);
        }
    }

    /**
     * 重启 App
     */
    public void restartApplication() {
        try {
            Intent intent = DevUtils.getContext().getPackageManager().getLaunchIntentForPackage(DevUtils.getContext().getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            DevUtils.getContext().startActivity(intent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "restartApplication");
        }
    }
}