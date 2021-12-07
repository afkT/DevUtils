package dev.utils.app.assist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.fragment.app.DialogFragment;

import dev.utils.app.ActivityUtils;

/**
 * detail: Window 辅助类
 * @author Ttt
 */
public final class WindowAssist {

    // ==========
    // = 静态方法 =
    // ==========

    /**
     * 获取 Window
     * @param context {@link Context}
     * @return {@link Window}
     */
    public static Window getWindow(final Context context) {
        return getWindow(ActivityUtils.getActivity(context));
    }

    /**
     * 获取 Window
     * @param activity {@link Activity}
     * @return {@link Window}
     */
    public static Window getWindow(final Activity activity) {
        return (activity != null) ? activity.getWindow() : null;
    }

    /**
     * 获取 Window
     * @param dialog {@link Dialog}
     * @return {@link Window}
     */
    public static Window getWindow(final Dialog dialog) {
        return (dialog != null) ? dialog.getWindow() : null;
    }

    /**
     * 获取 Window
     * @param dialog {@link DialogFragment}
     * @return {@link Window}
     */
    public static Window getWindow(final DialogFragment dialog) {
        if (dialog == null) return null;
        return getWindow(dialog.getDialog());
    }

    /**
     * 获取 Window
     * @param dialog {@link android.app.DialogFragment}
     * @return {@link Window}
     */
    public static Window getWindow(final android.app.DialogFragment dialog) {
        if (dialog == null) return null;
        return getWindow(dialog.getDialog());
    }

    // ===================
    // = WindowUtils 使用 =
    // ===================

    // WindowAssist 实例
    private static volatile WindowAssist sInstance;

    /**
     * 获取 WindowAssist 实例
     * @return {@link WindowAssist}
     */
    public static WindowAssist getInstance() {
        if (sInstance == null) {
            synchronized (WindowAssist.class) {
                if (sInstance == null) {
                    sInstance = new WindowAssist((Window) null);
                }
            }
        }
        return sInstance;
    }

    // ===============
    // = 辅助类具体实现 =
    // ===============

    // 日志 TAG
    private final String TAG = WindowAssist.class.getSimpleName();
    // 内部 Window
    private final Window mWindow;

    // ==========
    // = 构造函数 =
    // ==========

    public WindowAssist(final Window window) {
        this.mWindow = window;
    }

    public WindowAssist(final Context context) {
        this(getWindow(context));
    }

    public WindowAssist(final Activity activity) {
        this(getWindow(activity));
    }

    public WindowAssist(final Dialog dialog) {
        this(getWindow(dialog));
    }

    public WindowAssist(final DialogFragment dialog) {
        this(getWindow(dialog));
    }

    public WindowAssist(final android.app.DialogFragment dialog) {
        this(getWindow(dialog));
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 Window
     * @return {@link Window}
     */
    public Window getWindow() {
        return mWindow;
    }
}