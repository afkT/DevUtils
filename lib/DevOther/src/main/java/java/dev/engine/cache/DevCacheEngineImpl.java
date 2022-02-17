package java.dev.engine.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import dev.engine.cache.ICacheEngine;
import dev.engine.json.DevJSONEngine;
import dev.engine.json.IJSONEngine;
import dev.utils.app.cache.DevCache;

/**
 * detail: DevCache ( DevUtils ) Cache Engine 实现
 * @author Ttt
 */
public class DevCacheEngineImpl
        implements ICacheEngine<CacheConfig, DataItem> {

    private final CacheConfig mConfig;
    // JSON Engine
    private       IJSONEngine mJSONEngine;

    public DevCacheEngineImpl(final CacheConfig config) {
        this(config, DevJSONEngine.getEngine());
    }

    public DevCacheEngineImpl(
            final CacheConfig config,
            final IJSONEngine jsonEngine
    ) {
        this.mConfig     = config;
        this.mJSONEngine = jsonEngine;
    }

    // =

    public DevCacheEngineImpl setJSONEngine(final IJSONEngine jsonEngine) {
        this.mJSONEngine = jsonEngine;
        return this;
    }

    // =============
    // = 对外公开方法 =
    // =============

    @Override
    public CacheConfig getConfig() {
        return mConfig;
    }

    @Override
    public void remove(String key) {
        mConfig.mDevCache.remove(key);
    }

    @Override
    public void removeForKeys(String[] keys) {
        mConfig.mDevCache.removeForKeys(keys);
    }

    @Override
    public boolean contains(String key) {
        return mConfig.mDevCache.contains(key);
    }

    @Override
    public boolean isDue(String key) {
        return mConfig.mDevCache.isDue(key);
    }

    @Override
    public void clear() {
        mConfig.mDevCache.clear();
    }

    @Override
    public void clearDue() {
        mConfig.mDevCache.clearDue();
    }

    @Override
    public void clearType(int type) {
        mConfig.mDevCache.clearType(type);
    }

    @Override
    public DataItem getItemByKey(String key) {
        DevCache.Data data = mConfig.mDevCache.getItemByKey(key);
        if (data == null) return null;
        return new DataItem(
                data.getKey(), data.getType(), data.getSize(),
                data.getSaveTime(), data.getValidTime(),
                data.isPermanent(), data.isDue()
        );
    }

    @Override
    public List<DataItem> getKeys() {
        List<DevCache.Data> datas = mConfig.mDevCache.getKeys();
        List<DataItem>      lists = new ArrayList<>();
        for (DevCache.Data data : datas) {
            if (data != null) {
                DataItem item = new DataItem(
                        data.getKey(), data.getType(), data.getSize(),
                        data.getSaveTime(), data.getValidTime(),
                        data.isPermanent(), data.isDue()
                );
                lists.add(item);
            }
        }
        return lists;
    }

    @Override
    public List<DataItem> getPermanentKeys() {
        List<DevCache.Data> datas = mConfig.mDevCache.getPermanentKeys();
        List<DataItem>      lists = new ArrayList<>();
        for (DevCache.Data data : datas) {
            if (data != null) {
                DataItem item = new DataItem(
                        data.getKey(), data.getType(), data.getSize(),
                        data.getSaveTime(), data.getValidTime(),
                        data.isPermanent(), data.isDue()
                );
                lists.add(item);
            }
        }
        return lists;
    }

    @Override
    public int getCount() {
        return mConfig.mDevCache.getCount();
    }

    @Override
    public long getSize() {
        return mConfig.mDevCache.getSize();
    }

    // =======
    // = 存储 =
    // =======

    @Override
    public boolean put(
            String key,
            int value,
            long validTime
    ) {
        return mConfig.mDevCache.put(key, value, validTime);
    }

    @Override
    public boolean put(
            String key,
            long value,
            long validTime
    ) {
        return mConfig.mDevCache.put(key, value, validTime);
    }

    @Override
    public boolean put(
            String key,
            float value,
            long validTime
    ) {
        return mConfig.mDevCache.put(key, value, validTime);
    }

    @Override
    public boolean put(
            String key,
            double value,
            long validTime
    ) {
        return mConfig.mDevCache.put(key, value, validTime);
    }

    @Override
    public boolean put(
            String key,
            boolean value,
            long validTime
    ) {
        return mConfig.mDevCache.put(key, value, validTime);
    }

    @Override
    public boolean put(
            String key,
            String value,
            long validTime
    ) {
        return mConfig.mDevCache.put(key, value, validTime);
    }

    @Override
    public boolean put(
            String key,
            byte[] value,
            long validTime
    ) {
        return mConfig.mDevCache.put(key, value, validTime);
    }

    @Override
    public boolean put(
            String key,
            Bitmap value,
            long validTime
    ) {
        return mConfig.mDevCache.put(key, value, validTime);
    }

    @Override
    public boolean put(
            String key,
            Drawable value,
            long validTime
    ) {
        return mConfig.mDevCache.put(key, value, validTime);
    }

    @Override
    public boolean put(
            String key,
            Serializable value,
            long validTime
    ) {
        return mConfig.mDevCache.put(key, value, validTime);
    }

    @Override
    public boolean put(
            String key,
            Parcelable value,
            long validTime
    ) {
        return mConfig.mDevCache.put(key, value, validTime);
    }

    @Override
    public boolean put(
            String key,
            JSONObject value,
            long validTime
    ) {
        return mConfig.mDevCache.put(key, value, validTime);
    }

    @Override
    public boolean put(
            String key,
            JSONArray value,
            long validTime
    ) {
        return mConfig.mDevCache.put(key, value, validTime);
    }

    @Override
    public <T> boolean put(
            String key,
            T value,
            long validTime
    ) {
        if (mJSONEngine != null) {
            String json = mJSONEngine.toJson(value);
            return mConfig.mDevCache.put(key, json, validTime);
        }
        return false;
    }

    // =======
    // = 获取 =
    // =======

    @Override
    public int getInt(String key) {
        return mConfig.mDevCache.getInt(key);
    }

    @Override
    public long getLong(String key) {
        return mConfig.mDevCache.getLong(key);
    }

    @Override
    public float getFloat(String key) {
        return mConfig.mDevCache.getFloat(key);
    }

    @Override
    public double getDouble(String key) {
        return mConfig.mDevCache.getDouble(key);
    }

    @Override
    public boolean getBoolean(String key) {
        return mConfig.mDevCache.getBoolean(key);
    }

    @Override
    public String getString(String key) {
        return mConfig.mDevCache.getString(key);
    }

    @Override
    public byte[] getBytes(String key) {
        return mConfig.mDevCache.getBytes(key);
    }

    @Override
    public Bitmap getBitmap(String key) {
        return mConfig.mDevCache.getBitmap(key);
    }

    @Override
    public Drawable getDrawable(String key) {
        return mConfig.mDevCache.getDrawable(key);
    }

    @Override
    public Object getSerializable(String key) {
        return mConfig.mDevCache.getSerializable(key);
    }

    @Override
    public <T> T getParcelable(
            String key,
            Parcelable.Creator<T> creator
    ) {
        return mConfig.mDevCache.getParcelable(key, creator);
    }

    @Override
    public JSONObject getJSONObject(String key) {
        return mConfig.mDevCache.getJSONObject(key);
    }

    @Override
    public JSONArray getJSONArray(String key) {
        return mConfig.mDevCache.getJSONArray(key);
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
        return mConfig.mDevCache.getInt(key, defaultValue);
    }

    @Override
    public long getLong(
            String key,
            long defaultValue
    ) {
        return mConfig.mDevCache.getLong(key, defaultValue);
    }

    @Override
    public float getFloat(
            String key,
            float defaultValue
    ) {
        return mConfig.mDevCache.getFloat(key, defaultValue);
    }

    @Override
    public double getDouble(
            String key,
            double defaultValue
    ) {
        return mConfig.mDevCache.getDouble(key, defaultValue);
    }

    @Override
    public boolean getBoolean(
            String key,
            boolean defaultValue
    ) {
        return mConfig.mDevCache.getBoolean(key, defaultValue);
    }

    @Override
    public String getString(
            String key,
            String defaultValue
    ) {
        return mConfig.mDevCache.getString(key, defaultValue);
    }

    @Override
    public byte[] getBytes(
            String key,
            byte[] defaultValue
    ) {
        return mConfig.mDevCache.getBytes(key, defaultValue);
    }

    @Override
    public Bitmap getBitmap(
            String key,
            Bitmap defaultValue
    ) {
        return mConfig.mDevCache.getBitmap(key, defaultValue);
    }

    @Override
    public Drawable getDrawable(
            String key,
            Drawable defaultValue
    ) {
        return mConfig.mDevCache.getDrawable(key, defaultValue);
    }

    @Override
    public Object getSerializable(
            String key,
            Object defaultValue
    ) {
        return mConfig.mDevCache.getSerializable(key, defaultValue);
    }

    @Override
    public <T> T getParcelable(
            String key,
            Parcelable.Creator<T> creator,
            T defaultValue
    ) {
        return mConfig.mDevCache.getParcelable(key, creator, defaultValue);
    }

    @Override
    public JSONObject getJSONObject(
            String key,
            JSONObject defaultValue
    ) {
        return mConfig.mDevCache.getJSONObject(key, defaultValue);
    }

    @Override
    public JSONArray getJSONArray(
            String key,
            JSONArray defaultValue
    ) {
        return mConfig.mDevCache.getJSONArray(key, defaultValue);
    }

    @Override
    public <T> T getEntity(
            String key,
            Type typeOfT,
            T defaultValue
    ) {
        if (mJSONEngine != null) {
            String json   = getString(key, null);
            T      object = (T) mJSONEngine.fromJson(json, typeOfT);
            if (object == null) return defaultValue;
            return object;
        }
        return null;
    }
}