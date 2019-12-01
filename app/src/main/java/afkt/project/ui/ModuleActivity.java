package afkt.project.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.activity.ListenerActivity;
import afkt.project.ui.activity.NotificationServiceActivity;
import afkt.project.ui.activity.WifiActivity;
import afkt.project.ui.adapter.ButtonAdapter;
import afkt.project.util.SkipUtils;
import butterknife.BindView;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: Module 列表 Activity
 * @author Ttt
 */
public class ModuleActivity extends BaseToolbarActivity {

    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;

    @Override
    public int getLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    public void initValues() {
        super.initValues();
        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonValue.getModuleButtonValues(getModuleType()));
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this));
        vid_bvr_recy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {

                    // ============
                    // = 其他功能 =
                    // ============

                    case ButtonValue.BTN_LISTENER: // 事件 / 广播监听 ( 网络状态、屏幕旋转等 )
                        SkipUtils.startActivity(ListenerActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_NOTIFICATION_SERVICE: // 通知栏监听服务 ( NotificationService )
                        SkipUtils.startActivity(NotificationServiceActivity.class, buttonValue);
                        break;
                    case ButtonValue.BTN_WIFI: // Wifi 相关 ( 热点 )
                        SkipUtils.startActivity(WifiActivity.class, buttonValue);
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
    }
}
