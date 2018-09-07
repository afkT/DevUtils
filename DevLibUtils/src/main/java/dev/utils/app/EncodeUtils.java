package dev.utils.app;

import android.os.Build;
import android.text.Html;
import android.util.Base64;

import java.net.URLDecoder;
import java.net.URLEncoder;

import dev.utils.LogPrintUtils;

/**
 * detail: 编码工具类
 * Created by Ttt
 */
public final class EncodeUtils {

    private EncodeUtils() {
    }

    // 日志TAG
    private static final String TAG = EncodeUtils.class.getSimpleName();

    /**
     * url编码 - UTF-8
     * @param input The input.
     * @return the urlencoded string
     */
    public static String urlEncode(final String input) {
        return urlEncode(input, "UTF-8");
    }

    /**
     * url编码
     * @param input The input.
     * @param charsetName The name of charset.
     * @return the urlencoded string
     */
    public static String urlEncode(final String input, final String charsetName) {
        try {
            return URLEncoder.encode(input, charsetName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "urlEncode");
            return null;
        }
    }

    /**
     * url 解码 - UTF-8
     * @param input The input.
     * @return the string of decode urlencoded string
     */
    public static String urlDecode(final String input) {
        return urlDecode(input, "UTF-8");
    }

    /**
     * url 解码
     * @param input The input.
     * @param charsetName The name of charset.
     * @return the string of decode urlencoded string
     */
    public static String urlDecode(final String input, final String charsetName) {
        try {
            return URLDecoder.decode(input, charsetName);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "urlDecode");
            return null;
        }
    }

//    CRLF 这个参数看起来比较眼熟，它就是Win风格的换行符，意思就是使用CR LF这一对作为一行的结尾而不是Unix风格的LF
//
//    DEFAULT 这个参数是默认，使用默认的方法来加密
//
//    NO_PADDING 这个参数是略去加密字符串最后的”=”
//
//    NO_WRAP 这个参数意思是略去所有的换行符（设置后CRLF就没用了）
//
//    URL_SAFE 这个参数意思是加密时不使用对URL和文件名有特殊意义的字符来作为加密字符，具体就是以-和_取代+和/

    /**
     * base64 编码 => 去除\n 替换 + 和 - 号
     * @param input The input.
     * @return Base64-encode bytes
     */
    public static byte[] base64Encode(final String input) {
        return base64Encode(input.getBytes());
    }

    /**
     * base64 编码
     * @param input The input.
     * @return Base64-encode bytes
     */
    public static byte[] base64Encode(final byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    /**
     * base64 编码
     * @param input The input.
     * @return Base64-encode string
     */
    public static String base64Encode2String(final byte[] input) {
        return Base64.encodeToString(input, Base64.NO_WRAP);
    }

    /**
     * base64 解码
     * @param input The input.
     * @return the string of decode Base64-encode string
     */
    public static byte[] base64Decode(final String input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * base64 解码
     * @param input The input.
     * @return the bytes of decode Base64-encode bytes
     */
    public static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * base64 解码
     * @param input
     * @return
     */
    public static String base64DecodeToString(final byte[] input) {
        try {
            return new String(base64Decode(input));
        } catch (Exception e){
            LogPrintUtils.eTag(TAG, e, "base64DecodeToString");
        }
        return null;
    }

    /**
     * html编码
     * @param input The input.
     * @return html-encode string
     */
    public static String htmlEncode(final CharSequence input) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0, len = input.length(); i < len; i++) {
            c = input.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;"); //$NON-NLS-1$
                    break;
                case '>':
                    sb.append("&gt;"); //$NON-NLS-1$
                    break;
                case '&':
                    sb.append("&amp;"); //$NON-NLS-1$
                    break;
                case '\'':
                    //http://www.w3.org/TR/xhtml1
                    // The named character reference &apos; (the apostrophe, U+0027) was
                    // introduced in XML 1.0 but does not appear in HTML. Authors should
                    // therefore use &#39; instead of &apos; to work as expected in HTML 4
                    // user agents.
                    sb.append("&#39;"); //$NON-NLS-1$
                    break;
                case '"':
                    sb.append("&quot;"); //$NON-NLS-1$
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * html解码
     * @param input The input.
     * @return the string of decode html-encode string
     */
    @SuppressWarnings("deprecation")
    public static CharSequence htmlDecode(final String input) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(input);
        }
    }
}
