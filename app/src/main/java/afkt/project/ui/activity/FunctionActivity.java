package afkt.project.ui.activity;

import android.Manifest;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import java.util.List;

import afkt.project.MainActivity;
import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import dev.engine.log.DevLogEngine;
import dev.utils.app.ActivityUtils;
import dev.utils.app.AppUtils;
import dev.utils.app.DeviceUtils;
import dev.utils.app.FlashlightUtils;
import dev.utils.app.IntentUtils;
import dev.utils.app.MemoryUtils;
import dev.utils.app.NotificationUtils;
import dev.utils.app.ShortCutUtils;
import dev.utils.app.VibrationUtils;
import dev.utils.app.assist.BeepVibrateAssist;
import dev.utils.app.permission.PermissionUtils;
import dev.utils.app.toast.ToastTintUtils;
import dev.utils.app.toast.ToastUtils;

/**
 * detail: 铃声、震动、通知栏等功能
 * @author Ttt
 */
public class FunctionActivity
        extends BaseActivity<BaseViewRecyclerviewBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭手电筒
        FlashlightUtils.getInstance().setFlashlightOff();
        FlashlightUtils.getInstance().unregister();
    }

    @Override
    public void initValue() {
        super.initValue();

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getFunctionButtonValues());
        binding.vidBvrRecy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(
                    BaseQuickAdapter adapter,
                    View view,
                    int position
            ) {
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
                        result = new BeepVibrateAssist(mActivity, R.raw.dev_beep).setVibrate(false).playBeepSoundAndVibrate();
//                        result = new BeepVibrateAssist(mActivity, "xxx/a.mp3").setVibrate(false).playBeepSoundAndVibrate();
                        showToast(result);
                        break;
                    case ButtonValue.BTN_FUNCTION_NOTIFICATION_CHECK:
                        result = NotificationUtils.isNotificationEnabled();
                        showToast(result, "通知权限已开启", "通知权限未开启");
                        break;
                    case ButtonValue.BTN_FUNCTION_NOTIFICATION_OPEN:
                        result = AppUtils.startActivity(IntentUtils.getLaunchAppNotificationSettingsIntent(AppUtils.getPackageName()));
                        showToast(result);
                        break;
                    case ButtonValue.BTN_FUNCTION_NOTIFICATION:
                        result = NotificationUtils.notify(12, NotificationUtils.createNotification(
                                new NotificationUtils.Params(R.mipmap.icon_launcher, "标题", "内容"))
                        );
                        showToast(result);
                        break;
                    case ButtonValue.BTN_FUNCTION_NOTIFICATION_REMOVE:
                        result = NotificationUtils.cancel(12);
                        showToast(result);
                        break;
                    case ButtonValue.BTN_FUNCTION_HOME:
                        result = ActivityUtils.startHomeActivity();
                        showToast(result);
                        break;
                    case ButtonValue.BTN_FUNCTION_FLASHLIGHT_OPEN:
                        PermissionUtils.permission(Manifest.permission.CAMERA).callback(new PermissionUtils.PermissionCallback() {
                            @Override
                            public void onGranted() {
                                // 非传入 Camera 方式需要注册
                                FlashlightUtils.getInstance().register();
                                boolean result = FlashlightUtils.getInstance().setFlashlightOn();
                                showToast(result);
                            }

                            @Override
                            public void onDenied(
                                    List<String> grantedList,
                                    List<String> deniedList,
                                    List<String> notFoundList
                            ) {
                                ToastTintUtils.warning("打开手电筒需摄像头权限");
                            }
                        }).request(mActivity);
                        break;
                    case ButtonValue.BTN_FUNCTION_FLASHLIGHT_CLOSE:
                        result = FlashlightUtils.getInstance().setFlashlightOff();
                        showToast(result);
                        break;
                    case ButtonValue.BTN_FUNCTION_SHORTCUT_CHECK:
                        result = ShortCutUtils.hasShortcut("Dev 快捷方式");
                        showToast(result, "存在快捷方式", "不存在快捷方式");
                        break;
                    case ButtonValue.BTN_FUNCTION_SHORTCUT_CREATE:
                        PermissionUtils.permission(Manifest.permission.INSTALL_SHORTCUT).callback(new PermissionUtils.PermissionCallback() {
                            @Override
                            public void onGranted() {
                                boolean result = ShortCutUtils.addShortcut(MainActivity.class, "Dev 快捷方式", R.mipmap.icon_launcher_round);
                                showToast(result);
                            }

                            @Override
                            public void onDenied(
                                    List<String> grantedList,
                                    List<String> deniedList,
                                    List<String> notFoundList
                            ) {
                                ToastTintUtils.warning("创建快捷方式需要该权限");
                            }
                        }).request(mActivity);
                        break;
                    case ButtonValue.BTN_FUNCTION_SHORTCUT_DELETE:
                        result = ShortCutUtils.deleteShortcut(MainActivity.class, "Dev 快捷方式");
                        showToast(result);
                        break;
                    case ButtonValue.BTN_FUNCTION_MEMORY_PRINT:
                        String memoryInfo = MemoryUtils.printMemoryInfo();
                        ToastUtils.showShort(memoryInfo);
                        DevLogEngine.getEngine().dTag(TAG, memoryInfo);
                        break;
                    case ButtonValue.BTN_FUNCTION_DEVICE_PRINT:
                        String deviceInfo = DeviceUtils.handlerDeviceInfo(DeviceUtils.getDeviceInfo(), "");
                        ToastUtils.showShort(deviceInfo);
                        DevLogEngine.getEngine().dTag(TAG, deviceInfo);
                        break;
                    case ButtonValue.BTN_FUNCTION_APP_DETAILS_SETTINGS:
                        result = AppUtils.launchAppDetailsSettings();
                        showToast(result);
                        break;
                    case ButtonValue.BTN_FUNCTION_GPS_SETTINGS:
                        result = AppUtils.openGpsSettings();
                        showToast(result);
                        break;
                    case ButtonValue.BTN_FUNCTION_WIRELESS_SETTINGS:
                        result = AppUtils.openWirelessSettings();
                        showToast(result);
                        break;
                    case ButtonValue.BTN_FUNCTION_SYS_SETTINGS:
                        result = AppUtils.startSysSetting();
                        showToast(result);
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
    }
}