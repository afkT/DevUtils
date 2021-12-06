package dev.utils.common.validator;

import java.util.regex.Pattern;

import dev.utils.DevFinal;
import dev.utils.JCLogUtils;
import dev.utils.common.StringUtils;

/**
 * detail: 校验工具类
 * @author Ttt
 */
public final class ValidatorUtils {

    private ValidatorUtils() {
    }

    // 日志 TAG
    private static final String TAG = ValidatorUtils.class.getSimpleName();

    /**
     * 通用匹配函数
     * @param regex 正则表达式
     * @param input 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean match(
            final String regex,
            final String input
    ) {
        if (!StringUtils.isEmpty(input)) {
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
        return match(DevFinal.REGEX.NUMBER, str);
    }

    /**
     * 检验数字或包含小数点
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNumberDecimal(final String str) {
        return match(DevFinal.REGEX.NUMBER_OR_DECIMAL, str);
    }

    /**
     * 判断字符串是不是全是字母
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLetter(final String str) {
        return match(DevFinal.REGEX.LETTER, str);
    }

    /**
     * 判断字符串是不是包含数字
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isContainNumber(final String str) {
        return match(DevFinal.REGEX.CONTAIN_NUMBER, str);
    }

    /**
     * 判断字符串是不是只含字母和数字
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNumberLetter(final String str) {
        return match(DevFinal.REGEX.NUMBER_OR_LETTER, str);
    }

    /**
     * 检验特殊符号
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSpec(final String str) {
        return match(DevFinal.REGEX.SPECIAL, str);
    }

    /**
     * 检验微信号
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isWx(final String str) {
        return match(DevFinal.REGEX.WX, str);
    }

    /**
     * 检验真实姓名
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isRealName(final String str) {
        return match(DevFinal.REGEX.REALNAME, str);
    }

    /**
     * 校验昵称
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNickName(final String str) {
        return match(DevFinal.REGEX.NICKNAME, str);
    }

    /**
     * 校验用户名
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isUserName(final String str) {
        return match(DevFinal.REGEX.USERNAME, str);
    }

    /**
     * 校验密码
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isPassword(final String str) {
        return match(DevFinal.REGEX.PASSWORD, str);
    }

    /**
     * 校验邮箱
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmail(final String str) {
        return match(DevFinal.REGEX.EMAIL, str);
    }

    /**
     * 校验 URL
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isUrl(final String str) {
        if (!StringUtils.isEmpty(str)) {
            return match(DevFinal.REGEX.URL, str.toLowerCase());
        }
        return false;
    }

    /**
     * 校验 IP 地址
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isIPAddress(final String str) {
        return match(DevFinal.REGEX.IP_ADDRESS, str);
    }

    /**
     * 校验汉字 ( 无符号, 纯汉字 )
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isChinese(final String str) {
        return match(DevFinal.REGEX.CHINESE, str);
    }

    /**
     * 判断字符串是不是全是中文
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isChineseAll(final String str) {
        return match(DevFinal.REGEX.CHINESE_ALL, str);
    }

    /**
     * 判断字符串中包含中文、包括中文字符标点等
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isContainChinese(final String str) {
        if (!StringUtils.isEmpty(str)) {
            try {
                int length = str.length();
                if (length != 0) {
                    char[] dChar = str.toCharArray();
                    for (int i = 0; i < length; i++) {
                        boolean flag = String.valueOf(dChar[i]).matches(DevFinal.REGEX.CHINESE_ALL2);
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