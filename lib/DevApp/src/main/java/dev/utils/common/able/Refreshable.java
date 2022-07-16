package dev.utils.common.able;

/**
 * detail: 通用 Refresh 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Refresh 接口定义存储类
 *     全部接口只定义一个方法 refresh() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Refreshable {

    private Refreshable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Refresh<T> {

        T refresh();
    }

    // =======
    // = 有参 =
    // =======

    public interface RefreshByParam<T, Param> {

        T refresh(Param param);
    }

    public interface RefreshByParam2<T, Param, Param2> {

        T refresh(
                Param param,
                Param2 param2
        );
    }

    public interface RefreshByParam3<T, Param, Param2, Param3> {

        T refresh(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface RefreshByParamArgs<T, Param> {

        T refresh(Param... args);
    }
}