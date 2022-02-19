package afkt.project.feature.dev_widget.recy_item_decoration.linear

import afkt.project.databinding.IncludeItemDecorationAssistBinding
import androidx.recyclerview.widget.RecyclerView
import dev.utils.app.RecyclerViewUtils
import java.util.concurrent.atomic.AtomicInteger

/**
 * detail: DevWidget ItemDecoration 演示通用处理辅助类
 * @author Ttt
 */
class ItemDecorationAssist(
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
    private val itemList = mutableListOf<RecyclerView.ItemDecoration>()

    // 递增数
    var firstIndex = AtomicInteger()
    var lastIndex = AtomicInteger()
    var itemIndex = AtomicInteger()

    // ============
    // = 初始化数据 =
    // ============

    init {
        val vertical = RecyclerViewUtils.canScrollVertically(recyclerView)
    }
}