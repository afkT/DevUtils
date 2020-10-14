package dev.base

import dev.base.mvvm.BuildConfig

/**
 * detail: DevBaseMVVM
 * @author Ttt
 */
object DevBaseMVVM {

    // =============
    // = 工具类版本 =
    // =============

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