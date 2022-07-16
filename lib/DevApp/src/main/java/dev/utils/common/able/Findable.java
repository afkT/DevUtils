package dev.utils.common.able;

/**
 * detail: 通用 Find 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Find 接口定义存储类
 *     全部接口只定义一个方法 find() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Findable {

    private Findable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Find<T> {

        T find();
    }

    // =======
    // = 有参 =
    // =======

    public interface FindByParam<T, Param> {

        T find(Param param);
    }

    public interface FindByParam2<T, Param, Param2> {

        T find(
                Param param,
                Param2 param2
        );
    }

    public interface FindByParam3<T, Param, Param2, Param3> {

        T find(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface FindByParamArgs<T, Param> {

        T find(Param... args);
    }
}