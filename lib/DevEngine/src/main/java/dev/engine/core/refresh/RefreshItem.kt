package dev.engine.core.refresh

import android.view.View
import dev.engine.refresh.IRefreshEngine
import java.lang.ref.WeakReference

/**
 * detail: Refresh View Item Params
 * @author Ttt
 */
open class RefreshItem private constructor(
    view: View?
) : IRefreshEngine.EngineItem {

    // Refresh View
    private var mView: WeakReference<View>? = view?.let { WeakReference(it) }

    // 未设置值
    private val unsetValue = -1

    // Refresh Config
    private var mConfig: IRefreshEngine.EngineConfig? = null

    // 内容 View
    private var mContent: View? = null

    // 内容宽度
    private var mContentWidth = unsetValue

    // 内容高度
    private var mContentHeight = unsetValue

    // 刷新头
    private var mHeader: Any? = null

    // Header 宽度
    private var mHeaderWidth = unsetValue

    // Header 高度
    private var mHeaderHeight = unsetValue

    // 加载尾
    private var mFooter: Any? = null

    // Footer 宽度
    private var mFooterWidth = unsetValue

    // Footer 高度
    private var mFooterHeight = unsetValue

    // 固定在 Header 下方的视图 id
    private var mFixedHeaderViewId = unsetValue

    // 固定在 Footer 上方的视图 id
    private var mFixedFooterViewId = unsetValue

    // Header 滚动时跟随滚动的视图 id
    private var mHeaderTranslationViewId = unsetValue

    // Footer 滚动时跟随滚动的视图 id
    private var mFooterTranslationViewId = unsetValue

    // 滚动边界判断
    private var mScrollBoundaryDecider: Any? = null

    // 刷新监听
    private var mOnRefreshListener: IRefreshEngine.OnRefreshListener? = null

    // 加载更多监听
    private var mOnLoadMoreListener: IRefreshEngine.OnLoadMoreListener? = null

    // 刷新、加载更多监听
    private var mOnRefreshLoadMoreListener: IRefreshEngine.OnRefreshLoadMoreListener? = null

    // 多功能监听
    private var mMultiListener: Any? = null

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

    /**
     * Refresh View
     */
    override fun view(): WeakReference<View>? {
        return mView
    }

    open fun setView(view: View?): RefreshItem {
        mView = view?.let { WeakReference(it) }
        return this
    }

    open fun release(): RefreshItem {
        mView?.clear()
        mView = null
        mConfig = null
        mContent = null
        mHeader = null
        mFooter = null
        mScrollBoundaryDecider = null
        mOnRefreshListener = null
        mOnLoadMoreListener = null
        mOnRefreshLoadMoreListener = null
        mMultiListener = null
        return this
    }

    /**
     * Refresh Config
     */
    override fun config(): IRefreshEngine.EngineConfig? {
        return mConfig
    }

    open fun setConfig(config: IRefreshEngine.EngineConfig?): RefreshItem {
        mConfig = config
        return this
    }

    /**
     * 内容 View
     */
    override fun content(): View? {
        return mContent
    }

    open fun setContent(content: View?): RefreshItem {
        mContent = content
        return this
    }

    /**
     * 内容宽度
     */
    override fun contentWidth(): Int {
        return mContentWidth
    }

    /**
     * 内容高度
     */
    override fun contentHeight(): Int {
        return mContentHeight
    }

    open fun setContentSize(
        width: Int,
        height: Int
    ): RefreshItem {
        mContentWidth = width
        mContentHeight = height
        return this
    }

    /**
     * 刷新头
     */
    override fun header(): Any? {
        return mHeader
    }

    open fun setHeader(header: Any?): RefreshItem {
        mHeader = header
        return this
    }

    /**
     * Header 宽度
     */
    override fun headerWidth(): Int {
        return mHeaderWidth
    }

    /**
     * Header 高度
     */
    override fun headerHeight(): Int {
        return mHeaderHeight
    }

    open fun setHeaderSize(
        width: Int,
        height: Int
    ): RefreshItem {
        mHeaderWidth = width
        mHeaderHeight = height
        return this
    }

    /**
     * 加载尾
     */
    override fun footer(): Any? {
        return mFooter
    }

    open fun setFooter(footer: Any?): RefreshItem {
        mFooter = footer
        return this
    }

    /**
     * Footer 宽度
     */
    override fun footerWidth(): Int {
        return mFooterWidth
    }

    /**
     * Footer 高度
     */
    override fun footerHeight(): Int {
        return mFooterHeight
    }

    open fun setFooterSize(
        width: Int,
        height: Int
    ): RefreshItem {
        mFooterWidth = width
        mFooterHeight = height
        return this
    }

    /**
     * 固定在 Header 下方的视图 id
     */
    override fun fixedHeaderViewId(): Int {
        return mFixedHeaderViewId
    }

    open fun setFixedHeaderViewId(fixedHeaderViewId: Int): RefreshItem {
        mFixedHeaderViewId = fixedHeaderViewId
        return this
    }

    /**
     * 固定在 Footer 上方的视图 id
     */
    override fun fixedFooterViewId(): Int {
        return mFixedFooterViewId
    }

    open fun setFixedFooterViewId(fixedFooterViewId: Int): RefreshItem {
        mFixedFooterViewId = fixedFooterViewId
        return this
    }

    /**
     * Header 滚动时跟随滚动的视图 id
     */
    override fun headerTranslationViewId(): Int {
        return mHeaderTranslationViewId
    }

    open fun setHeaderTranslationViewId(headerTranslationViewId: Int): RefreshItem {
        mHeaderTranslationViewId = headerTranslationViewId
        return this
    }

    /**
     * Footer 滚动时跟随滚动的视图 id
     */
    override fun footerTranslationViewId(): Int {
        return mFooterTranslationViewId
    }

    open fun setFooterTranslationViewId(footerTranslationViewId: Int): RefreshItem {
        mFooterTranslationViewId = footerTranslationViewId
        return this
    }

    /**
     * 滚动边界判断
     */
    override fun scrollBoundaryDecider(): Any? {
        return mScrollBoundaryDecider
    }

    open fun setScrollBoundaryDecider(
        scrollBoundaryDecider: Any?
    ): RefreshItem {
        mScrollBoundaryDecider = scrollBoundaryDecider
        return this
    }

    /**
     * 刷新监听
     */
    override fun onRefreshListener(): IRefreshEngine.OnRefreshListener? {
        return mOnRefreshListener
    }

    open fun setOnRefreshListener(
        listener: IRefreshEngine.OnRefreshListener?
    ): RefreshItem {
        mOnRefreshListener = listener
        return this
    }

    /**
     * 加载更多监听
     */
    override fun onLoadMoreListener(): IRefreshEngine.OnLoadMoreListener? {
        return mOnLoadMoreListener
    }

    open fun setOnLoadMoreListener(
        listener: IRefreshEngine.OnLoadMoreListener?
    ): RefreshItem {
        mOnLoadMoreListener = listener
        return this
    }

    /**
     * 刷新、加载更多监听
     */
    override fun onRefreshLoadMoreListener(): IRefreshEngine.OnRefreshLoadMoreListener? {
        return mOnRefreshLoadMoreListener
    }

    open fun setOnRefreshLoadMoreListener(
        listener: IRefreshEngine.OnRefreshLoadMoreListener?
    ): RefreshItem {
        mOnRefreshLoadMoreListener = listener
        return this
    }

    /**
     * 多功能监听
     */
    override fun multiListener(): Any? {
        return mMultiListener
    }

    open fun setMultiListener(listener: Any?): RefreshItem {
        mMultiListener = listener
        return this
    }
}