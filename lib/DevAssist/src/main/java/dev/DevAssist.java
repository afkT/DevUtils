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
     * 获取 DevAssist 工具类版本号
     * @return DevAssist versionCode
     */
    public static int getDevAssistUtilsVersionCode() {
        return BuildConfig.DevAssist_VersionCode;
    }

    /**
     * 获取 DevAssist 工具类版本
     * @return DevAssist versionName
     */
    public static String getDevAssistUtilsVersion() {
        return BuildConfig.DevAssist_Version;
    }

    /**
     * 获取 DevJava 工具类版本号
     * @return DevJava version
     */
    public static int getDevJavaUtilsVersionCode() {
        return BuildConfig.DevJava_VersionCode;
    }

    /**
     * 获取 DevJava 工具类版本
     * @return DevJava version
     */
    public static String getDevJavaUtilsVersion() {
        return BuildConfig.DevJava_Version;
    }
}