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

    public BaseCallback(final T object) {
        super(object);
    }

    public BaseCallback(
            final T object,
            final Object tag
    ) {
        super(object, tag);
    }
}