package dev.engine.storage;

import android.net.Uri;
import android.os.Build;

import java.io.File;

import dev.base.DevSource;
import dev.engine.storage.listener.OnInsertListener;
import dev.utils.app.MediaStoreUtils;
import dev.utils.app.PathUtils;
import dev.utils.app.UriUtils;

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
        if (insidePreCheck(params, source, listener)) {

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
        if (insidePreCheck(params, source, listener)) {

        }
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
        if (insidePreCheck(params, source, listener)) {

        }
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
        if (insidePreCheck(params, source, listener)) {

        }
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
        if (insidePreCheck(params, source, listener)) {

        }
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
        if (insidePreCheck(params, source, listener)) {

        }
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
        if (insidePreCheck(params, source, listener)) {

        }
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
        if (insidePreCheck(params, source, listener)) {

        }
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
        if (insidePreCheck(params, source, listener)) {

        }
    }

    /**
     * 插入一条多媒体资源到内部存储空间
     * <pre>
     *     并不局限于多媒体, 如文本存储、其他文件写入等
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
        if (insidePreCheck(params, source, listener)) {

        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 通用内部预校验
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     * @return {@code true} 校验通过, {@code false} 校验失败
     */
    private boolean insidePreCheck(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        // 判断参数是否有效
        if (params != null && source != null && source.isSource()) {
            return true;
        }
        // 无效数据触发回调
        if (listener != null) {
            listener.onResult(StorageResult.failure(), params, source);
        }
        return false;
    }

    /**
     * 获取输出 Uri ( 存储文件 Uri )
     * @param params   原始参数
     * @param source   原始数据
     * @param external 是否外部存储
     * @param type     存储操作类型
     * @return 输出 Uri
     */
    private Uri getOutputUri(
            final StorageItem params,
            final DevSource source,
            final boolean external,
            final TYPE type
    ) {
        if (params != null && source != null && source.isSource()) {
            if (params.getOutputUri() != null) {
                return params.getOutputUri();
            }
            // 外部存储才需要进行适配
            if (external) {
                switch (type) {
                    case DOWNLOAD: // 存储到 Download 文件夹
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            // 创建 Download Uri
                            return MediaStoreUtils.createDownloadUri(
                                    params.getFileName(),
                                    params.getMimeType(),
                                    params.getFolder()
                            );
                        } else {
                            // 低版本直接创建 SDCard/Download File
                            File file = getOutputFile(params, source, external, type);
                            return UriUtils.fromFile(file);
                        }
                    case IMAGE: // 存储到 Pictures 文件夹
                        // 创建 Image Uri
                        return MediaStoreUtils.createImageUri(
                                params.getFileName(),
                                params.getMimeType(),
                                params.getFolder()
                        );
                }
            } else {
                // 获取存储路径
                File file = getOutputFile(params, source, external, type);
                return UriUtils.fromFile(file);
            }
        }
        return null;
    }

    private File getOutputFile(
            final StorageItem params,
            final DevSource source,
            final boolean external,
            final TYPE type
    ){
        return null;
    }

    // =

    /**
     * 内部插入数据方法
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     */
    private void insideInsertExternal(
            final StorageItem params,
            final DevSource source,
            final OnInsertListener<StorageItem, StorageResult> listener
    ) {
    }

    /**
     * detail: 存储操作类型
     * @author Ttt
     */
    private enum TYPE {
        IMAGE,
        VIDEO,
        AUDIO,
        DOWNLOAD,
        NONE
    }
}