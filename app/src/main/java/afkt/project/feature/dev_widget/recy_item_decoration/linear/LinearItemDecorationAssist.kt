package afkt.project.feature.dev_widget.recy_item_decoration.linear

import afkt.project.databinding.IncludeLinearItemDecorationAssistBinding
import afkt.project.feature.dev_widget.recy_item_decoration.CommonItemDecorationAssist
import afkt.project.utils.AppSize
import androidx.recyclerview.widget.RecyclerView
import dev.utils.app.RecyclerViewUtils
import dev.utils.common.ColorUtils
import dev.utils.common.RandomUtils
import dev.widget.decoration.BaseColorItemDecoration
import dev.widget.decoration.linear.FirstLinearColorItemDecoration
import dev.widget.decoration.linear.LastLinearColorItemDecoration
import dev.widget.decoration.linear.LinearColorItemDecoration
import java.util.concurrent.atomic.AtomicInteger

/**
 * detail: DevWidget ItemDecoration 演示通用处理辅助类
 * @author Ttt
 */
internal class LinearItemDecorationAssist(
    private val recyclerView: RecyclerView,
    private val binding: IncludeLinearItemDecorationAssistBinding
) {

    // DevWidget ItemDecoration 演示通用处理辅助类
    private val assist: CommonItemDecorationAssist by lazy {
        CommonItemDecorationAssist(recyclerView)
    }

    // 首条数据顶部添加 ItemDecoration
    private val firstList = mutableListOf<BaseColorItemDecoration>()

    // 最后一条数据底部添加 ItemDecoration
    private val lastList = mutableListOf<BaseColorItemDecoration>()

    // 每条数据底部添加 ItemDecoration
    private val lineList = mutableListOf<BaseColorItemDecoration>()

    // 递增数
    private var firstIndex = AtomicInteger()
    private var lastIndex = AtomicInteger()
    private var lineIndex = AtomicInteger()

    // ============
    // = 初始化数据 =
    // ============

    init {
        val height = AppSize.dp2pxf(10.0F)
        val vertical = RecyclerViewUtils.canScrollVertically(recyclerView)

        firstList.add(
            FirstLinearColorItemDecoration(
                vertical, height, ColorUtils.RED
            )
        )
        firstList.add(
            FirstLinearColorItemDecoration(
                vertical, height, ColorUtils.BLUE
            )
        )
        firstList.add(
            FirstLinearColorItemDecoration(
                vertical, height, ColorUtils.GREEN
            )
        )

        lastList.add(
            LastLinearColorItemDecoration(
                vertical, height, ColorUtils.GOLD
            )
        )
        lastList.add(
            LastLinearColorItemDecoration(
                vertical, height, ColorUtils.PINK
            )
        )
        lastList.add(
            LastLinearColorItemDecoration(
                vertical, height, ColorUtils.PURPLE
            )
        )

        lineList.add(
            LinearColorItemDecoration(
                vertical, height, ColorUtils.CHOCOLATE
            )
        )
        lineList.add(
            LinearColorItemDecoration(
                vertical, height, ColorUtils.CYAN
            )
        )
        lineList.add(
            LinearColorItemDecoration(
                vertical, height, ColorUtils.ORANGE
            )
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
        assist.forItemLeftRight(
            firstList, lastList, lineList
        )
    }

    /**
     * 初始化事件
     */
    private fun initListener() {
        binding.vidFirstAddBtn.setOnClickListener {
            assist.addItemDecoration(firstList, firstIndex)
        }
        binding.vidFirstRemoveBtn.setOnClickListener {
            assist.removeItemDecoration(firstList, firstIndex)
        }

        binding.vidLastAddBtn.setOnClickListener {
            assist.addItemDecoration(lastList, lastIndex)
        }
        binding.vidLastRemoveBtn.setOnClickListener {
            assist.removeItemDecoration(lastList, lastIndex)
        }

        binding.vidLineAddBtn.setOnClickListener {
            assist.addItemDecoration(lineList, lineIndex)
        }
        binding.vidLineRemoveBtn.setOnClickListener {
            assist.removeItemDecoration(lineList, lineIndex)
        }
    }
}