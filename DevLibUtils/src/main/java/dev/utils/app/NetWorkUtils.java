package dev.utils.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.MODIFY_PHONE_STATE;

/**
 * detail: 网络管理工具类
 * @author Ttt
 * <pre>
 *      @see <a href="https://blog.csdn.net/Clear_ws/article/details/78204150"/>
 * </pre>
 */
public final class NetWorkUtils {

    private NetWorkUtils() {
    }

    // 日志 TAG
    private static final String TAG = NetWorkUtils.class.getSimpleName();

    /**
     * 获取移动网络打开状态(默认属于未打开)
     * @return
     */
    public static boolean getMobileDataEnabled() {
        try {
            // 移动网络开关状态
            boolean mState;
            // 属于5.0以下的使用
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                // 获取网络连接状态
                ConnectivityManager cManager = (ConnectivityManager) DevUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                // 反射获取方法
                Method method = cManager.getClass().getMethod("getMobileDataEnabled");
                // 调用方法,获取状态
                mState = (Boolean) method.invoke(cManager);
            } else {
                TelephonyManager tm = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
                // 反射获取方法
                Method method = tm.getClass().getDeclaredMethod("getDataEnabled");
                // 调用方法,获取状态
                mState = (Boolean) method.invoke(tm);
            }
            // 返回移动网络开关状态
            return mState;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMobileDataEnabled");
        }
        return false;
    }

    /**
     * 设置移动网络开关(无判断是否已开启移动网络) - 实际无效果, 非系统应用无权限
     * <uses-permission android:name="android.permission.MODIFY_PHONE_STATE"/>
     * @param isOpen 是否打开移动网络
     * @return 是否执行正常
     */
    @RequiresPermission(MODIFY_PHONE_STATE)
    public static boolean setMobileDataEnabled(final boolean isOpen) {
        try {
            // 属于5.0以下的使用
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                // 获取网络连接状态
                ConnectivityManager cManager = (ConnectivityManager) DevUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                // 通过反射设置移动网络
                Method mMethod = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
                // 设置移动网络
                mMethod.invoke(cManager, isOpen);
            } else { // 需要 android.Manifest.permission.MODIFY_PHONE_STATE 权限, 普通 App 无法获取
                TelephonyManager tm = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
                // 通过反射设置移动网络
                Method mMethod = tm.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
                // 设置移动网络
                mMethod.invoke(tm, isOpen);
            }
        } catch (Exception e) { // 开启移动网络失败
            LogPrintUtils.eTag(TAG, e, "setMobileDataEnabled");
            return false;
        }
        return true;
    }

    /**
     * 判断是否连接了网络
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isConnect() {
        // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
        try {
            ConnectivityManager cManager = (ConnectivityManager) DevUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            // 版本兼容处理
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                // 获取网络连接管理的对象
                NetworkInfo nInfo = cManager.getActiveNetworkInfo();
                // 判断是否为 null
                if (nInfo != null) {
                    // 判断当前网络是否已经连接
                    if (nInfo.getState() == State.CONNECTED) {
                        return true;
                    }
                }
            } else {
                // 获取当前活跃的网络（连接的网络信息）
                Network network = cManager.getActiveNetwork();
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
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * @return -1 = 等于未知 , 1 = Wifi, 2 = 移动网络
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public static int getConnectType() {
        // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
        try {
            // 获取网络连接状态
            ConnectivityManager cManager = (ConnectivityManager) DevUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            // 版本兼容处理
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                // 判断连接的是否wifi
                State wifiState = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                // 判断是否连接上
                if (wifiState == State.CONNECTED || wifiState == State.CONNECTING) {
                    return 1;
                } else {
                    // 判断连接的是否移动网络
                    State mobileState = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
                    // 判断移动网络是否连接上
                    if (mobileState == State.CONNECTED || mobileState == State.CONNECTING) {
                        return 2;
                    }
                }
            } else {
                // 获取当前活跃的网络（连接的网络信息）
                Network network = cManager.getActiveNetwork();
                if (network != null) {
                    NetworkCapabilities networkCapabilities = cManager.getNetworkCapabilities(network);
                    // 判断连接的是否 wifi
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
     * 判断是否连接Wifi(连接上、连接中)
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * @return
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isConnWifi() {
        return (getConnectType() == 1);
    }

    /**
     * 判断是否连接移动网络(连接上、连接中)
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * @return
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isConnMobileData() {
        return (getConnectType() == 2);
    }

    // =

    /**
     * 网络连接类型
     */
    public enum NetworkType {
        NETWORK_WIFI,
        NETWORK_4G,
        NETWORK_3G,
        NETWORK_2G,
        NETWORK_UNKNOWN,
        NETWORK_NO
    }

    /**
     * 判断网络是否可用
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * @return {@code true} 可用, {@code false} 不可用
     */
    @Deprecated
    @RequiresPermission(anyOf = {android.Manifest.permission.ACCESS_NETWORK_STATE, android.Manifest.permission.INTERNET})
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
     * <uses-permission android:name="android.permission.INTERNET" />
     * @return
     */
    @RequiresPermission(Manifest.permission.INTERNET)
    public static boolean isAvailableByPing() {
        return isAvailableByPing(null);
    }

    /**
     * 使用 ping ip 方式判断网络是否可用
     * @param ip ip地址
     * @return
     */
    @RequiresPermission(Manifest.permission.INTERNET)
    public static boolean isAvailableByPing(String ip) {
        if (ip == null || ip.length() <= 0) {
            ip = "223.5.5.5"; // 默认阿里巴巴 DNS
        }
        // cmd ping ip
        ShellUtils.CommandResult result = ShellUtils.execCmd(String.format("ping -c 1 %s", ip), false);
        // 打印信息
        if (result.errorMsg != null) {
            LogPrintUtils.dTag(TAG, "isAvailableByPing - errorMsg: " + result.errorMsg);
        }
        if (result.successMsg != null) {
            LogPrintUtils.dTag(TAG, "isAvailableByPing - successMsg: " + result.successMsg);
        }
        // 判断结果, 返回数据不为 null
        return result.isSuccess3();
    }

    /**
     * 获取活动网络信息
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * @return
     */
    @Deprecated
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    private static NetworkInfo getActiveNetworkInfo() {
        try {
            return ((ConnectivityManager) DevUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getActiveNetworkInfo");
        }
        return null;
    }

    /**
     * 获取活动网络信息
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * @return
     */
    @RequiresApi(Build.VERSION_CODES.M)
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    private static Network getActiveNetwork() {
        try {
            return ((ConnectivityManager) DevUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetwork();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getActiveNetwork");
        }
        return null;
    }

    // =

    /**
     * 判断是否4G网络
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * @return
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean is4G() {
        return getNetworkType() == NetworkType.NETWORK_4G;
    }

    /**
     * 判断wifi是否打开
     * @return
     */
    @SuppressLint("WifiManagerLeak")
    public static boolean getWifiEnabled() {
        try {
            WifiManager wifiManager = (WifiManager) DevUtils.getContext().getSystemService(Context.WIFI_SERVICE);
            return wifiManager.isWifiEnabled();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getWifiEnabled");
        }
        return false;
    }

    /**
     * 判断wifi数据是否可用
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * @return
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isWifiAvailable() {
        return getWifiEnabled() && isAvailable();
    }

    /**
     * 获取网络运营商名称 - 中国移动、如中国联通、中国电信
     * @return 运营商名称
     */
    public static String getNetworkOperatorName() {
        try {
            TelephonyManager tm = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null ? tm.getNetworkOperatorName() : null;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getNetworkOperatorName");
        }
        return null;
    }

    // =

    /**
     * 获取当前网络类型
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * @return
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
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
                    case ConnectivityManager.TYPE_WIFI: // 属于Wifi
                        netType = NetworkType.NETWORK_WIFI;
                        break;
                    case ConnectivityManager.TYPE_MOBILE: // 属于手机网络
                        switch (networkInfo.getSubtype()) {
                            // = 2G网络 =
                            case TelephonyManager.NETWORK_TYPE_GSM:
                            case TelephonyManager.NETWORK_TYPE_GPRS:
                            case TelephonyManager.NETWORK_TYPE_CDMA:
                            case TelephonyManager.NETWORK_TYPE_EDGE:
                            case TelephonyManager.NETWORK_TYPE_1xRTT:
                            case TelephonyManager.NETWORK_TYPE_IDEN:
                                netType = NetworkType.NETWORK_2G;
                                break;
                            // = 3G网络 =
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
                            // = 4G网络 =
                            case TelephonyManager.NETWORK_TYPE_LTE:
                            case TelephonyManager.NETWORK_TYPE_IWLAN:
                                //case TelephonyManager.NETWORK_TYPE_LTE_CA: // 19
                            case 19:
                                netType = NetworkType.NETWORK_4G;
                                break;
                            default: // 其他判断
                                try {
                                    // 判断子类名字
                                    String subtypeName = networkInfo.getSubtypeName();
                                    // = 3G 网络 =
                                    if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                            || subtypeName.equalsIgnoreCase("WCDMA")
                                            || subtypeName.equalsIgnoreCase("CDMA2000")) {
                                        netType = NetworkType.NETWORK_3G;
                                    } else {
                                        netType = NetworkType.NETWORK_UNKNOWN;
                                    }
                                } catch (Exception e) {
                                    // 保存未知
                                    netType = NetworkType.NETWORK_UNKNOWN;
                                }
                                break;
                        }
                        break;
                }
            }
        } else {
            try {
                // 获取网络连接状态
                ConnectivityManager cManager = (ConnectivityManager) DevUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                // 获取当前活跃的网络（连接的网络信息）
                Network network = cManager.getActiveNetwork();
                // 防止为null
                if (network != null) {
                    // 属于可用则修改为未知
                    netType = NetworkType.NETWORK_UNKNOWN;
                    // 获取网络连接信息
                    NetworkCapabilities networkCapabilities = cManager.getNetworkCapabilities(network);
                    // 判断连接类型
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) { // 判断是否连接 wifi
                        netType = NetworkType.NETWORK_WIFI;
                    } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) { // 判断连接的是否移动网络
                        TelephonyManager telephonyManager = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
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
     * @return 0 = 未知, 1 = 2G, 2 = 3G, 3 = 4G
     * <pre>
     *      {@link TelephonyManager#getNetworkClass} hide 方法
     * </pre>
     */
    public static int getNetworkClass(int networkType) {
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
                //case TelephonyManager.NETWORK_TYPE_LTE_CA: // 19
            case 19:
                return 3;
            default:
                return 0;
        }
    }

    /**
     * 获取广播 ip 地址
     * @return
     */
    public static String getBroadcastIpAddress() {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                if (!ni.isUp() || ni.isLoopback()) continue;
                List<InterfaceAddress> ias = ni.getInterfaceAddresses();
                for (int i = 0; i < ias.size(); i++) {
                    InterfaceAddress ia = ias.get(i);
                    InetAddress broadcast = ia.getBroadcast();
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
     * 获取域名ip地址
     * @param domain 域名  www.baidu.com  不需要加上http
     * @return ip地址
     */
    public static String getDomainAddress(final String domain) {
        try {
            ExecutorService exec = Executors.newCachedThreadPool();
            Future<String> fs = exec.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    InetAddress inetAddress;
                    try {
                        inetAddress = InetAddress.getByName(domain);
                        return inetAddress.getHostAddress();
                    } catch (UnknownHostException e) {
                        LogPrintUtils.eTag(TAG, e, "getDomainAddress");
                    }
                    return null;
                }
            });
            return fs.get();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDomainAddress");
        }
        return null;
    }

    /**
     * 获取IP地址
     * @param useIPv4 是否用IPv4
     * @return IP地址
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                // 防止小米手机返回10.0.2.15
                if (!ni.isUp()) continue;
                for (Enumeration<InetAddress> addresses = ni.getInetAddresses(); addresses.hasMoreElements(); ) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        boolean isIPv4 = hostAddress.indexOf(':') < 0;
                        if (useIPv4) {
                            if (isIPv4) return hostAddress;
                        } else {
                            if (!isIPv4) {
                                int index = hostAddress.indexOf('%');
                                return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index).toUpperCase();
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
     * 根据 WiFi 获取网络 IP 地址
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     * @return
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getIpAddressByWifi() {
        try {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifiManager = (WifiManager) DevUtils.getContext().getSystemService(Context.WIFI_SERVICE);
            return Formatter.formatIpAddress(wifiManager.getDhcpInfo().ipAddress);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getIpAddressByWifi");
        }
        return null;
    }

    /**
     * 根据 WiFi 获取网关 IP 地址
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     * @return
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getGatewayByWifi() {
        try {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifiManager = (WifiManager) DevUtils.getContext().getSystemService(Context.WIFI_SERVICE);
            return Formatter.formatIpAddress(wifiManager.getDhcpInfo().gateway);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getGatewayByWifi");
        }
        return null;
    }

    /**
     * 根据 WiFi 获取子网掩码 IP 地址
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     * @return
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getNetMaskByWifi() {
        try {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifiManager = (WifiManager) DevUtils.getContext().getSystemService(Context.WIFI_SERVICE);
            return Formatter.formatIpAddress(wifiManager.getDhcpInfo().netmask);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getNetMaskByWifi");
        }
        return null;
    }

    /**
     * 根据 WiFi 获取服务端 IP 地址
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     * @return
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static String getServerAddressByWifi() {
        try {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifiManager = (WifiManager) DevUtils.getContext().getSystemService(Context.WIFI_SERVICE);
            return Formatter.formatIpAddress(wifiManager.getDhcpInfo().serverAddress);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getServerAddressByWifi");
        }
        return null;
    }
}
