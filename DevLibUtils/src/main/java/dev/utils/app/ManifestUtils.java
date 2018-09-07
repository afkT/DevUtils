package dev.utils.app;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

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
     * 获取 Manifest Meta Data
     * @param metaKey
     * @return
     */
    public static String getMetaData(String metaKey) {
        try {
            ApplicationInfo appInfo = DevUtils.getContext().getPackageManager().getApplicationInfo(DevUtils.getContext().getPackageName(), PackageManager.GET_META_DATA);
            String data = appInfo.metaData.getString(metaKey);
            return data;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMetaData");
        }
        return null;
    }

    // ==

    /**
     * 获取app版本信息
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
     * 获取app版本号
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
     * 获取app版本信息
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
     * 获取app版本号 - 内部判断
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
     * 获取app版本名 - 对外显示
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
