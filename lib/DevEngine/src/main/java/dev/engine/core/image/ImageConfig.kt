package dev.engine.core.image

import android.graphics.drawable.Drawable
import dev.engine.image.IImageEngine

/**
 * detail: Image Config
 * @author Ttt
 */
open class ImageConfig private constructor(
    config: ImageConfig?
) : IImageEngine.EngineConfig {

    // 是否缓存到 SDCard
    private var mCacheDisk = true

    // 是否缓存到内存
    private var mCacheMemory = true

    private var mTransform = TRANSFORM_NONE
    private var mRoundedCornersRadius = 0

    // placeholder
    private var mErrorPlaceholder = NO_PLACE_HOLDER
    private var mLoadingPlaceholder = NO_PLACE_HOLDER
    private var mErrorDrawable: Drawable? = null
    private var mLoadingDrawable: Drawable? = null

    // 加载图片宽
    private var mWidth = 0

    // 加载图片高
    private var mHeight = 0

    // 加载缩略图时应用尺寸的乘数
    private var mThumbnail = 0F

    // 图片保存质量
    private var mQuality = QUALITY

    // 转换符合格式文件是否原图返回
    private var mOriginalPathReturn = false

    // 是否不显示动画
    private var mDontAnimate = false

    // 额外扩展对象
    private var mOptions: Any? = null

    companion object {

        // transform
        const val TRANSFORM_NONE = 0
        const val TRANSFORM_CIRCLE = 1
        const val TRANSFORM_CENTER_INSIDE = 2
        const val TRANSFORM_CENTER_CROP = 3
        const val TRANSFORM_FIT_CENTER = 4
        const val TRANSFORM_ROUNDED_CORNERS = 5
        const val TRANSFORM_ROUNDED_CORNERS_CENTER_INSIDE = 6
        const val TRANSFORM_ROUNDED_CORNERS_CENTER_CROP = 7
        const val TRANSFORM_ROUNDED_CORNERS_FIT_CENTER = 8

        // placeholder
        const val NO_PLACE_HOLDER = -1

        // 默认图片保存质量值
        const val QUALITY = 100

        fun create(): ImageConfig {
            return ImageConfig(null)
        }

        fun create(config: ImageConfig?): ImageConfig {
            return ImageConfig(config)
        }
    }

    init {
        config?.let {
            // 是否缓存到 SDCard
            this.mCacheDisk = it.mCacheDisk
            // 是否缓存到内存
            this.mCacheMemory = it.mCacheMemory

            this.mTransform = it.mTransform
            this.mRoundedCornersRadius = it.mRoundedCornersRadius
            // placeholder
            this.mErrorPlaceholder = it.mErrorPlaceholder
            this.mLoadingPlaceholder = it.mLoadingPlaceholder
            this.mErrorDrawable = it.mErrorDrawable
            this.mLoadingDrawable = it.mLoadingDrawable
            // 加载图片宽
            this.mWidth = it.mWidth
            // 加载图片高
            this.mHeight = it.mHeight
            // 加载缩略图时应用尺寸的乘数
            this.mThumbnail = it.mThumbnail
            // 图片保存质量
            this.mQuality = it.mQuality
            // 转换符合格式文件是否原图返回
            this.mOriginalPathReturn = it.mOriginalPathReturn
            // 是否不显示动画
            this.mDontAnimate = it.mDontAnimate
            // 额外扩展对象
            this.mOptions = it.mOptions
        }
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 克隆配置信息
     * @return [ImageConfig]
     */
    open fun clone(): ImageConfig {
        return ImageConfig(this)
    }

    // ===========
    // = get/set =
    // ===========

    open fun isCacheDisk(): Boolean {
        return mCacheDisk
    }

    open fun setCacheDisk(cacheDisk: Boolean): ImageConfig {
        mCacheDisk = cacheDisk
        return this
    }

    open fun isCacheMemory(): Boolean {
        return mCacheMemory
    }

    open fun setCacheMemory(cacheMemory: Boolean): ImageConfig {
        mCacheMemory = cacheMemory
        return this
    }

    open fun getTransform(): Int {
        return mTransform
    }

    open fun setTransform(transform: Int): ImageConfig {
        mTransform = transform
        return this
    }

    open fun getRoundedCornersRadius(): Int {
        return mRoundedCornersRadius
    }

    open fun setRoundedCornersRadius(roundedCornersRadius: Int): ImageConfig {
        mRoundedCornersRadius = roundedCornersRadius
        return this
    }

    open fun getErrorPlaceholder(): Int {
        return mErrorPlaceholder
    }

    open fun setErrorPlaceholder(errorPlaceholder: Int): ImageConfig {
        mErrorPlaceholder = errorPlaceholder
        mErrorDrawable = null
        return this
    }

    open fun getLoadingPlaceholder(): Int {
        return mLoadingPlaceholder
    }

    open fun setLoadingPlaceholder(loadingPlaceholder: Int): ImageConfig {
        mLoadingPlaceholder = loadingPlaceholder
        mLoadingDrawable = null
        return this
    }

    open fun getErrorDrawable(): Drawable? {
        return mErrorDrawable
    }

    open fun setErrorDrawable(errorDrawable: Drawable?): ImageConfig {
        mErrorPlaceholder = NO_PLACE_HOLDER
        mErrorDrawable = errorDrawable
        return this
    }

    open fun getLoadingDrawable(): Drawable? {
        return mLoadingDrawable
    }

    open fun setLoadingDrawable(loadingDrawable: Drawable?): ImageConfig {
        mLoadingPlaceholder = NO_PLACE_HOLDER
        mLoadingDrawable = loadingDrawable
        return this
    }

    open fun getWidth(): Int {
        return mWidth
    }

    open fun getHeight(): Int {
        return mHeight
    }

    open fun setSize(
        width: Int,
        height: Int
    ): ImageConfig {
        mWidth = width
        mHeight = height
        return this
    }

    open fun getThumbnail(): Float {
        return mThumbnail
    }

    open fun setThumbnail(thumbnail: Float): ImageConfig {
        mThumbnail = thumbnail
        return this
    }

    open fun getQuality(): Int {
        return mQuality
    }

    open fun setQuality(quality: Int) {
        mQuality = quality
    }

    open fun isOriginalPathReturn(): Boolean {
        return mOriginalPathReturn
    }

    open fun setOriginalPathReturn(originalPathReturn: Boolean) {
        mOriginalPathReturn = originalPathReturn
    }

    open fun isDontAnimate(): Boolean {
        return mDontAnimate
    }

    open fun setDontAnimate(dontAnimate: Boolean) {
        mDontAnimate = dontAnimate
    }

    open fun getOptions(): Any? {
        return mOptions
    }

    open fun setOptions(options: Any?) {
        mOptions = options
    }
}