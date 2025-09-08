package dev.simple.bindingadapters.view

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import dev.base.DevSource
import dev.engine.extensions.image.display
import dev.engine.image.listener.LoadListener
import dev.simple.extensions.hi.hiif.hiIfNotNullWith
import dev.simple.extensions.toSource
import dev.utils.common.StringUtils

// ========================
// = Image BindingAdapter =
// ========================

private const val TAG = "Image_BindingAdapter"

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
    }
}

@BindingAdapter(
    value = ["binding_source", "binding_engine", "binding_listener"],
    requireAll = false
)
fun ImageView.bindingImageSource(
    source: DevSource,
    engine: String?,
    listener: LoadListener<Bitmap>?
) {
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
}