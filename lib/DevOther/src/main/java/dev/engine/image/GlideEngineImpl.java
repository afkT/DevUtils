package dev.engine.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import dev.base.DevSource;
import dev.utils.LogPrintUtils;

/**
 * detail: Glide Image Engine 实现
 * @author Ttt
 */
public class GlideEngineImpl
        implements IImageEngine<GlideConfig> {

    // 日志 TAG
    private final String TAG = GlideEngineImpl.class.getSimpleName();

    // ====================
    // = pause and resume =
    // ====================

    @Override
    public void pause(Fragment fragment) {
        if (fragment != null) {
            Glide.with(fragment).pauseRequests();
        }
    }

    @Override
    public void resume(Fragment fragment) {
        if (fragment != null) {
            Glide.with(fragment).resumeRequests();
        }
    }

    @Override
    public void pause(Context context) {
        if (context != null) {
            Glide.with(context).pauseRequests();
        }
    }

    @Override
    public void resume(Context context) {
        if (context != null) {
            Glide.with(context).resumeRequests();
        }
    }

    // ===========
    // = preload =
    // ===========

    @Override
    public void preload(
            Context context,
            DevSource source
    ) {
        if (context != null && source != null) {
            RequestManager requestManager = Glide.with(context);
            setToRequest(requestManager, source).preload();
        }
    }

    @Override
    public void preload(
            Context context,
            DevSource source,
            GlideConfig config
    ) {
        if (context != null && source != null && config != null) {
            RequestManager requestManager = Glide.with(context);
            setToRequest(requestManager, source).preload(
                    config.getWidth(), config.getHeight()
            );
        }
    }

    // =========
    // = clear =
    // =========

    @Override
    public void clear(View view) {
        if (view != null && view.getContext() != null) {
            Glide.with(view.getContext()).clear(view);
        }
    }

    @Override
    public void clear(
            Fragment fragment,
            View view
    ) {
        if (fragment != null && view != null) {
            Glide.with(fragment).clear(view);
        }
    }

    @Override
    public void clearDiskCache(Context context) {
        if (context != null) {
            new Thread(() -> {
                try {
                    // This method must be called on a background thread.
                    Glide.get(context).clearDiskCache();
                } catch (Exception e) {
                    LogPrintUtils.eTag(TAG, e, "clearDiskCache");
                }
            }).start();
        }
    }

    @Override
    public void clearMemoryCache(Context context) {
        if (context != null) {
            try {
                // This method must be called on the main thread.
                Glide.get(context).clearMemory(); // 必须在主线程上调用该方法
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "clearMemoryCache");
            }
        }
    }

    @Override
    public void clearAllCache(Context context) {
        clearDiskCache(context);
        clearMemoryCache(context);
    }

    // =========
    // = other =
    // =========

    @Override
    public void lowMemory(Context context) {
        if (context != null) {
            try {
                Glide.get(context).onLowMemory();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "lowMemory");
            }
        }
    }

    // ===========
    // = display =
    // ===========

    @Override
    public void display(
            ImageView imageView,
            String url
    ) {
        display(imageView, DevSource.create(url), (GlideConfig) null);
    }

    @Override
    public void display(
            ImageView imageView,
            DevSource source
    ) {
        display(imageView, source, (GlideConfig) null);
    }

    @Override
    public void display(
            ImageView imageView,
            String url,
            GlideConfig config
    ) {
        display(imageView, DevSource.create(url), config);
    }

    @Override
    public void display(
            ImageView imageView,
            DevSource source,
            GlideConfig config
    ) {
        if (imageView != null && imageView.getContext() != null) {
            RequestManager requestManager = Glide.with(imageView.getContext());
            priDisplayToRequestBuilder(
                    imageView,
                    setToRequest(requestManager, source),
                    config
            );
        }
    }

    // =

    @Override
    public <T> void display(
            ImageView imageView,
            String url,
            LoadListener<T> listener
    ) {
        display(imageView, DevSource.create(url), null, listener);
    }

    @Override
    public <T> void display(
            ImageView imageView,
            DevSource source,
            LoadListener<T> listener
    ) {
        display(imageView, source, null, listener);
    }

    @Override
    public <T> void display(
            ImageView imageView,
            String url,
            GlideConfig config,
            LoadListener<T> listener
    ) {
        display(imageView, DevSource.create(url), config, listener);
    }

    @Override
    public <T> void display(
            ImageView imageView,
            DevSource source,
            GlideConfig config,
            LoadListener<T> listener
    ) {

    }

    // =

    @Override
    public void display(
            Fragment fragment,
            ImageView imageView,
            String url
    ) {
        display(fragment, imageView, DevSource.create(url), (GlideConfig) null);
    }

    @Override
    public void display(
            Fragment fragment,
            ImageView imageView,
            DevSource source
    ) {
        display(fragment, imageView, source, (GlideConfig) null);
    }

    @Override
    public void display(
            Fragment fragment,
            ImageView imageView,
            String url,
            GlideConfig config
    ) {
        display(fragment, imageView, DevSource.create(url), config);
    }

    @Override
    public void display(
            Fragment fragment,
            ImageView imageView,
            DevSource source,
            GlideConfig config
    ) {

    }

    // =

    @Override
    public <T> void display(
            Fragment fragment,
            ImageView imageView,
            String url,
            LoadListener<T> listener
    ) {
        display(fragment, imageView, DevSource.create(url), null, listener);
    }

    @Override
    public <T> void display(
            Fragment fragment,
            ImageView imageView,
            DevSource source,
            LoadListener<T> listener
    ) {
        display(fragment, imageView, source, null, listener);
    }

    @Override
    public <T> void display(
            Fragment fragment,
            ImageView imageView,
            String url,
            GlideConfig config,
            LoadListener<T> listener
    ) {
        display(fragment, imageView, DevSource.create(url), config, listener);
    }

    @Override
    public <T> void display(
            Fragment fragment,
            ImageView imageView,
            DevSource source,
            GlideConfig config,
            LoadListener<T> listener
    ) {

    }

    // ========
    // = load =
    // ========

    @Override
    public <T> void loadImage(
            Context context,
            DevSource source,
            GlideConfig config,
            LoadListener<T> listener
    ) {

    }

    @Override
    public <T> void loadImage(
            Fragment fragment,
            DevSource source,
            GlideConfig config,
            LoadListener<T> listener
    ) {

    }

    @Override
    public <T> T loadImage(
            Context context,
            DevSource source,
            GlideConfig config
    ) {
        return null;
    }

    // =

    @Override
    public void loadBitmap(
            Context context,
            DevSource source,
            GlideConfig config,
            LoadListener<Bitmap> listener
    ) {
        loadImage(context, source, config, listener);
    }

    @Override
    public void loadBitmap(
            Fragment fragment,
            DevSource source,
            GlideConfig config,
            LoadListener<Bitmap> listener
    ) {
        loadImage(fragment, source, config, listener);
    }

    @Override
    public Bitmap loadBitmap(
            Context context,
            DevSource source,
            GlideConfig config
    ) {
        return loadImage(context, source, config);
    }

    // =

    @Override
    public void loadDrawable(
            Context context,
            DevSource source,
            GlideConfig config,
            LoadListener<Drawable> listener
    ) {
        loadImage(context, source, config, listener);
    }

    @Override
    public void loadDrawable(
            Fragment fragment,
            DevSource source,
            GlideConfig config,
            LoadListener<Drawable> listener
    ) {
        loadImage(fragment, source, config, listener);
    }

    @Override
    public Drawable loadDrawable(
            Context context,
            DevSource source,
            GlideConfig config
    ) {
        return loadImage(context, source, config);
    }

    // ===========
    // = 内部方法 =
    // ===========

    /**
     * Fragment 是否能够用于加载图片
     * @param fragment {@link Fragment}
     * @return {@code true} yes, {@code false} no
     */
    private boolean canFragmentLoadImage(Fragment fragment) {
        return fragment.isResumed() || fragment.isAdded() || fragment.isVisible();
    }

    /**
     * 通过 {@link DevSource} 设置 {@link RequestBuilder} 加载 source
     * @param manager {@link RequestManager}
     * @param source  {@link DevSource}
     * @return {@link RequestBuilder}
     */
    private RequestBuilder<?> setToRequest(
            RequestManager manager,
            DevSource source
    ) {
        if (manager != null && source != null) {
            if (source.mFile != null) {
                return manager.load(source.mFile);
            } else if (source.mUrl != null) {
                return manager.load(source.mUrl);
            } else if (source.mResource != 0) {
                return manager.load(source.mResource);
            } else if (source.mUri != null) {
                return manager.load(source.mUri);
            } else if (source.mBytes != null) {
                return manager.load(source.mBytes);
            } else {
                throw new IllegalArgumentException("UnSupport source");
            }
        }
        return null;
    }

    /**
     * 通过 {@link DevSource} 设置 {@link RequestBuilder} 加载 source
     * @param request {@link RequestBuilder}
     * @param source  {@link DevSource}
     * @param <T>     泛型 ( 如 Drawable、Bitmap )
     * @return {@link RequestBuilder}
     */
    private <T> RequestBuilder<T> setToRequest(
            RequestBuilder<T> request,
            DevSource source
    ) {
        if (request != null && source != null) {
            if (source.mFile != null) {
                return request.load(source.mFile);
            } else if (source.mUrl != null) {
                return request.load(source.mUrl);
            } else if (source.mResource != 0) {
                return request.load(source.mResource);
            } else if (source.mUri != null) {
                return request.load(source.mUri);
            } else if (source.mBytes != null) {
                return request.load(source.mBytes);
            } else {
                throw new IllegalArgumentException("UnSupport source");
            }
        }
        return null;
    }

    /**
     * 通过 {@link RequestBuilder} 与 {@link GlideConfig} 快捷显示方法
     * @param imageView {@link ImageView}
     * @param request   {@link RequestBuilder}
     * @param config    {@link GlideConfig}
     */
    private void priDisplayToRequestBuilder(
            ImageView imageView,
            RequestBuilder request,
            GlideConfig config
    ) {
        if (imageView != null && request != null) {
            RequestOptions options = buildRequestOptions(config);
            request = request.apply(options);
            if (config != null) {
                if (config.getThumbnail() > 0F) {
                    request = request.thumbnail(config.getThumbnail());
                }
            }
            request.into(imageView);
        }
    }

    /**
     * 通过 {@link GlideConfig} 构建 {@link RequestOptions}
     * @param config {@link GlideConfig}
     * @return {@link RequestOptions}
     */
    private RequestOptions buildRequestOptions(GlideConfig config) {
        RequestOptions options = new RequestOptions();
        if (config != null) {
        }
        return options;
    }
}