package dev.engine.keyvalue;

import java.lang.reflect.Type;

import dev.engine.json.DevJSONEngine;
import dev.other.MMKVUtils;
import dev.utils.common.ConvertUtils;

/**
 * detail: MMKV Key-Value Engine 实现
 * @author Ttt
 */
public class MMKVKeyValueEngineImpl
        implements IKeyValueEngine<MMKVConfig> {

    private final MMKVConfig       mConfig;
    // MMKV
    private final MMKVUtils.Holder mHolder;

    public MMKVKeyValueEngineImpl(MMKVConfig config) {
        this.mConfig = config;
        // MMKV Holder
        mHolder = MMKVUtils.putHolder(config.getMMKV().mmapID(), config.getMMKV());
    }

    // =============
    // = 对外公开方法 =
    // =============

    @Override
    public MMKVConfig getConfig() {
        return mConfig;
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
    public boolean putLong(
            String key,
            long value
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
    public boolean putDouble(
            String key,
            double value
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
        if (value != null && mConfig.cipher != null) {
            byte[] bytes = mConfig.cipher.encrypt(ConvertUtils.toBytes(value));
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
        return getInt(key, 0);
    }

    @Override
    public long getLong(String key) {
        return getLong(key, 0L);
    }

    @Override
    public float getFloat(String key) {
        return getFloat(key, 0F);
    }

    @Override
    public double getDouble(String key) {
        return getDouble(key, 0D);
    }

    @Override
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    @Override
    public String getString(String key) {
        return getString(key, null);
    }

    @Override
    public <T> T getEntity(
            String key,
            Type typeOfT
    ) {
        return getEntity(key, typeOfT, null);
    }

    // =

    @Override
    public int getInt(
            String key,
            int defaultValue
    ) {
        return mHolder.decodeInt(key, defaultValue);
    }

    @Override
    public long getLong(
            String key,
            long defaultValue
    ) {
        return mHolder.decodeLong(key, defaultValue);
    }

    @Override
    public float getFloat(
            String key,
            float defaultValue
    ) {
        return mHolder.decodeFloat(key, defaultValue);
    }

    @Override
    public double getDouble(
            String key,
            double defaultValue
    ) {
        return mHolder.decodeDouble(key, defaultValue);
    }

    @Override
    public boolean getBoolean(
            String key,
            boolean defaultValue
    ) {
        return mHolder.decodeBool(key, defaultValue);
    }

    @Override
    public String getString(
            String key,
            String defaultValue
    ) {
        String content = mHolder.decodeString(key, null);
        if (content == null) return defaultValue;
        if (content != null && mConfig.cipher != null) {
            byte[] bytes = mConfig.cipher.decrypt(ConvertUtils.toBytes(content));
            content = ConvertUtils.newString(bytes);
        }
        return content;
    }

    @Override
    public <T> T getEntity(
            String key,
            Type typeOfT,
            T defaultValue
    ) {
        String json   = getString(key, null);
        T      object = (T) DevJSONEngine.getEngine().fromJson(json, typeOfT);
        if (object == null) return defaultValue;
        return object;
    }
}