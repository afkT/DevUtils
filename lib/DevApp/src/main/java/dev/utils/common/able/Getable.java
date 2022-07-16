package dev.utils.common.able;

/**
 * detail: 通用 Get 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Get 接口定义存储类
 *     全部接口只定义一个方法 get() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Getable {

    private Getable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Get<T> {

        T get();
    }

    // =======
    // = 有参 =
    // =======

    public interface GetByParam<T, Param> {

        T get(Param param);
    }

    public interface GetByParam2<T, Param, Param2> {

        T get(
                Param param,
                Param2 param2
        );
    }

    public interface GetByParam3<T, Param, Param2, Param3> {

        T get(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface GetByParamArgs<T, Param> {

        T get(Param... args);
    }
}