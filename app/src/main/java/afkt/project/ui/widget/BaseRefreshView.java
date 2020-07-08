package afkt.project.ui.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import afkt.project.R;
import dev.assist.PageAssist;

/**
 * detail: Base Refresh、Load View
 * @author Ttt
 * <pre>
 *     通用 下拉刷新、上拉加载 封装 View
 * </pre>
 */
public class BaseRefreshView extends LinearLayout {

    public BaseRefreshView(Context context) {
        super(context);
        init();
    }

    public BaseRefreshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseRefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseRefreshView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    // =

    private FrameLayout        body;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView       recyclerView;
    private BaseQuickAdapter   adapter;
    // 请求页数辅助类
    private PageAssist<String> pageAssist = new PageAssist<>();

    /**
     * 默认初始化操作
     */
    private void init() {
        setOrientation(LinearLayout.VERTICAL);
        Context context = getContext();
        // 初始化 View
        View view = LayoutInflater.from(context).inflate(R.layout.base_refresh_view, null);
        body = view.findViewById(R.id.vid_brv_frame);
        smartRefreshLayout = view.findViewById(R.id.vid_brv_refresh);
        recyclerView = view.findViewById(R.id.vid_brv_recy);

        // 设置刷新、加载 View
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(context));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(context));

        // 打开刷新、加载
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setEnableLoadMore(true);
        // 不需要阻尼效果
        smartRefreshLayout.setEnableOverScrollDrag(false);

        addView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    // ===========
    // = get/set =
    // ===========

    /**
     * 获取 R.layout.base_refresh_view 最外层 View
     * @return {@link FrameLayout}
     */
    public FrameLayout getBody() {
        return body;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public BaseRefreshView setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        return this;
    }

    public <T extends BaseQuickAdapter> T getAdapter() {
        return (T) adapter;
    }

    public BaseRefreshView setAdapter(BaseQuickAdapter adapter) {
        if (adapter != null) {
            this.adapter = adapter;
            this.recyclerView.setAdapter(adapter);
        }
        return this;
    }

    public PageAssist<String> getPageAssist() {
        return pageAssist;
    }

    public BaseRefreshView setPageAssist(PageAssist<String> pageAssist) {
        this.pageAssist = pageAssist;
        return this;
    }

    // ============
    // = 对外公开 =
    // ============

    /**
     * 是否启用下拉刷新 ( 默认启用 )
     * @param enabled 是否启用
     * @return {@link BaseRefreshView}
     */
    public BaseRefreshView setEnableRefresh(boolean enabled) {
        smartRefreshLayout.setEnableRefresh(enabled);
        return this;
    }

    /**
     * 设置是否启用上拉加载更多 ( 默认启用 )
     * @param enabled 是否启用
     * @return {@link BaseRefreshView}
     */
    public BaseRefreshView setEnableLoadMore(boolean enabled) {
        smartRefreshLayout.setEnableLoadMore(enabled);
        return this;
    }

    /**
     * 设置数据全部加载完成, 将不能再次触发加载功能
     * @param noMoreData 是否有更多数据 {@code true} 无数据, {@code false} 还有数据
     * @return {@link BaseRefreshView}
     */
    public BaseRefreshView setNoMoreData(boolean noMoreData) {
        smartRefreshLayout.setNoMoreData(noMoreData);
        return this;
    }

    /**
     * 单独设置刷新监听器
     * @param listener OnRefreshListener 刷新监听器
     * @return {@link BaseRefreshView}
     */
    public BaseRefreshView setOnRefreshListener(OnRefreshListener listener) {
        if (listener != null) smartRefreshLayout.setOnRefreshListener(listener);
        return this;
    }

    /**
     * 单独设置加载监听器
     * @param listener OnLoadMoreListener 加载监听器
     * @return {@link BaseRefreshView}
     */
    public BaseRefreshView setOnLoadMoreListener(OnLoadMoreListener listener) {
        if (listener != null) smartRefreshLayout.setOnLoadMoreListener(listener);
        return this;
    }

    /**
     * 同时设置刷新和加载监听器
     * @param listener OnRefreshLoadMoreListener 刷新加载监听器
     * @return {@link BaseRefreshView}
     */
    public BaseRefreshView setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener listener) {
        if (listener != null) smartRefreshLayout.setOnRefreshLoadMoreListener(listener);
        return this;
    }

    /**
     * 完成刷新
     * @return {@link BaseRefreshView}
     */
    public BaseRefreshView finishRefresh() {
        if (smartRefreshLayout.isRefreshing()) smartRefreshLayout.finishRefresh();
        return this;
    }

    /**
     * 完成加载
     * @return {@link BaseRefreshView}
     */
    public BaseRefreshView finishLoadMore() {
        smartRefreshLayout.finishLoadMore();
        return this;
    }

    /**
     * 完成刷新、加载
     * @return {@link BaseRefreshView}
     */
    public BaseRefreshView finishRefreshAndLoad() {
        return finishRefresh().finishLoadMore();
    }

    /**
     * 完成刷新或加载
     * @param refresh 是否刷新
     * @return {@link BaseRefreshView}
     */
    public BaseRefreshView finishRefreshOrLoad(boolean refresh) {
        return refresh ? finishRefresh() : finishLoadMore();
    }

    // =

    /**
     * 获取添加头部 View 数量
     * @return 头部 View 数量
     */
    public int getHeaderLayoutCount() {
        if (adapter != null) return adapter.getHeaderLayoutCount();
        return 0;
    }

    /**
     * 添加头部 View
     * @param header header View
     * @return {@link BaseRefreshView}
     */
    public BaseRefreshView addHeaderView(View header) {
        if (adapter != null) adapter.addHeaderView(header);
        return this;
    }

    /**
     * 添加头部 View 到指定位置
     * @param header header View
     * @param index  index
     * @return {@link BaseRefreshView}
     */
    public BaseRefreshView addHeaderView(View header, int index) {
        if (adapter != null && index >= 0) adapter.addHeaderView(header, index);
        return this;
    }

    /**
     * 获取添加底部 View 数量
     * @return 底部 View 数量
     */
    public int getFooterLayoutCount() {
        if (adapter != null) return adapter.getFooterLayoutCount();
        return 0;
    }

    /**
     * 添加底部 View
     * @param header header View
     * @return {@link BaseRefreshView}
     */
    public BaseRefreshView addFooterView(View header) {
        if (adapter != null) adapter.addFooterView(header);
        return this;
    }

    /**
     * 添加底部 View 到指定位置
     * @param header header View
     * @param index  index
     * @return {@link BaseRefreshView}
     */
    public BaseRefreshView addFooterView(View header, int index) {
        if (adapter != null && index >= 0) adapter.addFooterView(header, index);
        return this;
    }
}