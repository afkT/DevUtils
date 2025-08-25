package dev.simple.mvvm.utils.image

import dev.base.DevVariableExt
import dev.engine.core.image.ImageConfig
import dev.simple.mvvm.utils.hi.hiif.hiIfNotNullWith

// ================================
// = dev.engine.image.ImageConfig =
// ================================

/**
 * detail: App ImageConfig 缓存类
 * @author Ttt
 */
object AppImageConfig {

    private val AUTO_CONFIG =
        AppAutoImageConfig(object : DevVariableExt.Creator<String, ImageConfig, ImageConfig> {
            override fun create(
                key: String?,
                param: ImageConfig?
            ): ImageConfig? {
                return innerCreator.hiIfNotNullWith({
                    return@hiIfNotNullWith it.create(key, param)
                }, {
                    return@hiIfNotNullWith param
                })
            }
        }, ImageConfig.create())

    // ImageConfig 内部创建器
    private var innerCreator: DevVariableExt.Creator<String, ImageConfig, ImageConfig>? = null

    // =

    /**
     * 设置 ImageConfig 创建器
     * @param creator Creator<String, ImageConfig, ImageConfig>
     */
    fun setCreator(creator: DevVariableExt.Creator<String, ImageConfig, ImageConfig>) {
        innerCreator = creator
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取变量操作基类扩展类
     * @return DevVariableExt<String, ImageConfig, String>
     */
    fun varExt(): DevVariableExt<String, ImageConfig, ImageConfig> {
        return AUTO_CONFIG.varExt()
    }

    /**
     * 通过 Key 获取 ImageConfig
     * @param key 对应 ImageConfig Key
     * @return [ImageConfig]
     */
    fun toImageConfig(key: String?): ImageConfig? {
        return AUTO_CONFIG.toImageConfig(key)
    }

    /**
     * 通过 Key 获取 ImageConfig
     * @param key 对应 ImageConfig Key
     * @param defaultValue 默认 ImageConfig
     * @return [ImageConfig]
     */
    fun toImageConfig(
        key: String?,
        defaultValue: ImageConfig?
    ): ImageConfig? {
        return AUTO_CONFIG.toImageConfig(key, defaultValue)
    }

    // =

    /**
     * 获取 ImageConfig 默认值
     * @return [ImageConfig]
     */
    fun defaultValue(): ImageConfig? {
        return AUTO_CONFIG.defaultValue()
    }

    /**
     * 设置 ImageConfig 默认值
     * @param value 默认 ImageConfig
     * @return AppAutoImageConfig
     */
    fun setDefaultValue(value: ImageConfig?): AppAutoImageConfig {
        return AUTO_CONFIG.setDefaultValue(value)
    }
}

// ==========
// = 扩展函数 =
// ==========

/**
 * 通过 Key 获取 ImageConfig
 * @return [ImageConfig]
 */
fun String?.toImageConfig(): ImageConfig? {
    return AppImageConfig.toImageConfig(this)
}

/**
 * 通过 Key 获取 ImageConfig
 * @param defaultValue 默认 ImageConfig
 * @return [ImageConfig]
 */
fun String?.toImageConfig(defaultValue: ImageConfig?): ImageConfig? {
    return AppImageConfig.toImageConfig(this, defaultValue)
}