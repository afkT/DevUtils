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

fun View.refresh_setNoMoreData(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    noMoreData: Boolean
): Boolean {
    return engine.getRefreshEngine()?.setNoMoreData(item, noMoreData) ?: false
}

fun View.refresh_resetMoreData(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    count: Int,
    perPage: Int
): Boolean {
    return engine.getRefreshEngine()?.resetMoreData(item, count, perPage) ?: false
}

fun View.refresh_closeLoader(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    pureScrollMode: Boolean = false
): Boolean {
    return engine.getRefreshEngine()?.closeLoader(item, pureScrollMode) ?: false
}

fun View.refresh_openLoader(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this)
): Boolean {
    return engine.getRefreshEngine()?.openLoader(item) ?: false
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

// ==========
// = 状态操作 =
// ==========

fun View.refresh_autoRefresh(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this)
): Boolean {
    return engine.getRefreshEngine()?.autoRefresh(item) ?: false
}

fun View.refresh_finishRefresh(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    success: Boolean = true
): Boolean {
    return engine.getRefreshEngine()?.finishRefresh(item, success) ?: false
}

fun View.refresh_finishLoadMore(
    engine: String? = null,
    item: RefreshItem = RefreshItem.create(this),
    success: Boolean = true
): Boolean {
    return engine.getRefreshEngine()?.finishLoadMore(item, success) ?: false
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