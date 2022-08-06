package dev.expand.engine.push

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
fun String?.getPushEngine(): IPushEngine<in IPushEngine.EngineConfig, in IPushEngine.EngineItem>? {
    DevEngine.getPush(this)?.let { value ->
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
    engine.getPushEngine()?.initialize(this, config)
}

fun <Config : IPushEngine.EngineConfig> Context.push_register(
    engine: String? = null,
    config: Config?
) {
    engine.getPushEngine()?.register(this, config)
}

fun <Config : IPushEngine.EngineConfig> Context.push_unregister(
    engine: String? = null,
    config: Config?
) {
    engine.getPushEngine()?.unregister(this, config)
}

// =

fun push_onReceiveServicePid(
    engine: String? = null,
    context: Context?,
    pid: Int
) {
    engine.getPushEngine()?.onReceiveServicePid(context, pid)
}

fun push_onReceiveClientId(
    engine: String? = null,
    context: Context?,
    clientId: String?
) {
    engine.getPushEngine()?.onReceiveClientId(context, clientId)
}

fun push_onReceiveDeviceToken(
    engine: String? = null,
    context: Context?,
    deviceToken: String?
) {
    engine.getPushEngine()?.onReceiveDeviceToken(context, deviceToken)
}

fun push_onReceiveOnlineState(
    engine: String? = null,
    context: Context?,
    online: Boolean
) {
    engine.getPushEngine()?.onReceiveOnlineState(context, online)
}

fun <Item : IPushEngine.EngineItem> push_onReceiveCommandResult(
    engine: String? = null,
    context: Context?,
    message: Item?
) {
    engine.getPushEngine()?.onReceiveCommandResult(context, message)
}

fun <Item : IPushEngine.EngineItem> push_onNotificationMessageArrived(
    engine: String? = null,
    context: Context?,
    message: Item?
) {
    engine.getPushEngine()?.onNotificationMessageArrived(context, message)
}

fun <Item : IPushEngine.EngineItem> push_onNotificationMessageClicked(
    engine: String? = null,
    context: Context?,
    message: Item?
) {
    engine.getPushEngine()?.onNotificationMessageClicked(context, message)
}

fun <Item : IPushEngine.EngineItem> push_onReceiveMessageData(
    engine: String? = null,
    context: Context?,
    message: Item?
) {
    engine.getPushEngine()?.onReceiveMessageData(context, message)
}

// ===============
// = 转换 Message =
// ===============

fun <Item : IPushEngine.EngineItem> push_convertMessage(
    engine: String? = null,
    message: Any?
): Item? {
    return engine.getPushEngine()?.convertMessage(message) as? Item
}