package afkt.project.ui.activity;

import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import dev.engine.log.DevLogEngine;
import dev.service.AccessibilityListenerService;
import dev.utils.app.AppUtils;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: 无障碍监听服务 ( AccessibilityListenerService )
 * @author Ttt
 * <pre>
 *     @see <a href="https://www.jianshu.com/p/981e7de2c7be"/>
 *     所需权限
 *     <uses-permission android:name="android.permission.BIND_NOTIFICATION_LISTENER_SERVICE" />
 * </pre>
 */
public class AccessibilityListenerServiceActivity
        extends BaseActivity<BaseViewRecyclerviewBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销监听
        AccessibilityListenerService.setAccessibilityListener(null);
        AccessibilityListenerService.stopService();
    }

    @Override
    public void initValue() {
        super.initValue();

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getAccessibilityListenerServiceButtonValues());
        binding.vidBvrRecy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(
                    BaseQuickAdapter adapter,
                    View view,
                    int position
            ) {
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {
                    case ButtonValue.BTN_ACCESSIBILITY_SERVICE_CHECK:
                        boolean check = AccessibilityListenerService.isAccessibilitySettingsOn(AppUtils.getPackageName());
                        showToast(check, "已开启无障碍功能", "未开启无障碍功能");
                        break;
                    case ButtonValue.BTN_ACCESSIBILITY_SERVICE_REGISTER:
                        if (!AccessibilityListenerService.checkAccessibility()) {
                            showToast(false, "请先开启无障碍功能");
                            return;
                        }
                        showToast(true, "绑定无障碍监听服务成功, 请查看 Logcat");
                        // 注册监听
                        AccessibilityListenerService.startService();
                        break;
                    case ButtonValue.BTN_ACCESSIBILITY_SERVICE_UNREGISTER:
                        showToast(true, "注销无障碍监听服务成功");
                        // 注销监听
                        AccessibilityListenerService.stopService();
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();

        // 设置监听事件
        AccessibilityListenerService.setAccessibilityListener(new AccessibilityListenerService.AccessibilityListener() {
            @Override
            public void onAccessibilityEvent(
                    AccessibilityEvent accessibilityEvent,
                    AccessibilityListenerService accessibilityListenerService
            ) {
                StringBuilder builder = new StringBuilder();
                builder.append("onAccessibilityEvent");
                builder.append("\naccessibilityEvent: ").append(accessibilityEvent);
                DevLogEngine.getEngine().dTag(TAG, builder.toString());
            }

            @Override
            public void onInterrupt() {
                super.onInterrupt();
                DevLogEngine.getEngine().dTag(TAG, "onInterrupt");
            }

            @Override
            public void onServiceCreated(AccessibilityListenerService service) {
                super.onServiceCreated(service);
                DevLogEngine.getEngine().dTag(TAG, "onServiceCreated");
            }

            @Override
            public void onServiceDestroy() {
                super.onServiceDestroy();
                DevLogEngine.getEngine().dTag(TAG, "onServiceDestroy");
            }
        });
    }
}