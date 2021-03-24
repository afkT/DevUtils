package dev.utils.app.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * detail: 缓存类
 * @author Ttt
 */
public final class DevCache {

    // 日志 TAG
    private static final String TAG = DevCache.class.getSimpleName();
    // 缓存地址
    private final        String mCachePath;

    /**
     * 获取 DevCache
     * @param cachePath 缓存文件夹
     * @return {@link DevCache}
     */
    private DevCache(final String cachePath) {
        this.mCachePath = cachePath;
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
    public static final int ENTITY       = 14;

    /**
     * detail: 数据源
     * @author Ttt
     */
    public static class Data {

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
        // 最后操作时间 ( 毫秒 )
        private       long   mLastModified;

        protected Data(
                String path,
                String key
        ) {
            this.mPath = path;
            this.mKey = key;
        }

        protected Data(
                String path,
                String key,
                int type,
                long saveTime,
                long validTime,
                long lastModified
        ) {
            this.mPath = path;
            this.mKey = key;
            this.mType = type;
            this.mSaveTime = saveTime;
            this.mValidTime = validTime;
            this.mLastModified = lastModified;
        }

        /**
         * 获取存储 Key
         * @return 存储 Key
         */
        public String getKey() {
            return mKey;
        }

        /**
         * 获取数据存储类型
         * @return 数据存储类型
         */
        public int getType() {
            return mType;
        }

        /**
         * 获取文件大小
         * @return 文件大小
         */
        public long getSize() {
            return DevCacheUtils.getDataFileSize(mPath, mKey);
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

        /**
         * 获取最后操作时间 ( 毫秒 )
         * @return 最后操作时间 ( 毫秒 )
         */
        public long getLastModified() {
            return mLastModified;
        }

        /**
         * 是否永久有效
         * @return {@code true} yes, {@code false} no
         */
        public boolean isPermanent() {
            return mValidTime <= 0;
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

        protected Data setLastModified(long lastModified) {
            this.mLastModified = lastModified;
            return this;
        }
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取缓存地址
     * @return 缓存地址
     */
    public String getCachePath() {
        return mCachePath;
    }

    // =

    /**
     * 移除数据
     * @param key 保存的 key
     */
    public void remove(String key) {
        DevCacheUtils.deleteFile(this, key);
    }

    /**
     * 删除 Key[] 配置、数据文件
     * @param keys 存储 key[]
     */
    public void removeForKeys(String[] keys) {
        DevCacheUtils.deleteFiles(this, keys);
    }

    /**
     * 是否存在 key
     * @param key 保存的 key
     * @return {@code true} yes, {@code false} no
     */
    public boolean contains(String key) {
        return DevCacheUtils.isExistKeyFile(this, key);
    }

    public boolean isDue(String key) {
        return false;
    }

    public void clear() {

    }

    public void clearDue() {

    }

    public void clearInvalid() {

    }

    public void clearType(int type) {

    }

    public Data getItemByKey(String key) {
        return null;
    }

    public List<Data> getKeys() {
        return null;
    }

    public List<Data> getPermanentKeys() {
        return null;
    }

    public int getCount() {
        return 0;
    }

    public long getSize() {
        return 0;
    }

    public boolean put(
            String key,
            int value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            long value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            float value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            double value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            boolean value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            String value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            byte[] value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            Bitmap value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            Drawable value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            Serializable value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            Parcelable value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            JSONObject value,
            long validTime
    ) {
        return false;
    }

    public boolean put(
            String key,
            JSONArray value,
            long validTime
    ) {
        return false;
    }

    public <T> boolean put(
            String key,
            T value,
            long validTime
    ) {
        return false;
    }

    public int getInt(String key) {
        return 0;
    }

    public long getLong(String key) {
        return 0;
    }

    public float getFloat(String key) {
        return 0;
    }

    public double getDouble(String key) {
        return 0;
    }

    public boolean getBoolean(String key) {
        return false;
    }

    public String getString(String key) {
        return null;
    }

    public byte[] getBytes(String key) {
        return new byte[0];
    }

    public Bitmap getBitmap(String key) {
        return null;
    }

    public Drawable getDrawable(String key) {
        return null;
    }

    public Serializable getSerializable(String key) {
        return null;
    }

    public Parcelable getParcelable(String key) {
        return null;
    }

    public JSONObject getJSONObject(String key) {
        return null;
    }

    public JSONArray getJSONArray(String key) {
        return null;
    }

    public <T> T getEntity(
            String key,
            Type typeOfT
    ) {
        return null;
    }

    public int getInt(
            String key,
            int defaultValue
    ) {
        return 0;
    }

    public long getLong(
            String key,
            long defaultValue
    ) {
        return 0;
    }

    public float getFloat(
            String key,
            float defaultValue
    ) {
        return 0;
    }

    public double getDouble(
            String key,
            double defaultValue
    ) {
        return 0;
    }

    public boolean getBoolean(
            String key,
            boolean defaultValue
    ) {
        return false;
    }

    public String getString(
            String key,
            String defaultValue
    ) {
        return null;
    }

    public byte[] getBytes(
            String key,
            byte[] defaultValue
    ) {
        return new byte[0];
    }

    public Bitmap getBitmap(
            String key,
            Bitmap defaultValue
    ) {
        return null;
    }

    public Drawable getDrawable(
            String key,
            Drawable defaultValue
    ) {
        return null;
    }

    public Serializable getSerializable(
            String key,
            Serializable defaultValue
    ) {
        return null;
    }

    public Parcelable getParcelable(
            String key,
            Parcelable defaultValue
    ) {
        return null;
    }

    public JSONObject getJSONObject(
            String key,
            JSONObject defaultValue
    ) {
        return null;
    }

    public JSONArray getJSONArray(
            String key,
            JSONArray defaultValue
    ) {
        return null;
    }

    public <T> T getEntity(
            String key,
            Type typeOfT,
            T defaultValue
    ) {
        return null;
    }
}