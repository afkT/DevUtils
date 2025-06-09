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
     * @param inputStream {@link InputStream}
     * @return {@link ByteArrayOutputStream}
     */
    public static ByteArrayOutputStream inputToOutputStream(final InputStream inputStream) {
        if (inputStream == null) return null;
        try {
            ByteArrayOutputStream baos   = new ByteArrayOutputStream();
            byte[]                buffer = new byte[1024];
            int                   len;
            while ((len = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return baos;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "inputToOutputStream");
            return null;
        } finally {
            CloseUtils.closeIOQuietly(inputStream);
        }
    }

    /**
     * 输出流转输入流
     * @param outputStream {@link OutputStream}
     * @return {@link ByteArrayInputStream}
     */
    public static ByteArrayInputStream outputToInputStream(final OutputStream outputStream) {
        if (outputStream == null) return null;
        try {
            return new ByteArrayInputStream(((ByteArrayOutputStream) outputStream).toByteArray());
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "outputToInputStream");
            return null;
        }
    }

    /**
     * 输入流转 byte[]
     * @param inputStream {@link InputStream}
     * @return byte[]
     */
    public static byte[] inputStreamToBytes(final InputStream inputStream) {
        if (inputStream == null) return null;
        try {
            return inputToOutputStream(inputStream).toByteArray();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "inputStreamToBytes");
            return null;
        }
    }

    /**
     * byte[] 转输出流
     * @param bytes 数据源
     * @return {@link InputStream}
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
     * @param outputStream {@link OutputStream}
     * @return byte[]
     */
    public static byte[] outputStreamToBytes(final OutputStream outputStream) {
        if (outputStream == null) return null;
        try {
            return ((ByteArrayOutputStream) outputStream).toByteArray();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "outputStreamToBytes");
            return null;
        }
    }

    /**
     * byte[] 转 输出流
     * @param bytes 数据源
     * @return {@link OutputStream}
     */
    public static OutputStream bytesToOutputStream(final byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            baos.write(bytes);
            return baos;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "bytesToOutputStream");
            return null;
        } finally {
            CloseUtils.closeIOQuietly(baos);
        }
    }

    /**
     * 输入流转 String
     * @param inputStream {@link InputStream}
     * @param charsetName 编码格式
     * @return 指定编码字符串
     */
    public static String inputStreamToString(
            final InputStream inputStream,
            final String charsetName
    ) {
        if (inputStream == null || StringUtils.isEmpty(charsetName)) return null;
        try {
            return new String(inputStreamToBytes(inputStream), charsetName);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "inputStreamToString");
            return null;
        }
    }

    /**
     * String 转换输入流
     * @param str         数据源
     * @param charsetName 编码格式
     * @return {@link InputStream}
     */
    public static InputStream stringToInputStream(
            final String str,
            final String charsetName
    ) {
        if (str == null || StringUtils.isEmpty(charsetName)) return null;
        try {
            return new ByteArrayInputStream(str.getBytes(charsetName));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "stringToInputStream");
            return null;
        }
    }

    /**
     * 输出流转 String
     * @param outputStream {@link OutputStream}
     * @param charsetName  编码格式
     * @return 指定编码字符串
     */
    public static String outputStreamToString(
            final OutputStream outputStream,
            final String charsetName
    ) {
        if (outputStream == null || StringUtils.isEmpty(charsetName)) return null;
        try {
            return new String(outputStreamToBytes(outputStream), charsetName);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "outputStreamToString");
            return null;
        }
    }

    /**
     * String 转 输出流
     * @param str         数据源
     * @param charsetName 编码格式
     * @return {@link OutputStream}
     */
    public static OutputStream stringToOutputStream(
            final String str,
            final String charsetName
    ) {
        if (str == null || StringUtils.isEmpty(charsetName)) return null;
        try {
            return bytesToOutputStream(str.getBytes(charsetName));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "stringToOutputStream");
            return null;
        }
    }

    // =================
    // = 输入流写入输出流 =
    // =================

    // 无数据读取
    public static final int EOF = -1;

    /**
     * 通过输入流写入输出流
     * @param inputStream  {@link InputStream}
     * @param outputStream {@link OutputStream}
     * @return {@code true} success, {@code false} fail
     */
    public static boolean inputWriteOutputStream(
            final InputStream inputStream,
            final OutputStream outputStream
    ) {
        return inputWriteOutputStream(inputStream, outputStream, 8192);
    }

    /**
     * 通过输入流写入输出流
     * @param inputStream  {@link InputStream}
     * @param outputStream {@link OutputStream}
     * @param bufferSize   缓冲 Buffer 大小
     * @return {@code true} success, {@code false} fail
     */
    public static boolean inputWriteOutputStream(
            final InputStream inputStream,
            final OutputStream outputStream,
            final int bufferSize
    ) {
        try {
            byte[] data = new byte[bufferSize];
            int    len;
            while ((len = inputStream.read(data)) != EOF) {
                outputStream.write(data, 0, len);
            }
            return true;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "inputWriteOutputStream");
            return false;
        } finally {
            CloseUtils.closeIOQuietly(inputStream, outputStream);
        }
    }

    /**
     * 通过输入流写入输出流 ( 回调通知进度 )
     * @param inputStream  {@link InputStream}
     * @param outputStream {@link OutputStream}
     * @param totalSize    数据总长度
     * @param callback     写入回调
     * @return {@code true} success, {@code false} fail
     */
    public static boolean inputWriteOutputStreamCallback(
            final InputStream inputStream,
            final OutputStream outputStream,
            final int totalSize,
            final WriteCallback callback
    ) {
        return inputWriteOutputStreamCallback(
                inputStream, outputStream, 8192, totalSize, callback
        );
    }

    /**
     * 通过输入流写入输出流 ( 回调通知进度 )
     * @param inputStream  {@link InputStream}
     * @param outputStream {@link OutputStream}
     * @param bufferSize   缓冲 Buffer 大小
     * @param totalSize    数据总长度
     * @param callback     写入回调
     * @return {@code true} success, {@code false} fail
     */
    public static boolean inputWriteOutputStreamCallback(
            final InputStream inputStream,
            final OutputStream outputStream,
            final int bufferSize,
            final int totalSize,
            final WriteCallback callback
    ) {
        if (callback != null && totalSize > 0) {
            // 开始写入
            callback.onStart();
            try {
                byte[] data        = new byte[bufferSize];
                int    len;
                int    currentSize = 0;
                while ((len = inputStream.read(data)) != EOF) {
                    outputStream.write(data, 0, len);
                    // 累加已写入长度
                    currentSize += len;
                    // 计算百分比进度
                    double percentD = (NumberUtils.percentD(
                            currentSize, totalSize
                    ) * 100);
                    // 写入进度回调
                    callback.onProgress(currentSize, totalSize, (int) percentD);
                }
                // 写入完成回调
                callback.onFinish();
                callback.onEnd();
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "inputWriteOutputStreamCallback");
                // 写入异常回调
                callback.onError(e);
                callback.onEnd();
                return false;
            } finally {
                CloseUtils.closeIOQuietly(inputStream, outputStream);
            }
        }
        return false;
    }

    /**
     * detail: 写入回调接口
     * @author Ttt
     */
    public interface WriteCallback {

        /**
         * 开始写入
         */
        void onStart();

        /**
         * 写入进度回调
         * @param currentSize 当前已写入长度
         * @param totalSize   数据总长度
         * @param percent     写入百分比
         */
        void onProgress(
                long currentSize,
                long totalSize,
                int percent
        );

        /**
         * 写入异常回调
         * @param error 异常信息
         */
        void onError(Throwable error);

        /**
         * 写入完成回调
         */
        void onFinish();

        /**
         * 流程结束回调
         * 不管是 {@link #onError)}、{@link #onFinish)} 最终都会触发该结束方法
         */
        void onEnd();
    }
}