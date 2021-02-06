package dev.base;

/**
 * detail: 状态实体类
 * @author Ttt
 */
public class DevState<T>
        extends DevObject<T> {

    public DevState() {
    }

    public DevState(final T object) {
        super(object);
    }

    public DevState(
            final T object,
            final Object tag
    ) {
        super(object, tag);
    }
}