package dev.engine.image;

import android.graphics.drawable.Drawable;

/**
 * detail: Glide Image Config
 * @author Ttt
 */
public class GlideConfig
        extends IImageEngine.ImageConfig {

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

    // ===========
    // = 构造函数 =
    // ===========

    private GlideConfig(final GlideConfig config) {
        if (config != null) {

        }
    }

    // ===========
    // = 其他方法 =
    // ===========

    public static GlideConfig create() {
        return new GlideConfig(null);
    }

    public static GlideConfig create(final GlideConfig config) {
        return new GlideConfig(config);
    }

    /**
     * 克隆配置信息
     * @param config {@link GlideConfig}
     * @return {@link GlideConfig}
     */
    public GlideConfig clone(final GlideConfig config) {
        return new GlideConfig(config);
    }

    // ===========
    // = get/set =
    // ===========

    public boolean isCacheDisk() {
        return mCacheDisk;
    }

    public GlideConfig setCacheDisk(boolean cacheDisk) {
        mCacheDisk = cacheDisk;
        return this;
    }

    public boolean isCacheMemory() {
        return mCacheMemory;
    }

    public GlideConfig setCacheMemory(boolean cacheMemory) {
        mCacheMemory = cacheMemory;
        return this;
    }

    public int getScaleType() {
        return mScaleType;
    }

    /**
     * @param scaleType {@link #SCALE_CENTER_CROP} or{@link #SCALE_FIT_CENTER}
     */
    public GlideConfig setScaleType(int scaleType) {
        mScaleType = scaleType;
        return this;
    }

    public int getTransform() {
        return mTransform;
    }

    /**
     * @param transform {@link #TRANSFORM_ROUNDED_CORNERS} or{@link #TRANSFORM_CIRCLE}
     */
    public GlideConfig setTransform(int transform) {
        mTransform = transform;
        return this;
    }

    public int getRoundedCornersRadius() {
        return mRoundedCornersRadius;
    }

    public GlideConfig setRoundedCornersRadius(int roundedCornersRadius) {
        mRoundedCornersRadius = roundedCornersRadius;
        return this;
    }

    public int getErrorPlaceholder() {
        return mErrorPlaceholder;
    }

    public GlideConfig setErrorPlaceholder(int errorPlaceholder) {
        mErrorPlaceholder = errorPlaceholder;
        mErrorDrawable = null;
        return this;
    }

    public int getLoadingPlaceholder() {
        return mLoadingPlaceholder;
    }

    public GlideConfig setLoadingPlaceholder(int loadingPlaceholder) {
        mLoadingPlaceholder = loadingPlaceholder;
        mErrorDrawable = null;
        return this;
    }

    public Drawable getErrorDrawable() {
        return mErrorDrawable;
    }

    public GlideConfig setErrorDrawable(Drawable errorDrawable) {
        mErrorPlaceholder = NO_PLACE_HOLDER;
        mErrorDrawable = errorDrawable;
        return this;
    }

    public Drawable getLoadingDrawable() {
        return mLoadingDrawable;
    }

    public GlideConfig setLoadingDrawable(Drawable loadingDrawable) {
        mLoadingPlaceholder = NO_PLACE_HOLDER;
        mLoadingDrawable = loadingDrawable;
        return this;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public GlideConfig setSize(
            int width,
            int height
    ) {
        mWidth = width;
        mHeight = height;
        return this;
    }

    public float getThumbnail() {
        return mThumbnail;
    }

    public GlideConfig setThumbnail(float thumbnail) {
        mThumbnail = thumbnail;
        return this;
    }
}