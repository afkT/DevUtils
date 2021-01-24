package dev.utils.app.wifi;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;

import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;
import dev.utils.common.CloseUtils;

/**
 * detail: Wifi 热点工具类
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.WRITE_SETTINGS" />
 *     <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
 *     <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 *     <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 *     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
 *     <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 * </pre>
 */
public final class WifiHotUtils {

    // 日志 TAG
    private static final String TAG = WifiHotUtils.class.getSimpleName();

    // WifiManager 对象
    private final WifiManager       mWifiManager;
    // Wifi 热点配置
    private       WifiConfiguration mAPWifiConfig;

    /**
     * 构造函数
     */
    public WifiHotUtils() {
        // 初始化 WifiManager 对象
        mWifiManager = AppUtils.getWifiManager();
    }

    // ============
    // = Wifi 操作 =
    // ============

    /**
     * 创建 Wifi 热点配置 ( 支持 无密码 /WPA2 PSK)
     * @param ssid Wifi ssid
     * @return {@link WifiConfiguration} 热点配置
     */
    public static WifiConfiguration createWifiConfigToAp(final String ssid) {
        return createWifiConfigToAp(ssid, null);
    }

    /**
     * 创建 Wifi 热点配置 ( 支持 无密码 /WPA2 PSK)
     * @param ssid Wifi ssid
     * @param pwd  密码 ( 需要大于等于 8 位 )
     * @return {@link WifiConfiguration}
     */
    public static WifiConfiguration createWifiConfigToAp(
            final String ssid,
            final String pwd
    ) {
        try {
            // 创建一个新的网络配置
            WifiConfiguration wifiConfig = new WifiConfiguration();
            wifiConfig.allowedAuthAlgorithms.clear();
            wifiConfig.allowedGroupCiphers.clear();
            wifiConfig.allowedKeyManagement.clear();
            wifiConfig.allowedPairwiseCiphers.clear();
            wifiConfig.allowedProtocols.clear();
            wifiConfig.priority = 0;
            // 设置连接的 SSID
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
     * 开启 Wifi 热点
     * <pre>
     *     android 8.0 及以上必须要有定位权限
     *     android 7.0 及以下需要 WRITE_SETTINGS 权限
     * </pre>
     * @param wifiConfig Wifi 配置
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(allOf = {
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION
    })
    public boolean startWifiAp(final WifiConfiguration wifiConfig) {
        this.mAPWifiConfig = wifiConfig;
        // 大于 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                // 关闭热点
                if (mReservation != null) {
                    mReservation.close();
                }
                // 清空信息
                mAPWifiSSID = mAPWifiPwd = null;
                // Android 8.0 是基于应用开启的, 必须使用固定生成的配置进行创建, 无法进行控制 (APP 关闭后, 热点自动关闭 )
                // 必须有定位权限
                mWifiManager.startLocalOnlyHotspot(new WifiManager.LocalOnlyHotspotCallback() {
                    @Override
                    public void onStarted(WifiManager.LocalOnlyHotspotReservation reservation) {
                        super.onStarted(reservation);
                        // 保存信息
                        mReservation = reservation;
                        // 获取配置信息
                        WifiConfiguration wifiConfiguration = reservation.getWifiConfiguration();
                        mAPWifiSSID = wifiConfiguration.SSID;
                        mAPWifiPwd = wifiConfiguration.preSharedKey;
                        // 打印信息
                        LogPrintUtils.dTag(TAG, "Android 8.0 onStarted wifiAp ssid: %s, pwd: %s", mAPWifiSSID, mAPWifiPwd);
                        // 触发回调
                        if (mWifiAPListener != null) {
                            mWifiAPListener.onStarted(wifiConfiguration);
                        }
                    }

                    @Override
                    public void onStopped() {
                        super.onStopped();
                        // 打印信息
                        LogPrintUtils.dTag(TAG, "Android 8.0 onStopped wifiAp");
                        // 触发回调
                        if (mWifiAPListener != null) {
                            mWifiAPListener.onStopped();
                        }
                    }

                    @Override
                    public void onFailed(int reason) {
                        super.onFailed(reason);
                        // 打印信息
                        LogPrintUtils.eTag(TAG, "Android 8.0 onFailed wifiAp, reason: %s", reason);
                        // 触发回调
                        if (mWifiAPListener != null) {
                            mWifiAPListener.onFailed(reason);
                        }
                    }
                }, null);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "startWifiAp");
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) { // android 7.1 系统以上不支持自动开启热点, 需要手动开启热点
            try {
                // 先设置 Wifi 热点信息, 这样跳转前保存热点信息, 开启热点则是对应设置的信息
                boolean setResult = setWifiApConfiguration(wifiConfig);
                // 打印日志
                LogPrintUtils.dTag(TAG, "设置 Wifi 热点信息是否成功: %s", setResult);
                // 跳转到便携式热点设置页面
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.TetherSettings"));
                AppUtils.startActivity(intent);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "startWifiAp");
            }
        } else {
            try {
                // 需要 android.permission.WRITE_SETTINGS 权限
                // 获取设置 Wifi 热点方法
                Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
                // 开启 Wifi 热点
                method.invoke(mWifiManager, wifiConfig, true);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "startWifiAp");
            }
        }
        return false;
    }

    /**
     * 关闭 Wifi 热点
     * @return {@code true} success, {@code false} fail
     */
    public boolean closeWifiAp() {
        // 大于 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 关闭热点
            if (mReservation != null) {
                mReservation.close();
            }
            // 清空信息
            mAPWifiSSID = mAPWifiPwd = null;
            return true;
        }
        try {
            // 获取设置 Wifi 热点方法
            Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            // 创建一个新的网络配置
            WifiConfiguration wifiConfig = new WifiConfiguration();
            wifiConfig.allowedAuthAlgorithms.clear();
            wifiConfig.allowedGroupCiphers.clear();
            wifiConfig.allowedKeyManagement.clear();
            wifiConfig.allowedPairwiseCiphers.clear();
            wifiConfig.allowedProtocols.clear();
            wifiConfig.priority = 0;
            // 设置 Wifi ssid
            wifiConfig.SSID = "CloseWifiAp"; // formatSSID(ssid,true);
            // 设置 Wifi 密码
            wifiConfig.preSharedKey = "CloseWifiAp";
            // 设置 Wifi 属性
            wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            // 开启 Wifi 热点
            method.invoke(mWifiManager, wifiConfig, false);
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "closeWifiAp");
        }
        return false;
    }

    // ===============
    // = 手机热点功能 =
    // ===============

    /**
     * Wifi 热点正在关闭 ( WifiManager.WIFI_AP_STATE_DISABLING )
     */
    public static final int WIFI_AP_STATE_DISABLING = 10;
    /**
     * Wifi 热点已关闭 ( WifiManager.WIFI_AP_STATE_DISABLED )
     */
    public static final int WIFI_AP_STATE_DISABLED  = 11;
    /**
     * Wifi 热点正在打开 ( WifiManager.WIFI_AP_STATE_ENABLING )
     */
    public static final int WIFI_AP_STATE_ENABLING  = 12;
    /**
     * Wifi 热点已打开 ( WifiManager.WIFI_AP_STATE_ENABLED )
     */
    public static final int WIFI_AP_STATE_ENABLED   = 13;
    /**
     * Wifi 热点状态未知 ( WifiManager.WIFI_AP_STATE_FAILED )
     */
    public static final int WIFI_AP_STATE_FAILED    = 14;

    /**
     * 获取 Wifi 热点状态
     * @return Wifi 热点状态
     */
    public int getWifiApState() {
        try {
            // 反射获取方法
            Method method = mWifiManager.getClass().getMethod("getWifiApState");
            // 调用方法, 获取状态
            int wifiApState = (Integer) method.invoke(mWifiManager);
            // 打印状态
            LogPrintUtils.dTag(TAG, "WifiApState: %s", wifiApState);
            return wifiApState;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWifiApState");
        }
        return WIFI_AP_STATE_FAILED;
    }

    /**
     * 获取 Wifi 热点配置信息
     * @return {@link WifiConfiguration} 热点配置
     */
    public WifiConfiguration getWifiApConfiguration() {
        try {
            // 获取 Wifi 热点方法
            Method method = mWifiManager.getClass().getMethod("getWifiApConfiguration");
            // 获取配置
            return (WifiConfiguration) method.invoke(mWifiManager);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWifiApConfiguration");
        }
        return null;
    }

    /**
     * 设置 Wifi 热点配置信息
     * @param apWifiConfig Wifi 热点配置
     * @return {@code true} success, {@code false} fail
     */
    public boolean setWifiApConfiguration(final WifiConfiguration apWifiConfig) {
        try {
            // 获取设置 Wifi 热点方法
            Method method = mWifiManager.getClass().getMethod("setWifiApConfiguration", WifiConfiguration.class);
            // 开启 Wifi 热点
            return (boolean) method.invoke(mWifiManager, apWifiConfig);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setWifiApConfiguration");
        }
        return false;
    }

    // =

    /**
     * 判断是否打开 Wifi 热点
     * @return {@code true} yes, {@code false} no
     */
    public boolean isOpenWifiAp() {
        // 判断是否开启热点 ( 默认未打开 )
        boolean isOpen = false;
        // 获取当前 Wifi 热点状态
        int wifiApState = getWifiApState();
        switch (wifiApState) {
            case WIFI_AP_STATE_DISABLING: // Wifi 热点正在关闭
                break;
            case WIFI_AP_STATE_DISABLED: // Wifi 热点已关闭
                break;
            case WIFI_AP_STATE_ENABLING: // Wifi 热点正在打开
                break;
            case WIFI_AP_STATE_ENABLED: // Wifi 热点已打开
                isOpen = true;
                break;
            case WIFI_AP_STATE_FAILED: // Wifi 热点状态未知
                break;
        }
        return isOpen;
    }

    /**
     * 关闭 Wifi 热点 ( 判断当前状态 )
     * @param isExecute 是否执行关闭
     * @return {@code true} success, {@code false} fail
     */
    public boolean closeWifiApCheck(final boolean isExecute) {
        // 判断是否开启热点 ( 默认是 )
        boolean isOpen = true;
        // 判断是否执行关闭
        boolean isExecuteClose = isExecute;
        // 获取当前 Wifi 热点状态
        int wifiApState = getWifiApState();
        switch (wifiApState) {
            case WIFI_AP_STATE_DISABLING: // Wifi 热点正在关闭
                isExecuteClose = false;
                break;
            case WIFI_AP_STATE_DISABLED: // Wifi 热点已关闭
                isOpen = false;
                break;
            case WIFI_AP_STATE_ENABLING: // Wifi 热点正在打开
                break;
            case WIFI_AP_STATE_ENABLED: // Wifi 热点已打开
                break;
            case WIFI_AP_STATE_FAILED: // Wifi 热点状态未知
                break;
        }
        // 如果属于开启, 则进行关闭
        if (isOpen && isExecuteClose) {
            closeWifiAp();
        }
        return isOpen;
    }

    /**
     * 是否有设备连接热点
     * @return {@code true} yes, {@code false} no
     */
    public boolean isConnectHot() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/proc/net/arp"));
            String         line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4) {
                    String ipAddress = splitted[0]; // IP 地址
                    // 防止地址为 null, 并且需要以. 拆分存在 4 个长度 255.255.255.255
                    if (ipAddress != null && ipAddress.split("\\.").length >= 3) {
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
     * 获取热点主机 IP 地址
     * @return 热点主机 IP 地址
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public String getHotspotServiceIp() {
        try {
            // 获取网关信息
            DhcpInfo dhcpInfo = mWifiManager.getDhcpInfo();
            // 获取服务器地址
            return intToString(dhcpInfo.serverAddress);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getHotspotServiceIp");
        }
        return null;
    }

    /**
     * 获取连接上的子网关热点 IP ( 一个 )
     * @return 连接上的子网关热点 IP
     */
    public String getHotspotAllotIp() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("/proc/net/arp"));
            String         line;
            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4) {
                    String ipAddress = splitted[0]; // IP 地址
                    // 防止地址为 null, 并且需要以. 拆分存在 4 个长度 255.255.255.255
                    if (ipAddress != null && ipAddress.split("\\.").length >= 3) {
                        return ipAddress;
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getHotspotAllotIp");
        }
        return null;
    }

    /**
     * 获取连接的热点信息
     * @return 连接的热点信息
     */
    public String getConnectHotspotMsg() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            StringBuilder builder = new StringBuilder();
            String        line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getHotspotAllotIp");
        } finally {
            CloseUtils.closeIOQuietly(br);
        }
        return null;
    }

//    /**
//     * 获取连接的热点信息
//     * @return 连接的热点信息
//     */
//    private String getConnectHotspotMsg() {
//        try {
//            BufferedReader br = new BufferedReader(new FileReader("/proc/net/arp"));
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] splitted = line.split(" +");
//                if (splitted != null && splitted.length >= 4) {
//                    String ip = splitted[0]; // IP 地址
//                    String mac = splitted[3]; // Mac 地址
//                }
//            }
//        } catch (Exception e) {
//            LogPrintUtils.eTag(TAG, e, "getConnectHotspotMsg");
//        }
//        return null;
//    }

    /**
     * 获取热点拼接后的 IP 网关掩码
     * @param defaultGateway 默认网关掩码
     * @param ipAddress      IP 地址
     * @return 网关掩码
     */
    public String getHotspotSplitIpMask(
            final String defaultGateway,
            final String ipAddress
    ) {
        // 网关掩码
        String hsMask = defaultGateway;
        // 获取网关掩码
        if (ipAddress != null) {
            try {
                int length = ipAddress.lastIndexOf('.');
                // 进行裁剪
                hsMask = ipAddress.substring(0, length) + ".255";
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getHotspotSplitIpMask");
            }
        }
        return hsMask;
    }

    /**
     * 转换 IP 地址
     * @param data 待转换的数据
     * @return 转换后的 IP 地址
     */
    private String intToString(final int data) {
        StringBuilder builder = new StringBuilder();
        builder.append((data >> 0) & 0xff).append(".");
        builder.append((data >> 8) & 0xff).append(".");
        builder.append((data >> 16) & 0xff).append(".");
        builder.append((data >> 24) & 0xff);
        return builder.toString();
    }

    // ===================
    // = Android 8.0 相关 =
    // ===================

    // Wifi ssid
    private String                                  mAPWifiSSID;
    // Wifi 密码
    private String                                  mAPWifiPwd;
    // Wifi 热点对象
    private WifiManager.LocalOnlyHotspotReservation mReservation;

    /**
     * 获取 Wifi 热点名
     * @return Wifi ssid
     */
    public String getApWifiSSID() {
        // 大于 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return mAPWifiSSID;
        } else {
            if (mAPWifiConfig != null) {
                return mAPWifiConfig.SSID;
            }
        }
        return null;
    }

    /**
     * 获取 Wifi 热点密码
     * @return Wifi 热点密码
     */
    public String getApWifiPwd() {
        // 大于 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return mAPWifiPwd;
        } else {
            if (mAPWifiConfig != null) {
                return mAPWifiConfig.preSharedKey;
            }
        }
        return null;
    }

    // =

    // Wifi 热点监听
    private OnWifiAPListener mWifiAPListener;

    /**
     * 设置 Wifi 热点监听事件
     * @param wifiAPListener {@link OnWifiAPListener}
     * @return {@link WifiHotUtils}
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public WifiHotUtils setOnWifiAPListener(final OnWifiAPListener wifiAPListener) {
        this.mWifiAPListener = wifiAPListener;
        return this;
    }

    /**
     * detail: Android Wifi 热点监听
     * @author Ttt
     */
    public interface OnWifiAPListener {

        /**
         * 开启热点回调
         * @param wifiConfig 热点配置
         */
        void onStarted(WifiConfiguration wifiConfig);

        /**
         * 关闭热点回调
         */
        void onStopped();

        /**
         * 失败回调
         * @param reason 失败原因 ( 错误码 )
         */
        void onFailed(int reason);
    }
}