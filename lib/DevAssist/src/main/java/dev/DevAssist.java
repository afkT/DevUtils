package dev;

import dev.assist.BuildConfig;

/**
 * detail: 开发辅助类
 * @author Ttt
 * <pre>
 *     GitHub
 *     @see <a href="https://github.com/afkT/DevUtils"/>
 *     DevApp Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md"/>
 *     DevAssist Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md"/>
 *     DevBase README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md"/>
 *     DevBaseMVVM README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/README.md"/>
 *     DevMVVM README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevMVVM/README.md"/>
 *     DevEngine README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md"/>
 *     DevSimple README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/DevSimple/README.md"/>
 *     DevWidget Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md"/>
 *     DevRetrofit Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/README.md"/>
 *     DevHttpManager Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md"/>
 *     DevHttpCapture Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md"/>
 *     DevEnvironment Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/Environment"/>
 *     DevDeprecated Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/README.md"/>
 *     DevJava Api
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md"/>
 * </pre>
 */
public final class DevAssist {

    private DevAssist() {
    }

    // ============
    // = 工具类版本 =
    // ============

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