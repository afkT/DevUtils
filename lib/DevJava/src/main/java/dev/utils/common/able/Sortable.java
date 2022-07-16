package dev.utils.common.able;

/**
 * detail: 通用 Sort 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Sort 接口定义存储类
 *     全部接口只定义一个方法 sort() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Sortable {

    private Sortable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Sort<T> {

        T sort();
    }

    // =======
    // = 有参 =
    // =======

    public interface SortByParam<T, Param> {

        T sort(Param param);
    }

    public interface SortByParam2<T, Param, Param2> {

        T sort(
                Param param,
                Param2 param2
        );
    }

    public interface SortByParam3<T, Param, Param2, Param3> {

        T sort(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface SortByParamArgs<T, Param> {

        T sort(Param... args);
    }
}