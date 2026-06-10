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
 * @receiver String?
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

/**
 * 获取 Refresh Engine Config
 * @param engine String?
 * @return Refresh Config
 */
fun refresh_getConfig(
    engine: String? = null
): Any? {
    return engine.getRefreshEngine()?.config
}

/**
 * 初始化 Refresh View
 * @receiver Refresh Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_initialize(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.initialize(this) ?: false
}

/**
 * 应用 Refresh Config
 * @receiver Refresh Item
 * @param engine String?
 * @param config Refresh Config
 * @return `true` success, `false` fail
 */
fun <Config : IRefreshEngine.EngineConfig, Item : IRefreshEngine.EngineItem> Item?.refresh_applyConfig(
    engine: String? = null,
    config: Config?
): Boolean {
    return engine.getRefreshEngine()?.applyConfig(this, config) ?: false
}

// ==========
// = 尺寸配置 =
// ==========

/**
 * 设置 Header 高度
 * @receiver Refresh Item
 * @param engine String?
 * @param dp dp
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setHeaderHeight(
    engine: String? = null,
    dp: Float
): Boolean {
    return engine.getRefreshEngine()?.setHeaderHeight(this, dp) ?: false
}

/**
 * 设置 Header 高度
 * @receiver Refresh Item
 * @param engine String?
 * @param px px
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setHeaderHeightPx(
    engine: String? = null,
    px: Int
): Boolean {
    return engine.getRefreshEngine()?.setHeaderHeightPx(this, px) ?: false
}

/**
 * 设置 Footer 高度
 * @receiver Refresh Item
 * @param engine String?
 * @param dp dp
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFooterHeight(
    engine: String? = null,
    dp: Float
): Boolean {
    return engine.getRefreshEngine()?.setFooterHeight(this, dp) ?: false
}

/**
 * 设置 Footer 高度
 * @receiver Refresh Item
 * @param engine String?
 * @param px px
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFooterHeightPx(
    engine: String? = null,
    px: Int
): Boolean {
    return engine.getRefreshEngine()?.setFooterHeightPx(this, px) ?: false
}

/**
 * 设置 Header 起始偏移量
 * @receiver Refresh Item
 * @param engine String?
 * @param dp dp
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setHeaderInsetStart(
    engine: String? = null,
    dp: Float
): Boolean {
    return engine.getRefreshEngine()?.setHeaderInsetStart(this, dp) ?: false
}

/**
 * 设置 Header 起始偏移量
 * @receiver Refresh Item
 * @param engine String?
 * @param px px
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setHeaderInsetStartPx(
    engine: String? = null,
    px: Int
): Boolean {
    return engine.getRefreshEngine()?.setHeaderInsetStartPx(this, px) ?: false
}

/**
 * 设置 Footer 起始偏移量
 * @receiver Refresh Item
 * @param engine String?
 * @param dp dp
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFooterInsetStart(
    engine: String? = null,
    dp: Float
): Boolean {
    return engine.getRefreshEngine()?.setFooterInsetStart(this, dp) ?: false
}

/**
 * 设置 Footer 起始偏移量
 * @receiver Refresh Item
 * @param engine String?
 * @param px px
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFooterInsetStartPx(
    engine: String? = null,
    px: Int
): Boolean {
    return engine.getRefreshEngine()?.setFooterInsetStartPx(this, px) ?: false
}

// ==========
// = 拖拽动画 =
// ==========

/**
 * 设置拖拽阻尼比率
 * @receiver Refresh Item
 * @param engine String?
 * @param rate 阻尼比率
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setDragRate(
    engine: String? = null,
    rate: Float
): Boolean {
    return engine.getRefreshEngine()?.setDragRate(this, rate) ?: false
}

/**
 * 设置 Header 最大拖拽高度比率
 * @receiver Refresh Item
 * @param engine String?
 * @param rate 比率
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setHeaderMaxDragRate(
    engine: String? = null,
    rate: Float
): Boolean {
    return engine.getRefreshEngine()?.setHeaderMaxDragRate(this, rate) ?: false
}

/**
 * 设置 Footer 最大拖拽高度比率
 * @receiver Refresh Item
 * @param engine String?
 * @param rate 比率
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFooterMaxDragRate(
    engine: String? = null,
    rate: Float
): Boolean {
    return engine.getRefreshEngine()?.setFooterMaxDragRate(this, rate) ?: false
}

/**
 * 设置 Header 触发刷新比率
 * @receiver Refresh Item
 * @param engine String?
 * @param rate 比率
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setHeaderTriggerRate(
    engine: String? = null,
    rate: Float
): Boolean {
    return engine.getRefreshEngine()?.setHeaderTriggerRate(this, rate) ?: false
}

/**
 * 设置 Footer 触发加载比率
 * @receiver Refresh Item
 * @param engine String?
 * @param rate 比率
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFooterTriggerRate(
    engine: String? = null,
    rate: Float
): Boolean {
    return engine.getRefreshEngine()?.setFooterTriggerRate(this, rate) ?: false
}

/**
 * 设置回弹动画插值器
 * @receiver Refresh Item
 * @param engine String?
 * @param interpolator 动画插值器
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setReboundInterpolator(
    engine: String? = null,
    interpolator: Interpolator?
): Boolean {
    return engine.getRefreshEngine()?.setReboundInterpolator(this, interpolator) ?: false
}

/**
 * 设置回弹动画时长
 * @receiver Refresh Item
 * @param engine String?
 * @param duration 时长
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setReboundDuration(
    engine: String? = null,
    duration: Int
): Boolean {
    return engine.getRefreshEngine()?.setReboundDuration(this, duration) ?: false
}

// ==========
// = 组件设置 =
// ==========

/**
 * 设置刷新头
 * @receiver Refresh Item
 * @param engine String?
 * @param header 刷新头
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setRefreshHeader(
    engine: String? = null,
    header: Any?
): Boolean {
    return engine.getRefreshEngine()?.setRefreshHeader(this, header) ?: false
}

/**
 * 设置刷新头
 * @receiver Refresh Item
 * @param engine String?
 * @param header 刷新头
 * @param width 宽度
 * @param height 高度
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setRefreshHeader(
    engine: String? = null,
    header: Any?,
    width: Int,
    height: Int
): Boolean {
    return engine.getRefreshEngine()?.setRefreshHeader(this, header, width, height) ?: false
}

/**
 * 设置加载尾
 * @receiver Refresh Item
 * @param engine String?
 * @param footer 加载尾
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setRefreshFooter(
    engine: String? = null,
    footer: Any?
): Boolean {
    return engine.getRefreshEngine()?.setRefreshFooter(this, footer) ?: false
}

/**
 * 设置加载尾
 * @receiver Refresh Item
 * @param engine String?
 * @param footer 加载尾
 * @param width 宽度
 * @param height 高度
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setRefreshFooter(
    engine: String? = null,
    footer: Any?,
    width: Int,
    height: Int
): Boolean {
    return engine.getRefreshEngine()?.setRefreshFooter(this, footer, width, height) ?: false
}

/**
 * 设置内容 View
 * @receiver Refresh Item
 * @param engine String?
 * @param content 内容 View
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setRefreshContent(
    engine: String? = null,
    content: View?
): Boolean {
    return engine.getRefreshEngine()?.setRefreshContent(this, content) ?: false
}

/**
 * 设置内容 View
 * @receiver Refresh Item
 * @param engine String?
 * @param content 内容 View
 * @param width 宽度
 * @param height 高度
 * @return `true` success, `false` fail
 */
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

/**
 * 是否启用下拉刷新
 * @receiver Refresh Item
 * @param engine String?
 * @param enabled 是否启用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableRefresh(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableRefresh(this, enabled) ?: false
}

/**
 * 是否启用上拉加载更多
 * @receiver Refresh Item
 * @param engine String?
 * @param enabled 是否启用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableLoadMore(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableLoadMore(this, enabled) ?: false
}

/**
 * 是否启用滚动到底部自动加载
 * @receiver Refresh Item
 * @param engine String?
 * @param enabled 是否启用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableAutoLoadMore(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableAutoLoadMore(this, enabled) ?: false
}

/**
 * 是否启用 Header 移动内容
 * @receiver Refresh Item
 * @param engine String?
 * @param enabled 是否启用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableHeaderTranslationContent(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableHeaderTranslationContent(this, enabled) ?: false
}

/**
 * 是否启用 Footer 移动内容
 * @receiver Refresh Item
 * @param engine String?
 * @param enabled 是否启用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableFooterTranslationContent(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableFooterTranslationContent(this, enabled) ?: false
}

/**
 * 是否启用越界回弹
 * @receiver Refresh Item
 * @param engine String?
 * @param enabled 是否启用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableOverScrollBounce(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableOverScrollBounce(this, enabled) ?: false
}

/**
 * 是否启用纯滚动模式
 * @receiver Refresh Item
 * @param engine String?
 * @param enabled 是否启用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnablePureScrollMode(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnablePureScrollMode(this, enabled) ?: false
}

/**
 * 加载完成后是否滚动内容显示新数据
 * @receiver Refresh Item
 * @param engine String?
 * @param enabled 是否启用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableScrollContentWhenLoaded(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableScrollContentWhenLoaded(this, enabled) ?: false
}

/**
 * 刷新完成后是否滚动内容显示新数据
 * @receiver Refresh Item
 * @param engine String?
 * @param enabled 是否启用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableScrollContentWhenRefreshed(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableScrollContentWhenRefreshed(this, enabled) ?: false
}

/**
 * 内容不满一页时是否可以加载更多
 * @receiver Refresh Item
 * @param engine String?
 * @param enabled 是否启用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableLoadMoreWhenContentNotFull(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableLoadMoreWhenContentNotFull(this, enabled) ?: false
}

/**
 * 是否启用越界拖动
 * @receiver Refresh Item
 * @param engine String?
 * @param enabled 是否启用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableOverScrollDrag(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableOverScrollDrag(this, enabled) ?: false
}

/**
 * 没有更多数据后 Footer 是否跟随内容
 * @receiver Refresh Item
 * @param engine String?
 * @param enabled 是否启用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableFooterFollowWhenNoMoreData(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableFooterFollowWhenNoMoreData(this, enabled) ?: false
}

/**
 * Header FixedBehind 时是否裁剪 Header
 * @receiver Refresh Item
 * @param engine String?
 * @param enabled 是否启用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableClipHeaderWhenFixedBehind(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableClipHeaderWhenFixedBehind(this, enabled) ?: false
}

/**
 * Footer FixedBehind 时是否裁剪 Footer
 * @receiver Refresh Item
 * @param engine String?
 * @param enabled 是否启用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableClipFooterWhenFixedBehind(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableClipFooterWhenFixedBehind(this, enabled) ?: false
}

/**
 * 是否启用嵌套滚动
 * @receiver Refresh Item
 * @param engine String?
 * @param enabled 是否启用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setEnableNestedScroll(
    engine: String? = null,
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableNestedScroll(this, enabled) ?: false
}

/**
 * 设置固定在 Header 下方的视图 id
 * @receiver Refresh Item
 * @param engine String?
 * @param id 视图 id
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFixedHeaderViewId(
    engine: String? = null,
    id: Int
): Boolean {
    return engine.getRefreshEngine()?.setFixedHeaderViewId(this, id) ?: false
}

/**
 * 设置固定在 Footer 上方的视图 id
 * @receiver Refresh Item
 * @param engine String?
 * @param id 视图 id
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFixedFooterViewId(
    engine: String? = null,
    id: Int
): Boolean {
    return engine.getRefreshEngine()?.setFixedFooterViewId(this, id) ?: false
}

/**
 * 设置 Header 滚动时跟随滚动的视图 id
 * @receiver Refresh Item
 * @param engine String?
 * @param id 视图 id
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setHeaderTranslationViewId(
    engine: String? = null,
    id: Int
): Boolean {
    return engine.getRefreshEngine()?.setHeaderTranslationViewId(this, id) ?: false
}

/**
 * 设置 Footer 滚动时跟随滚动的视图 id
 * @receiver Refresh Item
 * @param engine String?
 * @param id 视图 id
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setFooterTranslationViewId(
    engine: String? = null,
    id: Int
): Boolean {
    return engine.getRefreshEngine()?.setFooterTranslationViewId(this, id) ?: false
}

/**
 * 刷新时是否禁用内容操作
 * @receiver Refresh Item
 * @param engine String?
 * @param disable 是否禁用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setDisableContentWhenRefresh(
    engine: String? = null,
    disable: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setDisableContentWhenRefresh(this, disable) ?: false
}

/**
 * 加载时是否禁用内容操作
 * @receiver Refresh Item
 * @param engine String?
 * @param disable 是否禁用
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setDisableContentWhenLoading(
    engine: String? = null,
    disable: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setDisableContentWhenLoading(this, disable) ?: false
}

// ==========
// = 监听设置 =
// ==========

/**
 * 设置刷新监听器
 * @receiver Refresh Item
 * @param engine String?
 * @param listener 刷新监听器
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setOnRefreshListener(
    engine: String? = null,
    listener: IRefreshEngine.OnRefreshListener?
): Boolean {
    return engine.getRefreshEngine()?.setOnRefreshListener(this, listener) ?: false
}

/**
 * 设置加载监听器
 * @receiver Refresh Item
 * @param engine String?
 * @param listener 加载监听器
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setOnLoadMoreListener(
    engine: String? = null,
    listener: IRefreshEngine.OnLoadMoreListener?
): Boolean {
    return engine.getRefreshEngine()?.setOnLoadMoreListener(this, listener) ?: false
}

/**
 * 设置刷新和加载监听器
 * @receiver Refresh Item
 * @param engine String?
 * @param listener 刷新、加载监听器
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setOnRefreshLoadMoreListener(
    engine: String? = null,
    listener: IRefreshEngine.OnRefreshLoadMoreListener?
): Boolean {
    return engine.getRefreshEngine()?.setOnRefreshLoadMoreListener(this, listener) ?: false
}

/**
 * 设置多功能监听器
 * @receiver Refresh Item
 * @param engine String?
 * @param listener 多功能监听器
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setOnMultiListener(
    engine: String? = null,
    listener: Any?
): Boolean {
    return engine.getRefreshEngine()?.setOnMultiListener(this, listener) ?: false
}

/**
 * 设置滚动边界判断器
 * @receiver Refresh Item
 * @param engine String?
 * @param boundary 滚动边界判断器
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setScrollBoundaryDecider(
    engine: String? = null,
    boundary: Any?
): Boolean {
    return engine.getRefreshEngine()?.setScrollBoundaryDecider(this, boundary) ?: false
}

/**
 * 设置主题色
 * @receiver Refresh Item
 * @param engine String?
 * @param primaryColors 主题色
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setPrimaryColors(
    engine: String? = null,
    primaryColors: IntArray?
): Boolean {
    return engine.getRefreshEngine()?.setPrimaryColors(this, primaryColors) ?: false
}

/**
 * 设置主题色资源 id
 * @receiver Refresh Item
 * @param engine String?
 * @param primaryColorIds 主题色资源 id
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setPrimaryColorsId(
    engine: String? = null,
    primaryColorIds: IntArray?
): Boolean {
    return engine.getRefreshEngine()?.setPrimaryColorsId(this, primaryColorIds) ?: false
}

// ==========
// = 状态操作 =
// ==========

/**
 * 自动触发刷新
 * @receiver Refresh Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_autoRefresh(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.autoRefresh(this) ?: false
}

/**
 * 自动触发刷新
 * @receiver Refresh Item
 * @param engine String?
 * @param delayed 开始延时
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_autoRefresh(
    engine: String? = null,
    delayed: Int
): Boolean {
    return engine.getRefreshEngine()?.autoRefresh(this, delayed) ?: false
}

/**
 * 自动触发刷新动画
 * @receiver Refresh Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_autoRefreshAnimationOnly(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.autoRefreshAnimationOnly(this) ?: false
}

/**
 * 自动触发刷新
 * @receiver Refresh Item
 * @param engine String?
 * @param delayed 开始延时
 * @param duration 拖拽动画持续时间
 * @param dragRate 拉拽高度比率
 * @param animationOnly 是否只显示动画
 * @return `true` success, `false` fail
 */
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

/**
 * 自动触发加载
 * @receiver Refresh Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_autoLoadMore(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.autoLoadMore(this) ?: false
}

/**
 * 自动触发加载
 * @receiver Refresh Item
 * @param engine String?
 * @param delayed 开始延时
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_autoLoadMore(
    engine: String? = null,
    delayed: Int
): Boolean {
    return engine.getRefreshEngine()?.autoLoadMore(this, delayed) ?: false
}

/**
 * 自动触发加载动画
 * @receiver Refresh Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_autoLoadMoreAnimationOnly(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.autoLoadMoreAnimationOnly(this) ?: false
}

/**
 * 自动触发加载
 * @receiver Refresh Item
 * @param engine String?
 * @param delayed 开始延时
 * @param duration 拖拽动画持续时间
 * @param dragRate 拉拽高度比率
 * @param animationOnly 是否只显示动画
 * @return `true` success, `false` fail
 */
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

/**
 * 完成刷新
 * @receiver Refresh Item
 * @param engine String?
 * @param success 是否成功
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishRefresh(
    engine: String? = null,
    success: Boolean = true
): Boolean {
    return engine.getRefreshEngine()?.finishRefresh(this, success) ?: false
}

/**
 * 完成刷新
 * @receiver Refresh Item
 * @param engine String?
 * @param delayed 开始延时
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishRefresh(
    engine: String? = null,
    delayed: Int
): Boolean {
    return engine.getRefreshEngine()?.finishRefresh(this, delayed) ?: false
}

/**
 * 完成刷新
 * @receiver Refresh Item
 * @param engine String?
 * @param delayed 开始延时
 * @param success 是否成功
 * @param noMoreData 是否没有更多数据
 * @return `true` success, `false` fail
 */
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

/**
 * 完成刷新并标记没有更多数据
 * @receiver Refresh Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishRefreshWithNoMoreData(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.finishRefreshWithNoMoreData(this) ?: false
}

/**
 * 完成加载
 * @receiver Refresh Item
 * @param engine String?
 * @param success 是否成功
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishLoadMore(
    engine: String? = null,
    success: Boolean = true
): Boolean {
    return engine.getRefreshEngine()?.finishLoadMore(this, success) ?: false
}

/**
 * 完成加载
 * @receiver Refresh Item
 * @param engine String?
 * @param delayed 开始延时
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishLoadMore(
    engine: String? = null,
    delayed: Int
): Boolean {
    return engine.getRefreshEngine()?.finishLoadMore(this, delayed) ?: false
}

/**
 * 完成加载
 * @receiver Refresh Item
 * @param engine String?
 * @param delayed 开始延时
 * @param success 是否成功
 * @param noMoreData 是否没有更多数据
 * @return `true` success, `false` fail
 */
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

/**
 * 完成加载并标记没有更多数据
 * @receiver Refresh Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishLoadMoreWithNoMoreData(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.finishLoadMoreWithNoMoreData(this) ?: false
}

/**
 * 关闭 Header 或 Footer
 * @receiver Refresh Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_closeHeaderOrFooter(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.closeHeaderOrFooter(this) ?: false
}

/**
 * 设置没有更多数据状态
 * @receiver Refresh Item
 * @param engine String?
 * @param noMoreData 是否没有更多数据
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_setNoMoreData(
    engine: String? = null,
    noMoreData: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setNoMoreData(this, noMoreData) ?: false
}

/**
 * 重置没有更多数据状态
 * @receiver Refresh Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_resetNoMoreData(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.resetNoMoreData(this) ?: false
}

/**
 * 完成刷新、加载
 * @receiver Refresh Item
 * @param engine String?
 * @param success 是否成功
 * @return `true` success, `false` fail
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_finishRefreshAndLoad(
    engine: String? = null,
    success: Boolean = true
): Boolean {
    return engine.getRefreshEngine()?.finishRefreshAndLoad(this, success) ?: false
}

/**
 * 完成刷新或加载
 * @receiver Refresh Item
 * @param engine String?
 * @param refresh 是否刷新
 * @param success 是否成功
 * @return `true` success, `false` fail
 */
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

/**
 * 获取刷新头
 * @receiver Refresh Item
 * @param engine String?
 * @return 刷新头
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_getRefreshHeader(
    engine: String? = null
): Any? {
    return engine.getRefreshEngine()?.getRefreshHeader(this)
}

/**
 * 获取加载尾
 * @receiver Refresh Item
 * @param engine String?
 * @return 加载尾
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_getRefreshFooter(
    engine: String? = null
): Any? {
    return engine.getRefreshEngine()?.getRefreshFooter(this)
}

/**
 * 获取当前状态
 * @receiver Refresh Item
 * @param engine String?
 * @return 当前状态
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_getState(
    engine: String? = null
): Any? {
    return engine.getRefreshEngine()?.getState(this)
}

/**
 * 获取实体布局视图
 * @receiver Refresh Item
 * @param engine String?
 * @return 实体布局视图
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_getLayout(
    engine: String? = null
): ViewGroup? {
    return engine.getRefreshEngine()?.getLayout(this)
}

/**
 * 是否正在刷新
 * @receiver Refresh Item
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_isRefreshing(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.isRefreshing(this) ?: false
}

/**
 * 是否正在加载
 * @receiver Refresh Item
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun <Item : IRefreshEngine.EngineItem> Item?.refresh_isLoading(
    engine: String? = null
): Boolean {
    return engine.getRefreshEngine()?.isLoading(this) ?: false
}