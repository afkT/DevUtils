package dev.simple.core.adapter

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import dev.utils.common.RandomUtils

/**
 * detail: Adapter 模型
 * @author Ttt
 */
open class AdapterModel<T> {

    // 数据源
    @JvmField
    val items = ObservableArrayList<T>()

    // 是否存在数据
    @JvmField
    val existItems = ObservableBoolean(false)

    // ==========
    // = 通用方法 =
    // ==========

    /**
     *【刷新】是否存在数据状态
     */
    open fun refreshExist() {
        existItems.set(isNotEmpty())
    }

    /**
     *【获取】MutableList 数据源
     */
    open fun mutableList(): MutableList<T> {
        return items.toMutableList()
    }

    /**
     *【获取】MutableSet 数据源
     */
    open fun mutableSet(): MutableSet<T> {
        return items.toMutableSet()
    }

    // ==========
    // = 获取方法 =
    // ==========

    /**
     * 获取数据总数
     */
    open fun count(): Int {
        return items.size
    }

    /**
     * 判断数据长度是否等于期望长度
     * @param length     指定长度
     * @return `true` yes, `false` no
     */
    open fun isLength(length: Int): Boolean {
        return count() == length
    }

    /**
     * 判断数据长度是否大于指定长度
     * @param length     指定长度
     * @return `true` yes, `false` no
     */
    open fun greaterThan(length: Int): Boolean {
        return count() > length
    }

    /**
     * 判断数据长度长度是否大于等于指定长度
     * @param length     指定长度
     * @return `true` yes, `false` no
     */
    open fun greaterThanOrEqual(length: Int): Boolean {
        return count() >= length
    }


    // =
    /**
     * 判断数据长度长度是否小于指定长度
     * @param length     指定长度
     * @return `true` yes, `false` no
     */
    open fun lessThan(length: Int): Boolean {
        return count() < length
    }

    /**
     * 判断数据长度长度是否小于等于指定长度
     * @param length     指定长度
     * @return `true` yes, `false` no
     */
    open fun lessThanOrEqual(length: Int): Boolean {
        return count() <= length
    }

    /**
     * 是否不存在数据
     * @return `true` yes, `false` no
     */
    open fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    /**
     * 是否存在数据
     * @return `true` yes, `false` no
     */
    open fun isNotEmpty(): Boolean {
        return items.isNotEmpty()
    }

    /**
     * 只有一条数据时才进行返回
     * @return 单条数据
     */
    open fun singleOrNull(): T? {
        return items.singleOrNull()
    }

    /**
     * 获取第一条数据
     */
    open fun firstOrNull(): T? {
        return items.firstOrNull()
    }

    /**
     * 获取最后一条数据
     */
    open fun lastOrNull(): T? {
        return items.lastOrNull()
    }

    /**
     * 获取对应索引数据
     * @param index 索引
     * @return 指定索引数据
     */
    open fun getOrNull(index: Int): T? {
        return try {
            items.getOrNull(index)
        } catch (_: Exception) {
            null
        }
    }

    /**
     * 随机获取指定索引数据
     * @param min 最小随机数
     * @param max 最大随机数
     */
    open fun random(
        min: Int = 0,
        max: Int = count() - 1
    ): T? {
        val index = RandomUtils.getRandom(min, max)
        return getOrNull(index)
    }

    // ==========
    // = 数据操作 =
    // ==========

    /**
     * 清空全部数据
     * @param clearData 是否清空全部
     */
    open fun clear(clearData: Boolean = true) {
        if (clearData) {
            try {
                items.clear()
            } catch (_: Exception) {
            }
        }
    }

    // =====
    // = 增 =
    // =====

    /**
     * 添加一条数据
     * @param value 数据
     * @param clearData 是否清空全部
     * @return `true` success, `false` fail
     */
    open fun add(
        value: T?,
        clearData: Boolean = false
    ): Boolean {
        clear(clearData)
        if (value != null) {
            try {
                items.add(value)
                return true
            } catch (_: Exception) {
            }
        }
        return false
    }

    /**
     * 清空全部数据并重新添加一条数据
     * @param value 数据
     * @return `true` success, `false` fail
     */
    open fun addAndClear(value: T?): Boolean {
        return add(value, true)
    }

    /**
     * 添加多条数据
     * @param values 数据源
     * @param clearData 是否清空全部
     * @return `true` success, `false` fail
     */
    open fun addAll(
        values: Collection<T>?,
        clearData: Boolean = false
    ): Boolean {
        clear(clearData)
        if (values != null) {
            try {
                items.addAll(values)
                return true
            } catch (_: Exception) {
            }
        }
        return false
    }

    /**
     * 清空全部数据并重新添加多条数据
     * @param values 数据源
     * @return `true` success, `false` fail
     */
    open fun addAllAndClear(values: Collection<T>?): Boolean {
        return addAll(values, true)
    }

    /**
     * 添加一条数据到第一条
     * @param value 数据
     * @return `true` success, `false` fail
     */
    open fun addFirst(value: T?): Boolean {
        return addAt(0, value)
    }

    /**
     * 添加一条数据到最后一条
     * @param value 数据
     * @return `true` success, `false` fail
     */
    open fun addLast(value: T?): Boolean {
        return add(value)
    }

    /**
     * 添加一条数据到指定索引
     * @param index 索引
     * @param value 数据
     * @return `true` success, `false` fail
     */
    open fun addAt(
        index: Int,
        value: T?
    ): Boolean {
        if (index < 0) return false
        if (value != null) {
            try {
                items.add(index, value)
                return true
            } catch (_: Exception) {
            }
        }
        return false
    }

    // =====
    // = 删 =
    // =====

    /**
     * 移除一条数据
     * @return `true` success, `false` fail
     */
    open fun remove(value: T?): Boolean {
        return try {
            items.remove(value)
        } catch (_: Exception) {
            false
        }
    }

    /**
     * 移除一条数据
     * @param index 索引
     * @return `true` success, `false` fail
     */
    open fun removeAt(index: Int): Boolean {
        if (index < 0 || index >= count()) return false
        try {
            items.removeAt(index)
            return true
        } catch (_: Exception) {
        }
        return false
    }

    /**
     * 移除指定集合数据
     * @return `true` success, `false` fail
     */
    open fun removeAll(values: Collection<T>?): Boolean {
        if (values != null) {
            try {
                return items.removeAll(values)
            } catch (_: Exception) {
            }
        }
        return false
    }

    /**
     * 移除第一条数据并返回
     * @return 第一条数据
     */
    open fun removeFirstOrNull(): T? {
        return items.removeFirstOrNull()
    }

    /**
     * 移除最后一条数据并返回
     * @return 最后一条数据
     */
    open fun removeLastOrNull(): T? {
        return items.removeLastOrNull()
    }

    // =====
    // = 改 =
    // =====

    /**
     * 修改指定索引数据
     * @param index 索引
     * @param value 数据
     * @return `true` success, `false` fail
     */
    open fun setAt(
        index: Int,
        value: T?
    ): Boolean {
        if (index < 0) return false
        if (value != null) {
            try {
                items[index] = value
                return true
            } catch (_: Exception) {
            }
        }
        return false
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 循环方法
     */
    open fun forEach(action: (T) -> Unit) {
        mutableList().forEach(action)
    }

    /**
     * 循环方法
     */
    open fun forEachIndexed(action: (index: Int, T) -> Unit) {
        mutableList().forEachIndexed(action)
    }

    /**
     * 循环方法
     */
    open fun forEachReturn(action: (T) -> Boolean) {
        val lists = mutableList()
        for (item in lists) {
            if (action(item)) return
        }
    }

    /**
     * 循环方法
     */
    open fun forEachIndexedReturn(action: (index: Int, T) -> Boolean) {
        val lists = mutableList()
        var index = 0
        for (item in lists) {
            if (action(index++, item)) return
        }
    }
}