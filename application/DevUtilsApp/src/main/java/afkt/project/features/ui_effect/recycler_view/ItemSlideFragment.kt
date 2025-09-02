package afkt.project.features.ui_effect.recycler_view

import afkt.project.BR
import afkt.project.R
import afkt.project.app.AppFragment
import afkt.project.app.AppViewModel
import afkt.project.databinding.FragmentUiEffectItemSlideBinding
import afkt.project.features.ui_effect.recycler_view.adapter_concat.CommodityBean
import afkt.project.features.ui_effect.recycler_view.adapter_concat.createCommodity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dev.base.simple.extensions.asFragment
import dev.simple.core.adapter.AdapterModel
import dev.utils.app.RecyclerViewUtils
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.widget.decoration.linear.FirstLinearColorItemDecoration
import me.tatarka.bindingcollectionadapter2.ItemBinding
import java.util.*

/**
 * detail: RecyclerView 滑动删除、上下滑动
 * @author Ttt
 * RecyclerView 实现拖拽排序和侧滑删除
 * @see http://www.imooc.com/article/80640
 * RecyclerView 扩展
 * @see https://www.jianshu.com/p/c769f4ed298f
 */
class ItemSlideFragment : AppFragment<FragmentUiEffectItemSlideBinding, ItemSlideViewModel>(
    R.layout.fragment_ui_effect_item_slide, BR.viewModel, simple_Agile = { frg ->
        frg.asFragment<ItemSlideFragment> {
            QuickHelper.get(binding.vidRv).removeAllItemDecoration()
                .addItemDecoration(
                    FirstLinearColorItemDecoration(
                        true, ResourceUtils.getDimension(R.dimen.dp_10)
                    )
                )

            val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
                /**
                 * 获取动作标识
                 * 动作标识分 : dragFlags 和 swipeFlags
                 * dragFlags : 列表滚动方向的动作标识 ( 如竖直列表就是上和下, 水平列表就是左和右 )
                 * wipeFlags : 与列表滚动方向垂直的动作标识 ( 如竖直列表就是左和右, 水平列表就是上和下 )
                 */
                override fun getMovementFlags(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    // 如果你不想上下拖动, 可以将 dragFlags = 0
                    val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN

                    // 如果你不想左右滑动, 可以将 swipeFlags = 0
                    val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

                    // 最终的动作标识 ( flags ) 必须要用 makeMovementFlags() 方法生成
                    return makeMovementFlags(dragFlags, swipeFlags)
                }

                /**
                 * 是否开启 item 长按拖拽功能
                 */
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val fromPosition = viewHolder.bindingAdapterPosition
                    val toPosition = target.bindingAdapterPosition
                    Collections.swap(viewModel.adapterModel.items, fromPosition, toPosition)
                    RecyclerViewUtils.getAdapter<RecyclerView.Adapter<*>>(
                        binding.vidRv
                    )?.notifyItemMoved(fromPosition, toPosition)
                    return true
                }

                /**
                 * 当 item 侧滑出去时触发 ( 竖直列表是侧滑, 水平列表是竖滑 )
                 * @param viewHolder
                 * @param direction 滑动的方向
                 */
                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                        val position = viewHolder.bindingAdapterPosition
                        RecyclerViewUtils.getAdapter<RecyclerView.Adapter<*>>(
                            binding.vidRv
                        )?.apply {
                            viewModel.adapterModel.items.removeAt(position)
//                            /**
//                             * 例如有特殊需求, 需弹窗确认, 可以先触发调用
//                             * 接着弹窗, 确认要删除才移除对应 position
//                             */
//                            notifyItemChanged(position)
                        }
                    }
                }
            })
            itemTouchHelper.attachToRecyclerView(binding.vidRv)
        }
    }
)

class ItemSlideViewModel : AppViewModel() {

    val adapterModel = ItemSlideAdapter().apply {
        val lists = mutableListOf<CommodityBean>()
        for (i in 0..39) lists.add(createCommodity())
        addAll(lists)
    }
}

class ItemSlideAdapter() : AdapterModel<CommodityBean>() {

    // Item Binding
    val itemBinding = ItemBinding.of<CommodityBean>(
        BR.itemValue, R.layout.adapter_item_recy_slide
    )
}