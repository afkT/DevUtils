package dev.engine.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.util.List;

import dev.base.DevSource;
import dev.engine.image.listener.LoadListener;
import dev.engine.image.listener.OnConvertListener;

/**
 * detail: Image Engine 接口
 * @author Ttt
 */
public interface IImageEngine<Config extends IImageEngine.EngineConfig> {

    /**
     * detail: Image Config
     * @author Ttt
     */
    interface EngineConfig {
    }

    // ====================
    // = pause and resume =
    // ====================

    /**
     * 暂停 Fragment 关联的图片加载请求
     * @param fragment {@link Fragment}
     */
    void pause(Fragment fragment);

    /**
     * 恢复 Fragment 关联的图片加载请求
     * @param fragment {@link Fragment}
     */
    void resume(Fragment fragment);

    /**
     * 暂停 Context 关联的图片加载请求
     * @param context {@link Context}
     */
    void pause(Context context);

    /**
     * 恢复 Context 关联的图片加载请求
     * @param context {@link Context}
     */
    void resume(Context context);

    // ===========
    // = preload =
    // ===========

    /**
     * 预加载图片资源
     * @param context {@link Context}
     * @param source  数据来源
     */
    void preload(Context context, DevSource source);

    /**
     * 预加载图片资源
     * @param context {@link Context}
     * @param source  数据来源
     * @param config  配置信息
     */
    void preload(Context context, DevSource source, Config config);

    // =========
    // = clear =
    // =========

    /**
     * 清除 View 关联的图片加载
     * @param view {@link View}
     */
    void clear(View view);

    /**
     * 清除 Fragment 与 View 关联的图片加载
     * @param fragment {@link Fragment}
     * @param view     {@link View}
     */
    void clear(Fragment fragment, View view);

    /**
     * 清除磁盘缓存
     * @param context {@link Context}
     */
    void clearDiskCache(Context context);

    /**
     * 清除内存缓存
     * @param context {@link Context}
     */
    void clearMemoryCache(Context context);

    /**
     * 清除全部缓存
     * @param context {@link Context}
     */
    void clearAllCache(Context context);

    // =========
    // = other =
    // =========

    /**
     * 低内存回调处理
     * @param context {@link Context}
     */
    void lowMemory(Context context);

    // ===========
    // = display =
    // ===========

    /**
     * 加载图片并显示到 ImageView
     * @param imageView {@link ImageView}
     * @param url       图片地址
     */
    void display(ImageView imageView, String url);

    /**
     * 加载图片并显示到 ImageView
     * @param imageView {@link ImageView}
     * @param source    数据来源
     */
    void display(ImageView imageView, DevSource source);

    /**
     * 加载图片并显示到 ImageView
     * @param imageView {@link ImageView}
     * @param url       图片地址
     * @param config    配置信息
     */
    void display(ImageView imageView, String url, Config config);

    /**
     * 加载图片并显示到 ImageView
     * @param imageView {@link ImageView}
     * @param source    数据来源
     * @param config    配置信息
     */
    void display(ImageView imageView, DevSource source, Config config);

    /**
     * 加载图片并显示到 ImageView
     * @param imageView {@link ImageView}
     * @param url       图片地址
     * @param listener  加载监听
     * @param <T>       泛型
     */
    <T> void display(ImageView imageView, String url, LoadListener<T> listener);

    /**
     * 加载图片并显示到 ImageView
     * @param imageView {@link ImageView}
     * @param source    数据来源
     * @param listener  加载监听
     * @param <T>       泛型
     */
    <T> void display(ImageView imageView, DevSource source, LoadListener<T> listener);

    /**
     * 加载图片并显示到 ImageView
     * @param imageView {@link ImageView}
     * @param url       图片地址
     * @param config    配置信息
     * @param listener  加载监听
     * @param <T>       泛型
     */
    <T> void display(ImageView imageView, String url, Config config, LoadListener<T> listener);

    /**
     * 加载图片并显示到 ImageView
     * @param imageView {@link ImageView}
     * @param source    数据来源
     * @param config    配置信息
     * @param listener  加载监听
     * @param <T>       泛型
     */
    <T> void display(ImageView imageView, DevSource source, Config config, LoadListener<T> listener);

    // =

    /**
     * 加载图片并显示到 ImageView
     * @param fragment  {@link Fragment}
     * @param imageView {@link ImageView}
     * @param url       图片地址
     */
    void display(Fragment fragment, ImageView imageView, String url);

    /**
     * 加载图片并显示到 ImageView
     * @param fragment  {@link Fragment}
     * @param imageView {@link ImageView}
     * @param source    数据来源
     */
    void display(Fragment fragment, ImageView imageView, DevSource source);

    /**
     * 加载图片并显示到 ImageView
     * @param fragment  {@link Fragment}
     * @param imageView {@link ImageView}
     * @param url       图片地址
     * @param config    配置信息
     */
    void display(Fragment fragment, ImageView imageView, String url, Config config);

    /**
     * 加载图片并显示到 ImageView
     * @param fragment  {@link Fragment}
     * @param imageView {@link ImageView}
     * @param source    数据来源
     * @param config    配置信息
     */
    void display(Fragment fragment, ImageView imageView, DevSource source, Config config);

    /**
     * 加载图片并显示到 ImageView
     * @param fragment  {@link Fragment}
     * @param imageView {@link ImageView}
     * @param url       图片地址
     * @param listener  加载监听
     * @param <T>       泛型
     */
    <T> void display(Fragment fragment, ImageView imageView, String url, LoadListener<T> listener);

    /**
     * 加载图片并显示到 ImageView
     * @param fragment  {@link Fragment}
     * @param imageView {@link ImageView}
     * @param source    数据来源
     * @param listener  加载监听
     * @param <T>       泛型
     */
    <T> void display(Fragment fragment, ImageView imageView, DevSource source, LoadListener<T> listener);

    /**
     * 加载图片并显示到 ImageView
     * @param fragment  {@link Fragment}
     * @param imageView {@link ImageView}
     * @param url       图片地址
     * @param config    配置信息
     * @param listener  加载监听
     * @param <T>       泛型
     */
    <T> void display(Fragment fragment, ImageView imageView, String url, Config config, LoadListener<T> listener);

    /**
     * 加载图片并显示到 ImageView
     * @param fragment  {@link Fragment}
     * @param imageView {@link ImageView}
     * @param source    数据来源
     * @param config    配置信息
     * @param listener  加载监听
     * @param <T>       泛型
     */
    <T> void display(Fragment fragment, ImageView imageView, DevSource source, Config config, LoadListener<T> listener);

    // ========
    // = load =
    // ========

    /**
     * 加载图片
     * @param context  {@link Context}
     * @param source   数据来源
     * @param config   配置信息
     * @param listener 加载监听
     * @param <T>      泛型
     */
    <T> void loadImage(Context context, DevSource source, Config config, LoadListener<T> listener);

    /**
     * 加载图片
     * @param fragment {@link Fragment}
     * @param source   数据来源
     * @param config   配置信息
     * @param listener 加载监听
     * @param <T>      泛型
     */
    <T> void loadImage(Fragment fragment, DevSource source, Config config, LoadListener<T> listener);

    /**
     * 加载图片
     * @param context {@link Context}
     * @param source  数据来源
     * @param config  配置信息
     * @param type    目标类型
     * @param <T>     泛型
     * @return instance of type
     */
    <T> T loadImage(Context context, DevSource source, Config config, Class<?> type);

    /**
     * 加载图片
     * @param context {@link Context}
     * @param source  数据来源
     * @param config  配置信息
     * @param type    目标类型
     * @param <T>     泛型
     * @return instance of type
     */
    <T> T loadImageThrows(Context context, DevSource source, Config config, Class<?> type) throws Exception;

    // =

    /**
     * 加载 Bitmap
     * @param context  {@link Context}
     * @param source   数据来源
     * @param config   配置信息
     * @param listener 加载监听
     */
    void loadBitmap(Context context, DevSource source, Config config, LoadListener<Bitmap> listener);

    /**
     * 加载 Bitmap
     * @param fragment {@link Fragment}
     * @param source   数据来源
     * @param config   配置信息
     * @param listener 加载监听
     */
    void loadBitmap(Fragment fragment, DevSource source, Config config, LoadListener<Bitmap> listener);

    /**
     * 加载 Bitmap
     * @param context {@link Context}
     * @param source  数据来源
     * @param config  配置信息
     * @return {@link Bitmap}
     */
    Bitmap loadBitmap(Context context, DevSource source, Config config);

    /**
     * 加载 Bitmap
     * @param context {@link Context}
     * @param source  数据来源
     * @param config  配置信息
     * @return {@link Bitmap}
     */
    Bitmap loadBitmapThrows(Context context, DevSource source, Config config) throws Exception;

    // =

    /**
     * 加载 Drawable
     * @param context  {@link Context}
     * @param source   数据来源
     * @param config   配置信息
     * @param listener 加载监听
     */
    void loadDrawable(Context context, DevSource source, Config config, LoadListener<Drawable> listener);

    /**
     * 加载 Drawable
     * @param fragment {@link Fragment}
     * @param source   数据来源
     * @param config   配置信息
     * @param listener 加载监听
     */
    void loadDrawable(Fragment fragment, DevSource source, Config config, LoadListener<Drawable> listener);

    /**
     * 加载 Drawable
     * @param context {@link Context}
     * @param source  数据来源
     * @param config  配置信息
     * @return {@link Drawable}
     */
    Drawable loadDrawable(Context context, DevSource source, Config config);

    /**
     * 加载 Drawable
     * @param context {@link Context}
     * @param source  数据来源
     * @param config  配置信息
     * @return {@link Drawable}
     */
    Drawable loadDrawableThrows(Context context, DevSource source, Config config) throws Exception;

    // ===========
    // = convert =
    // ===========

    /**
     * 转换图片格式
     * @param context  {@link Context}
     * @param sources  数据来源列表
     * @param listener 转换监听
     * @return {@code true} success, {@code false} fail
     */
    boolean convertImageFormat(
            Context context, List<DevSource> sources,
            OnConvertListener listener
    );

    /**
     * 转换图片格式
     * @param context  {@link Context}
     * @param sources  数据来源列表
     * @param config   配置信息
     * @param listener 转换监听
     * @return {@code true} success, {@code false} fail
     */
    boolean convertImageFormat(
            Context context, List<DevSource> sources,
            Config config, OnConvertListener listener
    );
}