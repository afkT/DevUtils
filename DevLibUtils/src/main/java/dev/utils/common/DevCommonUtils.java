package dev.utils.common;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import dev.utils.JCLogUtils;

/**
 * detail: 开发常用方法工具类
 * Created by Ttt
 */
public final class DevCommonUtils {

    private DevCommonUtils() {
    }

    // 日志 TAG
    private static final String TAG = DevCommonUtils.class.getSimpleName();
    // 换行字符串
    public static final String NEW_LINE_STR = System.getProperty("line.separator");
    // 换行字符串 - 两行
    public static final String NEW_LINE_STR_X2 = NEW_LINE_STR + NEW_LINE_STR;

    /**
     * 计算百分比值 (最大 100%)
     * @param value
     * @param max
     * @return
     */
    public static float percent(final float value, final float max) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        if (value >= max) return 1.0f;
        return value / max;
    }

    /**
     * 计算百分比值 (最大 100%)
     * @param value
     * @param max
     * @return
     */
    public static float percent(final int value, final int max) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        if (value >= max) return 1.0f;
        return (float) value / (float) max;
    }

    /**
     * 计算百分比值 (可超出 100%)
     * @param value
     * @param max
     * @return
     */
    public static float percent2(final float value, final float max) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        return value / max;
    }

    /**
     * 计算百分比值 (可超出 100%)
     * @param value
     * @param max
     * @return
     */
    public static float percent2(final int value, final int max) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        return (float) value / (float) max;
    }

    /**
     * 返回的 value 介于 max、min之间，若 value 小于min，返回min，若大于max，返回max
     * @param value
     * @param max
     * @param min
     * @return
     */
    public static int clamp(final int value, final int max, final int min) {
        return value > max ? max : value < min ? min : value;
    }

    /**
     * 返回的 value 介于 max、min之间，若 value 小于min，返回min，若大于max，返回max
     * @param value
     * @param max
     * @param min
     * @return
     */
    public static float clamp(final float value, final float max, final float min) {
        return value > max ? max : value < min ? min : value;
    }

    // =

    /**
     * 获取格式化后的字符串
     * @param format
     * @param args
     * @return
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
     * @param format
     * @param args
     * @return
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

    /**
     * 获取自动数量格式化后的字符串(可变参数)
     * @param args
     * @return
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
     * @param args
     * @return
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

    // =

    /**
     * 判断是否网络资源
     * @param str 资源地址字符串
     * @return
     */
    public static boolean isHttpRes(final String str) {
        if (!isEmpty(str)) {
            // 属于第一位开始, 才是属于网络资源
            if (str.toLowerCase().startsWith("http:") ||
                    str.toLowerCase().startsWith("https:")) {
                return true;
            }
        }
        return false;
    }

    // =

    /**
     * 判断字符串是否为 null 或全为空白字符
     * @param str 待校验字符串
     * @return
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
     * 追加空格
     * @param number 空格数量
     * @return
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
     * @return
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
     * @return
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

    // = 判断数据是否为null =

    /**
     * 判断是否为 null
     * @param str
     * @return
     */
    public static boolean isEmpty(final String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 判断是否为 null
     * @param str
     * @param isTrim
     * @return
     */
    public static boolean isEmpty(final String str, final boolean isTrim) {
        if (str == null) {
            return true;
        } else {
            if (isTrim) {
                return (str.trim().length() == 0);
            } else {
                return (str.length() == 0);
            }
        }
    }

    /**
     * 判断多个字符串是否为null
     * @param strs
     * @return
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

    /**
     * 判断是否为 null 或者为空格
     * @param str
     * @return
     */
    public static boolean isTrimEmpty(final String str) {
        return (str == null || str.trim().length() == 0);
    }

    // =

    /**
     * 判断是否为 null to Object
     * @param object
     * @return
     */
    public static boolean isEmpty(final Object object) {
        if (object != null) {
            // 判断是否属于基本类型数组
            if (object.getClass().isArray()) {
                try {
                    Class<?> clazz = object.getClass();
                    // = 基本数据类型 =
                    if (clazz.isAssignableFrom(int[].class)) {
                        return (((int[]) object).length == 0);
                    } else if (clazz.isAssignableFrom(boolean[].class)) {
                        return (((boolean[]) object).length == 0);
                    } else if (clazz.isAssignableFrom(long[].class)) {
                        return (((long[]) object).length == 0);
                    } else if (clazz.isAssignableFrom(double[].class)) {
                        return (((double[]) object).length == 0);
                    } else if (clazz.isAssignableFrom(float[].class)) {
                        return (((float[]) object).length == 0);
                    } else if (clazz.isAssignableFrom(byte[].class)) {
                        return (((byte[]) object).length == 0);
                    } else if (clazz.isAssignableFrom(char[].class)) {
                        return (((char[]) object).length == 0);
                    } else if (clazz.isAssignableFrom(short[].class)) {
                        return (((short[]) object).length == 0);
                    }
                } catch (Exception e) {
                }
            }
            return false;
        }
        return true;
    }

    /**
     * 判断是否为null to 数组
     * @param objects
     * @return
     */
    public static boolean isEmpty(final Object[] objects) {
        return objects == null || objects.length == 0;
    }

    /**
     * 判读是否为 null to List
     * @param list
     * @return
     */
    public static boolean isEmpty(final List list) {
        return list == null || list.size() == 0;
    }

    /**
     * 判读是否为 null to Map
     * @param map
     * @return
     */
    public static boolean isEmpty(final Map map) {
        return map == null || map.size() == 0;
    }

    /**
     * 判读是否为 null to Set
     * @param set
     * @return
     */
    public static boolean isEmpty(final Set set) {
        return set == null || set.size() == 0;
    }

    /**
     * 判读是否为 null to Queue
     * @param queue
     * @return
     */
    public static boolean isEmpty(final Queue queue) {
        return queue == null || queue.size() == 0;
    }

    /**
     * 判读是否为 null to 可变数组
     * @param args
     * @return
     */
    public static boolean isEmptyObjs(final Object... args) {
        return args == null || args.length == 0;
    }

    // = 判断数据长度 =

    /**
     * 获取长度，如果字符串为null, 则返回 0
     * @param str
     * @return
     */
    public static int length(final String str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 获取数组长度
     * @param objects
     * @return
     */
    public static int length(final Object[] objects) {
        return length(objects, 0);
    }

    /**
     * 获取长度 to List
     * @param list
     * @return
     */
    public static int length(final List list) {
        return length(list, 0);
    }

    /**
     * 获取长度 to Map
     * @param map
     * @return
     */
    public static int length(final Map map) {
        return length(map, 0);
    }

    /**
     * 获取长度 to Set
     * @param set
     * @return
     */
    public static int length(final Set set) {
        return length(set, 0);
    }

    /**
     * 获取长度 to Queue
     * @param queue
     * @return
     */
    public static int length(final Queue queue) {
        return length(queue, 0);
    }

    // =

    /**
     * 获取字符串长度
     * @param str
     * @param defaultLength
     * @return
     */
    public static int length(final String str, final int defaultLength) {
        return str != null ? str.length() : defaultLength;
    }

    /**
     * 获取数组长度
     * @param objects
     * @param defaultLength
     * @return
     */
    public static int length(final Object[] objects, final int defaultLength) {
        return objects != null ? objects.length : defaultLength;
    }

    /**
     * 获取长度 to List
     * @param list
     * @param defaultLength
     * @return
     */
    public static int length(final List list, final int defaultLength) {
        return list != null ? list.size() : defaultLength;
    }

    /**
     * 获取长度 to Map
     * @param map
     * @param defaultLength
     * @return
     */
    public static int length(final Map map, final int defaultLength) {
        return map != null ? map.size() : defaultLength;
    }

    /**
     * 获取长度 to Set
     * @param set
     * @param defaultLength
     * @return
     */
    public static int length(final Set set, final int defaultLength) {
        return set != null ? set.size() : defaultLength;
    }

    /**
     * 获取长度 to Queue
     * @param queue
     * @param defaultLength
     * @return
     */
    public static int length(final Queue queue, final int defaultLength) {
        return queue != null ? queue.size() : defaultLength;
    }

    // =

    /**
     * 获取可变数组长度
     * @param args
     * @return
     */
    public static int lengthObjs(final Object... args) {
        return lengthObjsDf(0, args);
    }

    /**
     * 获取可变数组长度
     * @param args
     * @param defaultLength
     * @return
     */
    public static int lengthObjsDf(final int defaultLength, final Object... args) {
        return args != null ? args.length : defaultLength;
    }

    // =

    /**
     * 获取字符串长度 是否等于期望长度
     * @param str    字符串
     * @param length 期望长度
     * @return
     */
    public static boolean isLength(final String str, final int length) {
        return str != null && str.length() == length;
    }

    /**
     * 获取数组长度 是否等于期望长度
     * @param objects
     * @param length
     * @return
     */
    public static boolean isLength(final Object[] objects, final int length) {
        return objects != null && objects.length == length;
    }

    /**
     * 获取长度 to List 是否等于期望长度
     * @param list
     * @param length
     * @return
     */
    public static boolean isLength(final List list, final int length) {
        return list != null && list.size() == length;
    }

    /**
     * 获取长度 to Map 是否等于期望长度
     * @param map
     * @param length
     * @return
     */
    public static boolean isLength(final Map map, final int length) {
        return map != null && map.size() == length;
    }

    /**
     * 获取长度 to Set 是否等于 期望长度
     * @param set
     * @param length
     * @return
     */
    public static boolean isLength(final Set set, final int length) {
        return set != null && set.size() == length;
    }

    /**
     * 获取长度 to Queue 是否等于 期望长度
     * @param queue
     * @param length
     * @return
     */
    public static boolean isLength(final Queue queue, final int length) {
        return queue != null && queue.size() == length;
    }

    // =

    /**
     * 判断两字符串是否相等
     * @param a 待校验字符串 a
     * @param b 待校验字符串 b
     * @return true : 相等, false : 不相等
     */
    public static boolean equals(final CharSequence a, final CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断多个字符串是否相等, 只有全相等才返回 true - 对比大小写
     * @param args
     * @return
     */
    public static boolean isEquals(final String... args) {
        return isEquals(false, args);
    }

    /**
     * 判断多个字符串是否相等, 只有全相等才返回 true
     * @param isIgnore 是否忽略大小写
     * @param args
     * @return
     */
    public static boolean isEquals(final boolean isIgnore, final String... args) {
        if (args != null) {
            String last = null;
            // 获取数据长度
            int len = args.length;
            // 如果最多只有一个数据判断, 则直接跳过
            if (len <= 1) {
                return false;
            }
            // 遍历判断
            for (int i = 0; i < len; i++) {
                // 获取临时变量
                String val = args[i];
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
     * @param str
     * @param args
     * @return
     */
    public static boolean isOrEquals(final String str, final String... args) {
        return isOrEquals(false, str, args);
    }

    /**
     * 判断多个字符串,只要有一个符合条件,则通过
     * @param isIgnore 是否忽略大小写
     * @param str
     * @param args
     * @return
     */
    public static boolean isOrEquals(final boolean isIgnore, final String str, final String... args) {
        if (str != null && args != null && args.length != 0) {
            // 获取数据长度
            int len = args.length;
            // 遍历判断
            for (int i = 0; i < len; i++) {
                // 获取临时变量
                String val = args[i];
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
     * @param str
     * @param args
     * @return
     */
    public static boolean isContains(final String str, final String... args) {
        return isContains(false, str, args);
    }

    /**
     * 判断一堆值中，是否存在符合该条件的(包含)
     * @param isIgnore
     * @param str
     * @param args
     * @return
     */
    public static boolean isContains(final boolean isIgnore, final String str, final String... args) {
        if (str != null && args != null && args.length != 0) {
            String strTemp = str;
            // 判断是否需要忽略大小写
            if (isIgnore) {
                strTemp = strTemp.toLowerCase();
            }
            // 获取内容长度
            int cLength = strTemp.length();
            // 遍历判断
            for (int i = 0, len = args.length; i < len; i++) {
                // 获取参数
                String val = args[i];
                // 判断是否为null,或者长度为0
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
     * @param str
     * @param args
     * @return
     */
    public static boolean isStartsWith(final String str, final String... args) {
        return isStartsWith(false, str, args);
    }

    /**
     * 判断内容, 是否属于特定字符串开头
     * @param isIgnore 是否忽略大小写
     * @param str
     * @param args
     * @return
     */
    public static boolean isStartsWith(final boolean isIgnore, final String str, final String... args) {
        if (!isEmpty(str) && args != null && args.length != 0) {
            String strTemp = str;
            // 判断是否需要忽略大小写
            if (isIgnore) {
                strTemp = strTemp.toLowerCase();
            }
            // 获取数据长度
            int len = args.length;
            // 遍历判断
            for (int i = 0; i < len; i++) {
                // 获取临时变量
                String val = args[i];
                // 判断是否为null,或者长度为0
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
     * @param str
     * @param args
     * @return
     */
    public static boolean isEndsWith(final String str, final String... args) {
        return isEndsWith(false, str, args);
    }

    /**
     * 判断内容, 是否属于特定字符串结尾
     * @param isIgnore 是否忽略大小写
     * @param str
     * @param args
     * @return
     */
    public static boolean isEndsWith(final boolean isIgnore, final String str, final String... args) {
        if (!isEmpty(str) && args != null && args.length != 0) {
            String strTemp = str;
            // 判断是否需要忽略大小写
            if (isIgnore) {
                strTemp = strTemp.toLowerCase();
            }
            // 获取数据长度
            int len = args.length;
            // 遍历判断
            for (int i = 0; i < len; i++) {
                // 获取临时变量
                String val = args[i];
                // 判断是否为null,或者长度为0
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

    // =

    /**
     * 清空全部空格,并返回处理后的字符串
     * @param str
     * @return
     */
    public static String toClearSpace(final String str) {
        if (isEmpty(str)) return str;
        return str.replaceAll(" ", "");
    }

    /**
     * 清空前后空格,并返回处理后的字符串
     * @param str
     * @return
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
     * 检查字符串,如果为null,返回 ""
     * @param str
     * @return
     */
    public static String toCheckValue(final String str) {
        return toCheckValue("", str);
    }

    /**
     * 检查字符串,如果为null,返回 默认字符串
     * @param defaultStr
     * @param str
     * @return
     */
    public static String toCheckValue(final String defaultStr, final String str) {
        return isEmpty(str) ? defaultStr : str;
    }

    /**
     * 单独检查两个值,减少循环，不直接调用toCheckValues
     * @param defaultStr
     * @param value1
     * @param value2
     * @return
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
     * 检查多个值, 并返回第一个非null and "" 的字符串,如果都不符合条件，则返回默认值
     * @param defaultStr
     * @param params
     * @return
     */
    public static String toCheckValues(final String defaultStr, final String... params) {
        if (params != null && params.length != 0) {
            for (int i = 0, len = params.length; i < len; i++) {
                String param = params[i];
                if (isEmpty(param)) {
                    if (i == len - 1) {
                        return defaultStr; // 属于最后一个,则返回默认值
                    } else {
                        continue; // 不属于最后一个则跳过
                    }
                } else {
                    return param;
                }
            }
        }
        return defaultStr;
    }

    /**
     * 检查多个值,并返回第一个非null and "" and 全部不是属于空格 的字符串,如果都不符合条件，则返回默认值
     * @param defaultStr
     * @param params
     * @return
     */
    public static String toCheckValuesSpace(final String defaultStr, final String... params) {
        if (params != null && params.length != 0) {
            for (int i = 0, len = params.length; i < len; i++) {
                // 处理后, 进行返回 => 删除前后空格
                String param = toClearSpaceTrim(params[i]);
                if (isEmpty(param)) {
                    if (i == len - 1) {
                        return defaultStr; // 属于最后一个,则返回默认值
                    } else {
                        continue; // 不属于最后一个则跳过
                    }
                } else {
                    return param;
                }
            }
        }
        return defaultStr;
    }

    /**
     * 裁减超出的内容, 并且追加符号(如 ...)
     * @param maxLength 允许最大的长度
     * @param str
     * @param symbol
     * @return
     */
    public static String subEllipsize(final int maxLength, final String str, final String symbol) {
        if (maxLength >= 1) {
            // 获取内容长度
            int strLength = length(str);
            // 防止为不存在数据
            if (strLength != 0) {
                if (maxLength >= strLength) {
                    return str;
                }
                return str.substring(0, maxLength) + toCheckValue(symbol);
            }
        }
        return "";
    }

    /**
     * 裁剪符号处理
     * @param start        开始位置
     * @param symbolNumber 转换数量
     * @param str
     * @param symbol
     * @return
     */
    public static String subSymbolHide(final int start, final int symbolNumber, final String str, final String symbol) {
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
                StringBuffer buffer = new StringBuffer();
                buffer.append(str.substring(0, start));
                int len = length - start - symbolNumber;
                // 如果超出总长度, 则进行控制
                if (len <= 0) { // 表示后面的全部转换
                    len = length - start;
                } else { // 需要裁剪的数量
                    len = symbolNumber;
                }
                // 进行遍历保存
                for (int i = 0; i < len; i++) {
                    buffer.append(symbol);
                }
                buffer.append(str.substring(start + len, length));
                return buffer.toString();
            }
        }
        return "";
    }

    /**
     * 转换符号处理
     * @param start  开始位置
     * @param str
     * @param symbol
     * @return
     */
    public static String converSymbolHide(final int start, final String str, final String symbol) {
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
                StringBuffer buffer = new StringBuffer();
                buffer.append(str.substring(0, start));
                int len = length - start;
                // 进行平分
                len /= 2;
                // 进行遍历保存
                for (int i = 0; i < len; i++) {
                    buffer.append(symbol);
                }
                buffer.append(str.substring(start + len, length));
                return buffer.toString();
            }
        }
        return "";
    }

    // = 替换截取操作 =

    /**
     * 替换(删除 - 替换成"") 字符串中符合 特定标记字符的 startsWith - endsWith
     * * 如 _____a_a_a_a________  传入 _ 等于 ____a_a_a_a____
     * @param str
     * @param suffix
     * @return
     */
    public static String toReplaceSEWith(final String str, final String suffix) {
        return toReplaceSEWith(str, suffix, "");
    }

    /**
     * 替换字符串中符合 特定标记字符的 startsWith - endsWith
     * 如 _____a_a_a_a________  传入 _ , c 等于 c____a_a_a_a____c
     * @param str
     * @param key
     * @param value 需要替换的内容
     * @return
     */
    public static String toReplaceSEWith(final String str, final String key, final String value) {
        try {
            if (isEmpty(str) || isEmpty(key) || isEmpty(value) || key.equals(value)) return str;
            // 获取编辑内容长度
            int kLength = key.length();
            // 保存新的Buffer中,减少内存开销
            StringBuffer buffer = new StringBuffer(str);
            // 判断是否在最头部
            if (buffer.indexOf(key) == 0) {
                buffer.delete(0, kLength);
                // 追加内容
                buffer.insert(0, value);
            }
            // 获取尾部的位置
            int lastIndexOf = -1;
            // 数据长度
            int bufLength = -1;
            // 判断是否在最尾部
            if ((lastIndexOf = buffer.lastIndexOf(key)) == ((bufLength = buffer.length()) - kLength)) {
                buffer.delete(lastIndexOf, bufLength);
                // 追加内容
                buffer.insert(lastIndexOf, value);
            }
            return buffer.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toReplaceSEWith");
        }
        return str;
    }

    /**
     * (这个方法功能主要把字符符合标记的 头部和尾部都替换成 "")
     * 如 _____a_a_a_a________  传入 _ 等于 a_a_a_a
     * 替换字符串中符合 特定标记字符的 startsWith(indexOf) - endsWith(lastIndexOf) ,while
     * @param str
     * @param suffix
     * @return
     */
    public static String toClearSEWiths(final String str, final String suffix) {
        if (isEmpty(str) || isEmpty(suffix)) return str;
        try {
            // 获取编辑内容长度
            int kLength = suffix.length();
            // 保存新的Buffer中,减少内存开销
            StringBuffer buffer = new StringBuffer(str);
            // 进行循环判断 - 属于最前面的,才进行处理
            while (buffer.indexOf(suffix) == 0) {
                buffer.delete(0, kLength);
            }
            // 获取尾部的位置
            int lastIndexOf = -1;
            // 数据长度
            int bufLength = -1;
            // 进行循环判断 - 属于最后面的,才进行处理
            while ((lastIndexOf = buffer.lastIndexOf(suffix)) == ((bufLength = buffer.length()) - kLength)) {
                buffer.delete(lastIndexOf, bufLength);
            }
            return buffer.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toClearSEWiths");
        }
        return str;
    }

    /**
     * 裁剪字符串
     * @param str      需要裁剪的字符串
     * @param endIndex 结束裁剪的位置
     * @return
     */
    public static String substring(final String str, final int endIndex) {
        return substring(str, 0, endIndex, true);
    }

    /**
     * 裁剪字符串
     * @param str      需要裁剪的字符串
     * @param endIndex 结束裁剪的位置
     * @param isReturn 开始位置超过限制是否返回内容
     * @return
     */
    public static String substring(final String str, final int endIndex, final boolean isReturn) {
        return substring(str, 0, endIndex, isReturn);
    }

    /**
     * 裁剪字符串
     * @param str        需要裁剪的字符串
     * @param beginIndex 开始裁剪的位置
     * @param endIndex   结束裁剪的位置
     * @param isReturn   开始位置超过限制是否返回内容
     * @return
     */
    public static String substring(final String str, final int beginIndex, final int endIndex, final boolean isReturn) {
        if (!isEmpty(str) && beginIndex >= 0 && endIndex >= 0 && endIndex >= beginIndex) {
            // 获取数据长度
            int len = length(str);
            // 防止超过限制
            if (beginIndex > len) {
                return isReturn ? str : "";
            }
            // 防止超过限制
            return str.substring(beginIndex, (endIndex >= len) ? len : endIndex);
        }
        return isReturn ? str : "";
    }

    // =

    /**
     * 替换开头字符串
     * @param str    内容
     * @param prefix
     * @return
     */
    public static String toReplaceStartsWith(final String str, final String prefix) {
        return toReplaceStartsWith(str, prefix, "");
    }

    /**
     * 替换开头字符串
     * @param str         内容
     * @param suffix
     * @param startAppend 开头追加的内容
     * @return
     */
    public static String toReplaceStartsWith(final String str, final String suffix, final String startAppend) {
        if (!isEmpty(str) && !isEmpty(suffix)) {
            try {
                if (str.startsWith(suffix)) {
                    return toCheckValue(startAppend) + str.substring(suffix.length());
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toReplaceStartsWith");
            }
        }
        return str;
    }

    /**
     * 清空属于特定字符串开头的字段
     * 如 _____a_a_a_a________  传入 _ 等于 a_a_a_a_____
     * 替换字符串中符合 特定标记字符的 endsWith(lastIndexOf) ,while
     * @param str
     * @param suffix
     * @return
     */
    public static String toClearStartsWith(final String str, final String suffix) {
        if (isEmpty(str) || isEmpty(suffix)) return str;
        try {
            // 获取编辑内容长度
            int kLength = suffix.length();
            // 保存新的Buffer中,减少内存开销
            StringBuffer buffer = new StringBuffer(str);
            // 进行循环判断 - 属于最前面的,才进行处理
            while (buffer.indexOf(suffix) == 0) {
                buffer.delete(0, kLength);
            }
            return buffer.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toClearStartsWith");
        }
        return str;
    }

    // =

    /**
     * 替换结尾字符串
     * @param str    内容
     * @param suffix
     * @return
     */
    public static String toReplaceEndsWith(final String str, final String suffix) {
        return toReplaceEndsWith(str, suffix, "");
    }

    /**
     * 替换结尾字符串
     * @param str    内容
     * @param suffix
     * @param value  需要替换的内容
     * @return
     */
    public static String toReplaceEndsWith(final String str, final String suffix, final String value) {
        if (!isEmpty(str) && !isEmpty(suffix)) {
            try {
                if (str.endsWith(suffix)) {
                    return str.substring(0, str.length() - suffix.length()) + value;
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toReplaceEndsWith");
            }
        }
        return str;
    }

    /**
     * 清空属于特定字符串结尾的字段
     * 如 _____a_a_a_a________  传入 _ 等于 _____a_a_a_a
     * 替换字符串中符合 特定标记字符的 endsWith(lastIndexOf) ,while
     * @param str
     * @param suffix
     * @return
     */
    public static String toClearEndsWith(final String str, final String suffix) {
        if (isEmpty(str) || isEmpty(suffix)) return str;
        try {
            // 获取编辑内容长度
            int kLength = suffix.length();
            // 保存新的Buffer中,减少内存开销
            StringBuffer buffer = new StringBuffer(str);
            // 获取最后一位位置
            int sLength = 0;
            // 进行循环判断 - 属于最前面的,才进行处理
            while (buffer.lastIndexOf(suffix) == ((sLength = buffer.length()) - kLength)) {
                buffer.delete(sLength - kLength, sLength);
            }
            return buffer.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toClearEndsWith");
        }
        return str;
    }

    // =

    /**
     * 替换字符串
     * @param str         内容
     * @param suffixArys
     * @param replaceArys
     * @return
     */
    public static String replaceStrs(final String str, final String[] suffixArys, final String[] replaceArys) {
        // 防止数据为null
        if (str != null && suffixArys != null && replaceArys != null) {
            String cStr = str;
            // 替换的特殊字符串长度
            int spCount = suffixArys.length;
            // 替换的内容长度
            int reCount = replaceArys.length;
            // 相同才进行处理
            if (spCount == reCount) {
                // 遍历进行判断
                for (int i = 0; i < spCount; i++) {
                    // 进行替换字符串
                    cStr = replaceStr(cStr, suffixArys[i], replaceArys[i]);
                }
                // 最终不为null,则进行返回
                return cStr;
            }
        }
        return null;
    }

    /**
     * 替换字符串
     * @param str     需要替换的内容
     * @param suffix  特殊的字符串
     * @param replace 替换的内容
     * @return
     */
    public static String replaceStr(final String str, final String suffix, final String replace) {
        // 如果替换的内容或者判断的字符串为null,则直接跳过
        if (!isEmpty(str) && !isEmpty(suffix) && replace != null && !suffix.equals(replace)) {
            try {
                return str.replaceAll(suffix, replace);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "replaceStr");
            }
        }
        return str;
    }

    /**
     * 替换字符串
     * @param str     需要替换的内容
     * @param suffix  特殊的字符串
     * @param replace 替换的内容
     * @return 如果异常则直接返回null
     */
    public static String replaceStrToNull(final String str, final String suffix, final String replace) {
        // 如果替换的内容或者判断的字符串为null,则直接跳过
        if (!isEmpty(str) && !isEmpty(suffix) && replace != null && !suffix.equals(replace)) {
            try {
                return str.replaceAll(suffix, replace);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "replaceStrToNull");
            }
        }
        return null;
    }
}
