package afkt.project.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.net.wifi.WifiConfiguration;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseActivity;
import afkt.project.databinding.BaseViewRecyclerviewBinding;
import afkt.project.model.item.ButtonList;
import afkt.project.model.item.ButtonValue;
import afkt.project.ui.adapter.ButtonAdapter;
import afkt.project.util.QuickWifiHotUtils;
import dev.engine.log.DevLogEngine;
import dev.receiver.WifiReceiver;
import dev.utils.app.permission.PermissionUtils;
import dev.utils.app.toast.ToastTintUtils;
import dev.utils.app.toast.ToastUtils;
import dev.utils.app.wifi.WifiHotUtils;
import dev.utils.app.wifi.WifiUtils;

/**
 * detail: Wifi 相关 ( 热点 )
 * @author Ttt
 * <pre>
 *     Wifi 热点状态监听等可参考 {@link QuickWifiHotUtils}
 * </pre>
 */
public class WifiActivity
        extends BaseActivity<BaseViewRecyclerviewBinding> {

    // Wifi 工具类
    WifiUtils    wifiUtils;
    // Wifi 热点工具类
    WifiHotUtils wifiHotUtils;
    // 热点名、密码
    String       wifiHotSSID = "DevWifiAp", wifiHotPwd = "123456789";
    // Android 8.0 开启热点不能多次点击
    boolean isOpenAPING = false;

    @Override
    public int baseLayoutId() {
        return R.layout.base_view_recyclerview;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销监听
        WifiReceiver.unregisterReceiver();
    }

    @Override
    public void initValue() {
        super.initValue();

        // 初始化工具类
        wifiUtils = new WifiUtils();
        wifiHotUtils = new WifiHotUtils();

        // 无法进行申请, 只能跳转到权限页面, 让用户开启
        // 获取写入设置权限 , 必须有这个权限, 否则无法开启
        PermissionUtils.permission(Manifest.permission.WRITE_SETTINGS).request(this);

        // 初始化布局管理器、适配器
        final ButtonAdapter buttonAdapter = new ButtonAdapter(ButtonList.getWifiButtonValues());
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
                    case ButtonValue.BTN_WIFI_OPEN:
                        if (wifiUtils.isOpenWifi()) {
                            ToastTintUtils.error("Wifi 已打开");
                            return;
                        } else {
                            boolean success = wifiUtils.openWifi();
                            showToast(success, "打开成功", "打开失败");
                        }
                        break;
                    case ButtonValue.BTN_WIFI_CLOSE:
                        if (!wifiUtils.isOpenWifi()) {
                            ToastTintUtils.error("Wifi 已关闭");
                            return;
                        } else {
                            boolean success = wifiUtils.closeWifi();
                            showToast(success, "关闭成功", "关闭失败");
                        }
                        break;
                    case ButtonValue.BTN_WIFI_HOT_OPEN:
                        if (wifiHotUtils.isOpenWifiAp()) {
                            ToastTintUtils.error("Wifi 热点已打开");
                            return;
                        } else {
                            if (isOpenAPING) {
                                ToastUtils.showShort("Wifi 热点开启中");
                                return;
                            }
                            // = 8.0 特殊处理 =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                PermissionUtils.permission(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                ).callback(new PermissionUtils.PermissionCallback() {
                                    @Override
                                    public void onGranted() {
                                        isOpenAPING = true;
                                        // 设置热点 Wifi 监听
                                        wifiHotUtils.setOnWifiAPListener(new WifiHotUtils.OnWifiAPListener() {
                                            @Override
                                            public void onStarted(WifiConfiguration wifiConfiguration) {
                                                String wifiap = "ssid: " + wifiConfiguration.SSID + "\npwd: " + wifiConfiguration.preSharedKey;
                                                DevLogEngine.getEngine().dTag(TAG, wifiap);
                                                ToastTintUtils.success(wifiap);
                                                // 表示操作结束
                                                isOpenAPING = false;
                                            }

                                            @Override
                                            public void onStopped() {
                                                DevLogEngine.getEngine().dTag(TAG, "关闭热点");
                                                // 表示操作结束
                                                isOpenAPING = false;
                                            }

                                            @Override
                                            public void onFailed(int reason) {
                                                DevLogEngine.getEngine().dTag(TAG, "热点异常 reason: %s", reason);
                                                // 表示操作结束
                                                isOpenAPING = false;
                                            }
                                        });
                                        // 密码必须大于等于 8 位
                                        WifiConfiguration wifiConfiguration = WifiHotUtils.createWifiConfigToAp(wifiHotSSID, wifiHotPwd);
                                        @SuppressLint("MissingPermission")
                                        boolean success = wifiHotUtils.startWifiAp(wifiConfiguration);
                                        showToast(success, "打开热点成功", "打开热点失败");
                                    }

                                    @Override
                                    public void onDenied(
                                            List<String> grantedList,
                                            List<String> deniedList,
                                            List<String> notFoundList
                                    ) {
                                        ToastTintUtils.warning("开启热点需要定位权限");
                                    }
                                }).request(mActivity);
                                return;
                            } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) { // 7.0 及以下需要 WRITE_SETTINGS 权限
//                                // 无法进行申请, 只能跳转到权限页面, 让用户开启
//                                // 获取写入设置权限 , 必须有这个权限, 否则无法开启
//                                if (!PermissionUtils.isGranted(Manifest.permission.WRITE_SETTINGS)){
//                                    ToastUtils.showShort("开启热点需要修改系统设置权限");
//                                    // 如果没有权限则跳转过去
//                                    AppUtils.startActivity(IntentUtils.getLaunchAppDetailsSettingsIntent(AppUtils.getPackageName()));
//                                    return;
//                                }
                            }
                            // 密码必须大于等于 8 位
                            WifiConfiguration wifiConfiguration = WifiHotUtils.createWifiConfigToAp(wifiHotSSID, wifiHotPwd);
                            // 如果不需要密码, 则设置为非密码
                            //wifiConfiguration = WifiHotUtils.createWifiConfigToAp("TttWifiAp1", null);
                            // 开启热点
                            @SuppressLint("MissingPermission")
                            boolean success = wifiHotUtils.startWifiAp(wifiConfiguration); // Android 7.1 以上特殊处理
                            showToast(success, "打开热点成功", "打开热点失败");
                        }
                        break;
                    case ButtonValue.BTN_WIFI_HOT_CLOSE:
                        if (!wifiHotUtils.isOpenWifiAp()) {
                            ToastTintUtils.error("Wifi 热点已关闭");
                            return;
                        } else {
                            boolean success = wifiHotUtils.closeWifiAp();
                            showToast(success, "热点关闭成功", "热点关闭失败");
                        }
                        break;
                    case ButtonValue.BTN_WIFI_LISTENER_REGISTER:
                        WifiReceiver.registerReceiver();
                        showToast(true, "注册监听成功, 请查看 Logcat");
                        break;
                    case ButtonValue.BTN_WIFI_LISTENER_UNREGISTER:
                        WifiReceiver.unregisterReceiver();
                        showToast(true, "注销监听成功");
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
    }

    // ===========
    // = 检测任务 =
    // ===========

    // 检查热点状态
    private final int CHECK_HOTSOPT_STATE = 100;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                // 检查热点状态 ( 如果开启了热点, 则关闭热点开启 Wifi )
                case CHECK_HOTSOPT_STATE:
                    // 判断是否打开了热点, 如果开启了则进行关闭
                    if (wifiHotUtils.closeWifiApCheck(true)) {
                        // 进行延迟检查
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // 防止出现意外, 再次关闭
                                if (wifiHotUtils.closeWifiApCheck(true)) {
                                    try {
                                        // 堵塞 1.5 秒
                                        Thread.sleep(1500);
                                    } catch (Exception e) {
                                    }
                                }
                                // 防止页面已经关闭
                                if (!isFinishing() && wifiUtils != null) {
                                    // 打开 Wifi
                                    wifiUtils.openWifi();
                                }
                            }
                        }, 1500);
                    } else { // 如果没有开启热点, 则判断是否开启 Wifi
                        // 判断是否开启 Wifi
                        if (!wifiUtils.isOpenWifi()) {
                            wifiUtils.openWifi();
                        }
                    }
                    break;
            }
        }
    };
}