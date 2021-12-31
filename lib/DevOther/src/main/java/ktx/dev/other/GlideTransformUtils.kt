package ktx.dev.other

import android.graphics.*
import android.os.Build
import androidx.annotation.RequiresApi
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import dev.utils.app.image.ImageFilterUtils
import java.security.MessageDigest
import kotlin.math.min

/**
 * detail: Glide 图形处理工具类
 * @author Ttt
 * 结合 Glide 实现很炫的图片效果框架
 * @see https://github.com/open-android/Glide-transformations
 * @see https://github.com/wasabeef/glide-transformations
 */
class GlideTransformUtils private constructor() {

    /**
     * detail: 旋转处理
     * @author Ttt
     */
    class RotateTransformation(
        // 旋转角度
        private val rotateRotationAngle: Float
    ) : BitmapTransformation() {
        override fun transform(
            pool: BitmapPool,
            toTransform: Bitmap,
            outWidth: Int,
            outHeight: Int
        ): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(rotateRotationAngle)
            return Bitmap.createBitmap(
                toTransform, 0, 0,
                toTransform.width, toTransform.height, matrix, true
            )
        }

        override fun updateDiskCacheKey(messageDigest: MessageDigest) {}
    }

    /**
     * detail: 转换圆形处理
     * @author Ttt
     */
    class GlideCircleTransform : BitmapTransformation() {
        override fun transform(
            pool: BitmapPool,
            toTransform: Bitmap,
            outWidth: Int,
            outHeight: Int
        ): Bitmap {
            val size = min(toTransform.width, toTransform.height)
            val x = (toTransform.width - size) / 2
            val y = (toTransform.height - size) / 2
            val squared = Bitmap.createBitmap(toTransform, x, y, size, size)
            val result: Bitmap = pool[size, size, Bitmap.Config.ARGB_4444]
            val canvas = Canvas(result)
            val paint = Paint()
            paint.shader = BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.isAntiAlias = true
            val r = size / 2f
            canvas.drawCircle(r, r, r, paint)
            return result
        }

        override fun updateDiskCacheKey(messageDigest: MessageDigest) {}
    }

    /**
     * detail: 圆角处理
     * @author Ttt
     */
    class GlideRoundTransform(
        // 圆角大小
        private val radius: Float
    ) : BitmapTransformation() {
        override fun transform(
            pool: BitmapPool,
            toTransform: Bitmap,
            outWidth: Int,
            outHeight: Int
        ): Bitmap {
            val result = pool[toTransform.width, toTransform.height, Bitmap.Config.ARGB_4444]
            val canvas = Canvas(result)
            val paint = Paint()
            paint.shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.isAntiAlias = true
            val rectF = RectF(
                0f, 0f, toTransform.width.toFloat(),
                toTransform.height.toFloat()
            )
            canvas.drawRoundRect(rectF, radius, radius, paint)
            return result
        }

        override fun updateDiskCacheKey(messageDigest: MessageDigest) {}
    }

    /**
     * detail: 图片模糊处理
     * @author Ttt
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    class GlideBlurformation : BitmapTransformation() {
        override fun transform(
            pool: BitmapPool,
            toTransform: Bitmap,
            outWidth: Int,
            outHeight: Int
        ): Bitmap {
            val bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight)
            return ImageFilterUtils.blur(bitmap, 20)
        }

        override fun updateDiskCacheKey(messageDigest: MessageDigest) {}
    }
}