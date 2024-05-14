package dev.utils.app.assist.lifecycle.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import dev.utils.LogPrintUtils;
import dev.utils.common.able.Getable;

/**
 * detail: Fragment 生命周期辅助类
 * @author Ttt
 * <pre>
 *     必须主动调用 {@link #registerFragmentLifecycleCallbacks()} 方法进行注册监听
 * </pre>
 */
public final class FragmentLifecycleAssist {

    // 日志 TAG
    private static final String                       TAG = FragmentLifecycleAssist.class.getSimpleName();
    // FragmentManager Getable
    private final        Getable.Get<FragmentManager> mGetAble;

    // ==========
    // = 构造函数 =
    // ==========

    public FragmentLifecycleAssist(final FragmentManager manager) {
        this.mGetAble = () -> manager;
    }

    public FragmentLifecycleAssist(final Getable.Get<FragmentManager> getAble) {
        this.mGetAble = getAble;
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 设置 Fragment 生命周期 过滤判断接口
     * @param fragmentLifecycleFilter Fragment 过滤判断接口
     * @return {@link FragmentLifecycleAssist}
     */
    public FragmentLifecycleAssist setFragmentLifecycleFilter(final FragmentLifecycleFilter fragmentLifecycleFilter) {
        this.mFragmentLifecycleFilter = fragmentLifecycleFilter;
        return this;
    }

    /**
     * 设置 FragmentLifecycle 监听回调
     * @param abstractFragmentLifecycle Fragment 生命周期监听类
     * @return {@link FragmentLifecycleAssist}
     */
    public FragmentLifecycleAssist setAbstractFragmentLifecycle(final AbstractFragmentLifecycle abstractFragmentLifecycle) {
        this.mAbstractFragmentLifecycle = abstractFragmentLifecycle;
        return this;
    }

    // =

    /**
     * 注册绑定 Fragment 生命周期事件处理
     * @return {@link FragmentLifecycleAssist}
     */
    public FragmentLifecycleAssist registerFragmentLifecycleCallbacks() {
        return registerFragmentLifecycleCallbacks(false);
    }

    /**
     * 注册绑定 Fragment 生命周期事件处理
     * @param recursive 是否为所有子 FragmentManager 注册此监听
     * @return {@link FragmentLifecycleAssist}
     */
    public FragmentLifecycleAssist registerFragmentLifecycleCallbacks(final boolean recursive) {
        // 先移除监听
        unregisterFragmentLifecycleCallbacks();
        if (mGetAble != null) {
            try {
                mGetAble.get().registerFragmentLifecycleCallbacks(FRAGMENT_LIFECYCLE, recursive);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "registerFragmentLifecycleCallbacks");
            }
        }
        return this;
    }

    /**
     * 解除注册 Fragment 生命周期事件处理
     * @return {@link FragmentLifecycleAssist}
     */
    public FragmentLifecycleAssist unregisterFragmentLifecycleCallbacks() {
        if (mGetAble != null) {
            try {
                mGetAble.get().unregisterFragmentLifecycleCallbacks(FRAGMENT_LIFECYCLE);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "unregisterFragmentLifecycleCallbacks");
            }
        }
        return this;
    }

    // ================
    // = Fragment 监听 =
    // ================

    // Fragment LifecycleCallbacks 抽象类
    private       AbstractFragmentLifecycle mAbstractFragmentLifecycle;
    // Fragment 过滤判断接口
    private       FragmentLifecycleFilter   mFragmentLifecycleFilter;
    // FragmentLifecycleCallbacks 实现类, 监听 Fragment
    private final FragmentLifecycleImpl     FRAGMENT_LIFECYCLE        = new FragmentLifecycleImpl();
    // 内部 Fragment 生命周期过滤处理
    private final FragmentLifecycleFilter   FRAGMENT_LIFECYCLE_FILTER = new FragmentLifecycleFilter() {
        @Override
        public boolean filter(
                FragmentManager manager,
                Fragment fragment
        ) {
            if (mFragmentLifecycleFilter != null) {
                return mFragmentLifecycleFilter.filter(manager, fragment);
            }
            return false;
        }
    };

    /**
     * detail: 对 Fragment 的生命周期事件进行集中处理, FragmentLifecycleCallbacks 实现方法
     * @author Ttt
     */
    private class FragmentLifecycleImpl
            extends FragmentManager.FragmentLifecycleCallbacks {

        // ============
        // = Override =
        // ============

        @Override
        public void onFragmentPreAttached(
                @NonNull FragmentManager fm,
                @NonNull Fragment f,
                @NonNull Context context
        ) {
            super.onFragmentPreAttached(fm, f, context);

            // 判断是否过滤 Fragment
            if (FRAGMENT_LIFECYCLE_FILTER.filter(fm, f)) return;

            if (mAbstractFragmentLifecycle != null) {
                mAbstractFragmentLifecycle.onFragmentPreAttached(fm, f, context);
            }
        }

        @Override
        public void onFragmentAttached(
                @NonNull FragmentManager fm,
                @NonNull Fragment f,
                @NonNull Context context
        ) {
            super.onFragmentAttached(fm, f, context);

            // 判断是否过滤 Fragment
            if (FRAGMENT_LIFECYCLE_FILTER.filter(fm, f)) return;

            if (mAbstractFragmentLifecycle != null) {
                mAbstractFragmentLifecycle.onFragmentAttached(fm, f, context);
            }
        }

        @Override
        public void onFragmentPreCreated(
                @NonNull FragmentManager fm,
                @NonNull Fragment f,
                @Nullable Bundle savedInstanceState
        ) {
            super.onFragmentPreCreated(fm, f, savedInstanceState);

            // 判断是否过滤 Fragment
            if (FRAGMENT_LIFECYCLE_FILTER.filter(fm, f)) return;

            if (mAbstractFragmentLifecycle != null) {
                mAbstractFragmentLifecycle.onFragmentPreCreated(fm, f, savedInstanceState);
            }
        }

        @Override
        public void onFragmentCreated(
                @NonNull FragmentManager fm,
                @NonNull Fragment f,
                @Nullable Bundle savedInstanceState
        ) {
            super.onFragmentCreated(fm, f, savedInstanceState);

            // 判断是否过滤 Fragment
            if (FRAGMENT_LIFECYCLE_FILTER.filter(fm, f)) return;

            if (mAbstractFragmentLifecycle != null) {
                mAbstractFragmentLifecycle.onFragmentCreated(fm, f, savedInstanceState);
            }
        }

        @Override
        public void onFragmentActivityCreated(
                @NonNull FragmentManager fm,
                @NonNull Fragment f,
                @Nullable Bundle savedInstanceState
        ) {
            super.onFragmentActivityCreated(fm, f, savedInstanceState);

            // 判断是否过滤 Fragment
            if (FRAGMENT_LIFECYCLE_FILTER.filter(fm, f)) return;

            if (mAbstractFragmentLifecycle != null) {
                mAbstractFragmentLifecycle.onFragmentActivityCreated(fm, f, savedInstanceState);
            }
        }

        @Override
        public void onFragmentViewCreated(
                @NonNull FragmentManager fm,
                @NonNull Fragment f,
                @NonNull View v,
                @Nullable Bundle savedInstanceState
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState);

            // 判断是否过滤 Fragment
            if (FRAGMENT_LIFECYCLE_FILTER.filter(fm, f)) return;

            if (mAbstractFragmentLifecycle != null) {
                mAbstractFragmentLifecycle.onFragmentViewCreated(fm, f, v, savedInstanceState);
            }
        }

        @Override
        public void onFragmentStarted(
                @NonNull FragmentManager fm,
                @NonNull Fragment f
        ) {
            super.onFragmentStarted(fm, f);

            // 判断是否过滤 Fragment
            if (FRAGMENT_LIFECYCLE_FILTER.filter(fm, f)) return;

            if (mAbstractFragmentLifecycle != null) {
                mAbstractFragmentLifecycle.onFragmentStarted(fm, f);
            }
        }

        @Override
        public void onFragmentResumed(
                @NonNull FragmentManager fm,
                @NonNull Fragment f
        ) {
            super.onFragmentResumed(fm, f);

            // 判断是否过滤 Fragment
            if (FRAGMENT_LIFECYCLE_FILTER.filter(fm, f)) return;

            if (mAbstractFragmentLifecycle != null) {
                mAbstractFragmentLifecycle.onFragmentResumed(fm, f);
            }
        }

        @Override
        public void onFragmentPaused(
                @NonNull FragmentManager fm,
                @NonNull Fragment f
        ) {
            super.onFragmentPaused(fm, f);

            // 判断是否过滤 Fragment
            if (FRAGMENT_LIFECYCLE_FILTER.filter(fm, f)) return;

            if (mAbstractFragmentLifecycle != null) {
                mAbstractFragmentLifecycle.onFragmentPaused(fm, f);
            }
        }

        @Override
        public void onFragmentStopped(
                @NonNull FragmentManager fm,
                @NonNull Fragment f
        ) {
            super.onFragmentStopped(fm, f);

            // 判断是否过滤 Fragment
            if (FRAGMENT_LIFECYCLE_FILTER.filter(fm, f)) return;

            if (mAbstractFragmentLifecycle != null) {
                mAbstractFragmentLifecycle.onFragmentStopped(fm, f);
            }
        }

        @Override
        public void onFragmentSaveInstanceState(
                @NonNull FragmentManager fm,
                @NonNull Fragment f,
                @NonNull Bundle outState
        ) {
            super.onFragmentSaveInstanceState(fm, f, outState);

            // 判断是否过滤 Fragment
            if (FRAGMENT_LIFECYCLE_FILTER.filter(fm, f)) return;

            if (mAbstractFragmentLifecycle != null) {
                mAbstractFragmentLifecycle.onFragmentSaveInstanceState(fm, f, outState);
            }
        }

        @Override
        public void onFragmentViewDestroyed(
                @NonNull FragmentManager fm,
                @NonNull Fragment f
        ) {
            super.onFragmentViewDestroyed(fm, f);

            // 判断是否过滤 Fragment
            if (FRAGMENT_LIFECYCLE_FILTER.filter(fm, f)) return;

            if (mAbstractFragmentLifecycle != null) {
                mAbstractFragmentLifecycle.onFragmentViewDestroyed(fm, f);
            }
        }

        @Override
        public void onFragmentDestroyed(
                @NonNull FragmentManager fm,
                @NonNull Fragment f
        ) {
            super.onFragmentDestroyed(fm, f);

            // 判断是否过滤 Fragment
            if (FRAGMENT_LIFECYCLE_FILTER.filter(fm, f)) return;

            if (mAbstractFragmentLifecycle != null) {
                mAbstractFragmentLifecycle.onFragmentDestroyed(fm, f);
            }
        }

        @Override
        public void onFragmentDetached(
                @NonNull FragmentManager fm,
                @NonNull Fragment f
        ) {
            super.onFragmentDetached(fm, f);

            // 判断是否过滤 Fragment
            if (FRAGMENT_LIFECYCLE_FILTER.filter(fm, f)) return;

            if (mAbstractFragmentLifecycle != null) {
                mAbstractFragmentLifecycle.onFragmentDetached(fm, f);
            }
        }
    }
}