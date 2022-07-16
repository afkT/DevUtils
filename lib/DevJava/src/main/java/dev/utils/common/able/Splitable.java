package dev.utils.common.able;

/**
 * detail: 通用 Split 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Split 接口定义存储类
 *     全部接口只定义一个方法 split() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Splitable {

    private Splitable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Split<T> {

        T split();
    }

    // =======
    // = 有参 =
    // =======

    public interface SplitByParam<T, Param> {

        T split(Param param);
    }

    public interface SplitByParam2<T, Param, Param2> {

        T split(
                Param param,
                Param2 param2
        );
    }

    public interface SplitByParam3<T, Param, Param2, Param3> {

        T split(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface SplitByParamArgs<T, Param> {

        T split(Param... args);
    }
}