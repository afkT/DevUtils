package dev.engine.image.listener;

import android.graphics.drawable.Drawable;

import dev.engine.image.LoadListener;

/**
 * detail: Drawable 加载事件
 * @author Ttt
 */
public abstract class DrawableListener
        implements LoadListener<Drawable> {

    @Override
    public final Class getTranscodeType() {
        return Drawable.class;
    }
}