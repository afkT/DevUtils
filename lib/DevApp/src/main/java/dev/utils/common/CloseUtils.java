package dev.utils.common;

import java.io.Closeable;
import java.io.Flushable;
import java.io.OutputStream;
import java.io.Writer;

import dev.utils.JCLogUtils;

/**
 * detail: 关闭 ( IO 流 ) 工具类
 * @author Ttt
 */
public final class CloseUtils {

    private CloseUtils() {
    }

    // 日志 TAG
    private static final String TAG = CloseUtils.class.getSimpleName();

    /**
     * 关闭 IO
     * @param closeables Closeable[]
     */
    public static void closeIO(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "closeIO");
                }
            }
        }
    }

    /**
     * 安静关闭 IO
     * @param closeables Closeable[]
     */
    public static void closeIOQuietly(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

    // =

    /**
     * 将缓冲区数据输出
     * @param flushables Flushable[]
     */
    public static void flush(final Flushable... flushables) {
        if (flushables == null) return;
        for (Flushable flushable : flushables) {
            if (flushable != null) {
                try {
                    flushable.flush();
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "flush");
                }
            }
        }
    }

    /**
     * 安静将缓冲区数据输出
     * @param flushables Flushable[]
     */
    public static void flushQuietly(final Flushable... flushables) {
        if (flushables == null) return;
        for (Flushable flushable : flushables) {
            if (flushable != null) {
                try {
                    flushable.flush();
                } catch (Exception ignore) {
                }
            }
        }
    }

    // =

    /**
     * 将缓冲区数据输出并关闭流
     * @param outputStream {@link OutputStream}
     */
    public static void flushCloseIO(final OutputStream outputStream) {
        if (outputStream == null) return;
        try {
            outputStream.flush();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "flushCloseIO");
        }
        try {
            outputStream.close();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "flushCloseIO");
        }
    }

    /**
     * 安静将缓冲区数据输出并关闭流
     * @param outputStream {@link OutputStream}
     */
    public static void flushCloseIOQuietly(final OutputStream outputStream) {
        if (outputStream == null) return;
        try {
            outputStream.flush();
        } catch (Exception e) {
        }
        try {
            outputStream.close();
        } catch (Exception e) {
        }
    }

    /**
     * 将缓冲区数据输出并关闭流
     * @param writer {@link Writer}
     */
    public static void flushCloseIO(final Writer writer) {
        if (writer == null) return;
        try {
            writer.flush();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "flushCloseIO");
        }
        try {
            writer.close();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "flushCloseIO");
        }
    }

    /**
     * 安静将缓冲区数据输出并关闭流
     * @param writer {@link Writer}
     */
    public static void flushCloseIOQuietly(final Writer writer) {
        if (writer == null) return;
        try {
            writer.flush();
        } catch (Exception e) {
        }
        try {
            writer.close();
        } catch (Exception e) {
        }
    }
}