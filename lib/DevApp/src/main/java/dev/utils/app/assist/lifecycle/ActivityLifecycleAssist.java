package dev.utils.app.assist.lifecycle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.KeyBoardUtils;
import dev.utils.app.permission.PermissionUtils;

/**
 * detail: Activity 生命周期辅助类
 * @author Ttt
 * <pre>
 *     必须主动调用 {@link #registerActivityLifecycleCallbacks()} 方法进行注册监听
 * </pre>
 */
public final class ActivityLifecycleAssist {

    // 日志 TAG
    private static final String TAG = ActivityLifecycleAssist.class.getSimpleName();

    // Application 对象
    private final Application mApplication;

    // ==========
    // = 构造函数 =
    // ==========

    public ActivityLifecycleAssist() {
        this(DevUtils.getContext());
    }

    public ActivityLifecycleAssist(final Context context) {
        this(getApplication(context));
    }

    public ActivityLifecycleAssist(final Application application) {
        this.mApplication = application;
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 获取 Application
     * @param context {@link Context}
     * @return {@link Application}
     */
    private static Application getApplication(final Context context) {
        if (context == null) return null;
        try {
            return (Application) context.getApplicationContext();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getApplication");
        }
        return null;
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 Activity 生命周期 相关信息获取接口类
     * @return {@link ActivityLifecycleGet}
     */
    public ActivityLifecycleGet getActivityLifecycleGet() {
        return ACTIVITY_LIFECYCLE;
    }

    /**
     * 获取 Activity 生命周期 事件监听接口类
     * @return {@link ActivityLifecycleNotify}
     */
    public ActivityLifecycleNotify getActivityLifecycleNotify() {
        return ACTIVITY_LIFECYCLE;
    }

    /**
     * 获取 Top Activity
     * @return {@link Activity}
     */
    public Activity getTopActivity() {
        return ACTIVITY_LIFECYCLE.getTopActivity();
    }

    /**
     * 设置 Activity 生命周期 过滤判断接口
     * @param activityLifecycleFilter Activity 过滤判断接口
     * @return {@link ActivityLifecycleAssist}
     */
    public ActivityLifecycleAssist setActivityLifecycleFilter(final ActivityLifecycleFilter activityLifecycleFilter) {
        this.mActivityLifecycleFilter = activityLifecycleFilter;
        return this;
    }

    /**
     * 设置 ActivityLifecycle 监听回调
     * @param abstractActivityLifecycle Activity 生命周期监听类
     * @return {@link ActivityLifecycleAssist}
     */
    public ActivityLifecycleAssist setAbstractActivityLifecycle(final AbstractActivityLifecycle abstractActivityLifecycle) {
        this.mAbstractActivityLifecycle = abstractActivityLifecycle;
        return this;
    }

    /**
     * 注册绑定 Activity 生命周期事件处理
     * @return {@link ActivityLifecycleAssist}
     */
    public ActivityLifecycleAssist registerActivityLifecycleCallbacks() {
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
     * @return {@link ActivityLifecycleAssist}
     */
    public ActivityLifecycleAssist unregisterActivityLifecycleCallbacks() {
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

    // Activity 过滤判断接口
    private       ActivityLifecycleFilter   mActivityLifecycleFilter;
    // ActivityLifecycleCallbacks 抽象类
    private       AbstractActivityLifecycle mAbstractActivityLifecycle;
    // ActivityLifecycleCallbacks 实现类, 监听 Activity
    private final ActivityLifecycleImpl     ACTIVITY_LIFECYCLE        = new ActivityLifecycleImpl();
    // 内部 Activity 生命周期过滤处理
    private final ActivityLifecycleFilter   ACTIVITY_LIFECYCLE_FILTER = new ActivityLifecycleFilter() {
        @Override
        public boolean filter(Activity activity) {
            if (activity != null) {
                if (PermissionUtils.PERMISSION_ACTIVITY_CLASS_NAME.equals(activity.getClass().getName())) {
                    // 如果相同则不处理 ( 该页面为内部权限框架, 申请权限页面 )
                    return true;
                } else {
                    if (mActivityLifecycleFilter != null) {
                        return mActivityLifecycleFilter.filter(activity);
                    }
                }
            }
            return false;
        }
    };

    /**
     * detail: 对 Activity 的生命周期事件进行集中处理, ActivityLifecycleCallbacks 实现方法
     * @author Ttt
     */
    private class ActivityLifecycleImpl
            implements Application.ActivityLifecycleCallbacks,
            ActivityLifecycleGet,
            ActivityLifecycleNotify {

        // 保存未销毁的 Activity
        private final LinkedList<Activity>                            mActivityLists         = new LinkedList<>();
        // APP 状态改变事件
        private final Map<Object, OnAppStatusChangedListener>         mStatusListenerMaps    = new ConcurrentHashMap<>();
        // Activity 销毁事件
        private final Map<Activity, Set<OnActivityDestroyedListener>> mDestroyedListenerMaps = new ConcurrentHashMap<>();

        // 前台 Activity 总数
        private int     mForegroundCount = 0;
        // Activity Configuration 改变次数
        private int     mConfigCount     = 0;
        // 是否后台 Activity
        private boolean mBackground      = false;

        // ==============================
        // = ActivityLifecycleCallbacks =
        // ==============================

        @Override
        public void onActivityCreated(
                Activity activity,
                Bundle savedInstanceState
        ) {
            setTopActivity(activity);

            if (mAbstractActivityLifecycle != null) {
                mAbstractActivityLifecycle.onActivityCreated(activity, savedInstanceState);
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (!mBackground) {
                setTopActivity(activity);
            }
            if (mConfigCount < 0) {
                ++mConfigCount;
            } else {
                ++mForegroundCount;
            }

            if (mAbstractActivityLifecycle != null) {
                mAbstractActivityLifecycle.onActivityStarted(activity);
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivity(activity);
            // Activity 准备可见, 设置为非后台 Activity
            if (mBackground) {
                mBackground = false;
                postStatus(true);
            }

            if (mAbstractActivityLifecycle != null) {
                mAbstractActivityLifecycle.onActivityResumed(activity);
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            if (mAbstractActivityLifecycle != null) {
                mAbstractActivityLifecycle.onActivityPaused(activity);
            }
        }

        @Override
        public void onActivityStopped(Activity activity) {
            // 检测当前的 Activity 是否因为 Configuration 的改变被销毁了
            if (activity.isChangingConfigurations()) {
                --mConfigCount;
            } else {
                --mForegroundCount;
                if (mForegroundCount <= 0) {
                    mBackground = true;
                    postStatus(false);
                }
            }

            if (mAbstractActivityLifecycle != null) {
                mAbstractActivityLifecycle.onActivityStopped(activity);
            }
        }

        @Override
        public void onActivitySaveInstanceState(
                Activity activity,
                Bundle outState
        ) {
            if (mAbstractActivityLifecycle != null) {
                mAbstractActivityLifecycle.onActivitySaveInstanceState(activity, outState);
            }
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            mActivityLists.remove(activity);
            // 通知 Activity 销毁
            consumeOnActivityDestroyedListener(activity);
            // 修复软键盘内存泄漏 在 Activity.onDestroy() 中使用
            KeyBoardUtils.fixSoftInputLeaks(activity);

            if (mAbstractActivityLifecycle != null) {
                mAbstractActivityLifecycle.onActivityDestroyed(activity);
            }
        }

        // ==========
        // = 内部方法 =
        // ==========

        /**
         * 保存 Activity 栈顶
         * @param activity {@link Activity}
         */
        private void setTopActivity(final Activity activity) {
            if (activity == null) return;
            // 判断是否过滤 Activity
            if (ACTIVITY_LIFECYCLE_FILTER.filter(activity)) return;
            // 判断是否已经包含该 Activity
            if (mActivityLists.contains(activity)) {
                if (!activity.equals(mActivityLists.getLast())) {
                    mActivityLists.remove(activity);
                    mActivityLists.addLast(activity);
                }
            } else {
                mActivityLists.addLast(activity);
            }
        }

        /**
         * 反射获取栈顶 Activity
         * @return {@link Activity}
         */
        private Activity getTopActivityByReflect() {
            try {
                @SuppressLint("PrivateApi")
                Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
                Object activityThread  = activityThreadClass.getMethod("currentActivityThread").invoke(null);
                Field  activitiesField = activityThreadClass.getDeclaredField("mActivityLists");
                activitiesField.setAccessible(true);
                Map<?, ?> activities = (Map<?, ?>) activitiesField.get(activityThread);
                if (activities == null) return null;
                for (Object activityRecord : activities.values()) {
                    Class<?> activityRecordClass = activityRecord.getClass();
                    Field    pausedField         = activityRecordClass.getDeclaredField("paused");
                    pausedField.setAccessible(true);
                    if (!pausedField.getBoolean(activityRecord)) {
                        Field activityField = activityRecordClass.getDeclaredField("activity");
                        activityField.setAccessible(true);
                        return (Activity) activityField.get(activityRecord);
                    }
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getTopActivityByReflect");
            }
            return null;
        }

        // ============================
        // = ActivityLifecycleGet 方法 =
        // ============================

        /**
         * 获取最顶部 ( 当前或最后一个显示 ) Activity
         * @return {@link Activity}
         */
        @Override
        public Activity getTopActivity() {
            if (!mActivityLists.isEmpty()) {
                final Activity topActivity = mActivityLists.getLast();
                if (topActivity != null) {
                    return topActivity;
                }
            }
            Activity topActivityByReflect = getTopActivityByReflect();
            if (topActivityByReflect != null) {
                setTopActivity(topActivityByReflect);
            }
            return topActivityByReflect;
        }

        /**
         * 判断某个 Activity 是否 Top Activity
         * @param activityClassName Activity.class.getCanonicalName()
         * @return {@code true} yes, {@code false} no
         */
        @Override
        public boolean isTopActivity(final String activityClassName) {
            if (!TextUtils.isEmpty(activityClassName)) {
                try {
                    Activity activity = getTopActivity();
                    if (activity == null) return false;
                    // 判断是否类是否一致
                    return activityClassName.equals(
                            activity.getClass().getCanonicalName()
                    );
                } catch (Exception ignored) {
                }
            }
            return false;
        }

        /**
         * 判断某个 Class(Activity) 是否 Top Activity
         * @param clazz Activity.class or this.getClass()
         * @return {@code true} yes, {@code false} no
         */
        @Override
        public boolean isTopActivity(final Class<?> clazz) {
            if (clazz != null) {
                try {
                    Activity activity = getTopActivity();
                    if (activity == null || activity.getClass().getCanonicalName() == null) {
                        return false;
                    }
                    // 判断是否类是否一致
                    return clazz.getCanonicalName().equals(
                            activity.getClass().getCanonicalName()
                    );
                } catch (Exception ignored) {
                }
            }
            return false;
        }

        /**
         * 判断应用是否在后台 ( 不可见 )
         * @return {@code true} yes, {@code false} no
         */
        @Override
        public boolean isBackground() {
            return mBackground;
        }

        /**
         * 获取 Activity 总数
         * @return 已打开 Activity 总数
         */
        @Override
        public int getActivityCount() {
            return mActivityLists.size();
        }

        // ===========================
        // = ActivityLifecycleNotify =
        // ===========================

        /**
         * 添加 APP 状态改变事件监听
         * @param object   key
         * @param listener APP 状态改变监听事件
         */
        @Override
        public void addOnAppStatusChangedListener(
                final Object object,
                final OnAppStatusChangedListener listener
        ) {
            mStatusListenerMaps.put(object, listener);
        }

        /**
         * 移除 APP 状态改变事件监听
         * @param object key
         */
        @Override
        public void removeOnAppStatusChangedListener(final Object object) {
            mStatusListenerMaps.remove(object);
        }

        /**
         * 移除全部 APP 状态改变事件监听
         */
        @Override
        public void removeAllOnAppStatusChangedListener() {
            mStatusListenerMaps.clear();
        }

        // =

        /**
         * 添加 Activity 销毁通知事件
         * @param activity {@link Activity}
         * @param listener Activity 销毁通知事件
         */
        @Override
        public void addOnActivityDestroyedListener(
                final Activity activity,
                final OnActivityDestroyedListener listener
        ) {
            if (activity == null || listener == null) return;
            Set<OnActivityDestroyedListener> listeners;
            if (!mDestroyedListenerMaps.containsKey(activity)) {
                listeners = new HashSet<>();
                mDestroyedListenerMaps.put(activity, listeners);
            } else {
                listeners = mDestroyedListenerMaps.get(activity);
                if (listeners.contains(listener)) return;
            }
            listeners.add(listener);
        }

        /**
         * 移除 Activity 销毁通知事件
         * @param activity {@link Activity}
         */
        @Override
        public void removeOnActivityDestroyedListener(final Activity activity) {
            if (activity == null) return;
            mDestroyedListenerMaps.remove(activity);
        }

        /**
         * 移除全部 Activity 销毁通知事件
         */
        @Override
        public void removeAllOnActivityDestroyedListener() {
            mDestroyedListenerMaps.clear();
        }

        // =============
        // = 事件通知相关 =
        // =============

        /**
         * 发送状态改变通知
         * @param isForeground 是否在前台
         */
        private void postStatus(final boolean isForeground) {
            if (mStatusListenerMaps.isEmpty()) return;
            // 保存到新的集合, 防止 ConcurrentModificationException
            List<OnAppStatusChangedListener> lists = new ArrayList<>(mStatusListenerMaps.values());
            // 遍历通知
            for (OnAppStatusChangedListener listener : lists) {
                if (listener != null) {
                    if (isForeground) {
                        listener.onForeground();
                    } else {
                        listener.onBackground();
                    }
                }
            }
        }

        /**
         * 通知 Activity 销毁, 并且消费 ( 移除 ) 监听事件
         * @param activity {@link Activity}
         */
        private void consumeOnActivityDestroyedListener(final Activity activity) {
            try {
                // 保存到新的集合, 防止 ConcurrentModificationException
                Set<OnActivityDestroyedListener> sets = new HashSet<>(mDestroyedListenerMaps.get(activity));
                // 遍历通知
                for (OnActivityDestroyedListener listener : sets) {
                    if (listener != null) {
                        listener.onActivityDestroyed(activity);
                    }
                }
            } catch (Exception ignored) {
            }
            // 移除已消费的事件
            removeOnActivityDestroyedListener(activity);
        }
    }
}