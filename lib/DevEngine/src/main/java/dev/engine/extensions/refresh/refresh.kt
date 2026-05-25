package dev.engine.extensions.refresh

import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import dev.engine.DevEngine
import dev.engine.refresh.IRefreshEngine

// ============================================
// = IRefreshEngine<EngineConfig, EngineItem> =
// ============================================

/**
 * 通过 Key 获取 Refresh Engine
 * @param engine String?
 * @return IRefreshEngine<EngineConfig, EngineItem>
 * 内部做了处理如果匹配不到则返回默认 Refresh Engine
 */
fun String?.getRefreshEngine(): IRefreshEngine<in IRefreshEngine.EngineConfig, in IRefreshEngine.EngineItem>? {
    DevEngine.getRefresh(this)?.let { value ->
        return value
    }
    return DevEngine.getRefresh()
}

// =====================
// = Key Refresh Engine =
// =====================

// =============
// = 对外公开方法 =
// =============

fun refresh_getConfig(
    engine: String? = null
): Any? {
    return engine.getRefreshEngine()?.config
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_initialize(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.initialize(this) ?: false
}

fun <Config : IRefreshEngine.EngineConfig, Item : IRefreshEngine.EngineItem> Item?.refresh_applyConfig(
    engine: String? = null,
    config: Config?
): Boolean {
    return engine.getRefreshEngine()?.applyConfig(this, config) ?: false
}

// ==========
// = 尺寸配置 =
// ==========

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setHeaderHeight(
    engine: String? = null,
    dp: Float
): Boolean {
    return engine.getRefreshEngine()?.setHeaderHeight(this, dp) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setHeaderHeightPx(
    engine: String? = null,
    px: Int
): Boolean {
    return engine.getRefreshEngine()?.setHeaderHeightPx(this, px) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFooterHeight(
    engine: String? = null,
    dp: Float
): Boolean {
    return engine.getRefreshEngine()?.setFooterHeight(this, dp) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFooterHeightPx(
    engine: String? = null,
    px: Int
): Boolean {
    return engine.getRefreshEngine()?.setFooterHeightPx(this, px) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setHeaderInsetStart(
    engine: String? = null,
    dp: Float
): Boolean {
    return engine.getRefreshEngine()?.setHeaderInsetStart(this, dp) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setHeaderInsetStartPx(
    engine: String? = null,
    px: Int
): Boolean {
    return engine.getRefreshEngine()?.setHeaderInsetStartPx(this, px) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFooterInsetStart(
    engine: String? = null,
    dp: Float
): Boolean {
    return engine.getRefreshEngine()?.setFooterInsetStart(this, dp) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFooterInsetStartPx(
    engine: String? = null,
    px: Int
): Boolean {
    return engine.getRefreshEngine()?.setFooterInsetStartPx(this, px) ?: false
}

// ==========
// = 拖拽动画 =
// ==========

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setDragRate(
    engine: String? = null,
    rate: Float
): Boolean {
    return engine.getRefreshEngine()?.setDragRate(this, rate) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setHeaderMaxDragRate(
    engine: String? = null,
    rate: Float
): Boolean {
    return engine.getRefreshEngine()?.setHeaderMaxDragRate(this, rate) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFooterMaxDragRate(
    engine: String? = null,
    rate: Float
): Boolean {
    return engine.getRefreshEngine()?.setFooterMaxDragRate(this, rate) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setHeaderTriggerRate(
    engine: String? = null,
    rate: Float
): Boolean {
    return engine.getRefreshEngine()?.setHeaderTriggerRate(this, rate) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFooterTriggerRate(
    engine: String? = null,
    rate: Float
): Boolean {
    return engine.getRefreshEngine()?.setFooterTriggerRate(this, rate) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setReboundInterpolator(
    engine: String? = null,
    interpolator: Interpolator?
): Boolean {
    return engine.getRefreshEngine()?.setReboundInterpolator(this, interpolator) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setReboundDuration(
    engine: String? = null,
    duration: Int
): Boolean {
    return engine.getRefreshEngine()?.setReboundDuration(this, duration) ?: false
}

// ==========
// = 组件设置 =
// ==========

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setRefreshHeader(
    engine: String? = null,
    header: Any?
): Boolean {
    return engine.getRefreshEngine()?.setRefreshHeader(this, header) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setRefreshHeader(
    engine: String? = null,
    header: Any?,
    width: Int,
    height: Int
): Boolean {
    return engine.getRefreshEngine()?.setRefreshHeader(this, header, width, height) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setRefreshFooter(
    engine: String? = null,
    footer: Any?
): Boolean {
    return engine.getRefreshEngine()?.setRefreshFooter(this, footer) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setRefreshFooter(
    engine: String? = null,
    footer: Any?,
    width: Int,
    height: Int
): Boolean {
    return engine.getRefreshEngine()?.setRefreshFooter(this, footer, width, height) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setRefreshContent(
    engine: String? = null,
    content: View?
): Boolean {
    return engine.getRefreshEngine()?.setRefreshContent(this, content) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setRefreshContent(
    engine: String? = null,
    content: View?,
    width: Int,
    height: Int
): Boolean {
    return engine.getRefreshEngine()?.setRefreshContent(this, content, width, height) ?: false
}

// ==========
// = 开关配置 =
// ==========

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableRefresh(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableRefresh(this, enabled) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableLoadMore(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableLoadMore(this, enabled) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableAutoLoadMore(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableAutoLoadMore(this, enabled) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableHeaderTranslationContent(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableHeaderTranslationContent(this, enabled) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableFooterTranslationContent(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableFooterTranslationContent(this, enabled) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableOverScrollBounce(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableOverScrollBounce(this, enabled) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnablePureScrollMode(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnablePureScrollMode(this, enabled) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableScrollContentWhenLoaded(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableScrollContentWhenLoaded(this, enabled) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableScrollContentWhenRefreshed(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableScrollContentWhenRefreshed(this, enabled) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableLoadMoreWhenContentNotFull(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableLoadMoreWhenContentNotFull(this, enabled) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableOverScrollDrag(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableOverScrollDrag(this, enabled) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableFooterFollowWhenNoMoreData(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableFooterFollowWhenNoMoreData(this, enabled) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableClipHeaderWhenFixedBehind(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableClipHeaderWhenFixedBehind(this, enabled) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableClipFooterWhenFixedBehind(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableClipFooterWhenFixedBehind(this, enabled) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableNestedScroll(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableNestedScroll(this, enabled) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFixedHeaderViewId(
    engine: String? = null,
    id: Int
): Boolean {
    return engine.getRefreshEngine()?.setFixedHeaderViewId(this, id) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFixedFooterViewId(
    engine: String? = null,
    id: Int
): Boolean {
    return engine.getRefreshEngine()?.setFixedFooterViewId(this, id) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setHeaderTranslationViewId(
    engine: String? = null,
    id: Int
): Boolean {
    return engine.getRefreshEngine()?.setHeaderTranslationViewId(this, id) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFooterTranslationViewId(
    engine: String? = null,
    id: Int
): Boolean {
    return engine.getRefreshEngine()?.setFooterTranslationViewId(this, id) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setDisableContentWhenRefresh(
    engine: String? = null,
    disable: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setDisableContentWhenRefresh(this, disable) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setDisableContentWhenLoading(
    engine: String? = null,
    disable: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setDisableContentWhenLoading(this, disable) ?: false
}

// ==========
// = 监听设置 =
// ==========

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setOnRefreshListener(
    engine: String? = null,
    listener: IRefreshEngine.OnRefreshListener?
): Boolean {
    return engine.getRefreshEngine()?.setOnRefreshListener(this, listener) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setOnLoadMoreListener(
    engine: String? = null,
    listener: IRefreshEngine.OnLoadMoreListener?
): Boolean {
    return engine.getRefreshEngine()?.setOnLoadMoreListener(this, listener) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setOnRefreshLoadMoreListener(
    engine: String? = null,
    listener: IRefreshEngine.OnRefreshLoadMoreListener?
): Boolean {
    return engine.getRefreshEngine()?.setOnRefreshLoadMoreListener(this, listener) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setOnMultiListener(
    engine: String? = null,
    listener: Any?
): Boolean {
    return engine.getRefreshEngine()?.setOnMultiListener(this, listener) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setScrollBoundaryDecider(
    engine: String? = null,
    boundary: Any?
): Boolean {
    return engine.getRefreshEngine()?.setScrollBoundaryDecider(this, boundary) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setPrimaryColors(
    engine: String? = null,
    primaryColors: IntArray?
): Boolean {
    return engine.getRefreshEngine()?.setPrimaryColors(this, primaryColors) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setPrimaryColorsId(
    engine: String? = null,
    primaryColorIds: IntArray?
): Boolean {
    return engine.getRefreshEngine()?.setPrimaryColorsId(this, primaryColorIds) ?: false
}

// ==========
// = 状态操作 =
// ==========

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_autoRefresh(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.autoRefresh(this) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_autoRefresh(
    engine: String? = null,
    delayed: Int
): Boolean {
    return engine.getRefreshEngine()?.autoRefresh(this, delayed) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_autoRefreshAnimationOnly(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.autoRefreshAnimationOnly(this) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_autoRefresh(
    engine: String? = null,
    delayed: Int,
    duration: Int,
    dragRate: Float,
    animationOnly: Boolean
): Boolean {
    return engine.getRefreshEngine()?.autoRefresh(
        this, delayed, duration, dragRate, animationOnly
    ) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_autoLoadMore(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.autoLoadMore(this) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_autoLoadMore(
    engine: String? = null,
    delayed: Int
): Boolean {
    return engine.getRefreshEngine()?.autoLoadMore(this, delayed) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_autoLoadMoreAnimationOnly(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.autoLoadMoreAnimationOnly(this) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_autoLoadMore(
    engine: String? = null,
    delayed: Int,
    duration: Int,
    dragRate: Float,
    animationOnly: Boolean
): Boolean {
    return engine.getRefreshEngine()?.autoLoadMore(
        this, delayed, duration, dragRate, animationOnly
    ) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishRefresh(
    engine: String? = null,
    success: Boolean = true
): Boolean {
    return engine.getRefreshEngine()?.finishRefresh(this, success) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishRefresh(
    engine: String? = null,
    delayed: Int
): Boolean {
    return engine.getRefreshEngine()?.finishRefresh(this, delayed) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishRefresh(
    engine: String? = null,
    delayed: Int,
    success: Boolean,
    noMoreData: Boolean?
): Boolean {
    return engine.getRefreshEngine()?.finishRefresh(
        this, delayed, success, noMoreData
    ) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishRefreshWithNoMoreData(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.finishRefreshWithNoMoreData(this) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishLoadMore(
    engine: String? = null,
    success: Boolean = true
): Boolean {
    return engine.getRefreshEngine()?.finishLoadMore(this, success) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishLoadMore(
    engine: String? = null,
    delayed: Int
): Boolean {
    return engine.getRefreshEngine()?.finishLoadMore(this, delayed) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishLoadMore(
    engine: String? = null,
    delayed: Int,
    success: Boolean,
    noMoreData: Boolean
): Boolean {
    return engine.getRefreshEngine()?.finishLoadMore(
        this, delayed, success, noMoreData
    ) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishLoadMoreWithNoMoreData(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.finishLoadMoreWithNoMoreData(this) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_closeHeaderOrFooter(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.closeHeaderOrFooter(this) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setNoMoreData(
    engine: String? = null,
    noMoreData: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setNoMoreData(this, noMoreData) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_resetNoMoreData(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.resetNoMoreData(this) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishRefreshAndLoad(
    engine: String? = null,
    success: Boolean = true
): Boolean {
    return engine.getRefreshEngine()?.finishRefreshAndLoad(this, success) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishRefreshOrLoad(
    engine: String? = null,
    refresh: Boolean,
    success: Boolean = true
): Boolean {
    return engine.getRefreshEngine()?.finishRefreshOrLoad(this, refresh, success) ?: false
}

// ==========
// = 状态查询 =
// ==========

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_isRefreshing(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.isRefreshing(this) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_isLoading(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.isLoading(this) ?: false
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_getState(
    engine: String? = null
): Any? {
    return engine.getRefreshEngine()?.getState(this)
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_getRefreshHeader(
    engine: String? = null
): Any? {
    return engine.getRefreshEngine()?.getRefreshHeader(this)
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_getRefreshFooter(
    engine: String? = null
): Any? {
    return engine.getRefreshEngine()?.getRefreshFooter(this)
}

fun <Item : IRefreshEngine.EngineItem> Item?.refresh_getLayout(
    engine: String? = null
): ViewGroup? {
    return engine.getRefreshEngine()?.getLayout(this)
}