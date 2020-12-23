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
 * <pre>
 *     @see <a href="https://mp.weixin.qq.com/s/DmRmazyp-EmTQPXdfw9wpQ"/>
 * </pre>
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
     * @param map {@link Map}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final Map map) {
        return (map == null || map.size() == 0);
    }

    /**
     * 判断 Map 是否不为 null
     * @param map {@link Map}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final Map map) {
        return (map != null && map.size() != 0);
    }

    // ===========
    // = 判断长度 =
    // ===========

    /**
     * 获取 Map 长度
     * @param map {@link Map}
     * @return 如果 Map 为 null, 则返回默认长度, 如果不为 null, 则返回 map.size()
     */
    public static int length(final Map map) {
        return length(map, 0);
    }

    /**
     * 获取 Map 长度
     * @param map           {@link Map}
     * @param defaultLength 集合为 null 默认长度
     * @return 如果 Map 为 null, 则返回 defaultLength, 如果不为 null, 则返回 map.size()
     */
    public static int length(
            final Map map,
            final int defaultLength
    ) {
        return map != null ? map.size() : defaultLength;
    }

    // =

    /**
     * 获取长度 Map 是否等于期望长度
     * @param map    {@link Map}
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(
            final Map map,
            final int length
    ) {
        return map != null && map.size() == length;
    }

    // =

    /**
     * 判断 Map 长度是否大于指定长度
     * @param map    {@link Map}
     * @param length 指定长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean greaterThan(
            final Map map,
            final int length
    ) {
        return map != null && map.size() > length;
    }

    /**
     * 判断 Map 长度是否大于等于指定长度
     * @param map    {@link Map}
     * @param length 指定长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean greaterThanOrEqual(
            final Map map,
            final int length
    ) {
        return map != null && map.size() >= length;
    }

    // =

    /**
     * 判断 Map 长度是否小于指定长度
     * @param map    {@link Map}
     * @param length 指定长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean lessThan(
            final Map map,
            final int length
    ) {
        return map != null && map.size() < length;
    }

    /**
     * 判断 Map 长度是否小于等于指定长度
     * @param map    {@link Map}
     * @param length 指定长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean lessThanOrEqual(
            final Map map,
            final int length
    ) {
        return map != null && map.size() <= length;
    }

    // ===============
    // = 获取长度总和 =
    // ===============

    /**
     * 获取 Map 数组长度总和
     * @param maps Map[]
     * @return Map 数组长度总和
     */
    public static int getCount(final Map... maps) {
        if (maps == null) return 0;
        int count = 0;
        for (Map map : maps) {
            count += length(map);
        }
        return count;
    }

    // ===========
    // = 数据获取 =
    // ===========

    /**
     * 获取 value
     * @param map {@link Map}
     * @param key key
     * @param <K> key
     * @param <V> value
     * @return 指定 key 的 value
     */
    public static <K, V> V get(
            final Map<K, V> map,
            final K key
    ) {
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
     * 获取 value 如果 value 为 null, 则返回 defaultValue
     * @param map          {@link Map}
     * @param key          key
     * @param defaultValue 默认 value
     * @param <K>          key
     * @param <V>          value
     * @return 指定 key 的 value 如果 value 为 null, 则返回 defaultValue
     */
    public static <K, V> V get(
            final Map<K, V> map,
            final K key,
            final V defaultValue
    ) {
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
     * 通过 value 获取 key
     * @param map   {@link Map}
     * @param value Value
     * @param <K>   key
     * @param <V>   value
     * @return 等于 value 的 key
     */
    public static <K, V> K getKeyByValue(
            final Map<K, V> map,
            final V value
    ) {
        if (map != null) {
            try {
                // 进行遍历判断
                Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<K, V> entry = iterator.next();
                    V               v     = entry.getValue();
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
     * 通过 value 获取 key 集合 ( 返回等于 value 的 key 集合 )
     * @param map   {@link Map}
     * @param value Value
     * @param <K>   key
     * @param <V>   value
     * @return 等于 value 的 key 集合
     */
    public static <K, V> List<K> getKeysByValue(
            final Map<K, V> map,
            final V value
    ) {
        if (map != null) {
            try {
                List<K> lists = new ArrayList<>();
                // 进行遍历判断
                Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<K, V> entry = iterator.next();
                    V               v     = entry.getValue();
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
     * 通过 Map 获取 key 集合
     * @param map {@link Map}
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
     * 通过 Map 获取 key 数组
     * @param map {@link Map}
     * @param <K> key
     * @param <V> value
     * @return 全部存储 key 数组
     */
    public static <K, V> K[] getKeysToArrays(final Map<K, V> map) {
        if (map != null) {
            try {
                return CollectionUtils.toArrayT(getKeys(map));
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getKeysToArrays");
            }
        }
        return null;
    }

    /**
     * 通过 Map 获取 value 集合
     * @param map {@link Map}
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
     * 通过 Map 获取 value 数组
     * @param map {@link Map}
     * @param <K> key
     * @param <V> value
     * @return 全部存储 value 数组
     */
    public static <K, V> V[] getValuesToArrays(final Map<K, V> map) {
        if (map != null) {
            try {
                return CollectionUtils.toArrayT(getValues(map));
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getValuesToArrays");
            }
        }
        return null;
    }

    // =

    /**
     * 获取第一条数据
     * @param map {@link Map}
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
     * @param map {@link Map}
     * @param <K> key
     * @param <V> value
     * @return 最后一条数据 {@link Map.Entry}
     */
    public static <K, V> Map.Entry<K, V> getLast(final LinkedHashMap<K, V> map) {
        return getLast(map, true);
    }

    /**
     * 获取最后一条数据
     * @param map          {@link Map}
     * @param isReflection 是否使用反射
     * @param <K>          key
     * @param <V>          value
     * @return 最后一条数据 {@link Map.Entry}
     */
    public static <K, V> Map.Entry<K, V> getLast(
            final LinkedHashMap<K, V> map,
            final boolean isReflection
    ) {
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
                    Map.Entry<K, V>           tail     = null;
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
     * @param map {@link Map}
     * @param key key
     * @param <K> key
     * @param <V> value
     * @return 指定 key 下一条数据 {@link Map.Entry}
     */
    public static <K, V> Map.Entry<K, V> getNext(
            final LinkedHashMap<K, V> map,
            final K key
    ) {
        if (map != null) {
            try {
                // 进行遍历判断
                Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<K, V> entry = iterator.next();
                    K               k     = entry.getKey();
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
     * @param map {@link Map}
     * @param key key
     * @param <K> key
     * @param <V> value
     * @return 指定 key 上一条数据 {@link Map.Entry}
     */
    public static <K, V> Map.Entry<K, V> getPrevious(
            final LinkedHashMap<K, V> map,
            final K key
    ) {
        if (map != null) {
            try {
                // 进行遍历判断
                Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
                // 临时保存处理
                Map.Entry<K, V> temp = null;
                // 循环处理
                while (iterator.hasNext()) {
                    Map.Entry<K, V> entry = iterator.next();
                    K               k     = entry.getKey();
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

    // ===========
    // = 添加数据 =
    // ===========

    /**
     * 添加一条数据
     * @param map   {@link Map}
     * @param key   key
     * @param value value
     * @param <K>   key
     * @param <V>   value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean put(
            final Map<K, V> map,
            final K key,
            final V value
    ) {
        return put(map, key, value, false);
    }

    /**
     * 添加一条数据
     * @param map     {@link Map}
     * @param key     key
     * @param value   value
     * @param notNull 是否不允许 key 为 null
     * @param <K>     key
     * @param <V>     value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean put(
            final Map<K, V> map,
            final K key,
            final V value,
            final boolean notNull
    ) {
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
     * 添加一条数据 ( 不允许 key 为 null )
     * @param map   {@link Map}
     * @param key   key
     * @param value value
     * @param <K>   key
     * @param <V>   value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putNotNull(
            final Map<K, V> map,
            final K key,
            final V value
    ) {
        return put(map, key, value, true);
    }

    // =

    /**
     * 添加一条数据
     * @param map   {@link Map}
     * @param entry entry
     * @param <K>   key
     * @param <V>   value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean put(
            final Map<K, V> map,
            final Map.Entry<K, V> entry
    ) {
        return put(map, entry, false);
    }

    /**
     * 添加一条数据
     * @param map     {@link Map}
     * @param entry   entry
     * @param notNull 是否不允许 key 为 null
     * @param <K>     key
     * @param <V>     value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean put(
            final Map<K, V> map,
            final Map.Entry<K, V> entry,
            final boolean notNull
    ) {
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
     * 添加一条数据 ( 不允许 key 为 null )
     * @param map   {@link Map}
     * @param entry entry
     * @param <K>   key
     * @param <V>   value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putNotNull(
            final Map<K, V> map,
            final Map.Entry<K, V> entry
    ) {
        return put(map, entry, true);
    }

    // =

    /**
     * 添加多条数据
     * @param map        {@link Map}
     * @param listKeys   keys
     * @param listValues values
     * @param <K>        key
     * @param <V>        value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAll(
            final Map<K, V> map,
            final List<K> listKeys,
            final List<V> listValues
    ) {
        return putAll(map, listKeys, listValues, false);
    }

    /**
     * 添加多条数据
     * @param map        {@link Map}
     * @param listKeys   keys
     * @param listValues values
     * @param notNull    是否不允许 key 为 null
     * @param <K>        key
     * @param <V>        value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAll(
            final Map<K, V> map,
            final List<K> listKeys,
            final List<V> listValues,
            final boolean notNull
    ) {
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
     * @param map        {@link Map}
     * @param listKeys   keys
     * @param listValues values
     * @param <K>        key
     * @param <V>        value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAllNotNull(
            final Map<K, V> map,
            final List<K> listKeys,
            final List<V> listValues
    ) {
        return putAll(map, listKeys, listValues, true);
    }

    // =

    /**
     * 添加多条数据
     * @param map    {@link Map}
     * @param keys   keys
     * @param values values
     * @param <K>    key
     * @param <V>    value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAll(
            final Map<K, V> map,
            final K[] keys,
            final V[] values
    ) {
        return putAll(map, keys, values, false);
    }

    /**
     * 添加多条数据
     * @param map     {@link Map}
     * @param keys    keys
     * @param values  values
     * @param notNull 是否不允许 key 为 null
     * @param <K>     key
     * @param <V>     value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAll(
            final Map<K, V> map,
            final K[] keys,
            final V[] values,
            final boolean notNull
    ) {
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
     * @param map    {@link Map}
     * @param keys   keys
     * @param values values
     * @param <K>    key
     * @param <V>    value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAllNotNull(
            final Map<K, V> map,
            final K[] keys,
            final V[] values
    ) {
        return putAll(map, keys, values, true);
    }

    // =

    /**
     * 添加多条数据
     * @param map     {@link Map}
     * @param mapData map 数据
     * @param <K>     key
     * @param <V>     value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAll(
            final Map<K, V> map,
            final Map<K, V> mapData
    ) {
        return putAll(map, mapData, false);
    }

    /**
     * 添加多条数据
     * @param map     {@link Map}
     * @param mapData map 数据
     * @param notNull 是否不允许 key 为 null
     * @param <K>     key
     * @param <V>     value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAll(
            final Map<K, V> map,
            final Map<K, V> mapData,
            final boolean notNull
    ) {
        if (map != null && mapData != null) {
            if (notNull) {
                try {
                    // 进行遍历判断
                    Iterator<Map.Entry<K, V>> iterator = mapData.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<K, V> entry = iterator.next();
                        K               key   = entry.getKey();
                        if (key != null) {
                            // 保存数据
                            map.put(key, entry.getValue());
                        }
                    }
                    return true;
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
     * @param map     {@link Map}
     * @param mapData map 数据
     * @param <K>     key
     * @param <V>     value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean putAllNotNull(
            final Map<K, V> map,
            final Map<K, V> mapData
    ) {
        return putAll(map, mapData, true);
    }

    // ===========
    // = 删除数据 =
    // ===========

    /**
     * 移除一条数据
     * @param map {@link Map}
     * @param key key
     * @param <K> key
     * @param <V> value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean remove(
            final Map<K, V> map,
            final K key
    ) {
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
     * @param map   {@link Map}
     * @param key   key
     * @param value value
     * @param <K>   key
     * @param <V>   value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean remove(
            final Map<K, V> map,
            final K key,
            final V value
    ) {
        if (map != null) {
            try {
                // 判断值是否一样, 一样则移除 key
                if (equals(value, map.get(key))) {
                    map.remove(key);
                }
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "remove");
            }
        }
        return false;
    }

    /**
     * 移除多条数据
     * @param map  {@link Map}
     * @param keys keys
     * @param <K>  key
     * @param <V>  value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean removeToKeys(
            final Map<K, V> map,
            final Collection<K> keys
    ) {
        if (map != null && keys != null) {
            try {
                for (K key : keys) {
                    map.remove(key);
                }
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "removeToKeys");
            }
        }
        return false;
    }

    /**
     * 移除等于 value 的所有数据
     * @param map   {@link Map}
     * @param value value
     * @param <K>   key
     * @param <V>   value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean removeToValue(
            final Map<K, V> map,
            final V value
    ) {
        if (map != null) {
            try {
                // 进行遍历判断
                Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<K, V> entry = iterator.next();
                    V               v     = entry.getValue();
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
     * @param map    {@link Map}
     * @param values values
     * @param <K>    key
     * @param <V>    value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean removeToValues(
            final Map<K, V> map,
            final Collection<V> values
    ) {
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

    // ===========
    // = 快捷判断 =
    // ===========

    /**
     * 判断两个值是否一样
     * @param value1 第一个值
     * @param value2 第二个值
     * @param <T>    泛型
     * @return {@code true} yes, {@code false} no
     */
    public static <T> boolean equals(
            final T value1,
            final T value2
    ) {
        return ObjectUtils.equals(value1, value2);
    }

    // =

    /**
     * 切换保存状态
     * <pre>
     *     1. 如果存在, 则删除
     *     2. 如果不存在, 则保存
     * </pre>
     * @param map   {@link Map}
     * @param key   key
     * @param value value
     * @param <K>   key
     * @param <V>   value
     * @return {@code true} success, {@code false} fail
     */
    public static <K, V> boolean toggle(
            final Map<K, V> map,
            final K key,
            final V value
    ) {
        if (map != null) {
            // 判断是否存在 key
            boolean existKey = map.containsKey(key);
            try {
                if (existKey) { // 存在则删除
                    map.remove(key);
                } else { // 不存在则保存
                    map.put(key, value);
                }
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toggle");
            }
        }
        return false;
    }

    /**
     * 判断指定 key 的 value 是否为 null
     * @param map {@link Map}
     * @param key key
     * @param <K> key
     * @param <V> value
     * @return {@code true} yes, {@code false} no
     */
    public static <K, V> boolean isNullToValue(
            final Map<K, V> map,
            final K key
    ) {
        if (map != null) {
            return map.get(key) == null;
        }
        return true;
    }

    /**
     * 判断 Map 是否存储 key
     * @param map {@link Map}
     * @param key key
     * @param <K> key
     * @param <V> value
     * @return {@code true} yes, {@code false} no
     */
    public static <K, V> boolean containsKey(
            final Map<K, V> map,
            final K key
    ) {
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
     * 判断 Map 是否存储 value
     * @param map   {@link Map}
     * @param value value
     * @param <K>   key
     * @param <V>   value
     * @return {@code true} yes, {@code false} no
     */
    public static <K, V> boolean containsValue(
            final Map<K, V> map,
            final V value
    ) {
        if (map != null) {
            try {
                return map.containsValue(value);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "containsValue");
            }
        }
        return false;
    }

    // ================
    // = 特殊 Map 操作 =
    // ================

    /**
     * 添加一条数据
     * @param map   待添加 {@link Map}
     * @param key   key
     * @param value value, add to list
     * @param <K>   key
     * @param <T>   value type
     * @return {@code true} success, {@code false} fail
     */
    public static <K, T> boolean putToList(
            final Map<K, List<T>> map,
            final K key,
            final T value
    ) {
        return putToList(map, key, value, true);
    }

    /**
     * 添加一条数据
     * @param map   {@link Map}
     * @param key   key
     * @param value value, add to list
     * @param isNew 当指定 (key) 的 value 为 null, 是否创建
     * @param <K>   key
     * @param <T>   value type
     * @return {@code true} success, {@code false} fail
     */
    public static <K, T> boolean putToList(
            final Map<K, List<T>> map,
            final K key,
            final T value,
            final boolean isNew
    ) {
        if (map != null) {
            if (map.containsKey(key)) {
                List<T> lists = map.get(key);
                if (lists != null) {
                    try {
                        lists.add(value);
                        map.put(key, lists);
                        return true;
                    } catch (Exception e) {
                        JCLogUtils.eTag(TAG, e, "putToList");
                    }
                }
            } else {
                // 判断是否创建
                if (isNew) {
                    try {
                        List<T> lists = new ArrayList<>();
                        lists.add(value);
                        map.put(key, lists);
                        return true;
                    } catch (Exception e) {
                        JCLogUtils.eTag(TAG, e, "putToList");
                    }
                }
            }
        }
        return false;
    }

    // =

    /**
     * 移除一条数据
     * @param map {@link Map}
     * @param key key
     * @param <K> key
     * @param <T> value type
     * @return {@code true} success, {@code false} fail
     */
    public static <K, T> boolean removeToList(
            final Map<K, List<T>> map,
            final K key
    ) {
        if (map != null) {
            try {
                map.remove(key);
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "removeToList");
            }
        }
        return false;
    }

    /**
     * 移除一条数据
     * @param map   {@link Map}
     * @param key   key
     * @param value value, remove to list
     * @param <K>   key
     * @param <T>   value type
     * @return {@code true} success, {@code false} fail
     */
    public static <K, T> boolean removeToList(
            final Map<K, List<T>> map,
            final K key,
            final T value
    ) {
        if (map != null) {
            if (map.containsKey(key)) {
                List<T> lists = map.get(key);
                if (lists != null) {
                    try {
                        lists.remove(value);
                        return true;
                    } catch (Exception e) {
                        JCLogUtils.eTag(TAG, e, "removeToList");
                    }
                }
            }
        }
        return false;
    }

    /**
     * 移除多条数据
     * @param map   {@link Map}
     * @param key   key
     * @param lists 删除的 list 数据源
     * @param <K>   key
     * @param <T>   value type
     * @return {@code true} success, {@code false} fail
     */
    public static <K, T> boolean removeToLists(
            final Map<K, List<T>> map,
            final K key,
            final List<T> lists
    ) {
        if (map != null && lists != null) {
            if (map.containsKey(key)) {
                List<T> list = map.get(key);
                if (list != null) {
                    try {
                        list.removeAll(lists);
                        return true;
                    } catch (Exception e) {
                        JCLogUtils.eTag(TAG, e, "removeToLists");
                    }
                }
            }
        }
        return false;
    }

    // =

    /**
     * 移除多条数据 ( 通过 Map 进行移除 )
     * @param map       {@link Map}
     * @param removeMap {@link Map} 移除对比数据源
     * @param <K>       key
     * @param <T>       value type
     * @return {@code true} success, {@code false} fail
     */
    public static <K, T> boolean removeToMap(
            final Map<K, List<T>> map,
            final Map<K, List<T>> removeMap
    ) {
        return removeToMap(map, removeMap, true, false);
    }

    /**
     * 移除多条数据 ( 通过 Map 进行移除 )
     * @param map             {@link Map}
     * @param removeMap       {@link Map} 移除对比数据源
     * @param removeEmpty     是否移除 null、长度为 0 的数据
     * @param isNullRemoveAll 如果待移除的 List 是 null, 是否移除全部
     * @param <K>             key
     * @param <T>             value type
     * @return {@code true} success, {@code false} fail
     */
    public static <K, T> boolean removeToMap(
            final Map<K, List<T>> map,
            final Map<K, List<T>> removeMap,
            final boolean removeEmpty,
            final boolean isNullRemoveAll
    ) {
        if (map != null && removeMap != null) {
            Iterator<Map.Entry<K, List<T>>> iterator = removeMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<K, List<T>> entry = iterator.next();
                // 获取 key
                K key = entry.getKey();
                // 进行移除处理
                if (map.containsKey(key)) {
                    List<T> value = entry.getValue();
                    try {
                        if (value != null) {
                            map.get(key).removeAll(value);
                        } else {
                            if (isNullRemoveAll) {
                                map.remove(key);
                            }
                        }
                    } catch (Exception e) {
                        JCLogUtils.eTag(TAG, e, "removeToMap - removeAll");
                    }
                    // 判断是否移除 null、长度为 0 的数据
                    if (removeEmpty) {
                        List<T> lists = map.get(key);
                        try {
                            // 不存在数据了, 则移除
                            if (lists == null || lists.size() == 0) {
                                map.remove(key);
                            }
                        } catch (Exception e) {
                            JCLogUtils.eTag(TAG, e, "removeToMap");
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}