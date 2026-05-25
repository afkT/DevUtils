package dev.engine.refresh;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

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

        // Header 高度 ( dp )
        float headerHeight();

        // Header 高度 ( px )
        int headerHeightPx();

        // Footer 高度 ( dp )
        float footerHeight();

        // Footer 高度 ( px )
        int footerHeightPx();

        // Header 起始偏移量 ( dp )
        float headerInsetStart();

        // Header 起始偏移量 ( px )
        int headerInsetStartPx();

        // Footer 起始偏移量 ( dp )
        float footerInsetStart();

        // Footer 起始偏移量 ( px )
        int footerInsetStartPx();

        // 拖拽阻尼比率
        float dragRate();

        // Header 最大拖拽高度比率
        float headerMaxDragRate();

        // Footer 最大拖拽高度比率
        float footerMaxDragRate();

        // Header 触发刷新比率
        float headerTriggerRate();

        // Footer 触发加载比率
        float footerTriggerRate();

        // 回弹动画插值器
        Interpolator reboundInterpolator();

        // 回弹动画时长
        int reboundDuration();

        // 是否启用下拉刷新
        Boolean enableRefresh();

        // 是否启用上拉加载更多
        Boolean enableLoadMore();

        // 是否启用滚动到底部自动加载
        Boolean enableAutoLoadMore();

        // 是否启用 Header 移动内容
        Boolean enableHeaderTranslationContent();

        // 是否启用 Footer 移动内容
        Boolean enableFooterTranslationContent();

        // 是否启用越界回弹
        Boolean enableOverScrollBounce();

        // 是否启用纯滚动模式
        Boolean enablePureScrollMode();

        // 加载完成后是否滚动内容显示新数据
        Boolean enableScrollContentWhenLoaded();

        // 刷新完成后是否滚动内容显示新数据
        Boolean enableScrollContentWhenRefreshed();

        // 内容不满一页时是否可以加载更多
        Boolean enableLoadMoreWhenContentNotFull();

        // 是否启用越界拖动
        Boolean enableOverScrollDrag();

        // 没有更多数据后 Footer 是否跟随内容
        Boolean enableFooterFollowWhenNoMoreData();

        // Header FixedBehind 时是否裁剪 Header
        Boolean enableClipHeaderWhenFixedBehind();

        // Footer FixedBehind 时是否裁剪 Footer
        Boolean enableClipFooterWhenFixedBehind();

        // 是否启用嵌套滚动
        Boolean enableNestedScroll();

        // 刷新时是否禁用内容操作
        Boolean disableContentWhenRefresh();

        // 加载时是否禁用内容操作
        Boolean disableContentWhenLoading();

        // 主题色
        int[] primaryColors();

        // 主题色资源 id
        int[] primaryColorIds();
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

        // 内容 View
        View content();

        // 内容宽度
        int contentWidth();

        // 内容高度
        int contentHeight();

        // 刷新头
        Object header();

        // Header 宽度
        int headerWidth();

        // Header 高度
        int headerHeight();

        // 加载尾
        Object footer();

        // Footer 宽度
        int footerWidth();

        // Footer 高度
        int footerHeight();

        // 固定在 Header 下方的视图 id
        int fixedHeaderViewId();

        // 固定在 Footer 上方的视图 id
        int fixedFooterViewId();

        // Header 滚动时跟随滚动的视图 id
        int headerTranslationViewId();

        // Footer 滚动时跟随滚动的视图 id
        int footerTranslationViewId();

        // 滚动边界判断
        Object scrollBoundaryDecider();

        // 刷新监听
        OnRefreshListener onRefreshListener();

        // 加载更多监听
        OnLoadMoreListener onLoadMoreListener();

        // 刷新、加载更多监听
        OnRefreshLoadMoreListener onRefreshLoadMoreListener();

        // 多功能监听
        Object multiListener();
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

    /**
     * 应用 Refresh Config
     * @param item   Refresh Item
     * @param config Refresh Config
     * @return {@code true} success, {@code false} fail
     */
    boolean applyConfig(
            Item item,
            Config config
    );

    // ==========
    // = 尺寸配置 =
    // ==========

    /**
     * 设置 Header 高度
     * @param item Refresh Item
     * @param dp   dp
     * @return {@code true} success, {@code false} fail
     */
    boolean setHeaderHeight(
            Item item,
            float dp
    );

    /**
     * 设置 Header 高度
     * @param item Refresh Item
     * @param px   px
     * @return {@code true} success, {@code false} fail
     */
    boolean setHeaderHeightPx(
            Item item,
            int px
    );

    /**
     * 设置 Footer 高度
     * @param item Refresh Item
     * @param dp   dp
     * @return {@code true} success, {@code false} fail
     */
    boolean setFooterHeight(
            Item item,
            float dp
    );

    /**
     * 设置 Footer 高度
     * @param item Refresh Item
     * @param px   px
     * @return {@code true} success, {@code false} fail
     */
    boolean setFooterHeightPx(
            Item item,
            int px
    );

    /**
     * 设置 Header 起始偏移量
     * @param item Refresh Item
     * @param dp   dp
     * @return {@code true} success, {@code false} fail
     */
    boolean setHeaderInsetStart(
            Item item,
            float dp
    );

    /**
     * 设置 Header 起始偏移量
     * @param item Refresh Item
     * @param px   px
     * @return {@code true} success, {@code false} fail
     */
    boolean setHeaderInsetStartPx(
            Item item,
            int px
    );

    /**
     * 设置 Footer 起始偏移量
     * @param item Refresh Item
     * @param dp   dp
     * @return {@code true} success, {@code false} fail
     */
    boolean setFooterInsetStart(
            Item item,
            float dp
    );

    /**
     * 设置 Footer 起始偏移量
     * @param item Refresh Item
     * @param px   px
     * @return {@code true} success, {@code false} fail
     */
    boolean setFooterInsetStartPx(
            Item item,
            int px
    );

    // ==========
    // = 拖拽动画 =
    // ==========

    /**
     * 设置拖拽阻尼比率
     * @param item Refresh Item
     * @param rate 阻尼比率
     * @return {@code true} success, {@code false} fail
     */
    boolean setDragRate(
            Item item,
            float rate
    );

    /**
     * 设置 Header 最大拖拽高度比率
     * @param item Refresh Item
     * @param rate 比率
     * @return {@code true} success, {@code false} fail
     */
    boolean setHeaderMaxDragRate(
            Item item,
            float rate
    );

    /**
     * 设置 Footer 最大拖拽高度比率
     * @param item Refresh Item
     * @param rate 比率
     * @return {@code true} success, {@code false} fail
     */
    boolean setFooterMaxDragRate(
            Item item,
            float rate
    );

    /**
     * 设置 Header 触发刷新比率
     * @param item Refresh Item
     * @param rate 比率
     * @return {@code true} success, {@code false} fail
     */
    boolean setHeaderTriggerRate(
            Item item,
            float rate
    );

    /**
     * 设置 Footer 触发加载比率
     * @param item Refresh Item
     * @param rate 比率
     * @return {@code true} success, {@code false} fail
     */
    boolean setFooterTriggerRate(
            Item item,
            float rate
    );

    /**
     * 设置回弹动画插值器
     * @param item         Refresh Item
     * @param interpolator 动画插值器
     * @return {@code true} success, {@code false} fail
     */
    boolean setReboundInterpolator(
            Item item,
            Interpolator interpolator
    );

    /**
     * 设置回弹动画时长
     * @param item     Refresh Item
     * @param duration 时长
     * @return {@code true} success, {@code false} fail
     */
    boolean setReboundDuration(
            Item item,
            int duration
    );

    // ==========
    // = 组件设置 =
    // ==========

    /**
     * 设置刷新头
     * @param item   Refresh Item
     * @param header 刷新头
     * @return {@code true} success, {@code false} fail
     */
    boolean setRefreshHeader(
            Item item,
            Object header
    );

    /**
     * 设置刷新头
     * @param item   Refresh Item
     * @param header 刷新头
     * @param width  宽度
     * @param height 高度
     * @return {@code true} success, {@code false} fail
     */
    boolean setRefreshHeader(
            Item item,
            Object header,
            int width,
            int height
    );

    /**
     * 设置加载尾
     * @param item   Refresh Item
     * @param footer 加载尾
     * @return {@code true} success, {@code false} fail
     */
    boolean setRefreshFooter(
            Item item,
            Object footer
    );

    /**
     * 设置加载尾
     * @param item   Refresh Item
     * @param footer 加载尾
     * @param width  宽度
     * @param height 高度
     * @return {@code true} success, {@code false} fail
     */
    boolean setRefreshFooter(
            Item item,
            Object footer,
            int width,
            int height
    );

    /**
     * 设置内容 View
     * @param item    Refresh Item
     * @param content 内容 View
     * @return {@code true} success, {@code false} fail
     */
    boolean setRefreshContent(
            Item item,
            View content
    );

    /**
     * 设置内容 View
     * @param item    Refresh Item
     * @param content 内容 View
     * @param width   宽度
     * @param height  高度
     * @return {@code true} success, {@code false} fail
     */
    boolean setRefreshContent(
            Item item,
            View content,
            int width,
            int height
    );

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
     * 是否启用滚动到底部自动加载
     * @param item    Refresh Item
     * @param enabled 是否启用
     * @return {@code true} success, {@code false} fail
     */
    boolean setEnableAutoLoadMore(
            Item item,
            boolean enabled
    );

    /**
     * 是否启用 Header 移动内容
     * @param item    Refresh Item
     * @param enabled 是否启用
     * @return {@code true} success, {@code false} fail
     */
    boolean setEnableHeaderTranslationContent(
            Item item,
            boolean enabled
    );

    /**
     * 是否启用 Footer 移动内容
     * @param item    Refresh Item
     * @param enabled 是否启用
     * @return {@code true} success, {@code false} fail
     */
    boolean setEnableFooterTranslationContent(
            Item item,
            boolean enabled
    );

    /**
     * 是否启用越界回弹
     * @param item    Refresh Item
     * @param enabled 是否启用
     * @return {@code true} success, {@code false} fail
     */
    boolean setEnableOverScrollBounce(
            Item item,
            boolean enabled
    );

    /**
     * 是否启用纯滚动模式
     * @param item    Refresh Item
     * @param enabled 是否启用
     * @return {@code true} success, {@code false} fail
     */
    boolean setEnablePureScrollMode(
            Item item,
            boolean enabled
    );

    /**
     * 加载完成后是否滚动内容显示新数据
     * @param item    Refresh Item
     * @param enabled 是否启用
     * @return {@code true} success, {@code false} fail
     */
    boolean setEnableScrollContentWhenLoaded(
            Item item,
            boolean enabled
    );

    /**
     * 刷新完成后是否滚动内容显示新数据
     * @param item    Refresh Item
     * @param enabled 是否启用
     * @return {@code true} success, {@code false} fail
     */
    boolean setEnableScrollContentWhenRefreshed(
            Item item,
            boolean enabled
    );

    /**
     * 内容不满一页时是否可以加载更多
     * @param item    Refresh Item
     * @param enabled 是否启用
     * @return {@code true} success, {@code false} fail
     */
    boolean setEnableLoadMoreWhenContentNotFull(
            Item item,
            boolean enabled
    );

    /**
     * 是否启用越界拖动
     * @param item    Refresh Item
     * @param enabled 是否启用
     * @return {@code true} success, {@code false} fail
     */
    boolean setEnableOverScrollDrag(
            Item item,
            boolean enabled
    );

    /**
     * 没有更多数据后 Footer 是否跟随内容
     * @param item    Refresh Item
     * @param enabled 是否启用
     * @return {@code true} success, {@code false} fail
     */
    boolean setEnableFooterFollowWhenNoMoreData(
            Item item,
            boolean enabled
    );

    /**
     * Header FixedBehind 时是否裁剪 Header
     * @param item    Refresh Item
     * @param enabled 是否启用
     * @return {@code true} success, {@code false} fail
     */
    boolean setEnableClipHeaderWhenFixedBehind(
            Item item,
            boolean enabled
    );

    /**
     * Footer FixedBehind 时是否裁剪 Footer
     * @param item    Refresh Item
     * @param enabled 是否启用
     * @return {@code true} success, {@code false} fail
     */
    boolean setEnableClipFooterWhenFixedBehind(
            Item item,
            boolean enabled
    );

    /**
     * 是否启用嵌套滚动
     * @param item    Refresh Item
     * @param enabled 是否启用
     * @return {@code true} success, {@code false} fail
     */
    boolean setEnableNestedScroll(
            Item item,
            boolean enabled
    );

    /**
     * 设置固定在 Header 下方的视图 id
     * @param item Refresh Item
     * @param id   视图 id
     * @return {@code true} success, {@code false} fail
     */
    boolean setFixedHeaderViewId(
            Item item,
            int id
    );

    /**
     * 设置固定在 Footer 上方的视图 id
     * @param item Refresh Item
     * @param id   视图 id
     * @return {@code true} success, {@code false} fail
     */
    boolean setFixedFooterViewId(
            Item item,
            int id
    );

    /**
     * 设置 Header 滚动时跟随滚动的视图 id
     * @param item Refresh Item
     * @param id   视图 id
     * @return {@code true} success, {@code false} fail
     */
    boolean setHeaderTranslationViewId(
            Item item,
            int id
    );

    /**
     * 设置 Footer 滚动时跟随滚动的视图 id
     * @param item Refresh Item
     * @param id   视图 id
     * @return {@code true} success, {@code false} fail
     */
    boolean setFooterTranslationViewId(
            Item item,
            int id
    );

    /**
     * 刷新时是否禁用内容操作
     * @param item    Refresh Item
     * @param disable 是否禁用
     * @return {@code true} success, {@code false} fail
     */
    boolean setDisableContentWhenRefresh(
            Item item,
            boolean disable
    );

    /**
     * 加载时是否禁用内容操作
     * @param item    Refresh Item
     * @param disable 是否禁用
     * @return {@code true} success, {@code false} fail
     */
    boolean setDisableContentWhenLoading(
            Item item,
            boolean disable
    );

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

    /**
     * 设置多功能监听器
     * @param item     Refresh Item
     * @param listener 多功能监听器
     * @return {@code true} success, {@code false} fail
     */
    boolean setOnMultiListener(
            Item item,
            Object listener
    );

    /**
     * 设置滚动边界判断器
     * @param item     Refresh Item
     * @param boundary 滚动边界判断器
     * @return {@code true} success, {@code false} fail
     */
    boolean setScrollBoundaryDecider(
            Item item,
            Object boundary
    );

    /**
     * 设置主题色
     * @param item          Refresh Item
     * @param primaryColors 主题色
     * @return {@code true} success, {@code false} fail
     */
    boolean setPrimaryColors(
            Item item,
            int[] primaryColors
    );

    /**
     * 设置主题色资源 id
     * @param item            Refresh Item
     * @param primaryColorIds 主题色资源 id
     * @return {@code true} success, {@code false} fail
     */
    boolean setPrimaryColorsId(
            Item item,
            int[] primaryColorIds
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
     * 自动触发刷新
     * @param item    Refresh Item
     * @param delayed 开始延时
     * @return {@code true} success, {@code false} fail
     */
    boolean autoRefresh(
            Item item,
            int delayed
    );

    /**
     * 自动触发刷新动画
     * @param item Refresh Item
     * @return {@code true} success, {@code false} fail
     */
    boolean autoRefreshAnimationOnly(Item item);

    /**
     * 自动触发刷新
     * @param item          Refresh Item
     * @param delayed       开始延时
     * @param duration      拖拽动画持续时间
     * @param dragRate      拉拽高度比率
     * @param animationOnly 是否只显示动画
     * @return {@code true} success, {@code false} fail
     */
    boolean autoRefresh(
            Item item,
            int delayed,
            int duration,
            float dragRate,
            boolean animationOnly
    );

    /**
     * 自动触发加载
     * @param item Refresh Item
     * @return {@code true} success, {@code false} fail
     */
    boolean autoLoadMore(Item item);

    /**
     * 自动触发加载
     * @param item    Refresh Item
     * @param delayed 开始延时
     * @return {@code true} success, {@code false} fail
     */
    boolean autoLoadMore(
            Item item,
            int delayed
    );

    /**
     * 自动触发加载动画
     * @param item Refresh Item
     * @return {@code true} success, {@code false} fail
     */
    boolean autoLoadMoreAnimationOnly(Item item);

    /**
     * 自动触发加载
     * @param item          Refresh Item
     * @param delayed       开始延时
     * @param duration      拖拽动画持续时间
     * @param dragRate      拉拽高度比率
     * @param animationOnly 是否只显示动画
     * @return {@code true} success, {@code false} fail
     */
    boolean autoLoadMore(
            Item item,
            int delayed,
            int duration,
            float dragRate,
            boolean animationOnly
    );

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
     * 完成刷新
     * @param item    Refresh Item
     * @param delayed 开始延时
     * @return {@code true} success, {@code false} fail
     */
    boolean finishRefresh(
            Item item,
            int delayed
    );

    /**
     * 完成刷新
     * @param item       Refresh Item
     * @param delayed    开始延时
     * @param success    是否成功
     * @param noMoreData 是否没有更多数据
     * @return {@code true} success, {@code false} fail
     */
    boolean finishRefresh(
            Item item,
            int delayed,
            boolean success,
            Boolean noMoreData
    );

    /**
     * 完成刷新并标记没有更多数据
     * @param item Refresh Item
     * @return {@code true} success, {@code false} fail
     */
    boolean finishRefreshWithNoMoreData(Item item);

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
     * 完成加载
     * @param item    Refresh Item
     * @param delayed 开始延时
     * @return {@code true} success, {@code false} fail
     */
    boolean finishLoadMore(
            Item item,
            int delayed
    );

    /**
     * 完成加载
     * @param item       Refresh Item
     * @param delayed    开始延时
     * @param success    是否成功
     * @param noMoreData 是否没有更多数据
     * @return {@code true} success, {@code false} fail
     */
    boolean finishLoadMore(
            Item item,
            int delayed,
            boolean success,
            boolean noMoreData
    );

    /**
     * 完成加载并标记没有更多数据
     * @param item Refresh Item
     * @return {@code true} success, {@code false} fail
     */
    boolean finishLoadMoreWithNoMoreData(Item item);

    /**
     * 关闭 Header 或 Footer
     * @param item Refresh Item
     * @return {@code true} success, {@code false} fail
     */
    boolean closeHeaderOrFooter(Item item);

    /**
     * 设置没有更多数据状态
     * @param item       Refresh Item
     * @param noMoreData 是否没有更多数据
     * @return {@code true} success, {@code false} fail
     */
    boolean setNoMoreData(
            Item item,
            boolean noMoreData
    );

    /**
     * 重置没有更多数据状态
     * @param item Refresh Item
     * @return {@code true} success, {@code false} fail
     */
    boolean resetNoMoreData(Item item);

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

    // ==========
    // = 状态查询 =
    // ==========

    /**
     * 获取刷新头
     * @param item Refresh Item
     * @return 刷新头
     */
    Object getRefreshHeader(Item item);

    /**
     * 获取加载尾
     * @param item Refresh Item
     * @return 加载尾
     */
    Object getRefreshFooter(Item item);

    /**
     * 获取当前状态
     * @param item Refresh Item
     * @return 当前状态
     */
    Object getState(Item item);

    /**
     * 获取实体布局视图
     * @param item Refresh Item
     * @return 实体布局视图
     */
    ViewGroup getLayout(Item item);

    /**
     * 是否正在刷新
     * @param item Refresh Item
     * @return {@code true} yes, {@code false} no
     */
    boolean isRefreshing(Item item);

    /**
     * 是否正在加载
     * @param item Refresh Item
     * @return {@code true} yes, {@code false} no
     */
    boolean isLoading(Item item);
}
