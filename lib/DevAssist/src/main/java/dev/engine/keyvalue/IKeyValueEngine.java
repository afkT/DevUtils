package dev.engine.keyvalue;

import java.lang.reflect.Type;

import dev.utils.common.cipher.Cipher;

/**
 * detail: Key-Value Engine 接口
 * @author Ttt
 */
public interface IKeyValueEngine<Config extends IKeyValueEngine.EngineConfig> {

    /**
     * detail: Key-Value Config
     * @author Ttt
     */
    class EngineConfig {

        // 通用加解密中间层
        public final Cipher cipher;

        public EngineConfig(
                Cipher cipher
        ) {
            this.cipher = cipher;
        }
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 Key-Value Engine Config
     * @return Key-Value Config
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
     * 清除全部数据
     */
    void clear();

    // =======
    // = 存储 =
    // =======

    /**
     * 保存 int 类型的数据
     * @param key   保存的 key
     * @param value 存储的数据
     * @return {@code true} success, {@code false} fail
     */
    boolean putInt(
            String key,
            int value
    );

    /**
     * 保存 long 类型的数据
     * @param key   保存的 key
     * @param value 存储的数据
     * @return {@code true} success, {@code false} fail
     */
    boolean putLong(
            String key,
            long value
    );

    /**
     * 保存 float 类型的数据
     * @param key   保存的 key
     * @param value 存储的数据
     * @return {@code true} success, {@code false} fail
     */
    boolean putFloat(
            String key,
            float value
    );

    /**
     * 保存 double 类型的数据
     * @param key   保存的 key
     * @param value 存储的数据
     * @return {@code true} success, {@code false} fail
     */
    boolean putDouble(
            String key,
            double value
    );

    /**
     * 保存 boolean 类型的数据
     * @param key   保存的 key
     * @param value 存储的数据
     * @return {@code true} success, {@code false} fail
     */
    boolean putBoolean(
            String key,
            boolean value
    );

    /**
     * 保存 String 类型的数据
     * @param key   保存的 key
     * @param value 存储的数据
     * @return {@code true} success, {@code false} fail
     */
    boolean putString(
            String key,
            String value
    );

    /**
     * 保存指定类型对象
     * @param key   保存的 key
     * @param value 存储的数据
     * @param <T>   泛型
     * @return {@code true} success, {@code false} fail
     */
    <T> boolean putEntity(
            String key,
            T value
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
     * 获取指定类型对象
     * @param key     保存的 key
     * @param typeOfT {@link Type} T
     * @param <T>     泛型
     * @return instance of type
     */
    <T> T getEntity(
            String key,
            Type typeOfT
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
     * 获取指定类型对象
     * @param key          保存的 key
     * @param typeOfT      {@link Type} T
     * @param defaultValue 默认值
     * @param <T>          泛型
     * @return instance of type
     */
    <T> T getEntity(
            String key,
            Type typeOfT,
            T defaultValue
    );
}