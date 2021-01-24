package dev.utils.app.info;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.Keep;

import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;
import dev.utils.common.FileUtils;

/**
 * detail: APP 信息实体类
 * @author Ttt
 */
public class AppInfoBean {

    // 日志 TAG
    private static final String TAG = AppInfoBean.class.getSimpleName();

    @Keep // APP 包名
    private final           String   appPackName;
    @Keep // APP 应用名
    private final           String   appName;
    @Keep // APP 图标
    private final transient Drawable appIcon;
    @Keep // APP 类型
    private final           AppType  appType;
    @Keep // APP 版本号
    private final           long     versionCode;
    @Keep // APP 版本名
    private final           String   versionName;
    @Keep // APP 首次安装时间
    private final           long     firstInstallTime;
    @Keep // APP 最后一次更新时间
    private final           long     lastUpdateTime;
    @Keep // APP 地址
    private final           String   sourceDir;
    @Keep // APK 大小
    private final           long     apkSize;

    /**
     * 获取 AppInfoBean
     * @param packageInfo {@link PackageInfo}
     * @return {@link AppInfoBean}
     */
    protected static AppInfoBean get(final PackageInfo packageInfo) {
        try {
            return new AppInfoBean(packageInfo);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "get");
        }
        return null;
    }

    /**
     * 初始化 AppInfoBean
     * @param packageInfo {@link PackageInfo}
     */
    protected AppInfoBean(final PackageInfo packageInfo) {
        this(packageInfo, AppUtils.getPackageManager());
    }

    /**
     * 初始化 AppInfoBean
     * @param packageInfo    {@link PackageInfo}
     * @param packageManager {@link PackageManager}
     */
    protected AppInfoBean(
            final PackageInfo packageInfo,
            final PackageManager packageManager
    ) {
        // APP 包名
        appPackName = packageInfo.applicationInfo.packageName;
        // APP 应用名
        appName = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
        // APP 图标
        appIcon = packageManager.getApplicationIcon(packageInfo.applicationInfo);
        // APP 类型
        appType = AppInfoBean.getAppType(packageInfo);
        // APP 版本号
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            versionCode = packageInfo.getLongVersionCode();
        } else {
            versionCode = packageInfo.versionCode;
        }
        // APP 版本名
        versionName = packageInfo.versionName;
        // APP 首次安装时间
        firstInstallTime = packageInfo.firstInstallTime;
        // APP 最后一次更新时间
        lastUpdateTime = packageInfo.lastUpdateTime;
        // APP 地址
        sourceDir = packageInfo.applicationInfo.sourceDir;
        // APK 大小
        apkSize = FileUtils.getFileLength(sourceDir);
    }

    /**
     * 获取 APP 包名
     * @return APP 包名
     */
    public String getAppPackName() {
        return appPackName;
    }

    /**
     * 获取 APP 应用名
     * @return APP 应用名
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 获取 APP 图标
     * @return APP 图标
     */
    public Drawable getAppIcon() {
        return appIcon;
    }

    /**
     * 获取 APP 类型
     * @return APP 类型
     */
    public AppType getAppType() {
        return appType;
    }

    /**
     * 获取 versionCode
     * @return versionCode
     */
    public long getVersionCode() {
        return versionCode;
    }

    /**
     * 获取 versionName
     * @return versionName
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * 获取 APP 首次安装时间
     * @return APP 首次安装时间
     */
    public long getFirstInstallTime() {
        return firstInstallTime;
    }

    /**
     * 获取 APP 最后更新时间
     * @return APP 最后更新时间
     */
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * 获取 APK 地址
     * @return APK 地址
     */
    public String getSourceDir() {
        return sourceDir;
    }

    /**
     * 获取 APK 大小
     * @return APK 大小
     */
    public long getApkSize() {
        return apkSize;
    }

    // =

    /**
     * detail: 应用类型
     * @author Ttt
     */
    public enum AppType {

        USER, // 用户 APP

        SYSTEM, // 系统 APP

        ALL // 全部 APP
    }

    /**
     * 获取 APP 类型
     * @param packageInfo {@link PackageInfo}
     * @return {@link AppType} 应用类型
     */
    public static AppType getAppType(final PackageInfo packageInfo) {
        if (!isSystemApp(packageInfo) && !isSystemUpdateApp(packageInfo)) {
            return AppType.USER;
        }
        return AppType.SYSTEM;
    }

    /**
     * 是否系统程序
     * @param packageInfo {@link PackageInfo}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSystemApp(final PackageInfo packageInfo) {
        return ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    /**
     * 是否系统程序被手动更新后, 也成为第三方应用程序
     * @param packageInfo {@link PackageInfo}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSystemUpdateApp(final PackageInfo packageInfo) {
        return ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);
    }
}