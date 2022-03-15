package afkt.project.feature.dev_widget.recy_item_decoration.grid

import afkt.project.databinding.IncludeGridItemDecorationAssistBinding
import afkt.project.utils.AppSize
import androidx.recyclerview.widget.RecyclerView
import dev.utils.app.RecyclerViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.ColorUtils
import dev.utils.common.RandomUtils
import dev.widget.decoration.BaseColorGridItemDecoration
import dev.widget.decoration.grid.FirstGridColumnItemDecoration
import dev.widget.decoration.grid.FirstGridRowItemDecoration
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
    private val firstColumnList = mutableListOf<BaseColorGridItemDecoration>()

    // 首条数据顶部添加 行分割线处理 ItemDecoration
    private val firstRowList = mutableListOf<BaseColorGridItemDecoration>()

    // 最后一条数据底部添加 列分割线处理 ItemDecoration
    private val lastColumnList = mutableListOf<BaseColorGridItemDecoration>()

    // 最后一条数据底部添加 行分割线处理 ItemDecoration
    private val lastRowList = mutableListOf<BaseColorGridItemDecoration>()

    // 每条数据底部添加 列分割线处理 ItemDecoration
    private val lineColumnList = mutableListOf<BaseColorGridItemDecoration>()

    // 每条数据底部添加 行分割线处理 ItemDecoration
    private val lineRowList = mutableListOf<BaseColorGridItemDecoration>()

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
        val height = AppSize.dp2pxf(10.0F)
        val spanCount = RecyclerViewUtils.getSpanCount(recyclerView)
        val vertical = RecyclerViewUtils.canScrollVertically(recyclerView)

        firstColumnList.add(
            FirstGridColumnItemDecoration(spanCount, vertical, height, ColorUtils.RED)
        )
        firstColumnList.add(
            FirstGridColumnItemDecoration(spanCount, vertical, height, ColorUtils.BLUE)
        )
        firstColumnList.add(
            FirstGridColumnItemDecoration(spanCount, vertical, height, ColorUtils.GREEN)
        )

        firstRowList.add(
            FirstGridRowItemDecoration(spanCount, vertical, height, ColorUtils.RED)
        )
        firstRowList.add(
            FirstGridRowItemDecoration(spanCount, vertical, height, ColorUtils.BLUE)
        )
        firstRowList.add(
            FirstGridRowItemDecoration(spanCount, vertical, height, ColorUtils.GREEN)
        )

        lineColumnList.add(
            GridColumnItemDecoration(spanCount, vertical, height, ColorUtils.CHOCOLATE)
        )
        lineColumnList.add(
            GridColumnItemDecoration(spanCount, vertical, height, ColorUtils.CYAN)
        )
        lineColumnList.add(
            GridColumnItemDecoration(spanCount, vertical, height, ColorUtils.ORANGE)
        )

        lineRowList.add(
            GridRowItemDecoration(spanCount, vertical, height, ColorUtils.CHOCOLATE)
        )
        lineRowList.add(
            GridRowItemDecoration(spanCount, vertical, height, ColorUtils.CYAN)
        )
        lineRowList.add(
            GridRowItemDecoration(spanCount, vertical, height, ColorUtils.ORANGE)
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
        forItemLeftRight(firstColumnList)
        forItemLeftRight(firstRowList)

        forItemLeftRight(lastColumnList)
        forItemLeftRight(lastRowList)

        forItemLeftRight(lineColumnList)
        forItemLeftRight(lineRowList)
    }

    /**
     * 循环设置 Item Left、Right
     * @param list ItemDecoration List
     */
    private fun forItemLeftRight(list: List<BaseColorGridItemDecoration>) {
        list.forEachIndexed { index, item ->
            val value = AppSize.dp2pxf((index + 1) * 5.0F)
            item.setLeftRight(value, value)
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
        list: List<BaseColorGridItemDecoration>,
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
        list: List<BaseColorGridItemDecoration>,
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
        list: List<BaseColorGridItemDecoration>,
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