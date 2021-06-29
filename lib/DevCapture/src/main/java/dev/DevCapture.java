package dev;

import dev.capture.BuildConfig;

/**
 * detail: OKHttp 抓包工具库
 * @author Ttt
 */
public final class DevCapture {

    private DevCapture() {
    }

    // ============
    // = 工具类版本 =
    // ============

    /**
     * 获取 DevCapture 版本号
     * @return DevCapture versionCode
     */
    public static int getDevCaptureVersionCode() {
        return BuildConfig.DevCapture_VersionCode;
    }

    /**
     * 获取 DevCapture 版本
     * @return DevCapture versionName
     */
    public static String getDevCaptureVersion() {
        return BuildConfig.DevCapture_Version;
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