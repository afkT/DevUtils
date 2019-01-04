package dev.utils.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import dev.utils.JCLogUtils;

/**
 * detail: 流操作工具类
 * Created by Ttt
 */
public final class StreamUtils {

    private StreamUtils(){
    }

    // 日志TAG
    private static final String TAG = StreamUtils.class.getSimpleName();

    /**
     * Input stream to output stream.
     * @param is The input stream.
     * @return output stream
     */
    public static ByteArrayOutputStream input2OutputStream(final InputStream is) {
        if (is == null) return null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b, 0, 1024)) != -1) {
                os.write(b, 0, len);
            }
            return os;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "input2OutputStream");
            return null;
        } finally {
            CloseUtils.closeIO(is);
        }
    }

    /**
     * Output stream to input stream.
     * @param out The output stream.
     * @return input stream
     */
    public static ByteArrayInputStream output2InputStream(final OutputStream out) {
        if (out == null) return null;
        return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
    }

    /**
     * Input stream to bytes.
     * @param is The input stream.
     * @return bytes
     */
    public static byte[] inputStream2Bytes(final InputStream is) {
        if (is == null) return null;
        return input2OutputStream(is).toByteArray();
    }

    /**
     * Bytes to input stream.
     * @param bytes The bytes.
     * @return input stream
     */
    public static InputStream bytes2InputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        return new ByteArrayInputStream(bytes);
    }

    /**
     * Output stream to bytes.
     * @param out The output stream.
     * @return bytes
     */
    public static byte[] outputStream2Bytes(final OutputStream out) {
        if (out == null) return null;
        return ((ByteArrayOutputStream) out).toByteArray();
    }

    /**
     * Bytes to output stream.
     * @param bytes The bytes.
     * @return output stream
     */
    public static OutputStream bytes2OutputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            os.write(bytes);
            return os;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "bytes2OutputStream");
            return null;
        } finally {
            CloseUtils.closeIO(os);
        }
    }

    /**
     * Input stream to string.
     * @param is The input stream.
     * @param charsetName The name of charset.
     * @return string
     */
    public static String inputStream2String(final InputStream is, final String charsetName) {
        if (is == null || isSpace(charsetName)) return null;
        try {
            return new String(inputStream2Bytes(is), charsetName);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "inputStream2String");
            return null;
        }
    }

    /**
     * String to input stream.
     * @param string The string.
     * @param charsetName The name of charset.
     * @return input stream
     */
    public static InputStream string2InputStream(final String string, final String charsetName) {
        if (string == null || isSpace(charsetName)) return null;
        try {
            return new ByteArrayInputStream(string.getBytes(charsetName));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "string2InputStream");
            return null;
        }
    }

    /**
     * Output stream to string.
     * @param out The output stream.
     * @param charsetName The name of charset.
     * @return string
     */
    public static String outputStream2String(final OutputStream out, final String charsetName) {
        if (out == null || isSpace(charsetName)) return null;
        try {
            return new String(outputStream2Bytes(out), charsetName);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "outputStream2String");
            return null;
        }
    }

    /**
     * String to output stream.
     * @param string The string.
     * @param charsetName The name of charset.
     * @return output stream
     */
    public static OutputStream string2OutputStream(final String string, final String charsetName) {
        if (string == null || isSpace(charsetName)) return null;
        try {
            return bytes2OutputStream(string.getBytes(charsetName));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "string2OutputStream");
            return null;
        }
    }

    // =

    /**
     * 判断字符串是否为 null 或全为空白字符
     * @param str 待校验字符串
     * @return
     */
    private static boolean isSpace(final String str) {
        if (str == null) return true;
        for (int i = 0, len = str.length(); i < len; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
