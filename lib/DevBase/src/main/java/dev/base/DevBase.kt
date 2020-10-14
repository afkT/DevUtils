package dev.base

/**
 * detail: DevBase
 * @author Ttt
 */
object DevBase {

    // =============
    // = 工具类版本 =
    // =============

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