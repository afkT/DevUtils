package dev.engine.extensions.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import dev.base.DevSource
import dev.engine.DevEngine
import dev.engine.image.IImageEngine
import dev.engine.image.listener.LoadListener
import dev.engine.image.listener.OnConvertListener

// ==============================
// = IImageEngine<EngineConfig> =
// ==============================

/**
 * 通过 Key 获取 Image Engine
 * @receiver String?
 * @return IImageEngine<EngineConfig>
 * 内部做了处理如果匹配不到则返回默认 Image Engine
 */
fun String?.getImageEngine(): IImageEngine<in IImageEngine.EngineConfig>? {
    DevEngine.getImage(this)?.let { value ->
        return value
    }
    return DevEngine.getImage()
}

// ==========
// = 快捷方法 =
// ==========

/**
 * 请求校验 Source
 * @return `true` 直接 return, `false` 表示可以接着操作
 */
fun DevSource?.requireSource(): Boolean {
    if (this?.isSource == true) {
        return false
    }
    return true
}

// ====================
// = Key Image Engine =
// ====================

// ====================
// = pause and resume =
// ====================

/**
 * 暂停关联的图片加载请求
 * @receiver [Fragment]
 * @param engine String?
 */
fun Fragment.pause(
    engine: String? = null
) {
    engine.getImageEngine()?.pause(this)
}

/**
 * 恢复关联的图片加载请求
 * @receiver [Fragment]
 * @param engine String?
 */
fun Fragment.resume(
    engine: String? = null
) {
    engine.getImageEngine()?.resume(this)
}

/**
 * 暂停关联的图片加载请求
 * @receiver [Context]
 * @param engine String?
 */
fun Context.pause(
    engine: String? = null
) {
    engine.getImageEngine()?.pause(this)
}

/**
 * 恢复关联的图片加载请求
 * @receiver [Context]
 * @param engine String?
 */
fun Context.resume(
    engine: String? = null
) {
    engine.getImageEngine()?.resume(this)
}

// ===========
// = preload =
// ===========

/**
 * 预加载图片资源
 * @receiver [Context]
 * @param engine String?
 * @param source 数据来源
 */
fun Context.preload(
    engine: String? = null,
    source: DevSource?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.preload(this, source)
}

/**
 * 预加载图片资源
 * @receiver [Context]
 * @param engine String?
 * @param source 数据来源
 * @param config 配置信息
 */
fun <Config : IImageEngine.EngineConfig> Context.preload(
    engine: String? = null,
    source: DevSource?,
    config: Config?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.preload(this, source, config)
}

// =========
// = clear =
// =========

/**
 * 清除关联的图片加载
 * @receiver [View]
 * @param engine String?
 */
fun View.clear(
    engine: String? = null
) {
    engine.getImageEngine()?.clear(this)
}

/**
 * 清除关联的图片加载
 * @receiver [Fragment]
 * @param engine String?
 * @param view [View]
 */
fun Fragment.clear(
    engine: String? = null,
    view: View?
) {
    engine.getImageEngine()?.clear(this, view)
}

/**
 * 清除磁盘缓存
 * @receiver [Context]
 * @param engine String?
 */
fun Context.clearDiskCache(
    engine: String? = null
) {
    engine.getImageEngine()?.clearDiskCache(this)
}

/**
 * 清除内存缓存
 * @receiver [Context]
 * @param engine String?
 */
fun Context.clearMemoryCache(
    engine: String? = null
) {
    engine.getImageEngine()?.clearMemoryCache(this)
}

/**
 * 清除全部缓存
 * @receiver [Context]
 * @param engine String?
 */
fun Context.clearAllCache(
    engine: String? = null
) {
    engine.getImageEngine()?.clearAllCache(this)
}

// =========
// = other =
// =========

/**
 * 低内存回调处理
 * @receiver [Context]
 * @param engine String?
 */
fun Context.lowMemory(
    engine: String? = null
) {
    engine.getImageEngine()?.lowMemory(this)
}

// ===========
// = display =
// ===========

/**
 * 加载图片并显示到 ImageView
 * @receiver [ImageView]
 * @param engine String?
 * @param url 图片地址
 */
fun ImageView.display(
    engine: String? = null,
    url: String?
) {
    engine.getImageEngine()?.display(this, url)
}

/**
 * 加载图片并显示到 ImageView
 * @receiver [ImageView]
 * @param engine String?
 * @param source 数据来源
 */
fun ImageView.display(
    engine: String? = null,
    source: DevSource?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.display(this, source)
}

/**
 * 加载图片并显示到 ImageView
 * @param engine String?
 * @param url 图片地址
 * @param config 配置信息
 */
fun <Config : IImageEngine.EngineConfig> ImageView.display(
    engine: String? = null,
    url: String?,
    config: Config?
) {
    engine.getImageEngine()?.display(this, url, config)
}

/**
 * 加载图片并显示到 ImageView
 * @param engine String?
 * @param source 数据来源
 * @param config 配置信息
 */
fun <Config : IImageEngine.EngineConfig> ImageView.display(
    engine: String? = null,
    source: DevSource?,
    config: Config?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.display(this, source, config)
}

/**
 * 加载图片并显示到 ImageView
 * @param engine String?
 * @param url 图片地址
 * @param listener 加载监听
 */
fun <T : Any> ImageView.display(
    engine: String? = null,
    url: String?,
    listener: LoadListener<T>?
) {
    engine.getImageEngine()?.display(this, url, listener)
}

/**
 * 加载图片并显示到 ImageView
 * @param engine String?
 * @param source 数据来源
 * @param listener 加载监听
 */
fun <T : Any> ImageView.display(
    engine: String? = null,
    source: DevSource?,
    listener: LoadListener<T>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.display(this, source, listener)
}

/**
 * 加载图片并显示到 ImageView
 * @param engine String?
 * @param url 图片地址
 * @param config 配置信息
 * @param listener 加载监听
 */
fun <T : Any, Config : IImageEngine.EngineConfig> ImageView.display(
    engine: String? = null,
    url: String?,
    config: Config?,
    listener: LoadListener<T>?
) {
    engine.getImageEngine()?.display(this, url, config, listener)
}

/**
 * 加载图片并显示到 ImageView
 * @param engine String?
 * @param source 数据来源
 * @param config 配置信息
 * @param listener 加载监听
 */
fun <T : Any, Config : IImageEngine.EngineConfig> ImageView.display(
    engine: String? = null,
    source: DevSource?,
    config: Config?,
    listener: LoadListener<T>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.display(this, source, config, listener)
}

// =

/**
 * 加载图片并显示到 ImageView
 * @receiver [ImageView]
 * @param engine String?
 * @param fragment [Fragment]
 * @param url 图片地址
 */
fun ImageView.display(
    engine: String? = null,
    fragment: Fragment?,
    url: String?
) {
    engine.getImageEngine()?.display(fragment, this, url)
}

/**
 * 加载图片并显示到 ImageView
 * @receiver [ImageView]
 * @param engine String?
 * @param fragment [Fragment]
 * @param source 数据来源
 */
fun ImageView.display(
    engine: String? = null,
    fragment: Fragment?,
    source: DevSource?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.display(fragment, this, source)
}

/**
 * 加载图片并显示到 ImageView
 * @param engine String?
 * @param fragment [Fragment]
 * @param url 图片地址
 * @param config 配置信息
 */
fun <Config : IImageEngine.EngineConfig> ImageView.display(
    engine: String? = null,
    fragment: Fragment?,
    url: String?,
    config: Config?
) {
    engine.getImageEngine()?.display(fragment, this, url, config)
}

/**
 * 加载图片并显示到 ImageView
 * @param engine String?
 * @param fragment [Fragment]
 * @param source 数据来源
 * @param config 配置信息
 */
fun <Config : IImageEngine.EngineConfig> ImageView.display(
    engine: String? = null,
    fragment: Fragment?,
    source: DevSource?,
    config: Config?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.display(fragment, this, source, config)
}

/**
 * 加载图片并显示到 ImageView
 * @param engine String?
 * @param fragment [Fragment]
 * @param url 图片地址
 * @param listener 加载监听
 */
fun <T : Any> ImageView.display(
    engine: String? = null,
    fragment: Fragment?,
    url: String?,
    listener: LoadListener<T>?
) {
    engine.getImageEngine()?.display(fragment, this, url, listener)
}

/**
 * 加载图片并显示到 ImageView
 * @param engine String?
 * @param fragment [Fragment]
 * @param source 数据来源
 * @param listener 加载监听
 */
fun <T : Any> ImageView.display(
    engine: String? = null,
    fragment: Fragment?,
    source: DevSource?,
    listener: LoadListener<T>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.display(fragment, this, source, listener)
}

/**
 * 加载图片并显示到 ImageView
 * @param engine String?
 * @param fragment [Fragment]
 * @param url 图片地址
 * @param config 配置信息
 * @param listener 加载监听
 */
fun <T : Any, Config : IImageEngine.EngineConfig> ImageView.display(
    engine: String? = null,
    fragment: Fragment?,
    url: String?,
    config: Config?,
    listener: LoadListener<T>?
) {
    engine.getImageEngine()?.display(fragment, this, url, config, listener)
}

/**
 * 加载图片并显示到 ImageView
 * @param engine String?
 * @param fragment [Fragment]
 * @param source 数据来源
 * @param config 配置信息
 * @param listener 加载监听
 */
fun <T : Any, Config : IImageEngine.EngineConfig> ImageView.display(
    engine: String? = null,
    fragment: Fragment?,
    source: DevSource?,
    config: Config?,
    listener: LoadListener<T>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.display(fragment, this, source, config, listener)
}

// ========
// = load =
// ========

/**
 * 加载图片
 * @param engine String?
 * @param source 数据来源
 * @param config 配置信息
 * @param listener 加载监听
 */
fun <T : Any, Config : IImageEngine.EngineConfig> Context.loadImage(
    engine: String? = null,
    source: DevSource?,
    config: Config?,
    listener: LoadListener<T>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.loadImage(this, source, config, listener)
}

/**
 * 加载图片
 * @param engine String?
 * @param source 数据来源
 * @param config 配置信息
 * @param listener 加载监听
 */
fun <T : Any, Config : IImageEngine.EngineConfig> Fragment.loadImage(
    engine: String? = null,
    source: DevSource?,
    config: Config?,
    listener: LoadListener<T>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.loadImage(this, source, config, listener)
}

/**
 * 加载图片
 * @param engine String?
 * @param source 数据来源
 * @param config 配置信息
 * @param type 目标类型
 * @return [T] instance of type
 */
fun <T : Any, Config : IImageEngine.EngineConfig> Context.loadImage(
    engine: String? = null,
    source: DevSource?,
    config: Config?,
    type: Class<*>?
): T? {
    if (source.requireSource()) return null
    return engine.getImageEngine()?.loadImage(this, source, config, type)
}

/**
 * 加载图片
 * @param engine String?
 * @param source 数据来源
 * @param config 配置信息
 * @param type 目标类型
 */
fun <T : Any, Config : IImageEngine.EngineConfig> Context.loadImageThrows(
    engine: String? = null,
    source: DevSource?,
    config: Config?,
    type: Class<*>?
): T? {
    if (source.requireSource()) return null
    return engine.getImageEngine()?.loadImageThrows(this, source, config, type)
}

// =

/**
 * 加载 Bitmap
 * @param engine String?
 * @param source 数据来源
 * @param config 配置信息
 * @param listener 加载监听
 */
fun <Config : IImageEngine.EngineConfig> Context.loadBitmap(
    engine: String? = null,
    source: DevSource?,
    config: Config?,
    listener: LoadListener<Bitmap>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.loadBitmap(this, source, config, listener)
}

/**
 * 加载 Bitmap
 * @param engine String?
 * @param source 数据来源
 * @param config 配置信息
 * @param listener 加载监听
 */
fun <Config : IImageEngine.EngineConfig> Fragment.loadBitmap(
    engine: String? = null,
    source: DevSource?,
    config: Config?,
    listener: LoadListener<Bitmap>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.loadBitmap(this, source, config, listener)
}

/**
 * 加载 Bitmap
 * @param engine String?
 * @param source 数据来源
 * @param config 配置信息
 * @return [Bitmap]
 */
fun <Config : IImageEngine.EngineConfig> Context.loadBitmap(
    engine: String? = null,
    source: DevSource?,
    config: Config?
): Bitmap? {
    if (source.requireSource()) return null
    return engine.getImageEngine()?.loadBitmap(this, source, config)
}

/**
 * 加载 Bitmap
 * @param engine String?
 * @param source 数据来源
 * @param config 配置信息
 * @return [Bitmap]
 */
fun <Config : IImageEngine.EngineConfig> Context.loadBitmapThrows(
    engine: String? = null,
    source: DevSource?,
    config: Config?
): Bitmap? {
    if (source.requireSource()) return null
    return engine.getImageEngine()?.loadBitmapThrows(this, source, config)
}

// =

/**
 * 加载 Drawable
 * @param engine String?
 * @param source 数据来源
 * @param config 配置信息
 * @param listener 加载监听
 */
fun <Config : IImageEngine.EngineConfig> Context.loadDrawable(
    engine: String? = null,
    source: DevSource?,
    config: Config?,
    listener: LoadListener<Drawable>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.loadDrawable(this, source, config, listener)
}

/**
 * 加载 Drawable
 * @param engine String?
 * @param source 数据来源
 * @param config 配置信息
 * @param listener 加载监听
 */
fun <Config : IImageEngine.EngineConfig> Fragment.loadDrawable(
    engine: String? = null,
    source: DevSource?,
    config: Config?,
    listener: LoadListener<Drawable>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.loadDrawable(this, source, config, listener)
}

/**
 * 加载 Drawable
 * @param engine String?
 * @param source 数据来源
 * @param config 配置信息
 * @return [Drawable]
 */
fun <Config : IImageEngine.EngineConfig> Context.loadDrawable(
    engine: String? = null,
    source: DevSource?,
    config: Config?
): Drawable? {
    if (source.requireSource()) return null
    return engine.getImageEngine()?.loadDrawable(this, source, config)
}

/**
 * 加载 Drawable
 * @param engine String?
 * @param source 数据来源
 * @param config 配置信息
 * @return [Drawable]
 */
fun <Config : IImageEngine.EngineConfig> Context.loadDrawableThrows(
    engine: String? = null,
    source: DevSource?,
    config: Config?
): Drawable? {
    if (source.requireSource()) return null
    return engine.getImageEngine()?.loadDrawableThrows(this, source, config)
}

// ===========
// = convert =
// ===========

/**
 * 转换图片格式
 * @receiver [Context]
 * @param engine String?
 * @param listener 加载监听
 * @param sources 数据来源列表
 * @return `true` success, `false` fail
 */
fun Context.convertImageFormat(
    engine: String? = null,
    sources: MutableList<DevSource>?,
    listener: OnConvertListener?
): Boolean {
    return engine.getImageEngine()?.convertImageFormat(this, sources, listener) ?: false
}

/**
 * 转换图片格式
 * @param engine String?
 * @param config 配置信息
 * @param listener 加载监听
 * @param sources 数据来源列表
 * @return `true` success, `false` fail
 */
fun <Config : IImageEngine.EngineConfig> Context.convertImageFormat(
    engine: String? = null,
    sources: MutableList<DevSource>?,
    config: Config?,
    listener: OnConvertListener?
): Boolean {
    return engine.getImageEngine()?.convertImageFormat(this, sources, config, listener) ?: false
}