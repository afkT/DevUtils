package dev.engine.image.listener;

import android.content.Context;

import java.io.File;

import dev.base.DevSource;
import dev.engine.image.IImageEngine;

/**
 * detail: 转换图片格式存储接口
 * @author Ttt
 */
public interface ConvertStorage<Config extends IImageEngine.EngineConfig> {

    /**
     * 转换图片格式并存储
     * @param context {@link Context}
     * @param source  当前待转换资源
     * @param config  配置信息
     * @param index   当前转换索引
     * @param count   转换总数
     * @return 存储路径
     */
    File convert(
            Context context,
            DevSource source,
            Config config,
            int index,
            int count
    )
            throws Exception;
}