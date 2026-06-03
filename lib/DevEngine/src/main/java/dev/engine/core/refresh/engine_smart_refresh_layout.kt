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
    @JvmField protected val mConfig: RefreshConfig
) : IRefreshEngine<RefreshConfig?, RefreshItem?> {

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 Refresh Engine Config
     * @return Refresh Config
     */
    override fun getConfig(): RefreshConfig {
        return mConfig
    }

    /**
     * 初始化 Refresh View
     * @param item Refresh Item
     * @return `true` success, `false` fail
     */
    override fun initialize(item: RefreshItem?): Boolean {
        val refreshLayout = getRefreshLayout(item) ?: return false
        applyConfig(item, getRefreshConfig(item))
        item?.let { itemIt ->
            itemIt.content()?.let { content ->
                val contentWidth = itemIt.contentWidth()
                val contentHeight = itemIt.contentHeight()
                if (contentWidth >= 0 && contentHeight >= 0) {
                    refreshLayout.setRefreshContent(
                        content, contentWidth,
                        contentHeight
                    )
                } else {
                    refreshLayout.setRefreshContent(content)
                }
            }
            itemIt.header()?.let { header ->
                val headerWidth = itemIt.headerWidth()
                val headerHeight = itemIt.headerHeight()
                if (headerWidth >= 0 && headerHeight >= 0) {
                    setRefreshHeader(
                        itemIt, header, headerWidth,
                        headerHeight
                    )
                } else {
                    setRefreshHeader(itemIt, header)
                }
            }
            itemIt.footer()?.let { footer ->
                val footerWidth = itemIt.footerWidth()
                val footerHeight = itemIt.footerHeight()
                if (footerWidth >= 0 && footerHeight >= 0) {
                    setRefreshFooter(
                        itemIt, footer, footerWidth,
                        footerHeight
                    )
                } else {
                    setRefreshFooter(itemIt, footer)
                }
            }
            val fixedHeaderViewId = itemIt.fixedHeaderViewId()
            if (fixedHeaderViewId >= 0) {
                refreshLayout.setFixedHeaderViewId(fixedHeaderViewId)
            }
            val fixedFooterViewId = itemIt.fixedFooterViewId()
            if (fixedFooterViewId >= 0) {
                refreshLayout.setFixedFooterViewId(fixedFooterViewId)
            }
            val headerTranslationViewId = itemIt.headerTranslationViewId()
            if (headerTranslationViewId >= 0) {
                refreshLayout.setHeaderTranslationViewId(headerTranslationViewId)
            }
            val footerTranslationViewId = itemIt.footerTranslationViewId()
            if (footerTranslationViewId >= 0) {
                refreshLayout.setFooterTranslationViewId(footerTranslationViewId)
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

    /**
     * 应用 Refresh Config
     * @param item Refresh Item
     * @param config Refresh Config
     * @return `true` success, `false` fail
     */
    override fun applyConfig(
        item: RefreshItem?,
        config: RefreshConfig?
    ): Boolean {
        val refreshLayout = getRefreshLayout(item) ?: return false
        config?.let { configIt ->
            val headerHeight = configIt.headerHeight()
            if (headerHeight >= 0) {
                refreshLayout.setHeaderHeight(headerHeight)
            }
            val headerHeightPx = configIt.headerHeightPx()
            if (headerHeightPx >= 0) {
                refreshLayout.setHeaderHeightPx(headerHeightPx)
            }
            val footerHeight = configIt.footerHeight()
            if (footerHeight >= 0) {
                refreshLayout.setFooterHeight(footerHeight)
            }
            val footerHeightPx = configIt.footerHeightPx()
            if (footerHeightPx >= 0) {
                refreshLayout.setFooterHeightPx(footerHeightPx)
            }
            val headerInsetStart = configIt.headerInsetStart()
            if (headerInsetStart >= 0) {
                refreshLayout.setHeaderInsetStart(headerInsetStart)
            }
            val headerInsetStartPx = configIt.headerInsetStartPx()
            if (headerInsetStartPx >= 0) {
                refreshLayout.setHeaderInsetStartPx(headerInsetStartPx)
            }
            val footerInsetStart = configIt.footerInsetStart()
            if (footerInsetStart >= 0) {
                refreshLayout.setFooterInsetStart(footerInsetStart)
            }
            val footerInsetStartPx = configIt.footerInsetStartPx()
            if (footerInsetStartPx >= 0) {
                refreshLayout.setFooterInsetStartPx(footerInsetStartPx)
            }
            val dragRate = configIt.dragRate()
            if (dragRate >= 0) {
                refreshLayout.setDragRate(dragRate)
            }
            val headerMaxDragRate = configIt.headerMaxDragRate()
            if (headerMaxDragRate >= 0) {
                refreshLayout.setHeaderMaxDragRate(headerMaxDragRate)
            }
            val footerMaxDragRate = configIt.footerMaxDragRate()
            if (footerMaxDragRate >= 0) {
                refreshLayout.setFooterMaxDragRate(footerMaxDragRate)
            }
            val headerTriggerRate = configIt.headerTriggerRate()
            if (headerTriggerRate >= 0) {
                refreshLayout.setHeaderTriggerRate(headerTriggerRate)
            }
            val footerTriggerRate = configIt.footerTriggerRate()
            if (footerTriggerRate >= 0) {
                refreshLayout.setFooterTriggerRate(footerTriggerRate)
            }
            configIt.reboundInterpolator()?.let {
                refreshLayout.setReboundInterpolator(it)
            }
            val reboundDuration = configIt.reboundDuration()
            if (reboundDuration >= 0) {
                refreshLayout.setReboundDuration(reboundDuration)
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

    /**
     * 设置 Header 高度
     * @param item Refresh Item
     * @param dp dp
     * @return `true` success, `false` fail
     */
    override fun setHeaderHeight(
        item: RefreshItem?,
        dp: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setHeaderHeight(dp); true
        } ?: false
    }

    /**
     * 设置 Header 高度
     * @param item Refresh Item
     * @param px px
     * @return `true` success, `false` fail
     */
    override fun setHeaderHeightPx(
        item: RefreshItem?,
        px: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setHeaderHeightPx(px); true
        } ?: false
    }

    /**
     * 设置 Footer 高度
     * @param item Refresh Item
     * @param dp dp
     * @return `true` success, `false` fail
     */
    override fun setFooterHeight(
        item: RefreshItem?,
        dp: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFooterHeight(dp); true
        } ?: false
    }

    /**
     * 设置 Footer 高度
     * @param item Refresh Item
     * @param px px
     * @return `true` success, `false` fail
     */
    override fun setFooterHeightPx(
        item: RefreshItem?,
        px: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFooterHeightPx(px); true
        } ?: false
    }

    /**
     * 设置 Header 起始偏移量
     * @param item Refresh Item
     * @param dp dp
     * @return `true` success, `false` fail
     */
    override fun setHeaderInsetStart(
        item: RefreshItem?,
        dp: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setHeaderInsetStart(dp); true
        } ?: false
    }

    /**
     * 设置 Header 起始偏移量
     * @param item Refresh Item
     * @param px px
     * @return `true` success, `false` fail
     */
    override fun setHeaderInsetStartPx(
        item: RefreshItem?,
        px: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setHeaderInsetStartPx(px); true
        } ?: false
    }

    /**
     * 设置 Footer 起始偏移量
     * @param item Refresh Item
     * @param dp dp
     * @return `true` success, `false` fail
     */
    override fun setFooterInsetStart(
        item: RefreshItem?,
        dp: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFooterInsetStart(dp); true
        } ?: false
    }

    /**
     * 设置 Footer 起始偏移量
     * @param item Refresh Item
     * @param px px
     * @return `true` success, `false` fail
     */
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

    /**
     * 设置拖拽阻尼比率
     * @param item Refresh Item
     * @param rate 阻尼比率
     * @return `true` success, `false` fail
     */
    override fun setDragRate(
        item: RefreshItem?,
        rate: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setDragRate(rate); true
        } ?: false
    }

    /**
     * 设置 Header 最大拖拽高度比率
     * @param item Refresh Item
     * @param rate 比率
     * @return `true` success, `false` fail
     */
    override fun setHeaderMaxDragRate(
        item: RefreshItem?,
        rate: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setHeaderMaxDragRate(rate); true
        } ?: false
    }

    /**
     * 设置 Footer 最大拖拽高度比率
     * @param item Refresh Item
     * @param rate 比率
     * @return `true` success, `false` fail
     */
    override fun setFooterMaxDragRate(
        item: RefreshItem?,
        rate: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFooterMaxDragRate(rate); true
        } ?: false
    }

    /**
     * 设置 Header 触发刷新比率
     * @param item Refresh Item
     * @param rate 比率
     * @return `true` success, `false` fail
     */
    override fun setHeaderTriggerRate(
        item: RefreshItem?,
        rate: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setHeaderTriggerRate(rate); true
        } ?: false
    }

    /**
     * 设置 Footer 触发加载比率
     * @param item Refresh Item
     * @param rate 比率
     * @return `true` success, `false` fail
     */
    override fun setFooterTriggerRate(
        item: RefreshItem?,
        rate: Float
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFooterTriggerRate(rate); true
        } ?: false
    }

    /**
     * 设置回弹动画插值器
     * @param item Refresh Item
     * @param interpolator 动画插值器
     * @return `true` success, `false` fail
     */
    override fun setReboundInterpolator(
        item: RefreshItem?,
        interpolator: Interpolator?
    ): Boolean {
        interpolator ?: return false
        return getRefreshLayout(item)?.run {
            setReboundInterpolator(interpolator); true
        } ?: false
    }

    /**
     * 设置回弹动画时长
     * @param item Refresh Item
     * @param duration 时长
     * @return `true` success, `false` fail
     */
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

    /**
     * 设置刷新头
     * @param item Refresh Item
     * @param header 刷新头
     * @return `true` success, `false` fail
     */
    override fun setRefreshHeader(
        item: RefreshItem?,
        header: Any?
    ): Boolean {
        return getRefreshHeader(header)?.let {
            getRefreshLayout(item)?.run { setRefreshHeader(it); true }
        } ?: false
    }

    /**
     * 设置刷新头
     * @param item Refresh Item
     * @param header 刷新头
     * @param width 宽度
     * @param height 高度
     * @return `true` success, `false` fail
     */
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

    /**
     * 设置加载尾
     * @param item Refresh Item
     * @param footer 加载尾
     * @return `true` success, `false` fail
     */
    override fun setRefreshFooter(
        item: RefreshItem?,
        footer: Any?
    ): Boolean {
        return getRefreshFooter(footer)?.let {
            getRefreshLayout(item)?.run { setRefreshFooter(it); true }
        } ?: false
    }

    /**
     * 设置加载尾
     * @param item Refresh Item
     * @param footer 加载尾
     * @param width 宽度
     * @param height 高度
     * @return `true` success, `false` fail
     */
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

    /**
     * 设置内容 View
     * @param item Refresh Item
     * @param content 内容 View
     * @return `true` success, `false` fail
     */
    override fun setRefreshContent(
        item: RefreshItem?,
        content: View?
    ): Boolean {
        content ?: return false
        return getRefreshLayout(item)?.run {
            setRefreshContent(content); true
        } ?: false
    }

    /**
     * 设置内容 View
     * @param item Refresh Item
     * @param content 内容 View
     * @param width 宽度
     * @param height 高度
     * @return `true` success, `false` fail
     */
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

    /**
     * 是否启用下拉刷新
     * @param item Refresh Item
     * @param enabled 是否启用
     * @return `true` success, `false` fail
     */
    override fun setEnableRefresh(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.let {
            it.setEnableRefresh(enabled)
            true
        } ?: false
    }

    /**
     * 是否启用上拉加载更多
     * @param item Refresh Item
     * @param enabled 是否启用
     * @return `true` success, `false` fail
     */
    override fun setEnableLoadMore(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.let {
            it.setEnableLoadMore(enabled)
            true
        } ?: false
    }

    /**
     * 是否启用滚动到底部自动加载
     * @param item Refresh Item
     * @param enabled 是否启用
     * @return `true` success, `false` fail
     */
    override fun setEnableAutoLoadMore(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.let {
            it.setEnableAutoLoadMore(enabled)
            true
        } ?: false
    }

    /**
     * 是否启用 Header 移动内容
     * @param item Refresh Item
     * @param enabled 是否启用
     * @return `true` success, `false` fail
     */
    override fun setEnableHeaderTranslationContent(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableHeaderTranslationContent(enabled); true
        } ?: false
    }

    /**
     * 是否启用 Footer 移动内容
     * @param item Refresh Item
     * @param enabled 是否启用
     * @return `true` success, `false` fail
     */
    override fun setEnableFooterTranslationContent(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableFooterTranslationContent(enabled); true
        } ?: false
    }

    /**
     * 是否启用越界回弹
     * @param item Refresh Item
     * @param enabled 是否启用
     * @return `true` success, `false` fail
     */
    override fun setEnableOverScrollBounce(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableOverScrollBounce(enabled); true
        } ?: false
    }

    /**
     * 是否启用纯滚动模式
     * @param item Refresh Item
     * @param enabled 是否启用
     * @return `true` success, `false` fail
     */
    override fun setEnablePureScrollMode(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnablePureScrollMode(enabled); true
        } ?: false
    }

    /**
     * 加载完成后是否滚动内容显示新数据
     * @param item Refresh Item
     * @param enabled 是否启用
     * @return `true` success, `false` fail
     */
    override fun setEnableScrollContentWhenLoaded(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableScrollContentWhenLoaded(enabled); true
        } ?: false
    }

    /**
     * 刷新完成后是否滚动内容显示新数据
     * @param item Refresh Item
     * @param enabled 是否启用
     * @return `true` success, `false` fail
     */
    override fun setEnableScrollContentWhenRefreshed(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableScrollContentWhenRefreshed(enabled); true
        } ?: false
    }

    /**
     * 内容不满一页时是否可以加载更多
     * @param item Refresh Item
     * @param enabled 是否启用
     * @return `true` success, `false` fail
     */
    override fun setEnableLoadMoreWhenContentNotFull(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableLoadMoreWhenContentNotFull(enabled); true
        } ?: false
    }

    /**
     * 是否启用越界拖动
     * @param item Refresh Item
     * @param enabled 是否启用
     * @return `true` success, `false` fail
     */
    override fun setEnableOverScrollDrag(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableOverScrollDrag(enabled); true
        } ?: false
    }

    /**
     * 没有更多数据后 Footer 是否跟随内容
     * @param item Refresh Item
     * @param enabled 是否启用
     * @return `true` success, `false` fail
     */
    override fun setEnableFooterFollowWhenNoMoreData(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableFooterFollowWhenNoMoreData(enabled); true
        } ?: false
    }

    /**
     * Header FixedBehind 时是否裁剪 Header
     * @param item Refresh Item
     * @param enabled 是否启用
     * @return `true` success, `false` fail
     */
    override fun setEnableClipHeaderWhenFixedBehind(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableClipHeaderWhenFixedBehind(enabled); true
        } ?: false
    }

    /**
     * Footer FixedBehind 时是否裁剪 Footer
     * @param item Refresh Item
     * @param enabled 是否启用
     * @return `true` success, `false` fail
     */
    override fun setEnableClipFooterWhenFixedBehind(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableClipFooterWhenFixedBehind(enabled); true
        } ?: false
    }

    /**
     * 是否启用嵌套滚动
     * @param item Refresh Item
     * @param enabled 是否启用
     * @return `true` success, `false` fail
     */
    override fun setEnableNestedScroll(
        item: RefreshItem?,
        enabled: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setEnableNestedScroll(enabled); true
        } ?: false
    }

    /**
     * 设置固定在 Header 下方的视图 id
     * @param item Refresh Item
     * @param id 视图 id
     * @return `true` success, `false` fail
     */
    override fun setFixedHeaderViewId(
        item: RefreshItem?,
        id: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFixedHeaderViewId(id); true
        } ?: false
    }

    /**
     * 设置固定在 Footer 上方的视图 id
     * @param item Refresh Item
     * @param id 视图 id
     * @return `true` success, `false` fail
     */
    override fun setFixedFooterViewId(
        item: RefreshItem?,
        id: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFixedFooterViewId(id); true
        } ?: false
    }

    /**
     * 设置 Header 滚动时跟随滚动的视图 id
     * @param item Refresh Item
     * @param id 视图 id
     * @return `true` success, `false` fail
     */
    override fun setHeaderTranslationViewId(
        item: RefreshItem?,
        id: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setHeaderTranslationViewId(id); true
        } ?: false
    }

    /**
     * 设置 Footer 滚动时跟随滚动的视图 id
     * @param item Refresh Item
     * @param id 视图 id
     * @return `true` success, `false` fail
     */
    override fun setFooterTranslationViewId(
        item: RefreshItem?,
        id: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setFooterTranslationViewId(id); true
        } ?: false
    }

    /**
     * 刷新时是否禁用内容操作
     * @param item Refresh Item
     * @param disable 是否禁用
     * @return `true` success, `false` fail
     */
    override fun setDisableContentWhenRefresh(
        item: RefreshItem?,
        disable: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setDisableContentWhenRefresh(disable); true
        } ?: false
    }

    /**
     * 加载时是否禁用内容操作
     * @param item Refresh Item
     * @param disable 是否禁用
     * @return `true` success, `false` fail
     */
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

    /**
     * 设置刷新监听器
     * @param item Refresh Item
     * @param listener 刷新监听器
     * @return `true` success, `false` fail
     */
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

    /**
     * 设置加载监听器
     * @param item Refresh Item
     * @param listener 加载监听器
     * @return `true` success, `false` fail
     */
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

    /**
     * 设置刷新和加载监听器
     * @param item Refresh Item
     * @param listener 刷新、加载监听器
     * @return `true` success, `false` fail
     */
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

    /**
     * 设置多功能监听器
     * @param item Refresh Item
     * @param listener 多功能监听器
     * @return `true` success, `false` fail
     */
    override fun setOnMultiListener(
        item: RefreshItem?,
        listener: Any?
    ): Boolean {
        return getOnMultiListener(listener)?.let {
            getRefreshLayout(item)?.run { setOnMultiListener(it); true }
        } ?: false
    }

    /**
     * 设置滚动边界判断器
     * @param item Refresh Item
     * @param boundary 滚动边界判断器
     * @return `true` success, `false` fail
     */
    override fun setScrollBoundaryDecider(
        item: RefreshItem?,
        boundary: Any?
    ): Boolean {
        return getScrollBoundaryDecider(boundary)?.let {
            getRefreshLayout(item)?.run { setScrollBoundaryDecider(it); true }
        } ?: false
    }

    /**
     * 设置主题色
     * @param item Refresh Item
     * @param primaryColors 主题色
     * @return `true` success, `false` fail
     */
    override fun setPrimaryColors(
        item: RefreshItem?,
        primaryColors: IntArray?
    ): Boolean {
        primaryColors ?: return false
        return getRefreshLayout(item)?.run {
            setPrimaryColors(*primaryColors); true
        } ?: false
    }

    /**
     * 设置主题色资源 id
     * @param item Refresh Item
     * @param primaryColorIds 主题色资源 id
     * @return `true` success, `false` fail
     */
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

    /**
     * 自动触发刷新
     * @param item Refresh Item
     * @return `true` success, `false` fail
     */
    override fun autoRefresh(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.autoRefresh() ?: false
    }

    /**
     * 自动触发刷新
     * @param item Refresh Item
     * @param delayed 开始延时
     * @return `true` success, `false` fail
     */
    override fun autoRefresh(
        item: RefreshItem?,
        delayed: Int
    ): Boolean {
        return getRefreshLayout(item)?.autoRefresh(delayed) ?: false
    }

    /**
     * 自动触发刷新动画
     * @param item Refresh Item
     * @return `true` success, `false` fail
     */
    override fun autoRefreshAnimationOnly(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.autoRefreshAnimationOnly() ?: false
    }

    /**
     * 自动触发刷新
     * @param item Refresh Item
     * @param delayed 开始延时
     * @param duration 拖拽动画持续时间
     * @param dragRate 拉拽高度比率
     * @param animationOnly 是否只显示动画
     * @return `true` success, `false` fail
     */
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

    /**
     * 自动触发加载
     * @param item Refresh Item
     * @return `true` success, `false` fail
     */
    override fun autoLoadMore(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.autoLoadMore() ?: false
    }

    /**
     * 自动触发加载
     * @param item Refresh Item
     * @param delayed 开始延时
     * @return `true` success, `false` fail
     */
    override fun autoLoadMore(
        item: RefreshItem?,
        delayed: Int
    ): Boolean {
        return getRefreshLayout(item)?.autoLoadMore(delayed) ?: false
    }

    /**
     * 自动触发加载动画
     * @param item Refresh Item
     * @return `true` success, `false` fail
     */
    override fun autoLoadMoreAnimationOnly(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.autoLoadMoreAnimationOnly() ?: false
    }

    /**
     * 自动触发加载
     * @param item Refresh Item
     * @param delayed 开始延时
     * @param duration 拖拽动画持续时间
     * @param dragRate 拉拽高度比率
     * @param animationOnly 是否只显示动画
     * @return `true` success, `false` fail
     */
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

    /**
     * 完成刷新
     * @param item Refresh Item
     * @param success 是否成功
     * @return `true` success, `false` fail
     */
    override fun finishRefresh(
        item: RefreshItem?,
        success: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.let {
            if (it.isRefreshing) it.finishRefresh(success)
            true
        } ?: false
    }

    /**
     * 完成刷新
     * @param item Refresh Item
     * @param delayed 开始延时
     * @return `true` success, `false` fail
     */
    override fun finishRefresh(
        item: RefreshItem?,
        delayed: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            finishRefresh(delayed); true
        } ?: false
    }

    /**
     * 完成刷新
     * @param item Refresh Item
     * @param delayed 开始延时
     * @param success 是否成功
     * @param noMoreData 是否没有更多数据
     * @return `true` success, `false` fail
     */
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

    /**
     * 完成刷新并标记没有更多数据
     * @param item Refresh Item
     * @return `true` success, `false` fail
     */
    override fun finishRefreshWithNoMoreData(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.run {
            finishRefreshWithNoMoreData(); true
        } ?: false
    }

    /**
     * 完成加载
     * @param item Refresh Item
     * @param success 是否成功
     * @return `true` success, `false` fail
     */
    override fun finishLoadMore(
        item: RefreshItem?,
        success: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.let {
            it.finishLoadMore(success)
            true
        } ?: false
    }

    /**
     * 完成加载
     * @param item Refresh Item
     * @param delayed 开始延时
     * @return `true` success, `false` fail
     */
    override fun finishLoadMore(
        item: RefreshItem?,
        delayed: Int
    ): Boolean {
        return getRefreshLayout(item)?.run {
            finishLoadMore(delayed); true
        } ?: false
    }

    /**
     * 完成加载
     * @param item Refresh Item
     * @param delayed 开始延时
     * @param success 是否成功
     * @param noMoreData 是否没有更多数据
     * @return `true` success, `false` fail
     */
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

    /**
     * 完成加载并标记没有更多数据
     * @param item Refresh Item
     * @return `true` success, `false` fail
     */
    override fun finishLoadMoreWithNoMoreData(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.run {
            finishLoadMoreWithNoMoreData(); true
        } ?: false
    }

    /**
     * 关闭 Header 或 Footer
     * @param item Refresh Item
     * @return `true` success, `false` fail
     */
    override fun closeHeaderOrFooter(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.run {
            closeHeaderOrFooter(); true
        } ?: false
    }

    /**
     * 设置没有更多数据状态
     * @param item Refresh Item
     * @param noMoreData 是否没有更多数据
     * @return `true` success, `false` fail
     */
    override fun setNoMoreData(
        item: RefreshItem?,
        noMoreData: Boolean
    ): Boolean {
        return getRefreshLayout(item)?.run {
            setNoMoreData(noMoreData); true
        } ?: false
    }

    /**
     * 重置没有更多数据状态
     * @param item Refresh Item
     * @return `true` success, `false` fail
     */
    override fun resetNoMoreData(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.run {
            resetNoMoreData(); true
        } ?: false
    }

    /**
     * 完成刷新、加载
     * @param item Refresh Item
     * @param success 是否成功
     * @return `true` success, `false` fail
     */
    override fun finishRefreshAndLoad(
        item: RefreshItem?,
        success: Boolean
    ): Boolean {
        val finishRefresh = finishRefresh(item, success)
        val finishLoadMore = finishLoadMore(item, success)
        return finishRefresh || finishLoadMore
    }

    /**
     * 完成刷新或加载
     * @param item Refresh Item
     * @param refresh 是否刷新
     * @param success 是否成功
     * @return `true` success, `false` fail
     */
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

    /**
     * 获取刷新头
     * @param item Refresh Item
     * @return 刷新头
     */
    override fun getRefreshHeader(item: RefreshItem?): Any? {
        return getRefreshLayout(item)?.refreshHeader
    }

    /**
     * 获取加载尾
     * @param item Refresh Item
     * @return 加载尾
     */
    override fun getRefreshFooter(item: RefreshItem?): Any? {
        return getRefreshLayout(item)?.refreshFooter
    }

    /**
     * 获取当前状态
     * @param item Refresh Item
     * @return 当前状态
     */
    override fun getState(item: RefreshItem?): Any? {
        return getRefreshLayout(item)?.state
    }

    /**
     * 获取实体布局视图
     * @param item Refresh Item
     * @return 实体布局视图
     */
    override fun getLayout(item: RefreshItem?): ViewGroup? {
        return getRefreshLayout(item)?.layout
    }

    /**
     * 是否正在刷新
     * @param item Refresh Item
     * @return `true` yes, `false` no
     */
    override fun isRefreshing(item: RefreshItem?): Boolean {
        return getRefreshLayout(item)?.isRefreshing ?: false
    }

    /**
     * 是否正在加载
     * @param item Refresh Item
     * @return `true` yes, `false` no
     */
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
        return item?.view()?.get() as? SmartRefreshLayout
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
        return item?.view()?.get()
    }
}