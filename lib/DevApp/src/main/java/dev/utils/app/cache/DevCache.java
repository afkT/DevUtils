package dev.utils.app.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import dev.utils.app.PathUtils;
import dev.utils.common.cipher.Cipher;

/**
 * detail: 缓存类
 * @author Ttt
 */
public final class DevCache {

    // 默认缓存文件名
    private static final String          DEFAULT_NAME = DevCache.class.getSimpleName();
    // 缓存管理类
    private final        DevCacheManager mManager;

    /**
     * 获取 DevCache
     * @param cachePath 缓存文件夹路径
     * @param cipher    通用加解密中间层
     */
    private DevCache(
            final String cachePath,
            final Cipher cipher
    ) {
        mManager = new DevCacheManager(cachePath, cipher);
    }

    // 数据类型
    public static final int INT          = 1;
    public static final int LONG         = 2;
    public static final int FLOAT        = 3;
    public static final int DOUBLE       = 4;
    public static final int BOOLEAN      = 5;
    public static final int STRING       = 6;
    public static final int BYTES        = 7;
    public static final int BITMAP       = 8;
    public static final int DRAWABLE     = 9;
    public static final int SERIALIZABLE = 10;
    public static final int PARCELABLE   = 11;
    public static final int JSON_OBJECT  = 12;
    public static final int JSON_ARRAY   = 13;

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 DevCache
     * @return {@link DevCache}
     */
    public static DevCache newCache() {
        DevCache cache = DevCacheManager.sInstanceMaps.get("");
        if (cache == null) {
            String cachePath = PathUtils.getAppExternal().getAppCachePath(DEFAULT_NAME);
            cache = new DevCache(cachePath, null);
            DevCacheManager.sInstanceMaps.put("", cache);
            DevCacheManager.sInstanceMaps.put(cachePath, cache);
        }
        return cache;
    }

    /**
     * 获取 DevCache
     * @param cachePath 缓存文件夹路径
     * @return {@link DevCache}
     */
    public static DevCache newCache(final String cachePath) {
        return newCache(cachePath, null);
    }

    /**
     * 获取 DevCache
     * @param cachePath 缓存文件夹路径
     * @param cipher    通用加解密中间层
     * @return {@link DevCache}
     */
    public static DevCache newCache(
            final String cachePath,
            final Cipher cipher
    ) {
        if (TextUtils.isEmpty(cachePath)) {
            return newCache();
        }
        DevCache cache = DevCacheManager.sInstanceMaps.get(cachePath);
        if (cache == null) {
            cache = new DevCache(cachePath, cipher);
            DevCacheManager.sInstanceMaps.put(cachePath, cache);
        }
        return cache;
    }

    /**
     * 获取缓存地址
     * @return 缓存地址
     */
    public String getCachePath() {
        return mManager.getCachePath();
    }

    // =

    /**
     * 移除数据
     * @param key 保存的 key
     */
    public void remove(String key) {
        mManager.remove(key);
    }

    /**
     * 删除 Key[] 配置、数据文件
     * @param keys 存储 key[]
     */
    public void removeForKeys(String[] keys) {
        mManager.removeForKeys(keys);
    }

    /**
     * 是否存在 key
     * @param key 保存的 key
     * @return {@code true} yes, {@code false} no
     */
    public boolean contains(String key) {
        return mManager.contains(key);
    }

    /**
     * 判断某个 key 是否过期
     * <pre>
     *     如果不存在该 key 也返回过期
     * </pre>
     * @param key 保存的 key
     * @return {@code true} yes, {@code false} no
     */
    public boolean isDue(String key) {
        return mManager.isDue(key);
    }

    /**
     * 清除全部数据
     */
    public void clear() {
        mManager.clear();
    }

    /**
     * 清除过期数据
     */
    public void clearDue() {
        mManager.clearDue();
    }

    /**
     * 清除某个类型的全部数据
     * @param type 类型
     */
    public void clearType(int type) {
        mManager.clearType(type);
    }

    /**
     * 通过 Key 获取 Item
     * @param key 保存的 key
     * @return Item
     */
    public Data getItemByKey(String key) {
        return mManager.getItemByKey(key);
    }

    /**
     * 获取有效 Key 集合
     * @return 有效 Key 集合
     */
    public List<Data> getKeys() {
        return mManager.getKeys();
    }

    /**
     * 获取永久有效 Key 集合
     * @return 永久有效 Key 集合
     */
    public List<Data> getPermanentKeys() {
        return mManager.getPermanentKeys();
    }

    /**
     * 获取有效 Key 数量
     * @return 有效 Key 数量
     */
    public int getCount() {
        return mManager.getCount();
    }

    /**
     * 获取有效 Key 占用总大小
     * @return 有效 Key 占用总大小
     */
    public long getSize() {
        return mManager.getSize();
    }

    // =======
    // = 存储 =
    // =======

    /**
     * 保存 int 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(
            String key,
            int value,
            long validTime
    ) {
        return mManager.put(key, value, validTime);
    }

    /**
     * 保存 long 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(
            String key,
            long value,
            long validTime
    ) {
        return mManager.put(key, value, validTime);
    }

    /**
     * 保存 float 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(
            String key,
            float value,
            long validTime
    ) {
        return mManager.put(key, value, validTime);
    }

    /**
     * 保存 double 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(
            String key,
            double value,
            long validTime
    ) {
        return mManager.put(key, value, validTime);
    }

    /**
     * 保存 boolean 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(
            String key,
            boolean value,
            long validTime
    ) {
        return mManager.put(key, value, validTime);
    }

    /**
     * 保存 String 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(
            String key,
            String value,
            long validTime
    ) {
        return mManager.put(key, value, validTime);
    }

    /**
     * 保存 byte[] 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(
            String key,
            byte[] value,
            long validTime
    ) {
        return mManager.put(key, value, validTime);
    }

    /**
     * 保存 Bitmap 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(
            String key,
            Bitmap value,
            long validTime
    ) {
        return mManager.put(key, value, validTime);
    }

    /**
     * 保存 Drawable 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(
            String key,
            Drawable value,
            long validTime
    ) {
        return mManager.put(key, value, validTime);
    }

    /**
     * 保存 Serializable 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(
            String key,
            Serializable value,
            long validTime
    ) {
        return mManager.put(key, value, validTime);
    }

    /**
     * 保存 Parcelable 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(
            String key,
            Parcelable value,
            long validTime
    ) {
        return mManager.put(key, value, validTime);
    }

    /**
     * 保存 JSONObject 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(
            String key,
            JSONObject value,
            long validTime
    ) {
        return mManager.put(key, value, validTime);
    }

    /**
     * 保存 JSONArray 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 ) 小于等于 0 为永久有效
     * @return {@code true} success, {@code false} fail
     */
    public boolean put(
            String key,
            JSONArray value,
            long validTime
    ) {
        return mManager.put(key, value, validTime);
    }

    // =======
    // = 获取 =
    // =======

    /**
     * 获取 int 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public int getInt(String key) {
        return mManager.getInt(key);
    }

    /**
     * 获取 long 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public long getLong(String key) {
        return mManager.getLong(key);
    }

    /**
     * 获取 float 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public float getFloat(String key) {
        return mManager.getFloat(key);
    }

    /**
     * 获取 double 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public double getDouble(String key) {
        return mManager.getDouble(key);
    }

    /**
     * 获取 boolean 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public boolean getBoolean(String key) {
        return mManager.getBoolean(key);
    }

    /**
     * 获取 String 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public String getString(String key) {
        return mManager.getString(key);
    }

    /**
     * 获取 byte[] 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public byte[] getBytes(String key) {
        return mManager.getBytes(key);
    }

    /**
     * 获取 Bitmap 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public Bitmap getBitmap(String key) {
        return mManager.getBitmap(key);
    }

    /**
     * 获取 Drawable 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public Drawable getDrawable(String key) {
        return mManager.getDrawable(key);
    }

    /**
     * 获取 Serializable 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public Object getSerializable(String key) {
        return mManager.getSerializable(key);
    }

    /**
     * 获取 Parcelable 类型的数据
     * @param key     保存的 key
     * @param creator {@link Parcelable.Creator}
     * @return 存储的数据
     */
    public <T> T getParcelable(
            String key,
            Parcelable.Creator<T> creator
    ) {
        return mManager.getParcelable(key, creator);
    }

    /**
     * 获取 JSONObject 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public JSONObject getJSONObject(String key) {
        return mManager.getJSONObject(key);
    }

    /**
     * 获取 JSONArray 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    public JSONArray getJSONArray(String key) {
        return mManager.getJSONArray(key);
    }

    // =

    /**
     * 获取 int 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    public int getInt(
            String key,
            int defaultValue
    ) {
        return mManager.getInt(key, defaultValue);
    }

    /**
     * 获取 long 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    public long getLong(
            String key,
            long defaultValue
    ) {
        return mManager.getLong(key, defaultValue);
    }

    /**
     * 获取 float 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    public float getFloat(
            String key,
            float defaultValue
    ) {
        return mManager.getFloat(key, defaultValue);
    }

    /**
     * 获取 double 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    public double getDouble(
            String key,
            double defaultValue
    ) {
        return mManager.getDouble(key, defaultValue);
    }

    /**
     * 获取 boolean 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    public boolean getBoolean(
            String key,
            boolean defaultValue
    ) {
        return mManager.getBoolean(key, defaultValue);
    }

    /**
     * 获取 String 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    public String getString(
            String key,
            String defaultValue
    ) {
        return mManager.getString(key, defaultValue);
    }

    /**
     * 获取 byte[] 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    public byte[] getBytes(
            String key,
            byte[] defaultValue
    ) {
        return mManager.getBytes(key, defaultValue);
    }

    /**
     * 获取 Bitmap 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    public Bitmap getBitmap(
            String key,
            Bitmap defaultValue
    ) {
        return mManager.getBitmap(key, defaultValue);
    }

    /**
     * 获取 Drawable 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    public Drawable getDrawable(
            String key,
            Drawable defaultValue
    ) {
        return mManager.getDrawable(key, defaultValue);
    }

    /**
     * 获取 Serializable 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    public Object getSerializable(
            String key,
            Object defaultValue
    ) {
        return mManager.getSerializable(key, defaultValue);
    }

    /**
     * 获取 Parcelable 类型的数据
     * @param key          保存的 key
     * @param creator      {@link Parcelable.Creator}
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    public <T> T getParcelable(
            String key,
            Parcelable.Creator<T> creator,
            T defaultValue
    ) {
        return mManager.getParcelable(key, creator, defaultValue);
    }

    /**
     * 获取 JSONObject 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    public JSONObject getJSONObject(
            String key,
            JSONObject defaultValue
    ) {
        return mManager.getJSONObject(key, defaultValue);
    }

    /**
     * 获取 JSONArray 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    public JSONArray getJSONArray(
            String key,
            JSONArray defaultValue
    ) {
        return mManager.getJSONArray(key, defaultValue);
    }

    // ========
    // = 实体类 =
    // ========

    /**
     * detail: 数据源
     * @author Ttt
     */
    public static final class Data {

        // 缓存地址
        private final String mPath;
        // 存储 Key
        private final String mKey;
        // 存储类型
        private       int    mType;
        // 保存时间 ( 毫秒 )
        private       long   mSaveTime;
        // 有效期 ( 毫秒 )
        private       long   mValidTime;

        protected Data(
                String path,
                String key,
                int type,
                long saveTime,
                long validTime
        ) {
            this.mPath      = path;
            this.mKey       = key;
            this.mType      = type;
            this.mSaveTime  = saveTime;
            this.mValidTime = validTime;
        }

        /**
         * 获取存储 Key
         * @return 存储 Key
         */
        public String getKey() {
            return mKey;
        }

        /**
         * 是否永久有效
         * @return {@code true} yes, {@code false} no
         */
        public boolean isPermanent() {
            return mValidTime <= 0;
        }

        /**
         * 是否过期
         * @return {@code true} yes, {@code false} no
         */
        public boolean isDue() {
            if (mValidTime <= 0) return false;
            long time = mSaveTime + mValidTime;
            return System.currentTimeMillis() - time >= 0;
        }

        /**
         * 获取文件大小
         * @return 文件大小
         */
        public long getSize() {
            return DevCacheManager.getDataFileSize(mPath, mKey);
        }

        // =

        /**
         * 获取数据存储类型
         * @return 数据存储类型
         */
        public int getType() {
            return mType;
        }

        /**
         * 获取保存时间 ( 毫秒 )
         * @return 保存时间 ( 毫秒 )
         */
        public long getSaveTime() {
            return mSaveTime;
        }

        /**
         * 获取有效期 ( 毫秒 )
         * @return 有效期 ( 毫秒 )
         */
        public long getValidTime() {
            return mValidTime;
        }

        // =

        protected Data setType(int type) {
            this.mType = type;
            return this;
        }

        protected Data setSaveTime(long saveTime) {
            this.mSaveTime = saveTime;
            return this;
        }

        protected Data setValidTime(long validTime) {
            this.mValidTime = validTime;
            return this;
        }

        // ==========
        // = 判断方法 =
        // ==========

        public boolean isInt() {
            return mType == INT;
        }

        public boolean isLong() {
            return mType == LONG;
        }

        public boolean isFloat() {
            return mType == FLOAT;
        }

        public boolean isDouble() {
            return mType == DOUBLE;
        }

        public boolean isBoolean() {
            return mType == BOOLEAN;
        }

        public boolean isString() {
            return mType == STRING;
        }

        public boolean isBytes() {
            return mType == BYTES;
        }

        public boolean isBitmap() {
            return mType == BITMAP;
        }

        public boolean isDrawable() {
            return mType == DRAWABLE;
        }

        public boolean isSerializable() {
            return mType == SERIALIZABLE;
        }

        public boolean isParcelable() {
            return mType == PARCELABLE;
        }

        public boolean isJSONObject() {
            return mType == JSON_OBJECT;
        }

        public boolean isJSONArray() {
            return mType == JSON_ARRAY;
        }
    }
}