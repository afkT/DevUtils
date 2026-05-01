package dev.engine.core.barcode

import com.google.zxing.DecodeHintType
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import dev.engine.barcode.IBarCodeEngine
import dev.utils.DevFinal
import java.util.*
import kotlin.jvm.JvmField

/**
 * detail: BarCode Config
 * @author Ttt
 */
open class BarCodeConfig : IBarCodeEngine.EngineConfig {

    // 编码 ( 生成 ) 配置（m 前缀避免与 [getEncodeHints] JVM 签名冲突）
    @JvmField
    protected val mEncodeHints: MutableMap<EncodeHintType, Any> = EnumMap(
        EncodeHintType::class.java
    )

    // 解码 ( 解析 ) 配置
    @JvmField
    protected val mDecodeHints: MutableMap<DecodeHintType, Any> = EnumMap(
        DecodeHintType::class.java
    )

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取编码 ( 生成 ) 配置
     * @return 编码 ( 生成 ) 配置
     */
    open fun getEncodeHints(): MutableMap<EncodeHintType, Any> {
        return mEncodeHints
    }

    /**
     * 获取解码 ( 解析 ) 配置
     * @return 解码 ( 解析 ) 配置
     */
    open fun getDecodeHints(): MutableMap<DecodeHintType, Any> {
        return mDecodeHints
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 设置默认编码 ( 生成 ) 配置
     * @return BarCode Config
     */
    open fun defaultEncode(): BarCodeConfig {
        // 编码类型
        mEncodeHints[EncodeHintType.CHARACTER_SET] = DevFinal.ENCODE.UTF_8
        // 指定纠错等级, 纠错级别 ( L 7%、M 15%、Q 25%、H 30% )
        mEncodeHints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
        // 设置二维码边的空度, 非负数
        mEncodeHints[EncodeHintType.MARGIN] = 0
        return this
    }

    /**
     * 设置编码 ( 生成 ) 配置
     * @return BarCode Config
     */
    open fun putEncodeHints(hints: Map<EncodeHintType, Any>?): BarCodeConfig {
        hints?.let { mEncodeHints.putAll(it) }
        return this
    }

    /**
     * 设置解码 ( 解析 ) 配置
     * @return BarCode Config
     */
    open fun putDecodeHints(hints: Map<DecodeHintType, Any>?): BarCodeConfig {
        hints?.let { mDecodeHints.putAll(it) }
        return this
    }
}