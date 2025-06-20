package afkt.project.model.basic

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView.ItemAnimator

/**
 * detail: Adapter 模型
 * @author Ttt
 */
open class AdapterModel<T> {

    // 数据源
    val items = ObservableArrayList<T>()

    // Item 动画
    val itemAnimator = ObservableField<ItemAnimator>(DefaultItemAnimator())

    // ==========
    // = 通用方法 =
    // ==========

    /**
     * 获取对应索引数据
     */
    fun get(index: Int): T {
        return items[index]
    }

    /**
     * 获取对应索引数据 ( 可能返回 null )
     */
    fun getOrNull(index: Int): T? {
        return items.getOrNull(index)
    }

    /**
     * 添加一条数据
     */
    fun add(value: T) {
        items.add(value)
    }

    /**
     * 清空全部数据并重新添加一条
     */
    fun addAndClear(value: T) {
        items.clear()
        items.add(value)
    }

    /**
     * 添加多条数据
     * @param clear 是否清空全部
     */
    fun addAll(
        values: List<T>,
        clear: Boolean = false
    ) {
        if (clear) items.clear()
        items.addAll(values)
    }

    /**
     * 添加多条数据并清空全部数据
     */
    fun addAllAndClear(values: List<T>) {
        addAll(values, true)
    }

    /**
     * 移除一条数据
     */
    fun remove(value: T) {
        items.remove(value)
    }

    /**
     * 清空全部数据
     */
    fun clear(clear: Boolean = true) {
        if (clear) items.clear()
    }

    /**
     * 循环处理
     */
    fun forEach(action: (T) -> Unit) {
        items.forEach(action)
    }

    /**
     * 循环处理
     */
    fun forEachReturn(action: (T) -> Boolean) {
        for (item in items) {
            if (action(item)) return
        }
    }

    /**
     * 循环处理
     */
    fun newForEachReturn(action: (T) -> Boolean) {
        val newItems = items.toMutableList()
        for (item in newItems) {
            if (action(item)) return
        }
    }

    // ==============
    // = 对外公开方法 =
    // ==============

    /**
     * 获取数据总数
     */
    fun count(): Int {
        return items.size
    }

    /**
     * 是否不存在数据
     */
    fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    /**
     * 是否存在数据
     */
    fun isNotEmpty(): Boolean {
        return items.isNotEmpty()
    }

    /**
     * 获取第一条数据
     */
    fun first(): T? {
        return items.firstOrNull()
    }

    /**
     * 获取最后一条数据
     */
    fun last(): T? {
        return items.lastOrNull()
    }
}