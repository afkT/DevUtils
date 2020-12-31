package dev.utils.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.drawerlayout.widget.DrawerLayout;

import java.lang.reflect.Method;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Bar 相关工具类
 * @author Blankj
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />
 * </pre>
 */
public final class BarUtils {

    private BarUtils() {
    }

    // 日志 TAG
    private static final String TAG = BarUtils.class.getSimpleName();

    // ==============
    // = status bar =
    // ==============

    private static final String TAG_STATUS_BAR = "TAG_STATUS_BAR";
    private static final String TAG_OFFSET     = "TAG_OFFSET";
    private static final int    KEY_OFFSET     = -123;

    /**
     * 获取 StatusBar 高度
     * @return StatusBar 高度
     */
    public static int getStatusBarHeight() {
        try {
            Resources resources = Resources.getSystem();
            int       id        = resources.getIdentifier("status_bar_height", "dimen", "android");
            if (id != 0) {
                return resources.getDimensionPixelSize(id);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getStatusBarHeight");
        }
        return 0;
    }

    /**
     * 判断 StatusBar 是否显示
     * @param activity {@link Activity}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isStatusBarVisible(final Activity activity) {
        if (activity != null) {
            try {
                int flags = activity.getWindow().getAttributes().flags;
                return (flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isStatusBarVisible");
            }
        }
        return true;
    }

    /**
     * 设置 StatusBar 是否显示
     * @param activity  {@link Activity}
     * @param isVisible 是否显示 StatusBar
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setStatusBarVisibility(
            final Activity activity,
            final boolean isVisible
    ) {
        return setStatusBarVisibility(ActivityUtils.getWindow(activity), isVisible);
    }

    /**
     * 设置 StatusBar 是否显示
     * @param window    {@link Window}
     * @param isVisible 是否显示 StatusBar
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setStatusBarVisibility(
            final Window window,
            final boolean isVisible
    ) {
        if (window != null) {
            if (isVisible) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                showStatusBarView(window);
                addMarginTopEqualStatusBarHeight(window);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                hideStatusBarView(window);
                subtractMarginTopEqualStatusBarHeight(window);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置 StatusBar 是否高亮模式
     * @param activity    {@link Activity}
     * @param isLightMode 是否高亮模式
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setStatusBarLightMode(
            final Activity activity,
            final boolean isLightMode
    ) {
        return setStatusBarLightMode(ActivityUtils.getWindow(activity), isLightMode);
    }

    /**
     * 设置 StatusBar 是否高亮模式
     * @param window      {@link Window}
     * @param isLightMode 是否高亮模式
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setStatusBarLightMode(
            final Window window,
            final boolean isLightMode
    ) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            int  vis       = decorView.getSystemUiVisibility();
            if (isLightMode) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(vis);
            return true;
        }
        return false;
    }

    /**
     * 获取 StatusBar 是否高亮模式
     * @param activity {@link Activity}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isStatusBarLightMode(final Activity activity) {
        return isStatusBarLightMode(ActivityUtils.getWindow(activity));
    }

    /**
     * 获取 StatusBar 是否高亮模式
     * @param window {@link Window}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isStatusBarLightMode(final Window window) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            int  vis       = decorView.getSystemUiVisibility();
            return (vis & View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) != 0;
        }
        return false;
    }

    // =

    /**
     * 添加 View 向上 StatusBar 同等高度边距
     * @param view {@link View}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean addMarginTopEqualStatusBarHeight(final View view) {
        if (view != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.setTag(TAG_OFFSET);
            Object haveSetOffset = view.getTag(KEY_OFFSET);
            if (haveSetOffset != null && (Boolean) haveSetOffset) return false;
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + getStatusBarHeight(),
                    layoutParams.rightMargin, layoutParams.bottomMargin);
            view.setLayoutParams(layoutParams);
            view.setTag(KEY_OFFSET, true);
            return true;
        }
        return false;
    }

    /**
     * 移除 View 向上 StatusBar 同等高度边距
     * @param view {@link View}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean subtractMarginTopEqualStatusBarHeight(final View view) {
        if (view != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Object haveSetOffset = view.getTag(KEY_OFFSET);
            if (haveSetOffset == null || !(Boolean) haveSetOffset) return false;
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin - getStatusBarHeight(),
                    layoutParams.rightMargin, layoutParams.bottomMargin);
            view.setLayoutParams(layoutParams);
            view.setTag(KEY_OFFSET, false);
            return true;
        }
        return false;
    }

    /**
     * 添加 View 向上 StatusBar 同等高度边距
     * @param window {@link Window}
     */
    private static void addMarginTopEqualStatusBarHeight(final Window window) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View withTag = window.getDecorView().findViewWithTag(TAG_OFFSET);
            if (withTag == null) return;
            addMarginTopEqualStatusBarHeight(withTag);
        }
    }

    /**
     * 移除 View 向上 StatusBar 同等高度边距
     * @param window {@link Window}
     */
    private static void subtractMarginTopEqualStatusBarHeight(final Window window) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View withTag = window.getDecorView().findViewWithTag(TAG_OFFSET);
            if (withTag == null) return;
            subtractMarginTopEqualStatusBarHeight(withTag);
        }
    }

    // =

    /**
     * 设置 StatusBar 颜色
     * @param activity {@link Activity}
     * @param color    背景颜色
     */
    public static View setStatusBarColor(
            final Activity activity,
            final int color
    ) {
        return setStatusBarColor(activity, color, false);
    }

    /**
     * 设置 StatusBar 颜色
     * @param activity {@link Activity}
     * @param color    背景颜色
     * @param isDecor  {@code true} add DecorView, {@code false} add ContentView
     */
    public static View setStatusBarColor(
            final Activity activity,
            final int color,
            final boolean isDecor
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return null;
        transparentStatusBar(activity);
        return applyStatusBarColor(activity, color, isDecor);
    }

    /**
     * 设置 StatusBar 颜色
     * @param window {@link Window}
     * @param color  背景颜色
     */
    public static View setStatusBarColor(
            final Window window,
            final int color
    ) {
        return setStatusBarColor(window, color, false);
    }

    /**
     * 设置 StatusBar 颜色
     * @param window  {@link Window}
     * @param color   背景颜色
     * @param isDecor {@code true} add DecorView, {@code false} add ContentView
     */
    public static View setStatusBarColor(
            final Window window,
            final int color,
            final boolean isDecor
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return null;
        transparentStatusBar(window);
        return applyStatusBarColor(window, color, isDecor);
    }

    /**
     * 设置 StatusBar 颜色
     * @param fakeStatusBar StatusBar View
     * @param color         背景颜色
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setStatusBarColor(
            final View fakeStatusBar,
            final int color
    ) {
        if (fakeStatusBar != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Activity activity = ActivityUtils.getActivity(fakeStatusBar.getContext());
            if (activity == null) return false;
            transparentStatusBar(activity);
            fakeStatusBar.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = getStatusBarHeight();
            fakeStatusBar.setLayoutParams(layoutParams);
            fakeStatusBar.setBackgroundColor(color);
            return true;
        }
        return false;
    }

    /**
     * 设置自定义 StatusBar View
     * @param fakeStatusBar StatusBar View
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setStatusBarCustom(final View fakeStatusBar) {
        if (fakeStatusBar != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Activity activity = ActivityUtils.getActivity(fakeStatusBar.getContext());
            if (activity == null) return false;
            transparentStatusBar(activity);
            fakeStatusBar.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        getStatusBarHeight()
                );
                fakeStatusBar.setLayoutParams(layoutParams);
            } else {
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = getStatusBarHeight();
                fakeStatusBar.setLayoutParams(layoutParams);
            }
            return true;
        }
        return false;
    }

    /**
     * 设置 DrawerLayout StatusBar 颜色
     * @param drawer        DrawLayout
     * @param fakeStatusBar StatusBar View
     * @param color         背景颜色
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setStatusBarColorDrawer(
            final DrawerLayout drawer,
            final View fakeStatusBar,
            final int color
    ) {
        return setStatusBarColorDrawer(drawer, fakeStatusBar, color, false);
    }

    /**
     * 设置 DrawerLayout StatusBar 颜色
     * <pre>
     *     DrawLayout 必须添加
     *     {@code android:fitsSystemWindows="true"}
     * </pre>
     * @param drawer        DrawLayout
     * @param fakeStatusBar StatusBar View
     * @param color         背景颜色
     * @param isTop         是否设置 DrawerLayout 为顶层
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setStatusBarColorDrawer(
            final DrawerLayout drawer,
            final View fakeStatusBar,
            final int color,
            final boolean isTop
    ) {
        if (drawer != null && fakeStatusBar != null &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Activity activity = ActivityUtils.getActivity(fakeStatusBar.getContext());
            if (activity == null) return false;
            transparentStatusBar(activity);
            drawer.setFitsSystemWindows(false);
            setStatusBarColor(fakeStatusBar, color);
            for (int i = 0, count = drawer.getChildCount(); i < count; i++) {
                drawer.getChildAt(i).setFitsSystemWindows(false);
            }
            if (isTop) {
                hideStatusBarView(activity);
            } else {
                setStatusBarColor(activity, color, false);
            }
            return true;
        }
        return false;
    }

    // =

    /**
     * 设置透明 StatusBar
     * @param activity {@link Activity}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean transparentStatusBar(final Activity activity) {
        return transparentStatusBar(ActivityUtils.getWindow(activity));
    }

    /**
     * 设置透明 StatusBar
     * @param window {@link Window}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean transparentStatusBar(final Window window) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                int vis    = window.getDecorView().getSystemUiVisibility();
                window.getDecorView().setSystemUiVisibility(option | vis);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return true;
        }
        return false;
    }

    /**
     * 应用 StatusBar View
     * @param activity {@link Activity}
     * @param color    背景颜色
     * @param isDecor  是否添加在 DecorView 上
     * @return StatusBar View
     */
    private static View applyStatusBarColor(
            final Activity activity,
            final int color,
            final boolean isDecor
    ) {
        return applyStatusBarColor(ActivityUtils.getWindow(activity), color, isDecor);
    }

    /**
     * 应用 StatusBar View
     * @param window  {@link Window}
     * @param color   背景颜色
     * @param isDecor 是否添加在 DecorView 上
     * @return StatusBar View
     */
    private static View applyStatusBarColor(
            final Window window,
            final int color,
            final boolean isDecor
    ) {
        if (window == null) return null;
        ViewGroup parent            = isDecor ? (ViewGroup) window.getDecorView() : (ViewGroup) window.findViewById(android.R.id.content);
        View      fakeStatusBarView = parent.findViewWithTag(TAG_STATUS_BAR);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        } else {
            fakeStatusBarView = createStatusBarView(window.getContext(), color);
            parent.addView(fakeStatusBarView);
        }
        return fakeStatusBarView;
    }

    /**
     * 隐藏 StatusBar View
     * @param activity {@link Activity}
     */
    private static void hideStatusBarView(final Activity activity) {
        hideStatusBarView(ActivityUtils.getWindow(activity));
    }

    /**
     * 隐藏 StatusBar View
     * @param window {@link Window}
     */
    private static void hideStatusBarView(final Window window) {
        ViewGroup decorView         = (ViewGroup) window.getDecorView();
        View      fakeStatusBarView = decorView.findViewWithTag(TAG_STATUS_BAR);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.GONE);
    }

    /**
     * 显示 StatusBar View
     * @param window {@link Window}
     */
    private static void showStatusBarView(final Window window) {
        ViewGroup decorView         = (ViewGroup) window.getDecorView();
        View      fakeStatusBarView = decorView.findViewWithTag(TAG_STATUS_BAR);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.VISIBLE);
    }

    /**
     * 创建 StatusBar View
     * @param context {@link Context}
     * @param color   背景颜色
     * @return StatusBar View
     */
    private static View createStatusBarView(
            final Context context,
            final int color
    ) {
        View statusBarView = new View(context);
        statusBarView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()
        ));
        statusBarView.setBackgroundColor(color);
        statusBarView.setTag(TAG_STATUS_BAR);
        return statusBarView;
    }

    // ==============
    // = action bar =
    // ==============

    /**
     * 获取 ActionBar 高度
     * @return ActionBar 高度
     */
    public static int getActionBarHeight() {
        TypedValue tv = new TypedValue();
        try {
            if (ResourceUtils.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                return TypedValue.complexToDimensionPixelSize(tv.data, Resources.getSystem().getDisplayMetrics());
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getActionBarHeight");
        }
        return 0;
    }

    // ====================
    // = notification bar =
    // ====================

    /**
     * 设置 Notification Bar 是否显示
     * @param isVisible 是否显示 Notification Bar
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setNotificationBarVisibility(final boolean isVisible) {
        String methodName;
        if (isVisible) {
            methodName = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) ? "expand" : "expandNotificationsPanel";
        } else {
            methodName = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) ? "collapse" : "collapsePanels";
        }
        try {
            @SuppressLint("WrongConstant")
            Object service = AppUtils.getSystemService("statusbar");
            @SuppressLint("PrivateApi")
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod(methodName);
            expand.invoke(service);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setNotificationBarVisibility");
        }
        return false;
    }

    // ==================
    // = navigation bar =
    // ==================

    /**
     * 获取 Navigation Bar 高度
     * @return Navigation Bar 高度
     */
    public static int getNavBarHeight() {
        try {
            Resources resources = Resources.getSystem();
            int       id        = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            if (id != 0) {
                return resources.getDimensionPixelSize(id);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getNavBarHeight");
        }
        return 0;
    }

    /**
     * 设置 Navigation Bar 是否可见
     * @param activity  {@link Activity}
     * @param isVisible 是否显示 Navigation Bar
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setNavBarVisibility(
            final Activity activity,
            final boolean isVisible
    ) {
        return setNavBarVisibility(ActivityUtils.getWindow(activity), isVisible);
    }

    /**
     * 设置 Navigation Bar 是否可见
     * @param window    {@link Window}
     * @param isVisible 是否显示 Navigation Bar
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setNavBarVisibility(
            final Window window,
            final boolean isVisible
    ) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final ViewGroup decorView = (ViewGroup) window.getDecorView();
            for (int i = 0, count = decorView.getChildCount(); i < count; i++) {
                final View child = decorView.getChildAt(i);
                final int  id    = child.getId();
                if (id != View.NO_ID) {
                    String resourceEntryName = Resources.getSystem().getResourceEntryName(id);
                    if ("navigationBarBackground".equals(resourceEntryName)) {
                        child.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
                    }
                }
            }
            final int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            if (isVisible) {
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~uiOptions);
            } else {
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | uiOptions);
            }
            return true;
        }
        return false;
    }

    /**
     * 判断 Navigation Bar 是否可见
     * @param activity {@link Activity}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNavBarVisible(final Activity activity) {
        return isNavBarVisible(ActivityUtils.getWindow(activity));
    }

    /**
     * 判断 Navigation Bar 是否可见
     * @param window {@link Window}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNavBarVisible(final Window window) {
        if (window != null) {
            boolean   isVisible = false;
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            for (int i = 0, count = decorView.getChildCount(); i < count; i++) {
                final View child = decorView.getChildAt(i);
                final int  id    = child.getId();
                if (id != View.NO_ID) {
                    String resourceEntryName = Resources.getSystem().getResourceEntryName(id);
                    if ("navigationBarBackground".equals(resourceEntryName)
                            && child.getVisibility() == View.VISIBLE) {
                        isVisible = true;
                        break;
                    }
                }
            }
            if (isVisible) {
                int visibility = decorView.getSystemUiVisibility();
                isVisible = (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0;
            }
            return isVisible;
        }
        return false;
    }

    /**
     * 判断是否支持 Navigation Bar
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSupportNavBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            WindowManager windowManager = AppUtils.getWindowManager();
            if (windowManager == null) return false;
            Display display  = windowManager.getDefaultDisplay();
            Point   size     = new Point();
            Point   realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y || realSize.x != size.x;
        }
        boolean menu = ViewConfiguration.get(DevUtils.getContext()).hasPermanentMenuKey();
        boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        return !menu && !back;
    }

    /**
     * 设置 Navigation Bar 颜色
     * @param activity {@link Activity}
     * @param color    Navigation Bar 颜色
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setNavBarColor(
            final Activity activity,
            final int color
    ) {
        return setNavBarColor(ActivityUtils.getWindow(activity), color);
    }

    /**
     * 设置 Navigation Bar 颜色
     * @param window {@link Window}
     * @param color  Navigation Bar 颜色
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setNavBarColor(
            final Window window,
            final int color
    ) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(color);
            return true;
        }
        return false;
    }

    /**
     * 获取 Navigation Bar 颜色
     * @param activity {@link Activity}
     * @return Navigation Bar 颜色
     */
    public static int getNavBarColor(final Activity activity) {
        return getNavBarColor(ActivityUtils.getWindow(activity));
    }

    /**
     * 获取 Navigation Bar 颜色
     * @param window {@link Window}
     * @return Navigation Bar 颜色
     */
    public static int getNavBarColor(final Window window) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return window.getNavigationBarColor();
        }
        return -1;
    }

    /**
     * 设置 Navigation Bar 是否高亮模式
     * @param activity    {@link Activity}
     * @param isLightMode 是否高亮模式
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setNavBarLightMode(
            final Activity activity,
            final boolean isLightMode
    ) {
        return setNavBarLightMode(ActivityUtils.getWindow(activity), isLightMode);
    }

    /**
     * 设置 Navigation Bar 是否高亮模式
     * @param window      {@link Window}
     * @param isLightMode 是否高亮模式
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setNavBarLightMode(
            final Window window,
            final boolean isLightMode
    ) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            View decorView = window.getDecorView();
            int  vis       = decorView.getSystemUiVisibility();
            if (isLightMode) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }
            decorView.setSystemUiVisibility(vis);
            return true;
        }
        return false;
    }

    /**
     * 获取 Navigation Bar 是否高亮模式
     * @param activity {@link Activity}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNavBarLightMode(final Activity activity) {
        return isNavBarLightMode(ActivityUtils.getWindow(activity));
    }

    /**
     * 获取 Navigation Bar 是否高亮模式
     * @param window {@link Window}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNavBarLightMode(final Window window) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            View decorView = window.getDecorView();
            int  vis       = decorView.getSystemUiVisibility();
            return (vis & View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR) != 0;
        }
        return false;
    }
}