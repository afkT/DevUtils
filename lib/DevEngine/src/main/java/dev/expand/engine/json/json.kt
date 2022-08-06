package dev.expand.engine.json

import dev.engine.DevEngine
import dev.engine.json.IJSONEngine
import java.lang.reflect.Type

// =============================
// = IJSONEngine<EngineConfig> =
// =============================

/**
 * 通过 Key 获取 JSON Engine
 * @param engine String?
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

fun Any.toJson(
    engine: String? = null
): String? {
    return engine.getJSONEngine()?.toJson(this)
}

fun <Config : IJSONEngine.EngineConfig> Any.toJson(
    engine: String? = null,
    config: Config?
): String? {
    return engine.getJSONEngine()?.toJson(this, config)
}

// =

fun <T : Any> String.fromJson(
    engine: String? = null,
    classOfT: Class<T>?
): T? {
    return engine.getJSONEngine()?.fromJson(this, classOfT)
}

fun <T : Any, Config : IJSONEngine.EngineConfig> String.fromJson(
    engine: String? = null,
    classOfT: Class<T>?,
    config: Config?
): T? {
    return engine.getJSONEngine()?.fromJson(this, classOfT, config)
}

// =

fun <T : Any> String.fromJson(
    engine: String? = null,
    typeOfT: Type?
): T? {
    return engine.getJSONEngine()?.fromJson(this, typeOfT)
}

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

fun String.isJSON(
    engine: String? = null
): Boolean {
    return engine.getJSONEngine()?.isJSON(this) ?: false
}

fun String.isJSONObject(
    engine: String? = null
): Boolean {
    return engine.getJSONEngine()?.isJSONObject(this) ?: false
}

fun String.isJSONArray(
    engine: String? = null
): Boolean {
    return engine.getJSONEngine()?.isJSONArray(this) ?: false
}

// =

fun String.toJsonIndent(
    engine: String? = null
): String? {
    return engine.getJSONEngine()?.toJsonIndent(this)
}

fun <Config : IJSONEngine.EngineConfig> String.toJsonIndent(
    engine: String? = null,
    config: Config?
): String? {
    return engine.getJSONEngine()?.toJsonIndent(this, config)
}

fun Any.toJsonIndent(
    engine: String? = null
): String? {
    return engine.getJSONEngine()?.toJsonIndent(this)
}

fun <Config : IJSONEngine.EngineConfig> Any.toJsonIndent(
    engine: String? = null,
    config: Config?
): String? {
    return engine.getJSONEngine()?.toJsonIndent(this, config)
}