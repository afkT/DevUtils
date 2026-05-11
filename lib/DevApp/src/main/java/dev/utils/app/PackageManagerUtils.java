package dev.utils.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;

import java.util.Collections;
import java.util.List;

import dev.utils.LogPrintUtils;

/**
 * detail: PackageManager 查询相关工具类
 * @author Ttt
 */
public final class PackageManagerUtils {

    private PackageManagerUtils() {
    }

    // 日志 TAG
    private static final String TAG = PackageManagerUtils.class.getSimpleName();

    /**
     * 解析 Activity 的常用 flags：默认匹配
     * <pre>
     *     等价历史代码里对「默认匹配」的 {@link PackageManager#MATCH_DEFAULT_ONLY}
     * </pre>
     * @return {@link PackageManager#MATCH_DEFAULT_ONLY}
     */
    public static long flagsMatchDefaultOnly() {
        return PackageManager.MATCH_DEFAULT_ONLY;
    }

    /**
     * 解析 Activity 的常用 flags：完整枚举
     * <pre>
     *     需完整枚举候选组件时（如桌面图标列表），与历史 {@link PackageManager#MATCH_ALL} 语义一致；
     *     仍受包可见性限制，且调用方应清楚性能与隐私边界。
     * </pre>
     * @return {@link PackageManager#MATCH_ALL}
     */
    public static long flagsMatchAll() {
        return PackageManager.MATCH_ALL;
    }

    // =

    /**
     * 获取 PackageManager
     * @return {@link PackageManager}
     */
    public static PackageManager getPackageManager() {
        return AppUtils.getPackageManager();
    }

    /**
     * 获取 PackageManager
     * @param context Context
     * @return {@link PackageManager}
     */
    public static PackageManager getPackageManager(final Context context) {
        return AppUtils.getPackageManager(context);
    }

    // =

    /**
     * 查询匹配 Intent 的 Activity 列表（兼容封装）
     * <pre>
     *     封装 {@link PackageManager#queryIntentActivities(Intent, int)} 与 API 33+ 的 ResolveInfoFlags
     * </pre>
     * @param intent {@link Intent}
     * @param flags  查询 flags，多使用 {@link PackageManager#MATCH_DEFAULT_ONLY}、{@link PackageManager#MATCH_ALL} 等
     * @return 查询结果，失败或参数非法时返回空列表（非 null）
     */
    public static List<ResolveInfo> queryIntentActivitiesCompat(
            final Intent intent,
            final long flags
    ) {
        return queryIntentActivitiesCompat(
                getPackageManager(), intent, flags
        );
    }

    /**
     * 查询匹配 Intent 的 Activity 列表（兼容封装）
     * <pre>
     *     封装 {@link PackageManager#queryIntentActivities(Intent, int)} 与 API 33+ 的 ResolveInfoFlags
     * </pre>
     * @param packageManager {@link PackageManager}
     * @param intent         {@link Intent}
     * @param flags          查询 flags，多使用 {@link PackageManager#MATCH_DEFAULT_ONLY}、{@link PackageManager#MATCH_ALL} 等
     * @return 查询结果，失败或参数非法时返回空列表（非 null）
     */
    public static List<ResolveInfo> queryIntentActivitiesCompat(
            final PackageManager packageManager,
            final Intent intent,
            final long flags
    ) {
        if (packageManager == null || intent == null) {
            return Collections.emptyList();
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                return packageManager.queryIntentActivities(
                        intent, PackageManager.ResolveInfoFlags.of(flags));
            }
            return packageManager.queryIntentActivities(intent, (int) flags);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "queryIntentActivitiesCompat");
            return Collections.emptyList();
        }
    }

    /**
     * 解析匹配 Intent 的 Activity（兼容封装）
     * <pre>
     *     封装 {@link PackageManager#resolveActivity(Intent, int)} 与 API 33+ 的 ResolveInfoFlags
     * </pre>
     * @param packageManager {@link PackageManager}
     * @param intent         {@link Intent}
     * @param flags          与 {@link #queryIntentActivitiesCompat(PackageManager, Intent, long)} 相同
     * @return 解析结果，无匹配或异常时为 {@code null}
     */
    public static ResolveInfo resolveActivityCompat(
            final PackageManager packageManager,
            final Intent intent,
            final long flags
    ) {
        if (packageManager == null || intent == null) {
            return null;
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                return packageManager.resolveActivity(
                        intent, PackageManager.ResolveInfoFlags.of(flags));
            }
            return packageManager.resolveActivity(intent, (int) flags);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "resolveActivityCompat");
            return null;
        }
    }
}