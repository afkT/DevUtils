package dev.callback;

import dev.base.DevObject;

/**
 * detail: 回调基类
 * @author Ttt
 */
public class BaseCallback<T>
        extends DevObject<T> {

    public BaseCallback() {
    }

    public BaseCallback(final T value) {
        super(value);
    }

    public BaseCallback(
            final T value,
            final Object tag
    ) {
        super(value, tag);
    }
}