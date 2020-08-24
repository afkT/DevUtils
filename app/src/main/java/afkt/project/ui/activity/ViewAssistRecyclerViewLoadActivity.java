package afkt.project.ui.activity;

import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.ui.adapter.RecyclerLoadingAdapter;
import dev.utils.app.helper.ViewHelper;
import dev.utils.common.RandomUtils;

/**
 * detail: ViewAssist RecyclerView Loading
 * @author Ttt
 */
public class ViewAssistRecyclerViewLoadActivity extends BaseActivity<BaseViewRecyclerviewBinding> {

    @Override
    public int layoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValue() {
        super.initValue();

        ViewGroup parent = (ViewGroup) binding.vidBvrRecy.getParent();
        // 根布局处理
        ViewHelper.get().setPadding(parent, 0);

        String url = "https://picsum.photos/id/%s/1080/1920";

        List<String> lists = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            lists.add(String.format(url, RandomUtils.getRandom(1, 1000)));
        }

        // 初始化布局管理器、适配器
        binding.vidBvrRecy.setLayoutManager(new GridLayoutManager(this, 2));
        binding.vidBvrRecy.setAdapter(new RecyclerLoadingAdapter(lists));
    }
}