package dev.engine.qrcode;

import android.graphics.Bitmap;

import dev.engine.qrcode.listener.QREncodeCallback;

/**
 * detail: QRCode Engine 接口
 * @author Ttt
 */
public interface IQRCodeEngine<Config extends IQRCodeEngine.EngineConfig> {

    /**
     * detail: QRCode Config
     * @author Ttt
     */
    class EngineConfig {
    }

    /**
     * detail: Storage Result
     * @author Ttt
     */
    class EngineResult {
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 初始化方法
     * @param config QRCode Config
     */
    void initialize(Config config);

    // ============
    // = 生成二维码 =
    // ============

    /**
     * 编码 ( 生成 ) 二维码图片
     * @param content 生成内容
     * @param size    图片宽高大小 ( 正方形 px )
     */
    void decodeQRCode(
            final String content,
            final int size
    );

    /**
     * 编码 ( 生成 ) 二维码图片
     * @param content  生成内容
     * @param size     图片宽高大小 ( 正方形 px )
     * @param callback 生成结果回调
     */
    void decodeQRCode(
            final String content,
            final int size,
            final QREncodeCallback callback
    );

    /**
     * 编码 ( 生成 ) 二维码图片
     * @param content  生成内容
     * @param size     图片宽高大小 ( 正方形 px )
     * @param logo     中间 Logo
     * @param callback 生成结果回调
     */
    void decodeQRCode(
            final String content,
            final int size,
            final Bitmap logo,
            final QREncodeCallback callback
    );

    // ============
    // = 解析二维码 =
    // ============
}