package dev.engine.core.cache

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Parcelable
import dev.engine.cache.ICacheEngine
import dev.engine.json.DevJSONEngine
import dev.engine.json.IJSONEngine
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable
import java.lang.reflect.Type

/**
 * detail: DevCache ( DevUtils ) Cache Engine 实现
 * @author Ttt
 */
open class DevCacheEngineImpl(
    private val mConfig: CacheConfig
) : ICacheEngine<CacheConfig?, DataItem?> {

    // JSON Engine
    private var mJSONEngine: IJSONEngine<out IJSONEngine.EngineConfig>? = DevJSONEngine.getEngine()

    fun setJSONEngine(engine: IJSONEngine<out IJSONEngine.EngineConfig>) {
        this.mJSONEngine = engine
    }

    private fun _jsonEngine(): IJSONEngine<out IJSONEngine.EngineConfig>? {
        if (mJSONEngine != null) return mJSONEngine
        return DevJSONEngine.getEngine()
    }

    // =============
    // = 对外公开方法 =
    // =============

    override fun getConfig(): CacheConfig {
        return mConfig
    }

    override fun remove(key: String?) {
        mConfig.mDevCache.remove(key)
    }

    override fun removeForKeys(keys: Array<out String>?) {
        mConfig.mDevCache.removeForKeys(keys)
    }

    override fun contains(key: String?): Boolean {
        return mConfig.mDevCache.contains(key)
    }

    override fun isDue(key: String?): Boolean {
        return mConfig.mDevCache.isDue(key)
    }

    override fun clear() {
        mConfig.mDevCache.clear()
    }

    override fun clearDue() {
        mConfig.mDevCache.clearDue()
    }

    override fun clearType(type: Int) {
        mConfig.mDevCache.clearType(type)
    }

    override fun getItemByKey(key: String?): DataItem? {
        val data = mConfig.mDevCache.getItemByKey(key) ?: return null
        return DataItem(
            data.key, data.type, data.size,
            data.saveTime, data.validTime,
            data.isPermanent, data.isDue
        )
    }

    override fun getKeys(): MutableList<DataItem> {
        val datas = mConfig.mDevCache.keys
        val lists = mutableListOf<DataItem>()
        for (data in datas) {
            if (data != null) {
                val item = DataItem(
                    data.key, data.type, data.size,
                    data.saveTime, data.validTime,
                    data.isPermanent, data.isDue
                )
                lists.add(item)
            }
        }
        return lists
    }

    override fun getPermanentKeys(): MutableList<DataItem> {
        val datas = mConfig.mDevCache.permanentKeys
        val lists = mutableListOf<DataItem>()
        for (data in datas) {
            if (data != null) {
                val item = DataItem(
                    data.key, data.type, data.size,
                    data.saveTime, data.validTime,
                    data.isPermanent, data.isDue
                )
                lists.add(item)
            }
        }
        return lists
    }

    override fun getCount(): Int {
        return mConfig.mDevCache.count
    }

    override fun getSize(): Long {
        return mConfig.mDevCache.size
    }

    // =======
    // = 存储 =
    // =======

    override fun put(
        key: String?,
        value: Int,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    override fun put(
        key: String?,
        value: Long,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    override fun put(
        key: String?,
        value: Float,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    override fun put(
        key: String?,
        value: Double,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    override fun put(
        key: String?,
        value: Boolean,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    override fun put(
        key: String?,
        value: String?,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    override fun put(
        key: String?,
        value: ByteArray?,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    override fun put(
        key: String?,
        value: Bitmap?,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    override fun put(
        key: String?,
        value: Drawable?,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    override fun put(
        key: String?,
        value: Serializable?,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    override fun put(
        key: String?,
        value: Parcelable?,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    override fun put(
        key: String?,
        value: JSONObject?,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    override fun put(
        key: String?,
        value: JSONArray?,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    override fun <T : Any> put(
        key: String?,
        value: T,
        validTime: Long
    ): Boolean {
        val json = _jsonEngine()?.toJson(value)
        return mConfig.mDevCache.put(key, json, validTime)
    }

    // =======
    // = 获取 =
    // =======

    override fun getInt(key: String?): Int {
        return mConfig.mDevCache.getInt(key)
    }

    override fun getLong(key: String?): Long {
        return mConfig.mDevCache.getLong(key)
    }

    override fun getFloat(key: String?): Float {
        return mConfig.mDevCache.getFloat(key)
    }

    override fun getDouble(key: String?): Double {
        return mConfig.mDevCache.getDouble(key)
    }

    override fun getBoolean(key: String?): Boolean {
        return mConfig.mDevCache.getBoolean(key)
    }

    override fun getString(key: String?): String {
        return mConfig.mDevCache.getString(key)
    }

    override fun getBytes(key: String?): ByteArray {
        return mConfig.mDevCache.getBytes(key)
    }

    override fun getBitmap(key: String?): Bitmap {
        return mConfig.mDevCache.getBitmap(key)
    }

    override fun getDrawable(key: String?): Drawable {
        return mConfig.mDevCache.getDrawable(key)
    }

    override fun getSerializable(key: String?): Any {
        return mConfig.mDevCache.getSerializable(key)
    }

    override fun <T : Any> getParcelable(
        key: String?,
        creator: Parcelable.Creator<T>?
    ): T {
        return mConfig.mDevCache.getParcelable(key, creator)
    }

    override fun getJSONObject(key: String?): JSONObject {
        return mConfig.mDevCache.getJSONObject(key)
    }

    override fun getJSONArray(key: String?): JSONArray {
        return mConfig.mDevCache.getJSONArray(key)
    }

    override fun <T : Any> getEntity(
        key: String?,
        typeOfT: Type?
    ): T? {
        return getEntity(key, typeOfT, null)
    }

    // =

    override fun getInt(
        key: String?,
        defaultValue: Int
    ): Int {
        return mConfig.mDevCache.getInt(key, defaultValue)
    }

    override fun getLong(
        key: String?,
        defaultValue: Long
    ): Long {
        return mConfig.mDevCache.getLong(key, defaultValue)
    }

    override fun getFloat(
        key: String?,
        defaultValue: Float
    ): Float {
        return mConfig.mDevCache.getFloat(key, defaultValue)
    }

    override fun getDouble(
        key: String?,
        defaultValue: Double
    ): Double {
        return mConfig.mDevCache.getDouble(key, defaultValue)
    }

    override fun getBoolean(
        key: String?,
        defaultValue: Boolean
    ): Boolean {
        return mConfig.mDevCache.getBoolean(key, defaultValue)
    }

    override fun getString(
        key: String?,
        defaultValue: String?
    ): String {
        return mConfig.mDevCache.getString(key, defaultValue)
    }

    override fun getBytes(
        key: String?,
        defaultValue: ByteArray?
    ): ByteArray {
        return mConfig.mDevCache.getBytes(key, defaultValue)
    }

    override fun getBitmap(
        key: String?,
        defaultValue: Bitmap?
    ): Bitmap {
        return mConfig.mDevCache.getBitmap(key, defaultValue)
    }

    override fun getDrawable(
        key: String?,
        defaultValue: Drawable?
    ): Drawable {
        return mConfig.mDevCache.getDrawable(key, defaultValue)
    }

    override fun getSerializable(
        key: String?,
        defaultValue: Any?
    ): Any {
        return mConfig.mDevCache.getSerializable(key, defaultValue)
    }

    override fun <T : Any> getParcelable(
        key: String?,
        creator: Parcelable.Creator<T>?,
        defaultValue: T
    ): T {
        return mConfig.mDevCache.getParcelable(key, creator, defaultValue)
    }

    override fun getJSONObject(
        key: String?,
        defaultValue: JSONObject?
    ): JSONObject {
        return mConfig.mDevCache.getJSONObject(key, defaultValue)
    }

    override fun getJSONArray(
        key: String?,
        defaultValue: JSONArray?
    ): JSONArray {
        return mConfig.mDevCache.getJSONArray(key, defaultValue)
    }

    override fun <T : Any> getEntity(
        key: String?,
        typeOfT: Type?,
        defaultValue: T?
    ): T? {
        return _jsonEngine()?.fromJson<T>(
            getString(key, null), typeOfT
        ) ?: return defaultValue
    }
}