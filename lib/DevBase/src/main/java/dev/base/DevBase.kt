package dev.base

/**
 * detail: DevBase
 * @author Ttt
 * <p></p>
 * GitHub
 * @see https://github.com/afkT/DevUtils
 * DevApp Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md
 * DevAssist Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md
 * DevBase README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md
 * DevBaseMVVM README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/README.md
 * DevEngine README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md
 * DevHttpCapture Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md
 * DevHttpManager Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md
 * DevRetrofit Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/README.md
 * DevJava Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md
 * DevWidget Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md
 * DevEnvironment Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/Environment
 */
object DevBase {

    // ============
    // = 工具类版本 =
    // ============

    /**
     * 获取 DevBase 版本号
     * @return DevBase versionCode
     */
    fun getDevBaseVersionCode(): Int {
        return BuildConfig.DevBase_VersionCode
    }

    /**
     * 获取 DevBase 版本
     * @return DevBase versionName
     */
    fun getDevBaseVersion(): String {
        return BuildConfig.DevBase_Version
    }

    /**
     * 获取 DevApp 版本号
     * @return DevApp versionCode
     */
    fun getDevAppVersionCode(): Int {
        return BuildConfig.DevApp_VersionCode
    }

    /**
     * 获取 DevApp 版本
     * @return DevApp versionName
     */
    fun getDevAppVersion(): String {
        return BuildConfig.DevApp_Version
    }
}