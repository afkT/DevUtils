package dev.utils.app;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;

import dev.utils.LogPrintUtils;

/**
 * detail: Android Manifest 工具类
 * @author Ttt
 */
public final class ManifestUtils {

    private ManifestUtils() {
    }

    // 日志 TAG
    private static final String TAG = ManifestUtils.class.getSimpleName();

    /**
     * 获取 Application meta Data
     * @param metaKey meta Key
     * @return Application meta Data
     */
    public static String getMetaData(final String metaKey) {
        return getMetaData(AppUtils.getPackageName(), metaKey);
    }

    /**
     * 获取 Application meta Data
     * @param packageName 应用包名
     * @param metaKey     meta Key
     * @return Application meta Data
     */
    public static String getMetaData(
            final String packageName,
            final String metaKey
    ) {
        try {
            ApplicationInfo appInfo = AppUtils.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(metaKey);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMetaData");
        }
        return null;
    }

    // =

    /**
     * 获取 Activity meta Data
     * @param clazz   Activity.class
     * @param metaKey meta Key
     * @return Activity meta Data
     */
    public static String getMetaDataInActivity(
            final Class<?> clazz,
            final String metaKey
    ) {
        return (clazz != null) ? getMetaDataInActivity(AppUtils.getPackageName(), clazz.getCanonicalName(), metaKey) : null;
    }

    /**
     * 获取 Activity meta Data
     * @param name    class.getCanonicalName()
     * @param metaKey meta Key
     * @return Activity meta Data
     */
    public static String getMetaDataInActivity(
            final String name,
            final String metaKey
    ) {
        return getMetaDataInActivity(AppUtils.getPackageName(), name, metaKey);
    }

    /**
     * 获取 Activity meta Data
     * @param packageName 应用包名
     * @param name        class.getCanonicalName()
     * @param metaKey     meta Key
     * @return Activity meta Data
     */
    public static String getMetaDataInActivity(
            final String packageName,
            final String name,
            final String metaKey
    ) {
        try {
            ComponentName componentName = new ComponentName(packageName, name);
            ActivityInfo  activityInfo  = AppUtils.getPackageManager().getActivityInfo(componentName, PackageManager.GET_META_DATA);
            return activityInfo.metaData.getString(metaKey);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMetaDataInActivity");
        }
        return null;
    }

    // =

    /**
     * 获取 Service meta Data
     * @param clazz   Service.class
     * @param metaKey meta Key
     * @return Service meta Data
     */
    public static String getMetaDataInService(
            final Class<?> clazz,
            final String metaKey
    ) {
        return (clazz != null) ? getMetaDataInService(AppUtils.getPackageName(), clazz.getCanonicalName(), metaKey) : null;
    }

    /**
     * 获取 Service meta Data
     * @param name    class.getCanonicalName()
     * @param metaKey meta Key
     * @return Service meta Data
     */
    public static String getMetaDataInService(
            final String name,
            final String metaKey
    ) {
        return getMetaDataInService(AppUtils.getPackageName(), name, metaKey);
    }

    /**
     * 获取 Service meta Data
     * @param packageName 应用包名
     * @param name        class.getCanonicalName()
     * @param metaKey     meta Key
     * @return Service meta Data
     */
    public static String getMetaDataInService(
            final String packageName,
            final String name,
            final String metaKey
    ) {
        try {
            ComponentName componentName = new ComponentName(packageName, name);
            ServiceInfo   serviceInfo   = AppUtils.getPackageManager().getServiceInfo(componentName, PackageManager.GET_META_DATA);
            return serviceInfo.metaData.getString(metaKey);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMetaDataInService");
        }
        return null;
    }

    // =

    /**
     * 获取 Receiver meta Data
     * @param clazz   Receiver.class
     * @param metaKey meta Key
     * @return Receiver meta Data
     */
    public static String getMetaDataInReceiver(
            final Class<?> clazz,
            final String metaKey
    ) {
        return (clazz != null) ? getMetaDataInReceiver(AppUtils.getPackageName(), clazz.getCanonicalName(), metaKey) : null;
    }

    /**
     * 获取 Receiver meta Data
     * @param name    class.getCanonicalName()
     * @param metaKey meta Key
     * @return Receiver meta Data
     */
    public static String getMetaDataInReceiver(
            final String name,
            final String metaKey
    ) {
        return getMetaDataInReceiver(AppUtils.getPackageName(), name, metaKey);
    }

    /**
     * 获取 Receiver meta Data
     * @param packageName 应用包名
     * @param name        class.getCanonicalName()
     * @param metaKey     meta Key
     * @return Receiver meta Data
     */
    public static String getMetaDataInReceiver(
            final String packageName,
            final String name,
            final String metaKey
    ) {
        try {
            ComponentName componentName = new ComponentName(packageName, name);
            ActivityInfo  receiverInfo  = AppUtils.getPackageManager().getReceiverInfo(componentName, PackageManager.GET_META_DATA);
            return receiverInfo.metaData.getString(metaKey);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMetaDataInReceiver");
        }
        return null;
    }

    // =

    /**
     * 获取 ContentProvider meta Data
     * @param clazz   ContentProvider.class
     * @param metaKey meta Key
     * @return ContentProvider meta Data
     */
    public static String getMetaDataInProvider(
            final Class<?> clazz,
            final String metaKey
    ) {
        return (clazz != null) ? getMetaDataInProvider(AppUtils.getPackageName(), clazz.getCanonicalName(), metaKey) : null;
    }

    /**
     * 获取 ContentProvider meta Data
     * @param name    class.getCanonicalName()
     * @param metaKey meta Key
     * @return ContentProvider meta Data
     */
    public static String getMetaDataInProvider(
            final String name,
            final String metaKey
    ) {
        return getMetaDataInProvider(AppUtils.getPackageName(), name, metaKey);
    }

    /**
     * 获取 ContentProvider meta Data
     * @param packageName 应用包名
     * @param name        class.getCanonicalName()
     * @param metaKey     meta Key
     * @return ContentProvider meta Data
     */
    public static String getMetaDataInProvider(
            final String packageName,
            final String name,
            final String metaKey
    ) {
        try {
            ComponentName componentName = new ComponentName(packageName, name);
            ProviderInfo  providerInfo  = AppUtils.getPackageManager().getProviderInfo(componentName, PackageManager.GET_META_DATA);
            return providerInfo.metaData.getString(metaKey);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMetaDataInProvider");
        }
        return null;
    }

    // =

    /**
     * 获取 APP 版本信息
     * @return 0 = versionName, 1 = versionCode
     */
    public static String[] getAppVersion() {
        return getAppVersion(AppUtils.getPackageName());
    }

    /**
     * 获取 APP 版本信息
     * @param packageName 应用包名
     * @return 0 = versionName, 1 = versionCode
     */
    public static String[] getAppVersion(final String packageName) {
        try {
            PackageInfo packageInfo = AppUtils.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            if (packageInfo != null) {
                String versionName = String.valueOf(packageInfo.versionName);
                String versionCode;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    versionCode = String.valueOf(packageInfo.getLongVersionCode());
                } else {
                    versionCode = String.valueOf(packageInfo.versionCode);
                }
                return new String[]{versionName, versionCode};
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppVersion");
        }
        return null;
    }

    /**
     * 获取 APP versionCode
     * @return APP versionCode
     */
    public static long getAppVersionCode() {
        return AppUtils.getAppVersionCode();
    }

    /**
     * 获取 APP versionName
     * @return APP versionName
     */
    public static String getAppVersionName() {
        return AppUtils.getAppVersionName();
    }

    // =

    /**
     * 获取 APP versionCode
     * @param packageName 应用包名
     * @return APP versionCode
     */
    public static long getAppVersionCode(final String packageName) {
        return AppUtils.getAppVersionCode(packageName);
    }

    /**
     * 获取 APP versionName
     * @param packageName 应用包名
     * @return APP versionName
     */
    public static String getAppVersionName(final String packageName) {
        return AppUtils.getAppVersionName(packageName);
    }
}