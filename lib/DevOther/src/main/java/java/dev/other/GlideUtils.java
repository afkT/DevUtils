package java.dev.other;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.dev.engine.image.GlideEngineImpl;
import java.dev.engine.image.ImageConfig;
import java.util.List;

import dev.base.DevSource;
import dev.engine.image.listener.LoadListener;
import dev.engine.image.listener.OnConvertListener;

/**
 * detail: Glide 工具类
 * @author Ttt
 * @deprecated 推荐使用 {@link dev.engine.image.DevImageEngine} 实现类 {@link GlideEngineImpl}
 */
@Deprecated
public final class GlideUtils {

    private GlideUtils() {
    }

    private static final GlideEngineImpl IMPL = new GlideEngineImpl();

    // ====================
    // = pause and resume =
    // ====================

    public static void pause(Fragment fragment) {
        IMPL.pause(fragment);
    }

    public static void resume(Fragment fragment) {
        IMPL.resume(fragment);
    }

    public static void pause(Context context) {
        IMPL.pause(context);
    }

    public static void resume(Context context) {
        IMPL.resume(context);
    }

    // ===========
    // = preload =
    // ===========

    public static void preload(
            Context context,
            DevSource source
    ) {
        IMPL.preload(context, source);
    }

    public static void preload(
            Context context,
            DevSource source,
            ImageConfig config
    ) {
        IMPL.preload(context, source, config);
    }

    // =========
    // = clear =
    // =========

    public static void clear(View view) {
        IMPL.clear(view);
    }

    public static void clear(
            Fragment fragment,
            View view
    ) {
        IMPL.clear(fragment, view);
    }

    public static void clearDiskCache(Context context) {
        IMPL.clearDiskCache(context);
    }

    public static void clearMemoryCache(Context context) {
        IMPL.clearMemoryCache(context);
    }

    public static void clearAllCache(Context context) {
        IMPL.clearAllCache(context);
    }

    // =========
    // = other =
    // =========

    public static void lowMemory(Context context) {
        IMPL.lowMemory(context);
    }

    // ===========
    // = display =
    // ===========

    public static void display(
            ImageView imageView,
            String url
    ) {
        IMPL.display(imageView, url);
    }

    public static void display(
            ImageView imageView,
            DevSource source
    ) {
        IMPL.display(imageView, source);
    }

    public static void display(
            ImageView imageView,
            String url,
            ImageConfig config
    ) {
        IMPL.display(imageView, url, config);
    }

    public static void display(
            ImageView imageView,
            DevSource source,
            ImageConfig config
    ) {
        IMPL.display(imageView, source, config);
    }

    // =

    public static <T> void display(
            ImageView imageView,
            String url,
            LoadListener<T> listener
    ) {
        IMPL.display(imageView, url, listener);
    }

    public static <T> void display(
            ImageView imageView,
            DevSource source,
            LoadListener<T> listener
    ) {
        IMPL.display(imageView, source, listener);
    }

    public static <T> void display(
            ImageView imageView,
            String url,
            ImageConfig config,
            LoadListener<T> listener
    ) {
        IMPL.display(imageView, url, config, listener);
    }

    public static <T> void display(
            ImageView imageView,
            DevSource source,
            ImageConfig config,
            LoadListener<T> listener
    ) {
        IMPL.display(imageView, source, config, listener);
    }

    // =

    public static void display(
            Fragment fragment,
            ImageView imageView,
            String url
    ) {
        IMPL.display(fragment, imageView, url);
    }

    public static void display(
            Fragment fragment,
            ImageView imageView,
            DevSource source
    ) {
        IMPL.display(fragment, imageView, source);
    }

    public static void display(
            Fragment fragment,
            ImageView imageView,
            String url,
            ImageConfig config
    ) {
        IMPL.display(fragment, imageView, url, config);
    }

    public static void display(
            Fragment fragment,
            ImageView imageView,
            DevSource source,
            ImageConfig config
    ) {
        IMPL.display(fragment, imageView, source, config);
    }

    // =

    public static <T> void display(
            Fragment fragment,
            ImageView imageView,
            String url,
            LoadListener<T> listener
    ) {
        IMPL.display(fragment, imageView, url, listener);
    }

    public static <T> void display(
            Fragment fragment,
            ImageView imageView,
            DevSource source,
            LoadListener<T> listener
    ) {
        IMPL.display(fragment, imageView, source, listener);
    }

    public static <T> void display(
            Fragment fragment,
            ImageView imageView,
            String url,
            ImageConfig config,
            LoadListener<T> listener
    ) {
        IMPL.display(fragment, imageView, url, config, listener);
    }

    public static <T> void display(
            Fragment fragment,
            ImageView imageView,
            DevSource source,
            ImageConfig config,
            LoadListener<T> listener
    ) {
        IMPL.display(fragment, imageView, source, config, listener);
    }

    // ========
    // = load =
    // ========

    public static <T> void loadImage(
            Context context,
            DevSource source,
            ImageConfig config,
            LoadListener<T> listener
    ) {
        IMPL.loadImage(context, source, config, listener);
    }

    public static <T> void loadImage(
            Fragment fragment,
            DevSource source,
            ImageConfig config,
            LoadListener<T> listener
    ) {
        IMPL.loadImage(fragment, source, config, listener);
    }

    public static <T> T loadImage(
            Context context,
            DevSource source,
            ImageConfig config,
            Class<?> type
    ) {
        return IMPL.loadImage(context, source, config, type);
    }

    public static <T> T loadImageThrows(
            Context context,
            DevSource source,
            ImageConfig config,
            Class<?> type
    )
            throws Exception {
        return IMPL.loadImageThrows(context, source, config, type);
    }

    // =

    public static void loadBitmap(
            Context context,
            DevSource source,
            ImageConfig config,
            LoadListener<Bitmap> listener
    ) {
        IMPL.loadBitmap(context, source, config, listener);
    }

    public static void loadBitmap(
            Fragment fragment,
            DevSource source,
            ImageConfig config,
            LoadListener<Bitmap> listener
    ) {
        IMPL.loadBitmap(fragment, source, config, listener);
    }

    public static Bitmap loadBitmap(
            Context context,
            DevSource source,
            ImageConfig config
    ) {
        return IMPL.loadBitmap(context, source, config);
    }

    public static Bitmap loadBitmapThrows(
            Context context,
            DevSource source,
            ImageConfig config
    )
            throws Exception {
        return IMPL.loadBitmapThrows(context, source, config);
    }

    // =

    public static void loadDrawable(
            Context context,
            DevSource source,
            ImageConfig config,
            LoadListener<Drawable> listener
    ) {
        IMPL.loadDrawable(context, source, config, listener);
    }

    public static void loadDrawable(
            Fragment fragment,
            DevSource source,
            ImageConfig config,
            LoadListener<Drawable> listener
    ) {
        IMPL.loadDrawable(fragment, source, config, listener);
    }

    public static Drawable loadDrawable(
            Context context,
            DevSource source,
            ImageConfig config
    ) {
        return IMPL.loadDrawable(context, source, config);
    }

    public static Drawable loadDrawableThrows(
            Context context,
            DevSource source,
            ImageConfig config
    )
            throws Exception {
        return IMPL.loadDrawableThrows(context, source, config);
    }

    // ===========
    // = convert =
    // ===========

    public static boolean convertImageFormat(
            Context context,
            List<DevSource> sources,
            OnConvertListener listener
    ) {
        return IMPL.convertImageFormat(context, sources, listener);
    }

    public static boolean convertImageFormat(
            Context context,
            List<DevSource> sources,
            ImageConfig config,
            OnConvertListener listener
    ) {
        return IMPL.convertImageFormat(context, sources, config, listener);
    }
}