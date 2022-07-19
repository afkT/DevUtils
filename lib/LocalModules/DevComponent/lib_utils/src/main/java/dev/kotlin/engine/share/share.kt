package dev.kotlin.engine.share

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import dev.engine.DevEngine
import dev.engine.share.IShareEngine
import dev.engine.share.listener.ShareListener

// ==========================================
// = IShareEngine<EngineConfig, EngineItem> =
// ==========================================

/**
 * 通过 Key 获取 Share Engine
 * @param engine String?
 * @return IShareEngine<EngineConfig, EngineItem>
 * 内部做了处理如果匹配不到则返回默认 Share Engine
 */
internal fun getEngine(engine: String?): IShareEngine<in IShareEngine.EngineConfig, in IShareEngine.EngineItem>? {
    DevEngine.getShare(engine)?.let { value ->
        return value
    }
    return DevEngine.getShare()
}

// ====================
// = Key Share Engine =
// ====================

// =============
// = 对外公开方法 =
// =============

fun <Config : IShareEngine.EngineConfig> Application.share_initialize(
    engine: String? = null,
    config: Config?
) {
    getEngine(engine)?.initialize(this, config)
}

// ==========
// = 分享操作 =
// ==========

fun <Item : IShareEngine.EngineItem> Activity.share_openMinApp(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return getEngine(engine)?.let {
        return (it as? IShareEngine<*, Item>)?.openMinApp(
            this, params, listener
        ) ?: false
    } ?: false
}

fun <Item : IShareEngine.EngineItem> Activity.share_shareMinApp(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return getEngine(engine)?.let {
        return (it as? IShareEngine<*, Item>)?.shareMinApp(
            this, params, listener
        ) ?: false
    } ?: false
}

fun <Item : IShareEngine.EngineItem> Activity.share_shareUrl(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return getEngine(engine)?.let {
        return (it as? IShareEngine<*, Item>)?.shareUrl(
            this, params, listener
        ) ?: false
    } ?: false
}

fun <Item : IShareEngine.EngineItem> Activity.share_shareImage(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return getEngine(engine)?.let {
        return (it as? IShareEngine<*, Item>)?.shareImage(
            this, params, listener
        ) ?: false
    } ?: false
}

fun <Item : IShareEngine.EngineItem> Activity.share_shareImageList(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return getEngine(engine)?.let {
        return (it as? IShareEngine<*, Item>)?.shareImageList(
            this, params, listener
        ) ?: false
    } ?: false
}

fun <Item : IShareEngine.EngineItem> Activity.share_shareText(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return getEngine(engine)?.let {
        return (it as? IShareEngine<*, Item>)?.shareText(
            this, params, listener
        ) ?: false
    } ?: false
}

fun <Item : IShareEngine.EngineItem> Activity.share_shareVideo(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return getEngine(engine)?.let {
        return (it as? IShareEngine<*, Item>)?.shareVideo(
            this, params, listener
        ) ?: false
    } ?: false
}

fun <Item : IShareEngine.EngineItem> Activity.share_shareMusic(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return getEngine(engine)?.let {
        return (it as? IShareEngine<*, Item>)?.shareMusic(
            this, params, listener
        ) ?: false
    } ?: false
}

fun <Item : IShareEngine.EngineItem> Activity.share_shareEmoji(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return getEngine(engine)?.let {
        return (it as? IShareEngine<*, Item>)?.shareEmoji(
            this, params, listener
        ) ?: false
    } ?: false
}

fun <Item : IShareEngine.EngineItem> Activity.share_shareFile(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return getEngine(engine)?.let {
        return (it as? IShareEngine<*, Item>)?.shareFile(
            this, params, listener
        ) ?: false
    } ?: false
}

fun <Item : IShareEngine.EngineItem> Activity.share_share(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return getEngine(engine)?.let {
        return (it as? IShareEngine<*, Item>)?.share(
            this, params, listener
        ) ?: false
    } ?: false
}

// =

fun Context.share_onActivityResult(
    engine: String? = null,
    requestCode: Int,
    resultCode: Int,
    intent: Intent?
) {
    getEngine(engine)?.onActivityResult(this, requestCode, resultCode, intent)
}