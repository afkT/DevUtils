package dev.utils.app;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IntRange;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 亮度相关工具类
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.WRITE_SETTINGS" />
 * </pre>
 */
public final class BrightnessUtils {

    private BrightnessUtils() {
    }

    // 日志 TAG
    private static final String TAG = BrightnessUtils.class.getSimpleName();

    /**
     * 判断是否开启自动调节亮度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAutoBrightnessEnabled() {
        try {
            int mode = Settings.System.getInt(ResourceUtils.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
            return mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isAutoBrightnessEnabled");
            return false;
        }
    }

    /**
     * 设置是否开启自动调节亮度
     * @param enabled {@code true} 打开, {@code false} 关闭
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setAutoBrightnessEnabled(final boolean enabled) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(DevUtils.getContext())) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + AppUtils.getPackageName()));
                AppUtils.startActivity(intent);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setAutoBrightnessEnabled");
            }
            return false;
        }
        try {
            return Settings.System.putInt(ResourceUtils.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE,
                    enabled ? Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC : Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setAutoBrightnessEnabled");
        }
        return false;
    }

    /**
     * 获取屏幕亮度 0-255
     * @return 屏幕亮度
     */
    public static int getBrightness() {
        try {
            return Settings.System.getInt(ResourceUtils.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBrightness");
            return 0;
        }
    }

    /**
     * 设置屏幕亮度
     * @param brightness 亮度值
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setBrightness(@IntRange(from = 0, to = 255) final int brightness) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(DevUtils.getContext())) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + AppUtils.getPackageName()));
                AppUtils.startActivity(intent);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setBrightness");
            }
            return false;
        }
        try {
            ContentResolver resolver = ResourceUtils.getContentResolver();
            boolean         result   = Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
            resolver.notifyChange(Settings.System.getUriFor("screen_brightness"), null);
            return result;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setBrightness");
        }
        return false;
    }

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
    public static int getWindowBrightness(final Window window) {
        if (window == null) return -1;
        try {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            float                      brightness   = layoutParams.screenBrightness;
            if (brightness < 0) return getBrightness();
            return (int) (brightness * 255);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWindowBrightness");
        }
        return 0;
    }
}