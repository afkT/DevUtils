package dev.engine.extensions.eventbus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import dev.engine.DevEngine
import dev.engine.eventbus.IEventBusEngine

// =================================
// = IEventBusEngine<EngineConfig> =
// =================================

/**
 * 通过 Key 获取 EventBus Engine
 * @param engine String?
 * @return IEventBusEngine<EngineConfig>
 * 内部做了处理如果匹配不到则返回默认 EventBus Engine
 */
fun String?.getEventBusEngine(): IEventBusEngine<in IEventBusEngine.EngineConfig>? {
    DevEngine.getEventBus(this)?.let { value ->
        return value
    }
    return DevEngine.getEventBus()
}

// =====================
// = Key EventBus Engine =
// =====================

// =============
// = 对外公开方法 =
// =============

fun <Config : IEventBusEngine.EngineConfig> eventbus_initialize(
    engine: String? = null,
    config: Config?
): Boolean {
    return engine.getEventBusEngine()?.initialize(config) ?: false
}

fun <Config : IEventBusEngine.EngineConfig> eventbus_config(
    engine: String? = null,
    key: String?,
    config: Config?
): Boolean {
    return engine.getEventBusEngine()?.config(key, config) ?: false
}

// ==========
// = 发送事件 =
// ==========

fun <T> eventbus_post(
    engine: String? = null,
    key: String?,
    value: T
): Boolean {
    return engine.getEventBusEngine()?.post(key, value) ?: false
}

fun <T> eventbus_postSticky(
    engine: String? = null,
    key: String?,
    value: T
): Boolean {
    return engine.getEventBusEngine()?.postSticky(key, value) ?: false
}

fun <T> eventbus_postDelay(
    engine: String? = null,
    key: String?,
    value: T,
    delay: Long
): Boolean {
    return engine.getEventBusEngine()?.postDelay(key, value, delay) ?: false
}

fun <T> eventbus_postDelay(
    engine: String? = null,
    key: String?,
    lifecycle: LifecycleOwner?,
    value: T,
    delay: Long
): Boolean {
    return engine.getEventBusEngine()?.postDelay(key, lifecycle, value, delay) ?: false
}

fun <T> eventbus_postOrderly(
    engine: String? = null,
    key: String?,
    value: T
): Boolean {
    return engine.getEventBusEngine()?.postOrderly(key, value) ?: false
}

fun <T> eventbus_postAcrossProcess(
    engine: String? = null,
    key: String?,
    value: T
): Boolean {
    return engine.getEventBusEngine()?.postAcrossProcess(key, value) ?: false
}

fun <T> eventbus_postAcrossApp(
    engine: String? = null,
    key: String?,
    value: T
): Boolean {
    return engine.getEventBusEngine()?.postAcrossApp(key, value) ?: false
}

// ==========
// = 观察事件 =
// ==========

fun <T> eventbus_observe(
    engine: String? = null,
    key: String?,
    type: Class<T>?,
    lifecycle: LifecycleOwner?,
    observer: Observer<T>?
): Boolean {
    return engine.getEventBusEngine()?.observe(key, type, lifecycle, observer) ?: false
}

fun <T> eventbus_observeSticky(
    engine: String? = null,
    key: String?,
    type: Class<T>?,
    lifecycle: LifecycleOwner?,
    observer: Observer<T>?
): Boolean {
    return engine.getEventBusEngine()?.observeSticky(key, type, lifecycle, observer) ?: false
}

fun <T> eventbus_observeForever(
    engine: String? = null,
    key: String?,
    type: Class<T>?,
    observer: Observer<T>?
): Boolean {
    return engine.getEventBusEngine()?.observeForever(key, type, observer) ?: false
}

fun <T> eventbus_observeStickyForever(
    engine: String? = null,
    key: String?,
    type: Class<T>?,
    observer: Observer<T>?
): Boolean {
    return engine.getEventBusEngine()?.observeStickyForever(key, type, observer) ?: false
}

fun <T> eventbus_removeObserver(
    engine: String? = null,
    key: String?,
    type: Class<T>?,
    observer: Observer<T>?
): Boolean {
    return engine.getEventBusEngine()?.removeObserver(key, type, observer) ?: false
}
