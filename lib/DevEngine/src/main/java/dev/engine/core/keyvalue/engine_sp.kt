package dev.engine.core.keyvalue

import dev.engine.json.DevJSONEngine
import dev.engine.json.IJSONEngine
import dev.engine.keyvalue.IKeyValueEngine
import dev.utils.app.share.IPreference
import dev.utils.common.ConvertUtils
import dev.utils.common.cipher.Cipher
import java.lang.reflect.Type

/**
 * detail: SharedPreferences Key-Value Config
 * @author Ttt
 */
open class SPConfig(
    // SharedPreferences
    val preference: IPreference,
    // 通用加解密中间层
    private val cipher: Cipher? = null
) : IKeyValueEngine.EngineConfig {

    /**
     * 通用加解密中间层
     */
    override fun cipher(): Cipher? = cipher
}

/**
 * detail: SharedPreferences Key-Value Engine 实现
 * @author Ttt
 */
open class SPKeyValueEngineImpl(
    @JvmField protected val mConfig: SPConfig
) : IKeyValueEngine<SPConfig> {

    // SharedPreferences
    @JvmField
    protected val mPreference = mConfig.preference

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
     * 获取 Key-Value Engine Config
     * @return Key-Value Config
     */
    override fun getConfig(): SPConfig {
        return mConfig
    }

    /**
     * 移除数据
     * @param key 保存的 key
     */
    override fun remove(key: String?) {
        mPreference.remove(key)
    }

    /**
     * 移除数组的数据
     * @param keys 保存的 key 数组
     */
    override fun removeForKeys(keys: Array<out String>?) {
        mPreference.removeAll(keys)
    }

    /**
     * 是否存在 key
     * @param key 保存的 key
     * @return `true` yes, `false` no
     */
    override fun contains(key: String?): Boolean {
        return mPreference.contains(key)
    }

    /**
     * 清除全部数据
     */
    override fun clear() {
        mPreference.clear()
    }

    // =======
    // = 存储 =
    // =======

    /**
     * 保存 int 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @return `true` success, `false` fail
     */
    override fun putInt(
        key: String?,
        value: Int
    ): Boolean {
        mPreference.put(key, value)
        return true
    }

    /**
     * 保存 long 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @return `true` success, `false` fail
     */
    override fun putLong(
        key: String?,
        value: Long
    ): Boolean {
        mPreference.put(key, value)
        return true
    }

    /**
     * 保存 float 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @return `true` success, `false` fail
     */
    override fun putFloat(
        key: String?,
        value: Float
    ): Boolean {
        mPreference.put(key, value)
        return true
    }

    /**
     * 保存 double 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @return `true` success, `false` fail
     */
    override fun putDouble(
        key: String?,
        value: Double
    ): Boolean {
        mPreference.put(key, value)
        return true
    }

    /**
     * 保存 boolean 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @return `true` success, `false` fail
     */
    override fun putBoolean(
        key: String?,
        value: Boolean
    ): Boolean {
        mPreference.put(key, value)
        return true
    }

    /**
     * 保存 String 类型的数据
     * @param key 保存的 key
     * @param value 存储的数据
     * @return `true` success, `false` fail
     */
    override fun putString(
        key: String?,
        value: String?
    ): Boolean {
        var content = value
        val cipher = mConfig.cipher()
        if (value != null && cipher != null) {
            val bytes = cipher.encrypt(ConvertUtils.toBytes(value))
            content = ConvertUtils.newString(bytes)
        }
        mPreference.put(key, content)
        return true
    }

    /**
     * 保存指定类型对象
     * @param key 保存的 key
     * @param value 存储的数据
     * @return `true` success, `false` fail
     */
    override fun <T : Any> putEntity(
        key: String?,
        value: T
    ): Boolean {
        return putString(key, resolveJsonEngine()?.toJson(value))
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
        return mPreference.getInt(key)
    }

    /**
     * 获取 long 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getLong(key: String?): Long {
        return mPreference.getLong(key)
    }

    /**
     * 获取 float 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getFloat(key: String?): Float {
        return mPreference.getFloat(key)
    }

    /**
     * 获取 double 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getDouble(key: String?): Double {
        return mPreference.getDouble(key)
    }

    /**
     * 获取 boolean 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getBoolean(key: String?): Boolean {
        return mPreference.getBoolean(key)
    }

    /**
     * 获取 String 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getString(key: String?): String? {
        return mPreference.getString(key)
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
        return mPreference.getInt(key, defaultValue)
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
        return mPreference.getLong(key, defaultValue)
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
        return mPreference.getFloat(key, defaultValue)
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
        return mPreference.getDouble(key, defaultValue)
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
        return mPreference.getBoolean(key, defaultValue)
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
        var content = mPreference.getString(key, null) ?: return defaultValue
        val cipher = mConfig.cipher()
        if (cipher != null) {
            val bytes = cipher.decrypt(ConvertUtils.toBytes(content))
            content = ConvertUtils.newString(bytes)
        }
        return content
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