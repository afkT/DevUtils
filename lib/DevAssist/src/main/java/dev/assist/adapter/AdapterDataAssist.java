package dev.assist.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dev.utils.JCLogUtils;

/**
 * detail: Adapter 数据辅助类
 * @author Ttt
 * <pre>
 *     使用: 只需要实现 IAdapterData, 每个接口方法直接通过调用 AdapterDataAssist 已实现同名方法即可
 * </pre>
 */
public class AdapterDataAssist<V>
        implements IAdapterData<V>,
        IAdapterNotify {

    // 日志 TAG
    private static final String TAG = AdapterDataAssist.class.getSimpleName();

    // List Data
    private List<V>        mList = new ArrayList<>();
    // IAdapterNotify
    private IAdapterNotify mAdapterNotify;

    /**
     * 构造函数
     */
    public AdapterDataAssist() {
    }

    /**
     * 构造函数
     * @param adapterNotify {@link IAdapterNotify}
     */
    public AdapterDataAssist(final IAdapterNotify adapterNotify) {
        this.mAdapterNotify = adapterNotify;
    }

    // =======
    // = set =
    // =======

    /**
     * 设置 Adapter Notify
     * @param adapterNotify {@link IAdapterNotify}
     * @return {@link AdapterDataAssist}
     */
    public AdapterDataAssist<V> setAdapterNotify(final IAdapterNotify adapterNotify) {
        this.mAdapterNotify = adapterNotify;
        return this;
    }

    // ================
    // = IAdapterData =
    // ================

    // ===========
    // = 获取相关 =
    // ===========

    /**
     * 获取 List Data
     * @return {@link List}
     */
    @Override
    public List<V> getDataList() {
        return mList;
    }

    /**
     * 获取 ArrayList Data
     * @return {@link ArrayList}
     */
    @Override
    public ArrayList<V> getDataArrayList() {
        return new ArrayList<>(mList);
    }

    // =

    /**
     * 获取 List Count
     * @return List.size()
     */
    @Override
    public int getDataCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * 获取 List Position Item
     * @param position 索引
     * @return V Object
     */
    @Override
    public V getDataItem(final int position) {
        try {
            return mList.get(position);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getDataItem");
        }
        return null;
    }

    /**
     * 获取 Value Position
     * @param value V Object
     * @return position
     */
    @Override
    public int getDataItemPosition(final V value) {
        try {
            return mList.indexOf(value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getDataItemPosition");
        }
        return -1;
    }

    /**
     * 获取 First Item Data
     * @return V Object
     */
    @Override
    public V getDataFirstItem() {
        return getDataItem(0);
    }

    /**
     * 获取 Last Item Data
     * @return V Object
     */
    @Override
    public V getDataLastItem() {
        return getDataItem(getDataCount() - 1);
    }

    // ===========
    // = 其他方法 =
    // ===========

    /**
     * 判断是否 First Item Data
     * @param position 索引
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isFirstItem(final int position) {
        return position == 0;
    }

    /**
     * 判断是否 Last Item Data
     * @param position 索引
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isLastItem(final int position) {
        return isLastItem(position, getDataCount());
    }

    /**
     * 判断是否 Last Item Data
     * @param position 索引
     * @param count    总数
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isLastItem(
            final int position,
            final int count
    ) {
        return count - position == 1;
    }

    // =

    /**
     * 清空全部数据
     */
    @Override
    public void clearDataList() {
        clearDataList(true);
    }

    /**
     * 清空全部数据
     * @param notify 是否通知适配器
     */
    @Override
    public void clearDataList(final boolean notify) {
        if (mList != null) mList.clear();
        if (notify) adapterNotifyDataSetChanged();
    }

    // ===============
    // = 数据处理方法 =
    // ===============

    /**
     * 添加数据
     * @param value Value
     */
    @Override
    public void addData(final V value) {
        try {
            mList.add(value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "addData");
        }
    }

    /**
     * 添加数据
     * @param position 索引
     * @param value    Value
     */
    @Override
    public void addData(
            final int position,
            final V value
    ) {
        try {
            mList.add(position, value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "addData");
        }
    }

    /**
     * 添加数据
     * @param collection {@link Collection}
     */
    @Override
    public void addAllData(final Collection<V> collection) {
        if (collection != null) {
            try {
                mList.addAll(collection);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "addAllData");
            }
        }
    }

    /**
     * 添加数据
     * @param position   索引
     * @param collection {@link Collection}
     */
    @Override
    public void addAllData(
            final int position,
            final Collection<V> collection
    ) {
        if (collection != null) {
            try {
                mList.addAll(position, collection);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "addAllData");
            }
        }
    }
    // =

    /**
     * 移除数据
     * @param value Value
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean removeData(final V value) {
        try {
            return mList.remove(value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "removeData");
        }
        return false;
    }

    /**
     * 移除数据
     * @param position 索引
     * @return remove position value
     */
    @Override
    public V removeData(final int position) {
        try {
            return mList.remove(position);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "removeData");
        }
        return null;
    }

    /**
     * 移除数据
     * @param collection {@link Collection}
     */
    @Override
    public void removeData(final Collection<V> collection) {
        if (collection != null) {
            try {
                mList.removeAll(collection);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "removeData");
            }
        }
    }

    // =

    /**
     * 设置 List Data
     * @param lists {@link List}
     */
    @Override
    public void setDataList(final List<V> lists) {
        setDataList(lists, true);
    }

    /**
     * 设置 List Data
     * @param lists  {@link List}
     * @param notify 是否通知适配器
     */
    @Override
    public void setDataList(
            final List<V> lists,
            final boolean notify
    ) {
        if (mList != null) mList.clear();
        // 添加数据
        addAllData(lists);
        // 通知适配器
        if (notify) adapterNotifyDataSetChanged();
    }

    // ==================
    // = IAdapterNotify =
    // ==================

    /**
     * 通知 Adapter 数据改变
     */
    @Override
    public void adapterNotifyDataSetChanged() {
        // 通知适配器
        if (mAdapterNotify != null) {
            mAdapterNotify.adapterNotifyDataSetChanged();
        }
    }
}