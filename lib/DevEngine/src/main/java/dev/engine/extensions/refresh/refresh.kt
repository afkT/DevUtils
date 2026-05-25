package dev.engine.extensions.refresh

import android.view.View
import dev.engine.DevEngine
import dev.engine.core.refresh.RefreshItem
import dev.engine.refresh.IRefreshEngine

// =============================================
// = IRefreshEngine<EngineConfig, EngineItem> =
// =============================================

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

fun View.refresh_initialize(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this)
): Boolean {
    return engine.getRefreshEngine()?.initialize(item) ?: false
}

fun <Config : IRefreshEngine.EngineConfig> View.refresh_applyConfig(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    config: Config?
): Boolean {
    return engine.getRefreshEngine()?.applyConfig(item, config) ?: false
}

// ==========
// = 开关配置 =
// ==========

fun View.refresh_setEnableRefresh(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableRefresh(item, enabled) ?: false
}

fun View.refresh_setEnableLoadMore(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableLoadMore(item, enabled) ?: false
}

fun View.refresh_setEnableAutoLoadMore(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnableAutoLoadMore(item, enabled) ?: false
}

fun View.refresh_setEnablePureScrollMode(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    enabled: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setEnablePureScrollMode(item, enabled) ?: false
}

fun View.refresh_setDisableContentWhenRefresh(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    disable: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setDisableContentWhenRefresh(item, disable) ?: false
}

fun View.refresh_setDisableContentWhenLoading(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    disable: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setDisableContentWhenLoading(item, disable) ?: false
}

// ==========
// = 组件设置 =
// ==========

fun View.refresh_setRefreshContent(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    content: View?
): Boolean {
    return engine.getRefreshEngine()?.setRefreshContent(item, content) ?: false
}

fun View.refresh_setRefreshContent(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    content: View?,
    width: Int,
    height: Int
): Boolean {
    return engine.getRefreshEngine()?.setRefreshContent(item, content, width, height) ?: false
}

fun View.refresh_setRefreshHeader(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    header: Any?
): Boolean {
    return engine.getRefreshEngine()?.setRefreshHeader(item, header) ?: false
}

fun View.refresh_setRefreshFooter(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    footer: Any?
): Boolean {
    return engine.getRefreshEngine()?.setRefreshFooter(item, footer) ?: false
}

// ==========
// = 监听设置 =
// ==========

fun View.refresh_setOnRefreshListener(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    listener: IRefreshEngine.OnRefreshListener?
): Boolean {
    return engine.getRefreshEngine()?.setOnRefreshListener(item, listener) ?: false
}

fun View.refresh_setOnLoadMoreListener(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    listener: IRefreshEngine.OnLoadMoreListener?
): Boolean {
    return engine.getRefreshEngine()?.setOnLoadMoreListener(item, listener) ?: false
}

fun View.refresh_setOnRefreshLoadMoreListener(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    listener: IRefreshEngine.OnRefreshLoadMoreListener?
): Boolean {
    return engine.getRefreshEngine()?.setOnRefreshLoadMoreListener(item, listener) ?: false
}

fun View.refresh_setOnMultiListener(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    listener: Any?
): Boolean {
    return engine.getRefreshEngine()?.setOnMultiListener(item, listener) ?: false
}

fun View.refresh_setScrollBoundaryDecider(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    boundary: Any?
): Boolean {
    return engine.getRefreshEngine()?.setScrollBoundaryDecider(item, boundary) ?: false
}

// ==========
// = 状态操作 =
// ==========

fun View.refresh_autoRefresh(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this)
): Boolean {
    return engine.getRefreshEngine()?.autoRefresh(item) ?: false
}

fun View.refresh_autoRefresh(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    delayed: Int
): Boolean {
    return engine.getRefreshEngine()?.autoRefresh(item, delayed) ?: false
}

fun View.refresh_autoLoadMore(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this)
): Boolean {
    return engine.getRefreshEngine()?.autoLoadMore(item) ?: false
}

fun View.refresh_autoLoadMore(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    delayed: Int
): Boolean {
    return engine.getRefreshEngine()?.autoLoadMore(item, delayed) ?: false
}

fun View.refresh_finishRefresh(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    success: Boolean = true
): Boolean {
    return engine.getRefreshEngine()?.finishRefresh(item, success) ?: false
}

fun View.refresh_finishRefresh(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    delayed: Int
): Boolean {
    return engine.getRefreshEngine()?.finishRefresh(item, delayed) ?: false
}

fun View.refresh_finishRefreshWithNoMoreData(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this)
): Boolean {
    return engine.getRefreshEngine()?.finishRefreshWithNoMoreData(item) ?: false
}

fun View.refresh_finishLoadMore(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    success: Boolean = true
): Boolean {
    return engine.getRefreshEngine()?.finishLoadMore(item, success) ?: false
}

fun View.refresh_finishLoadMore(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    delayed: Int
): Boolean {
    return engine.getRefreshEngine()?.finishLoadMore(item, delayed) ?: false
}

fun View.refresh_finishLoadMoreWithNoMoreData(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this)
): Boolean {
    return engine.getRefreshEngine()?.finishLoadMoreWithNoMoreData(item) ?: false
}

fun View.refresh_closeHeaderOrFooter(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this)
): Boolean {
    return engine.getRefreshEngine()?.closeHeaderOrFooter(item) ?: false
}

fun View.refresh_setNoMoreData(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    noMoreData: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setNoMoreData(item, noMoreData) ?: false
}

fun View.refresh_resetNoMoreData(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this)
): Boolean {
    return engine.getRefreshEngine()?.resetNoMoreData(item) ?: false
}

fun View.refresh_finishRefreshAndLoad(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    success: Boolean = true
): Boolean {
    return engine.getRefreshEngine()?.finishRefreshAndLoad(item, success) ?: false
}

fun View.refresh_finishRefreshOrLoad(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    refresh: Boolean,
    success: Boolean = true
): Boolean {
    return engine.getRefreshEngine()?.finishRefreshOrLoad(item, refresh, success) ?: false
}

// ==========
// = 状态查询 =
// ==========

fun View.refresh_isRefreshing(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this)
): Boolean {
    return engine.getRefreshEngine()?.isRefreshing(item) ?: false
}

fun View.refresh_isLoading(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this)
): Boolean {
    return engine.getRefreshEngine()?.isLoading(item) ?: false
}

fun View.refresh_getState(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this)
): Any? {
    return engine.getRefreshEngine()?.getState(item)
}