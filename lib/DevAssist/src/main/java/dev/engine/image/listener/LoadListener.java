package dev.engine.image.listener;

import dev.base.DevSource;

/**
 * detail: 图片加载事件
 * @param <TranscodeType> 泛型
 * @author Ttt
 */
public interface LoadListener<TranscodeType> {

    /**
     * 获取转码类型
     * @return {@link TranscodeType#getClass()}
     */
    Class<?> getTranscodeType();

    /**
     * 开始加载
     * @param source 数据来源
     */
    void onStart(DevSource source);

    /**
     * 响应回调
     * @param source 数据来源
     * @param value  结果数据
     */
    void onResponse(
            DevSource source,
            TranscodeType value
    );

    /**
     * 失败回调
     * @param source    数据来源
     * @param throwable {@link Throwable}
     */
    void onFailure(
            DevSource source,
            Throwable throwable
    );
}