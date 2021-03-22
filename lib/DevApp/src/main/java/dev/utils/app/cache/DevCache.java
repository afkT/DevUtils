package dev.utils.app.cache;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * detail: 缓存类
 * @author Ttt
 */
public final class DevCache {

    // 日志 TAG
    private static final String        TAG                   = DevCache.class.getSimpleName();
    // 缓存配置文件名
    private final        String        DEV_CACHE_CONFIG_NAME = "dev_cache_config.json";
    // 总缓存大小
    private final        AtomicLong    mCacheSize            = new AtomicLong();
    // 总缓存的文件总数
    private final        AtomicInteger mCacheCount           = new AtomicInteger();
    // 缓存地址
    private final        String        mCachePath;

    /**
     * 获取 DevCache
     * @param cachePath 缓存文件夹
     * @return {@link DevCache}
     */
    private DevCache(final String cachePath) {
        this.mCachePath = cachePath;
    }

    // 数据类型
    private static final int TYPE         = 9500;
    public static final  int INT          = TYPE + 1;
    public static final  int LONG         = TYPE + 2;
    public static final  int FLOAT        = TYPE + 3;
    public static final  int DOUBLE       = TYPE + 4;
    public static final  int BOOLEAN      = TYPE + 5;
    public static final  int STRING       = TYPE + 6;
    public static final  int BYTES        = TYPE + 7;
    public static final  int BITMAP       = TYPE + 8;
    public static final  int DRAWABLE     = TYPE + 9;
    public static final  int SERIALIZABLE = TYPE + 10;
    public static final  int PARCELABLE   = TYPE + 11;
    public static final  int JSON_OBJECT  = TYPE + 12;
    public static final  int JSON_ARRAY   = TYPE + 13;
    public static final  int ENTITY       = TYPE + 14;

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


    // ===========
    // = 内部方法 =
    // ===========
}