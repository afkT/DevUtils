package dev.utils.common;

import java.io.Closeable;

import dev.utils.JCLogUtils;

/**
 * detail: 关闭(IO流)工具类
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
}
