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

import dev.base.Source;
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
        Glide.with(fragment).pauseRequests();
    }

    @Override
    public void resume(Fragment fragment) {
        Glide.with(fragment).resumeRequests();
    }

    @Override
    public void pause(Context context) {
        Glide.with(context).pauseRequests();
    }

    @Override
    public void resume(Context context) {
        Glide.with(context).resumeRequests();
    }

    // ===========
    // = preload =
    // ===========

    @Override
    public void preload(
            Context context,
            Source source
    ) {
        RequestManager requestManager = Glide.with(context);
        setToRequest(requestManager, source).preload();
    }

    @Override
    public void preload(
            Context context,
            Source source,
            GlideConfig config
    ) {
        RequestManager requestManager = Glide.with(context);
        setToRequest(requestManager, source).preload(config.width, config.height);
    }

    // =========
    // = clear =
    // =========

    @Override
    public void clear(Context context) {
        new Thread(() -> {
            try {
                // This method must be called on a background thread.
                Glide.get(context).clearDiskCache();
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "clearDiskCache");
            }
        }).start();
    }

    @Override
    public void clear(View view) {
        Context context = view.getContext();
        if (context != null) {
            Glide.with(context).clear(view);
        }
    }

    @Override
    public void clear(
            Fragment fragment,
            View view
    ) {
        Glide.with(fragment).clear(view);
    }

    @Override
    public void clearDiskCache(Context context) {
        clear(context);
    }

    @Override
    public void clearMemoryCache(Context context) {
        try {
            // This method must be called on the main thread.
            Glide.get(context).clearMemory(); // 必须在主线程上调用该方法
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "clearMemoryCache");
        }
    }

    @Override
    public void clearAllCache(Context context) {
        clearDiskCache(context);
        clearMemoryCache(context);
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
            Source source
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
            Source source,
            GlideConfig config
    ) {

    }

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
            String url,
            GlideConfig config,
            LoadListener<T> listener
    ) {

    }

    @Override
    public <T> void display(
            ImageView imageView,
            Source source,
            LoadListener<T> listener
    ) {

    }

    @Override
    public <T> void display(
            ImageView imageView,
            Source source,
            GlideConfig config,
            LoadListener<T> listener
    ) {

    }

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
            Source source
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
            Source source,
            GlideConfig Config
    ) {

    }

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
            Source source,
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
            Source source,
            GlideConfig Config,
            LoadListener<T> listener
    ) {

    }

    @Override
    public <T> void loadImage(
            Context context,
            Source source,
            GlideConfig Config,
            LoadListener<T> listener
    ) {

    }

    @Override
    public <T> void loadImage(
            Fragment fragment,
            Source source,
            GlideConfig Config,
            LoadListener<T> listener
    ) {

    }

    @Override
    public <T> T loadImage(
            Context context,
            Source source,
            GlideConfig Config
    ) {
        return null;
    }

    @Override
    public void loadBitmap(
            Context context,
            Source source,
            GlideConfig Config,
            LoadListener<Bitmap> listener
    ) {

    }

    @Override
    public void loadBitmap(
            Fragment fragment,
            Source source,
            GlideConfig Config,
            LoadListener<Bitmap> listener
    ) {

    }

    @Override
    public Bitmap loadBitmap(
            Context context,
            Source source,
            GlideConfig Config
    ) {
        return null;
    }

    @Override
    public void loadDrawable(
            Context context,
            Source source,
            GlideConfig Config,
            LoadListener<Drawable> listener
    ) {

    }

    @Override
    public void loadDrawable(
            Fragment fragment,
            Source source,
            GlideConfig Config,
            LoadListener<Drawable> listener
    ) {

    }

    @Override
    public Drawable loadDrawable(
            Context context,
            Source source,
            GlideConfig Config
    ) {
        return null;
    }

    // ===========
    // = 内部方法 =
    // ===========

    private boolean canFragmentLoadImage(Fragment fragment) {
        return fragment.isResumed() || fragment.isAdded() || fragment.isVisible();
    }

    private RequestBuilder<?> setToRequest(
            RequestManager requestManager,
            Source source
    ) {
        if (source.mFile != null) {
            return requestManager.load(source.mFile);
        } else if (source.mUrl != null) {
            return requestManager.load(source.mUrl);
        } else if (source.mResource != 0) {
            return requestManager.load(source.mResource);
        } else if (source.mUri != null) {
            return requestManager.load(source.mUri);
        } else if (source.mBytes != null) {
            return requestManager.load(source.mBytes);
        } else {
            throw new IllegalArgumentException("UnSupport source");
        }
    }

    private <T> RequestBuilder<T> setToRequest(
            RequestBuilder<T> requestBuilder,
            Source source
    ) {
        if (source.mFile != null) {
            return requestBuilder.load(source.mFile);
        } else if (source.mUrl != null) {
            return requestBuilder.load(source.mUrl);
        } else if (source.mResource != 0) {
            return requestBuilder.load(source.mResource);
        } else if (source.mUri != null) {
            return requestBuilder.load(source.mUri);
        } else if (source.mBytes != null) {
            return requestBuilder.load(source.mBytes);
        } else {
            throw new IllegalArgumentException("UnSupport source");
        }
    }

    private RequestOptions buildRequestOptions(GlideConfig config) {
        RequestOptions requestOptions = new RequestOptions();
        return requestOptions;
    }
}