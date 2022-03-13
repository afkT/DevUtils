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
import dev.widget.decoration.grid.GridRowItemDecoration
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

    // 首条数据顶部添加 列分割线处理 ItemDecoration
    private val firstColumnList = mutableListOf<BaseGridItemDecoration>()

    // 首条数据顶部添加 行分割线处理 ItemDecoration
    private val firstRowList = mutableListOf<BaseGridItemDecoration>()

    // 最后一条数据底部添加 列分割线处理 ItemDecoration
    private val lastColumnList = mutableListOf<BaseGridItemDecoration>()

    // 最后一条数据底部添加 行分割线处理 ItemDecoration
    private val lastRowList = mutableListOf<BaseGridItemDecoration>()

    // 每条数据底部添加 列分割线处理 ItemDecoration
    private val lineColumnList = mutableListOf<BaseGridItemDecoration>()

    // 每条数据底部添加 行分割线处理 ItemDecoration
    private val lineRowList = mutableListOf<BaseGridItemDecoration>()

    // 递增数
    private var firstColumnIndex = AtomicInteger()
    private var firstRowIndex = AtomicInteger()
    private var lastColumnIndex = AtomicInteger()
    private var lastRowIndex = AtomicInteger()
    private var lineColumnIndex = AtomicInteger()
    private var lineRowIndex = AtomicInteger()

    // ============
    // = 初始化数据 =
    // ============

    init {
        val columnHeight = AppSize.dp2pxf(10.0F)
        val rowHeight = AppSize.dp2pxf(10.0F)
        val spanCount = RecyclerViewUtils.getSpanCount(recyclerView)
        val vertical = RecyclerViewUtils.canScrollVertically(recyclerView)

        lineColumnList.add(
            GridColumnItemDecoration(vertical, spanCount, columnHeight, ColorUtils.CHOCOLATE)
        )
        lineColumnList.add(
            GridColumnItemDecoration(vertical, spanCount, columnHeight, ColorUtils.CYAN)
        )
        lineColumnList.add(
            GridColumnItemDecoration(vertical, spanCount, columnHeight, ColorUtils.ORANGE)
        )

        lineRowList.add(
            GridRowItemDecoration(vertical, spanCount, rowHeight, ColorUtils.CHOCOLATE)
        )
        lineRowList.add(
            GridRowItemDecoration(vertical, spanCount, rowHeight, ColorUtils.CYAN)
        )
        lineRowList.add(
            GridRowItemDecoration(vertical, spanCount, rowHeight, ColorUtils.ORANGE)
        )

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
        forItemLeftRight(firstColumnList, true)
        forItemLeftRight(firstRowList, false)

        forItemLeftRight(lastColumnList, true)
        forItemLeftRight(lastRowList, false)

        forItemLeftRight(lineColumnList, true)
        forItemLeftRight(lineRowList, false)
    }

    /**
     * 循环设置 Item Left、Right
     * @param list ItemDecoration List
     * @param column Boolean
     */
    private fun forItemLeftRight(
        list: List<BaseGridItemDecoration>,
        column: Boolean
    ) {
        list.forEachIndexed { index, item ->
            val value = AppSize.dp2pxf((index + 1) * 5.0F)
            if (column) {
                item.setColumnLeftRight(value, value)
            } else {
                item.setRowLeftRight(value, value)
            }
        }
    }

    /**
     * 初始化事件
     */
    private fun initListener() {

        // ========
        // = First =
        // ========

        binding.vidFirstColumnAddBtn.setOnClickListener {
            addItemDecoration(firstColumnList, firstColumnIndex)
        }
        binding.vidFirstColumnRemoveBtn.setOnClickListener {
            removeItemDecoration(firstColumnList, firstColumnIndex)
        }
        binding.vidFirstRowAddBtn.setOnClickListener {
            addItemDecoration(firstRowList, firstRowIndex)
        }
        binding.vidFirstRowRemoveBtn.setOnClickListener {
            removeItemDecoration(firstRowList, firstRowIndex)
        }

        // ========
        // = Last =
        // ========

        binding.vidLastColumnAddBtn.setOnClickListener {
            addItemDecoration(lastColumnList, lastColumnIndex)
        }
        binding.vidLastColumnRemoveBtn.setOnClickListener {
            removeItemDecoration(lastColumnList, lastColumnIndex)
        }
        binding.vidLastRowAddBtn.setOnClickListener {
            addItemDecoration(lastRowList, lastRowIndex)
        }
        binding.vidLastRowRemoveBtn.setOnClickListener {
            removeItemDecoration(lastRowList, lastRowIndex)
        }

        // ========
        // = Line =
        // ========

        binding.vidLineColumnAddBtn.setOnClickListener {
            addItemDecoration(lineColumnList, lineColumnIndex)
        }
        binding.vidLineColumnRemoveBtn.setOnClickListener {
            removeItemDecoration(lineColumnList, lineColumnIndex)
        }
        binding.vidLineRowAddBtn.setOnClickListener {
            addItemDecoration(lineRowList, lineRowIndex)
        }
        binding.vidLineRowRemoveBtn.setOnClickListener {
            removeItemDecoration(lineRowList, lineRowIndex)
        }
    }

    /**
     * 通用添加 ItemDecoration 方法
     * @param list ItemDecoration List
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
     * @param list ItemDecoration List
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
     * @param list ItemDecoration List
     * @param number Int
     */
    private fun calcOffset(
        list: List<BaseGridItemDecoration>,
        number: Int
    ) {
        val numberIndex = number - 1
        list.forEachIndexed { index, item ->
            when (item) {
                is GridColumnItemDecoration -> {
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
                }
                is GridRowItemDecoration -> {
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
}