package afkt.project.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import butterknife.BindView;
import dev.utils.app.ActivityUtils;
import dev.utils.app.NotificationUtils;
import dev.utils.app.VibrationUtils;
import dev.utils.app.assist.BeepVibrateAssist;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: 铃声、震动、通知栏等功能
 * @author Ttt
 */
public class FunctionActivity extends BaseToolbarActivity {

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

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonValue.getFunctionButtonValues());
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this));
        vid_bvr_recy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                // 获取操作结果
                boolean result;

                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {
                    case ButtonValue.BTN_FUNCTION_VIBRATE:
                        result = VibrationUtils.vibrate(200);
                        showToast(result);
                        break;
                    case ButtonValue.BTN_FUNCTION_BEEP:
                        // 表示不要震动、使用本地或者 raw 文件
                        result = new BeepVibrateAssist(FunctionActivity.this, R.raw.dev_beep).setVibrate(false).playBeepSoundAndVibrate();
//                        result = new BeepVibrateAssist(FunctionActivity.this, "xxx/a.mp3").setVibrate(false).playBeepSoundAndVibrate();
                        showToast(result);
                        break;
                    case ButtonValue.BTN_FUNCTION_NOTIFICATION:
                        result = NotificationUtils.notify(12, NotificationUtils.createNotification(R.mipmap.icon_launcher, "标题", "内容"));
                        showToast(result);
                        break;
                    case ButtonValue.BTN_FUNCTION_NOTIFICATION_REMOVE:
                        // 关闭通知消息
                        result = NotificationUtils.cancel(12);
                        showToast(result);
                        break;
                    case ButtonValue.BTN_FUNCTION_HOME:
                        result = ActivityUtils.startHomeActivity();
                        showToast(result);
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
    }

    @Override
    public void initListeners() {
        super.initListeners();
    }
}
