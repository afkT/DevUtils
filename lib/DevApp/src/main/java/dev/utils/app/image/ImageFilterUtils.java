package dev.utils.app.image;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import androidx.annotation.IntRange;
import androidx.annotation.RequiresApi;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: 图片 ( 滤镜、效果 ) 工具类
 * @author Ttt
 */
public final class ImageFilterUtils {

    private ImageFilterUtils() {
    }

    // 日志 TAG
    private static final String TAG = ImageFilterUtils.class.getSimpleName();

    // =======
    // = 效果 =
    // =======

    // =======
    // = 模糊 =
    // =======

    /**
     * 图片模糊处理 ( Android RenderScript 实现, 效率最高 )
     * @param bitmap 待模糊图片
     * @param radius 模糊度 (0-25)
     * @return 模糊后的图片
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blur(
            final Bitmap bitmap,
            @IntRange(from = 0, to = 25) final int radius
    ) {
        if (bitmap == null) return null;
        try {
            // 创建 RenderScript 内核对象
            RenderScript rs = RenderScript.create(DevUtils.getContext());
            // 由于 RenderScript 并没有使用 VM 来分配内存, 所以需要使用 Allocation 类来创建和分配内存空间
            // 创建 Allocation 对象的时候其实内存是空的, 需要使用 copyTo() 将数据填充进去
            Allocation input  = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
            Allocation output = Allocation.createTyped(rs, input.getType());
            // 创建一个模糊效果的 RenderScript 的工具对象, 第二个参数 Element 相当于一种像素处理的算法, 高斯模糊的话用这个就好
            ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

            // 设置 blur 对象的输入内存
            blur.setInput(input);
            // 设置渲染的模糊程度, 25f 是最大模糊度
            blur.setRadius(radius);
            // 将输出数据保存到输出内存中
            blur.forEach(output);
            // 将数据填充到 bitmap 中
            output.copyTo(bitmap);

            // 销毁它们释放内存
            input.destroy();
            output.destroy();
            blur.destroy();
            rs.destroy();
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "blur");
        }
        return null;
    }

    /**
     * 图片模糊处理 ( 毛玻璃化 FastBlur Java 实现 )
     * <pre>
     *     模糊程度越低, 处理速度越快
     * </pre>
     * @param bitmap 待模糊图片
     * @param radius 模糊度
     * @return 模糊后的图片
     */
    public static Bitmap fastBlur(
            final Bitmap bitmap,
            final int radius
    ) {
        if (bitmap == null) return null;
        // 如果 Bitmap 不允许编辑, 则返回 null
        if (!bitmap.isMutable()) return null;

        try {
            int   width  = bitmap.getWidth();
            int   height = bitmap.getHeight();
            int[] pix    = new int[width * height];

            bitmap.getPixels(pix, 0, width, 0, 0, width, height);

            int wm  = width - 1;
            int hm  = height - 1;
            int wh  = width * height;
            int div = radius + radius + 1;

            int[] r    = new int[wh];
            int[] g    = new int[wh];
            int[] b    = new int[wh];
            int   rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
            int[] vmin = new int[Math.max(width, height)];

            int divsum = (div + 1) >> 1;
            divsum *= divsum;
            int[] dv = new int[256 * divsum];
            for (i = 0; i < 256 * divsum; i++) {
                dv[i] = (i / divsum);
            }

            yw = yi = 0;

            int[][] stack = new int[div][3];
            int     stackpointer;
            int     stackstart;
            int[]   sir;
            int     rbs;
            int     r1    = radius + 1;
            int     routsum, goutsum, boutsum;
            int     rinsum, ginsum, binsum;

            for (y = 0; y < height; y++) {
                rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
                for (i = -radius; i <= radius; i++) {
                    p = pix[yi + Math.min(wm, Math.max(i, 0))];
                    sir = stack[i + radius];
                    sir[0] = (p & 0xff0000) >> 16;
                    sir[1] = (p & 0x00ff00) >> 8;
                    sir[2] = (p & 0x0000ff);
                    rbs = r1 - Math.abs(i);
                    rsum += sir[0] * rbs;
                    gsum += sir[1] * rbs;
                    bsum += sir[2] * rbs;
                    if (i > 0) {
                        rinsum += sir[0];
                        ginsum += sir[1];
                        binsum += sir[2];
                    } else {
                        routsum += sir[0];
                        goutsum += sir[1];
                        boutsum += sir[2];
                    }
                }
                stackpointer = radius;

                for (x = 0; x < width; x++) {

                    r[yi] = dv[rsum];
                    g[yi] = dv[gsum];
                    b[yi] = dv[bsum];

                    rsum -= routsum;
                    gsum -= goutsum;
                    bsum -= boutsum;

                    stackstart = stackpointer - radius + div;
                    sir = stack[stackstart % div];

                    routsum -= sir[0];
                    goutsum -= sir[1];
                    boutsum -= sir[2];

                    if (y == 0) {
                        vmin[x] = Math.min(x + radius + 1, wm);
                    }
                    p = pix[yw + vmin[x]];

                    sir[0] = (p & 0xff0000) >> 16;
                    sir[1] = (p & 0x00ff00) >> 8;
                    sir[2] = (p & 0x0000ff);

                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];

                    rsum += rinsum;
                    gsum += ginsum;
                    bsum += binsum;

                    stackpointer = (stackpointer + 1) % div;
                    sir = stack[(stackpointer) % div];

                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];

                    rinsum -= sir[0];
                    ginsum -= sir[1];
                    binsum -= sir[2];

                    yi++;
                }
                yw += width;
            }
            for (x = 0; x < width; x++) {
                rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
                yp = -radius * width;
                for (i = -radius; i <= radius; i++) {
                    yi = Math.max(0, yp) + x;

                    sir = stack[i + radius];

                    sir[0] = r[yi];
                    sir[1] = g[yi];
                    sir[2] = b[yi];

                    rbs = r1 - Math.abs(i);

                    rsum += r[yi] * rbs;
                    gsum += g[yi] * rbs;
                    bsum += b[yi] * rbs;

                    if (i > 0) {
                        rinsum += sir[0];
                        ginsum += sir[1];
                        binsum += sir[2];
                    } else {
                        routsum += sir[0];
                        goutsum += sir[1];
                        boutsum += sir[2];
                    }

                    if (i < hm) {
                        yp += width;
                    }
                }
                yi = x;
                stackpointer = radius;
                for (y = 0; y < height; y++) {
                    // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                    pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                    rsum -= routsum;
                    gsum -= goutsum;
                    bsum -= boutsum;

                    stackstart = stackpointer - radius + div;
                    sir = stack[stackstart % div];

                    routsum -= sir[0];
                    goutsum -= sir[1];
                    boutsum -= sir[2];

                    if (x == 0) {
                        vmin[y] = Math.min(y + r1, hm) * width;
                    }
                    p = x + vmin[y];

                    sir[0] = r[p];
                    sir[1] = g[p];
                    sir[2] = b[p];

                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];

                    rsum += rinsum;
                    gsum += ginsum;
                    bsum += binsum;

                    stackpointer = (stackpointer + 1) % div;
                    sir = stack[stackpointer];

                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];

                    rinsum -= sir[0];
                    ginsum -= sir[1];
                    binsum -= sir[2];

                    yi += width;
                }
            }
            bitmap.setPixels(pix, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "fastBlur");
        }
        return null;
    }

    // ===========
    // = 滤镜效果 =
    // ===========

    /**
     * 怀旧效果处理
     * @param bitmap 待操作源图片
     * @return 怀旧效果处理后的图片
     */
    public static Bitmap nostalgic(final Bitmap bitmap) {
        if (bitmap == null) return null;
        try {
            int width  = bitmap.getWidth();
            int height = bitmap.getHeight();

            int   pixColor = 0;
            int   pixR     = 0;
            int   pixG     = 0;
            int   pixB     = 0;
            int   newR     = 0;
            int   newG     = 0;
            int   newB     = 0;
            int[] pixels   = new int[width * height];
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

            Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return newBitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "nostalgic");
        }
        return null;
    }

    /**
     * 光照效果处理
     * @param bitmap  待操作源图片
     * @param centerX 光源在 X 轴的位置
     * @param centerY 光源在 Y 轴的位置
     * @return 光照效果处理后的图片
     */
    public static Bitmap sunshine(
            final Bitmap bitmap,
            final int centerX,
            final int centerY
    ) {
        if (bitmap == null) return null;
        try {
            int width  = bitmap.getWidth();
            int height = bitmap.getHeight();

            int pixR = 0;
            int pixG = 0;
            int pixB = 0;

            int pixColor = 0;

            int newR   = 0;
            int newG   = 0;
            int newB   = 0;
            int radius = Math.min(centerX, centerY);

            final float strength = 150f; // 光照强度 100 ~ 150
            int[]       pixels   = new int[width * height];
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

                    // 计算当前点到光照中心的距离, 平面座标系中求两点之间的距离
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

            Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return newBitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "sunshine");
        }
        return null;
    }

    /**
     * 底片效果处理
     * @param bitmap 待操作源图片
     * @return 底片效果处理后的图片
     */
    public static Bitmap film(final Bitmap bitmap) {
        if (bitmap == null) return null;
        try {
            // ARGB 的最大值
            final int MAX_VALUE = 255;
            int       width     = bitmap.getWidth();
            int       height    = bitmap.getHeight();

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

            Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return newBitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "film");
        }
        return null;
    }

    /**
     * 柔化效果处理
     * <pre>
     *     delta 取值范围只要大于等于 1 就可以, 但是避免太大, 导致变得很暗, 限制 1-24
     * </pre>
     * @param bitmap 待操作源图片
     * @param delta  图片的亮暗程度值, 越小图片会越亮
     * @return 柔化效果处理后的图片
     */
    public static Bitmap soften(
            final Bitmap bitmap,
            @IntRange(from = 1, to = 24) final int delta
    ) {
        if (bitmap == null) return null;
        if (delta > 24 || delta <= 0) return null;
        try {
            // 高斯矩阵
            int[] gauss = new int[]{1, 2, 1, 2, 4, 2, 1, 2, 1};

            int width  = bitmap.getWidth();
            int height = bitmap.getHeight();

            int pixR = 0;
            int pixG = 0;
            int pixB = 0;

            int pixColor = 0;

            int newR = 0;
            int newG = 0;
            int newB = 0;

            int   idx    = 0;
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

            Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return newBitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "soften");
        }
        return null;
    }

    /**
     * 锐化效果处理
     * @param bitmap 待操作源图片
     * @return 锐化效果处理后的图片
     */
    public static Bitmap sharpen(final Bitmap bitmap) {
        if (bitmap == null) return null;
        try {
            // 拉普拉斯矩阵
            int[] laplacian = new int[]{-1, -1, -1, -1, 9, -1, -1, -1, -1};

            int width  = bitmap.getWidth();
            int height = bitmap.getHeight();

            int pixR = 0;
            int pixG = 0;
            int pixB = 0;

            int pixColor = 0;

            int newR = 0;
            int newG = 0;
            int newB = 0;

            int   idx    = 0;
            float alpha  = 0.3f;
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

            Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return newBitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "sharpen");
        }
        return null;
    }

    /**
     * 浮雕效果处理
     * @param bitmap 待操作源图片
     * @return 浮雕效果处理后的图片
     */
    public static Bitmap emboss(final Bitmap bitmap) {
        if (bitmap == null) return null;
        try {
            int width  = bitmap.getWidth();
            int height = bitmap.getHeight();

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

            Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return newBitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "emboss");
        }
        return null;
    }

//    /**
//     * 转为灰度图片
//     * @param bitmap 待操作源图片
//     * @return 灰度图
//     */
//    public static Bitmap gray(final Bitmap bitmap) {
//        if (bitmap == null) return null;
//        try {
//            int width = bitmap.getWidth(); // 获取位图的宽
//            int height = bitmap.getHeight(); // 获取位图的高
//            int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组
//            bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
//
//            int alpha = 0xFF << 24; // 默认将 bitmap 当成 24 色图片
//            for (int i = 0; i < height; i++) {
//                for (int j = 0; j < width; j++) {
//                    int grey = pixels[width * i + j];
//
//                    int red = ((grey & 0x00FF0000) >> 16);
//                    int green = ((grey & 0x0000FF00) >> 8);
//                    int blue = (grey & 0x000000FF);
//
//                    grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
//                    grey = alpha | (grey << 16) | (grey << 8) | grey;
//                    pixels[width * i + j] = grey;
//                }
//            }
//            Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
//            newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//            return newBitmap;
//        } catch (Exception e) {
//            LogPrintUtils.eTag(TAG, e, "gray");
//        }
//        return null;
//    }

    /**
     * 转为灰度图片
     * @param bitmap 待操作源图片
     * @return 灰度图
     */
    public static Bitmap gray(final Bitmap bitmap) {
        if (bitmap == null) return null;
        try {
            Bitmap      newBitmap   = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
            Canvas      canvas      = new Canvas(newBitmap);
            Paint       paint       = new Paint();
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);
            paint.setColorFilter(colorMatrixColorFilter);
            canvas.drawBitmap(bitmap, 0, 0, paint);
            return newBitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "gray");
        }
        return null;
    }

    // =======
    // = 色彩 =
    // =======

    /**
     * 饱和度处理
     * @param bitmap          待操作源图片
     * @param saturationValue 新的饱和度值
     * @return 改变了饱和度值之后的图片
     */
    public static Bitmap saturation(
            final Bitmap bitmap,
            final int saturationValue
    ) {
        if (bitmap == null) return null;
        try {
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
            Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas    = new Canvas(newBitmap);
            // 将源图片使用给定的画笔画到画布上
            canvas.drawBitmap(bitmap, 0, 0, paint);
            return newBitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "saturation");
        }
        return null;
    }

    /**
     * 亮度处理
     * @param bitmap   待操作源图片
     * @param lumValue 新的亮度值
     * @return 改变了亮度值之后的图片
     */
    public static Bitmap lum(
            final Bitmap bitmap,
            final int lumValue
    ) {
        if (bitmap == null) return null;
        try {
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
            Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas    = new Canvas(newBitmap);
            // 将源图片使用给定的画笔画到画布上
            canvas.drawBitmap(bitmap, 0, 0, paint);
            return newBitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "lum");
        }
        return null;
    }

    /**
     * 色相处理
     * @param bitmap   待操作源图片
     * @param hueValue 新的色相值
     * @return 改变了色相值之后的图片
     */
    public static Bitmap hue(
            final Bitmap bitmap,
            final int hueValue
    ) {
        if (bitmap == null) return null;
        try {
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
            Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas    = new Canvas(newBitmap);
            // 将源图片使用给定的画笔画到画布上
            canvas.drawBitmap(bitmap, 0, 0, paint);
            return newBitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "hue");
        }
        return null;
    }

    /**
     * 亮度、色相、饱和度处理
     * @param bitmap          待操作源图片
     * @param lumValue        亮度值
     * @param hueValue        色相值
     * @param saturationValue 饱和度值
     * @return 亮度、色相、饱和度处理后的图片
     */
    public static Bitmap lumHueSaturation(
            final Bitmap bitmap,
            final int lumValue,
            final int hueValue,
            final int saturationValue
    ) {
        if (bitmap == null) return null;
        try {
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
            Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas    = new Canvas(newBitmap);
            // 将源图片使用给定的画笔画到画布上
            canvas.drawBitmap(bitmap, 0, 0, paint);
            return newBitmap;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "lumHueSaturation");
        }
        return null;
    }

    // =

    /**
     * 将 YUV 格式的图片的源数据从横屏模式转为竖屏模式
     * <pre>
     *     注: 将源图片的宽高互换一下就是新图片的宽高
     * </pre>
     * @param sourceData YUV 格式的图片的源数据
     * @param width      宽
     * @param height     高
     * @return byte[]
     */
    public static byte[] yuvLandscapeToPortrait(
            final byte[] sourceData,
            final int width,
            final int height
    ) {
        if (sourceData == null || sourceData.length == 0) return null;
        byte[] rotatedData = new byte[sourceData.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rotatedData[x * height + height - y - 1] = sourceData[x + y * width];
            }
        }
        return rotatedData;
    }
}