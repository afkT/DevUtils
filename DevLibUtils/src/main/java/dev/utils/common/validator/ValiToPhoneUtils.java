package dev.utils.common.validator;

import java.util.regex.Pattern;

import dev.utils.JCLogUtils;

/**
 * detail: 检验联系(手机号,座机)工具类
 * @author Ttt
 * @see <a href="http://blog.csdn.net/linbilin_/article/details/49796617"/>
 * @see <a href="http://www.cnblogs.com/zengxiangzhan/p/phone.html"/>
 */
public final class ValiToPhoneUtils {

    private ValiToPhoneUtils() {
    }

    // 日志 TAG
    private static final String TAG = ValiToPhoneUtils.class.getSimpleName();

    /**
     * 判断是否为 null
     * @param str 待校验的字符串
     * @return {@code true} is null, {@code false} not null
     */
    private static boolean isEmpty(final String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 通用匹配函数
     * @param regex 正则表达式
     * @param input 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    private static boolean match(final String regex, final String input) {
        if (!isEmpty(input)) {
            try {
                return Pattern.matches(regex, input);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "match");
            }
        }
        return false;
    }

    // =

    /**
     * 中国手机号格式验证,在输入可以调用该方法,点击发送验证码,使用 isPhone
     * @param phone 待校验的手机号
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneCheck(final String phone) {
        return match(CHAIN_PHONE_FORMAT_CHECK, phone);
    }

    /**
     * 是否中国手机号
     * @param phone 待校验的手机号
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhone(final String phone) {
        return match(CHINA_PHONE_PATTERN, phone);
    }

    /**
     * 是否中国电信手机号码
     * @param phone 待校验的手机号
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneToChinaTelecom(final String phone) {
        return match(CHINA_TELECOM_PATTERN, phone);
    }

    /**
     * 是否中国联通手机号码
     * @param phone 待校验的手机号
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneToChinaUnicom(final String phone) {
        return match(CHINA_UNICOM_PATTERN, phone);
    }

    /**
     * 是否中国移动手机号码
     * @param phone 待校验的手机号
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneToChinaMobile(final String phone) {
        return match(CHINA_MOBILE_PATTERN, phone);
    }

    /**
     * 判断是否香港手机号
     * @param phone 待校验的手机号
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneToHkMobile(final String phone) {
        return match(HK_PHONE_PATTERN, phone);
    }

    /**
     * 验证电话号码的格式
     * @param phone 待校验的号码
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPhoneCallNum(final String phone) {
        return match(PHONE_CALL_PATTERN, phone);
    }

    // ==============
    // = 手机号判断 =
    // ==============

    // 简单手机号码校验 => 校验手机号码的长度和1开头 (是否11位)
    public static final String CHAIN_PHONE_FORMAT_CHECK = "^(?:\\+86)?1\\d{10}$";

    // 中国手机号正则
    public static final String CHINA_PHONE_PATTERN;

    // 中国电信号码正则
    public static final String CHINA_TELECOM_PATTERN;

    // 中国联通号码正则
    public static final String CHINA_UNICOM_PATTERN;

    // 中国移动号码正则
    public static final String CHINA_MOBILE_PATTERN;

    // 香港手机号码正则 => 香港手机号码8位数，5|6|8|9开头+7位任意数
    public static final String HK_PHONE_PATTERN = "^(5|6|8|9)\\d{7}$";

    // ============
    // = 座机判断 =
    // ============

    // 座机电话格式验证
    public static final String PHONE_CALL_PATTERN = "^(?:\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}(?:-\\d{1,4})?$";

    static {

        // ============
        // = 中国电信 =
        // ============

        // 电信: 133、153、180、181、189 、177(4G)、149、173、174、199
        // 进行拼接字符串,便于理解,后期修改
        StringBuffer buffer = new StringBuffer();
        buffer.append("^13[3]{1}\\d{8}$"); // 13开头
        buffer.append("|"); // 或
        buffer.append("^14[9]{1}\\d{8}$"); // 14开头
        buffer.append("|");
        buffer.append("^15[3]{1}\\d{8}$"); // 15开头
        buffer.append("|");
        buffer.append("^17[3,4,7]{1}\\d{8}$"); // 17开头
        buffer.append("|");
        buffer.append("^18[0,1,9]{1}\\d{8}$"); // 18开头
        buffer.append("|");
        buffer.append("^19[9]{1}\\d{8}$"); // 19开头
        // 手机正则
        CHINA_TELECOM_PATTERN = buffer.toString();

        // ============
        // = 中国联通 =
        // ============

        // 联通: 130、131、132、155、156、185、186、176(4G)、145(上网卡)、146、166、171、175
        // 进行拼接字符串,便于理解,后期修改
        buffer = new StringBuffer();
        buffer.append("^13[0,1,2]{1}\\d{8}$"); // 13开头
        buffer.append("|"); // 或
        buffer.append("^14[5,6]{1}\\d{8}$"); // 14开头
        buffer.append("|");
        buffer.append("^15[5,6]{1}\\d{8}$"); // 15开头
        buffer.append("|");
        buffer.append("^16[6]{1}\\d{8}$"); // 16开头
        buffer.append("|");
        buffer.append("^17[1,5,6]{1}\\d{8}$"); // 17开头
        buffer.append("|");
        buffer.append("^18[5,6]{1}\\d{8}$"); // 18开头
        // 手机正则
        CHINA_UNICOM_PATTERN = buffer.toString();

        // ============
        // = 中国移动 =
        // ============

        // 移动: 134、135、136、137、138、139、150、151、152、157、158、159、182、183、184、187、188、178(4G)、147(上网卡)、148、172、198
        // 进行拼接字符串,便于理解,后期修改
        buffer = new StringBuffer();
        buffer.append("^13[4,5,6,7,8,9]{1}\\d{8}$"); // 13开头
        buffer.append("|"); // 或
        buffer.append("^14[7,8]{1}\\d{8}$"); // 14开头
        buffer.append("|");
        buffer.append("^15[0,1,2,7,8,9]{1}\\d{8}$"); // 15开头
        buffer.append("|");
        buffer.append("^17[2,8]{1}\\d{8}$"); // 17开头
        buffer.append("|");
        buffer.append("^18[2,3,4,7,8]{1}\\d{8}$"); // 18开头
        buffer.append("|");
        buffer.append("^19[8]{1}\\d{8}$"); // 19开头
        // 手机正则
        CHINA_MOBILE_PATTERN = buffer.toString();


        /**
         * 验证手机号是否正确
         * 移动: 134、135、136、137、138、139、150、151、152、157、158、159、182、183、184、187、188、178(4G)、147(上网卡)、148、172、198
         * 联通: 130、131、132、155、156、185、186、176(4G)、145(上网卡)、146、166、171、175
         * 电信: 133、153、180、181、189 、177(4G)、149、173、174、199
         * 卫星通信: 1349
         * 虚拟运营商: 170
         * @see <a href="http://www.cnblogs.com/zengxiangzhan/p/phone.html"/>
         */
        CHINA_PHONE_PATTERN = "^13[\\d]{9}$|^14[5,6,7,8,9]{1}\\d{8}$|^15[^4]{1}\\d{8}$|^16[6]{1}\\d{8}$|^17[0,1,2,3,4,5,6,7,8]{1}\\d{8}$|^18[\\d]{9}$|^19[8,9]{1}\\d{8}$";
    }
}
