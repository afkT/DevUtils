package dev.base.multiselect;

import java.util.List;

/**
 * detail: 多选操作接口 ( List )
 * @param <CollectionT> 集合泛型
 * @param <V>           Value
 * @author Ttt
 */
public interface IMultiSelectToList<CollectionT extends List, V>
        extends IMultiSelect<CollectionT, V> {

    /**
     * 判断是否选中 ( 通过 value 判断 )
     * @param value Value
     * @return {@code true} yes, {@code false} no
     */
    boolean isSelect(V value);

    // =

    /**
     * 设置选中操作
     * @param value Value
     */
    void select(V value);

    /**
     * 设置选中操作
     * @param isSelect 是否选中
     * @param value    Value
     */
    void select(
            boolean isSelect,
            V value
    );

    /**
     * 设置选中操作 ( 保存到指定索引 )
     * @param value    Value
     * @param position 索引
     */
    void select(
            V value,
            int position
    );

    // =

    /**
     * 设置非选中操作
     * @param position 索引
     * @return Value
     */
    V unselect(int position);

    /**
     * 设置非选中操作
     * @param value Value
     */
    void unselect(V value);

    // =

    /**
     * 切换选中状态
     * <pre>
     *     如果选中, 则设为非选中, 反之设为选中
     * </pre>
     * @param value Value
     */
    void toggle(V value);

    // =================
    // = 获取选中的数据 =
    // =================

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

    // =

    /**
     * 获取选中的数据
     * @param position 索引
     * @return Value
     */
    V getSelectValue(int position);

    /**
     * 获取选中的数据所在的索引
     * @param value Value
     * @return value position
     */
    int getSelectValueToPosition(V value);
}