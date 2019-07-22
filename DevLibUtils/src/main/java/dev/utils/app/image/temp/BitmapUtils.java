package dev.utils.app.image.temp;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

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
     * 复制 Bitmap
     * @param bitmap    {@link Bitmap}
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

    // ===============
    // = Bitmap 操作 =
    // ===============

    /**
     * 旋转图片
     * @param bitmap 待操作原图
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
     * @param bitmap 待操作原图
     * @param degrees 旋转角度
     * @param px    旋转中心点在 X 轴的坐标
     * @param py    旋转中心点在 Y 轴的坐标
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
}