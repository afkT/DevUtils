package dev.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * detail: 变量基类 ( 方便判断处理 )
 * @author Ttt
 */
public class DevBaseVariable<K, V> {

    // 存储数据 Map
    private LinkedHashMap<K, V> linkedHashMap = new LinkedHashMap<>();

    // ================
    // = 对外公开方法 =
    // ================

    /**
     * 获取全部变量数据
     * @return {@link LinkedHashMap}
     */
    public LinkedHashMap<K, V> getVariables() {
        return linkedHashMap;
    }

    /**
     * 清空全部变量数据
     * @return {@link DevBaseVariable}
     */
    public DevBaseVariable<K, V> clearVariables() {
        linkedHashMap.clear();
        return this;
    }

    /**
     * 保存变量数据集合
     * @param collection {@link LinkedHashMap}
     * @return {@link DevBaseVariable}
     */
    public DevBaseVariable<K, V> putVariables(final LinkedHashMap<K, V> collection) {
        if (collection != null) linkedHashMap.putAll(collection);
        return this;
    }

    /**
     * 获取变量总数
     * @return 变量总数
     */
    public int getVariablesSize() {
        return linkedHashMap.size();
    }

    /**
     * 判断是否存在变量数据
     * @return {@code true} yes, {@code false} no
     */
    public boolean isVariables() {
        return getVariablesSize() != 0;
    }

    // =

    /**
     * 判断是否存在变量 - 通过 value 判断
     * @param value Value
     * @return {@code true} yes, {@code false} no
     */
    public boolean isVariableValue(final V value) {
        return linkedHashMap.containsValue(value);
    }

    /**
     * 删除指定变量数据
     * @param value Value
     * @return {@link DevBaseVariable}
     */
    public DevBaseVariable<K, V> removeVariableValue(final V value) {
        Iterator<V> iterator = linkedHashMap.values().iterator();
        while (iterator.hasNext()) {
            V v = iterator.next();
            if (v == value) {
                iterator.remove();
                break;
            }
        }
        return this;
    }

    /**
     * 删除指定变量数据 - 符合条件的全部 value
     * @param value Value
     * @return {@link DevBaseVariable}
     */
    public DevBaseVariable<K, V> removeVariableValueAll(final V value) {
        Iterator<V> iterator = linkedHashMap.values().iterator();
        while (iterator.hasNext()) {
            V v = iterator.next();
            if (v == value) {
                iterator.remove();
            }
        }
        return this;
    }

    // =

    /**
     * 判断是否存在变量 - 通过 key 判断
     * @param key Key
     * @return {@code true} yes, {@code false} no
     */
    public boolean isVariable(final K key) {
        return linkedHashMap.containsKey(key);
    }

    /**
     * 判断是否存在变量 - 如果不存在, 则保存
     * @param key   Key
     * @param value Value
     * @return {@code true} yes, {@code false} no
     */
    public boolean isVariable(final K key, final V value) {
        if (!isVariable(key)) {
            linkedHashMap.put(key, value);
            return false;
        }
        return true;
    }

    // =

    /**
     * 保存变量数据
     * @param key   Key
     * @param value Value
     * @return {@link DevBaseVariable}
     */
    public DevBaseVariable<K, V> putVariable(final K key, final V value) {
        linkedHashMap.put(key, value);
        return this;
    }

    /**
     * 保存变量数据
     * @param put   {@code true} put, {@code false} remove
     * @param key   Key
     * @param value Value
     * @return {@link DevBaseVariable}
     */
    public DevBaseVariable<K, V> putVariable(final boolean put, final K key, final V value) {
        return put ? putVariable(key, value) : removeVariable(key);
    }

    /**
     * 移除指定变量数据 - 通过 key 判断
     * @param key Key
     * @return {@link DevBaseVariable}
     */
    public DevBaseVariable<K, V> removeVariable(final K key) {
        linkedHashMap.remove(key);
        return this;
    }

    /**
     * 切换变量数据存储状态
     * <pre>
     *     如果存在则删除、反之则保存
     * </pre>
     * @param key   Key
     * @param value Value
     * @return {@link DevBaseVariable}
     */
    public DevBaseVariable<K, V> toggle(final K key, final V value) {
        if (isVariable(key)) { // 移除存在的数据
            linkedHashMap.remove(key);
        } else { // 保存变量数据
            linkedHashMap.put(key, value);
        }
        return this;
    }

    // =

    /**
     * 根据 key 获取对应的 value
     * @param key Key
     * @return Value
     */
    public V getVariableValue(final K key) {
        return linkedHashMap.get(key);
    }

    /**
     * 根据 key 获取对应的 value
     * @param key Key
     * @param <T> 泛型
     * @return Value convert T value
     */
    public <T> T getVariableValueConvert(final K key) {
        try {
            return (T) linkedHashMap.get(key);
        } catch (Exception e) {
        }
        return null;
    }

    // =

    /**
     * 获取变量数据 value list
     * @return {@link List}
     */
    public List<V> getVariableValues() {
        List<V> lists = new ArrayList<>();
        lists.addAll(linkedHashMap.values());
        return lists;
    }

    /**
     * 获取变量数据 value list ( 倒 / 逆序 )
     * @return {@link List}
     */
    public List<V> getVariableValuesToReverse() {
        List<V> lists = getVariableValues();
        Collections.reverse(lists);
        return lists;
    }

    // =

    /**
     * 根据 value 获取对应的 key
     * @param value Value
     * @return Key
     */
    public K getVariableKey(final V value) {
        // 进行循环遍历获取
        Iterator<Map.Entry<K, V>> iterator = linkedHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<K, V> entry = iterator.next();
            V v = entry.getValue();
            // 判断是否符合对应的 value
            if (v == value) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 获取变量数据 key list
     * @return {@link List}
     */
    public List<K> getVariableKeys() {
        List<K> lists = new ArrayList<>();
        lists.addAll(linkedHashMap.keySet());
        return lists;
    }

    /**
     * 获取变量数据 key list ( 倒 / 逆序 )
     * @return {@link List}
     */
    public List<K> getVariableKeysToReverse() {
        List<K> lists = getVariableKeys();
        Collections.reverse(lists);
        return lists;
    }
}