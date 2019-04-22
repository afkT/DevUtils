package dev.utils.app.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Bitmap工具类主要包括获取Bitmap和对Bitmap的操作
 * @author Ttt
 */
public final class BitmapExtendUtils {

    private BitmapExtendUtils() {
    }

    // 日志 TAG
    private static final String TAG = BitmapExtendUtils.class.getSimpleName();

    /**
     * 图片压缩处理(使用Options的方法)
     * <pre>
     *      说明 使用方法:
     *      首先你要将Options的inJustDecodeBounds属性设置为true，BitmapFactory.decode一次图片 。
     *      然后将Options连同期望的宽度和高度一起传递到到本方法中。
     *      之后再使用本方法的返回值做参数调用BitmapFactory.decode创建图片。
     *      <p></p>
     *      说明 BitmapFactory创建bitmap会尝试为已经构建的bitmap分配内存，
     *      这时就会很容易导致OOM出现。为此每一种创建方法都提供了一个可选的Options参数，
     *      将这个参数的inJustDecodeBounds属性设置为true就可以让解析方法禁止为bitmap分配内存，
     *      返回值也不再是一个Bitmap对象， 而是null。虽然Bitmap是null了，但是Options的outWidth、 outHeight和outMimeType属性都会被赋值。
     * </pre>
     * @param targetWidth  目标宽度,这里的宽高只是阀值，实际显示的图片将小于等于这个值
     * @param targetHeight 目标高度,这里的宽高只是阀值，实际显示的图片将小于等于这个值
     * @return
     */
    public static BitmapFactory.Options calculateInSampleSize(final BitmapFactory.Options options, final int targetWidth, final int targetHeight) {
        if (options == null) return null;
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > 400 || width > 450) {
            if (height > targetHeight || width > targetWidth) {
                // 计算出实际宽高和目标宽高的比率
                final int heightRatio = Math.round((float) height / (float) targetHeight);
                final int widthRatio = Math.round((float) width / (float) targetWidth);
                // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
                // 一定都会大于等于目标的宽和高。
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }
        }
        // 设置压缩比例
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return options;
    }

    /**
     * 获取一个指定大小的bitmap
     * @param res          Resources
     * @param resId        图片ID
     * @param targetWidth  目标宽度
     * @param targetHeight 目标高度
     */
    public static Bitmap getBitmapFromResource(final Resources res, final int resId, final int targetWidth, final int targetHeight) {
//        if (res == null) return null;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(res, resId, options);
//        options = BitmapHelper.calculateInSampleSize(options, targetWidth, targetHeight);
//        return BitmapFactory.decodeResource(res, resId, options);

        if (res == null) return null;
        // 通过JNI的形式读取本地图片达到节省内存的目的
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        InputStream is = res.openRawResource(resId);
        return getBitmapFromStream(is, null, targetWidth, targetHeight);
    }

    /**
     * 获取一个指定大小的bitmap
     * @param targetWidth  目标宽度
     * @param targetHeight 目标高度
     */
    public static Bitmap getBitmapFromFile(final String filePath, final int targetWidth, final int targetHeight) {
        if (TextUtils.isEmpty(filePath)) return null;
        File file = new File(filePath);
        if (file.isDirectory() || !file.exists()) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options = calculateInSampleSize(options, targetWidth, targetHeight);
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 获取一个指定大小的bitmap
     * @param data         Bitmap的byte数组
     * @param offset       image从byte数组创建的起始位置
     * @param length       从offset处开始的长度
     * @param targetWidth  目标宽度
     * @param targetHeight 目标高度
     */
    public static Bitmap getBitmapFromByteArray(final byte[] data, final int offset, final int length, final int targetWidth, final int targetHeight) {
        if (data == null || data.length == 0) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, offset, length, options);
        options = calculateInSampleSize(options, targetWidth, targetHeight);
        return BitmapFactory.decodeByteArray(data, offset, length, options);
    }

    // =

    /**
     * Stream 转换成 byte[]
     * @param is
     * @return
     */
    public static byte[] getBytesFromStream(final InputStream is) {
        if (is == null) return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        try {
            int len;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } catch (IOException e) {
            LogPrintUtils.eTag(TAG, e, "getBytesFromStream");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
        return os.toByteArray();
    }


    /**
     * 获取一个指定大小的bitmap
     * @param is           从输入流中读取Bitmap
     * @param outPadding
     * @param targetWidth  目标宽度
     * @param targetHeight 目标高度
     */
    public static Bitmap getBitmapFromStream(final InputStream is, final Rect outPadding, final int targetWidth, final int targetHeight) {
        if (is == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, outPadding, options);
        options = calculateInSampleSize(options, targetWidth, targetHeight);
        return BitmapFactory.decodeStream(is, outPadding, options);
    }

    // =

    /**
     * 合并Bitmap
     * @param bgd 背景Bitmap
     * @param fg  前景Bitmap
     * @return 合成后的Bitmap
     */
    public static Bitmap combineImages(final Bitmap bgd, final Bitmap fg) {
        if (bgd == null || fg == null) return null;

        int width = bgd.getWidth() > fg.getWidth() ? bgd.getWidth() : fg.getWidth();
        int height = bgd.getHeight() > fg.getHeight() ? bgd.getHeight() : fg.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));

        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bgd, 0, 0, null);
        canvas.drawBitmap(fg, 0, 0, paint);
        return bitmap;
    }

    /**
     * 合并Bitmap
     * @param bgd 后景Bitmap
     * @param fg  前景Bitmap
     * @return 合成后Bitmap
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

        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_ATOP));

        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bgd, 0, 0, null);
        canvas.drawBitmap(fg, 0, 0, paint);

        return bitmap;
    }

    /**
     * 放大缩小图片
     * @param bitmap 源Bitmap
     * @param w      宽
     * @param h      高
     * @return 目标Bitmap
     */
    public static Bitmap zoom(final Bitmap bitmap, final int w, final int h) {
        if (bitmap == null) return null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbmp;
    }

    /**
     * 获取圆角图片的方法
     * @param bitmap  源Bitmap
     * @param roundPx 圆角大小
     * @return 期望Bitmap
     */
    public static Bitmap getRoundedCornerBitmap(final Bitmap bitmap, final float roundPx) {
        if (bitmap == null) return null;

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 获取带倒影的图片方法
     * @param bitmap 源Bitmap
     * @return 带倒影的Bitmap
     */
    public static Bitmap createReflectionBitmap(final Bitmap bitmap) {
        if (bitmap == null) return null;

        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /**
     * 压缩图片大小
     * @param bitmap 源Bitmap
     * @param size   kb为单位, 大于xx大小, 则一直压缩
     * @return 压缩后的Bitmap
     */
    public static Bitmap compressImage(final Bitmap bitmap, final long size) {
        if (bitmap == null) return null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > size) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
    }

    /**
     * 将彩色图转换为灰度图
     * @param bitmap 源Bitmap
     * @return 返回转换好的位图
     */
    public static Bitmap convertGreyImg(final Bitmap bitmap) {
        if (bitmap == null) return null;
        int width = bitmap.getWidth(); // 获取位图的宽
        int height = bitmap.getHeight(); // 获取位图的高

        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return result;
    }

    /**
     * 转换图片成圆形
     * @param bitmap 传入Bitmap对象
     * @return 圆形Bitmap
     */
    public static Bitmap getRoundBitmap(final Bitmap bitmap) {
        if (bitmap == null) return null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 创建图片缩略图
     * @param bitmap
     * @return
     */
    public static Bitmap createThumbnailBitmap(final Bitmap bitmap) {
        if (bitmap == null) return null;
        int sIconWidth = -1;
        int sIconHeight = -1;
        sIconWidth = sIconHeight = (int) DevUtils.getContext().getResources().getDimension(android.R.dimen.app_icon_size);

        final Paint sPaint = new Paint();
        final Rect sBounds = new Rect();
        final Rect sOldBounds = new Rect();
        Canvas sCanvas = new Canvas();

        int width = sIconWidth;
        int height = sIconHeight;

        sCanvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG, Paint.FILTER_BITMAP_FLAG));

        final int bitmapWidth = bitmap.getWidth();
        final int bitmapHeight = bitmap.getHeight();

        if (width > 0 && height > 0) {
            if (width < bitmapWidth || height < bitmapHeight) {
                final float ratio = (float) bitmapWidth / bitmapHeight;

                if (bitmapWidth > bitmapHeight) {
                    height = (int) (width / ratio);
                } else if (bitmapHeight > bitmapWidth) {
                    width = (int) (height * ratio);
                }

                final Config c = (width == sIconWidth && height == sIconHeight) ? bitmap.getConfig() : Config.ARGB_8888;
                final Bitmap thumb = Bitmap.createBitmap(sIconWidth, sIconHeight, c);
                final Canvas canvas = sCanvas;
                final Paint paint = sPaint;
                canvas.setBitmap(thumb);
                paint.setDither(false);
                paint.setFilterBitmap(true);
                sBounds.set((sIconWidth - width) / 2, (sIconHeight - height) / 2, width, height);
                sOldBounds.set(0, 0, bitmapWidth, bitmapHeight);
                canvas.drawBitmap(bitmap, sOldBounds, sBounds, paint);
                return thumb;
            } else if (bitmapWidth < width || bitmapHeight < height) {
                final Config c = Config.ARGB_8888;
                final Bitmap thumb = Bitmap.createBitmap(sIconWidth, sIconHeight, c);
                final Canvas canvas = sCanvas;
                final Paint paint = sPaint;
                canvas.setBitmap(thumb);
                paint.setDither(false);
                paint.setFilterBitmap(true);
                canvas.drawBitmap(bitmap, (sIconWidth - bitmapWidth) / 2, (sIconHeight - bitmapHeight) / 2, paint);
                return thumb;
            }
        }
        return bitmap;
    }

    /**
     * 生成水印图片 水印在右下角
     * @param src
     * @param watermark
     * @return
     */
    public static Bitmap createWatermarkBitmap(final Bitmap src, final Bitmap watermark) {
        if (src == null || watermark == null) return null;
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        // create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        // draw src into
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        // draw watermark into
        cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);// 在src的右下角画入水印
        // save all clip
        cv.save();// 保存
        // store
        cv.restore();// 存储
        return newb;
    }

    /**
     * 重新编码Bitmap
     * @param bitmap  需要重新编码的Bitmap
     * @param format  编码后的格式(目前只支持png和jpeg这两种格式)
     * @param quality 重新生成后的bitmap的质量
     * @return 返回重新生成后的bitmap
     */
    public static Bitmap codec(final Bitmap bitmap, final Bitmap.CompressFormat format, final int quality) {
        if (bitmap == null || format == null) return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(format, quality, os);
        byte[] array = os.toByteArray();
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    /**
     * 图片压缩方法(使用compress的方法)
     * 说明 如果bitmap本身的大小小于maxSize，则不作处理
     * @param bitmap  要压缩的图片
     * @param maxSize 压缩后的大小，单位kb
     * @return
     */
    public static Bitmap compress(final Bitmap bitmap, final double maxSize) {
        if (bitmap == null) return null;
        // 将bitmap放至数组中，意在获取bitmap的大小(与实际读取的原文件要大)
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 格式、质量、输出流
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] b = baos.toByteArray();
        // 将字节换成KB
        double mid = b.length / 1024;
        // 获取bitmap大小 是允许最大大小的多少倍
        double i = mid / maxSize;
        // 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
        if (i > 1) {
            // 缩放图片 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
            // (保持宽高不变，缩放后也达到了最大占用空间的大小)
            return scale(bitmap, bitmap.getWidth() / Math.sqrt(i), bitmap.getHeight() / Math.sqrt(i));
        }
        return null;
    }

    /**
     * 图片的缩放方法
     * @param bitmap    源图片资源
     * @param newWidth  缩放后宽度
     * @param newHeight 缩放后高度
     */
    public static Bitmap scale(final Bitmap bitmap, final double newWidth, final double newHeight) {
        if (bitmap == null) return null;
        // 记录src的宽高
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        // 创建一个matrix容器
        Matrix matrix = new Matrix();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 开始缩放
        matrix.postScale(scaleWidth, scaleHeight);
        // 创建缩放后的图片
        return Bitmap.createBitmap(bitmap, 0, 0, (int) width, (int) height, matrix, true);
    }

    /**
     * 图片的缩放方法
     * @param bitmap      源图片资源
     * @param scaleMatrix 缩放规则
     */
    public static Bitmap scale(final Bitmap bitmap, final Matrix scaleMatrix) {
        if (bitmap == null) return null;
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), scaleMatrix, true);
    }

    /**
     * 图片的缩放方法
     * @param bitmap 源图片资源
     * @param scaleX 横向缩放比例
     * @param scaleY 纵向缩放比例
     */
    public static Bitmap scale(final Bitmap bitmap, final float scaleX, final float scaleY) {
        if (bitmap == null) return null;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 图片的缩放方法
     * @param bitmap 源图片资源
     * @param scale  缩放比例
     */
    public static Bitmap scale(final Bitmap bitmap, final float scale) {
        if (bitmap == null) return null;
        return scale(bitmap, scale, scale);
    }

    /**
     * 旋转图片
     * @param angle  旋转角度
     * @param bitmap 要旋转的图片
     * @return 旋转后的图片
     */
    public static Bitmap rotate(final Bitmap bitmap, final int angle) {
        if (bitmap == null) return null;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 水平翻转处理
     * @param bitmap 原图
     * @return 水平翻转后的图片
     */
    public static Bitmap reverseByHorizontal(final Bitmap bitmap) {
        if (bitmap == null) return null;
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    /**
     * 垂直翻转处理
     * @param bitmap 原图
     * @return 垂直翻转后的图片
     */
    public static Bitmap reverseByVertical(final Bitmap bitmap) {
        if (bitmap == null) return null;
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    /**
     * 更改图片色系，变亮或变暗
     * @param delta 图片的亮暗程度值，越小图片会越亮，取值范围(0,24)
     * @return
     */
    public static Bitmap adjustTone(final Bitmap src, final int delta) {
        if (src == null) return null;
        if (delta >= 24 || delta <= 0) {
            return null;
        }
        // 设置高斯矩阵
        int[] gauss = new int[]{1, 2, 1, 2, 4, 2, 1, 2, 1};
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int pixColor = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int idx = 0;
        int[] pixels = new int[width * height];

        src.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + m) * width + k + n];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR += (pixR * gauss[idx]);
                        newG += (pixG * gauss[idx]);
                        newB += (pixB * gauss[idx]);
                        idx++;
                    }
                }
                newR /= delta;
                newG /= delta;
                newB /= delta;
                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));
                pixels[i * width + k] = Color.argb(255, newR, newG, newB);
                newR = 0;
                newG = 0;
                newB = 0;
            }
        }
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 将彩色图转换为黑白图
     * @param bitmap 位图
     * @return 返回转换好的位图
     */
    public static Bitmap convertToBlackWhite(final Bitmap bitmap) {
        if (bitmap == null) return null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int alpha = 0xFF << 24; // 默认将bitmap当成24色图片
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap newBmp = Bitmap.createBitmap(width, height, Config.RGB_565);
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBmp;
    }

    /**
     * 读取图片属性 图片被旋转的角度
     * @param filePath 图片绝对路径
     * @return 旋转的角度
     */
    public static int getImageDegree(final String filePath) {
        if (TextUtils.isEmpty(filePath)) return 0;
        File file = new File(filePath);
        if (file.isDirectory() || !file.exists()) return 0;

        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            LogPrintUtils.eTag(TAG, e, "getImageDegree");
        }
        return degree;
    }

    /**
     * 饱和度处理
     * @param bitmap          原图
     * @param saturationValue 新的饱和度值
     * @return 改变了饱和度值之后的图片
     */
    public static Bitmap saturation(final Bitmap bitmap, final int saturationValue) {
        if (bitmap == null) return null;
        // 计算出符合要求的饱和度值
        float newSaturationValue = saturationValue * 1.0F / 127;
        // 创建一个颜色矩阵
        ColorMatrix saturationColorMatrix = new ColorMatrix();
        // 设置饱和度值
        saturationColorMatrix.setSaturation(newSaturationValue);
        // 创建一个画笔并设置其颜色过滤器
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(saturationColorMatrix));
        // 创建一个新的图片并创建画布
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        // 将原图使用给定的画笔画到画布上
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newBitmap;
    }

    /**
     * 亮度处理
     * @param bitmap   原图
     * @param lumValue 新的亮度值
     * @return 改变了亮度值之后的图片
     */
    public static Bitmap lum(final Bitmap bitmap, final int lumValue) {
        if (bitmap == null) return null;
        // 计算出符合要求的亮度值
        float newlumValue = lumValue * 1.0F / 127;
        // 创建一个颜色矩阵
        ColorMatrix lumColorMatrix = new ColorMatrix();
        // 设置亮度值
        lumColorMatrix.setScale(newlumValue, newlumValue, newlumValue, 1);
        // 创建一个画笔并设置其颜色过滤器
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(lumColorMatrix));
        // 创建一个新的图片并创建画布
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        // 将原图使用给定的画笔画到画布上
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newBitmap;
    }

    /**
     * 色相处理
     * @param bitmap   原图
     * @param hueValue 新的色相值
     * @return 改变了色相值之后的图片
     */
    public static Bitmap hue(final Bitmap bitmap, final int hueValue) {
        if (bitmap == null) return null;
        // 计算出符合要求的色相值
        float newHueValue = (hueValue - 127) * 1.0F / 127 * 180;
        // 创建一个颜色矩阵
        ColorMatrix hueColorMatrix = new ColorMatrix();
        // 控制让红色区在色轮上旋转的角度
        hueColorMatrix.setRotate(0, newHueValue);
        // 控制让绿红色区在色轮上旋转的角度
        hueColorMatrix.setRotate(1, newHueValue);
        // 控制让蓝色区在色轮上旋转的角度
        hueColorMatrix.setRotate(2, newHueValue);
        // 创建一个画笔并设置其颜色过滤器
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(hueColorMatrix));
        // 创建一个新的图片并创建画布
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        // 将原图使用给定的画笔画到画布上
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newBitmap;
    }

    /**
     * 亮度、色相、饱和度处理
     * @param bitmap          原图
     * @param lumValue        亮度值
     * @param hueValue        色相值
     * @param saturationValue 饱和度值
     * @return 亮度、色相、饱和度处理后的图片
     */
    public static Bitmap lumAndHueAndSaturation(final Bitmap bitmap, final int lumValue, final int hueValue, final int saturationValue) {
        if (bitmap == null) return null;
        // 计算出符合要求的饱和度值
        float newSaturationValue = saturationValue * 1.0F / 127;
        // 计算出符合要求的亮度值
        float newlumValue = lumValue * 1.0F / 127;
        // 计算出符合要求的色相值
        float newHueValue = (hueValue - 127) * 1.0F / 127 * 180;
        // 创建一个颜色矩阵并设置其饱和度
        ColorMatrix colorMatrix = new ColorMatrix();
        // 设置饱和度值
        colorMatrix.setSaturation(newSaturationValue);
        // 设置亮度值
        colorMatrix.setScale(newlumValue, newlumValue, newlumValue, 1);
        // 控制让红色区在色轮上旋转的角度
        colorMatrix.setRotate(0, newHueValue);
        // 控制让绿红色区在色轮上旋转的角度
        colorMatrix.setRotate(1, newHueValue);
        // 控制让蓝色区在色轮上旋转的角度
        colorMatrix.setRotate(2, newHueValue);
        // 创建一个画笔并设置其颜色过滤器
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        // 创建一个新的图片并创建画布
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        // 将原图使用给定的画笔画到画布上
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newBitmap;
    }

    /**
     * 怀旧效果处理
     * @param bitmap 原图
     * @return 怀旧效果处理后的图片
     */
    public static Bitmap nostalgic(final Bitmap bitmap) {
        if (bitmap == null) return null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);
        int pixColor = 0;
        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int newR = 0;
        int newG = 0;
        int newB = 0;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < height; i++) {
            for (int k = 0; k < width; k++) {
                pixColor = pixels[width * i + k];
                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);
                newR = (int) (0.393 * pixR + 0.769 * pixG + 0.189 * pixB);
                newG = (int) (0.349 * pixR + 0.686 * pixG + 0.168 * pixB);
                newB = (int) (0.272 * pixR + 0.534 * pixG + 0.131 * pixB);
                int newColor = Color.argb(255, newR > 255 ? 255 : newR,
                        newG > 255 ? 255 : newG, newB > 255 ? 255 : newB);
                pixels[width * i + k] = newColor;
            }
        }
        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 柔化效果处理
     * @param bitmap 原图
     * @return 柔化效果处理后的图片
     */
    public static Bitmap soften(final Bitmap bitmap) {
        if (bitmap == null) return null;
        // 高斯矩阵
        int[] gauss = new int[]{1, 2, 1, 2, 4, 2, 1, 2, 1};

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int delta = 16; // 值越小图片会越亮，越大则越暗

        int idx = 0;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + m) * width + k + n];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR = newR + (int) (pixR * gauss[idx]);
                        newG = newG + (int) (pixG * gauss[idx]);
                        newB = newB + (int) (pixB * gauss[idx]);
                        idx++;
                    }
                }
                newR /= delta;
                newG /= delta;
                newB /= delta;

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[i * width + k] = Color.argb(255, newR, newG, newB);

                newR = 0;
                newG = 0;
                newB = 0;
            }
        }
        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 光照效果处理
     * @param bitmap  原图
     * @param centerX 光源在X轴的位置
     * @param centerY 光源在Y轴的位置
     * @return 光照效果处理后的图片
     */
    public static Bitmap sunshine(final Bitmap bitmap, final int centerX, final int centerY) {
        if (bitmap == null) return null;
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;
        int radius = Math.min(centerX, centerY);

        final float strength = 150F; // 光照强度 100~150
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int pos = 0;
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                pos = i * width + k;
                pixColor = pixels[pos];

                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);

                newR = pixR;
                newG = pixG;
                newB = pixB;

                // 计算当前点到光照中心的距离，平面座标系中求两点之间的距离
                int distance = (int) (Math.pow((centerY - i), 2) + Math.pow(centerX - k, 2));
                if (distance < radius * radius) {
                    // 按照距离大小计算增加的光照值
                    int result = (int) (strength * (1.0 - Math.sqrt(distance) / radius));
                    newR = pixR + result;
                    newG = pixG + result;
                    newB = pixB + result;
                }

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[pos] = Color.argb(255, newR, newG, newB);
            }
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 底片效果处理
     * @param bitmap 原图
     * @return 底片效果处理后的图片
     */
    public static Bitmap film(final Bitmap bitmap) {
        if (bitmap == null) return null;
        // RGBA的最大值
        final int MAX_VALUE = 255;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int pos = 0;
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                pos = i * width + k;
                pixColor = pixels[pos];

                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);

                newR = MAX_VALUE - pixR;
                newG = MAX_VALUE - pixG;
                newB = MAX_VALUE - pixB;

                newR = Math.min(MAX_VALUE, Math.max(0, newR));
                newG = Math.min(MAX_VALUE, Math.max(0, newG));
                newB = Math.min(MAX_VALUE, Math.max(0, newB));

                pixels[pos] = Color.argb(MAX_VALUE, newR, newG, newB);
            }
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 锐化效果处理
     * @param bitmap 原图
     * @return 锐化效果处理后的图片
     */
    public static Bitmap sharpen(final Bitmap bitmap) {
        if (bitmap == null) return null;
        // 拉普拉斯矩阵
        int[] laplacian = new int[]{-1, -1, -1, -1, 9, -1, -1, -1, -1};

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int idx = 0;
        float alpha = 0.3F;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + n) * width + k + m];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR = newR + (int) (pixR * laplacian[idx] * alpha);
                        newG = newG + (int) (pixG * laplacian[idx] * alpha);
                        newB = newB + (int) (pixB * laplacian[idx] * alpha);
                        idx++;
                    }
                }

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[i * width + k] = Color.argb(255, newR, newG, newB);
                newR = 0;
                newG = 0;
                newB = 0;
            }
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 浮雕效果处理
     * @param bitmap 原图
     * @return 浮雕效果处理后的图片
     */
    public static Bitmap emboss(final Bitmap bitmap) {
        if (bitmap == null) return null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int pos = 0;
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                pos = i * width + k;
                pixColor = pixels[pos];

                pixR = Color.red(pixColor);
                pixG = Color.green(pixColor);
                pixB = Color.blue(pixColor);

                pixColor = pixels[pos + 1];
                newR = Color.red(pixColor) - pixR + 127;
                newG = Color.green(pixColor) - pixG + 127;
                newB = Color.blue(pixColor) - pixB + 127;

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[pos] = Color.argb(255, newR, newG, newB);
            }
        }

        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    /**
     * 将YUV格式的图片的源数据从横屏模式转为竖屏模式，注: 将源图片的宽高互换一下就是新图片的宽高
     * @param sourceData YUV格式的图片的源数据
     * @param width      源图片的宽
     * @param height     源图片的高
     * @return
     */
    public static byte[] yuvLandscapeToPortrait(final byte[] sourceData, final int width, final int height) {
        if (sourceData == null || sourceData.length == 0) return null;
        byte[] rotatedData = new byte[sourceData.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
                rotatedData[x * height + height - y - 1] = sourceData[x + y * width];
        }
        return rotatedData;
    }

    /**
     * 比较安全的 解码(decodeStream) 方法
     * rather than the one of {@link BitmapFactory}
     * which will be easy to get OutOfMemory Exception
     * while loading a big image file.
     * @param uri 压缩相册返回的照片
     * @return
     * @throws FileNotFoundException
     */
    public static Bitmap safeDecodeStream(final Uri uri, final int targetWidth, final int targetHeight) throws FileNotFoundException {
        if (uri == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        android.content.ContentResolver resolver = DevUtils.getContext().getContentResolver();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(new BufferedInputStream(resolver.openInputStream(uri), 16 * 1024), null, options);
        // 获取图片的宽度、高度；
        float imgWidth = options.outWidth;
        float imgHeight = options.outHeight;
        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
        int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
        int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
        options.inSampleSize = 1;
        if (widthRatio > 1 || widthRatio > 1) {
            if (widthRatio > heightRatio) {
                options.inSampleSize = widthRatio;
            } else {
                options.inSampleSize = heightRatio;
            }
        }
        // 设置好缩放比例后，加载图片进内容；
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(new BufferedInputStream(resolver.openInputStream(uri), 16 * 1024), null, options);
    }
}