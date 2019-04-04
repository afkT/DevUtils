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

    private StreamUtils() {
    }

    // 日志 TAG
    private static final String TAG = StreamUtils.class.getSimpleName();

    /**
     * Input stream to output stream.
     * @param is The input stream.
     * @return output stream
     */
    public static ByteArrayOutputStream inputToOutputStream(final InputStream is) {
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
            JCLogUtils.eTag(TAG, e, "inputToOutputStream");
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
    public static ByteArrayInputStream outputToInputStream(final OutputStream out) {
        if (out == null) return null;
        return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
    }

    /**
     * Input stream to bytes.
     * @param is The input stream.
     * @return bytes
     */
    public static byte[] inputStreamToBytes(final InputStream is) {
        if (is == null) return null;
        return inputToOutputStream(is).toByteArray();
    }

    /**
     * Bytes to input stream.
     * @param bytes The bytes.
     * @return input stream
     */
    public static InputStream bytesToInputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        return new ByteArrayInputStream(bytes);
    }

    /**
     * Output stream to bytes.
     * @param out The output stream.
     * @return bytes
     */
    public static byte[] outputStreamToBytes(final OutputStream out) {
        if (out == null) return null;
        return ((ByteArrayOutputStream) out).toByteArray();
    }

    /**
     * Bytes to output stream.
     * @param bytes The bytes.
     * @return output stream
     */
    public static OutputStream bytesToOutputStream(final byte[] bytes) {
        if (bytes == null || bytes.length <= 0) return null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            os.write(bytes);
            return os;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "bytesToOutputStream");
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
    public static String inputStreamToString(final InputStream is, final String charsetName) {
        if (is == null || isSpace(charsetName)) return null;
        try {
            return new String(inputStreamToBytes(is), charsetName);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "inputStreamToString");
            return null;
        }
    }

    /**
     * String to input stream.
     * @param string The string.
     * @param charsetName The name of charset.
     * @return input stream
     */
    public static InputStream stringToInputStream(final String string, final String charsetName) {
        if (string == null || isSpace(charsetName)) return null;
        try {
            return new ByteArrayInputStream(string.getBytes(charsetName));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "stringToInputStream");
            return null;
        }
    }

    /**
     * Output stream to string.
     * @param out The output stream.
     * @param charsetName The name of charset.
     * @return string
     */
    public static String outputStreamToString(final OutputStream out, final String charsetName) {
        if (out == null || isSpace(charsetName)) return null;
        try {
            return new String(outputStreamToBytes(out), charsetName);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "outputStreamToString");
            return null;
        }
    }

    /**
     * String to output stream.
     * @param string The string.
     * @param charsetName The name of charset.
     * @return output stream
     */
    public static OutputStream stringToOutputStream(final String string, final String charsetName) {
        if (string == null || isSpace(charsetName)) return null;
        try {
            return bytesToOutputStream(string.getBytes(charsetName));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "stringToOutputStream");
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
