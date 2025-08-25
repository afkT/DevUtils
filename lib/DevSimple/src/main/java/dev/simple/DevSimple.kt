package dev.simple

import dev.base.DevVariableExt
import dev.engine.core.image.ImageConfig
import dev.simple.mvvm.base.Config
import dev.simple.mvvm.utils.image.AppImageConfig

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
 * DevDeprecated Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevDeprecated/README.md
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

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 开启日志开关
     */
    fun openLog(): DevSimple {
        Config.openLog()
        return this
    }

    /**
     * 设置默认点击时间间隔
     * @param intervalTime 双击时间间隔
     */
    fun setIntervalTime(intervalTime: Long): DevSimple {
        Config.setIntervalTime(intervalTime)
        return this
    }

    /**
     * 设置 ImageConfig 创建器
     * @param creator Creator<String, ImageConfig, ImageConfig>
     */
    fun setImageCreator(creator: DevVariableExt.Creator<String, ImageConfig, ImageConfig>): DevSimple {
        AppImageConfig.setCreator(creator)
        return this
    }
}