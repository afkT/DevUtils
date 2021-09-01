package dev.engine.qrcode.listener;

import dev.engine.qrcode.IQRCodeEngine;

/**
 * detail: 二维码解码 ( 解析 ) 回调
 * @author Ttt
 */
public interface QRDecodeCallback<Result extends IQRCodeEngine.EngineResult> {

    /**
     * 二维码解码 ( 解析 ) 回调
     * @param success 是否成功
     * @param result  识别结果
     * @param error   异常信息
     */
    void onResult(
            boolean success,
            Result result,
            Throwable error
    );
}