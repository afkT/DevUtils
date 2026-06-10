package dev.engine.extensions.analytics

import android.app.Application
import android.content.Context
import dev.engine.DevEngine
import dev.engine.analytics.IAnalyticsEngine

// ==============================================
// = IAnalyticsEngine<EngineConfig, EngineItem> =
// ==============================================

/**
 * 通过 Key 获取 Analytics Engine
 * @receiver String?
 * @return IAnalyticsEngine<EngineConfig, EngineItem>
 * 内部做了处理如果匹配不到则返回默认 Analytics Engine
 */
fun String?.getAnalyticsEngine(): IAnalyticsEngine<
        in IAnalyticsEngine.EngineConfig,
        in IAnalyticsEngine.EngineItem>? {
    DevEngine.getAnalytics(this)?.let { value ->
        return value
    }
    return DevEngine.getAnalytics()
}

// ========================
// = Key Analytics Engine =
// ========================

// =============
// = 对外公开方法 =
// =============

/**
 * 初始化方法
 * @receiver Application
 * @param engine String?
 * @param config Analytics Config
 */
fun <Config : IAnalyticsEngine.EngineConfig> Application.analytics_initialize(
    engine: String? = null,
    config: Config?
) {
    engine.getAnalyticsEngine()?.initialize(this, config)
}

/**
 * 绑定
 * @receiver Context
 * @param engine String?
 * @param config Analytics Config
 */
fun <Config : IAnalyticsEngine.EngineConfig> Context.analytics_register(
    engine: String? = null,
    config: Config?
) {
    engine.getAnalyticsEngine()?.register(this, config)
}

/**
 * 解绑
 * @receiver Context
 * @param engine String?
 * @param config Analytics Config
 */
fun <Config : IAnalyticsEngine.EngineConfig> Context.analytics_unregister(
    engine: String? = null,
    config: Config?
) {
    engine.getAnalyticsEngine()?.unregister(this, config)
}

/**
 * 数据统计 ( 埋点 ) 方法
 * @receiver Analytics ( Data、Params ) Item
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Item : IAnalyticsEngine.EngineItem> Item.analytics_track(
    engine: String? = null
): Boolean {
    return engine.getAnalyticsEngine()?.track(this) ?: false
}