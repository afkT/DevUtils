package dev.utils.common.able;

/**
 * detail: 通用 Query 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Query 接口定义存储类
 *     全部接口只定义一个方法 query() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Queryable {

    private Queryable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Query<T> {

        T query();
    }

    // =======
    // = 有参 =
    // =======

    public interface QueryByParam<T, Param> {

        T query(Param param);
    }

    public interface QueryByParam2<T, Param, Param2> {

        T query(
                Param param,
                Param2 param2
        );
    }

    public interface QueryByParam3<T, Param, Param2, Param3> {

        T query(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface QueryByParamArgs<T, Param> {

        T query(Param... args);
    }
}