package dev.utils.app;

import android.app.Activity;
import android.view.View;

import androidx.annotation.IdRes;

import java.lang.reflect.Field;

import dev.utils.LogPrintUtils;

/**
 * detail: 事件工具类
 * @author Ttt
 */
public final class ListenerUtils {

    private ListenerUtils() {
    }

    // 日志 TAG
    private static final String TAG = ListenerUtils.class.getSimpleName();

    // ========
    // = Hook =
    // ========

    /**
     * 获取 View 设置的 OnTouchListener 事件对象
     * @param view {@link View}
     * @return {@link View.OnTouchListener}
     */
    public static View.OnTouchListener getTouchListener(final View view) {
        return (View.OnTouchListener) getListenerInfoListener(
                view, "mOnTouchListener"
        );
    }

    /**
     * 获取 View 设置的 OnClickListener 事件对象
     * @param view {@link View}
     * @return {@link View.OnClickListener}
     */
    public static View.OnClickListener getClickListener(final View view) {
        return (View.OnClickListener) getListenerInfoListener(
                view, "mOnClickListener"
        );
    }

    // =

    /**
     * 获取 View ListenerInfo 对象 ( 内部类 )
     * @param view {@link View}
     * @return ListenerInfo
     */
    public static Object getListenerInfo(final View view) {
        try {
            // 获取 ListenerInfo 对象
            Field field = View.class.getDeclaredField("mListenerInfo");
            field.setAccessible(true);
            return field.get(view);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getListenerInfo");
        }
        return null;
    }

    /**
     * 获取 View ListenerInfo 对象内部事件对象
     * @param view     {@link View}
     * @param listener 事件名
     * @return 指定事件名事件对象
     */
    public static Object getListenerInfoListener(
            final View view,
            final String listener
    ) {
        try {
            // 获取 ListenerInfo 对象
            Object listenerInfo = getListenerInfo(view);
            // 获取 ListenerInfo 对象中的 mOnTouchListener 属性
            Class<?> clazz = Class.forName("android.view.View$ListenerInfo");
            Field    field = clazz.getDeclaredField(listener);
            field.setAccessible(true);
            // 进行获取返回
            return field.get(listenerInfo);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getListenerInfoListener");
        }
        return null;
    }

    // ==========
    // = 常见事件 =
    // ==========

    // =======
    // = 点击 =
    // =======

    /**
     * 设置点击事件
     * @param view     {@link View}
     * @param listener {@link View.OnClickListener}
     * @param viewIds  ViewId[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnClicks(
            final View view,
            final View.OnClickListener listener,
            @IdRes final int... viewIds
    ) {
        if (view != null && viewIds != null) {
            for (int viewId : viewIds) {
                View findView = ViewUtils.findViewById(view, viewId);
                if (findView != null) {
                    findView.setOnClickListener(listener);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置点击事件
     * @param activity {@link Activity}
     * @param listener {@link View.OnClickListener}
     * @param viewIds  ViewId[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnClicks(
            final Activity activity,
            final View.OnClickListener listener,
            @IdRes final int... viewIds
    ) {
        if (activity != null && viewIds != null) {
            for (int viewId : viewIds) {
                View findView = ViewUtils.findViewById(activity, viewId);
                if (findView != null) {
                    findView.setOnClickListener(listener);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置点击事件
     * @param listener {@link View.OnClickListener}
     * @param views    View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnClicks(
            final View.OnClickListener listener,
            final View... views
    ) {
        if (listener != null && views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setOnClickListener(listener);
                }
            }
            return true;
        }
        return false;
    }

    // =======
    // = 长按 =
    // =======

    /**
     * 设置长按事件
     * @param view     {@link View}
     * @param listener {@link View.OnLongClickListener}
     * @param viewIds  ViewId[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnLongClicks(
            final View view,
            final View.OnLongClickListener listener,
            @IdRes final int... viewIds
    ) {
        if (view != null && viewIds != null) {
            for (int viewId : viewIds) {
                View findView = ViewUtils.findViewById(view, viewId);
                if (findView != null) {
                    findView.setOnLongClickListener(listener);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置长按事件
     * @param activity {@link Activity}
     * @param listener {@link View.OnLongClickListener}
     * @param viewIds  ViewId[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnLongClicks(
            final Activity activity,
            final View.OnLongClickListener listener,
            @IdRes final int... viewIds
    ) {
        if (activity != null && viewIds != null) {
            for (int viewId : viewIds) {
                View findView = ViewUtils.findViewById(activity, viewId);
                if (findView != null) {
                    findView.setOnLongClickListener(listener);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置长按事件
     * @param listener {@link View.OnLongClickListener}
     * @param views    View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnLongClicks(
            final View.OnLongClickListener listener,
            final View... views
    ) {
        if (listener != null && views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setOnLongClickListener(listener);
                }
            }
            return true;
        }
        return false;
    }

    // =======
    // = 触摸 =
    // =======

    /**
     * 设置触摸事件
     * @param view     {@link View}
     * @param listener {@link View.OnTouchListener}
     * @param viewIds  ViewId[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnTouchs(
            final View view,
            final View.OnTouchListener listener,
            @IdRes final int... viewIds
    ) {
        if (view != null && viewIds != null) {
            for (int viewId : viewIds) {
                View findView = ViewUtils.findViewById(view, viewId);
                if (findView != null) {
                    findView.setOnTouchListener(listener);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置触摸事件
     * @param activity {@link Activity}
     * @param listener {@link View.OnTouchListener}
     * @param viewIds  ViewId[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnTouchs(
            final Activity activity,
            final View.OnTouchListener listener,
            @IdRes final int... viewIds
    ) {
        if (activity != null && viewIds != null) {
            for (int viewId : viewIds) {
                View findView = ViewUtils.findViewById(activity, viewId);
                if (findView != null) {
                    findView.setOnTouchListener(listener);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置触摸事件
     * @param listener {@link View.OnTouchListener}
     * @param views    View[]
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnTouchs(
            final View.OnTouchListener listener,
            final View... views
    ) {
        if (listener != null && views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setOnTouchListener(listener);
                }
            }
            return true;
        }
        return false;
    }
}