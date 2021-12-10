package dev.utils.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import dev.utils.app.assist.WindowAssist;

/**
 * detail: Window 工具类
 * @author Ttt
 */
public final class WindowUtils {

    private WindowUtils() {
    }

    /**
     * 获取 Window
     * @param context {@link Context}
     * @return {@link Window}
     */
    public static Window getWindow(final Context context) {
        return WindowAssist.getWindow(context);
    }

    /**
     * 获取 Window
     * @param activity {@link Activity}
     * @return {@link Window}
     */
    public static Window getWindow(final Activity activity) {
        return WindowAssist.getWindow(activity);
    }

    /**
     * 获取 Window
     * @param fragment {@link Fragment}
     * @return {@link Window}
     */
    public static Window getWindow(final Fragment fragment) {
        return WindowAssist.getWindow(fragment);
    }

    /**
     * 获取 Window
     * @param fragment {@link android.app.Fragment}
     * @return {@link Window}
     */
    public static Window getWindow(final android.app.Fragment fragment) {
        return WindowAssist.getWindow(fragment);
    }

    /**
     * 获取 Window
     * @param dialog {@link Dialog}
     * @return {@link Window}
     */
    public static Window getWindow(final Dialog dialog) {
        return WindowAssist.getWindow(dialog);
    }

    /**
     * 获取 Window
     * @param dialog {@link DialogFragment}
     * @return {@link Window}
     */
    public static Window getWindow(final DialogFragment dialog) {
        return WindowAssist.getWindow(dialog);
    }

    /**
     * 获取 Window
     * @param dialog {@link android.app.DialogFragment}
     * @return {@link Window}
     */
    public static Window getWindow(final android.app.DialogFragment dialog) {
        return WindowAssist.getWindow(dialog);
    }

    // =======
    // = get =
    // =======

    /**
     * 获取 WindowAssist
     * @return {@link WindowAssist}
     */
    public static WindowAssist get() {
        return WindowAssist.get();
    }

    /**
     * 获取 WindowAssist
     * @param window {@link Window}
     * @return {@link WindowAssist}
     */
    public static WindowAssist get(final Window window) {
        return WindowAssist.get(window);
    }

    /**
     * 获取 WindowAssist
     * @param context {@link Context}
     * @return {@link WindowAssist}
     */
    public static WindowAssist get(final Context context) {
        return WindowAssist.get(context);
    }

    /**
     * 获取 WindowAssist
     * @param activity {@link Activity}
     * @return {@link WindowAssist}
     */
    public static WindowAssist get(final Activity activity) {
        return WindowAssist.get(activity);
    }

    /**
     * 获取 WindowAssist
     * @param fragment {@link Fragment}
     * @return {@link WindowAssist}
     */
    public static WindowAssist get(final Fragment fragment) {
        return WindowAssist.get(fragment);
    }

    /**
     * 获取 WindowAssist
     * @param fragment {@link android.app.Fragment}
     * @return {@link WindowAssist}
     */
    public static WindowAssist get(final android.app.Fragment fragment) {
        return WindowAssist.get(fragment);
    }

    /**
     * 获取 WindowAssist
     * @param dialog {@link Dialog}
     * @return {@link WindowAssist}
     */
    public static WindowAssist get(final Dialog dialog) {
        return WindowAssist.get(dialog);
    }

    /**
     * 获取 WindowAssist
     * @param dialog {@link DialogFragment}
     * @return {@link WindowAssist}
     */
    public static WindowAssist get(final DialogFragment dialog) {
        return WindowAssist.get(dialog);
    }

    /**
     * 获取 WindowAssist
     * @param dialog {@link android.app.DialogFragment}
     * @return {@link WindowAssist}
     */
    public static WindowAssist get(final android.app.DialogFragment dialog) {
        return WindowAssist.get(dialog);
    }
}