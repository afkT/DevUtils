package ktx.dev.other

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import dev.base.DevSource
import dev.engine.image.GlideEngineImpl
import dev.engine.image.ImageConfig
import dev.engine.image.listener.LoadListener
import dev.engine.image.listener.OnConvertListener

/**
 * detail: Glide 工具类
 * @author Ttt
 */
@Deprecated("推荐使用 DevImageEngine 实现类 GlideEngineImpl")
object GlideUtils {

    private val IMPL = GlideEngineImpl()

    // ====================
    // = pause and resume =
    // ====================

    fun pause(fragment: Fragment?) {
        IMPL.pause(fragment)
    }

    fun resume(fragment: Fragment?) {
        IMPL.resume(fragment)
    }

    fun pause(context: Context?) {
        IMPL.pause(context)
    }

    fun resume(context: Context?) {
        IMPL.resume(context)
    }

    // ===========
    // = preload =
    // ===========

    fun preload(
        context: Context?,
        source: DevSource?
    ) {
        IMPL.preload(context, source)
    }

    fun preload(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?
    ) {
        IMPL.preload(context, source, config)
    }

    // =========
    // = clear =
    // =========

    fun clear(view: View?) {
        IMPL.clear(view)
    }

    fun clear(
        fragment: Fragment?,
        view: View?
    ) {
        IMPL.clear(fragment, view)
    }

    fun clearDiskCache(context: Context?) {
        IMPL.clearDiskCache(context)
    }

    fun clearMemoryCache(context: Context?) {
        IMPL.clearMemoryCache(context)
    }

    fun clearAllCache(context: Context?) {
        IMPL.clearAllCache(context)
    }

    // =========
    // = other =
    // =========

    fun lowMemory(context: Context?) {
        IMPL.lowMemory(context)
    }

    // ===========
    // = display =
    // ===========

    fun display(
        imageView: ImageView?,
        url: String?
    ) {
        IMPL.display(imageView, url)
    }

    fun display(
        imageView: ImageView?,
        source: DevSource?
    ) {
        IMPL.display(imageView, source)
    }

    fun display(
        imageView: ImageView?,
        url: String?,
        config: ImageConfig?
    ) {
        IMPL.display(imageView, url, config)
    }

    fun display(
        imageView: ImageView?,
        source: DevSource?,
        config: ImageConfig?
    ) {
        IMPL.display(imageView, source, config)
    }

    // =

    fun <T : Any> display(
        imageView: ImageView?,
        url: String?,
        listener: LoadListener<T>?
    ) {
        IMPL.display(imageView, url, listener)
    }

    fun <T : Any> display(
        imageView: ImageView?,
        source: DevSource?,
        listener: LoadListener<T>?
    ) {
        IMPL.display(imageView, source, listener)
    }

    fun <T : Any> display(
        imageView: ImageView?,
        url: String?,
        config: ImageConfig?,
        listener: LoadListener<T>?
    ) {
        IMPL.display(imageView, url, config, listener)
    }

    fun <T : Any> display(
        imageView: ImageView?,
        source: DevSource?,
        config: ImageConfig?,
        listener: LoadListener<T>?
    ) {
        IMPL.display(imageView, source, config, listener)
    }

    // =

    fun display(
        fragment: Fragment?,
        imageView: ImageView?,
        url: String?
    ) {
        IMPL.display(fragment, imageView, url)
    }

    fun display(
        fragment: Fragment?,
        imageView: ImageView?,
        source: DevSource?
    ) {
        IMPL.display(fragment, imageView, source)
    }

    fun display(
        fragment: Fragment?,
        imageView: ImageView?,
        url: String?,
        config: ImageConfig?
    ) {
        IMPL.display(fragment, imageView, url, config)
    }

    fun display(
        fragment: Fragment?,
        imageView: ImageView?,
        source: DevSource?,
        config: ImageConfig?
    ) {
        IMPL.display(fragment, imageView, source, config)
    }

    // =

    fun <T : Any> display(
        fragment: Fragment?,
        imageView: ImageView?,
        url: String?,
        listener: LoadListener<T>?
    ) {
        IMPL.display(fragment, imageView, url, listener)
    }

    fun <T : Any> display(
        fragment: Fragment?,
        imageView: ImageView?,
        source: DevSource?,
        listener: LoadListener<T>?
    ) {
        IMPL.display(fragment, imageView, source, listener)
    }

    fun <T : Any> display(
        fragment: Fragment?,
        imageView: ImageView?,
        url: String?,
        config: ImageConfig?,
        listener: LoadListener<T>?
    ) {
        IMPL.display(fragment, imageView, url, config, listener)
    }

    fun <T : Any> display(
        fragment: Fragment?,
        imageView: ImageView?,
        source: DevSource?,
        config: ImageConfig?,
        listener: LoadListener<T>?
    ) {
        IMPL.display(fragment, imageView, source, config, listener)
    }

    // ========
    // = load =
    // ========

    fun <T : Any> loadImage(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?,
        listener: LoadListener<T>?
    ) {
        IMPL.loadImage(context, source, config, listener)
    }

    fun <T : Any> loadImage(
        fragment: Fragment?,
        source: DevSource?,
        config: ImageConfig?,
        listener: LoadListener<T>?
    ) {
        IMPL.loadImage(fragment, source, config, listener)
    }

    fun <T : Any> loadImage(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?,
        type: Class<*>?
    ): T? {
        return IMPL.loadImage(context, source, config, type)
    }

    fun <T : Any> loadImageThrows(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?,
        type: Class<*>?
    ): T? {
        return IMPL.loadImageThrows(context, source, config, type)
    }

    // =

    fun loadBitmap(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?,
        listener: LoadListener<Bitmap>?
    ) {
        IMPL.loadBitmap(context, source, config, listener)
    }

    fun loadBitmap(
        fragment: Fragment?,
        source: DevSource?,
        config: ImageConfig?,
        listener: LoadListener<Bitmap>?
    ) {
        IMPL.loadBitmap(fragment, source, config, listener)
    }

    fun loadBitmap(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?
    ): Bitmap? {
        return IMPL.loadBitmap(context, source, config)
    }

    fun loadBitmapThrows(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?
    ): Bitmap? {
        return IMPL.loadBitmapThrows(context, source, config)
    }

    // =

    fun loadDrawable(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?,
        listener: LoadListener<Drawable>?
    ) {
        IMPL.loadDrawable(context, source, config, listener)
    }

    fun loadDrawable(
        fragment: Fragment?,
        source: DevSource?,
        config: ImageConfig?,
        listener: LoadListener<Drawable>?
    ) {
        IMPL.loadDrawable(fragment, source, config, listener)
    }

    fun loadDrawable(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?
    ): Drawable? {
        return IMPL.loadDrawable(context, source, config)
    }

    fun loadDrawableThrows(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?
    ): Drawable? {
        return IMPL.loadDrawableThrows(context, source, config)
    }

    // ===========
    // = convert =
    // ===========

    fun convertImageFormat(
        context: Context?,
        sources: MutableList<DevSource>?,
        listener: OnConvertListener?
    ): Boolean {
        return IMPL.convertImageFormat(context, sources, listener)
    }

    fun convertImageFormat(
        context: Context?,
        sources: MutableList<DevSource>?,
        config: ImageConfig?,
        listener: OnConvertListener?
    ): Boolean {
        return IMPL.convertImageFormat(context, sources, config, listener)
    }
}