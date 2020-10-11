package dev;

import dev.assist.BuildConfig;

/**
 * detail: 开发辅助类
 * @author Ttt
 */
public final class DevAssist {

    private DevAssist() {
    }

    // =============
    // = 工具类版本 =
    // =============

    /**
     * 获取 DevAssist 工具类版本
     * @return DevAssist versionName
     */
    public static String getDevAssistUtilsVersion() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * 获取 DevAssist 工具类版本号
     * @return DevAssist versionCode
     */
    public static int getDevAssistUtilsVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    /**
     * 获取 DevJava 工具类版本
     * @return DevJava version
     */
    public static String getDevJavaUtilsVersion() {
        return BuildConfig.DevJava_Version;
    }

    /**
     * 获取 DevJava 工具类版本号
     * @return DevJava version
     */
    public static int getDevJavaUtilsVersionCode() {
        return BuildConfig.DevJava_VersionCode;
    }
}