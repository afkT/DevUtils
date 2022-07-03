package dev.engine.barcode

import android.graphics.Bitmap
import android.graphics.Canvas
import android.text.TextUtils
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.MultiFormatWriter
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import dev.engine.barcode.listener.BarCodeDecodeCallback
import dev.engine.barcode.listener.BarCodeEncodeCallback
import dev.utils.LogPrintUtils
import dev.utils.common.thread.DevThreadPool

/**
 * detail: ZXing BarCode Engine 实现
 * @author Ttt
 */
class ZXingEngineImpl(threadNumber: Int = 6) : IBarCodeEngine<BarCodeConfig, BarCodeData, BarCodeResult> {

    // 日志 TAG
    private val TAG = ZXingEngineImpl::class.java.simpleName

    // 线程池 ( 构建类 )
    private val DEV_THREAD_POOL = DevThreadPool(threadNumber)

    // 默认条码配置
    private val DEFAULT_CONFIG = BarCodeConfig().defaultEncode()

    // 当前条码配置
    private var mBarCodeConfig: BarCodeConfig? = null

    // =============
    // = 对外公开方法 =
    // =============

    override fun initialize(config: BarCodeConfig?) {
        mBarCodeConfig = config
    }

    override fun getConfig(): BarCodeConfig? {
        return mBarCodeConfig
    }

    // ==========
    // = 生成条码 =
    // ==========

    override fun encodeBarCode(
        params: BarCodeData?,
        callback: BarCodeEncodeCallback?
    ) {
        val error = isValidData(params)
        if (error != null) {
            encodeFailureCallback(callback, error)
            return
        }
        DEV_THREAD_POOL.execute {
            try {
                var bitmap = encodeBarCodeSync(params)
                // 条码嵌入 icon、logo
                if (params?.getIcon() != null) {
                    bitmap = addIconToBarCode(params, bitmap, params.getIcon())
                }
                encodeCallback(callback, bitmap)
            } catch (e: Exception) {
                LogPrintUtils.eTag(TAG, e, "encodeBarCode")
                // 触发回调
                encodeFailureCallback(callback, e)
            }
        }
    }

    override fun encodeBarCodeSync(params: BarCodeData?): Bitmap {
        val error = isValidData(params)
        if (error == null && params != null) {
            val config = getInnerConfig()
            // 条码宽高
            val width = params.getWidth()
            val height = params.getHeight()
            // 创建条码矩阵
            val matrix = MultiFormatWriter().encode(
                params.getContent(), params.getFormat(),
                width, height, config.getEncodeHints()
            )
            val pixels = IntArray(width * height)
            for (y in 0 until height) {
                for (x in 0 until width) {
                    if (matrix[x, y]) {
                        pixels[y * width + x] = params.getForegroundColor()
                    } else {
                        pixels[y * width + x] = params.getBackgroundColor()
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            return bitmap
        }
        throw error!!
    }

    // ==========
    // = 解析条码 =
    // ==========

    override fun decodeBarCode(
        bitmap: Bitmap?,
        callback: BarCodeDecodeCallback<BarCodeResult>?
    ) {
        if (bitmap == null) {
            decodeFailureCallback(callback, Exception("BarCode decode Bitmap is null"))
            return
        }
        DEV_THREAD_POOL.execute {
            try {
                val result = decodeBarCodeSync(bitmap)
                // 触发回调
                decodeCallback(callback, result)
            } catch (e: Exception) {
                LogPrintUtils.eTag(TAG, e, "decodeBarCode")
                // 触发回调
                decodeFailureCallback(callback, e)
            }
        }
    }

    override fun decodeBarCodeSync(bitmap: Bitmap?): BarCodeResult {
        if (bitmap != null) {
            val config = getInnerConfig()
            // 解码处理
            val width = bitmap.width
            val height = bitmap.height
            val pixels = IntArray(width * height)
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
            val source = RGBLuminanceSource(width, height, pixels)
            val result = MultiFormatReader().decode(
                BinaryBitmap(HybridBinarizer(source)),
                config.getDecodeHints()
            )
            return BarCodeResult(result)
        }
        throw Exception("BarCode decode Bitmap is null")
    }

    // ==========
    // = 其他功能 =
    // ==========

    /**
     * 添加 Icon 到条码图片上
     * @param params BarCode ( Data、Params ) Item
     * @param src    条码图片
     * @param icon   icon
     * @return 含 icon 条码图片
     * 目前就处理了 二维码图片 其他需要根据需求自行添加
     */
    override fun addIconToBarCode(
        params: BarCodeData?,
        src: Bitmap?,
        icon: Bitmap?
    ): Bitmap {
        if (params == null) {
            throw Exception("BarCode ( Data、Params ) Item is null")
        }
        if (params.getIconScale() <= 0) {
            throw Exception("BarCode iconScale Less than or equal to 0")
        }
        if (src == null) {
            throw Exception("addIconToBarCode src Bitmap is null")
        }
        if (icon == null) {
            throw Exception("addIconToBarCode icon Bitmap is null")
        }
        // 获取图片宽度高度
        val srcWidth = src.width.toFloat()
        val srcHeight = src.height.toFloat()
        val iconWidth = icon.width.toFloat()
        val iconHeight = icon.height.toFloat()

        // 这里决定 icon 在图片的比例 ( 可自行判断 BarCodeData Format 决定绘制大小比例 )
        val scaleFactor = srcWidth / params.getIconScale() / iconWidth
        val bitmap = Bitmap.createBitmap(
            srcWidth.toInt(), srcHeight.toInt(),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        canvas.drawBitmap(src, 0F, 0F, null)
        canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2)
        canvas.drawBitmap(
            icon, (srcWidth - iconWidth) / 2,
            (srcHeight - iconHeight) / 2, null
        )
        canvas.save()
        canvas.restore()
        if (bitmap != null) return bitmap
        throw Exception("addIconToBarCode failure")
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 获取 BarCode Config
     * @return BarCode Config
     */
    private fun getInnerConfig(): BarCodeConfig {
        return mBarCodeConfig ?: DEFAULT_CONFIG
    }

    /**
     * 判断是否有效数据
     * @param params BarCode ( Data、Params ) Item
     * @return 如果属于有效数据则返回 null
     */
    private fun isValidData(params: BarCodeData?): Exception? {
        if (params == null) {
            return Exception("BarCode ( Data、Params ) Item is null")
        }
        if (TextUtils.isEmpty(params.getContent())) {
            return Exception("BarCode content is null")
        }
        if (params.getWidth() <= 0 || params.getHeight() <= 0) {
            return Exception("BarCode width、height Less than or equal to 0")
        }
        return null
    }

    // =

    /**
     * 编码 ( 生成 ) 回调
     * @param callback 生成结果回调
     * @param bitmap   条码图片
     */
    private fun encodeCallback(
        callback: BarCodeEncodeCallback?,
        bitmap: Bitmap?
    ) {
        if (callback != null) {
            if (bitmap != null) {
                callback.onResult(true, bitmap, null)
            } else {
                callback.onResult(
                    false, null,
                    Exception("BarCode encode Bitmap is null")
                )
            }
        }
    }

    /**
     * 编码 ( 生成 ) 失败回调
     * @param callback 生成结果回调
     * @param error    异常信息
     */
    private fun encodeFailureCallback(
        callback: BarCodeEncodeCallback?,
        error: Throwable
    ) {
        callback?.onResult(false, null, error)
    }

    // =

    /**
     * 解码 ( 解析 ) 回调
     * @param callback 生成结果回调
     * @param result   识别结果
     */
    private fun decodeCallback(
        callback: BarCodeDecodeCallback<BarCodeResult>?,
        result: BarCodeResult?
    ) {
        if (callback != null) {
            if (result != null) {
                callback.onResult(true, result, null)
            } else {
                callback.onResult(
                    false, null,
                    Exception("BarCode decode Result is null")
                )
            }
        }
    }

    /**
     * 解码 ( 解析 ) 失败回调
     * @param callback 生成结果回调
     * @param error    异常信息
     */
    private fun decodeFailureCallback(
        callback: BarCodeDecodeCallback<BarCodeResult>?,
        error: Throwable
    ) {
        callback?.onResult(false, null, error)
    }
}