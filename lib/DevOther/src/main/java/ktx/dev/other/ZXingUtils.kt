package ktx.dev.other

import android.graphics.Bitmap
import dev.engine.barcode.BarCodeConfig
import dev.engine.barcode.BarCodeData
import dev.engine.barcode.BarCodeResult
import dev.engine.barcode.ZXingEngineImpl
import dev.engine.barcode.listener.BarCodeDecodeCallback
import dev.engine.barcode.listener.BarCodeEncodeCallback

/**
 * detail: ZXing 条形码、二维码工具类
 * @author Ttt
 */
@Deprecated("推荐使用 DevBarCodeEngine 实现类 ZXingEngineImpl")
object ZXingUtils {

    private val IMPL = ZXingEngineImpl()

    // =============
    // = 对外公开方法 =
    // =============

    fun initialize(config: BarCodeConfig?) {
        IMPL.initialize(config)
    }

    fun getConfig(): BarCodeConfig? {
        return IMPL.config
    }

    // ==========
    // = 生成条码 =
    // ==========

    fun encodeBarCode(
        params: BarCodeData?,
        callback: BarCodeEncodeCallback?
    ) {
        IMPL.encodeBarCode(params, callback)
    }

    fun encodeBarCodeSync(params: BarCodeData?): Bitmap {
        return IMPL.encodeBarCodeSync(params)
    }

    // ==========
    // = 解析条码 =
    // ==========

    fun decodeBarCode(
        bitmap: Bitmap?,
        callback: BarCodeDecodeCallback<BarCodeResult>?
    ) {
        IMPL.decodeBarCode(bitmap, callback)
    }

    fun decodeBarCodeSync(bitmap: Bitmap?): BarCodeResult {
        return IMPL.decodeBarCodeSync(bitmap)
    }

    // ==========
    // = 其他功能 =
    // ==========

    fun addIconToBarCode(
        params: BarCodeData?,
        src: Bitmap?,
        icon: Bitmap?
    ): Bitmap {
        return IMPL.addIconToBarCode(params, src, icon)
    }
}