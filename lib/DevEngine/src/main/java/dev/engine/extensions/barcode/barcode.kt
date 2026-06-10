package dev.engine.extensions.barcode

import android.graphics.Bitmap
import dev.engine.DevEngine
import dev.engine.barcode.IBarCodeEngine
import dev.engine.barcode.listener.BarCodeDecodeCallback
import dev.engine.barcode.listener.BarCodeEncodeCallback

// ==========================================================
// = IBarCodeEngine<EngineConfig, EngineItem, EngineResult> =
// ==========================================================

/**
 * 通过 Key 获取 BarCode Engine
 * @receiver String?
 * @return IBarCodeEngine<EngineConfig, EngineItem, EngineResult>
 * 内部做了处理如果匹配不到则返回默认 BarCode Engine
 */
fun String?.getBarCodeEngine(): IBarCodeEngine<
        in IBarCodeEngine.EngineConfig,
        in IBarCodeEngine.EngineItem,
        in IBarCodeEngine.EngineResult>? {
    DevEngine.getBarCode(this)?.let { value ->
        return value
    }
    return DevEngine.getBarCode()
}

// ======================
// = Key BarCode Engine =
// ======================

// =============
// = 对外公开方法 =
// =============

/**
 * 初始化方法
 * @param engine String?
 */
fun <Config : IBarCodeEngine.EngineConfig> Config?.barcode_initialize(
    engine: String? = null
) {
    engine.getBarCodeEngine()?.initialize(this)
}

/**
 * 获取 BarCode Engine Config
 * @param engine String?
 * @return BarCode Engine Config
 */
fun barcode_getConfig(
    engine: String? = null
): Any? {
    return engine.getBarCodeEngine()?.config
}

// ==========
// = 生成条码 =
// ==========

/**
 * 编码 ( 生成 ) 条码图片
 * @receiver BarCode ( Data、Params ) Item
 * @param engine String?
 * @param callback 生成结果回调
 */
fun <Item : IBarCodeEngine.EngineItem> Item.barcode_encodeBarCode(
    engine: String? = null,
    callback: BarCodeEncodeCallback?
) {
    engine.getBarCodeEngine()?.encodeBarCode(this, callback)
}

/**
 * 编码 ( 生成 ) 条码图片
 * <pre>
 *     该方法是耗时操作, 请在子线程中调用
 * </pre>
 * @receiver BarCode ( Data、Params ) Item
 * @param engine String?
 * @return 条码图片
 */
@Throws(Exception::class)
fun <Item : IBarCodeEngine.EngineItem> Item.barcode_encodeBarCodeSync(
    engine: String? = null
): Bitmap? {
    return engine.getBarCodeEngine()?.encodeBarCodeSync(this)
}

// ==========
// = 解析条码 =
// ==========

/**
 * 解码 ( 解析 ) 条码图片
 * @receiver 待解析的条码图片
 * @param engine String?
 * @param callback 解析结果回调
 */
fun <Result : IBarCodeEngine.EngineResult> Bitmap.barcode_decodeBarCode(
    engine: String? = null,
    callback: BarCodeDecodeCallback<Result>?
) {
    engine.getBarCodeEngine()?.let {
        (it as? IBarCodeEngine<*, *, Result>)?.decodeBarCode(
            this, callback
        )
    }
}

/**
 * 解码 ( 解析 ) 条码图片
 * <pre>
 *     该方法是耗时操作, 请在子线程中调用
 * </pre>
 * @receiver 待解析的条码图片
 * @param engine String?
 * @return 解析结果
 */
@Throws(Exception::class)
fun Bitmap.barcode_decodeBarCodeSync(
    engine: String? = null
): Any? {
    return engine.getBarCodeEngine()?.decodeBarCodeSync(this)
}

// ==========
// = 其他功能 =
// ==========

/**
 * 添加 Icon 到条码图片上
 * @receiver BarCode ( Data、Params ) Item
 * @param engine String?
 * @param src 条码图片
 * @param icon icon
 * @return 含 icon 条码图片
 */
@Throws(Exception::class)
fun <Item : IBarCodeEngine.EngineItem> Item.barcode_addIconToBarCode(
    engine: String? = null,
    src: Bitmap?,
    icon: Bitmap?
): Bitmap? {
    return engine.getBarCodeEngine()?.addIconToBarCode(this, src, icon)
}