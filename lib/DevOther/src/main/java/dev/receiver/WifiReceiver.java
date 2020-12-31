package dev.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Message;
import android.os.Parcelable;

import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;
import dev.utils.app.wifi.WifiUtils;

/**
 * detail: Wifi 监听广播
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 *     <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 * </pre>
 */
public final class WifiReceiver
        extends BroadcastReceiver {

    private WifiReceiver() {
        super();
    }

    // 日志 TAG
    private static final String TAG = WifiReceiver.class.getSimpleName();

    // ========
    // = 常量 =
    // ========

    private static final int BASE                      = 302030;
    // startScan() 扫描附近 Wifi 结束触发
    public static final  int WIFI_SCAN_FINISH          = BASE + 1;
    // 已连接的 Wifi 强度发生变化
    public static final  int WIFI_RSSI_CHANGED         = BASE + 2;
    // Wifi 认证错误 ( 密码错误等 )
    public static final  int WIFI_ERROR_AUTHENTICATING = BASE + 3;
    // 连接错误 ( 其他错误 )
    public static final  int WIFI_ERROR_UNKNOWN        = BASE + 4;
    // Wifi 已打开
    public static final  int WIFI_STATE_ENABLED        = BASE + 5;
    // Wifi 正在打开
    public static final  int WIFI_STATE_ENABLING       = BASE + 6;
    // Wifi 已关闭
    public static final  int WIFI_STATE_DISABLED       = BASE + 7;
    // Wifi 正在关闭
    public static final  int WIFI_STATE_DISABLING      = BASE + 8;
    // Wifi 状态未知
    public static final  int WIFI_STATE_UNKNOWN        = BASE + 9;
    // Wifi 连接成功
    public static final  int CONNECTED                 = BASE + 10;
    // Wifi 连接中
    public static final  int CONNECTING                = BASE + 11;
    // Wifi 连接失败、断开
    public static final  int DISCONNECTED              = BASE + 12;
    // Wifi 暂停、延迟
    public static final  int SUSPENDED                 = BASE + 13;
    // Wifi 未知
    public static final  int UNKNOWN                   = BASE + 14;

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(
            Context context,
            Intent intent
    ) {
        try {
            // 触发回调通知 ( 每次进入都通知 )
            if (sListener != null) sListener.onIntoTrigger();
            // 触发意图
            String action = intent.getAction();
            // 打印当前触发的广播
            LogPrintUtils.dTag(TAG, "onReceive Action: %s", action);
            // 判断类型
            switch (action) {
                case WifiManager.SCAN_RESULTS_AVAILABLE_ACTION: // 当调用 WifiManager 的 startScan() 方法, 扫描结束后, 系统会发出改 Action 广播
                    if (sListener != null) {
                        sListener.onTrigger(WIFI_SCAN_FINISH);
                    }
                    break;
                case WifiManager.RSSI_CHANGED_ACTION: // 当前连接的 Wifi 强度发生变化触发
                    if (sListener != null) {
                        sListener.onTrigger(WIFI_RSSI_CHANGED);
                    }
                    break;
                case WifiManager.SUPPLICANT_STATE_CHANGED_ACTION: // 发送 Wifi 连接的过程信息, 如果出错 ERROR 信息才会收到, 连接 Wifi 时触发, 触发多次
                    // 出现错误状态, 则获取错误状态
                    int wifiErrorCode = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, 0);
                    // 获取错误状态
                    switch (wifiErrorCode) {
                        case WifiManager.ERROR_AUTHENTICATING: // 认证错误, 如密码错误等
                            if (sListener != null) {
                                sListener.onTrigger(WIFI_ERROR_AUTHENTICATING);
                            }
                            break;
                        default: // 连接错误 ( 其他错误 )
                            if (sListener != null) {
                                sListener.onTrigger(WIFI_ERROR_UNKNOWN);
                            }
                            break;
                    }
                    break;
                case WifiManager.WIFI_STATE_CHANGED_ACTION: // 监听 Wifi 打开与关闭等状态
                    // 获取 Wifi 状态
                    int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
                    switch (wifiState) {
                        case WifiManager.WIFI_STATE_ENABLED: // 已打开
                            if (sListener != null) {
                                sListener.onTrigger(WIFI_STATE_ENABLED);
                            }
                            break;
                        case WifiManager.WIFI_STATE_ENABLING: // 正在打开
                            if (sListener != null) {
                                sListener.onTrigger(WIFI_STATE_ENABLING);
                            }
                            break;
                        case WifiManager.WIFI_STATE_DISABLED: // 已关闭
                            if (sListener != null) {
                                sListener.onTrigger(WIFI_STATE_DISABLED);
                            }
                            break;
                        case WifiManager.WIFI_STATE_DISABLING: // 正在关闭
                            if (sListener != null) {
                                sListener.onTrigger(WIFI_STATE_DISABLING);
                            }
                            break;
                        case WifiManager.WIFI_STATE_UNKNOWN: // 未知
                            if (sListener != null) {
                                sListener.onTrigger(WIFI_STATE_UNKNOWN);
                            }
                            break;
                    }
                    break;
                case WifiManager.NETWORK_STATE_CHANGED_ACTION: // Wifi 连接过程的状态
                    Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    if (parcelableExtra != null) {
                        // 获取连接信息
                        NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                        // 获取连接的状态
                        NetworkInfo.State state = networkInfo.getState();
                        // 通知消息
                        Message msg = new Message();
                        // 当前连接的 SSID
                        msg.obj = WifiUtils.getSSID();
                        // 判断连接状态
                        switch (state) {
                            case CONNECTED: // 连接成功
                                msg.what = CONNECTED;
                                break;
                            case CONNECTING: // 连接中
                                msg.what = CONNECTING;
                                break;
                            case DISCONNECTED: // 连接失败、断开
                                msg.what = DISCONNECTED;
                                break;
                            case SUSPENDED: // 暂停、延迟
                                msg.what = SUSPENDED;
                                break;
                            case UNKNOWN: // 未知
                                msg.what = UNKNOWN;
                                break;
                        }
                        // 触发回调
                        if (sListener != null) {
                            sListener.onTrigger(msg.what, msg);
                        }
                    }
                    break;
                case WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION: // 判断是否 Wifi 打开了, 变化触发一次
                    // 判断是否打开 Wifi
                    boolean isOpenWifi = intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false);
                    // 触发回调
                    if (sListener != null) {
                        sListener.onWifiSwitch(isOpenWifi);
                    }
                    break;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "onReceive");
        }
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    // Wifi 监听广播
    private static final WifiReceiver sReceiver = new WifiReceiver();

    /**
     * 注册 Wifi 监听广播
     */
    public static void registerReceiver() {
        try {
            IntentFilter filter = new IntentFilter();
            // 当调用 WifiManager 的 startScan() 方法, 扫描结束后, 系统会发出改 Action 广播
            filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
            // 当前连接的 Wifi 强度发生变化触发
            filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
            // Wifi 在连接过程的状态返回
            filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
            // 监听 Wifi 的打开与关闭等状态, 与 Wifi 的连接无关
            filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
            // 发送 Wifi 连接的过程信息, 如果出错 ERROR 信息才会收到, 连接 Wifi 时触发, 触发多次
            filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
            // 判断是否 Wifi 打开了, 变化触发一次
            filter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
            // 注册广播
            AppUtils.registerReceiver(sReceiver, filter);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerReceiver");
        }
    }

    /**
     * 取消注册 Wifi 监听广播
     */
    public static void unregisterReceiver() {
        try {
            AppUtils.unregisterReceiver(sReceiver);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "unregisterReceiver");
        }
    }

    // =

    // Wifi 监听事件
    private static WifiListener sListener;

    /**
     * 设置 Wifi 监听事件
     * @param listener {@link WifiListener}
     * @return {@link WifiReceiver}
     */
    public static WifiReceiver setWifiListener(final WifiListener listener) {
        WifiReceiver.sListener = listener;
        return sReceiver;
    }

    /**
     * detail: Wifi 监听事件
     * @author Ttt
     */
    public static abstract class WifiListener {

        /**
         * 触发回调通知 ( 每次进入都通知 )
         */
        public void onIntoTrigger() {
        }

        /**
         * 触发回调通知
         * @param what 触发类型
         */
        public abstract void onTrigger(int what);

        /**
         * 触发回调通知 ( Wifi 连接过程的状态 )
         * @param what 触发类型
         * @param msg  触发信息
         */
        public abstract void onTrigger(
                int what,
                Message msg
        );

        /**
         * Wifi 开关状态
         * @param isOpenWifi 是否打开 Wifi
         */
        public abstract void onWifiSwitch(boolean isOpenWifi);
    }
}