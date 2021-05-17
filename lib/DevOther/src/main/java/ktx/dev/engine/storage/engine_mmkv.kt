package ktx.dev.engine.storage

import com.tencent.mmkv.MMKV
import dev.engine.json.DevJSONEngine
import dev.engine.storage.IStorageEngine
import dev.utils.common.ConvertUtils
import dev.utils.common.cipher.Cipher
import ktx.dev.other.MMKVUtils
import java.lang.reflect.Type

/**
 * detail: MMKV Storage Config
 * @author Ttt
 */
class MMKVConfig(
    storageID: String?,
    cipher: Cipher?,
    val mmkv: MMKV
) : IStorageEngine.EngineConfig(storageID, cipher)

/**
 * detail: MMKV Storage Engine 实现
 * @author Ttt
 */
class MMKVStorageEngineImpl(
    private val mConfig: MMKVConfig
) : IStorageEngine<MMKVConfig> {

    // MMKV
    private val mHolder: MMKVUtils.Holder

    init {
        // MMKV Holder
        mHolder = MMKVUtils.putHolder(config.storageID, config.mmkv)
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    override fun getConfig(): MMKVConfig {
        return mConfig
    }

    override fun remove(key: String?) {
        mHolder.removeValueForKey(key)
    }

    override fun removeForKeys(keys: Array<String?>?) {
        mHolder.removeValuesForKeys(keys)
    }

    override fun contains(key: String?): Boolean {
        return mHolder.containsKey(key)
    }

    override fun clear() {
        mHolder.clear()
    }

    // =======
    // = 存储 =
    // =======

    override fun putInt(
        key: String?,
        value: Int
    ): Boolean {
        return mHolder.encode(key, value)
    }

    override fun putLong(
        key: String?,
        value: Long
    ): Boolean {
        return mHolder.encode(key, value)
    }

    override fun putFloat(
        key: String?,
        value: Float
    ): Boolean {
        return mHolder.encode(key, value)
    }

    override fun putDouble(
        key: String?,
        value: Double
    ): Boolean {
        return mHolder.encode(key, value)
    }

    override fun putBoolean(
        key: String?,
        value: Boolean
    ): Boolean {
        return mHolder.encode(key, value)
    }

    override fun putString(
        key: String?,
        value: String?
    ): Boolean {
        var content: String? = value
        if (value != null && mConfig.cipher != null) {
            val bytes = mConfig.cipher.encrypt(ConvertUtils.toBytes(value))
            content = ConvertUtils.newString(bytes)
        }
        return mHolder.encode(key, content)
    }

    override fun <T : Any?> putEntity(
        key: String?,
        value: T
    ): Boolean {
        return putString(key, DevJSONEngine.getEngine().toJson(value))
    }

    // =======
    // = 获取 =
    // =======

    override fun getInt(key: String?): Int {
        return getInt(key, 0)
    }

    override fun getLong(key: String?): Long {
        return getLong(key, 0L)
    }

    override fun getFloat(key: String?): Float {
        return getFloat(key, 0f)
    }

    override fun getDouble(key: String?): Double {
        return getDouble(key, 0.0)
    }

    override fun getBoolean(key: String?): Boolean {
        return getBoolean(key, false)
    }

    override fun getString(key: String?): String? {
        return getString(key, null)
    }

    override fun <T : Any?> getEntity(
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
        return mHolder.decodeInt(key, defaultValue)
    }

    override fun getLong(
        key: String?,
        defaultValue: Long
    ): Long {
        return mHolder.decodeLong(key, defaultValue)
    }

    override fun getFloat(
        key: String?,
        defaultValue: Float
    ): Float {
        return mHolder.decodeFloat(key, defaultValue)
    }

    override fun getDouble(
        key: String?,
        defaultValue: Double
    ): Double {
        return mHolder.decodeDouble(key, defaultValue)
    }

    override fun getBoolean(
        key: String?,
        defaultValue: Boolean
    ): Boolean {
        return mHolder.decodeBool(key, defaultValue)
    }

    override fun getString(
        key: String?,
        defaultValue: String?
    ): String? {
        var content: String? = mHolder.decodeString(key, null) ?: return defaultValue
        if (content != null && mConfig.cipher != null) {
            val bytes = mConfig.cipher.decrypt(ConvertUtils.toBytes(content))
            content = ConvertUtils.newString(bytes)
        }
        return content
    }

    override fun <T : Any?> getEntity(
        key: String?,
        typeOfT: Type?,
        defaultValue: T
    ): T {
        val json = getString(key, null)
        return DevJSONEngine.getEngine().fromJson<T>(
            json, typeOfT
        ) as? T ?: return defaultValue
    }
}