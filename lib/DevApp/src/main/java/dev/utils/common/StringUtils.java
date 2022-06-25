package dev.utils.common;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.utils.DevFinal;
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

    // ==========
    // = String =
    // ==========

    /**
     * 判断字符串是否为 null
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final CharSequence str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 判断多个字符串是否存在为 null 的字符串
     * @param args 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final CharSequence... args) {
        if (args != null && args.length != 0) {
            for (CharSequence value : args) {
                if (isEmpty(value)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否为 null ( 调用 clearSpaceTabLineTrim )
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmptyClear(final String str) {
        return isEmpty(clearSpaceTabLineTrim(str));
    }

    /**
     * 判断多个字符串是否存在为 null 的字符串 ( 调用 clearSpaceTabLineTrim )
     * @param args 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmptyClear(final String... args) {
        if (args != null && args.length != 0) {
            for (String value : args) {
                if (isEmptyClear(value)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    // =

    /**
     * 判断字符串是否不为 null
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final CharSequence str) {
        return (str != null && str.length() != 0);
    }

    /**
     * 判断字符串是否不为 null ( 调用 clearSpaceTabLineTrim )
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmptyClear(final String str) {
        return isNotEmpty(clearSpaceTabLineTrim(str));
    }

    // ========
    // = Null =
    // ========

    /**
     * 判断字符串是否为 "null"
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNull(final String str) {
        return isEmpty(str) || DevFinal.SYMBOL.NULL.equalsIgnoreCase(str);
    }

    /**
     * 判断多个字符串是否存在为 "null" 的字符串
     * @param args 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNull(final String... args) {
        if (args != null && args.length != 0) {
            for (String value : args) {
                if (isNull(value)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否为 "null" ( 调用 clearSpaceTabLineTrim )
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNullClear(final String str) {
        return isNull(clearSpaceTabLineTrim(str));
    }

    /**
     * 判断多个字符串是否存在为 "null" 的字符串 ( 调用 clearSpaceTabLineTrim )
     * @param args 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNullClear(final String... args) {
        if (args != null && args.length != 0) {
            for (String value : args) {
                if (isNullClear(value)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    // =

    /**
     * 判断字符串是否不为 "null"
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotNull(final String str) {
        return !isNull(str);
    }

    /**
     * 判断字符串是否不为 "null" ( 调用 clearSpaceTabLineTrim )
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotNullClear(final String str) {
        return isNotNull(clearSpaceTabLineTrim(str));
    }

    // ==========
    // = 判断长度 =
    // ==========

    /**
     * 获取字符串长度
     * @param str 待校验的字符串
     * @return 字符串长度, 如果字符串为 null, 则返回 0
     */
    public static int length(final String str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 获取字符串长度
     * @param str           待校验的字符串
     * @param defaultLength 字符串为 null 时, 返回的长度
     * @return 字符串长度, 如果字符串为 null, 则返回 defaultLength
     */
    public static int length(
            final String str,
            final int defaultLength
    ) {
        return str != null ? str.length() : defaultLength;
    }

    // =

    /**
     * 获取字符串长度 是否等于期望长度
     * @param str    待校验的字符串
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(
            final String str,
            final int length
    ) {
        return str != null && str.length() == length;
    }

    // ==========
    // = 对比判断 =
    // ==========

    /**
     * 判断两个值是否一样
     * @param value1 第一个值
     * @param value2 第二个值
     * @param <T>    泛型
     * @return {@code true} yes, {@code false} no
     */
    public static <T> boolean equals(
            final T value1,
            final T value2
    ) {
        return ObjectUtils.equals(value1, value2);
    }

    /**
     * 判断两个值是否一样 ( 非 null 判断 )
     * @param value1 第一个值
     * @param value2 第二个值
     * @param <T>    泛型
     * @return {@code true} yes, {@code false} no
     */
    public static <T> boolean equalsNotNull(
            final T value1,
            final T value2
    ) {
        return value1 != null && ObjectUtils.equals(value1, value2);
    }

    // =

    /**
     * 判断多个字符串是否相等, 只有全相等才返回 true ( 对比大小写 )
     * @param args 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEquals(final String... args) {
        return isEquals(false, args);
    }

    /**
     * 判断多个字符串是否相等, 只有全相等才返回 true
     * @param isIgnore 是否忽略大小写
     * @param args     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEquals(
            final boolean isIgnore,
            final String... args
    ) {
        if (args != null) {
            String last = null;
            // 获取数据长度
            int len = args.length;
            // 如果最多只有一个数据判断, 则直接跳过
            if (len <= 1) {
                return false;
            }
            for (String value : args) {
                // 如果等于 null, 则跳过
                if (value == null) {
                    return false;
                }
                if (last != null) {
                    if (isIgnore) {
                        if (!value.equalsIgnoreCase(last)) {
                            return false;
                        }
                    } else {
                        if (!value.equals(last)) {
                            return false;
                        }
                    }
                }
                last = value;
            }
            return true;
        }
        return false;
    }

    /**
     * 判断多个字符串, 只要有一个符合条件则通过
     * @param str  待校验的字符串
     * @param args 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isOrEquals(
            final String str,
            final String... args
    ) {
        return isOrEquals(false, str, args);
    }

    /**
     * 判断多个字符串, 只要有一个符合条件则通过
     * @param isIgnore 是否忽略大小写
     * @param str      待校验的字符串
     * @param args     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isOrEquals(
            final boolean isIgnore,
            final String str,
            final String... args
    ) {
        if (str != null && args != null && args.length != 0) {
            for (String value : args) {
                if (isIgnore) {
                    if (value.equalsIgnoreCase(str)) {
                        return true;
                    }
                } else {
                    if (value.equals(str)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断一堆值中, 是否存在符合该条件的 ( 包含 )
     * @param str  待校验的字符串
     * @param args 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isContains(
            final String str,
            final String... args
    ) {
        return isContains(false, str, args);
    }

    /**
     * 判断一堆值中, 是否存在符合该条件的 ( 包含 )
     * @param isIgnore 是否忽略大小写
     * @param str      待校验的字符串
     * @param args     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isContains(
            final boolean isIgnore,
            final String str,
            final String... args
    ) {
        if (str != null && args != null && args.length != 0) {
            String tempString = str;
            // 判断是否需要忽略大小写
            if (isIgnore) {
                tempString = tempString.toLowerCase();
            }
            // 获取内容长度
            int strLength = tempString.length();
            // 遍历判断
            for (String value : args) {
                // 判断是否为 null, 或者长度为 0
                if (!isEmpty(value) && strLength != 0) {
                    if (isIgnore) {
                        // 转换小写
                        String valIgnore = value.toLowerCase();
                        // 判断是否包含
                        if (valIgnore.contains(tempString)) {
                            return true;
                        }
                    } else {
                        // 判断是否包含
                        if (value.contains(tempString)) {
                            return true;
                        }
                    }
                } else {
                    if (tempString.equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断内容, 是否属于特定字符串开头 ( 对比大小写 )
     * @param str  待校验的字符串
     * @param args 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isStartsWith(
            final String str,
            final String... args
    ) {
        return isStartsWith(false, str, args);
    }

    /**
     * 判断内容, 是否属于特定字符串开头
     * @param isIgnore 是否忽略大小写
     * @param str      待校验的字符串
     * @param args     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isStartsWith(
            final boolean isIgnore,
            final String str,
            final String... args
    ) {
        if (!isEmpty(str) && args != null && args.length != 0) {
            String tempString = str;
            // 判断是否需要忽略大小写
            if (isIgnore) {
                tempString = tempString.toLowerCase();
            }
            for (String value : args) {
                // 判断是否为 null, 或者长度为 0
                if (!isEmpty(value)) {
                    if (isIgnore) {
                        // 转换小写
                        String valIgnore = value.toLowerCase();
                        // 判断是否属于 val 开头
                        if (tempString.startsWith(valIgnore)) {
                            return true;
                        }
                    } else {
                        // 判断是否属于 val 开头
                        if (tempString.startsWith(value)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断内容, 是否属于特定字符串结尾 ( 对比大小写 )
     * @param str  待校验的字符串
     * @param args 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEndsWith(
            final String str,
            final String... args
    ) {
        return isEndsWith(false, str, args);
    }

    /**
     * 判断内容, 是否属于特定字符串结尾
     * @param isIgnore 是否忽略大小写
     * @param str      待校验的字符串
     * @param args     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEndsWith(
            final boolean isIgnore,
            final String str,
            final String... args
    ) {
        if (!isEmpty(str) && args != null && args.length != 0) {
            String tempString = str;
            // 判断是否需要忽略大小写
            if (isIgnore) {
                tempString = tempString.toLowerCase();
            }
            for (String value : args) {
                // 判断是否为 null, 或者长度为 0
                if (!isEmpty(value)) {
                    if (isIgnore) {
                        // 转换小写
                        String valIgnore = value.toLowerCase();
                        // 判断是否属于 val 结尾
                        if (tempString.endsWith(valIgnore)) {
                            return true;
                        }
                    } else {
                        // 判断是否属于 val 结尾
                        if (tempString.endsWith(value)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 统计字符串匹配个数
     * @param str     待匹配字符串
     * @param keyword 匹配 key
     * @return 字符串 key 匹配个数
     */
    public static int countMatches(
            final String str,
            final String keyword
    ) {
        if (isEmpty(str) || isEmpty(keyword)) return 0;
        try {
            int     count   = 0;
            Matcher matcher = Pattern.compile(keyword).matcher(str);
            // find() 对字符串进行匹配, 匹配到的字符串可以在任何位置
            while (matcher.find()) {
                count++;
            }
            return count;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "countMatches");
        }
        return -1;
    }

    /**
     * 统计字符串匹配个数
     * @param str     待匹配字符串
     * @param keyword 匹配 key
     * @return 字符串 key 匹配个数
     */
    public static int countMatches2(
            final String str,
            final String keyword
    ) {
        if (isEmpty(str) || isEmpty(keyword)) return 0;
        try {
            // 获取匹配 key 长度
            int keyLength = keyword.length();
            // =
            int count = 0, index = 0;
            while ((index = str.indexOf(keyword, index)) != -1) {
                index = index + keyLength;
                count++;
            }
            return count;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "countMatches2");
        }
        return -1;
    }

    // ==========
    // = 其他处理 =
    // ==========

    /**
     * 判断字符串是否为 null 或全为空白字符
     * @param str 待校验字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSpace(final CharSequence str) {
        if (str == null) return true;
        for (int i = 0, len = str.length(); i < len; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串 转 byte[]
     * @param str 待处理字符串
     * @return byte[]
     */
    public static byte[] getBytes(final String str) {
        return (str != null) ? str.getBytes() : null;
    }

    // =

    /**
     * 清空字符串全部空格
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    public static String clearSpace(final String str) {
        return replaceAll(str, DevFinal.SYMBOL.SPACE, "");
    }

    /**
     * 清空字符串全部 Tab
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    public static String clearTab(final String str) {
        return replaceAll(str, DevFinal.SYMBOL.TAB, "");
    }

    /**
     * 清空字符串全部换行符
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    public static String clearLine(final String str) {
        return replaceAll(str, DevFinal.SYMBOL.NEW_LINE, "");
    }

    /**
     * 清空字符串全部换行符
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    public static String clearLine2(final String str) {
        return replaceAll(str, DevFinal.SYMBOL.NL, "");
    }

    // =

    /**
     * 清空字符串前后全部空格
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    public static String clearSpaceTrim(final String str) {
        return clearSEWiths(str, DevFinal.SYMBOL.SPACE);
    }

    /**
     * 清空字符串前后全部 Tab
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    public static String clearTabTrim(final String str) {
        return clearSEWiths(str, DevFinal.SYMBOL.TAB);
    }

    /**
     * 清空字符串前后全部换行符
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    public static String clearLineTrim(final String str) {
        return clearSEWiths(str, DevFinal.SYMBOL.NEW_LINE);
    }

    /**
     * 清空字符串前后全部换行符
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    public static String clearLineTrim2(final String str) {
        return clearSEWiths(str, DevFinal.SYMBOL.NL);
    }

    /**
     * 清空字符串全部空格、Tab、换行符
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    public static String clearSpaceTabLine(final String str) {
        if (isEmpty(str)) return str;
        String value = clearSpace(str);
        value = clearTab(value);
        value = clearLine(value);
        value = clearLine2(value);
        return value;
    }

    /**
     * 清空字符串前后全部空格、Tab、换行符
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    public static String clearSpaceTabLineTrim(final String str) {
        if (isEmpty(str)) return str;
        String value = str;
        while (true) {
            boolean space = (value.startsWith(DevFinal.SYMBOL.SPACE) || value.endsWith(DevFinal.SYMBOL.SPACE));
            if (space) value = clearSpaceTrim(value);

            boolean tab = (value.startsWith(DevFinal.SYMBOL.TAB) || value.endsWith(DevFinal.SYMBOL.TAB));
            if (tab) value = clearTabTrim(value);

            boolean line = (value.startsWith(DevFinal.SYMBOL.NEW_LINE) || value.endsWith(DevFinal.SYMBOL.NEW_LINE));
            if (line) value = clearLineTrim(value);

            boolean line2 = (value.startsWith(DevFinal.SYMBOL.NL) || value.endsWith(DevFinal.SYMBOL.NL));
            if (line2) value = clearLineTrim2(value);

            // 都不存在则返回值
            if (!space && !tab && !line && !line2) return value;
        }
    }

    // =

    /**
     * 追加空格
     * @param number 空格数量
     * @return 指定数量的空格字符串
     */
    public static String appendSpace(final int number) {
        return forString(number, DevFinal.SYMBOL.SPACE);
    }

    /**
     * 追加 Tab
     * @param number tab 键数量
     * @return 指定数量的 Tab 字符串
     */
    public static String appendTab(final int number) {
        return forString(number, DevFinal.SYMBOL.TAB);
    }

    /**
     * 追加换行
     * @param number 换行数量
     * @return 指定数量的换行字符串
     */
    public static String appendLine(final int number) {
        return forString(number, DevFinal.SYMBOL.NEW_LINE);
    }

    /**
     * 追加换行
     * @param number 换行数量
     * @return 指定数量的换行字符串
     */
    public static String appendLine2(final int number) {
        return forString(number, DevFinal.SYMBOL.NL);
    }

    /**
     * 循环指定数量字符串
     * @param number 空格数量
     * @param str    待追加字符串
     * @return 指定数量字符串
     */
    public static String forString(
            final int number,
            final String str
    ) {
        StringBuilder builder = new StringBuilder();
        if (number > 0) {
            for (int i = 0; i < number; i++) {
                builder.append(str);
            }
        }
        return builder.toString();
    }

    /**
     * 循环拼接
     * @param delimiter 拼接符号
     * @param values    待拼接对象
     * @return 拼接后的值
     */
    public static String joinArgs(
            final Object delimiter,
            final Object... values
    ) {
        if (values != null) {
            int length = values.length;
            if (length != 0) {
                StringBuilder builder = new StringBuilder();
                builder.append(values[0]);
                for (int i = 1; i < length; i++) {
                    builder.append(delimiter);
                    builder.append(values[i]);
                }
                return builder.toString();
            }
        }
        return null;
    }

    /**
     * 循环拼接
     * @param delimiter 拼接符号
     * @param values    待拼接对象
     * @return 拼接后的值
     */
    public static String join(
            final Object delimiter,
            final Object[] values
    ) {
        if (values != null) {
            int length = values.length;
            if (length != 0) {
                StringBuilder builder = new StringBuilder();
                builder.append(values[0]);
                for (int i = 1; i < length; i++) {
                    builder.append(delimiter);
                    builder.append(values[i]);
                }
                return builder.toString();
            }
        }
        return null;
    }

    /**
     * 循环拼接
     * @param delimiter 拼接符号
     * @param iterable  待拼接对象
     * @return 拼接后的值
     */
    public static String join(
            final Object delimiter,
            final Iterable iterable
    ) {
        if (iterable != null) {
            final Iterator<?> it = iterable.iterator();
            if (!it.hasNext()) {
                return "";
            }
            StringBuilder builder = new StringBuilder();
            builder.append(it.next());
            while (it.hasNext()) {
                builder.append(delimiter);
                builder.append(it.next());
            }
            return builder.toString();
        }
        return null;
    }

    /**
     * 冒号分割处理
     * @param str 待处理字符串
     * @return 冒号分割后的字符串
     */
    public static String colonSplit(final String str) {
        if (!isEmpty(str)) {
            return str.replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
        }
        return str;
    }

    // =

    /**
     * 获取字符串 ( 判 null )
     * @param str 待校验的字符串
     * @return 校验后的字符串
     */
    public static String getString(final String str) {
        return getString(str, DevFinal.SYMBOL.NULL);
    }

    /**
     * 获取字符串 ( 判 null )
     * @param str        待校验的字符串
     * @param defaultStr 默认字符串
     * @return 校验后的字符串
     */
    public static String getString(
            final String str,
            final String defaultStr
    ) {
        return str != null ? str : defaultStr;
    }

    /**
     * 获取字符串 ( 判 null )
     * @param object 待校验的对象
     * @return 校验后的字符串
     */
    public static String getString(final Object object) {
        return getString(object, DevFinal.SYMBOL.NULL);
    }

    /**
     * 获取字符串 ( 判 null )
     * @param object     待校验的对象
     * @param defaultStr 默认字符串
     * @return 校验后的字符串
     */
    public static String getString(
            final Object object,
            final String defaultStr
    ) {
        return object != null ? object.toString() : defaultStr;
    }

    /**
     * 检查字符串
     * @param str 待校验字符串
     * @return 如果待校验字符串为 null, 则返回默认字符串, 如果不为 null, 则返回该字符串
     */
    public static String checkValue(final String str) {
        return checkValue("", str);
    }

    /**
     * 检查字符串
     * @param defaultStr 默认字符串
     * @param str        待校验字符串
     * @return 如果待校验字符串为 null, 则返回 defaultStr, 如果不为 null, 则返回该字符串
     */
    public static String checkValue(
            final String defaultStr,
            final String str
    ) {
        return isEmpty(str) ? defaultStr : str;
    }

    /**
     * 检查字符串 ( 单独检查两个值 )
     * @param defaultStr 默认字符串
     * @param value1     第一个待校验字符串
     * @param value2     第二个待校验字符串
     * @return 两个待校验字符串中不为 null 的字符串, 如果都为 null, 则返回 defaultStr
     */
    public static String checkValue(
            final String defaultStr,
            final String value1,
            final String value2
    ) {
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
     * 检查字符串 ( 多个值 )
     * @param defaultStr 默认字符串
     * @param args       待校验字符串数组
     * @return 字符串数组中不为 null 的字符串, 如果都为 null, 则返回 defaultStr
     */
    public static String checkValues(
            final String defaultStr,
            final String... args
    ) {
        if (args != null && args.length != 0) {
            for (String value : args) {
                if (!isEmpty(value)) {
                    return value;
                }
            }
        }
        return defaultStr;
    }

    /**
     * 检查字符串 ( 多个值, 删除前后空格对比判断 )
     * @param defaultStr 默认字符串
     * @param args       待校验字符串数组
     * @return 字符串数组中不为 null 的字符串, 如果都为 null, 则返回 defaultStr
     */
    public static String checkValuesSpace(
            final String defaultStr,
            final String... args
    ) {
        if (args != null && args.length != 0) {
            for (String value : args) {
                // 删除前后空格处理后, 进行返回
                String result = clearSpaceTrim(value);
                if (!isEmpty(result)) {
                    return result;
                }
            }
        }
        return defaultStr;
    }

    // ===============
    // = 数据格式化处理 =
    // ===============

    /**
     * 获取格式化后的字符串
     * @param format 待格式化字符串
     * @param args   格式化参数
     * @return 格式化后的字符串
     */
    public static String getFormatString(
            final String format,
            final Object... args
    ) {
        if (format == null) return null;
        try {
            if (args != null && args.length != 0) {
                return String.format(format, args);
            } else {
                return format;
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getFormatString");
        }
        return null;
    }

    // =

    /**
     * 获取自动数量格式化后的字符串 ( 可变参数 )
     * @param args 格式化参数
     * @return 格式化后的字符串
     */
    public static String getAutoFormatString(final Object... args) {
        if (args != null && args.length != 0) {
            try {
                int           length  = args.length;
                StringBuilder builder = new StringBuilder();
                builder.append("%s");
                if (length > 1) {
                    for (int i = 1; i < length; i++) {
                        builder.append(" %s");
                    }
                }
                return String.format(builder.toString(), args);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getAutoFormatString");
            }
        }
        return null;
    }

    /**
     * 获取自动数量格式化后的字符串 ( 可变参数 )
     * @param args 格式化参数
     * @return 格式化后的字符串
     */
    public static String getAutoFormatString2(final Object... args) {
        if (args != null && args.length != 0) {
            try {
                int           length  = args.length;
                StringBuilder builder = new StringBuilder();
                builder.append("[%s]");
                if (length > 1) {
                    for (int i = 1; i < length; i++) {
                        builder.append(" %s");
                    }
                }
                return String.format(builder.toString(), args);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getAutoFormatString2");
            }
        }
        return null;
    }

    // =

    /**
     * 字符串连接, 将参数列表拼接为一个字符串
     * @param args 追加数据
     * @return 拼接后的字符串
     */
    public static String concat(final Object... args) {
        return concatSpiltWith("", args);
    }

    /**
     * 字符串连接, 将参数列表拼接为一个字符串
     * @param split 追加间隔
     * @param args  追加数据
     * @return 拼接后的字符串
     */
    public static String concatSpiltWith(
            final String split,
            final Object... args
    ) {
        if (args == null) return null;
        StringBuilder builder = new StringBuilder();
        if (isEmpty(split)) {
            for (Object value : args) {
                builder.append(value);
            }
        } else {
            for (Object value : args) {
                builder.append(value).append(split);
            }
        }
        return builder.toString();
    }

    /**
     * 字符串连接, 将参数列表拼接为一个字符串 ( 最后一个不追加间隔 )
     * @param split 追加间隔
     * @param args  追加数据
     * @return 拼接后的字符串
     */
    public static String concatSpiltWithIgnoreLast(
            final String split,
            final Object... args
    ) {
        if (args == null) return null;
        StringBuilder builder = new StringBuilder();
        int           len     = args.length;
        if (len > 0) {
            if (isEmpty(split)) {
                for (Object value : args) {
                    builder.append(value);
                }
            } else {
                for (int i = 0; i < len - 1; i++) {
                    builder.append(args[i]).append(split);
                }
                builder.append(args[len - 1]);
            }
        }
        return builder.toString();
    }

    /**
     * StringBuilder 拼接处理
     * @param builder 拼接 Builder
     * @param split   追加间隔
     * @param args    拼接数据源
     * @return {@link StringBuilder}
     */
    public static StringBuilder appends(
            final StringBuilder builder,
            final String split,
            final Object... args
    ) {
        if (builder != null && args != null) {
            if (isEmpty(split)) {
                for (Object value : args) {
                    builder.append(value);
                }
            } else {
                for (Object value : args) {
                    builder.append(value).append(split);
                }
            }
        }
        return builder;
    }

    /**
     * StringBuilder 拼接处理 ( 最后一个不追加间隔 )
     * @param builder 拼接 Builder
     * @param split   追加间隔
     * @param args    拼接数据源
     * @return {@link StringBuilder}
     */
    public static StringBuilder appendsIgnoreLast(
            final StringBuilder builder,
            final String split,
            final Object... args
    ) {
        if (builder != null && args != null) {
            int len = args.length;
            if (len > 0) {
                if (isEmpty(split)) {
                    for (Object value : args) {
                        builder.append(value);
                    }
                } else {
                    for (int i = 0; i < len - 1; i++) {
                        builder.append(args[i]).append(split);
                    }
                    builder.append(args[len - 1]);
                }
            }
        }
        return builder;
    }

    // ==========
    // = 转换处理 =
    // ==========

    /**
     * 字符串进行 GBK 编码
     * @param str 待处理字符串
     * @return GBK 编码后的字符串
     */
    public static String gbkEncode(final String str) {
        return strEncode(str, DevFinal.ENCODE.GBK);
    }

    /**
     * 字符串进行 GBK2312 编码
     * @param str 待处理字符串
     * @return GBK2312 编码后的字符串
     */
    public static String gbk2312Encode(final String str) {
        return strEncode(str, DevFinal.ENCODE.GBK_2312);
    }

    /**
     * 字符串进行 UTF-8 编码
     * @param str 待处理字符串
     * @return UTF-8 编码后的字符串
     */
    public static String utf8Encode(final String str) {
        return strEncode(str, DevFinal.ENCODE.UTF_8);
    }

    /**
     * 进行字符串编码
     * @param str 待处理字符串
     * @param enc 编码格式
     * @return 指定编码格式编码后的字符串
     */
    public static String strEncode(
            final String str,
            final String enc
    ) {
        if (str == null || enc == null) return null;
        try {
            return new String(str.getBytes(), enc);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "strEncode");
        }
        return str;
    }

    // =

    /**
     * 进行 URL 编码, 默认 UTF-8
     * @param str 待处理字符串
     * @return UTF-8 编码格式 URL 编码后的字符串
     */
    public static String urlEncode(final String str) {
        return urlEncode(str, DevFinal.ENCODE.UTF_8);
    }

    /**
     * 进行 URL 编码
     * @param str 待处理字符串
     * @param enc 编码格式
     * @return 指定编码格式 URL 编码后的字符串
     */
    public static String urlEncode(
            final String str,
            final String enc
    ) {
        if (str == null || enc == null) return null;
        try {
            return URLEncoder.encode(str, enc);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "urlEncode");
        }
        return null;
    }

    // =

    /**
     * 进行 URL 解码, 默认 UTF-8
     * @param str 待处理字符串
     * @return UTF-8 编码格式 URL 解码后的字符串
     */
    public static String urlDecode(final String str) {
        return urlDecode(str, DevFinal.ENCODE.UTF_8);
    }

    /**
     * 进行 URL 解码
     * @param str 待处理字符串
     * @param enc 解码格式
     * @return 指定编码格式 URL 解码后的字符串
     */
    public static String urlDecode(
            final String str,
            final String enc
    ) {
        if (str == null || enc == null) return null;
        try {
            return URLDecoder.decode(str, enc);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "urlDecode");
        }
        return null;
    }

    // =

    /**
     * 进行 URL 解码, 默认 UTF-8 ( 循环到非 URL 编码为止 )
     * @param str       待处理字符串
     * @param threshold 解码次数阈值, 超过该次数还未完成则直接返回
     * @return UTF-8 编码格式 URL 解码后的字符串
     */
    public static String urlDecodeWhile(
            final String str,
            final int threshold
    ) {
        return urlDecodeWhile(str, DevFinal.ENCODE.UTF_8, threshold);
    }

    /**
     * 进行 URL 解码 ( 循环到非 URL 编码为止 )
     * @param str       待处理字符串
     * @param enc       解码格式
     * @param threshold 解码次数阈值, 超过该次数还未完成则直接返回
     * @return 指定编码格式 URL 解码后的字符串
     */
    public static String urlDecodeWhile(
            final String str,
            final String enc,
            final int threshold
    ) {
        if (str == null || enc == null) return null;
        int    count       = Math.max(threshold, 1);
        int    number      = 0;
        String result      = str;
        String decodeValue = StringUtils.urlDecode(str, enc);
        while (true) {
            // 如果相同则直接返回
            if (result.equals(decodeValue)) {
                return decodeValue;
            }
            if (decodeValue == null) return result;
            result      = decodeValue;
            decodeValue = StringUtils.urlDecode(result, enc);
            // 判断循环次数
            number++;
            if (number > count) {
                if (decodeValue != null) {
                    return decodeValue;
                } else {
                    return result;
                }
            }
        }
    }

    // =

    /**
     * 将字符串转移为 ASCII 码
     * @param str 待处理字符串
     * @return 字符串转 ASCII 码后的字符串
     */
    public static String ascii(final String str) {
        if (isEmpty(str)) return str;
        try {
            StringBuilder builder = new StringBuilder();
            byte[]        bytes   = str.getBytes();
            for (byte value : bytes) {
                builder.append(Integer.toHexString(value & 0xff));
            }
            return builder.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "ascii");
        }
        return null;
    }

    /**
     * 将字符串转移为 Unicode 码
     * @param str 待处理字符串
     * @return 字符串转 Unicode 码后的字符串
     */
    public static String unicode(final String str) {
        if (isEmpty(str)) return str;
        try {
            StringBuilder builder = new StringBuilder();
            char[]        chars   = str.toCharArray();
            for (char value : chars) {
                builder.append("\\u").append(Integer.toHexString(value));
            }
            return builder.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "unicode");
        }
        return null;
    }

    /**
     * 将字符数组转移为 Unicode 码
     * @param chars char[]
     * @return char[] 转 Unicode 码后的字符串
     */
    public static String unicodeString(final char[] chars) {
        if (chars == null) return null;
        try {
            StringBuilder builder = new StringBuilder();
            for (char value : chars) {
                builder.append("\\u").append(Integer.toHexString(value));
            }
            return builder.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "unicodeString");
        }
        return null;
    }

    /**
     * 转化为半角字符
     * @param str 待处理字符串
     * @return 转换半角字符串
     */
    public static String dbc(final String str) {
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
     * 转化为全角字符 如: a = ａ, A = Ａ
     * @param str 待处理字符串
     * @return 转换全角字符串
     */
    public static String sbc(final String str) {
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
        char[]  chars  = str.toCharArray();
        for (char value : chars) {
            if (!isChinese(value)) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 判断输入汉字
     * @param ch 待校验字符
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isChinese(final char ch) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    // ===============
    // = 字符串处理方法 =
    // ===============

    /**
     * 首字母大写
     * @param str 待处理字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(final String str) {
        if (isEmpty(str) || !Character.isLowerCase(str.charAt(0))) return str;
        try {
            return (char) (str.charAt(0) - 32) + str.substring(1);
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
            return (char) (str.charAt(0) + 32) + str.substring(1);
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
        int    mid   = len >> 1;
        char[] chars = str.toCharArray();
        char   ch;
        for (int i = 0; i < mid; ++i) {
            ch                 = chars[i];
            chars[i]           = chars[len - i - 1];
            chars[len - i - 1] = ch;
        }
        return new String(chars);
    }

    /**
     * 下划线命名转为驼峰命名
     * @param str 下划线命名格式字符串
     * @return 驼峰命名格式字符串
     */
    public static String underScoreCaseToCamelCase(final String str) {
        if (isEmpty(str)) return str;
        if (!str.contains("_")) return str;
        StringBuilder builder       = new StringBuilder();
        char[]        chars         = str.toCharArray();
        boolean       hitUnderScore = false;
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
        char[]        chars   = str.toCharArray();
        for (char value : chars) {
            if (Character.isUpperCase(value)) {
                builder.append("_").append(Character.toLowerCase(value));
            } else {
                builder.append(value);
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

    // ============
    // = 字符串处理 =
    // ============

    /**
     * 转换手机号
     * @param phone 待处理字符串
     * @return 处理后的字符串
     */
    public static String convertHideMobile(final String phone) {
        return convertHideMobile(phone, "*");
    }

    /**
     * 转换手机号
     * @param phone  待处理字符串
     * @param symbol 转换符号
     * @return 处理后的字符串
     */
    public static String convertHideMobile(
            final String phone,
            final String symbol
    ) {
        return convertSymbolHide(3, phone, symbol);
    }

    /**
     * 转换符号处理
     * @param start  开始位置
     * @param str    待处理字符串
     * @param symbol 转换符号
     * @return 处理后的字符串
     */
    public static String convertSymbolHide(
            final int start,
            final String str,
            final String symbol
    ) {
        if (!isEmpty(str)) {
            if (start <= 0) {
                return str;
            }
            // 获取数据长度
            int length = str.length();
            // 如果数据小于 start 位则直接返回
            if (length <= start) {
                return str;
            } else { // 大于 start 位
                StringBuilder builder = new StringBuilder();
                builder.append(str.substring(0, start));
                int len = length - start;
                // 进行平分
                len /= 2;
                // 进行遍历保存
                for (int i = 0; i < len; i++) {
                    builder.append(symbol);
                }
                builder.append(str.substring(start + len, length));
                return builder.toString();
            }
        }
        return "";
    }

    // =

    /**
     * 裁剪超出的内容, 并且追加符号 ( 如 ... )
     * @param maxLength 允许最大的长度
     * @param str       待处理字符串
     * @param symbol    转换符号
     * @return 处理后的字符串
     */
    public static String subEllipsize(
            final int maxLength,
            final String str,
            final String symbol
    ) {
        if (maxLength >= 1) {
            // 获取内容长度
            int strLength = length(str);
            // 防止为不存在数据
            if (strLength != 0) {
                if (maxLength >= strLength) {
                    return str;
                }
                return str.substring(0, maxLength) + checkValue(symbol);
            }
        }
        return "";
    }

    /**
     * 裁剪符号处理
     * @param start        开始位置
     * @param symbolNumber 转换数量
     * @param str          待处理字符串
     * @param symbol       转换符号
     * @return 处理后的字符串
     */
    public static String subSymbolHide(
            final int start,
            final int symbolNumber,
            final String str,
            final String symbol
    ) {
        if (!isEmpty(str)) {
            if (start <= 0 || symbolNumber <= 0) {
                return str;
            }
            // 获取数据长度
            int length = str.length();
            // 如果数据小于 start 位则直接返回
            if (length <= start) {
                return str;
            } else { // 大于 start 位
                StringBuilder builder = new StringBuilder();
                builder.append(str.substring(0, start));
                int len = length - start - symbolNumber;
                // 如果超出总长度, 则进行控制
                if (len <= 0) { // 表示后面的全部转换
                    len = length - start;
                } else { // 需要裁剪的数量
                    len = symbolNumber;
                }
                // 进行遍历保存
                for (int i = 0; i < len; i++) {
                    builder.append(symbol);
                }
                builder.append(str.substring(start + len, length));
                return builder.toString();
            }
        }
        return "";
    }

    /**
     * 裁剪内容 ( 设置符号处理 )
     * @param str               待处理字符串
     * @param frontRetainLength 前面保留的长度
     * @param rearRetainLength  后面保留的长度
     * @param symbol            转换符号
     * @return 处理后的字符串
     */
    public static String subSetSymbol(
            final String str,
            final int frontRetainLength,
            final int rearRetainLength,
            final String symbol
    ) {
        if (str != null) {
            try {
                // 截取前面需保留的内容
                String startStr = str.substring(0, frontRetainLength);
                // 截取后面需保留的内容
                String endStr = str.substring(str.length() - rearRetainLength);
                // 特殊符号长度
                int symbolLength = str.length() - (frontRetainLength + rearRetainLength);
                if (symbolLength >= 1) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < symbolLength; i++) {
                        builder.append(symbol);
                    }
                    return startStr + builder.toString() + endStr;
                }
                return startStr + endStr;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "subSetSymbol");
            }
        }
        return null;
    }

    // ===============
    // = 替换、截取操作 =
    // ===============

    /**
     * 裁剪字符串
     * @param str      待裁剪字符串
     * @param endIndex 结束裁剪的位置
     * @return 裁剪后的字符串
     */
    public static String substring(
            final String str,
            final int endIndex
    ) {
        return substring(str, 0, endIndex, true);
    }

    /**
     * 裁剪字符串
     * @param str      待裁剪字符串
     * @param endIndex 结束裁剪的位置
     * @param isReturn 开始位置超过限制是否返回内容
     * @return 裁剪后的字符串
     */
    public static String substring(
            final String str,
            final int endIndex,
            final boolean isReturn
    ) {
        return substring(str, 0, endIndex, isReturn);
    }

    /**
     * 裁剪字符串
     * @param str        待裁剪字符串
     * @param beginIndex 开始裁剪的位置
     * @param endIndex   结束裁剪的位置
     * @param isReturn   开始位置超过限制是否返回内容
     * @return 裁剪后的字符串
     */
    public static String substring(
            final String str,
            final int beginIndex,
            final int endIndex,
            final boolean isReturn
    ) {
        if (!isEmpty(str) && beginIndex >= 0 && endIndex >= 0 && endIndex >= beginIndex) {
            // 获取数据长度
            int len = length(str);
            // 防止超过限制
            if (beginIndex > len) {
                return isReturn ? str : "";
            }
            // 防止超过限制
            return str.substring(beginIndex, Math.min(endIndex, len));
        }
        return isReturn ? str : "";
    }

    // =

    /**
     * 替换特定字符串开头、结尾的字符串
     * 如 _____a_a_a_a_____ 传入 _ 等于 ____a_a_a_a____
     * @param str    待处理字符串
     * @param suffix 替换符号字符串
     * @return 处理后的字符串
     */
    public static String replaceSEWith(
            final String str,
            final String suffix
    ) {
        return replaceSEWith(str, suffix, "");
    }

    /**
     * 替换特定字符串开头、结尾的字符串
     * 如 _____a_a_a_a_____ 传入 _, c 等于 c____a_a_a_a____c
     * @param str     待处理字符串
     * @param suffix  替换匹配内容
     * @param replace 替换的内容
     * @return 处理后的字符串
     */
    public static String replaceSEWith(
            final String str,
            final String suffix,
            final String replace
    ) {
        try {
            if (isEmpty(str) || isEmpty(suffix) || replace == null || suffix.equals(replace)) {
                return str;
            }
            // 获取编辑内容长度
            int suffixLength = suffix.length();
            // 保存新的 Builder 中, 减少内存开销
            StringBuilder builder = new StringBuilder(str);
            // 判断是否在最头部
            if (builder.indexOf(suffix) == 0) {
                builder.delete(0, suffixLength);
                // 追加内容
                builder.insert(0, replace);
            }
            // 获取尾部的位置
            int lastIndexOf = builder.lastIndexOf(suffix);
            // 数据长度
            int bufLength = builder.length();
            // 判断是否在最尾部
            if (lastIndexOf != -1 && lastIndexOf == (bufLength - suffixLength)) {
                builder.delete(lastIndexOf, bufLength);
                // 追加内容
                builder.insert(lastIndexOf, replace);
            }
            return builder.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "replaceSEWith");
        }
        return str;
    }

    // =

    /**
     * 替换开头字符串
     * @param str    待处理字符串
     * @param prefix 开头匹配字符串
     * @return 处理后的字符串
     */
    public static String replaceStartsWith(
            final String str,
            final String prefix
    ) {
        return replaceStartsWith(str, prefix, "");
    }

    /**
     * 替换开头字符串
     * @param str         待处理字符串
     * @param prefix      开头匹配字符串
     * @param startAppend 开头追加的内容
     * @return 处理后的字符串
     */
    public static String replaceStartsWith(
            final String str,
            final String prefix,
            final String startAppend
    ) {
        if (!isEmpty(str) && !isEmpty(prefix)) {
            try {
                if (str.startsWith(prefix)) {
                    return checkValue(startAppend) + str.substring(prefix.length());
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "replaceStartsWith");
            }
        }
        return str;
    }

    /**
     * 替换结尾字符串
     * @param str    待处理字符串
     * @param suffix 结尾匹配字符串
     * @return 处理后的字符串
     */
    public static String replaceEndsWith(
            final String str,
            final String suffix
    ) {
        return replaceEndsWith(str, suffix, "");
    }

    /**
     * 替换结尾字符串
     * @param str     待处理字符串
     * @param suffix  结尾匹配字符串
     * @param replace 替换的内容
     * @return 处理后的字符串
     */
    public static String replaceEndsWith(
            final String str,
            final String suffix,
            final String replace
    ) {
        if (!isEmpty(str) && !isEmpty(suffix)) {
            try {
                if (str.endsWith(suffix)) {
                    return str.substring(0, str.length() - suffix.length()) + replace;
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "replaceEndsWith");
            }
        }
        return str;
    }

    // =

    /**
     * 清空特定字符串开头、结尾的字符串
     * 如 _____a_a_a_a_____ 传入 _ 等于 a_a_a_a
     * @param str    待处理字符串
     * @param suffix 匹配判断字符串
     * @return 处理后的字符串
     */
    public static String clearSEWiths(
            final String str,
            final String suffix
    ) {
        if (isEmpty(str) || isEmpty(suffix)) return str;
        try {
            // 获取编辑内容长度
            int suffixLength = suffix.length();
            // 保存新的 Builder 中, 减少内存开销
            StringBuilder builder = new StringBuilder(str);
            // 进行循环判断 ( 属于最前面的, 才进行处理 )
            while (builder.indexOf(suffix) == 0) {
                builder.delete(0, suffixLength);
            }
            // 获取尾部的位置
            int lastIndexOf = builder.lastIndexOf(suffix);
            // 数据长度
            int bufLength = builder.length();
            // 进行循环判断 ( 属于最后面的, 才进行处理 )
            while (lastIndexOf != -1 && lastIndexOf == (bufLength - suffixLength)) {
                builder.delete(lastIndexOf, bufLength);
                // 重置数据
                lastIndexOf = builder.lastIndexOf(suffix);
                bufLength   = builder.length();
            }
            return builder.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "clearSEWiths");
        }
        return str;
    }

    /**
     * 清空特定字符串开头的字符串
     * 如 _____a_a_a_a_____ 传入 _ 等于 a_a_a_a_____
     * @param str    待处理字符串
     * @param suffix 匹配判断字符串
     * @return 处理后的字符串
     */
    public static String clearStartsWith(
            final String str,
            final String suffix
    ) {
        if (isEmpty(str) || isEmpty(suffix)) return str;
        try {
            // 获取编辑内容长度
            int suffixLength = suffix.length();
            // 保存新的 Builder 中, 减少内存开销
            StringBuilder builder = new StringBuilder(str);
            // 进行循环判断 ( 属于最前面的, 才进行处理 )
            while (builder.indexOf(suffix) == 0) {
                builder.delete(0, suffixLength);
            }
            return builder.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "clearStartsWith");
        }
        return str;
    }

    /**
     * 清空特定字符串结尾的字符串
     * 如 _____a_a_a_a_____ 传入 _ 等于 _____a_a_a_a
     * @param str    待处理字符串
     * @param suffix 匹配判断字符串
     * @return 处理后的字符串
     */
    public static String clearEndsWith(
            final String str,
            final String suffix
    ) {
        if (isEmpty(str) || isEmpty(suffix)) return str;
        try {
            // 获取编辑内容长度
            int suffixLength = suffix.length();
            // 保存新的 Builder 中, 减少内存开销
            StringBuilder builder = new StringBuilder(str);
            // 获取尾部的位置
            int lastIndexOf = builder.lastIndexOf(suffix);
            // 数据长度
            int bufLength = builder.length();
            // 进行循环判断 ( 属于最后面的, 才进行处理 )
            while (lastIndexOf != -1 && lastIndexOf == (bufLength - suffixLength)) {
                builder.delete(lastIndexOf, bufLength);
                // 重置数据
                lastIndexOf = builder.lastIndexOf(suffix);
                bufLength   = builder.length();
            }
            return builder.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "clearEndsWith");
        }
        return str;
    }

    // =

    /**
     * 替换字符串
     * @param str     待处理字符串
     * @param suffix  匹配判断字符串
     * @param replace 替换的内容
     * @return 处理后的字符串
     */
    public static String replaceAll(
            final String str,
            final String suffix,
            final String replace
    ) {
        // 如果替换的内容或者判断的字符串为 null, 则直接跳过
        if (!isEmpty(str) && !isEmpty(suffix) && replace != null && !suffix.equals(replace)) {
            try {
                return str.replaceAll(suffix, replace);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "replaceAll");
            }
        }
        return str;
    }

    /**
     * 替换字符串
     * @param str     待处理字符串
     * @param suffix  匹配判断字符串
     * @param replace 替换的内容
     * @return 处理后的字符串, 替换失败则返回 null
     */
    public static String replaceAllToNull(
            final String str,
            final String suffix,
            final String replace
    ) {
        // 如果替换的内容或者判断的字符串为 null, 则直接跳过
        if (!isEmpty(str) && !isEmpty(suffix) && replace != null && !suffix.equals(replace)) {
            try {
                return str.replaceAll(suffix, replace);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "replaceAllToNull");
            }
        }
        return null;
    }

    /**
     * 替换字符串
     * @param str           待处理字符串
     * @param suffixArrays  匹配判断字符串数组
     * @param replaceArrays 准备替换的字符串数组
     * @return 处理后的字符串
     */
    public static String replaceAlls(
            final String str,
            final String[] suffixArrays,
            final String[] replaceArrays
    ) {
        // 防止数据为 null
        if (str != null && suffixArrays != null && replaceArrays != null) {
            String tempString = str;
            // 替换的特殊字符串长度
            int spCount = suffixArrays.length;
            // 替换的内容长度
            int reCount = replaceArrays.length;
            // 相同才进行处理
            if (spCount == reCount) {
                // 遍历进行判断
                for (int i = 0; i < spCount; i++) {
                    // 进行替换字符串
                    tempString = replaceAll(tempString, suffixArrays[i], replaceArrays[i]);
                }
                return tempString;
            }
        }
        return null;
    }

    /**
     * 拆分字符串
     * @param str   待处理字符串
     * @param regex 正则表达式
     * @return 拆分后的数组
     */
    public static String[] split(
            final String str,
            final String regex
    ) {
        if (!StringUtils.isEmpty(str) && !StringUtils.isEmpty(regex)) {
            return str.split(regex);
        }
        return null;
    }

    /**
     * 拆分字符串获取指定索引
     * @param str   待处理字符串
     * @param regex 正则表达式
     * @param index 索引
     * @return 拆分后的数组
     */
    public static String split(
            final String str,
            final String regex,
            final int index
    ) {
        return split(str, regex, index, null);
    }

    /**
     * 拆分字符串获取指定索引
     * @param str        待处理字符串
     * @param regex      正则表达式
     * @param index      索引
     * @param defaultStr 默认字符串
     * @return 拆分后的数组
     */
    public static String split(
            final String str,
            final String regex,
            final int index,
            final String defaultStr
    ) {
        if (index >= 0) {
            String[] arrays = split(str, regex);
            if (arrays != null && arrays.length > index) {
                return arrays[index];
            }
        }
        return defaultStr;
    }
}