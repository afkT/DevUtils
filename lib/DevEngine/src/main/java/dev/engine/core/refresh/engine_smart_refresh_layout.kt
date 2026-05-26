package dev.engine.core.refresh

import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnMultiListener
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
        applyConfig(item, getRefreshConfig(item))
        item?.let { itemIt ->
            itemIt.content()?.let { content ->
                if (itemIt.contentWidth() >= 0 && itemIt.contentHeight() >= 0) {
                    refreshLayout.setRefreshContent(
                        content, itemIt.contentWidth(),
                        itemIt.contentHeight()
                    )
                } else {
                    refreshLayout.setRefreshContent(content)
                }
            }
            itemIt.header()?.let { header ->
                if (itemIt.headerWidth() >= 0 && itemIt.headerHeight() >= 0) {
                    setRefreshHeader(
                        itemIt, header, itemIt.headerWidth(),
                        itemIt.headerHeight()
                    )
                } else {
                    setRefreshHeader(itemIt, header)
                }
            }
            itemIt.footer()?.let { footer ->
                if (itemIt.footerWidth() >= 0 && itemIt.footerHeight() >= 0) {
                    setRefreshFooter(
                        itemIt, footer, itemIt.footerWidth(),
                        itemIt.footerHeight()
                    )
                } else {
                    setRefreshFooter(itemIt, footer)
                }
            }
            if (itemIt.fixedHeaderViewId() >= 0) {
                refreshLayout.setFixedHeaderViewId(itemIt.fixedHeaderViewId())
            }
            if (itemIt.fixedFooterViewId() >= 0) {
                refreshLayout.setFixedFooterViewId(itemIt.fixedFooterViewId())
            }
            if (itemIt.headerTranslationViewId() >= 0) {
                refreshLayout.setHeaderTranslationViewId(itemIt.headerTranslationViewId())
            }
            if (itemIt.footerTranslationViewId() >= 0) {
                refreshLayout.setFooterTranslationViewId(itemIt.footerTranslationViewId())
            }
            itemIt.scrollBoundaryDecider()?.let {
                setScrollBoundaryDecider(itemIt, it)
            }
            itemIt.multiListener()?.let {
                setOnMultiListener(itemIt, it)
            }
            itemIt.onRefreshLoadMoreListener()?.let {
                setOnRefreshLoadMoreListener(itemIt, it)
            } ?: run {
                itemIt.onRefreshListener()?.let {
                    setOnRefreshListener(itemIt, it)
                }
                itemIt.onLoadMoreListener()?.let {
                    setOnLoadMoreListener(itemIt, it)
                }
            }
        } ?: return false
        return true
    }

    override fun applyConfig(
        item: RefreshItem?,
        config: RefreshConfig?
    ): Boolean {
        val refreshLayout = getRefreshLayout(item) ?: return false
        config?.let { configIt ->
            if (configIt.headerHeight() >= 0) {
                refreshLayout.setHeaderHeight(configIt.headerHeight())
            }
            if (configIt.headerHeightPx() >= 0) {
                refreshLayout.setHeaderHeightPx(configIt.headerHeightPx())
            }
            if (configIt.footerHeight() >= 0) {
                refreshLayout.setFooterHeight(configIt.footerHeight())
            }
            if (configIt.footerHeightPx() >= 0) {
                refreshLayout.setFooterHeightPx(configIt.footerHeightPx())
            }
            if (configIt.headerInsetStart() >= 0) {
                refreshLayout.setHeaderInsetStart(configIt.headerInsetStart())
            }
            if (configIt.headerInsetStartPx() >= 0) {
                refreshLayout.setHeaderInsetStartPx(configIt.headerInsetStartPx())
            }
            if (configIt.footerInsetStart() >= 0) {
                refreshLayout.setFooterInsetStart(configIt.footerInsetStart())
            }
            if (configIt.footerInsetStartPx() >= 0) {
                refreshLayout.setFooterInsetStartPx(configIt.footerInsetStartPx())
            }
            if (configIt.dragRate() >= 0) {
                refreshLayout.setDragRate(configIt.dragRate())
            }
            if (configIt.headerMaxDragRate() >= 0) {
                refreshLayout.setHeaderMaxDragRate(configIt.headerMaxDragRate())
            }
            if (configIt.footerMaxDragRate() >= 0) {
                refreshLayout.setFooterMaxDragRate(configIt.footerMaxDragRate())
            }
            if (configIt.headerTriggerRate() >= 0) {
                refreshLayout.setHeaderTriggerRate(configIt.headerTriggerRate())
            }
            if (configIt.footerTriggerRate() >= 0) {
                refreshLayout.setFooterTriggerRate(configIt.footerTriggerRate())
            }
            configIt.reboundInterpolator()?.let {
                refreshLayout.setReboundInterpolator(it)
            }
            if (configIt.reboundDuration() >= 0) {
                refreshLayout.setReboundDuration(configIt.reboundDuration())
            }
            configIt.enableRefresh()?.let {
                refreshLayout.setEnableRefresh(it)
            }
            configIt.enableLoadMore()?.let {
                refreshLayout.setEnableLoadMore(it)
            }
            configIt.enableAutoLoadMore()?.let {
                refreshLayout.setEnableAutoLoadMore(it)
            }
            configIt.enableHeaderTranslationContent()?.let {
                refreshLayout.setEnableHeaderTranslationContent(it)
            }
            configIt.enableFooterTranslationContent()?.let {
                refreshLayout.setEnableFooterTranslationContent(it)
            }
            configIt.enableOverScrollBounce()?.let {
                refreshLayout.setEnableOverScrollBounce(it)
            }
            configIt.enablePureScrollMode()?.let {
                refreshLayout.setEnablePureScrollMode(it)
            }
            configIt.enableScrollContentWhenLoaded()?.let {
                refreshLayout.setEnableScrollContentWhenLoaded(it)
            }
            configIt.enableScrollContentWhenRefreshed()?.let {
                refreshLayout.setEnableScrollContentWhenRefreshed(it)
            }
            configIt.enableLoadMoreWhenContentNotFull()?.let {
                refreshLayout.setEnableLoadMoreWhenContentNotFull(it)
            }
            configIt.enableOverScrollDrag()?.let {
                refreshLayout.setEnableOverScrollDrag(it)
            }
            configIt.enableFooterFollowWhenNoMoreData()?.let {
                refreshLayout.setEnableFooterFollowWhenNoMoreData(it)
            }
            configIt.enableClipHeaderWhenFixedBehind()?.let {
                refreshLayout.setEnableClipHeaderWhenFixedBehind(it)
            }
            configIt.enableClipFooterWhenFixedBehind()?.let {
                refreshLayout.setEnableClipFooterWhenFixedBehind(it)
            }
            configIt.enableNestedScroll()?.let {
                refreshLayout.setEnableNestedScroll(it)
            }
            configIt.disableContentWhenRefresh()?.let {
                refreshLayout.setDisableContentWhenRefresh(it)
            }
            configIt.disableContentWhenLoading()?.let {
                refreshLayout.setDisableContentWhenLoading(it)
            }
            configIt.primaryColors()?.let {
                refreshLayout.setPrimaryColors(*it)
            }
            configIt.primaryColorIds()?.let {
                refreshLayout.setPrimaryColorsId(*it)
            }
        } ?: return false
        return true
    }

    // ==========
    // = 尺寸配置 =
    // ==========

    override fun setHeaderHeight(
        item: RefreshItem?,
        dp: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setHeaderHeight(dp); true
        } ?: false
    }

    override fun setHeaderHeightPx(
        item: RefreshItem?,
        px: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setHeaderHeightPx(px); true
        } ?: false
    }

    override fun setFooterHeight(
        item: RefreshItem?,
        dp: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFooterHeight(dp); true
        } ?: false
    }

    override fun setFooterHeightPx(
        item: RefreshItem?,
        px: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFooterHeightPx(px); true
        } ?: false
    }

    override fun setHeaderInsetStart(
        item: RefreshItem?,
        dp: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setHeaderInsetStart(dp); true
        } ?: false
    }

    override fun setHeaderInsetStartPx(
        item: RefreshItem?,
        px: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setHeaderInsetStartPx(px); true
        } ?: false
    }

    override fun setFooterInsetStart(
        item: RefreshItem?,
        dp: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFooterInsetStart(dp); true
        } ?: false
    }

    override fun setFooterInsetStartPx(
        item: RefreshItem?,
        px: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFooterInsetStartPx(px); true
        } ?: false
    }

    // ==========
    // = 拖拽动画 =
    // ==========

    override fun setDragRate(
        item: RefreshItem?,
        rate: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setDragRate(rate); true
        } ?: false
    }

    override fun setHeaderMaxDragRate(
        item: RefreshItem?,
        rate: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setHeaderMaxDragRate(rate); true
        } ?: false
    }

    override fun setFooterMaxDragRate(
        item: RefreshItem?,
        rate: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFooterMaxDragRate(rate); true
        } ?: false
    }

    override fun setHeaderTriggerRate(
        item: RefreshItem?,
        rate: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setHeaderTriggerRate(rate); true
        } ?: false
    }

    override fun setFooterTriggerRate(
        item: RefreshItem?,
        rate: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFooterTriggerRate(rate); true
        } ?: false
    }

    override fun setReboundInterpolator(
        item: RefreshItem?,
        interpolator: Interpolator?
    ): Boolean {
        interpolator ?: return false
        return getRefreshLayout(item)?.run {
            setReboundInterpolator(interpolator); true
        } ?: false
    }

    override fun setReboundDuration(
        item: RefreshItem?,
        duration: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setReboundDuration(duration); true
        } ?: false
    }

    // ==========
    // = 组件设置 =
    // ==========

    override fun setRefreshHeader(
        item: RefreshItem?,
        header: Any?
    ): Boolean {
        return getRefreshHeader(header)?.let {
            getRefreshLayout(item)?.run { setRefreshHeader(it); true }
        } ?: false
    }

    override fun setRefreshHeader(
        item: RefreshItem?,
        header: Any?,
        width: Int,
        height: Int
    ): Boolean {
        return getRefreshHeader(header)?.let {
            getRefreshLayout(item)?.run { setRefreshHeader(it, width, height); true }
        } ?: false
    }

    override fun setRefreshFooter(
        item: RefreshItem?,
        footer: Any?
    ): Boolean {
        return getRefreshFooter(footer)?.let {
            getRefreshLayout(item)?.run { setRefreshFooter(it); true }
        } ?: false
    }

    override fun setRefreshFooter(
        item: RefreshItem?,
        footer: Any?,
        width: Int,
        height: Int
    ): Boolean {
        return getRefreshFooter(footer)?.let {
            getRefreshLayout(item)?.run { setRefreshFooter(it, width, height); true }
        } ?: false
    }

    override fun setRefreshContent(
        item: RefreshItem?,
        content: View?
    ): Boolean {
        content ?: return false
        return getRefreshLayout(item)?.run {
            setRefreshContent(content); true
        } ?: false
    }

    override fun setRefreshContent(
        item: RefreshItem?,
        content: View?,
        width: Int,
        height: Int
    ): Boolean {
        content ?: return false
        return getRefreshLayout(item)?.run {
            setRefreshContent(content, width, height); true
        } ?: false
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

    override fun setEnableAutoLoadMore(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.let {
            it.setEnableAutoLoadMore(enabled)
            true
        } ?: false
    }

    override fun setEnableHeaderTranslationContent(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableHeaderTranslationContent(enabled); true
        } ?: false
    }

    override fun setEnableFooterTranslationContent(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableFooterTranslationContent(enabled); true
        } ?: false
    }

    override fun setEnableOverScrollBounce(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableOverScrollBounce(enabled); true
        } ?: false
    }

    override fun setEnablePureScrollMode(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnablePureScrollMode(enabled); true
        } ?: false
    }

    override fun setEnableScrollContentWhenLoaded(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableScrollContentWhenLoaded(enabled); true
        } ?: false
    }

    override fun setEnableScrollContentWhenRefreshed(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableScrollContentWhenRefreshed(enabled); true
        } ?: false
    }

    override fun setEnableLoadMoreWhenContentNotFull(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableLoadMoreWhenContentNotFull(enabled); true
        } ?: false
    }

    override fun setEnableOverScrollDrag(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableOverScrollDrag(enabled); true
        } ?: false
    }

    override fun setEnableFooterFollowWhenNoMoreData(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableFooterFollowWhenNoMoreData(enabled); true
        } ?: false
    }

    override fun setEnableClipHeaderWhenFixedBehind(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableClipHeaderWhenFixedBehind(enabled); true
        } ?: false
    }

    override fun setEnableClipFooterWhenFixedBehind(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableClipFooterWhenFixedBehind(enabled); true
        } ?: false
    }

    override fun setEnableNestedScroll(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableNestedScroll(enabled); true
        } ?: false
    }

    override fun setFixedHeaderViewId(
        item: RefreshItem?,
        id: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFixedHeaderViewId(id); true
        } ?: false
    }

    override fun setFixedFooterViewId(
        item: RefreshItem?,
        id: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFixedFooterViewId(id); true
        } ?: false
    }

    override fun setHeaderTranslationViewId(
        item: RefreshItem?,
        id: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setHeaderTranslationViewId(id); true
        } ?: false
    }

    override fun setFooterTranslationViewId(
        item: RefreshItem?,
        id: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFooterTranslationViewId(id); true
        } ?: false
    }

    override fun setDisableContentWhenRefresh(
        item: RefreshItem?,
        disable: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setDisableContentWhenRefresh(disable); true
        } ?: false
    }

    override fun setDisableContentWhenLoading(
        item: RefreshItem?,
        disable: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setDisableContentWhenLoading(disable); true
        } ?: false
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

    override fun setOnMultiListener(
        item: RefreshItem?,
        listener: Any?
    ): Boolean {
        return getOnMultiListener(listener)?.let {
            getRefreshLayout(item)?.run { setOnMultiListener(it); true }
        } ?: false
    }

    override fun setScrollBoundaryDecider(
        item: RefreshItem?,
        boundary: Any?
    ): Boolean {
        return getScrollBoundaryDecider(boundary)?.let {
            getRefreshLayout(item)?.run { setScrollBoundaryDecider(it); true }
        } ?: false
    }

    override fun setPrimaryColors(
        item: RefreshItem?,
        primaryColors: IntArray?
    ): Boolean {
        primaryColors ?: return false
        return getRefreshLayout(item)?.run {
            setPrimaryColors(*primaryColors); true
        } ?: false
    }

    override fun setPrimaryColorsId(
        item: RefreshItem?,
        primaryColorIds: IntArray?
    ): Boolean {
        primaryColorIds ?: return false
        return getRefreshLayout(item)?.run {
            setPrimaryColorsId(*primaryColorIds); true
        } ?: false
    }

    // ==========
    // = 状态操作 =
    // ==========

    override fun autoRefresh(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.autoRefresh() ?: false
    }

    override fun autoRefresh(
        item: RefreshItem?,
        delayed: Int
    ): Boolean {
        return getRefreshLayout(item)?.autoRefresh(delayed) ?: false
    }

    override fun autoRefreshAnimationOnly(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.autoRefreshAnimationOnly() ?: false
    }

    override fun autoRefresh(
        item: RefreshItem?,
        delayed: Int,
        duration: Int,
        dragRate: Float,
        animationOnly: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.autoRefresh(
            delayed, duration, dragRate, animationOnly
        ) ?: false
    }

    override fun autoLoadMore(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.autoLoadMore() ?: false
    }

    override fun autoLoadMore(
        item: RefreshItem?,
        delayed: Int
    ): Boolean {
        return getRefreshLayout(item)?.autoLoadMore(delayed) ?: false
    }

    override fun autoLoadMoreAnimationOnly(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.autoLoadMoreAnimationOnly() ?: false
    }

    override fun autoLoadMore(
        item: RefreshItem?,
        delayed: Int,
        duration: Int,
        dragRate: Float,
        animationOnly: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.autoLoadMore(
            delayed, duration, dragRate, animationOnly
        ) ?: false
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

    override fun finishRefresh(
        item: RefreshItem?,
        delayed: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            finishRefresh(delayed); true
        } ?: false
    }

    override fun finishRefresh(
        item: RefreshItem?,
        delayed: Int,
        success: Boolean,
        noMoreData: Boolean?
    ): Boolean {
        return getRefreshLayout(item)?.run {
            finishRefresh(delayed, success, noMoreData)
            true
        } ?: false
    }

    override fun finishRefreshWithNoMoreData(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.run {
            finishRefreshWithNoMoreData(); true
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

    override fun finishLoadMore(
        item: RefreshItem?,
        delayed: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            finishLoadMore(delayed); true
        } ?: false
    }

    override fun finishLoadMore(
        item: RefreshItem?,
        delayed: Int,
        success: Boolean,
        noMoreData: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            finishLoadMore(delayed, success, noMoreData)
            true
        } ?: false
    }

    override fun finishLoadMoreWithNoMoreData(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.run {
            finishLoadMoreWithNoMoreData(); true
        } ?: false
    }

    override fun closeHeaderOrFooter(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.run {
            closeHeaderOrFooter(); true
        } ?: false
    }

    override fun setNoMoreData(
        item: RefreshItem?,
        noMoreData: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setNoMoreData(noMoreData); true
        } ?: false
    }

    override fun resetNoMoreData(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.run {
            resetNoMoreData(); true
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
    // = 状态查询 =
    // ==========

    override fun getRefreshHeader(item: RefreshItem?): Any? {
        return getRefreshLayout(item)?.refreshHeader
    }

    override fun getRefreshFooter(item: RefreshItem?): Any? {
        return getRefreshLayout(item)?.refreshFooter
    }

    override fun getState(item: RefreshItem?): Any? {
        return getRefreshLayout(item)?.state
    }

    override fun getLayout(item: RefreshItem?): ViewGroup? {
        return getRefreshLayout(item)?.layout
    }

    override fun isRefreshing(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.isRefreshing ?: false
    }

    override fun isLoading(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.isLoading ?: false
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 获取 Refresh View 配置
     * @param item Refresh Item
     * @return [IRefreshEngine.EngineConfig]
     */
    protected open fun getRefreshConfig(item: RefreshItem?): RefreshConfig {
        return item?.config() as? RefreshConfig ?: mConfig
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
     * 获取 RefreshHeader
     * @param header Header Item
     * @return [RefreshHeader]
     */
    protected open fun getRefreshHeader(header: Any?): RefreshHeader? {
        return header as? RefreshHeader
    }

    /**
     * 获取 RefreshFooter
     * @param footer Footer Item
     * @return [RefreshFooter]
     */
    protected open fun getRefreshFooter(footer: Any?): RefreshFooter? {
        return footer as? RefreshFooter
    }

    /**
     * 获取多功能监听
     * @param listener Listener Item
     * @return [OnMultiListener]
     */
    protected open fun getOnMultiListener(listener: Any?): OnMultiListener? {
        return listener as? OnMultiListener
    }

    /**
     * 获取滚动边界判断
     * @param boundary Boundary Item
     * @return [ScrollBoundaryDecider]
     */
    protected open fun getScrollBoundaryDecider(boundary: Any?): ScrollBoundaryDecider? {
        return boundary as? ScrollBoundaryDecider
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