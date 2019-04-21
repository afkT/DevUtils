package dev.utils.app.share;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * detail: SharedPreferences 操作接口
 * @author Ttt
 * <pre>
 *      @see <a href="http://www.jianshu.com/p/fcd75a324c35"/>
 * </pre>
 */
public interface IPreference {

    /**
     * 枚举: 存储或取出的数据类型
     */
    enum DataType {
        INTEGER, LONG, BOOLEAN, FLOAT, STRING, STRING_SET
    }

    // =

    /**
     * 保存一个数据
     * @param key
     * @param value
     */
    <T> void put(String key, T value);

    /**
     * 保存一个 Map 集合(只能是 Integer, Long, Boolean, Float, String, Set)
     * @param map
     */
    <T> void putAll(Map<String, T> map);

    /**
     * 保存一个List集合
     * @param key
     * @param list
     */
    void putAll(String key, List<String> list);

    /**
     * 保存一个 List 集合, 并且自定义保存顺序
     * @param key
     * @param list
     * @param comparator
     */
    void putAll(String key, List<String> list, Comparator<String> comparator);

    /**
     * 根据 key 取出一个数据
     * @param key
     */
    <T> T get(String key, DataType type);

    /**
     * 取出全部数据
     * @return
     */
    Map<String, ?> getAll();

    /**
     * 取出一个 List 集合
     * @param key
     * @return
     */
    List<String> getAll(String key);

    /**
     * 移除一个数据
     * @param key
     * @return
     */
    void remove(String key);

    /**
     * 移除一个集合的数据
     * @param keys
     * @return
     */
    void removeAll(List<String> keys);

    /**
     * 移除一个数组的数据
     * @param keys
     * @return
     */
    void removeAll(String[] keys);

    /**
     * 是否存在 key
     * @return
     */
    boolean contains(String key);

    /**
     * 清除全部数据
     */
    void clear();

    // =

    /**
     * 获取 int 类型的数据
     * @return
     */
    int getInt(String key);

    /**
     * 获取 float 类型的数据
     * @param key
     * @return
     */
    float getFloat(String key);

    /**
     * 获取 long 类型的数据
     * @param key
     * @return
     */
    long getLong(String key);


    /**
     * 获取 boolean 类型的数据
     * @param key
     * @return
     */
    boolean getBoolean(String key);

    /**
     * 获取 String 类型的数据
     * @param key
     * @return
     */
    String getString(String key);

    /**
     * 获取 Set 类型的数据
     * @param key
     * @return
     */
    Set<String> getSet(String key);
}
