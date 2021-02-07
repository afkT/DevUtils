package dev.base.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * detail: 数据管理接口
 * @param <V> 泛型
 * @author Ttt
 */
public interface DataManager<V> {

    // ===========
    // = 获取相关 =
    // ===========

    /**
     * 获取 List Data
     * @return {@link List}
     */
    List<V> getDataList();

    /**
     * 获取 ArrayList Data
     * @return {@link ArrayList}
     */
    ArrayList<V> getDataArrayList();

    /**
     * 获取 List Size
     * @return List.size()
     */
    int getDataSize();

    /**
     * 获取 List Position Data
     * @param position 索引
     * @return {@link V}
     */
    V getData(int position);

    /**
     * 获取 Value Position
     * @param value {@link V}
     * @return position
     */
    int getDataPosition(V value);

    /**
     * 获取 First Data
     * @return {@link V}
     */
    V getFirstData();

    /**
     * 获取 Last Data
     * @return {@link V}
     */
    V getLastData();

    // ===========
    // = 快速判断 =
    // ===========

    /**
     * 判断 List Size 是否为 0
     * @return {@code true} yes, {@code false} no
     */
    boolean isDataEmpty();

    /**
     * 判断 List Size 是否大于 0
     * @return {@code true} yes, {@code false} no
     */
    boolean isDataNotEmpty();

    /**
     * 判断是否 First Position
     * @param position 索引
     * @return {@code true} yes, {@code false} no
     */
    boolean isFirstPosition(int position);

    /**
     * 判断是否 Last Position
     * @param position 索引
     * @return {@code true} yes, {@code false} no
     */
    boolean isLastPosition(int position);

    /**
     * 判断是否 Last Position
     * @param position 索引
     * @param count    总数
     * @return {@code true} yes, {@code false} no
     */
    boolean isLastPosition(
            int position,
            int count
    );

    /**
     * 判断 First Value 是否一致
     * @param value 待校验 Value
     * @return {@code true} yes, {@code false} no
     */
    boolean equalsFirstData(V value);

    /**
     * 判断 Last Value 是否一致
     * @param value 待校验 Value
     * @return {@code true} yes, {@code false} no
     */
    boolean equalsLastData(V value);

    /**
     * 判断 Position Value 是否一致
     * @param position 索引
     * @param value    待校验 Value
     * @return {@code true} yes, {@code false} no
     */
    boolean equalsPositionData(
            int position,
            V value
    );

    // ===============
    // = 数据处理方法 =
    // ===============

    /**
     * 添加数据
     * @param value Value
     */
    void addData(V value);

    /**
     * 添加数据
     * @param position 索引
     * @param value    Value
     */
    void addDataAt(
            int position,
            V value
    );

    /**
     * 添加数据集
     * @param collection {@link Collection}
     */
    void addDatas(Collection<V> collection);

    /**
     * 添加数据集
     * @param position   索引
     * @param collection {@link Collection}
     */
    void addDatasAt(
            int position,
            Collection<V> collection
    );

    /**
     * 添加数据集 ( 存在校验 )
     * @param collection {@link Collection}
     */
    void addDatasChecked(Collection<V> collection);

    /**
     * 添加数据集 ( 存在校验 )
     * @param position   索引
     * @param collection {@link Collection}
     */
    void addDatasAtChecked(
            int position,
            Collection<V> collection
    );

    // =

    /**
     * 移除数据
     * @param value Value
     * @return {@code true} success, {@code false} fail
     */
    boolean removeData(V value);

    /**
     * 移除数据
     * @param position 索引
     * @return remove position value
     */
    V removeDataAt(int position);

    /**
     * 移除数据集
     * @param collection {@link Collection}
     */
    void removeDatas(Collection<V> collection);

    // =

    /**
     * 替换数据
     * @param oldValue 旧的 Value
     * @param newValue 新的 Value
     * @return {@code true} success, {@code false} fail
     */
    boolean replaceData(
            V oldValue,
            V newValue
    );

    /**
     * 替换数据
     * @param position 索引
     * @param value    Value
     * @return remove position value
     */
    V replaceDataAt(
            int position,
            V value
    );

    // =

    /**
     * 数据中两个索引 Data 互换位置
     * @param fromPosition 待换位置索引
     * @param toPosition   替换后位置索引
     * @return {@code true} success, {@code false} fail
     */
    boolean swipePosition(
            int fromPosition,
            int toPosition
    );

    /**
     * 是否存在 Data
     * @param value Value
     * @return {@code true} yes, {@code false} no
     */
    boolean contains(V value);

    /**
     * 清空全部数据
     */
    void clearDataList();

    /**
     * 清空全部数据
     * @param notify 是否进行通知
     */
    void clearDataList(boolean notify);

    /**
     * 设置 List Data
     * @param lists {@link List}
     */
    void setDataList(List<V> lists);

    /**
     * 设置 List Data
     * @param lists  {@link List}
     * @param notify 是否进行通知
     */
    void setDataList(
            List<V> lists,
            boolean notify
    );

    // ===========
    // = 通知方法 =
    // ===========

    /**
     * 通知数据改变
     */
    void notifyDataChanged();

    /**
     * 通知某个数据改变
     * @param value {@link V}
     */
    void notifyDataChanged(V value);
}