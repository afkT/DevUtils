package dev.utils.app.image;

import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.IntRange;
import android.support.annotation.RequiresApi;

import dev.DevUtils;
import dev.utils.LogPrintUtils;

/**
 * detail: Bitmap 滤镜、效果工具类
 * @author Ttt
 */
public final class BitmapFilterUtils {

    private BitmapFilterUtils() {
    }

    // 日志 TAG
    private static final String TAG = BitmapFilterUtils.class.getSimpleName();

    // ========
    // = 效果 =
    // ========

    /**
     * 图片模糊处理 ( Android RenderScript 实现, 效率最高 )
     * @param bitmap 待模糊图片
     * @param radius 模糊度 (0-25)
     * @return 模糊后的图片
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap blur(final Bitmap bitmap, @IntRange(from = 0, to = 25) final int radius) {
        if (bitmap == null) return null;
        try {
            // 创建 RenderScript 内核对象
            RenderScript rs = RenderScript.create(DevUtils.getContext());
            // 由于 RenderScript 并没有使用 VM 来分配内存, 所以需要使用 Allocation 类来创建和分配内存空间
            // 创建 Allocation 对象的时候其实内存是空的, 需要使用 copyTo() 将数据填充进去
            Allocation input = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
            Allocation output = Allocation.createTyped(rs, input.getType());
            // 创建一个模糊效果的 RenderScript 的工具对象，第二个参数 Element 相当于一种像素处理的算法，高斯模糊的话用这个就好
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
     * @param radius 模糊度 (0-25)
     * @return 模糊后的图片
     */
    public static Bitmap blurFast(final Bitmap bitmap, @IntRange(from = 0, to = 25) final int radius) {
        if (bitmap == null) return null;
        // 如果 Bitmap 不允许编辑, 则返回 null
        if (!bitmap.isMutable()) return null;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int[] pix = new int[width * height];
        bitmap.getPixels(pix, 0, width, 0, 0, width, height);

        int wm = width - 1;
        int hm = height - 1;
        int wh = width * height;
        int div = radius + radius + 1;

        int[] r = new int[wh];
        int[] g = new int[wh];
        int[] b = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int[] vmin = new int[Math.max(width, height)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int[] dv = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

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
        try {
            bitmap.setPixels(pix, 0, width, 0, 0, width, height);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "blurFast");
            return null;
        }
        return bitmap;
    }

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