package dev.utils.app.assist.lifecycle.current;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.assist.lifecycle.AbstractActivityLifecycle;
import dev.utils.app.assist.lifecycle.ActivityLifecycleAssist;
import dev.utils.app.assist.lifecycle.ActivityLifecycleFilter;

/**
 * detail: 当前 Activity 生命周期辅助类
 * @author Ttt
 * <pre>
 *     在 {@link ActivityLifecycleAssist} 基础上改造
 *     解决在 Activity 中调用
 *     {@link Activity#registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks)}
 *     需要 SDK 大于等于 29 {@link Build.VERSION_CODES#Q} 适配问题
 *     使用:
 *     只需要创建该类单例实例, 通过 {@link ActivityLifecycleFilter} 过滤
 *     可参考 DevSimple 库 BaseActivity 代码
 * </pre>
 */
public final class ThisActivityLifecycleAssist {

    // 日志 TAG
    private static final String TAG = ThisActivityLifecycleAssist.class.getSimpleName();

    // Application 对象
    private final Application mApplication;

    // ==========
    // = 构造函数 =
    // ==========

    public ThisActivityLifecycleAssist() {
        this(DevUtils.getContext());
    }

    public ThisActivityLifecycleAssist(final Context context) {
        this(DevUtils.getApplication(context));
    }

    public ThisActivityLifecycleAssist(final Application application) {
        this.mApplication = application;
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 Activity 生命周期通知事件
     * @param activity {@link Activity}
     * @return Activity 生命周期通知事件
     */
    public AbstractActivityLifecycle ofListener(final Activity activity) {
        return ACTIVITY_LIFECYCLE.ofListener(activity);
    }

    /**
     * 添加 Activity 生命周期通知事件
     * @param activity {@link Activity}
     * @param listener Activity 生命周期通知事件
     */
    public void addListener(
            final Activity activity,
            final AbstractActivityLifecycle listener
    ) {
        ACTIVITY_LIFECYCLE.addListener(activity, listener);
    }

    /**
     * 移除 Activity 生命周期通知事件
     * @param activity {@link Activity}
     */
    public void removeListener(final Activity activity) {
        ACTIVITY_LIFECYCLE.removeListener(activity);
    }

    /**
     * 移除全部 Activity 生命周期通知事件
     */
    public void removeAllListener() {
        ACTIVITY_LIFECYCLE.removeAllListener();
    }

    // =

    /**
     * 注册绑定 Activity 生命周期事件处理
     * @return {@link ThisActivityLifecycleAssist}
     */
    public ThisActivityLifecycleAssist registerActivityLifecycleCallbacks() {
        // 先移除监听
        unregisterActivityLifecycleCallbacks();
        if (mApplication != null) {
            try {
                mApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "registerActivityLifecycleCallbacks");
            }
        }
        return this;
    }

    /**
     * 解除注册 Activity 生命周期事件处理
     * @return {@link ThisActivityLifecycleAssist}
     */
    public ThisActivityLifecycleAssist unregisterActivityLifecycleCallbacks() {
        if (mApplication != null) {
            try {
                mApplication.unregisterActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "unregisterActivityLifecycleCallbacks");
            }
        }
        return this;
    }

    // ================
    // = Activity 监听 =
    // ================

    // 空监听实现
    private final AbstractActivityLifecycle EMPTY_LISTENER = new AbstractActivityLifecycle() {};

    // ActivityLifecycleCallbacks 实现类, 监听 Activity
    private final ActivityLifecycleImpl ACTIVITY_LIFECYCLE = new ActivityLifecycleImpl();

    /**
     * detail: 对 Activity 的生命周期事件进行集中处理, ActivityLifecycleCallbacks 实现方法
     * @author Ttt
     */
    private class ActivityLifecycleImpl
            implements Application.ActivityLifecycleCallbacks {

        // Activity 生命周期
        private final Map<Activity, AbstractActivityLifecycle> mListenerMaps = new ConcurrentHashMap<>();

        // =============================
        // = AbstractActivityLifecycle =
        // =============================

        @Override
        public void onActivityCreated(
                @NonNull Activity activity,
                Bundle savedInstanceState
        ) {
            _of(activity).onActivityCreated(activity, savedInstanceState);
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {
            _of(activity).onActivityStarted(activity);
        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            _of(activity).onActivityResumed(activity);
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {
            _of(activity).onActivityPaused(activity);
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {
            _of(activity).onActivityStopped(activity);
        }

        @Override
        public void onActivitySaveInstanceState(
                @NonNull Activity activity,
                @NonNull Bundle outState
        ) {
            _of(activity).onActivitySaveInstanceState(activity, outState);
        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {

            _of(activity).onActivityDestroyed(activity);

            // 移除已消费的事件
            removeListener(activity);
        }

        // ==============================
        // = ActivityLifecycleCallbacks =
        // ==============================

        @Override
        public void onActivityPreCreated(
                @NonNull Activity activity,
                @Nullable Bundle savedInstanceState
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                _of(activity).onActivityPreCreated(activity, savedInstanceState);
            }
        }

        @Override
        public void onActivityPostCreated(
                @NonNull Activity activity,
                @Nullable Bundle savedInstanceState
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                _of(activity).onActivityPostCreated(activity, savedInstanceState);
            }
        }

        @Override
        public void onActivityPreStarted(@NonNull Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                _of(activity).onActivityPreStarted(activity);
            }
        }

        @Override
        public void onActivityPostStarted(@NonNull Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                _of(activity).onActivityPostStarted(activity);
            }
        }

        @Override
        public void onActivityPreResumed(@NonNull Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                _of(activity).onActivityPreResumed(activity);
            }
        }

        @Override
        public void onActivityPostResumed(@NonNull Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                _of(activity).onActivityPostResumed(activity);
            }
        }

        @Override
        public void onActivityPrePaused(@NonNull Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                _of(activity).onActivityPrePaused(activity);
            }
        }

        @Override
        public void onActivityPostPaused(@NonNull Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                _of(activity).onActivityPostPaused(activity);
            }
        }

        @Override
        public void onActivityPreStopped(@NonNull Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                _of(activity).onActivityPreStopped(activity);
            }
        }

        @Override
        public void onActivityPostStopped(@NonNull Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                _of(activity).onActivityPostStopped(activity);
            }
        }

        @Override
        public void onActivityPreSaveInstanceState(
                @NonNull Activity activity,
                @NonNull Bundle outState
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                _of(activity).onActivityPreSaveInstanceState(activity, outState);
            }
        }

        @Override
        public void onActivityPostSaveInstanceState(
                @NonNull Activity activity,
                @NonNull Bundle outState
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                _of(activity).onActivityPostSaveInstanceState(activity, outState);
            }
        }

        @Override
        public void onActivityPreDestroyed(@NonNull Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                _of(activity).onActivityPreDestroyed(activity);
            }
        }

        @Override
        public void onActivityPostDestroyed(@NonNull Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                _of(activity).onActivityPostDestroyed(activity);
            }
        }

        // ==========
        // = 内部方法 =
        // ==========

        /**
         * 获取 Activity 生命周期通知事件
         * @param activity {@link Activity}
         * @return Activity 生命周期通知事件
         */
        private AbstractActivityLifecycle _of(final Activity activity) {
            AbstractActivityLifecycle listener = ofListener(activity);
            return (listener != null) ? listener : EMPTY_LISTENER;
        }

        // =

        /**
         * 获取 Activity 生命周期通知事件
         * @param activity {@link Activity}
         * @return Activity 生命周期通知事件
         */
        public AbstractActivityLifecycle ofListener(final Activity activity) {
            if (activity == null) return null;
            return mListenerMaps.get(activity);
        }

        /**
         * 添加 Activity 生命周期通知事件
         * @param activity {@link Activity}
         * @param listener Activity 生命周期通知事件
         */
        public void addListener(
                final Activity activity,
                final AbstractActivityLifecycle listener
        ) {
            if (activity == null || listener == null) return;
            mListenerMaps.put(activity, listener);
        }

        /**
         * 移除 Activity 生命周期通知事件
         * @param activity {@link Activity}
         */
        public void removeListener(final Activity activity) {
            if (activity == null) return;
            mListenerMaps.remove(activity);
        }

        /**
         * 移除全部 Activity 生命周期通知事件
         */
        public void removeAllListener() {
            mListenerMaps.clear();
        }
    }
}