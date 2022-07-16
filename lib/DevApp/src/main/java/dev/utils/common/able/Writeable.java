package dev.utils.common.able;

/**
 * detail: 通用 Write 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Write 接口定义存储类
 *     全部接口只定义一个方法 write() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Writeable {

    private Writeable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Write<T> {

        T write();
    }

    // =======
    // = 有参 =
    // =======

    public interface WriteByParam<T, Param> {

        T write(Param param);
    }

    public interface WriteByParam2<T, Param, Param2> {

        T write(
                Param param,
                Param2 param2
        );
    }

    public interface WriteByParam3<T, Param, Param2, Param3> {

        T write(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface WriteByParamArgs<T, Param> {

        T write(Param... args);
    }
}