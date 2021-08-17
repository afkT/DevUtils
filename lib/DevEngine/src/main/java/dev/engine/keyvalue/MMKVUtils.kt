package dev.engine.keyvalue

import android.content.Context
import android.os.Parcelable
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel
import dev.engine.keyvalue.MMKVUtils.defaultHolder
import dev.engine.keyvalue.MMKVUtils.get
import dev.utils.LogPrintUtils
import dev.utils.common.StringUtils
import java.util.*
import kotlin.collections.set

/**
 * 初始化方法 ( 必须调用 )
 * @param context [Context]
 */
fun mmkvInitialize(context: Context?) {
    // 初始化 MMKV 并设置日志级别
    MMKV.initialize(context, MMKVLogLevel.LevelNone)
}

/**
 * detail: MMKV 工具类
 * @author Ttt
 * 支持组件化 module 存储、以及默认通用 mmkv 对象
 * 基于 mmap 的高性能通用 key-value 组件
 * @see https://github.com/Tencent/MMKV/blob/master/readme_cn.md
 * Google 再见 SharedPreferences 拥抱 Jetpack DataStore
 * @see https://juejin.im/post/6881442312560803853
 * Google 再见 SharedPreferences 拥抱 Jetpack DataStore
 * @see https://juejin.im/post/6888847647802097672
 * 使用:
 * [defaultHolder].encode/decodeXxx
 * [get].encode/decodeXxx
 */
internal object MMKVUtils {

    // 日志 TAG
    private val TAG = MMKVUtils::class.java.simpleName

    // 持有类存储 Key-Holder
    private val HOLDER_MAP: MutableMap<String, Holder> = HashMap()

    // Default MMKV Holder
    private val DEFAULT_HOLDER: Holder by lazy {
        val mmkv = MMKV.mmkvWithID(TAG, MMKV.MULTI_PROCESS_MODE)
        Holder(mmkv)
    }

    /**
     * 初始化方法 ( 必须调用 )
     * @param context [Context]
     */
    fun initialize(context: Context?) {
        // 初始化 MMKV 并设置日志级别
        val rootDir = MMKV.initialize(context, MMKVLogLevel.LevelNone)
        LogPrintUtils.dTag(TAG, "MMKV rootDir: %s", rootDir)

//        // 设置打印日志级别
//        MMKV.setLogLevel(MMKVLogLevel.LevelNone)

//        https://github.com/Tencent/MMKV/wiki/android_advance_cn
//        // 视项目需求进行注册监听、数据恢复等
//        MMKV.registerHandler()
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 是否存在指定 Key 的 MMKV Holder
     * @param key Key
     * @return `true` yes, `false` no
     */
    fun containsMMKV(key: String): Boolean {
        return HOLDER_MAP.containsKey(key)
    }

    /**
     * 通过 Key 获取 MMKV Holder
     * @param key Key
     * @return [Holder]
     */
    operator fun get(key: String): Holder {
        return if (containsMMKV(key)) HOLDER_MAP[key]!! else putHolder(key)
    }

    /**
     * 保存自定义 MMKV Holder
     * @param key Key
     * @return [Holder]
     */
    fun putHolder(key: String): Holder {
        return putHolder(key, MMKV.mmkvWithID(key, MMKV.MULTI_PROCESS_MODE))
    }

    /**
     * 保存自定义 MMKV Holder
     * @param key  Key
     * @param mmkv [MMKV]
     * @return [Holder]
     */
    fun putHolder(
        key: String,
        mmkv: MMKV?
    ): Holder {
        val holder = Holder(mmkv)
        HOLDER_MAP[key] = holder
        return holder
    }

    // =

    /**
     * 获取 Default MMKV Holder
     * @return [Holder]
     */
    fun defaultHolder(): Holder {
        return DEFAULT_HOLDER
    }

    // ============
    // = 内部封装类 =
    // ============

    /**
     * detail: MMKV 持有类
     * @author Ttt
     * 提供常用方法, 可根据需求自行添加修改或通过 [Holder].mmkv 进行操作
     */
    class Holder(
        // MMKV
        val mmkv: MMKV?
    ) {

        /**
         * 获取 MMKV mmap id
         * @return mmap id
         */
        fun mmapID(): String? {
            return mmkv?.mmapID()
        }

        // ==========
        // = 其他操作 =
        // ==========

        /**
         * 判断 MMKV 是否为 null
         * @return `true` yes, `false` no
         */
        fun isMMKVEmpty(): Boolean {
            return mmkv == null
        }

        /**
         * 判断 MMKV 是否不为 null
         * @return `true` yes, `false` no
         */
        fun isMMKVNotEmpty(): Boolean {
            return mmkv != null
        }

        /**
         * 是否存在指定 Key value
         * @param key Key
         * @return `true` yes, `false` no
         */
        fun containsKey(key: String?): Boolean {
            if (StringUtils.isEmpty(key)) return false
            return mmkv?.containsKey(key) ?: false
        }

        /**
         * 通过 key 移除 value
         * @param key Key
         * @return `true` success, `false` fail
         */
        fun removeValueForKey(key: String?): Boolean {
            if (isMMKVEmpty()) return false
            if (StringUtils.isEmpty(key)) return false
            mmkv?.removeValueForKey(key)
            return true
        }

        /**
         * 通过 key 数组移除 value
         * @param keys key 数组
         * @return `true` success, `false` fail
         */
        fun removeValuesForKeys(keys: Array<String?>?): Boolean {
            if (isMMKVEmpty()) return false
            if (keys == null) return false
            mmkv?.removeValuesForKeys(keys)
            return true
        }

        /**
         * 同步操作
         * @return `true` success, `false` fail
         */
        fun sync(): Boolean {
            mmkv?.let {
                it.sync()
                return true
            }
            return false
        }

        /**
         * 异步操作
         * @return `true` success, `false` fail
         */
        fun async(): Boolean {
            mmkv?.let {
                it.async()
                return true
            }
            return false
        }

        /**
         * 清除全部数据
         * @return `true` success, `false` fail
         */
        fun clear(): Boolean {
            mmkv?.let {
                it.clear()
                return true
            }
            return false
        }

        // =======
        // = 存储 =
        // =======

        fun encode(
            key: String?,
            value: Boolean
        ): Boolean {
            if (StringUtils.isEmpty(key)) return false
            return mmkv?.encode(key, value) ?: false
        }

        fun encode(
            key: String?,
            value: Int
        ): Boolean {
            if (StringUtils.isEmpty(key)) return false
            return mmkv?.encode(key, value) ?: false
        }

        fun encode(
            key: String?,
            value: Long
        ): Boolean {
            if (StringUtils.isEmpty(key)) return false
            return mmkv?.encode(key, value) ?: false
        }

        fun encode(
            key: String?,
            value: Float
        ): Boolean {
            if (StringUtils.isEmpty(key)) return false
            return mmkv?.encode(key, value) ?: false
        }

        fun encode(
            key: String?,
            value: Double
        ): Boolean {
            if (StringUtils.isEmpty(key)) return false
            return mmkv?.encode(key, value) ?: false
        }

        fun encode(
            key: String?,
            value: String?
        ): Boolean {
            if (StringUtils.isEmpty(key)) return false
            return mmkv?.encode(key, value) ?: false
        }

        fun encode(
            key: String?,
            value: Set<String?>?
        ): Boolean {
            if (value == null) return false
            if (StringUtils.isEmpty(key)) return false
            return mmkv?.encode(key, value) ?: false
        }

        fun encode(
            key: String?,
            value: ByteArray?
        ): Boolean {
            if (value == null) return false
            if (StringUtils.isEmpty(key)) return false
            return mmkv?.encode(key, value) ?: false
        }

        fun encode(
            key: String?,
            value: Parcelable?
        ): Boolean {
            if (value == null) return false
            if (StringUtils.isEmpty(key)) return false
            return mmkv?.encode(key, value) ?: false
        }

        // =======
        // = 读取 =
        // =======

        fun decodeBool(key: String?): Boolean {
            return decodeBool(key, false)
        }

        fun decodeBool(
            key: String?,
            defaultValue: Boolean
        ): Boolean {
            if (StringUtils.isEmpty(key)) return defaultValue
            return mmkv?.decodeBool(key, defaultValue) ?: defaultValue
        }

        fun decodeInt(key: String?): Int {
            return decodeInt(key, 0)
        }

        fun decodeInt(
            key: String?,
            defaultValue: Int
        ): Int {
            if (StringUtils.isEmpty(key)) return defaultValue
            return mmkv?.decodeInt(key, defaultValue) ?: defaultValue
        }

        fun decodeLong(key: String?): Long {
            return decodeLong(key, 0L)
        }

        fun decodeLong(
            key: String?,
            defaultValue: Long
        ): Long {
            if (StringUtils.isEmpty(key)) return defaultValue
            return mmkv?.decodeLong(key, defaultValue) ?: defaultValue
        }

        fun decodeFloat(key: String?): Float {
            return decodeFloat(key, 0.0f)
        }

        fun decodeFloat(
            key: String?,
            defaultValue: Float
        ): Float {
            if (StringUtils.isEmpty(key)) return defaultValue
            return mmkv?.decodeFloat(key, defaultValue) ?: defaultValue
        }

        fun decodeDouble(key: String?): Double {
            return decodeDouble(key, 0.0)
        }

        fun decodeDouble(
            key: String?,
            defaultValue: Double
        ): Double {
            if (StringUtils.isEmpty(key)) return defaultValue
            return mmkv?.decodeDouble(key, defaultValue) ?: defaultValue
        }

        fun decodeString(key: String?): String? {
            return decodeString(key, null)
        }

        fun decodeString(
            key: String?,
            defaultValue: String?
        ): String? {
            if (StringUtils.isEmpty(key)) return defaultValue
            return mmkv?.decodeString(key, defaultValue) ?: defaultValue
        }

        fun decodeStringSet(key: String?): Set<String?>? {
            return decodeStringSet(key, null)
        }

        fun decodeStringSet(
            key: String?,
            defaultValue: Set<String?>?
        ): Set<String?>? {
            return decodeStringSet(key, defaultValue, HashSet::class.java)
        }

        fun decodeStringSet(
            key: String?,
            defaultValue: Set<String?>?,
            cls: Class<out Set<*>?>?
        ): Set<String?>? {
            if (StringUtils.isEmpty(key)) return defaultValue
            return mmkv?.decodeStringSet(key, defaultValue, cls) ?: defaultValue
        }

        fun decodeBytes(key: String?): ByteArray? {
            return decodeBytes(key, null)
        }

        fun decodeBytes(
            key: String?,
            defaultValue: ByteArray?
        ): ByteArray? {
            if (StringUtils.isEmpty(key)) return defaultValue
            return mmkv?.decodeBytes(key, defaultValue) ?: defaultValue
        }

        fun <T : Parcelable?> decodeParcelable(
            key: String?,
            tClass: Class<T>?
        ): T? {
            return decodeParcelable(key, tClass, null)
        }

        fun <T : Parcelable?> decodeParcelable(
            key: String?,
            tClass: Class<T>?,
            defaultValue: T?
        ): T? {
            if (StringUtils.isEmpty(key)) return defaultValue
            return mmkv?.decodeParcelable(key, tClass, defaultValue) ?: defaultValue
        }
    }
}