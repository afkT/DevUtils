package dev.utils.app.assist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;

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
     * @param window {@link Window}
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
     * @return {@code true} yes, {@code false} no
     */
    public boolean hasFlag(final int flag) {
        return hasFlag(mWindow, flag);
    }

    /**
     * Window 是否设置指定 flags 值
     * @param flags 待校验 flags
     * @return {@code true} yes, {@code false} no
     */
    public boolean hasFlags(final int... flags) {
        return hasFlags(mWindow, flags);
    }

    /**
     * Window 是否没有设置指定 flag 值
     * @param flag 待校验 flag
     * @return {@code true} yes, {@code false} no
     */
    public boolean notHasFlag(final int flag) {
        return notHasFlag(mWindow, flag);
    }

    /**
     * Window 是否没有设置指定 flags 值
     * @param flags 待校验 flags
     * @return {@code true} yes, {@code false} no
     */
    public boolean notHasFlags(final int... flags) {
        return notHasFlags(mWindow, flags);
    }

    /**
     * 启用 Window Extended Feature
     * <pre>
     *     启用后无法关闭, 需要在 setContentView() 之前调用
     * </pre>
     * @param featureId 待启用 feature
     * @return {@code true} success, {@code false} fail
     */
    public boolean requestFeature(final int featureId) {
        return requestFeature(mWindow, featureId);
    }

    /**
     * Window 是否开启指定 Extended Feature
     * @param featureId 待校验 feature
     * @return {@code true} yes, {@code false} no
     */
    public boolean hasFeature(final int featureId) {
        return hasFeature(mWindow, featureId);
    }

    /**
     * Window 是否开启指定 Extended Feature
     * @param featureIds 待校验 feature
     * @return {@code true} yes, {@code false} no
     */
    public boolean hasFeatures(final int... featureIds) {
        return hasFeatures(mWindow, featureIds);
    }

    /**
     * Window 是否没有开启指定 Extended Feature
     * @param featureId 待校验 feature
     * @return {@code true} yes, {@code false} no
     */
    public boolean notHasFeature(final int featureId) {
        return notHasFeature(mWindow, featureId);
    }

    /**
     * Window 是否没有开启指定 Extended Feature
     * @param featureIds 待校验 feature
     * @return {@code true} yes, {@code false} no
     */
    public boolean notHasFeatures(final int... featureIds) {
        return notHasFeatures(mWindow, featureIds);
    }

    /**
     * 设置 Window 输入模式
     * @param mode input mode
     * @return {@code true} success, {@code false} fail
     */
    public boolean setSoftInputMode(final int mode) {
        return setSoftInputMode(mWindow, mode);
    }

    /**
     * 设置 StatusBar Color
     * @param color StatusBar Color
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean setStatusBarColor(@ColorInt final int color) {
        return setStatusBarColor(mWindow, color);
    }

    /**
     * 获取 StatusBar Color
     * @return StatusBar Color
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int getStatusBarColor() {
        return getStatusBarColor(mWindow);
    }

    /**
     * 设置 NavigationBar Color
     * @param color NavigationBar Color
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean setNavigationBarColor(@ColorInt final int color) {
        return setNavigationBarColor(mWindow, color);
    }

    /**
     * 获取 NavigationBar Color
     * @return NavigationBar Color
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int getNavigationBarColor() {
        return getNavigationBarColor(mWindow);
    }

    /**
     * 设置 NavigationBar Divider Color
     * @param color NavigationBar Divider Color
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public boolean setNavigationBarDividerColor(@ColorInt final int color) {
        return setNavigationBarDividerColor(mWindow, color);
    }

    /**
     * 获取 NavigationBar Divider Color
     * @return NavigationBar Divider Color
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public int getNavigationBarDividerColor() {
        return getNavigationBarDividerColor(mWindow);
    }

    /**
     * 设置 Dialog 宽度
     * @param width 宽度
     * @return {@code true} success, {@code false} fail
     */
    public boolean setWidthByParams(final int width) {
        return setWidthByParams(mWindow, width);
    }

    /**
     * 设置 Dialog 高度
     * @param height 高度
     * @return {@code true} success, {@code false} fail
     */
    public boolean setHeightByParams(final int height) {
        return setHeightByParams(mWindow, height);
    }

    /**
     * 设置 Dialog 宽度、高度
     * @param width  宽度
     * @param height 高度
     * @return {@code true} success, {@code false} fail
     */
    public boolean setWidthHeightByParams(
            final int width,
            final int height
    ) {
        return setWidthHeightByParams(mWindow, width, height);
    }

    /**
     * 设置 Dialog X 轴坐标
     * @param x X 轴坐标
     * @return {@code true} success, {@code false} fail
     */
    public boolean setXByParams(final int x) {
        return setXByParams(mWindow, x);
    }

    /**
     * 设置 Dialog Y 轴坐标
     * @param y Y 轴坐标
     * @return {@code true} success, {@code false} fail
     */
    public boolean setYByParams(final int y) {
        return setYByParams(mWindow, y);
    }

    /**
     * 设置 Dialog X、Y 轴坐标
     * @param x X 轴坐标
     * @param y Y 轴坐标
     * @return {@code true} success, {@code false} fail
     */
    public boolean setXYByParams(
            final int x,
            final int y
    ) {
        return setXYByParams(mWindow, x, y);
    }

    /**
     * 设置 Dialog Gravity
     * @param gravity 重心
     * @return {@code true} success, {@code false} fail
     */
    public boolean setGravityByParams(final int gravity) {
        return setGravityByParams(mWindow, gravity);
    }

    /**
     * 设置 Dialog 透明度
     * @param dimAmount 透明度
     * @return {@code true} success, {@code false} fail
     */
    public boolean setDimAmountByParams(final float dimAmount) {
        return setDimAmountByParams(mWindow, dimAmount);
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
    public boolean isKeepScreenOnFlag() {
        return isKeepScreenOnFlag(mWindow);
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
    public boolean isSecureFlag() {
        return isSecureFlag(mWindow);
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
    public boolean isFullScreenFlag() {
        return isFullScreenFlag(mWindow);
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

    /**
     * 是否透明状态栏
     * @return {@code true} yes, {@code false} no
     */
    public boolean isTranslucentStatusFlag() {
        return isTranslucentStatusFlag(mWindow);
    }

    /**
     * 设置透明状态栏
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlagTranslucentStatus() {
        return setFlagTranslucentStatus(mWindow);
    }

    /**
     * 移除透明状态栏
     * @return {@code true} success, {@code false} fail
     */
    public boolean clearFlagTranslucentStatus() {
        return clearFlagTranslucentStatus(mWindow);
    }

    /**
     * 是否系统状态栏背景绘制
     * @return {@code true} yes, {@code false} no
     */
    public boolean isDrawsSystemBarBackgroundsFlag() {
        return isDrawsSystemBarBackgroundsFlag(mWindow);
    }

    /**
     * 设置系统状态栏背景绘制
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlagDrawsSystemBarBackgrounds() {
        return setFlagDrawsSystemBarBackgrounds(mWindow);
    }

    /**
     * 移除系统状态栏背景绘制
     * @return {@code true} success, {@code false} fail
     */
    public boolean clearFlagDrawsSystemBarBackgrounds() {
        return clearFlagDrawsSystemBarBackgrounds(mWindow);
    }

    /**
     * 是否屏幕页面为无标题
     * @return {@code true} yes, {@code false} no
     */
    public boolean isNoTitleFeature() {
        return isNoTitleFeature(mWindow);
    }

    /**
     * 设置屏幕页面无标题
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFeatureNoTitle() {
        return setFeatureNoTitle(mWindow);
    }

    /**
     * 设置屏幕为全屏无标题
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlagFullScreenAndNoTitle() {
        return setFlagFullScreenAndNoTitle(mWindow);
    }

    /**
     * 设置高版本状态栏蒙层
     * @param color StatusBar Color
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean setSemiTransparentStatusBarColor(@ColorInt final int color) {
        return setSemiTransparentStatusBarColor(mWindow, color);
    }

    /**
     * 设置状态栏颜色、高版本状态栏蒙层
     * @param color    StatusBar Color
     * @param addFlags 是否添加 Windows flags
     * @return {@code true} success, {@code false} fail
     */
    public boolean setStatusBarColorAndFlag(
            @ColorInt final int color,
            final boolean addFlags
    ) {
        return setStatusBarColorAndFlag(mWindow, color, addFlags);
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
     * @return {@code true} yes, {@code false} no
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
     * @return {@code true} yes, {@code false} no
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
     * Window 是否没有设置指定 flag 值
     * @param window {@link Window}
     * @param flag   待校验 flag
     * @return {@code true} yes, {@code false} no
     */
    public boolean notHasFlag(
            final Window window,
            final int flag
    ) {
        if (window == null) return false;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (layoutParams == null) return false;
        int flags = layoutParams.flags;
        return (flags & flag) == 0;
    }

    /**
     * Window 是否没有设置指定 flags 值
     * @param window {@link Window}
     * @param flags  待校验 flags
     * @return {@code true} yes, {@code false} no
     */
    public boolean notHasFlags(
            final Window window,
            final int... flags
    ) {
        if (window == null) return false;
        if (flags == null || flags.length == 0) return false;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (layoutParams == null) return false;
        for (int value : flags) {
            if ((value & layoutParams.flags) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 启用 Window Extended Feature
     * <pre>
     *     启用后无法关闭, 需要在 setContentView() 之前调用
     * </pre>
     * @param window    {@link Window}
     * @param featureId 待启用 feature
     * @return {@code true} success, {@code false} fail
     */
    public boolean requestFeature(
            final Window window,
            final int featureId
    ) {
        if (window == null) return false;
        window.requestFeature(featureId);
        return true;
    }

    /**
     * Window 是否开启指定 Extended Feature
     * @param window    {@link Window}
     * @param featureId 待校验 feature
     * @return {@code true} yes, {@code false} no
     */
    public boolean hasFeature(
            final Window window,
            final int featureId
    ) {
        if (window == null) return false;
        return window.hasFeature(featureId);
    }

    /**
     * Window 是否开启指定 Extended Feature
     * @param window     {@link Window}
     * @param featureIds 待校验 feature
     * @return {@code true} yes, {@code false} no
     */
    public boolean hasFeatures(
            final Window window,
            final int... featureIds
    ) {
        if (window == null) return false;
        if (featureIds == null || featureIds.length == 0) return false;
        for (int value : featureIds) {
            if (!window.hasFeature(value)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Window 是否没有开启指定 Extended Feature
     * @param window    {@link Window}
     * @param featureId 待校验 feature
     * @return {@code true} yes, {@code false} no
     */
    public boolean notHasFeature(
            final Window window,
            final int featureId
    ) {
        if (window == null) return false;
        return !window.hasFeature(featureId);
    }

    /**
     * Window 是否没有开启指定 Extended Feature
     * @param window     {@link Window}
     * @param featureIds 待校验 feature
     * @return {@code true} yes, {@code false} no
     */
    public boolean notHasFeatures(
            final Window window,
            final int... featureIds
    ) {
        if (window == null) return false;
        if (featureIds == null || featureIds.length == 0) return false;
        for (int value : featureIds) {
            if (window.hasFeature(value)) {
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

    /**
     * 设置 StatusBar Color
     * @param window {@link Window}
     * @param color  StatusBar Color
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean setStatusBarColor(
            final Window window,
            @ColorInt final int color
    ) {
        if (window == null) return false;
        window.setStatusBarColor(color);
        return true;
    }

    /**
     * 获取 StatusBar Color
     * @param window {@link Window}
     * @return StatusBar Color
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int getStatusBarColor(final Window window) {
        if (window == null) return 0;
        return window.getStatusBarColor();
    }

    /**
     * 设置 NavigationBar Color
     * @param window {@link Window}
     * @param color  NavigationBar Color
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean setNavigationBarColor(
            final Window window,
            @ColorInt final int color
    ) {
        if (window == null) return false;
        window.setNavigationBarColor(color);
        return true;
    }

    /**
     * 获取 NavigationBar Color
     * @param window {@link Window}
     * @return NavigationBar Color
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public int getNavigationBarColor(final Window window) {
        if (window == null) return 0;
        return window.getNavigationBarColor();
    }

    /**
     * 设置 NavigationBar Divider Color
     * @param window {@link Window}
     * @param color  NavigationBar Divider Color
     * @return {@code true} success, {@code false} fail
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public boolean setNavigationBarDividerColor(
            final Window window,
            @ColorInt final int color
    ) {
        if (window == null) return false;
        window.setNavigationBarDividerColor(color);
        return true;
    }

    /**
     * 获取 NavigationBar Divider Color
     * @param window {@link Window}
     * @return NavigationBar Divider Color
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public int getNavigationBarDividerColor(final Window window) {
        if (window == null) return 0;
        return window.getNavigationBarDividerColor();
    }

    /**
     * 设置 Dialog 宽度
     * @param window {@link Window}
     * @param width  宽度
     * @return {@code true} success, {@code false} fail
     */
    public boolean setWidthByParams(
            final Window window,
            final int width
    ) {
        if (window == null) return false;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (layoutParams == null) return false;
        layoutParams.width = width;
        return setAttributes(window, layoutParams);
    }

    /**
     * 设置 Dialog 高度
     * @param window {@link Window}
     * @param height 高度
     * @return {@code true} success, {@code false} fail
     */
    public boolean setHeightByParams(
            final Window window,
            final int height
    ) {
        if (window == null) return false;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (layoutParams == null) return false;
        layoutParams.height = height;
        return setAttributes(window, layoutParams);
    }

    /**
     * 设置 Dialog 宽度、高度
     * @param window {@link Window}
     * @param width  宽度
     * @param height 高度
     * @return {@code true} success, {@code false} fail
     */
    public boolean setWidthHeightByParams(
            final Window window,
            final int width,
            final int height
    ) {
        if (window == null) return false;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (layoutParams == null) return false;
        layoutParams.width  = width;
        layoutParams.height = height;
        return setAttributes(window, layoutParams);
    }

    /**
     * 设置 Dialog X 轴坐标
     * @param window {@link Window}
     * @param x      X 轴坐标
     * @return {@code true} success, {@code false} fail
     */
    public boolean setXByParams(
            final Window window,
            final int x
    ) {
        if (window == null) return false;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (layoutParams == null) return false;
        layoutParams.x = x;
        return setAttributes(window, layoutParams);
    }

    /**
     * 设置 Dialog Y 轴坐标
     * @param window {@link Window}
     * @param y      Y 轴坐标
     * @return {@code true} success, {@code false} fail
     */
    public boolean setYByParams(
            final Window window,
            final int y
    ) {
        if (window == null) return false;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (layoutParams == null) return false;
        layoutParams.y = y;
        return setAttributes(window, layoutParams);
    }

    /**
     * 设置 Dialog X、Y 轴坐标
     * @param window {@link Window}
     * @param x      X 轴坐标
     * @param y      Y 轴坐标
     * @return {@code true} success, {@code false} fail
     */
    public boolean setXYByParams(
            final Window window,
            final int x,
            final int y
    ) {
        if (window == null) return false;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (layoutParams == null) return false;
        layoutParams.x = x;
        layoutParams.y = y;
        return setAttributes(window, layoutParams);
    }

    /**
     * 设置 Dialog Gravity
     * @param window  {@link Window}
     * @param gravity 重心
     * @return {@code true} success, {@code false} fail
     */
    public boolean setGravityByParams(
            final Window window,
            final int gravity
    ) {
        if (window == null) return false;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (layoutParams == null) return false;
        layoutParams.gravity = gravity;
        return setAttributes(window, layoutParams);
    }

    /**
     * 设置 Dialog 透明度
     * @param window    {@link Window}
     * @param dimAmount 透明度
     * @return {@code true} success, {@code false} fail
     */
    public boolean setDimAmountByParams(
            final Window window,
            final float dimAmount
    ) {
        if (window == null) return false;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (layoutParams == null) return false;
        layoutParams.dimAmount = dimAmount;
        return setAttributes(window, layoutParams);
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
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (layoutParams == null) return false;
        layoutParams.screenBrightness = brightness / 255f;
        return setAttributes(window, layoutParams);
    }

    /**
     * 获取窗口亮度
     * @param window {@link Window}
     * @return 屏幕亮度 0-255
     */
    public int getWindowBrightness(final Window window) {
        if (window == null) return 0;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (layoutParams == null) return 0;
        float brightness = layoutParams.screenBrightness;
        if (brightness < 0) return BrightnessUtils.getBrightness();
        return (int) (brightness * 255);
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
     * @param window {@link Window}
     * @return {@code true} yes, {@code false} no
     */
    public boolean isKeepScreenOnFlag(final Window window) {
        return hasFlag(window, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 设置屏幕常亮
     * @param window {@link Window}
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlagKeepScreenOn(final Window window) {
        if (window == null) return false;
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return true;
    }

    /**
     * 移除屏幕常亮
     * @param window {@link Window}
     * @return {@code true} success, {@code false} fail
     */
    public boolean clearFlagKeepScreenOn(final Window window) {
        if (window == null) return false;
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return true;
    }

    /**
     * 是否禁止截屏
     * @param window {@link Window}
     * @return {@code true} yes, {@code false} no
     */
    public boolean isSecureFlag(final Window window) {
        return hasFlag(window, WindowManager.LayoutParams.FLAG_SECURE);
    }

    /**
     * 设置禁止截屏
     * @param window {@link Window}
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlagSecure(final Window window) {
        if (window == null) return false;
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        return true;
    }

    /**
     * 移除禁止截屏
     * @param window {@link Window}
     * @return {@code true} success, {@code false} fail
     */
    public boolean clearFlagSecure(final Window window) {
        if (window == null) return false;
        window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
        return true;
    }

    /**
     * 是否屏幕为全屏
     * @param window {@link Window}
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFullScreenFlag(final Window window) {
        return hasFlag(window, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置屏幕为全屏
     * @param window {@link Window}
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlagFullScreen(final Window window) {
        if (window == null) return false;
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return true;
    }

    /**
     * 移除屏幕全屏
     * @param window {@link Window}
     * @return {@code true} success, {@code false} fail
     */
    public boolean clearFlagFullScreen(final Window window) {
        if (window == null) return false;
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return true;
    }

    /**
     * 是否透明状态栏
     * @param window {@link Window}
     * @return {@code true} yes, {@code false} no
     */
    @SuppressLint("InlinedApi")
    public boolean isTranslucentStatusFlag(final Window window) {
        return hasFlag(window, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    /**
     * 设置透明状态栏
     * @param window {@link Window}
     * @return {@code true} success, {@code false} fail
     */
    @SuppressLint("InlinedApi")
    public boolean setFlagTranslucentStatus(final Window window) {
        if (window == null) return false;
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        return true;
    }

    /**
     * 移除透明状态栏
     * @param window {@link Window}
     * @return {@code true} success, {@code false} fail
     */
    @SuppressLint("InlinedApi")
    public boolean clearFlagTranslucentStatus(final Window window) {
        if (window == null) return false;
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        return true;
    }

    /**
     * 是否系统状态栏背景绘制
     * @param window {@link Window}
     * @return {@code true} yes, {@code false} no
     */
    @SuppressLint("InlinedApi")
    public boolean isDrawsSystemBarBackgroundsFlag(final Window window) {
        return hasFlag(window, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    }

    /**
     * 设置系统状态栏背景绘制
     * @param window {@link Window}
     * @return {@code true} success, {@code false} fail
     */
    @SuppressLint("InlinedApi")
    public boolean setFlagDrawsSystemBarBackgrounds(final Window window) {
        if (window == null) return false;
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        return true;
    }

    /**
     * 移除系统状态栏背景绘制
     * @param window {@link Window}
     * @return {@code true} success, {@code false} fail
     */
    @SuppressLint("InlinedApi")
    public boolean clearFlagDrawsSystemBarBackgrounds(final Window window) {
        if (window == null) return false;
        window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        return true;
    }

    /**
     * 是否屏幕页面为无标题
     * @param window {@link Window}
     * @return {@code true} yes, {@code false} no
     */
    public boolean isNoTitleFeature(final Window window) {
        return hasFeature(window, Window.FEATURE_NO_TITLE);
    }

    /**
     * 设置屏幕页面无标题
     * @param window {@link Window}
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFeatureNoTitle(final Window window) {
        return requestFeature(window, Window.FEATURE_NO_TITLE);
    }

    /**
     * 设置屏幕为全屏无标题
     * @param window {@link Window}
     * @return {@code true} success, {@code false} fail
     */
    public boolean setFlagFullScreenAndNoTitle(final Window window) {
        if (window == null) return false;
        setFeatureNoTitle(window);
        setFlagFullScreen(window);
        return true;
    }

    /**
     * 设置高版本状态栏蒙层
     * @param window {@link Window}
     * @param color  StatusBar Color
     * @return {@code true} success, {@code false} fail
     */
    @SuppressLint("PrivateApi")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean setSemiTransparentStatusBarColor(
            final Window window,
            @ColorInt final int color
    ) {
        if (window == null) return false;
        try {
            Class<?> decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
            Field field = decorViewClazz.getDeclaredField(
                    "mSemiTransparentStatusBarColor"
            );
            field.setAccessible(true);
            field.setInt(window, color);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * 设置状态栏颜色、高版本状态栏蒙层
     * @param window   {@link Window}
     * @param color    StatusBar Color
     * @param addFlags 是否添加 Windows flags
     * @return {@code true} success, {@code false} fail
     */
    public boolean setStatusBarColorAndFlag(
            final Window window,
            @ColorInt final int color,
            final boolean addFlags
    ) {
        if (window == null) return false;

        if (addFlags) {
            setFlagDrawsSystemBarBackgrounds(window);
            setFlagTranslucentStatus(window);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor(window, color);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setSemiTransparentStatusBarColor(window, color);
        }
        return true;
    }
}