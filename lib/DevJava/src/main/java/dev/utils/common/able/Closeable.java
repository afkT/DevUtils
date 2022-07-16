package dev.utils.common.able;

/**
 * detail: 通用 Close 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Close 接口定义存储类
 *     全部接口只定义一个方法 close() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Closeable {

    private Closeable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Close<T> {

        T close();
    }

    // =======
    // = 有参 =
    // =======

    public interface CloseByParam<T, Param> {

        T close(Param param);
    }

    public interface CloseByParam2<T, Param, Param2> {

        T close(
                Param param,
                Param2 param2
        );
    }

    public interface CloseByParam3<T, Param, Param2, Param3> {

        T close(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface CloseByParamArgs<T, Param> {

        T close(Param... args);
    }
}