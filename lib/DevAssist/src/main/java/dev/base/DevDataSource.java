package dev.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import dev.base.data.DataManager;
import dev.utils.JCLogUtils;
import dev.utils.common.ObjectUtils;

/**
 * detail: 数据源操作实体类
 * @author Ttt
 */
public class DevDataSource<T>
        extends DevObject<T>
        implements DataManager<T> {

    // 日志 TAG
    private static final String TAG = DevDataSource.class.getSimpleName();

    // List Data
    private final List<T> mList = new ArrayList<>();

    public DevDataSource() {
    }

    public DevDataSource(final T object) {
        super(object);
    }

    public DevDataSource(
            final T object,
            final Object tag
    ) {
        super(object, tag);
    }

    // ===========
    // = 获取相关 =
    // ===========

    /**
     * 获取 List Data
     * @return {@link List}
     */
    @Override
    public List<T> getDataList() {
        return mList;
    }

    /**
     * 获取 ArrayList Data
     * @return {@link ArrayList}
     */
    @Override
    public ArrayList<T> getDataArrayList() {
        return new ArrayList<>(mList);
    }

    /**
     * 获取 List Size
     * @return List.size()
     */
    @Override
    public int getDataSize() {
        return mList.size();
    }

    /**
     * 获取 List Position Data
     * @param position 索引
     * @return {@link T}
     */
    @Override
    public T getDataItem(int position) {
        if (position < 0) return null;
        try {
            return mList.get(position);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getDataItem");
        }
        return null;
    }

    /**
     * 获取 Value Position
     * @param value {@link T}
     * @return position
     */
    @Override
    public int getDataItemPosition(T value) {
        try {
            return mList.indexOf(value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getDataItemPosition");
        }
        return -1;
    }

    /**
     * 获取 First Data
     * @return {@link T}
     */
    @Override
    public T getFirstData() {
        return getDataItem(0);
    }

    /**
     * 获取 Last Data
     * @return {@link T}
     */
    @Override
    public T getLastData() {
        return getDataItem(getLastPosition());
    }

    /**
     * 获取 Last Position
     * @return Last Position
     */
    @Override
    public int getLastPosition() {
        int size = getDataSize();
        return (size == 0) ? 0 : size - 1;
    }

    // ===========
    // = 快速判断 =
    // ===========

    /**
     * 判断 List Size 是否为 0
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isDataEmpty() {
        return getDataSize() == 0;
    }

    /**
     * 判断 List Size 是否大于 0
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isDataNotEmpty() {
        return !isDataEmpty();
    }

    /**
     * 判断是否 First Position
     * @param position 索引
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isFirstPosition(int position) {
        return position == 0;
    }

    /**
     * 判断是否 Last Position
     * @param position 索引
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isLastPosition(int position) {
        return isLastPosition(position, getDataSize());
    }

    /**
     * 判断是否 Last Position
     * @param position 索引
     * @param size     总数
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isLastPosition(
            int position,
            int size
    ) {
        return position >= 0 && size >= 1 && size - position == 1;
    }

    /**
     * 判断 First Value 是否一致
     * @param value 待校验 Value
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean equalsFirstData(T value) {
        return value != null && ObjectUtils.equals(getFirstData(), value);
    }

    /**
     * 判断 Last Value 是否一致
     * @param value 待校验 Value
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean equalsLastData(T value) {
        return value != null && ObjectUtils.equals(getLastData(), value);
    }

    /**
     * 判断 Position Value 是否一致
     * @param position 索引
     * @param value    待校验 Value
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean equalsPositionData(
            int position,
            T value
    ) {
        return value != null && ObjectUtils.equals(getDataItem(position), value);
    }

    // ======
    // = 增 =
    // ======

    /**
     * 添加数据
     * @param value Value
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean addData(T value) {
        try {
            mList.add(value);
            return true;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "addData");
        }
        return false;
    }

    /**
     * 添加数据
     * @param position 索引
     * @param value    Value
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean addDataAt(
            int position,
            T value
    ) {
        if (position < 0) return false;
        try {
            mList.add(position, value);
            return true;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "addDataAt");
        }
        return false;
    }

    /**
     * 添加数据集
     * @param collection {@link Collection}
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean addDatas(Collection<T> collection) {
        if (collection == null) return false;
        try {
            mList.addAll(collection);
            return true;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "addDatas");
        }
        return false;
    }

    /**
     * 添加数据集
     * @param position   索引
     * @param collection {@link Collection}
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean addDatasAt(
            int position,
            Collection<T> collection
    ) {
        if (position < 0) return false;
        if (collection == null) return false;
        try {
            mList.addAll(position, collection);
            return true;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "addDatasAt");
        }
        return false;
    }

    /**
     * 添加数据集 ( 进行校验 )
     * @param collection {@link Collection}
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean addDatasChecked(Collection<T> collection) {
        if (collection == null) return false;
        try {
            List<T> lists = new ArrayList<>();
            for (T value : collection) {
                if (value != null) {
                    lists.add(value);
                }
            }
            mList.addAll(lists);
            return true;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "addDatasChecked");
        }
        return false;
    }

    /**
     * 添加数据集 ( 进行校验 )
     * @param position   索引
     * @param collection {@link Collection}
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean addDatasCheckedAt(
            int position,
            Collection<T> collection
    ) {
        if (position < 0) return false;
        if (collection == null) return false;
        try {
            List<T> lists = new ArrayList<>();
            for (T value : collection) {
                if (value != null) {
                    lists.add(value);
                }
            }
            mList.addAll(position, lists);
            return true;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "addDatasCheckedAt");
        }
        return false;
    }

    // ======
    // = 删 =
    // ======

    /**
     * 移除数据
     * @param value Value
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean removeData(T value) {
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
    public T removeDataAt(int position) {
        if (position < 0) return null;
        try {
            return mList.remove(position);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "removeDataAt");
        }
        return null;
    }

    /**
     * 移除数据集
     * @param collection {@link Collection}
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean removeDatas(Collection<T> collection) {
        if (collection == null) return false;
        try {
            mList.removeAll(collection);
            return true;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "removeDatas");
        }
        return false;
    }

    // ======
    // = 改 =
    // ======

    /**
     * 替换数据
     * @param oldValue 旧的 Value
     * @param newValue 新的 Value
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean replaceData(
            T oldValue,
            T newValue
    ) {
        if (contains(oldValue)) {
            return replaceDataAt(getDataItemPosition(oldValue), newValue);
        }
        return false;
    }

    /**
     * 替换数据
     * @param position 索引
     * @param value    Value
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean replaceDataAt(
            int position,
            T value
    ) {
        if (position < 0) return false;
        try {
            mList.set(position, value);
            return true;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "replaceDataAt");
        }
        return false;
    }

    // =

    /**
     * 数据中两个索引 Data 互换位置
     * @param fromPosition 待换位置索引
     * @param toPosition   替换后位置索引
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean swipePosition(
            int fromPosition,
            int toPosition
    ) {
        if (fromPosition != toPosition && fromPosition >= 0 && toPosition >= 0) {
            try {
                Collections.swap(mList, fromPosition, toPosition);
                return true;
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "swipePosition");
            }
        }
        return false;
    }

    /**
     * 是否存在 Data
     * @param value Value
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean contains(T value) {
        if (value == null) return false;
        try {
            return mList.contains(value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "contains");
        }
        return false;
    }

    /**
     * 清空全部数据
     */
    @Override
    public void clearDataList() {
        mList.clear();
    }

    /**
     * 清空全部数据
     * @param notify 是否进行通知
     */
    @Override
    public void clearDataList(boolean notify) {
        mList.clear();
    }

    /**
     * 设置 List Data
     * @param lists {@link List}
     */
    @Override
    public void setDataList(List<T> lists) {
        mList.clear();
        if (lists != null) {
            mList.addAll(lists);
        }
    }

    /**
     * 设置 List Data
     * @param lists  {@link List}
     * @param notify 是否进行通知
     */
    @Override
    public void setDataList(
            List<T> lists,
            boolean notify
    ) {
        mList.clear();
        if (lists != null) {
            mList.addAll(lists);
        }
    }
}