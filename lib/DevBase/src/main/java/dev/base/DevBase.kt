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
     * 获取 DevBase 工具类版本
     * @return DevBase versionName
     */
    fun getDevBaseUtilsVersion(): String {
        return BuildConfig.VERSION_NAME
    }

    /**
     * 获取 DevBase 工具类版本号
     * @return DevBase versionCode
     */
    fun getDevBaseUtilsVersionCode(): Int {
        return BuildConfig.VERSION_CODE
    }
}