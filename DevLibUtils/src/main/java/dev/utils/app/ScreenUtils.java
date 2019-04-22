package dev.utils.app;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.text.DecimalFormat;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 屏幕相关工具类
 * @author Ttt
 * <pre>
 *      计算屏幕尺寸
 *      @see <a href="https://blog.csdn.net/lincyang/article/details/42679589"/>
 *      <p></p>
 *      截图
 *      @see <a href="https://www.cnblogs.com/angel88/p/7933437.html"/>
 *      @see <a href="https://github.com/weizongwei5/AndroidScreenShot_SysApi"/>
 * </pre>
 */
public final class ScreenUtils {

    private ScreenUtils() {
    }

    // 日志 TAG
    private static final String TAG = ScreenUtils.class.getSimpleName();

    /**
     * 获取 DisplayMetrics
     * @return
     */
    public static DisplayMetrics getDisplayMetrics() {
        try {
            WindowManager wManager = (WindowManager) DevUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
            if (wManager != null) {
                DisplayMetrics dMetrics = new DisplayMetrics();
                wManager.getDefaultDisplay().getMetrics(dMetrics);
                return dMetrics;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDisplayMetrics");
        }
        return null;
    }

    /**
     * 获取屏幕的宽度(单位 px)
     * @return 屏幕宽
     */
    public static int getScreenWidth() {
        try {
            WindowManager windowManager = (WindowManager) DevUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
            if (windowManager == null) {
                return DevUtils.getContext().getResources().getDisplayMetrics().widthPixels;
            }
            Point point = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                windowManager.getDefaultDisplay().getRealSize(point);
            } else {
                windowManager.getDefaultDisplay().getSize(point);
            }
            return point.x;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getScreenWidth");
        }
        return 0;
    }

    /**
     * 获取屏幕的高度(单位 px)
     * @return 屏幕高
     */
    public static int getScreenHeight() {
        try {
            WindowManager windowManager = (WindowManager) DevUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
            if (windowManager == null) {
                return DevUtils.getContext().getResources().getDisplayMetrics().heightPixels;
            }
            Point point = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                windowManager.getDefaultDisplay().getRealSize(point);
            } else {
                windowManager.getDefaultDisplay().getSize(point);
            }
            return point.y;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getScreenHeight");
        }
        return 0;
    }

    /**
     * 通过 Context 获取屏幕宽度高度
     * @return point.x 宽, point.y 高
     */
    public static Point getScreenWidthHeightToPoint() {
        try {
            WindowManager windowManager = (WindowManager) DevUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
            if (windowManager == null) {
                DisplayMetrics dMetrics = DevUtils.getContext().getResources().getDisplayMetrics();
                return new Point(dMetrics.widthPixels, dMetrics.heightPixels);
            }
            Point point = new Point();
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

    /**
     * 通过 Context 获取屏幕宽度高度
     * @return int[] 0 = 宽度，1 = 高度
     */
    public static int[] getScreenWidthHeight() {
        try {
            WindowManager windowManager = (WindowManager) DevUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
            if (windowManager == null) {
                DisplayMetrics dMetrics = DevUtils.getContext().getResources().getDisplayMetrics();
                return new int[]{dMetrics.widthPixels, dMetrics.heightPixels};
            }
            Point point = new Point();
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
     * 获取屏幕分辨率
     * @return
     */
    public static String getScreenSize() {
        try {
            // 获取分辨率
            int[] whArys = getScreenWidthHeight();
            // 返回分辨率信息
            return whArys[1] + "x" + whArys[0];
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getScreenSize");
        }
        return "unknown";
    }

    /**
     * 获取屏幕英寸 例5.5英寸
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static String getScreenSizeOfDevice() {
        try {
            Point point = new Point();
            WindowManager windowManager = (WindowManager) DevUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getRealSize(point);
            DisplayMetrics dm = DevUtils.getContext().getResources().getDisplayMetrics();
            double x = Math.pow(point.x / dm.xdpi, 2);
            double y = Math.pow(point.y / dm.ydpi, 2);
            double screenInches = Math.sqrt(x + y);
            // 转换大小
            DecimalFormat df = new DecimalFormat("#.0");
            return df.format(screenInches);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getScreenSizeOfDevice");
        }
        return "unknown";
    }

    // =

    /**
     * 通过 Context 获取屏幕密度
     * @return
     */
    public static float getDensity() {
        try {
            // 获取屏幕信息
            DisplayMetrics dMetrics = getDisplayMetrics();
            if (dMetrics != null) {
                // 屏幕密度(0.75 / 1.0 / 1.5 / 2.0)
                return dMetrics.density;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDensity");
        }
        return 0;
    }

    /**
     * 通过 Context 获取屏幕密度Dpi
     * @return
     */
    public static int getDensityDpi() {
        try {
            // 获取屏幕信息
            DisplayMetrics dMetrics = getDisplayMetrics();
            if (dMetrics != null) {
                // 屏幕密度DPI(120 / 160 / 240 / 320)
                return dMetrics.densityDpi;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDensityDpi");
        }
        return 0;
    }

    /**
     * 通过 Context 获取屏幕缩放密度
     * @return
     */
    public static float getScaledDensity() {
        try {
            // 获取屏幕信息
            DisplayMetrics dMetrics = getDisplayMetrics();
            if (dMetrics != null) {
                return dMetrics.scaledDensity;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getScaledDensity");
        }
        return 0f;
    }

    /**
     * 获取 X轴 dpi
     * @return
     */
    public static float getXDpi() {
        try {
            // 获取屏幕信息
            DisplayMetrics dMetrics = getDisplayMetrics();
            if (dMetrics != null) {
                return dMetrics.xdpi;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getXDpi");
        }
        return 0f;
    }

    /**
     * 获取 Y轴 dpi
     * @return
     */
    public static float getYDpi() {
        try {
            // 获取屏幕信息
            DisplayMetrics dMetrics = getDisplayMetrics();
            if (dMetrics != null) {
                return dMetrics.ydpi;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getYDpi");
        }
        return 0f;
    }

    /**
     * 获取 宽度比例 dpi 基准
     * @return
     */
    public static float getWidthDpi() {
        try {
            // 获取屏幕信息
            DisplayMetrics dMetrics = getDisplayMetrics();
            if (dMetrics != null) {
                return dMetrics.widthPixels / dMetrics.density;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWidthDpi");
        }
        return 0f;
    }

    /**
     * 获取 高度比例 dpi 基准
     * @return
     */
    public static float getHeightDpi() {
        try {
            // 获取屏幕信息
            DisplayMetrics dMetrics = getDisplayMetrics();
            if (dMetrics != null) {
                return dMetrics.heightPixels / dMetrics.density;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getHeightDpi");
        }
        return 0f;
    }

    /**
     * 获取屏幕信息
     * @return
     */
    public static String getScreenInfo() {
        StringBuilder builder = new StringBuilder();
        // 获取屏幕信息
        DisplayMetrics dMetrics = getDisplayMetrics();
        if (dMetrics != null) {
            try {
                int heightPixels = dMetrics.heightPixels;
                int widthPixels = dMetrics.widthPixels;

                float xdpi = dMetrics.xdpi;
                float ydpi = dMetrics.ydpi;
                int densityDpi = dMetrics.densityDpi;

                float density = dMetrics.density;
                float scaledDensity = dMetrics.scaledDensity;

                float heightDpi = heightPixels / density;
                float widthDpi = widthPixels / density;
                // =
                builder.append("\nheightPixels: " + heightPixels + "px");
                builder.append("\nwidthPixels: " + widthPixels + "px");

                builder.append("\nxdpi: " + xdpi + "dip");
                builder.append("\nydpi: " + ydpi + "dpi");
                builder.append("\ndensityDpi: " + densityDpi + "dpi");

                builder.append("\ndensity: " + density);
                builder.append("\nscaledDensity: " + scaledDensity);

                builder.append("\nheightDpi: " + heightDpi + "dpi");
                builder.append("\nwidthDpi: " + widthDpi + "dpi");

                return builder.toString();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getScreenInfo");
            }
        }
        return builder.toString();
    }

    // =

    /**
     * 设置屏幕为全屏
     * @param activity
     */
    public static void setFullScreen(@NonNull final Activity activity) {
        try {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setFullScreen");
        }
    }

    /**
     * 设置屏幕为横屏
     * 还有一种就是在 Activity 中加属性 android:screenOrientation="landscape"
     * 不设置 Activity 的 android:configChanges 时，
     * 切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次
     * 设置 Activity 的 android:configChanges="orientation"时，
     * 切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次
     * 设置 Activity 的 android:configChanges="orientation|keyboardHidden|screenSize"
     * (4.0 以上必须带最后一个参数)时
     * 切屏不会重新调用各个生命周期，只会执行 onConfigurationChanged 方法
     * @param activity
     */
    public static void setLandscape(@NonNull final Activity activity) {
        try {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setLandscape");
        }
    }

    /**
     * 设置屏幕为竖屏
     * @param activity
     */
    public static void setPortrait(@NonNull final Activity activity) {
        try {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setPortrait");
        }
    }

    /**
     * 判断是否横屏
     * @return {@code true} 是, {@code false} 否
     */
    public static boolean isLandscape() {
        try {
            return DevUtils.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isLandscape");
        }
        return false;
    }

    /**
     * 判断是否竖屏
     * @return {@code true} 是, {@code false} 否
     */
    public static boolean isPortrait() {
        try {
            return DevUtils.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isPortrait");
        }
        return false;
    }

    /**
     * 获取屏幕旋转角度
     * @param activity
     * @return 屏幕旋转角度
     */
    public static int getScreenRotation(@NonNull final Activity activity) {
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
                default:
                    return 0;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getScreenRotation");
        }
        return 0;
    }

    /**
     * 判断是否锁屏
     * @return {@code true} 是, {@code false} 否
     */
    public static boolean isScreenLock() {
        try {
            KeyguardManager keyguardManager = (KeyguardManager) DevUtils.getContext().getSystemService(Context.KEYGUARD_SERVICE);
            return keyguardManager != null && keyguardManager.inKeyguardRestrictedInputMode();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isScreenLock");
        }
        return false;
    }

    /**
     * 判断是否是平板
     * @return {@code true} 是, {@code false} 否
     */
    public static boolean isTablet() {
        try {
            return (DevUtils.getContext().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isTablet");
        }
        return false;
    }

    // =

    /**
     * 获取状态栏的高度(无关 android:theme 获取状态栏高度)
     * @return
     */
    public static int getStatusHeight() {
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            return DevUtils.getContext().getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getStatusHeight");
        }
        return 0;
    }

    /**
     * 获取应用区域 TitleBar 高度 (顶部灰色TitleBar高度，没有设置 android:theme 的 NoTitleBar 时会显示)
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(final Activity activity) {
        try {
            Rect rect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            return rect.top;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getStatusBarHeight");
        }
        return 0;
    }

    /**
     * 设置进入休眠时长
     * <uses-permission android:name="android.permission.WRITE_SETTINGS" />
     * @param duration 时长
     */
    @RequiresPermission(Manifest.permission.WRITE_SETTINGS)
    public static void setSleepDuration(final int duration) {
        try {
            Settings.System.putInt(DevUtils.getContext().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, duration);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setSleepDuration");
        }
    }

    /**
     * 获取进入休眠时长
     * @return 进入休眠时长，报错返回 -1
     */
    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(DevUtils.getContext().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSleepDuration");
            return -1;
        }
    }

    // ==============
    // = 截图(有用) =
    // ==============

    /**
     * 获取当前屏幕截图，包含状态栏 (顶部灰色TitleBar高度，没有设置 android:theme 的 NoTitleBar 时会显示)
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(final Activity activity) {
        try {
            View view = activity.getWindow().getDecorView();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            Bitmap bmp = view.getDrawingCache();
            int[] sParams = getScreenWidthHeight();

            // 主要是 getWindowVisibleDisplayFrame 内容才不为null
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            // 创建新的图片
            Bitmap bitmap = Bitmap.createBitmap(bmp, 0, 0, sParams[0], sParams[1]);
            view.destroyDrawingCache();
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "snapShotWithStatusBar");
        }
        return null;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏 (如果 android:theme 全屏了，则截图无状态栏)
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(final Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int[] sParams = getScreenWidthHeight();

        // 获取状态栏高度
        int statusBarHeight = getStatusBarHeight(activity);

        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        // 创建新的图片
        Bitmap bitmap = Bitmap.createBitmap(bmp, 0, statusBarHeight, sParams[0], sParams[1] - statusBarHeight);
        view.destroyDrawingCache();
        return bitmap;
    }

    // =

    /**
     * 获取底部导航栏高度
     * @return
     */
    public static int getNavigationBarHeight() {
        int navigationBarHeight = 0;
        try {
            Resources resources = DevUtils.getContext().getResources();
            // 获取对应方向字符串
            String orientation = resources.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape";
            // 获取对应的id
            int resourceId = resources.getIdentifier(orientation, "dimen", "android");
            if (resourceId > 0 && checkDeviceHasNavigationBar()) {
                navigationBarHeight = resources.getDimensionPixelSize(resourceId);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getNavigationBarHeight");
        }
        return navigationBarHeight;
    }

    /**
     * 检测是否具有底部导航栏
     * <pre>
     *      一加手机上判断不准确
     * </pre>
     * @return
     */
    public static boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        try {
            Resources resources = DevUtils.getContext().getResources();
            int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
            if (id > 0) {
                hasNavigationBar = resources.getBoolean(id);
            }
            try {
                Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
                Method method = systemPropertiesClass.getMethod("get", String.class);
                String navBarOverride = (String) method.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
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

