package dev.engine.core.refresh

import dev.engine.refresh.IRefreshEngine

/**
 * detail: Refresh View Config
 * @author Ttt
 */
open class RefreshConfig private constructor(
    config: RefreshConfig?
) : IRefreshEngine.EngineConfig {

    // 是否启用下拉刷新
    private var mEnableRefresh = true

    // 是否启用上拉加载更多
    private var mEnableLoadMore = false

    // 如果没有数据, 是否设置禁止加载更多
    private var mNoMoreDataUpdate = false

    // 是否启用纯滚动模式
    private var mPureScrollMode = false

    companion object {

        fun create(): RefreshConfig {
            return RefreshConfig(null)
        }

        fun create(config: RefreshConfig?): RefreshConfig {
            return RefreshConfig(config)
        }
    }

    init {
        config?.let {
            this.mEnableRefresh = it.mEnableRefresh
            this.mEnableLoadMore = it.mEnableLoadMore
            this.mNoMoreDataUpdate = it.mNoMoreDataUpdate
            this.mPureScrollMode = it.mPureScrollMode
        }
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 克隆配置信息
     * @return [RefreshConfig]
     */
    open fun clone(): RefreshConfig {
        return RefreshConfig(this)
    }

    // ===========
    // = get/set =
    // ===========

    override fun isEnableRefresh(): Boolean {
        return mEnableRefresh
    }

    open fun setEnableRefresh(enableRefresh: Boolean): RefreshConfig {
        mEnableRefresh = enableRefresh
        return this
    }

    override fun isEnableLoadMore(): Boolean {
        return mEnableLoadMore
    }

    open fun setEnableLoadMore(enableLoadMore: Boolean): RefreshConfig {
        mEnableLoadMore = enableLoadMore
        return this
    }

    override fun isNoMoreDataUpdate(): Boolean {
        return mNoMoreDataUpdate
    }

    open fun setNoMoreDataUpdate(noMoreDataUpdate: Boolean): RefreshConfig {
        mNoMoreDataUpdate = noMoreDataUpdate
        return this
    }
}