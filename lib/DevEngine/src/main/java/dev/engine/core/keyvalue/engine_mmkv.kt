package dev.engine.core.keyvalue

import com.tencent.mmkv.MMKV
import dev.engine.json.DevJSONEngine
import dev.engine.json.IJSONEngine
import dev.engine.keyvalue.IKeyValueEngine
import dev.utils.common.ConvertUtils
import dev.utils.common.cipher.Cipher
import java.lang.reflect.Type

/**
 * detail: MMKV Key-Value Config
 * @author Ttt
 */
open class MMKVConfig(
    val mmkv: MMKV,
    // 通用加解密中间层
    private val cipher: Cipher? = null
) : IKeyValueEngine.EngineConfig {

    /**
     * 通用加解密中间层
     */
    override fun cipher(): Cipher? = cipher
}

/**
 * detail: MMKV Key-Value Engine 实现
 * @author Ttt
 */
open class MMKVKeyValueEngineImpl(
    @JvmField protected val mConfig: MMKVConfig
) : IKeyValueEngine<MMKVConfig> {

    // MMKV（Holder 依赖 internal MMKVUtils，属性不可为 protected）
    private val mHolder: MMKVUtils.Holder

    init {
        // MMKV Holder
        mHolder = MMKVUtils.putHolder(mConfig.mmkv.mmapID(), mConfig.mmkv)
    }

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
    override fun getConfig(): MMKVConfig {
        return mConfig
    }

    /**
     * 移除数据
     * @param key 保存的 key
     */
    override fun remove(key: String?) {
        mHolder.removeValueForKey(key)
    }

    /**
     * 移除数组的数据
     * @param keys 保存的 key 数组
     */
    override fun removeForKeys(keys: Array<String?>?) {
        mHolder.removeValuesForKeys(keys)
    }

    /**
     * 是否存在 key
     * @param key 保存的 key
     * @return `true` yes, `false` no
     */
    override fun contains(key: String?): Boolean {
        return mHolder.containsKey(key)
    }

    /**
     * 清除全部数据
     */
    override fun clear() {
        mHolder.clear()
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
        return mHolder.encode(key, value)
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
        return mHolder.encode(key, value)
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
        return mHolder.encode(key, value)
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
        return mHolder.encode(key, value)
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
        return mHolder.encode(key, value)
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
        return mHolder.encode(key, content)
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
        return mHolder.decodeInt(key)
    }

    /**
     * 获取 long 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getLong(key: String?): Long {
        return mHolder.decodeLong(key)
    }

    /**
     * 获取 float 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getFloat(key: String?): Float {
        return mHolder.decodeFloat(key)
    }

    /**
     * 获取 double 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getDouble(key: String?): Double {
        return mHolder.decodeDouble(key)
    }

    /**
     * 获取 boolean 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getBoolean(key: String?): Boolean {
        return mHolder.decodeBool(key)
    }

    /**
     * 获取 String 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    override fun getString(key: String?): String? {
        return mHolder.decodeString(key)
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
        return mHolder.decodeInt(key, defaultValue)
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
        return mHolder.decodeLong(key, defaultValue)
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
        return mHolder.decodeFloat(key, defaultValue)
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
        return mHolder.decodeDouble(key, defaultValue)
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
        return mHolder.decodeBool(key, defaultValue)
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
        var content = mHolder.decodeString(key, null) ?: return defaultValue
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