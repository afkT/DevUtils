package dev.utils.common.able;

/**
 * detail: 通用 By 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 By 接口定义存储类
 *     全部接口只定义一个方法 by() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Byable {

    private Byable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface By<T> {

        T by();
    }

    // =======
    // = 有参 =
    // =======

    public interface ByByParam<T, Param> {

        T by(Param param);
    }

    public interface ByByParam2<T, Param, Param2> {

        T by(
                Param param,
                Param2 param2
        );
    }

    public interface ByByParam3<T, Param, Param2, Param3> {

        T by(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface ByByParamArgs<T, Param> {

        T by(Param... args);
    }
}