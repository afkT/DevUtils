package dev.base.multiselect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.base.DevEntry;
import dev.base.DevObject;

/**
 * detail: Map 多选实体类
 * @param <K> Key
 * @param <V> Value
 * @author Ttt
 * <pre>
 *     实现 {@link IMultiSelectToMap}, 每个接口方法直接通过调用 {@link DevMultiSelectMap} 已实现同名方法即可
 * </pre>
 */
public class DevMultiSelectMap<K, V>
        extends DevObject
        implements IMultiSelectToMap<LinkedHashMap<K, V>, K, V> {

    // 选中数据集
    private final LinkedHashMap<K, V> mMapSelects = new LinkedHashMap<>();

    // ====================
    // = IBaseMultiSelect =
    // ====================

    /**
     * 清空全部选中数据
     */
    @Override
    public void clearSelects() {
        mMapSelects.clear();
    }

    /**
     * 获取选中的数据条数
     * @return 选中的数据条数
     */
    @Override
    public int getSelectSize() {
        return mMapSelects.size();
    }

    /**
     * 获取选中的数据集合
     * @return 选中的数据集合
     */
    @Override
    public LinkedHashMap<K, V> getSelects() {
        return mMapSelects;
    }

    /**
     * 通过集合添加选中数据
     * @param collection 集合泛型
     */
    @Override
    public void putSelects(final LinkedHashMap<K, V> collection) {
        if (collection != null) {
            mMapSelects.putAll(collection);
        }
    }

    /**
     * 通过集合添加选中数据
     * @param collections 集合
     */
    @Override
    public void putSelects(final Collection<? extends DevEntry<?, V>> collections) {
        if (collections != null) {
            for (DevEntry<?, V> entry : collections) {
                if (entry != null) {
                    mMapSelects.put((K) entry.getKey(), entry.getValue());
                }
            }
        }
    }

    /**
     * 判断是否存在选中的数据
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isSelect() {
        return getSelectSize() != 0;
    }

    /**
     * 判断是否选中 ( 通过 value 判断 )
     * @param value Value
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isSelectValue(final V value) {
        return mMapSelects.containsValue(value);
    }

    /**
     * 设置非选中
     * @param value Value
     */
    @Override
    public void unselectValue(final V value) {
        Iterator<V> iterator = mMapSelects.values().iterator();
        while (iterator.hasNext()) {
            V v = iterator.next();
            if (v == value) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * 设置非选中 ( 符合条件的全部 value )
     * @param value Value
     */
    @Override
    public void unselectValueAll(final V value) {
        Iterator<V> iterator = mMapSelects.values().iterator();
        while (iterator.hasNext()) {
            V v = iterator.next();
            if (v == value) {
                iterator.remove();
            }
        }
    }

    // =====================
    // = IMultiSelectToMap =
    // =====================

    /**
     * 判断是否选中 ( 如果未选中, 则设置为选中 )
     * @param key   Key
     * @param value Value
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isSelect(
            final K key,
            final V value
    ) {
        if (!isSelectKey(key)) {
            mMapSelects.put(key, value);
            return false;
        }
        return true;
    }

    /**
     * 判断是否选中 ( 通过 key 判断 )
     * @param key Key
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isSelectKey(final K key) {
        return mMapSelects.containsKey(key);
    }

    /**
     * 设置选中操作
     * @param key   Key
     * @param value Value
     */
    @Override
    public void select(
            final K key,
            final V value
    ) {
        mMapSelects.put(key, value);
    }

    /**
     * 设置选中操作
     * @param isSelect 是否选中
     * @param key      Key
     * @param value    Value
     */
    @Override
    public void select(
            final boolean isSelect,
            final K key,
            final V value
    ) {
        if (isSelect) {
            select(key, value);
        } else {
            unselect(key);
        }
    }

    /**
     * 设置非选中操作
     * @param key Key
     */
    @Override
    public void unselect(final K key) {
        mMapSelects.remove(key);
    }

    /**
     * 切换选中状态
     * <pre>
     *     如果选中, 则设为非选中, 反之设为选中
     * </pre>
     * @param key   Key
     * @param value Value
     */
    @Override
    public void toggle(
            final K key,
            final V value
    ) {
        if (isSelectKey(key)) {
            mMapSelects.remove(key);
        } else {
            mMapSelects.put(key, value);
        }
    }

    // =================
    // = 获取选中的数据 =
    // =================

    // =========
    // = Value =
    // =========

    /**
     * 通过 key 获取 value
     * @param key Key
     * @return Value
     */
    @Override
    public V getSelectValue(final K key) {
        return mMapSelects.get(key);
    }

    /**
     * 获取选中的数据集合
     * @return {@link List}
     */
    @Override
    public List<V> getSelectValues() {
        List<V> lists = new ArrayList<>();
        lists.addAll(mMapSelects.values());
        return lists;
    }

    /**
     * 获取选中的数据集合 ( 倒序 )
     * @return {@link List}
     */
    @Override
    public List<V> getSelectValuesToReverse() {
        List<V> lists = getSelectValues();
        Collections.reverse(lists);
        return lists;
    }

    // =======
    // = Key =
    // =======

    /**
     * 通过 value 获取 key
     * @param value Value
     * @return Key
     */
    @Override
    public K getSelectKey(final V value) {
        // 进行循环遍历获取
        Iterator<Map.Entry<K, V>> iterator = mMapSelects.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<K, V> entry = iterator.next();
            V               v     = entry.getValue();
            // 判断是否符合对应的 value
            if (v == value) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 获取选中的数据集合
     * @return {@link List}
     */
    @Override
    public List<K> getSelectKeys() {
        List<K> lists = new ArrayList<>();
        lists.addAll(mMapSelects.keySet());
        return lists;
    }

    /**
     * 获取选中的数据集合 ( 倒序 )
     * @return {@link List}
     */
    @Override
    public List<K> getSelectKeysToReverse() {
        List<K> lists = getSelectKeys();
        Collections.reverse(lists);
        return lists;
    }
}