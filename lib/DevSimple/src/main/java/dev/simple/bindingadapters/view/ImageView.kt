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

// ========================
// = Image BindingAdapter =
// ========================

private const val TAG = "Dev_Image_BindingAdapter"

// ==========
// = 默认配置 =
// ==========

// 不缓存图片加载配置
private val NOCACHE_CONFIG = ImageConfig.create()
    .setCacheDisk(false)
    .setCacheMemory(false)
    .setDontAnimate(true)

// ==========
// = 具体方法 =
// ==========

/**
 * 通过数据绑定按 URL 加载图片到当前 ImageView
 * <pre>
 *     布局属性：binding_image_url、binding_image_engine、binding_image_listener，requireAll 为 false。
 *     仅在 url 非空时发起加载；listener 为空时仍加载但不回调监听。
 * </pre>
 *
 * @param url 图片地址，为空或空串时不加载
 * @param engine 图片引擎标识，可为空以走默认引擎
 * @param listener 加载过程监听，可为空
 */
@BindingAdapter(
    value = ["binding_image_url", "binding_image_engine", "binding_image_config", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageUrl(
    url: String?,
    engine: String?,
    config: Config?,
    listener: LoadListener<Bitmap>?
) {
    if (StringUtils.isNotEmpty(url)) {
        url?.toSource().hiIfNotNull { source ->
            bindingImageSource(source, engine, config, listener)
        }
    }
}

@BindingAdapter(
    value = ["binding_image_file", "binding_image_engine", "binding_image_config", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageFile(
    file: File?,
    engine: String?,
    config: Config?,
    listener: LoadListener<Bitmap>?
) {
    file?.toSource().hiIfNotNull { source ->
        bindingImageSource(source, engine, config, listener)
    }
}

@BindingAdapter(
    value = ["binding_image_file_path", "binding_image_engine", "binding_image_config", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageFilePath(
    filePath: String?,
    engine: String?,
    config: Config?,
    listener: LoadListener<Bitmap>?
) {
    filePath?.toSourceByPath().hiIfNotNull { source ->
        bindingImageSource(source, engine, config, listener)
    }
}

@BindingAdapter(
    value = ["binding_image_bitmap", "binding_image_engine", "binding_image_config", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageBitmap(
    bitmap: Bitmap?,
    engine: String?,
    config: Config?,
    listener: LoadListener<Bitmap>?
) {
    bitmap?.toSource().hiIfNotNull { source ->
        bindingImageSource(source, engine, config, listener)
    }
}

@BindingAdapter(
    value = ["binding_image_drawable", "binding_image_engine", "binding_image_config", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageDrawable(
    drawable: Drawable?,
    engine: String?,
    config: Config?,
    listener: LoadListener<Bitmap>?
) {
    drawable?.toSource().hiIfNotNull { source ->
        bindingImageSource(source, engine, config, listener)
    }
}

@BindingAdapter(
    value = ["binding_image_uri", "binding_image_engine", "binding_image_config", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageUri(
    uri: Uri?,
    engine: String?,
    config: Config?,
    listener: LoadListener<Bitmap>?
) {
    uri?.toSource().hiIfNotNull { source ->
        bindingImageSource(source, engine, config, listener)
    }
}

@BindingAdapter(
    value = ["binding_image_input_stream", "binding_image_engine", "binding_image_config", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageInputStream(
    inputStream: InputStream?,
    engine: String?,
    config: Config?,
    listener: LoadListener<Bitmap>?
) {
    inputStream?.toSource().hiIfNotNull { source ->
        bindingImageSource(source, engine, config, listener)
    }
}

@BindingAdapter(
    value = ["binding_image_byte_array", "binding_image_engine", "binding_image_config", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageByteArray(
    array: ByteArray?,
    engine: String?,
    config: Config?,
    listener: LoadListener<Bitmap>?
) {
    array?.toSource().hiIfNotNull { source ->
        bindingImageSource(source, engine, config, listener)
    }
}

@BindingAdapter(
    value = ["binding_image_res", "binding_image_engine", "binding_image_config", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageRes(
    res: Int?,
    engine: String?,
    config: Config?,
    listener: LoadListener<Bitmap>?
) {
    res?.toSource().hiIfNotNull { source ->
        bindingImageSource(source, engine, config, listener)
    }
}

/**
 * 通过数据绑定按 DevSource 加载图片到当前 ImageView
 * <pre>
 *     布局属性：binding_image_source、binding_image_engine、binding_image_listener，requireAll 为 false。
 * </pre>
 *
 * @param source 图片数据源
 * @param engine 图片引擎标识，可为空以走默认引擎
 * @param listener 加载过程监听，可为空
 */
@BindingAdapter(
    value = ["binding_image_source", "binding_image_engine", "binding_image_config", "binding_image_listener"],
    requireAll = false
)
fun <Config : IImageEngine.EngineConfig> ImageView.bindingImageSource(
    source: DevSource,
    engine: String?,
    config: Config?,
    listener: LoadListener<Bitmap>?
) {
    try {
        listener.hiIfNotNullWith({
            this.display(
                engine = engine,
                source = source,
                config = config,
                listener = it
            )
        }, {
            this.display(
                engine = engine,
                source = source,
                config = config
            )
        })
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "bindingImageSource")
    }
}