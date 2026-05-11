package dev.simple.bindingadapters.view

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import dev.base.DevSource
import dev.engine.extensions.image.display
import dev.engine.image.listener.LoadListener
import dev.simple.extensions.hi.hiif.hiIfNotNullWith
import dev.simple.extensions.toSource
import dev.utils.LogPrintUtils
import dev.utils.common.StringUtils

// ========================
// = Image BindingAdapter =
// ========================

private const val TAG = "Image_BindingAdapter"

/**
 * 通过数据绑定按 URL 加载图片到当前 ImageView
 * <pre>
 *     布局属性：binding_url、binding_engine、binding_listener，requireAll 为 false。
 *     仅在 url 非空时发起加载；listener 为空时仍加载但不回调监听。
 * </pre>
 *
 * @param url 图片地址，为空或空串时不加载
 * @param engine 图片引擎标识，可为空以走默认引擎
 * @param listener 加载过程监听，可为空
 */
@BindingAdapter(
    value = ["binding_url", "binding_engine", "binding_listener"],
    requireAll = false
)
fun ImageView.bindingImageUrl(
    url: String?,
    engine: String?,
    listener: LoadListener<Bitmap>?
) {
    if (StringUtils.isNotEmpty(url)) {
        try {
            listener.hiIfNotNullWith({
                this.display(
                    engine = engine,
                    source = url?.toSource(),
                    listener = it
                )
            }, {
                this.display(
                    engine = engine,
                    source = url?.toSource()
                )
            })
        } catch (e: Throwable) {
            LogPrintUtils.eTag(TAG, e, "bindingImageUrl")
        }
    }
}

/**
 * 通过数据绑定按 DevSource 加载图片到当前 ImageView
 * <pre>
 *     布局属性：binding_source、binding_engine、binding_listener，requireAll 为 false。
 * </pre>
 *
 * @param source 图片数据源
 * @param engine 图片引擎标识，可为空以走默认引擎
 * @param listener 加载过程监听，可为空
 */
@BindingAdapter(
    value = ["binding_source", "binding_engine", "binding_listener"],
    requireAll = false
)
fun ImageView.bindingImageSource(
    source: DevSource,
    engine: String?,
    listener: LoadListener<Bitmap>?
) {
    try {
        listener.hiIfNotNullWith({
            this.display(
                engine = engine,
                source = source,
                listener = it
            )
        }, {
            this.display(
                engine = engine,
                source = source
            )
        })
    } catch (e: Throwable) {
        LogPrintUtils.eTag(TAG, e, "bindingImageSource")
    }
}