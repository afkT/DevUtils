package dev.utils.common.able;

/**
 * detail: 通用 Read 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Read 接口定义存储类
 *     全部接口只定义一个方法 read() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Readable {

    private Readable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Read<T> {

        T read();
    }

    // =======
    // = 有参 =
    // =======

    public interface ReadByParam<T, Param> {

        T read(Param param);
    }

    public interface ReadByParam2<T, Param, Param2> {

        T read(
                Param param,
                Param2 param2
        );
    }

    public interface ReadByParam3<T, Param, Param2, Param3> {

        T read(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface ReadByParamArgs<T, Param> {

        T read(Param... args);
    }
}