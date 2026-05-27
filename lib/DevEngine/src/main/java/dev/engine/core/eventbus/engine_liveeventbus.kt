package dev.engine.core.eventbus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import dev.engine.eventbus.IEventBusEngine

/**
 * detail: LiveEventBus EventBus Engine 实现
 * @author Ttt
 * @see https://github.com/JeremyLiao/LiveEventBus
 */
open class LiveEventBusEngineImpl : IEventBusEngine<LiveEventBusConfig?> {

    // =============
    // = 对外公开方法 =
    // =============

    override fun initialize(config: LiveEventBusConfig?): Boolean {
        config ?: return false
        return applyConfig(config)
    }

    override fun config(
        key: String?,
        config: LiveEventBusConfig?
    ): Boolean {
        if (key.isNullOrEmpty()) return false
        config ?: return false
        config.lifecycleObserverAlwaysActive()?.let {
            LiveEventBus.config(key).lifecycleObserverAlwaysActive(it)
        }
        config.autoClear()?.let {
            LiveEventBus.config(key).autoClear(it)
        }
        return true
    }

    // ==========
    // = 发送事件 =
    // ==========

    override fun <T : Any?> post(
        key: String?,
        value: T
    ): Boolean {
        return getObservable(key, value)?.run {
            post(value); true
        } ?: false
    }

    override fun <T : Any?> postSticky(
        key: String?,
        value: T
    ): Boolean {
        return post(key, value)
    }

    override fun <T : Any?> postDelay(
        key: String?,
        value: T,
        delay: Long
    ): Boolean {
        return getObservable(key, value)?.run {
            postDelay(value, delay); true
        } ?: false
    }

    override fun <T : Any?> postDelay(
        key: String?,
        lifecycle: LifecycleOwner?,
        value: T,
        delay: Long
    ): Boolean {
        lifecycle ?: return false
        return getObservable(key, value)?.run {
            postDelay(lifecycle, value, delay); true
        } ?: false
    }

    override fun <T : Any?> postOrderly(
        key: String?,
        value: T
    ): Boolean {
        return getObservable(key, value)?.run {
            postOrderly(value); true
        } ?: false
    }

    override fun <T : Any?> postAcrossProcess(
        key: String?,
        value: T
    ): Boolean {
        return getObservable(key, value)?.run {
            postAcrossProcess(value); true
        } ?: false
    }

    override fun <T : Any?> postAcrossApp(
        key: String?,
        value: T
    ): Boolean {
        return getObservable(key, value)?.run {
            postAcrossApp(value); true
        } ?: false
    }

    // ==========
    // = 观察事件 =
    // ==========

    override fun <T : Any?> observe(
        key: String?,
        type: Class<T>?,
        lifecycle: LifecycleOwner?,
        observer: Observer<T>?
    ): Boolean {
        lifecycle ?: return false
        observer ?: return false
        return getObservable(key, type)?.run {
            observe(lifecycle, observer); true
        } ?: false
    }

    override fun <T : Any?> observeSticky(
        key: String?,
        type: Class<T>?,
        lifecycle: LifecycleOwner?,
        observer: Observer<T>?
    ): Boolean {
        lifecycle ?: return false
        observer ?: return false
        return getObservable(key, type)?.run {
            observeSticky(lifecycle, observer); true
        } ?: false
    }

    override fun <T : Any?> observeForever(
        key: String?,
        type: Class<T>?,
        observer: Observer<T>?
    ): Boolean {
        observer ?: return false
        return getObservable(key, type)?.run {
            observeForever(observer); true
        } ?: false
    }

    override fun <T : Any?> observeStickyForever(
        key: String?,
        type: Class<T>?,
        observer: Observer<T>?
    ): Boolean {
        observer ?: return false
        return getObservable(key, type)?.run {
            observeStickyForever(observer); true
        } ?: false
    }

    override fun <T : Any?> removeObserver(
        key: String?,
        type: Class<T>?,
        observer: Observer<T>?
    ): Boolean {
        observer ?: return false
        return getObservable(key, type)?.run {
            removeObserver(observer); true
        } ?: false
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 应用 LiveEventBus 全局配置
     * @param config EventBus Config
     * @return `true` success, `false` fail
     */
    protected open fun applyConfig(config: LiveEventBusConfig): Boolean {
        config.context()?.let {
            LiveEventBus.config().setContext(it.applicationContext)
        }
        config.lifecycleObserverAlwaysActive()?.let {
            LiveEventBus.config().lifecycleObserverAlwaysActive(it)
        }
        config.autoClear()?.let {
            LiveEventBus.config().autoClear(it)
        }
        config.logger()?.let {
            LiveEventBus.config().setLogger(it)
        }
        config.enableLogger()?.let {
            LiveEventBus.config().enableLogger(it)
        }
        return true
    }

    /**
     * 获取事件通道
     * @param key key
     * @param value 事件数据
     * @return [com.jeremyliao.liveeventbus.core.Observable]
     */
    protected open fun <T> getObservable(
        key: String?,
        value: T
    ): com.jeremyliao.liveeventbus.core.Observable<T>? {
        if (key.isNullOrEmpty()) return null
        @Suppress("UNCHECKED_CAST")
        val type = value?.javaClass as? Class<T> ?: Any::class.java as Class<T>
        return LiveEventBus.get(key, type)
    }

    /**
     * 获取事件通道
     * @param key key
     * @param type 事件类型
     * @return [com.jeremyliao.liveeventbus.core.Observable]
     */
    protected open fun <T> getObservable(
        key: String?,
        type: Class<T>?
    ): com.jeremyliao.liveeventbus.core.Observable<T>? {
        if (key.isNullOrEmpty()) return null
        type ?: return null
        return LiveEventBus.get(key, type)
    }

}
