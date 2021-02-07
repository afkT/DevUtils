package dev.base.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * detail: 数据管理接口
 * @param <T> 泛型
 * @author Ttt
 */
public interface DataManager<T> {

    // ===========
    // = 获取相关 =
    // ===========

    /**
     * 获取 List Data
     * @return {@link List}
     */
    List<T> getDataList();

    /**
     * 获取 ArrayList Data
     * @return {@link ArrayList}
     */
    ArrayList<T> getDataArrayList();

    /**
     * 获取 List Size
     * @return List.size()
     */
    int getDataSize();

    /**
     * 获取 List Position Data
     * @param position 索引
     * @return {@link T}
     */
    T getDataItem(int position);

    /**
     * 获取 Value Position
     * @param value {@link T}
     * @return position
     */
    int getDataItemPosition(T value);

    /**
     * 获取 First Data
     * @return {@link T}
     */
    T getFirstData();

    /**
     * 获取 Last Data
     * @return {@link T}
     */
    T getLastData();

    /**
     * 获取 Last Position
     * @return Last Position
     */
    int getLastPosition();

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
     * @param size     总数
     * @return {@code true} yes, {@code false} no
     */
    boolean isLastPosition(
            int position,
            int size
    );

    /**
     * 判断 First Value 是否一致
     * @param value 待校验 Value
     * @return {@code true} yes, {@code false} no
     */
    boolean equalsFirstData(T value);

    /**
     * 判断 Last Value 是否一致
     * @param value 待校验 Value
     * @return {@code true} yes, {@code false} no
     */
    boolean equalsLastData(T value);

    /**
     * 判断 Position Value 是否一致
     * @param position 索引
     * @param value    待校验 Value
     * @return {@code true} yes, {@code false} no
     */
    boolean equalsPositionData(
            int position,
            T value
    );

    // ======
    // = 增 =
    // ======

    /**
     * 添加数据
     * @param value Value
     * @return {@code true} success, {@code false} fail
     */
    boolean addData(T value);

    /**
     * 添加数据
     * @param position 索引
     * @param value    Value
     * @return {@code true} success, {@code false} fail
     */
    boolean addDataAt(
            int position,
            T value
    );

    /**
     * 添加数据集
     * @param collection {@link Collection}
     * @return {@code true} success, {@code false} fail
     */
    boolean addDatas(Collection<T> collection);

    /**
     * 添加数据集
     * @param position   索引
     * @param collection {@link Collection}
     * @return {@code true} success, {@code false} fail
     */
    boolean addDatasAt(
            int position,
            Collection<T> collection
    );

    /**
     * 添加数据集 ( 进行校验 )
     * @param collection {@link Collection}
     * @return {@code true} success, {@code false} fail
     */
    boolean addDatasChecked(Collection<T> collection);

    /**
     * 添加数据集 ( 进行校验 )
     * @param position   索引
     * @param collection {@link Collection}
     * @return {@code true} success, {@code false} fail
     */
    boolean addDatasCheckedAt(
            int position,
            Collection<T> collection
    );

    // ======
    // = 删 =
    // ======

    /**
     * 移除数据
     * @param value Value
     * @return {@code true} success, {@code false} fail
     */
    boolean removeData(T value);

    /**
     * 移除数据
     * @param position 索引
     * @return remove position value
     */
    T removeDataAt(int position);

    /**
     * 移除数据集
     * @param collection {@link Collection}
     * @return {@code true} success, {@code false} fail
     */
    boolean removeDatas(Collection<T> collection);

    // ======
    // = 改 =
    // ======

    /**
     * 替换数据
     * @param oldValue 旧的 Value
     * @param newValue 新的 Value
     * @return {@code true} success, {@code false} fail
     */
    boolean replaceData(
            T oldValue,
            T newValue
    );

    /**
     * 替换数据
     * @param position 索引
     * @param value    Value
     * @return {@code true} success, {@code false} fail
     */
    boolean replaceDataAt(
            int position,
            T value
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
    boolean contains(T value);

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
    void setDataList(List<T> lists);

    /**
     * 设置 List Data
     * @param lists  {@link List}
     * @param notify 是否进行通知
     */
    void setDataList(
            List<T> lists,
            boolean notify
    );
}