package dev.callback;

import dev.base.DevObject;

/**
 * detail: 抽象回调 ( 基类 )
 * @author Ttt
 */
public abstract class AbstractCallback<T>
        extends DevObject<T> {

    public AbstractCallback() {
    }

    public AbstractCallback(final T value) {
        super(value);
    }

    public AbstractCallback(
            final T value,
            final Object tag
    ) {
        super(value, tag);
    }
}