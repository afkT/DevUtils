package dev.utils.common.able;

/**
 * detail: 通用 To 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 To 接口定义存储类
 *     全部接口只定义一个方法 to() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Toable {

    private Toable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface To<T> {

        T to();
    }

    // =======
    // = 有参 =
    // =======

    public interface ToByParam<T, Param> {

        T to(Param param);
    }

    public interface ToByParam2<T, Param, Param2> {

        T to(
                Param param,
                Param2 param2
        );
    }

    public interface ToByParam3<T, Param, Param2, Param3> {

        T to(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface ToByParamArgs<T, Param> {

        T to(Param... args);
    }
}