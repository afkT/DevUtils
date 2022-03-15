package afkt.project.feature.dev_widget.recy_item_decoration.grid

import afkt.project.databinding.IncludeGridItemDecorationAssistBinding
import afkt.project.feature.dev_widget.recy_item_decoration.CommonItemDecorationAssist
import afkt.project.utils.AppSize
import androidx.recyclerview.widget.RecyclerView
import dev.utils.app.RecyclerViewUtils
import dev.utils.common.ColorUtils
import dev.utils.common.RandomUtils
import dev.widget.decoration.BaseColorGridItemDecoration
import dev.widget.decoration.grid.FirstGridColumnColorItemDecoration
import dev.widget.decoration.grid.FirstGridRowColorItemDecoration
import dev.widget.decoration.grid.GridColumnColorItemDecoration
import dev.widget.decoration.grid.GridRowColorItemDecoration
import java.util.concurrent.atomic.AtomicInteger

/**
 * detail: DevWidget ItemDecoration 演示通用处理辅助类
 * @author Ttt
 */
internal class GridItemDecorationAssist(
    private val recyclerView: RecyclerView,
    private val binding: IncludeGridItemDecorationAssistBinding
) {

    // DevWidget ItemDecoration 演示通用处理辅助类
    private val assist: CommonItemDecorationAssist by lazy {
        CommonItemDecorationAssist(recyclerView)
    }

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
            FirstGridColumnColorItemDecoration(
                spanCount, vertical, height, ColorUtils.RED
            )
        )
        firstColumnList.add(
            FirstGridColumnColorItemDecoration(
                spanCount, vertical, height, ColorUtils.BLUE
            )
        )
        firstColumnList.add(
            FirstGridColumnColorItemDecoration(
                spanCount, vertical, height, ColorUtils.GREEN
            )
        )

        firstRowList.add(
            FirstGridRowColorItemDecoration(
                spanCount, vertical, height, ColorUtils.RED
            )
        )
        firstRowList.add(
            FirstGridRowColorItemDecoration(
                spanCount, vertical, height, ColorUtils.BLUE
            )
        )
        firstRowList.add(
            FirstGridRowColorItemDecoration(
                spanCount, vertical, height, ColorUtils.GREEN
            )
        )

        lineColumnList.add(
            GridColumnColorItemDecoration(
                spanCount, vertical, height, ColorUtils.CHOCOLATE
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
                spanCount, vertical, height, ColorUtils.CHOCOLATE
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
            firstColumnList, firstRowList,
            lastColumnList, lastRowList,
            lineColumnList, lineRowList
        )
    }

    /**
     * 初始化事件
     */
    private fun initListener() {

        // ========
        // = First =
        // ========

        binding.vidFirstColumnAddBtn.setOnClickListener {
            assist.addItemDecoration(firstColumnList, firstColumnIndex)
        }
        binding.vidFirstColumnRemoveBtn.setOnClickListener {
            assist.removeItemDecoration(firstColumnList, firstColumnIndex)
        }
        binding.vidFirstRowAddBtn.setOnClickListener {
            assist.addItemDecoration(firstRowList, firstRowIndex)
        }
        binding.vidFirstRowRemoveBtn.setOnClickListener {
            assist.removeItemDecoration(firstRowList, firstRowIndex)
        }

        // ========
        // = Last =
        // ========

        binding.vidLastColumnAddBtn.setOnClickListener {
            assist.addItemDecoration(lastColumnList, lastColumnIndex)
        }
        binding.vidLastColumnRemoveBtn.setOnClickListener {
            assist.removeItemDecoration(lastColumnList, lastColumnIndex)
        }
        binding.vidLastRowAddBtn.setOnClickListener {
            assist.addItemDecoration(lastRowList, lastRowIndex)
        }
        binding.vidLastRowRemoveBtn.setOnClickListener {
            assist.removeItemDecoration(lastRowList, lastRowIndex)
        }

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