package dev.base.utils.assist

import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import dev.assist.PageAssist

/**
 * detail: DevBase RefreshLayout 辅助类
 * @author Ttt
 */
class DevBaseRefreshAssist<T> {

    // 请求页数辅助类
    private var mPageAssist = PageAssist<T>()

    // Refresh Layout
    private var mRefreshLayout: SmartRefreshLayout? = null

    // RecyclerView
    private var mRecyclerView: RecyclerView? = null

    // Adapter
    private var mAdapter: RecyclerView.Adapter<*>? = null

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

    fun <T : RecyclerView.Adapter<*>> getAdapter(): T? {
        mAdapter?.let { return mAdapter as? T }
        return null
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>?): DevBaseRefreshAssist<T> {
        adapter?.let {
            mAdapter = it
            mRecyclerView?.adapter = it
        }
        return this
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 设置 LayoutManager
     * @param layoutManager RecyclerView LayoutManager
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

    /**
     * 设置指定刷新头
     * @param header RefreshHeader 刷新头
     * @return [DevBaseRefreshAssist]
     */
    fun setRefreshHeader(header: RefreshHeader): DevBaseRefreshAssist<T> {
        mRefreshLayout?.setRefreshHeader(header)
        return this
    }

    /**
     * 设置指定刷新尾巴
     * @param footer RefreshFooter 刷新尾巴
     * @return [DevBaseRefreshAssist]
     */
    fun setRefreshFooter(footer: RefreshFooter): DevBaseRefreshAssist<T> {
        mRefreshLayout?.setRefreshFooter(footer)
        return this
    }
}