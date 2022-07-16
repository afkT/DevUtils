package dev.utils.common.able;

/**
 * detail: 通用 Correct 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Correct 接口定义存储类
 *     全部接口只定义一个方法 correct() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Correctable {

    private Correctable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Correct<T> {

        T correct();
    }

    // =======
    // = 有参 =
    // =======

    public interface CorrectByParam<T, Param> {

        T correct(Param param);
    }

    public interface CorrectByParam2<T, Param, Param2> {

        T correct(
                Param param,
                Param2 param2
        );
    }

    public interface CorrectByParam3<T, Param, Param2, Param3> {

        T correct(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface CorrectByParamArgs<T, Param> {

        T correct(Param... args);
    }
}