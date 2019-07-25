package dev.utils.app.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;

import java.io.File;
import java.io.FileDescriptor;

import dev.DevUtils;

/**
 * detail: 图片相关工具类
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

    // =

    /**
     * 判断字符串是否为 null 或全为空白字符
     * @param str 待校验字符串
     * @return {@code true} yes, {@code false} no
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

    /**
     * 计算采样大小
     * <pre>
     *     最大宽高只是阀值, 实际算出来的图片将小于等于这个值
     * </pre>
     * @param options   选项
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 采样大小
     */
    public static int calculateInSampleSize(final BitmapFactory.Options options, final int maxWidth, final int maxHeight) {
        if (options == null) return 0;

        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        while (height > maxHeight || width > maxWidth) {
            height >>= 1;
            width >>= 1;
            inSampleSize <<= 1;
        }
        return inSampleSize;
    }

    // =

    /**
     * 获取 bitmap
     * @param file
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return
     */
    public static Bitmap getBitmap(final File file, final int maxWidth, final int maxHeight) {
        if (file == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    /**
     * 获取 bitmap
     * @param filePath
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return
     */
    public static Bitmap getBitmap(final String filePath, final int maxWidth, final int maxHeight) {
        if (isSpace(filePath)) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 获取 bitmap
     * @param resId     资源 id
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return
     */
    public static Bitmap getBitmap(@DrawableRes final int resId, final int maxWidth, final int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        final Resources resources = DevUtils.getContext().getResources();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    /**
     * 获取 bitmap
     * @param fd        文件描述
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return
     */
    public static Bitmap getBitmap(final FileDescriptor fd, final int maxWidth, final int maxHeight) {
        if (fd == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }
//
//    // = 裁剪图片, 裁剪中间部分, 防止全图压缩 =
//
//    /**
//     * 裁剪图片(默认比例16:9)
//     *
//     * @param filePath 保存路径
//     * @param bitmap   图片
//     */
//    public static void cropBitmap(final String filePath, final Bitmap bitmap) {
//        cropBitmap(filePath, bitmap, 16.0f, 9.0f);
//    }
//
//    /**
//     * 裁剪图片
//     *
//     * @param filePath    保存路径
//     * @param bitmap      图片
//     * @param widthScale  宽度比例
//     * @param heightScale 高度比例
//     */
//    public static void cropBitmap(final String filePath, final Bitmap bitmap, final float widthScale, final float heightScale) {
//        if (TextUtils.isEmpty(filePath)) return;
//        File file = new File(filePath);
//        if (file.isDirectory() || !file.exists()) return;
//
//        if (bitmap == null) {
//            return; // 防止图片为 null
//        }
//        // 裁剪处理后的图片
//        Bitmap newBitmap = null;
//        try {
//            // 获取图片宽度
//            int iWidth = bitmap.getWidth();
//            // 获取图片高度
//            int iHeight = bitmap.getHeight();
//            // 获取需要裁剪的高度
//            int rHeight = (int) ((iWidth * heightScale) / widthScale);
//            // 判断需要裁剪的高度与偏移差距
//            int dHeight = iHeight - rHeight;
//            // =
//            // 判断裁剪方式
//            if (dHeight >= 0) { // 属于宽度 * 对应比例 >= 高度, 以高度做偏移
//                // 计算偏移的y轴
//                int offsetY = dHeight / 2;
//                // 创建图片
//                newBitmap = Bitmap.createBitmap(bitmap, 0, offsetY, iWidth, rHeight, null, false);
//            } else { // 以宽度做偏移
//                // 获取需要裁剪的宽度
//                int rWidth = (int) ((iHeight * widthScale) / heightScale);
//                // 判断需要裁剪的宽度与偏移差距
//                int dWidth = iWidth - rWidth;
//                // 计算偏移的X轴
//                int offsetX = dWidth / 2;
//                // 创建图片
//                newBitmap = Bitmap.createBitmap(bitmap, offsetX, 0, rWidth, iHeight, null, false);
//            }
//            if (newBitmap != null) {
//                // 保存图片
//                dev.utils.app.image.temp.ImageUtils.saveBitmapToSDCardPNG(newBitmap, filePath, 85);
//            }
//        } catch (Exception e) {
//            LogPrintUtils.eTag(TAG, e, "cropBitmap");
//        } finally {
//            // = 清空资源 =
//            try {
//                if (newBitmap != null && !newBitmap.isRecycled()) {
//                    newBitmap.recycle();
//                }
//            } catch (Exception e) {
//            }
//            newBitmap = null;
//        }
//    }
}