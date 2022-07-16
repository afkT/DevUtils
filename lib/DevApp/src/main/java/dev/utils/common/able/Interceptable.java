package dev.utils.common.able;

/**
 * detail: 通用 Intercept 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Intercept 接口定义存储类
 *     全部接口只定义一个方法 intercept() 且返回值一致
 *     唯一差异就是参数不同
 *     <p></p>
 *     代码与 {@link Getable} 完全一致, 只有方法名、接口名不同
 * </pre>
 */
public final class Interceptable {

    private Interceptable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Intercept<T> {

        T intercept();
    }

    // =======
    // = 有参 =
    // =======

    public interface InterceptByParam<T, Param> {

        T intercept(Param param);
    }

    public interface InterceptByParam2<T, Param, Param2> {

        T intercept(
                Param param,
                Param2 param2
        );
    }

    public interface InterceptByParam3<T, Param, Param2, Param3> {

        T intercept(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface InterceptByParamArgs<T, Param> {

        T intercept(Param... args);
    }
}