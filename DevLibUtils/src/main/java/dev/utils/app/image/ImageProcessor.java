package dev.utils.app.image;

import android.content.Context;
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

import java.io.InputStream;

/**
 * detail: 图片处理工具类
 * @author Ttt
 */
public class ImageProcessor {

    // 待处理的Bitmap
    private Bitmap mBitmap;

    /**
     * 构造方法
     * @param bitmap 待处理的bitmap
     */
    public ImageProcessor(final Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    /**
     * 缩放处理
     * @param scaling 缩放比例
     * @return 缩放后的图片
     */
    public Bitmap scale(final float scaling) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaling, scaling);
        return Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
    }

    /**
     * 缩放处理
     * @param newWidth 新的宽度
     * @return
     */
    public Bitmap scaleByWidth(final int newWidth) {
        return scale((float) newWidth / mBitmap.getWidth());
    }

    /**
     * 缩放处理
     * @param newHeight 新的高度
     * @return
     */
    public Bitmap scaleByHeight(final int newHeight) {
        return scale((float) newHeight / mBitmap.getHeight());
    }

    /**
     * 水平翻转处理
     * @param bitmap 原图
     * @return 水平翻转后的图片
     */
    public Bitmap reverseByHorizontal(final Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    /**
     * 垂直翻转处理
     * @param bitmap 原图
     * @return 垂直翻转后的图片
     */
    public Bitmap reverseByVertical(final Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    /**
     * 将给定资源ID的Drawable转换成Bitmap
     * @param context {@link Context}
     * @param resId   Drawable资源文件的ID
     * @return 新的Bitmap
     */
    public Bitmap drawableToBitmap(final Context context, final int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 圆角处理
     * @param pixels 角度, 度数越大圆角越大
     * @return 转换成圆角后的图片
     */
    public Bitmap roundCorner(final float pixels) {
        Bitmap output = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight()); // 创建一个同原图一样大小的矩形, 用于把原图绘制到这个矩形上
        RectF rectF = new RectF(rect); // 创建一个精度更高的矩形, 用于画出圆角效果
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0); // 涂上黑色全透明的底色
        paint.setColor(0xff424242); // 设置画笔的颜色为不透明的灰色
        canvas.drawRoundRect(rectF, pixels, pixels, paint); // 用给给定的画笔把给定的矩形以给定的圆角的度数画到画布
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mBitmap, rect, rect, paint); // 用画笔paint将原图bitmap根据新的矩形重新绘制
        return output;
    }

    /**
     * 倒影处理
     * @param reflectionSpacing 原图与倒影之间的间距
     * @param reflectionHeight  倒影高度
     * @return 加上倒影后的图片
     */
    public Bitmap reflection(final int reflectionSpacing, final int reflectionHeight) {
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        // 获取倒影图片, 并创建一张宽度与原图相同, 但高度等于原图的高度加上间距加上倒影的高度的图片, 并创建画布, 画布分为上中下三部分, 上: 是原图, 中: 是原图与倒影的间距, 下: 是倒影
        Bitmap reflectionImage = reverseByVertical(mBitmap); //
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, height + reflectionSpacing + reflectionHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);

        // 将原图画到画布的上半部分, 将倒影画到画布的下半部分, 倒影与画布顶部的间距是原图的高度加上原图与倒影之间的间距
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionSpacing, null);
        reflectionImage.recycle();

        // 将倒影改成半透明, 创建画笔, 并设置画笔的渐变从半透明的白色到全透明的白色, 然后再倒影上面画半透明效果
        Paint paint = new Paint();
        paint.setShader(new LinearGradient(0, mBitmap.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionSpacing, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, height + reflectionSpacing, width, bitmapWithReflection.getHeight() + reflectionSpacing, paint);

        return bitmapWithReflection;
    }

    /**
     * 倒影处理
     * @return 加上倒影后的图片
     */
    public Bitmap reflection() {
        return reflection(4, mBitmap.getHeight() / 2);
    }

    /**
     * 旋转处理
     * @param angle 旋转角度
     * @param px    旋转中心点在X轴的坐标
     * @param py    旋转中心点在Y轴的坐标
     * @return 旋转后的图片
     */
    public Bitmap rotate(final float angle, final float px, final float py) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle, px, py);
        return Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, false);
    }

    /**
     * 旋转后处理
     * @param angle 旋转角度
     * @return 旋转后的图片
     */
    public Bitmap rotate(final float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, false);
    }
}