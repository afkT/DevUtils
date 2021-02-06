package dev.base;

/**
 * detail: 数据源操作实体类
 * @author Ttt
 */
public class DevDataSource<T>
        extends DevObject<T> {

    public DevDataSource() {
    }

    public DevDataSource(final T value) {
        super(value);
    }

    public DevDataSource(
            final T value,
            final Object tag
    ) {
        super(value, tag);
    }
}