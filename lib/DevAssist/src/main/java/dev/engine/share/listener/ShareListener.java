package dev.engine.share.listener;

import dev.engine.share.IShareEngine;

/**
 * detail: 分享回调
 * @author Ttt
 */
public interface ShareListener<Item extends IShareEngine.EngineItem> {

    /**
     * 开始分享
     * @param params {@link IShareEngine.EngineItem}
     */
    void onStart(Item params);

    /**
     * 分享成功
     * @param params {@link IShareEngine.EngineItem}
     */
    void onResult(Item params);

    /**
     * 分享失败
     * @param params    {@link IShareEngine.EngineItem}
     * @param throwable {@link Throwable}
     */
    void onError(
            Item params,
            Throwable throwable
    );

    /**
     * 取消分享
     * @param params {@link IShareEngine.EngineItem}
     */
    void onCancel(Item params);
}