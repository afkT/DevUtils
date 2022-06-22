package dev.utils.app.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.MediaMetadataRetriever;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.InputStream;
import java.util.HashMap;

import dev.DevUtils;
import dev.utils.LogPrintUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.assist.exif.ExifAssist;
import dev.utils.common.FileUtils;
import dev.utils.common.ScaleUtils;

/**
 * detail: Bitmap 工具类
 * @author Ttt
 * <pre>
 *     Android PorterDuffXfermode 的正确使用方式
 *     @see <a href="https://www.jianshu.com/p/d11892bbe055"/>
 *     Android 中一张图片占据的内存大小是如何计算
 *     @see <a href="https://www.cnblogs.com/dasusu/p/9789389.html"/>
 *     Bitmap 的六种压缩方式, Android 图片压缩
 *     @see <a href="https://blog.csdn.net/harryweasley/article/details/51955467"/>
 * </pre>
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
        return ImageUtils.isEmpty(bitmap);
    }

    /**
     * 判断 Bitmap 对象是否不为 null
     * @param bitmap {@link Bitmap}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final Bitmap bitmap) {
        return ImageUtils.isNotEmpty(bitmap);
    }

    // ==========
    // = 图片判断 =
    // ==========

    /**
     * 根据文件判断是否为图片
     * @param file 文件
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isImage(final File file) {
        return file != null && isImage(file.getPath());
    }

    /**
     * 根据文件判断是否为图片
     * @param filePath 文件路径
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isImage(final String filePath) {
        if (!FileUtils.isFileExists(filePath)) return false;
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 只解析图片信息, 不加载到内存中
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(filePath, options);
            return options.outWidth != -1 && options.outHeight != -1;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isImage");
        }
        return false;
    }

    // =======
    // = 宽高 =
    // =======

    /**
     * 获取 Bitmap 宽度
     * @param bitmap 源图片
     * @return Bitmap 宽度
     */
    public static int getBitmapWidth(final Bitmap bitmap) {
        if (isEmpty(bitmap)) return 0;
        return bitmap.getWidth();
    }

    /**
     * 获取 Bitmap 高度
     * @param bitmap 源图片
     * @return Bitmap 高度
     */
    public static int getBitmapHeight(final Bitmap bitmap) {
        if (isEmpty(bitmap)) return 0;
        return bitmap.getHeight();
    }

    /**
     * 获取 Bitmap 宽高
     * @param bitmap 源图片
     * @return int[] { 宽度, 高度 }
     */
    public static int[] getBitmapWidthHeight(final Bitmap bitmap) {
        if (isEmpty(bitmap)) return new int[]{0, 0};
        return new int[]{bitmap.getWidth(), bitmap.getHeight()};
    }

    // =

    /**
     * 获取 Bitmap 宽高
     * @param file 文件
     * @return int[] { 宽度, 高度 }
     */
    public static int[] getBitmapWidthHeight(final File file) {
        return getBitmapWidthHeight(FileUtils.getAbsolutePath(file));
    }

    /**
     * 获取 Bitmap 宽高
     * @param filePath 文件路径
     * @return int[] { 宽度, 高度 }
     */
    public static int[] getBitmapWidthHeight(final String filePath) {
        if (!FileUtils.isFileExists(filePath)) return new int[]{0, 0};
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
        return new int[]{0, 0};
    }

    // =

    /**
     * 获取 Bitmap 宽高
     * @param resId resource identifier
     * @return int[] { 宽度, 高度 }
     */
    public static int[] getBitmapWidthHeight(@DrawableRes final int resId) {
        return getBitmapWidthHeight(DevUtils.getContext(), resId);
    }

    /**
     * 获取 Bitmap 宽高
     * @param context {@link Context}
     * @param resId   resource identifier
     * @return int[] { 宽度, 高度 }
     */
    public static int[] getBitmapWidthHeight(
            final Context context,
            @DrawableRes final int resId
    ) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 只解析图片信息, 不加载到内存中
            options.inJustDecodeBounds = true;
            // 返回的 bitmap 为 null
            BitmapFactory.decodeResource(ResourceUtils.getResources(context), resId, options);
            // options.outHeight 为原始图片的高
            return new int[]{options.outWidth, options.outHeight};
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmapWidthHeight");
        }
        return new int[]{0, 0};
    }

    // =

    /**
     * 获取 Bitmap 宽高
     * @param inputStream {@link InputStream}
     * @return int[] { 宽度, 高度 }
     */
    public static int[] getBitmapWidthHeight(final InputStream inputStream) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 只解析图片信息, 不加载到内存中
            options.inJustDecodeBounds = true;
            // 返回的 bitmap 为 null
            BitmapFactory.decodeStream(inputStream, null, options);
            // options.outHeight 为原始图片的高
            return new int[]{options.outWidth, options.outHeight};
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmapWidthHeight");
        }
        return new int[]{0, 0};
    }

    // =

    /**
     * 获取 Bitmap 宽高
     * @param fd 文件描述
     * @return int[] { 宽度, 高度 }
     */
    public static int[] getBitmapWidthHeight(final FileDescriptor fd) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 只解析图片信息, 不加载到内存中
            options.inJustDecodeBounds = true;
            // 返回的 bitmap 为 null
            BitmapFactory.decodeFileDescriptor(fd, null, options);
            // options.outHeight 为原始图片的高
            return new int[]{options.outWidth, options.outHeight};
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmapWidthHeight");
        }
        return new int[]{0, 0};
    }

    /**
     * 获取 Bitmap 宽高
     * @param data byte[]
     * @return int[] { 宽度, 高度 }
     */
    public static int[] getBitmapWidthHeight(final byte[] data) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 只解析图片信息, 不加载到内存中
            options.inJustDecodeBounds = true;
            // 返回的 bitmap 为 null
            BitmapFactory.decodeByteArray(data, 0, data.length, options);
            // options.outHeight 为原始图片的高
            return new int[]{options.outWidth, options.outHeight};
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getBitmapWidthHeight");
        }
        return new int[]{0, 0};
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
    public static Bitmap copy(
            final Bitmap bitmap,
            final boolean isMutable
    ) {
        if (isEmpty(bitmap)) return null;
        return bitmap.copy(bitmap.getConfig(), isMutable);
    }

    // =

    /**
     * 获取 Alpha 位图 ( 获取源图片的轮廓 rgb 为 0 )
     * @param bitmap {@link Bitmap}
     * @return Alpha 位图
     */
    public static Bitmap extractAlpha(final Bitmap bitmap) {
        if (isEmpty(bitmap)) return null;
        return bitmap.extractAlpha();
    }

    /**
     * 重新编码 Bitmap
     * @param bitmap  需要重新编码的 bitmap
     * @param format  编码后的格式 如 Bitmap.CompressFormat.PNG
     * @param quality 质量
     * @return 重新编码后的图片
     */
    public static Bitmap recode(
            final Bitmap bitmap,
            final Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return recode(bitmap, format, quality, null);
    }

    /**
     * 重新编码 Bitmap
     * @param bitmap  需要重新编码的 bitmap
     * @param format  编码后的格式 如 Bitmap.CompressFormat.PNG
     * @param quality 质量
     * @param options {@link BitmapFactory.Options}
     * @return 重新编码后的图片
     */
    public static Bitmap recode(
            final Bitmap bitmap,
            final Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) final int quality,
            final BitmapFactory.Options options
    ) {
        if (isEmpty(bitmap) || format == null) return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(format, quality, baos);
            byte[] data = baos.toByteArray();
            return BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "recode");
        }
        return null;
    }

    /**
     * Bitmap 通知回收
     * @param bitmap 待回收图片
     * @return {@code true} success, {@code false} fail
     */
    public static boolean recycle(final Bitmap bitmap) {
        if (bitmap == null) return false;
        if (!bitmap.isRecycled()) {
            try {
                bitmap.recycle();
                return true;
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "recycle");
            }
        }
        return false;
    }

    // ==============
    // = Bitmap 操作 =
    // ==============

    // =======
    // = 旋转 =
    // =======

    /**
     * 旋转图片
     * @param bitmap  待操作源图片
     * @param degrees 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotate(
            final Bitmap bitmap,
            final float degrees
    ) {
        if (isEmpty(bitmap)) return null;
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(
                bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(),
                matrix, false
        );
    }

    /**
     * 旋转图片
     * @param bitmap  待操作源图片
     * @param degrees 旋转角度
     * @param px      旋转中心点在 X 轴的坐标
     * @param py      旋转中心点在 Y 轴的坐标
     * @return 旋转后的图片
     */
    public static Bitmap rotate(
            final Bitmap bitmap,
            final float degrees,
            final float px,
            final float py
    ) {
        if (isEmpty(bitmap)) return null;
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees, px, py);
        return Bitmap.createBitmap(
                bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(),
                matrix, false
        );
    }

    /**
     * 读取图片属性, 获取图片旋转角度
     * @param filePath 文件路径
     * @return 图片旋转角度
     */
    public static int getRotationDegrees(final String filePath) {
        return ExifAssist.get(filePath).getRotationDegrees();
    }

    // =======
    // = 翻转 =
    // =======

    /**
     * 水平翻转图片 ( 左右颠倒 )
     * @param bitmap 待操作源图片
     * @return 翻转后的图片
     */
    public static Bitmap reverseByHorizontal(final Bitmap bitmap) {
        return reverse(bitmap, true);
    }

    /**
     * 垂直翻转图片 ( 上下颠倒 )
     * @param bitmap 待操作源图片
     * @return 翻转后的图片
     */
    public static Bitmap reverseByVertical(final Bitmap bitmap) {
        return reverse(bitmap, false);
    }

    /**
     * 翻转图片
     * @param bitmap     待操作源图片
     * @param horizontal 是否水平翻转
     * @return 翻转后的图片
     */
    public static Bitmap reverse(
            final Bitmap bitmap,
            final boolean horizontal
    ) {
        if (isEmpty(bitmap)) return null;
        Matrix matrix = new Matrix();
        if (horizontal) {
            matrix.preScale(-1, 1);
        } else {
            matrix.preScale(1, -1);
        }
        return Bitmap.createBitmap(
                bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(),
                matrix, false
        );
    }

    // =======
    // = 缩放 =
    // =======

    /**
     * 缩放图片 ( 指定所需宽高 )
     * @param bitmap  待操作源图片
     * @param newSize 新尺寸 ( 宽高 )
     * @return 缩放后的图片
     */
    public static Bitmap zoom(
            final Bitmap bitmap,
            final int newSize
    ) {
        return zoom(bitmap, newSize, newSize);
    }

    /**
     * 缩放图片 ( 指定所需宽高 )
     * @param bitmap    待操作源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @return 缩放后的图片
     */
    public static Bitmap zoom(
            final Bitmap bitmap,
            final int newWidth,
            final int newHeight
    ) {
        if (isEmpty(bitmap)) return null;
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
    }

    // =

    /**
     * 缩放图片 ( 比例缩放 )
     * @param bitmap 待操作源图片
     * @param scale  缩放倍数
     * @return 缩放后的图片
     */
    public static Bitmap scale(
            final Bitmap bitmap,
            final float scale
    ) {
        return scale(bitmap, scale, scale);
    }

    /**
     * 缩放图片 ( 比例缩放 )
     * @param bitmap 待操作源图片
     * @param scaleX 横向缩放比例 ( 缩放宽度倍数 )
     * @param scaleY 纵向缩放比例 ( 缩放高度倍数 )
     * @return 缩放后的图片
     */
    public static Bitmap scale(
            final Bitmap bitmap,
            final float scaleX,
            final float scaleY
    ) {
        if (isEmpty(bitmap)) return null;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        return Bitmap.createBitmap(
                bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(),
                matrix, true
        );
    }

    // =======
    // = 倾斜 =
    // =======

    /**
     * 倾斜图片
     * @param bitmap 待操作源图片
     * @param kx     X 轴倾斜因子
     * @param ky     Y 轴倾斜因子
     * @return 倾斜后的图片
     */
    public static Bitmap skew(
            final Bitmap bitmap,
            final float kx,
            final float ky
    ) {
        return skew(bitmap, kx, ky, 0, 0);
    }

    /**
     * 倾斜图片
     * <pre>
     *     倾斜因子 以小数点倾斜 如: 0.1 防止数值过大 Canvas: trying to draw too large
     * </pre>
     * @param bitmap 待操作源图片
     * @param kx     X 轴倾斜因子
     * @param ky     Y 轴倾斜因子
     * @param px     X 轴轴心点
     * @param py     Y 轴轴心点
     * @return 倾斜后的图片
     */
    public static Bitmap skew(
            final Bitmap bitmap,
            final float kx,
            final float ky,
            final float px,
            final float py
    ) {
        if (isEmpty(bitmap)) return null;
        Matrix matrix = new Matrix();
        matrix.setSkew(kx, ky, px, py);
        return Bitmap.createBitmap(
                bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(),
                matrix, true
        );
    }

    // =======
    // = 裁剪 =
    // =======

    /**
     * 裁剪图片
     * @param bitmap 待操作源图片
     * @param width  裁剪宽度
     * @param height 裁剪高度
     * @return 裁剪后的图片
     */
    public static Bitmap clip(
            final Bitmap bitmap,
            final int width,
            final int height
    ) {
        return clip(bitmap, 0, 0, width, height);
    }

    /**
     * 裁剪图片
     * @param bitmap 待操作源图片
     * @param x      X 轴开始坐标
     * @param y      Y 轴开始坐标
     * @param width  裁剪宽度
     * @param height 裁剪高度
     * @return 裁剪后的图片
     */
    public static Bitmap clip(
            final Bitmap bitmap,
            final int x,
            final int y,
            final int width,
            final int height
    ) {
        if (isEmpty(bitmap)) return null;
        return Bitmap.createBitmap(bitmap, x, y, width, height);
    }

    // =

    /**
     * 裁剪图片 ( 返回指定比例图片 )
     * @param bitmap 待操作源图片
     * @return 裁剪指定比例的图片
     */
    public static Bitmap crop(final Bitmap bitmap) {
        return crop(bitmap, 16.0F, 9.0F);
    }

    /**
     * 裁剪图片 ( 返回指定比例图片 )
     * @param bitmap      待操作源图片
     * @param widthScale  宽度比例
     * @param heightScale 高度比例
     * @return 裁剪指定比例的图片
     */
    public static Bitmap crop(
            final Bitmap bitmap,
            final float widthScale,
            final float heightScale
    ) {
        if (bitmap == null) return null;
        try {
            int width  = bitmap.getWidth();
            int height = bitmap.getHeight();

            // 获取需要裁剪的高度
            int reHeight = (int) ((width * heightScale) / widthScale);
            // 判断需要裁剪的高度与偏移差距
            int diffHeight = height - reHeight;

            // 属于宽度 * 对应比例 >= 高度
            if (diffHeight >= 0) { // 以高度做偏移
                return Bitmap.createBitmap(
                        bitmap, 0, diffHeight / 2,
                        width, reHeight, null, false
                );
            } else { // 以宽度做偏移
                // 获取需要裁剪的宽度
                int reWidth = (int) ((height * widthScale) / heightScale);
                // 判断需要裁剪的宽度与偏移差距
                int diffWidth = width - reWidth;
                // 创建图片
                return Bitmap.createBitmap(
                        bitmap, diffWidth / 2, 0,
                        reWidth, height, null, false
                );
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "crop");
        }
        return null;
    }

    // =============
    // = 合并 / 叠加 =
    // =============

    /**
     * 合并图片
     * @param bgd 后景 Bitmap
     * @param fg  前景 Bitmap
     * @return 合并后的图片
     */
    public static Bitmap combine(
            final Bitmap bgd,
            final Bitmap fg
    ) {
        return combine(bgd, fg, PorterDuff.Mode.SRC_ATOP, null, null);
    }

    /**
     * 合并图片
     * @param bgd  后景 Bitmap
     * @param fg   前景 Bitmap
     * @param mode 合并模式 {@link PorterDuff.Mode}
     * @return 合并后的图片
     */
    public static Bitmap combine(
            final Bitmap bgd,
            final Bitmap fg,
            final PorterDuff.Mode mode
    ) {
        return combine(bgd, fg, mode, null, null);
    }

    /**
     * 合并图片
     * @param bgd      后景 Bitmap
     * @param fg       前景 Bitmap
     * @param mode     合并模式 {@link PorterDuff.Mode}
     * @param bgdPoint 后景绘制 left、top 坐标
     * @param fgPoint  前景绘制 left、top 坐标
     * @return 合并后的图片
     */
    public static Bitmap combine(
            final Bitmap bgd,
            final Bitmap fg,
            final PorterDuff.Mode mode,
            final Point bgdPoint,
            final Point fgPoint
    ) {
        if (isEmpty(bgd) || isEmpty(fg)) return null;

        int width  = Math.max(bgd.getWidth(), fg.getWidth());
        int height = Math.max(bgd.getHeight(), fg.getHeight());

        Paint paint = new Paint();
        if (mode != null) {
            paint.setXfermode(new PorterDuffXfermode(mode));
        }

        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas    = new Canvas(newBitmap);
        canvas.drawBitmap(
                bgd, (bgdPoint != null) ? bgdPoint.x : 0,
                (bgdPoint != null) ? bgdPoint.y : 0, null
        );
        canvas.drawBitmap(
                fg, (fgPoint != null) ? fgPoint.x : 0,
                (fgPoint != null) ? fgPoint.y : 0, paint
        );
        return newBitmap;
    }

    // =

    /**
     * 合并图片 ( 居中 )
     * @param bgd 后景 Bitmap
     * @param fg  前景 Bitmap
     * @return 合并后的图片
     */
    public static Bitmap combineToCenter(
            final Bitmap bgd,
            final Bitmap fg
    ) {
        return combineToCenter(bgd, fg, null);
    }

    /**
     * 合并图片 ( 居中 )
     * @param bgd  后景 Bitmap
     * @param fg   前景 Bitmap
     * @param mode 合并模式 {@link PorterDuff.Mode}
     * @return 合并后的图片
     */
    public static Bitmap combineToCenter(
            Bitmap bgd,
            Bitmap fg,
            final PorterDuff.Mode mode
    ) {
        if (isEmpty(bgd) || isEmpty(fg)) return null;

        // 绘制坐标点
        Point bgdPoint = new Point();
        Point fgPoint  = new Point();

        // 宽高信息
        int bgdWidth  = bgd.getWidth();
        int bgdHeight = bgd.getHeight();

        int fgWidth  = fg.getWidth();
        int fgHeight = fg.getHeight();

        if (bgdWidth > fgWidth) {
            fgPoint.x = (bgdWidth - fgWidth) / 2;
        } else {
            bgdPoint.x = (fgWidth - bgdWidth) / 2;
        }

        if (bgdHeight > fgHeight) {
            fgPoint.y = (bgdHeight - fgHeight) / 2;
        } else {
            bgdPoint.y = (fgHeight - bgdHeight) / 2;
        }

        return combine(bgd, fg, mode, bgdPoint, fgPoint);
    }

    // =

    /**
     * 合并图片 ( 转为相同大小 )
     * @param bgd 后景 Bitmap
     * @param fg  前景 Bitmap
     * @return 合并后的图片
     */
    public static Bitmap combineToSameSize(
            Bitmap bgd,
            Bitmap fg
    ) {
        return combineToSameSize(bgd, fg, PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * 合并图片 ( 转为相同大小 )
     * @param bgd  后景 Bitmap
     * @param fg   前景 Bitmap
     * @param mode 合并模式 {@link PorterDuff.Mode}
     * @return 合并后的图片
     */
    public static Bitmap combineToSameSize(
            Bitmap bgd,
            Bitmap fg,
            final PorterDuff.Mode mode
    ) {
        if (isEmpty(bgd) || isEmpty(fg)) return null;

        int width  = Math.min(bgd.getWidth(), fg.getWidth());
        int height = Math.min(bgd.getHeight(), fg.getHeight());

        if (fg.getWidth() != width && fg.getHeight() != height) {
            fg = zoom(fg, width, height);
        }

        if (bgd.getWidth() != width && bgd.getHeight() != height) {
            bgd = zoom(bgd, width, height);
        }

        Paint paint = new Paint();
        if (mode != null) {
            paint.setXfermode(new PorterDuffXfermode(mode));
        }

        Bitmap newBitmap = Bitmap.createBitmap(
                width, height, Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(bgd, 0, 0, null);
        canvas.drawBitmap(fg, 0, 0, paint);
        return newBitmap;
    }

    // =======
    // = 倒影 =
    // =======

    /**
     * 图片倒影处理
     * @param bitmap 待操作源图片
     * @return 倒影处理后的图片
     */
    public static Bitmap reflection(final Bitmap bitmap) {
        return reflection(bitmap, 0, getBitmapHeight(bitmap));
    }

    /**
     * 图片倒影处理
     * @param bitmap           待操作源图片
     * @param reflectionHeight 倒影高度
     * @return 倒影处理后的图片
     */
    public static Bitmap reflection(
            final Bitmap bitmap,
            final int reflectionHeight
    ) {
        return reflection(bitmap, 0, reflectionHeight);
    }

    /**
     * 图片倒影处理
     * @param bitmap            待操作源图片
     * @param reflectionSpacing 源图片与倒影之间的间距
     * @param reflectionHeight  倒影高度
     * @return 倒影处理后的图片
     */
    public static Bitmap reflection(
            final Bitmap bitmap,
            final int reflectionSpacing,
            final int reflectionHeight
    ) {
        if (isEmpty(bitmap)) return null;
        if (reflectionHeight <= 0) return null;
        // 获取图片宽高
        int width  = bitmap.getWidth();
        int height = bitmap.getHeight();

        // 创建画布, 画布分为上中下三部分, 上: 是源图片, 中: 是源图片与倒影的间距, 下: 是倒影

        // 创建倒影图片
        Bitmap reflectionImage = reverseByVertical(bitmap); // 垂直翻转图片 ( 上下颠倒 )
        // 创建一张宽度与源图片相同, 但高度等于 源图片的高度 + 间距 + 倒影的高度的图片
        Bitmap bitmapWithReflection = Bitmap.createBitmap(
                width, height + reflectionSpacing + reflectionHeight,
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmapWithReflection);

        // 将源图片画到画布的上半部分, 将倒影画到画布的下半部分, 倒影与画布顶部的间距是源图片的高度加上源图片与倒影之间的间距
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionSpacing, null);
        BitmapUtils.recycle(reflectionImage);

        // 边距负数处理
        int spacing = Math.max(reflectionSpacing, 0);

        // 将倒影改成半透明, 创建画笔, 并设置画笔的渐变从半透明的白色到全透明的白色, 然后再倒影上面画半透明效果
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(
                new LinearGradient(
                        0, bitmap.getHeight(), 0,
                        bitmapWithReflection.getHeight() + spacing,
                        0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP
                )
        );
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(
                0, height + spacing, width,
                bitmapWithReflection.getHeight() + spacing, paint
        );
        return bitmapWithReflection;
    }

    // =======
    // = 圆角 =
    // =======

    /**
     * 图片圆角处理 ( 非圆形 )
     * <pre>
     *     以宽高中最小值设置为圆角尺寸, 如果宽高一致, 则处理为圆形图片
     * </pre>
     * @param bitmap 待操作源图片
     * @return 圆角处理后的图片
     */
    public static Bitmap roundCorner(final Bitmap bitmap) {
        if (isEmpty(bitmap)) return null;
        return roundCorner(bitmap, Math.min(bitmap.getWidth(), bitmap.getHeight()));
    }

    /**
     * 图片圆角处理 ( 非圆形 )
     * @param bitmap 待操作源图片
     * @param pixels 圆角大小
     * @return 圆角处理后的图片
     */
    public static Bitmap roundCorner(
            final Bitmap bitmap,
            final float pixels
    ) {
        if (isEmpty(bitmap)) return null;
        // 创建一个同源图片一样大小的矩形, 用于把源图片绘制到这个矩形上
        Rect  rect  = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect); // 创建一个精度更高的矩形, 用于画出圆角效果

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xff424242); // 设置画笔的颜色为不透明的灰色

        Bitmap newBitmap = Bitmap.createBitmap(
                bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, pixels, pixels, paint);
        // 绘制底圆后, 进行合并 ( 交集处理 )
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return newBitmap;
    }

    // =

    /**
     * 图片圆角处理 ( 非圆形, 只有 leftTop、rightTop )
     * @param bitmap 待操作源图片
     * @param pixels 圆角大小
     * @return 圆角处理后的图片
     */
    public static Bitmap roundCornerTop(
            final Bitmap bitmap,
            final float pixels
    ) {
        return roundCorner(bitmap, pixels, new boolean[]{true, true, true, false});
    }

    /**
     * 图片圆角处理 ( 非圆形, 只有 leftBottom、rightBottom )
     * @param bitmap 待操作源图片
     * @param pixels 圆角大小
     * @return 圆角处理后的图片
     */
    public static Bitmap roundCornerBottom(
            final Bitmap bitmap,
            final float pixels
    ) {
        return roundCorner(bitmap, pixels, new boolean[]{true, false, true, true});
    }

    // =

    /**
     * 图片圆角处理 ( 非圆形 )
     * <pre>
     *     只要左上圆角: new boolean[] {true, true, false, false};
     *     只要右上圆角: new boolean[] {false, true, true, false};
     *     <p></p>
     *     只要左下圆角: new boolean[] {true, false, false, true};
     *     只要右下圆角: new boolean[] {false, false, true, true};
     * </pre>
     * @param bitmap     待操作源图片
     * @param pixels     圆角大小
     * @param directions 需要圆角的方向 [left, top, right, bottom]
     * @return 圆角处理后的图片
     */
    public static Bitmap roundCorner(
            final Bitmap bitmap,
            final float pixels,
            final boolean[] directions
    ) {
        if (isEmpty(bitmap)) return null;
        if (directions == null || directions.length != 4) return null;
        // 创建一个同源图片一样大小的矩形, 用于把源图片绘制到这个矩形上
        Rect  rect  = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect); // 创建一个精度更高的矩形, 用于画出圆角效果

        // =============
        // = 圆角方向控制 =
        // =============

        if (!directions[0]) {
            rectF.left -= pixels;
        }

        if (!directions[1]) {
            rectF.top -= pixels;
        }

        if (!directions[2]) {
            rectF.right += pixels;
        }

        if (!directions[3]) {
            rectF.bottom += pixels;
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xff424242); // 设置画笔的颜色为不透明的灰色

        Bitmap newBitmap = Bitmap.createBitmap(
                bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, pixels, pixels, paint);
        // 绘制底圆后, 进行合并 ( 交集处理 )
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return newBitmap;
    }

    // =======
    // = 圆形 =
    // =======

    /**
     * 图片圆形处理
     * @param bitmap 待操作源图片
     * @return 圆形处理后的图片
     */
    public static Bitmap round(final Bitmap bitmap) {
        return round(bitmap, 0, 0);
    }

    /**
     * 图片圆形处理
     * @param bitmap      待操作源图片
     * @param borderSize  边框尺寸
     * @param borderColor 边框颜色
     * @return 圆形处理后的图片
     */
    public static Bitmap round(
            final Bitmap bitmap,
            @IntRange(from = 0) final int borderSize,
            @ColorInt final int borderColor
    ) {
        if (isEmpty(bitmap)) return null;

        int width  = bitmap.getWidth();
        int height = bitmap.getHeight();
        int size   = Math.min(width, height);

        Paint paint  = new Paint(Paint.ANTI_ALIAS_FLAG);
        float center = size / 2F;
        RectF rectF  = new RectF(0, 0, width, height);
        rectF.inset((width - size) / 2F, (height - size) / 2F);

        Matrix matrix = new Matrix();
        matrix.setTranslate(rectF.left, rectF.top);
        if (width != height) {
            matrix.preScale((float) size / width, (float) size / height);
        }
        BitmapShader shader = new BitmapShader(
                bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP
        );
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);

        Bitmap newBitmap = Bitmap.createBitmap(width, height, bitmap.getConfig());
        Canvas canvas    = new Canvas(newBitmap);
        canvas.drawRoundRect(rectF, center, center, paint);

        if (borderSize > 0) {
            paint.setShader(null);
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(borderSize);
            float radius = center - borderSize / 2F;
            canvas.drawCircle(width / 2F, height / 2F, radius, paint);
        }
        return newBitmap;
    }

    // ===============
    // = 圆角、圆形边框 =
    // ===============

    /**
     * 添加圆角边框
     * @param bitmap       源图片
     * @param borderSize   边框尺寸
     * @param color        边框颜色
     * @param cornerRadius 圆角半径
     * @return 圆角边框图
     */
    public static Bitmap addCornerBorder(
            final Bitmap bitmap,
            @IntRange(from = 1) final int borderSize,
            @ColorInt final int color,
            @FloatRange(from = 0) final float cornerRadius
    ) {
        return addBorder(bitmap, borderSize, color, false, cornerRadius);
    }

    /**
     * 添加圆形边框
     * @param bitmap     源图片
     * @param borderSize 边框尺寸
     * @param color      边框颜色
     * @return 圆形边框图
     */
    public static Bitmap addCircleBorder(
            final Bitmap bitmap,
            @IntRange(from = 1) final int borderSize,
            @ColorInt final int color
    ) {
        return addBorder(bitmap, borderSize, color, true, 0);
    }

    /**
     * 添加边框
     * @param bitmap       待操作源图片
     * @param borderSize   边框尺寸
     * @param color        边框颜色
     * @param isCircle     是否画圆
     * @param cornerRadius 圆角半径
     * @return 添加边框后的图片
     */
    public static Bitmap addBorder(
            final Bitmap bitmap,
            @IntRange(from = 1) final int borderSize,
            @ColorInt final int color,
            final boolean isCircle,
            final float cornerRadius
    ) {
        if (isEmpty(bitmap)) return null;

        Bitmap newBitmap = bitmap.copy(bitmap.getConfig(), true);
        int    width     = newBitmap.getWidth();
        int    height    = newBitmap.getHeight();

        Canvas canvas = new Canvas(newBitmap);
        Paint  paint  = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderSize);
        if (isCircle) {
            float radius = Math.min(width, height) / 2F - borderSize / 2F;
            canvas.drawCircle(width / 2F, height / 2F, radius, paint);
        } else {
            int halfBorderSize = borderSize >> 1;
            RectF rectF = new RectF(
                    halfBorderSize, halfBorderSize,
                    width - halfBorderSize,
                    height - halfBorderSize
            );
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
        }
        return newBitmap;
    }

    // =======
    // = 水印 =
    // =======

    /**
     * 添加文字水印
     * @param bitmap   待操作源图片
     * @param content  水印文本
     * @param textSize 水印字体大小 pixel
     * @param color    水印字体颜色
     * @param x        起始坐标 x
     * @param y        起始坐标 y
     * @return 添加文字水印后的图片
     */
    public static Bitmap addTextWatermark(
            final Bitmap bitmap,
            final String content,
            final float textSize,
            @ColorInt final int color,
            final float x,
            final float y
    ) {
        if (isEmpty(bitmap) || content == null) return null;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(textSize);
        Rect bounds = new Rect();
        paint.getTextBounds(content, 0, content.length(), bounds);

        Bitmap newBitmap = bitmap.copy(bitmap.getConfig(), true);
        Canvas canvas    = new Canvas(newBitmap);
        canvas.drawText(content, x, y + textSize, paint);
        return newBitmap;
    }

    /**
     * 添加图片水印
     * @param bitmap    待操作源图片
     * @param watermark 水印图片
     * @param x         起始坐标 x
     * @param y         起始坐标 y
     * @param alpha     透明度
     * @return 添加图片水印后的图片
     */
    public static Bitmap addImageWatermark(
            final Bitmap bitmap,
            final Bitmap watermark,
            final int x,
            final int y,
            @IntRange(from = 0, to = 255) final int alpha
    ) {
        if (isEmpty(bitmap)) return null;
        Bitmap newBitmap = bitmap.copy(bitmap.getConfig(), true);
        if (!isEmpty(watermark)) {
            Paint  paint  = new Paint(Paint.ANTI_ALIAS_FLAG);
            Canvas canvas = new Canvas(newBitmap);
            paint.setAlpha(alpha);
            canvas.drawBitmap(watermark, x, y, paint);
        }
        return newBitmap;
    }

    // =======
    // = 压缩 =
    // =======

    /**
     * 按缩放宽高压缩
     * <pre>
     *     可搭配 {@link ScaleUtils} 工具类使用
     * </pre>
     * @param bitmap    待操作源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @return 缩放宽高压缩后的图片
     */
    public static Bitmap compressByZoom(
            final Bitmap bitmap,
            final int newWidth,
            final int newHeight
    ) {
        return zoom(bitmap, newWidth, newHeight);
    }

    /**
     * 按缩放比例压缩
     * <pre>
     *     可搭配 {@link ScaleUtils} 工具类使用
     * </pre>
     * @param bitmap 待操作源图片
     * @param scaleX 横向缩放比例 ( 缩放宽度倍数 )
     * @param scaleY 纵向缩放比例 ( 缩放高度倍数 )
     * @return 缩放比例压缩后的图片
     */
    public static Bitmap compressByScale(
            final Bitmap bitmap,
            final float scaleX,
            final float scaleY
    ) {
        return scale(bitmap, scaleX, scaleY);
    }

    // =

    /**
     * 按质量压缩
     * @param bitmap  待操作源图片
     * @param quality 质量
     * @return 质量压缩过的图片
     */
    public static Bitmap compressByQuality(
            final Bitmap bitmap,
            @IntRange(from = 0, to = 100) final int quality
    ) {
        return compressByQuality(
                bitmap, Bitmap.CompressFormat.JPEG, quality, null
        );
    }

    /**
     * 按质量压缩
     * @param bitmap  待操作源图片
     * @param quality 质量
     * @param options {@link BitmapFactory.Options}
     * @return 质量压缩过的图片
     */
    public static Bitmap compressByQuality(
            final Bitmap bitmap,
            @IntRange(from = 0, to = 100) final int quality,
            final BitmapFactory.Options options
    ) {
        return compressByQuality(bitmap, Bitmap.CompressFormat.JPEG, quality, options);
    }

    /**
     * 按质量压缩
     * @param bitmap  待操作源图片
     * @param format  图片压缩格式
     * @param quality 质量
     * @param options {@link BitmapFactory.Options}
     * @return 质量压缩过的图片
     */
    public static Bitmap compressByQuality(
            final Bitmap bitmap,
            final Bitmap.CompressFormat format,
            @IntRange(from = 0, to = 100) final int quality,
            final BitmapFactory.Options options
    ) {
        if (isEmpty(bitmap) || format == null) return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(format, quality, baos);
            byte[] data = baos.toByteArray();
            return BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "compressByQuality");
        }
        return null;
    }

    // =

    /**
     * 按质量压缩 ( 图片大小 )
     * @param bitmap      待操作源图片
     * @param maxByteSize 允许最大值字节数
     * @return 质量压缩过的图片
     */
    public static Bitmap compressByByteSize(
            final Bitmap bitmap,
            final long maxByteSize
    ) {
        return compressByByteSize(
                bitmap, Bitmap.CompressFormat.JPEG, maxByteSize, null
        );
    }

    /**
     * 按质量压缩 ( 图片大小 )
     * @param bitmap      待操作源图片
     * @param format      图片压缩格式
     * @param maxByteSize 允许最大值字节数
     * @return 质量压缩过的图片
     */
    public static Bitmap compressByByteSize(
            final Bitmap bitmap,
            final Bitmap.CompressFormat format,
            final long maxByteSize
    ) {
        return compressByByteSize(bitmap, format, maxByteSize, null);
    }

    /**
     * 按质量压缩 ( 图片大小 )
     * @param bitmap      待操作源图片
     * @param format      图片压缩格式
     * @param maxByteSize 允许最大值字节数
     * @param options     {@link BitmapFactory.Options}
     * @return 质量压缩过的图片
     */
    public static Bitmap compressByByteSize(
            final Bitmap bitmap,
            final Bitmap.CompressFormat format,
            final long maxByteSize,
            final BitmapFactory.Options options
    ) {
        if (isEmpty(bitmap) || maxByteSize <= 0) return null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(format, 100, baos);
            byte[] data;
            if (baos.size() <= maxByteSize) { // 最好质量的不大于最大字节, 则返回最佳质量
                data = baos.toByteArray();
            } else {
                baos.reset();
                bitmap.compress(format, 0, baos);
                if (baos.size() >= maxByteSize) { // 最差质量不小于最大字节, 则返回最差质量
                    data = baos.toByteArray();
                } else { // 二分法寻找最佳质量
                    int start = 0;
                    int end   = 100;
                    int mid   = 0;
                    while (start < end) {
                        mid = (start + end) / 2;
                        baos.reset();
                        bitmap.compress(format, mid, baos);
                        int len = baos.size();
                        if (len == maxByteSize) {
                            break;
                        } else if (len > maxByteSize) {
                            end = mid - 1;
                        } else {
                            start = mid + 1;
                        }
                    }
                    if (end == mid - 1) {
                        baos.reset();
                        bitmap.compress(format, start, baos);
                    }
                    data = baos.toByteArray();
                }
            }
            return BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "compressByByteSize");
        }
        return null;
    }

    // =

    /**
     * 按采样大小压缩
     * @param bitmap     待操作源图片
     * @param sampleSize 采样率大小
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(
            final Bitmap bitmap,
            final int sampleSize
    ) {
        return compressBySampleSize(bitmap, Bitmap.CompressFormat.JPEG, sampleSize);
    }

    /**
     * 按采样大小压缩
     * @param bitmap     待操作源图片
     * @param format     图片压缩格式
     * @param sampleSize 采样率大小
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(
            final Bitmap bitmap,
            final Bitmap.CompressFormat format,
            final int sampleSize
    ) {
        if (isEmpty(bitmap) || format == null) return null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = sampleSize;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(format, 100, baos);
            byte[] data = baos.toByteArray();
            return BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "compressBySampleSize");
        }
        return null;
    }

    // =

    /**
     * 按采样大小压缩
     * @param bitmap    待操作源图片
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(
            final Bitmap bitmap,
            final int maxWidth,
            final int maxHeight
    ) {
        return compressBySampleSize(
                bitmap, Bitmap.CompressFormat.JPEG, maxWidth, maxHeight
        );
    }

    /**
     * 按采样大小压缩
     * @param bitmap    待操作源图片
     * @param format    图片压缩格式
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(
            final Bitmap bitmap,
            final Bitmap.CompressFormat format,
            final int maxWidth,
            final int maxHeight
    ) {
        if (isEmpty(bitmap)) return null;
        try {
            // 获取宽高信息
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(format, 100, baos);

            // 进行采样压缩
            byte[] data = baos.toByteArray();
            BitmapFactory.decodeByteArray(data, 0, data.length, options);
            options.inSampleSize       = calculateInSampleSize(options, maxWidth, maxHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeByteArray(data, 0, data.length, options);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "compressBySampleSize");
        }
        return null;
    }

    // =

    /**
     * 计算采样大小
     * <pre>
     *     最大宽高只是阀值, 实际算出来的图片将小于等于这个值
     * </pre>
     * @param options   {@link BitmapFactory.Options}
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 采样大小
     */
    public static int calculateInSampleSize(
            final BitmapFactory.Options options,
            final int maxWidth,
            final int maxHeight
    ) {
        if (options == null) return 0;

        int height       = options.outHeight;
        int width        = options.outWidth;
        int inSampleSize = 1;
        while (height > maxHeight || width > maxWidth) {
            height >>= 1;
            width >>= 1;
            inSampleSize <<= 1;
        }
        return inSampleSize;
    }

    /**
     * 计算最佳压缩质量值
     * <pre>
     *     搭配 {@link ImageUtils#saveBitmapToSDCard} 等需要传入 quality 方法使用
     * </pre>
     * @param bitmap      待操作源图片
     * @param format      图片压缩格式
     * @param maxByteSize 允许最大值字节数
     * @return 最佳压缩质量值
     */
    public static int calculateQuality(
            final Bitmap bitmap,
            final Bitmap.CompressFormat format,
            final long maxByteSize
    ) {
        if (isEmpty(bitmap) || maxByteSize <= 0) return -1;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(format, 100, baos);
            if (baos.size() <= maxByteSize) { // 最好质量的不大于最大字节, 则返回最佳质量
                return 100;
            } else {
                baos.reset();
                bitmap.compress(format, 0, baos);
                if (baos.size() >= maxByteSize) { // 最差质量不小于最大字节, 则返回最差质量
                    return 0;
                } else { // 二分法寻找最佳质量
                    int start = 0;
                    int end   = 100;
                    int mid   = 0;
                    while (start < end) {
                        mid = (start + end) / 2;
                        baos.reset();
                        bitmap.compress(format, mid, baos);
                        int len = baos.size();
                        if (len == maxByteSize) {
                            return mid;
                        } else if (len > maxByteSize) {
                            end = mid - 1;
                        } else {
                            start = mid + 1;
                        }
                    }
                    if (end == mid - 1) {
                        return start;
                    }
                    return mid;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "calculateQuality");
        }
        return -1;
    }

    // ============
    // = 视频缩略图 =
    // ============

    /**
     * 获取视频缩略图
     * @param path 视频路径
     * @return {@link Bitmap}
     */
    public static Bitmap getVideoThumbnail(final String path) {
        return getVideoThumbnail(path, -1);
    }

    /**
     * 获取视频缩略图
     * <pre>
     *     // 获取视频的长度 ( 单位为毫秒 )
     *     String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
     *     // 缩放缩略图
     *     ThumbnailUtils.extractThumbnail(bitmap,  width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
     *     // 获取视频缩略图
     *     ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MICRO_KIND);
     * </pre>
     * @param path   视频路径
     * @param millis 对应毫秒视频帧
     * @return {@link Bitmap}
     */
    public static Bitmap getVideoThumbnail(
            final String path,
            final long millis
    ) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            // 设置视频路径
            if (FileUtils.isFileExists(path)) {
                retriever.setDataSource(path);
            } else {
                retriever.setDataSource(path, new HashMap<>());
            }
            return retriever.getFrameAtTime(millis);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getVideoThumbnail");
        } finally {
            try {
                retriever.release();
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}