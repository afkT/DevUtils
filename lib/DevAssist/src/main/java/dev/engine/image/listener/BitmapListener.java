package dev.engine.image.listener;

import android.graphics.Bitmap;

/**
 * detail: Bitmap 加载事件
 * @author Ttt
 */
public abstract class BitmapListener
        implements LoadListener<Bitmap> {

    @Override
    public final Class<?> getTranscodeType() {
        return Bitmap.class;
    }
}