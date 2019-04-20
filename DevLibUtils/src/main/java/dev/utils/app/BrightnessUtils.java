package dev.utils.app;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.view.Window;
import android.view.WindowManager;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

import static android.Manifest.permission.WRITE_SETTINGS;

/**
 * detail: 亮度相关工具类
 * @author Ttt
 */
public final class BrightnessUtils {

    private BrightnessUtils() {
    }

    // 日志 TAG
    private static final String TAG = BrightnessUtils.class.getSimpleName();

    /**
     * 判断是否开启自动调节亮度
     * @return {@code true} 是, {@code false} 否
     */
    public static boolean isAutoBrightnessEnabled() {
        try {
            int mode = Settings.System.getInt(DevUtils.getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
            return mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isAutoBrightnessEnabled");
            return false;
        }
    }

    /**
     * 设置是否开启自动调节亮度
     * <uses-permission android:name="android.permission.WRITE_SETTINGS" />
     * @param enabled {@code true} 打开, {@code false} 关闭
     * @return {@code true} 成功, {@code false} 失败
     */
    @RequiresPermission(WRITE_SETTINGS)
    public static boolean setAutoBrightnessEnabled(final boolean enabled) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(DevUtils.getContext())) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + DevUtils.getContext().getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                DevUtils.getContext().startActivity(intent);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setAutoBrightnessEnabled");
            }
            return false;
        }
        try {
            return Settings.System.putInt(DevUtils.getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                    enabled ? Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC : Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setAutoBrightnessEnabled");
        }
        return false;
    }

    /**
     * 获取屏幕亮度
     * @return 屏幕亮度 0-255
     */
    public static int getBrightness() {
        try {
            return Settings.System.getInt(DevUtils.getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBrightness");
            return 0;
        }
    }

    /**
     * 设置屏幕亮度
     * <uses-permission android:name="android.permission.WRITE_SETTINGS" />
     * @param brightness 亮度值
     */
    @RequiresPermission(WRITE_SETTINGS)
    public static boolean setBrightness(@IntRange(from = 0, to = 255) final int brightness) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(DevUtils.getContext())) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + DevUtils.getContext().getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                DevUtils.getContext().startActivity(intent);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBrightness");
            }
            return false;
        }
        try {
            ContentResolver resolver = DevUtils.getContext().getContentResolver();
            boolean result = Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
            resolver.notifyChange(Settings.System.getUriFor("screen_brightness"), null);
            return result;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setBrightness");
        }
        return false;
    }

    /**
     * 设置窗口亮度
     * @param window     窗口
     * @param brightness 亮度值
     */
    public static void setWindowBrightness(@NonNull final Window window, @IntRange(from = 0, to = 255) final int brightness) {
        if (window == null) return;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness / 255f;
        window.setAttributes(lp);
    }

    /**
     * 获取窗口亮度
     * @param window 窗口
     * @return 屏幕亮度 0-255
     */
    public static int getWindowBrightness(final Window window) {
        if (window == null) return -1;
        WindowManager.LayoutParams lp = window.getAttributes();
        float brightness = lp.screenBrightness;
        if (brightness < 0) return getBrightness();
        return (int) (brightness * 255);
    }
}
