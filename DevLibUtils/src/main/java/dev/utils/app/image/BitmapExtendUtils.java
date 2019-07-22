package dev.utils.app.image;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.net.Uri;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Bitmap 工具类, 主要包括获取 Bitmap 和对 Bitmap 的操作
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
     *     说明 使用方法:
     *     首先你要将Options的inJustDecodeBounds属性设置为 true, BitmapFactory.decode一次图片
     *     然后将Options连同期望的宽度和高度一起传递到到本方法中
     *     之后再使用本方法的返回值做参数调用 BitmapFactory.decode 创建图片
     *     <p></p>
     *     说明 BitmapFactory创建bitmap会尝试为已经构建的bitmap分配内存
     *     这时就会很容易导致OOM出现, 为此每一种创建方法都提供了一个可选的Options参数
     *     将这个参数的inJustDecodeBounds属性设置为 true就可以让解析方法禁止为bitmap分配内存
     *     返回值也不再是一个Bitmap对象, 而是 null, 虽然 Bitmap 是 null 了, 但是Options的outWidth、outHeight和outMimeType属性都会被赋值
     * </pre>
     * @param options
     * @param targetWidth  目标宽度, 这里的宽高只是阀值, 实际显示的图片将小于等于这个值
     * @param targetHeight 目标高度, 这里的宽高只是阀值, 实际显示的图片将小于等于这个值
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
                // 选择宽和高中最小的比率作为inSampleSize的值, 这样可以保证最终图片的宽和高
                // 一定都会大于等于目标的宽和高
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
     * @return {@link Bitmap}
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
     * @param filePath
     * @param targetWidth  目标宽度
     * @param targetHeight 目标高度
     * @return {@link Bitmap}
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
     * @param data         Bitmap 的 byte[]
     * @param offset       image 从 byte[] 创建的起始位置
     * @param length       从offset处开始的长度
     * @param targetWidth  目标宽度
     * @param targetHeight 目标高度
     * @return {@link Bitmap}
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
     * 获取一个指定大小的bitmap
     * @param is           从输入流中读取Bitmap
     * @param outPadding
     * @param targetWidth  目标宽度
     * @param targetHeight 目标高度
     * @return {@link Bitmap}
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
     * @param bgd 背景 Bitmap
     * @param fg  前景 Bitmap
     * @return {@link Bitmap}
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
     * @return {@link Bitmap}
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
     * @return {@link Bitmap}
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
     * @return {@link Bitmap}
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
     * @return {@link Bitmap}
     */
    public static Bitmap compressImage(final Bitmap bitmap, final long size) {
        if (bitmap == null) return null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // 质量压缩方法, 这里100表示不压缩, 把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > size) { // 循环判断如果压缩后图片是否大于 100kb, 大于继续压缩
            baos.reset(); // 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos); // 这里压缩options%, 把压缩后的数据存放到baos中
            options -= 10; // 每次都减少10
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray()); // 把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(bais, null, null); // 把ByteArrayInputStream数据生成图片
    }

    /**
     * 转换图片成圆形
     * @param bitmap 传入Bitmap对象
     * @return {@link Bitmap}
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
     * @return {@link Bitmap}
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
     * @return {@link Bitmap}
     */
    public static Bitmap createWatermarkBitmap(final Bitmap src, final Bitmap watermark) {
        if (src == null || watermark == null) return null;
        int width = src.getWidth();
        int height = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        // create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(width, height, Config.ARGB_8888); // 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        // draw src into
        cv.drawBitmap(src, 0, 0, null); // 在 0, 0坐标开始画入src
        // draw watermark into
        cv.drawBitmap(watermark, width - ww + 5, height - wh + 5, null); // 在src的右下角画入水印
        // save all clip
        cv.save(); // 保存
        // store
        cv.restore(); // 存储
        return newb;
    }

    /**
     * 重新编码Bitmap
     * @param bitmap  需要重新编码的Bitmap
     * @param format  编码后的格式(目前只支持png和jpeg这两种格式)
     * @param quality 重新生成后的bitmap的质量
     * @return {@link Bitmap}
     */
    public static Bitmap codec(final Bitmap bitmap, final Bitmap.CompressFormat format, final int quality) {
        if (bitmap == null || format == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, quality, baos);
        byte[] array = baos.toByteArray();
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    /**
     * 图片压缩方法(使用compress的方法)
     * 说明 如果bitmap本身的大小小于maxSize, 则不作处理
     * @param bitmap  要压缩的图片
     * @param maxSize 压缩后的大小, 单位kb
     * @return {@link Bitmap}
     */
    public static Bitmap compress(final Bitmap bitmap, final double maxSize) {
        if (bitmap == null) return null;
        // 将bitmap放至数组中, 意在获取bitmap的大小(与实际读取的原文件要大)
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
            // (保持宽高不变, 缩放后也达到了最大占用空间的大小)
            return scale(bitmap, bitmap.getWidth() / Math.sqrt(i), bitmap.getHeight() / Math.sqrt(i));
        }
        return null;
    }

    /**
     * 图片的缩放方法
     * @param bitmap    源图片资源
     * @param newWidth  缩放后宽度
     * @param newHeight 缩放后高度
     * @return {@link Bitmap}
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
     * @return {@link Bitmap}
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
     * @return {@link Bitmap}
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
     * @return {@link Bitmap}
     */
    public static Bitmap scale(final Bitmap bitmap, final float scale) {
        if (bitmap == null) return null;
        return scale(bitmap, scale, scale);
    }

    /**
     * 水平翻转处理
     * @param bitmap 原图
     * @return {@link Bitmap}
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
     * 将YUV格式的图片的源数据从横屏模式转为竖屏模式, 注: 将源图片的宽高互换一下就是新图片的宽高
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
     * @param uri          压缩相册返回的照片
     * @param targetWidth
     * @param targetHeight
     * @return
     * @throws FileNotFoundException
     */
    public static Bitmap safeDecodeStream(final Uri uri, final int targetWidth, final int targetHeight) throws FileNotFoundException {
        if (uri == null) return null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        android.content.ContentResolver resolver = DevUtils.getContext().getContentResolver();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(new BufferedInputStream(resolver.openInputStream(uri), 16 * 1024), null, options);
        // 获取图片的宽度、高度
        float imgWidth = options.outWidth;
        float imgHeight = options.outHeight;
        // 分别计算图片宽度、高度与目标宽度、高度的比例, 取大于等于该比例的最小整数
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
        // 设置好缩放比例后, 加载图片进内容
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(new BufferedInputStream(resolver.openInputStream(uri), 16 * 1024), null, options);
    }


    // =================

//    /**
//     * 缩放处理
//     * @param scaling 缩放比例
//     * @return 缩放后的图片
//     */
//    public Bitmap scale(final Bitmap bitmap, final float scaling) {
//        Matrix matrix = new Matrix();
//        matrix.postScale(scaling, scaling);
//        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//    }
//
//    /**
//     * 缩放处理
//     * @param newWidth 新的宽度
//     * @return
//     */
//    public Bitmap scaleByWidth(final Bitmap bitmap, final int newWidth) {
//        return scale(bitmap,(float) newWidth / bitmap.getWidth());
//    }
//
//    /**
//     * 缩放处理
//     * @param newHeight 新的高度
//     * @return
//     */
//    public Bitmap scaleByHeight(final Bitmap bitmap, final int newHeight) {
//        return scale(bitmap,(float) newHeight / bitmap.getHeight());
//    }
//
//    /**
//     * 水平翻转处理
//     * @param bitmap 原图
//     * @return 水平翻转后的图片
//     */
//    public Bitmap reverseByHorizontal(final Bitmap bitmap) {
//        Matrix matrix = new Matrix();
//        matrix.preScale(-1, 1);
//        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
//    }
//
//    /**
//     * 垂直翻转处理
//     * @param bitmap 原图
//     * @return 垂直翻转后的图片
//     */
//    public Bitmap reverseByVertical(final Bitmap bitmap) {
//        Matrix matrix = new Matrix();
//        matrix.preScale(1, -1);
//        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
//    }
//
//    /**
//     * 将给定资源ID的Drawable转换成Bitmap
//     * @param context {@link Context}
//     * @param resId   Drawable资源文件的ID
//     * @return 新的Bitmap
//     */
//    public Bitmap drawableToBitmap(final Context context, final int resId) {
//        BitmapFactory.Options opt = new BitmapFactory.Options();
//        opt.inPreferredConfig = Bitmap.Config.RGB_565;
//        opt.inPurgeable = true;
//        opt.inInputShareable = true;
//        InputStream is = context.getResources().openRawResource(resId);
//        return BitmapFactory.decodeStream(is, null, opt);
//    }
//
//    /**
//     * 圆角处理
//     * @param pixels 角度, 度数越大圆角越大
//     * @return 转换成圆角后的图片
//     */
//    public Bitmap roundCorner(final Bitmap bitmap, final float pixels) {
//        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(output);
//        Paint paint = new Paint();
//        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()); // 创建一个同原图一样大小的矩形, 用于把原图绘制到这个矩形上
//        RectF rectF = new RectF(rect); // 创建一个精度更高的矩形, 用于画出圆角效果
//        paint.setAntiAlias(true);
//        canvas.drawARGB(0, 0, 0, 0); // 涂上黑色全透明的底色
//        paint.setColor(0xff424242); // 设置画笔的颜色为不透明的灰色
//        canvas.drawRoundRect(rectF, pixels, pixels, paint); // 用给给定的画笔把给定的矩形以给定的圆角的度数画到画布
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rect, paint); // 用画笔paint将原图bitmap根据新的矩形重新绘制
//        return output;
//    }
//
//    /**
//     * 倒影处理
//     * @param reflectionSpacing 原图与倒影之间的间距
//     * @param reflectionHeight  倒影高度
//     * @return 加上倒影后的图片
//     */
//    public Bitmap reflection(final Bitmap bitmap, final int reflectionSpacing, final int reflectionHeight) {
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//
//        // 获取倒影图片, 并创建一张宽度与原图相同, 但高度等于原图的高度加上间距加上倒影的高度的图片, 并创建画布, 画布分为上中下三部分, 上: 是原图, 中: 是原图与倒影的间距, 下: 是倒影
//        Bitmap reflectionImage = reverseByVertical(bitmap); //
//        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, height + reflectionSpacing + reflectionHeight, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmapWithReflection);
//
//        // 将原图画到画布的上半部分, 将倒影画到画布的下半部分, 倒影与画布顶部的间距是原图的高度加上原图与倒影之间的间距
//        canvas.drawBitmap(bitmap, 0, 0, null);
//        canvas.drawBitmap(reflectionImage, 0, height + reflectionSpacing, null);
//        reflectionImage.recycle();
//
//        // 将倒影改成半透明, 创建画笔, 并设置画笔的渐变从半透明的白色到全透明的白色, 然后再倒影上面画半透明效果
//        Paint paint = new Paint();
//        paint.setShader(new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionSpacing, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//        canvas.drawRect(0, height + reflectionSpacing, width, bitmapWithReflection.getHeight() + reflectionSpacing, paint);
//
//        return bitmapWithReflection;
//    }
//
//    /**
//     * 倒影处理
//     * @return 加上倒影后的图片
//     */
//    public Bitmap reflection(final Bitmap bitmap) {
//        return reflection(bitmap, 4, bitmap.getHeight() / 2);
//    }

    // =================



    // = 裁剪图片, 裁剪中间部分, 防止全图压缩 =

    /**
     * 裁剪图片(默认比例16:9)
     * @param filePath 保存路径
     * @param bitmap   图片
     */
    public static void cropBitmap(final String filePath, final Bitmap bitmap) {
        cropBitmap(filePath, bitmap, 16.0f, 9.0f);
    }

    /**
     * 裁剪图片
     * @param filePath    保存路径
     * @param bitmap      图片
     * @param widthScale  宽度比例
     * @param heightScale 高度比例
     */
    public static void cropBitmap(final String filePath, final Bitmap bitmap, final float widthScale, final float heightScale) {
        if (TextUtils.isEmpty(filePath)) return;
        File file = new File(filePath);
        if (file.isDirectory() || !file.exists()) return;

        if (bitmap == null) {
            return; // 防止图片为 null
        }
        // 裁剪处理后的图片
        Bitmap cBitmap = null;
        try {
            // 获取图片宽度
            int iWidth = bitmap.getWidth();
            // 获取图片高度
            int iHeight = bitmap.getHeight();
            // 获取需要裁剪的高度
            int rHeight = (int) ((iWidth * heightScale) / widthScale);
            // 判断需要裁剪的高度与偏移差距
            int dHeight = iHeight - rHeight;
            // =
            // 判断裁剪方式
            if (dHeight >= 0) { // 属于宽度 * 对应比例 >= 高度, 以高度做偏移
                // 计算偏移的y轴
                int offsetY = dHeight / 2;
                // 创建图片
                cBitmap = Bitmap.createBitmap(bitmap, 0, offsetY, iWidth, rHeight, null, false);
            } else { // 以宽度做偏移
                // 获取需要裁剪的宽度
                int rWidth = (int) ((iHeight * widthScale) / heightScale);
                // 判断需要裁剪的宽度与偏移差距
                int dWidth = iWidth - rWidth;
                // 计算偏移的X轴
                int offsetX = dWidth / 2;
                // 创建图片
                cBitmap = Bitmap.createBitmap(bitmap, offsetX, 0, rWidth, iHeight, null, false);
            }
            if (cBitmap != null) {
                // 保存图片
                BitmapUtils.saveBitmapToSDCardPNG(cBitmap, filePath, 85);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "cropBitmap");
        } finally {
            // = 清空资源 =
            try {
                if (cBitmap != null && !cBitmap.isRecycled()) {
                    cBitmap.recycle();
                }
            } catch (Exception e) {
            }
            cBitmap = null;
        }
    }
}