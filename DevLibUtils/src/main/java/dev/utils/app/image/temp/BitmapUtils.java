package dev.utils.app.image.temp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;

import dev.utils.LogPrintUtils;

/**
 * detail: Bitmap 工具类
 * @author Ttt
 * <pre>
 *     Android PorterDuffXfermode 的正确使用方式
 *     @see <a href="http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0731/8318.html"/>
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
     * 获取 Bitmap 宽高
     * @param file 文件
     * @return int[] { 宽度, 高度 }
     */
    public static int[] getBitmapWidthHeight(final File file) {
        return getBitmapWidthHeight(getAbsolutePath(file));
    }

    /**
     * 获取 Bitmap 宽高
     * @param filePath 文件路径
     * @return int[] { 宽度, 高度 }
     */
    public static int[] getBitmapWidthHeight(final String filePath) {
        if (!isFileExists(filePath)) return new int[]{0, 0};
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
        if (isEmpty(bitmap)) return null;
        return bitmap.copy(bitmap.getConfig(), isMutable);
    }

    // =

    /**
     * 获取 Alpha 位图 ( 获取源图片的轮廓 rgb 为 0)
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

    /**
     * Bitmap 通知回收
     * @param bitmap 待回收图片
     */
    public static void recycle(final Bitmap bitmap) {
        if (bitmap == null) return;
        if (!bitmap.isRecycled()) {
            try {
                bitmap.recycle();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "recycle");
            }
        }
    }

    // ===============
    // = Bitmap 操作 =
    // ===============

    // ========
    // = 旋转 =
    // ========

    /**
     * 旋转图片
     * @param bitmap  待操作源图片
     * @param degrees 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotate(final Bitmap bitmap, final float degrees) {
        if (isEmpty(bitmap)) return null;
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    /**
     * 旋转图片
     * @param bitmap  待操作源图片
     * @param degrees 旋转角度
     * @param px      旋转中心点在 X 轴的坐标
     * @param py      旋转中心点在 Y 轴的坐标
     * @return 旋转后的图片
     */
    public static Bitmap rotate(final Bitmap bitmap, final float degrees, final float px, final float py) {
        if (isEmpty(bitmap)) return null;
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

    // ========
    // = 翻转 =
    // ========

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
     * 垂直翻转处理
     * @param bitmap     待操作源图片
     * @param horizontal 是否水平翻转
     * @return 翻转后的图片
     */
    public static Bitmap reverse(final Bitmap bitmap, final boolean horizontal) {
        if (isEmpty(bitmap)) return null;
        Matrix matrix = new Matrix();
        if (horizontal) {
            matrix.preScale(-1, 1);
        } else {
            matrix.preScale(1, -1);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    // ========
    // = 缩放 =
    // ========

    /**
     * 缩放图片 ( 指定所需宽高 )
     * @param bitmap  待操作源图片
     * @param newSize 新尺寸 ( 宽高 )
     * @return 缩放后的图片
     */
    public static Bitmap zoom(final Bitmap bitmap, final int newSize) {
        return zoom(bitmap, newSize, newSize);
    }

    /**
     * 缩放图片 ( 指定所需宽高 )
     * @param bitmap    待操作源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @return 缩放后的图片
     */
    public static Bitmap zoom(final Bitmap bitmap, final int newWidth, final int newHeight) {
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
    public static Bitmap scale(final Bitmap bitmap, final float scale) {
        return scale(bitmap, scale, scale);
    }

    /**
     * 缩放图片 ( 比例缩放 )
     * @param bitmap 待操作源图片
     * @param scaleX 横向缩放比例 ( 缩放宽度倍数 )
     * @param scaleY 纵向缩放比例 ( 缩放高度倍数 )
     * @return 缩放后的图片
     */
    public static Bitmap scale(final Bitmap bitmap, final float scaleX, final float scaleY) {
        if (isEmpty(bitmap)) return null;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    // ========
    // = 倾斜 =
    // ========

    /**
     * 倾斜图片
     * @param bitmap 待操作源图片
     * @param kx     X 轴倾斜因子
     * @param ky     Y 轴倾斜因子
     * @return 倾斜后的图片
     */
    public static Bitmap skew(final Bitmap bitmap, final float kx, final float ky) {
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
    public static Bitmap skew(final Bitmap bitmap, final float kx, final float ky, final float px, final float py) {
        if (isEmpty(bitmap)) return null;
        Matrix matrix = new Matrix();
        matrix.setSkew(kx, ky, px, py);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    // ========
    // = 裁剪 =
    // ========

    /**
     * 裁剪图片
     * @param src     待操作源图片
     * @param width   裁剪宽度
     * @param height  裁剪高度
     * @return 裁剪后的图片
     */
    public static Bitmap clip(final Bitmap src, final int width, final int height) {
        return clip(src, 0, 0, width, height);
    }

    /**
     * 裁剪图片
     * @param src     待操作源图片
     * @param x       X 轴开始坐标
     * @param y       Y 轴开始坐标
     * @param width   裁剪宽度
     * @param height  裁剪高度
     * @return 裁剪后的图片
     */
    public static Bitmap clip(final Bitmap src, final int x, final int y, final int width, final int height) {
        if (isEmpty(src)) return null;
        return Bitmap.createBitmap(src, x, y, width, height);
    }

    // =============
    // = 合并/叠加 =
    // =============



    // ========
    // = 倒影 =
    // ========

    /**
     * 倒影处理
     * @param bitmap 待操作源图片
     * @return 倒影处理后的图片
     */
    public static Bitmap reflection(final Bitmap bitmap) {
        return reflection(bitmap, 0, getBitmapHeight(bitmap));
    }

    /**
     * 倒影处理
     * @param bitmap           待操作源图片
     * @param reflectionHeight 倒影高度
     * @return 倒影处理后的图片
     */
    public static Bitmap reflection(final Bitmap bitmap, final int reflectionHeight) {
        return reflection(bitmap, 0, reflectionHeight);
    }

    /**
     * 倒影处理
     * @param bitmap            待操作源图片
     * @param reflectionSpacing 源图片与倒影之间的间距
     * @param reflectionHeight  倒影高度
     * @return 倒影处理后的图片
     */
    public static Bitmap reflection(final Bitmap bitmap, final int reflectionSpacing, final int reflectionHeight) {
        if (isEmpty(bitmap)) return null;
        if (reflectionHeight <= 0) return null;
        // 获取图片宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        // 创建画布, 画布分为上中下三部分, 上: 是源图片, 中: 是源图片与倒影的间距, 下: 是倒影

        // 创建倒影图片
        Bitmap reflectionImage = reverseByVertical(bitmap); // 垂直翻转图片 ( 上下颠倒 )
        // 创建一张宽度与源图片相同, 但高度等于 源图片的高度 + 间距 + 倒影的高度的图片
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, height + reflectionSpacing + reflectionHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);

        // 将源图片画到画布的上半部分, 将倒影画到画布的下半部分, 倒影与画布顶部的间距是源图片的高度加上源图片与倒影之间的间距
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionSpacing, null);
        reflectionImage.recycle();

        // 边距负数处理
        int spacing = Math.max(reflectionSpacing, 0);

        // 将倒影改成半透明, 创建画笔, 并设置画笔的渐变从半透明的白色到全透明的白色, 然后再倒影上面画半透明效果
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight() + spacing,
                0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, height + spacing, width, bitmapWithReflection.getHeight() + spacing, paint);
        return bitmapWithReflection;
    }

    // ========
    // = 圆角 =
    // ========

    /**
     * 圆角图片 ( 非圆形 )
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
     * 圆角图片 ( 非圆形 )
     * @param bitmap 待操作源图片
     * @param pixels 圆角大小
     * @return 圆角处理后的图片
     */
    public static Bitmap roundCorner(final Bitmap bitmap, final float pixels) {
        if (isEmpty(bitmap)) return null;
        // 创建一个同源图片一样大小的矩形, 用于把源图片绘制到这个矩形上
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect); // 创建一个精度更高的矩形, 用于画出圆角效果

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xff424242); // 设置画笔的颜色为不透明的灰色

        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
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
     * 圆角图片 ( 非圆形 ) - 只有 leftTop、rightTop
     * @param bitmap 待操作源图片
     * @param pixels 圆角大小
     * @return 圆角处理后的图片
     */
    public static Bitmap roundCornerTop(final Bitmap bitmap, final float pixels) {
        return roundCorner(bitmap, pixels, new boolean[]{true, true, true, false});
    }

    /**
     * 圆角图片 ( 非圆形 ) - 只有 leftBottom、rightBottom
     * @param bitmap 待操作源图片
     * @param pixels 圆角大小
     * @return 圆角处理后的图片
     */
    public static Bitmap roundCornerBottom(final Bitmap bitmap, final float pixels) {
        return roundCorner(bitmap, pixels, new boolean[]{true, false, true, true});
    }

    // =

    /**
     * 圆角图片 ( 非圆形 )
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
    public static Bitmap roundCorner(final Bitmap bitmap, final float pixels, final boolean[] directions) {
        if (isEmpty(bitmap)) return null;
        if (directions == null || directions.length != 4) return null;
        // 创建一个同源图片一样大小的矩形, 用于把源图片绘制到这个矩形上
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect); // 创建一个精度更高的矩形, 用于画出圆角效果

        // ================
        // = 圆角方向控制 =
        // ================

        if (!directions[0])
            rectF.left -= pixels;

        if (!directions[1])
            rectF.top -= pixels;

        if (!directions[2])
            rectF.right += pixels;

        if (!directions[3])
            rectF.bottom += pixels;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xff424242); // 设置画笔的颜色为不透明的灰色

        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, pixels, pixels, paint);
        // 绘制底圆后, 进行合并 ( 交集处理 )
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return newBitmap;
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