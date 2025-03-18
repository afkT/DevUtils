package dev.simple

/**
 * detail: DevSimple
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
 * DevSimple README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/DevSimple/README.md
 * DevAgile README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/DevAgile/README.md
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
object DevSimple {

    // ============
    // = 工具类版本 =
    // ============

    /**
     * 获取 DevSimple 版本号
     * @return DevSimple versionCode
     */
    fun getDevSimpleVersionCode(): Int {
        return BuildConfig.DevSimple_VersionCode
    }

    /**
     * 获取 DevSimple 版本
     * @return DevSimple versionName
     */
    fun getDevSimpleVersion(): String {
        return BuildConfig.DevSimple_Version
    }

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
}