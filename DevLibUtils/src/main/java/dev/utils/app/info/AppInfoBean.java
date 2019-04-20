package dev.utils.app.info;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.Keep;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.common.FileUtils;

/**
 * detail: App 信息实体类
 * @author Ttt
 */
public class AppInfoBean {

    // 日志 TAG
    private static final String TAG = AppInfoBean.class.getSimpleName();
    @Keep // App 包名
    private String appPackName;
    @Keep // App 名
    private String appName;
    @Keep // App 图标
    private transient Drawable appIcon;
    @Keep // App 类型
    private AppType appType;
    @Keep // App 版本号
    private int versionCode;
    @Keep // App 版本名
    private String versionName;
    @Keep // App 首次安装时间
    private long firstInstallTime;
    @Keep // App 最后一次更新时间
    private long lastUpdateTime;
    @Keep // App 地址
    private String sourceDir;
    @Keep // Apk 大小
    private long apkSize;

    /**
     * 获取 AppInfoBean
     * @param pInfo PackageInfo
     * @return
     */
    protected static AppInfoBean obtain(final PackageInfo pInfo) {
        try {
            return new AppInfoBean(pInfo);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "obtain");
        }
        return null;
    }

    /**
     * 初始化 App 信息实体类
     * @param pInfo
     */
    protected AppInfoBean(final PackageInfo pInfo) {
        this(pInfo, DevUtils.getContext().getPackageManager());
    }

    /**
     * 初始化 App 信息实体类
     * @param pInfo
     * @param pManager
     */
    protected AppInfoBean(final PackageInfo pInfo, final PackageManager pManager) {
        // App 包名
        appPackName = pInfo.applicationInfo.packageName;
        // App 名
        appName = pManager.getApplicationLabel(pInfo.applicationInfo).toString();
        // App 图标
        appIcon = pManager.getApplicationIcon(pInfo.applicationInfo);
        // App 类型
        appType = AppInfoBean.getAppType(pInfo);
        // App 版本号
        versionCode = pInfo.versionCode;
        // App 版本名
        versionName = pInfo.versionName;
        // App 首次安装时间
        firstInstallTime = pInfo.firstInstallTime;
        // App 最后一次更新时间
        lastUpdateTime = pInfo.lastUpdateTime;
        // App 地址
        sourceDir = pInfo.applicationInfo.sourceDir;
        // Apk 大小
        apkSize = FileUtils.getFileLength(sourceDir);
    }

    /**
     * 获取 App 包名
     * @return
     */
    public String getAppPackName() {
        return appPackName;
    }

    /**
     * 获取 App 名
     * @return
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 获取 App 图标
     * @return
     */
    public Drawable getAppIcon() {
        return appIcon;
    }

    /**
     * 获取 App 类型
     * @return
     */
    public AppType getAppType() {
        return appType;
    }

    /**
     * 获取 versionCode
     * @return
     */
    public int getVersionCode() {
        return versionCode;
    }

    /**
     * 获取 versionName
     * @return
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * 获取 App 首次安装时间
     * @return
     */
    public long getFirstInstallTime() {
        return firstInstallTime;
    }

    /**
     * 获取 App 最后更新时间
     * @return
     */
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * 获取 Apk 地址
     * @return
     */
    public String getSourceDir() {
        return sourceDir;
    }

    /**
     * 获取 Apk 大小
     * @return
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

        USER, // 用户 App

        SYSTEM, // 系统 App

        ALL, // 全部 App
    }

    /**
     * 获取 App 类型
     * @param pInfo
     * @return
     */
    public static AppType getAppType(final PackageInfo pInfo) {
        if (!isSystemApp(pInfo) && !isSystemUpdateApp(pInfo)) {
            return AppType.USER;
        }
        return AppType.SYSTEM;
    }

    /**
     * 是否系统程序
     * @param pInfo
     * @return
     */
    public static boolean isSystemApp(final PackageInfo pInfo) {
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    /**
     * 是否系统程序被手动更新后，也成为第三方应用程序
     * @param pInfo
     * @return
     */
    public static boolean isSystemUpdateApp(final PackageInfo pInfo) {
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);
    }
}
