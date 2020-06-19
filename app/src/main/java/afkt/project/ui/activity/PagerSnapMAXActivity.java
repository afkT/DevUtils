package afkt.project.ui.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.model.bean.ItemBean;
import afkt.project.ui.adapter.PagerSnapMAXAdapter;
import butterknife.BindView;
import dev.utils.app.ListViewUtils;
import dev.utils.app.helper.ViewHelper;
import dev.utils.app.logger.DevLogger;

/**
 * detail: PagerSnapHelper - 无限滑动
 * @author Ttt
 * <pre>
 *     PagerSnapHelper : 每次滑动一页居中显示, 类似 ViewPager
 * </pre>
 */
public class PagerSnapMAXActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;
    // = Object =
    PagerSnapMAXAdapter pagerSnapAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup parent = (ViewGroup) vid_bvr_recy.getParent();
        // 根布局处理
        ViewHelper.get().setPadding(parent, 0);
    }

    @Override
    public void initValues() {
        super.initValues();


        List<ItemBean> lists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lists.add(ItemBean.newItemBeanPager());
        }

        // 初始化布局管理器、适配器
        pagerSnapAdapter = new PagerSnapMAXAdapter(this, lists);
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
//        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        vid_bvr_recy.setAdapter(pagerSnapAdapter);

        PagerSnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(vid_bvr_recy);


        int size = lists.size();
        // 滑动到中间 ( 无滑动过程 )
        ((LinearLayoutManager) vid_bvr_recy.getLayoutManager()).scrollToPositionWithOffset(size * 100 - 1, 10);
        // 复位到中间
        ListViewUtils.smoothScrollToPosition(vid_bvr_recy, size * 100 + 1);
    }

    @Override
    public void initListeners() {
        super.initListeners();

        vid_bvr_recy.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        // 获取最后一个可见 view 的位置
                        int lastItemPosition = linearManager.findLastVisibleItemPosition();
                        // 获取第一个可见 view 的位置
                        int firstItemPosition = linearManager.findFirstVisibleItemPosition();

                        // 获取居中索引
                        int currentPosition = (lastItemPosition + firstItemPosition) / 2;
                        // 真实索引
                        int index = pagerSnapAdapter.getRealIndex(currentPosition);

                        DevLogger.dTag(mTag, lastItemPosition + " - " + firstItemPosition + " -> 当前显示索引: " + currentPosition + " - " + index);
                    }
                }
            }
        });
    }
}