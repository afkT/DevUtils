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
fun String?.getEngine(): ICacheEngine<in ICacheEngine.EngineConfig, in ICacheEngine.EngineItem>? {
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

fun <Config : ICacheEngine.EngineConfig> cache_getConfig(
    engine: String? = null
): Config? {
    return engine.getEngine()?.config as? Config
}

// =

fun String.cache_remove(
    engine: String? = null
) {
    engine.getEngine()?.remove(this)
}

fun Array<String?>.cache_removeForKeys(
    engine: String? = null
) {
    engine.getEngine()?.removeForKeys(this)
}

fun String.cache_contains(
    engine: String? = null
): Boolean {
    return engine.getEngine()?.contains(this) ?: false
}

fun String.cache_isDue(
    engine: String? = null
): Boolean {
    return engine.getEngine()?.isDue(this) ?: false
}

fun cache_clear(
    engine: String? = null
) {
    engine.getEngine()?.clear()
}

fun cache_clearDue(
    engine: String? = null
) {
    engine.getEngine()?.clearDue()
}

fun cache_clearType(
    engine: String? = null,
    type: Int
) {
    engine.getEngine()?.clearType(type)
}

fun <Item : ICacheEngine.EngineItem> String.cache_getItemByKey(
    engine: String? = null
): Item? {
    return engine.getEngine()?.getItemByKey(this) as? Item
}

fun <Item : ICacheEngine.EngineItem> cache_getKeys(
    engine: String? = null
): List<Item?>? {
    return engine.getEngine()?.keys as? List<Item?>
}

fun <Item : ICacheEngine.EngineItem> cache_getPermanentKeys(
    engine: String? = null
): List<Item?>? {
    return engine.getEngine()?.permanentKeys as? List<Item?>
}

fun cache_getCount(
    engine: String? = null
): Int {
    return engine.getEngine()?.count ?: 0
}

fun cache_getSize(
    engine: String? = null
): Long {
    return engine.getEngine()?.size ?: 0L
}

// =======
// = 存储 =
// =======

fun String.cache_put(
    engine: String? = null,
    value: Int,
    validTime: Long
): Boolean {
    return engine.getEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: Long,
    validTime: Long
): Boolean {
    return engine.getEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: Float,
    validTime: Long
): Boolean {
    return engine.getEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: Double,
    validTime: Long
): Boolean {
    return engine.getEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: Boolean,
    validTime: Long
): Boolean {
    return engine.getEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: String?,
    validTime: Long
): Boolean {
    return engine.getEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: ByteArray?,
    validTime: Long
): Boolean {
    return engine.getEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: Bitmap?,
    validTime: Long
): Boolean {
    return engine.getEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: Drawable?,
    validTime: Long
): Boolean {
    return engine.getEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: Serializable?,
    validTime: Long
): Boolean {
    return engine.getEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: Parcelable?,
    validTime: Long
): Boolean {
    return engine.getEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: JSONObject?,
    validTime: Long
): Boolean {
    return engine.getEngine()?.put(this, value, validTime) ?: false
}

fun String.cache_put(
    engine: String? = null,
    value: JSONArray?,
    validTime: Long
): Boolean {
    return engine.getEngine()?.put(this, value, validTime) ?: false
}

fun <T> String.cache_put(
    engine: String? = null,
    value: T,
    validTime: Long
): Boolean {
    return engine.getEngine()?.put(this, value, validTime) ?: false
}

// =======
// = 获取 =
// =======

fun String.cache_getInt(
    engine: String? = null
): Int {
    return engine.getEngine()?.getInt(this) ?: INTEGER_DEFAULT
}

fun String.cache_getLong(
    engine: String? = null
): Long {
    return engine.getEngine()?.getLong(this) ?: LONG_DEFAULT
}

fun String.cache_getFloat(
    engine: String? = null
): Float {
    return engine.getEngine()?.getFloat(this) ?: FLOAT_DEFAULT
}

fun String.cache_getDouble(
    engine: String? = null
): Double {
    return engine.getEngine()?.getDouble(this) ?: DOUBLE_DEFAULT
}

fun String.cache_getBoolean(
    engine: String? = null
): Boolean {
    return engine.getEngine()?.getBoolean(this) ?: BOOLEAN_DEFAULT
}

fun String.cache_getString(
    engine: String? = null
): String? {
    return engine.getEngine()?.getString(this)
}

fun String.cache_getBytes(
    engine: String? = null
): ByteArray? {
    return engine.getEngine()?.getBytes(this)
}

fun String.cache_getBitmap(
    engine: String? = null
): Bitmap? {
    return engine.getEngine()?.getBitmap(this)
}

fun String.cache_getDrawable(
    engine: String? = null
): Drawable? {
    return engine.getEngine()?.getDrawable(this)
}

fun String.cache_getSerializable(
    engine: String? = null
): Any? {
    return engine.getEngine()?.getSerializable(this)
}

fun <T> String.cache_getParcelable(
    engine: String? = null,
    creator: Parcelable.Creator<T>?
): T? {
    return engine.getEngine()?.getParcelable(this, creator)
}

fun String.cache_getJSONObject(
    engine: String? = null
): JSONObject? {
    return engine.getEngine()?.getJSONObject(this)
}

fun String.cache_getJSONArray(
    engine: String? = null
): JSONArray? {
    return engine.getEngine()?.getJSONArray(this)
}

fun <T> String.cache_getEntity(
    engine: String? = null,
    typeOfT: Type?
): T? {
    return engine.getEngine()?.getEntity(this, typeOfT)
}

// =================
// = 获取 ( 默认值 ) =
// =================

fun String.cache_getInt(
    engine: String? = null,
    defaultValue: Int
): Int {
    return engine.getEngine()?.getInt(this, defaultValue) ?: defaultValue
}

fun String.cache_getLong(
    engine: String? = null,
    defaultValue: Long
): Long {
    return engine.getEngine()?.getLong(this, defaultValue) ?: defaultValue
}

fun String.cache_getFloat(
    engine: String? = null,
    defaultValue: Float
): Float {
    return engine.getEngine()?.getFloat(this, defaultValue) ?: defaultValue
}

fun String.cache_getDouble(
    engine: String? = null,
    defaultValue: Double
): Double {
    return engine.getEngine()?.getDouble(this, defaultValue) ?: defaultValue
}

fun String.cache_getBoolean(
    engine: String? = null,
    defaultValue: Boolean
): Boolean {
    return engine.getEngine()?.getBoolean(this, defaultValue) ?: defaultValue
}

fun String.cache_getString(
    engine: String? = null,
    defaultValue: String?
): String? {
    return engine.getEngine()?.getString(this, defaultValue)
}

fun String.cache_getBytes(
    engine: String? = null,
    defaultValue: ByteArray?
): ByteArray? {
    return engine.getEngine()?.getBytes(this, defaultValue)
}

fun String.cache_getBitmap(
    engine: String? = null,
    defaultValue: Bitmap?
): Bitmap? {
    return engine.getEngine()?.getBitmap(this, defaultValue)
}

fun String.cache_getDrawable(
    engine: String? = null,
    defaultValue: Drawable?
): Drawable? {
    return engine.getEngine()?.getDrawable(this, defaultValue)
}

fun String.cache_getSerializable(
    engine: String? = null,
    defaultValue: Any?
): Any? {
    return engine.getEngine()?.getSerializable(this, defaultValue)
}

fun <T> String.cache_getParcelable(
    engine: String? = null,
    creator: Parcelable.Creator<T>?,
    defaultValue: T?
): T? {
    return engine.getEngine()?.getParcelable(this, creator, defaultValue)
}

fun String.cache_getJSONObject(
    engine: String? = null,
    defaultValue: JSONObject?
): JSONObject? {
    return engine.getEngine()?.getJSONObject(this, defaultValue)
}

fun String.cache_getJSONArray(
    engine: String? = null,
    defaultValue: JSONArray?
): JSONArray? {
    return engine.getEngine()?.getJSONArray(this, defaultValue)
}

fun <T> String.cache_getEntity(
    engine: String? = null,
    typeOfT: Type?,
    defaultValue: T?
): T? {
    return engine.getEngine()?.getEntity(this, typeOfT, defaultValue)
}