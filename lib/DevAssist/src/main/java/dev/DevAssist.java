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
     * 获取 DevApp 版本号
     * @return DevApp versionCode
     */
    public static int getDevAppVersionCode() {
        return BuildConfig.DevApp_VersionCode;
    }

    /**
     * 获取 DevApp 版本
     * @return DevApp versionName
     */
    public static String getDevAppVersion() {
        return BuildConfig.DevApp_Version;
    }
}