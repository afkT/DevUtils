package dev.base.multiselect;

import java.util.List;
import java.util.Map;

/**
 * detail: 多选操作接口 ( Map )
 * @param <CollectionT> 集合泛型
 * @param <K>           Key
 * @param <V>           Value
 * @author Ttt
 */
public interface IMultiSelectToMap<CollectionT extends Map, K, V>
        extends IMultiSelect<CollectionT, V> {

    /**
     * 判断是否选中 ( 如果未选中, 则设置为选中 )
     * @param key   Key
     * @param value Value
     * @return {@code true} yes, {@code false} no
     */
    boolean isSelect(
            K key,
            V value
    );

    /**
     * 判断是否选中 ( 通过 key 判断 )
     * @param key Key
     * @return {@code true} yes, {@code false} no
     */
    boolean isSelectKey(K key);

    // =

    /**
     * 设置选中操作
     * @param key   Key
     * @param value Value
     */
    void select(
            K key,
            V value
    );

    /**
     * 设置选中操作
     * @param isSelect 是否选中
     * @param key      Key
     * @param value    Value
     */
    void select(
            boolean isSelect,
            K key,
            V value
    );

    // =

    /**
     * 设置非选中操作
     * @param key Key
     */
    void unselect(K key);

    // =

    /**
     * 切换选中状态
     * <pre>
     *     如果选中, 则设为非选中, 反之设为选中
     * </pre>
     * @param key   Key
     * @param value Value
     */
    void toggle(
            K key,
            V value
    );

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
    V getSelectValue(K key);

    /**
     * 获取选中的数据集合
     * @return {@link List}
     */
    List<V> getSelectValues();

    /**
     * 获取选中的数据集合 ( 倒序 )
     * @return {@link List}
     */
    List<V> getSelectValuesToReverse();

    // =======
    // = Key =
    // =======

    /**
     * 通过 value 获取 key
     * @param value Value
     * @return Key
     */
    K getSelectKey(V value);

    /**
     * 获取选中的数据集合
     * @return {@link List}
     */
    List<K> getSelectKeys();

    /**
     * 获取选中的数据集合 ( 倒序 )
     * @return {@link List}
     */
    List<K> getSelectKeysToReverse();
}