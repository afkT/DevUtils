package dev.utils.common.validator;

import java.util.regex.Pattern;

/**
 * detail: 检验身份证工具类
 * Created by Ttt
 */
public final class ValiToIDCardUtils {

    private ValiToIDCardUtils() {
    }

    /** 正则表达式:验证身份证 */
    private static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";

    /** 正则表达式:验证台湾 */
    private static final String REGEX_TW_ID_CARD = "/^[a-zA-Z][0-9]{9}$/";

    /** 正则表达式:验证香港 */
    private static final String REGEX_XG_ID_CARD = "[A-Z][0-9]{6}\\([0-9A]\\)";

    /** 正则表达式:验证澳门 */
    private static final String REGEX_AM_ID_CARD = "[157][0-9]{6}\\([0-9]\\)";

    // ==== 内部方法 =====

    /**
     * 判断是否为null
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 通用匹配函数
     * @param regex
     * @param input
     * @return
     */
    private static boolean match(String regex, String input) {
        return Pattern.matches(regex, input);
    }

    // ==

    /**
     * 校验身份证
     * @param idCard
     * @return
     */
    public static boolean isIDCard(String idCard) {
        if (!isEmpty(idCard)) {
            return match(REGEX_ID_CARD, idCard);
        }
        return false;
    }

    /**
     * 校验身份证 -> 香港
     * @param idCard
     * @return
     */
    public static boolean isHKIDCard(String idCard) {
        if (!isEmpty(idCard)) {
            return match(REGEX_XG_ID_CARD, idCard);
        }
        return false;
    }

    /**
     * 校验身份证 -> 澳门
     * @param idCard
     * @return
     */
    public static boolean isAMIDCard(String idCard) {
        if (!isEmpty(idCard)) {
            return match(REGEX_AM_ID_CARD, idCard);
        }
        return false;
    }

    /**
     * 校验身份证 -> 台湾
     * @param idCard
     * @return
     */
    public static boolean isTWIDCard(String idCard) {
        if (!isEmpty(idCard)) {
            return match(REGEX_TW_ID_CARD, idCard);
        }
        return false;
    }

}
