package dev.utils.app.wifi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Wifi 热点工具类
 * @author Ttt
 * <pre>
 *      Android 8.0 开始, 热点操作方法, 已经变更
 *      @see <a href="https://blog.csdn.net/bukker/article/details/78649504"/>
 *      Android 7.1 系统以上不支持自动开启热点,需要手动开启热点
 *      @see <a href="https://www.jianshu.com/p/9dbb02c3e21f"/>
 *      <p></p>
 *      需要的权限:
 *      <uses-permission android:name="android.permission.WRITE_SETTINGS" />
 *      <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
 *      <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 *      <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 *      <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
 *      <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
 * </pre>
 */
public class WifiHotUtils {

    // 日志 TAG
    private static final String TAG = WifiHotUtils.class.getSimpleName();
    // Context
    private Context mContext;
    // 定义WifiManager对象
    private WifiManager mWifiManager;
    // 热点 Wifi 配置
    private WifiConfiguration apWifiConfig;

    /**
     * 构造器(只能进行初始化WifiManager操作，其他靠方法定义)
     */
    public WifiHotUtils() {
        this(DevUtils.getContext());
    }

    /**
     * 构造器(只能进行初始化WifiManager操作，其他靠方法定义)
     * @param context
     */
    public WifiHotUtils(final Context context) {
        this.mContext = context;
        // 初始化WifiManager对象
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    }

    // =============
    // = Wifi 操作 =
    // =============

    /**
     * 创建Wifi配置信息(无其他操作，单独返回WifiConfig) => Wifi热点 (就支持 无密码/WPA2 PSK)
     * @param ssid
     * @param pwd  密码需要大于等于8位
     * @return
     */
    public static WifiConfiguration createWifiConfigToAp(final String ssid, final String pwd) {
        try {
            // 创建一个新的网络配置
            WifiConfiguration wifiConfig = new WifiConfiguration();
            wifiConfig.allowedAuthAlgorithms.clear();
            wifiConfig.allowedGroupCiphers.clear();
            wifiConfig.allowedKeyManagement.clear();
            wifiConfig.allowedPairwiseCiphers.clear();
            wifiConfig.allowedProtocols.clear();
            wifiConfig.priority = 0;
            /** 设置连接的SSID */
            wifiConfig.SSID = ssid;
            // 判断密码
            if (TextUtils.isEmpty(pwd)) {
                wifiConfig.hiddenSSID = true;
                wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            } else {
                wifiConfig.preSharedKey = pwd;
                wifiConfig.hiddenSSID = true;
                wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
//					wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                wifiConfig.status = WifiConfiguration.Status.ENABLED;
            }
            return wifiConfig;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createWifiConfigToAp");
        }
        return null;
    }

    /**
     * 开启Wifi热点
     * @param wifiConfig wifi配置
     */
    public void stratWifiAp(final WifiConfiguration wifiConfig) {
        this.apWifiConfig = wifiConfig;
        // 大于 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 关闭热点
            if (mReservation != null) {
                mReservation.close();
            }
            // 清空信息
            apWifiSSID = apWifiPwd = null;
            // Android 8.0 是基于应用开启的, 必须使用固定生成的配置进行发送连接, 无法进行控制(App 关闭后, 热点自动关闭)
            // 必须有定位权限
            mWifiManager.startLocalOnlyHotspot(new WifiManager.LocalOnlyHotspotCallback() {
                @Override
                public void onStarted(WifiManager.LocalOnlyHotspotReservation reservation) {
                    super.onStarted(reservation);
                    // 保存信息
                    mReservation = reservation;
                    // 获取配置信息
                    WifiConfiguration wifiConfiguration = reservation.getWifiConfiguration();
                    apWifiSSID = wifiConfiguration.SSID;
                    apWifiPwd = wifiConfiguration.preSharedKey;
                    // 打印信息
                    LogPrintUtils.dTag(TAG, "Android 8.0 wifi Ap ssid: " + apWifiSSID + ", pwd: " + apWifiPwd);
                    // 触发回调
                    if (wifiAPListener != null) {
                        wifiAPListener.onStarted(wifiConfiguration);
                    }
                }

                @Override
                public void onStopped() {
                    super.onStopped();
                    // 打印信息
                    LogPrintUtils.dTag(TAG, "Android 8.0 onStopped wifiAp");
                    // 触发回调
                    if (wifiAPListener != null) {
                        wifiAPListener.onStopped();
                    }
                }

                @Override
                public void onFailed(int reason) {
                    super.onFailed(reason);
                    // 打印信息
                    LogPrintUtils.eTag(TAG, "Android 8.0 onFailed wifiAp, reason : " + reason);
                    // 触发回调
                    if (wifiAPListener != null) {
                        wifiAPListener.onFailed(reason);
                    }
                }
            }, null);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) { // android 7.1 系统以上不支持自动开启热点,需要手动开启热点
            // 先设置wifi热点信息, 这样跳转前保存热点信息, 开启热点则是对应设置的信息
            boolean setResult = setWifiApConfiguration(wifiConfig);
            // 打印日志
            LogPrintUtils.dTag(TAG, "设置Wifi 热点信息是否成功: " + setResult);
            // https://www.cnblogs.com/bluestorm/p/3665555.html
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.TetherSettings"));
            mContext.startActivity(intent);
        } else {
            try {
                // 获取设置Wifi热点方法
                Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
                // 开启Wifi热点
                method.invoke(mWifiManager, wifiConfig, true);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "stratWifiAp");
            }
        }
    }

    /**
     * 关闭Wifi热点
     */
    public void closeWifiAp() {
        // 大于 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 关闭热点
            if (mReservation != null) {
                mReservation.close();
            }
            // 清空信息
            apWifiSSID = apWifiPwd = null;
            return;
        }
        try {
            // 获取设置Wifi热点方法
            Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            // 创建一个新的网络配置
            WifiConfiguration wifiConfig = new WifiConfiguration();
            wifiConfig.allowedAuthAlgorithms.clear();
            wifiConfig.allowedGroupCiphers.clear();
            wifiConfig.allowedKeyManagement.clear();
            wifiConfig.allowedPairwiseCiphers.clear();
            wifiConfig.allowedProtocols.clear();
            wifiConfig.priority = 0;
            // 设置Wifi SSID
            wifiConfig.SSID = "CloseWifiAp"; // formatSSID(ssid,true);
            // 设置Wifi密码
            wifiConfig.preSharedKey = "CloseWifiAp";
            // 设置Wifi属性
            wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            // 开启Wifi热点
            method.invoke(mWifiManager, wifiConfig, false);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "closeWifiAp");
        }
    }

    // ================
    // = 手机热点功能 =
    // ================

    /**
     * Wifi热点正在关闭 -- WifiManager.WIFI_AP_STATE_DISABLING
     */
    public static final int WIFI_AP_STATE_DISABLING = 10;
    /**
     * Wifi热点已关闭 -- WifiManager.WIFI_AP_STATE_DISABLED
     */
    public static final int WIFI_AP_STATE_DISABLED = 11;
    /**
     * Wifi热点正在打开 -- WifiManager.WIFI_AP_STATE_ENABLING
     */
    public static final int WIFI_AP_STATE_ENABLING = 12;
    /**
     * Wifi热点已打开 -- WifiManager.WIFI_AP_STATE_ENABLED
     */
    public static final int WIFI_AP_STATE_ENABLED = 13;
    /**
     * Wifi热点状态未知 -- WifiManager.WIFI_AP_STATE_FAILED
     */
    public static final int WIFI_AP_STATE_FAILED = 14;

    /**
     * 获取Wifi热点状态
     * @return
     */
    public int getWifiApState() {
        try {
            // 反射获取方法
            Method method = mWifiManager.getClass().getMethod("getWifiApState");
            // 调用方法,获取状态
            int wifiApState = (Integer) method.invoke(mWifiManager);
            // 打印状态
            LogPrintUtils.dTag(TAG, "WifiApState: " + wifiApState);
            return wifiApState;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWifiApState");
        }
        return WIFI_AP_STATE_FAILED;
    }

    /**
     * 获取Wifi热点配置信息
     * @return
     */
    public WifiConfiguration getWifiApConfiguration() {
        try {
            // 获取Wifi热点方法
            Method method = mWifiManager.getClass().getMethod("getWifiApConfiguration");
            // 获取配置
            WifiConfiguration wifiApConfig = (WifiConfiguration) method.invoke(mWifiManager);
            // 返回配置信息
            return wifiApConfig;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWifiApConfiguration");
        }
        return null;
    }

    /**
     * 设置Wifi热点配置信息
     * @param apWifiConfig
     * @return 是否成功
     */
    public boolean setWifiApConfiguration(final WifiConfiguration apWifiConfig) {
        try {
            // 获取设置Wifi热点方法
            Method method = mWifiManager.getClass().getMethod("setWifiApConfiguration", WifiConfiguration.class);
            // 开启Wifi热点
            return (boolean) method.invoke(mWifiManager, apWifiConfig);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setWifiApConfiguration");
        }
        return false;
    }

    // =

    /**
     * 判断是否打开Wifi热点
     * @return
     */
    public boolean isOpenWifiAp() {
        // 判断是否开启热点(默认未打开)
        boolean isOpen = false;
        // 获取当前Wifi热点状态
        int wifiApState = getWifiApState();
        switch (wifiApState) {
            case WIFI_AP_STATE_DISABLING: // Wifi热点正在关闭
                break;
            case WIFI_AP_STATE_DISABLED: // Wifi热点已关闭
                break;
            case WIFI_AP_STATE_ENABLING: // Wifi热点正在打开
                break;
            case WIFI_AP_STATE_ENABLED: // Wifi热点已打开
                isOpen = true;
                break;
            case WIFI_AP_STATE_FAILED: // Wifi热点状态未知
                break;
        }
        return isOpen;
    }

    /**
     * 关闭Wifi热点(判断当前状态)
     * @param isExecute 是否执行关闭
     * @return 之前是否打开热点
     */
    public boolean closeWifiApCheck(boolean isExecute) {
        // 判断是否开启热点(默认是)
        boolean isOpen = true;
        // 获取当前Wifi热点状态
        int wifiApState = getWifiApState();
        switch (wifiApState) {
            case WIFI_AP_STATE_DISABLING: // Wifi热点正在关闭
                isExecute = false;
                break;
            case WIFI_AP_STATE_DISABLED: // Wifi热点已关闭
                isOpen = false;
                break;
            case WIFI_AP_STATE_ENABLING: // Wifi热点正在打开
                break;
            case WIFI_AP_STATE_ENABLED: // Wifi热点已打开
                break;
            case WIFI_AP_STATE_FAILED: // Wifi热点状态未知
                break;
        }
        // 如果属于开启，则进行关闭
        if (isOpen && isExecute) {
            closeWifiAp();
        }
        return isOpen;
    }

    /**
     * 是否有连接热点
     * @return
     */
    public boolean isConnectHot() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4) {
                    String ipAdr = splitted[0]; // Ip地址
                    // 防止地址为null,并且需要以.拆分 存在4个长度  255.255.255.255
                    if (ipAdr != null && ipAdr.split("\\.").length >= 3) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isConnectHot");
        }
        return false;
    }

    /**
     * 获取热点主机ip地址
     * @return
     */
    public String getHotspotServiceIp() {
        try {
            // 获取网关信息
            DhcpInfo dhcpinfo = mWifiManager.getDhcpInfo();
            // 获取服务器地址
            return intToString(dhcpinfo.serverAddress);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getHotspotServiceIp");
        }
        return null;
    }

    /**
     * 获取连接上的子网关热点IP(一个)
     * @return
     */
    public String getHotspotAllotIp() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4) {
                    String ipAdr = splitted[0]; // Ip地址
                    // 防止地址为null,并且需要以.拆分 存在4个长度  255.255.255.255
                    if (ipAdr != null && ipAdr.split("\\.").length >= 3) {
                        return ipAdr;
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getHotspotAllotIp");
        }
        return null;
    }

    /**
     * 获取热点拼接后的ip网关掩码
     * @param defaultGateway 默认网关掩码
     * @param ipAdr          ip地址
     * @return
     */
    public String getHotspotSplitIpMask(final String defaultGateway, final String ipAdr) {
        // 网关掩码
        String hsMask = defaultGateway;
        // 获取网关掩码
        if (ipAdr != null) {
            try {
                int length = ipAdr.lastIndexOf(".");
                // 进行裁剪
                hsMask = ipAdr.substring(0, length) + ".255";
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getHotspotSplitIpMask");
            }
        }
        return hsMask;
    }

//    /**
//     * 获取连接的热点信息(暂时不用)
//     * @return
//     */
//    private void getConnectedHotMsg() {
//        try {
//            BufferedReader br = new BufferedReader(new FileReader("/proc/net/arp"));
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] splitted = line.split(" +");
//                if (splitted != null && splitted.length >= 4) {
//                    String ip = splitted[0]; // Ip地址
//                    String mac = splitted[3]; // Mac 地址
//                }
//            }
//        } catch (Exception e) {
//            LogPrintUtils.eTag(TAG, e, "getConnectedHotMsg");
//        }
//    }

    /**
     * 转换ip地址
     * @param data 需要转换的数据
     * @return
     */
    private String intToString(final int data) {
        StringBuffer buffer = new StringBuffer();
        int b = (data >> 0) & 0xff;
        buffer.append(b + ".");
        b = (data >> 8) & 0xff;
        buffer.append(b + ".");
        b = (data >> 16) & 0xff;
        buffer.append(b + ".");
        b = (data >> 24) & 0xff;
        buffer.append(b);
        return buffer.toString();
    }

    // ===================
    // = Android 8.0相关 =
    // ===================

    // Wifi ssid
    private String apWifiSSID;
    // wifi 密码
    private String apWifiPwd;
    // wifi 热点对象
    private WifiManager.LocalOnlyHotspotReservation mReservation;

    /**
     * 获取Wifi 热点名
     * @return
     */
    public String getApWifiSSID() {
        // 大于 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return apWifiSSID;
        } else {
            if (apWifiConfig != null) {
                return apWifiConfig.SSID;
            }
        }
        return null;
    }

    /**
     * 获取Wifi 热点密码
     * @return
     */
    public String getApWifiPwd() {
        // 大于 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return apWifiPwd;
        } else {
            if (apWifiConfig != null) {
                return apWifiConfig.preSharedKey;
            }
        }
        return null;
    }

    // =

    private onWifiAPListener wifiAPListener;

    /**
     * 设置 Wifi 热点监听
     * @param wifiAPListener
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setOnWifiAPListener(onWifiAPListener wifiAPListener) {
        this.wifiAPListener = wifiAPListener;
    }

    /**
     * detail: Android Wifi监听
     * @author Ttt
     */
    public interface onWifiAPListener {

        /**
         * 开启热点触发
         * @param wifiConfig
         */
        void onStarted(WifiConfiguration wifiConfig);

        /**
         * 关闭热点回调
         */
        void onStopped();

        /**
         * 失败回调
         * @param reason
         */
        void onFailed(int reason);
    }
}
