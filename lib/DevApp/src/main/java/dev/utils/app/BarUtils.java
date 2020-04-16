package dev.utils.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Method;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 状态栏相关工具类
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

    private static final String TAG = BarUtils.class.getSimpleName();

    /**
     * 获取状态栏高度
     * @return 状态栏高度
     */
    public static int getStatusBarHeight() {
        try {
            Resources resources = Resources.getSystem();
            int id = resources.getIdentifier("status_bar_height", "dimen", "android");
            if (id != 0) {
                return resources.getDimensionPixelSize(id);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getStatusBarHeight");
        }
        return 0;
    }

    /**
     * 判断状态栏是否显示
     * @param activity {@link Activity}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isStatusBarVisible(final Activity activity) {
        if (activity != null) {
            try {
                int flags = activity.getWindow().getAttributes().flags;
                return (flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isStatusBarVisible");
            }
        }
        return true;
    }

    // =


    private static final int DEFAULT_ALPHA = 112;
    private static final String TAG_COLOR = "TAG_COLOR";
    private static final String TAG_ALPHA = "TAG_ALPHA";
    private static final String TAG_OFFSET = "TAG_OFFSET";
    private static final int KEY_OFFSET = -123;

    /**
     * 设置状态栏是否显示
     * @param activity  {@link Activity}
     * @param isVisible True to set status bar visible, false otherwise.
     */
    public static void setStatusBarVisibility(@NonNull final Activity activity, final boolean isVisible) {
        setStatusBarVisibility(activity.getWindow(), isVisible);
    }

    /**
     * 设置状态栏是否显示
     * @param window    {@link Window}
     * @param isVisible True to set status bar visible, false otherwise.
     */
    public static void setStatusBarVisibility(@NonNull final Window window, final boolean isVisible) {
        if (isVisible) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            showColorView(window);
            showAlphaView(window);
            addMarginTopEqualStatusBarHeight(window);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            hideColorView(window);
            hideAlphaView(window);
            subtractMarginTopEqualStatusBarHeight(window);
        }
    }

    /**
     * 设置状态是否高亮模式
     * @param activity    {@link Activity}
     * @param isLightMode True to set status bar light mode, false otherwise.
     */
    public static void setStatusBarLightMode(@NonNull final Activity activity, final boolean isLightMode) {
        setStatusBarLightMode(activity.getWindow(), isLightMode);
    }

    /**
     * 设置状态是否高亮模式
     * @param window      {@link Window}
     * @param isLightMode True to set status bar light mode, false otherwise.
     */
    public static void setStatusBarLightMode(@NonNull final Window window, final boolean isLightMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (isLightMode) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    /**
     * 添加状态栏同等高度到 View 的顶部
     * @param view {@link View}
     */
    public static void addMarginTopEqualStatusBarHeight(@NonNull final View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        view.setTag(TAG_OFFSET);
        Object haveSetOffset = view.getTag(KEY_OFFSET);
        if (haveSetOffset != null && (Boolean) haveSetOffset) return;
        MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + getStatusBarHeight(), layoutParams.rightMargin, layoutParams.bottomMargin);
        view.setTag(KEY_OFFSET, true);
    }

    /**
     * 添加状态栏同等高度到 View 的顶部
     * @param view {@link View}
     */
    public static void subtractMarginTopEqualStatusBarHeight(@NonNull final View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Object haveSetOffset = view.getTag(KEY_OFFSET);
        if (haveSetOffset == null || !(Boolean) haveSetOffset) return;
        MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin - getStatusBarHeight(), layoutParams.rightMargin, layoutParams.bottomMargin);
        view.setTag(KEY_OFFSET, false);
    }

    /**
     * 为 view 增加 MarginTop 为状态栏高度
     * @param window {@link Window}
     */
    private static void addMarginTopEqualStatusBarHeight(final Window window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        View withTag = window.getDecorView().findViewWithTag(TAG_OFFSET);
        if (withTag == null) return;
        addMarginTopEqualStatusBarHeight(withTag);
    }

    /**
     * 为 view 减少 MarginTop 为状态栏高度
     * @param window {@link Window}
     */
    private static void subtractMarginTopEqualStatusBarHeight(final Window window) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        View withTag = window.getDecorView().findViewWithTag(TAG_OFFSET);
        if (withTag == null) return;
        subtractMarginTopEqualStatusBarHeight(withTag);
    }

    /**
     * 设置状态栏颜色
     * @param activity {@link Activity}
     * @param color    状态栏颜色
     */
    public static void setStatusBarColor(@NonNull final Activity activity, @ColorInt final int color) {
        setStatusBarColor(activity, color, DEFAULT_ALPHA, false);
    }

    /**
     * 设置状态栏颜色
     * @param activity {@link Activity}
     * @param color    状态栏颜色
     * @param alpha    状态栏透明度
     */
    public static void setStatusBarColor(@NonNull final Activity activity, @ColorInt final int color, @IntRange(from = 0, to = 255) final int alpha) {
        setStatusBarColor(activity, color, alpha, false);
    }

    /**
     * 设置状态栏颜色
     * @param activity {@link Activity}
     * @param color    状态栏颜色
     * @param alpha    状态栏透明度
     * @param isDecor  True to add fake status bar in DecorView,
     *                 false to add fake status bar in ContentView.
     */
    public static void setStatusBarColor(@NonNull final Activity activity, @ColorInt final int color, @IntRange(from = 0, to = 255) final int alpha, final boolean isDecor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        hideAlphaView(activity);
        transparentStatusBar(activity);
        addStatusBarColor(activity, color, alpha, isDecor);
    }

    /**
     * 设置状态栏颜色
     * @param fakeStatusBar The fake status bar view.
     * @param color         状态栏颜色
     */
    public static void setStatusBarColor(@NonNull final View fakeStatusBar, @ColorInt final int color) {
        setStatusBarColor(fakeStatusBar, color, DEFAULT_ALPHA);
    }

    /**
     * 设置状态栏颜色
     * @param fakeStatusBar The fake status bar view.
     * @param color         状态栏颜色
     * @param alpha         状态栏透明度
     */
    public static void setStatusBarColor(@NonNull final View fakeStatusBar, @ColorInt final int color, @IntRange(from = 0, to = 255) final int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        fakeStatusBar.setVisibility(View.VISIBLE);
        transparentStatusBar((Activity) fakeStatusBar.getContext());
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = getStatusBarHeight();
        fakeStatusBar.setBackgroundColor(getStatusBarColor(color, alpha));
    }

    /**
     * 设置状态栏透明度
     * @param activity {@link Activity}
     */
    public static void setStatusBarAlpha(@NonNull final Activity activity) {
        setStatusBarAlpha(activity, DEFAULT_ALPHA, false);
    }

    /**
     * 设置状态栏透明度
     * @param activity {@link Activity}
     * @param alpha    状态栏透明度
     */
    public static void setStatusBarAlpha(@NonNull final Activity activity, @IntRange(from = 0, to = 255) final int alpha) {
        setStatusBarAlpha(activity, alpha, false);
    }

    /**
     * 设置状态栏透明度
     * @param activity {@link Activity}
     * @param alpha    状态栏透明度
     * @param isDecor  True to add fake status bar in DecorView,
     *                 false to add fake status bar in ContentView.
     */
    public static void setStatusBarAlpha(@NonNull final Activity activity, @IntRange(from = 0, to = 255) final int alpha, final boolean isDecor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        hideColorView(activity);
        transparentStatusBar(activity);
        addStatusBarAlpha(activity, alpha, isDecor);
    }

    /**
     * 设置状态栏透明度
     * @param fakeStatusBar The fake status bar view.
     */
    public static void setStatusBarAlpha(@NonNull final View fakeStatusBar) {
        setStatusBarAlpha(fakeStatusBar, DEFAULT_ALPHA);
    }

    /**
     * 设置状态栏透明度
     * @param fakeStatusBar The fake status bar view.
     * @param alpha         状态栏透明度
     */
    public static void setStatusBarAlpha(@NonNull final View fakeStatusBar, @IntRange(from = 0, to = 255) final int alpha) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        fakeStatusBar.setVisibility(View.VISIBLE);
        transparentStatusBar((Activity) fakeStatusBar.getContext());
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = getStatusBarHeight();
        fakeStatusBar.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
    }

    /**
     * 设置自定义状态栏
     * @param fakeStatusBar The fake status bar view.
     */
    public static void setStatusBarCustom(@NonNull final View fakeStatusBar) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        fakeStatusBar.setVisibility(View.VISIBLE);
        transparentStatusBar((Activity) fakeStatusBar.getContext());
        ViewGroup.LayoutParams layoutParams = fakeStatusBar.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = getStatusBarHeight();
    }

    /**
     * 设置状态栏的颜色
     * DrawLayout must add android:fitsSystemWindows="true"
     * @param activity      {@link Activity}
     * @param drawer        The DrawLayout.
     * @param fakeStatusBar The fake status bar view.
     * @param color         状态栏颜色
     * @param isTop         True to set DrawerLayout at the top layer, false otherwise.
     */
    public static void setStatusBarColor4Drawer(@NonNull final Activity activity, @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar, @ColorInt final int color, final boolean isTop) {
        setStatusBarColor4Drawer(activity, drawer, fakeStatusBar, color, DEFAULT_ALPHA, isTop);
    }

    /**
     * 设置状态栏的颜色
     * DrawLayout must add android:fitsSystemWindows="true"
     * @param activity      {@link Activity}
     * @param drawer        The DrawLayout.
     * @param fakeStatusBar The fake status bar view.
     * @param color         状态栏颜色
     * @param alpha         状态栏透明度
     * @param isTop         True to set DrawerLayout at the top layer, false otherwise.
     */
    public static void setStatusBarColor4Drawer(@NonNull final Activity activity, @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar, @ColorInt final int color,
                                                @IntRange(from = 0, to = 255) final int alpha, final boolean isTop) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        drawer.setFitsSystemWindows(false);
        transparentStatusBar(activity);
        setStatusBarColor(fakeStatusBar, color, isTop ? alpha : 0);
        for (int i = 0, len = drawer.getChildCount(); i < len; i++) {
            drawer.getChildAt(i).setFitsSystemWindows(false);
        }
        if (isTop) {
            hideAlphaView(activity);
        } else {
            addStatusBarAlpha(activity, alpha, false);
        }
    }

    /**
     * 设置状态栏透明度
     * DrawLayout must add android:fitsSystemWindows="true"
     * @param activity      {@link Activity}
     * @param drawer        drawerLayout
     * @param fakeStatusBar The fake status bar view.
     * @param isTop         True to set DrawerLayout at the top layer, false otherwise.
     */
    public static void setStatusBarAlpha4Drawer(@NonNull final Activity activity, @NonNull final DrawerLayout drawer,
                                                @NonNull final View fakeStatusBar, final boolean isTop) {
        setStatusBarAlpha4Drawer(activity, drawer, fakeStatusBar, DEFAULT_ALPHA, isTop);
    }

    /**
     * 设置状态栏透明度
     * DrawLayout must add android:fitsSystemWindows="true"
     * @param activity      {@link Activity}
     * @param drawer        drawerLayout
     * @param fakeStatusBar The fake status bar view.
     * @param alpha         状态栏透明度
     * @param isTop         True to set DrawerLayout at the top layer, false otherwise.
     */
    public static void setStatusBarAlpha4Drawer(@NonNull final Activity activity, @NonNull final DrawerLayout drawer, @NonNull final View fakeStatusBar,
                                                @IntRange(from = 0, to = 255) final int alpha, final boolean isTop) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        drawer.setFitsSystemWindows(false);
        transparentStatusBar(activity);
        setStatusBarAlpha(fakeStatusBar, isTop ? alpha : 0);
        for (int i = 0, len = drawer.getChildCount(); i < len; i++) {
            drawer.getChildAt(i).setFitsSystemWindows(false);
        }
        if (isTop) {
            hideAlphaView(activity);
        } else {
            addStatusBarAlpha(activity, alpha, false);
        }
    }

    /**
     * 设置状态栏颜色
     * @param activity {@link Activity}
     * @param color    状态栏颜色
     * @param alpha    状态栏透明度
     * @param isDecor  True to add fake status bar in DecorView,
     *                 false to add fake status bar in ContentView.
     */
    private static void addStatusBarColor(final Activity activity, @ColorInt final int color, @IntRange(from = 0, to = 255) final int alpha, final boolean isDecor) {
        ViewGroup parent = isDecor ? (ViewGroup) activity.getWindow().getDecorView() : (ViewGroup) activity.findViewById(android.R.id.content);
        View fakeStatusBarView = parent.findViewWithTag(TAG_COLOR);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(getStatusBarColor(color, alpha));
        } else {
            parent.addView(createColorStatusBarView(activity, color, alpha));
        }
    }

    /**
     * 设置状态栏透明度
     * @param activity {@link Activity}
     * @param alpha    状态栏透明度
     * @param isDecor  True to add fake status bar in DecorView,
     *                 false to add fake status bar in ContentView.
     */
    private static void addStatusBarAlpha(final Activity activity, @IntRange(from = 0, to = 255) final int alpha, final boolean isDecor) {
        ViewGroup parent = isDecor ? (ViewGroup) activity.getWindow().getDecorView() : (ViewGroup) activity.findViewById(android.R.id.content);
        View fakeStatusBarView = parent.findViewWithTag(TAG_ALPHA);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        } else {
            parent.addView(createAlphaStatusBarView(activity, alpha));
        }
    }

    /**
     * 隐藏颜色 View
     * @param activity {@link Activity}
     */
    private static void hideColorView(final Activity activity) {
        hideColorView(activity.getWindow());
    }

    /**
     * 隐藏 透明度 View
     * @param activity {@link Activity}
     */
    private static void hideAlphaView(final Activity activity) {
        hideAlphaView(activity.getWindow());
    }

    /**
     * 隐藏颜色 View
     * @param window {@link Window}
     */
    private static void hideColorView(final Window window) {
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_COLOR);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.GONE);
    }

    /**
     * 隐藏 透明度 View
     * @param window {@link Window}
     */
    private static void hideAlphaView(final Window window) {
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_ALPHA);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.GONE);
    }

    /**
     * 显示 View 颜色
     * @param window {@link Window}
     */
    private static void showColorView(final Window window) {
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_COLOR);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示 View 透明度
     * @param window {@link Window}
     */
    private static void showAlphaView(final Window window) {
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_ALPHA);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.VISIBLE);
    }

    /**
     * 获取状态栏颜色 ( 传入颜色等, 进行生成 )
     * @param color 状态栏颜色值
     * @param alpha 状态栏透明度
     * @return 状态栏颜色
     */
    private static int getStatusBarColor(@ColorInt final int color, @IntRange(from = 0, to = 255) final int alpha) {
        if (alpha == 0) return color;
        float a = 1 - alpha / 255f;
        int red = (color >> 16) & 0xff;
        int green = (color >> 8) & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return Color.argb(255, red, green, blue);
    }

    /**
     * 创建对应颜色的状态栏 View
     * @param context {@link Context}
     * @param color   状态栏颜色值
     * @param alpha   状态栏透明度
     * @return 对应颜色的状态栏 View
     */
    private static View createColorStatusBarView(final Context context, @ColorInt final int color, @IntRange(from = 0, to = 255) final int alpha) {
        View statusBarView = new View(context);
        statusBarView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
        statusBarView.setBackgroundColor(getStatusBarColor(color, alpha));
        statusBarView.setTag(TAG_COLOR);
        return statusBarView;
    }

    /**
     * 创建对应透明度的状态栏 View
     * @param context {@link Context}
     * @param alpha   状态栏透明度
     * @return 对应透明度的状态栏 View
     */
    private static View createAlphaStatusBarView(final Context context, @IntRange(from = 0, to = 255) final int alpha) {
        View statusBarView = new View(context);
        statusBarView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
        statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
        statusBarView.setTag(TAG_ALPHA);
        return statusBarView;
    }

    /**
     * 设置透明的状态栏
     * @param activity {@link Activity}
     */
    private static void transparentStatusBar(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
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
            if (DevUtils.getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                return TypedValue.complexToDimensionPixelSize(tv.data, ResourceUtils.getDisplayMetrics());
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
    @RequiresPermission(android.Manifest.permission.EXPAND_STATUS_BAR)
    public static boolean setNotificationBarVisibility(final boolean isVisible) {
        String methodName;
        if (isVisible) {
            methodName = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) ? "expand" : "expandNotificationsPanel";
        } else {
            methodName = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) ? "collapse" : "collapsePanels";
        }
        try {
            @SuppressLint("WrongConstant")
            Object service = DevUtils.getContext().getSystemService("statusbar");
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
            int id = resources.getIdentifier("navigation_bar_height", "dimen", "android");
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
    public static boolean setNavBarVisibility(final Activity activity, final boolean isVisible) {
        return setNavBarVisibility(ActivityUtils.getWindow(activity), isVisible);
    }

    /**
     * 设置 Navigation Bar 是否可见
     * @param window    {@link Window}
     * @param isVisible 是否显示 Navigation Bar
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setNavBarVisibility(final Window window, final boolean isVisible) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final ViewGroup decorView = (ViewGroup) window.getDecorView();
            for (int i = 0, count = decorView.getChildCount(); i < count; i++) {
                final View child = decorView.getChildAt(i);
                final int id = child.getId();
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
            boolean isVisible = false;
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            for (int i = 0, count = decorView.getChildCount(); i < count; i++) {
                final View child = decorView.getChildAt(i);
                final int id = child.getId();
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
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
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
    public static boolean setNavBarColor(final Activity activity, @ColorInt final int color) {
        return setNavBarColor(ActivityUtils.getWindow(activity), color);
    }

    /**
     * 设置 Navigation Bar 颜色
     * @param window {@link Window}
     * @param color  Navigation Bar 颜色
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setNavBarColor(final Window window, @ColorInt final int color) {
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
    public static boolean setNavBarLightMode(final Activity activity, final boolean isLightMode) {
        return setNavBarLightMode(ActivityUtils.getWindow(activity), isLightMode);
    }

    /**
     * 设置 Navigation Bar 是否高亮模式
     * @param window      {@link Window}
     * @param isLightMode 是否高亮模式
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setNavBarLightMode(final Window window, final boolean isLightMode) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            View decorView = window.getDecorView();
            int vis = decorView.getSystemUiVisibility();
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
     * 获取 Navigation Bar 是否为高亮模式
     * @param activity {@link Activity}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNavBarLightMode(final Activity activity) {
        return isNavBarLightMode(ActivityUtils.getWindow(activity));
    }

    /**
     * 获取 Navigation Bar 是否为高亮模式
     * @param window {@link Window}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNavBarLightMode(final Window window) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            View decorView = window.getDecorView();
            int vis = decorView.getSystemUiVisibility();
            return (vis & View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR) != 0;
        }
        return false;
    }
}