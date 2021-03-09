package dev.engine.image;

import android.graphics.drawable.Drawable;

/**
 * detail: Image Config
 * @author Ttt
 */
public class ImageConfig
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

    private ImageConfig(final ImageConfig config) {
        if (config != null) {

        }
    }

    // ===========
    // = 其他方法 =
    // ===========

    public static ImageConfig create() {
        return new ImageConfig(null);
    }

    public static ImageConfig create(final ImageConfig config) {
        return new ImageConfig(config);
    }

    /**
     * 克隆配置信息
     * @param config {@link ImageConfig}
     * @return {@link ImageConfig}
     */
    public ImageConfig clone(final ImageConfig config) {
        return new ImageConfig(config);
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
        mErrorDrawable = null;
        return this;
    }

    public int getLoadingPlaceholder() {
        return mLoadingPlaceholder;
    }

    public ImageConfig setLoadingPlaceholder(int loadingPlaceholder) {
        mLoadingPlaceholder = loadingPlaceholder;
        mErrorDrawable = null;
        return this;
    }

    public Drawable getErrorDrawable() {
        return mErrorDrawable;
    }

    public ImageConfig setErrorDrawable(Drawable errorDrawable) {
        mErrorPlaceholder = NO_PLACE_HOLDER;
        mErrorDrawable = errorDrawable;
        return this;
    }

    public Drawable getLoadingDrawable() {
        return mLoadingDrawable;
    }

    public ImageConfig setLoadingDrawable(Drawable loadingDrawable) {
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

    public ImageConfig setSize(
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

    public ImageConfig setThumbnail(float thumbnail) {
        mThumbnail = thumbnail;
        return this;
    }
}