package dev.mvvm.binding.view

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import dev.base.DevSource
import dev.engine.image.listener.LoadListener
import dev.expand.engine.image.display
import dev.expand.engine.log.log_dTag
import dev.mvvm.base.Config
import dev.mvvm.utils.hi.hiIfNotNullWith
import dev.mvvm.utils.toSource

// ========================
// = Image BindingAdapter =
// ========================

private const val TAG = "Image_BindingAdapter"

//, "binding_placeholderRes"

@BindingAdapter(
    value = ["binding_url", "binding_engine", "binding_listener"],
    requireAll = false
)
fun ImageView.bindingImageUrl(
    url: String,
    engine: String?,
    listener: LoadListener<Bitmap>?
) {
    if (Config.printLog(engine)) {
        TAG.log_dTag(
            engine = engine,
            message = "bindingImageUrl\nurl: %s\nengine: %s\nlistener: %s",
            args = arrayOf(url, engine, listener)
        )
    }
    listener.hiIfNotNullWith({
        this.display(
            engine = engine,
            source = url.toSource(),
            listener = it
        )
    }, {
        this.display(
            engine = engine,
            source = url.toSource()
        )
    })
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
    if (Config.printLog(engine)) {
        TAG.log_dTag(
            engine = engine,
            message = "bindingImageSource\nsource: %s\nengine: %s\nlistener: %s",
            args = arrayOf(source, engine, listener)
        )
    }
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