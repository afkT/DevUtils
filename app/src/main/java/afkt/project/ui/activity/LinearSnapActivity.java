package afkt.project.ui.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.model.bean.ItemBean;
import afkt.project.ui.adapter.LinearSnapAdapter;
import dev.utils.app.helper.ViewHelper;

/**
 * detail: LinearSnapHelper - RecyclerView
 * @author Ttt
 * <pre>
 *     LinearSnapHelper : 滑动多页居中显示, 类似 Gallery
 * </pre>
 */
public class LinearSnapActivity
        extends BaseActivity<BaseViewRecyclerviewBinding> {

    LinearSnapAdapter linearSnapAdapter;

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
            lists.add(ItemBean.newItemBean());
        }

        // 初始化布局管理器、适配器
        linearSnapAdapter = new LinearSnapAdapter(lists);
        binding.vidBvrRecy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
//        binding.vidBvrRecy.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.vidBvrRecy.setAdapter(linearSnapAdapter);

        LinearSnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(binding.vidBvrRecy);
    }
}