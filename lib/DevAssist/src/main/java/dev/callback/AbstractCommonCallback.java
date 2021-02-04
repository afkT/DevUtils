package dev.callback;

/**
 * detail: 通用抽象回调 ( 基类 )
 * @author Ttt
 */
public abstract class AbstractCommonCallback<T>
        extends AbstractCallback<T> {

    public AbstractCommonCallback() {
    }

    public AbstractCommonCallback(final T value) {
        super(value);
    }

    public AbstractCommonCallback(
            final T value,
            final Object object
    ) {
        super(value, object);
    }
}