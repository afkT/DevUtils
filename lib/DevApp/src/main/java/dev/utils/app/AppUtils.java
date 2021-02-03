package dev.utils.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.util.List;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.common.ConvertUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.StringUtils;
import dev.utils.common.encrypt.EncryptUtils;

/**
 * detail: APP ( Android ) 工具类
 * @author Ttt
 * <pre>
 *     MimeType
 *     @see <a href="https://www.jianshu.com/p/f3fcf033be5c"/>
 *     存储后缀根据 MIME_TYPE 决定, 值类型 {@link libcore.net.MimeUtils}
 *     @see <a href="https://www.androidos.net.cn/android/9.0.0_r8/xref/libcore/luni/src/main/java/libcore/net/MimeUtils.java"/>
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
     * 获取 StorageManager
     * @return {@link StorageManager}
     */
    public static StorageManager getStorageManager() {
        return getSystemService(Context.STORAGE_SERVICE);
    }

    /**
     * 获取 WifiManager
     * @return {@link WifiManager}
     */
    @SuppressLint("WifiManagerLeak")
    public static WifiManager getWifiManager() {
        return getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 获取 ConnectivityManager
     * @return {@link ConnectivityManager}
     */
    public static ConnectivityManager getConnectivityManager() {
        return getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 获取 TelephonyManager
     * @return {@link TelephonyManager}
     */
    public static TelephonyManager getTelephonyManager() {
        return getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 获取 AppOpsManager
     * @return {@link AppOpsManager}
     */
    public static AppOpsManager getAppOpsManager() {
        return getSystemService(Context.APP_OPS_SERVICE);
    }

    /**
     * 获取 NotificationManager
     * @return {@link NotificationManager}
     */
    public static NotificationManager getNotificationManager() {
        return getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 获取 ShortcutManager
     * @return {@link ShortcutManager}
     */
    public static ShortcutManager getShortcutManager() {
        return getSystemService(Context.SHORTCUT_SERVICE);
    }

    /**
     * 获取 ActivityManager
     * @return {@link ActivityManager}
     */
    public static ActivityManager getActivityManager() {
        return getSystemService(Context.ACTIVITY_SERVICE);
    }

    /**
     * 获取 PowerManager
     * @return {@link PowerManager}
     */
    public static PowerManager getPowerManager() {
        return getSystemService(Context.POWER_SERVICE);
    }

    /**
     * 获取 KeyguardManager
     * @return {@link KeyguardManager}
     */
    public static KeyguardManager getKeyguardManager() {
        return getSystemService(Context.KEYGUARD_SERVICE);
    }

    /**
     * 获取 InputMethodManager
     * @return {@link InputMethodManager}
     */
    public static InputMethodManager getInputMethodManager() {
        return getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /**
     * 获取 ClipboardManager
     * @return {@link ClipboardManager}
     */
    public static ClipboardManager getClipboardManager() {
        return getSystemService(Context.CLIPBOARD_SERVICE);
    }

    /**
     * 获取 UsageStatsManager
     * @return {@link UsageStatsManager}
     */
    public static UsageStatsManager getUsageStatsManager() {
        return getSystemService(Context.USAGE_STATS_SERVICE);
    }

    /**
     * 获取 AlarmManager
     * @return {@link AlarmManager}
     */
    public static AlarmManager getAlarmManager() {
        return getSystemService(Context.ALARM_SERVICE);
    }

    /**
     * 获取 LocationManager
     * @return {@link LocationManager}
     */
    public static LocationManager getLocationManager() {
        return getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * 获取 Vibrator
     * @return {@link Vibrator}
     */
    public static Vibrator getVibrator() {
        return getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * 获取 DevicePolicyManager
     * @return {@link DevicePolicyManager}
     */
    public static DevicePolicyManager getDevicePolicyManager() {
        return getSystemService(Context.DEVICE_POLICY_SERVICE);
    }

    /**
     * 获取 WallpaperManager
     * @return {@link WallpaperManager}
     */
    public static WallpaperManager getWallpaperManager() {
        return WallpaperManager.getInstance(DevUtils.getContext());
    }

    /**
     * 获取 SystemService
     * @param name 服务名
     * @param <T>  泛型
     * @return SystemService Object
     */
    public static <T> T getSystemService(final String name) {
        if (StringUtils.isSpace(name)) return null;
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

    /**
     * 获取 ApplicationInfo
     * @return {@link ApplicationInfo}
     */
    public static ApplicationInfo getApplicationInfo() {
        try {
            return DevUtils.getContext().getApplicationInfo();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getApplicationInfo");
        }
        return null;
    }

    /**
     * 获取 ApplicationInfo
     * @param packageName 应用包名
     * @param flags       application flags
     * @return {@link ApplicationInfo}
     */
    public static ApplicationInfo getApplicationInfo(
            final String packageName,
            final int flags
    ) {
        try {
            return DevUtils.getContext().getPackageManager().getApplicationInfo(packageName, flags);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getApplicationInfo %s", packageName);
        }
        return null;
    }

    /**
     * 获取 PackageInfo
     * @param flags package flags
     * @return {@link ApplicationInfo}
     */
    public static PackageInfo getPackageInfo(final int flags) {
        return getPackageInfo(getPackageName(), flags);
    }

    /**
     * 获取 PackageInfo
     * @param packageName 应用包名
     * @param flags       package flags
     * @return {@link ApplicationInfo}
     */
    public static PackageInfo getPackageInfo(
            final String packageName,
            final int flags
    ) {
        try {
            return DevUtils.getContext().getPackageManager().getPackageInfo(packageName, flags);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getPackageInfo %s", packageName);
        }
        return null;
    }

    /**
     * 获取 SharedPreferences
     * @param fileName 文件名
     * @return {@link SharedPreferences}
     */
    public static SharedPreferences getSharedPreferences(final String fileName) {
        return getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * 获取 SharedPreferences
     * @param fileName 文件名
     * @param mode     SharedPreferences 操作模式
     * @return {@link SharedPreferences}
     */
    public static SharedPreferences getSharedPreferences(
            final String fileName,
            final int mode
    ) {
        try {
            return DevUtils.getContext().getSharedPreferences(fileName, mode);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSharedPreferences %s", fileName);
        }
        return null;
    }

    // ===========
    // = APP 相关 =
    // ===========

    /**
     * 根据名称清除数据库
     * @param dbName 数据库名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean deleteDatabase(final String dbName) {
        try {
            return DevUtils.getContext().deleteDatabase(dbName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "deleteDatabase");
        }
        return false;
    }

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
        return getAppIcon(getPackageName());
    }

    /**
     * 获取 APP 图标
     * @param packageName 应用包名
     * @return {@link Drawable}
     */
    public static Drawable getAppIcon(final String packageName) {
        if (StringUtils.isSpace(packageName)) return null;
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo    packageInfo    = packageManager.getPackageInfo(packageName, 0);
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
        return getAppName(getPackageName());
    }

    /**
     * 获取 APP 应用名
     * @param packageName 应用包名
     * @return APP 应用名
     */
    public static String getAppName(final String packageName) {
        if (StringUtils.isSpace(packageName)) return null;
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo    packageInfo    = packageManager.getPackageInfo(packageName, 0);
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
        return getAppVersionName(getPackageName());
    }

    /**
     * 获取 APP versionName
     * @param packageName 应用包名
     * @return APP versionName
     */
    public static String getAppVersionName(final String packageName) {
        if (StringUtils.isSpace(packageName)) return null;
        try {
            PackageInfo packageInfo = getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
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
    public static long getAppVersionCode() {
        return getAppVersionCode(getPackageName());
    }

    /**
     * 获取 APP versionCode
     * @param packageName 应用包名
     * @return APP versionCode
     */
    public static long getAppVersionCode(final String packageName) {
        if (StringUtils.isSpace(packageName)) return -1;
        try {
            PackageInfo packageInfo = getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                return packageInfo.getLongVersionCode();
            } else {
                return packageInfo.versionCode;
            }
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
        return getAppPath(getPackageName());
    }

    /**
     * 获取 APP 安装包路径 /data/data/packageName/.apk
     * @param packageName 应用包名
     * @return APP 安装包路径
     */
    public static String getAppPath(final String packageName) {
        if (StringUtils.isSpace(packageName)) return null;
        try {
            PackageInfo packageInfo = getPackageInfo(packageName, 0);
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
        return getAppSignature(getPackageName());
    }

    /**
     * 获取 APP Signature
     * @param packageName 应用包名
     * @return {@link Signature} 数组
     */
    public static Signature[] getAppSignature(final String packageName) {
        if (StringUtils.isSpace(packageName)) return null;
        try {
            PackageInfo packageInfo = getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
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
        return getAppSignatureMD5(getPackageName());
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
        return getAppSignatureSHA1(getPackageName());
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
        return getAppSignatureSHA256(getPackageName());
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
    public static String getAppSignatureHash(
            final String packageName,
            final String algorithm
    ) {
        if (StringUtils.isSpace(packageName)) return null;
        try {
            Signature[] signature = getAppSignature(packageName);
            if (signature == null || signature.length == 0) return null;
            return StringUtils.colonSplit(ConvertUtils.toHexString(EncryptUtils.hashTemplate(signature[0].toByteArray(), algorithm)));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppSignatureHash - packageName: %s, algorithm: %s", packageName, algorithm);
            return null;
        }
    }

    // =

    /**
     * 判断 APP 是否 debug 模式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppDebug() {
        return isAppDebug(getPackageName());
    }

    /**
     * 判断 APP 是否 debug 模式
     * @param packageName 应用包名
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppDebug(final String packageName) {
        if (StringUtils.isSpace(packageName)) return false;
        try {
            ApplicationInfo appInfo = getApplicationInfo(packageName, 0);
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
        return isAppRelease(getPackageName());
    }

    /**
     * 判断 APP 是否 release 模式
     * @param packageName 应用包名
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppRelease(final String packageName) {
        if (StringUtils.isSpace(packageName)) return false;
        try {
            ApplicationInfo appInfo = getApplicationInfo(packageName, 0);
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
        return isAppSystem(getPackageName());
    }

    /**
     * 判断 APP 是否系统 app
     * @param packageName 应用包名
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppSystem(final String packageName) {
        if (StringUtils.isSpace(packageName)) return false;
        try {
            ApplicationInfo appInfo = getApplicationInfo(packageName, 0);
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
    public static boolean isAppForeground() {
        return isAppForeground(getPackageName());
    }

    /**
     * 判断 APP 是否在前台
     * @param packageName 应用包名
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAppForeground(final String packageName) {
        if (StringUtils.isSpace(packageName)) return false;
        try {
            List<ActivityManager.RunningAppProcessInfo> lists = getActivityManager().getRunningAppProcesses();
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
    public static boolean isInstalledApp(
            final String action,
            final String category
    ) {
        try {
            Intent intent = new Intent(action);
            intent.addCategory(category);
            ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, 0);
            return resolveInfo != null;
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
        if (StringUtils.isSpace(packageName)) return false;
        try {
            ApplicationInfo appInfo = getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return appInfo != null;
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
        return !StringUtils.isSpace(packageName) && IntentUtils.getLaunchAppIntent(packageName) != null;
    }

    // ================
    // = Activity 跳转 =
    // ================

    /**
     * Activity 跳转
     * @param intent {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startActivity(final Intent intent) {
        if (intent == null) return false;
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
    public static boolean startActivityForResult(
            final Activity activity,
            final Intent intent,
            final int requestCode
    ) {
        if (activity == null || intent == null) return false;
        try {
            activity.startActivityForResult(intent, requestCode);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startActivityForResult");
        }
        return false;
    }

    /**
     * Activity 跳转回传
     * @param callback Activity 跳转回传回调
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startActivityForResult(final ActivityUtils.ResultCallback callback) {
        return ActivityUtils.startActivityForResult(callback);
    }

    /**
     * Activity 请求权限跳转回传
     * @param activity      {@link Activity}
     * @param pendingIntent {@link PendingIntent}
     * @param requestCode   请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startIntentSenderForResult(
            final Activity activity,
            final PendingIntent pendingIntent,
            final int requestCode
    ) {
        if (activity == null || pendingIntent == null) return false;
        try {
            activity.startIntentSenderForResult(pendingIntent.getIntentSender(), requestCode,
                    null, 0, 0, 0);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startIntentSenderForResult");
        }
        return false;
    }

    // =======
    // = 广播 =
    // =======

    /**
     * 注册广播监听
     * @param receiver {@linkBroadcastReceiver}
     * @param filter   {@link IntentFilter}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean registerReceiver(
            final BroadcastReceiver receiver,
            final IntentFilter filter
    ) {
        if (receiver == null || filter == null) return false;
        try {
            DevUtils.getContext().registerReceiver(receiver, filter);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerReceiver");
        }
        return false;
    }

    /**
     * 注销广播监听
     * @param receiver {@linkBroadcastReceiver}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean unregisterReceiver(final BroadcastReceiver receiver) {
        if (receiver == null) return false;
        try {
            DevUtils.getContext().unregisterReceiver(receiver);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "unregisterReceiver");
        }
        return false;
    }

    // ===========
    // = 发送广播 =
    // ===========

    /**
     * 发送广播
     * @param intent {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sendBroadcast(final Intent intent) {
        if (intent == null) return false;
        try {
            DevUtils.getContext().sendBroadcast(intent);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "sendBroadcast");
        }
        return false;
    }

    /**
     * 发送广播
     * @param intent             {@link Intent}
     * @param receiverPermission 广播权限
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sendBroadcast(
            final Intent intent,
            final String receiverPermission
    ) {
        if (intent == null || receiverPermission == null) return false;
        try {
            DevUtils.getContext().sendBroadcast(intent, receiverPermission);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "sendBroadcast");
        }
        return false;
    }

    // =======
    // = 服务 =
    // =======

    /**
     * 启动服务
     * @param intent {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startService(final Intent intent) {
        if (intent == null) return false;
        try {
            DevUtils.getContext().startService(intent);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startService");
        }
        return false;
    }

    /**
     * 停止服务
     * @param intent {@link Intent}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean stopService(final Intent intent) {
        if (intent == null) return false;
        try {
            DevUtils.getContext().stopService(intent);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "stopService");
        }
        return false;
    }

    // =============
    // = 安装、卸载 =
    // =============

    /**
     * 安装 APP( 支持 8.0) 的意图
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean installApp(final String filePath) {
        return installApp(FileUtils.getFileByPath(filePath));
    }

    /**
     * 安装 APP( 支持 8.0) 的意图
     * @param file 文件
     * @return {@code true} success, {@code false} fail
     */
    public static boolean installApp(final File file) {
        if (!FileUtils.isFileExists(file)) return false;
        try {
            return startActivity(IntentUtils.getInstallAppIntent(file, true));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "installApp");
            return false;
        }
    }

    /**
     * 安装 APP( 支持 8.0) 的意图
     * @param activity    {@link Activity}
     * @param filePath    文件路径
     * @param requestCode 请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean installApp(
            final Activity activity,
            final String filePath,
            final int requestCode
    ) {
        return installApp(activity, FileUtils.getFileByPath(filePath), requestCode);
    }

    /**
     * 安装 APP( 支持 8.0) 的意图
     * @param activity    {@link Activity}
     * @param file        文件
     * @param requestCode 请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean installApp(
            final Activity activity,
            final File file,
            final int requestCode
    ) {
        if (!FileUtils.isFileExists(file)) return false;
        try {
            activity.startActivityForResult(IntentUtils.getInstallAppIntent(file), requestCode);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "installApp");
            return false;
        }
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
    public static boolean installAppSilent(
            final String filePath,
            final String params
    ) {
        return installAppSilent(FileUtils.getFileByPath(filePath), params, ADBUtils.isDeviceRooted());
    }

    /**
     * 静默安装应用
     * @param file   文件
     * @param params 安装参数
     * @return {@code true} success, {@code false} fail
     */
    public static boolean installAppSilent(
            final File file,
            final String params
    ) {
        return installAppSilent(file, params, ADBUtils.isDeviceRooted());
    }

    /**
     * 静默安装应用
     * @param file     文件
     * @param params   安装参数
     * @param isRooted 是否 root
     * @return {@code true} success, {@code false} fail
     */
    public static boolean installAppSilent(
            final File file,
            final String params,
            final boolean isRooted
    ) {
        if (!FileUtils.isFileExists(file)) return false;
        String                   filePath = '"' + file.getAbsolutePath() + '"';
        String                   command  = "LD_LIBRARY_PATH=/vendor/lib*:/system/lib* pm install " + (params == null ? "" : params + " ") + filePath;
        ShellUtils.CommandResult result   = ShellUtils.execCmd(command, isRooted);
        return result.isSuccess4("success");
    }

    // =

    /**
     * 卸载应用
     * @param packageName 应用包名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean uninstallApp(final String packageName) {
        if (StringUtils.isSpace(packageName)) return false;
        try {
            return startActivity(IntentUtils.getUninstallAppIntent(packageName, true));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "uninstallApp");
            return false;
        }
    }

    /**
     * 卸载应用
     * @param activity    {@link Activity}
     * @param packageName 应用包名
     * @param requestCode 请求 code
     * @return {@code true} success, {@code false} fail
     */
    public static boolean uninstallApp(
            final Activity activity,
            final String packageName,
            final int requestCode
    ) {
        if (StringUtils.isSpace(packageName)) return false;
        try {
            activity.startActivityForResult(IntentUtils.getUninstallAppIntent(packageName), requestCode);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "uninstallApp");
            return false;
        }
    }

    /**
     * 静默卸载应用
     * @param packageName 应用包名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean uninstallAppSilent(final String packageName) {
        return uninstallAppSilent(packageName, false, ADBUtils.isDeviceRooted());
    }

    /**
     * 静默卸载应用
     * @param packageName 应用包名
     * @param isKeepData  true 表示卸载应用但保留数据和缓存目录
     * @return {@code true} success, {@code false} fail
     */
    public static boolean uninstallAppSilent(
            final String packageName,
            final boolean isKeepData
    ) {
        return uninstallAppSilent(packageName, isKeepData, ADBUtils.isDeviceRooted());
    }

    /**
     * 静默卸载应用
     * @param packageName 应用包名
     * @param isKeepData  true 表示卸载应用但保留数据和缓存目录
     * @param isRooted    是否 root
     * @return {@code true} success, {@code false} fail
     */
    public static boolean uninstallAppSilent(
            final String packageName,
            final boolean isKeepData,
            final boolean isRooted
    ) {
        if (StringUtils.isSpace(packageName)) return false;
        String                   command = "LD_LIBRARY_PATH=/vendor/lib*:/system/lib* pm uninstall " + (isKeepData ? "-k " : "") + packageName;
        ShellUtils.CommandResult result  = ShellUtils.execCmd(command, isRooted);
        return result.isSuccess4("success");
    }

    // ===========
    // = 操作相关 =
    // ===========

    /**
     * 打开 APP
     * @param packageName 应用包名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean launchApp(final String packageName) {
        if (StringUtils.isSpace(packageName)) return false;
        try {
            return startActivity(IntentUtils.getLaunchAppIntent(packageName, true));
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
    public static boolean launchApp(
            final Activity activity,
            final String packageName,
            final int requestCode
    ) {
        if (StringUtils.isSpace(packageName)) return false;
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
        return launchAppDetailsSettings(getPackageName());
    }

    /**
     * 跳转到 APP 设置详情页面
     * @param packageName 应用包名
     * @return {@code true} success, {@code false} fail
     */
    public static boolean launchAppDetailsSettings(final String packageName) {
        if (StringUtils.isSpace(packageName)) return false;
        try {
            return startActivity(IntentUtils.getLaunchAppDetailsSettingsIntent(packageName, true));
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
        return launchAppDetails(getPackageName(), marketPkg);
    }

    /**
     * 跳转到 APP 应用商城详情页面
     * @param packageName 应用包名
     * @param marketPkg   应用商店包名, 如果为 ""  则由系统弹出应用商店列表供用户选择, 否则调转到目标市场的应用详情界面, 某些应用商店可能会失败
     * @return {@code true} success, {@code false} fail
     */
    public static boolean launchAppDetails(
            final String packageName,
            final String marketPkg
    ) {
        if (StringUtils.isSpace(packageName)) return false;
        try {
            return startActivity(IntentUtils.getLaunchAppDetailIntent(packageName, marketPkg, true));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "launchAppDetails");
        }
        return false;
    }

    // ===========
    // = 其他功能 =
    // ===========

    /**
     * 打开文件
     * @param filePath 文件路径
     * @param dataType 数据类型
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openFile(
            final String filePath,
            final String dataType
    ) {
        return openFile(FileUtils.getFileByPath(filePath), dataType);
    }

    /**
     * 打开文件
     * @param file     文件
     * @param dataType 数据类型
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openFile(
            final File file,
            final String dataType
    ) {
        if (!FileUtils.isFileExists(file)) return false;
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // 临时授权 ( 必须 )
            intent.setDataAndType(UriUtils.getUriForFile(file, DevUtils.getAuthority()), dataType);
            return startActivity(intent);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openFile");
        }
        return false;
    }

    // =

    /**
     * 打开文件 ( 指定应用 )
     * @param filePath    文件路径
     * @param packageName 应用包名
     * @param className   Activity.class.getCanonicalName()
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openFileByApp(
            final String filePath,
            final String packageName,
            final String className
    ) {
        return openFileByApp(FileUtils.getFileByPath(filePath), packageName, className);
    }

    /**
     * 打开文件 ( 指定应用 )
     * @param file        文件
     * @param packageName 应用包名
     * @param className   Activity.class.getCanonicalName()
     * @return {@code true} success, {@code false} fail
     */
    public static boolean openFileByApp(
            final File file,
            final String packageName,
            final String className
    ) {
        if (!FileUtils.isFileExists(file)) return false;
        try {
            Intent intent = new Intent();
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(DevUtils.getUriForFile(file));
            intent.setClassName(packageName, className);
            return startActivity(intent);
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
        return openPDFFile(FileUtils.getFileByPath(filePath));
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
        return openWordFile(FileUtils.getFileByPath(filePath));
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
        return openOfficeByWPS(FileUtils.getFileByPath(filePath));
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

    // ===========
    // = 系统页面 =
    // ===========

    /**
     * 跳转到系统设置页面
     * @return {@code true} success, {@code false} fail
     */
    public static boolean startSysSetting() {
        try {
            return startActivity(IntentUtils.getIntent(new Intent(Settings.ACTION_SETTINGS), true));
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
    public static boolean startSysSetting(
            final Activity activity,
            final int requestCode
    ) {
        try {
            activity.startActivityForResult(new Intent(Settings.ACTION_SETTINGS), requestCode);
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
            return startActivity(IntentUtils.getIntent(new Intent(Settings.ACTION_WIRELESS_SETTINGS), true));
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
    public static boolean openWirelessSettings(
            final Activity activity,
            final int requestCode
    ) {
        try {
            activity.startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), requestCode);
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
            return startActivity(IntentUtils.getIntent(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), true));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "openGpsSettings");
        }
        return false;
    }
}