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
     * 获取 DevAssist 版本号
     * @return DevAssist versionCode
     */
    public static int getDevAssistVersionCode() {
        return BuildConfig.DevAssist_VersionCode;
    }

    /**
     * 获取 DevAssist 版本
     * @return DevAssist versionName
     */
    public static String getDevAssistVersion() {
        return BuildConfig.DevAssist_Version;
    }

    /**
     * 获取 DevJava 版本号
     * @return DevJava version
     */
    public static int getDevJavaVersionCode() {
        return BuildConfig.DevJava_VersionCode;
    }

    /**
     * 获取 DevJava 版本
     * @return DevJava version
     */
    public static String getDevJavaVersion() {
        return BuildConfig.DevJava_Version;
    }
}