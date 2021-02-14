package dev.engine.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import dev.base.DevSource;

/**
 * detail: Image Engine 接口
 * @author Ttt
 */
public interface IImageEngine<Config extends IImageEngine.ImageConfig> {

    /**
     * detail: Image Config
     * @author Ttt
     */
    class ImageConfig {
    }

    // ====================
    // = pause and resume =
    // ====================

    void pause(Fragment fragment);

    void resume(Fragment fragment);

    void pause(Context context);

    void resume(Context context);

    // ===========
    // = preload =
    // ===========

    void preload(Context context, DevSource source);

    void preload(Context context, DevSource source, Config config);

    // =========
    // = clear =
    // =========

    void clear(View view);

    void clear(Fragment fragment, View view);

    void clearDiskCache(Context context);

    void clearMemoryCache(Context context);

    void clearAllCache(Context context);

    // =========
    // = other =
    // =========

    void lowMemory(Context context);

    // ===========
    // = display =
    // ===========

    void display(ImageView imageView, String url);

    void display(ImageView imageView, DevSource source);

    void display(ImageView imageView, String url, Config config);

    void display(ImageView imageView, DevSource source, Config config);

    <T> void display(ImageView imageView, String url, LoadListener<T> listener);

    <T> void display(ImageView imageView, DevSource source, LoadListener<T> listener);

    <T> void display(ImageView imageView, String url, Config config, LoadListener<T> listener);

    <T> void display(ImageView imageView, DevSource source, Config config, LoadListener<T> listener);

    // =

    void display(Fragment fragment, ImageView imageView, String url);

    void display(Fragment fragment, ImageView imageView, DevSource source);

    void display(Fragment fragment, ImageView imageView, String url, Config config);

    void display(Fragment fragment, ImageView imageView, DevSource source, Config config);

    <T> void display(Fragment fragment, ImageView imageView, String url, LoadListener<T> listener);

    <T> void display(Fragment fragment, ImageView imageView, DevSource source, LoadListener<T> listener);

    <T> void display(Fragment fragment, ImageView imageView, String url, Config config, LoadListener<T> listener);

    <T> void display(Fragment fragment, ImageView imageView, DevSource source, Config config, LoadListener<T> listener);

    // ========
    // = load =
    // ========

    <T> void loadImage(Context context, DevSource source, Config config, LoadListener<T> listener);

    <T> void loadImage(Fragment fragment, DevSource source, Config config, LoadListener<T> listener);

    <T> T loadImage(Context context, DevSource source, Config config, Class type);

    // =

    void loadBitmap(Context context, DevSource source, Config config, LoadListener<Bitmap> listener);

    void loadBitmap(Fragment fragment, DevSource source, Config config, LoadListener<Bitmap> listener);

    Bitmap loadBitmap(Context context, DevSource source, Config config);

    // =

    void loadDrawable(Context context, DevSource source, Config config, LoadListener<Drawable> listener);

    void loadDrawable(Fragment fragment, DevSource source, Config config, LoadListener<Drawable> listener);

    Drawable loadDrawable(Context context, DevSource source, Config config);
}