package ktx.dev.engine.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.transition.Transition
import dev.base.DevSource
import dev.engine.image.IImageEngine
import dev.engine.image.LoadListener
import dev.engine.image.listener.ConvertStorage
import dev.engine.image.listener.OnConvertListener
import dev.utils.app.PathUtils
import dev.utils.app.image.ImageUtils
import dev.utils.common.FileUtils
import dev.utils.common.RandomUtils
import dev.utils.common.encrypt.MD5Utils
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap


/**
 * detail: Glide Image Engine 实现
 * @author Ttt
 */
class GlideEngineImpl : IImageEngine<ImageConfig> {

    // 日志 TAG
    private val TAG = GlideEngineImpl::class.java.simpleName

    // ====================
    // = pause and resume =
    // ====================

    override fun pause(fragment: Fragment?) {
        fragment?.let {
            Glide.with(it).pauseRequests()
        }
    }

    override fun resume(fragment: Fragment?) {
        fragment?.let {
            Glide.with(it).resumeRequests()
        }
    }

    override fun pause(context: Context?) {
        context?.let {
            Glide.with(it).pauseRequests()
        }
    }

    override fun resume(context: Context?) {
        context?.let {
            Glide.with(it).resumeRequests()
        }
    }

    // ===========
    // = preload =
    // ===========

    override fun preload(
        context: Context?,
        source: DevSource?
    ) {
        if (context != null && source != null) {
            val requestManager = Glide.with(context)
            setToRequest(requestManager, source)!!.preload()
        }
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

    // ===========
    // = 内部方法 =
    // ===========

    /**
     * Fragment 是否能够用于加载图片
     * @param fragment [Fragment]
     * @return `true` yes, `false` no
     */
    private fun canFragmentLoadImage(fragment: Fragment): Boolean {
        return fragment.isResumed || fragment.isAdded || fragment.isVisible
    }

    /**
     * 通过 [DevSource] 设置 [RequestBuilder] 加载 source
     * @param manager [RequestManager]
     * @param source  [DevSource]
     * @return [RequestBuilder]
     */
    private fun setToRequest(
        manager: RequestManager?,
        source: DevSource?
    ): RequestBuilder<*>? {
        return if (manager != null && source != null) {
            when {
                source.mFile != null -> {
                    manager.load(source.mFile)
                }
                source.mUrl != null -> {
                    manager.load(source.mUrl)
                }
                source.mResource != 0 -> {
                    manager.load(source.mResource)
                }
                source.mUri != null -> {
                    manager.load(source.mUri)
                }
                source.mBytes != null -> {
                    manager.load(source.mBytes)
                }
                else -> {
                    throw IllegalArgumentException("UnSupport source")
                }
            }
        } else null
    }

    /**
     * 通过 [DevSource] 设置 [RequestBuilder] 加载 source
     * @param request [RequestBuilder]
     * @param source  [DevSource]
     * @param <T>     泛型 ( 如 Drawable、Bitmap )
     * @return [RequestBuilder]
     */
    private fun <T> setToRequest(
        request: RequestBuilder<T>?,
        source: DevSource?
    ): RequestBuilder<T>? {
        return if (request != null && source != null) {
            when {
                source.mFile != null -> {
                    request.load(source.mFile)
                }
                source.mUrl != null -> {
                    request.load(source.mUrl)
                }
                source.mResource != 0 -> {
                    request.load(source.mResource)
                }
                source.mUri != null -> {
                    request.load(source.mUri)
                }
                source.mBytes != null -> {
                    request.load(source.mBytes)
                }
                else -> {
                    throw IllegalArgumentException("UnSupport source")
                }
            }
        } else null
    }

    /**
     * 通过 [ImageConfig] 构建 [RequestOptions]
     * @param config [ImageConfig]
     * @return [RequestOptions]
     */
    private fun buildRequestOptions(config: ImageConfig?): RequestOptions {
        var options = RequestOptions()
        config?.let { config ->

            // =============
            // = 初始化配置 =
            // =============

            // DiskCache
            options = if (config.isCacheDisk()) {
                options.diskCacheStrategy(DiskCacheStrategy.ALL)
            } else {
                options.diskCacheStrategy(DiskCacheStrategy.NONE)
            }

            // MemoryCache
            options = if (config.isCacheMemory()) {
                options.skipMemoryCache(false)
            } else {
                options.skipMemoryCache(true)
            }

            // scale type
            if (config.getScaleType() == ImageConfig.SCALE_CENTER_CROP) {
                options = options.centerCrop()
            } else if (config.getScaleType() == ImageConfig.SCALE_FIT_CENTER) {
                options = options.fitCenter()
            }

            // transform
            if (config.getTransform() == ImageConfig.TRANSFORM_CIRCLE) {
                options = options.circleCrop()
            } else if (config.getTransform() == ImageConfig.TRANSFORM_ROUNDED_CORNERS) {
                if (config.getScaleType() == ImageConfig.SCALE_NONE) {
                    options = options.transform(RoundedCorners(config.getRoundedCornersRadius()))
                } else if (config.getScaleType() == ImageConfig.SCALE_CENTER_CROP) {
                    options = options.transform(
                        MultiTransformation(
                            CenterCrop(),
                            RoundedCorners(config.getRoundedCornersRadius())
                        )
                    )
                } else if (config.getScaleType() == ImageConfig.SCALE_FIT_CENTER) {
                    options = options.transform(
                        MultiTransformation(
                            FitCenter(),
                            RoundedCorners(config.getRoundedCornersRadius())
                        )
                    )
                }
            } else if (config.getTransform() == ImageConfig.TRANSFORM_NONE) {
                options = options.dontTransform() // 不做渐入渐出转换
            }

            // placeholder
            if (config.getErrorPlaceholder() != ImageConfig.NO_PLACE_HOLDER) {
                options = options.error(config.getErrorPlaceholder())
            }

            if (config.getErrorDrawable() != null) {
                options = options.error(config.getErrorDrawable())
            }

            if (config.getLoadingPlaceholder() != ImageConfig.NO_PLACE_HOLDER) {
                options = options.placeholder(config.getLoadingPlaceholder())
            }

            if (config.getLoadingDrawable() != null) {
                options = options.placeholder(config.getLoadingDrawable())
            }

            // width、height
            if (config.getWidth() > 0 && config.getHeight() > 0) {
                options = options.override(config.getWidth(), config.getHeight())
            }
        }
        return options
    }

    /**
     * 通过 [ImageConfig] 构建 [RequestBuilder]
     * @param request [RequestBuilder]
     * @param config  [ImageConfig]
     * @return [RequestBuilder]
     */
    private fun <T> buildRequest(
        request: RequestBuilder<T>?,
        config: ImageConfig?
    ): RequestBuilder<*>? {
        request?.let {
            val options = buildRequestOptions(config)
            val req = it.apply(options)
            config?.let { config ->
                if (config.getThumbnail() > 0f) {
                    return req.thumbnail(config.getThumbnail())
                }
            }
            return req
        }
        return request
    }

    // ====================
    // = 内部 Display 方法 =
    // ====================

    /**
     * 通过 [RequestBuilder] 与 [ImageConfig] 快捷显示方法
     * @param imageView [ImageView]
     * @param request   [RequestBuilder]
     * @param config    [ImageConfig]
     */
    private fun priDisplayToRequestBuilder(
        imageView: ImageView?,
        request: RequestBuilder<*>?,
        config: ImageConfig
    ) {
        if (imageView != null && request != null) {
            buildRequest(request, config)?.into(imageView)
        }
    }

    /**
     * 通过 [RequestBuilder] 与 [ImageConfig] 快捷显示方法
     * @param imageView [ImageView]
     * @param request   [RequestBuilder]
     * @param config    [ImageConfig]
     * @param source    [DevSource]
     * @param listener  [LoadListener]
     */
    private fun <T> priDisplayToRequestBuilder(
        imageView: ImageView?,
        request: RequestBuilder<*>?,
        config: ImageConfig,
        source: DevSource,
        listener: LoadListener<T>?
    ) {
        if (imageView != null && request != null && listener != null && listener.transcodeType != null) {
            val type = listener.transcodeType
            if (type == Drawable::class.java) {
                buildRequest(request, config)?.into(
                    InnerDrawableViewTarget(
                        imageView, source, listener as LoadListener<Drawable>
                    )
                )
            } else if (type == Bitmap::class.java) {
                buildRequest(request, config)?.into(
                    InnerBitmapViewTarget(
                        imageView, source, listener as LoadListener<Bitmap>
                    )
                )
            }
        }
    }

    // ===============
    // = 内部加载事件 =
    // ===============

    private class InnerDrawableViewTarget(
        view: ImageView?,
        private val mSource: DevSource,
        private val mListener: LoadListener<Drawable>
    ) : ImageViewTarget<Drawable>(view) {

        override fun setResource(resource: Drawable?) {
            getView().setImageDrawable(resource)
        }

        override fun onResourceReady(
            resource: Drawable,
            transition: Transition<in Drawable>?
        ) {
            super.onResourceReady(resource, transition)
            mListener.onResponse(mSource, resource)
        }

        override fun onLoadStarted(placeholder: Drawable?) {
            mListener.onStart(mSource)
            super.onLoadStarted(placeholder)
        }

        override fun onLoadFailed(errorDrawable: Drawable?) {
            super.onLoadFailed(errorDrawable)
            mListener.onFailure(mSource, Exception("Load Failed"))
        }
    }

    private class InnerBitmapViewTarget(
        view: ImageView?,
        private val mSource: DevSource,
        private val mListener: LoadListener<Bitmap>
    ) : ImageViewTarget<Drawable?>(view) {

        override fun setResource(resource: Drawable?) {
            getView().setImageDrawable(resource)
        }

        override fun onResourceReady(
            resource: Drawable,
            transition: Transition<in Drawable?>?
        ) {
            super.onResourceReady(resource, transition)
            mListener.onResponse(mSource, ImageUtils.drawableToBitmap(resource))
        }

        override fun onLoadStarted(placeholder: Drawable?) {
            mListener.onStart(mSource)
            super.onLoadStarted(placeholder)
        }

        override fun onLoadFailed(errorDrawable: Drawable?) {
            super.onLoadFailed(errorDrawable)
            mListener.onFailure(mSource, Exception("Load Failed"))
        }
    }

    // =

    private class InnerDrawableTarget(
        private val mSource: DevSource,
        private val mListener: LoadListener<Drawable>
    ) : CustomTarget<Drawable?>() {
        override fun onResourceReady(
            resource: Drawable,
            transition: Transition<in Drawable?>?
        ) {
            mListener.onResponse(mSource, resource)
        }

        override fun onLoadStarted(placeholder: Drawable?) {
            mListener.onStart(mSource)
            super.onLoadStarted(placeholder)
        }

        override fun onLoadFailed(errorDrawable: Drawable?) {
            super.onLoadFailed(errorDrawable)
            mListener.onFailure(mSource, Exception("Load Failed"))
        }

        override fun onLoadCleared(placeholder: Drawable?) {}
    }

    private class InnerBitmapTarget(
        private val mSource: DevSource,
        private val mListener: LoadListener<Bitmap>
    ) : CustomTarget<Bitmap?>() {
        override fun onResourceReady(
            resource: Bitmap,
            transition: Transition<in Bitmap?>?
        ) {
            mListener.onResponse(mSource, resource)
        }

        override fun onLoadStarted(placeholder: Drawable?) {
            mListener.onStart(mSource)
            super.onLoadStarted(placeholder)
        }

        override fun onLoadFailed(errorDrawable: Drawable?) {
            super.onLoadFailed(errorDrawable)
            mListener.onFailure(mSource, Exception("Load Failed"))
        }

        override fun onLoadCleared(placeholder: Drawable?) {}
    }

    // ====================
    // = 转换图片格式并存储 =
    // ====================

    /**
     * 私有转换图片格式处理方法
     * @param context  [Context]
     * @param sources  待转换资源集合
     * @param config   配置信息
     * @param listener 回调事件
     * @return `true` success, `false` fail
     */
    private fun priConvertImageFormat(
        context: Context?,
        sources: List<DevSource>?,
        config: ImageConfig,
        listener: OnConvertListener?
    ): Boolean {
        if (context != null && sources != null && listener != null && sources.isNotEmpty()) {
            val fileLists: MutableList<File> = ArrayList()
            val fileMaps: MutableMap<Int, File?> = LinkedHashMap()
            // 转换器
            val convertStorage = InnerConvertStorage(this)
            // 随机创建任务 id
            val randomTask = RandomUtils.getRandom(1000000, 10000000)
            val task = randomTask.toString()
            // 循环转存
            val len = sources.size
            for (i in 0 until len) {
                var result: File? = null
                try {
                    listener.onStart(i, len)
                    result = convertStorage.convert(context, sources[i], config, i, len, task)
                    if (result == null || !result.exists()) {
                        throw Exception("result file is null or not exists")
                    }
                } catch (e: Exception) {
                    listener.onError(e, i, len)
                }
                if (result != null && result.exists()) {
                    listener.onSuccess(result, i, len)
                    fileLists.add(result)
                }
                fileMaps[i] = result
            }
            listener.onComplete(fileLists, fileMaps, len)
        }
        return false
    }

    private class InnerConvertStorage(
        private val engineImpl: GlideEngineImpl
    ) : ConvertStorage<ImageConfig?> {
        override fun convert(
            context: Context?,
            source: DevSource?,
            config: ImageConfig?,
            index: Int,
            count: Int,
            task: String?
        ): File? {
            if (source == null) throw Exception("source is null")
            // 属于文件, 判断是否符合指定格式
            if (source.isFile) {
                // 符合条件直接返回
                if (FileUtils.isImageFormats(
                        source.mFile.absolutePath, arrayOf(".PNG", ".JPG", ".JPEG")
                    )
                ) {
                    // 配置为 null 或要求原路径返回
                    if (config == null || config.isOriginalPathReturn()) {
                        return source.mFile
                    }
                }
            }
            val readBitmap = engineImpl.loadBitmapThrows(context, source, config)
            // 创建随机名 ( 一定程度上唯一, 防止出现重复情况 )
            val randomName = String.format(
                "%s_%s_%s_%s_%s", task, UUID.randomUUID().hashCode(),
                System.currentTimeMillis(), index, count
            )
            // convert_task_index_md5.png
            val md5FileName = String.format("c_%s_%s_%s.png", task, index, MD5Utils.md5(randomName))
            // 存储到外部存储私有目录 ( /storage/emulated/0/Android/data/package/ )
            val dirPath = PathUtils.getAppExternal().getAppCachePath("convertStorage")
            // 图片保存质量
            var quality = ImageConfig.QUALITY
            if (config != null) {
                if (config.getQuality() in 1..100) {
                    quality = config.getQuality()
                }
            }
            // 创建文件夹
            FileUtils.createFolder(dirPath)
            val resultFile = File(dirPath, md5FileName)
            // 保存图片
            val result = ImageUtils.saveBitmapToSDCard(
                readBitmap, resultFile, Bitmap.CompressFormat.PNG, quality
            )
            return if (result) resultFile else null
        }
    }
}