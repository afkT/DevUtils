package ktx.dev.engine.storage;

import java.lang.reflect.Type;

import dev.engine.json.DevJSONEngine;
import dev.engine.storage.IStorageEngine;
import dev.utils.app.share.IPreference;
import dev.utils.common.ConvertUtils;

/**
 * detail: SharedPreferences Storage Engine 实现
 * @author Ttt
 */
public class SPStorageEngineImpl
        implements IStorageEngine<SPConfig> {

    private final SPConfig    mConfig;
    // SharedPreferences
    private final IPreference mPreference;

    public SPStorageEngineImpl(SPConfig config) {
        this.mConfig = config;
        // SharedPreferences
        mPreference = config.getPreference();
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    @Override
    public SPConfig getConfig() {
        return mConfig;
    }

    @Override
    public void remove(String key) {
        mPreference.remove(key);
    }

    @Override
    public void removeForKeys(String[] keys) {
        mPreference.removeAll(keys);
    }

    @Override
    public boolean contains(String key) {
        return mPreference.contains(key);
    }

    @Override
    public void clear() {
        mPreference.clear();
    }

    // =======
    // = 存储 =
    // =======

    @Override
    public boolean putInt(
            String key,
            int value
    ) {
        mPreference.put(key, value);
        return true;
    }

    @Override
    public boolean putLong(
            String key,
            long value
    ) {
        mPreference.put(key, value);
        return true;
    }

    @Override
    public boolean putFloat(
            String key,
            float value
    ) {
        mPreference.put(key, value);
        return true;
    }

    @Override
    public boolean putDouble(
            String key,
            double value
    ) {
        mPreference.put(key, value);
        return true;
    }

    @Override
    public boolean putBoolean(
            String key,
            boolean value
    ) {
        mPreference.put(key, value);
        return true;
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
        mPreference.put(key, content);
        return true;
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
        return mPreference.getInt(key, defaultValue);
    }

    @Override
    public long getLong(
            String key,
            long defaultValue
    ) {
        return mPreference.getLong(key, defaultValue);
    }

    @Override
    public float getFloat(
            String key,
            float defaultValue
    ) {
        return mPreference.getFloat(key, defaultValue);
    }

    @Override
    public double getDouble(
            String key,
            double defaultValue
    ) {
        return mPreference.getDouble(key, defaultValue);
    }

    @Override
    public boolean getBoolean(
            String key,
            boolean defaultValue
    ) {
        return mPreference.getBoolean(key, defaultValue);
    }

    @Override
    public String getString(
            String key,
            String defaultValue
    ) {
        String content = mPreference.getString(key, null);
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