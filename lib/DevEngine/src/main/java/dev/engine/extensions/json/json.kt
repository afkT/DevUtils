package dev.engine.extensions.json

import dev.engine.DevEngine
import dev.engine.json.IJSONEngine
import java.lang.reflect.Type

// =============================
// = IJSONEngine<EngineConfig> =
// =============================

/**
 * 通过 Key 获取 JSON Engine
 * @receiver String?
 * @return IJSONEngine<EngineConfig>
 * 内部做了处理如果匹配不到则返回默认 JSON Engine
 */
fun String?.getJSONEngine(): IJSONEngine<in IJSONEngine.EngineConfig>? {
    DevEngine.getJSON(this)?.let { value ->
        return value
    }
    return DevEngine.getJSON()
}

// ===================
// = Key JSON Engine =
// ===================

// ==========
// = 转换方法 =
// ==========

/**
 * 将对象转换为 JSON String
 * @receiver 对象
 * @param engine String?
 * @return JSON String
 */
fun Any.toJson(
    engine: String? = null
): String? {
    return engine.getJSONEngine()?.toJson(this)
}

/**
 * 将对象转换为 JSON String
 * @receiver 对象
 * @param engine String?
 * @param config 配置信息
 * @return JSON String
 */
fun <Config : IJSONEngine.EngineConfig> Any.toJson(
    engine: String? = null,
    config: Config?
): String? {
    return engine.getJSONEngine()?.toJson(this, config)
}

// =

/**
 * 将 JSON String 映射为指定类型对象
 * @receiver JSON String
 * @param engine String?
 * @param classOfT Class T
 * @return instance of type
 */
fun <T : Any> String.fromJson(
    engine: String? = null,
    classOfT: Class<T>?
): T? {
    return engine.getJSONEngine()?.fromJson(this, classOfT)
}

/**
 * 将 JSON String 映射为指定类型对象
 * @receiver JSON String
 * @param engine String?
 * @param classOfT Class T
 * @param config 配置信息
 * @return instance of type
 */
fun <T : Any, Config : IJSONEngine.EngineConfig> String.fromJson(
    engine: String? = null,
    classOfT: Class<T>?,
    config: Config?
): T? {
    return engine.getJSONEngine()?.fromJson(this, classOfT, config)
}

// =

/**
 * 将 JSON String 映射为指定类型对象
 * @receiver JSON String
 * @param engine String?
 * @param typeOfT Type T
 * @return instance of type
 */
fun <T : Any> String.fromJson(
    engine: String? = null,
    typeOfT: Type?
): T? {
    return engine.getJSONEngine()?.fromJson(this, typeOfT)
}

/**
 * 将 JSON String 映射为指定类型对象
 * @receiver JSON String
 * @param engine String?
 * @param typeOfT Type T
 * @param config 配置信息
 * @return instance of type
 */
fun <T : Any, Config : IJSONEngine.EngineConfig> String.fromJson(
    engine: String? = null,
    typeOfT: Type?,
    config: Config?
): T? {
    return engine.getJSONEngine()?.fromJson(this, typeOfT, config)
}

// ==========
// = 其他方法 =
// ==========

/**
 * 判断字符串是否 JSON 格式
 * @receiver 待校验 JSON String
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun String.isJSON(
    engine: String? = null
): Boolean {
    return engine.getJSONEngine()?.isJSON(this) ?: false
}

/**
 * 判断字符串是否 JSON Object 格式
 * @receiver 待校验 JSON String
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun String.isJSONObject(
    engine: String? = null
): Boolean {
    return engine.getJSONEngine()?.isJSONObject(this) ?: false
}

/**
 * 判断字符串是否 JSON Array 格式
 * @receiver 待校验 JSON String
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun String.isJSONArray(
    engine: String? = null
): Boolean {
    return engine.getJSONEngine()?.isJSONArray(this) ?: false
}

// =

/**
 * JSON String 缩进处理
 * @receiver JSON String
 * @param engine String?
 * @return JSON String
 */
fun String.toJsonIndent(
    engine: String? = null
): String? {
    return engine.getJSONEngine()?.toJsonIndent(this)
}

/**
 * JSON String 缩进处理
 * @receiver JSON String
 * @param engine String?
 * @param config 配置信息
 * @return JSON String
 */
fun <Config : IJSONEngine.EngineConfig> String.toJsonIndent(
    engine: String? = null,
    config: Config?
): String? {
    return engine.getJSONEngine()?.toJsonIndent(this, config)
}

/**
 * Object 转 JSON String 并进行缩进处理
 * @receiver 对象
 * @param engine String?
 * @return JSON String
 */
fun Any.toJsonIndent(
    engine: String? = null
): String? {
    return engine.getJSONEngine()?.toJsonIndent(this)
}

/**
 * Object 转 JSON String 并进行缩进处理
 * @receiver 对象
 * @param engine String?
 * @param config 配置信息
 * @return JSON String
 */
fun <Config : IJSONEngine.EngineConfig> Any.toJsonIndent(
    engine: String? = null,
    config: Config?
): String? {
    return engine.getJSONEngine()?.toJsonIndent(this, config)
}