package dev.engine.core.refresh

import android.view.View
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.ScrollBoundaryDecider
import dev.engine.refresh.IRefreshEngine

/**
 * detail: SmartRefreshLayout Refresh View Engine 实现
 * @author Ttt
 * @see https://github.com/scwang90/SmartRefreshLayout
 */
open class SmartRefreshLayoutEngineImpl(
    @JvmField protected val mConfig: RefreshConfig = RefreshConfig.create()
) : IRefreshEngine<RefreshConfig?, RefreshItem?> {

    // =============
    // = 对外公开方法 =
    // =============

    override fun getConfig(): RefreshConfig {
        return mConfig
    }

    override fun initialize(item: RefreshItem?): Boolean {
        val refreshLayout = getRefreshLayout(item) ?: return false
        val config = getRefreshConfig(item)
        refreshLayout.setEnableRefresh(config.isEnableRefresh())
            .setEnableLoadMore(config.isEnableLoadMore())
        item?.header()?.let { refreshLayout.setRefreshHeader(it) }
        item?.footer()?.let { refreshLayout.setRefreshFooter(it) }
        item?.scrollBoundaryDecider()?.let { refreshLayout.setScrollBoundaryDecider(it) }
        item?.onRefreshLoadMoreListener()?.let {
            setOnRefreshLoadMoreListener(item, it)
        } ?: run {
            item?.onRefreshListener()?.let { setOnRefreshListener(item, it) }
            item?.onLoadMoreListener()?.let { setOnLoadMoreListener(item, it) }
        }
        return true
    }

    // ==========
    // = 开关配置 =
    // ==========

    override fun setEnableRefresh(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.let {
            it.setEnableRefresh(enabled)
            true
        } ?: false
    }

    override fun setEnableLoadMore(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.let {
            it.setEnableLoadMore(enabled)
            true
        } ?: false
    }

    override fun setNoMoreData(
        item: RefreshItem?,
        noMoreData: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.let {
            it.setNoMoreData(noMoreData)
            true
        } ?: false
    }

    override fun resetMoreData(
        item: RefreshItem?,
        count: Int,
        perPage: Int
    ): Boolean {
        val refreshLayout = getRefreshLayout(item) ?: return false
        val noMoreData = count < perPage
        refreshLayout.setNoMoreData(noMoreData)
        if (getRefreshConfig(item).isNoMoreDataUpdate()) {
            refreshLayout.setEnableLoadMore(!noMoreData)
        }
        return true
    }

    override fun closeLoader(
        item: RefreshItem?,
        pureScrollMode: Boolean
    ): Boolean {
        val refreshLayout = getRefreshLayout(item) ?: return false
        refreshLayout.setEnableRefresh(false)
        if (pureScrollMode) {
            refreshLayout.setScrollBoundaryDecider(object : ScrollBoundaryDecider {
                override fun canRefresh(content: View?): Boolean {
                    return false
                }

                override fun canLoadMore(content: View?): Boolean {
                    return false
                }
            })
        }
        finishRefresh(item, true)
        return true
    }

    override fun openLoader(item: RefreshItem?): Boolean {
        val refreshLayout = getRefreshLayout(item) ?: return false
        refreshLayout.setEnableRefresh(true)
        finishRefresh(item, true)
        return true
    }

    // ==========
    // = 监听设置 =
    // ==========

    override fun setOnRefreshListener(
        item: RefreshItem?,
        listener: IRefreshEngine.OnRefreshListener?
    ): Boolean {
        val refreshLayout = getRefreshLayout(item) ?: return false
        refreshLayout.setOnRefreshListener { layout ->
            getRefreshView(layout, item)?.let { listener?.onRefresh(it) }
        }
        return true
    }

    override fun setOnLoadMoreListener(
        item: RefreshItem?,
        listener: IRefreshEngine.OnLoadMoreListener?
    ): Boolean {
        val refreshLayout = getRefreshLayout(item) ?: return false
        refreshLayout.setOnLoadMoreListener { layout ->
            getRefreshView(layout, item)?.let { listener?.onLoadMore(it) }
        }
        return true
    }

    override fun setOnRefreshLoadMoreListener(
        item: RefreshItem?,
        listener: IRefreshEngine.OnRefreshLoadMoreListener?
    ): Boolean {
        val refreshLayout = getRefreshLayout(item) ?: return false
        refreshLayout.setOnRefreshLoadMoreListener(object :
            com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener {

            override fun onRefresh(refreshLayout: RefreshLayout) {
                getRefreshView(refreshLayout, item)?.let { listener?.onRefresh(it) }
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getRefreshView(refreshLayout, item)?.let { listener?.onLoadMore(it) }
            }
        })
        return true
    }

    // ==========
    // = 状态操作 =
    // ==========

    override fun autoRefresh(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.autoRefresh() ?: false
    }

    override fun finishRefresh(
        item: RefreshItem?,
        success: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.let {
            if (it.isRefreshing) it.finishRefresh(success)
            true
        } ?: false
    }

    override fun finishLoadMore(
        item: RefreshItem?,
        success: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.let {
            it.finishLoadMore(success)
            true
        } ?: false
    }

    override fun finishRefreshAndLoad(
        item: RefreshItem?,
        success: Boolean
    ): Boolean {
        val finishRefresh = finishRefresh(item, success)
        val finishLoadMore = finishLoadMore(item, success)
        return finishRefresh || finishLoadMore
    }

    override fun finishRefreshOrLoad(
        item: RefreshItem?,
        refresh: Boolean,
        success: Boolean
    ): Boolean {
        return if (refresh) {
            finishRefresh(item, success)
        } else {
            finishLoadMore(item, success)
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 获取 Refresh View 配置
     * @param item Refresh Item
     * @return [IRefreshEngine.EngineConfig]
     */
    protected open fun getRefreshConfig(item: RefreshItem?): IRefreshEngine.EngineConfig {
        return item?.config() ?: mConfig
    }

    /**
     * 获取 SmartRefreshLayout
     * @param item Refresh Item
     * @return [SmartRefreshLayout]
     */
    protected open fun getRefreshLayout(item: RefreshItem?): SmartRefreshLayout? {
        return item?.view() as? SmartRefreshLayout
    }

    /**
     * 获取回调 View
     * @param refreshLayout RefreshLayout
     * @param item Refresh Item
     * @return [View]
     */
    protected open fun getRefreshView(
        refreshLayout: RefreshLayout?,
        item: RefreshItem?
    ): View? {
        if (refreshLayout is View) return refreshLayout
        return item?.view()
    }
}