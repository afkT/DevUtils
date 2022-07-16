package dev.utils.common.able;

/**
 * detail: 通用 Search 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Search 接口定义存储类
 *     全部接口只定义一个方法 search() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Searchable {

    private Searchable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Search<T> {

        T search();
    }

    // =======
    // = 有参 =
    // =======

    public interface SearchByParam<T, Param> {

        T search(Param param);
    }

    public interface SearchByParam2<T, Param, Param2> {

        T search(
                Param param,
                Param2 param2
        );
    }

    public interface SearchByParam3<T, Param, Param2, Param3> {

        T search(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface SearchByParamArgs<T, Param> {

        T search(Param... args);
    }
}