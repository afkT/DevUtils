package dev.engine.core.eventbus

import android.content.Context
import dev.engine.eventbus.IEventBusEngine

/**
 * detail: EventBus Config
 * @author Ttt
 */
open class EventBusConfig private constructor(
    config: EventBusConfig?
) : IEventBusEngine.EngineConfig {

    // Context
    private var mContext: Context? = null

    // 生命周期观察者是否始终激活
    private var mLifecycleObserverAlwaysActive: Boolean? = null

    // 无观察者时是否自动清理
    private var mAutoClear: Boolean? = null

    // Logger
    private var mLogger: Any? = null

    // 是否启用 Logger
    private var mEnableLogger: Boolean? = null

    companion object {

        fun create(): EventBusConfig {
            return EventBusConfig(null)
        }

        fun create(config: EventBusConfig?): EventBusConfig {
            return EventBusConfig(config)
        }
    }

    init {
        config?.let {
            this.mContext = it.mContext
            this.mLifecycleObserverAlwaysActive = it.mLifecycleObserverAlwaysActive
            this.mAutoClear = it.mAutoClear
            this.mLogger = it.mLogger
            this.mEnableLogger = it.mEnableLogger
        }
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 克隆配置信息
     * @return [EventBusConfig]
     */
    open fun clone(): EventBusConfig {
        return EventBusConfig(this)
    }

    // ===========
    // = get/set =
    // ===========

    open fun context(): Context? {
        return mContext
    }

    open fun setContext(context: Context?): EventBusConfig {
        mContext = context
        return this
    }

    open fun lifecycleObserverAlwaysActive(): Boolean? {
        return mLifecycleObserverAlwaysActive
    }

    open fun setLifecycleObserverAlwaysActive(
        lifecycleObserverAlwaysActive: Boolean?
    ): EventBusConfig {
        mLifecycleObserverAlwaysActive = lifecycleObserverAlwaysActive
        return this
    }

    open fun autoClear(): Boolean? {
        return mAutoClear
    }

    open fun setAutoClear(autoClear: Boolean?): EventBusConfig {
        mAutoClear = autoClear
        return this
    }

    open fun logger(): Any? {
        return mLogger
    }

    open fun setLogger(logger: Any?): EventBusConfig {
        mLogger = logger
        return this
    }

    open fun enableLogger(): Boolean? {
        return mEnableLogger
    }

    open fun setEnableLogger(enableLogger: Boolean?): EventBusConfig {
        mEnableLogger = enableLogger
        return this
    }
}