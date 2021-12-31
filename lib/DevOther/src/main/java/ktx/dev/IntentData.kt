package ktx.dev

import android.content.Intent
import android.os.Bundle
import dev.base.DevIntent

/**
 * detail: Intent 传参读写辅助类
 * @author Ttt
 * 在 [DevIntent] 基础上新增, 根据 DevFinal.STR 自动生成通用方法
 * 通过 IntentDataKotlinGenerateMain 生成, 自行 copy 代码到该类
 */
class IntentData private constructor() {

    // ==========
    // = 静态方法 =
    // ==========

    companion object {

        /**
         * 创建 IntentData
         * @return [IntentData]
         */
        fun with(): IntentData {
            return IntentData()
        }

        /**
         * 创建 IntentData
         * @param intent [Intent]
         * @return [IntentData]
         */
        fun with(intent: Intent?): IntentData {
            return IntentData().reader(intent)
        }

        /**
         * 创建 IntentData
         * @param bundle [Bundle]
         * @return [IntentData]
         */
        fun with(bundle: Bundle?): IntentData {
            return IntentData().reader(bundle)
        }
    }

    // =============
    // = 对外公开方法 =
    // =============

    // Intent 传参读写辅助类
    private val mIntent = DevIntent.with()

    /**
     * 插入数据
     * @param intent [Intent]
     * @return [Intent]
     */
    fun insert(intent: Intent?): Intent? {
        return mIntent.insert(intent)
    }

    /**
     * 插入数据
     * @return [Bundle]
     */
    fun insert(): Bundle? {
        return mIntent.insert()
    }

    /**
     * 插入数据
     * @param bundle [Bundle]
     * @return [Bundle]
     */
    fun insert(bundle: Bundle?): Bundle? {
        return mIntent.insert(bundle)
    }

    // =

    /**
     * 读取数据并存储
     * @param intent [Intent]
     * @return [IntentData]
     */
    fun reader(intent: Intent?): IntentData {
        mIntent.reader(intent)
        return this
    }

    /**
     * 读取数据并存储
     * @param bundle [Bundle]
     * @return [IntentData]
     */
    fun reader(bundle: Bundle?): IntentData {
        mIntent.reader(bundle)
        return this
    }

    // =======
    // = 通用 =
    // =======

    /**
     * 获取存储数据 Map
     * @return 存储数据 Map
     */
    fun getDataMaps(): Map<String?, String?> {
        return mIntent.dataMaps
    }

    /**
     * 是否存在 Key
     * @param key 保存的 key
     * @return `true` yes, `false` no
     */
    fun containsKey(key: String?): Boolean {
        return mIntent.containsKey(key)
    }

    /**
     * 是否存在 Value
     * @param value 保存的 value
     * @return `true` yes, `false` no
     */
    fun containsValue(value: String?): Boolean {
        return mIntent.containsValue(value)
    }

    /**
     * 对应 Key 保存的 Value 是否为 null
     * @param key 保存的 key
     * @return `true` yes, `false` no
     */
    fun isNullValue(key: String?): Boolean {
        return mIntent.isNullValue(key)
    }

    /**
     * 保存数据
     * @param key   保存的 key
     * @param value 保存的 value
     * @return [IntentData]
     */
    fun put(
        key: String?,
        value: String?
    ): IntentData {
        mIntent.put(key, value)
        return this
    }

    /**
     * 保存集合数据
     * @param map [Map]
     * @return [IntentData]
     */
    fun putAll(map: Map<String?, String?>?): IntentData {
        mIntent.putAll(map)
        return this
    }

    /**
     * 移除数据
     * @param key 保存的 key
     * @return [IntentData]
     */
    fun remove(key: String?): IntentData {
        mIntent.remove(key)
        return this
    }

    /**
     * 移除集合数据
     * @param keys 保存的 key 集合
     * @return [IntentData]
     */
    fun removeAll(keys: List<String?>?): IntentData {
        mIntent.removeAll(keys)
        return this
    }

    /**
     * 获取对应 Key 保存的 Value
     * @param key 保存的 key
     * @return 保存的 value
     */
    fun get(key: String?): String? {
        return mIntent.get(key)
    }

    /**
     * 清空数据
     * @return [IntentData]
     */
    fun clear(): IntentData {
        mIntent.clear()
        return this
    }

    // =

    /**
     * 清除 null 数据
     * @return [IntentData]
     * key、value 只要其中一个为 null 就清除
     */
    fun clearNull(): IntentData {
        mIntent.clearNull()
        return this
    }

    /**
     * 清除 null Key 数据
     * @return [IntentData]
     */
    fun clearNullKey(): IntentData {
        mIntent.clearNullKey()
        return this
    }

    /**
     * 清除 null Value 数据
     * @return [IntentData]
     * value 只要为 null 就清除
     */
    fun clearNullValue(): IntentData {
        mIntent.clearNullValue()
        return this
    }

    // =

    /**
     * 清除 empty 数据
     * @return [IntentData]
     * key、value 只要其中一个为 empty ( null、"" ) 就清除
     */
    fun clearEmpty(): IntentData {
        mIntent.clearEmpty()
        return this
    }

    /**
     * 清除 empty Key 数据
     * @return [IntentData]
     */
    fun clearEmptyKey(): IntentData {
        mIntent.clearEmptyKey()
        return this
    }

    /**
     * 清除 empty Value 数据
     * @return [IntentData]
     * value 只要为 empty ( null、"" ) 就清除
     */
    fun clearEmptyValue(): IntentData {
        mIntent.clearEmptyValue()
        return this
    }

    // ====================
    // = 以下属于自动生成代码 =
    // ====================
}