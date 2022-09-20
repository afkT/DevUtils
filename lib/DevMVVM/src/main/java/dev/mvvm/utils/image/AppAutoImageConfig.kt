package dev.mvvm.utils.image

import dev.base.DevVariableExt
import dev.engine.image.ImageConfig

// ================================
// = dev.engine.image.ImageConfig =
// ================================

/**
 * detail: App ImageConfig 创建
 * @author Ttt
 */
class AppAutoImageConfig(
    // 变量创建器
    creator: DevVariableExt.Creator<String, ImageConfig, ImageConfig>,
    // ImageConfig 默认值
    private var defaultValue: ImageConfig? = null
) {
    // 变量操作基类扩展类
    private val varExt = DevVariableExt(creator)

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取变量操作基类扩展类
     * @return DevVariableExt<String, ImageConfig, String>
     */
    fun varExt(): DevVariableExt<String, ImageConfig, ImageConfig> {
        return varExt
    }

    /**
     * 通过字符串转换 ImageConfig
     * @param key 对应 ImageConfig Key
     * @return ImageConfig?
     */
    fun toImageConfig(key: String?): ImageConfig? {
        return toImageConfig(key, defaultValue)
    }

    /**
     * 通过字符串转换 ImageConfig
     * @param key 对应 ImageConfig Key
     * @param defaultValue 默认 ImageConfig
     * @return ImageConfig?
     */
    fun toImageConfig(
        key: String?,
        defaultValue: ImageConfig?
    ): ImageConfig? {
        if (key != null) {
            return varExt.getVariableValue(key, null) ?: defaultValue
        }
        return defaultValue
    }

    // =

    /**
     * 获取 ImageConfig 默认值
     * @return ImageConfig?
     */
    fun defaultValue(): ImageConfig? {
        return defaultValue
    }

    /**
     * 设置 ImageConfig 默认值
     * @param value 默认 ImageConfig
     * @return AppAutoImageConfig
     */
    fun setDefaultValue(value: ImageConfig?): AppAutoImageConfig {
        this.defaultValue = value
        return this
    }
}