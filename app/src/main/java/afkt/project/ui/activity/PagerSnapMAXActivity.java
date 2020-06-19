package afkt.project.ui.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.model.bean.ItemBean;
import afkt.project.ui.adapter.PagerSnapAdapter;
import butterknife.BindView;
import dev.utils.app.helper.ViewHelper;

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
    PagerSnapAdapter pagerSnapAdapter;

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
        pagerSnapAdapter = new PagerSnapAdapter(lists);
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
//        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        vid_bvr_recy.setAdapter(pagerSnapAdapter);

        PagerSnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(vid_bvr_recy);
    }
}