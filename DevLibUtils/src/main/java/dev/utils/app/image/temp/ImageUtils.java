package dev.utils.app.image.temp;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * detail: Image ( Bitmap、Drawable 等 ) 工具类
 * @author Ttt
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
     * 判断 Drawable 对象是否为 null
     * @param drawable {@link Drawable}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final Drawable drawable) {
        return drawable == null;
    }

    /**
     * 判断 Bitmap 对象是否不为 null
     * @param bitmap {@link Bitmap}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final Bitmap bitmap) {
        return bitmap != null && bitmap.getWidth() != 0 && bitmap.getHeight() != 0;
    }

    /**
     * 判断 Drawable 对象是否不为 null
     * @param drawable {@link Drawable}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final Drawable drawable) {
        return drawable != null;
    }

    // ===========================
    // = Bitmap、Drawable 等获取 =
    // ===========================

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // ===============
    // = BitmapUtils =
    // ===============

    /**
     * 复制 Bitmap
     * @param bitmap    {@link Bitmap}
     * @param newBitmap 是否创建新的 Bitmap
     * @return {@link Bitmap}
     */
    private static Bitmap copyBitmap(final Bitmap bitmap, final boolean newBitmap) {
        if (bitmap == null) return null;
        // 需要创建新的 Bitmap 或 原 Bitmap 无法修改
        if (newBitmap || !bitmap.isMutable()) { // 则复制新的
            return bitmap.copy(bitmap.getConfig(), true);
        }
        return bitmap;
    }
}
