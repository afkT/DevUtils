package dev.utils.common.able;

/**
 * detail: 通用 Error 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Error 接口定义存储类
 *     全部接口只定义一个方法 tryCatch() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Errorable {

    private Errorable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Error<T> {

        T tryCatch();
    }

    // =======
    // = 有参 =
    // =======

    public interface ErrorByParam<T, Param> {

        T tryCatch(Param param);
    }

    public interface ErrorByParam2<T, Param, Param2> {

        T tryCatch(
                Param param,
                Param2 param2
        );
    }

    public interface ErrorByParam3<T, Param, Param2, Param3> {

        T tryCatch(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface ErrorByParamArgs<T, Param> {

        T tryCatch(Param... args);
    }
}