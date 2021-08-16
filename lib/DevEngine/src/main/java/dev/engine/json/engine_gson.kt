package dev.engine.json

import java.lang.reflect.Type

/**
 * detail: Gson JSON Engine 实现
 * @author Ttt
 */
class GsonEngineImpl : IJSONEngine<JSONConfig> {

    // ==========
    // = 转换方法 =
    // ==========

    override fun toJson(obj: Any?): String? {
        return GsonUtils.toJson(obj)
    }

    override fun toJson(
        obj: Any?,
        config: JSONConfig?
    ): String? {
        return GsonUtils.toJson(obj, config?.gson)
    }

    override fun <T : Any?> fromJson(
        json: String?,
        classOfT: Class<T>?
    ): T? {
        return GsonUtils.fromJson(json, classOfT)
    }

    override fun <T : Any?> fromJson(
        json: String?,
        classOfT: Class<T>?,
        config: JSONConfig?
    ): T? {
        return GsonUtils.fromJson(json, classOfT, config?.gson)
    }

    override fun <T : Any?> fromJson(
        json: String?,
        typeOfT: Type?
    ): T? {
        return GsonUtils.fromJson(json, typeOfT)
    }

    override fun <T : Any?> fromJson(
        json: String?,
        typeOfT: Type?,
        config: JSONConfig?
    ): T? {
        return GsonUtils.fromJson(json, typeOfT, config?.gson)
    }

    // ==========
    // = 其他方法 =
    // ==========

    override fun isJSON(json: String?): Boolean {
        return GsonUtils.isJSON(json)
    }

    override fun isJSONObject(json: String?): Boolean {
        return GsonUtils.isJSONObject(json)
    }

    override fun isJSONArray(json: String?): Boolean {
        return GsonUtils.isJSONArray(json)
    }

    override fun toJsonIndent(json: String?): String? {
        return GsonUtils.toJsonIndent(json)
    }

    override fun toJsonIndent(
        json: String?,
        config: JSONConfig?
    ): String? {
        return GsonUtils.toJsonIndent(json, config?.gson)
    }

    override fun toJsonIndent(obj: Any?): String? {
        return GsonUtils.toJsonIndent(obj)
    }

    override fun toJsonIndent(
        obj: Any?,
        config: JSONConfig?
    ): String? {
        return GsonUtils.toJsonIndent(obj, config?.gson)
    }
}