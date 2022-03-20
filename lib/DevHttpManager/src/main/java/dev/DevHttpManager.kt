package dev

import dev.http.BuildConfig

/**
 * detail: OkHttp 管理类 ( Retrofit 多 BaseUrl 等 )
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
 * DevJava Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md
 * DevWidget Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README_API.md
 * DevEnvironment Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/Environment
 */
object DevHttpManager {

    // ============
    // = 工具类版本 =
    // ============

    /**
     * 获取 DevHttpManager 版本号
     * @return DevHttpManager versionCode
     */
    fun getDevHttpManagerVersionCode(): Int {
        return BuildConfig.DevHttpManager_VersionCode;
    }

    /**
     * 获取 DevHttpManager 版本
     * @return DevHttpManager versionName
     */
    fun getDevHttpManagerVersion(): String {
        return BuildConfig.DevHttpManager_Version;
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