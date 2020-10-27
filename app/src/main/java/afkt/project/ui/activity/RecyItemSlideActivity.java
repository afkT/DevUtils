package afkt.project.ui.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.model.bean.CommodityEvaluateBean;
import afkt.project.ui.adapter.ItemSlideAdapter;
import dev.utils.app.ResourceUtils;
import dev.utils.app.helper.ViewHelper;

/**
 * detail: RecyclerView 滑动删除、上下滑动
 * @author Ttt
 * <pre>
 *     RecyclerView 实现拖拽排序和侧滑删除
 *     @see <a href="http://www.imooc.com/article/80640"/>
 *     RecyclerView 扩展 ( 二 ) - 手把手教你认识 ItemTouchHelper
 *     @see <a href="https://www.jianshu.com/p/c769f4ed298f"/>
 * </pre>
 */
public class RecyItemSlideActivity extends BaseActivity<BaseViewRecyclerviewBinding> {

    // 适配器
    ItemSlideAdapter itemSlideAdapter;

    @Override
    public int baseLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup parent = (ViewGroup) binding.vidBvrRecy.getParent();
        // 根布局处理
        ViewHelper.get().setPadding(parent, 0)
                .setBackgroundColor(parent, ResourceUtils.getColor(R.color.color_33));
    }

    @Override
    public void initValue() {
        super.initValue();

        List<CommodityEvaluateBean> lists = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            lists.add(CommodityEvaluateBean.newCommodityEvaluateBean());
        }

        // 初始化布局管理器、适配器
        itemSlideAdapter = new ItemSlideAdapter(lists);
        binding.vidBvrRecy.setAdapter(itemSlideAdapter);

        // =

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            /**
             * 获取动作标识
             * 动作标识分 : dragFlags 和 swipeFlags
             * dragFlags : 列表滚动方向的动作标识 ( 如竖直列表就是上和下, 水平列表就是左和右 )
             * wipeFlags : 与列表滚动方向垂直的动作标识 ( 如竖直列表就是左和右, 水平列表就是上和下 )
             */
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                // 如果你不想上下拖动, 可以将 dragFlags = 0
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

                // 如果你不想左右滑动, 可以将 swipeFlags = 0
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

                // 最终的动作标识 ( flags ) 必须要用 makeMovementFlags() 方法生成
                int flags = makeMovementFlags(dragFlags, swipeFlags);
                return flags;
            }

            /**
             * 是否开启 item 长按拖拽功能
             */
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                Collections.swap(itemSlideAdapter.getData(), fromPosition, toPosition);
                itemSlideAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            /**
             * 当 item 侧滑出去时触发 ( 竖直列表是侧滑, 水平列表是竖滑 )
             * @param viewHolder
             * @param direction 滑动的方向
             */
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                    itemSlideAdapter.getData().remove(position);
                    itemSlideAdapter.notifyItemRemoved(position);

//                    // 例如有特殊需求, 需弹窗确认
//                    // 可以先触发调用
//                    itemSlideAdapter.notifyItemChanged(position);
//                    // 接着弹窗, 确认要删除才移除对应 position
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(binding.vidBvrRecy);
    }
}