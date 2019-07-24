package dev.utils.app.image.temp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;

import dev.utils.LogPrintUtils;

/**
 * detail: Bitmap 工具类
 * @author Ttt
 */
public final class BitmapUtils {

    private BitmapUtils() {
    }

    // 日志 TAG
    private static final String TAG = BitmapUtils.class.getSimpleName();

    // ==============
    // = ImageUtils =
    // ==============

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
     * 获取 Bitmap 宽高 ( 不加载图片到内存中 )
     * @param file 文件
     * @return int[] { 宽度, 高度 }
     */
    public static int[] getBitmapWidthHeight(final File file) {
        return getBitmapWidthHeight(getAbsolutePath(file));
    }

    /**
     * 获取 Bitmap 宽高 ( 不加载图片到内存中 )
     * @param filePath 文件路径
     * @return int[] { 宽度, 高度 }
     */
    public static int[] getBitmapWidthHeight(final String filePath) {
        if (!isFileExists(filePath)) return null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 只解析图片信息, 不加载到内存中
            options.inJustDecodeBounds = true;
            // 返回的 bitmap 为 null
            BitmapFactory.decodeFile(filePath, options);
            // options.outHeight 为原始图片的高
            return new int[]{options.outWidth, options.outHeight};
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmapWidthHeight");
        }
        return null;
    }

    // =

    /**
     * 复制 Bitmap
     * @param bitmap {@link Bitmap}
     * @return {@link Bitmap}
     */
    public static Bitmap copy(final Bitmap bitmap) {
        return copy(bitmap, true);
    }

    /**
     * 复制 Bitmap
     * @param bitmap    {@link Bitmap}
     * @param isMutable 是否允许编辑
     * @return {@link Bitmap}
     */
    public static Bitmap copy(final Bitmap bitmap, final boolean isMutable) {
        if (bitmap == null) return null;
        return bitmap.copy(bitmap.getConfig(), isMutable);
    }

    // =

    /**
     * 获取 Alpha 位图 ( 获取原图的轮廓 rgb 为 0)
     * @param bitmap {@link Bitmap}
     * @return Alpha 位图
     */
    public static Bitmap extractAlpha(final Bitmap bitmap) {
        if (bitmap == null) return null;
        return bitmap.extractAlpha();
    }

    /**
     * 重新编码 Bitmap
     * @param bitmap  需要重新编码的 bitmap
     * @param format  编码后的格式 如 Bitmap.CompressFormat.PNG
     * @param quality 重新生成后的 bitmap 的质量
     * @return {@link Bitmap}
     */
    public static Bitmap recode(final Bitmap bitmap, final Bitmap.CompressFormat format, final int quality) {
        if (bitmap == null || format == null || quality <= 0) return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(format, quality, baos);
            byte[] data = baos.toByteArray();
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "recode");
        }
        return null;
    }

    // ===============
    // = Bitmap 操作 =
    // ===============

    /**
     * 旋转图片
     * @param bitmap  待操作原图
     * @param degrees 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotate(final Bitmap bitmap, final float degrees) {
        if (bitmap == null) return null;
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    /**
     * 旋转图片
     * @param bitmap  待操作原图
     * @param degrees 旋转角度
     * @param px      旋转中心点在 X 轴的坐标
     * @param py      旋转中心点在 Y 轴的坐标
     * @return 旋转后的图片
     */
    public static Bitmap rotate(final Bitmap bitmap, final float degrees, final float px, final float py) {
        if (bitmap == null) return null;
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees, px, py);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    /**
     * 读取图片属性, 获取图片被旋转的角度
     * @param filePath 文件路径
     * @return 旋转角度
     */
    public static int getRotateDegree(final String filePath) {
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
                default:
                    return 0;
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getRotateDegree");
            return 0;
        }
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

    /**
     * 获取文件绝对路径
     * @param file 文件
     * @return 文件绝对路径
     */
    private static String getAbsolutePath(final File file) {
        return file != null ? file.getAbsolutePath() : null;
    }

    /**
     * 检查是否存在某个文件
     * @param file 文件
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * 检查是否存在某个文件
     * @param filePath 文件路径
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isFileExists(final String filePath) {
        return isFileExists(getFileByPath(filePath));
    }
}