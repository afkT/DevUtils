package dev.utils.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.IntRange;
import androidx.fragment.app.DialogFragment;

import dev.utils.app.assist.WindowAssist;

/**
 * detail: Window 工具类
 * @author Ttt
 */
public final class WindowUtils {

    private WindowUtils() {
    }

    // 日志 TAG
    private static final String TAG = WindowUtils.class.getSimpleName();

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

    // =

    /**
     * 设置窗口亮度
     * @param window     {@link Window}
     * @param brightness 亮度值
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setWindowBrightness(
            final Window window,
            @IntRange(from = 0, to = 255) final int brightness
    ) {
        return WindowAssist.getInstance().setWindowBrightness(
                window, brightness
        );
    }

    /**
     * 获取窗口亮度
     * @param window {@link Window}
     * @return 屏幕亮度 0-255
     */
    public static int getWindowBrightness(final Window window) {
        return WindowAssist.getInstance().getWindowBrightness(window);
    }
}