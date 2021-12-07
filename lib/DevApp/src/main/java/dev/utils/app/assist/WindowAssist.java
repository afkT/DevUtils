package dev.utils.app.assist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

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

    // =======
    // = get =
    // =======

    /**
     * 获取 WindowAssist
     * @param window {@link Context}
     * @return {@link WindowAssist}
     */
    public static WindowAssist get(final Window window) {
        return new WindowAssist(window);
    }

    /**
     * 获取 WindowAssist
     * @param context {@link Context}
     * @return {@link WindowAssist}
     */
    public static WindowAssist get(final Context context) {
        return new WindowAssist(context);
    }

    /**
     * 获取 WindowAssist
     * @param activity {@link Activity}
     * @return {@link WindowAssist}
     */
    public static WindowAssist get(final Activity activity) {
        return new WindowAssist(activity);
    }

    /**
     * 获取 WindowAssist
     * @param dialog {@link Dialog}
     * @return {@link WindowAssist}
     */
    public static WindowAssist get(final Dialog dialog) {
        return new WindowAssist(dialog);
    }

    /**
     * 获取 WindowAssist
     * @param dialog {@link DialogFragment}
     * @return {@link WindowAssist}
     */
    public static WindowAssist get(final DialogFragment dialog) {
        return new WindowAssist(dialog);
    }

    /**
     * 获取 WindowAssist
     * @param dialog {@link android.app.DialogFragment}
     * @return {@link WindowAssist}
     */
    public static WindowAssist get(final android.app.DialogFragment dialog) {
        return new WindowAssist(dialog);
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

    /**
     * 获取 Window LayoutParams
     * @return Window LayoutParams
     */
    public WindowManager.LayoutParams getAttributes() {
        return getAttributes(mWindow);
    }

    /**
     * 设置 Window LayoutParams
     * @param params WindowManager.LayoutParams
     * @return {@code true} success, {@code false} fail
     */
    public boolean setAttributes(final WindowManager.LayoutParams params) {
        return setAttributes(mWindow, params);
    }

    /**
     * 清除 Window flags
     * @param flags 待清除 flags
     * @return {@code true} success, {@code false} fail
     */
    public boolean clearFlags(final int flags) {
        return clearFlags(mWindow, flags);
    }

    /**
     * 添加 Window flags
     * @param flags 待添加 flags
     * @return {@code true} success, {@code false} fail
     */
    public boolean addFlags(final int flags) {
        return addFlags(mWindow, flags);
    }

    /**
     * 设置 Window flags
     * @param flags 待设置 flags
     * @param mask  待设置 flags 位
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlags(
            final int flags,
            final int mask
    ) {
        return setFlags(mWindow, flags, mask);
    }

    // ==============
    // = Window 传参 =
    // ==============

    /**
     * 获取 Window LayoutParams
     * @param window {@link Window}
     * @return Window LayoutParams
     */
    public WindowManager.LayoutParams getAttributes(final Window window) {
        if (window == null) return null;
        return window.getAttributes();
    }

    /**
     * 设置 Window LayoutParams
     * @param window {@link Window}
     * @param params WindowManager.LayoutParams
     * @return {@code true} success, {@code false} fail
     */
    public boolean setAttributes(
            final Window window,
            final WindowManager.LayoutParams params
    ) {
        if (window == null || params == null) return false;
        window.setAttributes(params);
        return true;
    }

    /**
     * 清除 Window flags
     * @param window {@link Window}
     * @param flags  待清除 flags
     * @return {@code true} success, {@code false} fail
     */
    public boolean clearFlags(
            final Window window,
            final int flags
    ) {
        if (window == null) return false;
        window.clearFlags(flags);
        return true;
    }

    /**
     * 添加 Window flags
     * @param window {@link Window}
     * @param flags  待添加 flags
     * @return {@code true} success, {@code false} fail
     */
    public boolean addFlags(
            final Window window,
            final int flags
    ) {
        if (window == null) return false;
        window.addFlags(flags);
        return true;
    }

    /**
     * 设置 Window flags
     * @param window {@link Window}
     * @param flags  待设置 flags
     * @param mask   待设置 flags 位
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlags(
            final Window window,
            final int flags,
            final int mask
    ) {
        if (window == null) return false;
        window.setFlags(flags, mask);
        return true;
    }
}