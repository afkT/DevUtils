package dev.engine.extensions.keyvalue

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
fun String?.getKeyValueEngine(): IKeyValueEngine<in IKeyValueEngine.EngineConfig>? {
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

fun kv_getConfig(
    engine: String? = null
): Any? {
    return engine.getKeyValueEngine()?.config
}

// =

fun String.kv_remove(
    engine: String? = null
) {
    engine.getKeyValueEngine()?.remove(this)
}

fun kv_removeForKeys(
    engine: String? = null,
    keys: Array<out String>?
) {
    engine.getKeyValueEngine()?.removeForKeys(keys)
}

fun String.kv_contains(
    engine: String? = null
): Boolean {
    return engine.getKeyValueEngine()?.contains(this) ?: false
}

fun kv_clear(
    engine: String? = null
) {
    engine.getKeyValueEngine()?.clear()
}

// =======
// = 存储 =
// =======

fun String.kv_putInt(
    engine: String? = null,
    value: Int
): Boolean {
    return engine.getKeyValueEngine()?.putInt(this, value) ?: false
}

fun String.kv_putLong(
    engine: String? = null,
    value: Long
): Boolean {
    return engine.getKeyValueEngine()?.putLong(this, value) ?: false
}

fun String.kv_putFloat(
    engine: String? = null,
    value: Float
): Boolean {
    return engine.getKeyValueEngine()?.putFloat(this, value) ?: false
}

fun String.kv_putDouble(
    engine: String? = null,
    value: Double
): Boolean {
    return engine.getKeyValueEngine()?.putDouble(this, value) ?: false
}

fun String.kv_putBoolean(
    engine: String? = null,
    value: Boolean
): Boolean {
    return engine.getKeyValueEngine()?.putBoolean(this, value) ?: false
}

fun String.kv_putString(
    engine: String? = null,
    value: String?
): Boolean {
    return engine.getKeyValueEngine()?.putString(this, value) ?: false
}

fun <T : Any> String.kv_putEntity(
    engine: String? = null,
    value: T
): Boolean {
    return engine.getKeyValueEngine()?.putEntity(this, value) ?: false
}

// =======
// = 获取 =
// =======

fun String.kv_getInt(
    engine: String? = null
): Int {
    return engine.getKeyValueEngine()?.getInt(this) ?: INTEGER_DEFAULT
}

fun String.kv_getLong(
    engine: String? = null
): Long {
    return engine.getKeyValueEngine()?.getLong(this) ?: LONG_DEFAULT
}

fun String.kv_getFloat(
    engine: String? = null
): Float {
    return engine.getKeyValueEngine()?.getFloat(this) ?: FLOAT_DEFAULT
}

fun String.kv_getDouble(
    engine: String? = null
): Double {
    return engine.getKeyValueEngine()?.getDouble(this) ?: DOUBLE_DEFAULT
}

fun String.kv_getBoolean(
    engine: String? = null
): Boolean {
    return engine.getKeyValueEngine()?.getBoolean(this) ?: BOOLEAN_DEFAULT
}

fun String.kv_getString(
    engine: String? = null
): String? {
    return engine.getKeyValueEngine()?.getString(this)
}

fun <T : Any> String.kv_getEntity(
    engine: String? = null,
    typeOfT: Type?
): T? {
    return engine.getKeyValueEngine()?.getEntity(this, typeOfT) as? T
}

// =================
// = 获取 ( 默认值 ) =
// =================

fun String.kv_getInt(
    engine: String? = null,
    defaultValue: Int
): Int {
    return engine.getKeyValueEngine()?.getInt(this, defaultValue) ?: defaultValue
}

fun String.kv_getLong(
    engine: String? = null,
    defaultValue: Long
): Long {
    return engine.getKeyValueEngine()?.getLong(this, defaultValue) ?: defaultValue
}

fun String.kv_getFloat(
    engine: String? = null,
    defaultValue: Float
): Float {
    return engine.getKeyValueEngine()?.getFloat(this, defaultValue) ?: defaultValue
}

fun String.kv_getDouble(
    engine: String? = null,
    defaultValue: Double
): Double {
    return engine.getKeyValueEngine()?.getDouble(this, defaultValue) ?: defaultValue
}

fun String.kv_getBoolean(
    engine: String? = null,
    defaultValue: Boolean
): Boolean {
    return engine.getKeyValueEngine()?.getBoolean(this, defaultValue) ?: defaultValue
}

fun String.kv_getString(
    engine: String? = null,
    defaultValue: String?
): String? {
    return engine.getKeyValueEngine()?.getString(this, defaultValue)
}

fun <T : Any> String.kv_getEntity(
    engine: String? = null,
    typeOfT: Type?,
    defaultValue: T?
): T? {
    return engine.getKeyValueEngine()?.getEntity(this, typeOfT, defaultValue)
}