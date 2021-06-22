package dev.engine.storage;

import dev.base.DevSource;
import dev.engine.storage.listener.OnInsertListener;

/**
 * detail: DevUtils MediaStore Engine 实现
 * @author Ttt
 */
public class DevMediaStoreEngineImpl
        implements IStorageEngine<StorageItem> {

    // ==========
    // = 外部存储 =
    // ==========

    @Override
    public boolean insertImageToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem> listener
    ) {
        return false;
    }

    @Override
    public boolean insertVideoToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem> listener
    ) {
        return false;
    }

    @Override
    public boolean insertAudioToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem> listener
    ) {
        return false;
    }

    @Override
    public boolean insertDownloadToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem> listener
    ) {
        return false;
    }

    @Override
    public boolean insertMediaToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem> listener
    ) {
        return false;
    }

    // ==========
    // = 内部存储 =
    // ==========

    @Override
    public boolean insertImageToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem> listener
    ) {
        return false;
    }

    @Override
    public boolean insertVideoToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem> listener
    ) {
        return false;
    }

    @Override
    public boolean insertAudioToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem> listener
    ) {
        return false;
    }

    @Override
    public boolean insertDownloadToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem> listener
    ) {
        return false;
    }

    @Override
    public boolean insertMediaToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem> listener
    ) {
        return false;
    }
}