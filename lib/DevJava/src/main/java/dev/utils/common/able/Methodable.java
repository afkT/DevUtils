package dev.utils.common.able;

/**
 * detail: 通用 Method 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Method 接口定义存储类
 *     全部接口只定义一个方法 invoke() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Methodable {

    private Methodable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Method<T> {

        T invoke();
    }

    // =======
    // = 有参 =
    // =======

    public interface MethodByParam<T, Param> {

        T invoke(Param param);
    }

    public interface MethodByParam2<T, Param, Param2> {

        T invoke(
                Param param,
                Param2 param2
        );
    }

    public interface MethodByParam3<T, Param, Param2, Param3> {

        T invoke(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface MethodByParamArgs<T, Param> {

        T invoke(Param... args);
    }
}