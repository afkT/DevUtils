package afkt.project.feature.dev_widget.recy_item_decoration.linear

import afkt.project.databinding.IncludeItemDecorationAssistBinding
import afkt.project.utils.AppSize
import androidx.recyclerview.widget.RecyclerView
import dev.utils.app.RecyclerViewUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.utils.common.ColorUtils
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
internal class LinearItemDecorationAssist(
    private val recyclerView: RecyclerView,
    private val binding: IncludeItemDecorationAssistBinding
) {

    // 最大添加数量
    private val MAX = 3

    // 首条数据顶部添加 ItemDecoration
    private val firstList = mutableListOf<RecyclerView.ItemDecoration>()

    // 最后一条数据底部添加 ItemDecoration
    private val lastList = mutableListOf<RecyclerView.ItemDecoration>()

    // 每条数据底部添加 ItemDecoration
    private val lineList = mutableListOf<RecyclerView.ItemDecoration>()

    // 递增数
    var firstIndex = AtomicInteger()
    var lastIndex = AtomicInteger()
    var lineIndex = AtomicInteger()

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
        initListener()
    }

    /**
     * 初始化事件
     */
    private fun initListener() {
        binding.vidFirstAddBtn.setOnClickListener {
            if (firstIndex.get() >= MAX) {
                return@setOnClickListener
            }
            QuickHelper.get(recyclerView)
                .addItemDecoration(firstList[firstIndex.get()])
                .notifyDataSetChanged()
            firstIndex.incrementAndGet()
        }
        binding.vidFirstRemoveBtn.setOnClickListener {
            if (firstIndex.get() <= 0) {
                return@setOnClickListener
            }
            firstIndex.decrementAndGet()

            QuickHelper.get(recyclerView)
                .removeItemDecoration(firstList[firstIndex.get()])
                .notifyDataSetChanged()
        }

        binding.vidLastAddBtn.setOnClickListener {
            if (lastIndex.get() >= MAX) {
                return@setOnClickListener
            }
            QuickHelper.get(recyclerView)
                .addItemDecoration(lastList[lastIndex.get()])
                .notifyDataSetChanged()
            lastIndex.incrementAndGet()
        }
        binding.vidLastRemoveBtn.setOnClickListener {
            if (lastIndex.get() <= 0) {
                return@setOnClickListener
            }
            lastIndex.decrementAndGet()

            QuickHelper.get(recyclerView)
                .removeItemDecoration(lastList[lastIndex.get()])
                .notifyDataSetChanged()
        }

        binding.vidLineAddBtn.setOnClickListener {
            if (lineIndex.get() >= MAX) {
                return@setOnClickListener
            }
            QuickHelper.get(recyclerView)
                .addItemDecoration(lineList[lineIndex.get()])
                .notifyDataSetChanged()
            lineIndex.incrementAndGet()
        }
        binding.vidLineRemoveBtn.setOnClickListener {
            if (lineIndex.get() <= 0) {
                return@setOnClickListener
            }
            lineIndex.decrementAndGet()

            QuickHelper.get(recyclerView)
                .removeItemDecoration(lineList[lineIndex.get()])
                .notifyDataSetChanged()
        }
    }
}