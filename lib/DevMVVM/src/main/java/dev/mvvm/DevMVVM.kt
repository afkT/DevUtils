package dev.mvvm

import dev.base.DevVariableExt
import dev.engine.image.ImageConfig
import dev.mvvm.base.Config
import dev.mvvm.utils.image.AppImageConfig

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
 * DevSimple README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/DevSimple/README.md
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
     * 获取 DevEngine 版本号
     * @return DevEngine versionCode
     */
    fun getDevEngineVersionCode(): Int {
        return BuildConfig.DevEngine_VersionCode
    }

    /**
     * 获取 DevEngine 版本
     * @return DevEngine versionName
     */
    fun getDevEngineVersion(): String {
        return BuildConfig.DevEngine_Version
    }

    /**
     * 获取 DevWidget 版本号
     * @return DevWidget versionCode
     */
    fun getDevWidgetVersionCode(): Int {
        return BuildConfig.DevWidget_VersionCode
    }

    /**
     * 获取 DevWidget 版本
     * @return DevWidget versionName
     */
    fun getDevWidgetVersion(): String {
        return BuildConfig.DevWidget_Version
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 开启日志开关
     */
    fun openLog(): DevMVVM {
        Config.openLog()
        return this
    }

    /**
     * 设置默认点击时间间隔
     * @param intervalTime 双击时间间隔
     */
    fun setIntervalTime(intervalTime: Long): DevMVVM {
        Config.setIntervalTime(intervalTime)
        return this
    }

    /**
     * 设置 ImageConfig 创建器
     * @param creator Creator<String, ImageConfig, ImageConfig>
     */
    fun setImageCreator(creator: DevVariableExt.Creator<String, ImageConfig, ImageConfig>): DevMVVM {
        AppImageConfig.setCreator(creator)
        return this
    }
}