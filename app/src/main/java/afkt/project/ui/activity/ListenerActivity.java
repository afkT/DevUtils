package afkt.project.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import butterknife.BindView;
import dev.utils.app.ResourceUtils;
import dev.utils.app.TextViewUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.ViewHelper;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: 事件 / 广播监听 ( 网络状态、屏幕旋转等 )
 * @author Ttt
 */
public class ListenerActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_act_linear)
    LinearLayout vid_act_linear;
    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_tips;
    }

    @Override
    public void initValues() {
        super.initValues();

        View view = ViewUtils.inflate(R.layout.base_view_textview);
        ViewHelper.get().setText(view, "单击绑定, 双击注销")
                .setTextColor(view, ResourceUtils.getColor(R.color.grey));
        vid_act_linear.addView(view);

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonValue.getListenerButtonValues());
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this));
        vid_bvr_recy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {

                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
    }
}
