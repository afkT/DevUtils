package dev.utils.common;

import dev.utils.common.cipher.Base64;

/**
 * detail: 编码工具类
 * @author Ttt
 * <pre>
 *     Base64 flags
 *     <p></p>
 *     CRLF 这个参数看起来比较眼熟, 它就是 Win 风格的换行符, 意思就是使用 CR LF 这一对作为一行的结尾而不是 Unix 风格的 LF
 *     DEFAULT 这个参数是默认, 使用默认的方法来加密
 *     NO_PADDING 这个参数是略去加密字符串最后的 =
 *     NO_WRAP 这个参数意思是略去所有的换行符 ( 设置后 CRLF 就没用了 )
 *     URL_SAFE 这个参数意思是加密时不使用对 URL 和文件名有特殊意义的字符来作为加密字符, 具体就是以 - 和 _ 取代 + 和 /
 * </pre>
 */
public final class EncodeUtils {

    private EncodeUtils() {
    }

    // ==========
    // = Base64 =
    // ==========

    // ===============
    // = Base64 编码 =
    // ===============

    /**
     * Base64 编码
     * @param input 待处理数据
     * @return Base64 编码后的 byte[]
     */
    public static byte[] base64Encode(final String input) {
        return base64Encode(input, Base64.NO_WRAP);
    }

    /**
     * Base64 编码
     * @param input 待处理数据
     * @param flags Base64 编解码 flags
     * @return Base64 编码后的 byte[]
     */
    public static byte[] base64Encode(
            final String input,
            final int flags
    ) {
        return base64Encode(ConvertUtils.toBytes(input), flags);
    }

    /**
     * Base64 编码
     * @param input 待处理数据
     * @return Base64 编码后的 byte[]
     */
    public static byte[] base64Encode(final byte[] input) {
        return base64Encode(input, Base64.NO_WRAP);
    }

    /**
     * Base64 编码
     * @param input 待处理数据
     * @param flags Base64 编解码 flags
     * @return Base64 编码后的 byte[]
     */
    public static byte[] base64Encode(
            final byte[] input,
            final int flags
    ) {
        if (input == null) return null;
        return Base64.encode(input, flags);
    }

    // =

    /**
     * Base64 编码
     * @param input 待处理数据
     * @return Base64 编码后的 byte[] 转 String
     */
    public static String base64EncodeToString(final String input) {
        return base64EncodeToString(input, Base64.NO_WRAP);
    }

    /**
     * Base64 编码
     * @param input 待处理数据
     * @param flags Base64 编解码 flags
     * @return Base64 编码后的 byte[] 转 String
     */
    public static String base64EncodeToString(
            final String input,
            final int flags
    ) {
        return base64EncodeToString(ConvertUtils.toBytes(input), flags);
    }

    /**
     * Base64 编码
     * @param input 待处理数据
     * @return Base64 编码后的 byte[] 转 String
     */
    public static String base64EncodeToString(final byte[] input) {
        return base64EncodeToString(input, Base64.NO_WRAP);
    }

    /**
     * Base64 编码
     * @param input 待处理数据
     * @param flags Base64 编解码 flags
     * @return Base64 编码后的 byte[] 转 String
     */
    public static String base64EncodeToString(
            final byte[] input,
            final int flags
    ) {
        if (input == null) return null;
        return ConvertUtils.newString(Base64.encode(input, flags));
    }

    // ===============
    // = Base64 解码 =
    // ===============

    /**
     * Base64 解码
     * @param input 待处理数据
     * @return Base64 解码后的 byte[]
     */
    public static byte[] base64Decode(final String input) {
        return base64Decode(input, Base64.NO_WRAP);
    }

    /**
     * Base64 解码
     * @param input 待处理数据
     * @param flags Base64 编解码 flags
     * @return Base64 解码后的 byte[]
     */
    public static byte[] base64Decode(
            final String input,
            final int flags
    ) {
        return base64Decode(ConvertUtils.toBytes(input), flags);
    }

    /**
     * Base64 解码
     * @param input 待处理数据
     * @return Base64 解码后的 byte[]
     */
    public static byte[] base64Decode(final byte[] input) {
        return base64Decode(input, Base64.NO_WRAP);
    }

    /**
     * Base64 解码
     * @param input 待处理数据
     * @param flags Base64 编解码 flags
     * @return Base64 解码后的 byte[]
     */
    public static byte[] base64Decode(
            final byte[] input,
            final int flags
    ) {
        if (input == null) return null;
        return Base64.decode(input, flags);
    }

    // =

    /**
     * Base64 解码
     * @param input 待处理数据
     * @return Base64 解码后的 byte[] 转 String
     */
    public static String base64DecodeToString(final String input) {
        return base64DecodeToString(input, Base64.NO_WRAP);
    }

    /**
     * Base64 解码
     * @param input 待处理数据
     * @param flags Base64 编解码 flags
     * @return Base64 解码后的 byte[] 转 String
     */
    public static String base64DecodeToString(
            final String input,
            final int flags
    ) {
        return base64DecodeToString(ConvertUtils.toBytes(input), flags);
    }

    /**
     * Base64 解码
     * @param input 待处理数据
     * @return Base64 解码后的 byte[] 转 String
     */
    public static String base64DecodeToString(final byte[] input) {
        return base64DecodeToString(input, Base64.NO_WRAP);
    }

    /**
     * Base64 解码
     * @param input 待处理数据
     * @param flags Base64 编解码 flags
     * @return Base64 解码后的 byte[] 转 String
     */
    public static String base64DecodeToString(
            final byte[] input,
            final int flags
    ) {
        if (input == null) return null;
        return ConvertUtils.newString(Base64.decode(input, flags));
    }

    // ========
    // = Html =
    // ========

    /**
     * Html 字符串编码
     * @param input 待处理数据
     * @return Html 编码后的数据
     */
    public static String htmlEncode(final CharSequence input) {
        if (input == null) return null;
        StringBuilder builder = new StringBuilder();
        char          ch;
        for (int i = 0, len = input.length(); i < len; i++) {
            ch = input.charAt(i);
            switch (ch) {
                case '<':
                    builder.append("&lt;"); //$NON-NLS-1$
                    break;
                case '>':
                    builder.append("&gt;"); //$NON-NLS-1$
                    break;
                case '&':
                    builder.append("&amp;"); //$NON-NLS-1$
                    break;
                case '\'':
                    //http://www.w3.org/TR/xhtml1
                    // The named character reference &apos; (the apostrophe, U+0027) was introduced in
                    // XML 1.0 but does not appear in HTML. Authors should therefore use &#39; instead
                    // of &apos; to work as expected in HTML 4 user agents.
                    builder.append("&#39;"); //$NON-NLS-1$
                    break;
                case '"':
                    builder.append("&quot;"); //$NON-NLS-1$
                    break;
                default:
                    builder.append(ch);
            }
        }
        return builder.toString();
    }
}