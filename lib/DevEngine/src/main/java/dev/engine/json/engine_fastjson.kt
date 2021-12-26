package dev.engine.json

import java.lang.reflect.Type

/**
 * detail: Fastjson JSON Engine 实现
 * @author Ttt
 */
class FastjsonEngineImpl : IJSONEngine<JSONConfig> {

    // ==========
    // = 转换方法 =
    // ==========

    override fun toJson(obj: Any?): String? {
        return FastjsonUtils.toJson(obj)
    }

    override fun toJson(
        obj: Any?,
        config: JSONConfig?
    ): String? {
        return FastjsonUtils.toJson(obj)
    }

    override fun <T : Any> fromJson(
        json: String?,
        classOfT: Class<T>?
    ): T? {
        return FastjsonUtils.fromJson(json, classOfT)
    }

    override fun <T : Any> fromJson(
        json: String?,
        classOfT: Class<T>?,
        config: JSONConfig?
    ): T? {
        return FastjsonUtils.fromJson(json, classOfT)
    }

    override fun <T : Any> fromJson(
        json: String?,
        typeOfT: Type?
    ): T? {
        return FastjsonUtils.fromJson(json, typeOfT)
    }

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