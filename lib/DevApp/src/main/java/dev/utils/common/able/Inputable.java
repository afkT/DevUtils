package dev.utils.common.able;

/**
 * detail: 通用 Input 接口
 * @author Ttt
 * <pre>
 *     所有通用快捷 Input 接口定义存储类
 *     全部接口只定义一个方法 input() 且返回值一致
 *     唯一差异就是参数不同
 * </pre>
 */
public final class Inputable {

    private Inputable() {
    }

    // =======
    // = 无参 =
    // =======

    public interface Input<T> {

        T input();
    }

    // =======
    // = 有参 =
    // =======

    public interface InputByParam<T, Param> {

        T input(Param param);
    }

    public interface InputByParam2<T, Param, Param2> {

        T input(
                Param param,
                Param2 param2
        );
    }

    public interface InputByParam3<T, Param, Param2, Param3> {

        T input(
                Param param,
                Param2 param2,
                Param3 param3
        );
    }

    public interface InputByParamArgs<T, Param> {

        T input(Param... args);
    }
}