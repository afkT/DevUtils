package dev.engine.extensions.cache

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Parcelable
import dev.engine.DevEngine
import dev.engine.cache.ICacheEngine
import dev.utils.DevFinal
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable
import java.lang.reflect.Type

// ==========================================
// = ICacheEngine<EngineConfig, EngineItem> =
// ==========================================

/**
 * 通过 Key 获取 Cache Engine
 * @receiver String?
 * @return ICacheEngine<EngineConfig, EngineItem>
 * 内部做了处理如果匹配不到则返回默认 Cache Engine
 */
fun String?.getCacheEngine(): ICacheEngine<in ICacheEngine.EngineConfig, in ICacheEngine.EngineItem>? {
    DevEngine.getCache(this)?.let { value ->
        return value
    }
    return DevEngine.getCache()
}

// ========
// = 默认值 =
// ========

private const val INTEGER_DEFAULT: Int = DevFinal.DEFAULT.INT
private const val LONG_DEFAULT: Long = DevFinal.DEFAULT.LONG
private const val FLOAT_DEFAULT: Float = DevFinal.DEFAULT.FLOAT
private const val DOUBLE_DEFAULT: Double = DevFinal.DEFAULT.DOUBLE
private const val BOOLEAN_DEFAULT: Boolean = DevFinal.DEFAULT.BOOLEAN

// ====================
// = Key Cache Engine =
// ====================

// =============
// = 对外公开方法 =
// =============

/**
 * 获取 Cache Engine Config
 * @param engine String?
 * @return Cache Config
 */
fun cache_getConfig(
    engine: String? = null
): Any? {
    return engine.getCacheEngine()?.config
}

// =

/**
 * 移除数据
 * @receiver 保存的 key
 * @param engine String?
 */
fun String.cache_remove(
    engine: String? = null
) {
    engine.getCacheEngine()?.remove(this)
}

/**
 * 移除数组的数据
 * @receiver 保存的 key 数组
 * @param engine String?
 */
fun Array<String?>.cache_removeForKeys(
    engine: String? = null
) {
    engine.getCacheEngine()?.removeForKeys(this)
}

/**
 * 是否存在 key
 * @receiver 保存的 key
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun String.cache_contains(
    engine: String? = null
): Boolean {
    return engine.getCacheEngine()?.contains(this) ?: false
}

/**
 * 判断某个 key 是否过期
 * <pre>
 *     如果不存在该 key 也返回过期
 * </pre>
 * @receiver 保存的 key
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun String.cache_isDue(
    engine: String? = null
): Boolean {
    return engine.getCacheEngine()?.isDue(this) ?: false
}

/**
 * 清除全部数据
 * @param engine String?
 */
fun cache_clear(
    engine: String? = null
) {
    engine.getCacheEngine()?.clear()
}

/**
 * 清除过期数据
 * @param engine String?
 */
fun cache_clearDue(
    engine: String? = null
) {
    engine.getCacheEngine()?.clearDue()
}

/**
 * 清除某个类型的全部数据
 * @param engine String?
 */
fun Int.cache_clearType(
    engine: String? = null
) {
    engine.getCacheEngine()?.clearType(this)
}

/**
 * 通过 Key 获取 Item
 * @receiver 保存的 key
 * @param engine String?
 * @return Item
 */
fun <Item : ICacheEngine.EngineItem> String.cache_getItemByKey(
    engine: String? = null
): Item? {
    return engine.getCacheEngine()?.getItemByKey(this) as? Item
}

/**
 * 获取有效 Key 集合
 * @param engine String?
 * @return 有效 Key 集合
 */
fun <Item : ICacheEngine.EngineItem> cache_getKeys(
    engine: String? = null
): List<Item?>? {
    return engine.getCacheEngine()?.keys as? List<Item?>
}

/**
 * 获取永久有效 Key 集合
 * @param engine String?
 * @return 永久有效 Key 集合
 */
fun <Item : ICacheEngine.EngineItem> cache_getPermanentKeys(
    engine: String? = null
): List<Item?>? {
    return engine.getCacheEngine()?.permanentKeys as? List<Item?>
}

/**
 * 获取有效 Key 数量
 * @param engine String?
 * @return 有效 Key 数量
 */
fun cache_getCount(
    engine: String? = null
): Int {
    return engine.getCacheEngine()?.count ?: 0
}

/**
 * 获取有效 Key 占用总大小
 * @param engine String?
 * @return 有效 Key 占用总大小
 */
fun cache_getSize(
    engine: String? = null
): Long {
    return engine.getCacheEngine()?.size ?: 0L
}

// =======
// = 存储 =
// =======

/**
 * 保存 int 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
 * @return `true` success, `false` fail
 */
fun String.cache_put(
    engine: String? = null,
    value: Int,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

/**
 * 保存 long 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
 * @return `true` success, `false` fail
 */
fun String.cache_put(
    engine: String? = null,
    value: Long,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

/**
 * 保存 float 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
 * @return `true` success, `false` fail
 */
fun String.cache_put(
    engine: String? = null,
    value: Float,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

/**
 * 保存 double 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
 * @return `true` success, `false` fail
 */
fun String.cache_put(
    engine: String? = null,
    value: Double,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

/**
 * 保存 boolean 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
 * @return `true` success, `false` fail
 */
fun String.cache_put(
    engine: String? = null,
    value: Boolean,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

/**
 * 保存 String 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
 * @return `true` success, `false` fail
 */
fun String.cache_put(
    engine: String? = null,
    value: String?,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

/**
 * 保存 byte[] 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
 * @return `true` success, `false` fail
 */
fun String.cache_put(
    engine: String? = null,
    value: ByteArray?,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

/**
 * 保存 Bitmap 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
 * @return `true` success, `false` fail
 */
fun String.cache_put(
    engine: String? = null,
    value: Bitmap?,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

/**
 * 保存 Drawable 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
 * @return `true` success, `false` fail
 */
fun String.cache_put(
    engine: String? = null,
    value: Drawable?,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

/**
 * 保存 Serializable 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
 * @return `true` success, `false` fail
 */
fun String.cache_put(
    engine: String? = null,
    value: Serializable?,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

/**
 * 保存 Parcelable 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
 * @return `true` success, `false` fail
 */
fun String.cache_put(
    engine: String? = null,
    value: Parcelable?,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

/**
 * 保存 JSONObject 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
 * @return `true` success, `false` fail
 */
fun String.cache_put(
    engine: String? = null,
    value: JSONObject?,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

/**
 * 保存 JSONArray 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
 * @return `true` success, `false` fail
 */
fun String.cache_put(
    engine: String? = null,
    value: JSONArray?,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

/**
 * 保存指定类型对象
 * @receiver 保存的 key
 * @param engine String?
 * @param value 存储的数据
 * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
 * @return `true` success, `false` fail
 */
fun <T> String.cache_put(
    engine: String? = null,
    value: T,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
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
fun String.cache_getInt(
    engine: String? = null
): Int {
    return engine.getCacheEngine()?.getInt(this) ?: INTEGER_DEFAULT
}

/**
 * 获取 long 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.cache_getLong(
    engine: String? = null
): Long {
    return engine.getCacheEngine()?.getLong(this) ?: LONG_DEFAULT
}

/**
 * 获取 float 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.cache_getFloat(
    engine: String? = null
): Float {
    return engine.getCacheEngine()?.getFloat(this) ?: FLOAT_DEFAULT
}

/**
 * 获取 double 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.cache_getDouble(
    engine: String? = null
): Double {
    return engine.getCacheEngine()?.getDouble(this) ?: DOUBLE_DEFAULT
}

/**
 * 获取 boolean 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.cache_getBoolean(
    engine: String? = null
): Boolean {
    return engine.getCacheEngine()?.getBoolean(this) ?: BOOLEAN_DEFAULT
}

/**
 * 获取 String 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.cache_getString(
    engine: String? = null
): String? {
    return engine.getCacheEngine()?.getString(this)
}

/**
 * 获取 byte[] 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.cache_getBytes(
    engine: String? = null
): ByteArray? {
    return engine.getCacheEngine()?.getBytes(this)
}

/**
 * 获取 Bitmap 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.cache_getBitmap(
    engine: String? = null
): Bitmap? {
    return engine.getCacheEngine()?.getBitmap(this)
}

/**
 * 获取 Drawable 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.cache_getDrawable(
    engine: String? = null
): Drawable? {
    return engine.getCacheEngine()?.getDrawable(this)
}

/**
 * 获取 Serializable 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.cache_getSerializable(
    engine: String? = null
): Any? {
    return engine.getCacheEngine()?.getSerializable(this)
}

/**
 * 获取 Parcelable 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param creator Parcelable.Creator
 * @return 存储的数据
 */
fun <T> String.cache_getParcelable(
    engine: String? = null,
    creator: Parcelable.Creator<T>?
): T? {
    return engine.getCacheEngine()?.getParcelable(this, creator)
}

/**
 * 获取 JSONObject 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.cache_getJSONObject(
    engine: String? = null
): JSONObject? {
    return engine.getCacheEngine()?.getJSONObject(this)
}

/**
 * 获取 JSONArray 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @return 存储的数据
 */
fun String.cache_getJSONArray(
    engine: String? = null
): JSONArray? {
    return engine.getCacheEngine()?.getJSONArray(this)
}

/**
 * 获取指定类型对象
 * @receiver 保存的 key
 * @param engine String?
 * @param typeOfT Type T
 * @return instance of type
 */
fun <T> String.cache_getEntity(
    engine: String? = null,
    typeOfT: Type?
): T? {
    return engine.getCacheEngine()?.getEntity(this, typeOfT)
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
fun String.cache_getInt(
    engine: String? = null,
    defaultValue: Int
): Int {
    return engine.getCacheEngine()?.getInt(this, defaultValue) ?: defaultValue
}

/**
 * 获取 long 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.cache_getLong(
    engine: String? = null,
    defaultValue: Long
): Long {
    return engine.getCacheEngine()?.getLong(this, defaultValue) ?: defaultValue
}

/**
 * 获取 float 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.cache_getFloat(
    engine: String? = null,
    defaultValue: Float
): Float {
    return engine.getCacheEngine()?.getFloat(this, defaultValue) ?: defaultValue
}

/**
 * 获取 double 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.cache_getDouble(
    engine: String? = null,
    defaultValue: Double
): Double {
    return engine.getCacheEngine()?.getDouble(this, defaultValue) ?: defaultValue
}

/**
 * 获取 boolean 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.cache_getBoolean(
    engine: String? = null,
    defaultValue: Boolean
): Boolean {
    return engine.getCacheEngine()?.getBoolean(this, defaultValue) ?: defaultValue
}

/**
 * 获取 String 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.cache_getString(
    engine: String? = null,
    defaultValue: String?
): String? {
    return engine.getCacheEngine()?.getString(this, defaultValue)
}

/**
 * 获取 byte[] 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.cache_getBytes(
    engine: String? = null,
    defaultValue: ByteArray?
): ByteArray? {
    return engine.getCacheEngine()?.getBytes(this, defaultValue)
}

/**
 * 获取 Bitmap 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.cache_getBitmap(
    engine: String? = null,
    defaultValue: Bitmap?
): Bitmap? {
    return engine.getCacheEngine()?.getBitmap(this, defaultValue)
}

/**
 * 获取 Drawable 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.cache_getDrawable(
    engine: String? = null,
    defaultValue: Drawable?
): Drawable? {
    return engine.getCacheEngine()?.getDrawable(this, defaultValue)
}

/**
 * 获取 Serializable 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.cache_getSerializable(
    engine: String? = null,
    defaultValue: Any?
): Any? {
    return engine.getCacheEngine()?.getSerializable(this, defaultValue)
}

/**
 * 获取 Parcelable 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param creator Parcelable.Creator
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun <T> String.cache_getParcelable(
    engine: String? = null,
    creator: Parcelable.Creator<T>?,
    defaultValue: T?
): T? {
    return engine.getCacheEngine()?.getParcelable(this, creator, defaultValue)
}

/**
 * 获取 JSONObject 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.cache_getJSONObject(
    engine: String? = null,
    defaultValue: JSONObject?
): JSONObject? {
    return engine.getCacheEngine()?.getJSONObject(this, defaultValue)
}

/**
 * 获取 JSONArray 类型的数据
 * @receiver 保存的 key
 * @param engine String?
 * @param defaultValue 默认值
 * @return 存储的数据
 */
fun String.cache_getJSONArray(
    engine: String? = null,
    defaultValue: JSONArray?
): JSONArray? {
    return engine.getCacheEngine()?.getJSONArray(this, defaultValue)
}

/**
 * 获取指定类型对象
 * @receiver 保存的 key
 * @param engine String?
 * @param typeOfT Type T
 * @param defaultValue 默认值
 * @return instance of type
 */
fun <T> String.cache_getEntity(
    engine: String? = null,
    typeOfT: Type?,
    defaultValue: T?
): T? {
    return engine.getCacheEngine()?.getEntity(this, typeOfT, defaultValue)
}