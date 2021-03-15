package dev.engine.storage;

import java.lang.reflect.Type;

import dev.engine.json.DevJSONEngine;
import dev.other.MMKVUtils;
import dev.utils.common.ConvertUtils;

/**
 * detail: MMKV Storage Engine 实现
 * @author Ttt
 */
public class MMKVStorageEngineImpl
        implements IStorageEngine<StorageConfig> {

    private final StorageConfig    mStorage;
    // MMKV
    private final MMKVUtils.Holder mHolder;

    public MMKVStorageEngineImpl(StorageConfig storage) {
        this.mStorage = storage;
        // MMKV Holder
        mHolder = MMKVUtils.putHolder(storage.storageID, storage.getMMKV());
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    @Override
    public StorageConfig getConfig() {
        return mStorage;
    }

    @Override
    public void remove(String key) {
        mHolder.removeValueForKey(key);
    }

    @Override
    public void removeForKeys(String[] keys) {
        mHolder.removeValuesForKeys(keys);
    }

    @Override
    public boolean contains(String key) {
        return mHolder.containsKey(key);
    }

    @Override
    public void clear() {
        mHolder.clear();
    }

    // =======
    // = 存储 =
    // =======

    @Override
    public boolean putInt(
            String key,
            int value
    ) {
        return mHolder.encode(key, value);
    }

    @Override
    public boolean putFloat(
            String key,
            float value
    ) {
        return mHolder.encode(key, value);
    }

    @Override
    public boolean putLong(
            String key,
            long value
    ) {
        return mHolder.encode(key, value);
    }

    @Override
    public boolean putBoolean(
            String key,
            boolean value
    ) {
        return mHolder.encode(key, value);
    }

    @Override
    public boolean putString(
            String key,
            String value
    ) {
        String content = value;
        if (value != null && mStorage.cipher != null) {
            byte[] bytes = mStorage.cipher.encrypt(ConvertUtils.toBytes(value));
            content = ConvertUtils.newString(bytes);
        }
        return mHolder.encode(key, content);
    }

    @Override
    public <T> boolean putEntity(
            String key,
            T value
    ) {
        return putString(key, DevJSONEngine.getEngine().toJson(value));
    }

    // =======
    // = 获取 =
    // =======

    @Override
    public int getInt(String key) {
        return mHolder.decodeInt(key);
    }

    @Override
    public float getFloat(String key) {
        return 0;
    }

    @Override
    public long getLong(String key) {
        return 0;
    }

    @Override
    public boolean getBoolean(String key) {
        return false;
    }

    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public <T> T getEntity(
            String key,
            Type typeOfT
    ) {
        return null;
    }
}