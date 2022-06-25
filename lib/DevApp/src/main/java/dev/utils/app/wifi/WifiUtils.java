package dev.utils.app.wifi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresPermission;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import dev.utils.LogPrintUtils;
import dev.utils.app.AppUtils;
import dev.utils.common.ConvertUtils;

/**
 * detail: Wifi 工具类
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
 *     <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
 *     <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 * </pre>
 */
public final class WifiUtils {

    // 日志 TAG
    private static final String TAG = WifiUtils.class.getSimpleName();

    // WifiManager 对象
    private final WifiManager mWifiManager;

    // =======
    // = 常量 =
    // =======

    // 没有密码
    public static final int NOPWD = 0;
    // wep 加密方式
    public static final int WEP   = 1;
    // wpa 加密方式
    public static final int WPA   = 2;

    /**
     * 构造函数
     */
    public WifiUtils() {
        // 初始化 WifiManager 对象
        mWifiManager = AppUtils.getWifiManager();
    }

    // =======================
    // = Wifi 开关、连接状态获取 =
    // =======================

    /**
     * 判断是否打开 Wifi
     * @return {@code true} yes, {@code false} no
     */
    public boolean isOpenWifi() {
        return mWifiManager.isWifiEnabled();
    }

    /**
     * 打开 Wifi
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean openWifi() {
        // 如果没有打开 Wifi, 才进行打开
        if (!isOpenWifi()) {
            try {
                return mWifiManager.setWifiEnabled(true);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "openWifi");
            }
        }
        return false;
    }

    /**
     * 关闭 Wifi
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean closeWifi() {
        // 如果已经打开了 Wifi, 才进行关闭
        if (isOpenWifi()) {
            try {
                return mWifiManager.setWifiEnabled(false);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "closeWifi");
            }
        }
        return false;
    }

    /**
     * 自动切换 Wifi 开关状态
     * <pre>
     *     如果打开了, 则关闭
     *     如果关闭了, 则打开
     * </pre>
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean toggleWifiEnabled() {
        try {
            return mWifiManager.setWifiEnabled(!isOpenWifi());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "toggleWifiEnabled");
        }
        return false;
    }

    /**
     * 获取当前 Wifi 连接状态
     * @return Wifi 连接状态
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public int getWifiState() {
        // WifiManager.WIFI_STATE_ENABLED: // 已打开
        // WifiManager.WIFI_STATE_ENABLING: // 正在打开
        // WifiManager.WIFI_STATE_DISABLED: // 已关闭
        // WifiManager.WIFI_STATE_DISABLING: // 正在关闭
        // WifiManager.WIFI_STATE_UNKNOWN: // 未知
        try {
            return mWifiManager.getWifiState();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWifiState");
        }
        return WifiManager.WIFI_STATE_UNKNOWN;
    }

    // ===========
    // = get 操作 =
    // ===========

    /**
     * 开始扫描 Wifi
     * @return {@code true} 操作成功, {@code false} 操作失败
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean startScan() {
        try {
            return mWifiManager.startScan();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "startScan");
        }
        return false;
    }

    /**
     * 获取已配置 ( 连接过 ) 的 Wifi 配置
     * @return {@link List<WifiConfiguration>} 已配置 ( 连接过 ) 的 Wifi 配置
     */
    @RequiresPermission(allOf = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE
    })
    public List<WifiConfiguration> getConfiguration() {
        try {
            return mWifiManager.getConfiguredNetworks();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getConfiguration");
        }
        return null;
    }

    /**
     * 获取附近的 Wifi 列表
     * @return {@link List<ScanResult>} 附近的 Wifi 列表
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public List<ScanResult> getWifiList() {
        try {
            return mWifiManager.getScanResults();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWifiList");
        }
        return null;
    }

    /**
     * 获取连接的 WifiInfo
     * @return {@link WifiInfo}
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public WifiInfo getWifiInfo() {
        try {
            return mWifiManager.getConnectionInfo();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWifiInfo");
        }
        return null;
    }

    /**
     * 获取 MAC 地址
     * @param wifiInfo {@link WifiInfo}
     * @return MAC 地址
     */
    @SuppressLint("HardwareIds")
    public static String getMacAddress(final WifiInfo wifiInfo) {
        if (wifiInfo == null) return null;
        return wifiInfo.getMacAddress();
    }

    /**
     * 获取连接的 BSSID
     * @param wifiInfo {@link WifiInfo}
     * @return BSSID
     */
    public static String getBSSID(final WifiInfo wifiInfo) {
        if (wifiInfo == null) return null;
        return wifiInfo.getBSSID();
    }

    /**
     * 获取 IP 地址
     * @param wifiInfo {@link WifiInfo}
     * @return IP 地址
     */
    public static int getIPAddress(final WifiInfo wifiInfo) {
        if (wifiInfo == null) return -1;
        return wifiInfo.getIpAddress();
    }

    /**
     * 获取连接的 Network Id
     * @param wifiInfo {@link WifiInfo}
     * @return Network Id
     */
    public static int getNetworkId(final WifiInfo wifiInfo) {
        if (wifiInfo == null) return -1;
        return wifiInfo.getNetworkId();
    }

    /**
     * 获取 Wifi SSID
     * @param wifiInfo {@link WifiInfo}
     * @return Wifi SSID
     */
    public static String getSSID(final WifiInfo wifiInfo) {
        if (wifiInfo == null) return null;
        try {
            // 获取 SSID, 并进行处理
            return formatSSID(wifiInfo.getSSID(), false);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSSID");
        }
        return null;
    }

    /**
     * 获取当前连接的 Wifi SSID
     * @return Wifi SSID
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static String getSSID() {
        try {
            // 获取当前连接的 Wifi
            WifiInfo wifiInfo = AppUtils.getWifiManager().getConnectionInfo();
            // 获取 Wifi SSID
            return formatSSID(wifiInfo.getSSID(), false);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSSID");
        }
        return null;
    }

    // =

    /**
     * 判断是否存在 \"ssid\", 存在则裁剪返回
     * @param ssid 待处理的 SSID
     * @return 处理后的 SSID
     */
    public static String formatSSID(final String ssid) {
        if (ssid == null) return null;
        // 自动去掉 ""
        if (ssid.startsWith("\"") && ssid.endsWith("\"")) {
            try {
                // 裁剪连接的 ssid, 并返回
                return ssid.substring(1, ssid.length() - 1);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "formatSSID");
            }
        }
        return ssid;
    }

    /**
     * 格式化处理 SSID
     * @param ssid         待处理的 SSID
     * @param appendQuotes {@code true} 添加引号, {@code false} 删除引号
     * @return 处理后的 SSID
     */
    public static String formatSSID(
            final String ssid,
            final boolean appendQuotes
    ) {
        if (ssid == null) return null;
        if (appendQuotes) {
            return "\"" + ssid + "\"";
        } else {
            return formatSSID(ssid);
        }
    }

    /**
     * 获取处理后的密码
     * @param pwd     待处理的密码
     * @param isJudge 是否需要判断
     * @return 处理后的密码
     */
    public static String getPassword(
            final String pwd,
            final boolean isJudge
    ) {
        if (pwd == null) return null;
        if (isJudge && isHexWepKey(pwd)) {
            return pwd;
        } else {
            return "\"" + pwd + "\"";
        }
    }

    /**
     * 判断是否 wep 加密
     * @param wepKey 加密类型
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHexWepKey(final String wepKey) {
        if (wepKey == null) return false;
        // WEP-40, WEP-104, and some vendors using 256-bit WEP (WEP-232?)
        int len = wepKey.length();
        if (len != 10 && len != 26 && len != 58) {
            return false;
        }
        return ConvertUtils.isHex(wepKey);
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 获取加密类型
     * @param typeStr 加密类型
     * @return 加密类型 {@link WifiUtils#NOPWD}、{@link WifiUtils#WPA}、{@link WifiUtils#WEP}
     */
    public static int getWifiType(final String typeStr) {
        if (typeStr == null) return NOPWD;
        if (typeStr.contains("WPA")) {
            return WPA;
        } else if (typeStr.contains("WEP")) {
            return WEP;
        }
        return NOPWD;
    }

    /**
     * 获取加密类型
     * @param typeInt 加密类型
     * @return 加密类型 {@link WifiUtils#NOPWD}、{@link WifiUtils#WPA}、{@link WifiUtils#WEP}
     */
    public static int getWifiTypeInt(final String typeInt) {
        if (typeInt == null) return NOPWD;
        if ("2".equals(typeInt)) {
            return WPA;
        } else if ("1".equals(typeInt)) {
            return WEP;
        }
        return NOPWD;
    }

    /**
     * 获取加密类型
     * @param type 加密类型
     * @return 加密类型
     */
    public static String getWifiType(final int type) {
        switch (type) {
            case WPA:
                return "2";
            case WEP:
                return "1";
            case NOPWD:
                return "0";
        }
        return "0";
    }

    /**
     * 获取加密类型
     * @param type 加密类型
     * @return 加密类型
     */
    public static String getWifiTypeStr(final int type) {
        switch (type) {
            case WPA:
                return "WPA";
            case WEP:
                return "WEP";
            default:
                return "";
        }
    }

    /**
     * 判断是否连接为 null ( unknown ssid )
     * @param ssid Wifi ssid
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isConnNull(final String ssid) {
        if (ssid == null) return true;
        return ssid.contains("unknown"); // <unknown ssid>
    }

    /**
     * 获取连接的 Wifi 热点 SSID
     * @return Wifi 热点 SSID
     */
    @RequiresPermission(allOf = {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE
    })
    public static String isConnectAPHot() {
        try {
            // 连接管理
            ConnectivityManager manager = AppUtils.getConnectivityManager();
            // 版本兼容处理
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                // 连接状态
                NetworkInfo.State state = manager.getNetworkInfo(
                        ConnectivityManager.TYPE_WIFI
                ).getState();
                if ((state == NetworkInfo.State.CONNECTED)) {
                    // 获取连接的 ssid
                    return getSSID();
                }
            } else {
                // 获取当前活跃的网络 ( 连接的网络信息 )
                Network network = manager.getActiveNetwork();
                if (network != null) {
                    NetworkCapabilities networkCapabilities = manager.getNetworkCapabilities(network);
                    // 判断是否连接 Wifi
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        // 获取连接的 ssid
                        return getSSID();
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isConnectAPHot");
        }
        return null;
    }

    // ===============
    // = Wifi 配置操作 =
    // ===============

    // 默认没有密码
    public static final int SECURITY_NONE = 0;
    // WEP 加密方式
    public static final int SECURITY_WEP  = 1;
    // PSK 加密方式
    public static final int SECURITY_PSK  = 2;
    // EAP 加密方式
    public static final int SECURITY_EAP  = 3;

    /**
     * 获取 Wifi 加密类型
     * @param wifiConfig Wifi 配置信息
     * @return Wifi 加密类型
     */
    public static int getSecurity(final WifiConfiguration wifiConfig) {
        if (wifiConfig == null) return SECURITY_NONE;
        if (wifiConfig.allowedKeyManagement.get(KeyMgmt.WPA_PSK)) {
            return SECURITY_PSK;
        }
        if (wifiConfig.allowedKeyManagement.get(KeyMgmt.WPA_EAP)
                || wifiConfig.allowedKeyManagement.get(KeyMgmt.IEEE8021X)) {
            return SECURITY_EAP;
        }
        return (wifiConfig.wepKeys[0] != null) ? SECURITY_WEP : SECURITY_NONE;
    }

    /**
     * 判断 Wifi 加密类型, 是否为加密类型
     * @param wifiConfig Wifi 配置信息
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isExistsPwd(final WifiConfiguration wifiConfig) {
        if (wifiConfig == null) return false;
        int wifiSecurity = getSecurity(wifiConfig);
        // 判断是否加密
        return (wifiSecurity != SECURITY_NONE);
    }

    /**
     * 获取指定的 ssid 网络配置 ( 需连接保存过, 才存在 )
     * @param ssid Wifi ssid
     * @return {@link WifiConfiguration}
     */
    @RequiresPermission(allOf = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE
    })
    public WifiConfiguration isExists(final String ssid) {
        if (ssid == null) return null;
        // 获取 Wifi 连接过的配置信息
        List<WifiConfiguration> listWifiConfigs = getConfiguration();
        // 防止为 null
        if (listWifiConfigs == null) return null;
        // 遍历判断是否存在
        for (int i = 0, len = listWifiConfigs.size(); i < len; i++) {
            WifiConfiguration wifiConfig = listWifiConfigs.get(i);
            if (wifiConfig != null) {
                if (("\"" + ssid + "\"").equals(wifiConfig.SSID)) {
                    return wifiConfig;
                }
            }
        }
        return null;
    }

    /**
     * 获取指定的 network id 网络配置 ( 需连接保存过, 才存在 )
     * @param networkId network id
     * @return {@link WifiConfiguration}
     */
    @RequiresPermission(allOf = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE
    })
    public WifiConfiguration isExists(final int networkId) {
        // 获取 Wifi 连接过的配置信息
        List<WifiConfiguration> listWifiConfigs = getConfiguration();
        // 防止为 null
        if (listWifiConfigs == null) return null;
        // 遍历判断是否存在
        for (int i = 0, len = listWifiConfigs.size(); i < len; i++) {
            WifiConfiguration wConfig = listWifiConfigs.get(i);
            if (wConfig != null) {
                if (wConfig.networkId == networkId) {
                    return wConfig;
                }
            }
        }
        return null;
    }

    // ==========
    // = 配置操作 =
    // ==========

    /**
     * 删除指定的 Wifi ( SSID ) 配置信息
     * @param ssid Wifi ssid
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(allOf = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE
    })
    public static boolean delWifiConfig(final String ssid) {
        if (ssid == null) return false;
        try {
            // 初始化 WifiManager 对象
            WifiManager wifiManager = AppUtils.getWifiManager();
            // 获取 Wifi 连接过的配置信息
            List<WifiConfiguration> listWifiConfigs = wifiManager.getConfiguredNetworks();
            // 防止为 null
            if (listWifiConfigs != null) {
                // 遍历判断是否存在
                for (int i = 0, len = listWifiConfigs.size(); i < len; i++) {
                    WifiConfiguration wConfig = listWifiConfigs.get(i);
                    if (wConfig != null) {
                        if (("\"" + ssid + "\"").equals(wConfig.SSID)) {
                            // 删除操作
                            wifiManager.removeNetwork(wConfig.networkId);
                        }
                    }
                }
                // 保存操作
                return wifiManager.saveConfiguration();
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "delWifiConfig");
        }
        return false;
    }

    // =

    /**
     * 快速连接 Wifi ( 不使用静态 IP 方式 )
     * @param ssid Wifi ssid
     * @param pwd  Wifi 密码
     * @param type Wifi 加密类型
     * @return {@link WifiConfiguration}
     */
    @RequiresPermission(allOf = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE
    })
    public WifiConfiguration quickConnWifi(
            final String ssid,
            final String pwd,
            final int type
    ) {
        return quickConnWifi(ssid, pwd, type, false, null);
    }

    /**
     * 快速连接 Wifi
     * @param ssid     Wifi ssid
     * @param pwd      Wifi 密码
     * @param type     Wifi 加密类型
     * @param isStatic 是否使用静态 IP 连接
     * @param ip       静态 IP 地址
     * @return {@link WifiConfiguration}
     */
    @RequiresPermission(allOf = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE
    })
    public WifiConfiguration quickConnWifi(
            final String ssid,
            final String pwd,
            final int type,
            final boolean isStatic,
            final String ip
    ) {
        // 步骤:
        // 1. 创建 Wifi 静态 IP 连接配置
        // 2. 创建正常 Wifi 连接配置
        // 3. 查询准备连接的 Wifi SSID 是否存在配置文件, 准备进行删除
        // 4. 查询当前连接的 Wifi SSID 准备进行断开
        // 5. 同步进行断开, 删除操作, 并且进行保存
        // 6. 调用连接方法
        // 7. 返回连接的配置信息
        // =
        try {
            // 正常的 Wifi 连接配置
            WifiConfiguration connWifiConfig;
            // 如果需要通过静态 IP 方式连接, 则进行设置
            if (isStatic && !TextUtils.isEmpty(ip)) {
                // 创建 Wifi 静态 IP 连接配置
                WifiConfiguration staticWifiConfig = setStaticWifiConfig(
                        createWifiConfig(ssid, pwd, type, true), ip
                );
                // 如果静态 IP 方式, 配置失败, 则初始化正常连接的 Wifi 配置
                if (staticWifiConfig == null) {
                    // 创建正常的配置信息
                    connWifiConfig = createWifiConfig(ssid, pwd, type, true);
                    // =
                    LogPrintUtils.dTag(TAG, "属于正常方式连接 (DHCP)");
                } else {
                    // 设置静态信息
                    connWifiConfig = staticWifiConfig;
                    // =
                    LogPrintUtils.dTag(TAG, "属于静态 IP 方式连接");
                }
            } else {
                // 创建正常的配置信息
                connWifiConfig = createWifiConfig(ssid, pwd, type, true);
                // =
                LogPrintUtils.dTag(TAG, "属于正常方式连接 (DHCP)");
            }
            // 判断当前准备连接的 Wifi, 是否存在配置文件
            WifiConfiguration preWifiConfig = this.isExists(ssid);
            // =
            if (preWifiConfig != null) {
                // 存在则删除
                boolean isRemove = mWifiManager.removeNetwork(preWifiConfig.networkId);
                // 打印结果
                LogPrintUtils.dTag(
                        TAG, "删除旧的配置信息 %s, isRemove: %s",
                        preWifiConfig.SSID, isRemove
                );
                // 保存配置
                mWifiManager.saveConfiguration();
            }
            // =
            // 连接网络
            int nId = mWifiManager.addNetwork(connWifiConfig);
            if (nId != -1) {
                try {
                    // 获取当前连接的 Wifi 对象
                    WifiInfo wifiInfo = getWifiInfo();
                    // 获取连接的 id
                    int networkId = wifiInfo.getNetworkId();
                    // 禁用网络
                    boolean isDisable = mWifiManager.disableNetwork(networkId);
                    // 断开之前的连接
                    boolean isDisConnect = mWifiManager.disconnect();
                    // 打印断开连接结果
                    LogPrintUtils.dTag(
                            TAG, "isDisConnect: %s, isDisable: %s",
                            isDisConnect, isDisable
                    );
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "quickConnWifi 关闭连接出错: %s", nId);
                }
                // 开始连接
                boolean result = mWifiManager.enableNetwork(nId, true);
                // =
                if (!result) {
                    result = mWifiManager.enableNetwork(nId, true);
                }
                // 打印结果
                LogPrintUtils.dTag(TAG, "addNetwork(enableNetwork) result: %s", result);
            } else {
                // 尝试不带引号 SSID 连接
                connWifiConfig.SSID = formatSSID(connWifiConfig.SSID, false);
                // 连接网络
                nId = mWifiManager.addNetwork(connWifiConfig);
                if (nId != -1) {
                    try {
                        // 获取当前连接的 Wifi 对象
                        WifiInfo wifiInfo = getWifiInfo();
                        // 获取连接的 id
                        int networkId = wifiInfo.getNetworkId();
                        // 禁用网络
                        boolean isDisable = mWifiManager.disableNetwork(networkId);
                        // 断开之前的连接
                        boolean isDisConnect = mWifiManager.disconnect();
                        // 打印断开连接结果
                        LogPrintUtils.dTag(
                                TAG, "isDisConnect: %s, isDisable: %s",
                                isDisConnect, isDisable
                        );
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "quickConnWifi 关闭连接出错: %s", nId);
                    }
                    // 开始连接
                    boolean result = mWifiManager.enableNetwork(nId, true);
                    // =
                    if (!result) {
                        result = mWifiManager.enableNetwork(nId, true);
                    }
                    // 打印结果
                    LogPrintUtils.dTag(TAG, "addNetwork(enableNetwork) result: %s", result);
                }
            }
            // 保存 id
            connWifiConfig.networkId = nId;
            // 连接的 networkId
            LogPrintUtils.dTag(TAG, "连接的 SSID networkId: %s", nId);
            // 返回连接的信息
            return connWifiConfig;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "quickConnWifi");
        }
        return null;
    }

    /**
     * 创建 Wifi 配置信息
     * @param ssid         Wifi ssid
     * @param pwd          Wifi 密码
     * @param type         Wifi 加密类型
     * @param appendQuotes 是否追加双引号
     * @return {@link WifiConfiguration}
     */
    public static WifiConfiguration createWifiConfig(
            final String ssid,
            final String pwd,
            final int type,
            final boolean appendQuotes
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
            if (appendQuotes) {
                wifiConfig.SSID = formatSSID(ssid, true);
            } else {
                wifiConfig.SSID = ssid;
            }
            switch (type) {
                case WifiUtils.NOPWD: // 不存在密码
                    wifiConfig.hiddenSSID = true;
                    wifiConfig.allowedKeyManagement.set(KeyMgmt.NONE);
                    break;
                case WifiUtils.WEP: // WEP 加密方式
                    wifiConfig.hiddenSSID = true;
                    if (appendQuotes) {
                        if (isHexWepKey(pwd)) {
                            wifiConfig.wepKeys[0] = pwd;
                        } else {
                            wifiConfig.wepKeys[0] = "\"" + pwd + "\"";
                        }
                    } else {
                        wifiConfig.wepKeys[0] = pwd;
                    }
                    wifiConfig.allowedKeyManagement.set(KeyMgmt.NONE);
                    wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                    // wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                    wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                    wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                    wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                    wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
                    wifiConfig.wepTxKeyIndex = 0;
                    break;
                case WifiUtils.WPA: // WPA 加密方式
                    if (appendQuotes) {
                        wifiConfig.preSharedKey = "\"" + pwd + "\"";
                    } else {
                        wifiConfig.preSharedKey = pwd;
                    }
                    wifiConfig.hiddenSSID = true;
                    wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                    wifiConfig.allowedKeyManagement.set(KeyMgmt.WPA_PSK);
//					wifiConfig.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                    wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                    wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                    wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                    wifiConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                    wifiConfig.status = WifiConfiguration.Status.ENABLED;
                    break;
            }
            return wifiConfig;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "createWifiConfig");
        }
        return null;
    }

    // ==========
    // = 连接操作 =
    // ==========

    /**
     * 移除 Wifi 配置信息
     * @param wifiConfig Wifi 配置信息
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean removeWifiConfig(final WifiConfiguration wifiConfig) {
        // 如果等于 null 则直接返回
        if (wifiConfig == null) return false;
        try {
            // 删除配置
            boolean result = mWifiManager.removeNetwork(wifiConfig.networkId);
            // 保存操作
            mWifiManager.saveConfiguration();
            // 返回删除结果
            return result;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "removeWifiConfig");
        }
        return false;
    }

    /**
     * 断开指定 networkId 的网络
     * @param networkId network id
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
    public boolean disconnectWifi(final int networkId) {
        try {
            mWifiManager.disableNetwork(networkId);
            return mWifiManager.disconnect();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "disconnectWifi");
        }
        return false;
    }

    // =======================
    // = 设置静态 IP、域名等信息 =
    // =======================

    /**
     * 设置静态 Wifi 配置信息
     * @param wifiConfig Wifi 配置信息
     * @param ip         静态 IP
     * @return {@link WifiConfiguration}
     */
    private WifiConfiguration setStaticWifiConfig(
            final WifiConfiguration wifiConfig,
            final String ip
    ) {
        String gateway = null;
        String dns;
        if (ip != null) {
            try {
                InetAddress intetAddress = InetAddress.getByName(ip);
                int         intIp        = inetAddressToInt(intetAddress);
                dns     = (intIp & 0xFF) + "." + ((intIp >> 8) & 0xFF) + "." + ((intIp >> 16) & 0xFF) + ".1";
                gateway = dns;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setStaticWifiConfig");
                return null;
            }
        }
        // 暂时不需要设置 DNS, 所以 DNS 参数传入 null
        return setStaticWifiConfig(wifiConfig, ip, gateway, null, 24);
    }

    /**
     * 设置静态 Wifi 配置信息
     * @param wifiConfig          Wifi 配置信息
     * @param ip                  静态 IP
     * @param gateway             网关
     * @param dns                 DNS
     * @param networkPrefixLength 网络前缀长度
     * @return {@link WifiConfiguration}
     */
    private WifiConfiguration setStaticWifiConfig(
            final WifiConfiguration wifiConfig,
            final String ip,
            final String gateway,
            final String dns,
            final int networkPrefixLength
    ) {
        try {
            if (ip == null || gateway == null) return null;
            // 设置 InetAddress
            InetAddress intetAddress = InetAddress.getByName(ip);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH) { // 旧的版本, 5.0 之前
                // 设置 IP 分配方式, 静态 IP
                setEnumField(wifiConfig, "STATIC", "ipAssignment");
                // 设置不用代理
                setEnumField(wifiConfig, "NONE", "proxySettings");
                // 设置 IP 地址
                setIpAddress(intetAddress, networkPrefixLength, wifiConfig);
                // 设置网关
                setGateway(InetAddress.getByName(gateway), wifiConfig);
                if (dns != null) { // 判断是否需要设置域名
                    // 设置 DNS
                    setDNS(InetAddress.getByName(dns), wifiConfig);
                }
            } else { // 5.0 新版本改变到其他地方
                Object object = getDeclaredField(wifiConfig, "mIpConfiguration");
                // 设置 IP 分配方式, 静态 IP
                setEnumField(object, "STATIC", "ipAssignment");
                // 设置不用代理
                setEnumField(object, "NONE", "proxySettings");
                // 设置 IP 地址、网关、DNS
                setStaticIpConfig(ip, gateway, dns, networkPrefixLength, object);
            }
            return wifiConfig;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setStaticWifiConfig");
        }
        return null;
    }

    /**
     * 转换 IP 地址
     * @param inetAddress {@link InetAddress}
     * @return IPv4 地址
     * @throws Exception 不属于 IPv4 地址
     */
    private int inetAddressToInt(final InetAddress inetAddress) {
        byte[] data = inetAddress.getAddress();
        if (data.length != 4) {
            throw new IllegalArgumentException("Not an IPv4 address");
        }
        return ((data[3] & 0xff) << 24) | ((data[2] & 0xff) << 16)
                | ((data[1] & 0xff) << 8) | (data[0] & 0xff);
    }

    /**
     * 设置 DNS
     * @param dns        DNS
     * @param wifiConfig Wifi 配置信息
     * @throws Exception 设置失败, 抛出异常
     */
    private void setDNS(
            final InetAddress dns,
            final WifiConfiguration wifiConfig
    )
            throws Exception {
        Object linkProperties = getField(wifiConfig, "linkProperties");
        if (linkProperties == null) {
            throw new NullPointerException();
        }

        List<InetAddress> mDnses = (ArrayList<InetAddress>) getDeclaredField(
                linkProperties, "mDnses"
        );
        mDnses.clear(); // or add a new dns address, here I just want to replace DNS1
        mDnses.add(dns);
    }

    /**
     * 设置网关
     * @param gateway    网关
     * @param wifiConfig Wifi 配置信息
     * @throws Exception 设置失败, 抛出异常
     */
    private void setGateway(
            final InetAddress gateway,
            final WifiConfiguration wifiConfig
    )
            throws Exception {
        Object linkProperties = getField(wifiConfig, "linkProperties");
        if (linkProperties == null) {
            throw new NullPointerException();
        }

        Class<?>    routeInfoClass       = Class.forName("android.net.RouteInfo");
        Constructor routeInfoConstructor = routeInfoClass.getConstructor(InetAddress.class);
        Object      routeInfo            = routeInfoConstructor.newInstance(gateway);
        ArrayList   mRoutes              = (ArrayList) getDeclaredField(linkProperties, "mRoutes");
        mRoutes.clear();
        mRoutes.add(routeInfo);
    }

    /**
     * 设置 IP 地址
     * @param address      IP 地址
     * @param prefixLength 网络前缀长度
     * @param wifiConfig   Wifi 配置信息
     * @throws Exception 设置失败, 抛出异常
     */
    private void setIpAddress(
            final InetAddress address,
            final int prefixLength,
            final WifiConfiguration wifiConfig
    )
            throws Exception {
        Object linkProperties = getField(wifiConfig, "linkProperties");
        if (linkProperties == null) {
            throw new NullPointerException();
        }

        Class<?>    laClass        = Class.forName("android.net.LinkAddress");
        Constructor laConstructor  = laClass.getConstructor(InetAddress.class, int.class);
        Object      linkAddress    = laConstructor.newInstance(address, prefixLength);
        ArrayList   mLinkAddresses = (ArrayList) getDeclaredField(linkProperties, "mLinkAddresses");
        mLinkAddresses.clear();
        mLinkAddresses.add(linkAddress);
    }

    /**
     * 设置 IP 地址、网关、DNS (5.0 之后 )
     * @param ip           静态 IP
     * @param gateway      网关
     * @param dns          DNS
     * @param prefixLength 网络前缀长度
     * @param object       Wifi 配置信息
     * @throws Exception 设置失败, 抛出异常
     */
    private void setStaticIpConfig(
            final String ip,
            final String gateway,
            final String dns,
            final int prefixLength,
            final Object object
    )
            throws Exception {
        // 从 WifiConfig 成员变量 mIpConfiguration 获取 staticIpConfiguration
        // 获取 staticIpConfiguration 变量
        Object staticIpConfigClass = getField(object, "staticIpConfiguration");
        if (staticIpConfigClass == null) {
            // 创建静态 IP 配置类
            staticIpConfigClass = Class.forName("android.net.StaticIpConfiguration").newInstance();
        }
        // 初始化 LinkAddress 并设置 IP 地址
        Class<?>    laClass       = Class.forName("android.net.LinkAddress");
        Constructor laConstructor = laClass.getConstructor(InetAddress.class, int.class);
        Object      linkAddress   = laConstructor.newInstance(InetAddress.getByName(ip), prefixLength);
        // 设置地址 IP 地址 ipAddress
        setValueField(staticIpConfigClass, linkAddress, "ipAddress");
        // 设置网关 gateway
        setValueField(staticIpConfigClass, InetAddress.getByName(gateway), "gateway");
        if (dns != null) { // 判断是否需要设置域名
            // 设置 DNS
            List<InetAddress> mDnses = (ArrayList<InetAddress>) getDeclaredField(
                    staticIpConfigClass, "dnsServers"
            );
            mDnses.clear(); // or add a new dns address, here I just want to replace DNS1
            mDnses.add(InetAddress.getByName(dns));
        }
        // 设置赋值 staticIpConfiguration 属性
        setValueField(object, staticIpConfigClass, "staticIpConfiguration");
    }

    /**
     * 通过反射获取
     * @param object Object
     * @param name   字段名
     * @return 对应的字段
     * @throws Exception 获取失败, 抛出异常
     */
    private Object getField(
            final Object object,
            final String name
    )
            throws Exception {
        Field field = object.getClass().getField(name);
        return field.get(object);
    }

    /**
     * 通过反射获取
     * @param object Object
     * @param name   字段名
     * @return 对应的字段
     * @throws Exception 获取失败, 抛出异常
     */
    private Object getDeclaredField(
            final Object object,
            final String name
    )
            throws Exception {
        Field field = object.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(object);
    }

    /**
     * 通过反射枚举类, 进行设置
     * @param object Object
     * @param value  设置参数值
     * @param name   字段名
     * @throws Exception 设置失败, 抛出异常
     */
    private void setEnumField(
            final Object object,
            final String value,
            final String name
    )
            throws Exception {
        Field field = object.getClass().getField(name);
        field.set(object, Enum.valueOf((Class<Enum>) field.getType(), value));
    }

    /**
     * 通过反射, 进行设置
     * @param object Object
     * @param val    设置参数值
     * @param name   字段名
     * @throws Exception 设置失败, 抛出异常
     */
    private void setValueField(
            final Object object,
            final Object val,
            final String name
    )
            throws Exception {
        Field field = object.getClass().getField(name);
        field.set(object, val);
    }
}