package dev.other;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;

/**
 * detail: Glide 图形处理工具类
 * @author Ttt
 * <pre>
 *     结合 Glide 实现很炫的图片效果框架
 *     @see <a href="https://github.com/open-android/Glide-transformations"/>
 *     @see <a href="https://github.com/wasabeef/glide-transformations"/>
 * </pre>
 */
public final class GlideTransformUtils {

    private GlideTransformUtils() {
    }

    /**
     * detail: 旋转处理
     * @author Ttt
     */
    public static class RotateTransformation
            extends BitmapTransformation {
        // 旋转角度
        private float rotateRotationAngle;

        public RotateTransformation(float rotateRotationAngle) {
            this.rotateRotationAngle = rotateRotationAngle;
        }

        @Override
        protected Bitmap transform(
                BitmapPool pool,
                Bitmap toTransform,
                int outWidth,
                int outHeight
        ) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotateRotationAngle);
            return Bitmap.createBitmap(toTransform, 0, 0,
                    toTransform.getWidth(), toTransform.getHeight(), matrix, true);
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {
        }
    }

    /**
     * detail: 转换圆形处理
     * @author Ttt
     */
    public static class GlideCircleTransform
            extends BitmapTransformation {

        @Override
        protected Bitmap transform(
                BitmapPool pool,
                Bitmap toTransform,
                int outWidth,
                int outHeight
        ) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(
                BitmapPool pool,
                Bitmap source
        ) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x    = (source.getWidth() - size) / 2;
            int y    = (source.getHeight() - size) / 2;

            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_4444);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_4444);
            }

            Canvas canvas = new Canvas(result);
            Paint  paint  = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {
        }
    }

    /**
     * detail: 圆角处理
     * @author Ttt
     */
    public static class GlideRoundTransform
            extends BitmapTransformation {

        // 圆角大小
        private float radius;

        public GlideRoundTransform(float radius) {
            this.radius = radius;
        }

        @Override
        protected Bitmap transform(
                BitmapPool pool,
                Bitmap toTransform,
                int outWidth,
                int outHeight
        ) {
            return roundCrop(pool, toTransform);
        }

        private Bitmap roundCrop(
                BitmapPool pool,
                Bitmap source
        ) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_4444);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_4444);
            }

            Canvas canvas = new Canvas(result);
            Paint  paint  = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {
        }
    }

    /**
     * detail: 图片模糊处理
     * @author Ttt
     */
    public static class GlideBlurformation
            extends BitmapTransformation {

        // Context
        private final Context mContext;

        public GlideBlurformation(Context context) {
            this.mContext = context;
        }

        @Override
        protected Bitmap transform(
                @NonNull BitmapPool pool,
                @NonNull Bitmap toTransform,
                int outWidth,
                int outHeight
        ) {
            Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
            return blurBitmap(mContext, bitmap, 20, outWidth, outHeight);
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {
        }

        /**
         * 模糊图片处理
         * @param context   {@link Context}
         * @param image     待模糊的图片
         * @param outWidth  输出的宽度
         * @param outHeight 输出的高度
         * @return 模糊处理后的 Bitmap
         */
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        public Bitmap blurBitmap(
                Context context,
                Bitmap image,
                float blurRadius,
                int outWidth,
                int outHeight
        ) {
            // 将缩小后的图片做为预渲染的图片
            Bitmap inputBitmap = Bitmap.createScaledBitmap(image, outWidth, outHeight, false);
            // 创建一张渲染后的输出图片
            Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
            // 创建 RenderScript 内核对象
            RenderScript rs = RenderScript.create(context);
            // 创建一个模糊效果的 RenderScript 的工具对象
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            // 由于 RenderScript 并没有使用 VM 来分配内存, 所以需要使用 Allocation 类来创建和分配内存空间
            // 创建 Allocation 对象的时候其实内存是空的, 需要使用 copyTo() 将数据填充进去
            Allocation tmpIn  = Allocation.createFromBitmap(rs, inputBitmap);
            Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
            // 设置渲染的模糊程度, 25f 是最大模糊度
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                blurScript.setRadius(blurRadius);
            }
            // 设置 blurScript 对象的输入内存
            blurScript.setInput(tmpIn);
            // 将输出数据保存到输出内存中
            blurScript.forEach(tmpOut);
            // 将数据填充到 Allocation 中
            tmpOut.copyTo(outputBitmap);
            return outputBitmap;
        }
    }
}