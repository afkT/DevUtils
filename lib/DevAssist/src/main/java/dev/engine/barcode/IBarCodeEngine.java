package dev.engine.barcode;

import android.graphics.Bitmap;

import dev.engine.barcode.listener.BarCodeDecodeCallback;
import dev.engine.barcode.listener.BarCodeEncodeCallback;

/**
 * detail: BarCode Engine 接口
 * @author Ttt
 */
public interface IBarCodeEngine<Config extends IBarCodeEngine.EngineConfig,
        Item extends IBarCodeEngine.EngineItem,
        Result extends IBarCodeEngine.EngineResult> {

    /**
     * detail: BarCode Config
     * @author Ttt
     */
    class EngineConfig {
    }

    /**
     * detail: BarCode ( Data、Params ) Item
     * @author Ttt
     */
    class EngineItem {
    }

    /**
     * detail: BarCode Result
     * @author Ttt
     */
    class EngineResult {
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 初始化方法
     * @param config BarCode Config
     */
    void initialize(Config config);

    /**
     * 获取 BarCode Engine Config
     * @return BarCode Engine Config
     */
    Config getConfig();

    // ==========
    // = 生成条码 =
    // ==========

    /**
     * 编码 ( 生成 ) 条码图片
     * @param params   BarCode ( Data、Params ) Item
     * @param callback 生成结果回调
     */
    void encodeBarCode(
            Item params,
            BarCodeEncodeCallback callback
    );

    // =

    /**
     * 编码 ( 生成 ) 条码图片
     * <pre>
     *     该方法是耗时操作, 请在子线程中调用
     * </pre>
     * @param params BarCode ( Data、Params ) Item
     * @return 条码图片
     */
    Bitmap encodeBarCodeSync(Item params);

    // ==========
    // = 解析条码 =
    // ==========

    /**
     * 解码 ( 解析 ) 条码图片
     * @param bitmap   待解析的条码图片
     * @param callback 解析结果回调
     */
    void decodeBarCode(
            Bitmap bitmap,
            BarCodeDecodeCallback<Result> callback
    );

    /**
     * 解码 ( 解析 ) 条码图片
     * <pre>
     *     该方法是耗时操作, 请在子线程中调用
     * </pre>
     * @param bitmap 待解析的条码图片
     * @return 解析结果
     */
    Result decodeBarCodeSync(Bitmap bitmap);

    // ==========
    // = 其他功能 =
    // ==========

    /**
     * 添加 Logo 到条码图片上
     * @param params BarCode ( Data、Params ) Item
     * @param src    条码图片
     * @param logo   Logo
     * @return 含 Logo 条码图片
     */
    Bitmap addLogoToBarCode(
            Item params,
            Bitmap src,
            Bitmap logo
    );
}