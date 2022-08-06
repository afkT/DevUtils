package dev.expand.engine.keyvalue

import dev.engine.DevEngine
import dev.engine.keyvalue.IKeyValueEngine
import dev.utils.DevFinal
import java.lang.reflect.Type

// =================================
// = IKeyValueEngine<EngineConfig> =
// =================================

/**
 * 通过 Key 获取 KeyValue Engine
 * @param engine String?
 * @return IKeyValueEngine<EngineConfig>
 * 内部做了处理如果匹配不到则返回默认 KeyValue Engine
 */
fun String?.getEngine(): IKeyValueEngine<in IKeyValueEngine.EngineConfig>? {
    DevEngine.getKeyValue(this)?.let { value ->
        return value
    }
    return DevEngine.getKeyValue()
}

// ========
// = 默认值 =
// ========

private const val INTEGER_DEFAULT: Int = DevFinal.DEFAULT.INT
private const val LONG_DEFAULT: Long = DevFinal.DEFAULT.LONG
private const val FLOAT_DEFAULT: Float = DevFinal.DEFAULT.FLOAT
private const val DOUBLE_DEFAULT: Double = DevFinal.DEFAULT.DOUBLE
private const val BOOLEAN_DEFAULT: Boolean = DevFinal.DEFAULT.BOOLEAN

// =======================
// = Key KeyValue Engine =
// =======================

// =============
// = 对外公开方法 =
// =============

fun <Config : IKeyValueEngine.EngineConfig> kv_getConfig(
    engine: String? = null
): Config? {
    return engine.getEngine()?.config as? Config
}

// =

fun String.kv_remove(
    engine: String? = null
) {
    engine.getEngine()?.remove(this)
}

fun kv_removeForKeys(
    engine: String? = null,
    keys: Array<out String>?
) {
    engine.getEngine()?.removeForKeys(keys)
}

fun String.kv_contains(
    engine: String? = null
): Boolean {
    return engine.getEngine()?.contains(this) ?: false
}

fun kv_clear(
    engine: String? = null
) {
    engine.getEngine()?.clear()
}

// =======
// = 存储 =
// =======

fun String.kv_putInt(
    engine: String? = null,
    value: Int
): Boolean {
    return engine.getEngine()?.putInt(this, value) ?: false
}

fun String.kv_putLong(
    engine: String? = null,
    value: Long
): Boolean {
    return engine.getEngine()?.putLong(this, value) ?: false
}

fun String.kv_putFloat(
    engine: String? = null,
    value: Float
): Boolean {
    return engine.getEngine()?.putFloat(this, value) ?: false
}

fun String.kv_putDouble(
    engine: String? = null,
    value: Double
): Boolean {
    return engine.getEngine()?.putDouble(this, value) ?: false
}

fun String.kv_putBoolean(
    engine: String? = null,
    value: Boolean
): Boolean {
    return engine.getEngine()?.putBoolean(this, value) ?: false
}

fun String.kv_putString(
    engine: String? = null,
    value: String?
): Boolean {
    return engine.getEngine()?.putString(this, value) ?: false
}

fun <T : Any> String.kv_putEntity(
    engine: String? = null,
    value: T
): Boolean {
    return engine.getEngine()?.putEntity(this, value) ?: false
}

// =======
// = 获取 =
// =======

fun String.kv_getInt(
    engine: String? = null
): Int {
    return engine.getEngine()?.getInt(this) ?: INTEGER_DEFAULT
}

fun String.kv_getLong(
    engine: String? = null
): Long {
    return engine.getEngine()?.getLong(this) ?: LONG_DEFAULT
}

fun String.kv_getFloat(
    engine: String? = null
): Float {
    return engine.getEngine()?.getFloat(this) ?: FLOAT_DEFAULT
}

fun String.kv_getDouble(
    engine: String? = null
): Double {
    return engine.getEngine()?.getDouble(this) ?: DOUBLE_DEFAULT
}

fun String.kv_getBoolean(
    engine: String? = null
): Boolean {
    return engine.getEngine()?.getBoolean(this) ?: BOOLEAN_DEFAULT
}

fun String.kv_getString(
    engine: String? = null
): String? {
    return engine.getEngine()?.getString(this)
}

fun <T : Any> String.kv_getEntity(
    engine: String? = null,
    typeOfT: Type?
): T? {
    return engine.getEngine()?.getEntity(this, typeOfT) as? T
}

// =================
// = 获取 ( 默认值 ) =
// =================

fun String.kv_getInt(
    engine: String? = null,
    defaultValue: Int
): Int {
    return engine.getEngine()?.getInt(this, defaultValue) ?: defaultValue
}

fun String.kv_getLong(
    engine: String? = null,
    defaultValue: Long
): Long {
    return engine.getEngine()?.getLong(this, defaultValue) ?: defaultValue
}

fun String.kv_getFloat(
    engine: String? = null,
    defaultValue: Float
): Float {
    return engine.getEngine()?.getFloat(this, defaultValue) ?: defaultValue
}

fun String.kv_getDouble(
    engine: String? = null,
    defaultValue: Double
): Double {
    return engine.getEngine()?.getDouble(this, defaultValue) ?: defaultValue
}

fun String.kv_getBoolean(
    engine: String? = null,
    defaultValue: Boolean
): Boolean {
    return engine.getEngine()?.getBoolean(this, defaultValue) ?: defaultValue
}

fun String.kv_getString(
    engine: String? = null,
    defaultValue: String?
): String? {
    return engine.getEngine()?.getString(this, defaultValue)
}

fun <T : Any> String.kv_getEntity(
    engine: String? = null,
    typeOfT: Type?,
    defaultValue: T?
): T? {
    return engine.getEngine()?.getEntity(this, typeOfT, defaultValue)
}