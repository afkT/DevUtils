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
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.FileProvider;
import android.view.WindowManager;

import java.io.File;
import java.security.MessageDigest;
import java.util.List;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: APP (Android) 工具类
 * @author Ttt
 * <pre>
 *     MimeType
 *     @see <a href="https://www.jianshu.com/p/f3fcf033be5c"/>
 *     <p></p>
 *     所需权限
 *     <uses-permission android:name="android.permission.INSTALL_PACKAGES" />
 * </pre>
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
        return getSystemService(Context.WINDOW_SERVICE);
    }

    /**
     * 获取 AudioManager
     * @return {@link AudioManager}
     */
    public static AudioManager getAudioManager() {
        return getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * 获取 SensorManager
     * @return {@link SensorManager}
     */
    public static SensorManager getSensorManager() {
        return getSystemService(Context.SENSOR_SERVICE);
    }

    /**
     * 获取 Vibrator
     * @return {@link Vibrator}
     */
    public static Vibrator getVibrator() {
        return getSystemService(Context.VIBRATOR_SERVICE);
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
     * 获取 APP 包名
     * @return APP 包名
     */
    public static String getPackageName() {
        try {
            return DevUtils.getContext().getPackageName();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getPackageName");
        }
        return null;
    }

    /**
     * 获取 APP 图标
     * @return {@link Drawable}
     */
    public static Drawable getAppIcon() {
        return getAppIcon(AppUtils.getPackageName());
    }

    /**
     * 获取 APP 图标
     * @param packageName 应用包名
     * @return {@link Drawable}
     */
    public static Drawable getAppIcon(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager packageManager = AppUtils.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo == null ? null : packageInfo.applicationInfo.loadIcon(packageManager);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppIcon");
            return null;
        }
    }

    /**
     * 获取 APP 应用名
     * @return APP 应用名
     */
    public static String getAppName() {
        return getAppName(AppUtils.getPackageName());
    }

    /**
     * 获取 APP 应用名
     * @param packageName 应用包名
     * @return APP 应用名
     */
    public static String getAppName(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager packageManager = AppUtils.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo == null ? null : packageInfo.applicationInfo.loadLabel(packageManager).toString();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppName");
            return null;
        }
    }

    /**
     * 获取 APP versionName
     * @return APP versionName
     */
    public static String getAppVersionName() {
        return getAppVersionName(AppUtils.getPackageName());
    }

    /**
     * 获取 APP versionName
     * @param packageName 应用包名
     * @return APP versionName
     */
    public static String getAppVersionName(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageInfo packageInfo = AppUtils.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return packageInfo == null ? null : packageInfo.versionName;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppVersionName");
            return null;
        }
    }

    /**
     * 获取 APP versionCode
     * @return APP versionCode
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(AppUtils.getPackageName());
    }

    /**
     * 获取 APP versionCode
     * @param packageName 应用包名
     * @return APP versionCode
     */
    public static int getAppVersionCode(final String packageName) {
        if (isSpace(packageName)) return -1;
        try {
            PackageInfo packageInfo = AppUtils.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return packageInfo == null ? -1 : packageInfo.versionCode;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppVersionCode");
            return -1;
        }
    }

    /**
     * 获取 APP 安装包路径 /data/data/packageName/.apk
     * @return APP 安装包路径
     */
    public static String getAppPath() {
        return getAppPath(AppUtils.getPackageName());
    }

    /**
     * 获取 APP 安装包路径 /data/data/packageName/.apk
     * @param packageName 应用包名
     * @return APP 安装包路径
     */
    public static String getAppPath(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager packageManager = AppUtils.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo == null ? null : packageInfo.applicationInfo.sourceDir;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppPath");
            return null;
        }
    }

    // =

    /**
     * 获取 APP Signature
     * @return {@link Signature} 数组
     */
    public static Signature[] getAppSignature() {
        return getAppSignature(AppUtils.getPackageName());
    }

    /**
     * 获取 APP Signature
     * @param packageName 应用包名
     * @return {@link Signature} 数组
     */
    public static Signature[] getAppSignature(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager packageManager = AppUtils.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return packageInfo == null ? null : packageInfo.signatures;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppSignature");
            return null;
        }
    }

    // =

    /**
     * 获取 APP 签名 MD5 值
     * @return APP 签名 MD5 值
     */
    public static String getAppSignatureMD5() {
        return getAppSignatureMD5(AppUtils.getPackageName());
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
     * 获取 APP 签名 SHA1 值
     * @return APP 签名 SHA1 值
     */
    public static String getAppSignatureSHA1() {
        return getAppSignatureSHA1(AppUtils.getPackageName());
    }

    /**
     * 获取 APP 签名 SHA1 值
     * @param packageName 应用包名
     * @return APP 签名 SHA1 值
     */
    public static String getAppSignatureSHA1(final String packageName) {
        return getAppSignatureHash(packageName, "SHA1");
    }

    /**
     * 获取 APP 签名 SHA256 值
     * @return APP 签名 SHA256 值
     */
    public static String getAppSignatureSHA256() {
        return getAppSignatureSHA256(AppUtils.getPackageName());
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
     * 获取应用签名 Hash 值
     * @param packageName 应用包名
     * @param algorithm   算法
     * @return 对应算法处理后的签名信息
     */
    private static String getAppSignatureHash(final String packageName, final String algorithm) {
        if (isSpace(packageName)) return null;
        try {
            Signature[] signature = getAppSignature(packageName);
            if (signature == null || signature.length == 0) return null;
            return colonSplit(toHexString(hashTemplate(signature[0].toByteArray(), algorithm), HEX_DIGITS));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppSignatureHash - packageName: " + packageName + ", algorithm: " + algorithm);
            return null;
        }
    }

    // =

    /**
     * 判断 APP 是否 debug 模式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppDebug() {
        return isAppDebug(AppUtils.getPackageName());
    }

    /**
     * 判断 APP 是否 debug 模式
     * @param packageName 应用包名
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppDebug(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            ApplicationInfo appInfo = AppUtils.getPackageManager().getApplicationInfo(packageName, 0);
            return appInfo != null && (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isAppDebug");
            return false;
        }
    }

    /**
     * 判断 APP 是否 release 模式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppRelease() {
        return isAppRelease(AppUtils.getPackageName());
    }

    /**
     * 判断 APP 是否 release 模式
     * @param packageName 应用包名
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppRelease(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            ApplicationInfo appInfo = AppUtils.getPackageManager().getApplicationInfo(packageName, 0);
            return !(appInfo != null && (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isAppRelease");
            return false;
        }
    }

    // =

    /**
     * 判断 APP 是否系统 app
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppSystem() {
        return isAppSystem(AppUtils.getPackageName());
    }

    /**
     * 判断 APP 是否系统 app
     * @param packageName 应用包名
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppSystem(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            ApplicationInfo appInfo = AppUtils.getPackageManager().getApplicationInfo(packageName, 0);
            return appInfo != null && (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isAppSystem");
            return false;
        }
    }

    /**
     * 判断 APP 是否在前台
     * @return {@code true} yes, {@code false} no
     */
    @RequiresPermission(android.Manifest.permission.PACKAGE_USAGE_STATS)
    public static boolean isAppForeground() {
        return isAppForeground(AppUtils.getPackageName());
    }

    /**
     * 判断 APP 是否在前台
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
     * 判断是否安装了 APP
     * @param action   Action
     * @param category Category
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInstalledApp(final String action, final String category) {
        try {
            Intent intent = new Intent(action);
            intent.addCategory(category);
            ResolveInfo resolveinfo = AppUtils.getPackageManager().resolveActivity(intent, 0);
            return resolveinfo != null;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isInstalledApp");
            return false;
        }
    }

    /**
     * 判断是否安装了 APP
     * @param packageName 应用包名
     * @return {@code true} yes, {@code false} no
     */
    @SuppressWarnings("unused")
    public static boolean isInstalledApp(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            ApplicationInfo appInfo = AppUtils.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (Exception e) { // 未安装, 则会抛出异常
            LogPrintUtils.eTag(TAG, e, "isInstalledApp");
            return false;
        }
    }

    /**
     * 判断是否安装了 APP
     * @param packageName 应用包名
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isInstalledApp2(final String packageName) {
        return !isSpace(packageName) && IntentUtils.getLaunchAppIntent(packageName) != null;
    }

    // =================
    // = Activity 跳转 =
    // =================

    /**
     * Activity 跳转
     * @param intent {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startActivity(final Intent intent) {
        try {
            DevUtils.getContext().startActivity(IntentUtils.getIntent(intent, true));
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startActivity");
        }
        return false;
    }

    /**
     * Activity 跳转回传
     * @param activity    {@link Activity}
     * @param intent      {@link Intent}
     * @param requestCode 请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startActivityForResult(final Activity activity, final Intent intent, final int requestCode) {
        try {
            activity.startActivityForResult(intent, requestCode);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startActivityForResult");
        }
        return false;
    }

    // ==============
    // = 安装、卸载 =
    // ==============

    /**
     * 安装 APP( 支持 8.0) 的意图
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean installApp(final String filePath) {
        return installApp(getFileByPath(filePath));
    }

    /**
     * 安装 APP( 支持 8.0) 的意图
     * @param file 文件
     * @return {@code true} success, {@code false} fail
     */
    public static boolean installApp(final File file) {
        if (!isFileExists(file)) return false;
        try {
            DevUtils.getContext().startActivity(IntentUtils.getInstallAppIntent(file, true));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "installApp");
            return false;
        }
        return true;
    }

    /**
     * 安装 APP( 支持 8.0) 的意图
     * @param activity    {@link Activity}
     * @param filePath    文件路径
     * @param requestCode 请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean installApp(final Activity activity, final String filePath, final int requestCode) {
        return installApp(activity, getFileByPath(filePath), requestCode);
    }

    /**
     * 安装 APP( 支持 8.0) 的意图
     * @param activity    {@link Activity}
     * @param file        文件
     * @param requestCode 请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean installApp(final Activity activity, final File file, final int requestCode) {
        if (!isFileExists(file)) return false;
        try {
            activity.startActivityForResult(IntentUtils.getInstallAppIntent(file), requestCode);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "installApp");
            return false;
        }
        return true;
    }

    // =

    /**
     * 静默安装应用
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean installAppSilent(final String filePath) {
        return installAppSilent(filePath, null);
    }

    /**
     * 静默安装应用
     * @param file 文件
     * @return {@code true} success, {@code false} fail
     */
    public static boolean installAppSilent(final File file) {
        return installAppSilent(file, null);
    }

    /**
     * 静默安装应用
     * @param filePath 文件路径
     * @param params   安装参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean installAppSilent(final String filePath, final String params) {
        return installAppSilent(getFileByPath(filePath), params, isDeviceRooted());
    }

    /**
     * 静默安装应用
     * @param file   文件
     * @param params 安装参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean installAppSilent(final File file, final String params) {
        return installAppSilent(file, params, isDeviceRooted());
    }

    /**
     * 静默安装应用
     * @param file     文件
     * @param params   安装参数
     * @param isRooted 是否 root
     * @return {@code true} success, {@code false} fail
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
     * @param activity    {@link Activity}
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
     * @return {@code true} success, {@code false} fail
     */
    public static boolean uninstallAppSilent(final String packageName) {
        return uninstallAppSilent(packageName, false, isDeviceRooted());
    }

    /**
     * 静默卸载应用
     * @param packageName 应用包名
     * @param isKeepData  true 表示卸载应用但保留数据和缓存目录
     * @return {@code true} success, {@code false} fail
     */
    public static boolean uninstallAppSilent(final String packageName, final boolean isKeepData) {
        return uninstallAppSilent(packageName, isKeepData, isDeviceRooted());
    }

    /**
     * 静默卸载应用
     * @param packageName 应用包名
     * @param isKeepData  true 表示卸载应用但保留数据和缓存目录
     * @param isRooted    是否 root
     * @return {@code true} success, {@code false} fail
     */
    public static boolean uninstallAppSilent(final String packageName, final boolean isKeepData, final boolean isRooted) {
        if (isSpace(packageName)) return false;
        String command = "LD_LIBRARY_PATH=/vendor/lib*:/system/lib* pm uninstall " + (isKeepData ? "-k " : "") + packageName;
        ShellUtils.CommandResult result = ShellUtils.execCmd(command, isRooted);
        return result.isSuccess4("success");
    }

    // ============
    // = 操作相关 =
    // ============

    /**
     * 打开 APP
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
     * 打开 APP
     * @param activity    {@link Activity}
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

    // =

    /**
     * 跳转到 APP 设置详情页面
     * @return {@code true} success, {@code false} fail
     */
    public static boolean launchAppDetailsSettings() {
        return launchAppDetailsSettings(AppUtils.getPackageName());
    }

    /**
     * 跳转到 APP 设置详情页面
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
     * 跳转到 APP 应用商城详情页面
     * @param marketPkg 应用商店包名, 如果为 ""  则由系统弹出应用商店列表供用户选择, 否则调转到目标市场的应用详情界面, 某些应用商店可能会失败
     * @return {@code true} success, {@code false} fail
     */
    public static boolean launchAppDetails(final String marketPkg) {
        return launchAppDetails(AppUtils.getPackageName(), marketPkg);
    }

    /**
     * 跳转到 APP 应用商城详情页面
     * @param packageName 应用包名
     * @param marketPkg   应用商店包名, 如果为 ""  则由系统弹出应用商店列表供用户选择, 否则调转到目标市场的应用详情界面, 某些应用商店可能会失败
     * @return {@code true} success, {@code false} fail
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
     * 打开文件
     * @param filePath 文件路径
     * @param dataType 数据类型
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openFile(final String filePath, final String dataType) {
        return openFile(getFileByPath(filePath), dataType);
    }

    /**
     * 打开文件
     * @param file     文件
     * @param dataType 数据类型
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openFile(final File file, final String dataType) {
        if (!isFileExists(file)) return false;
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // 临时授权 ( 必须 )
            intent.setDataAndType(getUriForFile(file, DevUtils.getAuthority()), dataType);
            DevUtils.getContext().startActivity(intent);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openFile");
        }
        return false;
    }

    // =

    /**
     * 打开文件 - 指定应用
     * @param filePath    文件路径
     * @param packageName 应用包名
     * @param className   Activity.class.getCanonicalName()
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openFileByApp(final String filePath, final String packageName, final String className) {
        return openFileByApp(getFileByPath(filePath), packageName, className);
    }

    /**
     * 打开文件 - 指定应用
     * @param file        文件
     * @param packageName 应用包名
     * @param className   Activity.class.getCanonicalName()
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openFileByApp(final File file, final String packageName, final String className) {
        if (!isFileExists(file)) return false;
        try {
            Intent intent = new Intent();
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.fromFile(file));
            intent.setClassName(packageName, className);
            DevUtils.getContext().startActivity(intent);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openFile");
        }
        return false;
    }

    // =

    /**
     * 打开 PDF 文件
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openPDFFile(final String filePath) {
        return openPDFFile(getFileByPath(filePath));
    }

    /**
     * 打开 PDF 文件
     * @param file 文件
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openPDFFile(final File file) {
        return openFile(file, "application/pdf");
    }

    // =

    /**
     * 打开 Word 文件
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openWordFile(final String filePath) {
        return openWordFile(getFileByPath(filePath));
    }

    /**
     * 打开 Word 文件
     * @param file 文件
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openWordFile(final File file) {
        return openFile(file, "application/msword");
    }

    // =

    /**
     * 调用 WPS 打开 office 文档
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openOfficeByWPS(final String filePath) {
        return openOfficeByWPS(getFileByPath(filePath));
    }

    /**
     * 调用 WPS 打开 office 文档
     * @param file 文件
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openOfficeByWPS(final File file) {
        String wpsPackage = "cn.wps.moffice_eng"; // 普通版与英文版一样
        // String wpsActivity = "cn.wps.moffice.documentmanager.PreStartActivity";
        String wpsActivity2 = "cn.wps.moffice.documentmanager.PreStartActivity2";
        // 打开文件
        return openFileByApp(file, wpsPackage, wpsActivity2);
    }

    // ============
    // = 系统页面 =
    // ============

    /**
     * 跳转到系统设置页面
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startSysSetting() {
        try {
            DevUtils.getContext().startActivity(IntentUtils.getIntent(new Intent(Settings.ACTION_SETTINGS), true));
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startSysSetting");
        }
        return false;
    }

    /**
     * 跳转到系统设置页面
     * @param activity    {@link Activity}
     * @param requestCode 请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startSysSetting(final Activity activity, final int requestCode) {
        try {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            activity.startActivityForResult(intent, requestCode);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startSysSetting");
        }
        return false;
    }

    /**
     * 打开网络设置界面
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openWirelessSettings() {
        try {
            DevUtils.getContext().startActivity(IntentUtils.getIntent(new Intent(Settings.ACTION_WIRELESS_SETTINGS), true));
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openWirelessSettings");
        }
        return false;
    }

    /**
     * 打开网络设置界面
     * @param activity    {@link Activity}
     * @param requestCode 请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openWirelessSettings(final Activity activity, final int requestCode) {
        try {
            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            activity.startActivityForResult(intent, requestCode);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openWirelessSettings");
        }
        return false;
    }

    /**
     * 打开 GPS 设置界面
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openGpsSettings() {
        try {
            DevUtils.getContext().startActivity(IntentUtils.getIntent(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), true));
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openGpsSettings");
        }
        return false;
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

    /**
     * 冒号分割处理
     * @param str 待处理字符串
     * @return 冒号分割后的字符串
     */
    private static String colonSplit(final String str) {
        if (!isEmpty(str)) {
            return str.replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
        }
        return str;
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
            LogPrintUtils.eTag(TAG, e, "hashTemplate");
            return null;
        }
    }

    // ============
    // = UriUtils =
    // ============

    /**
     * 获取文件 Uri
     * @param file      文件
     * @param authority android:authorities
     * @return 指定文件 {@link Uri}
     */
    private static Uri getUriForFile(final File file, final String authority) {
        if (file == null || authority == null) return null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return FileProvider.getUriForFile(DevUtils.getContext(), authority, file);
            } else {
                return Uri.fromFile(file);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getUriForFile");
            return null;
        }
    }
}