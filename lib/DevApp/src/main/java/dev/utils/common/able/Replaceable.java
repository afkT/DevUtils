package dev.utils.common.able;

/**
 * detail: 通用 Replace 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Replace 接口定义存储类
 *     全部接口只定义一个方法 replace() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Replaceable {

    private Replaceable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Replace<T> {

        T replace();
    }

    // =======
    // = 有参 =
    // =======

    public interface ReplaceByParam<T, Param> {

        T replace(Param param);
    }

    public interface ReplaceByParam2<T, Param, Param2> {

        T replace(
                Param param,
                Param2 param2
        );
    }

    public interface ReplaceByParam3<T, Param, Param2, Param3> {

        T replace(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface ReplaceByParamArgs<T, Param> {

        T replace(Param... args);
    }
}