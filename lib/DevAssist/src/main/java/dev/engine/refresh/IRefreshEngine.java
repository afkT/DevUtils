package dev.engine.refresh;

import android.view.View;

/**
 * detail: Refresh View Engine 接口
 * @author Ttt
 */
public interface IRefreshEngine<Config extends IRefreshEngine.EngineConfig,
        Item extends IRefreshEngine.EngineItem> {

    /**
     * detail: Refresh Config
     * @author Ttt
     */
    interface EngineConfig {

        // 是否启用下拉刷新
        boolean isEnableRefresh();

        // 是否启用上拉加载更多
        boolean isEnableLoadMore();

        // 没有更多数据时是否关闭上拉加载更多
        boolean isNoMoreDataUpdate();
    }

    /**
     * detail: Refresh ( View、Listener、Params ) Item
     * @author Ttt
     */
    interface EngineItem {

        // Refresh View
        View view();

        // Refresh Config
        EngineConfig config();

        // 刷新头
        Object header();

        // 加载尾
        Object footer();

        // 滚动边界判断
        Object scrollBoundaryDecider();

        // 刷新监听
        OnRefreshListener onRefreshListener();

        // 加载更多监听
        OnLoadMoreListener onLoadMoreListener();

        // 刷新、加载更多监听
        OnRefreshLoadMoreListener onRefreshLoadMoreListener();
    }

    /**
     * detail: 刷新监听
     * @author Ttt
     */
    interface OnRefreshListener {

        void onRefresh(View view);
    }

    /**
     * detail: 加载更多监听
     * @author Ttt
     */
    interface OnLoadMoreListener {

        void onLoadMore(View view);
    }

    /**
     * detail: 刷新、加载更多监听
     * @author Ttt
     */
    interface OnRefreshLoadMoreListener
            extends OnRefreshListener,
            OnLoadMoreListener {
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 Refresh Engine Config
     * @return Refresh Config
     */
    Config getConfig();

    /**
     * 初始化 Refresh View
     * @param item Refresh Item
     * @return {@code true} success, {@code false} fail
     */
    boolean initialize(Item item);

    // ==========
    // = 开关配置 =
    // ==========

    /**
     * 是否启用下拉刷新
     * @param item    Refresh Item
     * @param enabled 是否启用
     * @return {@code true} success, {@code false} fail
     */
    boolean setEnableRefresh(
            Item item,
            boolean enabled
    );

    /**
     * 是否启用上拉加载更多
     * @param item    Refresh Item
     * @param enabled 是否启用
     * @return {@code true} success, {@code false} fail
     */
    boolean setEnableLoadMore(
            Item item,
            boolean enabled
    );

    /**
     * 设置数据全部加载完成
     * @param item       Refresh Item
     * @param noMoreData 是否没有更多数据
     * @return {@code true} success, {@code false} fail
     */
    boolean setNoMoreData(
            Item item,
            boolean noMoreData
    );

    /**
     * 重置更多数据状态
     * @param item    Refresh Item
     * @param count   当前返回的数据总数
     * @param perPage 每页数量
     * @return {@code true} success, {@code false} fail
     */
    boolean resetMoreData(
            Item item,
            int count,
            int perPage
    );

    /**
     * 关闭加载更多数据功能
     * @param item           Refresh Item
     * @param pureScrollMode 是否启用纯滚动模式
     * @return {@code true} success, {@code false} fail
     */
    boolean closeLoader(
            Item item,
            boolean pureScrollMode
    );

    /**
     * 开启加载更多数据功能
     * @param item Refresh Item
     * @return {@code true} success, {@code false} fail
     */
    boolean openLoader(Item item);

    // ==========
    // = 监听设置 =
    // ==========

    /**
     * 设置刷新监听器
     * @param item     Refresh Item
     * @param listener 刷新监听器
     * @return {@code true} success, {@code false} fail
     */
    boolean setOnRefreshListener(
            Item item,
            OnRefreshListener listener
    );

    /**
     * 设置加载监听器
     * @param item     Refresh Item
     * @param listener 加载监听器
     * @return {@code true} success, {@code false} fail
     */
    boolean setOnLoadMoreListener(
            Item item,
            OnLoadMoreListener listener
    );

    /**
     * 设置刷新和加载监听器
     * @param item     Refresh Item
     * @param listener 刷新、加载监听器
     * @return {@code true} success, {@code false} fail
     */
    boolean setOnRefreshLoadMoreListener(
            Item item,
            OnRefreshLoadMoreListener listener
    );

    // ==========
    // = 状态操作 =
    // ==========

    /**
     * 自动触发刷新
     * @param item Refresh Item
     * @return {@code true} success, {@code false} fail
     */
    boolean autoRefresh(Item item);

    /**
     * 完成刷新
     * @param item    Refresh Item
     * @param success 是否成功
     * @return {@code true} success, {@code false} fail
     */
    boolean finishRefresh(
            Item item,
            boolean success
    );

    /**
     * 完成加载
     * @param item    Refresh Item
     * @param success 是否成功
     * @return {@code true} success, {@code false} fail
     */
    boolean finishLoadMore(
            Item item,
            boolean success
    );

    /**
     * 完成刷新、加载
     * @param item    Refresh Item
     * @param success 是否成功
     * @return {@code true} success, {@code false} fail
     */
    boolean finishRefreshAndLoad(
            Item item,
            boolean success
    );

    /**
     * 完成刷新或加载
     * @param item    Refresh Item
     * @param refresh 是否刷新
     * @param success 是否成功
     * @return {@code true} success, {@code false} fail
     */
    boolean finishRefreshOrLoad(
            Item item,
            boolean refresh,
            boolean success
    );
}
