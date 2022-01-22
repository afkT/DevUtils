package dev.engine.image

import android.graphics.drawable.Drawable

/**
 * detail: Image Config
 * @author Ttt
 */
class ImageConfig private constructor(
    config: ImageConfig?
) : IImageEngine.EngineConfig() {

    // 是否缓存到 SDCard
    private var mCacheDisk = true

    // 是否缓存到内存
    private var mCacheMemory = true

    private var mScaleType = 0
    private var mTransform = 0
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

    // 是否移除所有 Transformation 效果
    private var mDontTransform = false

    companion object {

        // scale type
        const val SCALE_NONE = 0
        const val SCALE_CENTER_CROP = 1
        const val SCALE_FIT_CENTER = 2

        // transform
        const val TRANSFORM_NONE = 1
        const val TRANSFORM_CIRCLE = 2
        const val TRANSFORM_ROUNDED_CORNERS = 3

        // placeholder
        const val NO_PLACE_HOLDER = -1

        // 默认图片保存质量值
        const val QUALITY = 80

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

            this.mScaleType = it.mScaleType
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
            // 是否移除所有 Transformation 效果
            this.mDontTransform = it.mDontTransform
        }
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 克隆配置信息
     * @return [ImageConfig]
     */
    fun clone(): ImageConfig {
        return ImageConfig(this)
    }

    // ===========
    // = get/set =
    // ===========

    fun isCacheDisk(): Boolean {
        return mCacheDisk
    }

    fun setCacheDisk(cacheDisk: Boolean): ImageConfig {
        mCacheDisk = cacheDisk
        return this
    }

    fun isCacheMemory(): Boolean {
        return mCacheMemory
    }

    fun setCacheMemory(cacheMemory: Boolean): ImageConfig {
        mCacheMemory = cacheMemory
        return this
    }

    fun getScaleType(): Int {
        return mScaleType
    }

    /**
     * @param scaleType [SCALE_CENTER_CROP]、[SCALE_FIT_CENTER]
     */
    fun setScaleType(scaleType: Int): ImageConfig {
        mScaleType = scaleType
        return this
    }

    fun getTransform(): Int {
        return mTransform
    }

    /**
     * @param transform [TRANSFORM_ROUNDED_CORNERS]、[TRANSFORM_CIRCLE]
     */
    fun setTransform(transform: Int): ImageConfig {
        mTransform = transform
        return this
    }

    fun getRoundedCornersRadius(): Int {
        return mRoundedCornersRadius
    }

    fun setRoundedCornersRadius(roundedCornersRadius: Int): ImageConfig {
        mRoundedCornersRadius = roundedCornersRadius
        return this
    }

    fun getErrorPlaceholder(): Int {
        return mErrorPlaceholder
    }

    fun setErrorPlaceholder(errorPlaceholder: Int): ImageConfig {
        mErrorPlaceholder = errorPlaceholder
        mErrorDrawable = null
        return this
    }

    fun getLoadingPlaceholder(): Int {
        return mLoadingPlaceholder
    }

    fun setLoadingPlaceholder(loadingPlaceholder: Int): ImageConfig {
        mLoadingPlaceholder = loadingPlaceholder
        mLoadingDrawable = null
        return this
    }

    fun getErrorDrawable(): Drawable? {
        return mErrorDrawable
    }

    fun setErrorDrawable(errorDrawable: Drawable?): ImageConfig {
        mErrorPlaceholder = NO_PLACE_HOLDER
        mErrorDrawable = errorDrawable
        return this
    }

    fun getLoadingDrawable(): Drawable? {
        return mLoadingDrawable
    }

    fun setLoadingDrawable(loadingDrawable: Drawable?): ImageConfig {
        mLoadingPlaceholder = NO_PLACE_HOLDER
        mLoadingDrawable = loadingDrawable
        return this
    }

    fun getWidth(): Int {
        return mWidth
    }

    fun getHeight(): Int {
        return mHeight
    }

    fun setSize(
        width: Int,
        height: Int
    ): ImageConfig {
        mWidth = width
        mHeight = height
        return this
    }

    fun getThumbnail(): Float {
        return mThumbnail
    }

    fun setThumbnail(thumbnail: Float): ImageConfig {
        mThumbnail = thumbnail
        return this
    }

    fun getQuality(): Int {
        return mQuality
    }

    fun setQuality(quality: Int) {
        mQuality = quality
    }

    fun isOriginalPathReturn(): Boolean {
        return mOriginalPathReturn
    }

    fun setOriginalPathReturn(originalPathReturn: Boolean) {
        mOriginalPathReturn = originalPathReturn
    }

    fun isDontAnimate(): Boolean {
        return mDontAnimate
    }

    fun setDontAnimate(dontAnimate: Boolean) {
        mDontAnimate = dontAnimate
    }

    fun isDontTransform(): Boolean {
        return mDontTransform
    }

    fun setDontTransform(dontTransform: Boolean) {
        mDontTransform = dontTransform
    }
}