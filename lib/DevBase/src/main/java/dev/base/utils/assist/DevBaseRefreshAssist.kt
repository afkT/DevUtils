package dev.base.utils.assist

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import dev.assist.PageAssist

/**
 * detail: DevBase RefreshLayout 辅助类
 * @author Ttt
 */
class DevBaseRefreshAssist<T> {

    // 日志 TAG
    private val TAG = DevBaseRefreshAssist::class.java.simpleName

    // 请求页数辅助类
    private var mPageAssist = PageAssist<T>()

    // Refresh Layout
    private var mRefreshLayout: SmartRefreshLayout? = null

    // RecyclerView
    private var mRecyclerView: RecyclerView? = null

    // 通用适配器
    private var mAdapter: BaseQuickAdapter<*, *>? = null

    // ===========
    // = get/set =
    // ===========

    fun getPageAssist(): PageAssist<T> {
        return mPageAssist
    }

    fun setPageAssist(pageAssist: PageAssist<T>): DevBaseRefreshAssist<T> {
        mPageAssist = pageAssist
        return this
    }

    fun getRefreshLayout(): SmartRefreshLayout? {
        return mRefreshLayout
    }

    fun setRefreshLayout(refreshLayout: SmartRefreshLayout?): DevBaseRefreshAssist<T> {
        mRefreshLayout = refreshLayout
        return this
    }

    fun getRecyclerView(): RecyclerView? {
        return mRecyclerView
    }

    fun setRecyclerView(recyclerView: RecyclerView?): DevBaseRefreshAssist<T> {
        mRecyclerView = recyclerView
        return this
    }

    fun <T : BaseQuickAdapter<*, *>?> getAdapter(): T? {
        mAdapter?.let { return mAdapter as T? }
        return null
    }

    fun setAdapter(adapter: BaseQuickAdapter<*, *>?): DevBaseRefreshAssist<T> {
        adapter?.let {
            mAdapter = it
            mRecyclerView?.adapter = it
        }
        return this
    }

    // ============
    // = 快捷方法 =
    // ===========

    /**
     * 设置 LayoutManager
     * @param layoutManager [LayoutManager]
     * @return [DevBaseRefreshAssist]
     */
    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager): DevBaseRefreshAssist<T> {
        mRecyclerView?.layoutManager = layoutManager
        return this
    }

    /**
     * 是否启用下拉刷新
     * @param enabled 是否启用
     * @return [DevBaseRefreshAssist]
     */
    fun setEnableRefresh(enabled: Boolean): DevBaseRefreshAssist<T> {
        mRefreshLayout?.setEnableRefresh(enabled)
        return this
    }

    /**
     * 是否启用上拉加载更多
     * @param enabled 是否启用
     * @return [DevBaseRefreshAssist]
     */
    fun setEnableLoadMore(enabled: Boolean): DevBaseRefreshAssist<T> {
        mRefreshLayout?.setEnableLoadMore(enabled)
        return this
    }

    /**
     * 设置数据全部加载完成 ( 将不能再次触发加载功能 )
     * @param noMoreData 是否有更多数据 `true` 无数据, `false` 还有数据
     * @return [DevBaseRefreshAssist]
     */
    fun setNoMoreData(noMoreData: Boolean): DevBaseRefreshAssist<T> {
        mRefreshLayout?.setNoMoreData(noMoreData)
        return this
    }

    /**
     * 设置刷新监听器
     * @param listener 刷新监听器
     * @return [DevBaseRefreshAssist]
     */
    fun setOnRefreshListener(listener: OnRefreshListener?): DevBaseRefreshAssist<T> {
        mRefreshLayout?.setOnRefreshListener(listener)
        return this
    }

    /**
     * 设置加载监听器
     * @param listener 加载监听器
     * @return [DevBaseRefreshAssist]
     */
    fun setOnLoadMoreListener(listener: OnLoadMoreListener?): DevBaseRefreshAssist<T> {
        mRefreshLayout?.setOnLoadMoreListener(listener)
        return this
    }

    /**
     * 设置刷新和加载监听器
     * @param listener 刷新、加载监听器
     * @return [DevBaseRefreshAssist]
     */
    fun setOnRefreshLoadMoreListener(listener: OnRefreshLoadMoreListener?): DevBaseRefreshAssist<T> {
        mRefreshLayout?.setOnRefreshLoadMoreListener(listener)
        return this
    }

    // =

    /**
     * 完成刷新
     * @return [DevBaseRefreshAssist]
     */
    fun finishRefresh(): DevBaseRefreshAssist<T> {
        mRefreshLayout?.let { if (it.isRefreshing) it.finishRefresh() }
        return this
    }

    /**
     * 完成加载
     * @return [DevBaseRefreshAssist]
     */
    fun finishLoadMore(): DevBaseRefreshAssist<T> {
        mRefreshLayout?.finishLoadMore()
        return this
    }

    /**
     * 完成刷新、加载
     * @return [DevBaseRefreshAssist]
     */
    fun finishRefreshAndLoad(): DevBaseRefreshAssist<T> {
        return finishRefresh().finishLoadMore()
    }

    /**
     * 完成刷新或加载
     * @param refresh 是否刷新
     * @return [DevBaseRefreshAssist]
     */
    fun finishRefreshOrLoad(refresh: Boolean): DevBaseRefreshAssist<T> {
        return if (refresh) finishRefresh() else finishLoadMore()
    }

    // =
}