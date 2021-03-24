package dev.engine.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import dev.utils.common.cipher.Cipher;

/**
 * detail: Cache Engine 接口
 * @author Ttt
 */
public interface ICacheEngine<Config extends ICacheEngine.EngineConfig, Item extends ICacheEngine.EngineItem> {

    /**
     * detail: Cache Config
     * @author Ttt
     */
    class EngineConfig {

        // 缓存存储 id
        public final String  cacheID;
        // 通用加解密中间层
        public final Cipher  cipher;
        // 获取操作是否会移除失效资源
        public       boolean removeDue;

        public EngineConfig(
                String cacheID,
                Cipher cipher
        ) {
            this.cacheID = cacheID;
            this.cipher = cipher;
        }

        public EngineConfig(
                String cacheID,
                Cipher cipher,
                boolean removeDue
        ) {
            this.cacheID = cacheID;
            this.cipher = cipher;
            this.removeDue = removeDue;
        }
    }

    /**
     * detail: Cache Item
     * @author Ttt
     */
    class EngineItem {

        // 存储 Key
        public final String  key;
        // 存储类型
        public final int     type;
        // 文件大小
        public final long    size;
        // 保存时间 ( 毫秒 )
        public final long    saveTime;
        // 有效期 ( 毫秒 )
        public final long    validTime;
        // 最后操作时间 ( 毫秒 )
        public final long    lastModified;
        // 是否永久有效
        public final boolean isPermanent;

        public EngineItem(
                String key,
                int type,
                long size,
                long saveTime,
                long validTime,
                long lastModified,
                boolean isPermanent
        ) {
            this.key = key;
            this.type = type;
            this.size = size;
            this.saveTime = saveTime;
            this.validTime = validTime;
            this.lastModified = lastModified;
            this.isPermanent = isPermanent;
        }
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取 Cache Engine Config
     * @return {@link Config}
     */
    Config getConfig();

    // =

    /**
     * 移除数据
     * @param key 保存的 key
     */
    void remove(String key);

    /**
     * 移除数组的数据
     * @param keys 保存的 key 数组
     */
    void removeForKeys(String[] keys);

    /**
     * 是否存在 key
     * @param key 保存的 key
     * @return {@code true} yes, {@code false} no
     */
    boolean contains(String key);

    /**
     * 判断某个 key 是否过期
     * <pre>
     *     如果不存在该 key 也返回过期
     * </pre>
     * @param key 保存的 key
     * @return {@code true} yes, {@code false} no
     */
    boolean isDue(String key);

    /**
     * 清除全部数据
     */
    void clear();

    /**
     * 清除过期数据
     */
    void clearDue();

    /**
     * 清除无效数据
     */
    void clearInvalid();

    /**
     * 清除某个类型的全部数据
     * @param type 类型
     */
    void clearType(int type);

    /**
     * 通过 Key 获取 Item
     * @param key 保存的 key
     * @return Item
     */
    Item getItemByKey(String key);

    /**
     * 获取有效 Key 集合
     * <pre>
     *     {@link EngineConfig#removeDue} 控制移除失效资源
     * </pre>
     * @return 有效 Key 集合
     */
    List<Item> getKeys();

    /**
     * 获取永久有效 Key 集合
     * <pre>
     *     {@link EngineConfig#removeDue} 控制移除失效资源
     * </pre>
     * @return 永久有效 Key 集合
     */
    List<Item> getPermanentKeys();

    /**
     * 获取有效 Key 数量
     * <pre>
     *     {@link EngineConfig#removeDue} 控制移除失效资源
     * </pre>
     * @return 有效 Key 数量
     */
    int getCount();

    /**
     * 获取有效 Key 占用总大小
     * <pre>
     *     {@link EngineConfig#removeDue} 控制移除失效资源
     * </pre>
     * @return 有效 Key 占用总大小
     */
    long getSize();

    // =======
    // = 存储 =
    // =======

    /**
     * 保存 int 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    boolean put(
            String key,
            int value,
            long validTime
    );

    /**
     * 保存 long 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    boolean put(
            String key,
            long value,
            long validTime
    );

    /**
     * 保存 float 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    boolean put(
            String key,
            float value,
            long validTime
    );

    /**
     * 保存 double 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    boolean put(
            String key,
            double value,
            long validTime
    );

    /**
     * 保存 boolean 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    boolean put(
            String key,
            boolean value,
            long validTime
    );

    /**
     * 保存 String 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    boolean put(
            String key,
            String value,
            long validTime
    );

    /**
     * 保存 byte[] 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    boolean put(
            final String key,
            final byte[] value,
            long validTime
    );

    /**
     * 保存 Bitmap 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    boolean put(
            final String key,
            final Bitmap value,
            long validTime
    );

    /**
     * 保存 Drawable 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    boolean put(
            final String key,
            final Drawable value,
            long validTime
    );

    /**
     * 保存 Serializable 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    boolean put(
            final String key,
            final Serializable value,
            long validTime
    );

    /**
     * 保存 Parcelable 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    boolean put(
            final String key,
            final Parcelable value,
            long validTime
    );

    /**
     * 保存 JSONObject 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    boolean put(
            final String key,
            final JSONObject value,
            long validTime
    );

    /**
     * 保存 JSONArray 类型的数据
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 )
     * @return {@code true} success, {@code false} fail
     */
    boolean put(
            final String key,
            final JSONArray value,
            long validTime
    );

    /**
     * 保存指定类型对象
     * @param key       保存的 key
     * @param value     存储的数据
     * @param validTime 有效时间 ( 毫秒 )
     * @param <T>       泛型
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean put(
            final String key,
            final T value,
            long validTime
    );

    // =======
    // = 获取 =
    // =======

    /**
     * 获取 int 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    int getInt(String key);

    /**
     * 获取 long 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    long getLong(String key);

    /**
     * 获取 float 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    float getFloat(String key);

    /**
     * 获取 double 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    double getDouble(String key);

    /**
     * 获取 boolean 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    boolean getBoolean(String key);

    /**
     * 获取 String 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    String getString(String key);

    /**
     * 获取 byte[] 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    byte[] getBytes(String key);

    /**
     * 获取 Bitmap 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    Bitmap getBitmap(String key);

    /**
     * 获取 Drawable 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    Drawable getDrawable(String key);

    /**
     * 获取 Serializable 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    Serializable getSerializable(String key);

    /**
     * 获取 Parcelable 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    Parcelable getParcelable(String key);

    /**
     * 获取 JSONObject 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    JSONObject getJSONObject(String key);

    /**
     * 获取 JSONArray 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    JSONArray getJSONArray(String key);

    /**
     * 获取指定类型对象
     * @param key     保存的 key
     * @param typeOfT {@link Type} T
     * @param <T>     泛型
     * @return instance of type
     */
    <T> T getEntity(
            final String key,
            final Type typeOfT
    );

    // =

    /**
     * 获取 int 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    int getInt(
            String key,
            int defaultValue
    );

    /**
     * 获取 long 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    long getLong(
            String key,
            long defaultValue
    );

    /**
     * 获取 float 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    float getFloat(
            String key,
            float defaultValue
    );

    /**
     * 获取 double 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    double getDouble(
            String key,
            double defaultValue
    );

    /**
     * 获取 boolean 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    boolean getBoolean(
            String key,
            boolean defaultValue
    );

    /**
     * 获取 String 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    String getString(
            String key,
            String defaultValue
    );

    /**
     * 获取 byte[] 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    byte[] getBytes(
            String key,
            byte[] defaultValue
    );

    /**
     * 获取 Bitmap 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    Bitmap getBitmap(
            String key,
            Bitmap defaultValue
    );

    /**
     * 获取 Drawable 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    Drawable getDrawable(
            String key,
            Drawable defaultValue
    );

    /**
     * 获取 Serializable 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    Serializable getSerializable(
            String key,
            Serializable defaultValue
    );

    /**
     * 获取 Parcelable 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    Parcelable getParcelable(
            String key,
            Parcelable defaultValue
    );

    /**
     * 获取 JSONObject 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    JSONObject getJSONObject(
            String key,
            JSONObject defaultValue
    );

    /**
     * 获取 JSONArray 类型的数据
     * @param key          保存的 key
     * @param defaultValue 默认值
     * @return 存储的数据
     */
    JSONArray getJSONArray(
            String key,
            JSONArray defaultValue
    );

    /**
     * 获取指定类型对象
     * @param key          保存的 key
     * @param typeOfT      {@link Type} T
     * @param defaultValue 默认值
     * @param <T>          泛型
     * @return instance of type
     */
    <T> T getEntity(
            final String key,
            final Type typeOfT,
            T defaultValue
    );
}