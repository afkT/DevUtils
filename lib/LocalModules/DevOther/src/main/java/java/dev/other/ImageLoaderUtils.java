package java.dev.other;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 * detail: ImageLoader 工具类
 * @author Ttt
 * @deprecated 推荐使用 DevImageEngine 实现类 GlideEngineImpl
 * <pre>
 *     init: ImageLoaderUtils.initialize(getApplicationContext());
 *     use modify: 修改 defaultOptions() 配置、以及加载默认图片资源
 * </pre>
 */
@Deprecated
public final class ImageLoaderUtils {

    private ImageLoaderUtils() {
    }

    // 图片加载中
    private static final int                 sImageLoadingRes  = 0;
    // 图片地址异常
    private static final int                 sImageUriErrorRes = 0;
    // 图片 ( 加载 / 解码 ) 失败
    private static final int                 sImageFailRes     = 0;
    // 图片默认加载配置
    private static final DisplayImageOptions DEF_OPTIONS       = defaultOptions();

    // ========
    // = init =
    // ========

    /**
     * 初始化 ImageLoader 加载配置
     * @param context {@link Context}
     */
    public static void initialize(final Context context) {
        DisplayImageOptions options = DEF_OPTIONS;
        // 针对图片缓存的全局加载配置 ( 主要有线程类、缓存大小、磁盘大小、图片下载与解析、日志方面的配置 )
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(options) // 加载 DisplayImageOptions 参数
                .threadPriority(Thread.NORM_PRIORITY - 2) // 线程池内加载的数量
                .denyCacheImageMultipleSizesInMemory() // 加载同一 URL 图片时 imageView 从小变大时从内存缓存中加载
                //.memoryCache(new UsingFreqLimitedMemoryCache(1024 * 1024)) // 通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024) // 内存缓存最大值
                .memoryCacheSizePercentage(13)
                //.diskCacheSize(50 * 1024 * 1024) // SDCard 缓存最大值 50mb
                //.discCacheFileNameGenerator(new Md5FileNameGenerator()) // 将保存的时候的 URI 名称用 MD5 加密
                //.diskCacheFileCount(100) // 缓存的文件数量
                //.memoryCache(new WeakMemoryCache()).diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(context)) // default
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    // =======================
    // = DisplayImageOptions =
    // =======================

    /**
     * 获取 DisplayImageOptions 图片加载配置
     * @return {@link DisplayImageOptions}
     */
    public static DisplayImageOptions defaultOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(sImageLoadingRes) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(sImageUriErrorRes) // 设置图片 Uri 为空或是错误的时候显示的图片
                .showImageOnFail(sImageFailRes) // 设置图片 ( 加载 / 解码 ) 过程中错误时候显示的图片
                .imageScaleType(ImageScaleType.EXACTLY) // 设置图片缩放
                .bitmapConfig(Bitmap.Config.RGB_565) // 图片解码类型
                .cacheInMemory(true) // 是否保存到内存
                .considerExifParams(false) // 是否考虑 JPEG 图片 EXIF 参数 ( 旋转、翻转 )
                .resetViewBeforeLoading(false) // default 设置图片在加载前是否重置、复位
                //.displayer(new FadeInBitmapDisplayer(100)) // 图片加载好后渐入的动画时间
                .cacheOnDisk(true).build(); // 是否保存到 SDCard 上
        return options;
    }

    /**
     * 获取图片默认加载配置
     * @param loadingRes 设置加载中显示的图片
     * @return {@link DisplayImageOptions}
     */
    public static DisplayImageOptions getDefaultImageOptions(@DrawableRes final int loadingRes) {
        DisplayImageOptions.Builder optionsBuilder = cloneImageOptions(DEF_OPTIONS);
        optionsBuilder.showImageOnLoading(loadingRes) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(loadingRes) // 设置图片 Uri 为空或是错误的时候显示的图片
                .showImageOnFail(loadingRes); // 设置图片 ( 加载 / 解码 ) 过程中错误时候显示的图片
        return optionsBuilder.build();
    }

    /**
     * 获取不使用缓存的图片加载配置
     * @return {@link DisplayImageOptions}
     */
    public static DisplayImageOptions getNoCacheImageOptions() {
        return getCacheImageOptions(false, false, -1);
    }

    /**
     * 获取不使用缓存的图片加载配置
     * @param loadingRes 设置加载中显示的图片
     * @return {@link DisplayImageOptions}
     */
    public static DisplayImageOptions getNoCacheImageOptions(@DrawableRes final int loadingRes) {
        return getCacheImageOptions(false, false, loadingRes);
    }

    /**
     * 获取 ImageLoader 图片缓存加载配置
     * @param isCache     是否缓存在内存中
     * @param isCacheDisk 是否保存在 SDCard
     * @return {@link DisplayImageOptions}
     */
    public static DisplayImageOptions getCacheImageOptions(
            final boolean isCache,
            final boolean isCacheDisk
    ) {
        return getCacheImageOptions(isCache, isCacheDisk, -1);
    }

    /**
     * 获取 ImageLoader 图片加载配置
     * @param isCache     是否缓存在内存中
     * @param isCacheDisk 是否保存在 SDCard
     * @param loadingRes  设置加载中显示的图片
     * @return {@link DisplayImageOptions}
     */
    public static DisplayImageOptions getCacheImageOptions(
            final boolean isCache,
            final boolean isCacheDisk,
            @DrawableRes final int loadingRes
    ) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY) // 设置图片缩放
                .bitmapConfig(Bitmap.Config.RGB_565) // 图片解码类型
                .cacheInMemory(isCache) // 是否保存到内存
                .displayer(new RoundedBitmapDisplayer(20))
                .cacheOnDisk(isCacheDisk); // 是否保存到 SDCard 上
        // 判断是否使用加载图片
        if (loadingRes != 0) {
            builder.showImageOnLoading(loadingRes) // 设置图片在下载期间显示的图片
                    .showImageForEmptyUri(loadingRes) // 设置图片 Uri 为空或是错误的时候显示的图片
                    .showImageOnFail(loadingRes); // 设置图片 ( 加载 / 解码 ) 过程中错误时候显示的图片
        }
        return builder.build();
    }

    /**
     * 克隆图片加载配置
     * @param options 待克隆加载配置
     * @return {@link DisplayImageOptions}
     */
    public static DisplayImageOptions.Builder cloneImageOptions(final DisplayImageOptions options) {
        return (options != null) ? new DisplayImageOptions.Builder().cloneFrom(options) : null;
    }

    // ==========
    // = 图片效果 =
    // ==========

    /**
     * 获取图片渐变动画加载配置
     * @param durationMillis 动画持续时间
     * @return {@link DisplayImageOptions}
     */
    public static DisplayImageOptions getFadeInBitmapDisplayer(final int durationMillis) {
        return getBitmapDisplayerOptions(cloneImageOptions(DEF_OPTIONS).build(), new FadeInBitmapDisplayer(durationMillis));
    }

    /**
     * 获取图片渐变动画加载配置
     * @param options        {@link DisplayImageOptions}
     * @param durationMillis 动画持续时间
     * @return {@link DisplayImageOptions}
     */
    public static DisplayImageOptions getFadeInBitmapDisplayer(
            final DisplayImageOptions options,
            final int durationMillis
    ) {
        if (options != null) {
            return getBitmapDisplayerOptions(options, new FadeInBitmapDisplayer(durationMillis));
        }
        return options;
    }

    // =

    /**
     * 获取圆角图片加载配置
     * @param cornerRadiusPixels 圆角大小
     * @return {@link DisplayImageOptions}
     */
    public static DisplayImageOptions getRoundedBitmapDisplayer(final int cornerRadiusPixels) {
        return getBitmapDisplayerOptions(cloneImageOptions(DEF_OPTIONS).build(), new RoundedBitmapDisplayer(cornerRadiusPixels));
    }

    /**
     * 获取圆角图片加载配置
     * @param options            {@link DisplayImageOptions}
     * @param cornerRadiusPixels 圆角大小
     * @return {@link DisplayImageOptions}
     */
    public static DisplayImageOptions getRoundedBitmapDisplayer(
            final DisplayImageOptions options,
            final int cornerRadiusPixels
    ) {
        if (options != null) {
            return getBitmapDisplayerOptions(options, new RoundedBitmapDisplayer(cornerRadiusPixels));
        }
        return options;
    }

    /**
     * 获取图片效果加载配置
     * @param options   {@link DisplayImageOptions}
     * @param displayer {@link BitmapDisplayer}
     * @return {@link DisplayImageOptions}
     */
    public static DisplayImageOptions getBitmapDisplayerOptions(
            final DisplayImageOptions options,
            final BitmapDisplayer displayer
    ) {
        if (options != null && displayer != null) {
            return cloneImageOptions(options).displayer(displayer).build();
        }
        return null;
    }

    // ==========
    // = 图片显示 =
    // ==========

    /**
     * 图片显示
     * @param uri       Image Uri
     * @param imageView ImageView
     */
    public static void displayImage(
            final String uri,
            final ImageView imageView
    ) {
        if (imageView != null) ImageLoader.getInstance().displayImage(uri, imageView);
    }

    /**
     * 图片显示
     * @param uri       Image Uri
     * @param imageView ImageView
     * @param options   {@link DisplayImageOptions}
     */
    public static void displayImage(
            final String uri,
            final ImageView imageView,
            final DisplayImageOptions options
    ) {
        if (imageView != null) {
            // 判断是否使用自定义图片加载配置
            if (options != null) {
                ImageLoader.getInstance().displayImage(uri, imageView, options);
            } else {
                ImageLoader.getInstance().displayImage(uri, imageView);
            }
        }
    }

    /**
     * 图片显示
     * @param uri       Image Uri
     * @param imageView ImageView
     * @param imageSize {@link ImageSize}
     */
    public static void displayImage(
            final String uri,
            final ImageView imageView,
            final ImageSize imageSize
    ) {
        if (imageView != null && imageSize != null) {
            ImageLoader.getInstance().displayImage(uri, imageView, imageSize);
        }
    }

    /**
     * 图片显示
     * @param uri        Image Uri
     * @param imageAware new ImageViewAware(imageView);
     * @param options    {@link DisplayImageOptions}
     */
    public static void displayImage(
            final String uri,
            final ImageAware imageAware,
            final DisplayImageOptions options
    ) {
        if (imageAware != null) {
            if (options != null) {
                ImageLoader.getInstance().displayImage(uri, imageAware, options);
            } else {
                ImageLoader.getInstance().displayImage(uri, imageAware);
            }
        }
    }

    /**
     * 图片显示
     * @param uri       Image Uri
     * @param imageView ImageView
     * @param listener  加载监听事件
     */
    public static void displayImage(
            final String uri,
            final ImageView imageView,
            final ImageLoadingListener listener
    ) {
        displayImage(uri, imageView, null, listener);
    }

    /**
     * 图片显示
     * @param uri       Image Uri
     * @param imageView ImageView
     * @param options   {@link DisplayImageOptions}
     * @param listener  加载监听事件
     */
    public static void displayImage(
            final String uri,
            final ImageView imageView,
            final DisplayImageOptions options,
            final ImageLoadingListener listener
    ) {
        if (imageView != null) {
            if (options != null) {
                ImageLoader.getInstance().displayImage(uri, imageView, options, listener);
            } else {
                ImageLoader.getInstance().displayImage(uri, imageView, listener);
            }
        }
    }

    /**
     * 图片显示
     * @param uri              Image Uri
     * @param imageView        ImageView
     * @param listener         加载监听事件
     * @param progressListener 图片下载监听事件
     */
    public static void displayImage(
            final String uri,
            final ImageView imageView,
            final ImageLoadingListener listener,
            final ImageLoadingProgressListener progressListener
    ) {
        displayImage(uri, imageView, null, listener, progressListener);
    }

    /**
     * 图片显示
     * @param uri              Image Uri
     * @param imageView        ImageView
     * @param options          {@link DisplayImageOptions}
     * @param listener         加载监听事件
     * @param progressListener 图片下载监听事件
     */
    public static void displayImage(
            final String uri,
            final ImageView imageView,
            final DisplayImageOptions options,
            final ImageLoadingListener listener,
            final ImageLoadingProgressListener progressListener
    ) {
        if (imageView != null) {
            ImageLoader.getInstance().displayImage(uri, imageView, options, listener, progressListener);
        }
    }

    // ==========
    // = 图片加载 =
    // ==========

    /**
     * 图片加载
     * @param uri      Image Uri
     * @param listener 加载监听事件
     */
    public static void loadImage(
            final String uri,
            final ImageLoadingListener listener
    ) {
        loadImage(uri, null, null, listener, null);
    }

    /**
     * 图片加载
     * @param uri       Image Uri
     * @param imageSize {@link ImageSize}
     * @param listener  加载监听事件
     */
    public static void loadImage(
            final String uri,
            final ImageSize imageSize,
            final ImageLoadingListener listener
    ) {
        loadImage(uri, imageSize, null, listener, null);
    }

    /**
     * 图片加载
     * @param uri              Image Uri
     * @param imageSize        {@link ImageSize}
     * @param listener         加载监听事件
     * @param progressListener 图片下载监听事件
     */
    public static void loadImage(
            final String uri,
            final ImageSize imageSize,
            final ImageLoadingListener listener,
            final ImageLoadingProgressListener progressListener
    ) {
        loadImage(uri, imageSize, null, listener, progressListener);
    }

    /**
     * 图片加载
     * @param uri              Image Uri
     * @param listener         加载监听事件
     * @param progressListener 图片下载监听事件
     */
    public static void loadImage(
            final String uri,
            final ImageLoadingListener listener,
            final ImageLoadingProgressListener progressListener
    ) {
        loadImage(uri, null, null, listener, progressListener);
    }

    /**
     * 图片加载
     * @param uri              Image Uri
     * @param options          {@link DisplayImageOptions}
     * @param listener         加载监听事件
     * @param progressListener 图片下载监听事件
     */
    public static void loadImage(
            final String uri,
            final DisplayImageOptions options,
            final ImageLoadingListener listener,
            final ImageLoadingProgressListener progressListener
    ) {
        loadImage(uri, null, options, listener, progressListener);
    }

    /**
     * 图片加载
     * @param uri              Image Uri
     * @param imageSize        {@link ImageSize}
     * @param options          {@link DisplayImageOptions}
     * @param listener         加载监听事件
     * @param progressListener 图片下载监听事件
     */
    public static void loadImage(
            final String uri,
            final ImageSize imageSize,
            final DisplayImageOptions options,
            final ImageLoadingListener listener,
            final ImageLoadingProgressListener progressListener
    ) {
        ImageLoader.getInstance().loadImage(uri, imageSize, options, listener, progressListener);
    }

    // =============
    // = 图片同步加载 =
    // =============

    /**
     * 图片同步加载
     * @param uri Image Uri
     * @return {@link Bitmap}
     */
    public static Bitmap loadImageSync(final String uri) {
        return loadImageSync(uri, null, null);
    }

    /**
     * 图片同步加载
     * @param uri       Image Uri
     * @param imageSize {@link ImageSize}
     * @return {@link Bitmap}
     */
    public static Bitmap loadImageSync(
            final String uri,
            final ImageSize imageSize
    ) {
        return loadImageSync(uri, imageSize, null);
    }

    /**
     * 图片同步加载
     * @param uri     Image Uri
     * @param options {@link DisplayImageOptions}
     * @return {@link Bitmap}
     */
    public static Bitmap loadImageSync(
            final String uri,
            final DisplayImageOptions options
    ) {
        return loadImageSync(uri, null, options);
    }

    /**
     * 图片同步加载
     * @param uri       Image Uri
     * @param imageSize {@link ImageSize}
     * @param options   {@link DisplayImageOptions}
     * @return {@link Bitmap}
     */
    public static Bitmap loadImageSync(
            final String uri,
            final ImageSize imageSize,
            final DisplayImageOptions options
    ) {
        return ImageLoader.getInstance().loadImageSync(uri, imageSize, options);
    }

    // ==========
    // = 其他操作 =
    // ==========

    /**
     * 清除磁盘缓存
     */
    public static void clearDiskCache() {
        ImageLoader.getInstance().clearDiskCache();
    }

    /**
     * 清除内存缓存
     */
    public static void clearMemoryCache() {
        ImageLoader.getInstance().clearMemoryCache();
    }

    /**
     * 获取 SDCard 缓存空间
     * @return {@link DiskCache}
     */
    public static DiskCache getDiskCache() {
        return ImageLoader.getInstance().getDiskCache();
    }

    /**
     * 获取 Memory 缓存空间
     * @return {@link MemoryCache}
     */
    public static MemoryCache getMemoryCache() {
        return ImageLoader.getInstance().getMemoryCache();
    }

    /**
     * 设置是否处理弱网情况
     * @param handleSlowNetwork 是否处理弱网情况
     */
    public static void handleSlowNetwork(final boolean handleSlowNetwork) {
        ImageLoader.getInstance().handleSlowNetwork(handleSlowNetwork);
    }

    /**
     * 设置是否禁止网络下载
     * @param denyNetworkDownloads 是否禁止网络下载
     */
    public static void denyNetworkDownloads(final boolean denyNetworkDownloads) {
        ImageLoader.getInstance().denyNetworkDownloads(denyNetworkDownloads);
    }

    // =

    /**
     * 取消图片显示任务
     * @param imageView ImageView
     */
    public static void cancelDisplayTask(final ImageView imageView) {
        if (imageView != null) {
            ImageLoader.getInstance().cancelDisplayTask(imageView);
        }
    }

    /**
     * 通过 ImageView 获取图片加载地址
     * @param imageView ImageView
     * @return Image Uri
     */
    public static String getLoadingUriForView(final ImageView imageView) {
        if (imageView != null) {
            return ImageLoader.getInstance().getLoadingUriForView(imageView);
        }
        return null;
    }

    /**
     * 设置全局加载监听事件
     * @param listener 加载监听事件
     */
    public static void setDefaultLoadingListener(final ImageLoadingListener listener) {
        ImageLoader.getInstance().setDefaultLoadingListener(listener);
    }

    // =

    /**
     * 销毁操作
     */
    public static void destroy() {
        ImageLoader.getInstance().destroy();
    }

    /**
     * 暂停图片加载
     */
    public static void pause() {
        ImageLoader.getInstance().pause();
    }

    /**
     * 恢复图片加载
     */
    public static void resume() {
        ImageLoader.getInstance().resume();
    }

    /**
     * 停止图片加载
     */
    public static void stop() {
        ImageLoader.getInstance().stop();
    }
}