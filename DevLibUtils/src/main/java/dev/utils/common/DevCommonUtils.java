package dev.utils.common;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import dev.utils.JCLogUtils;

/**
 * detail: 开发常用方法 - 工具类
 * Created by Ttt
 */
public final class DevCommonUtils {

    private DevCommonUtils() {
    }

    // 日志TAG
    private static final String TAG = DevCommonUtils.class.getSimpleName();
    /** 换行字符串 */
    public static final String NEW_LINE_STR = System.getProperty("line.separator");
    /** 换行字符串 - 两行 */
    public static final String NEW_LINE_STR_X2 = NEW_LINE_STR + NEW_LINE_STR;

    /**
     * 获取格式化字符串
     * @param format
     * @param args
     * @return
     */
    public static String getFormatString(String format, Object... args) {
        try {
            return String.format(format, args);
        } catch (Exception e){
            JCLogUtils.eTag(TAG, e, "getFormatString");
        }
        return null;
    }

    /**
     * 获取格式化字符串
     * @param format
     * @param args
     * @return
     */
    public static String getFormatString2(String format, Object... args) {
        try {
            if (args != null && args.length != 0){
                return String.format(format, args);
            } else {
                return format;
            }
        } catch (Exception e){
            JCLogUtils.eTag(TAG, e, "getFormatString2");
        }
        return null;
    }

    /**
     * 判断是否网络资源
     * @param resPath 资源地址
     * @return
     */
    public static boolean isHttpRes(String resPath){
        if (!isEmpty(resPath)){
            // 属于第一位开始, 才是属于网络资源
            if (resPath.toLowerCase().startsWith("http:") ||
                    resPath.toLowerCase().startsWith("https:")){
                return true;
            }
        }
        return false;
    }

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
     * 获取空格
     * @param number 空格数量
     * @return
     */
    private static String getSpace(int number){
        StringBuffer buffer = new StringBuffer();
        // 循环空格
        for (int i = 0; i < number; i++){
            buffer.append(" ");
        }
        return buffer.toString();
    }

    /**
     * 获取 Tab
     * @param number tab 键数量
     * @return
     */
    private static String getTab(int number){
        StringBuffer buffer = new StringBuffer();
        // 循环空格
        for (int i = 0; i < number; i++){
            buffer.append("\t");
        }
        return buffer.toString();
    }

    // ========================
    // == 判断数据是否为null ==
    // ========================

    /**
     * 判断是否为null
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 判断字符串是否为 null 或全为空格
     * @param str
     * @return
     */
    public static boolean isTrimEmpty(final String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 判断多个字符串是否为null
     * @param strs
     * @return
     */
    public static boolean isEmpty(String... strs){
        if (strs != null && strs.length != 0){
            for (int i = 0, len = strs.length; i < len; i++){
                if (isEmpty(strs[i])){
                    return true;
                }
            }
            return false;
        }
        // 默认表示属于null
        return true;
    }

    // ====================
    // 单独需要判断是否为null,而不需要判断长度,只需要调用length方法
    // length(list, -1) == -1  =>  true 表示为null, false 表示不为null,存在数据(可能返回0)
    // ====================

    /**
     * 判断是否为null to Object
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj){
        if (obj != null){
            // 判断是否属于基本类型数组
            if (obj.getClass().isArray()){
                try {
                    Class<?> cla = obj.getClass();
                    // == 基本数据类型 ==
                    if (cla.isAssignableFrom(int[].class)){
                        return (((int[]) obj).length == 0);
                    } else if (cla.isAssignableFrom(boolean[].class)){
                        return (((boolean[]) obj).length == 0);
                    } else if (cla.isAssignableFrom(long[].class)){
                        return (((long[]) obj).length == 0);
                    } else if (cla.isAssignableFrom(double[].class)){
                        return (((double[]) obj).length == 0);
                    } else if (cla.isAssignableFrom(float[].class)){
                        return (((float[]) obj).length == 0);
                    } else if (cla.isAssignableFrom(byte[].class)){
                        return (((byte[]) obj).length == 0);
                    } else if (cla.isAssignableFrom(char[].class)){
                        return (((char[]) obj).length == 0);
                    } else if (cla.isAssignableFrom(short[].class)){
                        return (((short[]) obj).length == 0);
                    }
                } catch (Exception e){
                }
            }
            return false;
        }
        return true;
    }

    /**
     * 判断是否为null to 数组
     * @param objs
     * @return
     */
    public static boolean isEmpty(Object[] objs){
        if (objs != null){
            return (objs.length == 0);
        }
        return true;
    }

    /**
     * 判读是否为null to List
     * @param list
     * @return
     */
    public static boolean isEmpty(List list){
        if (list != null){
            return (list.size() == 0);
        }
        return true;
    }

    /**
     * 判读是否为null to Map
     * @param map
     * @return
     */
    public static boolean isEmpty(Map map){
        if (map != null){
            return (map.size() == 0);
        }
        return true;
    }

    /**
     * 判读是否为null to Set
     * @param set
     * @return
     */
    public static boolean isEmpty(Set set){
        if (set != null){
            return (set.size() == 0);
        }
        return true;
    }

    /**
     * 判读是否为null to Queue
     * @param queue
     * @return
     */
    public static boolean isEmpty(Queue queue){
        if (queue != null){
            return (queue.size() == 0);
        }
        return true;
    }

    /**
     * 判读是否为null to 可变数组
     * @param args
     * @return
     */
    public static boolean isEmptyObjs(Object... args){
        if (args != null){
            return (args.length == 0);
        }
        return true;
    }

    // ==================
    // == 判断数据长度 ==
    // ==================

    /**
     * 获取长度，如果字符串为null,则返回0
     * @param str
     * @return
     */
    public static int length(String str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 获取数组长度
     * @param objs
     * @return
     */
    public static int length(Object[] objs){
        return length(objs, 0);
    }

    /**
     * 获取长度 to List
     * @param list
     * @return
     */
    public static int length(List list){
        return length(list, 0);
    }

    /**
     * 获取长度 to Map
     * @param map
     * @return
     */
    public static int length(Map map){
        return length(map, 0);
    }

    /**
     * 获取长度 to Set
     * @param set
     * @return
     */
    public static int length(Set set){
        return length(set, 0);
    }

    /**
     * 获取长度 to Queue
     * @param queue
     * @return
     */
    public static int length(Queue queue){
        return length(queue, 0);
    }

    // =

    /**
     * 获取字符串长度
     * @param str
     * @param dfLength
     * @return
     */
    public static int length(String str, int dfLength) {
        if (str != null){
            return str.length();
        }
        return dfLength;
    }

    /**
     * 获取数组长度
     * @param objs
     * @param dfLength
     * @return
     */
    public static int length(Object[] objs, int dfLength){
        if (objs != null){
            return objs.length;
        }
        return dfLength;
    }

    /**
     * 获取长度 to List
     * @param list
     * @param dfLength
     * @return
     */
    public static int length(List list, int dfLength){
        if (list != null){
            return list.size();
        }
        return dfLength;
    }

    /**
     * 获取长度 to Map
     * @param map
     * @param dfLength
     * @return
     */
    public static int length(Map map, int dfLength){
        if (map != null){
            return map.size();
        }
        return dfLength;
    }

    /**
     * 获取长度 to Set
     * @param set
     * @param dfLength
     * @return
     */
    public static int length(Set set, int dfLength){
        if (set != null){
            return set.size();
        }
        return dfLength;
    }

    /**
     * 获取长度 to Queue
     * @param queue
     * @param dfLength
     * @return
     */
    public static int length(Queue queue, int dfLength){
        if (queue != null){
            return queue.size();
        }
        return dfLength;
    }

    // ==

    /**
     * 获取可变数组长度
     * @param args
     * @return
     */
    public static int lengthObjs(Object... args){
        return lengthObjsDf(0, args);
    }

    /**
     * 获取可变数组长度
     * @param args
     * @param dfLength
     * @return
     */
    public static int lengthObjsDf(int dfLength, Object... args){
        if (args != null){
            return args.length;
        }
        return dfLength;
    }

    // ===

    /**
     * 字符串长度匹配
     * @param str 源字符串
     * @param length 期望长度
     * @return
     */
    public static boolean isLength(String str, int length) {
        return str != null && str.length() == length;
    }

    /**
     * 获取数组长度 是否等于 期望长度
     * @param objs
     * @param length
     * @return
     */
    public static boolean isLength(Object[] objs, int length){
        return objs != null && objs.length == length;
    }

    /**
     * 获取长度 to List 是否等于 期望长度
     * @param list
     * @param length
     * @return
     */
    public static boolean isLength(List list, int length){
        return list != null && list.size() == length;
    }

    /**
     * 获取长度 to Map 是否等于 期望长度
     * @param map
     * @param length
     * @return
     */
    public static boolean isLength(Map map, int length){
        return map != null && map.size() == length;
    }

    /**
     * 获取长度 to Set 是否等于 期望长度
     * @param set
     * @param length
     * @return
     */
    public static boolean isLength(Set set, int length){
        return set != null && set.size() == length;
    }

    /**
     * 获取长度 to Queue 是否等于 期望长度
     * @param queue
     * @param length
     * @return
     */
    public static boolean isLength(Queue queue, int length){
        return queue != null && queue.size() == length;
    }

    // ===

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
     * 判断多个字符串是否相等, 只有全相等才返回true - 对比大小写
     * @param args
     * @return
     */
    public static boolean isEquals(String... args) {
        return isEquals(false, args);
    }

    /**
     * 判断多个字符串是否相等, 只有全相等才返回true
     * @param isIgnore 是否忽略大小写
     * @param args
     * @return
     */
    public static boolean isEquals(boolean isIgnore, String... args) {
        if (args != null) {
            String last = null;
            // 获取数据长度
            int len = args.length;
            // 如果最多只有一个数据判断,则直接跳过
            if (len <= 1){
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
     * @param content
     * @param args
     * @return
     */
    public static boolean isOrEquals(String content, String... args){
        return isOrEquals(false, content, args);
    }

    /**
     * 判断多个字符串,只要有一个符合条件,则通过
     * @param isIgnore 是否忽略大小写
     * @param content
     * @param args
     * @return
     */
    public static boolean isOrEquals(boolean isIgnore, String content, String... args){
        if (content != null && args != null && args.length != 0) {
            // 获取数据长度
            int len = args.length;
            // 如果最多只有一个数据判断,则直接跳过
            if (len <= 1){
                return false;
            }
            // 遍历判断
            for (int i = 0; i < len; i++) {
                // 获取临时变量
                String val = args[i];
                // 如果等于null,则跳过
                if (val == null) {
                    continue;
                } else {
                    if (isIgnore) {
                        if (val.equalsIgnoreCase(content)) {
                            return true;
                        }
                    } else {
                        if (val.equals(content)) {
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
     * @param content
     * @param args
     * @return
     */
    public static boolean isContains(String content, String... args){
        return isContains(false, content, args);
    }

    /**
     * 判断一堆值中，是否存在符合该条件的(包含)
     * @param isIgnore
     * @param content
     * @param args
     * @return
     */
    public static boolean isContains(boolean isIgnore, String content, String... args){
        if (content != null && args != null && args.length != 0){
            // 判断是否需要忽略大小写
            if (isIgnore){
                content = content.toLowerCase();
            }
            // 获取内容长度
            int cLength = content.length();
            // 遍历判断
            for (int i = 0, len = args.length; i < len; i++){
                // 获取参数
                String val = args[i];
                // 判断是否为null,或者长度为0
                if (!isEmpty(val) && cLength != 0){
                    if (isIgnore) {
                        // 转换小写
                        String valIgnore = val.toLowerCase();
                        // 判断是否包含
                        if (valIgnore.indexOf(content) != -1) {
                            return true;
                        }
                    } else {
                        // 判断是否包含
                        if (val.indexOf(content) != -1) {
                            return true;
                        }
                    }
                } else {
                    // 下面这一串可以不要,因为判断字符串是否包含
                    // 已经处理了值不为null,并且需要判断的值长度不能为0,下面则不需要加上
                    if (content.equals(val)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断内容, 是否属于特定字符串数组开头 - 对比大小写
     * @param content
     * @param args
     * @return
     */
    public static boolean isStartsWith(String content, String... args) {
        return isStartsWith(false, content, args);
    }

    /**
     * 判断内容, 是否属于特定字符串数组开头
     * @param isIgnore 是否忽略大小写
     * @param content
     * @param args
     * @return
     */
    public static boolean isStartsWith(boolean isIgnore, String content, String... args) {
        if (!isEmpty(content) && args != null && args.length != 0){
            // 判断是否需要忽略大小写
            if (isIgnore){
                content = content.toLowerCase();
            }
            // 获取数据长度
            int len = args.length;
            // 遍历判断
            for (int i = 0; i < len; i++) {
                // 获取临时变量
                String val = args[i];
                // 判断是否为null,或者长度为0
                if (!isEmpty(val)){
                    if (isIgnore) {
                        // 转换小写
                        String valIgnore = val.toLowerCase();
                        // 判断是否属于 val 开头
                        if (content.startsWith(valIgnore)) {
                            return true;
                        }
                    } else {
                        // 判断是否属于 val 开头
                        if (content.startsWith(val)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断内容, 是否属于特定字符串数组结尾 - 对比大小写
     * @param content
     * @param args
     * @return
     */
    public static boolean isEndsWith(String content, String... args) {
        return isEndsWith(false, content, args);
    }

    /**
     * 判断内容, 是否属于特定字符串数组结尾
     * @param isIgnore 是否忽略大小写
     * @param content
     * @param args
     * @return
     */
    public static boolean isEndsWith(boolean isIgnore, String content, String... args) {
        if (!isEmpty(content) && args != null && args.length != 0){
            // 判断是否需要忽略大小写
            if (isIgnore){
                content = content.toLowerCase();
            }
            // 获取数据长度
            int len = args.length;
            // 遍历判断
            for (int i = 0; i < len; i++) {
                // 获取临时变量
                String val = args[i];
                // 判断是否为null,或者长度为0
                if (!isEmpty(val)){
                    if (isIgnore) {
                        // 转换小写
                        String valIgnore = val.toLowerCase();
                        // 判断是否属于 val 结尾
                        if (content.endsWith(valIgnore)) {
                            return true;
                        }
                    } else {
                        // 判断是否属于 val 结尾
                        if (content.endsWith(val)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // --

    /**
     * 清空全部空格,并返回处理后的字符串
     * @param str
     * @return
     */
    public static String toClearSpace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.replaceAll(" ", "");
    }

    /**
     * 清空前后空格,并返回处理后的字符串
     * @param str
     * @return
     */
    public static String toClearSpaceTrim(String str) {
        if (isEmpty(str)) {
            return str;
        }
        // 如果前面或者后面都是 空格开头,就一直进行处理
        while(str.startsWith(" ") || str.endsWith(" ")) {
            str = str.trim();
        }
        return str;
    }

    // ==

    /**
     * 检查字符串,如果为null,返回 ""
     * @param str
     * @return
     */
    public static String toCheckValue(String str) {
        return toCheckValue("", str);
    }

    /**
     * 检查字符串,如果为null,返回 默认字符串
     * @param dfStr
     * @param str
     * @return
     */
    public static String toCheckValue(String dfStr, String str) {
        return isEmpty(str) ? dfStr : str;
    }

    /**
     * 单独检查两个值,减少循环，不直接调用toCheckValues
     * @param dfStr
     * @param value1
     * @param value2
     * @return
     */
    public static String toCheckValue(String dfStr, String value1, String value2){
        if (isEmpty(value1)){
            if (isEmpty(value2)){
                return dfStr;
            } else {
                return value2;
            }
        } else {
            return value1;
        }
    }

    /**
     * 检查多个值,并返回第一个非null and "" 的字符串,如果都不符合条件，则返回默认值
     * @param dfStr
     * @param params
     * @return
     */
    public static String toCheckValues(String dfStr, String... params){
        if (params != null && params.length != 0){
            for (int i = 0, len = params.length; i < len; i++){
                String param = params[i];
                if (isEmpty(param)){
                    if (i == len - 1){
                        return dfStr; // 属于最后一个,则返回默认值
                    } else {
                        continue; // 不属于最后一个则跳过
                    }
                } else {
                    return param;
                }
            }
        }
        return dfStr;
    }

    /**
     * 检查多个值,并返回第一个非null and "" and 全部不是属于空格 的字符串,如果都不符合条件，则返回默认值
     * @param dfStr
     * @param params
     * @return
     */
    public static String toCheckValuesSpace(String dfStr, String... params){
        if (params != null && params.length != 0){
            for (int i = 0, len = params.length; i < len; i++){
                // 处理后,进行返回 => 删除前后空格
                String param = toClearSpaceTrim(params[i]);
                if (isEmpty(param)){
                    if (i == len - 1){
                        return dfStr; // 属于最后一个,则返回默认值
                    } else {
                        continue; // 不属于最后一个则跳过
                    }
                } else {
                    return param;
                }
            }
        }
        return dfStr;
    }

    /**
     * 裁减超出的内容, 并且追加符号(如 ...)
     * @param maxLength 允许最大的长度
     * @param content
     * @param symbol
     * @return
     */
    public static String subEllipsize(int maxLength, String content, String symbol){
        if (maxLength >= 1){
            // 获取内容长度
            int contentLength = length(content);
            // 防止为不存在数据
            if (contentLength != 0){
                if (maxLength >= contentLength){
                    return content;
                }
                return content.substring(0, maxLength) + toCheckValue(symbol);
            }
        }
        return "";
    }

    /**
     * 裁剪符号处理
     * @param start 开始位置
     * @param symbolNumber 转换数量
     * @param content
     * @param symbol
     * @return
     */
    public static String subSymbolHide(int start, int symbolNumber, String content, String symbol){
        if (!isEmpty(content)){
            if (start <= 0 || symbolNumber <= 0){
                return content;
            }
            // 获取数据长度
            int length = content.length();
            // 如果数据小于 start 位则直接返回
            if (length <= start){
                return content;
            } else { // 大于 start 位
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(content.substring(0, start));
                int len = length - start - symbolNumber;
                // 如果超出总长度, 则进行控制
                if (len <= 0){ // 表示后面的全部转换
                    len = length - start;
                } else { // 需要裁剪的数量
                    len = symbolNumber;
                }
                // 进行遍历保存
                for (int i = 0; i < len; i++) {
                    stringBuffer.append(symbol);
                }
                stringBuffer.append(content.substring(start + len, length));
                return stringBuffer.toString();
            }
        }
        return "";
    }

    /**
     * 转换符号处理
     * @param start 开始位置
     * @param content
     * @param symbol
     * @return
     */
    public static String converSymbolHide(int start, String content, String symbol){
        if (!isEmpty(content)){
            if (start <= 0){
                return content;
            }
            // 获取数据长度
            int length = content.length();
            // 如果数据小于 start 位则直接返回
            if (length <= start){
                return content;
            } else { // 大于 start 位
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(content.substring(0, start));
                int len = length - start;
                // 进行平分
                len /= 2;
                // 进行遍历保存
                for (int i = 0; i < len; i++){
                    stringBuffer.append(symbol);
                }
                stringBuffer.append(content.substring(start + len, length));
                return stringBuffer.toString();
            }
        }
        return "";
    }

    // == 替换截取操作 ==

    /**
     * 替换(删除 - 替换成"") 字符串中符合 特定标记字符的 startsWith - endsWith
     * * 如 _____a_a_a_a________  传入 _ 等于 ____a_a_a_a____
     * @param str
     * @param key 需要判断的标记
     * @return
     */
    public static String toReplaceSEWith(String str, String key){
        return toReplaceSEWith(str, key, "");
    }

    /**
     * 替换字符串中符合 特定标记字符的 startsWith - endsWith
     * 如 _____a_a_a_a________  传入 _ , c 等于 c____a_a_a_a____c
     * @param str
     * @param key 需要判断的标记
     * @param value 需要替换的内容
     * @return
     */
    public static String toReplaceSEWith(String str, String key, String value){
        if (!(!isEmpty(str) && !isEmpty(key) && !isEmpty(value) && !key.equals(value))) {
            return str;
        }
        try {
            // 获取编辑内容长度
            int kLength = key.length();
            // 保存新的Buffer中,减少内存开销
            StringBuffer sBuffer = new StringBuffer(str);
            // 判断是否在最头部
            if (sBuffer.indexOf(key) == 0) {
                sBuffer.delete(0, kLength);
                // 追加内容
                sBuffer.insert(0, value);
            }
            // 获取尾部的位置
            int lastIndexOf = -1;
            // 数据长度
            int bufLength = -1;
            // 判断是否在最尾部
            if ((lastIndexOf = sBuffer.lastIndexOf(key)) == ((bufLength = sBuffer.length()) - kLength)) {
                sBuffer.delete(lastIndexOf, bufLength);
                // 追加内容
                sBuffer.insert(lastIndexOf, value);
            }
            return sBuffer.toString();
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
     * @param key 需要判断的标记
     * @return
     */
    public static String toClearSEWiths(String str, String key){
        if (!(!isEmpty(str) && !isEmpty(key))) {
            return str;
        }
        try {
            // 获取编辑内容长度
            int kLength = key.length();
            // 保存新的Buffer中,减少内存开销
            StringBuffer sBuffer = new StringBuffer(str);
            // 进行循环判断 - 属于最前面的,才进行处理
            while (sBuffer.indexOf(key) == 0) {
                sBuffer.delete(0, kLength);
            }
            // 获取尾部的位置
            int lastIndexOf = -1;
            // 数据长度
            int bufLength = -1;
            // 进行循环判断 - 属于最后面的,才进行处理
            while ((lastIndexOf = sBuffer.lastIndexOf(key)) == ((bufLength = sBuffer.length()) - kLength)) {
                sBuffer.delete(lastIndexOf, bufLength);
            }
            return sBuffer.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toClearSEWiths");
        }
        return str;
    }

    /**
     * 裁剪字符串
     * @param content 需要裁剪的字符串
     * @param endIndex 结束裁剪的位置
     * @return
     */
    public static String substring(String content, int endIndex){
        return substring(content, 0, endIndex, true);
    }

    /**
     * 裁剪字符串
     * @param content 需要裁剪的字符串
     * @param endIndex 结束裁剪的位置
     * @param isReturn 开始位置超过限制是否返回内容
     * @return
     */
    public static String substring(String content, int endIndex, boolean isReturn){
        return substring(content, 0, endIndex, isReturn);
    }

    /**
     * 裁剪字符串
     * @param content 需要裁剪的字符串
     * @param beginIndex 开始裁剪的位置
     * @param endIndex 结束裁剪的位置
     * @param isReturn 开始位置超过限制是否返回内容
     * @return
     */
    public static String substring(String content, int beginIndex, int endIndex, boolean isReturn){
        if (!isEmpty(content) && beginIndex >= 0 && endIndex >= 0 && endIndex >= beginIndex){
            // 获取数据长度
            int len = length(content);
            // 防止超过限制
            if (beginIndex > len){
                return isReturn ? content : "";
            }
            // 防止超过限制
            if (endIndex >= len){
                endIndex = len;
            }
            return content.substring(beginIndex, endIndex);
        }
        return isReturn ? content : "";
    }

    // ==

    /**
     * 替换开头字符串
     * @param str 内容
     * @param key 需要判断的kye
     * @return
     */
    public static String toReplaceStartsWith(String str, String key){
        return toReplaceStartsWith(str, key, "");
    }

    /**
     * 替换开头字符串
     * @param str 内容
     * @param key 需要判断的kye
     * @param value 需要替换的内容
     * @return
     */
    public static String toReplaceStartsWith(String str, String key, String value){
        if (!isEmpty(str) && !isEmpty(key)) {
            try {
                if (str.startsWith(key)){
                    return value + str.substring(key.length(), str.length());
                }
            } catch (Exception e) {
            }
        }
        return str;
    }

    /**
     * 清空属于特定字符串开头的字段
     * 如 _____a_a_a_a________  传入 _ 等于 a_a_a_a_____
     * 替换字符串中符合 特定标记字符的 endsWith(lastIndexOf) ,while
     * @param str
     * @param key 需要判断的标记
     * @return
     */
    public static String toClearStartsWith(String str, String key){
        if (!(!isEmpty(str) && !isEmpty(key))) {
            return str;
        }
        try {
            // 获取编辑内容长度
            int kLength = key.length();
            // 保存新的Buffer中,减少内存开销
            StringBuffer sBuffer = new StringBuffer(str);
            // 进行循环判断 - 属于最前面的,才进行处理
            while (sBuffer.indexOf(key) == 0) {
                sBuffer.delete(0, kLength);
            }
            return sBuffer.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toClearStartsWith");
        }
        return str;
    }

    // ==

    /**
     * 替换结尾字符串
     * @param content 内容
     * @param key 需要判断的kye
     * @return
     */
    public static String toReplaceEndsWith(String content, String key){
        return toReplaceEndsWith(content, key, "");
    }

    /**
     * 替换结尾字符串
     * @param content 内容
     * @param key 需要判断的kye
     * @param value 需要替换的内容
     * @return
     */
    public static String toReplaceEndsWith(String content, String key, String value){
        if (!isEmpty(content) && !isEmpty(key)) {
            try {
                if (content.endsWith(key)){
                    return content.substring(0, content.length() - key.length()) + value;
                }
            } catch (Exception e) {
            }
        }
        return content;
    }

    /**
     * 清空属于特定字符串结尾的字段
     * 如 _____a_a_a_a________  传入 _ 等于 _____a_a_a_a
     * 替换字符串中符合 特定标记字符的 endsWith(lastIndexOf) ,while
     * @param str
     * @param key 需要判断的标记
     * @return
     */
    public static String toClearEndsWith(String str, String key){
        if (!(!isEmpty(str) && !isEmpty(key))) {
            return str;
        }
        try {
            // 获取编辑内容长度
            int kLength = key.length();
            // 保存新的Buffer中,减少内存开销
            StringBuffer sBuffer = new StringBuffer(str);
            // 获取最后一位位置
            int sLength = 0;
            // 进行循环判断 - 属于最前面的,才进行处理
            while (sBuffer.lastIndexOf(key) == ((sLength = sBuffer.length()) - kLength)) {
                sBuffer.delete(sLength - kLength, sLength);
            }
            return sBuffer.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toClearEndsWith");
        }
        return str;
    }

    // ===

    /**
     * 替换字符串
     * @param content 内容
     * @param
     * @return
     */
    public static String replaceStrs(String content, String[] spArys, String[] reArys){
        // 防止数据为null
        if (content != null && spArys != null && reArys != null){
            String cStr = content;
            // 替换的特殊字符串长度
            int spCount = spArys.length;
            // 替换的内容长度
            int reCount = reArys.length;
            // 相同才进行处理
            if (spCount == reCount){
                // 遍历进行判断
                for (int i = 0; i < spCount; i++){
                    // 进行替换字符串
                    cStr = replaceStr(cStr, spArys[i], reArys[i]);
                }
                // 最终不为null,则进行返回
                return cStr;
            }
        }
        return null;
    }

    /**
     * 替换字符串
     * @param content 需要替换的内容
     * @param spStr 特殊的字符串
     * @param reStr 替换的内容
     * @return
     */
    public static String replaceStr(String content, String spStr, String reStr){
        // 如果替换的内容或者判断的字符串为null,则直接跳过
        if (!isEmpty(content) && !isEmpty(spStr) && reStr != null){
            try {
                return content.replaceAll(spStr, reStr);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "replaceStr");
            }
        }
        return content;
    }

    /**
     * 替换字符串
     * @param content 需要替换的内容
     * @param spStr 特殊的字符串
     * @param reStr 替换的内容
     * @return 如果异常则直接返回null
     */
    public static String replaceStrToNull(String content, String spStr, String reStr){
        // 如果替换的内容或者判断的字符串为null,则直接跳过
        if (!isEmpty(content) && !isEmpty(spStr) && reStr != null){
            try {
                return content.replaceAll(spStr, reStr);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "replaceStrToNull");
            }
        }
        return null;
    }
}
