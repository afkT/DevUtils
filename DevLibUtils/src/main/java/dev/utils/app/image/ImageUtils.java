package dev.utils.app.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;

import dev.DevUtils;

import static dev.utils.app.image.temp.BitmapUtils.zoom;

/**
 * detail: 图片相关工具类
 * @author Ttt
 */
public final class ImageUtils {

    private ImageUtils() {
    }

    // 日志 TAG
    private static final String TAG = ImageUtils.class.getSimpleName();

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

    //=

    /**
     * 转为圆形图片
     * @param src 源图片
     * @return 圆形图片
     */
    public static Bitmap toRound(final Bitmap src) {
        return toRound(src, 0, 0, false);
    }

    /**
     * 转为圆形图片
     * @param src     源图片
     * @param recycle 是否回收
     * @return 圆形图片
     */
    public static Bitmap toRound(final Bitmap src, final boolean recycle) {
        return toRound(src, 0, 0, recycle);
    }

    /**
     * 转为圆形图片
     * @param src         源图片
     * @param borderSize  边框尺寸
     * @param borderColor 边框颜色
     * @return 圆形图片
     */
    public static Bitmap toRound(final Bitmap src, @IntRange(from = 0) final int borderSize, @ColorInt final int borderColor) {
        return toRound(src, borderSize, borderColor, false);
    }

    /**
     * 转为圆形图片
     * @param src         源图片
     * @param borderSize  边框尺寸
     * @param borderColor 边框颜色
     * @param recycle     是否回收
     * @return 圆形图片
     */
    public static Bitmap toRound(final Bitmap src, @IntRange(from = 0) final int borderSize, @ColorInt final int borderColor, final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        int width = src.getWidth();
        int height = src.getHeight();
        int size = Math.min(width, height);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap ret = Bitmap.createBitmap(width, height, src.getConfig());
        float center = size / 2f;
        RectF rectF = new RectF(0, 0, width, height);
        rectF.inset((width - size) / 2f, (height - size) / 2f);
        Matrix matrix = new Matrix();
        matrix.setTranslate(rectF.left, rectF.top);
        if (width != height) {
            matrix.preScale((float) size / width, (float) size / height);
        }
        BitmapShader shader = new BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
        Canvas canvas = new Canvas(ret);
        canvas.drawRoundRect(rectF, center, center, paint);
        if (borderSize > 0) {
            paint.setShader(null);
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(borderSize);
            float radius = center - borderSize / 2f;
            canvas.drawCircle(width / 2f, height / 2f, radius, paint);
        }
        if (recycle && !src.isRecycled() && ret != src) src.recycle();
        return ret;
    }

    /**
     * 转为圆角图片
     * @param src    源图片
     * @param radius 圆角的度数
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(final Bitmap src, final float radius) {
        return toRoundCorner(src, radius, 0, 0, false);
    }

    /**
     * 转为圆角图片
     * @param src     源图片
     * @param radius  圆角的度数
     * @param recycle 是否回收
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(final Bitmap src, final float radius, final boolean recycle) {
        return toRoundCorner(src, radius, 0, 0, recycle);
    }

    /**
     * 转为圆角图片
     * @param src         源图片
     * @param radius      圆角的度数
     * @param borderSize  边框尺寸
     * @param borderColor 边框颜色
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(final Bitmap src, final float radius, @IntRange(from = 0) final int borderSize, @ColorInt final int borderColor) {
        return toRoundCorner(src, radius, borderSize, borderColor, false);
    }

    /**
     * 转为圆角图片
     * @param src         源图片
     * @param radius      圆角的度数
     * @param borderSize  边框尺寸
     * @param borderColor 边框颜色
     * @param recycle     是否回收
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(final Bitmap src, final float radius, @IntRange(from = 0) final int borderSize, @ColorInt final int borderColor, final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        int width = src.getWidth();
        int height = src.getHeight();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap ret = Bitmap.createBitmap(width, height, src.getConfig());
        BitmapShader shader = new BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        Canvas canvas = new Canvas(ret);
        RectF rectF = new RectF(0, 0, width, height);
        float halfBorderSize = borderSize / 2f;
        rectF.inset(halfBorderSize, halfBorderSize);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        if (borderSize > 0) {
            paint.setShader(null);
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(borderSize);
            paint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawRoundRect(rectF, radius, radius, paint);
        }
        if (recycle && !src.isRecycled() && ret != src) src.recycle();
        return ret;
    }

    /**
     * 添加圆角边框
     * @param src          源图片
     * @param borderSize   边框尺寸
     * @param color        边框颜色
     * @param cornerRadius 圆角半径
     * @return 圆角边框图
     */
    public static Bitmap addCornerBorder(final Bitmap src, @IntRange(from = 1) final int borderSize, @ColorInt final int color, @FloatRange(from = 0) final float cornerRadius) {
        return addBorder(src, borderSize, color, false, cornerRadius, false);
    }

    /**
     * 添加圆角边框
     * @param src          源图片
     * @param borderSize   边框尺寸
     * @param color        边框颜色
     * @param cornerRadius 圆角半径
     * @param recycle      是否回收
     * @return 圆角边框图
     */
    public static Bitmap addCornerBorder(final Bitmap src, @IntRange(from = 1) final int borderSize, @ColorInt final int color, @FloatRange(from = 0) final float cornerRadius, final boolean recycle) {
        return addBorder(src, borderSize, color, false, cornerRadius, recycle);
    }

    /**
     * 添加圆形边框
     * @param src        源图片
     * @param borderSize 边框尺寸
     * @param color      边框颜色
     * @return 圆形边框图
     */
    public static Bitmap addCircleBorder(final Bitmap src, @IntRange(from = 1) final int borderSize, @ColorInt final int color) {
        return addBorder(src, borderSize, color, true, 0, false);
    }

    /**
     * 添加圆形边框
     * @param src        源图片
     * @param borderSize 边框尺寸
     * @param color      边框颜色
     * @param recycle    是否回收
     * @return 圆形边框图
     */
    public static Bitmap addCircleBorder(final Bitmap src, @IntRange(from = 1) final int borderSize, @ColorInt final int color, final boolean recycle) {
        return addBorder(src, borderSize, color, true, 0, recycle);
    }

    /**
     * 添加边框
     * @param src          源图片
     * @param borderSize   边框尺寸
     * @param color        边框颜色
     * @param isCircle     是否画圆
     * @param cornerRadius 圆角半径
     * @param recycle      是否回收
     * @return 边框图
     */
    private static Bitmap addBorder(final Bitmap src, @IntRange(from = 1) final int borderSize, @ColorInt final int color,
                                    final boolean isCircle, final float cornerRadius, final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Bitmap ret = recycle ? src : src.copy(src.getConfig(), true);
        int width = ret.getWidth();
        int height = ret.getHeight();
        Canvas canvas = new Canvas(ret);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderSize);
        if (isCircle) {
            float radius = Math.min(width, height) / 2f - borderSize / 2f;
            canvas.drawCircle(width / 2f, height / 2f, radius, paint);
        } else {
            int halfBorderSize = borderSize >> 1;
            RectF rectF = new RectF(halfBorderSize, halfBorderSize, width - halfBorderSize, height - halfBorderSize);
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
        }
        return ret;
    }

    /**
     * 添加文字水印
     * @param src      源图片
     * @param content  水印文本
     * @param textSize 水印字体大小
     * @param color    水印字体颜色
     * @param x        起始坐标 x
     * @param y        起始坐标 y
     * @return 带有文字水印的图片
     */
    public static Bitmap addTextWatermark(final Bitmap src, final String content, final int textSize,
                                          @ColorInt final int color, final float x, final float y) {
        return addTextWatermark(src, content, textSize, color, x, y, false);
    }

    /**
     * 添加文字水印
     * @param src      源图片
     * @param content  水印文本
     * @param textSize 水印字体大小
     * @param color    水印字体颜色
     * @param x        起始坐标 x
     * @param y        起始坐标 y
     * @param recycle  是否回收
     * @return 带有文字水印的图片
     */
    public static Bitmap addTextWatermark(final Bitmap src, final String content, final float textSize,
                                          @ColorInt final int color, final float x, final float y, final boolean recycle) {
        if (isEmptyBitmap(src) || content == null) return null;
        Bitmap ret = src.copy(src.getConfig(), true);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(ret);
        paint.setColor(color);
        paint.setTextSize(textSize);
        Rect bounds = new Rect();
        paint.getTextBounds(content, 0, content.length(), bounds);
        canvas.drawText(content, x, y + textSize, paint);
        if (recycle && !src.isRecycled() && ret != src) src.recycle();
        return ret;
    }

    /**
     * 添加图片水印
     * @param src       源图片
     * @param watermark 图片水印
     * @param x         起始坐标 x
     * @param y         起始坐标 y
     * @param alpha     透明度
     * @return 带有图片水印的图片
     */
    public static Bitmap addImageWatermark(final Bitmap src, final Bitmap watermark, final int x, final int y, final int alpha) {
        return addImageWatermark(src, watermark, x, y, alpha, false);
    }

    /**
     * 添加图片水印
     * @param src       源图片
     * @param watermark 图片水印
     * @param x         起始坐标 x
     * @param y         起始坐标 y
     * @param alpha     透明度
     * @param recycle   是否回收
     * @return 带有图片水印的图片
     */
    public static Bitmap addImageWatermark(final Bitmap src, final Bitmap watermark, final int x, final int y, final int alpha, final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        Bitmap ret = src.copy(src.getConfig(), true);
        if (!isEmptyBitmap(watermark)) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            Canvas canvas = new Canvas(ret);
            paint.setAlpha(alpha);
            canvas.drawBitmap(watermark, x, y, paint);
        }
        if (recycle && !src.isRecycled() && ret != src) src.recycle();
        return ret;
    }

    /**
     * 判断 bitmap 对象是否为空
     * @param src 源图片
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isEmptyBitmap(final Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

    // = 压缩有关 =

//    /**
//     * 按缩放压缩
//     * @param src       源图片
//     * @param newWidth  新宽度
//     * @param newHeight 新高度
//     * @return 缩放压缩后的图片
//     */
//    public static Bitmap compressByScale(final Bitmap src, final int newWidth, final int newHeight) {
//        return scale(src, newWidth, newHeight, false);
//    }
//
//    /**
//     * 按缩放压缩
//     * @param src       源图片
//     * @param newWidth  新宽度
//     * @param newHeight 新高度
//     * @param recycle   是否回收
//     * @return 缩放压缩后的图片
//     */
//    public static Bitmap compressByScale(final Bitmap src, final int newWidth, final int newHeight, final boolean recycle) {
//        return scale(src, newWidth, newHeight, recycle);
//    }
//
//    /**
//     * 按缩放压缩
//     * @param src         源图片
//     * @param scaleWidth  缩放宽度倍数
//     * @param scaleHeight 缩放高度倍数
//     * @return 缩放压缩后的图片
//     */
//    public static Bitmap compressByScale(final Bitmap src, final float scaleWidth, final float scaleHeight) {
//        return scale(src, scaleWidth, scaleHeight, false);
//    }
//
//    /**
//     * 按缩放压缩
//     * @param src         源图片
//     * @param scaleWidth  缩放宽度倍数
//     * @param scaleHeight 缩放高度倍数
//     * @param recycle     是否回收
//     * @return 缩放压缩后的图片
//     */
//    public static Bitmap compressByScale(final Bitmap src, final float scaleWidth, final float scaleHeight, final boolean recycle) {
//        return scale(src, scaleWidth, scaleHeight, recycle);
//    }

    /**
     * 按质量压缩
     * @param src     源图片
     * @param quality 质量
     * @return 质量压缩后的图片
     */
    public static Bitmap compressByQuality(final Bitmap src, @IntRange(from = 0, to = 100) final int quality) {
        return compressByQuality(src, quality, false);
    }

    /**
     * 按质量压缩
     * @param src     源图片
     * @param quality 质量
     * @param recycle 是否回收
     * @return 质量压缩后的图片
     */
    public static Bitmap compressByQuality(final Bitmap src, @IntRange(from = 0, to = 100) final int quality, final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled()) src.recycle();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 按质量压缩
     * @param src         源图片
     * @param maxByteSize 允许最大值字节数
     * @return 质量压缩压缩过的图片
     */
    public static Bitmap compressByQuality(final Bitmap src, final long maxByteSize) {
        return compressByQuality(src, maxByteSize, false);
    }

    /**
     * 按质量压缩
     * @param src         源图片
     * @param maxByteSize 允许最大值字节数
     * @param recycle     是否回收
     * @return 质量压缩压缩过的图片
     */
    public static Bitmap compressByQuality(final Bitmap src, final long maxByteSize, final boolean recycle) {
        if (isEmptyBitmap(src) || maxByteSize <= 0) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(CompressFormat.JPEG, 100, baos);
        byte[] bytes;
        if (baos.size() <= maxByteSize) { // 最好质量的不大于最大字节, 则返回最佳质量
            bytes = baos.toByteArray();
        } else {
            baos.reset();
            src.compress(CompressFormat.JPEG, 0, baos);
            if (baos.size() >= maxByteSize) { // 最差质量不小于最大字节, 则返回最差质量
                bytes = baos.toByteArray();
            } else {
                // 二分法寻找最佳质量
                int st = 0;
                int end = 100;
                int mid = 0;
                while (st < end) {
                    mid = (st + end) / 2;
                    baos.reset();
                    src.compress(CompressFormat.JPEG, mid, baos);
                    int len = baos.size();
                    if (len == maxByteSize) {
                        break;
                    } else if (len > maxByteSize) {
                        end = mid - 1;
                    } else {
                        st = mid + 1;
                    }
                }
                if (end == mid - 1) {
                    baos.reset();
                    src.compress(CompressFormat.JPEG, st, baos);
                }
                bytes = baos.toByteArray();
            }
        }
        if (recycle && !src.isRecycled()) src.recycle();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 按采样大小压缩
     * @param src        源图片
     * @param sampleSize 采样率大小
     * @return 按采样率压缩后的图片
     */

    public static Bitmap compressBySampleSize(final Bitmap src, final int sampleSize) {
        return compressBySampleSize(src, sampleSize, false);
    }

    /**
     * 按采样大小压缩
     * @param src        源图片
     * @param sampleSize 采样率大小
     * @param recycle    是否回收
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(final Bitmap src, final int sampleSize, final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled()) src.recycle();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 按采样大小压缩
     * @param src       源图片
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(final Bitmap src, final int maxWidth, final int maxHeight) {
        return compressBySampleSize(src, maxWidth, maxHeight, false);
    }

    /**
     * 按采样大小压缩
     * @param src       源图片
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @param recycle   是否回收
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(final Bitmap src, final int maxWidth, final int maxHeight, final boolean recycle) {
        if (isEmptyBitmap(src)) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inJustDecodeBounds = false;
        if (recycle && !src.isRecycled()) src.recycle();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

    /**
     * 计算采样大小
     * @param options   选项
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 采样大小
     */
    private static int calculateInSampleSize(final BitmapFactory.Options options, final int maxWidth, final int maxHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        while ((width >>= 1) >= maxWidth && (height >>= 1) >= maxHeight) {
            inSampleSize <<= 1;
        }
        return inSampleSize;
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

    // =

    /**
     * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
     * @param options
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static int caculateInSampleSize(final BitmapFactory.Options options, final int targetWidth, final int targetHeight) {
        if (options == null) return 0;

        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;
        if (width > targetWidth || height > targetHeight) {
            int widthRadio = Math.round(width * 1.0f / targetWidth);
            int heightRadio = Math.round(height * 1.0f / targetHeight);
            inSampleSize = Math.max(widthRadio, heightRadio);
        }
        return inSampleSize;
    }


//    /**
//     * 图片压缩处理(使用Options的方法)
//     * <pre>
//     *     说明 使用方法:
//     *     首先你要将Options的inJustDecodeBounds属性设置为 true, BitmapFactory.decode一次图片
//     *     然后将Options连同期望的宽度和高度一起传递到到本方法中
//     *     之后再使用本方法的返回值做参数调用 BitmapFactory.decode 创建图片
//     *     <p></p>
//     *     说明 BitmapFactory创建bitmap会尝试为已经构建的bitmap分配内存
//     *     这时就会很容易导致OOM出现, 为此每一种创建方法都提供了一个可选的Options参数
//     *     将这个参数的inJustDecodeBounds属性设置为 true就可以让解析方法禁止为bitmap分配内存
//     *     返回值也不再是一个Bitmap对象, 而是 null, 虽然 Bitmap 是 null 了, 但是Options的outWidth、outHeight和outMimeType属性都会被赋值
//     * </pre>
//     *
//     * @param options
//     * @param targetWidth  目标宽度, 这里的宽高只是阀值, 实际显示的图片将小于等于这个值
//     * @param targetHeight 目标高度, 这里的宽高只是阀值, 实际显示的图片将小于等于这个值
//     * @return
//     */
//    public static BitmapFactory.Options calculateInSampleSize(final BitmapFactory.Options options, final int targetWidth, final int targetHeight) {
//        if (options == null) return null;
//        // 源图片的高度和宽度
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//        if (height > 400 || width > 450) {
//            if (height > targetHeight || width > targetWidth) {
//                // 计算出实际宽高和目标宽高的比率
//                final int heightRatio = Math.round((float) height / (float) targetHeight);
//                final int widthRatio = Math.round((float) width / (float) targetWidth);
//                // 选择宽和高中最小的比率作为inSampleSize的值, 这样可以保证最终图片的宽和高
//                // 一定都会大于等于目标的宽和高
//                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
//            }
//        }
//        // 设置压缩比例
//        options.inSampleSize = inSampleSize;
//        options.inJustDecodeBounds = false;
//        return options;
//    }
//
//    /**
//     * 获取一个指定大小的bitmap
//     *
//     * @param res          Resources
//     * @param resId        图片ID
//     * @param targetWidth  目标宽度
//     * @param targetHeight 目标高度
//     * @return {@link Bitmap}
//     */
//    public static Bitmap getBitmapFromResource(final Resources res, final int resId, final int targetWidth, final int targetHeight) {
////        if (res == null) return null;
////        BitmapFactory.Options options = new BitmapFactory.Options();
////        options.inJustDecodeBounds = true;
////        BitmapFactory.decodeResource(res, resId, options);
////        options = BitmapHelper.calculateInSampleSize(options, targetWidth, targetHeight);
////        return BitmapFactory.decodeResource(res, resId, options);
//
//        if (res == null) return null;
//        // 通过JNI的形式读取本地图片达到节省内存的目的
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.RGB_565;
//        options.inPurgeable = true;
//        options.inInputShareable = true;
//        InputStream is = res.openRawResource(resId);
//        return getBitmapFromStream(is, null, targetWidth, targetHeight);
//    }
//
//    /**
//     * 获取一个指定大小的bitmap
//     *
//     * @param filePath
//     * @param targetWidth  目标宽度
//     * @param targetHeight 目标高度
//     * @return {@link Bitmap}
//     */
//    public static Bitmap getBitmapFromFile(final String filePath, final int targetWidth, final int targetHeight) {
//        if (TextUtils.isEmpty(filePath)) return null;
//        File file = new File(filePath);
//        if (file.isDirectory() || !file.exists()) return null;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(filePath, options);
//        options = calculateInSampleSize(options, targetWidth, targetHeight);
//        return BitmapFactory.decodeFile(filePath, options);
//    }
//
//    /**
//     * 获取一个指定大小的bitmap
//     *
//     * @param data         Bitmap 的 byte[]
//     * @param offset       image 从 byte[] 创建的起始位置
//     * @param length       从offset处开始的长度
//     * @param targetWidth  目标宽度
//     * @param targetHeight 目标高度
//     * @return {@link Bitmap}
//     */
//    public static Bitmap getBitmapFromByteArray(final byte[] data, final int offset, final int length, final int targetWidth, final int targetHeight) {
//        if (data == null || data.length == 0) return null;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeByteArray(data, offset, length, options);
//        options = calculateInSampleSize(options, targetWidth, targetHeight);
//        return BitmapFactory.decodeByteArray(data, offset, length, options);
//    }
//
//    // =
//
//    /**
//     * 获取一个指定大小的bitmap
//     *
//     * @param is           从输入流中读取Bitmap
//     * @param outPadding
//     * @param targetWidth  目标宽度
//     * @param targetHeight 目标高度
//     * @return {@link Bitmap}
//     */
//    public static Bitmap getBitmapFromStream(final InputStream is, final Rect outPadding, final int targetWidth, final int targetHeight) {
//        if (is == null) return null;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeStream(is, outPadding, options);
//        options = calculateInSampleSize(options, targetWidth, targetHeight);
//        return BitmapFactory.decodeStream(is, outPadding, options);
//    }
//
    /**
     * 合并Bitmap
     *
     * @param bgd 背景 Bitmap
     * @param fg  前景 Bitmap
     * @return {@link Bitmap}
     */
    public static Bitmap combineImages(final Bitmap bgd, final Bitmap fg) {
        if (bgd == null || fg == null) return null;

        int width = bgd.getWidth() > fg.getWidth() ? bgd.getWidth() : fg.getWidth();
        int height = bgd.getHeight() > fg.getHeight() ? bgd.getHeight() : fg.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bgd, 0, 0, null);
        canvas.drawBitmap(fg, 0, 0, paint);
        return bitmap;
    }

    /**
     * 合并Bitmap
     *
     * @param bgd 后景Bitmap
     * @param fg  前景Bitmap
     * @return {@link Bitmap}
     */
    public static Bitmap combineImagesToSameSize(Bitmap bgd, Bitmap fg) {
        if (bgd == null || fg == null) return null;

        int width = bgd.getWidth() < fg.getWidth() ? bgd.getWidth() : fg.getWidth();
        int height = bgd.getHeight() < fg.getHeight() ? bgd.getHeight() : fg.getHeight();

        if (fg.getWidth() != width && fg.getHeight() != height) {
            fg = zoom(fg, width, height);
        }
        if (bgd.getWidth() != width && bgd.getHeight() != height) {
            bgd = zoom(bgd, width, height);
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bgd, 0, 0, null);
        canvas.drawBitmap(fg, 0, 0, paint);

        return bitmap;
    }
//
//    /**
//     * 压缩图片大小
//     *
//     * @param bitmap 源Bitmap
//     * @param size   kb为单位, 大于xx大小, 则一直压缩
//     * @return {@link Bitmap}
//     */
//    public static Bitmap compressImage(final Bitmap bitmap, final long size) {
//        if (bitmap == null) return null;
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // 质量压缩方法, 这里100表示不压缩, 把压缩后的数据存放到baos中
//        int options = 100;
//        while (baos.toByteArray().length / 1024 > size) { // 循环判断如果压缩后图片是否大于 100kb, 大于继续压缩
//            baos.reset(); // 重置baos即清空baos
//            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos); // 这里压缩options%, 把压缩后的数据存放到baos中
//            options -= 10; // 每次都减少10
//        }
//        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray()); // 把压缩后的数据baos存放到ByteArrayInputStream中
//        return BitmapFactory.decodeStream(bais, null, null); // 把ByteArrayInputStream数据生成图片
//    }
//
//    /**
//     * 图片压缩方法(使用compress的方法)
//     * 说明 如果bitmap本身的大小小于maxSize, 则不作处理
//     *
//     * @param bitmap  要压缩的图片
//     * @param maxSize 压缩后的大小, 单位kb
//     * @return {@link Bitmap}
//     */
//    public static Bitmap compress(final Bitmap bitmap, final double maxSize) {
//        if (bitmap == null) return null;
//        // 将bitmap放至数组中, 意在获取bitmap的大小(与实际读取的原文件要大)
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        // 格式、质量、输出流
//        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
//        byte[] b = baos.toByteArray();
//        // 将字节换成KB
//        double mid = b.length / 1024;
//        // 获取bitmap大小 是允许最大大小的多少倍
//        double i = mid / maxSize;
//        // 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
//        if (i > 1) {
//            // 缩放图片 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
//            // (保持宽高不变, 缩放后也达到了最大占用空间的大小)
////            return scale(bitmap, bitmap.getWidth() / Math.sqrt(i), bitmap.getHeight() / Math.sqrt(i));
//        }
//        return null;
//    }
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