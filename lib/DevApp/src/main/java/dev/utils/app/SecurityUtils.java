package dev.utils.app;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.security.advancedprotection.AdvancedProtectionManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import java.util.concurrent.Executor;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 安全相关工具类（高级保护模式等）
 * @author Ttt
 * <pre>
 *     Android 17 {@link AdvancedProtectionManager} 用于检测用户是否开启高级保护模式 ( AAPM )，
 *     以便在侧载受限等场景下调整应用行为。
 *     @see <a href="https://developer.android.com/about/versions/17/features">Android 17 Features</a>
 * </pre>
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    // 日志 TAG
    private static final String TAG = SecurityUtils.class.getSimpleName();

    /**
     * 是否支持高级保护模式管理器
     * <pre>
     *     {@link AdvancedProtectionManager} 需 API 37+。
     * </pre>
     * @return {@code true} API 37+, {@code false} 低版本
     */
    public static boolean isAdvancedProtectionSupported() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.CINNAMON_BUN;
    }

    /**
     * 获取高级保护模式管理器
     * <pre>
     *     通过 {@link Context#getSystemService(Class)} 获取
     *     {@link AdvancedProtectionManager} 系统服务实例。
     * </pre>
     * @param context {@link Context}
     * @return {@link AdvancedProtectionManager} 实例；低版本或不可用时返回 null
     */
    @RequiresApi(api = Build.VERSION_CODES.CINNAMON_BUN)
    @Nullable
    public static AdvancedProtectionManager getAdvancedProtectionManager(final Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.CINNAMON_BUN) {
            return null;
        }
        try {
            Context appContext = DevUtils.getContext(context);
            if (appContext == null) {
                return null;
            }
            return appContext.getSystemService(AdvancedProtectionManager.class);
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "getAdvancedProtectionManager");
            return null;
        }
    }

    /**
     * 用户是否已开启高级保护模式
     * @param context {@link Context}
     * @return {@code true} 已开启, {@code false} 未开启、低版本、无服务或异常
     */
    @RequiresPermission(Manifest.permission.QUERY_ADVANCED_PROTECTION_MODE)
    @RequiresApi(api = Build.VERSION_CODES.CINNAMON_BUN)
    public static boolean isAdvancedProtectionEnabled(final Context context) {
        AdvancedProtectionManager manager = getAdvancedProtectionManager(context);
        if (manager == null) {
            return false;
        }
        try {
            return manager.isAdvancedProtectionEnabled();
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "isAdvancedProtectionEnabled");
            return false;
        }
    }

    /**
     * 注册高级保护模式状态回调
     * @param context  {@link Context}
     * @param executor 回调线程 {@link Executor}
     * @param callback {@link AdvancedProtectionManager.Callback}
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.QUERY_ADVANCED_PROTECTION_MODE)
    @RequiresApi(api = Build.VERSION_CODES.CINNAMON_BUN)
    public static boolean registerAdvancedProtectionCallback(
            final Context context,
            @NonNull final Executor executor,
            @NonNull final AdvancedProtectionManager.Callback callback
    ) {
        AdvancedProtectionManager manager = getAdvancedProtectionManager(context);
        if (manager == null || executor == null || callback == null) {
            return false;
        }
        try {
            manager.registerAdvancedProtectionCallback(executor, callback);
            return true;
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "registerAdvancedProtectionCallback");
            return false;
        }
    }

    /**
     * 注销高级保护模式状态回调
     * @param context  {@link Context}
     * @param callback 与注册时相同的 {@link AdvancedProtectionManager.Callback} 实例
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.QUERY_ADVANCED_PROTECTION_MODE)
    @RequiresApi(api = Build.VERSION_CODES.CINNAMON_BUN)
    public static boolean unregisterAdvancedProtectionCallback(
            final Context context,
            @NonNull final AdvancedProtectionManager.Callback callback
    ) {
        AdvancedProtectionManager manager = getAdvancedProtectionManager(context);
        if (manager == null || callback == null) {
            return false;
        }
        try {
            manager.unregisterAdvancedProtectionCallback(callback);
            return true;
        } catch (Throwable e) {
            LogPrintUtils.eTag(TAG, e, "unregisterAdvancedProtectionCallback");
            return false;
        }
    }
}