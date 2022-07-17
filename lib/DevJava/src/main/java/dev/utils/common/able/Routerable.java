package dev.utils.common.able;

/**
 * detail: 通用 Router 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Router 接口定义存储类
 *     全部接口只定义一个方法 router() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Routerable {

    private Routerable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Router<T> {

        T router();
    }

    // =======
    // = 有参 =
    // =======

    public interface RouterByParam<T, Param> {

        T router(Param param);
    }

    public interface RouterByParam2<T, Param, Param2> {

        T router(
                Param param,
                Param2 param2
        );
    }

    public interface RouterByParam3<T, Param, Param2, Param3> {

        T router(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface RouterByParamArgs<T, Param> {

        T router(Param... args);
    }
}