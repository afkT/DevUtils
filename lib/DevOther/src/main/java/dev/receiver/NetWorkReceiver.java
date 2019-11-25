package dev.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.RequiresPermission;

import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;

/**
 * detail: 网络监听广播
 * @author Ttt
 */
public final class NetWorkReceiver extends BroadcastReceiver {

    private NetWorkReceiver() {
        super();
    }

    // 日志 TAG
    private static final String TAG = NetWorkReceiver.class.getSimpleName();
    // 当前连接的状态
    private static int mConnectState = NetWorkReceiver.NO_NETWORK;

    // ========
    // = 常量 =
    // ========

    private static final int BASE = 202030;
    // Wifi
    public static final int NET_WIFI = BASE + 1;
    // 移动网络
    public static final int NET_MOBILE = BASE + 2;
    // ( 无网络 / 未知 ) 状态
    public static final int NO_NETWORK = BASE + 3;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String action = intent.getAction();
            // 打印当前触发的广播
            LogPrintUtils.dTag(TAG, "NetWorkReceiver onReceive Action: " + action);
            // 网络连接状态改变时通知
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                // 设置连接类型
                mConnectState = getConnectType();
                // 触发事件
                if (NetWorkReceiver.networdStateListener != null) {
                    NetWorkReceiver.networdStateListener.onNetworkState(mConnectState);
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "onReceive");
        }
    }

    // ================
    // = 对外公开方法 =
    // ================

    /**
     * 是否连接网络
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isConnectNetWork() {
        return (mConnectState == NET_WIFI || mConnectState == NET_MOBILE);
    }

    /**
     * 获取连接的网络类型
     * @return 连接的网络类型
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public static int getConnectType() {
        // 获取手机所有连接管理对象 ( 包括对 wi-fi,net 等连接的管理 )
        try {
            // 获取网络连接状态
            ConnectivityManager cManager = AppUtils.getConnectivityManager();
            // 版本兼容处理
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                // 判断连接的是否 wifi
                NetworkInfo.State wifiState = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                // 判断是否连接上
                if (wifiState == NetworkInfo.State.CONNECTED || wifiState == NetworkInfo.State.CONNECTING) {
                    return NET_WIFI;
                } else {
                    // 判断连接的是否移动网络
                    NetworkInfo.State mobileState = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
                    // 判断移动网络是否连接上
                    if (mobileState == NetworkInfo.State.CONNECTED || mobileState == NetworkInfo.State.CONNECTING) {
                        return NET_MOBILE;
                    }
                }
            } else {
                // 获取当前活跃的网络 ( 连接的网络信息 )
                Network network = cManager.getActiveNetwork();
                if (network != null) {
                    NetworkCapabilities networkCapabilities = cManager.getNetworkCapabilities(network);
                    // 判断连接的是否 wifi
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return NET_WIFI;
                    } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return NET_MOBILE;
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getConnectType");
        }
        return NO_NETWORK;
    }

    // =

    // 网络广播监听
    private static final NetWorkReceiver netReceiver = new NetWorkReceiver();

    /**
     * 注册网络广播监听
     */
    public static void registerReceiver() {
        try {
            IntentFilter filter = new IntentFilter();
            // 网络连接状态改变时通知
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            filter.setPriority(Integer.MAX_VALUE);
            // 注册广播
            AppUtils.registerReceiver(netReceiver, filter);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "registerReceiver");
        }
    }

    /**
     * 取消注册网络广播监听
     */
    public static void unregisterReceiver() {
        try {
            AppUtils.unregisterReceiver(netReceiver);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "unregisterReceiver");
        }
    }

    // =

    // 监听通知事件
    private static NetwordStateListener networdStateListener;

    /**
     * 设置监听通知事件
     * @param listener {@link NetwordStateListener}
     */
    public static void setNetListener(final NetwordStateListener listener) {
        NetWorkReceiver.networdStateListener = listener;
    }

    /**
     * detail: 监听通知事件
     * @author Ttt
     */
    public interface NetwordStateListener {

        /**
         * 网络连接状态改变时通知
         * @param type 通知类型
         */
        void onNetworkState(int type);
    }
}