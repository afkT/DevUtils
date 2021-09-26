package dev.capture;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

import dev.utils.LogPrintUtils;

public final class Utils {

    // 日志 TAG
    private static final String TAG = Utils.class.getSimpleName();

    // Utils 实例
    private static volatile Utils sInstance;

    /**
     * 获取 Utils 实例
     * @return {@link Utils}
     */
    public static Utils getInstance() {
        if (sInstance == null) {
            synchronized (Utils.class) {
                if (sInstance == null) {
                    sInstance = new Utils();
                }
            }
        }
        return sInstance;
    }

    // =================
    // = Activity 栈处理 =
    // =================

    // Activity 栈 ( 后进先出 )
    private final Stack<Activity> mActivityStacks = new Stack<>();

    /**
     * 添加 Activity
     * @param activity {@link Activity}
     * @return {@link Utils}
     */
    protected Utils addActivity(final Activity activity) {
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
     * @return {@link Utils}
     */
    protected Utils removeActivity(final Activity activity) {
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
     * 结束所有 Activity
     */
    public void finishAllActivity() {
        try {
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
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "finishAllActivity");
        }
    }
}