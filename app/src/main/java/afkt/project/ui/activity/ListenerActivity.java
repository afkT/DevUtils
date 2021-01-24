package afkt.project.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;
import android.view.OrientationEventListener;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.ActivityCommonTipsBinding;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import dev.engine.log.DevLogEngine;
import dev.receiver.AppStateReceiver;
import dev.receiver.BatteryReceiver;
import dev.receiver.NetWorkReceiver;
import dev.receiver.PhoneReceiver;
import dev.receiver.ScreenReceiver;
import dev.receiver.SmsReceiver;
import dev.receiver.TimeReceiver;
import dev.receiver.WifiReceiver;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.assist.ScreenSensorAssist;
import dev.utils.app.helper.ViewHelper;
import dev.utils.app.toast.ToastTintUtils;

/**
 * detail: 事件 / 广播监听 ( 网络状态、屏幕旋转等 )
 * @author Ttt
 */
public class ListenerActivity
        extends BaseActivity<ActivityCommonTipsBinding> {

    @Override
    public int baseLayoutId() {
        return R.layout.activity_common_tips;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销监听
        WifiReceiver.unregisterReceiver();
        NetWorkReceiver.unregisterReceiver();
        PhoneReceiver.unregisterReceiver();
        SmsReceiver.unregisterReceiver();
        TimeReceiver.unregisterReceiver();
        ScreenReceiver.unregisterReceiver();
        BatteryReceiver.unregisterReceiver();
        AppStateReceiver.unregisterReceiver();
        screenSensorAssist.stop();
        try {
            mOrientationEventListener.disable();
        } catch (Exception e) {
        }
    }

    @Override
    public void initValue() {
        super.initValue();

        View view = ViewUtils.inflate(R.layout.base_view_textview);
        ViewHelper.get().setText(view, "单击绑定, 长按注销")
                .setTextColor(view, ResourceUtils.getColor(R.color.gray));
        binding.vidActLinear.addView(view);

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getListenerButtonValues());
        binding.vidBaseRecy.vidBvrRecy.setAdapter(buttonAdapter);
        buttonAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(
                    BaseQuickAdapter adapter,
                    View view,
                    int position
            ) {
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {
                    case ButtonValue.BTN_WIFI_LISTENER:
                        wifiListener(true);
                        break;
                    case ButtonValue.BTN_NETWORK_LISTENER:
                        netListener(true);
                        break;
                    case ButtonValue.BTN_PHONE_LISTENER:
                        phoneListener(true);
                        break;
                    case ButtonValue.BTN_SMS_LISTENER:
                        smsListener(true);
                        break;
                    case ButtonValue.BTN_TIME_LISTENER:
                        timeListener(true);
                        break;
                    case ButtonValue.BTN_SCREEN_LISTENER:
                        screenListener(true);
                        break;
                    case ButtonValue.BTN_ROTA_LISTENER:
                        rotaListener(true);
                        break;
                    case ButtonValue.BTN_ROTA2_LISTENER:
                        rotaListener2(true);
                        break;
                    case ButtonValue.BTN_BATTERY_LISTENER:
                        batteryListener(true);
                        break;
                    case ButtonValue.BTN_APP_STATE_LISTENER:
                        appStateListener(true);
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
            }
        });
        buttonAdapter.setOnItemChildLongClickListener(new OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(
                    BaseQuickAdapter adapter,
                    View view,
                    int position
            ) {
                ButtonValue buttonValue = buttonAdapter.getItem(position);
                switch (buttonValue.type) {
                    case ButtonValue.BTN_WIFI_LISTENER:
                        wifiListener(false);
                        break;
                    case ButtonValue.BTN_NETWORK_LISTENER:
                        netListener(false);
                        break;
                    case ButtonValue.BTN_PHONE_LISTENER:
                        phoneListener(false);
                        break;
                    case ButtonValue.BTN_SMS_LISTENER:
                        smsListener(false);
                        break;
                    case ButtonValue.BTN_TIME_LISTENER:
                        timeListener(false);
                        break;
                    case ButtonValue.BTN_SCREEN_LISTENER:
                        screenListener(false);
                        break;
                    case ButtonValue.BTN_ROTA_LISTENER:
                        rotaListener(false);
                        break;
                    case ButtonValue.BTN_ROTA2_LISTENER:
                        rotaListener2(false);
                        break;
                    case ButtonValue.BTN_BATTERY_LISTENER:
                        batteryListener(false);
                        break;
                    case ButtonValue.BTN_APP_STATE_LISTENER:
                        appStateListener(false);
                        break;
                    default:
                        ToastTintUtils.warning("未处理 " + buttonValue.text + " 事件");
                        break;
                }
                return true;
            }
        });
    }

    // ============
    // = Listener =
    // ============

    /**
     * Wifi 监听
     * @param isBind 是否绑定
     */
    private void wifiListener(boolean isBind) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销 Wifi 监听成功");
            // 清空回调
            WifiReceiver.setWifiListener(null);
            // 注销监听
            WifiReceiver.unregisterReceiver();
        } else {
            ToastTintUtils.success("绑定 Wifi 监听成功, 请查看 Logcat");
            // 设置监听事件
            WifiReceiver.setWifiListener(new WifiReceiver.WifiListener() {
                @Override
                public void onWifiSwitch(boolean isOpenWifi) { // Wifi 开关状态
                    DevLogEngine.getEngine().dTag(TAG, "Wifi 是否打开: %s", isOpenWifi);
                }

                @Override
                public void onIntoTrigger() {
                    super.onIntoTrigger();
                    DevLogEngine.getEngine().dTag(TAG, "触发回调通知 ( 每次进入都通知 )");
                }

                @Override
                public void onTrigger(int what) {
                    switch (what) {
                        case WifiReceiver.WIFI_SCAN_FINISH: // startScan() 扫描附近 Wifi 结束触发
                            DevLogEngine.getEngine().dTag(TAG, "startScan() 扫描附近 Wifi 结束触发");
                            break;
                        case WifiReceiver.WIFI_RSSI_CHANGED: // 已连接的 Wifi 强度发生变化
                            DevLogEngine.getEngine().dTag(TAG, "已连接的 Wifi 强度发生变化");
                            break;
                        case WifiReceiver.WIFI_ERROR_AUTHENTICATING: // Wifi 认证错误 ( 密码错误等 )
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 认证错误 ( 密码错误等 )");
                            break;
                        case WifiReceiver.WIFI_ERROR_UNKNOWN: // 连接错误 ( 其他错误 )
                            DevLogEngine.getEngine().dTag(TAG, "连接错误 ( 其他错误 )");
                            break;
                        case WifiReceiver.WIFI_STATE_ENABLED: // Wifi 已打开
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 已打开");
                            break;
                        case WifiReceiver.WIFI_STATE_ENABLING: // Wifi 正在打开
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 正在打开");
                            break;
                        case WifiReceiver.WIFI_STATE_DISABLED: // Wifi  已关闭
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 已关闭");
                            break;
                        case WifiReceiver.WIFI_STATE_DISABLING: // Wifi  正在关闭
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 正在关闭");
                            break;
                        case WifiReceiver.WIFI_STATE_UNKNOWN: // Wifi  状态未知
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 状态未知");
                            break;
                        case WifiReceiver.CONNECTED: // Wifi  连接成功
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 连接成功");
                            break;
                        case WifiReceiver.CONNECTING: // Wifi  连接中
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 连接中");
                            break;
                        case WifiReceiver.DISCONNECTED: // Wifi  连接失败、断开
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 连接失败、断开");
                            break;
                        case WifiReceiver.SUSPENDED: // Wifi  暂停、延迟
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 暂停、延迟");
                            break;
                        case WifiReceiver.UNKNOWN: // Wifi  未知
                            DevLogEngine.getEngine().dTag(TAG, "Wifi 未知");
                            break;
                    }
                }

                @Override
                public void onTrigger(
                        int what,
                        Message message
                ) { // Wifi 在连接过程的状态返回
                    String ssid = (String) message.obj;
                    // 判断连接状态
                    switch (what) {
                        case WifiReceiver.CONNECTED: // Wifi 连接成功
                            DevLogEngine.getEngine().dTag(TAG, "连接 Wifi 成功: %s", message.obj);
                            break;
                        case WifiReceiver.CONNECTING: // Wifi 连接中
                            DevLogEngine.getEngine().dTag(TAG, "连接 Wifi 中: %s", message.obj);
                            break;
                        case WifiReceiver.DISCONNECTED: // Wifi 连接失败、断开
                            DevLogEngine.getEngine().dTag(TAG, "连接 Wifi 断开");
                            break;
                        case WifiReceiver.SUSPENDED: // Wifi 暂停、延迟
                            DevLogEngine.getEngine().dTag(TAG, "连接 Wifi 暂停、延迟");
                            break;
                        case WifiReceiver.UNKNOWN: // Wifi 未知
                            DevLogEngine.getEngine().dTag(TAG, "连接 Wifi 状态未知");
                            break;
                    }
                }
            });
            // 注册监听
            WifiReceiver.registerReceiver();
        }
    }

    /**
     * 网络监听
     * @param isBind 是否绑定
     */
    private void netListener(boolean isBind) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销网络监听成功");
            // 清空回调
            NetWorkReceiver.setNetListener(null);
            // 注销监听
            NetWorkReceiver.unregisterReceiver();
        } else {
            ToastTintUtils.success("绑定网络监听成功, 请查看 Logcat");
            // 设置监听事件
            NetWorkReceiver.setNetListener(new NetWorkReceiver.NetWorkStateListener() {
                @Override
                public void onNetworkState(int nType) {
                    String state = "";
                    switch (nType) {
                        case NetWorkReceiver.NET_WIFI: // Wifi
                            state = "Wifi";
                            break;
                        case NetWorkReceiver.NET_MOBILE: // 移动网络
                            state = "移动网络";
                            break;
                        case NetWorkReceiver.NO_NETWORK: // ( 无网络 / 未知 ) 状态
                            state = "( 无网络 / 未知 ) 状态";
                            break;
                    }
                    DevLogEngine.getEngine().dTag(TAG, "网络连接状态 %s", state);
                }
            });
            // 注册监听
            NetWorkReceiver.registerReceiver();
        }
    }

    /**
     * 电话监听
     * @param isBind 是否绑定
     */
    private void phoneListener(boolean isBind) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销电话监听成功");
            // 清空回调
            PhoneReceiver.setPhoneListener(null);
            // 注销监听
            PhoneReceiver.unregisterReceiver();
        } else {
            ToastTintUtils.success("绑定电话监听成功, 请查看 Logcat");
            // 设置监听事件
            PhoneReceiver.setPhoneListener(new PhoneReceiver.PhoneListener() {
                @Override
                public void onPhoneStateChanged(
                        PhoneReceiver.CallState callState,
                        String number
                ) {
                    /**
                     * 分别是:
                     * 播出电话
                     * 播出电话结束
                     * 接入电话铃响
                     * 接入通话中
                     * 接入通话完毕
                     */
                    switch (callState) {
                        case Outgoing:
                            DevLogEngine.getEngine().dTag(TAG, "播出电话: %s", number);
                            break;
                        case OutgoingEnd:
                            DevLogEngine.getEngine().dTag(TAG, "播出电话结束: %s", number);
                            break;
                        case IncomingRing:
                            DevLogEngine.getEngine().dTag(TAG, "接入电话铃响: %s", number);
                            break;
                        case Incoming:
                            DevLogEngine.getEngine().dTag(TAG, "接入通话中: %s", number);
                            break;
                        case IncomingEnd:
                            DevLogEngine.getEngine().dTag(TAG, "接入通话完毕: %s", number);
                            break;
                    }
                }
            });
            // 注册监听
            PhoneReceiver.registerReceiver();
        }
    }

    /**
     * 短信监听
     * @param isBind 是否绑定
     */
    private void smsListener(boolean isBind) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销短信监听成功");
            // 清空回调
            SmsReceiver.setSmsListener(null);
            // 注销监听
            SmsReceiver.unregisterReceiver();
        } else {
            ToastTintUtils.success("绑定短信监听成功, 请查看 Logcat");
            // 设置监听事件
            SmsReceiver.setSmsListener(new SmsReceiver.SmsListener() {
                @Override
                public void onMessage(
                        String msg,
                        String fromAddress,
                        String serviceCenterAddress
                ) {
                    DevLogEngine.getEngine().dTag(TAG, "onMessage\nmsg: %s\nfromAddress: %s\nserviceCenterAddress: %s", msg, fromAddress, serviceCenterAddress);
                }

                @Override
                public void onMessage(SmsMessage msg) {
                    super.onMessage(msg);
                    DevLogEngine.getEngine().dTag(TAG, "onMessage\nSmsMessage: %s", msg.toString());
                }
            });
            // 注册监听
            SmsReceiver.registerReceiver();
        }
    }

    /**
     * 时区、时间监听
     * @param isBind 是否绑定
     */
    private void timeListener(boolean isBind) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销时区、时间监听成功");
            // 清空回调
            TimeReceiver.setTimeListener(null);
            // 注销监听
            TimeReceiver.unregisterReceiver();
        } else {
            ToastTintUtils.success("绑定时区、时间监听成功, 请查看 Logcat");
            // 设置监听事件
            TimeReceiver.setTimeListener(new TimeReceiver.TimeListener() {
                @Override
                public void onTimeZoneChanged() {
                    DevLogEngine.getEngine().dTag(TAG, "onTimeZoneChanged: 时区改变");
                }

                @Override
                public void onTimeChanged() {
                    DevLogEngine.getEngine().dTag(TAG, "onTimeChanged: 时间改变");
                }

                @Override
                public void onTimeTick() {
                    DevLogEngine.getEngine().dTag(TAG, "onTimeTick: 分钟改变");
                }
            });
            // 注册监听
            TimeReceiver.registerReceiver();
        }
    }

    /**
     * 屏幕监听
     * @param isBind 是否绑定
     */
    private void screenListener(boolean isBind) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销屏幕监听成功");
            // 清空回调
            ScreenReceiver.setScreenListener(null);
            // 注销监听
            ScreenReceiver.unregisterReceiver();
        } else {
            ToastTintUtils.success("绑定屏幕监听成功, 请查看 Logcat");
            // 设置监听事件
            ScreenReceiver.setScreenListener(new ScreenReceiver.ScreenListener() {
                @Override
                public void screenOn() {
                    DevLogEngine.getEngine().dTag(TAG, "screenOn: 用户打开屏幕 - 屏幕变亮");
                }

                @Override
                public void screenOff() {
                    DevLogEngine.getEngine().dTag(TAG, "screenOff: 锁屏触发");
                }

                @Override
                public void userPresent() {
                    DevLogEngine.getEngine().dTag(TAG, "userPresent: 用户解锁触发");
                }
            });
            // 注册监听
            ScreenReceiver.registerReceiver();
        }
    }

    // =

    // 重力传感器辅助类
    private final ScreenSensorAssist screenSensorAssist = new ScreenSensorAssist();
    // 切屏时间
    private       long               cOrientationTime   = 0L;

    /**
     * 屏幕旋转监听 ( 重力传感器 )
     * @param isBind 是否绑定
     */
    private void rotaListener(boolean isBind) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销屏幕旋转监听 ( 重力传感器 ) 成功");
            // 注销监听
            screenSensorAssist.stop();
        } else {
            ToastTintUtils.success("绑定屏幕旋转监听 ( 重力传感器 ) 成功, 请查看 Logcat");
            // 注册监听
            screenSensorAssist.start(new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case ScreenSensorAssist.CHANGE_ORIENTATION_WHAT: // 触发屏幕方向改变回调
                            if (!screenSensorAssist.isAllowChange()) { // 如果不允许切屏, 则不显示
                                return;
                            } else if (isFinishing()) { // 如果页面关闭了
                                return;
                            }
                            // 获取触发的方向
                            int orientation = msg.arg1;
                            // 判断方向
                            if (orientation == 1) { // 横屏
                                // 当前时间 - 切屏的时间大于 1.5 秒间隔才进行处理
                                if (System.currentTimeMillis() - cOrientationTime >= 1500) {
                                    DevLogEngine.getEngine().dTag(TAG, "横屏");
                                    // 重置时间,防止多次触发
                                    cOrientationTime = System.currentTimeMillis();
                                    // 跳转到横屏, 并且关闭监听
                                    //Intent intent = new Intent(mContext, Activity.class);
                                    //mContext.startActivity(intent);
                                }
                            } else if (orientation == 2) { // 竖屏
                                DevLogEngine.getEngine().dTag(TAG, "竖屏");
                            }
                            break;
                    }
                }
            });
        }
    }

    // =

    // 判断是否竖屏
    private boolean                  isPortrait      = true;
    // 录制角度记录值
    private int                      mRotationFlag   = 90;
    // 录制角度旋值
    private int                      mRotationRecord = 90;
    // 旋转监听事件
    private OrientationEventListener mOrientationEventListener;

    /**
     * 屏幕旋转监听 ( OrientationEventListener )
     * @param isBind 是否绑定
     */
    private void rotaListener2(boolean isBind) {
        if (mOrientationEventListener == null) {
            mOrientationEventListener = new OrientationEventListener(mContext) {
                @Override
                public void onOrientationChanged(int rotation) {
                    if (((rotation >= 0) && (rotation <= 30)) || (rotation >= 330)) {
                        DevLogEngine.getEngine().dTag(TAG, "竖屏拍摄");
                        isPortrait = true;
                        // 竖屏拍摄
                        if (mRotationFlag != 0) {
                            // 这是竖屏视频需要的角度
                            mRotationRecord = 90;
                            // 这是记录当前角度的 flag
                            mRotationFlag = 0;
                        }
                    } else if (((rotation >= 230) && (rotation <= 310))) {
                        DevLogEngine.getEngine().dTag(TAG, "横屏拍摄");
                        isPortrait = false;
                        // 横屏拍摄
                        if (mRotationFlag != 90) {
                            // 这是正横屏视频需要的角度
                            mRotationRecord = 0;
                            // 这是记录当前角度的 flag
                            mRotationFlag = 90;
                        }
                    } else if (rotation > 30 && rotation < 135) {
                        DevLogEngine.getEngine().dTag(TAG, "反横屏拍摄");
                        isPortrait = false;
                        // 反横屏拍摄
                        if (mRotationFlag != 270) {
                            // 这是反横屏视频需要的角度
                            mRotationRecord = 180;
                            // 这是记录当前角度的 flag
                            mRotationFlag = 270;
                        }
                    } else if (rotation > 135 && rotation < 230) {
                        DevLogEngine.getEngine().dTag(TAG, "反竖屏拍摄");
                        isPortrait = true;
                        // 竖屏拍摄
                        if (mRotationFlag != 360) {
                            // 这是竖屏视频需要的角度
                            mRotationRecord = 270;
                            // 这是记录当前角度的 flag
                            mRotationFlag = 360;
                        }
                    }
                }
            };
        }

        try {
            if (!isBind) { // 取反判断, 方便代码顺序查看
                ToastTintUtils.success("注销屏幕旋转监听 ( OrientationEventListener ) 成功");
                // 注销监听
                mOrientationEventListener.disable();
            } else {
                ToastTintUtils.success("绑定屏幕旋转监听 ( OrientationEventListener ) 成功, 请查看 Logcat");
                // 注册监听
                mOrientationEventListener.enable();
            }
        } catch (Exception e) {
            DevLogEngine.getEngine().eTag(TAG, "rotaListener2");
        }
    }

    /**
     * 电量监听
     * @param isBind 是否绑定
     */
    private void batteryListener(boolean isBind) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销电量监听成功");
            // 清空回调
            BatteryReceiver.setBatteryListener(null);
            // 注销监听
            BatteryReceiver.unregisterReceiver();
        } else {
            ToastTintUtils.success("绑定电量监听成功, 请查看 Logcat");
            // 设置监听事件
            BatteryReceiver.setBatteryListener(new BatteryReceiver.BatteryListener() {
                @Override
                public void onBatteryChanged(int level) {
                    DevLogEngine.getEngine().dTag(TAG, "电量改变通知 level: %s", level);
                }

                @Override
                public void onBatteryLow(int level) {
                    DevLogEngine.getEngine().dTag(TAG, "电量低通知 level: %s", level);
                }

                @Override
                public void onBatteryOkay(int level) {
                    DevLogEngine.getEngine().dTag(TAG, "电量从低变回高通知 level: %s", level);
                }

                @Override
                public void onPowerConnected(
                        int level,
                        boolean isConnected
                ) {
                    DevLogEngine.getEngine().dTag(TAG, "充电状态改变通知 level: %s, 是否充电中: %s", level, isConnected);
                }

                @Override
                public void onPowerUsageSummary(int level) {
                    DevLogEngine.getEngine().dTag(TAG, "电力使用情况总结 level: %s", level);
                }
            });
            // 注册监听
            BatteryReceiver.registerReceiver();
        }
    }

    /**
     * 应用状态监听
     * @param isBind 是否绑定
     */
    private void appStateListener(boolean isBind) {
        if (!isBind) { // 取反判断, 方便代码顺序查看
            ToastTintUtils.success("注销应用状态监听成功");
            // 清空回调
            AppStateReceiver.setAppStateListener(null);
            // 注销监听
            AppStateReceiver.unregisterReceiver();
        } else {
            ToastTintUtils.success("绑定应用状态监听成功, 请查看 Logcat");
            // 设置监听事件
            AppStateReceiver.setAppStateListener(new AppStateReceiver.AppStateListener() {
                @Override
                public void onAdded(String packageName) {
                    DevLogEngine.getEngine().dTag(TAG, "应用安装 packageName: %s", packageName);
                }

                @Override
                public void onReplaced(String packageName) {
                    DevLogEngine.getEngine().dTag(TAG, "应用更新 packageName: %s", packageName);
                }

                @Override
                public void onRemoved(String packageName) {
                    DevLogEngine.getEngine().dTag(TAG, "应用卸载 packageName: %s", packageName);
                }
            });
            // 注册监听
            AppStateReceiver.registerReceiver();
        }
    }
}