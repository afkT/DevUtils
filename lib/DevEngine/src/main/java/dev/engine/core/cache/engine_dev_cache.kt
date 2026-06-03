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
    @JvmField protected val mConfig: CacheConfig
) : ICacheEngine<CacheConfig?, DataItem?> {

    // JSON Engine
    @JvmField
    protected var mJSONEngine: IJSONEngine<out IJSONEngine.EngineConfig>? = DevJSONEngine.getEngine()

    open fun setJSONEngine(engine: IJSONEngine<out IJSONEngine.EngineConfig>) {
        this.mJSONEngine = engine
    }

    protected open fun resolveJsonEngine(): IJSONEngine<out IJSONEngine.EngineConfig>? {
        if (mJSONEngine != null) return mJSONEngine
        return DevJSONEngine.getEngine()
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 Cache Engine Config
     * @return Cache Config
     */
    override fun getConfig(): CacheConfig {
        return mConfig
    }

    /**
     * 移除数据
     * @param key 保存的 key
     */
    override fun remove(key: String?) {
        mConfig.mDevCache.remove(key)
    }

    /**
     * 移除数组的数据
     * @param keys 保存的 key 数组
     */
    override fun removeForKeys(keys: Array<out String>?) {
        mConfig.mDevCache.removeForKeys(keys)
    }

    /**
     * 是否存在 key
     * @param key 保存的 key
     * @return `true` yes, `false` no
     */
    override fun contains(key: String?): Boolean {
        return mConfig.mDevCache.contains(key)
    }

    /**
     * 判断某个 key 是否过期
     * <pre>
     *     如果不存在该 key 也返回过期
     * </pre>
     * @param key 保存的 key
     * @return `true` yes, `false` no
     */
    override fun isDue(key: String?): Boolean {
        return mConfig.mDevCache.isDue(key)
    }

    /**
     * 清除全部数据
     */
    override fun clear() {
        mConfig.mDevCache.clear()
    }

    /**
     * 清除过期数据
     */
    override fun clearDue() {
        mConfig.mDevCache.clearDue()
    }

    /**
     * 清除某个类型的全部数据
     * @param type 类型
     */
    override fun clearType(type: Int) {
        mConfig.mDevCache.clearType(type)
    }

    /**
     * 通过 Key 获取 Item
     * @param key 保存的 key
     * @return Item
     */
    override fun getItemByKey(key: String?): DataItem? {
        val data = mConfig.mDevCache.getItemByKey(key) ?: return null
        return DataItem(
            data.key, data.type, data.size,
            data.saveTime, data.validTime,
            data.isPermanent, data.isDue
        )
    }

    /**
     * 获取有效 Key 集合
     * @return 有效 Key 集合
     */
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

    /**
     * 获取永久有效 Key 集合
     * @return 永久有效 Key 集合
     */
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

    /**
     * 获取有效 Key 数量
     * @return 有效 Key 数量
     */
    override fun getCount(): Int {
        return mConfig.mDevCache.count
    }

    /**
     * 获取有效 Key 占用总大小
     * @return 有效 Key 占用总大小
     */
    override fun getSize(): Long {
        return mConfig.mDevCache.size
    }

    // =======
    // = 存储 =
    // =======

    /**
     * 保存 int 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return `true` success, `false` fail
     */
    override fun put(
        key: String?,
        value: Int,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    /**
     * 保存 long 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return `true` success, `false` fail
     */
    override fun put(
        key: String?,
        value: Long,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    /**
     * 保存 float 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return `true` success, `false` fail
     */
    override fun put(
        key: String?,
        value: Float,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    /**
     * 保存 double 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return `true` success, `false` fail
     */
    override fun put(
        key: String?,
        value: Double,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    /**
     * 保存 boolean 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return `true` success, `false` fail
     */
    override fun put(
        key: String?,
        value: Boolean,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    /**
     * 保存 String 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return `true` success, `false` fail
     */
    override fun put(
        key: String?,
        value: String?,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    /**
     * 保存 byte[] 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return `true` success, `false` fail
     */
    override fun put(
        key: String?,
        value: ByteArray?,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    /**
     * 保存 Bitmap 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return `true` success, `false` fail
     */
    override fun put(
        key: String?,
        value: Bitmap?,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    /**
     * 保存 Drawable 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return `true` success, `false` fail
     */
    override fun put(
        key: String?,
        value: Drawable?,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    /**
     * 保存 Serializable 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return `true` success, `false` fail
     */
    override fun put(
        key: String?,
        value: Serializable?,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    /**
     * 保存 Parcelable 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return `true` success, `false` fail
     */
    override fun put(
        key: String?,
        value: Parcelable?,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    /**
     * 保存 JSONObject 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return `true` success, `false` fail
     */
    override fun put(
        key: String?,
        value: JSONObject?,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    /**
     * 保存 JSONArray 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return `true` success, `false` fail
     */
    override fun put(
        key: String?,
        value: JSONArray?,
        validTime: Long
    ): Boolean {
        return mConfig.mDevCache.put(key, value, validTime)
    }

    /**
     * 保存指定类型对象
     * @param key 保存的 key
     * @param value 存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return `true` success, `false` fail
     */
    override fun <T : Any> put(
        key: String?,
        value: T,
        validTime: Long
    ): Boolean {
        val json = resolveJsonEngine()?.toJson(value)
        return mConfig.mDevCache.put(key, json, validTime)
    }

    // =======
    // = 获取 =
    // =======

    /**
     * 获取 int 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getInt(key: String?): Int {
        return mConfig.mDevCache.getInt(key)
    }

    /**
     * 获取 long 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getLong(key: String?): Long {
        return mConfig.mDevCache.getLong(key)
    }

    /**
     * 获取 float 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getFloat(key: String?): Float {
        return mConfig.mDevCache.getFloat(key)
    }

    /**
     * 获取 double 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getDouble(key: String?): Double {
        return mConfig.mDevCache.getDouble(key)
    }

    /**
     * 获取 boolean 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getBoolean(key: String?): Boolean {
        return mConfig.mDevCache.getBoolean(key)
    }

    /**
     * 获取 String 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getString(key: String?): String? {
        return mConfig.mDevCache.getString(key)
    }

    /**
     * 获取 byte[] 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getBytes(key: String?): ByteArray? {
        return mConfig.mDevCache.getBytes(key)
    }

    /**
     * 获取 Bitmap 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getBitmap(key: String?): Bitmap? {
        return mConfig.mDevCache.getBitmap(key)
    }

    /**
     * 获取 Drawable 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getDrawable(key: String?): Drawable? {
        return mConfig.mDevCache.getDrawable(key)
    }

    /**
     * 获取 Serializable 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getSerializable(key: String?): Any? {
        return mConfig.mDevCache.getSerializable(key)
    }

    /**
     * 获取 Parcelable 类型的数据
     * @param key 保存的 key
     * @param creator Parcelable.Creator
     * @return 存储的数据
     */
    override fun <T : Any> getParcelable(
        key: String?,
        creator: Parcelable.Creator<T>?
    ): T? {
        return mConfig.mDevCache.getParcelable(key, creator)
    }

    /**
     * 获取 JSONObject 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getJSONObject(key: String?): JSONObject? {
        return mConfig.mDevCache.getJSONObject(key)
    }

    /**
     * 获取 JSONArray 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getJSONArray(key: String?): JSONArray? {
        return mConfig.mDevCache.getJSONArray(key)
    }

    /**
     * 获取指定类型对象
     * @param key 保存的 key
     * @param typeOfT Type T
     * @return instance of type
     */
    override fun <T : Any> getEntity(
        key: String?,
        typeOfT: Type?
    ): T? {
        return getEntity(key, typeOfT, null)
    }

    // =

    /**
     * 获取 int 类型的数据
     * @param key 保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    override fun getInt(
        key: String?,
        defaultValue: Int
    ): Int {
        return mConfig.mDevCache.getInt(key, defaultValue)
    }

    /**
     * 获取 long 类型的数据
     * @param key 保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    override fun getLong(
        key: String?,
        defaultValue: Long
    ): Long {
        return mConfig.mDevCache.getLong(key, defaultValue)
    }

    /**
     * 获取 float 类型的数据
     * @param key 保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    override fun getFloat(
        key: String?,
        defaultValue: Float
    ): Float {
        return mConfig.mDevCache.getFloat(key, defaultValue)
    }

    /**
     * 获取 double 类型的数据
     * @param key 保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    override fun getDouble(
        key: String?,
        defaultValue: Double
    ): Double {
        return mConfig.mDevCache.getDouble(key, defaultValue)
    }

    /**
     * 获取 boolean 类型的数据
     * @param key 保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    override fun getBoolean(
        key: String?,
        defaultValue: Boolean
    ): Boolean {
        return mConfig.mDevCache.getBoolean(key, defaultValue)
    }

    /**
     * 获取 String 类型的数据
     * @param key 保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    override fun getString(
        key: String?,
        defaultValue: String?
    ): String? {
        return mConfig.mDevCache.getString(key, defaultValue)
    }

    /**
     * 获取 byte[] 类型的数据
     * @param key 保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    override fun getBytes(
        key: String?,
        defaultValue: ByteArray?
    ): ByteArray? {
        return mConfig.mDevCache.getBytes(key, defaultValue)
    }

    /**
     * 获取 Bitmap 类型的数据
     * @param key 保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    override fun getBitmap(
        key: String?,
        defaultValue: Bitmap?
    ): Bitmap? {
        return mConfig.mDevCache.getBitmap(key, defaultValue)
    }

    /**
     * 获取 Drawable 类型的数据
     * @param key 保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    override fun getDrawable(
        key: String?,
        defaultValue: Drawable?
    ): Drawable? {
        return mConfig.mDevCache.getDrawable(key, defaultValue)
    }

    /**
     * 获取 Serializable 类型的数据
     * @param key 保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    override fun getSerializable(
        key: String?,
        defaultValue: Any?
    ): Any? {
        return mConfig.mDevCache.getSerializable(key, defaultValue)
    }

    /**
     * 获取 Parcelable 类型的数据
     * @param key 保存的 key
     * @param creator Parcelable.Creator
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    override fun <T : Any> getParcelable(
        key: String?,
        creator: Parcelable.Creator<T>?,
        defaultValue: T
    ): T? {
        return mConfig.mDevCache.getParcelable(key, creator, defaultValue)
    }

    /**
     * 获取 JSONObject 类型的数据
     * @param key 保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    override fun getJSONObject(
        key: String?,
        defaultValue: JSONObject?
    ): JSONObject? {
        return mConfig.mDevCache.getJSONObject(key, defaultValue)
    }

    /**
     * 获取 JSONArray 类型的数据
     * @param key 保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    override fun getJSONArray(
        key: String?,
        defaultValue: JSONArray?
    ): JSONArray? {
        return mConfig.mDevCache.getJSONArray(key, defaultValue)
    }

    /**
     * 获取指定类型对象
     * @param key 保存的 key
     * @param typeOfT Type T
     * @param defaultValue 默认值
     * @return instance of type
     */
    override fun <T : Any> getEntity(
        key: String?,
        typeOfT: Type?,
        defaultValue: T?
    ): T? {
        return resolveJsonEngine()?.fromJson<T>(
            getString(key, null), typeOfT
        ) ?: return defaultValue
    }
}