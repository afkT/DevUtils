package dev.base;

/**
 * detail: 状态实体类
 * @author Ttt
 */
public class DevState<T>
        extends DevObject<T> {

    public DevState() {
    }

    public DevState(final T value) {
        super(value);
    }

    public DevState(
            final T value,
            final Object tag
    ) {
        super(value, tag);
    }
}