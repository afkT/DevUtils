package dev.simple.extensions.image

import dev.base.DevVariableExt
import dev.engine.core.image.ImageConfig

// ================================
// = dev.engine.image.ImageConfig =
// ================================

/**
 * detail: App ImageConfig 创建
 * @author Ttt
 */
open class AppAutoImageConfig(
    // 变量创建器
    creator: DevVariableExt.Creator<String, ImageConfig, ImageConfig>,
    // ImageConfig 默认值
    private var defaultValue: ImageConfig? = null
) {
    // 变量操作基类扩展类
    private val _devVariableExt = DevVariableExt(creator)

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取变量操作基类扩展类
     * @return DevVariableExt<String, ImageConfig, ImageConfig>
     */
    open fun devVariableExt(): DevVariableExt<String, ImageConfig, ImageConfig> {
        return _devVariableExt
    }

    /**
     * 通过 Key 获取 ImageConfig
     * @param key 对应 ImageConfig Key
     * @return [ImageConfig]
     */
    open fun toImageConfig(key: String?): ImageConfig? {
        return toImageConfig(key, defaultValue)
    }

    /**
     * 通过 Key 获取 ImageConfig
     * @param key 对应 ImageConfig Key
     * @param defaultValue 默认 ImageConfig
     * @return [ImageConfig]
     */
    open fun toImageConfig(
        key: String?,
        defaultValue: ImageConfig?
    ): ImageConfig? {
        if (key != null) {
            return _devVariableExt.getVariableValue(key, null) ?: defaultValue
        }
        return defaultValue
    }

    // =

    /**
     * 获取 ImageConfig 默认值
     * @return [ImageConfig]
     */
    open fun defaultValue(): ImageConfig? {
        return defaultValue
    }

    /**
     * 设置 ImageConfig 默认值
     * @param value 默认 ImageConfig
     * @return AppAutoImageConfig
     */
    open fun setDefaultValue(value: ImageConfig?): AppAutoImageConfig {
        this.defaultValue = value
        return this
    }
}