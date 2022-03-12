package afkt.project.feature.dev_widget.recy_item_decoration.grid

import afkt.project.databinding.IncludeGridItemDecorationAssistBinding
import afkt.project.utils.AppSize
import androidx.recyclerview.widget.RecyclerView
import dev.utils.app.RecyclerViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.ColorUtils
import dev.utils.common.RandomUtils
import dev.widget.decoration.BaseGridItemDecoration
import dev.widget.decoration.grid.GridColumnItemDecoration
import java.util.concurrent.atomic.AtomicInteger

/**
 * detail: DevWidget ItemDecoration 演示通用处理辅助类
 * @author Ttt
 */
internal class GridItemDecorationAssist(
    private val recyclerView: RecyclerView,
    private val binding: IncludeGridItemDecorationAssistBinding
) {

    // 最大添加数量
    private val MAX = 3

    // 列分割线处理 ItemDecoration
    private val columnList = mutableListOf<BaseGridItemDecoration>()

    // 递增数
    private var columnIndex = AtomicInteger()

    // ============
    // = 初始化数据 =
    // ============

    init {
        val lineHeight = AppSize.dp2pxf(10.0F)
        val columnHeight = AppSize.dp2pxf(10.0F)
        val spanCount = RecyclerViewUtils.getSpanCount(recyclerView)
        val vertical = RecyclerViewUtils.canScrollVertically(recyclerView)

        if (vertical) {
            columnList.add(GridColumnItemDecoration(spanCount, columnHeight, ColorUtils.CHOCOLATE))
            columnList.add(GridColumnItemDecoration(spanCount, columnHeight, ColorUtils.CYAN))
            columnList.add(GridColumnItemDecoration(spanCount, columnHeight, ColorUtils.ORANGE))
        } else {
        }
        // 通用设置 ItemDecoration 左右边距
        if (RandomUtils.nextBoolean()) {
            setItemLeftRight()
        }
        initListener()
    }

    /**
     * 通用设置 ItemDecoration 左右边距
     */
    private fun setItemLeftRight() {
//        columnList.forEachIndexed { index, item ->
//            item.setLineLeftRight(
//                AppSize.dp2pxf((index + 1) * 5.0F),
//                AppSize.dp2pxf((index + 1) * 5.0F),
//            )
//        }
    }

    /**
     * 初始化事件
     */
    private fun initListener() {
        binding.vidColumnAddBtn.setOnClickListener {
            addItemDecoration(columnList, columnIndex)
        }
        binding.vidColumnRemoveBtn.setOnClickListener {
            removeItemDecoration(columnList, columnIndex)
        }
    }

    /**
     * 通用添加 ItemDecoration 方法
     * @param list List<BaseGridItemDecoration>
     * @param index AtomicInteger
     */
    private fun addItemDecoration(
        list: List<BaseGridItemDecoration>,
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
     * @param list List<BaseGridItemDecoration>
     * @param index AtomicInteger
     */
    private fun removeItemDecoration(
        list: List<BaseGridItemDecoration>,
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
     * @param list List<BaseGridItemDecoration>
     * @param number Int
     */
    private fun calcOffset(
        list: List<BaseGridItemDecoration>,
        number: Int
    ) {
        val vertical = RecyclerViewUtils.canScrollVertically(recyclerView)
        val numberIndex = number - 1
        list.forEachIndexed { index, item ->
            if (vertical) {
                item.columnOffset = 0.0F
                if (numberIndex >= index) {
                    // 偏差值 ( 用于解决多个 ItemDecoration 叠加覆盖问题 )
                    var offset = 0.0F
                    for (i in (index + 1)..numberIndex) {
                        offset += list[i].columnHeight
                    }
                    // 设置偏差值
                    item.columnOffset = offset
                }
            } else {
                item.rowOffset = 0.0F
                if (numberIndex >= index) {
                    // 偏差值 ( 用于解决多个 ItemDecoration 叠加覆盖问题 )
                    var offset = 0.0F
                    for (i in (index + 1)..numberIndex) {
                        offset += list[i].rowHeight
                    }
                    // 设置偏差值
                    item.rowOffset = offset
                }
            }
        }
    }
}