package dev.assist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dev.base.DevDataSource;
import dev.base.data.DataChanged;
import dev.base.data.DataManager;

/**
 * detail: 数据辅助类
 * @author Ttt
 * <pre>
 *     实现 {@link DataManager}, 每个接口方法直接通过调用 {@link DataAssist} 已实现同名方法即可
 * </pre>
 */
public class DataAssist<T>
        implements DataManager<T>,
        DataChanged<T> {

    // DataSource Object
    private final DevDataSource<T> mData = new DevDataSource<>();

    // 数据改变通知
    private DataChanged<T> mDataChanged;

    public DataAssist() {
    }

    public DataAssist(final DataChanged<T> dataChanged) {
        this.mDataChanged = dataChanged;
    }

    /**
     * 设置数据改变通知
     * @param dataChanged {@link DataChanged}
     * @return {@link DataAssist}
     */
    public DataAssist<T> setDataChanged(final DataChanged<T> dataChanged) {
        this.mDataChanged = dataChanged;
        return this;
    }

    /**
     * 获取 DataSource Object
     * @return {@link DevDataSource}
     */
    public DevDataSource<T> getDataSource() {
        return mData;
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
        return mData.getDataList();
    }

    /**
     * 获取 ArrayList Data
     * @return {@link ArrayList}
     */
    @Override
    public ArrayList<T> getDataArrayList() {
        return mData.getDataArrayList();
    }

    /**
     * 获取 List Size
     * @return List.size()
     */
    @Override
    public int getDataSize() {
        return mData.getDataSize();
    }

    /**
     * 获取 List Position Data
     * @param position 索引
     * @return {@link T}
     */
    @Override
    public T getDataItem(int position) {
        return mData.getDataItem(position);
    }

    /**
     * 获取 Value Position
     * @param value {@link T}
     * @return position
     */
    @Override
    public int getDataItemPosition(T value) {
        return mData.getDataItemPosition(value);
    }

    /**
     * 获取 First Data
     * @return {@link T}
     */
    @Override
    public T getFirstData() {
        return mData.getFirstData();
    }

    /**
     * 获取 Last Data
     * @return {@link T}
     */
    @Override
    public T getLastData() {
        return mData.getLastData();
    }

    /**
     * 获取 Last Position
     * @return Last Position
     */
    @Override
    public int getLastPosition() {
        return mData.getLastPosition();
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
        return mData.isDataEmpty();
    }

    /**
     * 判断 List Size 是否大于 0
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isDataNotEmpty() {
        return mData.isDataNotEmpty();
    }

    /**
     * 判断是否 First Position
     * @param position 索引
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isFirstPosition(int position) {
        return mData.isFirstPosition(position);
    }

    /**
     * 判断是否 Last Position
     * @param position 索引
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isLastPosition(int position) {
        return mData.isLastPosition(position);
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
        return mData.isLastPosition(position, size);
    }

    /**
     * 判断 First Value 是否一致
     * @param value 待校验 Value
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean equalsFirstData(T value) {
        return mData.equalsFirstData(value);
    }

    /**
     * 判断 Last Value 是否一致
     * @param value 待校验 Value
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean equalsLastData(T value) {
        return mData.equalsLastData(value);
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
        return mData.equalsPositionData(position, value);
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
        if (mData.addData(value)) {
            notifyDataChanged();
            return true;
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
        if (mData.addDataAt(position, value)) {
            notifyDataChanged();
            return true;
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
        if (mData.addDatas(collection)) {
            notifyDataChanged();
            return true;
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
        if (mData.addDatasAt(position, collection)) {
            notifyDataChanged();
            return true;
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
        if (mData.addDatasChecked(collection)) {
            notifyDataChanged();
            return true;
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
        if (mData.addDatasCheckedAt(position, collection)) {
            notifyDataChanged();
            return true;
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
        if (mData.removeData(value)) {
            notifyDataChanged();
            return true;
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
        T value = mData.removeDataAt(position);
        notifyDataChanged();
        return value;
    }

    /**
     * 移除数据集
     * @param collection {@link Collection}
     * @return {@code true} success, {@code false} fail
     */
    @Override
    public boolean removeDatas(Collection<T> collection) {
        if (mData.removeDatas(collection)) {
            notifyDataChanged();
            return true;
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
        if (mData.replaceData(oldValue, newValue)) {
            notifyDataChanged();
            return true;
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
        if (mData.replaceDataAt(position, value)) {
            notifyDataChanged();
            return true;
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
        if (mData.swipePosition(fromPosition, toPosition)) {
            notifyDataChanged();
            return true;
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
        return mData.contains(value);
    }

    /**
     * 清空全部数据
     */
    @Override
    public void clearDataList() {
        mData.clearDataList();
        notifyDataChanged();
    }

    /**
     * 清空全部数据
     * @param notify 是否进行通知
     */
    @Override
    public void clearDataList(boolean notify) {
        mData.clearDataList(notify);
        if (notify) notifyDataChanged();
    }

    /**
     * 设置 List Data
     * @param lists {@link List}
     */
    @Override
    public void setDataList(List<T> lists) {
        mData.setDataList(lists);
        notifyDataChanged();
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
        mData.setDataList(lists, notify);
        if (notify) notifyDataChanged();
    }

    // ===========
    // = 通知方法 =
    // ===========

    /**
     * 通知数据改变
     */
    @Override
    public void notifyDataChanged() {
        if (mDataChanged != null) {
            mDataChanged.notifyDataChanged();
        }
    }

    /**
     * 通知某个数据改变
     * @param value {@link T}
     */
    @Override
    public void notifyElementChanged(T value) {
        if (mDataChanged != null) {
            mDataChanged.notifyElementChanged(value);
        }
    }
}