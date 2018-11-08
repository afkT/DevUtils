package dev.utils.app;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Android Manifest工具类
 * Created by Ttt
 */
public final class ManifestUtils {

    private ManifestUtils(){
    }

    // 日志TAG
    private static final String TAG = ManifestUtils.class.getSimpleName();

    /**
     * 获取 Application Meta Data
     * @param metaKey
     * @return
     */
    public static String getMetaData(String metaKey) {
        return getMetaData(DevUtils.getContext().getPackageName(), metaKey);
    }

    /**
     * 获取 Application Meta Data
     * @param packageName
     * @param metaKey
     * @return
     */
    public static String getMetaData(String packageName, String metaKey) {
        try {
            ApplicationInfo appInfo = DevUtils.getContext().getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            String data = appInfo.metaData.getString(metaKey);
            return data;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMetaData");
        }
        return null;
    }

    // -

    /**
     * 获取 Activity Meta Data
     * @param cla
     * @param metaKey
     * @return
     */
    public static String getMetaDataInActivity(Class<?> cla, String metaKey) {
        return getMetaDataInActivity(DevUtils.getContext().getPackageName(), cla.getCanonicalName(), metaKey);
    }

    /**
     * 获取 Activity Meta Data
     * @param name 完整路径名 package.name => class.getCanonicalName()
     * @param metaKey
     * @return
     */
    public static String getMetaDataInActivity(String name, String metaKey) {
        return getMetaDataInActivity(DevUtils.getContext().getPackageName(), name, metaKey);
    }

    /**
     * 获取 Activity Meta Data
     * @param packageName
     * @param name 完整路径名 package.name => class.getCanonicalName()
     * @param metaKey
     * @return
     */
    public static String getMetaDataInActivity(String packageName, String name, String metaKey) {
        try {
            ComponentName componentName = new ComponentName(packageName, name);
            ActivityInfo activityInfo = DevUtils.getContext().getPackageManager().getActivityInfo(componentName, PackageManager.GET_META_DATA);
            String data = activityInfo.metaData.getString(metaKey);
            return data;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMetaDataInActivity");
        }
        return null;
    }

    // -

    /**
     * 获取 Service Meta Data
     * @param cla
     * @param metaKey
     * @return
     */
    public static String getMetaDataInService(Class<?> cla, String metaKey) {
        return getMetaDataInService(DevUtils.getContext().getPackageName(), cla.getCanonicalName(), metaKey);
    }

    /**
     * 获取 Service Meta Data
     * @param name 完整路径名 package.name => class.getCanonicalName()
     * @param metaKey
     * @return
     */
    public static String getMetaDataInService(String name, String metaKey) {
        return getMetaDataInService(DevUtils.getContext().getPackageName(), name, metaKey);
    }

    /**
     * 获取 Service Meta Data
     * @param packageName
     * @param name 完整路径名 package.name => class.getCanonicalName()
     * @param metaKey
     * @return
     */
    public static String getMetaDataInService(String packageName, String name, String metaKey) {
        try {
            ComponentName componentName = new ComponentName(packageName, name);
            ServiceInfo serviceInfo = DevUtils.getContext().getPackageManager().getServiceInfo(componentName, PackageManager.GET_META_DATA);
            String data = serviceInfo.metaData.getString(metaKey);
            return data;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMetaDataInService");
        }
        return null;
    }

    // -

    /**
     * 获取 Receiver Meta Data
     * @param cla
     * @param metaKey
     * @return
     */
    public static String getMetaDataInReceiver(Class<?> cla, String metaKey) {
        return getMetaDataInReceiver(DevUtils.getContext().getPackageName(), cla.getCanonicalName(), metaKey);
    }

    /**
     * 获取 Receiver Meta Data
     * @param name 完整路径名 package.name => class.getCanonicalName()
     * @param metaKey
     * @return
     */
    public static String getMetaDataInReceiver(String name, String metaKey) {
        return getMetaDataInReceiver(DevUtils.getContext().getPackageName(), name, metaKey);
    }

    /**
     * 获取 Receiver Meta Data
     * @param packageName
     * @param name 完整路径名 package.name => class.getCanonicalName()
     * @param metaKey
     * @return
     */
    public static String getMetaDataInReceiver(String packageName, String name, String metaKey) {
        try {
            ComponentName componentName = new ComponentName(packageName, name);
            ActivityInfo receiverInfo = DevUtils.getContext().getPackageManager().getReceiverInfo(componentName, PackageManager.GET_META_DATA);
            String data = receiverInfo.metaData.getString(metaKey);
            return data;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMetaDataInReceiver");
        }
        return null;
    }

    // -

    /**
     * 获取 ContentProvider Meta Data
     * @param cla
     * @param metaKey
     * @return
     */
    public static String getMetaDataInProvider(Class<?> cla, String metaKey) {
        return getMetaDataInProvider(DevUtils.getContext().getPackageName(), cla.getCanonicalName(), metaKey);
    }

    /**
     * 获取 ContentProvider Meta Data
     * @param name 完整路径名 package.name => class.getCanonicalName()
     * @param metaKey
     * @return
     */
    public static String getMetaDataInProvider(String name, String metaKey) {
        return getMetaDataInProvider(DevUtils.getContext().getPackageName(), name, metaKey);
    }

    /**
     * 获取 ContentProvider Meta Data
     * @param packageName
     * @param name 完整路径名 package.name => class.getCanonicalName()
     * @param metaKey
     * @return
     */
    public static String getMetaDataInProvider(String packageName, String name, String metaKey) {
        try {
            ComponentName componentName = new ComponentName(packageName, name);
            ProviderInfo providerInfo = DevUtils.getContext().getPackageManager().getProviderInfo(componentName, PackageManager.GET_META_DATA);
            String data = providerInfo.metaData.getString(metaKey);
            return data;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMetaDataInProvider");
        }
        return null;
    }

    // ==

    /**
     * 获取 App 版本信息
     * @return 0 = versionName , 1 = versionCode
     */
    public static String[] getAppVersion() {
        try {
            PackageManager pm = DevUtils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(DevUtils.getContext().getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";

                return new String[]{versionName,versionCode};
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppVersion");
        }
        return null;
    }

    /**
     * 获取 App 版本号
     * @return 当前版本Code
     */
    public static int getAppVersionCode() {
        try {
            PackageManager pm = DevUtils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(DevUtils.getContext().getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                return pi.versionCode;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppVersionCode");
        }
        return -1;
    }

    /**
     * 获取 App 版本信息
     * @return 当前版本信息
     */
    public static String getAppVersionName() {
        try {
            PackageManager pm = DevUtils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(DevUtils.getContext().getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                return pi.versionName;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppVersionName");
        }
        return null;
    }

    // =

    /**
     * 获取 App 版本号 - 内部判断
     * @param packageName
     * @return
     */
    public static int getAppVersionCode(final String packageName) {
        if (isSpace(packageName)) return -1;
        try {
            PackageManager pm = DevUtils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            LogPrintUtils.eTag(TAG, e, "getAppVersionCode");
            return -1;
        }
    }


    /**
     * 获取 App 版本名 - 对外显示
     * @param packageName
     * @return
     */
    public static String getAppVersionName(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = DevUtils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            LogPrintUtils.eTag(TAG, e, "getAppVersionName");
            return null;
        }
    }

    // =

    /**
     * 判断字符串是否为 null 或全为空白字符
     * @param str 待校验字符串
     * @return
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
}
