package dev.utils.app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.RequiresPermission;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * detail: 手机相关工具类
 * @author Ttt
 * <pre>
 *      双卡双待系统IMEI和IMSI方案
 *      @see <a href="http://benson37.iteye.com/blog/1923946"/>
 * </pre>
 */
public final class PhoneUtils {

    private PhoneUtils() {
    }

    // 日志 TAG
    private static final String TAG = PhoneUtils.class.getSimpleName();

    /**
     * 判断是否装载sim卡
     * @return
     */
    public static boolean isSimReady() {
        try {
            // 获取电话管理类
            TelephonyManager tpManager = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            // 是否准备完毕
            if (tpManager != null && tpManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
                return true;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isSimReady");
        }
        return false;
    }

    /**
     * 获取Sim卡所属地区，非国内地区暂不支持播放
     * @return 返回SIM的地区码
     */
    public static String getUserCountry() {
        try {
            TelephonyManager tpManager = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            String simCountry = tpManager.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) {
                // SIM country code is available
                return simCountry.toLowerCase(Locale.CHINA);
            } else if (tpManager.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) {
                // device is not 3G (would be unreliable)
                String networkCountry = tpManager.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) {
                    // network country code is available
                    return networkCountry.toLowerCase(Locale.CHINESE);
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getUserCountry");
        }
        return null;
    }

    /**
     * 判断地区，是否属于国内
     * @return 状态码 1 属于国内(中国)，2 属于 国外，3 属于无SIM卡
     */
    public static int judgeArea() {
        // 默认属于无sim卡
        int state = 3;
        try {
            String countryCode = getUserCountry();
            // 不等于null,表示属于存在SIM卡
            if (countryCode != null) {
                // zh_CN Locale.SIMPLIFIED_CHINESE
                // 截取前面两位属于zh表示属于中国
                String country = countryCode.substring(0, 2);
                // 如果属于ch开头表示属于中国
                if (country.toLowerCase().equals("cn")) {
                    state = 1;
                } else {
                    state = 2;
                }
            } else { // 不存在sim卡
                String localCountry = Locale.getDefault().getCountry();
                // 如果属于ch开头表示属于中国
                if (localCountry.toLowerCase().equals("cn")) {
                    return 1;
                } else {
                    return 2;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "judgeArea");
        }
        return state;
    }

    /**
     * 判断设备是否是手机
     * @return {@code true} 是, {@code false} 否
     */
    public static boolean isPhone() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager != null && telephonyManager.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isPhone");
        }
        return false;
    }

    /**
     * 获取 MEID 移动设备识别码
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * @return
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getMEID() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return telephonyManager.getMeid();
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMEID");
        }
        return null;
    }

    /**
     * 获取 MEID 移动设备识别码
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * @param slotId
     * @return
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getMEID(final int slotId) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return telephonyManager.getMeid(slotId);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMEID");
        }
        return null;
    }

    /**
     * 获取 IMEI 码
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * @return IMEI 码
     * <pre>
     *      IMEI是International Mobile Equipment Identity (国际移动设备标识)的简称
     *      IMEI由15位数字组成的”电子串号”，它与每台手机一一对应，而且该码是全世界唯一的
     *      其组成为:
     *      1. 前6位数(TAC)是”型号核准号码”，一般代表机型
     *      2. 接着的2位数(FAC)是”最后装配号”，一般代表产地
     *      3. 之后的6位数(SNR)是”串号”，一般代表生产顺序号
     *      4. 最后1位数(SP)通常是”0″，为检验码，目前暂备用
     * </pre>
     */
    @SuppressLint({"HardwareIds"})
    @RequiresPermission(READ_PHONE_STATE)
    public static String getIMEI() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return telephonyManager.getImei();
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {
                    Class clazz = telephonyManager.getClass();
                    Method getImeiMethod = clazz.getDeclaredMethod("getImei");
                    getImeiMethod.setAccessible(true);
                    String imei = (String) getImeiMethod.invoke(telephonyManager);
                    if (imei != null) return imei;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "getIMEI");
                }
            }
            return telephonyManager.getDeviceId();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getIMEI");
        }
        return null;
    }

    /**
     * 获取 IMEI 码
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * @param slotId
     * @return IMEI 码
     */
    @SuppressLint({"HardwareIds"})
    @RequiresPermission(READ_PHONE_STATE)
    public static String getIMEI(final int slotId) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return telephonyManager.getImei(slotId);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {
                    Class clazz = telephonyManager.getClass();
                    Method getImeiMethod = clazz.getDeclaredMethod("getImei");
                    getImeiMethod.setAccessible(true);
                    String imei = (String) getImeiMethod.invoke(telephonyManager, slotId);
                    if (imei != null) return imei;
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "getIMEI");
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getIMEI");
        }
        return getIMEI();
    }

    /**
     * 获取 IMSI 码
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * @return IMSI 码
     * <pre>
     *      IMSI是国际移动用户识别码的简称(International Mobile Subscriber Identity)
     *      IMSI共有15位，其结构如下:
     *      MCC+MNC+MIN
     *      MCC: Mobile Country Code，移动国家码，共3位，中国为460;
     *      MNC: Mobile NetworkCode，移动网络码，共2位
     *      在中国，移动的代码为电00和02，联通的代码为01，电信的代码为03
     *      合起来就是(也是Android手机中APN配置文件中的代码):
     *      中国移动: 46000 46002
     *      中国联通: 46001
     *      中国电信: 46003
     *      举例，一个典型的IMSI号码为460030912121001
     * </pre>
     */
    @SuppressLint({"HardwareIds"})
    @RequiresPermission(READ_PHONE_STATE)
    public static String getIMSI() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager != null ? telephonyManager.getSubscriberId() : null;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getIMSI");
        }
        return null;
    }

    /**
     * 获取IMSI处理过后的简称
     * @param IMSI
     * @return
     */
    public static String getIMSIIDName(final String IMSI) {
        if (IMSI != null) {
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                return "中国移动";
            } else if (IMSI.startsWith("46001")) {
                return "中国联通";
            } else if (IMSI.startsWith("46003")) {
                return "中国电信";
            }
        }
        return null;
    }

    /**
     * 获取移动终端类型
     * <pre>
     *      {@link TelephonyManager#PHONE_TYPE_NONE } : 0 手机制式未知
     *      {@link TelephonyManager#PHONE_TYPE_GSM  } : 1 手机制式为 GSM，移动和联通
     *      {@link TelephonyManager#PHONE_TYPE_CDMA } : 2 手机制式为 CDMA，电信
     *      {@link TelephonyManager#PHONE_TYPE_SIP  } : 3
     * </pre>
     * @return 手机制式
     */
    public static int getPhoneType() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager != null ? telephonyManager.getPhoneType() : -1;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getPhoneType");
        }
        return 0;
    }

    /**
     * 判断 sim 卡是否准备好
     * @return {@code true} 是, {@code false} 否
     */
    public static boolean isSimCardReady() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager != null && telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isSimCardReady");
        }
        return false;
    }

    /**
     * 获取 Sim 卡运营商名称 => 中国移动、如中国联通、中国电信
     * @return sim 卡运营商名称
     */
    public static String getSimOperatorName() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager != null ? telephonyManager.getSimOperatorName() : null;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSimOperatorName");
        }
        return null;
    }

    /**
     * 获取 Sim 卡运营商名称 => 中国移动、如中国联通、中国电信
     * @return 移动网络运营商名称
     */
    public static String getSimOperatorByMnc() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            String operator = telephonyManager != null ? telephonyManager.getSimOperator() : null;
            if (operator == null) return null;
            switch (operator) {
                case "46000":
                case "46002":
                case "46007":
                    return "中国移动";
                case "46001":
                    return "中国联通";
                case "46003":
                    return "中国电信";
                default:
                    return operator;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSimOperatorByMnc");
        }
        return null;
    }

    /**
     * 获取设备id
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * @return
     */
    @SuppressLint({"HardwareIds"})
    @RequiresPermission(READ_PHONE_STATE)
    public static String getDeviceId() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager == null) return "";
            return telephonyManager.getDeviceId();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getDeviceId");
        }
        return null;
    }

    /**
     * 返回设备序列化
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * @return
     */
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
     * 获取Android id
     * @return
     */
    public static String getAndroidId() {
        try {
            // 在设备首次启动时，系统会随机生成一个64位的数字，并把这个数字以16进制字符串的形式保存下来，这个16进制的字符串就是ANDROID_ID，当设备被wipe后该值会被重置。
            String androidId = Settings.Secure.getString(DevUtils.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            return androidId;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAndroidId");
        }
        return null;
    }

    /**
     * 获取设备唯一id
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * @return
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getUUID() {
        String androidId = getAndroidId() + "";
        String deviceId = getDeviceId() + "";
        String serialNumber = getSerialNumber() + "";
        // 生成唯一关联uuid  androidId.hashCode(), deviceId.hashCode() | serialNumber.hashCode()
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) deviceId.hashCode() << 32) | serialNumber.hashCode());
        // 获取uid
        return deviceUuid.toString();
    }

    /**
     * 获取手机状态信息
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * <pre>
     *      DeviceId(IMEI) = 99000311726612
     *      DeviceSoftwareVersion = 00
     *      Line1Number =
     *      NetworkCountryIso = cn
     *      NetworkOperator = 46003
     *      NetworkOperatorName = 中国电信
     *      NetworkType = 6
     *      PhoneType = 2
     *      SimCountryIso = cn
     *      SimOperator = 46003
     *      SimOperatorName = 中国电信
     *      SimSerialNumber = 89860315045710604022
     *      SimState = 5
     *      SubscriberId(IMSI) = 460030419724900
     *      VoiceMailNumber = *86
     * </pre>
     * @return
     */
    @SuppressLint({"HardwareIds"})
    @RequiresPermission(READ_PHONE_STATE)
    public static String getPhoneStatus() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager == null) return "";
            StringBuffer buffer = new StringBuffer();
            buffer.append("DeviceId(IMEI) = " + telephonyManager.getDeviceId() + "\n");
            buffer.append("DeviceSoftwareVersion = " + telephonyManager.getDeviceSoftwareVersion() + "\n");
            buffer.append("Line1Number = " + telephonyManager.getLine1Number() + "\n");
            buffer.append("NetworkCountryIso = " + telephonyManager.getNetworkCountryIso() + "\n");
            buffer.append("NetworkOperator = " + telephonyManager.getNetworkOperator() + "\n");
            buffer.append("NetworkOperatorName = " + telephonyManager.getNetworkOperatorName() + "\n");
            buffer.append("NetworkType = " + telephonyManager.getNetworkType() + "\n");
            buffer.append("PhoneType = " + telephonyManager.getPhoneType() + "\n");
            buffer.append("SimCountryIso = " + telephonyManager.getSimCountryIso() + "\n");
            buffer.append("SimOperator = " + telephonyManager.getSimOperator() + "\n");
            buffer.append("SimOperatorName = " + telephonyManager.getSimOperatorName() + "\n");
            buffer.append("SimSerialNumber = " + telephonyManager.getSimSerialNumber() + "\n");
            buffer.append("SimState = " + telephonyManager.getSimState() + "\n");
            buffer.append("SubscriberId(IMSI) = " + telephonyManager.getSubscriberId() + "(" + getIMSIIDName(telephonyManager.getSubscriberId()) + ")\n");
            buffer.append("VoiceMailNumber = " + telephonyManager.getVoiceMailNumber() + "\n");
            return buffer.toString();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getPhoneStatus");
        }
        return "";
    }

    /**
     * 跳至拨号界面
     * @param phoneNumber 电话号码
     * @return
     */
    public static boolean dial(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        if (isIntentAvailable(intent)) {
            DevUtils.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            return true;
        }
        return false;
    }

    /**
     * 拨打电话
     * <uses-permission android:name="android.permission.CALL_PHONE" />
     * @param phoneNumber 电话号码
     * @return
     */
    @RequiresPermission(CALL_PHONE)
    public static boolean call(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        if (isIntentAvailable(intent)) {
            DevUtils.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            return true;
        }
        return false;
    }

    /**
     * 跳至发送短信界面
     * @param phoneNumber 接收号码
     * @param content     短信内容
     * @return
     */
    public static boolean sendSms(final String phoneNumber, final String content) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        if (isIntentAvailable(intent)) {
            intent.putExtra("sms_body", content);
            DevUtils.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            return true;
        }
        return false;
    }

    /**
     * 发送短信
     * <uses-permission android:name="android.permission.SEND_SMS" />
     * @param phoneNumber 接收号码
     * @param content     短信内容
     */
    @RequiresPermission(SEND_SMS)
    public static void sendSmsSilent(final String phoneNumber, final String content) {
        if (TextUtils.isEmpty(content)) return;
        PendingIntent sentIntent = PendingIntent.getBroadcast(DevUtils.getContext(), 0, new Intent("send"), 0);
        SmsManager smsManager = SmsManager.getDefault();
        if (content.length() >= 70) {
            List<String> ms = smsManager.divideMessage(content);
            for (String str : ms) {
                smsManager.sendTextMessage(phoneNumber, null, str, sentIntent, null);
            }
        } else {
            smsManager.sendTextMessage(phoneNumber, null, content, sentIntent, null);
        }
    }

    /**
     * 获取手机联系人
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
     * <uses-permission android:name="android.permission.READ_CONTACTS" />
     * @return
     */
    @RequiresPermission(allOf = {READ_EXTERNAL_STORAGE, READ_CONTACTS})
    public static List<Map<String, String>> getAllContactInfo() {
        List<Map<String, String>> list = new ArrayList<>();
        // 1.获取内容解析者
        ContentResolver resolver = DevUtils.getContext().getContentResolver();
        // 2.获取内容提供者的地址:com.android.contacts
        // raw_contacts 表的地址 :raw_contacts
        // view_data 表的地址 : data
        // 3.生成查询地址
        Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri date_uri = Uri.parse("content://com.android.contacts/data");
        // 4.查询操作,先查询 raw_contacts,查询 contact_id
        // projection : 查询的字段
        Cursor cursor = resolver.query(raw_uri, new String[]{"contact_id"}, null, null, null);
        try {
            // 5.解析 cursor
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 6.获取查询的数据
                    String contact_id = cursor.getString(0);
                    // cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex
                    // : 查询字段在 cursor 中索引值,一般都是用在查询字段比较多的时候
                    // 判断 contact_id 是否为空
                    if (!TextUtils.isEmpty(contact_id)) {//null   ""
                        // 7.根据 contact_id 查询 view_data 表中的数据
                        // selection : 查询条件
                        // selectionArgs :查询条件的参数
                        // sortOrder : 排序
                        // 空指针: 1.null.方法 2.参数为 null
                        Cursor c = resolver.query(date_uri, new String[]{"data1", "mimetype"}, "raw_contact_id=?", new String[]{contact_id}, null);
                        Map<String, String> map = new HashMap<>();
                        // 8.解析 c
                        if (c != null) {
                            while (c.moveToNext()) {
                                // 9.获取数据
                                String data1 = c.getString(0);
                                String mimetype = c.getString(1);
                                // 10.根据类型去判断获取的 data1 数据并保存
                                if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {
                                    map.put("phone", data1); // 电话
                                } else if (mimetype.equals("vnd.android.cursor.item/name")) {
                                    map.put("name", data1); // 姓名
                                }
                            }
                        }
                        // 11.添加到集合中数据
                        list.add(map);
                        // 12.关闭 cursor
                        if (c != null) {
                            c.close();
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAllContactInfo");
        } finally {
            // 12.关闭 cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    /**
     * 获取手机联系人
     * @return
     */
    public static List<Map<String, String>> getAllContactInfo2() {
        List<Map<String, String>> list = new ArrayList<>();
        try {
            Cursor cursor = DevUtils.getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (cursor.moveToNext()) {
                Map<String, String> map = new HashMap<>();
                // 电话号码
                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).trim().replaceAll(" ", "");
                // 手机联系人名字
                String phoneName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)).trim();
                // 保存手机号
                map.put("phone", phoneNumber);
                // 保存名字
                map.put("name", phoneName);
                // =
                list.add(map);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAllContactInfo2");
        }
        // 返回数据
        return list;
    }

    /**
     * 打开手机联系人界面点击联系人后便获取该号码
     * @param activity
     */
    public static void getContactNum(final Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.PICK");
            intent.setType("vnd.android.cursor.dir/phone_v2");
            activity.startActivityForResult(intent, 0);
        }

//        @Override
//        protected void onActivityResult (int requestCode, int resultCode, Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//            if (data != null) {
//                Uri uri = data.getData();
//                String num = null;
//                // 创建内容解析者
//                ContentResolver contentResolver = getContentResolver();
//                Cursor cursor = contentResolver.query(uri,
//                        null, null, null, null);
//                while (cursor.moveToNext()) {
//                    num = cursor.getString(cursor.getColumnIndex("data1"));
//                }
//                cursor.close();
//                num = num.replaceAll("-", "");//替换的操作,555-6 -> 5556
//            }
//        }
    }

    /**
     * 获取手机短信并保存到 xml 中
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
     * <uses-permission android:name="android.permission.READ_SMS" />
     */
    @RequiresPermission(allOf = {WRITE_EXTERNAL_STORAGE, READ_SMS})
    public static void getAllSMS() {
        // 1.获取短信
        // 1.1获取内容解析者
        ContentResolver resolver = DevUtils.getContext().getContentResolver();
        // 1.2获取内容提供者地址   sms,sms表的地址:null  不写
        // 1.3获取查询路径
        Uri uri = Uri.parse("content://sms");
        // 1.4.查询操作
        // projection : 查询的字段
        // selection : 查询的条件
        // selectionArgs : 查询条件的参数
        // sortOrder : 排序
        Cursor cursor = resolver.query(uri,
                new String[]{"address", "date", "type", "body"},
                null,
                null,
                null);
        // 设置最大进度
        int count = cursor.getCount();//获取短信的个数
        // 2.备份短信
        // 2.1获取xml序列器
        XmlSerializer xmlSerializer = Xml.newSerializer();
        try {
            // 2.2设置xml文件保存的路径
            // os : 保存的位置
            // encoding : 编码格式
            xmlSerializer.setOutput(new FileOutputStream(new File("/mnt/sdcard/backupsms.xml")), "utf-8");
            // 2.3设置头信息
            // standalone : 是否独立保存
            xmlSerializer.startDocument("utf-8", true);
            // 2.4设置根标签
            xmlSerializer.startTag(null, "smss");
            // 1.5.解析cursor
            while (cursor.moveToNext()) {
                SystemClock.sleep(1000);
                // 2.5设置短信的标签
                xmlSerializer.startTag(null, "sms");
                // 2.6设置文本内容的标签
                xmlSerializer.startTag(null, "address");
                String address = cursor.getString(0);
                // 2.7设置文本内容
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
            // 2.8将数据刷新到文件中
            xmlSerializer.flush();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getAllSMS");
        }
    }

    // ============
    // = 双卡模块 =
    // ============

    /**
     * 双卡双待神机IMSI、IMSI、PhoneType信息
     * <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     */
    public static class TeleInfo {
        public String imsi_1;
        public String imsi_2;
        public String imei_1;
        public String imei_2;
        public int phoneType_1;
        public int phoneType_2;

        /**
         * 打印 TeleInfo 信息
         * @return
         */
        public String printInfo() {
            return "TeleInfo {" +
                    "imsi_1='" + imsi_1 + '\'' +
                    ", imsi_2='" + imsi_2 + '\'' +
                    ", imei_1='" + imei_1 + '\'' +
                    ", imei_2='" + imei_2 + '\'' +
                    ", phoneType_1=" + phoneType_1 +
                    ", phoneType_2=" + phoneType_2 +
                    '}';
        }
    }

    /**
     * 获取 MTK 神机的双卡 IMSI、IMSI 信息
     * @return
     */
    public static TeleInfo getMtkTeleInfo() {
        TeleInfo teleInfo = new TeleInfo();
        try {
            Class<?> phone = Class.forName("com.android.internal.telephony.Phone");

            Field fields1 = phone.getField("GEMINI_SIM_1");
            fields1.setAccessible(true);
            int simId_1 = (Integer) fields1.get(null);

            Field fields2 = phone.getField("GEMINI_SIM_2");
            fields2.setAccessible(true);
            int simId_2 = (Integer) fields2.get(null);

            TelephonyManager tm = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            Method getSubscriberIdGemini = TelephonyManager.class.getDeclaredMethod("getSubscriberIdGemini", int.class);
            String imsi_1 = (String) getSubscriberIdGemini.invoke(tm, simId_1);
            String imsi_2 = (String) getSubscriberIdGemini.invoke(tm, simId_2);
            teleInfo.imsi_1 = imsi_1;
            teleInfo.imsi_2 = imsi_2;

            Method getDeviceIdGemini = TelephonyManager.class.getDeclaredMethod("getDeviceIdGemini", int.class);
            String imei_1 = (String) getDeviceIdGemini.invoke(tm, simId_1);
            String imei_2 = (String) getDeviceIdGemini.invoke(tm, simId_2);

            teleInfo.imei_1 = imei_1;
            teleInfo.imei_2 = imei_2;

            Method getPhoneTypeGemini = TelephonyManager.class.getDeclaredMethod("getPhoneTypeGemini", int.class);
            int phoneType_1 = (Integer) getPhoneTypeGemini.invoke(tm, simId_1);
            int phoneType_2 = (Integer) getPhoneTypeGemini.invoke(tm, simId_2);
            teleInfo.phoneType_1 = phoneType_1;
            teleInfo.phoneType_2 = phoneType_2;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMtkTeleInfo");
        }
        return teleInfo;
    }

    /**
     * 获取 MTK 神机的双卡 IMSI、IMSI 信息
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * @return
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static TeleInfo getMtkTeleInfo2() {
        TeleInfo teleInfo = new TeleInfo();
        try {
            TelephonyManager tm = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> phone = Class.forName("com.android.internal.telephony.Phone");
            Field fields1 = phone.getField("GEMINI_SIM_1");
            fields1.setAccessible(true);
            int simId_1 = (Integer) fields1.get(null);
            Field fields2 = phone.getField("GEMINI_SIM_2");
            fields2.setAccessible(true);
            int simId_2 = (Integer) fields2.get(null);

            Method getDefault = TelephonyManager.class.getMethod("getDefault", int.class);
            TelephonyManager tm1 = (TelephonyManager) getDefault.invoke(tm, simId_1);
            TelephonyManager tm2 = (TelephonyManager) getDefault.invoke(tm, simId_2);

            String imsi_1 = tm1.getSubscriberId();
            String imsi_2 = tm2.getSubscriberId();
            teleInfo.imsi_1 = imsi_1;
            teleInfo.imsi_2 = imsi_2;

            String imei_1 = tm1.getDeviceId();
            String imei_2 = tm2.getDeviceId();
            teleInfo.imei_1 = imei_1;
            teleInfo.imei_2 = imei_2;

            int phoneType_1 = tm1.getPhoneType();
            int phoneType_2 = tm2.getPhoneType();
            teleInfo.phoneType_1 = phoneType_1;
            teleInfo.phoneType_2 = phoneType_2;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getMtkTeleInfo2");
        }
        return teleInfo;
    }

    /**
     * 获取 高通 神机的双卡 IMSI、IMSI 信息
     * @return
     */
    public static TeleInfo getQualcommTeleInfo() {
        TeleInfo teleInfo = new TeleInfo();
        try {
            TelephonyManager tm = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> simTMclass = Class.forName("android.telephony.MSimTelephonyManager");
            Object sim = DevUtils.getContext().getSystemService("phone_msim");
            int simId_1 = 0;
            int simId_2 = 1;

            Method getSubscriberId = simTMclass.getMethod("getSubscriberId", int.class);
            String imsi_1 = (String) getSubscriberId.invoke(sim, simId_1);
            String imsi_2 = (String) getSubscriberId.invoke(sim, simId_2);
            teleInfo.imsi_1 = imsi_1;
            teleInfo.imsi_2 = imsi_2;

            Method getDeviceId = simTMclass.getMethod("getDeviceId", int.class);
            String imei_1 = (String) getDeviceId.invoke(sim, simId_1);
            String imei_2 = (String) getDeviceId.invoke(sim, simId_2);
            teleInfo.imei_1 = imei_1;
            teleInfo.imei_2 = imei_2;

            Method getDataState = simTMclass.getMethod("getDataState");
            int phoneType_1 = tm.getDataState();
            int phoneType_2 = (Integer) getDataState.invoke(sim);
            teleInfo.phoneType_1 = phoneType_1;
            teleInfo.phoneType_2 = phoneType_2;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getQualcommTeleInfo");
        }
        return teleInfo;
    }

    /**
     * 获取 展讯 神机的双卡 IMSI、IMSI 信息
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * @return
     */
    @RequiresPermission(Manifest.permission.READ_PHONE_STATE)
    public static TeleInfo getSpreadtrumTeleInfo() {
        TeleInfo teleInfo = new TeleInfo();
        try {
            TelephonyManager tm1 = (TelephonyManager) DevUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            String imsi_1 = tm1.getSubscriberId();
            String imei_1 = tm1.getDeviceId();
            int phoneType_1 = tm1.getPhoneType();
            teleInfo.imsi_1 = imsi_1;
            teleInfo.imei_1 = imei_1;
            teleInfo.phoneType_1 = phoneType_1;

            Class<?> phoneFactory = Class.forName("com.android.internal.telephony.PhoneFactory");
            Method getServiceName = phoneFactory.getMethod("getServiceName", String.class, int.class);
            getServiceName.setAccessible(true);
            String spreadTmService = (String) getServiceName.invoke(phoneFactory, Context.TELEPHONY_SERVICE, 1);

            TelephonyManager tm2 = (TelephonyManager) DevUtils.getContext().getSystemService(spreadTmService);
            String imsi_2 = tm2.getSubscriberId();
            String imei_2 = tm2.getDeviceId();
            int phoneType_2 = tm2.getPhoneType();
            teleInfo.imsi_2 = imsi_2;
            teleInfo.imei_2 = imei_2;
            teleInfo.phoneType_2 = phoneType_2;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSpreadtrumTeleInfo");
        }
        return teleInfo;
    }

    // =

    /**
     * 判断 Intent 是否可用
     * @param intent
     * @return
     */
    private static boolean isIntentAvailable(final Intent intent) {
        if (intent == null) return false;
        try {
            return DevUtils.getContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isIntentAvailable");
        }
        return false;
    }
}
