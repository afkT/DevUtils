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
 * @receiver String?
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

/**
 * 获取 Key-Value Engine Config
 * @param engine String?
 * @return Key-Value Config
 */
fun kv_getConfig(
    engine: String? = null
): Any? {
    return engine.getKeyValueEngine()?.config
}

// =

/**
 * 移除数据
 * @receiver 保存的 key
 * @param engine String?
 */
fun String.kv_remove(
    engine: String? = null
) {
    engine.getKeyValueEngine()?.remove(this)
}

/**
 * 移除数组的数据
 * @receiver 保存的 key 数组
 * @param engine String?
 */
fun Array<out String>?.kv_removeForKeys(
    engine: String? = null
) {
    engine.getKeyValueEngine()?.removeForKeys(this)
}

/**
 * 是否存在 key
 * @receiver 保存的 key
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun String.kv_contains(
    engine: String? = null
): Boolean {
    return engine.getKeyValueEngine()?.contains(this) ?: false
}

/**
 * 清除全部数据
 * @param engine String?
 */
fun kv_clear(
    engine: String? = null
) {
    engine.getKeyValueEngine()?.clear()
}

// =======
// = 存储 =
// =======

/**
 * 保存 int 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @return `true` success, `false` fail
 */
fun String.kv_putInt(
    engine: String? = null,
    value: Int
): Boolean {
    return engine.getKeyValueEngine()?.putInt(this, value) ?: false
}

/**
 * 保存 long 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @return `true` success, `false` fail
 */
fun String.kv_putLong(
    engine: String? = null,
    value: Long
): Boolean {
    return engine.getKeyValueEngine()?.putLong(this, value) ?: false
}

/**
 * 保存 float 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @return `true` success, `false` fail
 */
fun String.kv_putFloat(
    engine: String? = null,
    value: Float
): Boolean {
    return engine.getKeyValueEngine()?.putFloat(this, value) ?: false
}

/**
 * 保存 double 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @return `true` success, `false` fail
 */
fun String.kv_putDouble(
    engine: String? = null,
    value: Double
): Boolean {
    return engine.getKeyValueEngine()?.putDouble(this, value) ?: false
}

/**
 * 保存 boolean 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @return `true` success, `false` fail
 */
fun String.kv_putBoolean(
    engine: String? = null,
    value: Boolean
): Boolean {
    return engine.getKeyValueEngine()?.putBoolean(this, value) ?: false
}

/**
 * 保存 String 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @return `true` success, `false` fail
 */
fun String.kv_putString(
    engine: String? = null,
    value: String?
): Boolean {
    return engine.getKeyValueEngine()?.putString(this, value) ?: false
}

/**
 * 保存指定类型对象
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @return `true` success, `false` fail
 */
fun <T : Any> String.kv_putEntity(
    engine: String? = null,
    value: T
): Boolean {
    return engine.getKeyValueEngine()?.putEntity(this, value) ?: false
}

// =======
// = 获取 =
// =======

/**
 * 获取 int 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.kv_getInt(
    engine: String? = null
): Int {
    return engine.getKeyValueEngine()?.getInt(this) ?: INTEGER_DEFAULT
}

/**
 * 获取 long 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.kv_getLong(
    engine: String? = null
): Long {
    return engine.getKeyValueEngine()?.getLong(this) ?: LONG_DEFAULT
}

/**
 * 获取 float 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.kv_getFloat(
    engine: String? = null
): Float {
    return engine.getKeyValueEngine()?.getFloat(this) ?: FLOAT_DEFAULT
}

/**
 * 获取 double 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.kv_getDouble(
    engine: String? = null
): Double {
    return engine.getKeyValueEngine()?.getDouble(this) ?: DOUBLE_DEFAULT
}

/**
 * 获取 boolean 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.kv_getBoolean(
    engine: String? = null
): Boolean {
    return engine.getKeyValueEngine()?.getBoolean(this) ?: BOOLEAN_DEFAULT
}

/**
 * 获取 String 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.kv_getString(
    engine: String? = null
): String? {
    return engine.getKeyValueEngine()?.getString(this)
}

/**
 * 获取指定类型对象
 * @receiver 保存的 key
 * @param engine String?
 * @param typeOfT Type T
 * @return instance of type
 */
fun <T : Any> String.kv_getEntity(
    engine: String? = null,
    typeOfT: Type?
): T? {
    return engine.getKeyValueEngine()?.getEntity(this, typeOfT) as? T
}

// =================
// = 获取 ( 默认值 ) =
// =================

/**
 * 获取 int 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.kv_getInt(
    engine: String? = null,
    defaultValue: Int
): Int {
    return engine.getKeyValueEngine()?.getInt(this, defaultValue) ?: defaultValue
}

/**
 * 获取 long 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.kv_getLong(
    engine: String? = null,
    defaultValue: Long
): Long {
    return engine.getKeyValueEngine()?.getLong(this, defaultValue) ?: defaultValue
}

/**
 * 获取 float 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.kv_getFloat(
    engine: String? = null,
    defaultValue: Float
): Float {
    return engine.getKeyValueEngine()?.getFloat(this, defaultValue) ?: defaultValue
}

/**
 * 获取 double 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.kv_getDouble(
    engine: String? = null,
    defaultValue: Double
): Double {
    return engine.getKeyValueEngine()?.getDouble(this, defaultValue) ?: defaultValue
}

/**
 * 获取 boolean 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.kv_getBoolean(
    engine: String? = null,
    defaultValue: Boolean
): Boolean {
    return engine.getKeyValueEngine()?.getBoolean(this, defaultValue) ?: defaultValue
}

/**
 * 获取 String 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.kv_getString(
    engine: String? = null,
    defaultValue: String?
): String? {
    return engine.getKeyValueEngine()?.getString(this, defaultValue)
}

/**
 * 获取指定类型对象
 * @receiver 保存的 key
 * @param engine String?
 * @param typeOfT Type T
 * @param defaultValue 默认值
 * @return instance of type
 */
fun <T : Any> String.kv_getEntity(
    engine: String? = null,
    typeOfT: Type?,
    defaultValue: T?
): T? {
    return engine.getKeyValueEngine()?.getEntity(this, typeOfT, defaultValue)
}