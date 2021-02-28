package dev.utils.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import dev.utils.DevFinal;
import dev.utils.LogPrintUtils;

/**
 * detail: 设备相关工具类
 * @author Ttt
 * <pre>
 *     android.os.Build.BOARD: 获取设备基板名称
 *     android.os.Build.BOOTLOADER: 获取设备引导程序版本号
 *     android.os.Build.BRAND: 获取设备品牌
 *     android.os.Build.CPU_ABI: 获取设备指令集名称 (CPU 的类型 )
 *     android.os.Build.CPU_ABI2: 获取第二个指令集名称
 *     android.os.Build.DEVICE: 获取设备驱动名称
 *     android.os.Build.DISPLAY: 获取设备显示的版本包 ( 在系统设置中显示为版本号 ) 和 ID 一样
 *     android.os.Build.FINGERPRINT: 设备的唯一标识, 由设备的多个信息拼接合成
 *     android.os.Build.HARDWARE: 设备硬件名称, 一般和基板名称一样 (BOARD)
 *     android.os.Build.HOST: 设备主机地址
 *     android.os.Build.ID: 设备版本号
 *     android.os.Build.MODEL : 获取手机的型号 设备名称
 *     android.os.Build.MANUFACTURER: 获取设备制造商
 *     android:os.Build.PRODUCT: 整个产品的名称
 *     android:os.Build.RADIO: 无线电固件版本号, 通常是不可用的 显示 unknown
 *     android.os.Build.TAGS: 设备标签, 如 release-keys 或测试的 test-keys
 *     android.os.Build.TIME: 时间
 *     android.os.Build.TYPE: 设备版本类型 主要为 "user" 或 "eng".
 *     android.os.Build.USER: 设备用户名 基本上都为 android-build
 *     android.os.Build.VERSION.RELEASE: 获取系统版本字符串, 如 4.1.2 或 2.2 或 2.3 等
 *     android.os.Build.VERSION.CODENAME: 设备当前的系统开发代号, 一般使用 REL 代替
 *     android.os.Build.VERSION.INCREMENTAL: 系统源代码控制值, 一个数字或者 git hash 值
 *     android.os.Build.VERSION.SDK: 系统的 API 级别 一般使用下面大的 SDK_INT 来查看
 *     android.os.Build.VERSION.SDK_INT: 系统的 API 级别 数字表示
 *     <p></p>
 *     所需权限
 *     <uses-permission android:name="android.permission.INTERNET" />
 *     <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
 *     <uses-permission android:name="android.permission.READ_PHONE_STATE" />
 *     <p></p>
 *     Android Settings 系统属性, 共分三种:
 *     {@link Settings.Global}: 所有的偏好设置对系统的所有用户公开, 第三方 APP 有读没有写的权限
 *     {@link Settings.System}: 包含各种各样的用户偏好系统设置
 *     {@link Settings.Secure}: 安全性的用户偏好系统设置, 第三方 APP 有读没有写的权限
 * </pre>
 */
public final class DeviceUtils {

    private DeviceUtils() {
    }

    // 日志 TAG
    private static final String TAG = DeviceUtils.class.getSimpleName();

    // 应用、设备信息 ( 可用于 FileRecordUtils 插入信息使用 )
    private static String APP_DEVICE_INFO = null;

    /**
     * 获取应用、设备信息
     * @return 应用、设备信息
     */
    public static String getAppDeviceInfo() {
        if (TextUtils.isEmpty(APP_DEVICE_INFO)) {
            refreshAppDeviceInfo();
        }
        return APP_DEVICE_INFO;
    }

    /**
     * 刷新应用、设备信息
     * @return 应用、设备信息
     */
    public static String refreshAppDeviceInfo() {
        try {
            StringBuilder builder = new StringBuilder();
            // 获取 APP 版本信息
            String[] versions    = ManifestUtils.getAppVersion();
            String   versionName = versions[0];
            String   versionCode = versions[1];
            String   packageName = AppUtils.getPackageName();
            String   deviceInfo  = DeviceUtils.handlerDeviceInfo(DeviceUtils.getDeviceInfo(), null);
            if (TextUtils.isEmpty(versionName) || TextUtils.isEmpty(versionCode) ||
                    TextUtils.isEmpty(packageName) || TextUtils.isEmpty(deviceInfo)) {
                return null;
            }
            // 保存 APP 版本信息
            builder.append("versionName: ").append(versionName);
            builder.append(DevFinal.NEW_LINE_STR);
            builder.append("versionCode: ").append(versionCode);
            builder.append(DevFinal.NEW_LINE_STR);
            builder.append("package: ").append(packageName);
            builder.append(DevFinal.NEW_LINE_STR);
            builder.append(deviceInfo);
            // 设置应用、设备信息
            APP_DEVICE_INFO = builder.toString();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "refreshAppDeviceInfo");
        }
        return APP_DEVICE_INFO;
    }

    // =

    /**
     * 获取设备唯一 UUID
     * @return 设备唯一 UUID
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getUUID() {
        return PhoneUtils.getUUID();
    }

    /**
     * 获取设备唯一 UUID ( 使用硬件信息拼凑出来的 )
     * @return 设备唯一 UUID
     * <pre>
     *     https://developer.android.com/training/articles/user-data-ids
     * </pre>
     */
    public static String getUUIDDevice() {
        String serial = "serial";
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; // 13 位
        // 使用硬件信息拼凑出来的 15 位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    // =

    /**
     * 获取设备信息
     * @return {@link Map<String, String>}
     */
    public static Map<String, String> getDeviceInfo() {
        return getDeviceInfo(new HashMap<>());
    }

    /**
     * 获取设备信息
     * @param deviceInfoMap 设备信息 Map
     * @return {@link Map<String, String>}
     */
    public static Map<String, String> getDeviceInfo(final Map<String, String> deviceInfoMap) {
        // 获取设备信息类的所有申明的字段, 即包括 public、private 和 protected, 但是不包括父类的申明字段
        Field[] fields = Build.class.getDeclaredFields();
        // 遍历字段
        for (Field field : fields) {
            try {
                // 取消 Java 的权限控制检查
                field.setAccessible(true);
                // 转换当前设备支持的 ABI ( CPU 指令集 )
                if (field.getName().toLowerCase().startsWith("SUPPORTED".toLowerCase())) {
                    try {
                        Object object = field.get(null);
                        // 判断是否数组
                        if (object instanceof String[]) {
                            if (object != null) {
                                // 获取类型对应字段的数据, 并保存支持的指令集 [arm64-v8a, armeabi-v7a, armeabi]
                                deviceInfoMap.put(field.getName(), Arrays.toString((String[]) object));
                            }
                            continue;
                        }
                    } catch (Exception e) {
                    }
                }
                // 获取类型对应字段的数据, 并保存
                deviceInfoMap.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getDeviceInfo");
            }
        }
        return deviceInfoMap;
    }

    /**
     * 处理设备信息
     * @param deviceInfoMap 设备信息 Map
     * @param errorInfo     错误提示信息, 如获取设备信息失败
     * @return 拼接后的设备信息字符串
     */
    public static String handlerDeviceInfo(
            final Map<String, String> deviceInfoMap,
            final String errorInfo
    ) {
        try {
            // 初始化 Builder, 拼接字符串
            StringBuilder builder = new StringBuilder();
            // 获取设备信息
            Iterator<Map.Entry<String, String>> mapIter = deviceInfoMap.entrySet().iterator();
            // 遍历设备信息
            while (mapIter.hasNext()) {
                // 获取对应的 key - value
                Map.Entry<String, String> rnEntry = mapIter.next();
                String                    rnKey   = rnEntry.getKey(); // key
                String                    rnValue = rnEntry.getValue(); // value
                // 保存设备信息
                builder.append(rnKey);
                builder.append(" = ");
                builder.append(rnValue);
                builder.append(DevFinal.NEW_LINE_STR);
            }
            return builder.toString();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "handlerDeviceInfo");
        }
        return errorInfo;
    }

    // =

    /**
     * 获取设备基板名称
     * @return 设备基板名称
     */
    public static String getBoard() {
        return Build.BOARD;
    }

    /**
     * 获取设备引导程序版本号
     * @return 设备引导程序版本号
     */
    public static String getBootloader() {
        return Build.BOOTLOADER;
    }

    /**
     * 获取设备品牌
     * @return 设备品牌
     */
    public static String getBrand() {
        return Build.BRAND;
    }

    /**
     * 获取支持的第一个指令集
     * @return 支持的第一个指令集
     */
    public static String getCPU_ABI() {
        return Build.CPU_ABI;
    }

    /**
     * 获取支持的第二个指令集
     * @return 支持的第二个指令集
     */
    public static String getCPU_ABI2() {
        return Build.CPU_ABI2;
    }

    /**
     * 获取支持的指令集 如: [arm64-v8a, armeabi-v7a, armeabi]
     * @return 支持的指令集
     */
    public static String[] getABIs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Build.SUPPORTED_ABIS;
        } else {
            if (!TextUtils.isEmpty(Build.CPU_ABI2)) {
                return new String[]{Build.CPU_ABI, Build.CPU_ABI2};
            }
            return new String[]{Build.CPU_ABI};
        }
    }

    /**
     * 获取支持的 32 位指令集
     * @return 支持的 32 位指令集
     */
    public static String[] getSUPPORTED_32_BIT_ABIS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Build.SUPPORTED_32_BIT_ABIS;
        }
        return null;
    }

    /**
     * 获取支持的 64 位指令集
     * @return 支持的 64 位指令集
     */
    public static String[] getSUPPORTED_64_BIT_ABIS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Build.SUPPORTED_64_BIT_ABIS;
        }
        return null;
    }

    /**
     * 获取设备驱动名称
     * @return 设备驱动名称
     */
    public static String getDevice() {
        return Build.DEVICE;
    }

    /**
     * 获取设备显示的版本包 ( 在系统设置中显示为版本号 ) 和 ID 一样
     * @return 设备显示的版本包
     */
    public static String getDisplay() {
        return Build.DISPLAY;
    }

    /**
     * 获取设备的唯一标识, 由设备的多个信息拼接合成
     * @return 设备的唯一标识, 由设备的多个信息拼接合成
     */
    public static String getFingerprint() {
        return Build.FINGERPRINT;
    }

    /**
     * 获取设备硬件名称, 一般和基板名称一样 (BOARD)
     * @return 设备硬件名称, 一般和基板名称一样 (BOARD)
     */
    public static String getHardware() {
        return Build.HARDWARE;
    }

    /**
     * 获取设备主机地址
     * @return 设备主机地址
     */
    public static String getHost() {
        return Build.HOST;
    }

    /**
     * 获取设备版本号
     * @return 设备版本号
     */
    public static String getID() {
        return Build.ID;
    }

    /**
     * 获取设备型号 如 RedmiNote4X
     * @return 设备型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    /**
     * 获取设备厂商 如 Xiaomi
     * @return 设备厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取整个产品的名称
     * @return 整个产品的名称
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取无线电固件版本号, 通常是不可用的 显示 unknown
     * @return 无线电固件版本号
     */
    public static String getRadio() {
        return Build.RADIO;
    }

    /**
     * 获取设备标签, 如 release-keys 或测试的 test-keys
     * @return 设备标签
     */
    public static String getTags() {
        return Build.TAGS;
    }

    /**
     * 获取设备时间
     * @return 设备时间
     */
    public static long getTime() {
        return Build.TIME;
    }

    /**
     * 获取设备版本类型 主要为 "user" 或 "eng".
     * @return 设备版本类型
     */
    public static String getType() {
        return Build.TYPE;
    }

    /**
     * 获取设备用户名 基本上都为 android-build
     * @return 设备用户名
     */
    public static String getUser() {
        return Build.USER;
    }

    // =

    /**
     * 获取 SDK 版本号
     * @return SDK 版本号
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取系统版本号, 如 4.1.2 或 2.2 或 2.3 等
     * @return 系统版本号
     */
    public static String getRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备当前的系统开发代号, 一般使用 REL 代替
     * @return 设备当前的系统开发代号
     */
    public static String getCodename() {
        return Build.VERSION.CODENAME;
    }

    /**
     * 获取系统源代码控制值, 一个数字或者 git hash 值
     * @return 系统源代码控制值
     */
    public static String getIncremental() {
        return Build.VERSION.INCREMENTAL;
    }

    /**
     * 获取 Android id
     * <pre>
     *     在设备首次启动时, 系统会随机生成一个 64 位的数字, 并把这个数字以十六进制字符串的形式保存下来,
     *     这个十六进制的字符串就是 ANDROID_ID, 当设备被 wipe 后该值会被重置
     * </pre>
     * @return Android id
     */
    public static String getAndroidId() {
        String androidId = null;
        try {
            androidId = Settings.Secure.getString(ResourceUtils.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAndroidId");
        }
        return androidId;
    }

    /**
     * 获取基带版本 BASEBAND-VER
     * @return 基带版本 BASEBAND-VER
     */
    public static String getBaseband_Ver() {
        String basebandVersion = "";
        try {
            Class  clazz   = Class.forName("android.os.SystemProperties");
            Object invoker = clazz.newInstance();
            Method method  = clazz.getMethod("get", String.class, String.class);
            Object result  = method.invoke(invoker, "gsm.version.baseband", "no message");
            basebandVersion = (String) result;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBaseband_Ver");
        }
        return basebandVersion;
    }

    /**
     * 获取内核版本 CORE-VER
     * @return 内核版本 CORE-VER
     */
    public static String getLinuxCore_Ver() {
        String kernelVersion = "";
        try {
            Process           process = Runtime.getRuntime().exec("cat /proc/version");
            InputStream       is      = process.getInputStream();
            InputStreamReader isr     = new InputStreamReader(is);
            BufferedReader    br      = new BufferedReader(isr, 8 * 1024);

            String        line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            String result = builder.toString();
            if (!"".equals(result)) {
                String keyword = "version ";
                int    index   = result.indexOf(keyword);
                line = result.substring(index + keyword.length());
                index = line.indexOf(' ');
                kernelVersion = line.substring(0, index);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLinuxCore_Ver");
        }
        return kernelVersion;
    }

    // =

    /**
     * 判断设备是否 root
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取是否启用 ADB
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isAdbEnabled() {
        try {
            return Settings.Secure.getInt(
                    ResourceUtils.getContentResolver(),
                    Settings.Global.ADB_ENABLED, 0
            ) > 0;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isAdbEnabled");
        }
        return false;
    }

    /**
     * 是否打开开发者选项
     * @return {@code true} yes, {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isDevelopmentSettingsEnabled() {
        try {
            return Settings.Global.getInt(
                    ResourceUtils.getContentResolver(),
                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
            ) > 0;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isDevelopmentSettingsEnabled");
        }
        return false;
    }

    // =

    // Default MAC address reported to a client that does not have the android.permission.LOCAL_MAC_ADDRESS permission.
    private static final String DEFAULT_MAC_ADDRESS = "02:00:00:00:00:00";

    /**
     * 获取设备 MAC 地址
     * <pre>
     *     没有打开 Wifi, 则获取 WLAN MAC 地址失败
     * </pre>
     * @return 设备 MAC 地址
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    public static String getMacAddress() {
        String macAddress = getMacAddressByWifiInfo();
        if (!DEFAULT_MAC_ADDRESS.equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByNetworkInterface();
        if (!DEFAULT_MAC_ADDRESS.equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByInetAddress();
        if (!DEFAULT_MAC_ADDRESS.equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByFile();
        if (!DEFAULT_MAC_ADDRESS.equals(macAddress)) {
            return macAddress;
        }
        return null;
    }

    /**
     * 获取 MAC 地址
     * @return MAC 地址
     */
    @RequiresPermission(Manifest.permission.ACCESS_WIFI_STATE)
    private static String getMacAddressByWifiInfo() {
        try {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifiManager = AppUtils.getWifiManager();
            if (wifiManager != null) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo != null) return wifiInfo.getMacAddress();
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMacAddressByWifiInfo");
        }
        return DEFAULT_MAC_ADDRESS;
    }

    /**
     * 获取 MAC 地址
     * @return MAC 地址
     */
    private static String getMacAddressByNetworkInterface() {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                if (ni == null || !ni.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = ni.getHardwareAddress();
                if (macBytes != null && macBytes.length > 0) {
                    StringBuilder builder = new StringBuilder();
                    for (byte b : macBytes) {
                        builder.append(String.format("%02x:", b));
                    }
                    return builder.substring(0, builder.length() - 1);
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMacAddressByNetworkInterface");
        }
        return DEFAULT_MAC_ADDRESS;
    }

    /**
     * 通过 InetAddress 获取 Mac 地址
     * @return Mac 地址
     */
    private static String getMacAddressByInetAddress() {
        try {
            InetAddress inetAddress = getInetAddress();
            if (inetAddress != null) {
                NetworkInterface ni = NetworkInterface.getByInetAddress(inetAddress);
                if (ni != null) {
                    byte[] macBytes = ni.getHardwareAddress();
                    if (macBytes != null && macBytes.length > 0) {
                        StringBuilder builder = new StringBuilder();
                        for (byte b : macBytes) {
                            builder.append(String.format("%02x:", b));
                        }
                        return builder.substring(0, builder.length() - 1);
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMacAddressByInetAddress");
        }
        return DEFAULT_MAC_ADDRESS;
    }

    /**
     * 获取 InetAddress
     * @return {@link InetAddress}
     */
    private static InetAddress getInetAddress() {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                // To prevent phone of xiaomi return "10.0.2.15"
                if (!ni.isUp()) continue;
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        if (hostAddress.indexOf(':') < 0) return inetAddress;
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getInetAddress");
        }
        return null;
    }

    /**
     * 获取 MAC 地址
     * @return MAC 地址
     */
    private static String getMacAddressByFile() {
        ShellUtils.CommandResult result = ShellUtils.execCmd("getprop wifi.interface", false);
        if (result.isSuccess()) {
            String name = result.successMsg;
            if (name != null) {
                result = ShellUtils.execCmd("cat /sys/class/net/" + name + "/address", false);
                if (result.result == 0) {
                    String address = result.successMsg;
                    if (address != null && address.length() > 0) {
                        return address;
                    }
                }
            }
        }
        return DEFAULT_MAC_ADDRESS;
    }

    // =

    /**
     * 关机 ( 需要 root 权限 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean shutdown() {
        return ADBUtils.shutdown();
    }

    /**
     * 重启设备 ( 需要 root 权限 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean reboot() {
        return ADBUtils.reboot();
    }

    /**
     * 重启设备 ( 需要 root 权限 )
     * @param reason 传递给内核来请求特殊的引导模式, 如 "recovery"
     *               重启到 Fastboot 模式 bootloader
     * @return {@code true} success, {@code false} fail
     */
    @RequiresPermission(Manifest.permission.REBOOT)
    public static boolean reboot(final String reason) {
        return ADBUtils.reboot(reason);
    }

    /**
     * 重启引导到 recovery ( 需要 root 权限 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean rebootToRecovery() {
        return ADBUtils.rebootToRecovery();
    }

    /**
     * 重启引导到 bootloader ( 需要 root 权限 )
     * @return {@code true} success, {@code false} fail
     */
    public static boolean rebootToBootloader() {
        return ADBUtils.rebootToBootloader();
    }

    // =

    /**
     * 判断是否是平板
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isTablet() {
        try {
            return (ResourceUtils.getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isTablet");
        }
        return false;
    }
}