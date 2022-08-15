package dev.mvvm

/**
 * detail: DevMVVM
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
 * DevMVVM README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevMVVM/README.md
 * DevEngine README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md
 * DevHttpCapture Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md
 * DevHttpManager Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md
 * DevRetrofit Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/README.md
 * DevWidget Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md
 * DevEnvironment Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/Environment
 * DevJava Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md
 */
object DevMVVM {

    // ============
    // = 工具类版本 =
    // ============

    /**
     * 获取 DevMVVM 版本号
     * @return DevMVVM versionCode
     */
    fun getDevMVVMVersionCode(): Int {
        return BuildConfig.DevMVVM_VersionCode
    }

    /**
     * 获取 DevMVVM 版本
     * @return DevMVVM versionName
     */
    fun getDevMVVMVersion(): String {
        return BuildConfig.DevMVVM_Version
    }

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
}