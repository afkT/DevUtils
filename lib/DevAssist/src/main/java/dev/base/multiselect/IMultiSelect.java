package dev.base.multiselect;

import java.util.Collection;

import dev.base.DevEntry;

/**
 * detail: 多选操作接口 ( 基类 )
 * @param <CollectionT> 集合泛型
 * @param <V>           Value
 * @author Ttt
 */
public interface IMultiSelect<CollectionT, V> {

    /**
     * 清空全部选中数据
     */
    void clearSelects();

    /**
     * 获取选中的数据条数
     * @return 选中的数据条数
     */
    int getSelectSize();

    /**
     * 获取选中的数据集合
     * @return 选中的数据集合
     */
    CollectionT getSelects();

    /**
     * 通过集合添加选中数据
     * @param collection 集合泛型
     */
    void putSelects(CollectionT collection);

    /**
     * 通过集合添加选中数据
     * @param collections 集合
     */
    void putSelects(Collection<? extends DevEntry<?, V>> collections);

    /**
     * 判断是否存在选中的数据
     * @return {@code true} yes, {@code false} no
     */
    boolean isSelect();

    /**
     * 判断是否选中 ( 通过 value 判断 )
     * @param value Value
     * @return {@code true} yes, {@code false} no
     */
    boolean isSelectValue(V value);

    /**
     * 设置非选中
     * @param value Value
     */
    void unselectValue(V value);

    /**
     * 设置非选中 ( 符合条件的全部 value )
     * @param value Value
     */
    void unselectValueAll(V value);
}