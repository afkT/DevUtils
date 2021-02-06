package dev.base;

/**
 * detail: 数据源操作实体类
 * @author Ttt
 */
public class DevDataSource<T>
        extends DevObject<T> {

    public DevDataSource() {
    }

    public DevDataSource(final T object) {
        super(object);
    }

    public DevDataSource(
            final T object,
            final Object tag
    ) {
        super(object, tag);
    }
}