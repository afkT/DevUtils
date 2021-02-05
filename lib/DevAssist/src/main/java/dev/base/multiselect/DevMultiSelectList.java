package dev.base.multiselect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import dev.base.DevEntry;
import dev.base.DevObject;

/**
 * detail: List 多选实体类
 * @param <V> Value
 * @author Ttt
 * <pre>
 *     实现 {@link IMultiSelectToList}, 每个接口方法直接通过调用 {@link DevMultiSelectList} 已实现同名方法即可
 * </pre>
 */
public class DevMultiSelectList<V>
        extends DevObject
        implements IMultiSelectToList<List<V>, V> {

    // 选中数据集
    private final List<V> mListSelects = new ArrayList<>();

    // ====================
    // = IBaseMultiSelect =
    // ====================

    /**
     * 清空全部选中数据
     */
    @Override
    public void clearSelects() {
        mListSelects.clear();
    }

    /**
     * 获取选中的数据条数
     * @return 选中的数据条数
     */
    @Override
    public int getSelectSize() {
        return mListSelects.size();
    }

    /**
     * 获取选中的数据集合
     * @return 选中的数据集合
     */
    @Override
    public List<V> getSelects() {
        return mListSelects;
    }

    /**
     * 通过集合添加选中数据
     * @param collection 集合泛型
     */
    @Override
    public void putSelects(final List<V> collection) {
        if (collection != null) {
            mListSelects.addAll(collection);
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
                    mListSelects.add(entry.getValue());
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
        return mListSelects.contains(value);
    }

    /**
     * 设置非选中
     * @param value Value
     */
    @Override
    public void unselectValue(final V value) {
        mListSelects.remove(value);
    }

    /**
     * 设置非选中 ( 符合条件的全部 value )
     * @param value Value
     */
    @Override
    public void unselectValueAll(final V value) {
        Iterator<V> iterator = mListSelects.iterator();
        while (iterator.hasNext()) {
            V v = iterator.next();
            if (v == value) {
                iterator.remove();
            }
        }
    }

    // ======================
    // = IMultiSelectToList =
    // ======================

    /**
     * 判断是否选中 ( 通过 value 判断 )
     * @param value Value
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isSelect(final V value) {
        return isSelectValue(value);
    }

    /**
     * 设置选中操作
     * @param value Value
     */
    @Override
    public void select(final V value) {
        mListSelects.add(value);
    }

    /**
     * 设置选中操作
     * @param isSelect 是否选中
     * @param value    Value
     */
    @Override
    public void select(
            final boolean isSelect,
            final V value
    ) {
        if (isSelect) {
            select(value);
        } else {
            unselect(value);
        }
    }

    /**
     * 设置选中操作 ( 保存到指定索引 )
     * @param value    Value
     * @param position 索引
     */
    @Override
    public void select(
            final V value,
            final int position
    ) {
        if (position >= 0) mListSelects.add(position, value);
    }

    /**
     * 设置非选中操作
     * @param position 索引
     * @return Value
     */
    @Override
    public V unselect(final int position) {
        if (position >= 0) {
            try {
                return mListSelects.remove(position);
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 设置非选中操作
     * @param value Value
     */
    @Override
    public void unselect(final V value) {
        mListSelects.remove(value);
    }

    /**
     * 切换选中状态
     * <pre>
     *     如果选中, 则设为非选中, 反之设为选中
     * </pre>
     * @param value Value
     */
    @Override
    public void toggle(final V value) {
        if (mListSelects.contains(value)) {
            mListSelects.remove(value);
        } else {
            mListSelects.add(value);
        }
    }

    // =================
    // = 获取选中的数据 =
    // =================

    /**
     * 获取选中的数据集合
     * @return {@link List}
     */
    @Override
    public List<V> getSelectValues() {
        return new ArrayList<>(mListSelects);
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

    /**
     * 获取选中的数据
     * @param position 索引
     * @return Value
     */
    @Override
    public V getSelectValue(final int position) {
        if (position >= 0) {
            try {
                return mListSelects.get(position);
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 获取选中的数据所在的索引
     * @param value Value
     * @return value position
     */
    @Override
    public int getSelectValueToPosition(final V value) {
        return mListSelects.indexOf(value);
    }
}