package dev.agile

/**
 * detail: DevAgile
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
object DevAgile {

    // ============
    // = 工具类版本 =
    // ============

    /**
     * 获取 DevAgile 版本号
     * @return DevAgile versionCode
     */
    fun getDevAgileVersionCode(): Int {
        return BuildConfig.DevAgile_VersionCode
    }

    /**
     * 获取 DevAgile 版本
     * @return DevAgile versionName
     */
    fun getDevAgileVersion(): String {
        return BuildConfig.DevAgile_Version
    }

    /**
     * 获取 DevAssist 版本号
     * @return DevAssist versionCode
     */
    fun getDevAssistVersionCode(): Int {
        return BuildConfig.DevAssist_VersionCode
    }

    /**
     * 获取 DevAssist 版本
     * @return DevAssist versionName
     */
    fun getDevAssistVersion(): String {
        return BuildConfig.DevAssist_Version
    }

    /**
     * 获取 DevRetrofit 版本号
     * @return DevRetrofit versionCode
     */
    fun getDevRetrofitVersionCode(): Int {
        return BuildConfig.DevRetrofit_VersionCode
    }

    /**
     * 获取 DevRetrofit 版本
     * @return DevRetrofit versionName
     */
    fun getDevRetrofitVersion(): String {
        return BuildConfig.DevRetrofit_Version
    }
}