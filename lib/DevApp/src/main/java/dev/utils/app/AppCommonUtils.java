package dev.utils.app;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresPermission;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: APP 通用工具类
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.READ_PHONE_STATE" />
 * </pre>
 */
public final class AppCommonUtils {

    private AppCommonUtils() {
    }

    // 日志 TAG
    private static final String TAG = AppCommonUtils.class.getSimpleName();
    // 应用、设备信息 ( 可用于 FileRecordUtils 插入信息使用 )
    private static String APP_DEVICE_INFO = null;

    /**
     * 获取应用、设备信息
     * @return 应用、设备信息
     */
    public static String getAppDeviceInfo() {
        return APP_DEVICE_INFO;
    }

    /**
     * 刷新应用、设备信息
     * @return 应用、设备信息
     */
    public static String refreshAppDeviceInfo() {
        try {
            StringBuilder builder = new StringBuilder();
            // 获取 APP 版本信息
            String[] versions = getAppVersion();
            String versionName = versions[0];
            String versionCode = versions[1];
            String packageName = getPackageName();
            String deviceInfo = handlerDeviceInfo(getDeviceInfo(), null);
            if (TextUtils.isEmpty(versionName) || TextUtils.isEmpty(versionCode) ||
                    TextUtils.isEmpty(packageName) || TextUtils.isEmpty(deviceInfo)) {
                return null;
            }
            // 保存 APP 版本信息
            builder.append("versionName: " + versionName);
            builder.append(NEW_LINE_STR);
            builder.append("versionCode: " + versionCode);
            builder.append(NEW_LINE_STR);
            builder.append("package: " + packageName);
            builder.append(NEW_LINE_STR);
            builder.append(deviceInfo);
            // 设置应用、设备信息
            APP_DEVICE_INFO = builder.toString();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "refreshAppDeviceInfo");
        }
        return APP_DEVICE_INFO;
    }

    // =

    /**
     * 获取设备唯一 UUID
     * @return 设备唯一 UUID
     */
    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    public static String getUUID() {
        return PhoneUtils.getUUID();
    }

    /**
     * 获取 R.string 资源的格式化字符串
     * @param resId R.string.id
     * @param objs  格式化参数
     * @return 格式化后的字符串
     */
    public static String getFormatRes(@StringRes final int resId, final Object... objs) {
        return getFormatRes(false, resId, objs);
    }

    /**
     * 获取 R.string 资源的格式化字符串
     * @param errorMsg 是否设置异常信息
     * @param resId    R.string.id
     * @param objs     格式化参数
     * @return 格式化后的字符串
     */
    public static String getFormatRes(final boolean errorMsg, @StringRes final int resId, final Object... objs) {
        try {
            if (objs != null && objs.length != 0) {
                return DevUtils.getContext().getString(resId, objs);
            } else {
                return DevUtils.getContext().getString(resId);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getFormatRes");
            if (errorMsg && e != null) {
                return e.getMessage();
            }
        }
        return null;
    }

    // ================
    // = 版本判断处理 =
    // ================

    /**
     * 获取 SDK 版本
     * @return SDK 版本
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    // =

    /**
     * 是否在 2.2 版本及以上
     * @return 是否在 2.2 版本及以上
     */
    public static boolean isFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * 是否在 2.3 版本及以上
     * @return 是否在 2.3 版本及以上
     */
    public static boolean isGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * 是否在 2.3.3 版本及以上
     * @return 是否在 2.3.3 版本及以上
     */
    public static boolean isGingerbreadMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
    }

    /**
     * 是否在 3.0 版本及以上
     * @return 是否在 3.0 版本及以上
     */
    public static boolean isHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * 是否在 3.1 版本及以上
     * @return 是否在 3.1 版本及以上
     */
    public static boolean isHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * 是否在 4.0 版本及以上
     * @return 是否在 4.0 版本及以上
     */
    public static boolean isIceCreamSandwich() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * 是否在 4.0.3 版本及以上
     * @return 是否在 4.0.3 版本及以上
     */
    public static boolean isIceCreamSandwichMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
    }

    /**
     * 是否在 4.1 版本及以上
     * @return 是否在 4.1 版本及以上
     */
    public static boolean isJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * 是否在 4.2 版本及以上
     * @return 是否在 4.2 版本及以上
     */
    public static boolean isJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * 是否在 4.3 版本及以上
     * @return 是否在 4.3 版本及以上
     */
    public static boolean isJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * 是否在 4.4.2 版本及以上
     * @return 是否在 4.4.2 版本及以上
     */
    public static boolean isKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 是否在 5.0.1 版本及以上
     * @return 是否在 5.0.1 版本及以上
     */
    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * 是否在 6.0 版本及以上
     * @return 是否在 6.0 版本及以上
     */
    public static boolean isM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 是否在 7.0 版本及以上
     * @return 是否在 7.0 版本及以上
     */
    public static boolean isN() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * 是否在 7.1.1 版本及以上
     * @return 是否在 7.1.1 版本及以上
     */
    public static boolean isN_MR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;
    }

    /**
     * 是否在 8.0 版本及以上
     * @return 是否在 8.0 版本及以上
     */
    public static boolean isO() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * 是否在 8.1 版本及以上
     * @return 是否在 8.1 版本及以上
     */
    public static boolean isO_MR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1;
    }

    /**
     * 是否在 9.0 版本及以上
     * @return 是否在 9.0 版本及以上
     */
    public static boolean isP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    /**
     * 是否在 10.0 版本及以上
     * @return 是否在 10.0 版本及以上
     */
    public static boolean isQ() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    /**
     * 转换 SDK 版本 convertSDKVersion(14) = Android 4.0.0-2
     * @return SDK 版本
     */
    public static String convertSDKVersion() {
        return convertSDKVersion(Build.VERSION.SDK_INT);
    }

    /**
     * 转换 SDK 版本 convertSDKVersion(14) = Android 4.0.0-2
     * @param sdkVersion SDK 版本
     * @return SDK 版本
     */
    public static String convertSDKVersion(final int sdkVersion) {
        switch (sdkVersion) {
            case 1:
                return "Android 1.0";
            case 2:
                return "Android 1.1";
            case 3:
                return "Android 1.5";
            case 4:
                return "Android 1.6";
            case 5:
                return "Android 2.0";
            case 6:
                return "Android 2.0.1";
            case 7:
                return "Android 2.1.x";
            case 8:
                return "Android 2.2.x";
            case 9:
                return "Android 2.3.0-2";
            case 10:
                return "Android 2.3.3-4";
            case 11:
                return "Android 3.0.x";
            case 12:
                return "Android 3.1.x";
            case 13:
                return "Android 3.2";
            case 14:
                return "Android 4.0.0-2";
            case 15:
                return "Android 4.0.3-4";
            case 16:
                return "Android 4.1.x";
            case 17:
                return "Android 4.2.x";
            case 18:
                return "Android 4.3";
            case 19:
                return "Android 4.4";
            case 20:
                return "Android 4.4W";
            case 21:
                return "Android 5.0";
            case 22:
                return "Android 5.1";
            case 23:
                return "Android 6.0";
            case 24:
                return "Android 7.0";
            case 25:
                return "Android 7.1.1";
            case 26:
                return "Android 8.0";
            case 27:
                return "Android 8.1";
            case 28:
                return "Android 9.0";
            case 29:
                return "Android 10.0";
        }
        return "unknown";
    }

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // ===============
    // = DeviceUtils =
    // ===============

    // 换行字符串
    private static final String NEW_LINE_STR = System.getProperty("line.separator");

    /**
     * 获取设备信息
     * @return {@link Map<String, String>}
     */
    private static Map<String, String> getDeviceInfo() {
        return getDeviceInfo(new HashMap<>());
    }

    /**
     * 获取设备信息
     * @param deviceInfoMap 设备信息 Map
     * @return {@link Map<String, String>}
     */
    private static Map<String, String> getDeviceInfo(final Map<String, String> deviceInfoMap) {
        // 获取设备信息类的所有申明的字段, 即包括 public、private 和 proteced, 但是不包括父类的申明字段
        Field[] fields = Build.class.getDeclaredFields();
        // 遍历字段
        for (Field field : fields) {
            try {
                // 取消 Java 的权限控制检查
                field.setAccessible(true);
                // 转换当前设备支持的 ABI - CPU 指令集
                if (field.getName().toLowerCase().startsWith("SUPPORTED".toLowerCase())) {
                    try {
                        Object object = field.get(null);
                        // 判断是否数组
                        if (object instanceof String[]) {
                            if (object != null) {
                                // 获取类型对应字段的数据, 并保存支持的指令集 [arm64-v8a, armeabi-v7a, armeabi]
                                deviceInfoMap.put(field.getName(), Arrays.toString((String[]) object));
                            }
                            continue;
                        }
                    } catch (Exception e) {
                    }
                }
                // 获取类型对应字段的数据, 并保存
                deviceInfoMap.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getDeviceInfo");
            }
        }
        return deviceInfoMap;
    }

    /**
     * 处理设备信息
     * @param deviceInfoMap 设备信息 Map
     * @param errorInfo     错误提示信息, 如获取设备信息失败
     * @return 拼接后的设备信息字符串
     */
    private static String handlerDeviceInfo(final Map<String, String> deviceInfoMap, final String errorInfo) {
        try {
            // 初始化 Builder, 拼接字符串
            StringBuilder builder = new StringBuilder();
            // 获取设备信息
            Iterator<Map.Entry<String, String>> mapIter = deviceInfoMap.entrySet().iterator();
            // 遍历设备信息
            while (mapIter.hasNext()) {
                // 获取对应的 key - value
                Map.Entry<String, String> rnEntry = mapIter.next();
                String rnKey = rnEntry.getKey(); // key
                String rnValue = rnEntry.getValue(); // value
                // 保存设备信息
                builder.append(rnKey);
                builder.append(" = ");
                builder.append(rnValue);
                builder.append(NEW_LINE_STR);
            }
            return builder.toString();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "handlerDeviceInfo");
        }
        return errorInfo;
    }

    // =================
    // = ManifestUtils =
    // =================

    /**
     * 获取 APP 版本信息
     * @return 0 = versionName, 1 = versionCode
     */
    private static String[] getAppVersion() {
        try {
            PackageManager packageManager = AppUtils.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(AppUtils.getPackageName(), PackageManager.GET_SIGNATURES);
            if (packageInfo != null) {
                String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String versionCode = packageInfo.versionCode + "";
                return new String[]{versionName, versionCode};
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAppVersion");
        }
        return null;
    }

    // ============
    // = AppUtils =
    // ============

    /**
     * 获取 APP 包名
     * @return APP 包名
     */
    private static String getPackageName() {
        try {
            return DevUtils.getContext().getPackageName();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getPackageName");
        }
        return null;
    }
}