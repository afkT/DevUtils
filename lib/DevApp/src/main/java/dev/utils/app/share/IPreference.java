package dev.utils.app.share;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * detail: SharedPreferences 操作接口
 * @author Ttt
 */
public interface IPreference {

    /**
     * detail: 存储 / 取出 数据类型
     * @author Ttt
     */
    enum DataType {
        INTEGER,
        LONG,
        BOOLEAN,
        FLOAT,
        STRING,
        STRING_SET
    }

    // =

    /**
     * 保存数据
     * @param key   保存的 key
     * @param value 保存的 value
     * @param <T>   泛型
     */
    <T> void put(
            String key,
            T value
    );

    /**
     * 保存 Map 集合 ( 只能是 Integer、Long、Boolean、Float、String、Set)
     * @param map {@link Map}
     * @param <T> 泛型
     */
    <T> void putAll(Map<String, T> map);

    /**
     * 保存 List 集合
     * @param key  保存的 key
     * @param list 保存的 value
     */
    void putAll(
            String key,
            List<String> list
    );

    /**
     * 保存 List 集合, 并且自定义保存顺序
     * @param key        保存的 key
     * @param list       保存的 value
     * @param comparator 排序 {@link Comparator}
     */
    void putAll(
            String key,
            List<String> list,
            Comparator<String> comparator
    );

    /**
     * 根据 key 获取数据
     * @param key  保存的 key
     * @param type 数据类型
     * @param <T>  泛型
     * @return 存储的数据
     */
    <T> T get(
            String key,
            DataType type
    );

    /**
     * 获取全部数据
     * @return 存储的数据
     */
    Map<String, ?> getAll();

    /**
     * 获取 List 集合
     * @param key 保存的 key
     * @return 存储的数据
     */
    List<String> getAll(String key);

    /**
     * 移除数据
     * @param key 保存的 key
     */
    void remove(String key);

    /**
     * 移除集合的数据
     * @param keys 保存的 key 集合
     */
    void removeAll(List<String> keys);

    /**
     * 移除数组的数据
     * @param keys 保存的 key 数组
     */
    void removeAll(String[] keys);

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

    // =

    /**
     * 获取 int 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    int getInt(String key);

    /**
     * 获取 float 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    float getFloat(String key);

    /**
     * 获取 long 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    long getLong(String key);

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
     * 获取 Set 类型的数据
     * @param key 保存的 key
     * @return 存储的数据
     */
    Set<String> getSet(String key);
}