package dev.engine.qrcode.listener;

import android.graphics.Bitmap;

/**
 * detail: 二维码编码 ( 生成 ) 回调
 * @author Ttt
 */
public interface QREncodeCallback {

    /**
     * 二维码编码 ( 生成 ) 回调
     * @param success 是否成功
     * @param bitmap  成功图片
     * @param error   异常信息
     */
    void onResult(
            boolean success,
            Bitmap bitmap,
            Throwable error
    );
}