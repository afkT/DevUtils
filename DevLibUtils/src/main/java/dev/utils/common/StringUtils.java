package dev.utils.common;

import java.net.URLEncoder;
import java.util.Locale;

import dev.utils.JCLogUtils;

/**
 * detail: 字符串工具类
 * Created by Ttt
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

    // =

    /**
     * 获取长度，如果字符串为null,则返回 0
     * @param str
     * @return
     */
    public static int length(final String str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 判断是否为 null
     * @param str
     * @return
     */
    public static boolean isEmpty(final String str) {
        return (str == null || str.length() == 0);
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
     * 字符串进行 GBK 编码
     * @param str
     * @return
     */
    public static String toGBKEncode(final String str) {
        return toStrEncode(str, "GBK");
    }

    /**
     * 字符串进行 GBK2312 编码
     * @param str
     * @return
     */
    public static String toGBK2312Encode(final String str) {
        return toStrEncode(str, "GBK-2312");
    }

    /**
     * 字符串进行 UTF-8 编码
     * @param str
     * @return
     */
    public static String toUTF8Encode(final String str) {
        return toStrEncode(str, "UTF-8");
    }

    /**
     * 进行字符串编码
     * @param str
     * @param enc
     * @return
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
     * @param str
     * @return
     */
    public static String toUrlEncode(final String str) {
        return toUrlEncode(str, "UTF-8");
    }

    /**
     * 进行 URL 编码
     * @param str
     * @param enc
     * @return
     */
    public static String toUrlEncode(final String str, final String enc) {
        if (str == null || enc == null) return null;
        try {
            return URLEncoder.encode(str, enc);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toUrlEncode");
            // 如果URL编码失败,则直接进行字符串编码
            return toStrEncode(str, enc);
        }
    }

    // =

    /**
     * 将字符串转移为 ASCII 码
     * @param str 字符串
     * @return 字符串 ASCII 码
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
     * @param str 字符串
     * @return
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
     * 将字符串转移为 Unicode 码
     * @param chars 字符数组
     * @return
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
     * @param str 待转字符串
     * @return 半角字符串
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
     * 转化为全角字符 如： a => ａ A => Ａ
     * @param str 待转字符串
     * @return 全角字符串
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
     * byte[]  转换为 16进制的字符串
     * @param data 要转换的字节数组
     * @return 转换后的结果
     */
    public static String byteArrayToHexString(byte[] data) {
        if (data == null) return null;
        try {
            StringBuilder builder = new StringBuilder(data.length * 2);
            for (byte b : data) {
                int v = b & 0xff;
                if (v < 16) {
                    builder.append('0');
                }
                builder.append(Integer.toHexString(v));
            }
            return builder.toString().toUpperCase(Locale.getDefault());
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "byteArrayToHexString");
        }
        return null;
    }

    /**
     * 进行十六进制转换
     * @param data
     * @param hexDigits
     * @return
     */
    public static String toHexString(final byte[] data, final char[] hexDigits) {
        if (data == null || hexDigits == null) return null;
        try {
            StringBuilder builder = new StringBuilder(data.length * 2);
            for (int i = 0, len = data.length; i < len; i++) {
                builder.append(hexDigits[(data[i] & 0xf0) >>> 4]);
                builder.append(hexDigits[data[i] & 0x0f]);
            }
            return builder.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toHexString");
        }
        return null;
    }

    /**
     * 16进制表示的字符串 转换为 字节数组
     * @param str 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToBytes(final String str) {
        if (isEmpty(str)) return null;
        int len = str.length();
        try {
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
                data[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
            }
            return data;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "hexStringToBytes");
        }
        return null;
    }

    /**
     * 检测字符串是否全是中文
     * @param str
     * @return
     */
    public static boolean checkCheseToString(final String str) {
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
     * @param c
     * @return
     */
    public static boolean isChinese(final char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
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

    // == 字符串处理方法 ==

    /**
     * 首字母大写
     * @param str 待转字符串
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
     * @param str 待转字符串
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
     * @param str 待反转字符串
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
     * @param more 追加
     * @return 返回拼接后的字符串
     */
    public static String concat(final Object... more) {
        return concatSpiltWith("", more);
    }

    /**
     * 字符串连接，将参数列表拼接为一个字符串
     * @param split
     * @param more
     * @return 回拼接后的字符串
     */
    public static String concatSpiltWith(final String split, final Object... more) {
        if (more == null) return null;
        StringBuilder builder = new StringBuilder();
        for (int i = 0, len = more.length; i < len; i++) {
            if (i != 0) builder.append(split);
            builder.append(more[i]);
        }
        return builder.toString();
    }

    /**
     * 下划线命名转为驼峰命名
     * @param str 下划线命名格式
     * @return 驼峰命名格式
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
     * @param str 驼峰命名格式
     * @return 下划线命名格式
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
     * 数据库字符转义
     * @param keyWord
     * @return
     */
    public static String sqliteEscape(final String keyWord) {
        if (isEmpty(keyWord)) return keyWord;
        String keyWordTemp = keyWord;
        // 替换关键字
        keyWordTemp = keyWordTemp.replace("/", "//");
        keyWordTemp = keyWordTemp.replace("'", "''");
        keyWordTemp = keyWordTemp.replace("[", "/[");
        keyWordTemp = keyWordTemp.replace("]", "/]");
        keyWordTemp = keyWordTemp.replace("%", "/%");
        keyWordTemp = keyWordTemp.replace("&", "/&");
        keyWordTemp = keyWordTemp.replace("_", "/_");
        keyWordTemp = keyWordTemp.replace("(", "/(");
        keyWordTemp = keyWordTemp.replace(")", "/)");
        return keyWordTemp;
    }
}
