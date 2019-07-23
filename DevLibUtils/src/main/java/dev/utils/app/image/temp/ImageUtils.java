package dev.utils.app.image.temp;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import dev.utils.LogPrintUtils;

/**
 * detail: Image ( Bitmap、Drawable 等 ) 工具类
 * @author Ttt
 * <pre>
 *     图片文件头标识信息
 *     @see <a href="https://www.jianshu.com/p/45c0f85c47ed"/>
 *     @see <a href="https://mp.weixin.qq.com/s?__biz=MzA3NTYzODYzMg==&mid=2653578220&idx=1&sn=bdc57c640427984e240b19d8b9e10a15&chksm=84b3b1ebb3c438fdcbd446e9f28abd2713e685246efedfc133eb453ca41d08730dbf297ba1a4&scene=4#wechat_redirect"/>
 *     @see <a href="https://developers.google.com/speed/webp/docs/riff_container"/>
 * </pre>
 */
public final class ImageUtils {

    private ImageUtils() {
    }

    // 日志 TAG
    private static final String TAG = ImageUtils.class.getSimpleName();

    /**
     * 判断 Bitmap 对象是否为 null
     * @param bitmap {@link Bitmap}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final Bitmap bitmap) {
        return bitmap == null || bitmap.getWidth() == 0 || bitmap.getHeight() == 0;
    }

    /**
     * 判断 Bitmap 对象是否不为 null
     * @param bitmap {@link Bitmap}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final Bitmap bitmap) {
        return bitmap != null && bitmap.getWidth() != 0 && bitmap.getHeight() != 0;
    }

    // =

    /**
     * 判断 Drawable 对象是否为 null
     * @param drawable {@link Drawable}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final Drawable drawable) {
        return drawable == null;
    }

    /**
     * 判断 Drawable 对象是否不为 null
     * @param drawable {@link Drawable}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final Drawable drawable) {
        return drawable != null;
    }

    // ================
    // = 图片类型判断 =
    // ================

    // 图片格式
    private static final String[] IMAGE_FORMATS = new String[]{".PNG", ".JPG", ".JPEG", ".BMP", ".GIF", ".WEBP"};

    /**
     * 根据文件名判断文件是否为图片
     * @param file 文件
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isImage(final File file) {
        return file != null && isImage(file.getPath(), IMAGE_FORMATS);
    }

    /**
     * 根据文件名判断文件是否为图片
     * @param filePath 文件路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isImage(final String filePath) {
        return isImage(filePath, IMAGE_FORMATS);
    }

    /**
     * 根据文件名判断文件是否为图片
     * @param filePath    文件路径
     * @param imageFormat 图片格式
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isImage(final String filePath, final String[] imageFormat) {
        if (filePath == null || imageFormat == null || imageFormat.length == 0) return false;
        String path = filePath.toUpperCase();
        for (String format : imageFormat) {
            if (format != null) {
                if (path.endsWith(format.toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    // =

    /**
     * 获取图片类型
     * @param filePath 文件路径
     * @return 图片类型
     */
    public static String getImageType(final String filePath) {
        return getImageType(getFileByPath(filePath));
    }

    /**
     * 获取图片类型
     * @param file 文件
     * @return 图片类型
     */
    public static String getImageType(final File file) {
        if (file == null) return null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return getImageType(is);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getImageType");
            return null;
        } finally {
            closeIOQuietly(is);
        }
    }

    /**
     * 获取图片类型
     * @param is 输入流
     * @return 图片类型
     */
    public static String getImageType(final InputStream is) {
        if (is == null) return null;
        try {
            byte[] bytes = new byte[12];
            return is.read(bytes, 0, 12) != -1 ? getImageType(bytes) : null;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getImageType");
            return null;
        }
    }

    /**
     * 获取图片类型
     * @param data 图片 byte[]
     * @return 图片类型
     */
    public static String getImageType(final byte[] data) {
        if (isPNG(data)) return "PNG";
        if (isJPEG(data)) return "JPEG";
        if (isBMP(data)) return "BMP";
        if (isGif(data)) return "GIF";
        if (isWEBP(data)) return "WEBP";
        return null;
    }

    // =

    /**
     * 判断是否 PNG 图片
     * @param data 图片 byte[]
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isPNG(final byte[] data) {
        return data != null && data.length >= 8
                && (data[0] == (byte) 137 && data[1] == (byte) 80
                && data[2] == (byte) 78 && data[3] == (byte) 71
                && data[4] == (byte) 13 && data[5] == (byte) 10
                && data[6] == (byte) 26 && data[7] == (byte) 10);
    }

    /**
     * 判断是否 JPG 图片
     * @param data 图片 byte[]
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isJPEG(final byte[] data) {
        return data != null && data.length >= 2 && (data[0] == (byte) 0xFF) && (data[1] == (byte) 0xD8);
    }

    /**
     * 判断是否 BMP 图片
     * @param data 图片 byte[]
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isBMP(final byte[] data) {
        return data != null && data.length >= 2 && (data[0] == 0x42) && (data[1] == 0x4d);
    }

    /**
     * 判断是否 GIF 图片
     * @param data 图片 byte[]
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isGif(final byte[] data) {
        return data != null && data.length >= 6 && data[0] == 'G' && data[1] == 'I' && data[2] == 'F'
                && data[3] == '8' && (data[4] == '7' || data[4] == '9') && data[5] == 'a';
    }

    /**
     * 判断是否 webp 图片
     * @param data 图片 byte[]
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isWEBP(final byte[] data) {
        return data != null && data.length >= 12 && data[0] == 'R' && data[1] == 'I' && data[2] == 'F' && data[3] == 'F'
                && data[8] == 'W' && (data[9] == 'E' || data[10] == 'B') && data[11] == 'P';
    }

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // =============
    // = FileUtils =
    // =============

    /**
     * 获取文件
     * @param filePath 文件路径
     * @return 文件 {@link File}
     */
    private static File getFileByPath(final String filePath) {
        return filePath != null ? new File(filePath) : null;
    }

    // ==============
    // = CloseUtils =
    // ==============

    /**
     * 安静关闭 IO
     * @param closeables Closeable[]
     */
    private static void closeIOQuietly(final Closeable... closeables) {
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