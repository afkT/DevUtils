package dev.expand.engine.barcode

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
 * @param engine String?
 * @return IBarCodeEngine<EngineConfig, EngineItem, EngineResult>
 * 内部做了处理如果匹配不到则返回默认 BarCode Engine
 */
fun String?.getEngine(): IBarCodeEngine<
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

fun <Config : IBarCodeEngine.EngineConfig> barcode_initialize(
    engine: String? = null,
    config: Config?
) {
    engine.getEngine()?.initialize(config)
}

fun <Config : IBarCodeEngine.EngineConfig> barcode_getConfig(
    engine: String? = null
): Config? {
    return engine.getEngine()?.config as? Config
}

// ==========
// = 生成条码 =
// ==========

fun <Item : IBarCodeEngine.EngineItem> Item.barcode_encodeBarCode(
    engine: String? = null,
    callback: BarCodeEncodeCallback?
) {
    engine.getEngine()?.encodeBarCode(this, callback)
}

@Throws(Exception::class)
fun <Item : IBarCodeEngine.EngineItem> Item.barcode_encodeBarCodeSync(
    engine: String? = null
): Bitmap? {
    return engine.getEngine()?.encodeBarCodeSync(this)
}

// ==========
// = 解析条码 =
// ==========

fun <Result : IBarCodeEngine.EngineResult> Bitmap.barcode_decodeBarCode(
    engine: String? = null,
    callback: BarCodeDecodeCallback<Result>?
) {
    engine.getEngine()?.let {
        (it as? IBarCodeEngine<*, *, Result>)?.decodeBarCode(
            this, callback
        )
    }
}

@Throws(Exception::class)
fun <Result : IBarCodeEngine.EngineResult> Bitmap.barcode_decodeBarCodeSync(
    engine: String? = null
): Result? {
    return engine.getEngine()?.decodeBarCodeSync(this) as? Result
}

// ==========
// = 其他功能 =
// ==========

@Throws(Exception::class)
fun <Item : IBarCodeEngine.EngineItem> Item.barcode_addIconToBarCode(
    engine: String? = null,
    src: Bitmap?,
    icon: Bitmap?
): Bitmap? {
    return engine.getEngine()?.addIconToBarCode(this, src, icon)
}