package dev.utils.app.info;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.common.FileUtils;

/**
 * detail: app 信息实体类
 * Created by Ttt
 */
public class AppInfoBean {

    // https://my.oschina.net/orgsky/blog/368768

    // 日志Tag
    private static final String TAG = AppInfoBean.class.getSimpleName();
    // app 包名
    private String appPackName;
    // app 名
    private String appName;
    // app 图标
    private transient Drawable appIcon;
    // App 类型
    private AppType appType;
    // 获取版本号
    private int versionCode;
    // 获取版本名
    private String versionName;
    // app 首次安装时间
    private long firstInstallTime;
    // 获取最后一次更新时间
    private long lastUpdateTime;
    // 获取 app 地址
    private String sourceDir;
    // APK 大小
    private long apkSize;
    // 申请的权限
    private String [] apkPermissionsArys;

    /**
     * 通过 apk路径 初始化 App 信息实体类
     * @param apkUri apk路径
     */
    public static AppInfoBean obtainUri(String apkUri){
        try {
            // https://blog.csdn.net/sljjyy/article/details/17370665
            PackageManager pManager = DevUtils.getApplication().getPackageManager();
            PackageInfo pInfo = pManager.getPackageArchiveInfo(apkUri, PackageManager.GET_ACTIVITIES);
            // = 设置 apk 位置信息 =
            ApplicationInfo appInfo = pInfo.applicationInfo;
                /* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */
            appInfo.sourceDir = apkUri;
            appInfo.publicSourceDir = apkUri;
            return new AppInfoBean(pInfo, pManager);
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "obtainUri");
        }
        return null;
    }

    /**
     * 通过包名 初始化 App 信息实体类
     * @param pckName 包名
     */
    public static AppInfoBean obtainPck(String pckName){
        try {
            // https://blog.csdn.net/sljjyy/article/details/17370665
            PackageManager pManager = DevUtils.getApplication().getPackageManager();
            // 获取对应的PackageInfo(原始的PackageInfo 获取 signatures 等于null,需要这样获取)
            PackageInfo pInfo = pManager.getPackageInfo(pckName, PackageManager.GET_SIGNATURES); // 64
            // 返回app信息
            return new AppInfoBean(pInfo, pManager);
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "obtainPck");
        }
        return null;
    }

    /**
     * 初始化当前 App 信息实体类
     */
    public static AppInfoBean obtain(){
        try {
            // https://blog.csdn.net/sljjyy/article/details/17370665
            PackageManager pManager = DevUtils.getApplication().getPackageManager();
            // 获取对应的PackageInfo(原始的PackageInfo 获取 signatures 等于null,需要这样获取)
            PackageInfo pInfo = pManager.getPackageInfo(DevUtils.getContext().getPackageName(), PackageManager.GET_SIGNATURES); // 64
            // 返回app信息
            return new AppInfoBean(pInfo, pManager);
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "obtain");
        }
        return null;
    }

    /**
     * 初始化 App 信息实体类
     * @param pInfo
     * @param pManager
     */
    public AppInfoBean(PackageInfo pInfo, PackageManager pManager){
        // app 包名
        appPackName = pInfo.applicationInfo.packageName;
        // app 名
        appName = pManager.getApplicationLabel(pInfo.applicationInfo).toString();
        // app 图标
        appIcon = pManager.getApplicationIcon(pInfo.applicationInfo);
        // 获取App 类型
        appType = AppInfoBean.getAppType(pInfo);
        // 获取版本号
        versionCode = pInfo.versionCode;
        // 获取版本名
        versionName = pInfo.versionName;
        // app 首次安装时间
        firstInstallTime = pInfo.firstInstallTime;
        // 获取最后一次更新时间
        lastUpdateTime = pInfo.lastUpdateTime;
        // 获取 app 地址
        sourceDir = pInfo.applicationInfo.sourceDir;
        // 获取 APK 大小
        apkSize = FileUtils.getFileLength(sourceDir);
        try {
            // 获取权限
            apkPermissionsArys = pInfo.requestedPermissions;
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "AppInfoBean");
        }
    }

    /**
     * 获取App 包名
     * @return
     */
    public String getAppPackName() {
        return appPackName;
    }

    /**
     * 获取App 名
     * @return
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 获取App 图标
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

    // =

    /** App 类型 */
    public enum AppType {

        USER, // 用户 App

        SYSTEM, // 系统 App

        ALL, // 全部 App
    }

    /**
     * 获取App 类型
     * @param pInfo
     * @return
     */
    public static AppType getAppType(PackageInfo pInfo){
        if (!isSystemApp(pInfo) && !isSystemUpdateApp(pInfo)){
            return AppType.USER;
        }
        return AppType.SYSTEM;
    }

    /**
     * 表示系统程序
     * @param pInfo
     * @return
     */
    public static boolean isSystemApp(PackageInfo pInfo) {
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    /**
     * 表示系统程序被手动更新后，也成为第三方应用程序
     * @param pInfo
     * @return
     */
    public static boolean isSystemUpdateApp(PackageInfo pInfo) {
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);
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
     * 获取首次安装时间
     * @return
     */
    public long getFirstInstallTime() {
        return firstInstallTime;
    }

    /**
     * 获取最后更新时间
     * @return
     */
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * 获取 apk 地址
     * @return
     */
    public String getSourceDir() {
        return sourceDir;
    }

    /**
     * 获取 apk 大小
     * @return
     */
    public long getApkSize() {
        return apkSize;
    }

    /**
     * 获取 apk 注册的权限
     * @return
     */
    public String[] getApkPermissionsArys() {
        return apkPermissionsArys;
    }
}
