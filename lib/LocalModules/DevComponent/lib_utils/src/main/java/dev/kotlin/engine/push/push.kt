package dev.kotlin.engine.push

import android.app.Application
import android.content.Context
import dev.engine.DevEngine
import dev.engine.push.IPushEngine

// =========================================
// = IPushEngine<EngineConfig, EngineItem> =
// =========================================

/**
 * 通过 Key 获取 Push Engine
 * @param engine String?
 * @return IPushEngine<EngineConfig, EngineItem>
 * 内部做了处理如果匹配不到则返回默认 Push Engine
 */
internal fun getEngine(engine: String?): IPushEngine<in IPushEngine.EngineConfig, in IPushEngine.EngineItem>? {
    DevEngine.getPush(engine)?.let { value ->
        return value
    }
    return DevEngine.getPush()
}

// ===================
// = Key Push Engine =
// ===================

// =============
// = 对外公开方法 =
// =============

fun <Config : IPushEngine.EngineConfig> Application.push_initialize(
    engine: String? = null,
    config: Config?
) {
    getEngine(engine)?.initialize(this, config)
}

fun <Config : IPushEngine.EngineConfig> Context.push_register(
    engine: String? = null,
    config: Config?
) {
    getEngine(engine)?.register(this, config)
}

fun <Config : IPushEngine.EngineConfig> Context.push_unregister(
    engine: String? = null,
    config: Config?
) {
    getEngine(engine)?.unregister(this, config)
}

// =

fun push_onReceiveServicePid(
    engine: String? = null,
    context: Context?,
    pid: Int
) {
    getEngine(engine)?.onReceiveServicePid(context, pid)
}

fun push_onReceiveClientId(
    engine: String? = null,
    context: Context?,
    clientId: String?
) {
    getEngine(engine)?.onReceiveClientId(context, clientId)
}

fun push_onReceiveDeviceToken(
    engine: String? = null,
    context: Context?,
    deviceToken: String?
) {
    getEngine(engine)?.onReceiveDeviceToken(context, deviceToken)
}

fun push_onReceiveOnlineState(
    engine: String? = null,
    context: Context?,
    online: Boolean
) {
    getEngine(engine)?.onReceiveOnlineState(context, online)
}

fun <Item : IPushEngine.EngineItem> push_onReceiveCommandResult(
    engine: String? = null,
    context: Context?,
    message: Item?
) {
    getEngine(engine)?.onReceiveCommandResult(context, message)
}

fun <Item : IPushEngine.EngineItem> push_onNotificationMessageArrived(
    engine: String? = null,
    context: Context?,
    message: Item?
) {
    getEngine(engine)?.onNotificationMessageArrived(context, message)
}

fun <Item : IPushEngine.EngineItem> push_onNotificationMessageClicked(
    engine: String? = null,
    context: Context?,
    message: Item?
) {
    getEngine(engine)?.onNotificationMessageClicked(context, message)
}

fun <Item : IPushEngine.EngineItem> push_onReceiveMessageData(
    engine: String? = null,
    context: Context?,
    message: Item?
) {
    getEngine(engine)?.onReceiveMessageData(context, message)
}

// ===============
// = 转换 Message =
// ===============

fun <Item : IPushEngine.EngineItem> push_convertMessage(
    engine: String? = null,
    message: Any?
): Item? {
    return getEngine(engine)?.convertMessage(message) as? Item
}