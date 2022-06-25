package dev.utils.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Xml;

import androidx.annotation.RequiresPermission;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import dev.DevUtils;
import dev.utils.DevFinal;
import dev.utils.LogPrintUtils;
import dev.utils.common.CloseUtils;
import dev.utils.common.StringUtils;

/**
 * detail: 手机相关工具类
 * @author Ttt
 * <pre>
 *     所需权限
 *     <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 * </pre>
 */
public final class PhoneUtils {

    private PhoneUtils() {
    }

    // 日志 TAG
    private static final String TAG = PhoneUtils.class.getSimpleName();

    /**
     * 判断设备是否是手机
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhone() {
        try {
            TelephonyManager telephonyManager = AppUtils.getTelephonyManager();
            return telephonyManager != null && telephonyManager.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isPhone");
        }
        return false;
    }

    /**
     * 获取 SIM 卡状态
     * @return SIM 卡状态
     */
    public static int getSimState() {
        return getSimState(-1);
    }

    /**
     * 获取 SIM 卡状态
     * @param slotIndex 卡槽索引
     * @return SIM 卡状态
     */
    public static int getSimState(final int slotIndex) {
        try {
            TelephonyManager telephonyManager = AppUtils.getTelephonyManager();
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                return telephonyManager.getSimState();
            } else { // 使用默认卡槽
                if (slotIndex == -1) {
                    return telephonyManager.getSimState();
                }
                // 26 以上有公开 api
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    return telephonyManager.getSimState(slotIndex);
                }
                // 反射调用方法
                Method method = telephonyManager.getClass().getDeclaredMethod("getSimState");
                method.setAccessible(true);
                return (Integer) (method.invoke(telephonyManager, slotIndex));
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSimState");
        }
        return TelephonyManager.SIM_STATE_UNKNOWN;
    }

    /**
     * 判断是否装载 SIM 卡
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSimReady() {
        return isSimReady(-1);
    }

    /**
     * 判断是否装载 SIM 卡
     * @param slotIndex 卡槽索引
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSimReady(final int slotIndex) {
        return getSimState(slotIndex) == TelephonyManager.SIM_STATE_READY;
    }

    /**
     * 获取 SIM 卡运营商的国家代码
     * @return SIM 卡运营商的国家代码
     */
    public static String getSimCountryIso() {
        try {
            return AppUtils.getTelephonyManager().getSimCountryIso();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSimCountryIso");
        }
        return null;
    }

    /**
     * 获取 SIM 卡注册的网络运营商的国家代码
     * @return SIM 卡注册的网络运营商的国家代码
     */
    public static String getNetworkCountryIso() {
        try {
            return AppUtils.getTelephonyManager().getNetworkCountryIso();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getNetworkCountryIso");
        }
        return null;
    }

    /**
     * 获取 SIM 卡运营商的国家代码
     * @return SIM 卡运营商的国家代码
     */
    public static String getSimCountry() {
        try {
            TelephonyManager telephonyManager = AppUtils.getTelephonyManager();
            // SIM 卡运营商的国家代码
            String simCountry = telephonyManager.getSimCountryIso();
            // 注册的网络运营商的国家代码
            String networkCountry = telephonyManager.getNetworkCountryIso();
            if (simCountry != null && simCountry.trim().length() != 0) {
                return simCountry.trim();
            } else if (networkCountry != null && networkCountry.trim().length() != 0) {
                return networkCountry.trim();
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSimCountry");
        }
        return null;
    }

    /**
     * 判断 SIM 卡运营商是否国内
     * @return 状态码 1 属于国内 ( 中国 ), 2 属于国外, 3 属于无 SIM 卡
     */
    public static int checkSimCountry() {
        try {
            String countryCode = getSimCountry();
            // 不等于 null, 表示属于存在 SIM 卡
            if (countryCode != null) {
                // zh_CN Locale.SIMPLIFIED_CHINESE
                // 截取前面两位属于 zh 表示属于中国
                String country = countryCode.substring(0, 2);
                // 如果属于 cn 表示属于国内
                if ("cn".equalsIgnoreCase(country)) {
                    return 1;
                } else {
                    return 2;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "checkSimCountry");
        }
        return 3;
    }

    /**
     * 获取 MEID 码
     * @return MEID 码
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getMEID() {
        return getMEID(-1);
    }

    /**
     * 获取 MEID 码
     * @param slotIndex 卡槽索引
     * @return MEID 码
     */
    @SuppressLint("MissingPermission")
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getMEID(final int slotIndex) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                if (slotIndex == -1) return AppUtils.getTelephonyManager().getMeid();
                return AppUtils.getTelephonyManager().getMeid(slotIndex);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getMEID");
            }
        }
        return null;
    }

    /**
     * 获取 IMEI 码
     * @return IMEI 码
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getIMEI() {
        return getIMEI(-1);
    }

    /**
     * 获取 IMEI 码
     * <pre>
     *     IMEI 是 International Mobile Equipment Identity ( 国际移动设备标识 ) 的简称
     *     IMEI 由 15 位数字组成的「电子串号」它与每台手机一一对应, 而且该码是全世界唯一的
     *     其组成为:
     *     1. 前 6 位数 (TAC) 是「型号核准号码」一般代表机型
     *     2. 接着的 2 位数 (FAC) 是「最后装配号」一般代表产地
     *     3. 之后的 6 位数 (SNR) 是「串号」一般代表生产顺序号
     *     4. 最后 1 位数 (SP) 通常是「0」为检验码, 目前暂备用
     * </pre>
     * @param slotIndex 卡槽索引
     * @return IMEI 码
     */
    @SuppressLint("MissingPermission")
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getIMEI(final int slotIndex) {
        try {
            TelephonyManager telephonyManager = AppUtils.getTelephonyManager();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (slotIndex == -1) return telephonyManager.getImei();
                return telephonyManager.getImei(slotIndex);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // 反射调用方法
                Class<?> clazz  = telephonyManager.getClass();
                Method   method = clazz.getDeclaredMethod("getImei");
                method.setAccessible(true);
                return (String) method.invoke(telephonyManager);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getIMEI");
        }
        return null;
    }

    /**
     * 获取 IMSI 码
     * <pre>
     *     IMSI 是国际移动用户识别码的简称 (International Mobile Subscriber Identity)
     *     IMSI 共有 15 位, 其结构如下:
     *     MCC + MNC + MIN
     *     MCC: Mobile Country Code, 移动国家码, 共 3 位, 中国为 460
     *     MNC: Mobile NetworkCode, 移动网络码, 共 2 位
     *     在中国, 移动的代码为 00 和 02, 联通的代码为 01, 电信的代码为 03
     *     合起来就是 Android 手机中 APN 配置文件中的代码
     *     中国移动: 46000 46002 46007
     *     中国联通: 46001 46006
     *     中国电信: 46003 46005 46011
     *     举例, 一个典型的 IMSI 号码为 460030912121001
     * </pre>
     * @return IMSI 码
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getIMSI() {
        try {
            return AppUtils.getTelephonyManager().getSubscriberId();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getIMSI");
        }
        return null;
    }

    /**
     * 获取 SIM 卡运营商名称 ( 如: 中国移动、如中国联通、中国电信 )
     * @return SIM 卡运营商名称
     */
    public static String getSimOperatorName() {
        try {
            return AppUtils.getTelephonyManager().getSimOperatorName();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSimOperatorName");
        }
        return null;
    }

    /**
     * 获取 SIM 卡运营商 MCC + MNC
     * @return SIM 卡运营商 MCC + MNC
     */
    public static String getSimOperator() {
        try {
            return AppUtils.getTelephonyManager().getSimOperator();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSimOperator");
        }
        return null;
    }

    /**
     * 通过 IMSI 获取中国运营商简称
     * @return 中国运营商简称
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getChinaOperatorByIMSI() {
        return getChinaOperatorByIMSI(getIMSI());
    }

    /**
     * 通过 IMSI 获取中国运营商简称
     * @param imsi IMSI 码
     * @return 中国运营商简称
     */
    public static String getChinaOperatorByIMSI(final String imsi) {
        if (imsi != null) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007")) {
                return "中国移动";
            } else if (imsi.startsWith("46001") || imsi.startsWith("46006")) {
                return "中国联通";
            } else if (imsi.startsWith("46003") || imsi.startsWith("46005") || imsi.startsWith("46011")) {
                return "中国电信";
            }
        }
        return null;
    }

    /**
     * 获取 SIM 卡中国运营商简称
     * @return SIM 卡中国运营商简称
     */
    public static String getChinaOperatorBySimOperator() {
        return getChinaOperatorBySimOperator(getSimOperator());
    }

    /**
     * 获取 SIM 卡中国运营商简称
     * @param simOperator SIM 卡运营商 MCC + MNC
     * @return SIM 卡中国运营商简称
     */
    public static String getChinaOperatorBySimOperator(final String simOperator) {
        if (simOperator != null) {
            switch (simOperator) {
                case "46000":
                case "46002":
                case "46007":
                    return "中国移动";
                case "46001":
                case "46006":
                    return "中国联通";
                case "46003":
                case "46005":
                case "46011":
                    return "中国电信";
            }
        }
        return null;
    }

    /**
     * 获取手机类型
     * <pre>
     *     {@link TelephonyManager#PHONE_TYPE_NONE} 0 手机制式未知
     *     {@link TelephonyManager#PHONE_TYPE_GSM} 1 手机制式为 GSM, 移动和联通
     *     {@link TelephonyManager#PHONE_TYPE_CDMA} 2 手机制式为 CDMA, 电信
     *     {@link TelephonyManager#PHONE_TYPE_SIP} 3 手机制式为 SIP
     * </pre>
     * @return 手机类型
     */
    public static int getPhoneType() {
        try {
            return AppUtils.getTelephonyManager().getPhoneType();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getPhoneType");
        }
        return TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取设备 id
     * @return 设备 id
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getDeviceId() {
        return getDeviceId(-1);
    }

    /**
     * 获取设备 id
     * @param slotIndex 卡槽索引
     * @return 设备 id
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getDeviceId(final int slotIndex) {
        try {
            TelephonyManager telephonyManager = AppUtils.getTelephonyManager();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (slotIndex == -1) return telephonyManager.getDeviceId();
                return telephonyManager.getDeviceId(slotIndex);
            }
            return telephonyManager.getDeviceId();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDeviceId");
        }
        return null;
    }

    /**
     * 获取 Android id
     * <pre>
     *     在设备首次启动时, 系统会随机生成一个 64 位的数字, 并把这个数字以十六进制字符串的形式保存下来
     *     这个十六进制的字符串就是 ANDROID_ID, 当设备被 wipe 后该值会被重置
     * </pre>
     * @return Android id
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidId() {
        try {
            return Settings.Secure.getString(
                    ResourceUtils.getContentResolver(), Settings.Secure.ANDROID_ID
            );
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAndroidId");
        }
        return null;
    }

    /**
     * 获取设备序列号
     * @return 设备序列号
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getSerialNumber() {
        try {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? Build.getSerial() : Build.SERIAL;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSerialNumber");
        }
        return null;
    }

    /**
     * 获取 SIM 卡序列号
     * @return SIM 卡序列号
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getSimSerialNumber() {
        try {
            return AppUtils.getTelephonyManager().getSimSerialNumber();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSimSerialNumber");
        }
        return null;
    }

    /**
     * 获取设备唯一 UUID
     * @return 设备唯一 UUID
     */
    @SuppressLint("MissingPermission")
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getUUID() {
        String deviceId     = StringUtils.getString(getDeviceId());
        String androidId    = StringUtils.getString(getAndroidId());
        String serialNumber = StringUtils.getString(getSerialNumber());
        // 生成唯一关联 uuid
        UUID deviceUUID = new UUID(androidId.hashCode(), ((long) deviceId.hashCode() << 32) | serialNumber.hashCode());
        return deviceUUID.toString();
    }

    /**
     * 获取手机状态信息
     * <pre>
     *     DeviceId(IMEI) = 99000311726612
     *     DeviceSoftwareVersion = 00
     *     Line1Number =
     *     NetworkCountryIso = cn
     *     NetworkOperator = 46003
     *     NetworkOperatorName = 中国电信
     *     NetworkType = 6
     *     PhoneType = 2
     *     SimCountryIso = cn
     *     SimOperator = 46003
     *     SimOperatorName = 中国电信
     *     SimSerialNumber = 89860315045710604022
     *     SimState = 5
     *     SubscriberId(IMSI) = 460030419724900
     *     VoiceMailNumber = *86
     * </pre>
     * @return 手机状态信息
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getPhoneStatus() {
        try {
            TelephonyManager telephonyManager = AppUtils.getTelephonyManager();
            if (telephonyManager == null) return "";
            return "DeviceId(IMEI) = " + telephonyManager.getDeviceId() +
                    "\nDeviceSoftwareVersion = " + telephonyManager.getDeviceSoftwareVersion() +
                    "\nLine1Number = " + telephonyManager.getLine1Number() +
                    "\nNetworkCountryIso = " + telephonyManager.getNetworkCountryIso() +
                    "\nNetworkOperator = " + telephonyManager.getNetworkOperator() +
                    "\nNetworkOperatorName = " + telephonyManager.getNetworkOperatorName() +
                    "\nNetworkType = " + telephonyManager.getNetworkType() +
                    "\nPhoneType = " + telephonyManager.getPhoneType() +
                    "\nSimCountryIso = " + telephonyManager.getSimCountryIso() +
                    "\nSimOperator = " + telephonyManager.getSimOperator() +
                    "\nSimOperatorName = " + telephonyManager.getSimOperatorName() +
                    "\nSimSerialNumber = " + telephonyManager.getSimSerialNumber() +
                    "\nSimState = " + telephonyManager.getSimState() +
                    "\nSubscriberId(IMSI) = " + telephonyManager.getSubscriberId()
                    + "(" + getChinaOperatorByIMSI() + ")" +
                    "\nVoiceMailNumber = " + telephonyManager.getVoiceMailNumber();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getPhoneStatus");
        }
        return "";
    }

    /**
     * 跳至拨号界面
     * @param phoneNumber 电话号码
     * @return {@code true} success, {@code false} fail
     */
    public static boolean dial(final String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            if (IntentUtils.isIntentAvailable(intent)) {
                return AppUtils.startActivity(intent);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "dial");
        }
        return false;
    }

    /**
     * 拨打电话
     * @param phoneNumber 电话号码
     * @return {@code true} success, {@code false} fail
     */
    public static boolean call(final String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
            if (IntentUtils.isIntentAvailable(intent)) {
                return AppUtils.startActivity(intent);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "call");
        }
        return false;
    }

    /**
     * 跳至发送短信界面
     * @param phoneNumber 接收号码
     * @param content     短信内容
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sendSms(
            final String phoneNumber,
            final String content
    ) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            if (IntentUtils.isIntentAvailable(intent)) {
                intent.putExtra("sms_body", content);
                return AppUtils.startActivity(intent);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "sendSms");
        }
        return false;
    }

    /**
     * 发送短信
     * @param phoneNumber 接收号码
     * @param content     短信内容
     * @return {@code true} success, {@code false} fail
     */
    public static boolean sendSmsSilent(
            final String phoneNumber,
            final String content
    ) {
        if (TextUtils.isEmpty(content)) return false;
        try {
            PendingIntent sentIntent = PendingIntent.getBroadcast(
                    DevUtils.getContext(), 0, new Intent("send"), 0
            );
            SmsManager smsManager = SmsManager.getDefault();
            if (content.length() >= 70) {
                List<String> lists = smsManager.divideMessage(content);
                for (String value : lists) {
                    smsManager.sendTextMessage(
                            phoneNumber, null, value, sentIntent, null
                    );
                }
            } else {
                smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null);
            }
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "sendSmsSilent");
        }
        return false;
    }

    /**
     * 打开手机联系人界面点击联系人后便获取该号码
     * <pre>
     *     protected void onActivityResult (int requestCode, int resultCode, Intent intent) {
     *          super.onActivityResult(requestCode, resultCode, intent);
     *          if (intent != null) {
     *              Uri uri = intent.getData();
     *              String num = null;
     *              // 创建内容解析者
     *              ContentResolver resolver = getContentResolver();
     *              Cursor cursor = resolver.query(uri, null, null, null, null);
     *              while (cursor.moveToNext()) {
     *                  num = cursor.getString(cursor.getColumnIndex("data1"));
     *              }
     *              CloseUtils.closeIOQuietly(cursor);
     *              num = num.replaceAll("-", "");
     *          }
     *      }
     * </pre>
     * @param activity {@link Activity}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean getContactNum(final Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            try {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.PICK");
                intent.setType("vnd.android.cursor.dir/phone_v2");
                activity.startActivityForResult(intent, 0);
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getContactNum");
            }
        }
        return false;
    }

    /**
     * 获取手机联系人信息
     * @return 手机联系人信息
     */
    public static List<Map<String, String>> getAllContactInfo() {
        List<Map<String, String>> list = new ArrayList<>();
        // 游标
        Cursor cursor = null;
        try {
            ContentResolver resolver = ResourceUtils.getContentResolver();
            Uri             raw_uri  = Uri.parse("content://com.android.contacts/raw_contacts");
            Uri             date_uri = Uri.parse("content://com.android.contacts/data");
            cursor = resolver.query(
                    raw_uri, new String[]{"contact_id"},
                    null, null, null
            );
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String contact_id = cursor.getString(0);
                    if (!TextUtils.isEmpty(contact_id)) {
                        Cursor c = resolver.query(
                                date_uri, new String[]{"data1", "mimetype"},
                                "raw_contact_id=?", new String[]{contact_id}, null
                        );
                        Map<String, String> map = new HashMap<>();
                        if (c != null) {
                            while (c.moveToNext()) {
                                String data1    = c.getString(0);
                                String mimetype = c.getString(1);
                                if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                                    map.put("phone", data1); // 电话
                                } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                                    map.put("name", data1); // 姓名
                                }
                            }
                        }
                        list.add(map);
                        CloseUtils.closeIOQuietly(c);
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAllContactInfo");
        } finally {
            CloseUtils.closeIOQuietly(cursor);
        }
        return list;
    }

    /**
     * 获取手机联系人信息
     * @return 手机联系人信息
     */
    public static List<Map<String, String>> getAllContactInfo2() {
        List<Map<String, String>> list   = new ArrayList<>();
        Cursor                    cursor = null;
        try {
            cursor = ResourceUtils.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null
            );
            while (cursor.moveToNext()) {
                Map<String, String> map = new HashMap<>();
                String phoneNumber = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                ).trim().replaceAll(" ", "");
                String phoneName = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                ).trim();
                map.put("phone", phoneNumber);
                map.put("name", phoneName);
                list.add(map);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAllContactInfo2");
        } finally {
            CloseUtils.closeIOQuietly(cursor);
        }
        return list;
    }

    /**
     * 获取手机短信并保存到 xml 中
     * @param filePath 文件路径
     * @return {@code true} success, {@code false} fail
     */
    public static boolean getAllSMS(final String filePath) {
        Cursor cursor = null;
        try {
            ContentResolver resolver = ResourceUtils.getContentResolver();
            Uri             uri      = Uri.parse("content://sms");
            cursor = resolver.query(
                    uri, new String[]{"address", "date", "type", "body"},
                    null, null, null
            );
//            // 获取短信的个数
//            int count = cursor.getCount();
            XmlSerializer xmlSerializer = Xml.newSerializer();
            xmlSerializer.setOutput(new FileOutputStream(new File(filePath)), DevFinal.ENCODE.UTF_8);
            xmlSerializer.startDocument(DevFinal.ENCODE.UTF_8, true);
            xmlSerializer.startTag(null, "smss");
            while (cursor.moveToNext()) {
                xmlSerializer.startTag(null, "sms");

                xmlSerializer.startTag(null, "address");
                String address = cursor.getString(0);
                xmlSerializer.text(address);
                xmlSerializer.endTag(null, "address");

                xmlSerializer.startTag(null, "date");
                String date = cursor.getString(1);
                xmlSerializer.text(date);
                xmlSerializer.endTag(null, "date");

                xmlSerializer.startTag(null, "type");
                String type = cursor.getString(2);
                xmlSerializer.text(type);
                xmlSerializer.endTag(null, "type");

                xmlSerializer.startTag(null, "body");
                String body = cursor.getString(3);
                xmlSerializer.text(body);
                xmlSerializer.endTag(null, "body");

                xmlSerializer.endTag(null, "sms");
            }
            xmlSerializer.endTag(null, "smss");
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            return true;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAllSMS");
        } finally {
            CloseUtils.closeIOQuietly(cursor);
        }
        return false;
    }
}