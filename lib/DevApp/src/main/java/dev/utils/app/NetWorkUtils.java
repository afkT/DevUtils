package dev.utils.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

import androidx.annotation.RequiresPermission;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dev.utils.LogPrintUtils;

/**
 * detail: 网络管理工具类
 * @author Ttt
 * <pre>
 *     @see <a href="https://blog.csdn.net/Clear_ws/article/details/78204150"/>
 *     所需权限
 *     <uses-permission android:name="android.permission.INTERNET"/>
 *     <uses-permission android:name="android.permission.MODIFY_PHONE_STATE"/>
 *     <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 *     <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
 * </pre>
 */
public final class NetWorkUtils {

    private NetWorkUtils() {
    }

    // 日志 TAG
    private static final String TAG = NetWorkUtils.class.getSimpleName();

    /**
     * 获取移动网络打开状态 ( 默认属于未打开 )
     * @return {@code true} yes, {@code false} no
     */
    public static boolean getMobileDataEnabled() {
        try {
            // 移动网络开关状态
            boolean state;
            // 属于 5.0 以下的使用
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                // 获取网络连接状态
                ConnectivityManager manager = AppUtils.getConnectivityManager();
                // 反射获取方法
                Method method = manager.getClass().getMethod("getMobileDataEnabled");
                // 调用方法, 获取状态
                state = (Boolean) method.invoke(manager);
            } else {
                TelephonyManager telephonyManager = AppUtils.getTelephonyManager();
                // 反射获取方法
                Method method = telephonyManager.getClass().getDeclaredMethod("getDataEnabled");
                // 调用方法, 获取状态
                state = (Boolean) method.invoke(telephonyManager);
            }
            return state;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMobileDataEnabled");
        }
        return false;
    }

    /**
     * 设置移动网络开关 ( 无判断是否已开启移动网络 )
     * <pre>
     *     实际无效果, 非系统应用无权限需手动开启
     * </pre>
     * @param isOpen 是否打开移动网络
     * @return {@code true} success, {@code false} fail
     */
    public static boolean setMobileDataEnabled(final boolean isOpen) {
        try {
            // 属于 5.0 以下的使用
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                // 获取网络连接状态
                ConnectivityManager manager = AppUtils.getConnectivityManager();
                // 通过反射设置移动网络
                Method method = manager.getClass().getDeclaredMethod(
                        "setMobileDataEnabled", Boolean.TYPE
                );
                // 设置移动网络
                method.invoke(manager, isOpen);
            } else { // 需要 Manifest.permission.MODIFY_PHONE_STATE 权限, 普通 APP 无法获取
                TelephonyManager telephonyManager = AppUtils.getTelephonyManager();
                // 通过反射设置移动网络
                Method method = telephonyManager.getClass().getDeclaredMethod(
                        "setDataEnabled", boolean.class
                );
                // 设置移动网络
                method.invoke(telephonyManager, isOpen);
            }
            return true;
        } catch (Exception e) { // 开启移动网络失败
            LogPrintUtils.eTag(TAG, e, "setMobileDataEnabled");
        }
        return false;
    }

    /**
     * 判断是否连接了网络
     * @return {@code true} yes, {@code false} no
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isConnect() {
        try {
            // 获取手机所有连接管理对象 ( 包括对 wi-fi,net 等连接的管理 )
            ConnectivityManager manager = AppUtils.getConnectivityManager();
            // 版本兼容处理
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                // 获取网络连接管理的对象
                NetworkInfo info = manager.getActiveNetworkInfo();
                // 判断是否为 null
                if (info != null) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == State.CONNECTED) {
                        return true;
                    }
                }
            } else {
                // 获取当前活跃的网络 ( 连接的网络信息 )
                Network network = manager.getActiveNetwork();
                // 判断是否为 null
                if (network != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isConnect");
        }
        return false;
    }

    /**
     * 获取连接的网络类型
     * @return -1 = 等于未知, 1 = Wifi, 2 = 移动网络
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static int getConnectType() {
        try {
            // 获取手机所有连接管理对象 ( 包括对 wi-fi,net 等连接的管理 )
            ConnectivityManager manager = AppUtils.getConnectivityManager();
            // 版本兼容处理
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                // 判断连接的是否 Wifi
                State wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                // 判断是否连接上
                if (wifiState == State.CONNECTED || wifiState == State.CONNECTING) {
                    return 1;
                } else {
                    // 判断连接的是否移动网络
                    State mobileState = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
                    // 判断移动网络是否连接上
                    if (mobileState == State.CONNECTED || mobileState == State.CONNECTING) {
                        return 2;
                    }
                }
            } else {
                // 获取当前活跃的网络 ( 连接的网络信息 )
                Network network = manager.getActiveNetwork();
                if (network != null) {
                    NetworkCapabilities networkCapabilities = manager.getNetworkCapabilities(network);
                    // 判断连接的是否 Wifi
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return 1;
                    } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        // 判断连接的是否移动网络
                        return 2;
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getConnectType");
        }
        return -1;
    }

    /**
     * 判断是否连接 Wifi ( 连接上、连接中 )
     * @return {@code true} yes, {@code false} no
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isConnWifi() {
        return (getConnectType() == 1);
    }

    /**
     * 判断是否连接移动网络 ( 连接上、连接中 )
     * @return {@code true} yes, {@code false} no
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isConnMobileData() {
        return (getConnectType() == 2);
    }

    // =

    /**
     * detail: 网络连接类型
     * @author Ttt
     */
    public enum NetworkType {
        NETWORK_WIFI,
        NETWORK_5G,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }

    /**
     * 判断网络是否可用
     * @return {@code true} 可用, {@code false} 不可用
     */
    @Deprecated
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isAvailable() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            NetworkInfo info = getActiveNetworkInfo();
            return info != null && info.isAvailable();
        } else {
            return isAvailableByPing();
        }
    }

    /**
     * 使用 ping ip 方式判断网络是否可用
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAvailableByPing() {
        return isAvailableByPing(null);
    }

    /**
     * 使用 ping ip 方式判断网络是否可用
     * @param ip IP 地址
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isAvailableByPing(String ip) {
        if (ip == null || ip.length() <= 0) {
            ip = "223.5.5.5"; // 默认阿里巴巴 DNS
        }
        // cmd ping ip
        ShellUtils.CommandResult result = ShellUtils.execCmd(
                String.format("ping -c 1 %s", ip), false
        );
        // 打印信息
        if (result.errorMsg != null) {
            LogPrintUtils.dTag(
                    TAG, "isAvailableByPing - errorMsg: %s",
                    result.errorMsg
            );
        }
        if (result.successMsg != null) {
            LogPrintUtils.dTag(
                    TAG, "isAvailableByPing - successMsg: %s",
                    result.successMsg
            );
        }
        // 判断结果, 返回数据不为 null
        return result.isSuccess3();
    }

    /**
     * 获取活动网络信息
     * @return {@link NetworkInfo}
     */
    @Deprecated
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static NetworkInfo getActiveNetworkInfo() {
        try {
            return AppUtils.getConnectivityManager().getActiveNetworkInfo();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getActiveNetworkInfo");
        }
        return null;
    }

    /**
     * 获取活动网络
     * @return {@link Network}
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static Network getActiveNetwork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                return AppUtils.getConnectivityManager().getActiveNetwork();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getActiveNetwork");
            }
        }
        return null;
    }

    // =

    /**
     * 判断是否 4G 网络
     * @return {@code true} yes, {@code false} no
     */
    @RequiresPermission(allOf = {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE
    })
    public static boolean is4G() {
        return getNetworkType() == NetworkType.NETWORK_4G;
    }

    /**
     * 判断 Wifi 是否打开
     * @return {@code true} yes, {@code false} no
     */
    public static boolean getWifiEnabled() {
        try {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifiManager = AppUtils.getWifiManager();
            return wifiManager.isWifiEnabled();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWifiEnabled");
        }
        return false;
    }

    /**
     * 判断 Wifi 数据是否可用
     * @return {@code true} yes, {@code false} no
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isWifiAvailable() {
        return getWifiEnabled() && isAvailable();
    }

    /**
     * 获取网络运营商名称 ( 中国移动、如中国联通、中国电信 )
     * @return 运营商名称
     */
    public static String getNetworkOperatorName() {
        try {
            TelephonyManager telephonyManager = AppUtils.getTelephonyManager();
            return telephonyManager != null ? telephonyManager.getNetworkOperatorName() : null;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getNetworkOperatorName");
        }
        return null;
    }

    // =

    /**
     * 获取当前网络类型
     * @return {@link NetworkType}
     */
    @RequiresPermission(allOf = {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE
    })
    public static NetworkType getNetworkType() {
        // 默认网络类型
        NetworkType netType = NetworkType.NETWORK_NO;
        // 版本兼容处理
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            // 获取网络信息
            NetworkInfo networkInfo = getActiveNetworkInfo();
            // 判断是否可用
            if (networkInfo != null && networkInfo.isAvailable()) { // 同 getNetworkClass 方法
                // 属于可用则修改为未知
                netType = NetworkType.NETWORK_UNKNOWN;
                // 获取类型
                switch (networkInfo.getType()) {
                    case ConnectivityManager.TYPE_WIFI: // 属于 Wifi
                        netType = NetworkType.NETWORK_WIFI;
                        break;
                    case ConnectivityManager.TYPE_MOBILE: // 属于手机网络
                        switch (networkInfo.getSubtype()) {
                            // = 2G 网络 =
                            case TelephonyManager.NETWORK_TYPE_GSM:
                            case TelephonyManager.NETWORK_TYPE_GPRS:
                            case TelephonyManager.NETWORK_TYPE_CDMA:
                            case TelephonyManager.NETWORK_TYPE_EDGE:
                            case TelephonyManager.NETWORK_TYPE_1xRTT:
                            case TelephonyManager.NETWORK_TYPE_IDEN:
                                netType = NetworkType.NETWORK_2G;
                                break;
                            // = 3G 网络 =
                            case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                            case TelephonyManager.NETWORK_TYPE_UMTS:
                            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                            case TelephonyManager.NETWORK_TYPE_HSDPA:
                            case TelephonyManager.NETWORK_TYPE_HSUPA:
                            case TelephonyManager.NETWORK_TYPE_HSPA:
                            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                            case TelephonyManager.NETWORK_TYPE_EHRPD:
                            case TelephonyManager.NETWORK_TYPE_HSPAP:
                                netType = NetworkType.NETWORK_3G;
                                break;
                            // = 4G 网络 =
                            case TelephonyManager.NETWORK_TYPE_LTE:
                            case TelephonyManager.NETWORK_TYPE_IWLAN:
                            case 19: // TelephonyManager.NETWORK_TYPE_LTE_CA
                                netType = NetworkType.NETWORK_4G;
                                break;
                            // = 5G 网络 =
                            case TelephonyManager.NETWORK_TYPE_NR:
                                netType = NetworkType.NETWORK_5G;
                                break;
                            // = 其他判断 =
                            default:
                                try {
                                    // 判断子类名字
                                    String subtypeName = networkInfo.getSubtypeName();
                                    // = 3G 网络 =
                                    if ("TD-SCDMA".equalsIgnoreCase(subtypeName)
                                            || "WCDMA".equalsIgnoreCase(subtypeName)
                                            || "CDMA2000".equalsIgnoreCase(subtypeName)) {
                                        netType = NetworkType.NETWORK_3G;
                                    }
                                } catch (Exception ignored) {
                                }
                                break;
                        }
                        break;
                }
            }
        } else {
            try {
                // 获取网络连接状态
                ConnectivityManager manager = AppUtils.getConnectivityManager();
                // 获取当前活跃的网络 ( 连接的网络信息 )
                Network network = manager.getActiveNetwork();
                // 防止为 null
                if (network != null) {
                    // 属于可用则修改为未知
                    netType = NetworkType.NETWORK_UNKNOWN;
                    // 获取网络连接信息
                    NetworkCapabilities networkCapabilities = manager.getNetworkCapabilities(network);
                    // 判断是否连接 Wifi
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        netType = NetworkType.NETWORK_WIFI;
                    } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        // 判断连接的是否移动网络
                        TelephonyManager telephonyManager = AppUtils.getTelephonyManager();
                        // 获取网络类型
                        int networkType = telephonyManager.getNetworkType();
                        // 获取移动网络类型
                        switch (getNetworkClass(networkType)) {
                            case 1: // 2G
                                netType = NetworkType.NETWORK_2G;
                                break;
                            case 2: // 3G
                                netType = NetworkType.NETWORK_3G;
                                break;
                            case 3: // 4G
                                netType = NetworkType.NETWORK_4G;
                                break;
                            case 4: // 5G
                                netType = NetworkType.NETWORK_5G;
                                break;
                        }
                    }
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getNetworkType");
            }
        }
        return netType;
    }

    /**
     * 获取移动网络连接类型
     * @param networkType {@link TelephonyManager#getNetworkType}
     * @return 0 = 未知, 1 = 2G, 2 = 3G, 3 = 4G, 4 = 5G
     */
    public static int getNetworkClass(final int networkType) {
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_GSM:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return 1;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
            case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                return 2;
            case TelephonyManager.NETWORK_TYPE_LTE:
            case TelephonyManager.NETWORK_TYPE_IWLAN:
            case 19: // TelephonyManager.NETWORK_TYPE_LTE_CA
                return 3;
            case TelephonyManager.NETWORK_TYPE_NR:
                return 4;
            default:
                return 0;
        }
    }

    /**
     * 获取广播 IP 地址
     * @return 广播 IP 地址
     */
    public static String getBroadcastIpAddress() {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                if (!ni.isUp() || ni.isLoopback()) continue;
                List<InterfaceAddress> ias = ni.getInterfaceAddresses();
                for (int i = 0, len = ias.size(); i < len; i++) {
                    InterfaceAddress ia        = ias.get(i);
                    InetAddress      broadcast = ia.getBroadcast();
                    if (broadcast != null) {
                        return broadcast.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBroadcastIpAddress");
        }
        return null;
    }

    /**
     * 获取域名 IP 地址
     * @param domain 域名 www.baidu.com 不需要加上 http
     * @return 域名 IP 地址
     */
    public static String getDomainAddress(final String domain) {
        try {
            ExecutorService exec = Executors.newCachedThreadPool();
            Future<String> fs = exec.submit(() -> {
                InetAddress inetAddress;
                try {
                    inetAddress = InetAddress.getByName(domain);
                    return inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    LogPrintUtils.eTag(TAG, e, "getDomainAddress");
                }
                return null;
            });
            return fs.get();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDomainAddress");
        }
        return null;
    }

    /**
     * 获取 IP 地址
     * @param useIPv4 是否用 IPv4
     * @return IP 地址
     */
    public static String getIPAddress(final boolean useIPv4) {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                // 防止小米手机返回 10.0.2.15
                if (!ni.isUp()) continue;
                for (Enumeration<InetAddress> addresses = ni.getInetAddresses(); addresses.hasMoreElements(); ) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String  hostAddress = inetAddress.getHostAddress();
                        boolean isIPv4      = hostAddress.indexOf(':') < 0;
                        if (useIPv4) {
                            if (isIPv4) return hostAddress;
                        } else {
                            if (!isIPv4) {
                                int index = hostAddress.indexOf('%');
                                return index < 0 ? hostAddress.toUpperCase()
                                        : hostAddress.substring(0, index).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            LogPrintUtils.eTag(TAG, e, "getIPAddress");
        }
        return null;
    }

    /**
     * 根据 Wifi 获取网络 IP 地址
     * @return 网络 IP 地址
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static String getIpAddressByWifi() {
        try {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifiManager = AppUtils.getWifiManager();
            return Formatter.formatIpAddress(wifiManager.getDhcpInfo().ipAddress);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getIpAddressByWifi");
        }
        return null;
    }

    /**
     * 根据 Wifi 获取网关 IP 地址
     * @return 网关 IP 地址
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static String getGatewayByWifi() {
        try {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifiManager = AppUtils.getWifiManager();
            return Formatter.formatIpAddress(wifiManager.getDhcpInfo().gateway);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getGatewayByWifi");
        }
        return null;
    }

    /**
     * 根据 Wifi 获取子网掩码 IP 地址
     * @return 子网掩码 IP 地址
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static String getNetMaskByWifi() {
        try {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifiManager = AppUtils.getWifiManager();
            return Formatter.formatIpAddress(wifiManager.getDhcpInfo().netmask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getNetMaskByWifi");
        }
        return null;
    }

    /**
     * 根据 Wifi 获取服务端 IP 地址
     * @return 服务端 IP 地址
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static String getServerAddressByWifi() {
        try {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifiManager = AppUtils.getWifiManager();
            return Formatter.formatIpAddress(wifiManager.getDhcpInfo().serverAddress);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getServerAddressByWifi");
        }
        return null;
    }
}