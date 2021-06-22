package dev.engine.storage.listener;

import android.net.Uri;

import java.io.File;

import dev.base.DevSource;
import dev.engine.storage.IStorageEngine;

/**
 * detail: 插入多媒体资源事件
 * @author Ttt
 */
public interface OnInsertListener<Item extends IStorageEngine.EngineItem> {

    /**
     * 插入多媒体资源结果方法
     * @param file   保存地址
     * @param uri    保存 Uri 地址
     * @param e      失败、异常原因
     * @param params 原始参数
     * @param source 原始数据
     */
    void onResult(
            File file,
            Uri uri,
            Exception e,
            Item params,
            DevSource source
    );
}