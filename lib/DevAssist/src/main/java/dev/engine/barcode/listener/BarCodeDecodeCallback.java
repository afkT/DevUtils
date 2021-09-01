package dev.engine.barcode.listener;

import dev.engine.barcode.IBarCodeEngine;

/**
 * detail: 条码解码 ( 解析 ) 回调
 * @author Ttt
 */
public interface BarCodeDecodeCallback<Result extends IBarCodeEngine.EngineResult> {

    /**
     * 条码解码 ( 解析 ) 回调
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