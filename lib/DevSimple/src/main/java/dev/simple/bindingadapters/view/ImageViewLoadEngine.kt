package dev.simple.bindingadapters.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import dev.base.DevSource
import dev.engine.core.image.ImageConfig
import dev.engine.extensions.image.display
import dev.engine.image.IImageEngine
import dev.engine.image.listener.LoadListener
import dev.simple.extensions.hi.hiif.hiIfNotNull
import dev.simple.extensions.hi.hiif.hiIfNotNullWith
import dev.simple.extensions.toSource
import dev.simple.extensions.toSourceByPath
import dev.utils.LogPrintUtils
import dev.utils.common.StringUtils
import java.io.File
import java.io.InputStream

// ========================================
// = ImageView Load Engine BindingAdapter =
// ========================================

/**
 * [ImageView] 经图片引擎（[IImageEngine]）加载的 Data Binding 适配集合。
 *
 * 布局属性前缀为 `binding_image_*`（如 `binding_image_url`、`binding_image_engine`、
 * `binding_image_config`、`binding_image_no_cache`、`binding_image_listener`），内部经 [display] 走引擎加载。
 * <pre>
 *     各数据源入口（url / file / bitmap / uri 等）最终汇聚至 [bindingImageSource]。
 *     若仅需单参调用 [ImageView.setImageBitmap]、[ImageView.setImageResource] 等原生 API，
 *     请使用 `ImageViewLoadNative.kt` 中 `binding_image_native_*`（与本文件属性名互不重复）。
 * </pre>
 */

private const val TAG = "Dev_ImageView_Load_Engine_BindingAdapter"

// ==========
// = 加载配置 =
// ==========

// 不缓存图片加载配置
private val NOCACHE_CONFIG = ImageConfig.create()
    .setCacheDisk(false)
    .setCacheMemory(false)
    .setDontAnimate(true)

@Suppress("UNCHECKED_CAST")
private fun <Config : IImageEngine.EngineConfig> resolveBindingImageConfig(
    noCache: Boolean?,
    config: Config?,
): Config? = if (noCache == true) {
    NOCACHE_CONFIG as Config
} else {
    config
}

// =============
// = URL 与文件 =
// =============

/**
 * 通过数据绑定按 URL 加载图片到当前 ImageView
 * <pre>
 *     布局属性：binding_image_url、binding_image_engine、binding_image_config、binding_image_no_cache、binding_image_listener，requireAll 为 false。
 *     仅在 url 非空时发起加载；listener 为空时仍加载但不回调监听。
 *     binding_image_no_cache 为 true 时使用模块内不缓存配置，为 false 或未设置时使用 binding_image_config。
 * </pre>
 *
 * @param url 图片地址，为空或空串时不加载
 * @param engine 图片引擎标识，可为空以走默认引擎
 * @param config 图片加载配置，可为空以走引擎默认配置
 * @param noCache 为 true 时不使用 config 的缓存策略，改为不缓存加载
 * @param listener 加载过程监听，可为空
 */
@BindingAdapter(
    value = ["binding_image_url", "binding_image_engine", "binding_image_config", "binding_image_no_cache", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageUrl(
    url: String?,
    engine: String?,
    config: Config?,
    noCache: Boolean?,
    listener: LoadListener<Bitmap>?
) {
    if (StringUtils.isNotEmpty(url)) {
        url?.toSource().hiIfNotNull { source ->
            bindingImageSource(source, engine, config, noCache, listener)
        }
    }
}

/**
 * 通过数据绑定按 File 加载图片到当前 ImageView
 * <pre>
 *     布局属性：binding_image_file、binding_image_engine、binding_image_config、binding_image_no_cache、binding_image_listener，requireAll 为 false。
 *     binding_image_no_cache 为 true 时使用模块内不缓存配置，为 false 或未设置时使用 binding_image_config。
 * </pre>
 *
 * @param file 图片文件，可为空
 * @param engine 图片引擎标识，可为空以走默认引擎
 * @param config 图片加载配置，可为空以走引擎默认配置
 * @param noCache 为 true 时不使用 config 的缓存策略，改为不缓存加载
 * @param listener 加载过程监听，可为空
 */
@BindingAdapter(
    value = ["binding_image_file", "binding_image_engine", "binding_image_config", "binding_image_no_cache", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageFile(
    file: File?,
    engine: String?,
    config: Config?,
    noCache: Boolean?,
    listener: LoadListener<Bitmap>?
) {
    file?.toSource().hiIfNotNull { source ->
        bindingImageSource(source, engine, config, noCache, listener)
    }
}

/**
 * 通过数据绑定按文件路径加载图片到当前 ImageView
 * <pre>
 *     布局属性：binding_image_file_path、binding_image_engine、binding_image_config、binding_image_no_cache、binding_image_listener，requireAll 为 false。
 *     仅在 filePath 非空时发起加载。
 *     binding_image_no_cache 为 true 时使用模块内不缓存配置，为 false 或未设置时使用 binding_image_config。
 * </pre>
 *
 * @param filePath 图片文件路径，为空或空串时不加载
 * @param engine 图片引擎标识，可为空以走默认引擎
 * @param config 图片加载配置，可为空以走引擎默认配置
 * @param noCache 为 true 时不使用 config 的缓存策略，改为不缓存加载
 * @param listener 加载过程监听，可为空
 */
@BindingAdapter(
    value = ["binding_image_file_path", "binding_image_engine", "binding_image_config", "binding_image_no_cache", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageFilePath(
    filePath: String?,
    engine: String?,
    config: Config?,
    noCache: Boolean?,
    listener: LoadListener<Bitmap>?
) {
    if (StringUtils.isNotEmpty(filePath)) {
        filePath?.toSourceByPath().hiIfNotNull { source ->
            bindingImageSource(source, engine, config, noCache, listener)
        }
    }
}

// =====================
// = Bitmap 与 Drawable =
// =====================

/**
 * 通过数据绑定按 Bitmap 加载图片到当前 ImageView
 * <pre>
 *     布局属性：binding_image_bitmap、binding_image_engine、binding_image_config、binding_image_no_cache、binding_image_listener，requireAll 为 false。
 *     binding_image_no_cache 为 true 时使用模块内不缓存配置，为 false 或未设置时使用 binding_image_config。
 * </pre>
 *
 * @param bitmap 图片位图，可为空
 * @param engine 图片引擎标识，可为空以走默认引擎
 * @param config 图片加载配置，可为空以走引擎默认配置
 * @param noCache 为 true 时不使用 config 的缓存策略，改为不缓存加载
 * @param listener 加载过程监听，可为空
 */
@BindingAdapter(
    value = ["binding_image_bitmap", "binding_image_engine", "binding_image_config", "binding_image_no_cache", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageBitmap(
    bitmap: Bitmap?,
    engine: String?,
    config: Config?,
    noCache: Boolean?,
    listener: LoadListener<Bitmap>?
) {
    bitmap?.toSource().hiIfNotNull { source ->
        bindingImageSource(source, engine, config, noCache, listener)
    }
}

/**
 * 通过数据绑定按 Drawable 加载图片到当前 ImageView
 * <pre>
 *     布局属性：binding_image_drawable、binding_image_engine、binding_image_config、binding_image_no_cache、binding_image_listener，requireAll 为 false。
 *     binding_image_no_cache 为 true 时使用模块内不缓存配置，为 false 或未设置时使用 binding_image_config。
 * </pre>
 *
 * @param drawable 图片 Drawable，可为空
 * @param engine 图片引擎标识，可为空以走默认引擎
 * @param config 图片加载配置，可为空以走引擎默认配置
 * @param noCache 为 true 时不使用 config 的缓存策略，改为不缓存加载
 * @param listener 加载过程监听，可为空
 */
@BindingAdapter(
    value = ["binding_image_drawable", "binding_image_engine", "binding_image_config", "binding_image_no_cache", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageDrawable(
    drawable: Drawable?,
    engine: String?,
    config: Config?,
    noCache: Boolean?,
    listener: LoadListener<Bitmap>?
) {
    drawable?.toSource().hiIfNotNull { source ->
        bindingImageSource(source, engine, config, noCache, listener)
    }
}

// ==============
// = Uri 与二进制 =
// ==============

/**
 * 通过数据绑定按 Uri 加载图片到当前 ImageView
 * <pre>
 *     布局属性：binding_image_uri、binding_image_engine、binding_image_config、binding_image_no_cache、binding_image_listener，requireAll 为 false。
 *     binding_image_no_cache 为 true 时使用模块内不缓存配置，为 false 或未设置时使用 binding_image_config。
 * </pre>
 *
 * @param uri 图片 Uri，可为空
 * @param engine 图片引擎标识，可为空以走默认引擎
 * @param config 图片加载配置，可为空以走引擎默认配置
 * @param noCache 为 true 时不使用 config 的缓存策略，改为不缓存加载
 * @param listener 加载过程监听，可为空
 */
@BindingAdapter(
    value = ["binding_image_uri", "binding_image_engine", "binding_image_config", "binding_image_no_cache", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageUri(
    uri: Uri?,
    engine: String?,
    config: Config?,
    noCache: Boolean?,
    listener: LoadListener<Bitmap>?
) {
    uri?.toSource().hiIfNotNull { source ->
        bindingImageSource(source, engine, config, noCache, listener)
    }
}

/**
 * 通过数据绑定按 InputStream 加载图片到当前 ImageView
 * <pre>
 *     布局属性：binding_image_input_stream、binding_image_engine、binding_image_config、binding_image_no_cache、binding_image_listener，requireAll 为 false。
 *     binding_image_no_cache 为 true 时使用模块内不缓存配置，为 false 或未设置时使用 binding_image_config。
 * </pre>
 *
 * @param inputStream 图片输入流，可为空
 * @param engine 图片引擎标识，可为空以走默认引擎
 * @param config 图片加载配置，可为空以走引擎默认配置
 * @param noCache 为 true 时不使用 config 的缓存策略，改为不缓存加载
 * @param listener 加载过程监听，可为空
 */
@BindingAdapter(
    value = ["binding_image_input_stream", "binding_image_engine", "binding_image_config", "binding_image_no_cache", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageInputStream(
    inputStream: InputStream?,
    engine: String?,
    config: Config?,
    noCache: Boolean?,
    listener: LoadListener<Bitmap>?
) {
    inputStream?.toSource().hiIfNotNull { source ->
        bindingImageSource(source, engine, config, noCache, listener)
    }
}

/**
 * 通过数据绑定按 ByteArray 加载图片到当前 ImageView
 * <pre>
 *     布局属性：binding_image_byte_array、binding_image_engine、binding_image_config、binding_image_no_cache、binding_image_listener，requireAll 为 false。
 *     binding_image_no_cache 为 true 时使用模块内不缓存配置，为 false 或未设置时使用 binding_image_config。
 * </pre>
 *
 * @param byteArray 图片字节数组，可为空
 * @param engine 图片引擎标识，可为空以走默认引擎
 * @param config 图片加载配置，可为空以走引擎默认配置
 * @param noCache 为 true 时不使用 config 的缓存策略，改为不缓存加载
 * @param listener 加载过程监听，可为空
 */
@BindingAdapter(
    value = ["binding_image_byte_array", "binding_image_engine", "binding_image_config", "binding_image_no_cache", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageByteArray(
    byteArray: ByteArray?,
    engine: String?,
    config: Config?,
    noCache: Boolean?,
    listener: LoadListener<Bitmap>?
) {
    byteArray?.toSource().hiIfNotNull { source ->
        bindingImageSource(source, engine, config, noCache, listener)
    }
}

// ===================
// = 资源与 DevSource =
// ===================

/**
 * 通过数据绑定按资源 ID 加载图片到当前 ImageView
 * <pre>
 *     布局属性：binding_image_res、binding_image_engine、binding_image_config、binding_image_no_cache、binding_image_listener，requireAll 为 false。
 *     binding_image_no_cache 为 true 时使用模块内不缓存配置，为 false 或未设置时使用 binding_image_config。
 * </pre>
 *
 * @param res 图片资源 ID，可为空
 * @param engine 图片引擎标识，可为空以走默认引擎
 * @param config 图片加载配置，可为空以走引擎默认配置
 * @param noCache 为 true 时不使用 config 的缓存策略，改为不缓存加载
 * @param listener 加载过程监听，可为空
 */
@BindingAdapter(
    value = ["binding_image_res", "binding_image_engine", "binding_image_config", "binding_image_no_cache", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageRes(
    res: Int?,
    engine: String?,
    config: Config?,
    noCache: Boolean?,
    listener: LoadListener<Bitmap>?
) {
    res?.toSource().hiIfNotNull { source ->
        bindingImageSource(source, engine, config, noCache, listener)
    }
}

/**
 * 通过数据绑定按 DevSource 加载图片到当前 ImageView
 * <pre>
 *     布局属性：binding_image_source、binding_image_engine、binding_image_config、binding_image_no_cache、binding_image_listener，requireAll 为 false。
 *     binding_image_no_cache 为 true 时使用模块内不缓存配置，为 false 或未设置时使用 binding_image_config。
 * </pre>
 *
 * @param source 图片数据源
 * @param engine 图片引擎标识，可为空以走默认引擎
 * @param config 图片加载配置，可为空以走引擎默认配置
 * @param noCache 为 true 时不使用 config 的缓存策略，改为不缓存加载
 * @param listener 加载过程监听，可为空
 */
@BindingAdapter(
    value = ["binding_image_source", "binding_image_engine", "binding_image_config", "binding_image_no_cache", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageSource(
    source: DevSource?,
    engine: String?,
    config: Config?,
    noCache: Boolean?,
    listener: LoadListener<Bitmap>?
) {
    if (source == null) return
    val effectiveConfig = resolveBindingImageConfig(noCache, config)
    try {
        listener.hiIfNotNullWith({
            this.display(
                engine = engine,
                source = source,
                config = effectiveConfig,
                listener = it
            )
        }, {
            this.display(
                engine = engine,
                source = source,
                config = effectiveConfig
            )
        })
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "bindingImageSource")
    }
}