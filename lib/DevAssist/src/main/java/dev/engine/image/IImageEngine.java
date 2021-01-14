package dev.engine.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import dev.base.Source;

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

    void preload(Context context, Source source);

    void preload(Context context, Source source, Config config);

    // =========
    // = clear =
    // =========

    void clear(View view);

    void clear(Fragment fragment, View view);

    void clearDiskCache(Context context);

    void clearMemoryCache(Context context);

    void clearAllCache(Context context);

    // ===========
    // = display =
    // ===========

    void display(ImageView imageView, String url);

    void display(ImageView imageView, Source source);

    void display(ImageView imageView, String url, Config config);

    void display(ImageView imageView, Source source, Config config);

    <T> void display(ImageView imageView, String url, LoadListener<T> listener);

    <T> void display(ImageView imageView, String url, Config config, LoadListener<T> listener);

    <T> void display(ImageView imageView, Source source, LoadListener<T> listener);

    <T> void display(ImageView imageView, Source source, Config config, LoadListener<T> listener);

    // =

    void display(Fragment fragment, ImageView imageView, String url);

    void display(Fragment fragment, ImageView imageView, Source source);

    void display(Fragment fragment, ImageView imageView, String url, Config Config);

    void display(Fragment fragment, ImageView imageView, Source source, Config Config);

    <T> void display(Fragment fragment, ImageView imageView, String url, LoadListener<T> listener);

    <T> void display(Fragment fragment, ImageView imageView, Source source, LoadListener<T> listener);

    <T> void display(Fragment fragment, ImageView imageView, String url, Config Config, LoadListener<T> listener);

    <T> void display(Fragment fragment, ImageView imageView, Source source, Config Config, LoadListener<T> listener);

    // ========
    // = load =
    // ========

    <T> void loadImage(Context context, Source source, Config Config, LoadListener<T> listener);

    <T> void loadImage(Fragment fragment, Source source, Config Config, LoadListener<T> listener);

    <T> T loadImage(Context context, Source source, Config Config);

    // =

    void loadBitmap(Context context, Source source, Config Config, LoadListener<Bitmap> listener);

    void loadBitmap(Fragment fragment, Source source, Config Config, LoadListener<Bitmap> listener);

    Bitmap loadBitmap(Context context, Source source, Config Config);

    // =

    void loadDrawable(Context context, Source source, Config Config, LoadListener<Drawable> listener);

    void loadDrawable(Fragment fragment, Source source, Config Config, LoadListener<Drawable> listener);

    Drawable loadDrawable(Context context, Source source, Config Config);
}