package dev.utils.app.assist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IntRange;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import dev.utils.LogPrintUtils;
import dev.utils.app.ActivityUtils;
import dev.utils.app.BrightnessUtils;

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
     * @param fragment {@link Fragment}
     * @return {@link Window}
     */
    public static Window getWindow(final Fragment fragment) {
        if (fragment == null) return null;
        return getWindow(fragment.getActivity());
    }

    /**
     * 获取 Window
     * @param fragment {@link android.app.Fragment}
     * @return {@link Window}
     */
    public static Window getWindow(final android.app.Fragment fragment) {
        if (fragment == null) return null;
        return getWindow(fragment.getActivity());
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
     * @return {@link WindowAssist}
     */
    public static WindowAssist get() {
        return getInstance();
    }

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
     * @param fragment {@link Fragment}
     * @return {@link WindowAssist}
     */
    public static WindowAssist get(final Fragment fragment) {
        return new WindowAssist(fragment);
    }

    /**
     * 获取 WindowAssist
     * @param fragment {@link android.app.Fragment}
     * @return {@link WindowAssist}
     */
    public static WindowAssist get(final android.app.Fragment fragment) {
        return new WindowAssist(fragment);
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
    private static WindowAssist getInstance() {
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

    public WindowAssist(final Fragment fragment) {
        this(getWindow(fragment));
    }

    public WindowAssist(final android.app.Fragment fragment) {
        this(getWindow(fragment));
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
     * 获取 Window DecorView
     * @return DecorView
     */
    public View getDecorView() {
        return getDecorView(mWindow);
    }

    /**
     * 获取 Window DecorView
     * @return DecorView
     */
    public View peekDecorView() {
        return peekDecorView(mWindow);
    }

    /**
     * 获取 Window 当前获取焦点 View
     * @return 当前获取焦点 View
     */
    public View getCurrentFocus() {
        return getCurrentFocus(mWindow);
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
     * 刷新自身 Window LayoutParams
     * @return {@code true} success, {@code false} fail
     */
    public boolean refreshSelfAttributes() {
        return refreshSelfAttributes(mWindow);
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

    /**
     * Window 是否设置指定 flag 值
     * @param flag 待校验 flag
     * @return {@code true} success, {@code false} fail
     */
    public boolean hasFlag(final int flag) {
        return hasFlag(mWindow, flag);
    }

    /**
     * Window 是否设置指定 flags 值
     * @param flags 待校验 flags
     * @return {@code true} success, {@code false} fail
     */
    public boolean hasFlags(final int... flags) {
        return hasFlags(mWindow, flags);
    }

    /**
     * 设置 Window 输入模式
     * @param mode input mode
     * @return {@code true} success, {@code false} fail
     */
    public boolean setSoftInputMode(final int mode) {
        return setSoftInputMode(mWindow, mode);
    }

    // ==========
    // = 具体功能 =
    // ==========

    /**
     * 设置窗口亮度
     * @param brightness 亮度值
     * @return {@code true} success, {@code false} fail
     */
    public boolean setWindowBrightness(@IntRange(from = 0, to = 255) final int brightness) {
        return setWindowBrightness(mWindow, brightness);
    }

    /**
     * 获取窗口亮度
     * @return 屏幕亮度 0-255
     */
    public int getWindowBrightness() {
        return getWindowBrightness(mWindow);
    }

    /**
     * 设置 Window 软键盘是否显示
     * @param inputVisible 是否显示软键盘
     * @param clearFlag    是否清空 Flag ( FLAG_ALT_FOCUSABLE_IM | FLAG_NOT_FOCUSABLE )
     * @return {@code true} success, {@code false} fail
     */
    public boolean setKeyBoardSoftInputMode(
            final boolean inputVisible,
            final boolean clearFlag
    ) {
        return setKeyBoardSoftInputMode(mWindow, inputVisible, clearFlag);
    }


    /**
     * 是否屏幕常亮
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFlagKeepScreenOn() {
        return isFlagKeepScreenOn(mWindow);
    }

    /**
     * 设置屏幕常亮
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlagKeepScreenOn() {
        return setFlagKeepScreenOn(mWindow);
    }

    /**
     * 移除屏幕常亮
     * @return {@code true} success, {@code false} fail
     */
    public boolean clearFlagKeepScreenOn() {
        return clearFlagKeepScreenOn(mWindow);
    }

    /**
     * 是否禁止截屏
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFlagSecure() {
        return isFlagSecure(mWindow);
    }

    /**
     * 设置禁止截屏
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlagSecure() {
        return setFlagSecure(mWindow);
    }

    /**
     * 移除禁止截屏
     * @return {@code true} success, {@code false} fail
     */
    public boolean clearFlagSecure() {
        return clearFlagSecure(mWindow);
    }

    /**
     * 是否屏幕为全屏
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFullScreen() {
        return isFullScreen(mWindow);
    }

    /**
     * 设置屏幕为全屏
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlagFullScreen() {
        return setFlagFullScreen(mWindow);
    }

    /**
     * 移除屏幕全屏
     * @return {@code true} success, {@code false} fail
     */
    public boolean clearFlagFullScreen() {
        return clearFlagFullScreen(mWindow);
    }

    // ==============
    // = Window 传参 =
    // ==============

    /**
     * 获取 Window DecorView
     * @param window {@link Window}
     * @return DecorView
     */
    public View getDecorView(final Window window) {
        if (window == null) return null;
        return window.getDecorView();
    }

    /**
     * 获取 Window DecorView
     * @param window {@link Window}
     * @return DecorView
     */
    public View peekDecorView(final Window window) {
        if (window == null) return null;
        return window.peekDecorView();
    }

    /**
     * 获取 Window 当前获取焦点 View
     * @param window {@link Window}
     * @return 当前获取焦点 View
     */
    public View getCurrentFocus(final Window window) {
        if (window == null) return null;
        return window.getCurrentFocus();
    }

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
     * 刷新自身 Window LayoutParams
     * @param window {@link Window}
     * @return {@code true} success, {@code false} fail
     */
    public boolean refreshSelfAttributes(final Window window) {
        return setAttributes(window, getAttributes(window));
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

    /**
     * Window 是否设置指定 flag 值
     * @param window {@link Window}
     * @param flag   待校验 flag
     * @return {@code true} success, {@code false} fail
     */
    public boolean hasFlag(
            final Window window,
            final int flag
    ) {
        if (window == null) return false;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (layoutParams == null) return false;
        int flags = layoutParams.flags;
        return (flags & flag) != 0;
    }

    /**
     * Window 是否设置指定 flags 值
     * @param window {@link Window}
     * @param flags  待校验 flags
     * @return {@code true} success, {@code false} fail
     */
    public boolean hasFlags(
            final Window window,
            final int... flags
    ) {
        if (window == null) return false;
        if (flags == null || flags.length == 0) return false;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (layoutParams == null) return false;
        for (int value : flags) {
            if ((value & layoutParams.flags) == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 设置 Window 输入模式
     * @param window {@link Window}
     * @param mode   input mode
     * @return {@code true} success, {@code false} fail
     */
    public boolean setSoftInputMode(
            final Window window,
            final int mode
    ) {
        if (window == null) return false;
        window.setSoftInputMode(mode);
        return true;
    }

    // ==========
    // = 具体功能 =
    // ==========

    /**
     * 设置窗口亮度
     * @param window     {@link Window}
     * @param brightness 亮度值
     * @return {@code true} success, {@code false} fail
     */
    public boolean setWindowBrightness(
            final Window window,
            @IntRange(from = 0, to = 255) final int brightness
    ) {
        if (window == null) return false;
        try {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.screenBrightness = brightness / 255f;
            window.setAttributes(layoutParams);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setWindowBrightness");
        }
        return false;
    }

    /**
     * 获取窗口亮度
     * @param window {@link Window}
     * @return 屏幕亮度 0-255
     */
    public int getWindowBrightness(final Window window) {
        if (window == null) return 0;
        try {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            float                      brightness   = layoutParams.screenBrightness;
            if (brightness < 0) return BrightnessUtils.getBrightness();
            return (int) (brightness * 255);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWindowBrightness");
        }
        return 0;
    }

    /**
     * 设置 Window 软键盘是否显示
     * @param window       {@link Window}
     * @param inputVisible 是否显示软键盘
     * @param clearFlag    是否清空 Flag ( FLAG_ALT_FOCUSABLE_IM | FLAG_NOT_FOCUSABLE )
     * @return {@code true} success, {@code false} fail
     */
    public boolean setKeyBoardSoftInputMode(
            final Window window,
            final boolean inputVisible,
            final boolean clearFlag
    ) {
        if (window == null) return false;
        if (inputVisible) {
            if (clearFlag) {
                window.clearFlags(
                        WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                );
            }
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        } else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
        return true;
    }

    /**
     * 是否屏幕常亮
     * @param window {@link Activity#getWindow()}
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFlagKeepScreenOn(final Window window) {
        return hasFlag(window, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 设置屏幕常亮
     * @param window {@link Activity#getWindow()}
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlagKeepScreenOn(final Window window) {
        if (window == null) return false;
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return true;
    }

    /**
     * 移除屏幕常亮
     * @param window {@link Activity#getWindow()}
     * @return {@code true} success, {@code false} fail
     */
    public boolean clearFlagKeepScreenOn(final Window window) {
        if (window == null) return false;
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return true;
    }

    /**
     * 是否禁止截屏
     * @param window {@link Activity#getWindow()}
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFlagSecure(final Window window) {
        return hasFlag(window, WindowManager.LayoutParams.FLAG_SECURE);
    }

    /**
     * 设置禁止截屏
     * @param window {@link Activity#getWindow()}
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlagSecure(final Window window) {
        if (window == null) return false;
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        return true;
    }

    /**
     * 移除禁止截屏
     * @param window {@link Activity#getWindow()}
     * @return {@code true} success, {@code false} fail
     */
    public boolean clearFlagSecure(final Window window) {
        if (window == null) return false;
        window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
        return true;
    }

    /**
     * 是否屏幕为全屏
     * @param window {@link Activity#getWindow()}
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFullScreen(final Window window) {
        return hasFlag(window, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置屏幕为全屏
     * @param window {@link Activity#getWindow()}
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlagFullScreen(final Window window) {
        if (window == null) return false;
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return true;
    }

    /**
     * 移除屏幕全屏
     * @param window {@link Activity#getWindow()}
     * @return {@code true} success, {@code false} fail
     */
    public boolean clearFlagFullScreen(final Window window) {
        if (window == null) return false;
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return true;
    }
}