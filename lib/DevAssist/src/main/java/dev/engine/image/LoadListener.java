package dev.engine.image;

import dev.base.Source;

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
    Class getTranscodeType();

    /**
     * 开始加载
     * @param source {@link Source}
     */
    void onStart(Source source);

    /**
     * 响应回调
     * @param source {@link Source}
     * @param value  {@link TranscodeType}
     */
    void onResponse(
            Source source,
            TranscodeType value
    );

    /**
     * 失败回调
     * @param source    {@link Source}
     * @param throwable {@link Throwable}
     */
    void onFailure(
            Source source,
            Throwable throwable
    );
}