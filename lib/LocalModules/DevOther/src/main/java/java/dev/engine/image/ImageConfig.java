package java.dev.engine.image;

import android.graphics.drawable.Drawable;

import androidx.annotation.IntRange;

import dev.engine.image.IImageEngine;

/**
 * detail: Image Config
 * @author Ttt
 */
public class ImageConfig
        extends IImageEngine.EngineConfig {

    // 是否缓存到 SDCard
    private boolean mCacheDisk   = true;
    // 是否缓存到内存
    private boolean mCacheMemory = true;

    private int mScaleType;
    private int mTransform;
    private int mRoundedCornersRadius;

    // scale type
    public static final int SCALE_NONE        = 0;
    public static final int SCALE_CENTER_CROP = 1;
    public static final int SCALE_FIT_CENTER  = 2;

    // transform
    public static final int TRANSFORM_NONE            = 1;
    public static final int TRANSFORM_CIRCLE          = 2;
    public static final int TRANSFORM_ROUNDED_CORNERS = 3;

    // placeholder
    public static final int      NO_PLACE_HOLDER     = -1;
    private             int      mErrorPlaceholder   = NO_PLACE_HOLDER;
    private             int      mLoadingPlaceholder = NO_PLACE_HOLDER;
    private             Drawable mErrorDrawable      = null;
    private             Drawable mLoadingDrawable    = null;

    // 加载图片宽
    private int   mWidth;
    // 加载图片高
    private int   mHeight;
    // 加载缩略图时应用尺寸的乘数
    private float mThumbnail;

    // 默认图片保存质量值
    public static final int     QUALITY             = 80;
    // 图片保存质量
    private             int     mQuality            = QUALITY;
    // 转换符合格式文件是否原图返回
    private             boolean mOriginalPathReturn = false;
    // 是否不显示动画
    private             boolean mDontAnimate        = false;
    // 是否移除所有 Transformation 效果
    private             boolean mDontTransform      = false;

    // ==========
    // = 构造函数 =
    // ==========

    private ImageConfig(final ImageConfig config) {
        if (config != null) {
            // 是否缓存到 SDCard
            this.mCacheDisk = config.mCacheDisk;
            // 是否缓存到内存
            this.mCacheMemory = config.mCacheMemory;

            this.mScaleType            = config.mScaleType;
            this.mTransform            = config.mTransform;
            this.mRoundedCornersRadius = config.mRoundedCornersRadius;
            // placeholder
            this.mErrorPlaceholder   = config.mErrorPlaceholder;
            this.mLoadingPlaceholder = config.mLoadingPlaceholder;
            this.mErrorDrawable      = config.mErrorDrawable;
            this.mLoadingDrawable    = config.mLoadingDrawable;
            // 加载图片宽
            this.mWidth = config.mWidth;
            // 加载图片高
            this.mHeight = config.mHeight;
            // 加载缩略图时应用尺寸的乘数
            this.mThumbnail = config.mThumbnail;
            // 图片保存质量
            this.mQuality = config.mQuality;
            // 转换符合格式文件是否原图返回
            this.mOriginalPathReturn = config.mOriginalPathReturn;
            // 是否不显示动画
            this.mDontAnimate = config.mDontAnimate;
            // 是否移除所有 Transformation 效果
            this.mDontTransform = config.mDontTransform;
        }
    }

    // ==========
    // = 其他方法 =
    // ==========

    public static ImageConfig create() {
        return new ImageConfig(null);
    }

    public static ImageConfig create(final ImageConfig config) {
        return new ImageConfig(config);
    }

    /**
     * 克隆配置信息
     * @return {@link ImageConfig}
     */
    public ImageConfig clone() {
        return new ImageConfig(this);
    }

    // ===========
    // = get/set =
    // ===========

    public boolean isCacheDisk() {
        return mCacheDisk;
    }

    public ImageConfig setCacheDisk(boolean cacheDisk) {
        mCacheDisk = cacheDisk;
        return this;
    }

    public boolean isCacheMemory() {
        return mCacheMemory;
    }

    public ImageConfig setCacheMemory(boolean cacheMemory) {
        mCacheMemory = cacheMemory;
        return this;
    }

    public int getScaleType() {
        return mScaleType;
    }

    /**
     * @param scaleType {@link #SCALE_CENTER_CROP} or{@link #SCALE_FIT_CENTER}
     */
    public ImageConfig setScaleType(int scaleType) {
        mScaleType = scaleType;
        return this;
    }

    public int getTransform() {
        return mTransform;
    }

    /**
     * @param transform {@link #TRANSFORM_ROUNDED_CORNERS} or{@link #TRANSFORM_CIRCLE}
     */
    public ImageConfig setTransform(int transform) {
        mTransform = transform;
        return this;
    }

    public int getRoundedCornersRadius() {
        return mRoundedCornersRadius;
    }

    public ImageConfig setRoundedCornersRadius(int roundedCornersRadius) {
        mRoundedCornersRadius = roundedCornersRadius;
        return this;
    }

    public int getErrorPlaceholder() {
        return mErrorPlaceholder;
    }

    public ImageConfig setErrorPlaceholder(int errorPlaceholder) {
        mErrorPlaceholder = errorPlaceholder;
        mErrorDrawable    = null;
        return this;
    }

    public int getLoadingPlaceholder() {
        return mLoadingPlaceholder;
    }

    public ImageConfig setLoadingPlaceholder(int loadingPlaceholder) {
        mLoadingPlaceholder = loadingPlaceholder;
        mLoadingDrawable    = null;
        return this;
    }

    public Drawable getErrorDrawable() {
        return mErrorDrawable;
    }

    public ImageConfig setErrorDrawable(Drawable errorDrawable) {
        mErrorPlaceholder = NO_PLACE_HOLDER;
        mErrorDrawable    = errorDrawable;
        return this;
    }

    public Drawable getLoadingDrawable() {
        return mLoadingDrawable;
    }

    public ImageConfig setLoadingDrawable(Drawable loadingDrawable) {
        mLoadingPlaceholder = NO_PLACE_HOLDER;
        mLoadingDrawable    = loadingDrawable;
        return this;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public ImageConfig setSize(
            int width,
            int height
    ) {
        mWidth  = width;
        mHeight = height;
        return this;
    }

    public float getThumbnail() {
        return mThumbnail;
    }

    public ImageConfig setThumbnail(float thumbnail) {
        mThumbnail = thumbnail;
        return this;
    }

    public int getQuality() {
        return mQuality;
    }

    public ImageConfig setQuality(@IntRange(from = 0, to = 100) int quality) {
        this.mQuality = quality;
        return this;
    }

    public boolean isOriginalPathReturn() {
        return mOriginalPathReturn;
    }

    public ImageConfig setOriginalPathReturn(boolean originalPathReturn) {
        this.mOriginalPathReturn = originalPathReturn;
        return this;
    }

    public boolean isDontAnimate() {
        return mDontAnimate;
    }

    public ImageConfig setDontAnimate(boolean dontAnimate) {
        this.mDontAnimate = dontAnimate;
        return this;
    }

    public boolean isDontTransform() {
        return mDontTransform;
    }

    public ImageConfig setDontTransform(boolean dontTransform) {
        this.mDontTransform = dontTransform;
        return this;
    }
}