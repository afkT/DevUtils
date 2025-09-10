package dev.engine.core.barcode

import com.google.zxing.DecodeHintType
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import dev.engine.barcode.IBarCodeEngine
import dev.utils.DevFinal
import java.util.*

/**
 * detail: BarCode Config
 * @author Ttt
 */
open class BarCodeConfig : IBarCodeEngine.EngineConfig {

    // 编码 ( 生成 ) 配置
    private val encodeHints: MutableMap<EncodeHintType, Any> = EnumMap(
        EncodeHintType::class.java
    )

    // 解码 ( 解析 ) 配置
    private val decodeHints: MutableMap<DecodeHintType, Any> = EnumMap(
        DecodeHintType::class.java
    )

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取编码 ( 生成 ) 配置
     * @return 编码 ( 生成 ) 配置
     */
    fun getEncodeHints(): MutableMap<EncodeHintType, Any> {
        return encodeHints
    }

    /**
     * 获取解码 ( 解析 ) 配置
     * @return 解码 ( 解析 ) 配置
     */
    fun getDecodeHints(): MutableMap<DecodeHintType, Any> {
        return decodeHints
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 设置默认编码 ( 生成 ) 配置
     * @return BarCode Config
     */
    fun defaultEncode(): BarCodeConfig {
        // 编码类型
        encodeHints[EncodeHintType.CHARACTER_SET] = DevFinal.ENCODE.UTF_8
        // 指定纠错等级, 纠错级别 ( L 7%、M 15%、Q 25%、H 30% )
        encodeHints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
        // 设置二维码边的空度, 非负数
        encodeHints[EncodeHintType.MARGIN] = 0
        return this
    }

    /**
     * 设置编码 ( 生成 ) 配置
     * @return BarCode Config
     */
    fun putEncodeHints(hints: Map<EncodeHintType, Any>?): BarCodeConfig {
        hints?.let { encodeHints.putAll(it) }
        return this
    }

    /**
     * 设置解码 ( 解析 ) 配置
     * @return BarCode Config
     */
    fun putDecodeHints(hints: Map<DecodeHintType, Any>?): BarCodeConfig {
        hints?.let { decodeHints.putAll(it) }
        return this
    }
}