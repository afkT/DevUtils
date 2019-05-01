package dev.utils.common;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import dev.utils.JCLogUtils;
import dev.utils.common.encrypt.MD5Utils;

/**
 * detail: 开发常用方法工具类
 * @author Ttt
 */
public final class DevCommonUtils {

    private DevCommonUtils() {
    }

    // 日志 TAG
    private static final String TAG = DevCommonUtils.class.getSimpleName();
    // 换行字符串
    private static final String NEW_LINE_STR = System.getProperty("line.separator");

    // ================
    // = 计时相关处理 =
    // ================

    /**
     * 耗时时间记录
     * @param buffer    拼接Buffer
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static void timeRecord(final StringBuffer buffer, final long startTime, final long endTime) {
        timeRecord(buffer, null, startTime, endTime);
    }

    /**
     * 耗时时间记录
     * @param buffer    拼接Buffer
     * @param title     标题
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static void timeRecord(final StringBuffer buffer, final String title, final long startTime, final long endTime) {
        if (buffer == null) return;
        // 使用时间
        long diffTime = endTime - startTime;
        // 计算时间
        if (!isEmpty(title)) {
            buffer.append(NEW_LINE_STR);
            buffer.append(title);
        }
        // 计算时间
        buffer.append(NEW_LINE_STR + "开始时间: " + DateUtils.formatTime(startTime, DateUtils.yyyyMMddHHmmss));
        buffer.append(NEW_LINE_STR + "结束时间: " + DateUtils.formatTime(endTime, DateUtils.yyyyMMddHHmmss));
        buffer.append(NEW_LINE_STR + "所用时间(毫秒): " + diffTime);
        buffer.append(NEW_LINE_STR + "所用时间(秒): " + (diffTime / 1000));
    }

    // =

    /**
     * 获取操作时间
     * @param operateTime 操作时间(毫秒)
     * @return 操作时间
     */
    public static long getOperateTime(final long operateTime) {
        return getOperateTime(operateTime, -1);
    }

    /**
     * 获取操作时间
     * @param operateTime 操作时间(毫秒)
     * @param randomTime  随机时间范围(毫秒)
     * @return 操作时间
     */
    public static long getOperateTime(final long operateTime, final int randomTime) {
        int random = 0;
        // 大于2才处理
        if (randomTime >= 2) {
            // 随机时间
            random = RandomUtils.getRandom(randomTime);
        }
        // 返回操作时间
        return Math.max(0, operateTime) + random;
    }

    /**
     * 堵塞操作
     * @param sleepTime 堵塞时间(毫秒)
     */
    public static void sleepOperate(final long sleepTime) {
        sleepOperate(sleepTime, -1);
    }

    /**
     * 堵塞操作
     * @param sleepTime  堵塞时间(毫秒)
     * @param randomTime 随机时间范围(毫秒)
     */
    public static void sleepOperate(final long sleepTime, final int randomTime) {
        long time = getOperateTime(sleepTime, randomTime);
        if (time > 0) {
            try {
                Thread.sleep(time);
            } catch (Throwable e1) {
                JCLogUtils.eTag(TAG, e1, "sleepOperate");
            }
        }
    }

    // =

    /**
     * 判断是否网络资源
     * @param str 待校验字符串
     * @return {@code true} yes, {@code false} no
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

    /**
     * 循环 MD5 加密处理
     * @param str         待处理数据
     * @param number      MD5 加密次数
     * @param isUppercase 是否大写处理
     * @param salts       特殊 salt 拼接
     * @return 循环加密后的字符串
     */
    public static String whileMD5(final String str, final int number, final boolean isUppercase, final String... salts) {
        if (str != null && number >= 1) {
            int saltLen = (salts != null) ? salts.length : 0;
            // 临时字符串
            String dataTemp = str;
            // 判断是否大写
            if (isUppercase) {
                // 循环加密
                for (int i = 0; i < number; i++) {
                    if (saltLen >= i) {
                        String salt = salts[i];
                        if (salt != null) {
                            dataTemp = MD5Utils.md5Upper(dataTemp + salt);
                        } else {
                            dataTemp = MD5Utils.md5Upper(dataTemp);
                        }
                    } else {
                        dataTemp = MD5Utils.md5Upper(dataTemp);
                    }
                }
            } else {
                // 循环加密
                for (int i = 0; i < number; i++) {
                    if (saltLen >= i) {
                        String salt = salts[i];
                        if (salt != null) {
                            dataTemp = MD5Utils.md5(dataTemp + salt);
                        } else {
                            dataTemp = MD5Utils.md5(dataTemp);
                        }
                    } else {
                        dataTemp = MD5Utils.md5(dataTemp);
                    }
                }
            }
        }
        return str;
    }

    // ==============
    // = 获取唯一ID =
    // ==============

    /**
     * 获取随机唯一数
     * @return {@link UUID}
     */
    public static UUID randomUUID() {
        return UUID.randomUUID();
    }

    /**
     * 获取随机唯一数 HashCode
     * @return 随机 UUID hashCode
     */
    public static int randomUUIDToHashCode() {
        return UUID.randomUUID().hashCode();
    }

    /**
     * 获取随机唯一数 HashCode
     * @param uuid {@link UUID}
     * @return 随机 UUID hashCode
     */
    public static int randomUUIDToHashCode(final UUID uuid) {
        return (uuid != null) ? uuid.hashCode() : 0;
    }

    /**
     * 获取随机规则生成 UUID
     * @return 随机规则生成 UUID
     */
    public static UUID getRandomUUID() {
        // 获取随机数
        String random1 = (900000 + new Random().nextInt(10000)) + "";
        // 获取随机数
        String random2 = (900000 + new Random().nextInt(10000)) + "";
        // 获取当前时间
        String cTime = Long.toString(System.currentTimeMillis()) + random1 + random2;
        // 生成唯一随机uuid  cTime.hashCode(), random1.hashCode() | random2.hashCode()
        UUID randomUUID = new UUID(cTime.hashCode(), ((long) random1.hashCode() << 32) | random2.hashCode());
        return randomUUID;
    }

    /**
     * 获取随机规则生成 UUID 字符串
     * @return 随机规则生成 UUID 字符串
     */
    public static String getRandomUUIDToString() {
        return getRandomUUID().toString();
    }

    // ==============
    // = 字符串处理 =
    // ==============

    /**
     * 转换手机号
     * @param phone 待处理字符串
     * @return 处理后的字符串
     */
    public static String converHideMobile(final String phone) {
        return converHideMobile(phone, "*");
    }

    /**
     * 转换手机号
     * @param phone  待处理字符串
     * @param symbol 转换符号
     * @return 处理后的字符串
     */
    public static String converHideMobile(final String phone, final String symbol) {
        return converSymbolHide(3, phone, symbol);
    }

    /**
     * 转换符号处理
     * @param start  开始位置
     * @param str    待处理字符串
     * @param symbol 转换符号
     * @return 处理后的字符串
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

    // =

    /**
     * 裁剪超出的内容, 并且追加符号(如 ...)
     * @param maxLength 允许最大的长度
     * @param str       待处理字符串
     * @param symbol    转换符号
     * @return 处理后的字符串
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
     * @param str          待处理字符串
     * @param symbol       转换符号
     * @return 处理后的字符串
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

    // =================
    // = 替换/截取操作 =
    // =================

    /**
     * 裁剪字符串
     * @param str      需要裁剪的字符串
     * @param endIndex 结束裁剪的位置
     * @return 裁剪后的字符串
     */
    public static String substring(final String str, final int endIndex) {
        return substring(str, 0, endIndex, true);
    }

    /**
     * 裁剪字符串
     * @param str      需要裁剪的字符串
     * @param endIndex 结束裁剪的位置
     * @param isReturn 开始位置超过限制是否返回内容
     * @return 裁剪后的字符串
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
     * @return 裁剪后的字符串
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
     * 替换(删除 - 替换成"") 字符串中符合 特定标记字符的 startsWith - endsWith
     * * 如 _____a_a_a_a________  传入 _ 等于 ____a_a_a_a____
     * @param str    待处理字符串
     * @param suffix 替换符号字符串
     * @return 处理后的字符串
     */
    public static String toReplaceSEWith(final String str, final String suffix) {
        return toReplaceSEWith(str, suffix, "");
    }

    /**
     * 替换字符串中符合 特定标记字符的 startsWith - endsWith
     * 如 _____a_a_a_a________  传入 _ , c 等于 c____a_a_a_a____c
     * @param str    待处理字符串
     * @param suffix 替换匹配内容
     * @param value  需要替换的内容
     * @return 处理后的字符串
     */
    public static String toReplaceSEWith(final String str, final String suffix, final String value) {
        try {
            if (isEmpty(str) || isEmpty(suffix) || isEmpty(value) || suffix.equals(value))
                return str;
            // 获取编辑内容长度
            int kLength = suffix.length();
            // 保存新的Buffer中,减少内存开销
            StringBuffer buffer = new StringBuffer(str);
            // 判断是否在最头部
            if (buffer.indexOf(suffix) == 0) {
                buffer.delete(0, kLength);
                // 追加内容
                buffer.insert(0, value);
            }
            // 获取尾部的位置
            int lastIndexOf = -1;
            // 数据长度
            int bufLength = -1;
            // 判断是否在最尾部
            if ((lastIndexOf = buffer.lastIndexOf(suffix)) == ((bufLength = buffer.length()) - kLength)) {
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

    // =

    /**
     * 替换开头字符串
     * @param str    待处理字符串
     * @param prefix 开头匹配字符串
     * @return 处理后的字符串
     */
    public static String toReplaceStartsWith(final String str, final String prefix) {
        return toReplaceStartsWith(str, prefix, "");
    }

    /**
     * 替换开头字符串
     * @param str         待处理字符串
     * @param prefix      开头匹配字符串
     * @param startAppend 开头追加的内容
     * @return 处理后的字符串
     */
    public static String toReplaceStartsWith(final String str, final String prefix, final String startAppend) {
        if (!isEmpty(str) && !isEmpty(prefix)) {
            try {
                if (str.startsWith(prefix)) {
                    return toCheckValue(startAppend) + str.substring(prefix.length());
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toReplaceStartsWith");
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
    public static String toReplaceEndsWith(final String str, final String suffix) {
        return toReplaceEndsWith(str, suffix, "");
    }

    /**
     * 替换结尾字符串
     * @param str    待处理字符串
     * @param suffix 结尾匹配字符串
     * @param value  需要替换的内容
     * @return 处理后的字符串
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

    // =

    /**
     * (这个方法功能主要把字符符合标记的 头部和尾部都替换成 "")
     * 如 _____a_a_a_a________  传入 _ 等于 a_a_a_a
     * 替换字符串中符合 特定标记字符的 startsWith(indexOf) - endsWith(lastIndexOf) ,while
     * @param str    待处理字符串
     * @param suffix 匹配判断字符串
     * @return 处理后的字符串
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
     * 清空属于特定字符串开头的字段
     * 如 _____a_a_a_a________  传入 _ 等于 a_a_a_a_____
     * 替换字符串中符合 特定标记字符的 endsWith(lastIndexOf) ,while
     * @param str    待处理字符串
     * @param suffix 匹配判断字符串
     * @return 处理后的字符串
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

    /**
     * 清空属于特定字符串结尾的字段
     * 如 _____a_a_a_a________  传入 _ 等于 _____a_a_a_a
     * 替换字符串中符合 特定标记字符的 endsWith(lastIndexOf) ,while
     * @param str    待处理字符串
     * @param suffix 匹配判断字符串
     * @return 处理后的字符串
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
     * @param str     待处理字符串
     * @param suffix  匹配判断字符串
     * @param replace 替换的内容
     * @return 处理后的字符串
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
     * @param str     待处理字符串
     * @param suffix  匹配判断字符串
     * @param replace 替换的内容
     * @return 处理后的字符串, 替换失败则返回 null
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

    /**
     * 替换字符串
     * @param str         内容
     * @param suffixArys  匹配判断字符串数组
     * @param replaceArys 准备替换的字符串数组
     * @return 处理后的字符串
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

    // =====================================================================================
    // = 其他工具类已实现(ArrayUtils、CollectionUtils、MapUtils、StringUtils、ObjectUtils) =
    // =====================================================================================

    // ==============
    // = ArrayUtils =
    // ==============

    /**
     * 判断数组是否为 null
     * @param objects object[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final Object[] objects) {
        return objects == null || objects.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param ints int[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final int[] ints) {
        return ints == null || ints.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param bytes byte[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final byte[] bytes) {
        return bytes == null || bytes.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param chars char[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final char[] chars) {
        return chars == null || chars.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param shorts short[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final short[] shorts) {
        return shorts == null || shorts.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param longs long[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final long[] longs) {
        return longs == null || longs.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param floats float[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final float[] floats) {
        return floats == null || floats.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param doubles double[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final double[] doubles) {
        return doubles == null || doubles.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param booleans boolean[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final boolean[] booleans) {
        return booleans == null || booleans.length == 0;
    }

    // =

    /**
     * 判断数组是否不为 null
     * @param objects object[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final Object[] objects) {
        return objects != null && objects.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param ints int[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final int[] ints) {
        return ints != null && ints.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param bytes byte[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final byte[] bytes) {
        return bytes != null && bytes.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param chars char[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final char[] chars) {
        return chars != null && chars.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param shorts short[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final short[] shorts) {
        return shorts != null && shorts.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param longs long[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final long[] longs) {
        return longs != null && longs.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param floats float[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final float[] floats) {
        return floats != null && floats.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param doubles double[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final double[] doubles) {
        return doubles != null && doubles.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param booleans boolean[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final boolean[] booleans) {
        return booleans != null && booleans.length != 0;
    }

    // ============
    // = 判断长度 =
    // ============

    /**
     * 获取数组长度
     * @param objects object[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final Object[] objects) {
        return length(objects, 0);
    }

    /**
     * 获取数组长度
     * @param ints int[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final int[] ints) {
        return length(ints, 0);
    }

    /**
     * 获取数组长度
     * @param bytes byte[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final byte[] bytes) {
        return length(bytes, 0);
    }

    /**
     * 获取数组长度
     * @param chars char[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final char[] chars) {
        return length(chars, 0);
    }

    /**
     * 获取数组长度
     * @param shorts short[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final short[] shorts) {
        return length(shorts, 0);
    }

    /**
     * 获取数组长度
     * @param longs long[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final long[] longs) {
        return length(longs, 0);
    }

    /**
     * 获取数组长度
     * @param floats float[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final float[] floats) {
        return length(floats, 0);
    }

    /**
     * 获取数组长度
     * @param doubles double[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final double[] doubles) {
        return length(doubles, 0);
    }

    /**
     * 获取数组长度
     * @param booleans boolean[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final boolean[] booleans) {
        return length(booleans, 0);
    }

    // =

    /**
     * 获取数组长度
     * @param objects       object[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final Object[] objects, final int defaultLength) {
        return objects != null ? objects.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param ints          int[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final int[] ints, final int defaultLength) {
        return ints != null ? ints.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param bytes         byte[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final byte[] bytes, final int defaultLength) {
        return bytes != null ? bytes.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param chars         char[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final char[] chars, final int defaultLength) {
        return chars != null ? chars.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param shorts        short[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final short[] shorts, final int defaultLength) {
        return shorts != null ? shorts.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param longs         long[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final long[] longs, final int defaultLength) {
        return longs != null ? longs.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param floats        float[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final float[] floats, final int defaultLength) {
        return floats != null ? floats.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param doubles       double[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final double[] doubles, final int defaultLength) {
        return doubles != null ? doubles.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param booleans      boolean[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final boolean[] booleans, final int defaultLength) {
        return booleans != null ? booleans.length : defaultLength;
    }

    // =

    /**
     * 判断数组长度是否等于期望长度
     * @param objects object[]
     * @param length  期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final Object[] objects, final int length) {
        return objects != null && objects.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param ints   int[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final int[] ints, final int length) {
        return ints != null && ints.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param bytes  byte[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final byte[] bytes, final int length) {
        return bytes != null && bytes.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param chars  char[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final char[] chars, final int length) {
        return chars != null && chars.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param shorts short[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final short[] shorts, final int length) {
        return shorts != null && shorts.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param longs  long[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final long[] longs, final int length) {
        return longs != null && longs.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param floats float[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final float[] floats, final int length) {
        return floats != null && floats.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param doubles double[]
     * @param length  期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final double[] doubles, final int length) {
        return doubles != null && doubles.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param booleans boolean[]
     * @param length   期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final boolean[] booleans, final int length) {
        return booleans != null && booleans.length == length;
    }

    // ===================
    // = CollectionUtils =
    // ===================

    /**
     * 判断 Collection 是否为 null
     * @param collection Collection
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final Collection collection) {
        return (collection == null || collection.size() == 0);
    }

    /**
     * 判断 Collection 是否不为 null
     * @param collection Collection
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final Collection collection) {
        return (collection != null && collection.size() != 0);
    }

    // ============
    // = 判断长度 =
    // ============

    /**
     * 获取 Collection 长度
     * @param collection Collection
     * @return 如果 Collection 为 null, 则返回默认长度, 如果不为 null, 则返回 collection.size()
     */
    public static int length(final Collection collection) {
        return length(collection, 0);
    }

    /**
     * 获取 Collection 长度
     * @param collection    Collection
     * @param defaultLength 集合为 null 默认长度
     * @return 如果 Collection 为 null, 则返回 defaultLength, 如果不为 null, 则返回 collection.size()
     */
    public static int length(final Collection collection, final int defaultLength) {
        return collection != null ? collection.size() : defaultLength;
    }

    // =

    /**
     * 获取长度 to Collection 是否等于期望长度
     * @param collection Collection
     * @param length     期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final Collection collection, final int length) {
        return collection != null && collection.size() == length;
    }

    // ============
    // = MapUtils =
    // ============

    /**
     * 判断 Map 是否为 null
     * @param map Map
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final Map map) {
        return (map == null || map.size() == 0);
    }

    /**
     * 判断 Map 是否不为 null
     * @param map Map
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final Map map) {
        return (map != null && map.size() != 0);
    }

    // ============
    // = 判断长度 =
    // ============

    /**
     * 获取 Map 长度
     * @param map Map
     * @return 如果 Map 为 null, 则返回默认长度, 如果不为 null, 则返回 map.size()
     */
    public static int length(final Map map) {
        return length(map, 0);
    }

    /**
     * 获取 Map 长度
     * @param map           Map
     * @param defaultLength 集合为 null 默认长度
     * @return 如果 Map 为 null, 则返回 defaultLength, 如果不为 null, 则返回 map.size()
     */
    public static int length(final Map map, final int defaultLength) {
        return map != null ? map.size() : defaultLength;
    }

    // =

    /**
     * 获取长度 to Map 是否等于期望长度
     * @param map    Map
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final Map map, final int length) {
        return map != null && map.size() == length;
    }

    // ===============
    // = StringUtils =
    // ===============

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

    // ===============
    // = ObjectUtils =
    // ===============

    /**
     * 判断对象是否为空
     * @param object 对象
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final Object object) {
        if (object == null) {
            return true;
        }
        try {
            if (object.getClass().isArray() && Array.getLength(object) == 0) {
                return true;
            }
            if (object instanceof CharSequence && object.toString().length() == 0) {
                return true;
            }
            if (object instanceof Collection && ((Collection) object).isEmpty()) {
                return true;
            }
            if (object instanceof Map && ((Map) object).isEmpty()) {
                return true;
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "isEmpty");
        }
        return false;
    }

    /**
     * 判断对象是否非空
     * @param object 对象
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final Object object) {
        return !isEmpty(object);
    }
}
