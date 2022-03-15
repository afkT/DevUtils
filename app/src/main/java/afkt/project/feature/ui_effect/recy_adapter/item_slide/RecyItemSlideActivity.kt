package afkt.project.feature.ui_effect.recy_adapter.item_slide

import afkt.project.R
import afkt.project.base.app.BaseActivity
import afkt.project.databinding.BaseViewRecyclerviewBinding
import afkt.project.model.bean.CommodityItem
import afkt.project.model.item.RouterPath
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import dev.utils.app.ResourceUtils
import dev.utils.app.helper.quick.QuickHelper
import dev.widget.decoration.linear.FirstLinearColorItemDecoration
import java.util.*

/**
 * detail: RecyclerView 滑动删除、上下滑动
 * @author Ttt
 * RecyclerView 实现拖拽排序和侧滑删除
 * @see http://www.imooc.com/article/80640
 * RecyclerView 扩展
 * @see https://www.jianshu.com/p/c769f4ed298f
 */
@Route(path = RouterPath.UI_EFFECT.RecyItemSlideActivity_PATH)
class RecyItemSlideActivity : BaseActivity<BaseViewRecyclerviewBinding>() {

    private lateinit var itemSlideAdapter: ItemSlideAdapter

    override fun baseLayoutId(): Int = R.layout.base_view_recyclerview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val parent = binding.vidRv.parent as? ViewGroup
        // 根布局处理
        QuickHelper.get(parent).setPadding(0)
            .setBackgroundColor(ResourceUtils.getColor(R.color.color_33))
    }

    override fun initValue() {
        super.initValue()
        val lists: MutableList<CommodityItem> = ArrayList()
        for (i in 0..39) lists.add(CommodityItem.newCommodityItem())

        // 初始化布局管理器、适配器
        itemSlideAdapter = ItemSlideAdapter(lists)
        itemSlideAdapter.bindAdapter(binding.vidRv)

        QuickHelper.get(binding.vidRv)
            .removeAllItemDecoration()
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
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition
                Collections.swap(itemSlideAdapter.dataList, fromPosition, toPosition)
                itemSlideAdapter.notifyItemMoved(fromPosition, toPosition)
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
                val position = viewHolder.adapterPosition
                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                    itemSlideAdapter.removeDataAt(position)
                    itemSlideAdapter.notifyItemRemoved(position)

//                    // 例如有特殊需求, 需弹窗确认
//                    // 可以先触发调用
//                    itemSlideAdapter.notifyItemChanged(position)
//                    // 接着弹窗, 确认要删除才移除对应 position
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.vidRv)
    }
}