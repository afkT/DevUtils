package dev.utils.app;

import android.app.Activity;
import android.view.View;
import android.view.Window;

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

    /**
     * 获取 View 设置的 OnTouchListener
     * @param view
     * @return
     */
    public static View.OnTouchListener getTouchListener(final View view) {
        return (View.OnTouchListener) getListenerInfoListener(view, "mOnTouchListener");
    }

    /**
     * 获取 View ListenerInfo 对象(内部类)
     * @param view
     * @return
     */
    public static Object getListenerInfo(final View view) {
        try {
            // 获取 ListenerInfo 对象
            Field infoField = View.class.getDeclaredField("mListenerInfo");
            infoField.setAccessible(true);
            return infoField.get(view);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getListenerInfo");
        }
        return null;
    }

    /**
     * 获取 View ListenerInfo 对象内部事件对象
     * @param view
     * @param listener
     * @return
     */
    public static Object getListenerInfoListener(final View view, final String listener) {
        try {
            // 获取 ListenerInfo 对象
            Object listenerInfo = getListenerInfo(view);
            // 获取 ListenerInfo 对象中的 mOnTouchListener 属性
            Class infoClazz = Class.forName("android.view.View$ListenerInfo");
            Field listenerField = infoClazz.getDeclaredField(listener);
            listenerField.setAccessible(true);
            // 进行获取返回
            return listenerField.get(listenerInfo);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getListenerInfoListener");
        }
        return null;
    }

    // =

    /**
     * 设置点击事件
     * @param view
     * @param onClickListener
     * @param viewIds
     */
    public static void setOnClicks(final View view, final View.OnClickListener onClickListener, final int... viewIds) {
        if (view != null && onClickListener != null && viewIds != null) {
            for (int i = 0, len = viewIds.length; i < len; i++) {
                View findView = findViewById(view, viewIds[i]);
                if (findView != null) {
                    findView.setOnClickListener(onClickListener);
                }
            }
        }
    }

    /**
     * 设置点击事件
     * @param activity
     * @param onClickListener
     * @param viewIds
     */
    public static void setOnClicks(final Activity activity, final View.OnClickListener onClickListener, final int... viewIds) {
        if (activity != null && onClickListener != null && viewIds != null) {
            for (int i = 0, len = viewIds.length; i < len; i++) {
                View findView = findViewById(activity, viewIds[i]);
                if (findView != null) {
                    findView.setOnClickListener(onClickListener);
                }
            }
        }
    }

    /**
     * 设置点击事件
     * @param onClickListener
     * @param views
     */
    public static void setOnClicks(final View.OnClickListener onClickListener, final View... views) {
        if (onClickListener != null && views != null) {
            for (int i = 0, len = views.length; i < len; i++) {
                if (views[i] != null) {
                    views[i].setOnClickListener(onClickListener);
                }
            }
        }
    }

    // ====================
    // = 初始化View操作等 =
    // ====================

    /**
     * 初始化View
     * @param view
     * @param id
     * @param <T>  泛型
     * @return
     */
    private static <T extends View> T findViewById(final View view, final int id) {
        try {
            return view.findViewById(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "findViewById");
        }
        return null;
    }

    /**
     * 初始化View
     * @param window
     * @param id
     * @param <T>    泛型
     * @return
     */
    private static <T extends View> T findViewById(final Window window, final int id) {
        try {
            return window.findViewById(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "findViewById");
        }
        return null;
    }

    /**
     * 初始化View
     * @param activity
     * @param id
     * @param <T>      泛型
     * @return
     */
    private static <T extends View> T findViewById(final Activity activity, final int id) {
        try {
            return activity.findViewById(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "findViewById");
        }
        return null;
    }
}
