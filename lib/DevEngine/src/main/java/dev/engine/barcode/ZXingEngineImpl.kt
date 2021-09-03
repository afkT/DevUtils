package dev.engine.barcode

import android.graphics.Bitmap
import android.graphics.Canvas
import dev.engine.barcode.listener.BarCodeDecodeCallback
import dev.engine.barcode.listener.BarCodeEncodeCallback
import dev.utils.common.StringUtils
import dev.utils.common.thread.DevThreadPool

/**
 * detail: ZXing BarCode Engine 实现
 * @author Ttt
 */
class ZXingEngineImpl : IBarCodeEngine<BarCodeConfig, BarCodeData, BarCodeResult> {

    // 日志 TAG
    private val TAG = ZXingEngineImpl::class.java.simpleName

    // 线程池 ( 构建类 )
    private val DEV_THREAD_POOL = DevThreadPool(6)

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
        TODO("Not yet implemented")
    }

    override fun encodeBarCodeSync(params: BarCodeData?): Bitmap {
        TODO("Not yet implemented")
    }

    // ==========
    // = 解析条码 =
    // ==========

    override fun decodeBarCode(
        bitmap: Bitmap?,
        callback: BarCodeDecodeCallback<BarCodeResult>?
    ) {
        TODO("Not yet implemented")
    }

    override fun decodeBarCodeSync(bitmap: Bitmap?): BarCodeResult {
        TODO("Not yet implemented")
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
        if (params.getFormat() == null) {
            throw Exception("BarCode format is null")
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
        canvas.drawBitmap(src, 0f, 0f, null)
        canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2)
        canvas.drawBitmap(
            icon, (srcWidth - iconWidth) / 2,
            (srcHeight - iconHeight) / 2, null
        )
        canvas.save()
        canvas.restore()
        if (bitmap != null) return bitmap
        throw java.lang.Exception("addIconToBarCode failure")
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 获取 BarCode Config
     * @return BarCode Config
     */
    private fun getInnerConfig(): BarCodeConfig? {
        return if (mBarCodeConfig != null) mBarCodeConfig else DEFAULT_CONFIG
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
        if (StringUtils.isEmpty(params.getContent())) {
            return Exception("BarCode content is null")
        }
        if (params.getWidth() <= 0 || params.getHeight() <= 0) {
            return Exception("BarCode width、height Less than or equal to 0")
        }
        if (params.getFormat() == null) {
            return Exception("BarCode format is null")
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
        callback: BarCodeDecodeCallback<BarCodeResult?>?,
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
        callback: BarCodeDecodeCallback<BarCodeResult?>?,
        error: Throwable
    ) {
        callback?.onResult(false, null, error)
    }
}