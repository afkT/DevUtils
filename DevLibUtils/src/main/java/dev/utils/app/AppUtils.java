package dev.utils.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresPermission;
import android.view.WindowManager;

import java.io.File;
import java.security.MessageDigest;
import java.util.List;

import dev.DevUtils;
import dev.utils.JCLogUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: App (Android) 工具类
 * @author Ttt
 */
public final class AppUtils {

    private AppUtils() {
    }

    // 日志 TAG
    private static final String TAG = AppUtils.class.getSimpleName();

    /**
     * 获取 WindowManager
     * @return {@link WindowManager}
     */
    public static WindowManager getWindowManager() {
        try {
            return (WindowManager) DevUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWindowManager");
        }
        return null;
    }

    /**
     * 获取 SystemService
     * @param name 服务名
     * @param <T>  泛型
     * @return SystemService Object
     */
    public static <T> T getSystemService(final String name) {
        if (isSpace(name)) return null;
        try {
            return (T) DevUtils.getContext().getSystemService(name);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSystemService");
        }
        return null;
    }

    /**
     * 获取 PackageManager
     * @return {@link PackageManager}
     */
    public static PackageManager getPackageManager() {
        try {
            return DevUtils.getContext().getPackageManager();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getPackageManager");
        }
        return null;
    }

    // ============
    // = APP 相关 =
    // ============

    /**
     * 获取 App 包名
     * @return App 包名
     */
    public static String getAppPackageName() {
        return DevUtils.getContext().getPackageName();
    }

    /**
     * 获取 App 图标
     * @return {@link Drawable}
     */
    public static Drawable getAppIcon() {
        return getAppIcon(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 App 图标
     * @param packageName 应用包名
     * @return {@link Drawable}
     */
    public static Drawable getAppIcon(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = DevUtils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppIcon");
            return null;
        }
    }

    /**
     * 获取 App 应用名
     * @return App 应用名
     */
    public static String getAppName() {
        return getAppName(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 App 应用名
     * @param packageName 应用包名
     * @return App 应用名
     */
    public static String getAppName(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = DevUtils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppName");
            return null;
        }
    }

    /**
     * 获取 App versionName
     * @return App versionName
     */
    public static String getAppVersionName() {
        return getAppVersionName(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 App versionName
     * @param packageName 应用包名
     * @return App versionName
     */
    public static String getAppVersionName(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageInfo pi = DevUtils.getContext().getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.versionName;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppVersionName");
            return null;
        }
    }

    /**
     * 获取 App versionCode
     * @return App versionCode
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 App versionCode
     * @param packageName 应用包名
     * @return App versionCode
     */
    public static int getAppVersionCode(final String packageName) {
        if (isSpace(packageName)) return -1;
        try {
            PackageInfo pi = DevUtils.getContext().getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? -1 : pi.versionCode;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppVersionCode");
            return -1;
        }
    }

    /**
     * 获取 App 安装包路径 /data/data/packageName/.apk
     * @return App 安装包路径
     */
    public static String getAppPath() {
        return getAppPath(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 App 安装包路径 /data/data/packageName/.apk
     * @param packageName 应用包名
     * @return App 安装包路径
     */
    public static String getAppPath(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = DevUtils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.sourceDir;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppPath");
            return null;
        }
    }

    // =

    /**
     * 获取 App Signature
     * @return {@link Signature} 数组
     */
    public static Signature[] getAppSignature() {
        return getAppSignature(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 App Signature
     * @param packageName 应用包名
     * @return {@link Signature} 数组
     */
    public static Signature[] getAppSignature(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = DevUtils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppSignature");
            return null;
        }
    }

    // =

    /**
     * 获取 App 签名 SHA1 值
     * @return App 签名 SHA1 值
     */
    public static String getAppSignatureSHA1() {
        return getAppSignatureSHA1(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 App 签名 SHA1 值
     * @param packageName 应用包名
     * @return App 签名 SHA1 值
     */
    public static String getAppSignatureSHA1(final String packageName) {
        return getAppSignatureHash(packageName, "SHA1");
    }

    /**
     * 获取 APP 签名 SHA256 值
     * @return APP 签名 SHA256 值
     */
    public static String getAppSignatureSHA256() {
        return getAppSignatureSHA256(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 APP 签名 SHA256 值
     * @param packageName 应用包名
     * @return APP 签名 SHA256 值
     */
    public static String getAppSignatureSHA256(final String packageName) {
        return getAppSignatureHash(packageName, "SHA256");
    }

    /**
     * 获取 APP 签名 MD5 值
     * @return APP 签名 MD5 值
     */
    public static String getAppSignatureMD5() {
        return getAppSignatureMD5(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 APP 签名 MD5 值
     * @param packageName 应用包名
     * @return APP 签名 MD5 值
     */
    public static String getAppSignatureMD5(final String packageName) {
        return getAppSignatureHash(packageName, "MD5");
    }

    /**
     * 获取应用签名 Hash 值
     * @param packageName 应用包名
     * @param algorithm   算法
     * @return
     */
    private static String getAppSignatureHash(final String packageName, final String algorithm) {
        if (isSpace(packageName)) return null;
        try {
            Signature[] signature = getAppSignature(packageName);
            if (signature == null || signature.length == 0) return null;
            return toHexString(hashTemplate(signature[0].toByteArray(), algorithm), HEX_DIGITS)
                    .replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppSignatureHash - packageName: " + packageName + ", algorithm: " + algorithm);
            return null;
        }
    }

    // =

    /**
     * 判断 App 是否 debug 模式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppDebug() {
        return isAppDebug(DevUtils.getContext().getPackageName());
    }

    /**
     * 判断 App 是否 debug 模式
     * @param packageName 应用包名
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppDebug(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            ApplicationInfo ai = DevUtils.getContext().getPackageManager().getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isAppDebug");
            return false;
        }
    }

    /**
     * 判断 App 是否系统 app
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppSystem() {
        return isAppSystem(DevUtils.getContext().getPackageName());
    }

    /**
     * 判断 App 是否系统 app
     * @param packageName 应用包名
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppSystem(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            ApplicationInfo ai = DevUtils.getContext().getPackageManager().getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isAppSystem");
            return false;
        }
    }

    /**
     * 判断 App 是否在前台
     * @return {@code true} yes, {@code false} no
     */
    @RequiresPermission(android.Manifest.permission.PACKAGE_USAGE_STATS)
    public static boolean isAppForeground() {
        return isAppForeground(DevUtils.getContext().getPackageName());
    }

    /**
     * 判断 App 是否在前台
     * @param packageName 应用包名
     * @return {@code true} yes, {@code false} no
     */
    @RequiresPermission(android.Manifest.permission.PACKAGE_USAGE_STATS)
    public static boolean isAppForeground(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            ActivityManager activityManager = (ActivityManager) DevUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager == null) return false;
            List<ActivityManager.RunningAppProcessInfo> lists = activityManager.getRunningAppProcesses();
            if (lists != null && lists.size() > 0) {
                for (ActivityManager.RunningAppProcessInfo appProcess : lists) {
                    if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return appProcess.processName.equals(packageName);
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isAppForeground");
        }
        return false;
    }

    // =

    /**
     * 判断是否安装了 App
     * @param action   Action
     * @param category Category
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInstalledApp(final String action, final String category) {
        try {
            Intent intent = new Intent(action);
            intent.addCategory(category);
            PackageManager pm = DevUtils.getContext().getPackageManager();
            ResolveInfo info = pm.resolveActivity(intent, 0);
            return info != null;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isInstalledApp");
            return false;
        }
    }

    /**
     * 判断是否安装了 App
     * @param packageName 应用包名
     * @return {@code true} yes, {@code false} no
     */
    @SuppressWarnings("unused")
    public static boolean isInstalledApp(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            ApplicationInfo info = DevUtils.getContext().getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isInstalledApp");
            return false;
        }
    }

    /**
     * 判断是否安装了 App
     * @param packageName 应用包名
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInstalledApp2(final String packageName) {
        return !isSpace(packageName) && IntentUtils.getLaunchAppIntent(packageName) != null;
    }

    // ============
    // = 操作相关 =
    // ============

    /**
     * 安装 App( 支持 8.0) 的意图
     * @param filePath  文件路径
     * @param authority 7.0 及以上安装需要传入清单文件中的 <provider> 的 authorities 属性
     * @return {@code true} yes, {@code false} no
     */
    public static boolean installApp(final String filePath, final String authority) {
        return installApp(getFileByPath(filePath), authority);
    }

    /**
     * 安装 App( 支持 8.0) 的意图
     * @param file      文件
     * @param authority 7.0 及以上安装需要传入清单文件中的 <provider> 的 authorities 属性
     * @return {@code true} yes, {@code false} no
     */
    public static boolean installApp(final File file, final String authority) {
        if (!isFileExists(file)) return false;
        try {
            DevUtils.getContext().startActivity(IntentUtils.getInstallAppIntent(file, authority, true));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "installApp");
            return false;
        }
        return true;
    }

    /**
     * 安装 App( 支持 8.0) 的意图 - 回传
     * @param activity    {@link Activity}
     * @param filePath    文件路径
     * @param authority   7.0 及以上安装需要传入清单文件中的 <provider> 的 authorities 属性
     * @param requestCode 请求 code
     * @return {@code true} yes, {@code false} no
     */
    public static boolean installApp(final Activity activity, final String filePath, final String authority, final int requestCode) {
        return installApp(activity, getFileByPath(filePath), authority, requestCode);
    }

    /**
     * 安装 App( 支持 8.0) 的意图 - 回传
     * @param activity    {@link Activity}
     * @param file        文件
     * @param authority   7.0 及以上安装需要传入清单文件中的 <provider> 的 authorities 属性
     * @param requestCode 请求 code
     * @return
     */
    public static boolean installApp(final Activity activity, final File file, final String authority, final int requestCode) {
        if (!isFileExists(file)) return false;
        try {
            activity.startActivityForResult(IntentUtils.getInstallAppIntent(file, authority), requestCode);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "installApp");
            return false;
        }
        return true;
    }

    // =

    /**
     * 静默安装应用
     * @param filePath
     * @return
     */
    public static boolean installAppSilent(final String filePath) {
        return installAppSilent(filePath, null);
    }

    /**
     * 静默安装应用
     * @param file
     * @return
     */
    public static boolean installAppSilent(final File file) {
        return installAppSilent(file, null);
    }

    /**
     * 静默安装应用
     * @param filePath
     * @param params
     * @return
     */
    public static boolean installAppSilent(final String filePath, final String params) {
        return installAppSilent(getFileByPath(filePath), params, isDeviceRooted());
    }

    /**
     * 静默安装应用
     * @param file
     * @param params
     * @return
     */
    public static boolean installAppSilent(final File file, final String params) {
        return installAppSilent(file, params, isDeviceRooted());
    }

    /**
     * 静默安装应用
     * <uses-permission android:name="android.permission.INSTALL_PACKAGES" />
     * @param file
     * @param params
     * @param isRooted
     * @return
     */
    public static boolean installAppSilent(final File file, final String params, final boolean isRooted) {
        if (!isFileExists(file)) return false;
        String filePath = '"' + file.getAbsolutePath() + '"';
        String command = "LD_LIBRARY_PATH=/vendor/lib*:/system/lib* pm install " + (params == null ? "" : params + " ") + filePath;
        ShellUtils.CommandResult result = ShellUtils.execCmd(command, isRooted);
        return result.isSuccess4("success");
    }

    // =

    /**
     * 卸载应用
     * @param packageName 应用包名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean uninstallApp(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            DevUtils.getContext().startActivity(IntentUtils.getUninstallAppIntent(packageName, true));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "uninstallApp");
            return false;
        }
        return true;
    }

    /**
     * 卸载应用
     * @param activity
     * @param packageName 应用包名
     * @param requestCode 请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean uninstallApp(final Activity activity, final String packageName, final int requestCode) {
        if (isSpace(packageName)) return false;
        try {
            activity.startActivityForResult(IntentUtils.getUninstallAppIntent(packageName), requestCode);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "uninstallApp");
            return false;
        }
        return true;
    }

    /**
     * 静默卸载应用
     * @param packageName 应用包名
     * @return
     */
    public static boolean uninstallAppSilent(final String packageName) {
        return uninstallAppSilent(packageName, false, isDeviceRooted());
    }

    /**
     * 静默卸载应用
     * @param packageName 应用包名
     * @param isKeepData
     * @return
     */
    public static boolean uninstallAppSilent(final String packageName, final boolean isKeepData) {
        return uninstallAppSilent(packageName, isKeepData, isDeviceRooted());
    }

    /**
     * 静默卸载应用
     * @param packageName 应用包名
     * @param isKeepData
     * @param isRooted
     * @return
     */
    public static boolean uninstallAppSilent(final String packageName, final boolean isKeepData, final boolean isRooted) {
        if (isSpace(packageName)) return false;
        String command = "LD_LIBRARY_PATH=/vendor/lib*:/system/lib* pm uninstall " + (isKeepData ? "-k " : "") + packageName;
        ShellUtils.CommandResult result = ShellUtils.execCmd(command, isRooted);
        return result.isSuccess4("success");
    }

    // =

    /**
     * 打开 App
     * @param packageName 应用包名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean launchApp(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            DevUtils.getContext().startActivity(IntentUtils.getLaunchAppIntent(packageName, true));
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "launchApp");
        }
        return false;
    }

    /**
     * 打开 App, 并且回传
     * @param activity
     * @param packageName 应用包名
     * @param requestCode 请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean launchApp(final Activity activity, final String packageName, final int requestCode) {
        if (isSpace(packageName)) return false;
        try {
            activity.startActivityForResult(IntentUtils.getLaunchAppIntent(packageName), requestCode);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "launchApp");
        }
        return false;
    }

    /**
     * 跳转到 专门的 App 设置详情页面
     * @return {@code true} success, {@code false} fail
     */
    public static boolean launchAppDetailsSettings() {
        return launchAppDetailsSettings(DevUtils.getContext().getPackageName());
    }

    /**
     * 跳转到 专门的 App 设置详情页面
     * @param packageName 应用包名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean launchAppDetailsSettings(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            DevUtils.getContext().startActivity(IntentUtils.getLaunchAppDetailsSettingsIntent(packageName, true));
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "launchAppDetailsSettings");
        }
        return false;
    }

    /**
     * 跳转到 专门的 App 应用商城详情页面
     * @param marketPkg
     * @return
     */
    public static boolean launchAppDetails(final String marketPkg) {
        return launchAppDetails(DevUtils.getContext().getPackageName(), marketPkg);
    }

    /**
     * 跳转到 专门的 App 应用商城详情页面
     * @param packageName 应用包名
     * @param marketPkg
     * @return
     */
    public static boolean launchAppDetails(final String packageName, final String marketPkg) {
        if (isSpace(packageName)) return false;
        try {
            DevUtils.getContext().startActivity(IntentUtils.getLaunchAppDetailIntent(packageName, marketPkg, true));
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "launchAppDetails");
        }
        return false;
    }

    // ============
    // = 其他功能 =
    // ============

    /**
     * 启动本地应用打开 PDF
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openPDFFile(final String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Uri path = Uri.fromFile(file);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(path, "application/pdf");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                DevUtils.getContext().startActivity(intent);
                return true;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openPDFFile");
        }
        return false;
    }

    /**
     * 启动本地应用打开 Word
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openWordFile(final String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Uri path = Uri.fromFile(file);
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(path, "application/msword");
                DevUtils.getContext().startActivity(intent);
                return true;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openWordFile");
        }
        return false;
    }

    /**
     * 调用 WPS 打开 office 文档
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openOfficeByWPS(final String filePath) {
        try {
            // 检查是否安装 WPS
            String wpsPackageEng = "cn.wps.moffice_eng"; // 普通版与英文版一样
            // String wpsActivity = "cn.wps.moffice.documentmanager.PreStartActivity";
            String wpsActivity2 = "cn.wps.moffice.documentmanager.PreStartActivity2"; // 默认第三方程序启动

            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setClassName(wpsPackageEng, wpsActivity2);

            Uri uri = Uri.fromFile(new File(filePath));
            intent.setData(uri);
            DevUtils.getContext().startActivity(intent);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openOfficeByWPS");
        }
        return false;
    }

    /**
     * 跳转到系统设置页面
     * @param activity {@link Activity}
     */
    public static void startSysSetting(final Activity activity) {
//        // 跳转到 无线和网络 设置页面 ( 可以设置移动网络, sim 卡 1, 2 的移动网络 )
//        Intent intent =  new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
//        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        try {
            if (activity != null) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                activity.startActivity(intent);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startSysSetting");
        }
    }

    /**
     * 跳转到系统设置页面
     * @param activity
     * @param requestCode 请求 code
     */
    public static void startSysSetting(final Activity activity, final int requestCode) {
        try {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startSysSetting");
        }
    }

    /**
     * 打开网络设置界面 - 3.0 以下打开设置界面
     * @param activity
     */
    public static void openWirelessSettings(final Activity activity) {
        try {
            if (Build.VERSION.SDK_INT > 10) {
                activity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                activity.startActivity(new Intent(Settings.ACTION_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openWirelessSettings");
        }
    }

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // =============
    // = FileUtils =
    // =============

    /**
     * 检查是否存在某个文件
     * @param file 文件
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * 获取文件
     * @param filePath 文件路径
     * @return 文件 {@link File}
     */
    private static File getFileByPath(final String filePath) {
        return filePath != null ? new File(filePath) : null;
    }

    // ===============
    // = StringUtils =
    // ===============

    /**
     * 判断字符串是否为 null
     * @param str 待校验的字符串
     * @return {@code true} is null, {@code false} not null
     */
    private static boolean isEmpty(final String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     * @param str 待校验字符串
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isSpace(final String str) {
        if (str == null) return true;
        for (int i = 0, len = str.length(); i < len; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // ===============
    // = DeviceUtils =
    // ===============

    /**
     * 判断设备是否 root
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    // ================
    // = ConvertUtils =
    // ================

    // 用于建立十六进制字符的输出的小写字符数组
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data 待转换数据
     * @return 十六进制 String
     */
    private static String toHexString(final byte[] data) {
        return toHexString(data, HEX_DIGITS);
    }

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data      待转换数据
     * @param hexDigits {@link #HEX_DIGITS}
     * @return 十六进制字符串
     */
    private static String toHexString(final byte[] data, final char[] hexDigits) {
        if (data == null || hexDigits == null) return null;
        try {
            int len = data.length;
            StringBuilder builder = new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                builder.append(hexDigits[(data[i] & 0xf0) >>> 4]);
                builder.append(hexDigits[data[i] & 0x0f]);
            }
            return builder.toString();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "toHexString");
        }
        return null;
    }

    // ================
    // = EncryptUtils =
    // ================

    /**
     * Hash 加密模版方法
     * @param data      待加密数据
     * @param algorithm 算法
     * @return 指定加密算法加密后的数据
     */
    private static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (data == null || data.length == 0) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(data);
            return digest.digest();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "hashTemplate");
            return null;
        }
    }
}