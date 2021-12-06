package java.dev.engine.barcode;

import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.EnumMap;
import java.util.Map;

import dev.engine.barcode.IBarCodeEngine;
import dev.utils.DevFinal;

/**
 * detail: BarCode Config
 * @author Ttt
 */
public class BarCodeConfig
        extends IBarCodeEngine.EngineConfig {

    // 编码 ( 生成 ) 配置
    private final Map<EncodeHintType, Object> encodeHints = new EnumMap<>(EncodeHintType.class);
    // 解码 ( 解析 ) 配置
    private final Map<DecodeHintType, Object> decodeHints = new EnumMap<>(DecodeHintType.class);

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取编码 ( 生成 ) 配置
     * @return 编码 ( 生成 ) 配置
     */
    public Map<EncodeHintType, Object> getEncodeHints() {
        return encodeHints;
    }

    /**
     * 获取解码 ( 解析 ) 配置
     * @return 解码 ( 解析 ) 配置
     */
    public Map<DecodeHintType, Object> getDecodeHints() {
        return decodeHints;
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 设置默认编码 ( 生成 ) 配置
     * @return BarCode Config
     */
    public BarCodeConfig defaultEncode() {
        // 编码类型
        encodeHints.put(EncodeHintType.CHARACTER_SET, DevFinal.ENCODE.UTF_8);
        // 指定纠错等级, 纠错级别 ( L 7%、M 15%、Q 25%、H 30% )
        encodeHints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置二维码边的空度, 非负数
        encodeHints.put(EncodeHintType.MARGIN, 0);
        return this;
    }

    /**
     * 设置编码 ( 生成 ) 配置
     * @return BarCode Config
     */
    public BarCodeConfig putEncodeHints(Map<EncodeHintType, Object> hints) {
        if (hints != null) encodeHints.putAll(hints);
        return this;
    }

    /**
     * 设置解码 ( 解析 ) 配置
     * @return BarCode Config
     */
    public BarCodeConfig putDecodeHints(Map<DecodeHintType, Object> hints) {
        if (hints != null) decodeHints.putAll(hints);
        return this;
    }
}