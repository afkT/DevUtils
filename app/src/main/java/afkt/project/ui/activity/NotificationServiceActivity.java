package afkt.project.ui.activity;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import butterknife.BindView;
import dev.service.NotificationService;
import dev.utils.app.logger.DevLogger;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: 通知栏监听服务 ( NotificationService )
 * @author Ttt
 * <pre>
 *     @see <a href="https://www.jianshu.com/p/981e7de2c7be"/>
 *     所需权限
 *     <uses-permission android:name="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE" />
 * </pre>
 */
public class NotificationServiceActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_bvr_recy)
    RecyclerView vid_bvr_recy;

    @Override
    public int getLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销监听
        NotificationService.setNotificationListener(null);
        NotificationService.stopService();
    }

    @Override
    public void initValues() {
        super.initValues();

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonValue.getNotificationServiceButtonValues());
        vid_bvr_recy.setLayoutManager(new LinearLayoutManager(this));
        vid_bvr_recy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {
                    case ButtonValue.BTN_NOTIFICATION_SERVICE_CHECK:
                        boolean check = NotificationService.isNotificationListenerEnabled();
                        showToast(check, "已开启服务通知", "未开启服务通知");
                        break;
                    case ButtonValue.BTN_NOTIFICATION_SERVICE_REGISTER:
                        if (!NotificationService.checkAndIntentSetting()) {
                            showToast(false, "请先开启服务通知权限");
                            return;
                        }
                        showToast(true, "绑定通知栏监听服务成功, 请查看 Logcat");
                        // 注册监听
                        NotificationService.startService();
                        break;
                    case ButtonValue.BTN_NOTIFICATION_SERVICE_UNREGISTER:
                        showToast(true, "注销通知栏监听服务成功");
                        // 注销监听
                        NotificationService.stopService();
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

        // 设置监听事件
        NotificationService.setNotificationListener(new NotificationService.NotificationListener() {
            @Override
            public void onServiceCreated(NotificationService service) {
                DevLogger.dTag(mTag, "服务创建通知");
            }

            @Override
            public void onServiceDestroy() {
                DevLogger.dTag(mTag, "服务销毁通知");
            }

            @Override
            public int onStartCommand(NotificationService service, Intent intent, int flags, int startId) { // 触发服务指令
                StringBuilder builder = new StringBuilder();
                builder.append("onServiceStartCommand");
                builder.append("\nintent: " + intent.toString());
                builder.append("\nflags: " + flags);
                builder.append("\nstartId: " + startId);
                DevLogger.dTag(mTag, builder.toString());
                return 0;
            }

            @Override
            public void onNotificationPosted(StatusBarNotification sbn) { // 当系统收到新的通知后触发回调
                StringBuilder builder = new StringBuilder();
                builder.append("onNotificationPosted");

                Notification notification = sbn.getNotification();
                builder.append("\nstatusBarNotification: " + sbn.toString());
                builder.append("\ntickerText : " + notification.tickerText);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Bundle bundle = notification.extras;
                    for (String key : bundle.keySet()) {
                        builder.append("\n" + key + ": " + bundle.get(key));
                    }
                }
                // 打印日志
                DevLogger.dTag(mTag, builder.toString());
            }

            @Override
            public void onNotificationRemoved(StatusBarNotification sbn) { // 当系统通知被删掉后触发回调
                StringBuilder builder = new StringBuilder();
                builder.append("onNotificationRemoved");
                builder.append("\nstatusBarNotification: " + sbn.toString());
                // 打印日志
                DevLogger.dTag(mTag, builder.toString());
            }
        });
    }
}
