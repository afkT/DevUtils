package dev.utils.app.wifi;

import android.Manifest;
import android.content.Context;
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
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: wifi工具类
 * @author Ttt
 * <pre>
 *      需要的权限:
 *      <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
 *      <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 *      <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 * </pre>
 */
public final class WifiUtils {

    // 日志 TAG
    private static final String TAG = WifiUtils.class.getSimpleName();
    // 定义WifiManager对象
    private WifiManager mWifiManager;

    // ========
    // = 常量 =
    // ========

    // 没有密码
    public static final int NOPWD = 0;
    // wep加密方式
    public static final int WEP = 1;
    // wpa加密方式
    public static final int WPA = 2;

    /**
     * 构造器(只能进行初始化WifiManager操作，其他靠方法定义)
     */
    public WifiUtils() {
        this(DevUtils.getContext());
    }

    /**
     * 构造器(只能进行初始化WifiManager操作，其他靠方法定义)
     * @param context
     */
    public WifiUtils(final Context context) {
        // 初始化WifiManager对象
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 获取wifi管理对象
     * @return
     */
    public WifiManager getWifiManager() {
        return this.mWifiManager;
    }

    // ===========================
    // = Wifi 开关、连接状态获取 =
    // ===========================

    /**
     * 判断是否打开wifi
     */
    public boolean isOpenWifi() {
        return mWifiManager.isWifiEnabled();
    }

    /**
     * 打开WIFI
     */
    public void openWifi() {
        // 如果没有打开wifi，才进行打开
        if (!isOpenWifi()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    /**
     * 关闭WIFI
     */
    public void closeWifi() {
        // 如果已经打开了wifi，才进行关闭
        if (isOpenWifi()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    /**
     * 自动切换wifi开关状态
     */
    public void toggleWifiEnabled() {
        // 如果打开了，则关闭
        // 如果关闭了，则打开
        // =
        mWifiManager.setWifiEnabled(!isOpenWifi());
    }

    /**
     * 获取当前WIFI连接状态
     */
    public int getWifiState() {
        // WifiManager.WIFI_STATE_ENABLED: // 已打开
        // WifiManager.WIFI_STATE_ENABLING: // 正在打开
        // WifiManager.WIFI_STATE_DISABLED: // 已关闭
        // WifiManager.WIFI_STATE_DISABLING: // 正在关闭
        // WifiManager.WIFI_STATE_UNKNOWN: // 未知
        return mWifiManager.getWifiState();
    }

    // ============
    // = GET 操作 =
    // ============

    /**
     * 开始扫描wifi
     */
    public boolean startScan() {
        // 开始扫描
        return mWifiManager.startScan();
    }

    /**
     * 获取已配置的网络
     */
    public List<WifiConfiguration> getConfiguration() {
        return mWifiManager.getConfiguredNetworks();
    }

    /**
     * 获取网络列表
     */
    public List<ScanResult> getWifiList() {
        return mWifiManager.getScanResults();
    }

    /**
     * 获取WifiInfo对象
     */
    public WifiInfo getWifiInfo() {
        return mWifiManager.getConnectionInfo();
    }

    /**
     * 获取MAC地址
     * @param wifiInfo
     * @return
     */
    public String getMacAddress(final WifiInfo wifiInfo) {
        if (wifiInfo == null) return null;
        return wifiInfo.getMacAddress();
    }

    /**
     * 获取接入点的BSSID
     * @param wifiInfo
     * @return
     */
    public String getBSSID(final WifiInfo wifiInfo) {
        if (wifiInfo == null) return null;
        return wifiInfo.getBSSID();
    }

    /**
     * 获取IP地址
     * @param wifiInfo
     * @return
     */
    public int getIPAddress(final WifiInfo wifiInfo) {
        if (wifiInfo == null) return -1;
        return wifiInfo.getIpAddress();
    }

    /**
     * 获取连接的ID
     * @param wifiInfo
     * @return
     */
    public int getNetworkId(final WifiInfo wifiInfo) {
        if (wifiInfo == null) return -1;
        return wifiInfo.getNetworkId();
    }

    // =

    /**
     * 获取SSID
     * @param wifiInfo wifi信息
     * @return
     */
    public static String getSSID(final WifiInfo wifiInfo) {
        if (wifiInfo == null) return null;
        try {
            // 获取SSID,并进行处理
            return formatSSID(wifiInfo.getSSID(), false);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSSID");
        }
        return null;
    }

    /**
     * 通过 Context 获取当前连接的ssid
     */
    public static String getSSID() {
        try {
            // 初始化WifiManager对象
            WifiManager mWifiManager = (WifiManager) DevUtils.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            // 获取当前连接的wifi
            WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
            // 获取wifi - SSID
            return formatSSID(wifiInfo.getSSID(), false);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSSID");
        }
        return null;
    }

    // =

    /**
     * 判断是否存在\"ssid\"，存在则裁剪返回
     * @param ssid
     */
    public static String formatSSID(final String ssid) {
        if (ssid == null) return null;
        // 自动去掉SSID
        if (ssid != null && ssid.startsWith("\"") && ssid.endsWith("\"")) {
            try {
                // 裁剪连接的ssid,并返回
                return ssid.substring(1, ssid.length() - 1);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "formatSSID");
            }
        }
        return ssid;
    }

    /**
     * 格式化，处理SSID
     * @param ssid
     * @param isHandler {@code true} 添加引号, {@code false} 删除引号
     * @return
     */
    public static String formatSSID(final String ssid, final boolean isHandler) {
        if (ssid == null) return null;
        if (isHandler) {
            return "\"" + ssid + "\"";
        } else {
            return formatSSID(ssid);
        }
    }

    /**
     * 获取密码(经过处理)
     * @param pwd     需要处理的密码
     * @param isJudge 是否需要判断
     * @return
     */
    public String getPassword(final String pwd, final boolean isJudge) {
        if (pwd == null) return null;
        if (isJudge && isHexWepKey(pwd)) {
            return pwd;
        } else {
            return "\"" + pwd + "\"";
        }
    }

    /**
     * 判断是否 wep 加密
     * @param wepKey
     * @return
     */
    public static boolean isHexWepKey(final String wepKey) {
        if (wepKey == null) return false;
        // WEP-40, WEP-104, and some vendors using 256-bit WEP (WEP-232?)
        int len = wepKey.length();
        if (len != 10 && len != 26 && len != 58) {
            return false;
        }
        return isHex(wepKey);
    }

    /**
     * 判断是否 十六进制
     * @param key
     * @return
     */
    public static boolean isHex(final String key) {
        if (key == null) return false;
        for (int i = key.length() - 1; i >= 0; i--) {
            char c = key.charAt(i);
            if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a' && c <= 'f')) {
                return false;
            }
        }
        return true;
    }

    // ============
    // = 快捷操作 =
    // ============

    /**
     * 获取加密类型(int常量) - 判断String
     * @param type
     */
    public static int getWifiType(final String type) {
        if (type == null) return NOPWD;
        // WPA 是本机的用法
        if (type.contains("WPA")) {
            return WPA;
        } else if (type.contains("WEP")) {
            return WEP;
        }
        // 默认没有密码
        return NOPWD;
    }

    /**
     * 获取加密类型(int常量) - 判断int(String)
     * @param type
     */
    public static int getWifiTypeInt(final String type) {
        if (type == null) return NOPWD;
        // WPA 是本机的用法
        if (type.equals("2")) {
            return WPA;
        } else if (type.equals("1")) {
            return WEP;
        }
        // 默认没有密码
        return NOPWD;
    }

    /**
     * 获取加密类型(int常量)
     * @param type
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
     * 获取加密类型(String)
     * @param type
     * @return
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
     * 判断是否连接为null - unknown ssid
     * @param ssid
     * @return
     */
    public static boolean isConnNull(final String ssid) {
        if (ssid == null) {
            return true;
        } else if (ssid.indexOf("unknown") != -1) { // <unknown ssid>
            return true;
        }
        return false;
    }

    /**
     * 判断是否连接上Wifi(非连接中)
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * @return 返回ssid
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static String isConnectAphot() {
        try {
            // 连接管理
            ConnectivityManager cManager = (ConnectivityManager) DevUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            // 版本兼容处理
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                // 连接状态
                NetworkInfo.State nState = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                if ((nState == NetworkInfo.State.CONNECTED)) {
                    // 获取连接的ssid
                    return getSSID();
                }
            } else {
                // 获取当前活跃的网络（连接的网络信息）
                Network network = cManager.getActiveNetwork();
                if (network != null) {
                    NetworkCapabilities networkCapabilities = cManager.getNetworkCapabilities(network);
                    // 判断是否连接 wifi
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        // 获取连接的ssid
                        return getSSID();
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isConnectAphot");
        }
        return null;
    }


    // ================
    // = Wifi配置操作 =
    // ================

    // 默认没有密码
    public static final int SECURITY_NONE = 0;
    // WEP加密方式
    public static final int SECURITY_WEP = 1;
    // PSK加密方式
    public static final int SECURITY_PSK = 2;
    // EAP加密方式
    public static final int SECURITY_EAP = 3;

    /**
     * 获取Wifi配置,加密类型
     * @param wifiConfig
     * @return
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
     * 获知Wifi配置，是否属于密码加密类型
     * @param wifiConfig
     * @return
     */
    public static boolean isExsitsPwd(final WifiConfiguration wifiConfig) {
        if (wifiConfig == null) return false;
        int wifiSecurity = getSecurity(wifiConfig);
        // 判断是否加密
        return (wifiSecurity != SECURITY_NONE);
    }

    /**
     * 查看以前是否也配置过这个网络
     * @param ssid 需要判断的wifi SSID
     */
    public WifiConfiguration isExsits(final String ssid) {
        if (ssid == null) return null;
        // 获取wifi 连接过的配置信息
        List<WifiConfiguration> listWifiConfigs = getConfiguration();
        // 防止为null
        if (listWifiConfigs == null) {
            return null;
        }
        // 遍历判断是否存在
        for (int i = 0, len = listWifiConfigs.size(); i < len; i++) {
            WifiConfiguration wConfig = listWifiConfigs.get(i);
            if (wConfig != null) {
                if (wConfig.SSID.equals("\"" + ssid + "\"")) {
                    return wConfig;
                }
            }
        }
        return null;
    }

    /**
     * 查看以前是否也配置过这个网络
     * @param networkId 网络id
     */
    public WifiConfiguration isExsits(final int networkId) {
        // 获取wifi 连接过的配置信息
        List<WifiConfiguration> listWifiConfigs = getConfiguration();
        // 防止为null
        if (listWifiConfigs == null) {
            return null;
        }
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

    // ============
    // = 配置操作 =
    // ============

    /**
     * 删除指定的 Wifi(SSID) 配置信息
     * @param ssid
     * @return 删除结果
     */
    public static boolean delWifiConfig(final String ssid) {
        if (ssid == null) return false;
        try {
            // 初始化WifiManager对象
            WifiManager mWifiManager = (WifiManager) DevUtils.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            // 获取wifi 连接过的配置信息
            List<WifiConfiguration> listWifiConfigs = mWifiManager.getConfiguredNetworks();
            // 防止为null
            if (listWifiConfigs != null) {
                // 遍历判断是否存在
                for (int i = 0, len = listWifiConfigs.size(); i < len; i++) {
                    WifiConfiguration wConfig = listWifiConfigs.get(i);
                    if (wConfig != null) {
                        if (wConfig.SSID.equals("\"" + ssid + "\"")) {
                            // 删除操作
                            mWifiManager.removeNetwork(wConfig.networkId);
                        }
                    }
                }
                // 保存操作
                mWifiManager.saveConfiguration();
                return true;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "delWifiConfig");
        }
        return false;
    }

    /**
     * 快速连接Wifi(不使用静态ip方式)
     * @param ssid  wifi SSID
     * @param pwd   wifi密码
     * @param wType 加密类型
     */
    public WifiConfiguration quickConnWifi(final String ssid, final String pwd, final int wType) {
        return quickConnWifi(ssid, pwd, wType, false, null);
    }

    /**
     * 快速连接Wifi
     * @param ssid     wifi SSID
     * @param pwd      wifi密码
     * @param wType    加密类型
     * @param isStatic 是否使用静态ip连接
     * @param ip       静态IP地址
     * @return 连接成功的 WifiConfiguration
     */
    public WifiConfiguration quickConnWifi(final String ssid, final String pwd, final int wType, final boolean isStatic, final String ip) {
        // 步骤
        // 1.创建Wifi静态Ip连接配置
        // 2.创建正常Wifi连接配置
        // 3.查询准备连接的Wifi-SSID 是否存在配置文件，准备进行删除
        // 4.查询当前连接的Wifi-SSID 准备进行断开
        // 5.同步进行断开，删除操作，并且进行保存
        // 6.调用连接方法
        // 7.返回连接的配置信息
        // =
        try {
            // 正常的Wifi连接配置
            WifiConfiguration connWifiConfig = null;
            // 如果需要通过静态ip方式连接,则进行设置
            if (isStatic && !TextUtils.isEmpty(ip)) {
                // 创建Wifi静态IP连接配置
                WifiConfiguration staticWifiConfig = setStaticWifiConfig(createWifiConfig(ssid, pwd, wType, true), ip);
                // 如果静态ip方式,配置失败,则初始化正常连接的Wifi配置
                if (staticWifiConfig == null) {
                    // 创建正常的配置信息
                    connWifiConfig = createWifiConfig(ssid, pwd, wType, true);
                    // =
                    LogPrintUtils.dTag(TAG, "属于正常方式连接(DHCP)");
                } else {
                    // 设置静态信息
                    connWifiConfig = staticWifiConfig;
                    // =
                    LogPrintUtils.dTag(TAG, "属于静态IP方式连接");
                }
            } else {
                // 创建正常的配置信息
                connWifiConfig = createWifiConfig(ssid, pwd, wType, true);
                // =
                LogPrintUtils.dTag(TAG, "属于正常方式连接(DHCP)");
            }
            // 判断当前准备连接的wifi，是否存在配置文件
            WifiConfiguration preWifiConfig = this.isExsits(ssid);
            // =
            if (preWifiConfig != null) {
                // 存在则删除
                boolean isRemove = mWifiManager.removeNetwork(preWifiConfig.networkId);
                // 打印结果
                LogPrintUtils.dTag(TAG, "删除旧的配置信息 - " + preWifiConfig.SSID + ", isRemove: " + isRemove);
                // 保存配置
                mWifiManager.saveConfiguration();
            }
            // =
            // 连接网络
            int _nId = mWifiManager.addNetwork(connWifiConfig);
            if (_nId != -1) {
                try {
                    // 获取当前连接的Wifi对象
                    WifiInfo wifiInfo = getWifiInfo();
                    // 获取连接的id
                    int networdId = wifiInfo.getNetworkId();
                    // 禁用网络
                    boolean isDisable = mWifiManager.disableNetwork(networdId);
                    // 断开之前的连接
                    boolean isDisConnect = mWifiManager.disconnect();
                    // 打印断开连接结果
                    LogPrintUtils.dTag(TAG, "isDisConnect : " + isDisConnect + ", isDisable: " + isDisable);
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "quickConnWifi - 关闭连接出错: " + _nId);
                }
                // 开始连接
                boolean isResult = mWifiManager.enableNetwork(_nId, true);
                // =
                if (!isResult) {
                    isResult = mWifiManager.enableNetwork(_nId, true);
                }
                // 打印结果
                LogPrintUtils.dTag(TAG, "addNetwork(enableNetwork) - result: " + isResult);
            } else {
                // 尝试不带引号SSID连接
                connWifiConfig.SSID = formatSSID(connWifiConfig.SSID, false);
                // 连接网络
                _nId = mWifiManager.addNetwork(connWifiConfig);
                if (_nId != -1) {
                    try {
                        // 获取当前连接的Wifi对象
                        WifiInfo wifiInfo = getWifiInfo();
                        // 获取连接的id
                        int networdId = wifiInfo.getNetworkId();
                        // 禁用网络
                        boolean isDisable = mWifiManager.disableNetwork(networdId);
                        // 断开之前的连接
                        boolean isDisConnect = mWifiManager.disconnect();
                        // 打印断开连接结果
                        LogPrintUtils.dTag(TAG, "isDisConnect : " + isDisConnect + ", isDisable: " + isDisable);
                    } catch (Exception e) {
                        LogPrintUtils.eTag(TAG, e, "quickConnWifi - 关闭连接出错: " + _nId);
                    }
                    // 开始连接
                    boolean isResult = mWifiManager.enableNetwork(_nId, true);
                    // =
                    if (!isResult) {
                        isResult = mWifiManager.enableNetwork(_nId, true);
                    }
                    // 打印结果
                    LogPrintUtils.dTag(TAG, "addNetwork(enableNetwork) - result: " + isResult);
                }
            }
            // 保存id
            connWifiConfig.networkId = _nId;
            // 连接的networkId
            LogPrintUtils.dTag(TAG, "连接的SSID - networkId : " + _nId);
            // 返回连接的信息
            return connWifiConfig;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, "quickConnWifi", e);
        }
        return null;
    }

    /**
     * 创建Wifi配置信息(无其他操作，单独返回WifiConfig)
     * @param ssid
     * @param pwd
     * @param wType
     * @param isHandler 是否处理双引号
     */
    public static WifiConfiguration createWifiConfig(final String ssid, final String pwd, final int wType, final boolean isHandler) {
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
            if (isHandler) {
                wifiConfig.SSID = formatSSID(ssid, true);
            } else {
                wifiConfig.SSID = ssid;
            }
            switch (wType) {
                case WifiUtils.NOPWD: // 不存在密码
                    wifiConfig.hiddenSSID = true;
                    wifiConfig.allowedKeyManagement.set(KeyMgmt.NONE);
                    break;
                case WifiUtils.WEP: // WEP 加密方式
                    wifiConfig.hiddenSSID = true;
                    if (isHandler) {
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
                    if (isHandler) {
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

    // ============
    // = 连接操作 =
    // ============

    /**
     * 移除某个Wifi配置信息
     * @param wcg
     * @return
     */
    public boolean removeWifiConfig(final WifiConfiguration wcg) {
        // 如果等于null则直接返回
        if (wcg == null)
            return false;
        try {
            // 删除配置
            boolean isResult = mWifiManager.removeNetwork(wcg.networkId);
            // 保存操作
            mWifiManager.saveConfiguration();
            // 返回删除结果
            return isResult;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, "removeWifiConfig", e);
        }
        return false;
    }

    /**
     * 断开指定ID的网络
     * @param netId wifiid
     */
    public void disconnectWifi(final int netId) {
        try {
            mWifiManager.disableNetwork(netId);
            mWifiManager.disconnect();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, "disconnectWifi", e);
        }
    }

    // ============================
    // = 设置静态ip，域名，等信息 =
    // ============================

    /**
     * 设置静态Wifi信息
     * @param wifiConfig Wifi配置信息
     * @param ip         静态ip
     * @return
     */
    private WifiConfiguration setStaticWifiConfig(final WifiConfiguration wifiConfig, final String ip) {
        String gateway = null;
        String dns;
        if (ip != null) {
            try {
                InetAddress intetAddress = InetAddress.getByName(ip);
                int intIp = inetAddressToInt(intetAddress);
                dns = (intIp & 0xFF) + "." + ((intIp >> 8) & 0xFF) + "." + ((intIp >> 16) & 0xFF) + ".1";
                gateway = dns;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "setStaticWifiConfig");
                return null;
            }
        }
        // 暂时不需要设置dns，所以dns参数传入null
        return setStaticWifiConfig(wifiConfig, ip, gateway, null, 24);
    }

    /**
     * 设置静态Wifi信息
     * @param wifiConfig          Wifi配置信息
     * @param ip                  静态ip
     * @param gateway             网关
     * @param dns                 dns
     * @param networkPrefixLength 网络前缀长度
     * @return
     */
    private WifiConfiguration setStaticWifiConfig(final WifiConfiguration wifiConfig, final String ip, final String gateway, final String dns, final int networkPrefixLength) {
        try {
            if (ip == null || gateway == null) {
                return null;
            }
            // 设置Inet地址
            InetAddress intetAddress = InetAddress.getByName(ip);
            if (Build.VERSION.SDK_INT <= 20) { // 旧的版本，5.0之前
                // 设置IP分配方式，静态ip
                setEnumField(wifiConfig, "STATIC", "ipAssignment");
                // 设置不用代理
                setEnumField(wifiConfig, "NONE", "proxySettings");
                // 设置ip地址
                setIpAddress(intetAddress, networkPrefixLength, wifiConfig);
                // 设置网关
                setGateway(InetAddress.getByName(gateway), wifiConfig);
                if (dns != null) { // 判断是否需要设置域名
                    // 设置DNS
                    setDNS(InetAddress.getByName(dns), wifiConfig);
                }
            } else { // 5.0新版本改变到其他地方
                Object obj = getDeclaredField(wifiConfig, "mIpConfiguration");
                // 设置IP分配方式，静态ip
                setEnumField(obj, "STATIC", "ipAssignment");
                // 设置不用代理
                setEnumField(obj, "NONE", "proxySettings");
                // 设置ip地址、网关、DNS
                setStaticIpConfig(ip, gateway, dns, networkPrefixLength, obj);
            }
            return wifiConfig;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "setStaticWifiConfig");
        }
        return null;
    }

    private int inetAddressToInt(final InetAddress inetAddr) throws Exception {
        byte[] data = inetAddr.getAddress();
        if (data.length != 4) {
            throw new IllegalArgumentException("Not an IPv4 address");
        }
        return ((data[3] & 0xff) << 24) | ((data[2] & 0xff) << 16) | ((data[1] & 0xff) << 8) | (data[0] & 0xff);
    }

    /**
     * 设置DNS
     * @param dns
     * @param wifiConfig
     * @throws Exception
     */
    private void setDNS(final InetAddress dns, final WifiConfiguration wifiConfig) throws Exception {
        Object linkProperties = getField(wifiConfig, "linkProperties");
        if (linkProperties == null)
            throw new NullPointerException();

        List<InetAddress> mDnses = (ArrayList<InetAddress>) getDeclaredField(linkProperties, "mDnses");
        mDnses.clear(); // or add a new dns address , here I just want to replace DNS1
        mDnses.add(dns);
    }

    /**
     * 设置网关
     * @param gateway
     * @param wifiConfig
     * @throws Exception
     */
    private void setGateway(final InetAddress gateway, final WifiConfiguration wifiConfig) throws Exception {
        Object linkProperties = getField(wifiConfig, "linkProperties");
        if (linkProperties == null)
            throw new NullPointerException();

        Class routeInfoClass = Class.forName("android.net.RouteInfo");
        Constructor routeInfoConstructor = routeInfoClass.getConstructor(new Class[]{InetAddress.class});
        Object routeInfo = routeInfoConstructor.newInstance(gateway);
        ArrayList mRoutes = (ArrayList) getDeclaredField(linkProperties, "mRoutes");
        mRoutes.clear();
        mRoutes.add(routeInfo);
    }

    /**
     * 设置Ip地址
     * @param addr         ip地址
     * @param prefixLength 网络前缀长度
     * @param wifiConfig   Wifi配置信息
     * @throws Exception
     */
    private void setIpAddress(final InetAddress addr, final int prefixLength, final WifiConfiguration wifiConfig) throws Exception {
        Object linkProperties = getField(wifiConfig, "linkProperties");
        if (linkProperties == null)
            throw new NullPointerException();

        Class laClass = Class.forName("android.net.LinkAddress");
        Constructor laConstructor = laClass.getConstructor(new Class[]{InetAddress.class, int.class});
        Object linkAddress = laConstructor.newInstance(addr, prefixLength);
        ArrayList mLinkAddresses = (ArrayList) getDeclaredField(linkProperties, "mLinkAddresses");
        mLinkAddresses.clear();
        mLinkAddresses.add(linkAddress);
    }

    /**
     * 设置Ip地址、网关、DNS(5.0之后)
     * @param ip           静态ip
     * @param gateway      网关
     * @param dns          dns
     * @param prefixLength 网络前缀长度
     * @param object       Wifi配置信息
     * @throws Exception
     */
    private void setStaticIpConfig(final String ip, final String gateway, final String dns, final int prefixLength, final Object object) throws Exception {
        // 从WifiConfig -> mIpConfiguration 获取staticIpConfiguration
        // 获取 staticIpConfiguration 变量
        Object staticIpConfigClass = getField(object, "staticIpConfiguration");
        if (staticIpConfigClass == null) {
            // 创建静态ip配置类
            staticIpConfigClass = Class.forName("android.net.StaticIpConfiguration").newInstance();
        }
        // 初始化LinkAddress 并设置ip地址
        Class laClass = Class.forName("android.net.LinkAddress");
        Constructor laConstructor = laClass.getConstructor(new Class[]{InetAddress.class, int.class});
        Object linkAddress = laConstructor.newInstance(InetAddress.getByName(ip), prefixLength);
        // 设置地址ip地址 ipAddress
        setValueField(staticIpConfigClass, linkAddress, "ipAddress");
        // 设置网关 gateway
        setValueField(staticIpConfigClass, InetAddress.getByName(gateway), "gateway");
        if (dns != null) { // 判断是否需要设置域名
            // 设置DNS
            List<InetAddress> mDnses = (ArrayList<InetAddress>) getDeclaredField(staticIpConfigClass, "dnsServers");
            mDnses.clear(); // or add a new dns address , here I just want to replace DNS1
            mDnses.add(InetAddress.getByName(dns));
        }
        // 设置赋值 staticIpConfiguration 属性
        setValueField(object, staticIpConfigClass, "staticIpConfiguration");
    }

    /**
     * 通过反射获取public字段
     * @param object
     * @param name
     * @return
     * @throws Exception
     */
    private Object getField(final Object object, final String name) throws Exception {
        Field field = object.getClass().getField(name);
        return field.get(object);
    }

    /**
     * 通过反射获取全部字段
     * @param object
     * @param name
     * @return
     * @throws Exception
     */
    private Object getDeclaredField(final Object object, final String name) throws Exception {
        Field field = object.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(object);
    }

    /**
     * 通过反射枚举类，进行设置
     * @param object 设置对象
     * @param value  设置参数值
     * @param name   变量名
     * @throws Exception 抛出异常
     */
    private void setEnumField(final Object object, final String value, final String name) throws Exception {
        Field field = object.getClass().getField(name);
        field.set(object, Enum.valueOf((Class<Enum>) field.getType(), value));
    }

    /**
     * 通过反射，进行设置
     * @param object 设置对象
     * @param val    设置参数值
     * @param name   变量名
     * @throws Exception 抛出异常
     */
    private void setValueField(final Object object, final Object val, final String name) throws Exception {
        Field field = object.getClass().getField(name);
        field.set(object, val);
    }
}
