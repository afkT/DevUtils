package afkt.project.ui.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.model.bean.ItemBean;
import afkt.project.ui.adapter.LinearSnapAdapter;
import butterknife.BindView;
import dev.utils.app.helper.ViewHelper;

/**
 * detail: LinearSnapHelper - RecyclerView
 * @author Ttt
 * <pre>
 *     LinearSnapHelper : 滑动多页居中显示, 类似 Gallery
 * </pre>
 */
public class LinearSnapActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;
    // = Object =
    LinearSnapAdapter linearSnapAdapter;

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
    public void initValue() {
        super.initValue();

        List<ItemBean> lists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lists.add(ItemBean.newItemBean());
        }

        // 初始化布局管理器、适配器
        linearSnapAdapter = new LinearSnapAdapter(lists);
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
//        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        vid_bvr_recy.setAdapter(linearSnapAdapter);

        LinearSnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(vid_bvr_recy);
    }
}