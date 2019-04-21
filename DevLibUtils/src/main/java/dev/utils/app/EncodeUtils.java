package dev.utils.app;

import android.os.Build;
import android.text.Html;
import android.util.Base64;

import java.net.URLDecoder;
import java.net.URLEncoder;

import dev.utils.LogPrintUtils;

/**
 * detail: 编码工具类
 * @author Ttt
 */
public final class EncodeUtils {

    private EncodeUtils() {
    }

    // 日志 TAG
    private static final String TAG = EncodeUtils.class.getSimpleName();

    /**
     * url 编码 - UTF-8
     * @param input
     * @return
     */
    public static String urlEncode(final String input) {
        return urlEncode(input, "UTF-8");
    }

    /**
     * url 编码
     * @param input
     * @param charsetName
     * @return
     */
    public static String urlEncode(final String input, final String charsetName) {
        try {
            return URLEncoder.encode(input, charsetName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "urlEncode");
        }
        return null;
    }

    /**
     * url 解码 - UTF-8
     * @param input
     * @return
     */
    public static String urlDecode(final String input) {
        return urlDecode(input, "UTF-8");
    }

    /**
     * url 解码
     * @param input
     * @param charsetName
     * @return
     */
    public static String urlDecode(final String input, final String charsetName) {
        try {
            return URLDecoder.decode(input, charsetName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "urlDecode");
        }
        return null;
    }

//    CRLF 这个参数看起来比较眼熟，它就是Win风格的换行符，意思就是使用CR LF这一对作为一行的结尾而不是Unix风格的LF
//
//    DEFAULT 这个参数是默认，使用默认的方法来加密
//
//    NO_PADDING 这个参数是略去加密字符串最后的”=”
//
//    NO_WRAP 这个参数意思是略去所有的换行符(设置后CRLF就没用了)
//
//    URL_SAFE 这个参数意思是加密时不使用对URL和文件名有特殊意义的字符来作为加密字符，具体就是以-和_取代+和/

    /**
     * base64 编码 => 去除\n 替换 + 和 - 号
     * @param input
     * @return
     */
    public static byte[] base64Encode(final String input) {
        if (input == null) return null;
        try {
            return base64Encode(input.getBytes());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "base64Encode");
        }
        return null;
    }

    /**
     * base64 编码
     * @param input
     * @return
     */
    public static byte[] base64Encode(final byte[] input) {
        if (input == null) return null;
        try {
            return Base64.encode(input, Base64.NO_WRAP);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "base64Encode");
        }
        return null;
    }

    /**
     * base64 编码
     * @param input
     * @return
     */
    public static String base64EncodeToString(final byte[] input) {
        if (input == null) return null;
        try {
            return Base64.encodeToString(input, Base64.NO_WRAP);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "base64EncodeToString");
        }
        return null;
    }

    // =

    /**
     * base64 解码
     * @param input
     * @return
     */
    public static byte[] base64Decode(final String input) {
        if (input == null) return null;
        try {
            return Base64.decode(input, Base64.NO_WRAP);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "base64Decode");
        }
        return null;
    }

    /**
     * base64 解码
     * @param input
     * @return
     */
    public static byte[] base64Decode(final byte[] input) {
        if (input == null) return null;
        try {
            return Base64.decode(input, Base64.NO_WRAP);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "base64Decode");
        }
        return null;
    }

    /**
     * base64 解码
     * @param input
     * @return
     */
    public static String base64DecodeToString(final byte[] input) {
        if (input == null) return null;
        try {
            return new String(base64Decode(input));
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "base64DecodeToString");
        }
        return null;
    }

    // =

    /**
     * html 编码
     * @param input
     * @return
     */
    public static String htmlEncode(final CharSequence input) {
        if (input == null) return null;
        StringBuilder builder = new StringBuilder();
        char ch;
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
                    // http://www.w3.org/TR/xhtml1
                    // The named character reference &apos; (the apostrophe, U+0027) was
                    // introduced in XML 1.0 but does not appear in HTML. Authors should
                    // therefore use &#39; instead of &apos; to work as expected in HTML 4
                    // user agents.
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

    /**
     * html 解码
     * @param input
     * @return
     */
    @SuppressWarnings("deprecation")
    public static CharSequence htmlDecode(final String input) {
        if (input == null) return null;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY);
            } else {
                return Html.fromHtml(input);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "htmlDecode");
        }
        return null;
    }
}
