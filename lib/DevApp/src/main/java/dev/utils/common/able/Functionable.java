package dev.utils.common.able;

/**
 * detail: 通用 Function 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Function 接口定义存储类
 *     全部接口只定义一个方法 apply() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Functionable {

    private Functionable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Function<T> {

        T apply();
    }

    // =======
    // = 有参 =
    // =======

    public interface FunctionByParam<T, Param> {

        T apply(Param param);
    }

    public interface FunctionByParam2<T, Param, Param2> {

        T apply(
                Param param,
                Param2 param2
        );
    }

    public interface FunctionByParam3<T, Param, Param2, Param3> {

        T apply(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface FunctionByParamArgs<T, Param> {

        T apply(Param... args);
    }
}