package afkt.project.feature.dev_widget.recy_item_decoration.linear

import afkt.project.databinding.IncludeLinearItemDecorationAssistBinding
import afkt.project.utils.AppSize
import androidx.recyclerview.widget.RecyclerView
import dev.utils.app.RecyclerViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.ColorUtils
import dev.utils.common.RandomUtils
import dev.widget.decoration.BaseLinearItemDecoration
import dev.widget.decoration.linear.FirstLineItemDecoration
import dev.widget.decoration.linear.LastLineItemDecoration
import dev.widget.decoration.linear.LineItemDecoration
import java.util.concurrent.atomic.AtomicInteger

/**
 * detail: DevWidget ItemDecoration 演示通用处理辅助类
 * @author Ttt
 */
internal class LinearItemDecorationAssist(
    private val recyclerView: RecyclerView,
    private val binding: IncludeLinearItemDecorationAssistBinding
) {

    // 最大添加数量
    private val MAX = 3

    // 首条数据顶部添加 ItemDecoration
    private val firstList = mutableListOf<BaseLinearItemDecoration>()

    // 最后一条数据底部添加 ItemDecoration
    private val lastList = mutableListOf<BaseLinearItemDecoration>()

    // 每条数据底部添加 ItemDecoration
    private val lineList = mutableListOf<BaseLinearItemDecoration>()

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

        firstList.add(FirstLineItemDecoration(vertical, lineHeight, ColorUtils.RED))
        firstList.add(FirstLineItemDecoration(vertical, lineHeight, ColorUtils.BLUE))
        firstList.add(FirstLineItemDecoration(vertical, lineHeight, ColorUtils.GREEN))

        lastList.add(LastLineItemDecoration(vertical, lineHeight, ColorUtils.GOLD))
        lastList.add(LastLineItemDecoration(vertical, lineHeight, ColorUtils.PINK))
        lastList.add(LastLineItemDecoration(vertical, lineHeight, ColorUtils.PURPLE))

        lineList.add(LineItemDecoration(vertical, lineHeight, ColorUtils.CHOCOLATE))
        lineList.add(LineItemDecoration(vertical, lineHeight, ColorUtils.CYAN))
        lineList.add(LineItemDecoration(vertical, lineHeight, ColorUtils.ORANGE))

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
        forItemLeftRight(firstList)
        forItemLeftRight(lastList)
        forItemLeftRight(lineList)
    }

    /**
     * 循环设置 Item Left、Right
     * @param list ItemDecoration List
     */
    private fun forItemLeftRight(list: List<BaseLinearItemDecoration>) {
        list.forEachIndexed { index, item ->
            val value = AppSize.dp2pxf((index + 1) * 5.0F)
            item.setLineLeftRight(value, value)
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
     * @param list ItemDecoration List
     * @param index AtomicInteger
     */
    private fun addItemDecoration(
        list: List<BaseLinearItemDecoration>,
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
        list: List<BaseLinearItemDecoration>,
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
        list: List<BaseLinearItemDecoration>,
        number: Int
    ) {
        val numberIndex = number - 1
        list.forEachIndexed { index, item ->
            // 先重置为 0
            item.lineOffset = 0.0F
            if (numberIndex >= index) {
                // 偏差值 ( 用于解决多个 ItemDecoration 叠加覆盖问题 )
                var offset = 0.0F
                for (i in (index + 1)..numberIndex) {
                    offset += list[i].lineHeight
                }
                // 设置偏差值
                item.lineOffset = offset
            }
        }
    }
}