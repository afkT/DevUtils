package dev.capture;

import android.app.Activity;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.DevHttpCapture;
import dev.callback.DevCallback;
import dev.utils.LogPrintUtils;
import dev.utils.app.HandlerUtils;

public final class UtilsCompiler {

    // 日志 TAG
    private static final String TAG = UtilsCompiler.class.getSimpleName();

    // Utils 实例
    private static volatile UtilsCompiler sInstance;

    /**
     * 获取 Utils 实例
     * @return {@link UtilsCompiler}
     */
    public static UtilsCompiler getInstance() {
        if (sInstance == null) {
            synchronized (UtilsCompiler.class) {
                if (sInstance == null) {
                    sInstance = new UtilsCompiler();
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
     * @return {@link UtilsCompiler}
     */
    protected UtilsCompiler addActivity(final Activity activity) {
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
     * @return {@link UtilsCompiler}
     */
    protected UtilsCompiler removeActivity(final Activity activity) {
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

    // ============
    // = Callback =
    // ============

    // 监听回调
    private List<DevCallback<Boolean>> mCallbackLists = new CopyOnWriteArrayList<>();

    /**
     * 移除所有回调
     */
    private void clearCallback() {
        mCallbackLists.clear();
    }

    /**
     * 移除回调 ( 关闭页面调用 )
     * @param callback 回调事件
     */
    private void removeCallback(final DevCallback<Boolean> callback) {
        if (callback == null) return;
        mCallbackLists.remove(callback);
    }

    /**
     * 添加回调
     * @param callback 回调事件
     */
    private void addCallback(final DevCallback<Boolean> callback) {
        if (callback == null) return;
        if (mCallbackLists.contains(callback)) return;
        mCallbackLists.add(callback);
    }

    /**
     * 通知回调
     */
    private void notifyCallback(final boolean isQuerying) {
        for (DevCallback<Boolean> callback : mCallbackLists) {
            HandlerUtils.postRunnable(() -> {
                try {
                    callback.callback(isQuerying);
                } catch (Exception ignored) {
                }
            });
        }
    }

    // ==========
    // = 数据获取 =
    // ==========

    // 是否查询中
    private boolean                        mQuerying = false;
    // 数据源
    private Map<String, List<CaptureItem>> mDataMaps = new LinkedHashMap<>();

    /**
     * 查询数据
     * @param callback  回调事件
     * @param isRefresh 是否刷新操作
     */
    private void queryData(
            final DevCallback<Boolean> callback,
            final boolean isRefresh
    ) {
        addCallback(callback);
        if (mQuerying) {
            notifyCallback(true);
            return;
        }
        // 如果存在数据且非刷新操作表示需要获取数据
        if (mDataMaps.size() != 0 && !isRefresh) {
            notifyCallback(false);
            return;
        }
        mQuerying = true;
        new Thread(() -> {
            Map<String, List<CaptureItem>> maps = DevHttpCapture.getAllModule(false);
            mDataMaps.clear();
            mDataMaps.putAll(maps);
            mQuerying = false;
            notifyCallback(false);
        }).start();
    }
}