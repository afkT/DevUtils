package dev.utils.common.validator;

import java.util.regex.Pattern;

import dev.utils.JCLogUtils;

/**
 * detail: 校验工具类
 * @author Ttt
 */
public final class ValidatorUtils {

    private ValidatorUtils() {
    }

    // 日志 TAG
    private static final String TAG = ValidatorUtils.class.getSimpleName();

    // 正则表达式: 空格
    public static final String REGEX_SPACE = "\\s";

    // 正则表达式: 验证数字
    public static final String REGEX_NUMBER = "^[0-9]*$";

    // 正则表达式: 验证数字或包含小数点
    private static final String REGEX_NUMBER_OR_DECIMAL = "^[0-9]*[.]?[0-9]*$";

    // 正则表达式: 验证是否包含数字
    public static final String REGEX_CONTAIN_NUMBER = ".*\\d+.*";

    // 正则表达式: 验证是否数字或者字母
    public static final String REGEX_NUMBER_OR_LETTER = "^[A-Za-z0-9]+$";

    // 正则表达式: 验证是否全是字母
    public static final String REGEX_LETTER = "^[A-Za-z]+$";

    // 正则表达式: 不能输入特殊字符   ^[\u4E00-\u9FA5A-Za-z0-9]+$ 或 ^[\u4E00-\u9FA5A-Za-z0-9]{2,20}$
    public static final String REGEX_SPECIAL = "^[\\u4E00-\\u9FA5A-Za-z0-9]+$";

    // 正则表达式: 验证微信号  ^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}+$
    public static final String REGEX_WX = "^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}+$";

    // 正则表达式: 验证真实姓名  ^[\u4e00-\u9fa5]+(·[\u4e00-\u9fa5]+)*$
    public static final String REGEX_REALNAME = "^[\\u4e00-\\u9fa5]+(•[\\u4e00-\\u9fa5]*)*$|^[\\u4e00-\\u9fa5]+(·[\\u4e00-\\u9fa5]*)*$";

    // 正则表达式: 验证昵称
    public static final String REGEX_NICKNAME = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$";

    // 正则表达式: 验证用户名(不包含中文和特殊字符)如果用户名使用手机号码或邮箱 则结合手机号验证和邮箱验证
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    // 正则表达式: 验证密码(不包含特殊字符)
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,18}$";

    // 正则表达式: 验证邮箱
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    // 正则表达式: 验证URL
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?";

    // 正则表达式: 验证IP地址
    public static final String REGEX_IP_ADDR = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";

//    // 正则表达式: 验证IP地址
//    public static final String REGEX_IP_ADDR2 = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";

    /**
     * 判断是否为 null
     * @param str 待校验的字符串
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 通用匹配函数
     * @param regex 正则表达式
     * @param input 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean match(final String regex, final String input) {
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
     * 检验数字
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNumber(final String str) {
        return match(REGEX_NUMBER, str);
    }

    /**
     * 检验数字或包含小数点
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNumberDecimal(final String str) {
        return match(REGEX_NUMBER_OR_DECIMAL, str);
    }

    /**
     * 判断字符串是不是全是字母
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLetter(final String str) {
        return match(REGEX_LETTER, str);
    }

    /**
     * 判断字符串是不是包含数字
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isContainNumber(final String str) {
        return match(REGEX_CONTAIN_NUMBER, str);
    }

    /**
     * 判断字符串是不是只含字母和数字
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNumberLetter(final String str) {
        return match(REGEX_NUMBER_OR_LETTER, str);
    }

    /**
     * 检验特殊符号
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSpec(final String str) {
        return match(REGEX_SPECIAL, str);
    }

    /**
     * 检验微信号
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isWx(final String str) {
        return match(REGEX_WX, str);
    }

    /**
     * 检验真实姓名
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isRealName(final String str) {
        return match(REGEX_REALNAME, str);
    }

    /**
     * 校验昵称
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNickName(final String str) {
        return match(REGEX_NICKNAME, str);
    }

    /**
     * 校验用户名
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isUserName(final String str) {
        return match(REGEX_USERNAME, str);
    }

    /**
     * 校验密码
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPassword(final String str) {
        return match(REGEX_PASSWORD, str);
    }

    /**
     * 校验邮箱
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmail(final String str) {
        return match(REGEX_EMAIL, str);
    }

    /**
     * 校验URL
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isUrl(final String str) {
        if (!isEmpty(str)) {
            return match(REGEX_URL, str.toLowerCase());
        }
        return false;
    }

    /**
     * 校验IP地址
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isIPAddress(final String str) {
        return match(REGEX_IP_ADDR, str);
    }

    // =

//     // http://blog.csdn.net/myfuturein/article/details/6885216
//    [\\u0391-\\uFFE5]匹配双字节字符(汉字+符号)
//    [\\u4e00-\\u9fa5]注意只匹配汉字，不匹配双字节字符

    // 正则表达式: 验证汉字
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]+$";
    // 正则表达式: 验证汉字(含双角符号)
    public static final String REGEX_CHINESE_ALL = "^[\u0391-\uFFE5]+$";
    // 正则表达式: 验证汉字(含双角符号)
    public static final String REGEX_CHINESE_ALL2 = "[\u0391-\uFFE5]";

    /**
     * 校验汉字(无符号,纯汉字)
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isChinese(final String str) {
        return match(REGEX_CHINESE, str);
    }

    /**
     * 判断字符串是不是全是中文
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isChineseAll(final String str) {
        return match(REGEX_CHINESE_ALL, str);
    }

    /**
     * 判断字符串中包含中文、包括中文字符标点等
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isContainChinese(final String str) {
        if (!isEmpty(str)) {
            try {
                int length;
                if (str != null && (length = str.length()) != 0) {
                    char[] dChar = str.toCharArray();
                    for (int i = 0; i < length; i++) {
                        boolean flag = String.valueOf(dChar[i]).matches(REGEX_CHINESE_ALL2);
                        if (flag) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "isContainChinese");
            }
        }
        return false;
    }
}
