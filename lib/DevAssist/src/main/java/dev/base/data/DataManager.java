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

    // =

    /**
     * 获取 List Count
     * @return List.size()
     */
    int getDataCount();

    /**
     * 获取 List Position Item
     * @param position 索引
     * @return {@link V}
     */
    V getDataItem(int position);

    /**
     * 获取 Value Position
     * @param value {@link V}
     * @return position
     */
    int getDataItemPosition(V value);

    /**
     * 获取 First Item Data
     * @return {@link V}
     */
    V getDataFirstItem();

    /**
     * 获取 Last Item Data
     * @return {@link V}
     */
    V getDataLastItem();

    // ===========
    // = 其他方法 =
    // ===========

    /**
     * 判断是否 First Item Data
     * @param position 索引
     * @return {@code true} yes, {@code false} no
     */
    boolean isFirstItem(int position);

    /**
     * 判断是否 Last Item Data
     * @param position 索引
     * @return {@code true} yes, {@code false} no
     */
    boolean isLastItem(int position);

    /**
     * 判断是否 Last Item Data
     * @param position 索引
     * @param count    总数
     * @return {@code true} yes, {@code false} no
     */
    boolean isLastItem(
            int position,
            int count
    );

    // =

    /**
     * 清空全部数据
     */
    void clearDataList();

    /**
     * 清空全部数据
     * @param notify 是否通知适配器
     */
    void clearDataList(boolean notify);

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
    void addData(
            int position,
            V value
    );

    /**
     * 添加数据
     * @param collection {@link Collection}
     */
    void addAllData(Collection<V> collection);

    /**
     * 添加数据
     * @param position   索引
     * @param collection {@link Collection}
     */
    void addAllData(
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
    V removeData(int position);

    /**
     * 移除数据
     * @param collection {@link Collection}
     */
    void removeData(Collection<V> collection);

    // =

    /**
     * 设置 List Data
     * @param lists {@link List}
     */
    void setDataList(List<V> lists);

    /**
     * 设置 List Data
     * @param lists  {@link List}
     * @param notify 是否通知适配器
     */
    void setDataList(
            List<V> lists,
            boolean notify
    );

    // ===========
    // = 刷新方法 =
    // ===========

    /**
     * 通知数据改变
     */
    void notifyDataChanged();

    /**
     * 通知某个元素改变
     * @param element {@link V}
     */
    void notifyElementChanged(V element);
}