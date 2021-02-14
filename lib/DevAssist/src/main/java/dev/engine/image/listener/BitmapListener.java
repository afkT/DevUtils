package dev.engine.image.listener;

import android.graphics.Bitmap;

import dev.engine.image.LoadListener;

/**
 * detail: Bitmap 加载事件
 * @author Ttt
 */
public abstract class BitmapListener
        implements LoadListener<Bitmap> {

    @Override
    public final Class getTranscodeType() {
        return Bitmap.class;
    }
}