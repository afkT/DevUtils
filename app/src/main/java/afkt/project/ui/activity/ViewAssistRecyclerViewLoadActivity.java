package afkt.project.ui.activity;

import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.ui.adapter.RecyclerLoadingAdapter;
import butterknife.BindView;
import dev.utils.app.helper.ViewHelper;
import dev.utils.common.RandomUtils;

/**
 * detail: ViewAssist RecyclerView Loading
 * @author Ttt
 */
public class ViewAssistRecyclerViewLoadActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;

    @Override
    public int getLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValues() {
        super.initValues();

        ViewGroup parent = (ViewGroup) vid_bvr_recy.getParent();
        // 根布局处理
        ViewHelper.get().setPadding(parent, 0);

        String url = "https://picsum.photos/id/%s/1080/1920";

        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            lists.add(String.format(url, RandomUtils.getRandom(1, 1000)));
        }

        // 初始化布局管理器、适配器
        vid_bvr_recy.setLayoutManager(new GridLayoutManager(this, 2));
        vid_bvr_recy.setAdapter(new RecyclerLoadingAdapter(lists));
    }
}