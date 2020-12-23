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
        return (View.OnTouchListener) getListenerInfoListener(view, "mOnTouchListener");
    }

    /**
     * 获取 View 设置的 OnClickListener 事件对象
     * @param view {@link View}
     * @return {@link View.OnClickListener}
     */
    public static View.OnClickListener getClickListener(final View view) {
        return (View.OnClickListener) getListenerInfoListener(view, "mOnClickListener");
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
            Class clazz = Class.forName("android.view.View$ListenerInfo");
            Field field = clazz.getDeclaredField(listener);
            field.setAccessible(true);
            // 进行获取返回
            return field.get(listenerInfo);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getListenerInfoListener");
        }
        return null;
    }

    // ===============
    // = 设置点击事件 =
    // ===============

    /**
     * 设置点击事件
     * @param view            {@link View}
     * @param onClickListener {@link View.OnClickListener}
     * @param viewIds         View id 数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnClicks(
            final View view,
            final View.OnClickListener onClickListener,
            @IdRes final int... viewIds
    ) {
        if (view != null && onClickListener != null && viewIds != null) {
            for (int i = 0, len = viewIds.length; i < len; i++) {
                View findView = ViewUtils.findViewById(view, viewIds[i]);
                if (findView != null) {
                    findView.setOnClickListener(onClickListener);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置点击事件
     * @param activity        {@link Activity}
     * @param onClickListener {@link View.OnClickListener}
     * @param viewIds         View id 数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnClicks(
            final Activity activity,
            final View.OnClickListener onClickListener,
            @IdRes final int... viewIds
    ) {
        if (activity != null && onClickListener != null && viewIds != null) {
            for (int i = 0, len = viewIds.length; i < len; i++) {
                View findView = ViewUtils.findViewById(activity, viewIds[i]);
                if (findView != null) {
                    findView.setOnClickListener(onClickListener);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置点击事件
     * @param onClickListener {@link View.OnClickListener}
     * @param views           View 数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnClicks(
            final View.OnClickListener onClickListener,
            final View... views
    ) {
        if (onClickListener != null && views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                if (views[i] != null) {
                    views[i].setOnClickListener(onClickListener);
                }
            }
            return true;
        }
        return false;
    }

    // ===============
    // = 设置长按事件 =
    // ===============

    /**
     * 设置长按事件
     * @param view                {@link View}
     * @param onLongClickListener {@link View.OnLongClickListener}
     * @param viewIds             View id 数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnLongClicks(
            final View view,
            final View.OnLongClickListener onLongClickListener,
            @IdRes final int... viewIds
    ) {
        if (view != null && onLongClickListener != null && viewIds != null) {
            for (int i = 0, len = viewIds.length; i < len; i++) {
                View findView = ViewUtils.findViewById(view, viewIds[i]);
                if (findView != null) {
                    findView.setOnLongClickListener(onLongClickListener);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置长按事件
     * @param activity            {@link Activity}
     * @param onLongClickListener {@link View.OnLongClickListener}
     * @param viewIds             View id 数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnLongClicks(
            final Activity activity,
            final View.OnLongClickListener onLongClickListener,
            @IdRes final int... viewIds
    ) {
        if (activity != null && onLongClickListener != null && viewIds != null) {
            for (int i = 0, len = viewIds.length; i < len; i++) {
                View findView = ViewUtils.findViewById(activity, viewIds[i]);
                if (findView != null) {
                    findView.setOnLongClickListener(onLongClickListener);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置长按事件
     * @param onLongClickListener {@link View.OnLongClickListener}
     * @param views               View 数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnLongClicks(
            final View.OnLongClickListener onLongClickListener,
            final View... views
    ) {
        if (onLongClickListener != null && views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                if (views[i] != null) {
                    views[i].setOnLongClickListener(onLongClickListener);
                }
            }
            return true;
        }
        return false;
    }

    // ===============
    // = 设置触摸事件 =
    // ===============

    /**
     * 设置触摸事件
     * @param view            {@link View}
     * @param onTouchListener {@link View.OnTouchListener}
     * @param viewIds         View id 数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnTouchs(
            final View view,
            final View.OnTouchListener onTouchListener,
            @IdRes final int... viewIds
    ) {
        if (view != null && onTouchListener != null && viewIds != null) {
            for (int i = 0, len = viewIds.length; i < len; i++) {
                View findView = ViewUtils.findViewById(view, viewIds[i]);
                if (findView != null) {
                    findView.setOnTouchListener(onTouchListener);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置触摸事件
     * @param activity        {@link Activity}
     * @param onTouchListener {@link View.OnTouchListener}
     * @param viewIds         View id 数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnTouchs(
            final Activity activity,
            final View.OnTouchListener onTouchListener,
            @IdRes final int... viewIds
    ) {
        if (activity != null && onTouchListener != null && viewIds != null) {
            for (int i = 0, len = viewIds.length; i < len; i++) {
                View findView = ViewUtils.findViewById(activity, viewIds[i]);
                if (findView != null) {
                    findView.setOnTouchListener(onTouchListener);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 设置触摸事件
     * @param onTouchListener {@link View.OnTouchListener}
     * @param views           View 数组
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setOnTouchs(
            final View.OnTouchListener onTouchListener,
            final View... views
    ) {
        if (onTouchListener != null && views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                if (views[i] != null) {
                    views[i].setOnTouchListener(onTouchListener);
                }
            }
            return true;
        }
        return false;
    }
}