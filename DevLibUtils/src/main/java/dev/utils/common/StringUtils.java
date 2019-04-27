package dev.utils.common;

import java.net.URLEncoder;

import dev.utils.JCLogUtils;

/**
 * detail: 字符串工具类
 * @author Ttt
 */
public final class StringUtils {

    private StringUtils() {
    }

    // 日志 TAG
    private static final String TAG = StringUtils.class.getSimpleName();
    // 换行字符串
    public static final String NEW_LINE_STR = System.getProperty("line.separator");
    // 换行字符串 - 两行
    public static final String NEW_LINE_STR_X2 = NEW_LINE_STR + NEW_LINE_STR;

    // ==========
    // = String =
    // ==========

    /**
     * 判断字符串是否为 null
     * @param str 待校验的字符串
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 判断字符串是否为 null
     * @param str    待校验的字符串
     * @param isTrim 是否调用 trim()
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final String str, final boolean isTrim) {
        if (str != null) {
            return isEmpty(isTrim ? str.trim() : str);
        }
        return false;
    }

    /**
     * 判断多个字符串是否存在为 null 的字符串
     * @param strs 待校验的字符串数组
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final String... strs) {
        if (strs != null && strs.length != 0) {
            for (int i = 0, len = strs.length; i < len; i++) {
                if (isEmpty(strs[i])) {
                    return true;
                }
            }
            return false;
        }
        // 默认表示属于null
        return true;
    }

    // =

    /**
     * 判断字符串是否不为 null
     * @param str 待校验的字符串
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final String str) {
        return (str != null && str.length() != 0);
    }

    /**
     * 判断字符串是否不为 null
     * @param str    待校验的字符串
     * @param isTrim 是否调用 trim()
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final String str, final boolean isTrim) {
        return isNotEmpty(isTrim ? str.trim() : str);
    }

    // ============
    // = 判断长度 =
    // ============

    /**
     * 获取字符串长度
     * @param str 待校验的字符串
     * @return 字符串长度, 如果字符串为 null,则返回 0
     */
    public static int length(final String str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 获取字符串长度
     * @param str           待校验的字符串
     * @param defaultLength 字符串为null时, 返回的长度
     * @return 字符串长度, 如果字符串为 null,则返回 defaultLength
     */
    public static int length(final String str, final int defaultLength) {
        return str != null ? str.length() : defaultLength;
    }

    // =

    /**
     * 获取字符串长度 是否等于期望长度
     * @param str    待校验的字符串
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final String str, final int length) {
        return str != null && str.length() == length;
    }

    // ============
    // = 对比判断 =
    // ============

    /**
     * 判断两个值是否一样
     * @param value1 第一个值
     * @param value2 第二个值
     * @param <T>    泛型
     * @return {@code true} yes, {@code false} no
     */
    public static <T> boolean equals(final T value1, final T value2) {
        // 两个值都不为 null
        if (value1 != null && value2 != null) {
            try {
                if (value1 instanceof String && value2 instanceof String) {
                    return value1.equals(value2);
                } else if (value1 instanceof CharSequence && value2 instanceof CharSequence) {
                    CharSequence v1 = (CharSequence) value1;
                    CharSequence v2 = (CharSequence) value2;
                    // 获取数据长度
                    int length = v1.length();
                    // 判断数据长度是否一致
                    if (length == v2.length()) {
                        for (int i = 0; i < length; i++) {
                            if (v1.charAt(i) != v2.charAt(i)) {
                                return false;
                            }
                        }
                        return true;
                    }
                    return false;
                }
                // 其他都使用 equals 判断
                return value1.equals(value2);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "equals");
            }
            return false;
        }
        // 防止两个值都为null
        return (value1 == null && value2 == null);
    }

    /**
     * 判断多个字符串是否相等, 只有全相等才返回 true - 对比大小写
     * @param strs 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEquals(final String... strs) {
        return isEquals(false, strs);
    }

    /**
     * 判断多个字符串是否相等, 只有全相等才返回 true
     * @param isIgnore 是否忽略大小写
     * @param strs     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEquals(final boolean isIgnore, final String... strs) {
        if (strs != null) {
            String last = null;
            // 获取数据长度
            int len = strs.length;
            // 如果最多只有一个数据判断, 则直接跳过
            if (len <= 1) {
                return false;
            }
            // 遍历判断
            for (int i = 0; i < len; i++) {
                // 获取临时变量
                String val = strs[i];
                // 如果等于null,则跳过
                if (val == null) {
                    return false;
                }
                if (last != null) {
                    if (isIgnore) {
                        if (!val.equalsIgnoreCase(last)) {
                            return false;
                        }
                    } else {
                        if (!val.equals(last)) {
                            return false;
                        }
                    }
                }
                last = val;
            }
            return true;
        }
        return false;
    }

    /**
     * 判断多个字符串,只要有一个符合条件,则通过
     * @param str  待校验的字符串
     * @param strs 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isOrEquals(final String str, final String... strs) {
        return isOrEquals(false, str, strs);
    }

    /**
     * 判断多个字符串,只要有一个符合条件,则通过
     * @param isIgnore 是否忽略大小写
     * @param str      待校验的字符串
     * @param strs     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isOrEquals(final boolean isIgnore, final String str, final String... strs) {
        if (str != null && strs != null && strs.length != 0) {
            // 获取数据长度
            int len = strs.length;
            // 遍历判断
            for (int i = 0; i < len; i++) {
                // 获取临时变量
                String val = strs[i];
                // 如果等于null,则跳过
                if (val == null) {
                    continue;
                } else {
                    if (isIgnore) {
                        if (val.equalsIgnoreCase(str)) {
                            return true;
                        }
                    } else {
                        if (val.equals(str)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断一堆值中，是否存在符合该条件的(包含)
     * @param str  待校验的字符串
     * @param strs 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isContains(final String str, final String... strs) {
        return isContains(false, str, strs);
    }

    /**
     * 判断一堆值中，是否存在符合该条件的(包含)
     * @param isIgnore 是否忽略大小写
     * @param str      待校验的字符串
     * @param strs     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isContains(final boolean isIgnore, final String str, final String... strs) {
        if (str != null && strs != null && strs.length != 0) {
            String strTemp = str;
            // 判断是否需要忽略大小写
            if (isIgnore) {
                strTemp = strTemp.toLowerCase();
            }
            // 获取内容长度
            int cLength = strTemp.length();
            // 遍历判断
            for (int i = 0, len = strs.length; i < len; i++) {
                // 获取参数
                String val = strs[i];
                // 判断是否为 null,或者长度为0
                if (!isEmpty(val) && cLength != 0) {
                    if (isIgnore) {
                        // 转换小写
                        String valIgnore = val.toLowerCase();
                        // 判断是否包含
                        if (valIgnore.indexOf(strTemp) != -1) {
                            return true;
                        }
                    } else {
                        // 判断是否包含
                        if (val.indexOf(strTemp) != -1) {
                            return true;
                        }
                    }
                } else {
                    // 下面这一串可以不要,因为判断字符串是否包含
                    // 已经处理了值不为null,并且需要判断的值长度不能为0,下面则不需要加上
                    if (strTemp.equals(val)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断内容, 是否属于特定字符串开头 - 对比大小写
     * @param str  待校验的字符串
     * @param strs 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isStartsWith(final String str, final String... strs) {
        return isStartsWith(false, str, strs);
    }

    /**
     * 判断内容, 是否属于特定字符串开头
     * @param isIgnore 是否忽略大小写
     * @param str      待校验的字符串
     * @param strs     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isStartsWith(final boolean isIgnore, final String str, final String... strs) {
        if (!isEmpty(str) && strs != null && strs.length != 0) {
            String strTemp = str;
            // 判断是否需要忽略大小写
            if (isIgnore) {
                strTemp = strTemp.toLowerCase();
            }
            // 获取数据长度
            int len = strs.length;
            // 遍历判断
            for (int i = 0; i < len; i++) {
                // 获取临时变量
                String val = strs[i];
                // 判断是否为 null,或者长度为0
                if (!isEmpty(val)) {
                    if (isIgnore) {
                        // 转换小写
                        String valIgnore = val.toLowerCase();
                        // 判断是否属于 val 开头
                        if (strTemp.startsWith(valIgnore)) {
                            return true;
                        }
                    } else {
                        // 判断是否属于 val 开头
                        if (strTemp.startsWith(val)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断内容, 是否属于特定字符串结尾 - 对比大小写
     * @param str  待校验的字符串
     * @param strs 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEndsWith(final String str, final String... strs) {
        return isEndsWith(false, str, strs);
    }

    /**
     * 判断内容, 是否属于特定字符串结尾
     * @param isIgnore 是否忽略大小写
     * @param str      待校验的字符串
     * @param strs     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEndsWith(final boolean isIgnore, final String str, final String... strs) {
        if (!isEmpty(str) && strs != null && strs.length != 0) {
            String strTemp = str;
            // 判断是否需要忽略大小写
            if (isIgnore) {
                strTemp = strTemp.toLowerCase();
            }
            // 获取数据长度
            int len = strs.length;
            // 遍历判断
            for (int i = 0; i < len; i++) {
                // 获取临时变量
                String val = strs[i];
                // 判断是否为 null,或者长度为0
                if (!isEmpty(val)) {
                    if (isIgnore) {
                        // 转换小写
                        String valIgnore = val.toLowerCase();
                        // 判断是否属于 val 结尾
                        if (strTemp.endsWith(valIgnore)) {
                            return true;
                        }
                    } else {
                        // 判断是否属于 val 结尾
                        if (strTemp.endsWith(val)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // ============
    // = 其他处理 =
    // ============

    /**
     * 判断字符串是否为 null 或全为空白字符
     * @param str 待校验字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSpace(final String str) {
        if (str == null) return true;
        for (int i = 0, len = str.length(); i < len; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 清空字符串全部空格
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    public static String toClearSpace(final String str) {
        if (isEmpty(str)) return str;
        return str.replaceAll(" ", "");
    }

    /**
     * 清空字符串前后所有空格
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    public static String toClearSpaceTrim(final String str) {
        if (isEmpty(str)) return str;
        String strTemp = str;
        // 如果前面或者后面都是 空格开头,就一直进行处理
        while (strTemp.startsWith(" ") || strTemp.endsWith(" ")) {
            strTemp = strTemp.trim();
        }
        return strTemp;
    }

    // =

    /**
     * 追加空格
     * @param number 空格数量
     * @return 指定数量的空格字符串
     */
    public static String appendSpace(final int number) {
        StringBuffer buffer = new StringBuffer();
        if (number > 0) {
            for (int i = 0; i < number; i++) {
                buffer.append(" ");
            }
        }
        return buffer.toString();
    }

    /**
     * 追加 Tab
     * @param number tab 键数量
     * @return 指定数量的 Tab 字符串
     */
    public static String appendTab(final int number) {
        StringBuffer buffer = new StringBuffer();
        if (number > 0) {
            for (int i = 0; i < number; i++) {
                buffer.append("\t");
            }
        }
        return buffer.toString();
    }

    /**
     * 追加 换行
     * @param number 换行数量
     * @return 指定数量的换行字符串
     */
    public static String appendLine(final int number) {
        StringBuffer buffer = new StringBuffer();
        if (number > 0) {
            for (int i = 0; i < number; i++) {
                buffer.append(NEW_LINE_STR);
            }
        }
        return buffer.toString();
    }

    // =

    /**
     * 检查字符串
     * @param str 待校验字符串
     * @return 如果待校验字符串为 null, 则返回默认字符串, 如果不为 null, 则返回该字符串
     */
    public static String toCheckValue(final String str) {
        return toCheckValue("", str);
    }

    /**
     * 检查字符串
     * @param defaultStr 默认字符串
     * @param str        待校验字符串
     * @return 如果待校验字符串为 null, 则返回 defaultStr , 如果不为 null, 则返回该字符串
     */
    public static String toCheckValue(final String defaultStr, final String str) {
        return isEmpty(str) ? defaultStr : str;
    }

    /**
     * 检查字符串 - 单独检查两个值
     * @param defaultStr 默认字符串
     * @param value1     第一个待校验字符串
     * @param value2     第二个待校验字符串
     * @return 两个待校验字符串中不为 null 的字符串, 如果都为 null, 则返回 defaultStr
     */
    public static String toCheckValue(final String defaultStr, final String value1, final String value2) {
        if (isEmpty(value1)) {
            if (isEmpty(value2)) {
                return defaultStr;
            } else {
                return value2;
            }
        } else {
            return value1;
        }
    }

    /**
     * 检查字符串 - 多个值
     * @param defaultStr 默认字符串
     * @param strs       待校验字符串数组
     * @return 字符串数组中不为 null 的字符串, 如果都为 null, 则返回 defaultStr
     */
    public static String toCheckValues(final String defaultStr, final String... strs) {
        if (strs != null && strs.length != 0) {
            for (int i = 0, len = strs.length; i < len; i++) {
                String val = strs[i];
                if (isEmpty(val)) {
                    if (i == len - 1) {
                        return defaultStr; // 属于最后一个,则返回默认值
                    } else {
                        continue; // 不属于最后一个则跳过
                    }
                } else {
                    return val;
                }
            }
        }
        return defaultStr;
    }

    /**
     * 检查字符串 - 多个值(删除前后空格对比判断)
     * @param defaultStr 默认字符串
     * @param strs       待校验字符串数组
     * @return 字符串数组中不为 null 的字符串, 如果都为 null, 则返回 defaultStr
     */
    public static String toCheckValuesSpace(final String defaultStr, final String... strs) {
        if (strs != null && strs.length != 0) {
            for (int i = 0, len = strs.length; i < len; i++) {
                // 处理后, 进行返回 => 删除前后空格
                String val = toClearSpaceTrim(strs[i]);
                if (isEmpty(val)) {
                    if (i == len - 1) {
                        return defaultStr; // 属于最后一个,则返回默认值
                    } else {
                        continue; // 不属于最后一个则跳过
                    }
                } else {
                    return val;
                }
            }
        }
        return defaultStr;
    }

    // ==================
    // = 数据格式化处理 =
    // ==================

    /**
     * 获取格式化后的字符串
     * @param format 待格式化字符串
     * @param args   格式化参数
     * @return 格式化后的字符串
     */
    public static String getFormatString(final String format, final Object... args) {
        if (format == null) return null;
        try {
            return String.format(format, args);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getFormatString");
        }
        return null;
    }

    /**
     * 获取格式化后的字符串
     * @param format 待格式化字符串
     * @param args   格式化参数
     * @return 格式化后的字符串
     */
    public static String getFormatString2(final String format, final Object... args) {
        if (format == null) return null;
        try {
            if (args != null && args.length != 0) {
                return String.format(format, args);
            } else {
                return format;
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getFormatString2");
        }
        return null;
    }

    // =

    /**
     * 获取自动数量格式化后的字符串(可变参数)
     * @param args 格式化参数
     * @return 格式化后的字符串
     */
    public static String getAutoFormatString(final Object... args) {
        if (args != null && args.length != 0) {
            try {
                int length = args.length;
                StringBuffer buffer = new StringBuffer();
                buffer.append("%s");
                if (length > 1) {
                    for (int i = 1; i < length; i++) {
                        buffer.append(" %s");
                    }
                }
                return String.format(buffer.toString(), args);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getAutoFormatString");
            }
        }
        return null;
    }

    /**
     * 获取自动数量格式化后的字符串(可变参数)
     * @param args 格式化参数
     * @return 格式化后的字符串
     */
    public static String getAutoFormatString2(final Object... args) {
        if (args != null && args.length != 0) {
            try {
                int length = args.length;
                StringBuffer buffer = new StringBuffer();
                buffer.append("【%s】");
                if (length > 1) {
                    for (int i = 1; i < length; i++) {
                        buffer.append(" %s");
                    }
                }
                return String.format(buffer.toString(), args);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getAutoFormatString2");
            }
        }
        return null;
    }

    // ============
    // = 转换处理 =
    // ============

    /**
     * 字符串进行 GBK 编码
     * @param str 待处理字符串
     * @return GBK 编码后的字符串
     */
    public static String toGBKEncode(final String str) {
        return toStrEncode(str, "GBK");
    }

    /**
     * 字符串进行 GBK2312 编码
     * @param str 待处理字符串
     * @return GBK2312 编码后的字符串
     */
    public static String toGBK2312Encode(final String str) {
        return toStrEncode(str, "GBK-2312");
    }

    /**
     * 字符串进行 UTF-8 编码
     * @param str 待处理字符串
     * @return UTF-8 编码后的字符串
     */
    public static String toUTF8Encode(final String str) {
        return toStrEncode(str, "UTF-8");
    }

    /**
     * 进行字符串编码
     * @param str 待处理字符串
     * @param enc 编码格式
     * @return 指定编码格式编码后的字符串
     */
    public static String toStrEncode(final String str, final String enc) {
        if (str == null || enc == null) return null;
        try {
            return new String(str.getBytes(), enc);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toStrEncode");
        }
        return str;
    }

    // =

    /**
     * 进行 URL 编码,默认UTF-8
     * @param str 待处理字符串
     * @return 字符串 UTF-8 编码后, 再进行 Url 编码后的字符串
     */
    public static String toUrlEncode(final String str) {
        return toUrlEncode(str, "UTF-8");
    }

    /**
     * 进行 URL 编码
     * @param str 待处理字符串
     * @param enc 编码格式
     * @return 指定编码格式编码后, 再进行 Url 编码后的字符串
     */
    public static String toUrlEncode(final String str, final String enc) {
        if (str == null || enc == null) return null;
        try {
            return URLEncoder.encode(str, enc);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toUrlEncode");
        }
        return null;
    }

    // =

    /**
     * 将字符串转移为 ASCII 码
     * @param str 待处理字符串
     * @return 字符串转 ASCII 码后的字符串
     */
    public static String toASCII(final String str) {
        if (isEmpty(str)) return str;
        try {
            StringBuffer buffer = new StringBuffer();
            byte[] bytes = str.getBytes();
            for (int i = 0, len = bytes.length; i < len; i++) {
                buffer.append(Integer.toHexString(bytes[i] & 0xff));
            }
            return buffer.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toASCII");
        }
        return null;
    }

    /**
     * 将字符串转移为 Unicode 码
     * @param str 待处理字符串
     * @return 字符串转 Unicode 码后的字符串
     */
    public static String toUnicode(final String str) {
        if (isEmpty(str)) return str;
        try {
            StringBuffer buffer = new StringBuffer();
            char[] chars = str.toCharArray();
            for (int i = 0, len = chars.length; i < len; i++) {
                buffer.append("\\u").append(Integer.toHexString(chars[i]));
            }
            return buffer.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toUnicode");
        }
        return null;
    }

    /**
     * 将字符数组转移为 Unicode 码
     * @param chars char[]
     * @return char[] 转 Unicode 码后的字符串
     */
    public static String toUnicodeString(final char[] chars) {
        if (chars == null) return null;
        try {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0, len = chars.length; i < len; i++) {
                buffer.append("\\u").append(Integer.toHexString(chars[i]));
            }
            return buffer.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toUnicodeString");
        }
        return null;
    }

    /**
     * 转化为半角字符
     * @param str 待处理字符串
     * @return 转换半角字符串
     */
    public static String toDBC(final String str) {
        if (isEmpty(str)) return str;
        char[] chars = str.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符 如: a => ａ A => Ａ
     * @param str 待处理字符串
     * @return 转换全角字符串
     */
    public static String toSBC(final String str) {
        if (isEmpty(str)) return str;
        char[] chars = str.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    // =

    /**
     * 检测字符串是否全是中文
     * @param str 待校验字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean checkChineseToString(final String str) {
        if (isEmpty(str)) return false;
        boolean result = true;
        char[] chars = str.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (!isChinese(chars[i])) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 判定输入汉字
     * @param ch 待校验字符
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isChinese(final char ch) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    // ==================
    // = 字符串处理方法 =
    // ==================

    /**
     * 首字母大写
     * @param str 待处理字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(final String str) {
        if (isEmpty(str) || !Character.isLowerCase(str.charAt(0))) return str;
        try {
            return String.valueOf((char) (str.charAt(0) - 32)) + str.substring(1);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "upperFirstLetter");
            return str;
        }
    }

    /**
     * 首字母小写
     * @param str 待处理字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(final String str) {
        if (isEmpty(str) || !Character.isUpperCase(str.charAt(0))) return str;
        try {
            return String.valueOf((char) (str.charAt(0) + 32)) + str.substring(1);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "lowerFirstLetter");
            return str;
        }
    }

    /**
     * 反转字符串
     * @param str 待处理字符串
     * @return 反转字符串
     */
    public static String reverse(final String str) {
        int len = length(str);
        if (len <= 1) return str;
        int mid = len >> 1;
        char[] chars = str.toCharArray();
        char ch;
        for (int i = 0; i < mid; ++i) {
            ch = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = ch;
        }
        return new String(chars);
    }

    /**
     * 字符串连接，将参数列表拼接为一个字符串
     * @param args 追加数据
     * @return 拼接后的字符串
     */
    public static String concat(final Object... args) {
        return concatSpiltWith("", args);
    }

    /**
     * 字符串连接，将参数列表拼接为一个字符串
     * @param startStr 开始字符串
     * @param args     追加数据
     * @return 拼接后的字符串
     */
    public static String concatSpiltWith(final String startStr, final Object... args) {
        if (args == null) return null;
        StringBuilder builder = new StringBuilder();
        for (int i = 0, len = args.length; i < len; i++) {
            if (i != 0) builder.append(startStr);
            builder.append(args[i]);
        }
        return builder.toString();
    }

    /**
     * 下划线命名转为驼峰命名
     * @param str 下划线命名格式字符串
     * @return 驼峰命名格式字符串
     */
    public static String underScoreCaseToCamelCase(final String str) {
        if (isEmpty(str)) return str;
        if (!str.contains("_")) return str;
        StringBuilder builder = new StringBuilder();
        char[] chars = str.toCharArray();
        boolean hitUnderScore = false;
        builder.append(chars[0]);
        for (int i = 1, len = chars.length; i < len; i++) {
            char c = chars[i];
            if (c == '_') {
                hitUnderScore = true;
            } else {
                if (hitUnderScore) {
                    builder.append(Character.toUpperCase(c));
                    hitUnderScore = false;
                } else {
                    builder.append(c);
                }
            }
        }
        return builder.toString();
    }

    /**
     * 驼峰命名法转为下划线命名
     * @param str 驼峰命名格式字符串
     * @return 下划线命名格式字符串
     */
    public static String camelCaseToUnderScoreCase(final String str) {
        if (isEmpty(str)) return str;
        StringBuilder builder = new StringBuilder();
        char[] chars = str.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            char c = chars[i];
            if (Character.isUpperCase(c)) {
                builder.append("_").append(Character.toLowerCase(c));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    /**
     * 字符串数据库字符转义
     * @param str 待处理字符串
     * @return 转义处理后的字符串
     */
    public static String sqliteEscape(final String str) {
        if (isEmpty(str)) return str;
        String keyWord = str;
        // 替换关键字
        keyWord = keyWord.replace("/", "//");
        keyWord = keyWord.replace("'", "''");
        keyWord = keyWord.replace("[", "/[");
        keyWord = keyWord.replace("]", "/]");
        keyWord = keyWord.replace("%", "/%");
        keyWord = keyWord.replace("&", "/&");
        keyWord = keyWord.replace("_", "/_");
        keyWord = keyWord.replace("(", "/(");
        keyWord = keyWord.replace(")", "/)");
        return keyWord;
    }
}
