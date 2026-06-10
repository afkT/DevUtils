package dev.engine.extensions.media

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import dev.engine.DevEngine
import dev.engine.media.IMediaEngine

// ==========================================
// = IMediaEngine<EngineConfig, EngineData> =
// ==========================================

/**
 * 通过 Key 获取 Media Engine
 * @receiver String?
 * @return IMediaEngine<EngineConfig, EngineData>
 * 内部做了处理如果匹配不到则返回默认 Media Engine
 */
fun String?.getMediaEngine(): IMediaEngine<in IMediaEngine.EngineConfig, in IMediaEngine.EngineData>? {
    DevEngine.getMedia(this)?.let { value ->
        return value
    }
    return DevEngine.getMedia()
}

// ====================
// = Key Media Engine =
// ====================

// ==========
// = 配置方法 =
// ==========

/**
 * 获取全局配置
 * @param engine String?
 * @return 全局配置信息
 */
fun media_getConfig(
    engine: String? = null
): Any? {
    return engine.getMediaEngine()?.config
}

/**
 * 设置全局配置
 * @param engine String?
 */
fun <Config : IMediaEngine.EngineConfig> Config?.media_setConfig(
    engine: String? = null
) {
    engine.getMediaEngine()?.config = this
}

// =============
// = 对外公开方法 =
// =============

/**
 * 打开相册拍照
 * @receiver Activity
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun Activity.media_openCamera(
    engine: String? = null
): Boolean {
    return engine.getMediaEngine()?.openCamera(this) ?: false
}

/**
 * 打开相册拍照
 * @receiver Activity
 * @param engine String?
 * @param config 配置信息
 * @return `true` success, `false` fail
 */
fun <Config : IMediaEngine.EngineConfig> Activity.media_openCamera(
    engine: String? = null,
    config: Config?
): Boolean {
    return engine.getMediaEngine()?.openCamera(this, config) ?: false
}

/**
 * 打开相册拍照
 * @receiver Fragment
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun Fragment.media_openCamera(
    engine: String? = null
): Boolean {
    return engine.getMediaEngine()?.openCamera(this) ?: false
}

/**
 * 打开相册拍照
 * @receiver Fragment
 * @param engine String?
 * @param config 配置信息
 * @return `true` success, `false` fail
 */
fun <Config : IMediaEngine.EngineConfig> Fragment.media_openCamera(
    engine: String? = null,
    config: Config?
): Boolean {
    return engine.getMediaEngine()?.openCamera(this, config) ?: false
}

// =

/**
 * 打开相册选择
 * @receiver Activity
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun Activity.media_openGallery(
    engine: String? = null
): Boolean {
    return engine.getMediaEngine()?.openGallery(this) ?: false
}

/**
 * 打开相册选择
 * @receiver Activity
 * @param engine String?
 * @param config 配置信息
 * @return `true` success, `false` fail
 */
fun <Config : IMediaEngine.EngineConfig> Activity.media_openGallery(
    engine: String? = null,
    config: Config?
): Boolean {
    return engine.getMediaEngine()?.openGallery(this, config) ?: false
}

/**
 * 打开相册选择
 * @receiver Fragment
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun Fragment.media_openGallery(
    engine: String? = null
): Boolean {
    return engine.getMediaEngine()?.openGallery(this) ?: false
}

/**
 * 打开相册选择
 * @receiver Fragment
 * @param engine String?
 * @param config 配置信息
 * @return `true` success, `false` fail
 */
fun <Config : IMediaEngine.EngineConfig> Fragment.media_openGallery(
    engine: String? = null,
    config: Config?
): Boolean {
    return engine.getMediaEngine()?.openGallery(this, config) ?: false
}

// =

/**
 * 打开相册预览
 * @receiver Activity
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun Activity.media_openPreview(
    engine: String? = null
): Boolean {
    return engine.getMediaEngine()?.openPreview(this) ?: false
}

/**
 * 打开相册预览
 * @receiver Activity
 * @param engine String?
 * @param config 配置信息
 * @return `true` success, `false` fail
 */
fun <Config : IMediaEngine.EngineConfig> Activity.media_openPreview(
    engine: String? = null,
    config: Config?
): Boolean {
    return engine.getMediaEngine()?.openPreview(this, config) ?: false
}

/**
 * 打开相册预览
 * @receiver Fragment
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun Fragment.media_openPreview(
    engine: String? = null
): Boolean {
    return engine.getMediaEngine()?.openPreview(this) ?: false
}

/**
 * 打开相册预览
 * @receiver Fragment
 * @param engine String?
 * @param config 配置信息
 * @return `true` success, `false` fail
 */
fun <Config : IMediaEngine.EngineConfig> Fragment.media_openPreview(
    engine: String? = null,
    config: Config?
): Boolean {
    return engine.getMediaEngine()?.openPreview(this, config) ?: false
}

// ==========
// = 其他方法 =
// ==========

/**
 * 删除缓存文件
 * @receiver Context
 * @param engine String?
 * @param type 类型 ( 图片、视频 )
 */
fun Context.media_deleteCacheDirFile(
    engine: String? = null,
    type: Int
) {
    engine.getMediaEngine()?.deleteCacheDirFile(this, type)
}

/**
 * 删除全部缓存文件
 * @receiver Context
 * @param engine String?
 */
fun Context.media_deleteAllCacheDirFile(
    engine: String? = null
) {
    engine.getMediaEngine()?.deleteAllCacheDirFile(this)
}

/**
 * 是否图片选择 ( onActivityResult )
 * @param engine String?
 * @param requestCode 请求 code
 * @param resultCode resultCode
 * @return `true` success, `false` fail
 */
fun media_isMediaSelectorResult(
    engine: String? = null,
    requestCode: Int,
    resultCode: Int
): Boolean {
    return engine.getMediaEngine()?.isMediaSelectorResult(requestCode, resultCode) ?: false
}

// =

/**
 * 获取 Media Selector Data List
 * @receiver onActivityResult Intent data
 * @param engine String?
 * @return Media Selector Data List
 */
fun <Data : IMediaEngine.EngineData> Intent.media_getSelectors(
    engine: String? = null
): List<Data?>? {
    return engine.getMediaEngine()?.getSelectors(this) as? List<Data?>
}

/**
 * 获取 Media Selector Uri List
 * @receiver onActivityResult Intent data
 * @param engine String?
 * @param original 是否使用原图
 * @return Media Selector Uri List
 */
fun Intent.media_getSelectorUris(
    engine: String? = null,
    original: Boolean
): List<Uri?>? {
    return engine.getMediaEngine()?.getSelectorUris(this, original)
}

/**
 * 获取 Single Media Selector Data
 * @receiver onActivityResult Intent data
 * @param engine String?
 * @return Single Media Selector Data
 */
fun Intent.media_getSingleSelector(
    engine: String? = null
): Any? {
    return engine.getMediaEngine()?.getSingleSelector(this)
}

/**
 * 获取 Single Media Selector Uri
 * @receiver onActivityResult Intent data
 * @param engine String?
 * @param original 是否使用原图
 * @return Single Media Selector Uri
 */
fun Intent.media_getSingleSelectorUri(
    engine: String? = null,
    original: Boolean
): Uri? {
    return engine.getMediaEngine()?.getSingleSelectorUri(this, original)
}