package dev.engine.core.refresh

import android.view.View
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.listener.ScrollBoundaryDecider
import dev.engine.refresh.IRefreshEngine

/**
 * detail: Refresh View Item Params
 * @author Ttt
 */
open class RefreshItem private constructor(
    private var mView: View?
) : IRefreshEngine.EngineItem {

    // Refresh Config
    private var mConfig: IRefreshEngine.EngineConfig? = null

    // 刷新头
    private var mHeader: RefreshHeader? = null

    // 加载尾
    private var mFooter: RefreshFooter? = null

    // 滚动边界判断
    private var mScrollBoundaryDecider: ScrollBoundaryDecider? = null

    // 刷新监听
    private var mOnRefreshListener: IRefreshEngine.OnRefreshListener? = null

    // 加载更多监听
    private var mOnLoadMoreListener: IRefreshEngine.OnLoadMoreListener? = null

    // 刷新、加载更多监听
    private var mOnRefreshLoadMoreListener: IRefreshEngine.OnRefreshLoadMoreListener? = null

    companion object {

        /**
         * 创建 Refresh View Item
         * @param view Refresh View
         * @return [RefreshItem]
         */
        fun create(view: View?): RefreshItem {
            return RefreshItem(view)
        }
    }

    // ===========
    // = get/set =
    // ===========

    override fun view(): View? {
        return mView
    }

    open fun setView(view: View?): RefreshItem {
        mView = view
        return this
    }

    override fun config(): IRefreshEngine.EngineConfig? {
        return mConfig
    }

    open fun setConfig(config: IRefreshEngine.EngineConfig?): RefreshItem {
        mConfig = config
        return this
    }

    override fun header(): RefreshHeader? {
        return mHeader
    }

    open fun setHeader(header: RefreshHeader?): RefreshItem {
        mHeader = header
        return this
    }

    override fun footer(): RefreshFooter? {
        return mFooter
    }

    open fun setFooter(footer: RefreshFooter?): RefreshItem {
        mFooter = footer
        return this
    }

    override fun scrollBoundaryDecider(): ScrollBoundaryDecider? {
        return mScrollBoundaryDecider
    }

    open fun setScrollBoundaryDecider(
        scrollBoundaryDecider: ScrollBoundaryDecider?
    ): RefreshItem {
        mScrollBoundaryDecider = scrollBoundaryDecider
        return this
    }

    override fun onRefreshListener(): IRefreshEngine.OnRefreshListener? {
        return mOnRefreshListener
    }

    open fun setOnRefreshListener(
        listener: IRefreshEngine.OnRefreshListener?
    ): RefreshItem {
        mOnRefreshListener = listener
        return this
    }

    override fun onLoadMoreListener(): IRefreshEngine.OnLoadMoreListener? {
        return mOnLoadMoreListener
    }

    open fun setOnLoadMoreListener(
        listener: IRefreshEngine.OnLoadMoreListener?
    ): RefreshItem {
        mOnLoadMoreListener = listener
        return this
    }

    override fun onRefreshLoadMoreListener(): IRefreshEngine.OnRefreshLoadMoreListener? {
        return mOnRefreshLoadMoreListener
    }

    open fun setOnRefreshLoadMoreListener(
        listener: IRefreshEngine.OnRefreshLoadMoreListener?
    ): RefreshItem {
        mOnRefreshLoadMoreListener = listener
        return this
    }
}