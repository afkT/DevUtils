package ktx.dev.other

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import dev.utils.DevFinal
import dev.utils.LogPrintUtils
import dev.utils.common.thread.DevThreadManager
import java.util.*

/**
 * detail: ZXing 二维码工具类
 * @author Ttt
 */
@Deprecated("推荐使用 DevBarCodeEngine 实现类 ZXingEngineImpl")
object ZXingQRCodeUtils {

    // 日志 TAG
    private val TAG = ZXingQRCodeUtils::class.java.simpleName

    // ============
    // = 生成二维码 =
    // ============

    /**
     * detail: 二维码编码 ( 生成 ) 回调
     * @author Ttt
     */
    interface QREncodeCallback {

        /**
         * 二维码编码 ( 生成 ) 回调
         * @param success 是否成功
         * @param bitmap  成功图片
         * @param error   异常信息
         */
        fun onResult(
            success: Boolean,
            bitmap: Bitmap?,
            error: Throwable?
        )
    }

    // =

    /**
     * 编码 ( 生成 ) 二维码图片
     * @param content 生成内容
     * @param size    图片宽高大小 ( 正方形 px )
     */
    fun encodeQRCode(
        content: String,
        size: Int
    ) {
        encodeQRCode(content, size, null, null)
    }

    /**
     * 编码 ( 生成 ) 二维码图片
     * @param content  生成内容
     * @param size     图片宽高大小 ( 正方形 px )
     * @param callback 生成结果回调
     */
    fun encodeQRCode(
        content: String,
        size: Int,
        callback: QREncodeCallback?
    ) {
        encodeQRCode(content, size, null, callback)
    }

    /**
     * 编码 ( 生成 ) 二维码图片
     * @param content  生成内容
     * @param size     图片宽高大小 ( 正方形 px )
     * @param logo     中间 Logo
     * @param callback 生成结果回调
     */
    fun encodeQRCode(
        content: String,
        size: Int,
        logo: Bitmap?,
        callback: QREncodeCallback?
    ) {
        DevThreadManager.getInstance(10).execute {
            try {
                // 编码 ( 生成 ) 二维码图片
                var qrCodeBitmap = encodeQRCodeSync(content, size)
                if (logo != null) { // 中间 Logo
                    qrCodeBitmap = addLogoToQRCode(qrCodeBitmap, logo)
                }
                // 触发回调
                callback?.onResult(true, qrCodeBitmap, null)
            } catch (e: Exception) {
                LogPrintUtils.eTag(TAG, e, "encodeQRCode")
                // 触发回调
                callback?.onResult(false, null, e)
            }
        }
    }

    // =======
    // = 编码 =
    // =======

    // 编码类型
    private val ENCODE_HINTS: MutableMap<EncodeHintType, Any?> = EnumMap(
        EncodeHintType::class.java
    )

    init {
        // 编码类型
        ENCODE_HINTS[EncodeHintType.CHARACTER_SET] = DevFinal.ENCODE.UTF_8
        // 指定纠错等级, 纠错级别 ( L 7%、M 15%、Q 25%、H 30% )
        ENCODE_HINTS[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
        // 设置二维码边的空度, 非负数
        ENCODE_HINTS[EncodeHintType.MARGIN] = 0
    }

    /**
     * 编码 ( 生成 ) 二维码图片
     * @param content 生成内容
     * @param size    图片宽高大小 ( 正方形 px )
     * @return 二维码图片
     */
    fun encodeQRCodeSync(
        content: String,
        size: Int
    ): Bitmap? {
        return encodeQRCodeSync(content, size, Color.BLACK, Color.WHITE)
    }

    /**
     * 编码 ( 生成 ) 二维码图片
     * @param content         生成内容
     * @param size            图片宽高大小 ( 正方形 px )
     * @param foregroundColor 二维码图片的前景色
     * @param backgroundColor 二维码图片的背景色
     * @return 二维码图片
     * 该方法是耗时操作, 请在子线程中调用
     */
    fun encodeQRCodeSync(
        content: String,
        size: Int,
        foregroundColor: Int,
        backgroundColor: Int
    ): Bitmap? {
        val matrix =
            MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, ENCODE_HINTS)
        val pixels = IntArray(size * size)
        for (y in 0 until size) {
            for (x in 0 until size) {
                if (matrix[x, y]) {
                    pixels[y * size + x] = foregroundColor
                } else {
                    pixels[y * size + x] = backgroundColor
                }
            }
        }
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, size, 0, 0, size, size)
        return bitmap
    }

    // ============
    // = 解析二维码 =
    // ============

    // 解析配置
    private val DECODE_HINTS: Map<DecodeHintType, Any?> = EnumMap(
        DecodeHintType::class.java
    )

    /**
     * detail: 二维码解码 ( 解析 ) 回调
     * @author Ttt
     */
    interface QRDecodeCallback {

        /**
         * 二维码解码 ( 解析 ) 回调
         * @param success 是否成功
         * @param result  识别数据 [Result]
         * @param error   异常信息
         */
        fun onResult(
            success: Boolean,
            result: Result?,
            error: Throwable?
        )
    }

    // =

    /**
     * 解码 ( 解析 ) 二维码图片
     * @param bitmap   待解析的二维码图片
     * @param callback 解析结果回调
     */
    fun decodeQRCode(
        bitmap: Bitmap?,
        callback: QRDecodeCallback?
    ) {
        if (bitmap == null) {
            callback?.onResult(false, null, Exception("bitmap is null"))
            return
        }
        // 开始解析
        DevThreadManager.getInstance(5).execute {
            try {
                val result = decodeQRCodeSync(bitmap)
                // 触发回调
                callback?.onResult(result != null, result, null)
            } catch (e: Exception) {
                LogPrintUtils.eTag(TAG, e, "decodeQRCode")
                // 触发回调
                callback?.onResult(false, null, e)
            }
        }
    }

    /**
     * 解码 ( 解析 ) 二维码图片
     * @param bitmap   待解析的二维码图片
     */
    fun decodeQRCodeSync(bitmap: Bitmap?): Result? {
        if (bitmap != null) {
            val width = bitmap.width
            val height = bitmap.height
            val pixels = IntArray(width * height)
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
            val source = RGBLuminanceSource(width, height, pixels)
            val result = MultiFormatReader().decode(
                BinaryBitmap(HybridBinarizer(source)), DECODE_HINTS
            )
            return result
        }
        return null
    }

    // ==========
    // = 其他功能 =
    // ==========

    /**
     * 添加 Logo 到二维码图片上
     * @param src  二维码图片
     * @param logo Logo
     * @return 含 Logo 二维码图片
     * 非最优方法, 该方式是直接把 Logo 覆盖在图片上会遮挡部分内容
     */
    fun addLogoToQRCode(
        src: Bitmap?,
        logo: Bitmap?
    ): Bitmap? {
        if (src == null || logo == null) return src
        // 获取图片宽度高度
        val srcWidth = src.width
        val srcHeight = src.height
        val logoWidth = logo.width
        val logoHeight = logo.height
        // 缩放图片
        val scaleFactor = srcWidth * 1.0f / 4 / logoWidth // 这里的 4 决定 Logo 在图片的比例 四分之一
        var bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888)
        try {
            val canvas = Canvas(bitmap)
            canvas.drawBitmap(src, 0F, 0F, null)
            canvas.scale(
                scaleFactor,
                scaleFactor,
                (srcWidth / 2).toFloat(),
                (srcHeight / 2).toFloat()
            )
            canvas.drawBitmap(
                logo,
                ((srcWidth - logoWidth) / 2).toFloat(),
                ((srcHeight - logoHeight) / 2).toFloat(),
                null
            )
            canvas.save()
            canvas.restore()
        } catch (e: Exception) {
            LogPrintUtils.eTag(TAG, e, "addLogoToQRCode")
            bitmap = null
        }
        return bitmap
    }

    // ==========
    // = 获取结果 =
    // ==========

    /**
     * 获取扫描结果数据
     * @param result 识别数据 [Result]
     * @return 扫描结果数据
     */
    fun getResultData(result: Result?): String? {
        return result?.text
    }
}