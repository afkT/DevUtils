package dev.callback;

import dev.base.DevObject;

/**
 * detail: 抽象回调 ( 基类 )
 *
 * @author Ttt
 */
public abstract class AbstractCallback<T>
        extends DevObject<T> {

    public AbstractCallback() {
    }

    public AbstractCallback(T value) {
        super(value);
    }

    public AbstractCallback(
            T value,
            Object tag
    ) {
        super(value, tag);
    }
}