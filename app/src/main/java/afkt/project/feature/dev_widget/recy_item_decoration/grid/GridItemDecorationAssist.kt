package afkt.project.feature.dev_widget.recy_item_decoration.grid

import afkt.project.databinding.IncludeItemDecorationAssistBinding
import afkt.project.utils.AppSize
import androidx.recyclerview.widget.RecyclerView
import dev.utils.app.RecyclerViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.ColorUtils
import dev.utils.common.RandomUtils
import dev.widget.decoration.BaseItemDecoration
import dev.widget.decoration.linear.FirstLineItemDecoration
import dev.widget.decoration.linear.LastLineItemDecoration
import dev.widget.decoration.linear.LineItemDecoration
import dev.widget.decoration.linear.horizontal.FirstLineHorizontalItemDecoration
import dev.widget.decoration.linear.horizontal.LastLineHorizontalItemDecoration
import dev.widget.decoration.linear.horizontal.LineHorizontalItemDecoration
import java.util.concurrent.atomic.AtomicInteger

/**
 * detail: DevWidget ItemDecoration 演示通用处理辅助类
 * @author Ttt
 */
internal class GridItemDecorationAssist(
    private val recyclerView: RecyclerView,
    private val binding: IncludeItemDecorationAssistBinding
) {

    // 最大添加数量
    private val MAX = 3

    // 首条数据顶部添加 ItemDecoration
    private val firstList = mutableListOf<BaseItemDecoration>()

    // 最后一条数据底部添加 ItemDecoration
    private val lastList = mutableListOf<BaseItemDecoration>()

    // 每条数据底部添加 ItemDecoration
    private val lineList = mutableListOf<BaseItemDecoration>()

    // 递增数
    private var firstIndex = AtomicInteger()
    private var lastIndex = AtomicInteger()
    private var lineIndex = AtomicInteger()

    // ============
    // = 初始化数据 =
    // ============

    init {
        val lineHeight = AppSize.dp2pxf(10.0F)
        val vertical = RecyclerViewUtils.canScrollVertically(recyclerView)

        if (vertical) {
            firstList.add(FirstLineItemDecoration(lineHeight, ColorUtils.RED))
            firstList.add(FirstLineItemDecoration(lineHeight, ColorUtils.BLUE))
            firstList.add(FirstLineItemDecoration(lineHeight, ColorUtils.GREEN))

            lastList.add(LastLineItemDecoration(lineHeight, ColorUtils.GOLD))
            lastList.add(LastLineItemDecoration(lineHeight, ColorUtils.PINK))
            lastList.add(LastLineItemDecoration(lineHeight, ColorUtils.PURPLE))

            lineList.add(LineItemDecoration(lineHeight, ColorUtils.CHOCOLATE))
            lineList.add(LineItemDecoration(lineHeight, ColorUtils.CYAN))
            lineList.add(LineItemDecoration(lineHeight, ColorUtils.ORANGE))
        } else {
            firstList.add(FirstLineHorizontalItemDecoration(lineHeight, ColorUtils.RED))
            firstList.add(FirstLineHorizontalItemDecoration(lineHeight, ColorUtils.BLUE))
            firstList.add(FirstLineHorizontalItemDecoration(lineHeight, ColorUtils.GREEN))

            lastList.add(LastLineHorizontalItemDecoration(lineHeight, ColorUtils.GOLD))
            lastList.add(LastLineHorizontalItemDecoration(lineHeight, ColorUtils.PINK))
            lastList.add(LastLineHorizontalItemDecoration(lineHeight, ColorUtils.PURPLE))

            lineList.add(LineHorizontalItemDecoration(lineHeight, ColorUtils.CHOCOLATE))
            lineList.add(LineHorizontalItemDecoration(lineHeight, ColorUtils.CYAN))
            lineList.add(LineHorizontalItemDecoration(lineHeight, ColorUtils.ORANGE))
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
        firstList.forEachIndexed { index, item ->
            item.setLineLeftRight(
                AppSize.dp2pxf((index + 1) * 3.0F),
                AppSize.dp2pxf((index + 1) * 3.0F),
            )
        }
        lastList.forEachIndexed { index, item ->
            item.setLineLeftRight(
                AppSize.dp2pxf((index + 1) * 4.0F),
                AppSize.dp2pxf((index + 1) * 4.0F),
            )
        }
        lineList.forEachIndexed { index, item ->
            item.setLineLeftRight(
                AppSize.dp2pxf((index + 1) * 5.0F),
                AppSize.dp2pxf((index + 1) * 5.0F),
            )
        }
    }

    /**
     * 初始化事件
     */
    private fun initListener() {
        binding.vidFirstAddBtn.setOnClickListener {
            addItemDecoration(firstList, firstIndex)
        }
        binding.vidFirstRemoveBtn.setOnClickListener {
            removeItemDecoration(firstList, firstIndex)
        }

        binding.vidLastAddBtn.setOnClickListener {
            addItemDecoration(lastList, lastIndex)
        }
        binding.vidLastRemoveBtn.setOnClickListener {
            removeItemDecoration(lastList, lastIndex)
        }

        binding.vidLineAddBtn.setOnClickListener {
            addItemDecoration(lineList, lineIndex)
        }
        binding.vidLineRemoveBtn.setOnClickListener {
            removeItemDecoration(lineList, lineIndex)
        }
    }

    /**
     * 通用添加 ItemDecoration 方法
     * @param list List<BaseItemDecoration>
     * @param index AtomicInteger
     */
    private fun addItemDecoration(
        list: List<BaseItemDecoration>,
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
     * @param list List<BaseItemDecoration>
     * @param index AtomicInteger
     */
    private fun removeItemDecoration(
        list: List<BaseItemDecoration>,
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
     * @param list List<BaseItemDecoration>
     * @param number Int
     */
    private fun calcOffset(
        list: List<BaseItemDecoration>,
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
                    offset += list[i].lineHeight
                }
                // 设置偏差值
                item.offset = offset
            }
        }
    }
}