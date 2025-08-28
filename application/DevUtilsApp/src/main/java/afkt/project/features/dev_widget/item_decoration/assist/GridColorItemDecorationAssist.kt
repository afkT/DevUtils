package afkt.project.features.dev_widget.item_decoration.assist

import afkt.project.databinding.IncludeGridItemDecorationAssistBinding
import androidx.recyclerview.widget.RecyclerView
import dev.simple.core.utils.size.AppSize
import dev.utils.app.RecyclerViewUtils
import dev.utils.common.ColorUtils
import dev.utils.common.RandomUtils
import dev.widget.decoration.BaseColorGridItemDecoration
import dev.widget.decoration.grid.GridColumnColorItemDecoration
import dev.widget.decoration.grid.GridRowColorItemDecoration
import java.util.concurrent.atomic.AtomicInteger

/**
 * detail: DevWidget Color ItemDecoration 演示通用处理辅助类
 * @author Ttt
 */
internal class GridColorItemDecorationAssist(
    private val recyclerView: RecyclerView,
    private val binding: IncludeGridItemDecorationAssistBinding
) {

    // DevWidget Color ItemDecoration 演示通用处理辅助类
    private val assist: CommonColorItemDecorationAssist by lazy {
        CommonColorItemDecorationAssist(recyclerView)
    }

    // 每条数据底部添加 列分割线处理 ItemDecoration
    private val lineColumnList = mutableListOf<BaseColorGridItemDecoration>()

    // 每条数据底部添加 行分割线处理 ItemDecoration
    private val lineRowList = mutableListOf<BaseColorGridItemDecoration>()

    // 递增数
    private var lineColumnIndex = AtomicInteger()
    private var lineRowIndex = AtomicInteger()

    // ============
    // = 初始化数据 =
    // ============

    init {
        val height = AppSize.dp2pxf(recyclerView.context, 10.0F)
        val vertical = RecyclerViewUtils.canScrollVertically(recyclerView)
        val spanCount = RecyclerViewUtils.getSpanCount(recyclerView)

        // ========
        // = Line =
        // ========

        lineColumnList.add(
            GridColumnColorItemDecoration(
                spanCount, vertical, height, ColorUtils.PINK
            )
        )
        lineColumnList.add(
            GridColumnColorItemDecoration(
                spanCount, vertical, height, ColorUtils.CYAN
            )
        )
        lineColumnList.add(
            GridColumnColorItemDecoration(
                spanCount, vertical, height, ColorUtils.ORANGE
            )
        )

        lineRowList.add(
            GridRowColorItemDecoration(
                spanCount, vertical, height, ColorUtils.PINK
            )
        )
        lineRowList.add(
            GridRowColorItemDecoration(
                spanCount, vertical, height, ColorUtils.CYAN
            )
        )
        lineRowList.add(
            GridRowColorItemDecoration(
                spanCount, vertical, height, ColorUtils.ORANGE
            )
        )

        // =================================
        // = 通用设置 ItemDecoration 左右边距 =
        // =================================

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
            lineColumnList, lineRowList
        )
    }

    /**
     * 初始化事件
     */
    private fun initListener() {

        // ========
        // = Line =
        // ========

        binding.vidLineColumnAddBtn.setOnClickListener {
            assist.addItemDecoration(lineColumnList, lineColumnIndex)
        }
        binding.vidLineColumnRemoveBtn.setOnClickListener {
            assist.removeItemDecoration(lineColumnList, lineColumnIndex)
        }
        binding.vidLineRowAddBtn.setOnClickListener {
            assist.addItemDecoration(lineRowList, lineRowIndex)
        }
        binding.vidLineRowRemoveBtn.setOnClickListener {
            assist.removeItemDecoration(lineRowList, lineRowIndex)
        }
    }
}