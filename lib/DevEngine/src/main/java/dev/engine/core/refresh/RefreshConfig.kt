package dev.engine.core.refresh

import android.view.animation.Interpolator
import dev.engine.refresh.IRefreshEngine

/**
 * detail: Refresh View Config
 * @author Ttt
 */
open class RefreshConfig private constructor(
    config: RefreshConfig?
) : IRefreshEngine.EngineConfig {

    // Header 高度 ( dp )
    private var mHeaderHeight = UNSET_FLOAT

    // Header 高度 ( px )
    private var mHeaderHeightPx = UNSET_INT

    // Footer 高度 ( dp )
    private var mFooterHeight = UNSET_FLOAT

    // Footer 高度 ( px )
    private var mFooterHeightPx = UNSET_INT

    // Header 起始偏移量 ( dp )
    private var mHeaderInsetStart = UNSET_FLOAT

    // Header 起始偏移量 ( px )
    private var mHeaderInsetStartPx = UNSET_INT

    // Footer 起始偏移量 ( dp )
    private var mFooterInsetStart = UNSET_FLOAT

    // Footer 起始偏移量 ( px )
    private var mFooterInsetStartPx = UNSET_INT

    // 拖拽阻尼比率
    private var mDragRate = UNSET_FLOAT

    // Header 最大拖拽高度比率
    private var mHeaderMaxDragRate = UNSET_FLOAT

    // Footer 最大拖拽高度比率
    private var mFooterMaxDragRate = UNSET_FLOAT

    // Header 触发刷新比率
    private var mHeaderTriggerRate = UNSET_FLOAT

    // Footer 触发加载比率
    private var mFooterTriggerRate = UNSET_FLOAT

    // 回弹动画插值器
    private var mReboundInterpolator: Interpolator? = null

    // 回弹动画时长
    private var mReboundDuration = UNSET_INT

    // 是否启用下拉刷新
    private var mEnableRefresh: Boolean? = null

    // 是否启用上拉加载更多
    private var mEnableLoadMore: Boolean? = null

    // 是否启用滚动到底部自动加载
    private var mEnableAutoLoadMore: Boolean? = null

    // 是否启用 Header 移动内容
    private var mEnableHeaderTranslationContent: Boolean? = null

    // 是否启用 Footer 移动内容
    private var mEnableFooterTranslationContent: Boolean? = null

    // 是否启用越界回弹
    private var mEnableOverScrollBounce: Boolean? = null

    // 是否启用纯滚动模式
    private var mEnablePureScrollMode: Boolean? = null

    // 加载完成后是否滚动内容显示新数据
    private var mEnableScrollContentWhenLoaded: Boolean? = null

    // 刷新完成后是否滚动内容显示新数据
    private var mEnableScrollContentWhenRefreshed: Boolean? = null

    // 内容不满一页时是否可以加载更多
    private var mEnableLoadMoreWhenContentNotFull: Boolean? = null

    // 是否启用越界拖动
    private var mEnableOverScrollDrag: Boolean? = null

    // 没有更多数据后 Footer 是否跟随内容
    private var mEnableFooterFollowWhenNoMoreData: Boolean? = null

    // Header FixedBehind 时是否裁剪 Header
    private var mEnableClipHeaderWhenFixedBehind: Boolean? = null

    // Footer FixedBehind 时是否裁剪 Footer
    private var mEnableClipFooterWhenFixedBehind: Boolean? = null

    // 是否启用嵌套滚动
    private var mEnableNestedScroll: Boolean? = null

    // 刷新时是否禁用内容操作
    private var mDisableContentWhenRefresh: Boolean? = null

    // 加载时是否禁用内容操作
    private var mDisableContentWhenLoading: Boolean? = null

    // 主题色
    private var mPrimaryColors: IntArray? = null

    // 主题色资源 id
    private var mPrimaryColorIds: IntArray? = null

    companion object {

        const val UNSET_FLOAT = -1F
        const val UNSET_INT = -1

        fun create(): RefreshConfig {
            return RefreshConfig(null)
        }

        fun create(config: RefreshConfig?): RefreshConfig {
            return RefreshConfig(config)
        }
    }

    init {
        config?.let {
            this.mHeaderHeight = it.mHeaderHeight
            this.mHeaderHeightPx = it.mHeaderHeightPx
            this.mFooterHeight = it.mFooterHeight
            this.mFooterHeightPx = it.mFooterHeightPx
            this.mHeaderInsetStart = it.mHeaderInsetStart
            this.mHeaderInsetStartPx = it.mHeaderInsetStartPx
            this.mFooterInsetStart = it.mFooterInsetStart
            this.mFooterInsetStartPx = it.mFooterInsetStartPx
            this.mDragRate = it.mDragRate
            this.mHeaderMaxDragRate = it.mHeaderMaxDragRate
            this.mFooterMaxDragRate = it.mFooterMaxDragRate
            this.mHeaderTriggerRate = it.mHeaderTriggerRate
            this.mFooterTriggerRate = it.mFooterTriggerRate
            this.mReboundInterpolator = it.mReboundInterpolator
            this.mReboundDuration = it.mReboundDuration
            this.mEnableRefresh = it.mEnableRefresh
            this.mEnableLoadMore = it.mEnableLoadMore
            this.mEnableAutoLoadMore = it.mEnableAutoLoadMore
            this.mEnableHeaderTranslationContent = it.mEnableHeaderTranslationContent
            this.mEnableFooterTranslationContent = it.mEnableFooterTranslationContent
            this.mEnableOverScrollBounce = it.mEnableOverScrollBounce
            this.mEnablePureScrollMode = it.mEnablePureScrollMode
            this.mEnableScrollContentWhenLoaded = it.mEnableScrollContentWhenLoaded
            this.mEnableScrollContentWhenRefreshed = it.mEnableScrollContentWhenRefreshed
            this.mEnableLoadMoreWhenContentNotFull = it.mEnableLoadMoreWhenContentNotFull
            this.mEnableOverScrollDrag = it.mEnableOverScrollDrag
            this.mEnableFooterFollowWhenNoMoreData = it.mEnableFooterFollowWhenNoMoreData
            this.mEnableClipHeaderWhenFixedBehind = it.mEnableClipHeaderWhenFixedBehind
            this.mEnableClipFooterWhenFixedBehind = it.mEnableClipFooterWhenFixedBehind
            this.mEnableNestedScroll = it.mEnableNestedScroll
            this.mDisableContentWhenRefresh = it.mDisableContentWhenRefresh
            this.mDisableContentWhenLoading = it.mDisableContentWhenLoading
            this.mPrimaryColors = it.mPrimaryColors
            this.mPrimaryColorIds = it.mPrimaryColorIds
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

    override fun headerHeight(): Float {
        return mHeaderHeight
    }

    open fun setHeaderHeight(headerHeight: Float): RefreshConfig {
        mHeaderHeight = headerHeight
        return this
    }

    override fun headerHeightPx(): Int {
        return mHeaderHeightPx
    }

    open fun setHeaderHeightPx(headerHeightPx: Int): RefreshConfig {
        mHeaderHeightPx = headerHeightPx
        return this
    }

    override fun footerHeight(): Float {
        return mFooterHeight
    }

    open fun setFooterHeight(footerHeight: Float): RefreshConfig {
        mFooterHeight = footerHeight
        return this
    }

    override fun footerHeightPx(): Int {
        return mFooterHeightPx
    }

    open fun setFooterHeightPx(footerHeightPx: Int): RefreshConfig {
        mFooterHeightPx = footerHeightPx
        return this
    }

    override fun headerInsetStart(): Float {
        return mHeaderInsetStart
    }

    open fun setHeaderInsetStart(headerInsetStart: Float): RefreshConfig {
        mHeaderInsetStart = headerInsetStart
        return this
    }

    override fun headerInsetStartPx(): Int {
        return mHeaderInsetStartPx
    }

    open fun setHeaderInsetStartPx(headerInsetStartPx: Int): RefreshConfig {
        mHeaderInsetStartPx = headerInsetStartPx
        return this
    }

    override fun footerInsetStart(): Float {
        return mFooterInsetStart
    }

    open fun setFooterInsetStart(footerInsetStart: Float): RefreshConfig {
        mFooterInsetStart = footerInsetStart
        return this
    }

    override fun footerInsetStartPx(): Int {
        return mFooterInsetStartPx
    }

    open fun setFooterInsetStartPx(footerInsetStartPx: Int): RefreshConfig {
        mFooterInsetStartPx = footerInsetStartPx
        return this
    }

    override fun dragRate(): Float {
        return mDragRate
    }

    open fun setDragRate(dragRate: Float): RefreshConfig {
        mDragRate = dragRate
        return this
    }

    override fun headerMaxDragRate(): Float {
        return mHeaderMaxDragRate
    }

    open fun setHeaderMaxDragRate(headerMaxDragRate: Float): RefreshConfig {
        mHeaderMaxDragRate = headerMaxDragRate
        return this
    }

    override fun footerMaxDragRate(): Float {
        return mFooterMaxDragRate
    }

    open fun setFooterMaxDragRate(footerMaxDragRate: Float): RefreshConfig {
        mFooterMaxDragRate = footerMaxDragRate
        return this
    }

    override fun headerTriggerRate(): Float {
        return mHeaderTriggerRate
    }

    open fun setHeaderTriggerRate(headerTriggerRate: Float): RefreshConfig {
        mHeaderTriggerRate = headerTriggerRate
        return this
    }

    override fun footerTriggerRate(): Float {
        return mFooterTriggerRate
    }

    open fun setFooterTriggerRate(footerTriggerRate: Float): RefreshConfig {
        mFooterTriggerRate = footerTriggerRate
        return this
    }

    override fun reboundInterpolator(): Interpolator? {
        return mReboundInterpolator
    }

    open fun setReboundInterpolator(reboundInterpolator: Interpolator?): RefreshConfig {
        mReboundInterpolator = reboundInterpolator
        return this
    }

    override fun reboundDuration(): Int {
        return mReboundDuration
    }

    open fun setReboundDuration(reboundDuration: Int): RefreshConfig {
        mReboundDuration = reboundDuration
        return this
    }

    override fun enableRefresh(): Boolean? {
        return mEnableRefresh
    }

    open fun setEnableRefresh(enableRefresh: Boolean?): RefreshConfig {
        mEnableRefresh = enableRefresh
        return this
    }

    override fun enableLoadMore(): Boolean? {
        return mEnableLoadMore
    }

    open fun setEnableLoadMore(enableLoadMore: Boolean?): RefreshConfig {
        mEnableLoadMore = enableLoadMore
        return this
    }

    override fun enableAutoLoadMore(): Boolean? {
        return mEnableAutoLoadMore
    }

    open fun setEnableAutoLoadMore(enableAutoLoadMore: Boolean?): RefreshConfig {
        mEnableAutoLoadMore = enableAutoLoadMore
        return this
    }

    override fun enableHeaderTranslationContent(): Boolean? {
        return mEnableHeaderTranslationContent
    }

    open fun setEnableHeaderTranslationContent(
        enableHeaderTranslationContent: Boolean?
    ): RefreshConfig {
        mEnableHeaderTranslationContent = enableHeaderTranslationContent
        return this
    }

    override fun enableFooterTranslationContent(): Boolean? {
        return mEnableFooterTranslationContent
    }

    open fun setEnableFooterTranslationContent(
        enableFooterTranslationContent: Boolean?
    ): RefreshConfig {
        mEnableFooterTranslationContent = enableFooterTranslationContent
        return this
    }

    override fun enableOverScrollBounce(): Boolean? {
        return mEnableOverScrollBounce
    }

    open fun setEnableOverScrollBounce(enableOverScrollBounce: Boolean?): RefreshConfig {
        mEnableOverScrollBounce = enableOverScrollBounce
        return this
    }

    override fun enablePureScrollMode(): Boolean? {
        return mEnablePureScrollMode
    }

    open fun setEnablePureScrollMode(enablePureScrollMode: Boolean?): RefreshConfig {
        mEnablePureScrollMode = enablePureScrollMode
        return this
    }

    override fun enableScrollContentWhenLoaded(): Boolean? {
        return mEnableScrollContentWhenLoaded
    }

    open fun setEnableScrollContentWhenLoaded(
        enableScrollContentWhenLoaded: Boolean?
    ): RefreshConfig {
        mEnableScrollContentWhenLoaded = enableScrollContentWhenLoaded
        return this
    }

    override fun enableScrollContentWhenRefreshed(): Boolean? {
        return mEnableScrollContentWhenRefreshed
    }

    open fun setEnableScrollContentWhenRefreshed(
        enableScrollContentWhenRefreshed: Boolean?
    ): RefreshConfig {
        mEnableScrollContentWhenRefreshed = enableScrollContentWhenRefreshed
        return this
    }

    override fun enableLoadMoreWhenContentNotFull(): Boolean? {
        return mEnableLoadMoreWhenContentNotFull
    }

    open fun setEnableLoadMoreWhenContentNotFull(
        enableLoadMoreWhenContentNotFull: Boolean?
    ): RefreshConfig {
        mEnableLoadMoreWhenContentNotFull = enableLoadMoreWhenContentNotFull
        return this
    }

    override fun enableOverScrollDrag(): Boolean? {
        return mEnableOverScrollDrag
    }

    open fun setEnableOverScrollDrag(enableOverScrollDrag: Boolean?): RefreshConfig {
        mEnableOverScrollDrag = enableOverScrollDrag
        return this
    }

    override fun enableFooterFollowWhenNoMoreData(): Boolean? {
        return mEnableFooterFollowWhenNoMoreData
    }

    open fun setEnableFooterFollowWhenNoMoreData(
        enableFooterFollowWhenNoMoreData: Boolean?
    ): RefreshConfig {
        mEnableFooterFollowWhenNoMoreData = enableFooterFollowWhenNoMoreData
        return this
    }

    override fun enableClipHeaderWhenFixedBehind(): Boolean? {
        return mEnableClipHeaderWhenFixedBehind
    }

    open fun setEnableClipHeaderWhenFixedBehind(
        enableClipHeaderWhenFixedBehind: Boolean?
    ): RefreshConfig {
        mEnableClipHeaderWhenFixedBehind = enableClipHeaderWhenFixedBehind
        return this
    }

    override fun enableClipFooterWhenFixedBehind(): Boolean? {
        return mEnableClipFooterWhenFixedBehind
    }

    open fun setEnableClipFooterWhenFixedBehind(
        enableClipFooterWhenFixedBehind: Boolean?
    ): RefreshConfig {
        mEnableClipFooterWhenFixedBehind = enableClipFooterWhenFixedBehind
        return this
    }

    override fun enableNestedScroll(): Boolean? {
        return mEnableNestedScroll
    }

    open fun setEnableNestedScroll(enableNestedScroll: Boolean?): RefreshConfig {
        mEnableNestedScroll = enableNestedScroll
        return this
    }

    override fun disableContentWhenRefresh(): Boolean? {
        return mDisableContentWhenRefresh
    }

    open fun setDisableContentWhenRefresh(
        disableContentWhenRefresh: Boolean?
    ): RefreshConfig {
        mDisableContentWhenRefresh = disableContentWhenRefresh
        return this
    }

    override fun disableContentWhenLoading(): Boolean? {
        return mDisableContentWhenLoading
    }

    open fun setDisableContentWhenLoading(
        disableContentWhenLoading: Boolean?
    ): RefreshConfig {
        mDisableContentWhenLoading = disableContentWhenLoading
        return this
    }

    override fun primaryColors(): IntArray? {
        return mPrimaryColors
    }

    open fun setPrimaryColors(primaryColors: IntArray?): RefreshConfig {
        mPrimaryColors = primaryColors
        return this
    }

    override fun primaryColorIds(): IntArray? {
        return mPrimaryColorIds
    }

    open fun setPrimaryColorIds(primaryColorIds: IntArray?): RefreshConfig {
        mPrimaryColorIds = primaryColorIds
        return this
    }
}