package dev.utils.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.utils.JCLogUtils;

/**
 * detail: Map 工具类
 * @author Ttt
 * @see <a href="https://mp.weixin.qq.com/s/DmRmazyp-EmTQPXdfw9wpQ"/>
 * @see <a href="http://bookshadow.com/weblog/2016/10/27/java-linked-hash-map-get-first-and-get-last/"/>
 */
public final class MapUtils {

    private MapUtils() {
    }

    // 日志 TAG
    private static final String TAG = MapUtils.class.getSimpleName();

    // =======
    // = Map =
    // =======

    /**
     * 判断 Map 是否为 null
     * @param map Map
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final Map map) {
        return (map == null || map.size() == 0);
    }

    /**
     * 判断 Map 是否不为 null
     * @param map Map
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final Map map) {
        return (map != null && map.size() != 0);
    }

    // ============
    // = 判断长度 =
    // ============

    /**
     * 获取 Map 长度
     * @param map Map
     * @return 如果 Map 为 null, 则返回默认长度, 如果不为 null, 则返回 map.size()
     */
    public static int length(final Map map) {
        return length(map, 0);
    }

    /**
     * 获取 Map 长度
     * @param map           Map
     * @param defaultLength 集合为 null 默认长度
     * @return 如果 Map 为 null, 则返回 defaultLength, 如果不为 null, 则返回 map.size()
     */
    public static int length(final Map map, final int defaultLength) {
        return map != null ? map.size() : defaultLength;
    }

    // =

    /**
     * 获取长度 to Map 是否等于期望长度
     * @param map    Map
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final Map map, final int length) {
        return map != null && map.size() == length;
    }

    // =

    /**
     * 判断 Map 长度是否大于指定长度
     * @param map    Map
     * @param length 指定长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean greaterThan(final Map map, final int length) {
        return map != null && map.size() > length;
    }

    /**
     * 判断 Map 长度是否大于等于指定长度
     * @param map    Map
     * @param length 指定长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean greaterThanOrEqual(final Map map, final int length) {
        return map != null && map.size() >= length;
    }

    // =

    /**
     * 判断 Map 长度是否小于指定长度
     * @param map    Map
     * @param length 指定长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean lessThan(final Map map, final int length) {
        return map != null && map.size() < length;
    }

    /**
     * 判断 Map 长度是否小于等于指定长度
     * @param map    Map
     * @param length 指定长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean lessThanOrEqual(final Map map, final int length) {
        return map != null && map.size() <= length;
    }

    // ============
    // = 数据获取 =
    // ============

    /**
     * 获取 Value
     * @param map Map
     * @param key key
     * @param <K> key
     * @param <V> value
     * @return 指定 key 的 value
     */
    public static <K, V> V get(final Map<K, V> map, final K key) {
        if (map != null) {
            try {
                return map.get(key);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get");
            }
        }
        return null;
    }

    /**
     * 获取 Value, 如果 Value 为 null, 则返回 defaultValue
     * @param map          Map
     * @param key          key
     * @param defaultValue 默认value
     * @param <K>          key
     * @param <V>          value
     * @return 指定 key 的 value, 如果 value 为 null, 则返回 defaultValue
     */
    public static <K, V> V get(final Map<K, V> map, final K key, final V defaultValue) {
        if (map != null) {
            try {
                V value = map.get(key);
                if (value == null) {
                    return defaultValue;
                } else {
                    return value;
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get");
            }
        }
        return null;
    }

    /**
     * 通过 value 获取 Key
     * @param map   Map
     * @param value Value
     * @param <K>   key
     * @param <V>   value
     * @return 等于 value 的 key
     */
    public static <K, V> K getKeyByValue(final Map<K, V> map, final V value) {
        if (map != null) {
            try {
                List<K> lists = new ArrayList<>();
                // 进行遍历判断
                Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<K, V> entry = iterator.next();
                    V v = entry.getValue();
                    if (equals(v, value)) {
                        return entry.getKey();
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getKeyByValue");
            }
        }
        return null;
    }

    /**
     * 通过 value 获取 Key 集合 (返回等于 value 的 key 集合)
     * @param map   Map
     * @param value Value
     * @param <K>   key
     * @param <V>   value
     * @return 等于 value 的 key 集合
     */
    public static <K, V> List<K> getKeysByValue(final Map<K, V> map, final V value) {
        if (map != null) {
            try {
                List<K> lists = new ArrayList<>();
                // 进行遍历判断
                Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<K, V> entry = iterator.next();
                    V v = entry.getValue();
                    if (equals(v, value)) {
                        // 保存 key
                        lists.add(entry.getKey());
                    }
                }
                // 返回 key 集合
                return lists;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getKeysByValue");
            }
        }
        return null;
    }

    // =

    /**
     * 通过 Map 获取 Key 集合
     * @param map Map
     * @param <K> key
     * @param <V> value
     * @return 全部存储 key 集合
     */
    public static <K, V> List<K> getKeys(final Map<K, V> map) {
        if (map != null) {
            try {
                return new ArrayList<>(map.keySet());
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getKeys");
            }
        }
        return null;
    }

    /**
     * 通过 Map 获取 Key 数组
     * @param map Map
     * @param <K> key
     * @param <V> value
     * @return 全部存储 key 数组
     */
    public static <K, V> K[] getKeysToArrays(final Map<K, V> map) {
        if (map != null) {
            try {
                return (K[]) getKeys(map).toArray();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getKeysToArrays");
            }
        }
        return null;
    }

    /**
     * 通过 Map 获取 Value 集合
     * @param map Map
     * @param <K> key
     * @param <V> value
     * @return 全部存储 value 数组
     */
    public static <K, V> List<V> getValues(final Map<K, V> map) {
        if (map != null) {
            try {
                return new ArrayList<>(map.values());
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getValues");
            }
        }
        return null;
    }

    /**
     * 通过 Map 获取 Value 数组
     * @param map Map
     * @param <K> key
     * @param <V> value
     * @return 全部存储 value 数组
     */
    public static <K, V> V[] getValuesToArrays(final Map<K, V> map) {
        if (map != null) {
            try {
                return (V[]) getValues(map).toArray();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getValuesToArrays");
            }
        }
        return null;
    }

    // =

    /**
     * 获取第一条数据
     * @param map Map
     * @param <K> key
     * @param <V> value
     * @return 第一条数据 {@link Map.Entry}
     */
    public static <K, V> Map.Entry<K, V> getFirst(final LinkedHashMap<K, V> map) {
        if (map != null) {
            try {
                return map.entrySet().iterator().next();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getFirst");
            }
        }
        return null;
    }

    /**
     * 获取最后一条数据
     * @param map Map
     * @param <K> key
     * @param <V> value
     * @return 最后一条数据 {@link Map.Entry}
     */
    public static <K, V> Map.Entry<K, V> getLast(final LinkedHashMap<K, V> map) {
        return getLast(map, true);
    }

    /**
     * 获取最后一条数据
     * <pre>
     *      @see <a href="http://bookshadow.com/weblog/2016/10/27/java-linked-hash-map-get-first-and-get-last/"/>
     * </pre>
     * @param map          Map
     * @param isReflection 是否通过反射获取
     * @param <K>          key
     * @param <V>          value
     * @return 最后一条数据 {@link Map.Entry}
     */
    public static <K, V> Map.Entry<K, V> getLast(final LinkedHashMap<K, V> map, final boolean isReflection) {
        if (map != null) {
            if (isReflection) {
                try {
                    // 反射方式
                    Field tail = map.getClass().getDeclaredField("tail");
                    tail.setAccessible(true);
                    return (Map.Entry<K, V>) tail.get(map);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "getLast");
                }
            } else {
                try {
                    // 遍历方式
                    Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
                    Map.Entry<K, V> tail = null;
                    while (iterator.hasNext()) {
                        tail = iterator.next();
                    }
                    return tail;
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "getLast");
                }
            }
        }
        return null;
    }

    // =

    /**
     * 根据指定 key 获取 key 所在位置的下一条数据
     * @param map Map
     * @param key key
     * @param <K> key
     * @param <V> value
     * @return 指定 key 下一条数据 {@link Map.Entry}
     */
    public static <K, V> Map.Entry<K, V> getNext(final LinkedHashMap<K, V> map, final K key) {
        if (map != null) {
            try {
                // 进行遍历判断
                Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<K, V> entry = iterator.next();
                    K k = entry.getKey();
                    // 判断 key 是否相同
                    if (equals(k, key)) {
                        return entry;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getNext");
            }
        }
        return null;
    }

    /**
     * 根据指定 key 获取 key 所在位置的上一条数据
     * @param map Map
     * @param key key
     * @param <K> key
     * @param <V> value
     * @return 指定 key 上一条数据 {@link Map.Entry}
     */
    public static <K, V> Map.Entry<K, V> getPrevious(final LinkedHashMap<K, V> map, final K key) {
        if (map != null) {
            try {
                // 进行遍历判断
                Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
                // 临时保存处理
                Map.Entry<K, V> temp = null;
                // 循环处理
                while (iterator.hasNext()) {
                    Map.Entry<K, V> entry = iterator.next();
                    K k = entry.getKey();
                    // 判断 key 是否相同
                    if (equals(k, key)) {
                        return temp;
                    }
                    // 赋值上一条数据
                    temp = entry;
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getPrevious");
            }
        }
        return null;
    }

    // ============
    // = 添加数据 =
    // ============

    /**
     * 添加一条数据
     * @param map   Map
     * @param key   key
     * @param value value
     * @param <K>   key
     * @param <V>   value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean put(final Map<K, V> map, final K key, final V value) {
        return put(map, key, value, false);
    }

    /**
     * 添加一条数据
     * @param map     Map
     * @param key     key
     * @param value   value
     * @param notNull 是否不允许 key 为 null
     * @param <K>     key
     * @param <V>     value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean put(final Map<K, V> map, final K key, final V value, final boolean notNull) {
        if (map != null) {
            if (notNull && key == null) {
                return false;
            }
            try {
                map.put(key, value);
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "put");
            }
        }
        return false;
    }

    /**
     * 添加一条数据, 不允许 key 为 null
     * @param map   Map
     * @param key   key
     * @param value value
     * @param <K>   key
     * @param <V>   value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putNotNull(final Map<K, V> map, final K key, final V value) {
        return put(map, key, value, true);
    }

    // =

    /**
     * 添加一条数据
     * @param map   Map
     * @param entry entry
     * @param <K>   key
     * @param <V>   value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean put(final Map<K, V> map, final Map.Entry<K, V> entry) {
        return put(map, entry, false);
    }

    /**
     * 添加一条数据
     * @param map     Map
     * @param entry   entry
     * @param notNull 是否不允许 key 为 null
     * @param <K>     key
     * @param <V>     value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean put(final Map<K, V> map, final Map.Entry<K, V> entry, final boolean notNull) {
        if (map != null && entry != null) {
            if (notNull && entry.getKey() == null) {
                return false;
            }
            try {
                map.put(entry.getKey(), entry.getValue());
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "put");
            }
        }
        return false;
    }

    /**
     * 添加一条数据, 不允许 key 为 null
     * @param map   Map
     * @param entry entry
     * @param <K>   key
     * @param <V>   value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putNotNull(final Map<K, V> map, final Map.Entry<K, V> entry) {
        return put(map, entry, true);
    }

    // =

    /**
     * 添加多条数据
     * @param map        Map
     * @param listKeys   keys
     * @param listValues values
     * @param <K>        key
     * @param <V>        value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAll(final Map<K, V> map, final List<K> listKeys, final List<V> listValues) {
        return putAll(map, listKeys, listValues, false);
    }

    /**
     * 添加多条数据
     * @param map        Map
     * @param listKeys   keys
     * @param listValues values
     * @param notNull    是否不允许 key 为 null
     * @param <K>        key
     * @param <V>        value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAll(final Map<K, V> map, final List<K> listKeys, final List<V> listValues, final boolean notNull) {
        if (map != null && listKeys != null && listValues != null && listKeys.size() == listValues.size()) {
            try {
                // 循环保存
                for (int i = 0, len = listKeys.size(); i < len; i++) {
                    K key = listKeys.get(i);
                    if (notNull && key == null) {
                        continue; // 忽略进行下一个
                    }
                    // 添加数据
                    map.put(key, listValues.get(i));
                }
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "putAll");
            }
        }
        return false;
    }

    /**
     * 添加多条数据, 不允许 key 为 null
     * @param map        Map
     * @param listKeys   keys
     * @param listValues values
     * @param <K>        key
     * @param <V>        value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAllNotNull(final Map<K, V> map, final List<K> listKeys, final List<V> listValues) {
        return putAll(map, listKeys, listValues, true);
    }

    // =

    /**
     * 添加多条数据
     * @param map    Map
     * @param keys   keys
     * @param values values
     * @param <K>    key
     * @param <V>    value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAll(final Map<K, V> map, final K[] keys, final V[] values) {
        return putAll(map, keys, values, false);
    }

    /**
     * 添加多条数据
     * @param map     Map
     * @param keys    keys
     * @param values  values
     * @param notNull 是否不允许 key 为 null
     * @param <K>     key
     * @param <V>     value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAll(final Map<K, V> map, final K[] keys, final V[] values, final boolean notNull) {
        if (map != null && keys != null && values != null && keys.length == values.length) {
            try {
                // 循环保存
                for (int i = 0, len = keys.length; i < len; i++) {
                    K key = keys[i];
                    if (notNull && key == null) {
                        continue; // 忽略进行下一个
                    }
                    // 添加数据
                    map.put(key, values[i]);
                }
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "putAll");
            }
        }
        return false;
    }

    /**
     * 添加多条数据, 不允许 key 为 null
     * @param map    Map
     * @param keys   keys
     * @param values values
     * @param <K>    key
     * @param <V>    value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAllNotNull(final Map<K, V> map, final K[] keys, final V[] values) {
        return putAll(map, keys, values, true);
    }

    // =

    /**
     * 添加多条数据
     * @param map     Map
     * @param mapData map数据
     * @param <K>     key
     * @param <V>     value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAll(final Map<K, V> map, final Map<K, V> mapData) {
        return putAll(map, mapData, false);
    }

    /**
     * 添加多条数据
     * @param map     Map
     * @param mapData map数据
     * @param notNull 是否不允许 key 为 null
     * @param <K>     key
     * @param <V>     value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAll(final Map<K, V> map, final Map<K, V> mapData, final boolean notNull) {
        if (map != null && mapData != null) {
            if (notNull) {
                try {
                    // 进行遍历判断
                    Iterator<Map.Entry<K, V>> iterator = mapData.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<K, V> entry = iterator.next();
                        K key = entry.getKey();
                        if (key != null) {
                            // 保存数据
                            map.put(key, entry.getValue());
                        }
                    }
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "putAll");
                }
            } else {
                try {
                    map.putAll(mapData);
                    return true;
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "putAll");
                }
            }
        }
        return false;
    }

    /**
     * 添加多条数据, 不允许 key 为 null
     * @param map     Map
     * @param mapData map数据
     * @param <K>     key
     * @param <V>     value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAllNotNull(final Map<K, V> map, final Map<K, V> mapData) {
        return putAll(map, mapData, true);
    }

    // ============
    // = 删除数据 =
    // ============

    /**
     * 移除一条数据
     * @param map Map
     * @param key key
     * @param <K> key
     * @param <V> value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean remove(final Map<K, V> map, final K key) {
        if (map != null) {
            try {
                map.remove(key);
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "remove");
            }
        }
        return false;
    }

    /**
     * 移除一条数据
     * @param map   Map
     * @param key   key
     * @param value value
     * @param <K>   key
     * @param <V>   value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean remove(final Map<K, V> map, final K key, final V value) {
        if (map != null) {
            try {
                // 判断值是否一样, 一样则移除 key
                if (equals(value, map.get(key))) {
                    map.remove(key);
                }
                //return map.remove(key, value);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "remove");
            }
        }
        return false;
    }

    /**
     * 移除多条数据
     * @param map  Map
     * @param keys keys
     * @param <K>  key
     * @param <V>  value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean removeToKeys(final Map<K, V> map, final Collection<K> keys) {
        if (map != null && keys != null) {
            try {
                for (K key : keys) {
                    map.remove(key);
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "removeToKeys");
            }
        }
        return false;
    }

    /**
     * 移除等于 value 的所有数据
     * @param map   Map
     * @param value value
     * @param <K>   key
     * @param <V>   value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean removeToValue(final Map<K, V> map, final V value) {
        if (map != null) {
            try {
                // 进行遍历判断
                Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<K, V> entry = iterator.next();
                    V v = entry.getValue();
                    if (equals(v, value)) {
                        iterator.remove();
                    }
                }
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "removeToValue");
            }
        }
        return false;
    }

    /**
     * 移除等于 value 的所有数据 (Collection<Value>)
     * @param map    Map
     * @param values values
     * @param <K>    key
     * @param <V>    value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean removeToValues(final Map<K, V> map, final Collection<V> values) {
        if (map != null && values != null) {
            try {
                for (V value : values) {
                    removeToValue(map, value);
                }
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "removeToValues");
            }
        }
        return false;
    }

    // ============
    // = 快捷判断 =
    // ============

    /**
     * 判断两个值是否一样
     * @param value1 第一个值
     * @param value2 第二个值
     * @param <T>    泛型
     * @return {@code true} yes, {@code false} no
     */
    public static <T> boolean equals(final T value1, final T value2) {
        // 两个值都不为 null
        if (value1 != null && value2 != null) {
            try {
                if (value1 instanceof String && value2 instanceof String) {
                    return value1.equals(value2);
                } else if (value1 instanceof CharSequence && value2 instanceof CharSequence) {
                    CharSequence v1 = (CharSequence) value1;
                    CharSequence v2 = (CharSequence) value2;
                    // 获取数据长度
                    int length = v1.length();
                    // 判断数据长度是否一致
                    if (length == v2.length()) {
                        for (int i = 0; i < length; i++) {
                            if (v1.charAt(i) != v2.charAt(i)) {
                                return false;
                            }
                        }
                        return true;
                    }
                    return false;
                }
                // 其他都使用 equals 判断
                return value1.equals(value2);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "equals");
            }
            return false;
        }
        // 防止两个值都为null
        return (value1 == null && value2 == null);
    }

    // =

    /**
     * 切换保存状态
     * 1.如果存在, 则删除
     * 2.如果不存在, 则保存
     * @param map   Map
     * @param key   key
     * @param value value
     */
    public static <K, V> void toggle(final Map<K, V> map, final K key, final V value) {
        if (map != null) {
            // 判断是否存在 key
            boolean existKey = map.containsKey(key);
            if (existKey) { // 存在则删除
                map.remove(key);
            } else { // 不存在则保存
                map.put(key, value);
            }
        }
    }

    /**
     * 判断指定 key 的 value 是否为 null
     * @param map Map
     * @param key key
     * @param <K> key
     * @param <V> value
     * @return {@code true} yes, {@code false} no
     */
    public static <K, V> boolean isNullToValue(final Map<K, V> map, final K key) {
        if (map != null) {
            return map.get(key) == null;
        }
        return true;
    }

    /**
     * 判断 Map 是否存储了 key
     * @param map Map
     * @param key key
     * @param <K> key
     * @param <V> value
     * @return {@code true} yes, {@code false} no
     */
    public static <K, V> boolean containsKey(final Map<K, V> map, final K key) {
        if (map != null) {
            try {
                return map.containsKey(key);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "containsKey");
            }
        }
        return false;
    }

    /**
     * 判断 Map 是否存储了 value
     * @param map   Map
     * @param value value
     * @param <K>   key
     * @param <V>   value
     * @return {@code true} yes, {@code false} no
     */
    public static <K, V> boolean containsValue(final Map<K, V> map, final V value) {
        if (map != null) {
            try {
                return map.containsValue(value);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "containsValue");
            }
        }
        return false;
    }
}
