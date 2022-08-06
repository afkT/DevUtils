package dev.engine.keyvalue

import dev.engine.json.DevJSONEngine
import dev.engine.json.IJSONEngine
import dev.utils.app.share.IPreference
import dev.utils.common.ConvertUtils
import dev.utils.common.cipher.Cipher
import java.lang.reflect.Type

/**
 * detail: SharedPreferences Key-Value Config
 * @author Ttt
 */
open class SPConfig(
    cipher: Cipher?,
    // SharedPreferences
    val preference: IPreference
) : IKeyValueEngine.EngineConfig(cipher)

/**
 * detail: SharedPreferences Key-Value Engine 实现
 * @author Ttt
 */
open class SPKeyValueEngineImpl(
    private val mConfig: SPConfig
) : IKeyValueEngine<SPConfig> {

    // SharedPreferences
    private val mPreference: IPreference = mConfig.preference

    init {
        // SharedPreferences
    }

    // JSON Engine
    private var mJSONEngine: IJSONEngine<out IJSONEngine.EngineConfig>? = DevJSONEngine.getEngine()

    fun setJSONEngine(engine: IJSONEngine<out IJSONEngine.EngineConfig>) {
        this.mJSONEngine = engine
    }

    // =============
    // = 对外公开方法 =
    // =============

    override fun getConfig(): SPConfig {
        return mConfig
    }

    override fun remove(key: String?) {
        mPreference.remove(key)
    }

    override fun removeForKeys(keys: Array<out String>?) {
        mPreference.removeAll(keys)
    }

    override fun contains(key: String?): Boolean {
        return mPreference.contains(key)
    }

    override fun clear() {
        mPreference.clear()
    }

    // =======
    // = 存储 =
    // =======

    override fun putInt(
        key: String?,
        value: Int
    ): Boolean {
        mPreference.put(key, value)
        return true
    }

    override fun putLong(
        key: String?,
        value: Long
    ): Boolean {
        mPreference.put(key, value)
        return true
    }

    override fun putFloat(
        key: String?,
        value: Float
    ): Boolean {
        mPreference.put(key, value)
        return true
    }

    override fun putDouble(
        key: String?,
        value: Double
    ): Boolean {
        mPreference.put(key, value)
        return true
    }

    override fun putBoolean(
        key: String?,
        value: Boolean
    ): Boolean {
        mPreference.put(key, value)
        return true
    }

    override fun putString(
        key: String?,
        value: String?
    ): Boolean {
        var content = value
        if (value != null && mConfig.cipher != null) {
            val bytes = mConfig.cipher.encrypt(ConvertUtils.toBytes(value))
            content = ConvertUtils.newString(bytes)
        }
        mPreference.put(key, content)
        return true
    }

    override fun <T : Any> putEntity(
        key: String?,
        value: T
    ): Boolean {
        return putString(key, mJSONEngine?.toJson(value))
    }

    // =======
    // = 获取 =
    // =======

    override fun getInt(key: String?): Int {
        return mPreference.getInt(key)
    }

    override fun getLong(key: String?): Long {
        return mPreference.getLong(key)
    }

    override fun getFloat(key: String?): Float {
        return mPreference.getFloat(key)
    }

    override fun getDouble(key: String?): Double {
        return mPreference.getDouble(key)
    }

    override fun getBoolean(key: String?): Boolean {
        return mPreference.getBoolean(key)
    }

    override fun getString(key: String?): String? {
        return mPreference.getString(key)
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
        return mPreference.getInt(key, defaultValue)
    }

    override fun getLong(
        key: String?,
        defaultValue: Long
    ): Long {
        return mPreference.getLong(key, defaultValue)
    }

    override fun getFloat(
        key: String?,
        defaultValue: Float
    ): Float {
        return mPreference.getFloat(key, defaultValue)
    }

    override fun getDouble(
        key: String?,
        defaultValue: Double
    ): Double {
        return mPreference.getDouble(key, defaultValue)
    }

    override fun getBoolean(
        key: String?,
        defaultValue: Boolean
    ): Boolean {
        return mPreference.getBoolean(key, defaultValue)
    }

    override fun getString(
        key: String?,
        defaultValue: String?
    ): String? {
        var content: String? = mPreference.getString(key, null) ?: return defaultValue
        if (content != null && mConfig.cipher != null) {
            val bytes = mConfig.cipher.decrypt(ConvertUtils.toBytes(content))
            content = ConvertUtils.newString(bytes)
        }
        return content
    }

    override fun <T : Any> getEntity(
        key: String?,
        typeOfT: Type?,
        defaultValue: T?
    ): T? {
        return mJSONEngine?.fromJson<T>(
            getString(key, null), typeOfT
        ) ?: return defaultValue
    }
}