package dev.utils.app;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Method;
import java.text.DecimalFormat;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 屏幕相关工具类
 * @author Ttt
 * <pre>
 *     计算屏幕尺寸
 *     @see <a href="https://blog.csdn.net/lincyang/article/details/42679589"/>
 *     <p></p>
 *     所需权限
 *     <uses-permission android:name="android.permission.WRITE_SETTINGS" />
 * </pre>
 */
public final class ScreenUtils {

    private ScreenUtils() {
    }

    // 日志 TAG
    private static final String TAG = ScreenUtils.class.getSimpleName();

    /**
     * 获取 DisplayMetrics
     * @return {@link DisplayMetrics}
     */
    public static DisplayMetrics getDisplayMetrics() {
        try {
            WindowManager  windowManager  = AppUtils.getWindowManager();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDisplayMetrics");
        }
        return null;
    }

    // ===========
    // = 宽高获取 =
    // ===========

    /**
     * 获取屏幕宽度
     * @return 屏幕宽度
     */
    public static int getScreenWidth() {
        return getScreenWidthHeight()[0];
    }

    /**
     * 获取屏幕高度
     * @return 屏幕高度
     */
    public static int getScreenHeight() {
        return getScreenWidthHeight()[1];
    }

    // =

    /**
     * 获取屏幕宽高
     * @return int[], 0 = 宽度, 1 = 高度
     */
    public static int[] getScreenWidthHeight() {
        try {
//            DisplayMetrics displayMetrics = ResourceUtils.getDisplayMetrics();
//            return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};

            WindowManager windowManager = AppUtils.getWindowManager();
            Point         point         = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                windowManager.getDefaultDisplay().getRealSize(point);
            } else {
                windowManager.getDefaultDisplay().getSize(point);
            }
            return new int[]{point.x, point.y};
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getScreenWidthHeight");
        }
        return new int[]{0, 0};
    }

    /**
     * 获取屏幕宽高
     * @return {@link Point}, point.x 宽, point.y 高
     */
    public static Point getScreenWidthHeightToPoint() {
        try {
//            DisplayMetrics displayMetrics = ResourceUtils.getDisplayMetrics();
//            return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);

            WindowManager windowManager = AppUtils.getWindowManager();
            Point         point         = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                windowManager.getDefaultDisplay().getRealSize(point);
            } else {
                windowManager.getDefaultDisplay().getSize(point);
            }
            return point;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getScreenWidthHeightToPoint");
        }
        return null;
    }

    // =

    /**
     * 获取屏幕分辨率
     * @return 屏幕分辨率
     */
    public static String getScreenSize() {
        return getScreenSize("x");
    }

    /**
     * 获取屏幕分辨率
     * @param symbol 拼接符号
     * @return 屏幕分辨率
     */
    public static String getScreenSize(final String symbol) {
        // 获取分辨率
        int[] widthHeight = getScreenWidthHeight();
        // 返回分辨率信息
        return widthHeight[1] + symbol + widthHeight[0];
    }

    /**
     * 获取屏幕英寸 ( 例 5.5 英寸 )
     * @return 屏幕英寸
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static String getScreenSizeOfDevice() {
        try {
            Point          point          = new Point();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager  windowManager  = AppUtils.getWindowManager();
            windowManager.getDefaultDisplay().getRealSize(point);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            // 计算尺寸
            double x            = Math.pow(point.x / displayMetrics.xdpi, 2);
            double y            = Math.pow(point.y / displayMetrics.ydpi, 2);
            double screenInches = Math.sqrt(x + y);
            // 转换大小
            return new DecimalFormat("#.0").format(screenInches);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getScreenSizeOfDevice");
        }
        return "unknown";
    }

    // =

    /**
     * 获取屏幕密度
     * @return 屏幕密度
     */
    public static float getDensity() {
        DisplayMetrics displayMetrics = getDisplayMetrics();
        // 屏幕密度, 如 (0.75 / 1.0 / 1.5 / 2.0)
        return (displayMetrics != null) ? displayMetrics.density : 0f;
    }

    /**
     * 获取屏幕密度 dpi
     * @return 屏幕密度 dpi
     */
    public static int getDensityDpi() {
        DisplayMetrics displayMetrics = getDisplayMetrics();
        // 屏幕密度 DPI, 如 (120 / 160 / 240 / 320)
        return (displayMetrics != null) ? displayMetrics.densityDpi : 0;
    }

    /**
     * 获取屏幕缩放密度
     * @return 屏幕缩放密度
     */
    public static float getScaledDensity() {
        DisplayMetrics displayMetrics = getDisplayMetrics();
        return (displayMetrics != null) ? displayMetrics.scaledDensity : 0f;
    }

    /**
     * 获取 X 轴 dpi
     * @return X 轴 dpi
     */
    public static float getXDpi() {
        DisplayMetrics displayMetrics = getDisplayMetrics();
        return (displayMetrics != null) ? displayMetrics.xdpi : 0f;
    }

    /**
     * 获取 Y 轴 dpi
     * @return Y 轴 dpi
     */
    public static float getYDpi() {
        DisplayMetrics displayMetrics = getDisplayMetrics();
        return (displayMetrics != null) ? displayMetrics.ydpi : 0f;
    }

    /**
     * 获取宽度比例 dpi 基准
     * @return 宽度比例 dpi 基准
     */
    public static float getWidthDpi() {
        DisplayMetrics displayMetrics = getDisplayMetrics();
        return (displayMetrics != null) ? (displayMetrics.widthPixels / displayMetrics.density) : 0f;
    }

    /**
     * 获取高度比例 dpi 基准
     * @return 高度比例 dpi 基准
     */
    public static float getHeightDpi() {
        DisplayMetrics displayMetrics = getDisplayMetrics();
        return (displayMetrics != null) ? (displayMetrics.heightPixels / displayMetrics.density) : 0f;
    }

    /**
     * 获取屏幕信息
     * @return 屏幕信息
     */
    public static String getScreenInfo() {
        StringBuilder  builder        = new StringBuilder();
        DisplayMetrics displayMetrics = getDisplayMetrics();
        if (displayMetrics != null) {
            try {
                int heightPixels = displayMetrics.heightPixels;
                int widthPixels  = displayMetrics.widthPixels;

                float xdpi       = displayMetrics.xdpi;
                float ydpi       = displayMetrics.ydpi;
                int   densityDpi = displayMetrics.densityDpi;

                float density       = displayMetrics.density;
                float scaledDensity = displayMetrics.scaledDensity;

                float heightDpi = heightPixels / density;
                float widthDpi  = widthPixels / density;

                builder.append("heightPixels: ").append(heightPixels).append("px");
                builder.append("\nwidthPixels: ").append(widthPixels).append("px");

                builder.append("\nxdpi: ").append(xdpi).append("dpi");
                builder.append("\nydpi: ").append(ydpi).append("dpi");
                builder.append("\ndensityDpi: ").append(densityDpi).append("dpi");

                builder.append("\ndensity: ").append(density);
                builder.append("\nscaledDensity: ").append(scaledDensity);

                builder.append("\nheightDpi: ").append(heightDpi).append("dpi");
                builder.append("\nwidthDpi: ").append(widthDpi).append("dpi");
                return builder.toString();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getScreenInfo");
            }
        }
        return builder.toString();
    }

    // =

    /**
     * 设置禁止截屏
     * @param activity {@link Activity}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setWindowSecure(final Activity activity) {
        try {
            // 禁止截屏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setWindowSecure");
        }
        return false;
    }

    /**
     * 屏幕是否为全屏
     * @param activity {@link Activity}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isFullScreen(final Activity activity) {
        if (activity != null) {
            try {
                int flags = activity.getWindow().getAttributes().flags;
                return (flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isFullScreen");
            }
        }
        return false;
    }

    /**
     * 设置屏幕为全屏
     * @param activity {@link Activity}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setFullScreen(final Activity activity) {
        try {
            // 设置全屏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setFullScreen");
        }
        return false;
    }

    /**
     * 设置屏幕为全屏无标题
     * <pre>
     *     需要在 setContentView 之前调用
     * </pre>
     * @param activity {@link Activity}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setFullScreenNoTitle(final Activity activity) {
        try {
            // 隐藏标题
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
            // 设置全屏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setFullScreenNoTitle");
        }
        return false;
    }

    /**
     * 设置屏幕为横屏
     * <pre>
     *     还有一种就是在 Activity 中加属性 android:screenOrientation="landscape"
     *     不设置 Activity 的 android:configChanges 时
     *     切屏会重新调用各个生命周期, 切横屏时会执行一次, 切竖屏时会执行两次
     *     设置 Activity 的 android:configChanges="orientation" 时
     *     切屏还是会重新调用各个生命周期, 切横、竖屏时只会执行一次
     *     设置 Activity 的 android:configChanges="orientation|keyboardHidden|screenSize"
     *     4.0 以上必须带最后一个参数时
     *     切屏不会重新调用各个生命周期, 只会执行 onConfigurationChanged 方法
     * </pre>
     * @param activity {@link Activity}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setLandscape(final Activity activity) {
        try {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setLandscape");
        }
        return false;
    }

    /**
     * 设置屏幕为竖屏
     * @param activity {@link Activity}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setPortrait(final Activity activity) {
        try {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setPortrait");
        }
        return false;
    }

    /**
     * 判断是否横屏
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLandscape() {
        return isLandscape(DevUtils.getContext());
    }

    /**
     * 判断是否横屏
     * @param context {@link Context}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLandscape(final Context context) {
        try {
            return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isLandscape");
        }
        return false;
    }

    /**
     * 判断是否竖屏
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPortrait() {
        return isPortrait(DevUtils.getContext());
    }

    /**
     * 判断是否竖屏
     * @param context {@link Context}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPortrait(final Context context) {
        try {
            return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isPortrait");
        }
        return false;
    }

    /**
     * 切换屏幕方向
     * @param activity {@link Activity}
     * @return {@code true} 横屏, {@code false} 竖屏
     */
    public static boolean toggleScreenOrientation(final Activity activity) {
        try {
            // 判断是否竖屏
            if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                return true; // 切换横屏, 并且表示属于横屏
            } else {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return false; // 切换竖屏, 并且表示属于竖屏
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "toggleScreenOrientation");
        }
        return false;
    }

    // =

    /**
     * 获取屏幕旋转角度
     * @param activity {@link Activity}
     * @return 屏幕旋转角度
     */
    public static int getScreenRotation(final Activity activity) {
        try {
            switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
                case Surface.ROTATION_0:
                    return 0;
                case Surface.ROTATION_90:
                    return 90;
                case Surface.ROTATION_180:
                    return 180;
                case Surface.ROTATION_270:
                    return 270;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getScreenRotation");
        }
        return 0;
    }

    /**
     * 判断是否锁屏
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isScreenLock() {
        try {
            KeyguardManager keyguardManager = AppUtils.getKeyguardManager();
            return keyguardManager != null && keyguardManager.inKeyguardRestrictedInputMode();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isScreenLock");
        }
        return false;
    }

    /**
     * 判断是否是平板
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isTablet() {
        return DeviceUtils.isTablet();
    }

    // =

    /**
     * 获取 StatusBar 高度
     * @return StatusBar 高度
     */
    public static int getStatusBarHeight() {
        return BarUtils.getStatusBarHeight();
    }

    /**
     * 设置进入休眠时长
     * @param duration 时长
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setSleepDuration(final int duration) {
        try {
            Settings.System.putInt(ResourceUtils.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, duration);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setSleepDuration");
        }
        return false;
    }

    /**
     * 获取进入休眠时长
     * @return 进入休眠时长
     */
    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(ResourceUtils.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSleepDuration");
            return -1;
        }
    }

    // =

    /**
     * 获取底部导航栏高度
     * @return 底部导航栏高度
     */
    public static int getNavigationBarHeight() {
        try {
            Resources resources = ResourceUtils.getResources();
            // 获取对应方向字符串
            String orientation = resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape";
            // 获取对应的 id
            int resourceId = resources.getIdentifier(orientation, "dimen", "android");
            if (resourceId > 0 && checkDeviceHasNavigationBar()) {
                return resources.getDimensionPixelSize(resourceId);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getNavigationBarHeight");
        }
        return 0;
    }

    /**
     * 检测是否具有底部导航栏
     * <pre>
     *     一加手机上判断不准确
     * </pre>
     * @return {@code true} yes, {@code false} no
     */
    public static boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        try {
            Resources resources = ResourceUtils.getResources();
            int       id        = resources.getIdentifier("config_showNavigationBar", "bool", "android");
            if (id > 0) {
                hasNavigationBar = resources.getBoolean(id);
            }
            try {
                Class  systemPropertiesClass = Class.forName("android.os.SystemProperties");
                Method method                = systemPropertiesClass.getMethod("get", String.class);
                String navBarOverride        = (String) method.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
                if ("1".equals(navBarOverride)) {
                    hasNavigationBar = false;
                } else if ("0".equals(navBarOverride)) {
                    hasNavigationBar = true;
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "checkDeviceHasNavigationBar - SystemProperties");
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "checkDeviceHasNavigationBar");
        }
        return hasNavigationBar;
    }
}