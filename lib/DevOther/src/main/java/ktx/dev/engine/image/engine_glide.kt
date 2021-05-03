package ktx.dev.engine.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import dev.base.DevSource
import dev.engine.image.IImageEngine
import dev.engine.image.LoadListener
import dev.engine.image.listener.OnConvertListener

/**
 * detail: Glide Image Engine 实现
 * @author Ttt
 */
class GlideEngineImpl : IImageEngine<ImageConfig> {

    override fun pause(fragment: Fragment?) {
        TODO("Not yet implemented")
    }

    override fun resume(fragment: Fragment?) {
        TODO("Not yet implemented")
    }

    override fun pause(context: Context?) {
        TODO("Not yet implemented")
    }

    override fun resume(context: Context?) {
        TODO("Not yet implemented")
    }

    override fun preload(
        context: Context?,
        source: DevSource?
    ) {
        TODO("Not yet implemented")
    }

    override fun preload(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?
    ) {
        TODO("Not yet implemented")
    }

    override fun clear(view: View?) {
        TODO("Not yet implemented")
    }

    override fun clear(
        fragment: Fragment?,
        view: View?
    ) {
        TODO("Not yet implemented")
    }

    override fun clearDiskCache(context: Context?) {
        TODO("Not yet implemented")
    }

    override fun clearMemoryCache(context: Context?) {
        TODO("Not yet implemented")
    }

    override fun clearAllCache(context: Context?) {
        TODO("Not yet implemented")
    }

    override fun lowMemory(context: Context?) {
        TODO("Not yet implemented")
    }

    override fun display(
        imageView: ImageView?,
        url: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun display(
        imageView: ImageView?,
        source: DevSource?
    ) {
        TODO("Not yet implemented")
    }

    override fun display(
        imageView: ImageView?,
        url: String?,
        config: ImageConfig?
    ) {
        TODO("Not yet implemented")
    }

    override fun display(
        imageView: ImageView?,
        source: DevSource?,
        config: ImageConfig?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> display(
        imageView: ImageView?,
        url: String?,
        listener: LoadListener<T>?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> display(
        imageView: ImageView?,
        source: DevSource?,
        listener: LoadListener<T>?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> display(
        imageView: ImageView?,
        url: String?,
        config: ImageConfig?,
        listener: LoadListener<T>?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> display(
        imageView: ImageView?,
        source: DevSource?,
        config: ImageConfig?,
        listener: LoadListener<T>?
    ) {
        TODO("Not yet implemented")
    }

    override fun display(
        fragment: Fragment?,
        imageView: ImageView?,
        url: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun display(
        fragment: Fragment?,
        imageView: ImageView?,
        source: DevSource?
    ) {
        TODO("Not yet implemented")
    }

    override fun display(
        fragment: Fragment?,
        imageView: ImageView?,
        url: String?,
        config: ImageConfig?
    ) {
        TODO("Not yet implemented")
    }

    override fun display(
        fragment: Fragment?,
        imageView: ImageView?,
        source: DevSource?,
        config: ImageConfig?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> display(
        fragment: Fragment?,
        imageView: ImageView?,
        url: String?,
        listener: LoadListener<T>?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> display(
        fragment: Fragment?,
        imageView: ImageView?,
        source: DevSource?,
        listener: LoadListener<T>?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> display(
        fragment: Fragment?,
        imageView: ImageView?,
        url: String?,
        config: ImageConfig?,
        listener: LoadListener<T>?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> display(
        fragment: Fragment?,
        imageView: ImageView?,
        source: DevSource?,
        config: ImageConfig?,
        listener: LoadListener<T>?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> loadImage(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?,
        listener: LoadListener<T>?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> loadImage(
        fragment: Fragment?,
        source: DevSource?,
        config: ImageConfig?,
        listener: LoadListener<T>?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> loadImage(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?,
        type: Class<*>?
    ): T {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> loadImageThrows(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?,
        type: Class<*>?
    ): T {
        TODO("Not yet implemented")
    }

    override fun loadBitmap(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?,
        listener: LoadListener<Bitmap>?
    ) {
        TODO("Not yet implemented")
    }

    override fun loadBitmap(
        fragment: Fragment?,
        source: DevSource?,
        config: ImageConfig?,
        listener: LoadListener<Bitmap>?
    ) {
        TODO("Not yet implemented")
    }

    override fun loadBitmap(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?
    ): Bitmap {
        TODO("Not yet implemented")
    }

    override fun loadBitmapThrows(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?
    ): Bitmap {
        TODO("Not yet implemented")
    }

    override fun loadDrawable(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?,
        listener: LoadListener<Drawable>?
    ) {
        TODO("Not yet implemented")
    }

    override fun loadDrawable(
        fragment: Fragment?,
        source: DevSource?,
        config: ImageConfig?,
        listener: LoadListener<Drawable>?
    ) {
        TODO("Not yet implemented")
    }

    override fun loadDrawable(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?
    ): Drawable {
        TODO("Not yet implemented")
    }

    override fun loadDrawableThrows(
        context: Context?,
        source: DevSource?,
        config: ImageConfig?
    ): Drawable {
        TODO("Not yet implemented")
    }

    override fun convertImageFormat(
        context: Context?,
        sources: MutableList<DevSource>?,
        listener: OnConvertListener?
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun convertImageFormat(
        context: Context?,
        sources: MutableList<DevSource>?,
        config: ImageConfig?,
        listener: OnConvertListener?
    ): Boolean {
        TODO("Not yet implemented")
    }
}