package dev.utils.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import dev.utils.JCLogUtils;

/**
 * detail: 流操作工具类
 * @author Ttt
 */
public final class StreamUtils {

    private StreamUtils() {
    }

    // 日志 TAG
    private static final String TAG = StreamUtils.class.getSimpleName();

    /**
     * 输入流转输出流
     * @param is
     * @return
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
     * 输出流转输入流
     * @param out
     * @return
     */
    public static ByteArrayInputStream outputToInputStream(final OutputStream out) {
        if (out == null) return null;
        try {
            return new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "outputToInputStream");
            return null;
        }
    }

    /**
     * 输入流转 byte[]
     * @param is
     * @return
     */
    public static byte[] inputStreamToBytes(final InputStream is) {
        if (is == null) return null;
        try {
            return inputToOutputStream(is).toByteArray();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "inputStreamToBytes");
            return null;
        }
    }

    /**
     * byte[] 转输出流
     * @param bytes
     * @return
     */
    public static InputStream bytesToInputStream(final byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        try {
            return new ByteArrayInputStream(bytes);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "bytesToInputStream");
            return null;
        }
    }

    /**
     * 输出流转 byte[]
     * @param out
     * @return
     */
    public static byte[] outputStreamToBytes(final OutputStream out) {
        if (out == null) return null;
        try {
            return ((ByteArrayOutputStream) out).toByteArray();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "outputStreamToBytes");
            return null;
        }
    }

    /**
     * byte[] 转 输出流
     * @param bytes
     * @return
     */
    public static OutputStream bytesToOutputStream(final byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
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
     * 输入流转 string
     * @param is
     * @param charsetName
     * @return
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
     * String 转换输入流
     * @param string
     * @param charsetName
     * @return
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
     * 输出流转 string
     * @param out
     * @param charsetName
     * @return
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
     * string 转 输出流
     * @param string
     * @param charsetName
     * @return
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
