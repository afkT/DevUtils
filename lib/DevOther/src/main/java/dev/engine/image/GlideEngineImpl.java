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
            setToRequest(requestManager, source).preload(config.width, config.height);
        }
    }

    // =========
    // = clear =
    // =========

    @Override
    public void clear(View view) {
        if (view != null && view.getContext() != null) {
            Context context = view.getContext();
            Glide.with(context).clear(view);
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

    }

    @Override
    public void display(
            ImageView imageView,
            DevSource source
    ) {

    }

    @Override
    public void display(
            ImageView imageView,
            String url,
            GlideConfig config
    ) {

    }

    @Override
    public void display(
            ImageView imageView,
            DevSource source,
            GlideConfig config
    ) {

    }

    // =

    @Override
    public <T> void display(
            ImageView imageView,
            String url,
            LoadListener<T> listener
    ) {

    }

    @Override
    public <T> void display(
            ImageView imageView,
            DevSource source,
            LoadListener<T> listener
    ) {

    }

    @Override
    public <T> void display(
            ImageView imageView,
            String url,
            GlideConfig config,
            LoadListener<T> listener
    ) {

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

    }

    @Override
    public void display(
            Fragment fragment,
            ImageView imageView,
            DevSource source
    ) {

    }

    @Override
    public void display(
            Fragment fragment,
            ImageView imageView,
            String url,
            GlideConfig Config
    ) {

    }

    @Override
    public void display(
            Fragment fragment,
            ImageView imageView,
            DevSource source,
            GlideConfig Config
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

    }

    @Override
    public <T> void display(
            Fragment fragment,
            ImageView imageView,
            DevSource source,
            LoadListener<T> listener
    ) {

    }

    @Override
    public <T> void display(
            Fragment fragment,
            ImageView imageView,
            String url,
            GlideConfig Config,
            LoadListener<T> listener
    ) {

    }

    @Override
    public <T> void display(
            Fragment fragment,
            ImageView imageView,
            DevSource source,
            GlideConfig Config,
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
            GlideConfig Config,
            LoadListener<T> listener
    ) {

    }

    @Override
    public <T> void loadImage(
            Fragment fragment,
            DevSource source,
            GlideConfig Config,
            LoadListener<T> listener
    ) {

    }

    @Override
    public <T> T loadImage(
            Context context,
            DevSource source,
            GlideConfig Config
    ) {
        return null;
    }

    // =

    @Override
    public void loadBitmap(
            Context context,
            DevSource source,
            GlideConfig Config,
            LoadListener<Bitmap> listener
    ) {

    }

    @Override
    public void loadBitmap(
            Fragment fragment,
            DevSource source,
            GlideConfig Config,
            LoadListener<Bitmap> listener
    ) {

    }

    @Override
    public Bitmap loadBitmap(
            Context context,
            DevSource source,
            GlideConfig Config
    ) {
        return null;
    }

    // =

    @Override
    public void loadDrawable(
            Context context,
            DevSource source,
            GlideConfig Config,
            LoadListener<Drawable> listener
    ) {

    }

    @Override
    public void loadDrawable(
            Fragment fragment,
            DevSource source,
            GlideConfig Config,
            LoadListener<Drawable> listener
    ) {

    }

    @Override
    public Drawable loadDrawable(
            Context context,
            DevSource source,
            GlideConfig Config
    ) {
        return null;
    }

    // ===========
    // = 内部方法 =
    // ===========

    private boolean canFragmentLoadImage(Fragment fragment) {
        if (fragment == null) return false;
        return fragment.isResumed() || fragment.isAdded() || fragment.isVisible();
    }

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

    private <T> RequestBuilder<T> setToRequest(
            RequestBuilder<T> builder,
            DevSource source
    ) {
        if (builder != null && source != null) {
            if (source.mFile != null) {
                return builder.load(source.mFile);
            } else if (source.mUrl != null) {
                return builder.load(source.mUrl);
            } else if (source.mResource != 0) {
                return builder.load(source.mResource);
            } else if (source.mUri != null) {
                return builder.load(source.mUri);
            } else if (source.mBytes != null) {
                return builder.load(source.mBytes);
            } else {
                throw new IllegalArgumentException("UnSupport source");
            }
        }
        return null;
    }

    private RequestOptions buildRequestOptions(GlideConfig config) {
        RequestOptions options = new RequestOptions();
        if (config != null) {
        }
        return options;
    }
}