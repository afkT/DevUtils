package dev.engine.qrcode;

import android.graphics.Bitmap;

import dev.engine.qrcode.listener.QRDecodeCallback;
import dev.engine.qrcode.listener.QREncodeCallback;

/**
 * detail: QRCode Engine 接口
 * @author Ttt
 */
public interface IQRCodeEngine<Config extends IQRCodeEngine.EngineConfig, Result extends IQRCodeEngine.EngineResult> {

    /**
     * detail: QRCode Config
     * @author Ttt
     */
    class EngineConfig {
    }

    /**
     * detail: QRCode Result
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
    void encodeQRCode(
            String content,
            int size
    );

    /**
     * 编码 ( 生成 ) 二维码图片
     * @param content  生成内容
     * @param size     图片宽高大小 ( 正方形 px )
     * @param callback 生成结果回调
     */
    void encodeQRCode(
            String content,
            int size,
            QREncodeCallback callback
    );

    /**
     * 编码 ( 生成 ) 二维码图片
     * @param content  生成内容
     * @param size     图片宽高大小 ( 正方形 px )
     * @param logo     中间 Logo
     * @param callback 生成结果回调
     */
    void encodeQRCode(
            String content,
            int size,
            Bitmap logo,
            QREncodeCallback callback
    );

    // =

    /**
     * 编码 ( 生成 ) 二维码图片
     * @param content 生成内容
     * @param size    图片宽高大小 ( 正方形 px )
     * @return 二维码图片
     */
    Bitmap encodeQRCodeSync(
            String content,
            int size
    );

    /**
     * 编码 ( 生成 ) 二维码图片
     * @param content 生成内容
     * @param size    图片宽高大小 ( 正方形 px )
     * @param logo    中间 Logo
     * @return 二维码图片
     */
    Bitmap encodeQRCodeSync(
            String content,
            int size,
            Bitmap logo
    );

    /**
     * 编码 ( 生成 ) 二维码图片
     * <pre>
     *     该方法是耗时操作, 请在子线程中调用
     * </pre>
     * @param content         生成内容
     * @param size            图片宽高大小 ( 正方形 px )
     * @param logo            中间 Logo
     * @param foregroundColor 二维码图片的前景色
     * @param backgroundColor 二维码图片的背景色
     * @return 二维码图片
     */
    Bitmap encodeQRCodeSync(
            String content,
            int size,
            Bitmap logo,
            int foregroundColor,
            int backgroundColor
    );

    // ============
    // = 解析二维码 =
    // ============

    // =

    /**
     * 解码 ( 解析 ) 二维码图片
     * @param bitmap   待解析的二维码图片
     * @param callback 解析结果回调
     */
    void decodeQRCode(
            Bitmap bitmap,
            QRDecodeCallback<Result> callback
    );

    /**
     * 解码 ( 解析 ) 二维码图片
     * @param bitmap 待解析的二维码图片
     * @return 解析结果
     */
    Result decodeQRCodeSync(Bitmap bitmap);

    // ==========
    // = 其他功能 =
    // ==========

    /**
     * 添加 Logo 到二维码图片上
     * @param src  二维码图片
     * @param logo Logo
     * @return 含 Logo 二维码图片
     */
    Bitmap addLogoToQRCode(
            Bitmap src,
            Bitmap logo
    );

    /**
     * 获取扫描结果数据
     * @param result 识别数据
     * @return 扫描结果数据
     */
    String getResultData(Result result);
}