package dev.utils.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
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
import java.util.Locale;
import java.util.Map;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 设备相关工具类
 * Created by Ttt
 */
public final class DeviceUtils {

    private DeviceUtils() {
    }

    // 日志TAG
    private static final String TAG = DBUtils.class.getSimpleName();

//    // http://blog.csdn.net/zhangcanyan/article/details/52817866
//    android.os.Build.BOARD：获取设备基板名称
//    android.os.Build.BOOTLOADER:获取设备引导程序版本号
//    android.os.Build.BRAND：获取设备品牌
//    android.os.Build.CPU_ABI：获取设备指令集名称(CPU的类型)
//    android.os.Build.CPU_ABI2：获取第二个指令集名称
//    android.os.Build.DEVICE：获取设备驱动名称
//    android.os.Build.DISPLAY：获取设备显示的版本包(在系统设置中显示为版本号)和ID一样
//    android.os.Build.FINGERPRINT：设备的唯一标识。由设备的多个信息拼接合成。
//    android.os.Build.HARDWARE：设备硬件名称,一般和基板名称一样(BOARD)
//    android.os.Build.HOST：设备主机地址
//    android.os.Build.ID:设备版本号。
//    android.os.Build.MODEL ：获取手机的型号 设备名称。
//    android.os.Build.MANUFACTURER:获取设备制造商
//    android:os.Build.PRODUCT：整个产品的名称
//    android:os.Build.RADIO：无线电固件版本号，通常是不可用的 显示unknown
//    android.os.Build.TAGS：设备标签。如release-keys 或测试的 test-keys
//    android.os.Build.TIME：时间
//    android.os.Build.TYPE:设备版本类型  主要为"user" 或"eng".
//    android.os.Build.USER:设备用户名 基本上都为android-build
//    android.os.Build.VERSION.RELEASE：获取系统版本字符串。如4.1.2 或2.2 或2.3等
//    android.os.Build.VERSION.CODENAME：设备当前的系统开发代号，一般使用REL代替
//    android.os.Build.VERSION.INCREMENTAL：系统源代码控制值，一个数字或者git hash值
//    android.os.Build.VERSION.SDK：系统的API级别 一般使用下面大的SDK_INT 来查看
//    android.os.Build.VERSION.SDK_INT：系统的API级别 数字表示

    /** 换行字符串 */
    private static final String NEW_LINE_STR = System.getProperty("line.separator");

    /**
     * 获取当前操作系统的语言
     * @return String 系统语言
     */
    public static String getSysLanguage() {
        String language = Locale.getDefault().getLanguage();
        return language;
    }

    /**
     * 获取设备信息
     * @param dInfoMaps 传入设备信息传出HashMap
     */
    public static void getDeviceInfo(HashMap<String, String> dInfoMaps) {
        // 获取设备信息类的所有申明的字段,即包括public、private和proteced， 但是不包括父类的申明字段。
        Field[] fields = Build.class.getDeclaredFields();
        // 遍历字段
        for (Field field : fields) {
            try {
                // 取消java的权限控制检查
                field.setAccessible(true);
                // 获取类型对应字段的数据，并保存
                dInfoMaps.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getDeviceInfo");
            }
        }
    }

    /**
     * 获取设备信息
     * @param dInfoMaps 传入设备信息传出HashMap
     */
    public static void getDeviceInfo2(HashMap<String, String> dInfoMaps) {
        // 获取设备信息类的所有申明的字段,即包括public、private和proteced， 但是不包括父类的申明字段。
        Field[] fields = Build.class.getDeclaredFields();
        // 遍历字段
        for (Field field : fields) {
            try {
                // 取消java的权限控制检查
                field.setAccessible(true);

                if (field.getName().toLowerCase().equals("SUPPORTED_ABIS".toLowerCase())) {
                    Object object = field.get(null);
                    // 判断是否数组
                    if (object instanceof String[]) {
                        // 获取类型对应字段的数据，并保存 - 保存支持的指令集 [arm64-v8a, armeabi-v7a, armeabi]
                        dInfoMaps.put(field.getName().toLowerCase(), Arrays.toString((String[]) object));
                        continue;
                    }
                }
                // 获取类型对应字段的数据，并保存
                dInfoMaps.put(field.getName().toLowerCase(), field.get(null).toString());
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getDeviceInfo2");
            }
        }
    }

    /**
     * 处理设备信息
     * @param dInfoMaps 设备信息
     * @param eHint 错误提示，如获取设备信息失败
     */
    public static String handleDeviceInfo(HashMap<String, String> dInfoMaps, String eHint) {
        try {
            // 初始化StringBuilder，拼接字符串
            StringBuilder sBuilder = new StringBuilder();
            // 获取设备信息
            Iterator<Map.Entry<String, String>> mapIter = dInfoMaps.entrySet().iterator();
            // 遍历设备信息
            while (mapIter.hasNext()) {
                // 获取对应的key-value
                Map.Entry<String, String> rnEntry = (Map.Entry<String, String>) mapIter.next();
                String rnKey = (String) rnEntry.getKey(); // key
                String rnValue = (String) rnEntry.getValue(); // value
                // 保存设备信息
                sBuilder.append(rnKey);
                sBuilder.append(" = ");
                sBuilder.append(rnValue);
                sBuilder.append(NEW_LINE_STR);
            }
            return sBuilder.toString();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "handleDeviceInfo");
        }
        return eHint;
    }

    /**
     * 获取设备系统版本号
     * @return 设备系统版本号
     */
    public static String getSDKVersionName() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取当前SDK 版本号
     * @return
     */
    public static int getSDKVersion(){
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取Android id
     * hint:
     * 在设备首次启动时，系统会随机生成一个64位的数字，并把这个数字以16进制字符串的形式保存下来，这个16进制的字符串就是ANDROID_ID，当设备被wipe后该值会被重置。
     * @return
     */
    public static String getAndroidId(){
        // Android id 默认为null
        String androidId = null;
        try {
            androidId = Settings.Secure.getString(DevUtils.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAndroidId");
        }
        return androidId;
    }

    /**
     * 判断设备是否 root
     * @return
     */
    public static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = { "/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/" };
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    // ==

    /** 特殊mac地址用于判断是否获取失败 */
    private static final String CUSTOM_MAC = "02:00:00:00:00:00";

    /**
     * 获取设备 MAC 地址
     * hint:
     * 需添加权限 <uses-permission android:name="android.permission.INTERNET" />
     * 需添加权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     * @return
     */
    public static String getMacAddress() {
        String macAddress = getMacAddressByWifiInfo();
        if (!CUSTOM_MAC.equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByNetworkInterface();
        if (!CUSTOM_MAC.equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByInetAddress();
        if (!CUSTOM_MAC.equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByFile();
        if (!CUSTOM_MAC.equals(macAddress)) {
            return macAddress;
        }
        // 没有打开wifi, 获取 WLAN MAC 地址失败
        return null;
    }

    /**
     * 获取设备 MAC 地址
     * hint:
     * 需添加权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     * @return
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    private static String getMacAddressByWifiInfo() {
        try {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifiManager = (WifiManager) DevUtils.getContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo != null) return wifiInfo.getMacAddress();
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMacAddressByWifiInfo");
        }
        return CUSTOM_MAC;
    }

    /**
     * 获取设备 MAC 地址
     * hint:
     * 需添加权限 <uses-permission android:name="android.permission.INTERNET" />
     * @return
     */
    private static String getMacAddressByNetworkInterface() {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                if (ni == null || !ni.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = ni.getHardwareAddress();
                if (macBytes != null && macBytes.length > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (byte b : macBytes) {
                        sb.append(String.format("%02x:", b));
                    }
                    return sb.substring(0, sb.length() - 1);
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMacAddressByNetworkInterface");
        }
        return CUSTOM_MAC;
    }

    private static String getMacAddressByInetAddress() {
        try {
            InetAddress inetAddress = getInetAddress();
            if (inetAddress != null) {
                NetworkInterface ni = NetworkInterface.getByInetAddress(inetAddress);
                if (ni != null) {
                    byte[] macBytes = ni.getHardwareAddress();
                    if (macBytes != null && macBytes.length > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (byte b : macBytes) {
                            sb.append(String.format("%02x:", b));
                        }
                        return sb.substring(0, sb.length() - 1);
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMacAddressByInetAddress");
        }
        return "02:00:00:00:00:00";
    }

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
     * 获取设备 MAC 地址
     * @return MAC 地址
     */
    private static String getMacAddressByFile() {
        ShellUtils.CommandResult result = ShellUtils.execCmd("getprop wifi.interface", false);
        if (result.result == 0) {
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
        return CUSTOM_MAC;
    }

    // ==

    /**
     * 获取设备厂商 如 Xiaomi
     * @return 设备厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
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
     * 关机 (需要 root 权限)
     * @return
     */
    public static boolean shutdown() {
        try {
            ShellUtils.execCmd("reboot -p", true);
            Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
            intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
            DevUtils.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            return true;
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "shutdown");
        }
        return false;
    }

    /**
     * 重启设备 (需要 root 权限)
     * @return
     */
    public static boolean reboot() {
        try {
            ShellUtils.execCmd("reboot", true);
            Intent intent = new Intent(Intent.ACTION_REBOOT);
            intent.putExtra("nowait", 1);
            intent.putExtra("interval", 1);
            intent.putExtra("window", 0);
            DevUtils.getContext().sendBroadcast(intent);
            return true;
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "reboot");
        }
        return false;
    }

    /**
     * 重启设备 (需要 root 权限) - 并进行特殊的引导模式 (recovery、 Fastboot)
     * @param reason 传递给内核来请求特殊的引导模式，如"recovery"
     *               重启到 Fastboot 模式 bootloader
     */
    public static void reboot(final String reason) {
        try {
            PowerManager mPowerManager = (PowerManager) DevUtils.getContext().getSystemService(Context.POWER_SERVICE);
            if (mPowerManager == null)
                return;
            mPowerManager.reboot(reason);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "reboot");
        }
    }

    /**
     * 重启引导到 recovery (需要 root 权限)
     * @return
     */
    public static boolean reboot2Recovery() {
        ShellUtils.CommandResult result = ShellUtils.execCmd("reboot recovery", true);
        return result.isSuccess2();
    }

    /**
     * 重启引导到 bootloader (需要 root 权限)
     * @return
     */
    public static boolean reboot2Bootloader() {
        ShellUtils.CommandResult result = ShellUtils.execCmd("reboot bootloader", true);
        return result.isSuccess2();
    }

    /**
     * 获取 基带版本 BASEBAND-VER
     * @return
     */
    public static String getBaseband_Ver() {
        String Version = "";
        try {
            Class cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
            Method m = cl.getMethod("get", new Class[]{String.class, String.class});
            Object result = m.invoke(invoker, new Object[]{"gsm.version.baseband", "no message"});
            Version = (String) result;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBaseband_Ver");
        }
        return Version;
    }

    /**
     * 获取 内核版本 CORE-VER
     * @return
     */
    public static String getLinuxCore_Ver() {
        String kernelVersion = "";
        try {
            Process process = null;
            try {
                process = Runtime.getRuntime().exec("cat /proc/version");
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getLinuxCore_Ver - Process");
            }
            InputStream outs = process.getInputStream();
            InputStreamReader isrout = new InputStreamReader(outs);
            BufferedReader brout = new BufferedReader(isrout, 8 * 1024);

            String result = "";
            String line;
            try {
                while ((line = brout.readLine()) != null) {
                    result += line;
                }
            } catch (IOException e) {
                LogPrintUtils.eTag(TAG, e, "getLinuxCore_Ver - readLine");
            }
            try {
                if (result != "") {
                    String Keyword = "version ";
                    int index = result.indexOf(Keyword);
                    line = result.substring(index + Keyword.length());
                    index = line.indexOf(" ");
                    kernelVersion = line.substring(0, index);
                }
            } catch (IndexOutOfBoundsException e) {
                LogPrintUtils.eTag(TAG, e, "getLinuxCore_Ver - substring");
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getLinuxCore_Ver");
        }
        return kernelVersion;
    }
}
