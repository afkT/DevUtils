package dev.kotlin.engine.analytics

import android.app.Application
import android.content.Context
import dev.engine.DevEngine
import dev.engine.analytics.IAnalyticsEngine

// ==============================================
// = IAnalyticsEngine<EngineConfig, EngineItem> =
// ==============================================

/**
 * 通过 Key 获取 Analytics Engine
 * @param engine String?
 * @return IAnalyticsEngine<EngineConfig, EngineItem>
 * 内部做了处理如果匹配不到则返回默认 Analytics Engine
 */
internal fun getEngine(engine: String?): IAnalyticsEngine<
        in IAnalyticsEngine.EngineConfig,
        in IAnalyticsEngine.EngineItem>? {
    DevEngine.getAnalytics(engine)?.let { value ->
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

fun <Config : IAnalyticsEngine.EngineConfig> Application.analytics_initialize(
    engine: String? = null,
    config: Config?
) {
    getEngine(engine)?.initialize(this, config)
}

fun <Config : IAnalyticsEngine.EngineConfig> Context.analytics_register(
    engine: String? = null,
    config: Config?
) {
    getEngine(engine)?.register(this, config)
}

fun <Config : IAnalyticsEngine.EngineConfig> Context.analytics_unregister(
    engine: String? = null,
    config: Config?
) {
    getEngine(engine)?.unregister(this, config)
}

fun <Item : IAnalyticsEngine.EngineItem> Item.analytics_track(
    engine: String? = null
): Boolean {
    return getEngine(engine)?.track(this) ?: false
}