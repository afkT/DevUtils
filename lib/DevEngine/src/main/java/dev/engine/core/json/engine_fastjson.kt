package dev.engine.core.json

import dev.engine.json.IJSONEngine
import java.lang.reflect.Type

/**
 * detail: Fastjson JSON Engine 实现
 * @author Ttt
 */
open class FastjsonEngineImpl : IJSONEngine<JSONConfig> {

    // ==========
    // = 转换方法 =
    // ==========

    /**
     * 将对象转换为 JSON String
     * @param obj 对象
     * @return JSON String
     */
    override fun toJson(obj: Any?): String? {
        return FastjsonUtils.toJson(obj)
    }

    /**
     * 将对象转换为 JSON String
     * @param obj 对象
     * @param config 配置信息
     * @return JSON String
     */
    override fun toJson(
        obj: Any?,
        config: JSONConfig?
    ): String? {
        return FastjsonUtils.toJson(obj)
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json JSON String
     * @param classOfT Class T
     * @return instance of type
     */
    override fun <T : Any> fromJson(
        json: String?,
        classOfT: Class<T>?
    ): T? {
        return FastjsonUtils.fromJson(json, classOfT)
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json JSON String
     * @param classOfT Class T
     * @param config 配置信息
     * @return instance of type
     */
    override fun <T : Any> fromJson(
        json: String?,
        classOfT: Class<T>?,
        config: JSONConfig?
    ): T? {
        return FastjsonUtils.fromJson(json, classOfT)
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json JSON String
     * @param typeOfT Type T
     * @return instance of type
     */
    override fun <T : Any> fromJson(
        json: String?,
        typeOfT: Type?
    ): T? {
        return FastjsonUtils.fromJson(json, typeOfT)
    }

    /**
     * 将 JSON String 映射为指定类型对象
     * @param json JSON String
     * @param typeOfT Type T
     * @param config 配置信息
     * @return instance of type
     */
    override fun <T : Any> fromJson(
        json: String?,
        typeOfT: Type?,
        config: JSONConfig?
    ): T? {
        return FastjsonUtils.fromJson(json, typeOfT)
    }

    // ==========
    // = 其他方法 =
    // ==========

    override fun isJSON(json: String?): Boolean {
        return FastjsonUtils.isJSON(json)
    }

    override fun isJSONObject(json: String?): Boolean {
        return FastjsonUtils.isJSONObject(json)
    }

    override fun isJSONArray(json: String?): Boolean {
        return FastjsonUtils.isJSONArray(json)
    }

    override fun toJsonIndent(json: String?): String? {
        return FastjsonUtils.toJsonIndent(json)
    }

    override fun toJsonIndent(
        json: String?,
        config: JSONConfig?
    ): String? {
        return FastjsonUtils.toJsonIndent(json)
    }

    override fun toJsonIndent(obj: Any?): String? {
        return FastjsonUtils.toJsonIndent(obj)
    }

    override fun toJsonIndent(
        obj: Any?,
        config: JSONConfig?
    ): String? {
        return FastjsonUtils.toJsonIndent(obj)
    }
}