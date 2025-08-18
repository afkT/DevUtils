package dev.base

import dev.base.mvvm.BuildConfig

/**
 * detail: DevBaseMVVM
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
 * DevEngine README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md
 * DevSimple README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/README.md
 * DevWidget Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md
 * DevRetrofit Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/README.md
 * DevHttpManager Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md
 * DevHttpCapture Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md
 * DevEnvironment Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/Environment
 * DevJava Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md
 */
object DevBaseMVVM {

    // ============
    // = 工具类版本 =
    // ============

    /**
     * 获取 DevBaseMVVM 版本号
     * @return DevBaseMVVM versionCode
     */
    fun getDevBaseMVVMVersionCode(): Int {
        return BuildConfig.DevBaseMVVM_VersionCode
    }

    /**
     * 获取 DevBaseMVVM 版本
     * @return DevBaseMVVM versionName
     */
    fun getDevBaseMVVMVersion(): String {
        return BuildConfig.DevBaseMVVM_Version
    }

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
}