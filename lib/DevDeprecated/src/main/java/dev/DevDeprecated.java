package dev;

import dev.deprecated.BuildConfig;

/**
 * detail: Dev 系列库弃用代码统一存储库
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
 *     DevEngine README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md"/>
 *     DevSimple README
 *     @see <a href="https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/README.md"/>
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
public final class DevDeprecated {

    private DevDeprecated() {
    }

    // ============
    // = 工具类版本 =
    // ============

    /**
     * 获取 DevDeprecated 版本号
     * @return DevDeprecated versionCode
     */
    public static int getDevDeprecatedVersionCode() {
        return BuildConfig.DevDeprecated_VersionCode;
    }

    /**
     * 获取 DevDeprecated 版本
     * @return DevDeprecated versionName
     */
    public static String getDevDeprecatedVersion() {
        return BuildConfig.DevDeprecated_Version;
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