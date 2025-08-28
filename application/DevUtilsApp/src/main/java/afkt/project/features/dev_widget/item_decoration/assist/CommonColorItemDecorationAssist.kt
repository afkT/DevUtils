package afkt.project.features.dev_widget.item_decoration.assist

import androidx.recyclerview.widget.RecyclerView
import dev.simple.core.utils.size.AppSize
import dev.utils.app.helper.quick.QuickHelper
import dev.widget.decoration.BaseColorItemDecoration
import java.util.concurrent.atomic.AtomicInteger

/**
 * detail: DevWidget Color ItemDecoration 演示通用处理辅助类
 * @author Ttt
 */
class CommonColorItemDecorationAssist(
    private val recyclerView: RecyclerView,
    // 最大添加数量
    private val MAX: Int = 3
) {

    /**
     * 循环设置 Item Left、Right
     * @param list ItemDecoration List
     */
    fun forItemLeftRight(vararg list: List<BaseColorItemDecoration>) {
        list.forEach {
            it.forEachIndexed { index, item ->
                val value = AppSize.dp2pxf((index + 1) * 5.0F)
                item.setLeftRight(value, value)
            }
        }
    }

    /**
     * 通用添加 ItemDecoration 方法
     * @param list ItemDecoration List
     * @param index AtomicInteger
     */
    fun addItemDecoration(
        list: List<BaseColorItemDecoration>,
        index: AtomicInteger
    ) {
        if (index.get() >= MAX) {
            return
        }
        // 计算偏差值
        calcOffset(list, index.get() + 1)
        // 添加 ItemDecoration
        QuickHelper.get(recyclerView)
            .addItemDecoration(list[index.get()])
            .notifyDataSetChanged()
        index.incrementAndGet()
    }

    /**
     * 通用移除 ItemDecoration 方法
     * @param list ItemDecoration List
     * @param index AtomicInteger
     */
    fun removeItemDecoration(
        list: List<BaseColorItemDecoration>,
        index: AtomicInteger
    ) {
        if (index.get() <= 0) {
            return
        }
        index.decrementAndGet()
        // 计算偏差值
        calcOffset(list, index.get())
        // 移除 ItemDecoration
        QuickHelper.get(recyclerView)
            .removeItemDecoration(list[index.get()])
            .notifyDataSetChanged()
    }

    // =

    /**
     * 计算 Item 偏差值
     * @param list ItemDecoration List
     * @param number Int
     */
    private fun calcOffset(
        list: List<BaseColorItemDecoration>,
        number: Int
    ) {
        val numberIndex = number - 1
        list.forEachIndexed { index, item ->
            // 先重置为 0
            item.offset = 0.0F
            if (numberIndex >= index) {
                // 偏差值 ( 用于解决多个 ItemDecoration 叠加覆盖问题 )
                var offset = 0.0F
                for (i in (index + 1)..numberIndex) {
                    offset += list[i].height
                }
                // 设置偏差值
                item.offset = offset
            }
        }
    }
}