package dev.utils.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.io.File;
import java.security.MessageDigest;
import java.util.List;

import dev.DevUtils;
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
     * @return
     */
    public static WindowManager getWindowManager() {
        try {
            return (WindowManager) DevUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWindowManager");
        }
        return null;
    }

    // ================
    // = 快捷获取方法 =
    // ================

    /**
     * 获取 View
     * @param resource
     * @return
     */
    public static View getView(@LayoutRes final int resource) {
        return getView(resource, null);
    }

    /**
     * 获取View
     * @param resource
     * @param root
     * @return
     */
    public static View getView(@LayoutRes final int resource, final ViewGroup root) {
        try {
            return ((LayoutInflater) DevUtils.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(resource, root);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getView");
        }
        return null;
    }

    /**
     * 获取 Resources
     * @return
     */
    public static Resources getResources() {
        try {
            return DevUtils.getContext().getResources();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getResources");
        }
        return null;
    }

    // =

    /**
     * 获取 String
     * @return
     */
    public static String getString(@StringRes final int id) {
        try {
            return DevUtils.getContext().getResources().getString(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getString");
        }
        return null;
    }

    /**
     * 获取 String
     * @return
     */
    public static String getString(@StringRes final int id, final Object... formatArgs) {
        try {
            return DevUtils.getContext().getResources().getString(id, formatArgs);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getString");
        }
        return null;
    }

    /**
     * 获取 Color
     * @param colorId 颜色id
     * @return 颜色
     */
    public static int getColor(final int colorId) {
        try {
            return DevUtils.getContext().getResources().getColor(colorId);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getColor");
        }
        return -1;
    }

    /**
     * 获取 Drawable
     * @param drawableId Drawable的id
     * @return
     */
    public static Drawable getDrawable(final int drawableId) {
        try {
            return DevUtils.getContext().getResources().getDrawable(drawableId);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDrawable");
        }
        return null;
    }

    /**
     * 获取 Dimen 资源
     * @param id
     * @return
     */
    public static float getDimension(final int id) {
        try {
            return DevUtils.getContext().getResources().getDimension(id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDimension");
        }
        return 0f;
    }

    // =

    /**
     * 获取 Resources.Theme
     * @return
     */
    public static Resources.Theme getTheme() {
        try {
            return DevUtils.getContext().getTheme();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getTheme");
        }
        return null;
    }

    /**
     * 获取 AssetManager
     * @return
     */
    public static AssetManager getAssets() {
        try {
            return DevUtils.getContext().getAssets();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAssets");
        }
        return null;
    }

    // =

    /**
     * 获取 ColorStateList
     * @return
     */
    public static ColorStateList getColorStateList(final int id) {
        try {
            return ContextCompat.getColorStateList(DevUtils.getContext(), id);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getColorStateList");
        }
        return null;
    }

    /**
     * 获取 SystemService
     * @return
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
     * @return
     */
    public static PackageManager getPackageManager() {
        try {
            return DevUtils.getContext().getPackageManager();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getPackageManager");
        }
        return null;
    }

    /**
     * 获取 Configuration
     * @return
     */
    public static Configuration getConfiguration() {
        try {
            return DevUtils.getContext().getResources().getConfiguration();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getConfiguration");
        }
        return null;
    }

    /**
     * 获取 DisplayMetrics
     * @return
     */
    public static DisplayMetrics getDisplayMetrics() {
        try {
            return DevUtils.getContext().getResources().getDisplayMetrics();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDisplayMetrics");
        }
        return null;
    }

    /**
     * 获取 ContentResolver
     * @return
     */
    public static ContentResolver getContentResolver() {
        try {
            return DevUtils.getContext().getContentResolver();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getContentResolver");
        }
        return null;
    }

    /**
     * 获取 App 的图标
     * @return
     */
    public static Drawable getAppIcon() {
        return getAppIcon(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 App 的图标
     * @param packageName
     * @return
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
     * 获取 App 包名
     * @return
     */
    public static String getAppPackageName() {
        return DevUtils.getContext().getPackageName();
    }

    /**
     * 获取 App 名
     * @return
     */
    public static String getAppName() {
        return getAppName(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 App 名
     * @param packageName
     * @return
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
     * 获取 App 版本名 - 对外显示
     * @return
     */
    public static String getAppVersionName() {
        return getAppVersionName(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 App 版本名 - 对外显示
     * @param packageName
     * @return
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
     * 获取 App 版本号 - 内部判断
     * @return
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 App 版本号 - 内部判断
     * @param packageName
     * @return
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

    // =

    /**
     * 安装 App(支持 8.0)的意图
     * @param filePath  文件路径
     * @param authority 7.0 及以上安装需要传入清单文件中的<provider>}的 authorities 属性
     * @return 是否可以跳转
     */
    public static boolean installApp(final String filePath, final String authority) {
        return installApp(getFileByPath(filePath), authority);
    }

    /**
     * 安装 App(支持 8.0)的意图
     * @param file
     * @param authority 7.0 及以上安装需要传入清单文件中的<provider>}的 authorities 属性
     * @return 是否可以跳转
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
     * 安装 App(支持 8.0)的意图 - 回传
     * @param activity
     * @param filePath
     * @param authority
     * @param requestCode
     * @return
     */
    public static boolean installApp(final Activity activity, final String filePath, final String authority, final int requestCode) {
        return installApp(activity, getFileByPath(filePath), authority, requestCode);
    }

    /**
     * 安装 App(支持 8.0)的意图 - 回传
     * @param activity
     * @param file
     * @param authority
     * @param requestCode
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
     * 静默安装 App
     * @param filePath
     * @return
     */
    public static boolean installAppSilent(final String filePath) {
        return installAppSilent(filePath, null);
    }

    /**
     * 静默安装 App
     * @param file
     * @return
     */
    public static boolean installAppSilent(final File file) {
        return installAppSilent(file, null);
    }

    /**
     * 静默安装 App
     * @param filePath
     * @param params
     * @return
     */
    public static boolean installAppSilent(final String filePath, final String params) {
        return installAppSilent(getFileByPath(filePath), params, isDeviceRooted());
    }

    /**
     * 静默安装 App
     * @param file
     * @param params
     * @return
     */
    public static boolean installAppSilent(final File file, final String params) {
        return installAppSilent(file, params, isDeviceRooted());
    }

    /**
     * 静默安装 App
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
     * 卸载 App
     * @param packageName
     * @param
     * @return 卸载 App 的意图
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
     * 卸载 App
     * @param activity
     * @param packageName
     * @param requestCode
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
     * 静默卸载 App
     * @param packageName
     * @return
     */
    public static boolean uninstallAppSilent(final String packageName) {
        return uninstallAppSilent(packageName, false, isDeviceRooted());
    }

    /**
     * 静默卸载 App
     * @param packageName
     * @param isKeepData
     * @return
     */
    public static boolean uninstallAppSilent(final String packageName, final boolean isKeepData) {
        return uninstallAppSilent(packageName, isKeepData, isDeviceRooted());
    }

    /**
     * 静默卸载 App
     * @param packageName
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
     * 判断是否安装了应用
     * @param action
     * @param category
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppInstalled(@NonNull final String action, @NonNull final String category) {
        try {
            Intent intent = new Intent(action);
            intent.addCategory(category);
            PackageManager pm = DevUtils.getContext().getPackageManager();
            ResolveInfo info = pm.resolveActivity(intent, 0);
            return info != null;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isAppInstalled");
            return false;
        }
    }

    /**
     * 判断是否安装了应用
     * @param packageName
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppInstalled(@NonNull final String packageName) {
        return !isSpace(packageName) && IntentUtils.getLaunchAppIntent(packageName) != null;
    }

    /**
     * 判断是否安装指定包名的 App
     * @param packageName 包路径
     * @return
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
     * 判断是否存在 root 权限
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppRoot() {
        ShellUtils.CommandResult result = ShellUtils.execCmd("echo root", true);
        if (result.result == 0) return true; // result.errorMsg => 失败错误消息
        return false;
    }

    /**
     * 判断是否 App 是否debug模式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppDebug() {
        return isAppDebug(DevUtils.getContext().getPackageName());
    }

    /**
     * 判断是否 App 是否debug模式
     * @param packageName
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
     * 判断 App 是否系统app
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppSystem() {
        return isAppSystem(DevUtils.getContext().getPackageName());
    }

    /**
     * 判断 App 是否系统app
     * @param packageName
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
    public static boolean isAppForeground() {
        try {
            ActivityManager manager = (ActivityManager) DevUtils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> info = manager.getRunningAppProcesses();
            if (info == null || info.size() == 0) return false;
            for (ActivityManager.RunningAppProcessInfo aInfo : info) {
                if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return aInfo.processName.equals(DevUtils.getContext().getPackageName());
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isAppForeground");
        }
        return false;
    }

    /**
     * 判断 App 是否在前台
     * <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />
     * @param packageName
     * @return
     */
    @RequiresPermission(Manifest.permission.PACKAGE_USAGE_STATS)
    public static boolean isAppForeground(@NonNull final String packageName) {
        return !isSpace(packageName) && packageName.equals(ProcessUtils.getForegroundProcessName());
    }

    /**
     * 打开 App
     * @param packageName
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
     * @param packageName
     * @param requestCode
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
     * @return
     */
    public static boolean launchAppDetailsSettings() {
        return launchAppDetailsSettings(DevUtils.getContext().getPackageName());
    }

    /**
     * 跳转到 专门的 App 设置详情页面
     * @param packageName
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
     * @param marketPkg
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

    /**
     * 获取 App 路径 /data/data/包名/.apk
     * @return
     */
    public static String getAppPath() {
        return getAppPath(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 App 路径 /data/data/包名/.apk
     * @param packageName
     * @return
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
     * 获取 App 签名
     * @return
     */
    public static Signature[] getAppSignature() {
        return getAppSignature(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 App 签名
     * @param packageName
     * @return
     */
    public static Signature[] getAppSignature(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = DevUtils.getContext().getPackageManager();
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppSignature");
            return null;
        }
    }

    /**
     * 获取 App sha1值
     * @return
     */
    public static String getAppSignatureSHA1() {
        return getAppSignatureSHA1(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取 App sha1值
     * @param packageName
     * @return
     */
    public static String getAppSignatureSHA1(@NonNull final String packageName) {
        return getAppSignatureHash(packageName, "SHA1");
    }

    /**
     * 获取应用签名的的 SHA256 值
     * @return
     */
    public static String getAppSignatureSHA256() {
        return getAppSignatureSHA256(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取应用签名的的 SHA256 值
     * @param packageName
     * @return
     */
    public static String getAppSignatureSHA256(final String packageName) {
        return getAppSignatureHash(packageName, "SHA256");
    }

    /**
     * 获取应用签名 MD5 值
     * @return
     */
    public static String getAppSignatureMD5() {
        return getAppSignatureMD5(DevUtils.getContext().getPackageName());
    }

    /**
     * 获取应用签名 MD5 值
     * @param packageName
     * @return
     */
    public static String getAppSignatureMD5(final String packageName) {
        return getAppSignatureHash(packageName, "MD5");
    }

    /**
     * 获取应用签名 Hash 值
     * @param packageName
     * @param algorithm
     * @return
     */
    private static String getAppSignatureHash(final String packageName, final String algorithm) {
        if (isSpace(packageName)) return "";
        Signature[] signature = getAppSignature(packageName);
        if (signature == null || signature.length == 0) return "";
        return toHexString(hashTemplate(signature[0].toByteArray(), algorithm), HEX_DIGITS).replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
    }

    // =

    /**
     * 检查是否存在某个文件
     * @param file 文件路径
     * @return 是否存在文件
     */
    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * 获取文件
     * @param filePath
     * @return
     */
    private static File getFileByPath(final String filePath) {
        return filePath != null ? new File(filePath) : null;
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
     * 判断设备是否 root
     * @return
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

    // 用于建立十六进制字符的输出的小写字符数组
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * hash 加密模版方法
     * @param data
     * @param algorithm
     * @return
     */
    private static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (data == null || data.length == 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "hashTemplate");
            return null;
        }
    }

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data
     * @param hexDigits
     * @return
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

    // ============
    // = 其他功能 =
    // ============

    /**
     * 启动本地应用打开 PDF
     * @param filePath 文件路径
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
     */
    public static boolean openOfficeByWPS(final String filePath) {
        try {
            // 检查是否安装WPS
            String wpsPackageEng = "cn.wps.moffice_eng";// 普通版与英文版一样
            // String wpsActivity = "cn.wps.moffice.documentmanager.PreStartActivity";
            String wpsActivity2 = "cn.wps.moffice.documentmanager.PreStartActivity2";// 默认第三方程序启动

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
}
