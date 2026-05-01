package dev.simple.core.app

import android.content.Intent
import android.os.Bundle
import dev.base.DevIntent

/**
 * detail: 基础 Intent 传参读写辅助类通用方法封装
 * @author Ttt
 */
abstract class BaseIntent<T> {

    /**
     * 返回 T 链式调用
     * @return [T]
     */
    abstract fun returnT(): T

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 创建 [DevIntent] 实现
     * 子类可重写以替换 Intent 传参读写辅助类构造方式
     * 注意: 该方法会在构造阶段被调用, 实现不应依赖子类自身状态
     */
    protected open fun newDevIntent(): DevIntent = DevIntent.with()

    // Intent 传参读写辅助类 ( 子类可重写 newDevIntent() 替换实现 )
    @Suppress("LeakingThis")
    @JvmField
    protected val mIntent: DevIntent = newDevIntent()

    /**
     * 插入数据
     * @param intent [android.content.Intent]
     * @return [android.content.Intent]
     */
    open fun insert(intent: Intent?): Intent? {
        return mIntent.insert(intent)
    }

    /**
     * 插入数据
     * @return [android.os.Bundle]
     */
    open fun insert(): Bundle? {
        return mIntent.insert()
    }

    /**
     * 插入数据
     * @param bundle [Bundle]
     * @return [Bundle]
     */
    open fun insert(bundle: Bundle?): Bundle? {
        return mIntent.insert(bundle)
    }

    // =

    /**
     * 读取数据并存储
     * @param intent [Intent]
     * @return [T]
     */
    open fun reader(intent: Intent?): T {
        mIntent.reader(intent)
        return returnT()
    }

    /**
     * 读取数据并存储
     * @param bundle [Bundle]
     * @return [T]
     */
    open fun reader(bundle: Bundle?): T {
        mIntent.reader(bundle)
        return returnT()
    }

    // =======
    // = 通用 =
    // =======

    /**
     * 获取存储数据 Map
     * @return 存储数据 Map
     */
    open fun getDataMaps(): Map<String?, String?> {
        return mIntent.dataMaps
    }

    /**
     * 是否存在 Key
     * @param key 保存的 key
     * @return `true` yes, `false` no
     */
    open fun containsKey(key: String?): Boolean {
        return mIntent.containsKey(key)
    }

    /**
     * 是否存在 Value
     * @param value 保存的 value
     * @return `true` yes, `false` no
     */
    open fun containsValue(value: String?): Boolean {
        return mIntent.containsValue(value)
    }

    /**
     * 对应 Key 保存的 Value 是否为 null
     * @param key 保存的 key
     * @return `true` yes, `false` no
     */
    open fun isNullValue(key: String?): Boolean {
        return mIntent.isNullValue(key)
    }

    /**
     * 保存数据
     * @param key   保存的 key
     * @param value 保存的 value
     * @return [T]
     */
    open fun put(
        key: String?,
        value: String?
    ): T {
        mIntent.put(key, value)
        return returnT()
    }

    /**
     * 保存集合数据
     * @param map [Map]
     * @return [T]
     */
    open fun putAll(map: Map<String?, String?>?): T {
        mIntent.putAll(map)
        return returnT()
    }

    /**
     * 移除数据
     * @param key 保存的 key
     * @return [T]
     */
    open fun remove(key: String?): T {
        mIntent.remove(key)
        return returnT()
    }

    /**
     * 移除集合数据
     * @param keys 保存的 key 集合
     * @return [T]
     */
    open fun removeAll(keys: List<String?>?): T {
        mIntent.removeAll(keys)
        return returnT()
    }

    /**
     * 获取对应 Key 保存的 Value
     * @param key 保存的 key
     * @return 保存的 value
     */
    open fun get(key: String?): String? {
        return mIntent.get(key)
    }

    /**
     * 清空数据
     * @return [T]
     */
    open fun clear(): T {
        mIntent.clear()
        return returnT()
    }

    // =

    /**
     * 清除 null 数据
     * @return [T]
     * key、value 只要其中一个为 null 就清除
     */
    open fun clearNull(): T {
        mIntent.clearNull()
        return returnT()
    }

    /**
     * 清除 null Key 数据
     * @return [T]
     */
    open fun clearNullKey(): T {
        mIntent.clearNullKey()
        return returnT()
    }

    /**
     * 清除 null Value 数据
     * @return [T]
     * value 只要为 null 就清除
     */
    open fun clearNullValue(): T {
        mIntent.clearNullValue()
        return returnT()
    }

    // =

    /**
     * 清除 empty 数据
     * @return [T]
     * key、value 只要其中一个为 empty ( null、"" ) 就清除
     */
    open fun clearEmpty(): T {
        mIntent.clearEmpty()
        return returnT()
    }

    /**
     * 清除 empty Key 数据
     * @return [T]
     */
    open fun clearEmptyKey(): T {
        mIntent.clearEmptyKey()
        return returnT()
    }

    /**
     * 清除 empty Value 数据
     * @return [T]
     * value 只要为 empty ( null、"" ) 就清除
     */
    open fun clearEmptyValue(): T {
        mIntent.clearEmptyValue()
        return returnT()
    }
}