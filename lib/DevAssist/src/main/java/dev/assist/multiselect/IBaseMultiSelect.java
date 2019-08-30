package dev.assist.multiselect;

import dev.base.DevBaseEntry;

/**
 * detail: 多选操作接口 - 基类
 * @param <CollectionG> 集合泛型
 * @param <V>           Value
 * @author Ttt
 */
public interface IBaseMultiSelect<CollectionG, V> {

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
    CollectionG getSelects();

    /**
     * 通过集合添加选中数据
     * @param collection 集合泛型
     */
    void putSelects(CollectionG collection);

    /**
     * 通过集合添加选中数据
     * @param collections 集合
     */
    void putSelects(java.util.Collection<? extends DevBaseEntry<?, V>> collections);

    /**
     * 判断是否存在选中的数据
     * @return {@code true} yes, {@code false} no
     */
    boolean isSelect();

    /**
     * 判断是否选中 - 通过 value 判断
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
     * 设置非选中 - 符合条件的全部 value
     * @param value Value
     */
    void unselectValueAll(V value);
}
