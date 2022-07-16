package dev.utils.common.able;

/**
 * detail: 通用 IO 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 IO 接口定义存储类
 *     全部接口只定义一个方法 io() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class IOable {

    private IOable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface IO<T> {

        T io();
    }

    // =======
    // = 有参 =
    // =======

    public interface IOByParam<T, Param> {

        T io(Param param);
    }

    public interface IOByParam2<T, Param, Param2> {

        T io(
                Param param,
                Param2 param2
        );
    }

    public interface IOByParam3<T, Param, Param2, Param3> {

        T io(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface IOByParamArgs<T, Param> {

        T io(Param... args);
    }
}