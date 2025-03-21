package dev.expand.engine.cache

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
 * @param engine String?
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

fun cache_getConfig(
    engine: String? = null
): Any? {
    return engine.getCacheEngine()?.config
}

// =

fun String.cache_remove(
    engine: String? = null
) {
    engine.getCacheEngine()?.remove(this)
}

fun Array<String?>.cache_removeForKeys(
    engine: String? = null
) {
    engine.getCacheEngine()?.removeForKeys(this)
}

fun String.cache_contains(
    engine: String? = null
): Boolean {
    return engine.getCacheEngine()?.contains(this) ?: false
}

fun String.cache_isDue(
    engine: String? = null
): Boolean {
    return engine.getCacheEngine()?.isDue(this) ?: false
}

fun cache_clear(
    engine: String? = null
) {
    engine.getCacheEngine()?.clear()
}

fun cache_clearDue(
    engine: String? = null
) {
    engine.getCacheEngine()?.clearDue()
}

fun cache_clearType(
    engine: String? = null,
    type: Int
) {
    engine.getCacheEngine()?.clearType(type)
}

fun <Item : ICacheEngine.EngineItem> String.cache_getItemByKey(
    engine: String? = null
): Item? {
    return engine.getCacheEngine()?.getItemByKey(this) as? Item
}

fun <Item : ICacheEngine.EngineItem> cache_getKeys(
    engine: String? = null
): List<Item?>? {
    return engine.getCacheEngine()?.keys as? List<Item?>
}

fun <Item : ICacheEngine.EngineItem> cache_getPermanentKeys(
    engine: String? = null
): List<Item?>? {
    return engine.getCacheEngine()?.permanentKeys as? List<Item?>
}

fun cache_getCount(
    engine: String? = null
): Int {
    return engine.getCacheEngine()?.count ?: 0
}

fun cache_getSize(
    engine: String? = null
): Long {
    return engine.getCacheEngine()?.size ?: 0L
}

// =======
// = 存储 =
// =======

fun String.cache_put(
    engine: String? = null,
    value: Int,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: Long,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: Float,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: Double,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: Boolean,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: String?,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: ByteArray?,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: Bitmap?,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: Drawable?,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: Serializable?,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: Parcelable?,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: JSONObject?,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: JSONArray?,
    validTime: Long
): Boolean {
    return engine.getCacheEngine()?.put(this, value, validTime) ?: false
}

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

fun String.cache_getInt(
    engine: String? = null
): Int {
    return engine.getCacheEngine()?.getInt(this) ?: INTEGER_DEFAULT
}

fun String.cache_getLong(
    engine: String? = null
): Long {
    return engine.getCacheEngine()?.getLong(this) ?: LONG_DEFAULT
}

fun String.cache_getFloat(
    engine: String? = null
): Float {
    return engine.getCacheEngine()?.getFloat(this) ?: FLOAT_DEFAULT
}

fun String.cache_getDouble(
    engine: String? = null
): Double {
    return engine.getCacheEngine()?.getDouble(this) ?: DOUBLE_DEFAULT
}

fun String.cache_getBoolean(
    engine: String? = null
): Boolean {
    return engine.getCacheEngine()?.getBoolean(this) ?: BOOLEAN_DEFAULT
}

fun String.cache_getString(
    engine: String? = null
): String? {
    return engine.getCacheEngine()?.getString(this)
}

fun String.cache_getBytes(
    engine: String? = null
): ByteArray? {
    return engine.getCacheEngine()?.getBytes(this)
}

fun String.cache_getBitmap(
    engine: String? = null
): Bitmap? {
    return engine.getCacheEngine()?.getBitmap(this)
}

fun String.cache_getDrawable(
    engine: String? = null
): Drawable? {
    return engine.getCacheEngine()?.getDrawable(this)
}

fun String.cache_getSerializable(
    engine: String? = null
): Any? {
    return engine.getCacheEngine()?.getSerializable(this)
}

fun <T> String.cache_getParcelable(
    engine: String? = null,
    creator: Parcelable.Creator<T>?
): T? {
    return engine.getCacheEngine()?.getParcelable(this, creator)
}

fun String.cache_getJSONObject(
    engine: String? = null
): JSONObject? {
    return engine.getCacheEngine()?.getJSONObject(this)
}

fun String.cache_getJSONArray(
    engine: String? = null
): JSONArray? {
    return engine.getCacheEngine()?.getJSONArray(this)
}

fun <T> String.cache_getEntity(
    engine: String? = null,
    typeOfT: Type?
): T? {
    return engine.getCacheEngine()?.getEntity(this, typeOfT)
}

// =================
// = 获取 ( 默认值 ) =
// =================

fun String.cache_getInt(
    engine: String? = null,
    defaultValue: Int
): Int {
    return engine.getCacheEngine()?.getInt(this, defaultValue) ?: defaultValue
}

fun String.cache_getLong(
    engine: String? = null,
    defaultValue: Long
): Long {
    return engine.getCacheEngine()?.getLong(this, defaultValue) ?: defaultValue
}

fun String.cache_getFloat(
    engine: String? = null,
    defaultValue: Float
): Float {
    return engine.getCacheEngine()?.getFloat(this, defaultValue) ?: defaultValue
}

fun String.cache_getDouble(
    engine: String? = null,
    defaultValue: Double
): Double {
    return engine.getCacheEngine()?.getDouble(this, defaultValue) ?: defaultValue
}

fun String.cache_getBoolean(
    engine: String? = null,
    defaultValue: Boolean
): Boolean {
    return engine.getCacheEngine()?.getBoolean(this, defaultValue) ?: defaultValue
}

fun String.cache_getString(
    engine: String? = null,
    defaultValue: String?
): String? {
    return engine.getCacheEngine()?.getString(this, defaultValue)
}

fun String.cache_getBytes(
    engine: String? = null,
    defaultValue: ByteArray?
): ByteArray? {
    return engine.getCacheEngine()?.getBytes(this, defaultValue)
}

fun String.cache_getBitmap(
    engine: String? = null,
    defaultValue: Bitmap?
): Bitmap? {
    return engine.getCacheEngine()?.getBitmap(this, defaultValue)
}

fun String.cache_getDrawable(
    engine: String? = null,
    defaultValue: Drawable?
): Drawable? {
    return engine.getCacheEngine()?.getDrawable(this, defaultValue)
}

fun String.cache_getSerializable(
    engine: String? = null,
    defaultValue: Any?
): Any? {
    return engine.getCacheEngine()?.getSerializable(this, defaultValue)
}

fun <T> String.cache_getParcelable(
    engine: String? = null,
    creator: Parcelable.Creator<T>?,
    defaultValue: T?
): T? {
    return engine.getCacheEngine()?.getParcelable(this, creator, defaultValue)
}

fun String.cache_getJSONObject(
    engine: String? = null,
    defaultValue: JSONObject?
): JSONObject? {
    return engine.getCacheEngine()?.getJSONObject(this, defaultValue)
}

fun String.cache_getJSONArray(
    engine: String? = null,
    defaultValue: JSONArray?
): JSONArray? {
    return engine.getCacheEngine()?.getJSONArray(this, defaultValue)
}

fun <T> String.cache_getEntity(
    engine: String? = null,
    typeOfT: Type?,
    defaultValue: T?
): T? {
    return engine.getCacheEngine()?.getEntity(this, typeOfT, defaultValue)
}