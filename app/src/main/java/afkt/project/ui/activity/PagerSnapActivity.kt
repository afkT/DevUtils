package afkt.project.ui.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.model.bean.ItemBean;
import afkt.project.ui.adapter.PagerSnapAdapter;
import dev.utils.app.helper.ViewHelper;

/**
 * detail: PagerSnapHelper - RecyclerView
 * @author Ttt
 * <pre>
 *     PagerSnapHelper : 每次滑动一页居中显示, 类似 ViewPager
 * </pre>
 */
public class PagerSnapActivity
        extends BaseActivity<BaseViewRecyclerviewBinding> {

    private PagerSnapAdapter pagerSnapAdapter;

    @Override
    public int baseLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup parent = (ViewGroup) binding.vidBvrRecy.getParent();
        // 根布局处理
        ViewHelper.get().setPadding(parent, 0);
    }

    @Override
    public void initValue() {
        super.initValue();

        List<ItemBean> lists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lists.add(ItemBean.newItemBeanPager());
        }

        // 初始化布局管理器、适配器
        pagerSnapAdapter = new PagerSnapAdapter(lists);
        binding.vidBvrRecy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
//        binding.vidBvrRecy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.vidBvrRecy.setAdapter(pagerSnapAdapter);

        PagerSnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(binding.vidBvrRecy);
    }
}