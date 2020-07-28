package afkt.project.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.model.item.EvaluateItem;
import afkt.project.ui.adapter.EditsAdapter;
import afkt.project.ui.widget.BaseTextView;
import butterknife.BindView;
import dev.utils.app.ResourceUtils;
import dev.utils.app.helper.QuickHelper;
import dev.utils.app.helper.ViewHelper;
import dev.utils.app.logger.DevLogger;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: Adapter Item EditText 输入监听
 * @author Ttt
 */
public class AdapterEditsActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;
    // 适配器
    EditsAdapter editsAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加按钮
        View view = QuickHelper.get(new BaseTextView(this))
                .setText("提交")
                .setBold()
                .setTextColor(ResourceUtils.getColor(R.color.white))
                .setTextSizeBySp(13.0f)
                .setPaddingLeft(30)
                .setPaddingRight(30)
                .setOnClicks(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringBuilder builder = new StringBuilder();
                        for (EvaluateItem item : editsAdapter.getData()) {
                            builder.append("\nevaluateContent: " + item.evaluateContent);
                            builder.append("\nevaluateLevel: " + item.evaluateLevel);
                            builder.append("\n");
                        }
                        DevLogger.dTag(mTag, builder.toString());
                        ToastTintUtils.success("数据已打印, 请查看 Logcat");
                    }
                }).getView();
        vid_bt_toolbar.addView(view);

        ViewGroup parent = (ViewGroup) vid_bvr_recy.getParent();
        // 根布局处理
        ViewHelper.get().setPadding(parent, 0)
                .setBackgroundColor(parent, ResourceUtils.getColor(R.color.color_33));
    }

    @Override
    public void initValues() {
        super.initValues();

        List<EvaluateItem> lists = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            lists.add(new EvaluateItem());
        }
        // 默认清空第一条数据内容
        lists.get(0).evaluateContent = "";

        // 初始化布局管理器、适配器
        editsAdapter = new EditsAdapter(lists);
        vid_bvr_recy.setAdapter(editsAdapter);
    }
}