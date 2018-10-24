package dev.utils.app.assist.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.Iterator;
import java.util.Stack;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 应用程序Activity管理类：用于Activity管理和应用程序
 * Created by Ttt
 */
public final class ActivityManager {

    /** 禁止构造对象,保证只有一个实例 */
    private ActivityManager() {
    }

    // 日志TAG
    private static final String TAG = ActivityManager.class.getSimpleName();

    /** ActivityManager 实例 */
    private static ActivityManager INSTANCE = new ActivityManager();

    /** 获取 ActivityManager 实例 ,单例模式 */
    public static ActivityManager getInstance() {
        return INSTANCE;
    }

    /**
     * 通过 Context 获取Activity
     * @param context
     * @return context 所属的 Activity
     */
    public static Activity getActivity(Context context) {
        try {
            Activity activity = (Activity) context;
            return activity;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getActivity");
        }
        return null;
    }

    // == 页面判断处理 ==

    /**
     * 判断页面是否关闭
     * @param activity
     * @return true: 关闭, false: 未关闭
     */
    public static boolean isFinishing(Activity activity){
        if (activity != null){
            return activity.isFinishing();
        }
        return false;
    }

    /**
     * 判断页面是否关闭
     * @param context
     * @return true: 关闭, false: 未关闭
     */
    public static boolean isFinishingCtx(Context context){
        if (context != null){
            try {
                return ((Activity) context).isFinishing();
            } catch (Exception e){
                LogPrintUtils.eTag(TAG, e, "isFinishingCtx");
            }
        }
        return false;
    }

    // ==============

    /** Activity 栈(后进先出) */
    private final Stack<Activity> activityStacks = new Stack<>();

    /**
     * 获取 Activity 栈
     * @return Activity 栈
     */
    public Stack<Activity> getActivityStacks() {
        return activityStacks;
    }

    /**
     * 保存 Activity
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        synchronized (activityStacks) {
            if (activityStacks.contains(activity)) {
                return;
            }
            activityStacks.add(activity);
        }
    }

    /**
     * 移除 Activity
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        synchronized (activityStacks) {
            int index = activityStacks.indexOf(activity);
            if (index == -1) {
                return;
            }
            activityStacks.remove(index);
        }
    }

    /**
     * 移除 Activity
     * @param activitys
     */
    public void removeActivity(Activity... activitys) {
        if (activitys != null && activitys.length != 0){
            for (int i = 0, len = activitys.length; i < len; i++){
                removeActivity(activitys[i]);
            }
        }
    }

    /**
     * 获取最后一个(当前)Activity
     * @return lastElement() activity
     */
    public Activity currentActivity() {
        return activityStacks.lastElement();
    }

    /**
     * 结束最后一个(当前)Activity
     */
    public void finishActivity() {
        finishActivity(activityStacks.lastElement());
    }

    /**
     * 结束指定的 Activity
     * @param activity
     */
    public void finishActivity(Activity activity) {
        // 先移除Activity
        removeActivity(activity);
        // Activity 不为null,并且属于未销毁状态
        if (activity != null && !activity.isFinishing()) {
            // finish到Activity
            activity.finish();
        }
    }

    /**
     * 结束指定的 Activity
     * @param activitys
     */
    public void finishActivity(Activity... activitys) {
        if (activitys != null && activitys.length != 0){
            for (int i = 0, len = activitys.length; i < len; i++){
                finishActivity(activitys[i]);
            }
        }
    }

    /**
     * 结束指定类名的 Activity
     * @param cls Activity.class
     */
    public void finishActivity(Class<?> cls) {
        synchronized (activityStacks) {
            // 保存新的任务,防止出现同步问题
            Stack<Activity> aStacks = new Stack<>();
            aStacks.addAll(activityStacks);
            // 清空全部,便于后续操作处理
            activityStacks.clear();
            // 进行遍历移除
            Iterator<Activity> iterator = aStacks.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                // 判断是否想要关闭的Activity
                if (activity != null) {
                    if (activity.getClass() == cls){
                        // 如果页面没有finish 则进行finish
                        if (!activity.isFinishing()) {
                            activity.finish();
                        }
                        // 删除对应的Item
                        iterator.remove();
                    }
                } else {
                    // 删除对应的Item
                    iterator.remove();
                }
            }
            // 把不符合条件的保存回去
            activityStacks.addAll(aStacks);
            // 移除,并且清空内存
            aStacks.clear();
            aStacks = null;
        }
    }

    /**
     * 结束指定类名的 Activity
     * @param clss Activity.class, x.class
     */
    public void finishActivity(Class<?>... clss) {
        if (clss != null && clss.length != 0){
            synchronized (activityStacks) {
                // 保存新的任务,防止出现同步问题
                Stack<Activity> aStacks = new Stack<>();
                aStacks.addAll(activityStacks);
                // 清空全部,便于后续操作处理
                activityStacks.clear();
                // 判断是否销毁
                boolean isRemove;
                // 进行遍历移除
                Iterator<Activity> iterator = aStacks.iterator();
                while (iterator.hasNext()) {
                    Activity activity = iterator.next();
                    // 判断是否想要关闭的Activity
                    if (activity != null) {
                        // 默认不需要销毁
                        isRemove = false;
                        // 循环判断
                        for (int i = 0, len = clss.length; i < len; i++){
                            // 判断是否相同
                            if (activity.getClass() == clss[i]){
                                isRemove = true;
                                break;
                            }
                        }
                        // 判断是否销毁
                        if (isRemove){
                            // 如果页面没有finish 则进行finish
                            if (!activity.isFinishing()) {
                                activity.finish();
                            }
                            // 删除对应的Item
                            iterator.remove();
                        }
                    } else {
                        // 删除对应的Item
                        iterator.remove();
                    }
                }
                // 把不符合条件的保存回去
                activityStacks.addAll(aStacks);
                // 移除,并且清空内存
                aStacks.clear();
                aStacks = null;
            }
        }
    }

    /**
     * 结束全部Activity 除忽略的页面外
     * @param cls
     */
    public void finishAllActivityToIgnore(Class<?> cls){
        synchronized (activityStacks) {
            // 保存新的任务,防止出现同步问题
            Stack<Activity> aStacks = new Stack<>();
            aStacks.addAll(activityStacks);
            // 清空全部,便于后续操作处理
            activityStacks.clear();
            // 进行遍历移除
            Iterator<Activity> iterator = aStacks.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                // 判断是否想要关闭的Activity
                if (activity != null) {
                    if (!(activity.getClass() == cls)){
                        // 如果页面没有finish 则进行finish
                        if (!activity.isFinishing()) {
                            activity.finish();
                        }
                        // 删除对应的Item
                        iterator.remove();
                    }
                } else {
                    // 删除对应的Item
                    iterator.remove();
                }
            }
            // 把不符合条件的保存回去
            activityStacks.addAll(aStacks);
            // 移除,并且清空内存
            aStacks.clear();
            aStacks = null;
        }
    }

    /**
     * 结束全部Activity 除忽略的页面外
     * @param clss
     */
    public void finishAllActivityToIgnore(Class<?>... clss){
        if (clss != null && clss.length != 0){
            synchronized (activityStacks) {
                // 保存新的任务,防止出现同步问题
                Stack<Activity> aStacks = new Stack<>();
                aStacks.addAll(activityStacks);
                // 清空全部,便于后续操作处理
                activityStacks.clear();
                // 判断是否销毁
                boolean isRemove;
                // 进行遍历移除
                Iterator<Activity> iterator = aStacks.iterator();
                while (iterator.hasNext()) {
                    Activity activity = iterator.next();
                    // 判断是否想要关闭的Activity
                    if (activity != null) {
                        // 默认需要销毁
                        isRemove = true;
                        // 循环判断
                        for (int i = 0, len = clss.length; i < len; i++){
                            // 判断是否相同
                            if (activity.getClass() == clss[i]){
                                isRemove = false;
                                break;
                            }
                        }
                        // 判断是否销毁
                        if (isRemove){
                            // 如果页面没有finish 则进行finish
                            if (!activity.isFinishing()) {
                                activity.finish();
                            }
                            // 删除对应的Item
                            iterator.remove();
                        }
                    } else {
                        // 删除对应的Item
                        iterator.remove();
                    }
                }
                // 把不符合条件的保存回去
                activityStacks.addAll(aStacks);
                // 移除,并且清空内存
                aStacks.clear();
                aStacks = null;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        synchronized (activityStacks) {
            // 保存新的任务,防止出现同步问题
            Stack<Activity> aStacks = new Stack<>();
            aStacks.addAll(activityStacks);
            // 清空全部,便于后续操作处理
            activityStacks.clear();
            // 进行遍历移除
            Iterator<Activity> iterator = aStacks.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                if (activity != null && !activity.isFinishing()) {
                     activity.finish();
                    // 删除对应的Item
                    iterator.remove();
                }
            }
            // 移除,并且清空内存
            aStacks.clear();
            aStacks = null;
        }
    }

    /**
     * 退出应用程序
     * @param context
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            // --
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "appExit");
        }
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAllActivity();
            //退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
            System.exit(0);
            //从操作系统中结束掉当前程序的进程
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "appExit");
            // -
            System.exit(-1);
        }
    }

    /**
     * 重启 App
     */
    public static void restartApplication() {
        try {
            Intent intent = DevUtils.getContext().getPackageManager().getLaunchIntentForPackage(DevUtils.getContext().getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            DevUtils.getContext().startActivity(intent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "restartApplication");
        }
    }
}