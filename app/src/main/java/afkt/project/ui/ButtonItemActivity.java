package afkt.project.ui;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.activity.ViewAssistActivity;
import afkt.project.ui.activity.ViewAssistRecyclerViewLoadActivity;
import afkt.project.ui.adapter.ButtonAdapter;
import afkt.project.util.SkipUtils;
import butterknife.BindView;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: Button 列表 Activity
 * @author Ttt
 */
public class ButtonItemActivity extends BaseToolbarActivity {

    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;

    @Override
    public int getLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValue() {
        super.initValue();
        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getButtonValues(getModuleType()));
        vid_bvr_recy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {

                    // =============
                    // = DevWidget =
                    // =============

                    case ButtonValue.BTN_VIEW_ASSIST_RECYCLER: // RecyclerView ( loading )
                        SkipUtils.startActivity(ViewAssistRecyclerViewLoadActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_VIEW_ASSIST_ERROR: // Error ( failed )
                    case ButtonValue.BTN_VIEW_ASSIST_EMPTY: // Empty ( data )
                    case ButtonValue.BTN_VIEW_ASSIST_CUSTOM: // Custom Type
                        SkipUtils.startActivity(ViewAssistActivity.class, buttonValue);
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
        // 注册观察者
        registerAdapterDataObserver(vid_bvr_recy, true);
    }
}