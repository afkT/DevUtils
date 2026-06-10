package dev.engine.extensions.share

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
 * @receiver String?
 * @return IShareEngine<EngineConfig, EngineItem>
 * 内部做了处理如果匹配不到则返回默认 Share Engine
 */
fun String?.getShareEngine(): IShareEngine<in IShareEngine.EngineConfig, in IShareEngine.EngineItem>? {
    DevEngine.getShare(this)?.let { value ->
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

/**
 * 初始化方法
 * @receiver Application
 * @param engine String?
 * @param config Share Config
 */
fun <Config : IShareEngine.EngineConfig> Application.share_initialize(
    engine: String? = null,
    config: Config?
) {
    engine.getShareEngine()?.initialize(this, config)
}

// ==========
// = 分享操作 =
// ==========

/**
 * 打开小程序
 * @receiver Activity
 * @param engine String?
 * @param params Share ( Data、Params ) Item
 * @param listener 分享回调
 * @return `true` success, `false` fail
 */
fun <Item : IShareEngine.EngineItem> Activity.share_openMinApp(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return engine.getShareEngine()?.let {
        return (it as? IShareEngine<*, Item>)?.openMinApp(
            this, params, listener
        ) ?: false
    } ?: false
}

/**
 * 分享小程序
 * @receiver Activity
 * @param engine String?
 * @param params Share ( Data、Params ) Item
 * @param listener 分享回调
 * @return `true` success, `false` fail
 */
fun <Item : IShareEngine.EngineItem> Activity.share_shareMinApp(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return engine.getShareEngine()?.let {
        return (it as? IShareEngine<*, Item>)?.shareMinApp(
            this, params, listener
        ) ?: false
    } ?: false
}

/**
 * 分享链接
 * @receiver Activity
 * @param engine String?
 * @param params Share ( Data、Params ) Item
 * @param listener 分享回调
 * @return `true` success, `false` fail
 */
fun <Item : IShareEngine.EngineItem> Activity.share_shareUrl(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return engine.getShareEngine()?.let {
        return (it as? IShareEngine<*, Item>)?.shareUrl(
            this, params, listener
        ) ?: false
    } ?: false
}

/**
 * 分享图片
 * @receiver Activity
 * @param engine String?
 * @param params Share ( Data、Params ) Item
 * @param listener 分享回调
 * @return `true` success, `false` fail
 */
fun <Item : IShareEngine.EngineItem> Activity.share_shareImage(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return engine.getShareEngine()?.let {
        return (it as? IShareEngine<*, Item>)?.shareImage(
            this, params, listener
        ) ?: false
    } ?: false
}

/**
 * 分享多张图片
 * @receiver Activity
 * @param engine String?
 * @param params Share ( Data、Params ) Item
 * @param listener 分享回调
 * @return `true` success, `false` fail
 */
fun <Item : IShareEngine.EngineItem> Activity.share_shareImageList(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return engine.getShareEngine()?.let {
        return (it as? IShareEngine<*, Item>)?.shareImageList(
            this, params, listener
        ) ?: false
    } ?: false
}

/**
 * 分享文本
 * @receiver Activity
 * @param engine String?
 * @param params Share ( Data、Params ) Item
 * @param listener 分享回调
 * @return `true` success, `false` fail
 */
fun <Item : IShareEngine.EngineItem> Activity.share_shareText(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return engine.getShareEngine()?.let {
        return (it as? IShareEngine<*, Item>)?.shareText(
            this, params, listener
        ) ?: false
    } ?: false
}

/**
 * 分享视频
 * @receiver Activity
 * @param engine String?
 * @param params Share ( Data、Params ) Item
 * @param listener 分享回调
 * @return `true` success, `false` fail
 */
fun <Item : IShareEngine.EngineItem> Activity.share_shareVideo(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return engine.getShareEngine()?.let {
        return (it as? IShareEngine<*, Item>)?.shareVideo(
            this, params, listener
        ) ?: false
    } ?: false
}

/**
 * 分享音乐
 * @receiver Activity
 * @param engine String?
 * @param params Share ( Data、Params ) Item
 * @param listener 分享回调
 * @return `true` success, `false` fail
 */
fun <Item : IShareEngine.EngineItem> Activity.share_shareMusic(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return engine.getShareEngine()?.let {
        return (it as? IShareEngine<*, Item>)?.shareMusic(
            this, params, listener
        ) ?: false
    } ?: false
}

/**
 * 分享表情
 * @receiver Activity
 * @param engine String?
 * @param params Share ( Data、Params ) Item
 * @param listener 分享回调
 * @return `true` success, `false` fail
 */
fun <Item : IShareEngine.EngineItem> Activity.share_shareEmoji(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return engine.getShareEngine()?.let {
        return (it as? IShareEngine<*, Item>)?.shareEmoji(
            this, params, listener
        ) ?: false
    } ?: false
}

/**
 * 分享文件
 * @receiver Activity
 * @param engine String?
 * @param params Share ( Data、Params ) Item
 * @param listener 分享回调
 * @return `true` success, `false` fail
 */
fun <Item : IShareEngine.EngineItem> Activity.share_shareFile(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return engine.getShareEngine()?.let {
        return (it as? IShareEngine<*, Item>)?.shareFile(
            this, params, listener
        ) ?: false
    } ?: false
}

/**
 * 分享操作 ( 通用扩展 )
 * @receiver Activity
 * @param engine String?
 * @param params Share ( Data、Params ) Item
 * @param listener 分享回调
 * @return `true` success, `false` fail
 */
fun <Item : IShareEngine.EngineItem> Activity.share_share(
    engine: String? = null,
    params: Item?,
    listener: ShareListener<Item>?
): Boolean {
    return engine.getShareEngine()?.let {
        return (it as? IShareEngine<*, Item>)?.share(
            this, params, listener
        ) ?: false
    } ?: false
}

// =

/**
 * 部分平台 Activity onActivityResult 额外调用处理
 * @receiver Context
 * @param engine String?
 * @param requestCode 请求 code
 * @param resultCode resultCode
 * @param intent Intent
 */
fun Context.share_onActivityResult(
    engine: String? = null,
    requestCode: Int,
    resultCode: Int,
    intent: Intent?
) {
    engine.getShareEngine()?.onActivityResult(this, requestCode, resultCode, intent)
}