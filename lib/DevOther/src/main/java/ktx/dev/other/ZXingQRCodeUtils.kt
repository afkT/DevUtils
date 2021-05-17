package ktx.dev.other

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import dev.utils.LogPrintUtils
import dev.utils.common.thread.DevThreadManager
import java.util.*

/**
 * detail: ZXing 二维码工具类
 * @author Ttt
 */
object ZXingQRCodeUtils {

    // 日志 TAG
    private val TAG = ZXingQRCodeUtils::class.java.simpleName

    // ============
    // = 生成二维码 =
    // ============

    /**
     * detail: 生成二维码结果回调
     * @author Ttt
     */
    interface QRResultCallback {

        /**
         * 生成二维码结果回调
         * @param success 是否成功
         * @param bitmap  成功图片
         * @param e       异常信息
         */
        fun onResult(
            success: Boolean,
            bitmap: Bitmap?,
            e: Exception?
        )
    }

    // =

    /**
     * 生成二维码图片
     * @param content 生成内容
     * @param size    图片宽高大小 ( 正方形 px )
     */
    fun createQRCodeImage(
        content: String,
        size: Int
    ) {
        createQRCodeImage(content, size, null, null)
    }

    /**
     * 生成二维码图片
     * @param content  生成内容
     * @param size     图片宽高大小 ( 正方形 px )
     * @param callback 生成结果回调
     */
    fun createQRCodeImage(
        content: String,
        size: Int,
        callback: QRResultCallback?
    ) {
        createQRCodeImage(content, size, null, callback)
    }

    /**
     * 生成二维码图片
     * @param content  生成内容
     * @param size     图片宽高大小 ( 正方形 px )
     * @param logo     中间 Logo
     * @param callback 生成结果回调
     */
    fun createQRCodeImage(
        content: String,
        size: Int,
        logo: Bitmap?,
        callback: QRResultCallback?
    ) {
        DevThreadManager.getInstance(10).execute {
            try {
                // 生成二维码图片
                var qrCodeBitmap = syncEncodeQRCode(content, size)
                if (logo != null) { // 中间 Logo
                    qrCodeBitmap = addLogoToQRCode(qrCodeBitmap, logo)
                }
                // 触发回调
                callback?.onResult(true, qrCodeBitmap, null)
            } catch (e: java.lang.Exception) {
                LogPrintUtils.eTag(TAG, e, "createQRCodeImage")
                // 触发回调
                callback?.onResult(false, null, e)
            }
        }
    }

    // ============
    // = 解析二维码 =
    // ============

    // 解析配置
    private val DECODE_HINTS: Map<DecodeHintType, Any?> = EnumMap(
        DecodeHintType::class.java
    )

    /**
     * detail: 二维码扫描结果回调
     * @author Ttt
     */
    interface QRScanCallback {

        /**
         * 二维码扫描结果回调
         * @param success 是否成功
         * @param result  识别数据 [Result]
         * @param e       异常信息
         */
        fun onResult(
            success: Boolean,
            result: Result?,
            e: Exception?
        )
    }

    // =

    /**
     * 解析二维码图片
     * @param bitmap   待解析的二维码图片
     * @param callback 解析结果回调
     */
    fun decodeQRCode(
        bitmap: Bitmap?,
        callback: QRScanCallback?
    ) {
        if (bitmap == null) {
            callback?.onResult(false, null, java.lang.Exception("bitmap is null"))
            return
        }
        // 开始解析
        DevThreadManager.getInstance(5).execute {
            try {
                val width = bitmap.width
                val height = bitmap.height
                val pixels = IntArray(width * height)
                bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
                val source = RGBLuminanceSource(width, height, pixels)
                val result =
                    MultiFormatReader().decode(BinaryBitmap(HybridBinarizer(source)), DECODE_HINTS)
                // 触发回调
                callback?.onResult(result != null, result, null)
            } catch (e: java.lang.Exception) {
                LogPrintUtils.eTag(TAG, e, "decodeQRCode")
                // 触发回调
                callback?.onResult(false, null, e)
            }
        }
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

    // =======
    // = 编码 =
    // =======

    // 编码类型
    private val ENCODE_HINTS: MutableMap<EncodeHintType, Any?> = EnumMap(
        EncodeHintType::class.java
    )

    init {
        // 编码类型
        ENCODE_HINTS[EncodeHintType.CHARACTER_SET] = "utf-8"
        // 指定纠错等级, 纠错级别 ( L 7%、M 15%、Q 25%、H 30% )
        ENCODE_HINTS[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
        // 设置二维码边的空度, 非负数
        ENCODE_HINTS[EncodeHintType.MARGIN] = 0
    }

    /**
     * 同步创建黑色前景色、白色背景色的二维码图片
     * @param content 生成内容
     * @param size    图片宽高大小 ( 正方形 px )
     * @return 二维码图片
     */
    fun syncEncodeQRCode(
        content: String,
        size: Int
    ): Bitmap? {
        return syncEncodeQRCode(content, size, Color.BLACK, Color.WHITE)
    }

    /**
     * 同步创建指定前景色、指定背景色二维码图片
     * @param content         生成内容
     * @param size            图片宽高大小 ( 正方形 px )
     * @param foregroundColor 二维码图片的前景色
     * @param backgroundColor 二维码图片的背景色
     * @return 二维码图片
     * 该方法是耗时操作, 请在子线程中调用
     */
    fun syncEncodeQRCode(
        content: String,
        size: Int,
        foregroundColor: Int,
        backgroundColor: Int
    ): Bitmap? {
        try {
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
        } catch (e: java.lang.Exception) {
            LogPrintUtils.eTag(TAG, e, "syncEncodeQRCode")
            return null
        }
    }

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
            canvas.drawBitmap(src, 0f, 0f, null)
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
        } catch (e: java.lang.Exception) {
            LogPrintUtils.eTag(TAG, e, "addLogoToQRCode")
            bitmap = null
        }
        return bitmap
    }
}