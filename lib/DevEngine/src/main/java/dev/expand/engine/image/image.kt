package dev.expand.engine.image

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
 * @param engine String?
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

fun Fragment.pause(
    engine: String? = null
) {
    engine.getImageEngine()?.pause(this)
}

fun Fragment.resume(
    engine: String? = null
) {
    engine.getImageEngine()?.resume(this)
}

fun Context.pause(
    engine: String? = null
) {
    engine.getImageEngine()?.pause(this)
}

fun Context.resume(
    engine: String? = null
) {
    engine.getImageEngine()?.resume(this)
}

// ===========
// = preload =
// ===========

fun Context.preload(
    engine: String? = null,
    source: DevSource?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.preload(this, source)
}

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

fun View.clear(
    engine: String? = null
) {
    engine.getImageEngine()?.clear(this)
}

fun Fragment.clear(
    engine: String? = null,
    view: View?
) {
    engine.getImageEngine()?.clear(this, view)
}

fun Context.clearDiskCache(
    engine: String? = null
) {
    engine.getImageEngine()?.clearDiskCache(this)
}

fun Context.clearMemoryCache(
    engine: String? = null
) {
    engine.getImageEngine()?.clearMemoryCache(this)
}

fun Context.clearAllCache(
    engine: String? = null
) {
    engine.getImageEngine()?.clearAllCache(this)
}

// =========
// = other =
// =========

fun Context.lowMemory(
    engine: String? = null
) {
    engine.getImageEngine()?.lowMemory(this)
}

// ===========
// = display =
// ===========

fun ImageView.display(
    engine: String? = null,
    source: DevSource?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.display(this, source)
}

fun <Config : IImageEngine.EngineConfig> ImageView.display(
    engine: String? = null,
    source: DevSource?,
    config: Config?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.display(this, source, config)
}

fun <T : Any> ImageView.display(
    engine: String? = null,
    source: DevSource?,
    listener: LoadListener<T>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.display(this, source, listener)
}

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

fun ImageView.display(
    engine: String? = null,
    fragment: Fragment?,
    source: DevSource?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.display(fragment, this, source)
}

fun <Config : IImageEngine.EngineConfig> ImageView.display(
    engine: String? = null,
    fragment: Fragment?,
    source: DevSource?,
    config: Config?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.display(fragment, this, source, config)
}

fun <T : Any> ImageView.display(
    engine: String? = null,
    fragment: Fragment?,
    source: DevSource?,
    listener: LoadListener<T>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.display(fragment, this, source, listener)
}

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

fun <T : Any, Config : IImageEngine.EngineConfig> Context.loadImage(
    engine: String? = null,
    source: DevSource?,
    config: Config?,
    listener: LoadListener<T>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.loadImage(this, source, config, listener)
}

fun <T : Any, Config : IImageEngine.EngineConfig> Fragment.loadImage(
    engine: String? = null,
    source: DevSource?,
    config: Config?,
    listener: LoadListener<T>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.loadImage(this, source, config, listener)
}

fun <T : Any, Config : IImageEngine.EngineConfig> Context.loadImage(
    engine: String? = null,
    source: DevSource?,
    config: Config?,
    type: Class<*>?
): T? {
    if (source.requireSource()) return null
    return engine.getImageEngine()?.loadImage(this, source, config, type)
}

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

fun <Config : IImageEngine.EngineConfig> Context.loadBitmap(
    engine: String? = null,
    source: DevSource?,
    config: Config?,
    listener: LoadListener<Bitmap>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.loadBitmap(this, source, config, listener)
}

fun <Config : IImageEngine.EngineConfig> Fragment.loadBitmap(
    engine: String? = null,
    source: DevSource?,
    config: Config?,
    listener: LoadListener<Bitmap>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.loadBitmap(this, source, config, listener)
}

fun <Config : IImageEngine.EngineConfig> Context.loadBitmap(
    engine: String? = null,
    source: DevSource?,
    config: Config?
): Bitmap? {
    if (source.requireSource()) return null
    return engine.getImageEngine()?.loadBitmap(this, source, config)
}

fun <Config : IImageEngine.EngineConfig> Context.loadBitmapThrows(
    engine: String? = null,
    source: DevSource?,
    config: Config?
): Bitmap? {
    if (source.requireSource()) return null
    return engine.getImageEngine()?.loadBitmapThrows(this, source, config)
}

// =

fun <Config : IImageEngine.EngineConfig> Context.loadDrawable(
    engine: String? = null,
    source: DevSource?,
    config: Config?,
    listener: LoadListener<Drawable>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.loadDrawable(this, source, config, listener)
}

fun <Config : IImageEngine.EngineConfig> Fragment.loadDrawable(
    engine: String? = null,
    source: DevSource?,
    config: Config?,
    listener: LoadListener<Drawable>?
) {
    if (source.requireSource()) return
    engine.getImageEngine()?.loadDrawable(this, source, config, listener)
}

fun <Config : IImageEngine.EngineConfig> Context.loadDrawable(
    engine: String? = null,
    source: DevSource?,
    config: Config?
): Drawable? {
    if (source.requireSource()) return null
    return engine.getImageEngine()?.loadDrawable(this, source, config)
}

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

fun Context.convertImageFormat(
    engine: String? = null,
    sources: MutableList<DevSource>?,
    listener: OnConvertListener?
): Boolean {
    return engine.getImageEngine()?.convertImageFormat(this, sources, listener) ?: false
}

fun <Config : IImageEngine.EngineConfig> Context.convertImageFormat(
    engine: String? = null,
    sources: MutableList<DevSource>?,
    config: Config?,
    listener: OnConvertListener?
): Boolean {
    return engine.getImageEngine()?.convertImageFormat(this, sources, config, listener) ?: false
}