package dev.engine.image;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * detail: Image Engine 接口
 * @author Ttt
 */
public interface IImageEngine {

    /**
     * detail: Image Config
     * @author Ttt
     */
    class ImageConfig {
    }

    /**
     * detail: 图片加载回调
     * @param <TranscodeType> 泛型
     * @author Ttt
     */
    interface ImageCallback<TranscodeType> {

        /**
         * 获取转码类型
         * @return TranscodeType Class
         */
        Class getTranscodeType();

        /**
         * 响应回调
         * @param uri   Image Uri
         * @param value Value
         */
        void onResponse(
                String uri,
                TranscodeType value
        );

        /**
         * 失败回调
         * @param uri       Image Uri
         * @param throwable 异常
         */
        void onFailure(
                String uri,
                Throwable throwable
        );
    }

    /**
     * detail: Bitmap Callback
     * @author Ttt
     */
    abstract class BitmapCallback implements ImageCallback<Bitmap> {

        @Override
        public final Class getTranscodeType() {
            return Bitmap.class;
        }

        @Override
        public abstract void onResponse(
                String uri,
                Bitmap value
        );

        @Override
        public abstract void onFailure(
                String uri,
                Throwable throwable
        );
    }

    /**
     * detail: Drawable Callback
     * @author Ttt
     */
    abstract class DrawableCallback implements ImageCallback<Drawable> {

        @Override
        public final Class getTranscodeType() {
            return Drawable.class;
        }

        @Override
        public abstract void onResponse(
                String uri,
                Drawable value
        );

        @Override
        public abstract void onFailure(
                String uri,
                Throwable throwable
        );
    }

    // ===========
    // = 图片显示 =
    // ===========

    /**
     * 图片显示
     * @param uri       Image Uri
     * @param imageView ImageView
     */
    void displayImage(
            String uri,
            ImageView imageView
    );

    /**
     * 图片显示
     * @param uri          Image Uri
     * @param imageView    ImageView
     * @param defaultImage Default resource image
     */
    void displayImage(
            String uri,
            ImageView imageView,
            int defaultImage
    );

    /**
     * 图片显示
     * @param uri       Image Uri
     * @param imageView ImageView
     * @param isDefault 是否使用默认配置
     */
    void displayImage(
            String uri,
            ImageView imageView,
            boolean isDefault
    );

    /**
     * 图片显示
     * @param uri       Image Uri
     * @param imageView ImageView
     * @param config    {@link ImageConfig} 加载配置
     */
    void displayImage(
            String uri,
            ImageView imageView,
            ImageConfig config
    );

    // ===========
    // = 图片加载 =
    // ===========

    /**
     * 图片加载
     * @param uri      Image Uri
     * @param callback 图片加载回调
     */
    void loadImage(
            String uri,
            ImageCallback callback
    );

    /**
     * 图片加载
     * @param uri      Image Uri
     * @param callback 图片加载回调
     * @param config   {@link ImageConfig} 加载配置
     */
    void loadImage(
            String uri,
            ImageCallback callback,
            ImageConfig config
    );

    // ===========
    // = 其他方法 =
    // ===========

    /**
     * 清除磁盘缓存
     */
    void clearDiskCache();

    /**
     * 清除内存缓存
     */
    void clearMemoryCache();
}