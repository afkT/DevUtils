package dev.engine.storage;

import android.net.Uri;

import dev.base.DevSource;
import dev.engine.storage.listener.OnInsertListener;

/**
 * detail: DevUtils MediaStore Engine 实现
 * @author Ttt
 */
public class DevMediaStoreEngineImpl
        implements IStorageEngine<StorageItem, StorageResult> {

    // ==========
    // = 外部存储 =
    // ==========

    /**
     * 插入一张图片到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertImageToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (params != null && source != null) {

        }
    }

    /**
     * 插入一条视频到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertVideoToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
    }

    /**
     * 插入一条音频到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertAudioToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
    }

    /**
     * 插入一条文件资源到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertDownloadToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
    }

    /**
     * 插入一条多媒体资源到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertMediaToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
    }

    // ==========
    // = 内部存储 =
    // ==========

    /**
     * 插入一张图片到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertImageToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
    }

    /**
     * 插入一条视频到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertVideoToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
    }

    /**
     * 插入一条音频到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertAudioToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
    }

    /**
     * 插入一条文件资源到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertDownloadToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
    }

    /**
     * 插入一条多媒体资源到内部存储空间
     * <pre>
     *     并不局限于多媒体, 如保存文本文件、写入文件等
     * </pre>
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertMediaToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 内部插入数据方法
     * @param uri
     * @param params   原始参数
     * @param source   原始数据
     * @param listener
     */
    private void priInsertExternal(
            Uri uri,
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
    }
}